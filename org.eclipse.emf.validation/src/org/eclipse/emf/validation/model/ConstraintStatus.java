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

import java.util.Collections;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;

/**
 * <p>
 * Indicates the result of evaluating a constraint on a model object.  Any
 * constraint which is met by the object results in an {@link IStatus#OK}
 * status.  Failure of a constraint results in a status severity matching the
 * {@link org.eclipse.emf.validation.service.IConstraintDescriptor#getSeverity severity}
 * defined for the constraint.
 * </p>
 * <p>
 * As a special case of constraint failure, if the validation framework
 * receives an uncaught exception from the constraint, then the constraint is
 * disabled and the resulting <code>ConstraintStatus</code> is an
 * {@link IStatus#INFO} indicating this fact and containing the exception that
 * caused the failure.  Once disabled, the constraint will not be evaluated
 * again.
 * </p>
 * <p>
 * This class should not be extended outside of the validation framework.
 * </p>
 * 
 * @see IModelConstraint#validate
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintStatus extends Status implements IConstraintStatus {
	private final IModelConstraint constraint;
	private final EObject target;
	
	private final Set resultLocus;

	/**
	 * Initializes me as a failure of the specified <code>constraint</code>
	 * with a <code>message</code> to be displayed somehow to the user.
	 * 
	 * @param constraint the constraint that failed
	 * @param target the target of the failed validation
	 * @param message the message describing the failure
	 * @param resultLocus the objects which caused the constraint to fail (at
	 *     least the original target should be among these).  May be
	 *     <code>null</code> if there really is no result locus
	 */
	public ConstraintStatus(IModelConstraint constraint,
							EObject target,
							String message,
							Set resultLocus) {
		this(
			constraint,
			target,
			constraint.getDescriptor().getSeverity().toIStatusSeverity(),
			constraint.getDescriptor().getStatusCode(),
			message,
			resultLocus);
	}

	/**
	 * Initializes me as a successful execution of the specified
	 * <code>constraint</code>.
	 * 
	 * @param constraint the constraint that succeeded
	 * @param target the target of the successful validation
	 */
	public ConstraintStatus(IModelConstraint constraint, EObject target) {
		this(
			constraint,
			target,
			IStatus.OK,
			IModelConstraint.STATUS_CODE_SUCCESS,
			EMFModelValidationStatusCodes.CONSTRAINT_SUCCESS_MSG,
			null);
	}
	
	/**
	 * <p>
	 * Constructor that explicitly initializes all of my parts.
	 * </p>
	 * <p>
	 * This constructor should not be used outside of the validation framework.
	 * </p>
	 * 
	 * @param constraint the constraint that was evaluated
	 * @param target the object on which validation was performed
	 * @param status the status of the constraint evaluation
	 * @param code the error code (if the constraint failed)
	 * @param message the error message (if the constraint failed)
	 * @param resultLocus the result locus (if the constraint failed)
	 */
	public ConstraintStatus(IModelConstraint constraint,
							EObject target,
							int status,
							int code,
							String message,
							Set resultLocus) {
		super(status, constraint.getDescriptor().getPluginId(), code, message, null);
		
		assert constraint != null;
		assert target != null;
		assert message != null;
		
		this.constraint = constraint;
		this.target = target;
		
		// unmodifiable defensive copy
		this.resultLocus = (resultLocus != null)
			? Collections.unmodifiableSet(new java.util.HashSet(resultLocus))
			: Collections.EMPTY_SET;
	}

	/**
	 * Obtains the constraint which either succeeded or failed, according to
	 * what I have to say.
	 * 
	 * @return my constraint
	 */
	public final IModelConstraint getConstraint() {
		return constraint;
	}
	
	/**
	 * Obtains the target object, on which the constraint was evaluated.
	 * 
	 * @return the target of the validation operation
	 */
	public final EObject getTarget() {
		return target;
	}
	
	/**
	 * Obtains the objects which are involved in the failure of the constraint.
	 * These are objects which caused the constraint to fail, and would be
	 * useful to link to from some display of the error message.
	 * 
	 * @return the objects which caused the constraint to fail.  In cases of
	 *     successful validation, the result is an empty collection.  The result
	 *     is never <code>null</code>
	 */
	public final Set getResultLocus() {
		return resultLocus;
	}
}
