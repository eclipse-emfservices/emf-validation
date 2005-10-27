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


package org.eclipse.emf.validation.service;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;

import org.eclipse.emf.validation.model.EvaluationMode;

/**
 * <p>
 * A validator validates model elements on behalf of the validation service's
 * clients.  The validator supports a variety of controls determining how the
 * validation is done; for example, whether only validation problems are
 * reported or also which constraints are satisfied, whether batch validation
 * is recursive, etc.
 * </p>
 * <p>
 * The different specializations of this interface evaluate constraints for the
 * various {@link org.eclipse.emf.validation.model.EvaluationMode modes} of
 * validation, and are obtained from the {@link ModelValidationService}.
 * Typically, a client will obtain a new validator and retain it for repeated
 * use (probably for the lifetime of the client object).  In general, validators
 * are not thread-safe.
 * </p>
 * <p>
 * The result of the {@link IValidator#validate} method is
 * an {@link IStatus} containing the results (as individual status objects)
 * of every constraint that failed and, if {@link #setReportSuccesses enabled},
 * also those that passed.  The validator guarantees that the
 * same constraint is not evaluated twice on the same model object for the same
 * trigger.  
 * </p>
 * <p>
 * Validation clients are encouraged to put information into the validators that
 * they create, that will help validation
 * {@linkplain IValidationListener listeners} to know how to interpret
 * validation results.  This information can be provided by the
 * {@link #putClientData(String, Object)} method.  As an example, a client may
 * wish to publish the marker type ID under which a listener should create or
 * look for validation problem markers, especially for batch validation.
 * </p>
 * <p>
 * In the future, the validator may provide additional services, such as
 * statistics, diagnostics, and other meta-data about the validation operation.
 * </p>
 * <p>
 * <b>Note</b> that clients are not intended to implement this interface.
 * </p>
 * 
 * @see ModelValidationService#newValidator(EvaluationMode)
 * 
 * @author Christian W. Damus (cdamus)
 */
public interface IValidator {
	/**
	 * Indicates the evaluation mode that I support.  This indicates the kind
	 * of objects expected by the <code>validate()</code> methods to process.
	 * 
	 * @return my evaluation mode
	 */
	EvaluationMode getEvaluationMode();
	
	/**
	 * Queries whether successful constraint evaluations are reported, in
	 * addition to validation problems.
	 * 
	 * @return whether successful constraint evaluations are reported
	 * @see #setReportSuccesses
	 */
	boolean isReportSuccesses();
	
	/**
	 * Indicates whether successful constraint evaluations are to be reported,
	 * in addition to validation problems.  If <code>false</code>, then the
	 * status reported by the <code>validate()</code> methods will not
	 * contain sub-statuses representing the constraints that passed, but will
	 * only have sub-statuses for the constraints (if any) that failed.
	 * 
	 * @param reportSuccesses <code>true</code> to report successes;
	 *        <code>false</code> to ignore them
	 */
	void setReportSuccesses(boolean reportSuccesses);
	
	/**
	 * Makes the specified named particle of information available to listeners
	 * who will receiver validation events from this validator.  This is useful
	 * to communicate some contextual information about the validation client
	 * to listeners, to better interpret the validation events.
	 * 
	 * @param key identifies an entry in the data map; must not be
	 *      <code>null</code>
	 * @param data the associated data, or <code>null</code> to remove it
	 */
	void putClientData(String key, Object data);

	/**
	 * Allows a client to retrieve "client data" that it had previously
	 * {@link #putClientData(String, Object) put}.
	 * 
	 * @param key the key under which the data object was put
	 * @return the corresponding data, or <code>null</code> if none was
	 *     put under this <code>key</code>
	 * 
	 * @see #putClientData(String, Object)
	 */
	Object getClientData(String key);
	
	/**
	 * Validates an object.  The type of object that is expected various by
	 * implementation.
	 * 
	 * @param object the object to validate
	 * @return the status of validation.  The
	 *    {@link IStatus#getSeverity severity} of the result indicates whether
	 *    validation passed or (how badly it) failed.  Normally, the result is
	 *    a {@link IStatus#isMultiStatus multi-status} whose children are
	 *    the results of individual constraint evaluations
	 * @throws ClassCastException if the <code>object</code> is not of
	 *    the correct type for this validator
	 */
	IStatus validate(Object object);
	
	/**
	 * Validates multiple objects, all in the same
	 * {@link org.eclipse.emf.validation.IValidationContext validation context}.
	 * This method is preferable to repeated invocations of
	 * {@link #validate(Object)} because it avoids repetition of constraints
	 * (as well as results) and other performance optimizations.
	 *  
	 * @param objects the objects to be validated
	 * @return a collective status of the validation operation, which usually
	 *     is a {@link IStatus#isMultiStatus multi-status} of individual results
	 * @throws ClassCastException if any of the <code>objects</code> is
	 *    not of the correct type for this validator
	 * @see #validate(Object)
	 */
	IStatus validate(Collection objects);
}
