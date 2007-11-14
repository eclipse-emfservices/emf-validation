/**
 * <copyright>
 *
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Radek Dvorak (Borland) - Bugzilla 165458
 *
 * </copyright>
 *
 * $Id: AbstractOCLModelConstraint.java,v 1.5 2007/11/14 18:03:54 cdamus Exp $
 */

package org.eclipse.emf.validation.ocl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

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
 * {@link org.eclipse.emf.validation.model.IModelConstraint} interface.
 * This class considers the OCL constraint text as a context-free expression,
 * possibly targeting multiple model types (because the validation framework
 * permits declaration of any number of targets).  A separate OCL {@link Query}
 * is created and cached for each of these target types as required.
 * </p><p>
 * Any problems in parsing or executing the OCL will result in the constraint
 * being disabled at run-time.
 * </p><p>
 * This class is intended to be used by clients of the validation framework that
 * need to customize the OCL parsing environment for their constraints.
 * </p><p>
 * The generic type parameters declared by this class correspond to the like-named
 * parameters of the {@link EnvironmentFactory} interface.
 * </p>
 * 
 * @param <C> The metaclass corresponding to the UML Classifier in the environment
 *      provided by subclasses.
 * @param <CT> The metaclass corresponding to the UML Constraint in the environment
 *      provided by subclasses.
 * @param <CLS> The metaclass corresponding to the UML Class in the environment
 *      provided by subclasses.
 * @param <E> The metaclass of run-time instances in the environment
 *      provided by subclasses.
 * 
 * @author Christian W. Damus (cdamus)
 */
public abstract class AbstractOCLModelConstraint<C, CT, CLS, E> implements IModelConstraint {
	private final IConstraintDescriptor descriptor;
    
    /**
     * A separate query is maintained for each EClass of model object
     * that this constraint handles.  Maintain the values in weak references
     * also, because the queries reference the EClasses that are the keys!
     */
    private final java.util.Map<EClass, Reference<?>> queries =
        new java.util.WeakHashMap<EClass, Reference<?>>();
    
    private QueryManager queryManager;
    
	/**
	 * Initializes me with the <code>descriptor</code> which contains my OCL
	 * body.
	 *  
	 * @param descriptor the descriptor, which must contain an OCL expression
	 *   in its body
	 */
	public AbstractOCLModelConstraint(IConstraintDescriptor descriptor) {
		this.descriptor = descriptor;
	}
    
    /**
     * Creates an Environment Factory suitable for the parsing of the client's
     * OCL constraints.  This default implementation returns <code>null</code>,
     * signalling that compatibility with the OCL 1.0 API is required.  In such
     * case, the result of the {@link #createEnvironmentFactory()} method is
     * used.
     * 
     * @return an environment factory for parsing OCL constraints, or
     *     <code>null</code> to use the result of the
     *     {@link #createEnvironmentFactory()} method
     *
     * @since 1.1
     */
    protected EnvironmentFactory<?, C, ?, ?, ?, ?, ?, ?, ?, CT, CLS, E>
    createOCLEnvironmentFactory() {
        return null;
    }
	
	/**
     * Creates an environment factory for compatibility with the OCL 1.0 API.
     * 
	 * @deprecated Override the {@link #createOCLEnvironmentFactory()} method,
     * instead.
	 */
	@Deprecated
	protected org.eclipse.emf.ocl.parser.EnvironmentFactory createEnvironmentFactory() {
        return org.eclipse.emf.ocl.parser.EnvironmentFactory.ECORE_INSTANCE;
    }


    /**
     * Obtains the cached OCL query/constraint that implements me for the
     * specified element's metaclass.
     * 
     * @param target a model element
     * @return the corresponding OCL query
     */
    public Query<C, CLS, E> getConstraintCondition(EObject target) {
        Query<C, CLS, E> result = null;
        EClass eClass = target.eClass();
        
        @SuppressWarnings("unchecked")
        Reference<Query<C, CLS, E>> reference =
            (Reference<Query<C, CLS, E>>) queries.get(eClass);
        if (reference != null) {
            result = reference.get();
        }

        if (result == null) {
            // lazily initialize the condition.  If a RuntimeException is thrown
            //   by the QueryFactory because of a bad OCL expression, then the
            //   constraints framework will catch it and disable me
            OCL<?, C, ?, ?, ?, ?, ?, ?, ?, CT, CLS, E> ocl = OCL.newInstance(
                createOCLEnvironmentFactory());
            OCLHelper<C, ?, ?, CT> helper = ocl.createOCLHelper();
            helper.setInstanceContext(target);
            
            try {
                result = ocl.createQuery(helper.createInvariant(getDescriptor().getBody()));
            } catch(ParserException e) {    
                // TODO - a better error handling ?
                throw new RuntimeException(e);
            }

            queries.put(eClass, new WeakReference<Query<C, CLS, E>>(result));
        }
        
        return result;
    }
    
	/**
	 * Obtains the cached OCL query/constraint that implements me for the
	 * specified EMF type.
	 * 
	 * @param eClass an EMF model object type
	 * @return the corresponding OCL query
     * 
     * @deprecated Use the {@link #getConstraintCondition(EObject)} method, instead.
	 */
	@Deprecated
	public org.eclipse.emf.ocl.query.Query getCondition(EClass eClass) {
		org.eclipse.emf.ocl.query.Query result = null;
		
        @SuppressWarnings("unchecked")
		Reference<org.eclipse.emf.ocl.query.Query> reference =
            (Reference<org.eclipse.emf.ocl.query.Query>) queries.get(eClass);
		if (reference != null) {
			result = reference.get();
		}

		if (result == null) {
			// lazily initialize the condition.  If a RuntimeException is thrown
			//   by the QueryFactory because of a bad OCL expression, then the
			//   constraints framework will catch it and disable me
			org.eclipse.emf.ocl.helper.IOCLHelper oclHelper =
                org.eclipse.emf.ocl.helper.HelperUtil.createOCLHelper(
                    createEnvironmentFactory());
			oclHelper.setContext(eClass);
			try {
				result = org.eclipse.emf.ocl.query.QueryFactory.eINSTANCE.createQuery(
                    oclHelper.createQuery(getDescriptor().getBody()));
			} catch(org.eclipse.emf.ocl.helper.OCLParsingException e) {	
				// TODO - a better error handling ?
				throw new RuntimeException(e);
			}

			queries.put(eClass,
                new WeakReference<org.eclipse.emf.ocl.query.Query>(result));
		}

		return result;
	}

	// implements the inherited method
	public IStatus validate(IValidationContext ctx) {
		EObject target = ctx.getTarget();
		
		if (getQueryManager().check(target)) {
			return ctx.createSuccessStatus();
		} else {
			// OCL constraints only support the target object as an extraction
			//   variable and result locus, as OCL has no way to provide
			//   additional extractions.  Also, there is no way for the OCL
			//   to access the context object
			return ctx.createFailureStatus(target);
		}
	}
    
    private QueryManager getQueryManager() {
        if (queryManager == null) {
            queryManager = new QueryManager();
        }
        
        return queryManager;
    }
	
	
	/* (non-Javadoc)
	 * Implements the interface method.
	 */
	public IConstraintDescriptor getDescriptor() {
		return descriptor;
	}
    
    /**
     * An object that knows how to obtain and evaluate the query implementation
     * appropriate to the constraint's environment factory, accounting for
     * whether it is using the OCL 1.0 or later API.
     *
     * @author Christian W. Damus (cdamus)
     */
    private final class QueryManager {
        private final boolean isOldStyle;
        
        QueryManager() {
            isOldStyle = createOCLEnvironmentFactory() == null;
        }
        
        /**
         * Obtains and checks the appropriate parsed constraint for the specified
         * target element.
         * 
         * @param target an element to be validated
         * @return whether it passed the constraint
         */
        @SuppressWarnings("deprecation")
        boolean check(EObject target) {
            if (isOldStyle) {
                org.eclipse.emf.ocl.query.Query query = getCondition(target.eClass());
                return query.check(target);
            }
            
            Query<C, CLS, E> query = getConstraintCondition(target);
            return query.check(target);
        }
    }
}
