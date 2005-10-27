/******************************************************************************
 * Copyright (c) 2003, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <p>
 * Interface providing contextual information to
 * {@link AbstractModelConstraint}s about the validation currently in progress.
 * Also provides some useful services to manipulate a constraint's environment.
 * </p>
 * <p>
 * The context knows at any point in time what constraint is currently being
 * evaluated on which model element.  Thus, the context will seem to know
 * what constraint implementation is calling it, but this is just a consequence
 * of the fact that constraints are invoked strictly one at a time by the
 * context, itself.
 * </p>
 * <p>
 * <b>Note</b> that the results of any method calls on the context object are
 * only valid during the invocation of the constraint's
 * {@link AbstractModelConstraint#validate validate()} method.  The results
 * should not be retained by a constraint implementation.
 * </p>
 * <p>
 * This interface should not be implemented outside of the validation framework.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public interface IValidationContext {
	/**
	 * Obtains the ID of the constraint currently being evaluated.  This is
	 * useful for a class that implements multiple different constraints to
	 * determine for which constraint it is being invoked.
	 * 
	 * @return the ID of the constraint currently being evaluated
	 * 
	 * @see AbstractModelConstraint
	 */
	String getCurrentConstraintId();
	
	/**
	 * Obtains the EMF object currently being validated.
	 * 
	 * @return the current validation target
	 */
	EObject getTarget();
	
	/**
	 * In the case of a live constraint evaluation, obtains the type of event
	 * which is currently being validated.
	 * 
	 * @return the live constraint's triggering event type, or
	 *    {@link EMFEventType#NULL} if the constraint is being evaluated in
	 *    batch mode
	 */
	EMFEventType getEventType();
	
	/**
	 * Gets all of the {@link org.eclipse.emf.common.notify.Notification}s that are currently being validated.
	 * This is useful for live constraints that need more contextual information
	 * about the changes being validated than is available in a single
	 * notification.
	 * 
	 * @return the raw {@link org.eclipse.emf.common.notify.Notification}s being validated, or an empty
	 *    list if this is a batch validation.  This list is not modifiable
	 * 
	 * @since 7.0
	 */
	List getAllEvents();
	
	/**
	 * In the case of a live constraint evaluation, obtains the particular
	 * feature on which the constraint is triggered.  Note that this only
	 * applies to constraints that are triggered by specific features; the
	 * result will be <code>null</code> if the constraint does not specify any
	 * feature triggers in the XML meta-data.
	 * 
	 * @return the feature constraint's triggering feature, or
	 *    <code>null</code> if the constraint is not triggered by a particular
	 *    feature
	 * 
	 * @see #getFeatureNewValue()
	 */
	EStructuralFeature getFeature();
	
	/**
	 * <p>
	 * In the case of live constraint evaluation that is triggered by a
	 * particular feature, obtains the new value of the feature that is being
	 * validated.  The exact contents of the result are as follows:
	 * <ul>
	 *   <li>Add or Add Many:  in the "Add" case, a single object added to the
	 *       feature.  In "Add Many" case, a collection of objects added</li>
	 *   <li>Remove or Remove Many:  in the "Remove" case, a single object
	 *       removed from the feature.  In "Remove Many" case, a collection of
	 *       objects removed</li>
	 *   <li>Set, Unset, or Move:  the final value of the feature at the end
	 *       of the transaction (i.e., its current value)</li>
	 *   <li>Create:  a single object or a collection of objects created in the
	 *       feature</li>
	 *   <li>Resolved:  a single object or a collection of objects resolved
	 *       in the feature</li>
	 *   <li>Resolving Adapter:  a single adapter or a collection of adapters
	 *       removed from the adapter list of the {@link #getTarget() target}</li>
	 *   <li>Others:  the final value of the feature at the end
	 *       of the transaction (i.e., its current value)</li>
	 * </ul>.
	 * </p>
	 * 
	 * @return the feature constraint's triggering new value (as a single
	 *    object or a {@link Collection}), or <code>null</code> if the
	 *    constraint is not triggered by a particular feature
	 * 
	 * @see #getFeature()
	 */
	Object getFeatureNewValue();
	
	/**
	 * <p>
	 * Causes the current constraint to be skipped in any subsequent validation
	 * of the specified <code>eObject</code> in the current validation
	 * operation.  Constraints can use this facility to short-circuit
	 * processing, especially in a recursive batch validation, of objects that
	 * they have already checked in processing the current
	 * {@link #getTarget() target}.
	 * </p>
	 * <p>
	 * For example, checking for dependency cycles in a UML model requires
	 * traversing the dependency relationships from an element as far as they
	 * reach throughout the model.  Every element thus traversed can
	 * subsequently be ignored by the constraint, as ground that needs not be
	 * trodden again, whether a cycle was found or not.
	 * </p>
	 * <p>
	 * <b>Note</b> that there is no need to invoke this method for the
	 * {@link #getTarget() current target} object; the validation system
	 * guarantees that it will not be revisited.
	 * </p>
	 * 
	 * @param eObject the model object to be skipped by the current constraint
	 */
	void skipCurrentConstraintFor(EObject eObject);
	
	/**
	 * Causes the current constraint to be skipped in any subsequent validation
	 * of any of the specified <code>eObjects</code> in the current validation
	 * operation.
	 * 
	 * @param eObjects the model objects to be skipped by the current constraint
	 * 
	 * @see #skipCurrentConstraintFor(EObject)
	 */
	void skipCurrentConstraintForAll(Collection eObjects);
	
	/**
	 * <p>
	 * Causes the current constraint to be disabled for the remainder of the
	 * Eclipse session, because it is no longer viable due to some run-time
	 * exception.
	 * </p>
	 * <p>
	 * <b>Note</b> how this differs from the
	 * {@link #skipCurrentConstraintFor(EObject)} method: the current constraint
	 * is skipped for all model elements and even in subsequent validation
	 * operations.
	 * </p>
	 * 
	 * @param exception the exception that has caused the current constraint to
	 *     be non-viable.  Must not be <code>null</code>
	 */
	void disableCurrentConstraint(Throwable exception);
	
	/**
	 * <p>
	 * Gets the current constraint's private working data.  This object can be
	 * anything that the caller defines.  The value is <code>null</code>
	 * until it is set by this same constraint using the
	 * {@link #putCurrentConstraintData} method.  The working data persists
	 * only for the duration of the validation operation.
	 * </p>
	 * <p>
	 * The constraint may use this to cache any data that may be of use to it
	 * in optimizing the processing of multiple objects, reporting information
	 * to the user, etc. 
	 * </p>
	 * 
	 * @return the current constraint's working data
	 * 
	 * @see #putCurrentConstraintData(Object)
	 */
	Object getCurrentConstraintData();
	
	/**
	 * Puts into this context some working data that is to be cached for the
	 * current constraint.  The data may be retrieved later by a call to the
	 * {@link #getCurrentConstraintData} method during the same validation 
	 * operation.
	 * 
	 * @param newData the new working data
	 * @return the previous constraint data object that the
	 *     <code>newData</code> is displacing
	 */
	Object putCurrentConstraintData(Object newData);
	
	/**
	 * Obtains the result locus of this evaluation of the current constraint.
	 * The returned set is not modifiable.
	 * 
	 * @return an unmodifiable view of the result locus
	 * 
	 * @see #addResult(EObject)
	 */
	Set getResultLocus();
	
	/**
	 * Adds a result to the result locus of the current constraint.  The result
	 * locus is the set of model elements that contribute to the violation of
	 * a constraint.  The {@link #getTarget() current target} is implicitly
	 * added to the result locus if the constraint evaluation fails.
	 * 
	 * @param eObject the model element to add to the result locus
	 * 
	 * @see #getResultLocus()
	 */
	void addResult(EObject eObject);
	
	/**
	 * Adds all of the specified model elements to the result locus of the
	 * current constraint.
	 * 
	 * @param eObjects the model elements to add to the result locus.  Must
	 *     contain elements of type {@link EObject}
	 * 
	 * @see #addResult(EObject)
	 */
	void addResults(Collection eObjects);
	
	/**
	 * Creates a status object indicating successful evaluation of the
	 * current constraint on the current target element.
	 * 
	 * @return the "success" status
	 */
	IStatus createSuccessStatus();
	
	/**
	 * Creates a status object indicating unsuccessful evaluation of the
	 * current constraint on the current target element.  The status will have
	 * the severity, error code, and message defined in the constraint meta-data
	 * in the XML.
	 * 
	 * @param messageArguments the positional {0}, {1}, etc. arguments to
	 *      replace in the message pattern (may by <code>null</code> if none
	 *      are needed)
	 * @return the status indicating a constraint violation
	 */
	IStatus createFailureStatus(Object[] messageArguments);
}
