/******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation;

import org.eclipse.core.runtime.IStatus;

/**
 * <p>
 * Abstract superclass of all constraint implementations provided via the
 * <tt>constraintProviders</tt> extension point in the plug-in manifest XML
 * whose language is "Java".
 * </p>
 * <p>
 * The same constraint implementation class may be supplied for multiple
 * constraints (distinguished by their IDs in the extension XML).  In such
 * cases, the validation system will only create a single instance of the
 * <code>AbstractModelConstraint</code>, shared by all of the constraint IDs.
 * Therefore, this instance should not cache or otherwise retain any state
 * related to a particular constraint or validation operation.  If it is
 * necessary to retain any state, then this information should be indexed by
 * the constraint ID provided by the
 * {@link IValidationContext#getCurrentConstraintId()} method of the validation
 * context.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public abstract class AbstractModelConstraint {

	/**
	 * Initializes me.
	 */
	public AbstractModelConstraint() {
		super();
	}
	
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
	 * 
	 * @see IValidationContext#createSuccessStatus()
	 * @see IValidationContext#createFailureStatus(Object[])
	 */
	public abstract IStatus validate(IValidationContext ctx);
}
