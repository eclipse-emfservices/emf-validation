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
 * As of the 1.3 release, some of the customizations of the validator behaviour
 * are also implemented as {@linkplain IValidator#setOptions(java.util.Map)
 * validation options}. This provides a unified approach to this extensible
 * mechanism, simplifying the configuration of a validator by enabling
 * separation of the configuration step from the construction of the validator.
 * </p>
 * <p>
 * In the future, the validator may provide additional services, such as
 * statistics, diagnostics, and other meta-data about the validation operation.
 * </p>
 * <p>
 * <b>Note</b> that clients are not intended to implement this interface.
 * </p>
 * 
 * @param <T> the kind of target element validated by the validator
 * 
 * @see ModelValidationService#newValidator(EvaluationMode)
 * 
 * @author Christian W. Damus (cdamus)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IValidator<T> {
	/**
	 * A boolean-valued option indicating whether to report the success
	 * evaluation of constraints on target elements. The default value is
	 * <code>false</code>.
	 * 
	 * @since 1.3
	 * 
	 * @see #setOptions(Map)
	 */
	Option<Boolean> OPTION_REPORT_SUCCESSES = Option.make(false);
	
	/**
	 * Indicates the evaluation mode that I support.  This indicates the kind
	 * of objects expected by the <code>validate()</code> methods to process.
	 * 
	 * @return my evaluation mode
	 */
	EvaluationMode<T> getEvaluationMode();
	
	/**
	 * <p>
	 * Queries whether successful constraint evaluations are reported, in
	 * addition to validation problems.
	 * </p>
	 * <p>
	 * Since the 1.3 release, this method is equivalent to checking whether
	 * the {@link #OPTION_REPORT_SUCCESSES} validation option is applied.
	 * </p>
	 * 
	 * @return whether successful constraint evaluations are reported
	 * 
	 * @see #setReportSuccesses
	 * @see #getOptions()
	 * @see #OPTION_REPORT_SUCCESSES
	 */
	boolean isReportSuccesses();
	
	/**
	 * <p>
	 * Indicates whether successful constraint evaluations are to be reported,
	 * in addition to validation problems.  If <code>false</code>, then the
	 * status reported by the <code>validate()</code> methods will not
	 * contain sub-statuses representing the constraints that passed, but will
	 * only have sub-statuses for the constraints (if any) that failed.
	 * </p>
	 * <p>
	 * Since the 1.3 release, this method is equivalent to applying the
	 * {@link #OPTION_REPORT_SUCCESSES} validation option.
	 * </p>
	 * 
	 * @param reportSuccesses <code>true</code> to report successes;
	 *        <code>false</code> to ignore them
	 * 
	 * @see #isReportSuccesses()
	 * @see #setOptions(Map)
	 * @see #OPTION_REPORT_SUCCESSES
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
	IStatus validate(T object);
	
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
	IStatus validate(Collection<? extends T> objects);
	
	/**
	 * Adds a constraint filter to this validator.  The validator
	 * will only evaluate constraints that are accepted by its constraint
	 * filters.  If a validator has no filters, then all constraints are
     * validated.
	 * 
	 * @since 1.1
	 * 
	 * @param filter the constraint filter to add
	 */
	void addConstraintFilter(IConstraintFilter filter);
    
    /**
     * Removes a constraint filter from this validator.
     * If a validator has no filters, then all constraints are validated.
     * 
     * @since 1.1
     * 
     * @param filter the constraint filter to remove
     * 
     * @see #addConstraintFilter(IConstraintFilter)
     */
    void removeConstraintFilter(IConstraintFilter filter);
	
	/**
     * Obtains a collection of {@link IConstraintFilter}s that define
     * which constraints should be excluded for validation.
     * 
     * @return my constraint filters.  This list is not modifiable
     * 
     * @see #addConstraintFilter(IConstraintFilter)
     * @see #removeConstraintFilter(IConstraintFilter)
     * 
     * @since 1.1
     */
	Collection<IConstraintFilter> getConstraintFilters();
	
	/**
	 * Obtains the options applied to me that customize my operation. The
	 * resulting map is not modifiable by clients.
	 * 
	 * @return an unmodifiable view of my options
	 * 
	 * @since 1.3
	 * 
	 * @see #setOptions(Map)
	 * @see #getOption(Object, Object)
	 */
	Map<Option<?>, ?> getOptions();

	/**
	 * Sets options to apply to me, that customize my operation.
	 * 
	 * @param options
	 *            my options, or <code>null</code> to set the defaults
	 * 
	 * @since 1.3
	 * 
	 * @see #getOptions()
	 * @see #setOption(Object, Object, Object)
	 */
	void setOptions(Map<Option<?>, ?> options);
	
	/**
	 * Convenience for querying an option.
	 * 
	 * @param <V>
	 *            the value type of the option
	 * @param option
	 *            the option key
	 * @return the option's current value
	 * 
	 * @since 1.3
	 * 
	 * @see #getOptions()
	 */
	<V> V getOption(Option<V> option);
	
	/**
	 * Convenience for setting an option.
	 * 
	 * @param <V>
	 *            the value type of the option
	 * @param option
	 *            the option key
	 * @param value
	 *            the new value to set
	 * 
	 * @since 1.3
	 * 
	 * @see #setOptions(Map)
	 */
	<V> void setOption(Option<? super V> option, V value);
	
	/**
	 * The definition of a validator option.
	 *
	 * @param <V> the option's value type
	 * 
	 * @author Christian W. Damus (cdamus)
	 * 
	 * @since 1.3
	 */
	class Option<V> {

		private final V default_;

		/**
		 * Constructs a new option with the specified default value.
		 * 
		 * @param <V>
		 *            the option's value type
		 * @param defaultValue
		 *            the option's default value
		 * 
		 * @return the new option
		 */
		static <V> Option<V> make(V defaultValue) {
			return new Option<V>(defaultValue);
		}

		/**
		 * Initializes me with a static default value.
		 * 
		 * @param defaultValue
		 *            my default value
		 */
		protected Option(V defaultValue) {
			default_ = defaultValue;
		}

		/**
		 * Queries my default value for the specified validator. This allows the
		 * actual default value to be computed, based on the validator.
		 * 
		 * @param validator
		 *            the validator for which to query the default option vale.
		 *            Must not be <code>null</code>
		 * 
		 * @return the default value for the given validator
		 */
		public V defaultValue(IValidator<?> validator) {
			return default_;
		}
	}
}
