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
 *    Zeligsoft - Bugs 218765, 249690
 ****************************************************************************/

package org.eclipse.emf.validation.service;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.marker.MarkerUtil;
import org.eclipse.emf.validation.model.EvaluationMode;

/**
 * <p>
 * An {@link IValidator} that supports batch-mode validation of
 * {@link org.eclipse.emf.ecore.EObject}s.
 * </p>
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 *
 * @see org.eclipse.emf.validation.service.ModelValidationService#newValidator(EvaluationMode)
 * @see org.eclipse.emf.validation.model.EvaluationMode#BATCH
 *
 * @author Christian W. Damus (cdamus)
 *
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IBatchValidator extends IValidator<EObject> {
	/**
	 * A boolean-valued option indicating whether to include live constraints in a
	 * batch validation. The default value is <code>false</code>.
	 *
	 * @since 1.3
	 *
	 * @see #setOptions(Map)
	 */
	Option<Boolean> OPTION_INCLUDE_LIVE_CONSTRAINTS = Option.make(false);

	/**
	 * An option specifying an implementation of the {@link ITraversalStrategy}
	 * interface to be used to walk the model being validated. The default value of
	 * this option is a {@linkplain #getDefaultTraversalStrategy() traversal
	 * strategy} that delegates to strategy objects registered on the
	 * <tt>org.eclipse.emf.validation.traversal</tt> extension point.
	 *
	 * @since 1.3
	 *
	 * @see #setOptions(Map)
	 */
	Option<ITraversalStrategy> OPTION_TRAVERSAL_STRATEGY = new Option<>(null) {
		@Override
		public ITraversalStrategy defaultValue(IValidator<?> validator) {
			return ((IBatchValidator) validator).getDefaultTraversalStrategy();
		}
	};

	/**
	 * A boolean-valued option indicating whether to include include, in the
	 * validation result, marker statuses for the resources covered by the
	 * validation operation. This is required when using the {@link MarkerUtil} to
	 * update the problem markers attached to resources. This is optional because of
	 * the increased cost (in time efficiency) of determining the resources. The
	 * default value of this option is <code>false</code>.
	 *
	 * @since 1.3
	 *
	 * @see #setOptions(Map)
	 */
	Option<Boolean> OPTION_TRACK_RESOURCES = Option.make(false);

	/**
	 * <p>
	 * Queries whether I also evaluate applicable live constraints on each object
	 * that I validate. This supports those clients that do not implement a
	 * transaction model for which live validation would be appropriate, but which
	 * still need to ensure that vital data integrity and other live constraints are
	 * satisfied by their models.
	 * </p>
	 * <p>
	 * By default, I only apply batch constraints (not live constraints).
	 * </p>
	 * <p>
	 * Since the 1.3 release, this method is equivalent to checking whether the
	 * {@link #OPTION_INCLUDE_LIVE_CONSTRAINTS} validation option is applied.
	 * </p>
	 *
	 * @return <code>true</code> if I apply both batch and live constraints;
	 *         <code>false</code> if I apply only batch constraints (the default)
	 *
	 * @see IValidator#getOptions()
	 * @see #OPTION_INCLUDE_LIVE_CONSTRAINTS
	 */
	boolean isIncludeLiveConstraints();

	/**
	 * <p>
	 * Sets whether live constraints are to be included in validation.
	 * </p>
	 * <p>
	 * Since the 1.3 release, this method is equivalent to applying the
	 * {@link #OPTION_REPORT_SUCCESSES} validation option.
	 * </p>
	 *
	 * @param includeLiveConstraints whether to include live constraints
	 *
	 * @see #isIncludeLiveConstraints
	 * @see IValidator#setOptions(java.util.Map)
	 * @see IBatchValidator#OPTION_INCLUDE_LIVE_CONSTRAINTS
	 */
	void setIncludeLiveConstraints(boolean includeLiveConstraints);

	/**
	 * <p>
	 * Obtains the traversal strategy that I employ to walk the model starting from
	 * the elements selected by the client.
	 * </p>
	 * <p>
	 * Since the 1.3 release, this method is equivalent to obtaining the value of
	 * the {@link #OPTION_TRAVERSAL_STRATEGY} validation option.
	 * </p>
	 *
	 * @return my current traversal strategy
	 *
	 * @see IValidator#getOptions()
	 * @see #OPTION_TRAVERSAL_STRATEGY
	 */
	ITraversalStrategy getTraversalStrategy();

	/**
	 * <p>
	 * Sets my traversal strategy.
	 * </p>
	 * <p>
	 * Since the 1.3 release, this method is equivalent to applying the
	 * {@link #OPTION_TRAVERSAL_STRATEGY} validation option.
	 * </p>
	 *
	 * @param strategy the new traversal strategy. Must not be <code>null</code>
	 *
	 * @throws IllegalArgumentException on <code>null</code> strategies
	 *
	 * @see #getTraversalStrategy
	 * @see IValidator#setOptions(java.util.Map)
	 * @see #OPTION_TRAVERSAL_STRATEGY
	 */
	void setTraversalStrategy(ITraversalStrategy strategy);

	/**
	 * Obtains the default traversal strategy, which can be assigned to me to
	 * {@link #setTraversalStrategy restore} the default behavior of recursive
	 * validation. The default strategy makes use of all available customized
	 * {@link ITraversalStrategy} implementations contributed by plug-ins for
	 * iteration of the model sub-trees selected for recursive validation. This is
	 * different from the {@link ITraversalStrategy.Recursive} strategy, which
	 * simply iterates the entire contents of a model subtree.
	 *
	 * @return the default traversal strategy
	 *
	 * @see #setTraversalStrategy
	 */
	ITraversalStrategy getDefaultTraversalStrategy();

	/**
	 * Validates the specified {@link EObject EMF element}, using the specified
	 * progress <code>monitor</code> to monitor progress of validation (which is
	 * especially useful for recursive validation).
	 *
	 * @param eObject the EMF element to validate
	 * @param monitor the progress monitor to track validation progress, or
	 *                <code>null</code> if no progress monitoring is required
	 * @return the validation status
	 * @see IValidator#validate(Object)
	 */
	IStatus validate(EObject eObject, IProgressMonitor monitor);

	/**
	 * Validates the specified {@link EObject EMF elements}, using the specified
	 * progress monitor to monitor progress of validation.
	 *
	 * @param eObjects the EMF elements to validate
	 * @param monitor  the progress monitor to track validation progress, or
	 *                 <code>null</code> if no progress monitoring is required
	 * @return the validation status
	 * @see IValidator#validate(Object)
	 */
	IStatus validate(Collection<? extends EObject> eObjects, IProgressMonitor monitor);

	/**
	 * Validates a single {@link EObject EMF element} without using any progress
	 * monitor.
	 *
	 * @param object the object to validate
	 */
	@Override
	IStatus validate(EObject eobject);

	/**
	 * Validates multiple {@link EObject EMF elements} without using any progress
	 * monitor.
	 *
	 * @param objects must all be {@link org.eclipse.emf.ecore.EObject}s
	 * @throws ClassCastException if any of the <code>objects</code> is not an
	 *                            {@link org.eclipse.emf.ecore.EObject}
	 */
	@Override
	IStatus validate(Collection<? extends EObject> objects);
}
