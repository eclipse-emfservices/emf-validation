/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
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
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.emf.validation.model.IModelConstraint;

/**
 * A specialization of {@link ConstraintStatus} that indicates the failure of
 * a constraint to validate the target object, because an uncaught exception
 * occurred.  This status makes that exception available to the client.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class DisabledConstraintStatus extends ConstraintStatus {
	private final Throwable exception;
	
	/**
	 * Initializes me as a constraint failure, with the exception that caused
	 * the constraint to be disabled.
	 * 
	 * @param constraint the constraint that is disabled
	 * @param target the target of the validation
	 * @param exception the uncaught exception that the constraint threw
	 */
	public DisabledConstraintStatus(
			IModelConstraint constraint,
			EObject target,
			Throwable exception) {
		super(constraint,
			  target,
			  IStatus.INFO,
			  EMFModelValidationStatusCodes.CONSTRAINT_DISABLED,
			  EMFModelValidationPlugin.getMessage(
			  		EMFModelValidationStatusCodes.CONSTRAINT_DISABLED_MSG,
			  		new Object[] {constraint.getDescriptor().getName()}),
			  null);
		
		this.exception = exception;
	}
	
	// implements/extends the inherited method
	@Override
    public Throwable getException() {
		return exception;
	}
}
