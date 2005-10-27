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


package org.eclipse.emf.validation.internal.util;

import org.eclipse.core.runtime.IStatus;

import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;

/**
 * <p>
 * A disabled constraint is a placeholder for a constraint that could not be
 * lazily initialized from its descriptor.  On the first attempt to validate
 * an object, it will emit a warning to the log indicating that it is disabled
 * and will be silent for the rest of the session. 
 * </p> 
 * <p>
 * This class is not intended to be used outside of the validation framework.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class DisabledConstraint implements IModelConstraint {
	private IStatus status = null;
	private final Throwable exception;
	private final IConstraintDescriptor descriptor;
	
	/**
	 * Initializes me with my descriptor.
	 * 
	 * @param descriptor my descriptor
	 * @param exception the exception which caused me to be disabled
	 */
	public DisabledConstraint(IConstraintDescriptor descriptor, Throwable exception) {
		this.descriptor = descriptor;
		descriptor.setError(exception);
		this.exception = exception;
	}

	/**
	 * Implements the inherited method by simply logging a message the first
	 * time that I am executed.
	 * 
	 * @return an informational status, in order to be as innocuous as possible
	 *    but still let the user know that the constraint is disabled
	 */
	public IStatus validate(IValidationContext ctx) {
		// obviously this hasn't already been done because I have been invoked
		ctx.disableCurrentConstraint(exception);
		
		if (status == null) {
			status = new DisabledConstraintStatus(
					this,
					ctx.getTarget(),
					exception);
			
			// log it as a warning, even though in the UI it will be an info
			Trace.trace(
					EMFModelValidationDebugOptions.CONSTRAINTS_DISABLED,
					"Constraint is disabled: " + getDescriptor().getId() + ". Check log for details.");  //$NON-NLS-1$//$NON-NLS-2$
			Log.warning(
					status.getCode(),
					status.getMessage(),
					status.getException());
		}
		
		return status;
	}
	
	/* (non-Javadoc)
	 * Implements the interface method.
	 */
	public final IConstraintDescriptor getDescriptor() {
		return descriptor;
	}
}
