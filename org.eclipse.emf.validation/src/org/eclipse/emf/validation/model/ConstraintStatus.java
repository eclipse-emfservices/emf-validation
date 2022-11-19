/******************************************************************************
 * Copyright (c) 2003, 2008 IBM Corporation, Zeligsoft Inc., and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *    Zeligsoft - Bug 249690 
 ****************************************************************************/

package org.eclipse.emf.validation.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.internal.util.TextUtils;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;

/**
 * <p>
 * Indicates the result of evaluating a constraint on a model object. Any
 * constraint which is met by the object results in an {@link IStatus#OK}
 * status. Failure of a constraint results in a status severity matching the
 * {@link org.eclipse.emf.validation.service.IConstraintDescriptor#getSeverity
 * severity} defined for the constraint.
 * </p>
 * <p>
 * As a special case of constraint failure, if the validation framework receives
 * an uncaught exception from the constraint, then the constraint is disabled
 * and the resulting <code>ConstraintStatus</code> is an {@link IStatus#INFO}
 * indicating this fact and containing the exception that caused the failure.
 * Once disabled, the constraint will not be evaluated again.
 * </p>
 * <p>
 * This class should not be extended outside of the validation framework. It may
 * be instantiated by clients, especially using the <code>createStatus()</code>
 * factory methods.
 * </p>
 * 
 * @see IModelConstraint#validate
 * 
 * @author Christian W. Damus (cdamus)
 * 
 * @see #createStatus(IValidationContext, Collection, String, Object[])
 * @see #createMultiStatus(IValidationContext, Collection)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 */
public class ConstraintStatus extends Status implements IConstraintStatus {
	private final IModelConstraint constraint;
	private EObject target;

	private Set<EObject> resultLocus;

	/**
	 * Initializes me as a failure of the specified <code>constraint</code> with a
	 * <code>message</code> to be displayed somehow to the user.
	 * 
	 * @param constraint  the constraint that failed
	 * @param target      the target of the failed validation
	 * @param message     the message describing the failure
	 * @param resultLocus the objects which caused the constraint to fail (at least
	 *                    the original target should be among these). May be
	 *                    <code>null</code> if there really is no result locus
	 */
	public ConstraintStatus(IModelConstraint constraint, EObject target, String message,
			Set<? extends EObject> resultLocus) {
		this(constraint, target, constraint.getDescriptor().getSeverity().toIStatusSeverity(),
				constraint.getDescriptor().getStatusCode(), message, resultLocus);
	}

	/**
	 * Initializes me as a successful execution of the specified
	 * <code>constraint</code>.
	 * 
	 * @param constraint the constraint that succeeded
	 * @param target     the target of the successful validation
	 */
	public ConstraintStatus(IModelConstraint constraint, EObject target) {
		this(constraint, target, IStatus.OK, IModelConstraint.STATUS_CODE_SUCCESS,
				EMFModelValidationStatusCodes.CONSTRAINT_SUCCESS_MSG, null);
	}

	/**
	 * <p>
	 * Constructor that explicitly initializes all of my parts.
	 * </p>
	 * <p>
	 * This constructor should not be used outside of the validation framework.
	 * </p>
	 * 
	 * @param constraint  the constraint that was evaluated
	 * @param target      the object on which validation was performed
	 * @param severity    the severity of the constraint evaluation result
	 * @param code        the error code (if the constraint failed)
	 * @param message     the error message (if the constraint failed)
	 * @param resultLocus the result locus (if the constraint failed)
	 */
	public ConstraintStatus(IModelConstraint constraint, EObject target, int severity, int code, String message,
			Set<? extends EObject> resultLocus) {
		super(severity, constraint.getDescriptor().getPluginId(), code, message, null);

		assert constraint != null;
		assert target != null;
		assert message != null;

		this.constraint = constraint;
		this.target = target;

		// unmodifiable defensive copy
		this.resultLocus = (resultLocus != null)
				? Collections.unmodifiableSet(new java.util.HashSet<EObject>(resultLocus))
				: Collections.<EObject>emptySet();
	}

	/**
	 * Creates a status object indicating unsuccessful evaluation of the current
	 * constraint on the provided target element. The status will have the severity
	 * and error code defined in the constraint meta-data (its descriptor), and a
	 * message composed from the specified pattern and arguments.
	 * <p>
	 * This is useful in the case that a single constraint object reports multiple
	 * distinct problems, with different messages, at the same severity.
	 * </p>
	 * 
	 * @param ctx              the calling constraint's validation context. Must not
	 *                         be <code>null</code>
	 * @param target           the target for the status. If the target is
	 *                         <code>null</code>, the target from the validation
	 *                         context is used
	 * @param resultLocus      the objects involved in the problem that this status
	 *                         is to report. If this collection does not contain the
	 *                         target object of the validation, then it is
	 *                         considered to contain it implicitly. Thus, this
	 *                         parameter may be <code>null</code> if only the target
	 *                         object is in the result locus
	 * @param messagePattern   the message pattern (with optional {0} etc.
	 *                         parameters)
	 * @param messageArguments the positional {0}, {1}, etc. arguments to replace in
	 *                         the message pattern (may by <code>null</code> if none
	 *                         are needed)
	 * @return the status indicating a constraint violation
	 * 
	 * @since 1.1
	 * 
	 * @see #createStatus(IValidationContext, Collection, int, int, String,
	 *      Object[])
	 */
	public static ConstraintStatus createStatus(IValidationContext ctx, EObject target,
			Collection<? extends EObject> resultLocus, String messagePattern, Object... messageArguments) {

		IConstraintDescriptor desc = ConstraintRegistry.getInstance().getDescriptor(ctx.getCurrentConstraintId());

		return createStatus(ctx, target, resultLocus, desc.getSeverity().toIStatusSeverity(), desc.getStatusCode(),
				messagePattern, messageArguments);
	}

	/**
	 * Creates a status object indicating unsuccessful evaluation of the current
	 * constraint on the provided target element.
	 * <p>
	 * This is useful in the case that a single constraint object reports multiple
	 * distinct problems, with different messages and severities.
	 * </p>
	 * 
	 * @param ctx              the calling constraint's validation context. Must not
	 *                         be <code>null</code>
	 * @param target           the target for the status. If the target is
	 *                         <code>null</code>, the target from the validation
	 *                         context is used
	 * @param resultLocus      the objects involved in the problem that this status
	 *                         is to report. If this collection does not contain the
	 *                         target object of the validation, then it is
	 *                         considered to contain it implicitly. Thus, this
	 *                         parameter may be <code>null</code> if only the target
	 *                         object is in the result locus
	 * @param severity         the severity of the problem (one of the constants
	 *                         defined in the {@link IStatus} interface; should not
	 *                         be <code>OK</code>)
	 * @param errorCode        the error code. A constraint may wish to use
	 *                         different error codes for different conditions, or
	 *                         just supply the
	 *                         {@link IConstraintDescriptor#getStatusCode() status
	 *                         code} provided by its is constraint descriptor
	 * @param messagePattern   the message pattern (with optional {0} etc.
	 *                         parameters)
	 * @param messageArguments the positional {0}, {1}, etc. arguments to replace in
	 *                         the message pattern (may by <code>null</code> if none
	 *                         are needed)
	 * @return the status indicating a constraint violation
	 * 
	 * @since 1.1
	 * 
	 * @see #createStatus(IValidationContext, Collection, String, Object[])
	 */
	public static ConstraintStatus createStatus(IValidationContext ctx, EObject target,
			Collection<? extends EObject> resultLocus, int severity, int errorCode, String messagePattern,
			Object... messageArguments) {

		// need a prototype status to get certain critical information, such
		// as the constraint object and target
		ConstraintStatus result = (ConstraintStatus) ctx.createFailureStatus();

		if (target != null) {
			result.target = target;
		}

		Set<EObject> results;
		if (resultLocus == null) {
			results = Collections.singleton(result.getTarget());
		} else {
			results = new java.util.HashSet<EObject>(resultLocus);
			if (!results.contains(result.getTarget())) {
				results.add(result.getTarget());
			}
		}

		String message = TextUtils.formatMessage(messagePattern,
				(messageArguments == null) ? new Object[0] : messageArguments);

		result.setMessage(message);
		result.setSeverity(severity);
		result.setCode(errorCode);
		result.resultLocus = results;

		return result;
	}

	/**
	 * Creates a status object indicating unsuccessful evaluation of the current
	 * constraint on the current target element, as indicated by the supplied
	 * validation context. The status will have the severity and error code defined
	 * in the constraint meta-data (its descriptor), and a message composed from the
	 * specified pattern and arguments.
	 * <p>
	 * This is useful in the case that a single constraint object reports multiple
	 * distinct problems, with different messages, at the same severity.
	 * </p>
	 * 
	 * @param ctx              the calling constraint's validation context. Must not
	 *                         be <code>null</code>
	 * @param resultLocus      the objects involved in the problem that this status
	 *                         is to report. If this collection does not contain the
	 *                         target object of the validation, then it is
	 *                         considered to contain it implicitly. Thus, this
	 *                         parameter may be <code>null</code> if only the target
	 *                         object is in the result locus
	 * @param messagePattern   the message pattern (with optional {0} etc.
	 *                         parameters)
	 * @param messageArguments the positional {0}, {1}, etc. arguments to replace in
	 *                         the message pattern (may by <code>null</code> if none
	 *                         are needed)
	 * @return the status indicating a constraint violation
	 * 
	 * @since 1.1
	 * 
	 * @see #createStatus(IValidationContext, Collection, int, int, String,
	 *      Object[])
	 */
	public static ConstraintStatus createStatus(IValidationContext ctx, Collection<? extends EObject> resultLocus,
			String messagePattern, Object... messageArguments) {

		IConstraintDescriptor desc = ConstraintRegistry.getInstance().getDescriptor(ctx.getCurrentConstraintId());

		return createStatus(ctx, null, resultLocus, desc.getSeverity().toIStatusSeverity(), desc.getStatusCode(),
				messagePattern, messageArguments);
	}

	/**
	 * Creates a status object indicating unsuccessful evaluation of the current
	 * constraint on the current target element, as indicated by the supplied
	 * validation context.
	 * <p>
	 * This is useful in the case that a single constraint object reports multiple
	 * distinct problems, with different messages and severities.
	 * </p>
	 * 
	 * @param ctx              the calling constraint's validation context. Must not
	 *                         be <code>null</code>
	 * @param resultLocus      the objects involved in the problem that this status
	 *                         is to report. If this collection does not contain the
	 *                         target object of the validation, then it is
	 *                         considered to contain it implicitly. Thus, this
	 *                         parameter may be <code>null</code> if only the target
	 *                         object is in the result locus
	 * @param severity         the severity of the problem (one of the constants
	 *                         defined in the {@link IStatus} interface; should not
	 *                         be <code>OK</code>)
	 * @param errorCode        the error code. A constraint may wish to use
	 *                         different error codes for different conditions, or
	 *                         just supply the
	 *                         {@link IConstraintDescriptor#getStatusCode() status
	 *                         code} provided by its is constraint descriptor
	 * @param messagePattern   the message pattern (with optional {0} etc.
	 *                         parameters)
	 * @param messageArguments the positional {0}, {1}, etc. arguments to replace in
	 *                         the message pattern (may by <code>null</code> if none
	 *                         are needed)
	 * @return the status indicating a constraint violation
	 * 
	 * @since 1.1
	 * 
	 * @see #createStatus(IValidationContext, Collection, String, Object[])
	 */
	public static ConstraintStatus createStatus(IValidationContext ctx, Collection<? extends EObject> resultLocus,
			int severity, int errorCode, String messagePattern, Object... messageArguments) {
		return createStatus(ctx, null, resultLocus, severity, errorCode, messagePattern, messageArguments);
	}

	/**
	 * Creates a status object indicating successful evaluation of the current
	 * constraint on the provided target element. The status will have the severity
	 * and error code defined in by
	 * {@link ConstraintStatus#ConstraintStatus(IModelConstraint, EObject)}
	 * 
	 * <p>
	 * This method will only return a <code>ConstraintStatus</code> when the
	 * validation context's {@link IValidationContext#createSuccessStatus()} method
	 * returns an {@link IConstraintStatus}, otherwise it simply returns the success
	 * created by the validation context
	 * </p>
	 * 
	 * @param ctx         the calling constraint's validation context. Must not be
	 *                    <code>null</code>
	 * @param target      the target for the status. Must not be <code>null</code>
	 * @param resultLocus the objects involved in the success that this status is to
	 *                    report. If this collection does not contain the target
	 *                    object of the validation, then it is considered to contain
	 *                    it implicitly. Thus, this parameter may be
	 *                    <code>null</code> if only the target object is in the
	 *                    result locus
	 * @return the status indicating a success
	 * 
	 * @since 1.1
	 * 
	 * @see #createStatus(IValidationContext, Collection, int, int, String,
	 *      Object[])
	 */
	public static IStatus createSuccessStatus(IValidationContext ctx, EObject target,
			Collection<? extends EObject> resultLocus) {
		IStatus status = ctx.createSuccessStatus();

		if (status instanceof IConstraintStatus) {
			IConstraintStatus successStatus = (IConstraintStatus) status;
			ConstraintStatus constraintStatus = new ConstraintStatus(successStatus.getConstraint(), target);

			Set<EObject> results;
			if (resultLocus == null) {
				results = Collections.singleton(target);
			} else {
				results = new java.util.HashSet<EObject>(resultLocus);
				if (!results.contains(target)) {
					results.add(target);
				}
			}

			constraintStatus.resultLocus = results;
			return constraintStatus;
		}
		return status;
	}

	/**
	 * Creates a multi-status from the specified problem <tt>statuses</tt>. The
	 * resulting status has a generic message and the error code defined by the
	 * constraint's metadata. The severity is, as with all multi-statuses, the
	 * maximum of the severities of the supplied <tt>statuses</tt>.
	 * <p>
	 * This is useful in the case that a single constraint object reports multiple
	 * distinct problems. A constraint may choose to compose and return a
	 * {@link MultiStatus} if it wants different values for some of the status
	 * properties.
	 * </p>
	 * 
	 * @param ctx      the calling constraint's current validation context
	 * @param statuses the statuses to combine into a multi-status. Must not be
	 *                 <code>null</code> or empty
	 * 
	 * @return a multi-status aggregating the supplied statuses
	 * 
	 * @throws IllegalArgumentException if the <tt>statuses</tt> is
	 *                                  <code>null</code> or empty
	 * 
	 * @since 1.1
	 * 
	 * @see #createMultiStatus(IValidationContext, String, Object[], Collection)
	 */
	public static IStatus createMultiStatus(IValidationContext ctx, Collection<? extends IStatus> statuses) {
		return createMultiStatus(ctx, ValidationMessages.eval_some_fail_WARN_, null, statuses);
	}

	/**
	 * Creates a multi-status from the specified problem <tt>statuses</tt>. The
	 * resulting status has a message composed from the specified pattern and
	 * arguments, and the error code defined by the constraint's metadata. The
	 * severity is, as with all multi-statuses, the maximum of the severities of the
	 * supplied <tt>statuses</tt>.
	 * <p>
	 * This is useful in the case that a single constraint object reports multiple
	 * distinct problems. A constraint may choose to compose and return a
	 * {@link MultiStatus} if it wants different values for some of the status
	 * properties.
	 * </p>
	 * 
	 * @param ctx              the calling constraint's current validation context
	 * @param messagePattern   the message pattern (with optional {0} etc.
	 *                         parameters)
	 * @param messageArguments the positional {0}, {1}, etc. arguments to replace in
	 *                         the message pattern (may by <code>null</code> if none
	 *                         are needed)
	 * @param statuses         the statuses to combine into a multi-status. Must not
	 *                         be <code>null</code> or empty
	 * 
	 * @return a multi-status aggregating the supplied statuses
	 * 
	 * @throws IllegalArgumentException if the <tt>statuses</tt> is
	 *                                  <code>null</code> or empty
	 * 
	 * @since 1.1
	 * 
	 * @see #createMultiStatus(IValidationContext, Collection)
	 */
	public static IStatus createMultiStatus(IValidationContext ctx, String messagePattern, Object[] messageArguments,
			Collection<? extends IStatus> statuses) {
		if (statuses == null || statuses.isEmpty()) {
			throw new IllegalArgumentException("no statuses to aggregate"); //$NON-NLS-1$
		}

		IStatus[] children = statuses.toArray(new IStatus[statuses.size()]);

		IConstraintDescriptor desc = ConstraintRegistry.getInstance().getDescriptor(ctx.getCurrentConstraintId());

		String message = TextUtils.formatMessage(messagePattern,
				(messageArguments == null) ? new Object[0] : messageArguments);

		return new Multi(desc.getPluginId(), desc.getStatusCode(), children, message);
	}

	/**
	 * Obtains the constraint which either succeeded or failed, according to what I
	 * have to say.
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
	 * These are objects which caused the constraint to fail, and would be useful to
	 * link to from some display of the error message.
	 * 
	 * @return the objects which caused the constraint to fail. In cases of
	 *         successful validation, the result is an empty collection. The result
	 *         is never <code>null</code>
	 */
	public final Set<EObject> getResultLocus() {
		return resultLocus;
	}

	private static final class Multi extends MultiStatus implements IConstraintStatus {
		private final IModelConstraint constraint;
		private final EObject target;
		private final Set<EObject> resultLocus;

		Multi(String pluginId, int code, IStatus[] children, String message) {
			super(pluginId, code, children, message, null);

			IConstraintStatus prototype = null;

			for (IStatus element : children) {
				if (element instanceof IConstraintStatus) {
					prototype = (IConstraintStatus) element;
					break;
				}
			}

			if (prototype == null) {
				constraint = null;
				target = null;
				resultLocus = Collections.emptySet();
			} else {
				constraint = prototype.getConstraint();
				target = prototype.getTarget();
				resultLocus = Collections.singleton(target);
			}
		}

		public IModelConstraint getConstraint() {
			return constraint;
		}

		public EObject getTarget() {
			return target;
		}

		public Set<EObject> getResultLocus() {
			return resultLocus;
		}
	}
}
