/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.emf.validation;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.model.ConstraintStatus;

/**
 * <p>
 * Abstract superclass of all constraint implementations provided via the
 * <tt>constraintProviders</tt> extension point in the plug-in manifest XML
 * whose language is "Java".
 * </p>
 * <p>
 * The same constraint implementation class may be supplied for multiple
 * constraints (distinguished by their IDs in the extension XML). In such cases,
 * the validation system will only create a single instance of the
 * <code>AbstractModelConstraint</code>, shared by all of the constraint IDs.
 * Therefore, this instance should not cache or otherwise retain any state
 * related to a particular constraint or validation operation. If it is
 * necessary to retain any state, then this information should be indexed by the
 * constraint ID provided by the
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
	 * Validates an object in the specified context. The
	 * {@link IValidationContext#getTarget target} of the validation operation is
	 * available from the context object.
	 * </p>
	 * <p>
	 * <b>Note</b> that it is best to use the
	 * {@link IValidationContext#createSuccessStatus()} and
	 * {@link IValidationContext#createFailureStatus(Object...)} methods of the
	 * context object to create the status object returned from this method, to
	 * ensure that the status object returned is correctly handled by the validation
	 * system.
	 * </p>
	 * <p>
	 * A single constraint implementation may check multiple conditions. In such
	 * cases, it can return a
	 * {@link ConstraintStatus#createMultiStatus(IValidationContext, Collection)
	 * multi-status} of multiple results created by the overloaded variants of the
	 * {@link ConstraintStatus#createStatus(IValidationContext, java.util.Collection, String, Object[])}
	 * method. In these cases, also, each resulting status can store a distinct
	 * result locus. For example:
	 * </p>
	 * 
	 * <pre>
	 *     public IStatus validate(IValidationContext ctx) {
	 *         List problems = new java.util.ArrayList();
	 *         
	 *         // check the first condition.  This method adds results to the
	 *         //    ctx's result locus if it finds a problem
	 *         IStatus problem = checkFirstCondition(ctx);
	 *         if (problem != null) problems.add(problem);
	 *         
	 *         // check another condition, involving different objects
	 *         problem = checkSecondCondition(ctx);
	 *         if (problem != null) problems.add(problem);
	 *         
	 *         return problems.isEmpty()? ctx.createSuccessStatus() :
	 *             ConstraintStatus.createMultiStatus(ctx, problems);
	 *     }
	 *     
	 *     private IStatus checkFirstCondition(IValidationContext ctx) {
	 *         EObject target = ctx.getTarget();
	 *         
	 *         Collection problemElements = ...; // collect problem elements
	 *         boolean ok = ... ;  // check the target and some related objects
	 *         
	 *         return ok? null : ConstraintStatus.createStatus(
	 *                 ctx,
	 *                 problemElements,
	 *                 "Problem with {0}",
	 *                 new Object[] {problemElements});
	 *     }
	 *     
	 *     private IStatus checkSecondCondition(IValidationContext ctx) ...
	 * </pre>
	 * 
	 * @param ctx the validation context that provides access to the current
	 *            constraint evaluation environment. The framework will never pass a
	 *            <code>null</code> value
	 * @return the status of validation of the target object. The
	 *         {@link IStatus#getSeverity()} of the record is either
	 *         {@link IStatus#OK} to indicate success, or some other value to
	 *         indicate that validation failed. Must not return <code>null</code>
	 * 
	 * @see IValidationContext#createSuccessStatus()
	 * @see IValidationContext#createFailureStatus(Object...)
	 * @see ConstraintStatus#createStatus(IValidationContext, java.util.Collection,
	 *      String, Object[])
	 * @see ConstraintStatus#createMultiStatus(IValidationContext, Collection)
	 */
	public abstract IStatus validate(IValidationContext ctx);
}
