/******************************************************************************
 * Copyright (c) 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.model;

import org.eclipse.core.runtime.IStatus;

import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.service.IConstraintDescriptor;


/**
 * <p>
 * Interface implemented by all constraint objects in the EMF model validation
 * framework, providing the {@link #validate validate} request.
 * </p>
 * @author Christian W. Damus (cdamus)
 */
public interface IModelConstraint {
	/**
	 * The status code reported in the {@link IStatus} when a constraint
	 * succeeds.
	 */
	public static final int STATUS_CODE_SUCCESS = 0;
	
	/**
	 * <p>
	 * Validates an object in the specified context.  The
	 * {@link IValidationContext#getTarget target} of the validation operation
	 * is available from the context object.
	 * </p>
	 * <p>
	 * <b>Note</b> that it is best to use the
	 * {@link IValidationContext#createSuccessStatus} and
	 * {@link IValidationContext#createFailureStatus} methods of the context
	 * object to create the status object returned from this method, to ensure
	 * that the status object returned is correctly handled by the validation
	 * system.
	 * </p>
	 * 
	 * @param ctx the validation context that provides access to the current
	 *         constraint evaluation environment.  The framework will never
	 *         pass a <code>null</code> value
	 * @return the status of validation of the target object. The
	 *         {@link IStatus#getSeverity()} of the record is either
	 *         {@link IStatus#OK} to indicate success,
	 *         or some other value to indicate that validation failed.
	 *         Must not return <code>null</code>
	 */
	IStatus validate(IValidationContext ctx);
	
	/**
	 * Obtains my descriptor, which provides a variety of meta-data about me.
	 * 
	 * @return my constraint descriptor
	 */
	IConstraintDescriptor getDescriptor();
}
