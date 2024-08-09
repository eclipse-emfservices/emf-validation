/**
 * Copyright (c) 2003, 2024 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Radek Dvorak (Borland) - Bugzilla 165458
 */
package org.eclipse.emf.validation.ocl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Optional;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.ocl.EnvironmentFactory;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.helper.OCLHelper;

/**
 * <p>
 * An OCL-language implementation of the
 * {@link org.eclipse.emf.validation.model.IModelConstraint} interface. This
 * class considers the OCL constraint text as a context-free expression,
 * possibly targeting multiple model types (because the validation framework
 * permits declaration of any number of targets). A separate OCL {@link Query}
 * is created and cached for each of these target types as required.
 * </p>
 * <p>
 * Any problems in parsing or executing the OCL will result in the constraint
 * being disabled at run-time.
 * </p>
 * <p>
 * This class is intended to be used by clients of the validation framework that
 * need to customize the OCL parsing environment for their constraints.
 * </p>
 * <p>
 * The generic type parameters declared by this class correspond to the
 * like-named parameters of the {@link EnvironmentFactory} interface.
 * </p>
 *
 * @param <C>   The metaclass corresponding to the UML Classifier in the
 *              environment provided by subclasses.
 * @param <CT>  The metaclass corresponding to the UML Constraint in the
 *              environment provided by subclasses.
 * @param <CLS> The metaclass corresponding to the UML Class in the environment
 *              provided by subclasses.
 * @param <E>   The metaclass of run-time instances in the environment provided
 *              by subclasses.
 *
 * @author Christian W. Damus (cdamus)
 */
public abstract class AbstractOCLModelConstraint<C, CT, CLS, E> implements IModelConstraint {
	private static final Method OLD_OCL_NEW_INSTANCE_METHOD = getOCLMethod("newInstance");

	private static final Method NEW_OCL_NEW_INSTANCE_METHOD = getOCLMethod("newInstanceAbstract");
	  
	private final IConstraintDescriptor descriptor;

	/**
	 * A separate query is maintained for each EClass of model object that this
	 * constraint handles. Maintain the values in weak references also, because the
	 * queries reference the EClasses that are the keys!
	 */
	private final java.util.Map<EClass, Reference<?>> queries = new java.util.WeakHashMap<>();

	private QueryManager queryManager;
	
	private static Method getOCLMethod(String methodName) {
		try {
			return OCL.class.getDeclaredMethod(methodName, EnvironmentFactory.class);
		} catch (Throwable ex) {
			return null;
		}
	}

	/**
	 * Initializes me with the <code>descriptor</code> which contains my OCL body.
	 *
	 * @param descriptor the descriptor, which must contain an OCL expression in its
	 *                   body
	 */
	public AbstractOCLModelConstraint(IConstraintDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * Creates an Environment Factory suitable for the parsing of the client's OCL
	 * constraints. This default implementation returns <code>null</code>,
	 * signalling that compatibility with the OCL 1.0 API is required. In such case,
	 * the result of the {@link #createEnvironmentFactory()} method is used.
	 *
	 * @return an environment factory for parsing OCL constraints, or
	 *         <code>null</code> to use the result of the
	 *         {@link #createEnvironmentFactory()} method
	 *
	 * @since 1.1
	 */
	protected EnvironmentFactory<?, C, ?, ?, ?, ?, ?, ?, ?, CT, CLS, E> createOCLEnvironmentFactory() {
		return null;
	}

	/**
	 * Obtains the cached OCL query/constraint that implements me for the specified
	 * element's metaclass.
	 *
	 * @param target a model element
	 * @return the corresponding OCL query
	 */
	public Query<C, CLS, E> getConstraintCondition(EObject target) {
		Query<C, CLS, E> result = null;
		EClass eClass = target.eClass();

		@SuppressWarnings("unchecked")
		Reference<Query<C, CLS, E>> reference = (Reference<Query<C, CLS, E>>) queries.get(eClass);
		if (reference != null) {
			result = reference.get();
		}

		if (result == null) {
			// lazily initialize the condition. If a RuntimeException is thrown
			// by the QueryFactory because of a bad OCL expression, then the
			// constraints framework will catch it and disable me
			OCL<?, C, ?, ?, ?, ?, ?, ?, ?, CT, CLS, E> ocl = createOCL(createOCLEnvironmentFactory());
			OCLHelper<C, ?, ?, CT> helper = ocl.createOCLHelper();
			helper.setInstanceContext(target);

			try {
				result = ocl.createQuery(helper.createInvariant(getDescriptor().getBody()));
			} catch (ParserException e) {
				// TODO - a better error handling ?
				throw new RuntimeException(e);
			}

			queries.put(eClass, new WeakReference<>(result));
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	private OCL<?, C, ?, ?, ?, ?, ?, ?, ?, CT, CLS, E> createOCL(EnvironmentFactory<?, C, ?, ?, ?, ?, ?, ?, ?, CT, CLS, E> environmentFactory) {
		Method newInstanceMethod = NEW_OCL_NEW_INSTANCE_METHOD;
		if (newInstanceMethod == null) {
			newInstanceMethod = OLD_OCL_NEW_INSTANCE_METHOD;
		}
		if (newInstanceMethod != null && Modifier.isStatic(newInstanceMethod.getModifiers())) {
			try {
				return (OCL<?, C, ?, ?, ?, ?, ?, ?, ?, CT, CLS, E>) newInstanceMethod.invoke(null, environmentFactory);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		throw new RuntimeException("No valid static factory method found for OCL");
	}

	// implements the inherited method
	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject target = ctx.getTarget();

		if (getQueryManager().check(target)) {
			return ctx.createSuccessStatus();
		} else {
			// OCL constraints only support the target object as an extraction
			// variable and result locus, as OCL has no way to provide
			// additional extractions. Also, there is no way for the OCL
			// to access the context object
			return ctx.createFailureStatus(target);
		}
	}

	private QueryManager getQueryManager() {
		if (queryManager == null) {
			queryManager = new QueryManager();
		}

		return queryManager;
	}

	/*
	 * (non-Javadoc) Implements the interface method.
	 */
	@Override
	public IConstraintDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * An object that knows how to obtain and evaluate the query implementation
	 * appropriate to the constraint's environment factory, accounting for whether
	 * it is using the OCL 1.0 or later API.
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private final class QueryManager {
		/**
		 * Obtains and checks the appropriate parsed constraint for the specified target
		 * element.
		 *
		 * @param target an element to be validated
		 * @return whether it passed the constraint
		 */
		boolean check(EObject target) {
			Query<C, CLS, E> query = getConstraintCondition(target);
			return query.check(target);
		}
	}
}
