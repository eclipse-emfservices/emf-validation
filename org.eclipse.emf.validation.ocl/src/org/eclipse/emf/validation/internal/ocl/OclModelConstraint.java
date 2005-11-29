/**
 * <copyright>
 *
 * Copyright (c) 2003-2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */

package org.eclipse.emf.validation.internal.ocl;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ocl.query.Query;
import org.eclipse.emf.ocl.query.QueryFactory;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;

/**
 * <p>
 * An OCL-language implementation of the
 * {@link org.eclipse.emf.validation.model.IModelConstraint} interface.
 * This class encapsulates an {@link org.eclipse.emf.query.ocl.conditions.OclConstraintCondition} from the
 * <i>EMF Query Framework</i> plug-in which actually implements the OCL
 * constraint logic.  Any problems in parsing or executing the OCL will result
 * in the constraint being disabled at run-time.
 * </p>
 * <p>
 * This class is not intended to be used by clients of the validation framework.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class OclModelConstraint implements IModelConstraint {
	private final IConstraintDescriptor descriptor;
	
	/**
	 * A separate query is maintained for each EClass of model object
	 * that this constraint handles.  Maintain the values in weak references
	 * also, because the queries reference the EClasses that are the keys!
	 */
	private final java.util.Map queries = new java.util.WeakHashMap();

	/**
	 * Initializes me with the <code>descriptor</code> which contains my OCL
	 * body.
	 *  
	 * @param descriptor the descriptor, which must contain an OCL expression
	 *   in its body
	 */
	public OclModelConstraint(IConstraintDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	/**
	 * Obtains the cached OCL query/constraint that implements me for the
	 * specified EMF type.
	 * 
	 * @param eClass an EMF model object type
	 * @return the corresponding OCL query
	 */
	public Query getCondition(EClass eClass) {
		Query result = null;
		
		Reference reference = (Reference) queries.get(eClass);
		if (reference != null) {
			result = (Query) reference.get();
		}

		if (result == null) {
			// lazily initialize the condition.  If a RuntimeException is thrown
			//   by the QueryFactory because of a bad OCL expression, then the
			//   constraints framework will catch it and disable me

			result = QueryFactory.eINSTANCE.createQuery(
						getDescriptor().getBody(),
						eClass);

			queries.put(eClass, new WeakReference(result));
		}

		return result;
	}

	// implements the inherited method
	public IStatus validate(IValidationContext ctx) {
		EObject target = ctx.getTarget();
		
		if (getCondition(target.eClass()).check(target)) {
			return ctx.createSuccessStatus();
		} else {
			// OCL constraints only support the target object as an extraction
			//   variable and result locus, as OCL has no way to provide
			//   additional extractions.  Also, there is no way for the OCL
			//   to access the context object
			return ctx.createFailureStatus(new Object[]{target});
		}
	}
	
	
	/* (non-Javadoc)
	 * Implements the interface method.
	 */
	public IConstraintDescriptor getDescriptor() {
		return descriptor;
	}
}
