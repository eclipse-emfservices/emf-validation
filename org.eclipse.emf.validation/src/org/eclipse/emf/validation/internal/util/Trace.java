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

package org.eclipse.emf.validation.internal.util;

import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;

/**
 * Delegates tracing duties to the {@link EMFModelValidationPlugin.Tracing}
 * class, with the EMF Model Validation plug-in implicit in all traces and the
 * option ID also implicit in many cases.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class Trace {
	/**
	 * Queries whether method entry tracing is enabled.
	 * 
	 * @return whether method entry tracing is enabled
	 */
	public static boolean shouldTraceEntering() {
		return shouldTrace(EMFModelValidationDebugOptions.METHODS_ENTERING);
	}

	/**
	 * Queries whether method entry tracing is enabled for the specified debug
	 * option.
	 * 
	 * @param option the debug option to test
	 * @return whether method entry tracing is enabled for the option
	 */
	public static boolean shouldTraceEntering(String option) {
		return shouldTraceEntering() && shouldTrace(option);
	}

	/**
	 * Queries whether method exit tracing is enabled.
	 * 
	 * @return whether method exit tracing is enabled
	 */
	public static boolean shouldTraceExiting() {
		return shouldTrace(EMFModelValidationDebugOptions.METHODS_EXITING);
	}

	/**
	 * Queries whether method exit tracing is enabled for the specified debug
	 * option.
	 * 
	 * @param option the debug option to test
	 * @return whether method exit tracing is enabled for the option
	 */
	public static boolean shouldTraceExiting(String option) {
		return shouldTraceExiting() && shouldTrace(option);
	}

	/**
	 * Queries whether exception catch tracing is enabled.
	 * 
	 * @return whether exception catch tracing is enabled
	 */
	public static boolean shouldTraceCatching() {
		return shouldTrace(EMFModelValidationDebugOptions.EXCEPTIONS_CATCHING);
	}

	/**
	 * Queries whether exception throw tracing is enabled.
	 * 
	 * @return whether exception throw tracing is enabled
	 */
	public static boolean shouldTraceThrowing() {
		return shouldTrace(EMFModelValidationDebugOptions.EXCEPTIONS_THROWING);
	}

	/**
	 * Queries whether tracing is enabled for the specified debug option of this
	 * plug-in.
	 * 
	 * @param option The debug option for which to determine trace enablement.
	 * @return Whether tracing is enabled for the debug option of the plug-in.
	 */
	public static boolean shouldTrace(String option) {
		return EMFModelValidationPlugin.Tracing.shouldTrace(option);
	}

	/**
	 * Traces the specified message from this plug-in.
	 * 
	 * @param message The message to be traced.
	 */
	public static void trace(String message) {
		EMFModelValidationPlugin.Tracing.trace(message);
	}

	/**
	 * Traces the specified message from this plug-in for the specified debug
	 * option.
	 * 
	 * @param option  The debug option for which to trace.
	 * @param message The message to be traced.
	 */
	public static void trace(String option, String message) {
		EMFModelValidationPlugin.Tracing.trace(option, message);
	}

	/**
	 * Traces an entry into the specified method of the specified class.
	 * 
	 * @param clazz      The class whose method is being entered.
	 * @param methodName The name of method that is being entered.
	 */
	public static void entering(Class<?> clazz, String methodName) {

		EMFModelValidationPlugin.Tracing.entering(EMFModelValidationDebugOptions.METHODS_ENTERING, clazz, methodName);
	}

	/**
	 * Traces an entry into the specified method of the specified class, with the
	 * specified parameter.
	 * 
	 * @param clazz      The class whose method is being entered.
	 * @param methodName The name of method that is being entered.
	 * @param parameter  The parameter to the method being entered.
	 */
	public static void entering(Class<?> clazz, String methodName, Object parameter) {

		EMFModelValidationPlugin.Tracing.entering(EMFModelValidationDebugOptions.METHODS_ENTERING, clazz, methodName,
				parameter);
	}

	/**
	 * Traces an entry into the specified method of the specified class, with the
	 * specified parameters.
	 * 
	 * @param clazz      The class whose method is being entered.
	 * @param methodName The name of method that is being entered.
	 * @param parameters The parameters to the method being entered.
	 */
	public static void entering(Class<?> clazz, String methodName, Object[] parameters) {

		EMFModelValidationPlugin.Tracing.entering(EMFModelValidationDebugOptions.METHODS_ENTERING, clazz, methodName,
				parameters);
	}

	/**
	 * Traces an entry into the specified method of the specified class.
	 * 
	 * @param option     only trace entering if this option is enabled (in addition
	 *                   to the generic method-entry option)
	 * @param clazz      The class whose method is being entered.
	 * @param methodName The name of method that is being entered.
	 */
	public static void entering(String option, Class<?> clazz, String methodName) {

		if (shouldTraceEntering()) {
			EMFModelValidationPlugin.Tracing.entering(option, clazz, methodName);
		}
	}

	/**
	 * Traces an entry into the specified method of the specified class, with the
	 * specified parameter.
	 * 
	 * @param option     only trace entering if this option is enabled (in addition
	 *                   to the generic method-entry option)
	 * @param clazz      The class whose method is being entered.
	 * @param methodName The name of method that is being entered.
	 * @param parameter  The parameter to the method being entered.
	 */
	public static void entering(String option, Class<?> clazz, String methodName, Object parameter) {

		if (shouldTraceEntering()) {
			EMFModelValidationPlugin.Tracing.entering(option, clazz, methodName, parameter);
		}
	}

	/**
	 * Traces an entry into the specified method of the specified class, with the
	 * specified parameters.
	 * 
	 * @param option     only trace entering if this option is enabled (in addition
	 *                   to the generic method-entry option)
	 * @param clazz      The class whose method is being entered.
	 * @param methodName The name of method that is being entered.
	 * @param parameters The parameters to the method being entered.
	 */
	public static void entering(String option, Class<?> clazz, String methodName, Object[] parameters) {

		if (shouldTraceEntering()) {
			EMFModelValidationPlugin.Tracing.entering(option, clazz, methodName, parameters);
		}
	}

	/**
	 * Traces an exit from the specified method of the specified class.
	 * 
	 * @param clazz      The class whose method is being exited.
	 * @param methodName The name of method that is being exited.
	 */
	public static void exiting(Class<?> clazz, String methodName) {

		EMFModelValidationPlugin.Tracing.exiting(EMFModelValidationDebugOptions.METHODS_EXITING, clazz, methodName);
	}

	/**
	 * Traces an exit from the specified method of the specified class, with the
	 * specified return value.
	 * 
	 * @param clazz       The class whose method is being exited.
	 * @param methodName  The name of method that is being exited.
	 * @param returnValue The return value of the method being exited.
	 */
	public static void exiting(Class<?> clazz, String methodName, Object returnValue) {

		EMFModelValidationPlugin.Tracing.exiting(EMFModelValidationDebugOptions.METHODS_EXITING, clazz, methodName,
				returnValue);
	}

	/**
	 * Traces an exit from the specified method of the specified class.
	 * 
	 * @param option     only trace entering if this option is enabled (in addition
	 *                   to the generic method-exit option)
	 * @param clazz      The class whose method is being exited.
	 * @param methodName The name of method that is being exited.
	 */
	public static void exiting(String option, Class<?> clazz, String methodName) {

		if (shouldTraceExiting()) {
			EMFModelValidationPlugin.Tracing.exiting(option, clazz, methodName);
		}
	}

	/**
	 * Traces an exit from the specified method of the specified class, with the
	 * specified return value.
	 * 
	 * @param option      only trace entering if this option is enabled (in addition
	 *                    to the generic method-exit option)
	 * @param clazz       The class whose method is being exited.
	 * @param methodName  The name of method that is being exited.
	 * @param returnValue The return value of the method being exited.
	 */
	public static void exiting(String option, Class<?> clazz, String methodName, Object returnValue) {

		if (shouldTraceExiting()) {
			EMFModelValidationPlugin.Tracing.exiting(option, clazz, methodName, returnValue);
		}
	}

	/**
	 * Traces the catching of the specified throwable in the specified method of the
	 * specified class.
	 * 
	 * @param clazz      The class in which the throwable is being caught.
	 * @param methodName The name of the method in which the throwable is being
	 *                   caught.
	 * @param throwable  The throwable that is being caught.
	 */
	public static void catching(Class<?> clazz, String methodName, Throwable throwable) {

		EMFModelValidationPlugin.Tracing.catching(EMFModelValidationDebugOptions.EXCEPTIONS_CATCHING, clazz, methodName,
				throwable);
	}

	/**
	 * Traces the throwing of the specified throwable from the specified method of
	 * the specified class.
	 * 
	 * @param clazz      The class from which the throwable is being thrown.
	 * @param methodName The name of the method from which the throwable is being
	 *                   thrown.
	 * @param throwable  The throwable that is being thrown.
	 */
	public static void throwing(Class<?> clazz, String methodName, Throwable throwable) {

		EMFModelValidationPlugin.Tracing.throwing(EMFModelValidationDebugOptions.EXCEPTIONS_THROWING, clazz, methodName,
				throwable);
	}

	/**
	 * Converts an array of objects to a string for trace output.
	 * 
	 * @param array the array to convert to a string
	 * @return the string
	 */
	public static String toString(Object[] array) {
		StringBuffer result = new StringBuffer(64);

		result.append('[');

		for (int i = 0; i < array.length; i++) {
			if (i > 0) {
				result.append(", "); //$NON-NLS-1$
			}

			result.append(array[i]);
		}

		result.append(']');

		return result.toString();
	}
}
