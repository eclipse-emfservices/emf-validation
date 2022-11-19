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
 *    SAP AG - Bug 240352
 ****************************************************************************/

package org.eclipse.emf.validation.internal.util;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;

/**
 * Delegates logging duties to the
 * {@link org.eclipse.gmf.runtime.common.core.util.Log} class, with the EMF
 * Model Validation plug-in implicit in all logs.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class Log {
	/**
	 * Generates an error log for this plug-in, with the specified status code and
	 * message.
	 * 
	 * @param code    The status code for the log.
	 * @param message The message for the log.
	 */
	public static void error(int code, String message) {
		error(code, message, null);
	}

	/**
	 * Generates an error log for this plug-in, with the specified status code,
	 * message, and throwable.
	 * 
	 * @param code      The status code for the log.
	 * @param message   The message for the log.
	 * @param throwable The throwable for the log.
	 */
	public static void error(int code, String message, Throwable throwable) {

		log(IStatus.ERROR, code, message, throwable);
	}

	/**
	 * Generates a warning log for this plug-in, with the specified status code and
	 * message.
	 * 
	 * @param code    The status code for the log.
	 * @param message The message for the log.
	 * 
	 */
	public static void warning(int code, String message) {
		warning(code, message, null);
	}

	/**
	 * Generates a warning log for this plug-in, with the specified status code,
	 * message, and throwable.
	 * 
	 * @param code      The status code for the log.
	 * @param message   The message for the log.
	 * @param throwable The throwable for the log.
	 */
	public static void warning(int code, String message, Throwable throwable) {

		log(IStatus.WARNING, code, message, throwable);
	}

	/**
	 * Generates an information log for this plug-in, with the specified status code
	 * and message.
	 * 
	 * @param code    The status code for the log.
	 * @param message The message for the log.
	 */
	public static void info(int code, String message) {
		info(code, message, null);
	}

	/**
	 * Generates an information log for this plug-in, with the specified status
	 * code, message, and throwable.
	 * 
	 * @param code      The status code for the log.
	 * @param message   The message for the log.
	 * @param throwable The throwable for the log.
	 */
	public static void info(int code, String message, Throwable throwable) {

		log(IStatus.INFO, code, message, throwable);
	}

	/**
	 * Generates a log for this plug-in, with the specified severity, status code,
	 * and message.
	 * 
	 * @param severity The severity of the log.
	 * @param code     The status code for the log.
	 * @param message  The message for the log.
	 */
	public static void log(int severity, int code, String message) {

		log(severity, code, message, null);
	}

	/**
	 * Generates a log for this plug-in, with the specified severity, status code,
	 * message, and throwable.
	 * 
	 * @param severity  The severity of the log.
	 * @param code      The status code for the log.
	 * @param message   The message for the log.
	 * @param throwable The throwable for the log.
	 */
	public static void log(int severity, int code, String message, Throwable throwable) {

		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			return;
		}

		Status s = new Status(severity, EMFModelValidationPlugin.getPluginId(), code, message, throwable);

		EMFModelValidationPlugin.getPlugin().log(s);
	}

	/**
	 * Responds to a log request for this plug-in based on the specified status
	 * object. Statuses with severity of error or warning result in the generation
	 * of a platform log for the specified plug-in; all log requests are forward to
	 * the default log listener.
	 * 
	 * @param status The status object on which to base the log.
	 */
	public static void log(IStatus status) {
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			EMFModelValidationPlugin.getPlugin().log(status);
		}
	}

	/**
	 * Logs a localized error message for this plug-in.
	 * 
	 * @param code    the
	 *                {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                error code
	 * @param message the localized message
	 */
	public static void l7dError(int code, String message) {

		l7dError(code, message, null);
	}

	/**
	 * Logs a localized error message for this plug-in.
	 * 
	 * @param code      the
	 *                  {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                  error code
	 * @param message   the localized message
	 * @param exception the exception which caused the problem (if any; may be
	 *                  <code>null</code>)
	 */
	public static void l7dError(int code, String message, Throwable exception) {

		l7dLog(IStatus.ERROR, code, message, exception);
	}

	/**
	 * Logs a localized warning message for this plug-in.
	 * 
	 * @param code    the
	 *                {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                error code
	 * @param message the localized message
	 */
	public static void l7dWarning(int code, String message) {

		l7dWarning(code, message, null);
	}

	/**
	 * Logs a localized warning message for this plug-in.
	 * 
	 * @param code      the
	 *                  {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                  error code
	 * @param message   the localized message
	 * @param exception the exception which caused the problem (if any; may be
	 *                  <code>null</code>)
	 */
	public static void l7dWarning(int code, String message, Throwable exception) {

		l7dLog(IStatus.WARNING, code, message, exception);
	}

	/**
	 * Logs a localized informational message for this plug-in.
	 * 
	 * @param code    the
	 *                {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                error code
	 * @param message the localized message
	 */
	public static void l7dInfo(int code, String message) {

		l7dInfo(code, message, null);
	}

	/**
	 * Logs a localized informational message for this plug-in.
	 * 
	 * @param code      the
	 *                  {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                  error code
	 * @param message   the localized message
	 * @param exception the exception which caused the problem (if any; may be
	 *                  <code>null</code>)
	 */
	public static void l7dInfo(int code, String message, Throwable exception) {

		l7dLog(IStatus.INFO, code, message, exception);
	}

	/**
	 * Logs a localized message for this plug-in.
	 * 
	 * @param severity  the {@link org.eclipse.core.runtime.IStatus} severity code
	 * @param code      the
	 *                  {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                  error code
	 * @param message   the localized message
	 * @param exception the exception which caused the problem (if any; may be
	 *                  <code>null</code>)
	 */
	public static void l7dLog(int severity, int code, String message, Throwable exception) {

		log(severity, code, message, exception);
	}

	/**
	 * <p>
	 * Logs a localized error message for this plug-in with substitution variables.
	 * The message is formatted according to the conventions of the
	 * {@link org.eclipse.osgi.util.NLS} class.
	 * </p>
	 * <p>
	 * The value of the <code>patternArgs</code> argument may be a single object if
	 * the message pattern requires only one argument (<tt>{0}</tt>), or it may be a
	 * collection or an array if multiple arguments are required.
	 * </p>
	 * 
	 * @param code           the
	 *                       {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                       error code
	 * @param messagePattern the message message pattern
	 * @param patternArgs    the arguments to replace the variables in the message
	 *                       pattern
	 */
	public static void errorMessage(int code, String messagePattern, Object patternArgs) {

		errorMessage(code, messagePattern, patternArgs, null);
	}

	/**
	 * <p>
	 * Logs a localized error message for this plug-in with substitution variables
	 * an an exception.
	 * </p>
	 * 
	 * @param code           the
	 *                       {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                       error code
	 * @param messagePattern the message pattern
	 * @param patternArgs    the arguments to replace the variables in the message
	 *                       pattern
	 * @param exception      the exception which caused the problem (if any; may be
	 *                       <code>null</code>)
	 */
	public static void errorMessage(int code, String messagePattern, Object patternArgs, Throwable exception) {

		message(IStatus.ERROR, code, messagePattern, patternArgs, exception);
	}

	/**
	 * <p>
	 * Logs a localized warning message for this plug-in with substitution
	 * variables. The message is formatted according to the conventions of the
	 * {@link org.eclipse.osgi.util.NLS} class.
	 * </p>
	 * <p>
	 * The value of the <code>patternArgs</code> argument may be a single object if
	 * the message pattern requires only one argument (<tt>{0}</tt>), or it may be a
	 * collection or an array if multiple arguments are required.
	 * </p>
	 * 
	 * @param code           the
	 *                       {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                       error code
	 * @param messagePattern the message pattern
	 * @param patternArgs    the arguments to replace the variables in the message
	 *                       pattern
	 */
	public static void warningMessage(int code, String messagePattern, Object patternArgs) {

		warningMessage(code, messagePattern, patternArgs, null);
	}

	/**
	 * <p>
	 * Logs a localized warning message for this plug-in with substitution variables
	 * an an exception.
	 * </p>
	 * 
	 * @param code           the
	 *                       {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                       error code
	 * @param messagePattern the message pattern
	 * @param patternArgs    the arguments to replace the variables in the message
	 *                       pattern
	 * @param exception      the exception which caused the problem (if any; may be
	 *                       <code>null</code>)
	 */
	public static void warningMessage(int code, String messagePattern, Object patternArgs, Throwable exception) {

		message(IStatus.WARNING, code, messagePattern, patternArgs, exception);
	}

	/**
	 * <p>
	 * Logs a localized informational message for this plug-in with substitution
	 * variables. The message is formatted according to the conventions of the
	 * {@link org.eclipse.osgi.util.NLS} class.
	 * </p>
	 * <p>
	 * The value of the <code>patternArgs</code> argument may be a single object if
	 * the message pattern requires only one argument (<tt>{0}</tt>), or it may be a
	 * collection or an array if multiple arguments are required.
	 * </p>
	 * 
	 * @param code           the
	 *                       {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                       error code
	 * @param messagePattern the message pattern
	 * @param patternArgs    the arguments to replace the variables in the message
	 *                       pattern
	 */
	public static void infoMessage(int code, String messagePattern, Object patternArgs) {

		infoMessage(code, messagePattern, patternArgs, null);
	}

	/**
	 * <p>
	 * Logs a localized informational message for this plug-in with substitution
	 * variables an an exception.
	 * </p>
	 * 
	 * @param code           the
	 *                       {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                       error code
	 * @param messagePattern the message pattern
	 * @param patternArgs    the arguments to replace the variables in the message
	 *                       pattern
	 * @param exception      the exception which caused the problem (if any; may be
	 *                       <code>null</code>)
	 */
	public static void infoMessage(int code, String messagePattern, Object patternArgs, Throwable exception) {

		message(IStatus.INFO, code, messagePattern, patternArgs, exception);
	}

	/**
	 * <p>
	 * Logs a localized message for this plug-in with substitution variables. The
	 * message is formatted according to the conventions of the
	 * {@link org.eclipse.osgi.util.NLS} class.
	 * </p>
	 * <p>
	 * The value of the <code>patternArgs</code> argument may be a single object if
	 * the message pattern requires only one argument (<tt>{0}</tt>), or it may be a
	 * collection or an array if multiple arguments are required.
	 * </p>
	 * 
	 * @param severity       the {@link org.eclipse.core.runtime.IStatus} severity
	 *                       code
	 * @param code           the
	 *                       {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                       error code
	 * @param messagePattern the message pattern
	 * @param patternArgs    the arguments to replace the variables in the message
	 *                       pattern
	 */
	public static void message(int severity, int code, String messagePattern, Object patternArgs) {

		message(severity, code, messagePattern, patternArgs, null);
	}

	/**
	 * <p>
	 * Logs a localized message for this plug-in with substitution variables and an
	 * exception.
	 * </p>
	 * 
	 * @param severity       the {@link org.eclipse.core.runtime.IStatus} severity
	 *                       code
	 * @param code           the
	 *                       {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                       error code
	 * @param messagePattern the message pattern
	 * @param patternArgs    the arguments to replace the variables in the message
	 *                       pattern
	 * @param exception      the exception which caused the problem (if any; may be
	 *                       <code>null</code>)
	 */
	public static void message(int severity, int code, String messagePattern, Object patternArgs, Throwable exception) {

		Object[] args;

		if (patternArgs instanceof Object[]) {
			args = (Object[]) patternArgs;
		} else if (patternArgs instanceof Collection) {
			Collection<?> argsCollection = (Collection<?>) patternArgs;

			args = argsCollection.toArray(new Object[argsCollection.size()]);
		} else {
			args = new Object[] { patternArgs };
		}

		log(severity, code, EMFModelValidationPlugin.getMessage(messagePattern, args), exception);
	}

	/**
	 * <p>
	 * Logs a localized message for this plug-in with substitution variables that
	 * are also localized strings. The message is formatted according to the
	 * conventions of the {@link org.eclipse.osgi.util.NLS} class.
	 * </p>
	 * <p>
	 * The value of the <code>patternArgKeys</code> argument is an array of other
	 * keys in the resource bundle which indicate localizable text fragments to be
	 * inserted into the message.
	 * </p>
	 * 
	 * @param severity       the {@link org.eclipse.core.runtime.IStatus} severity
	 *                       code
	 * @param code           the
	 *                       {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                       error code
	 * @param messagePattern the message pattern
	 * @param patternArgKeys resource-bundle keys of text to replace the variables
	 *                       in the message pattern
	 */
	public static void l7dMessage(int severity, int code, String messagePattern, String[] patternArgKeys) {

		l7dMessage(severity, code, messagePattern, patternArgKeys, null);
	}

	/**
	 * <p>
	 * Logs a localized message for this plug-in with substitution variables that
	 * are also localized strings, and an exception.
	 * </p>
	 * 
	 * @param severity       the {@link org.eclipse.core.runtime.IStatus} severity
	 *                       code
	 * @param code           the
	 *                       {@link org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes}
	 *                       error code
	 * @param messagePattern the message pattern
	 * @param patternArgKeys resource-bundle keys of text to replace the variables
	 *                       in the message pattern
	 * @param exception      the exception which caused the problem (if any; may be
	 *                       <code>null</code>)
	 */
	public static void l7dMessage(int severity, int code, String messagePattern, String[] patternArgKeys,
			Throwable exception) {

		log(severity, code, EMFModelValidationPlugin.getMessage(messagePattern, (Object[]) patternArgKeys), exception);
	}
}
