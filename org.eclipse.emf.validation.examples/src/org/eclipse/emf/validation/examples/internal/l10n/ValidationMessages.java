/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.emf.validation.examples.internal.l10n;

import org.eclipse.osgi.util.NLS;

/**
 * An accessor class for externalized strings.
 * 
 * @author Christian Vogt (cvogt)
 */
public class ValidationMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.validation.examples.internal.l10n.ValidationMessages"; //$NON-NLS-1$

	public static String message_exception;
	public static String BatchValidationDelegate_title;
	public static String BatchValidationDelegate_successMessage;
	public static String BatchValidationDelegate_errorMessage;
	public static String EnableLiveValidationDelegate_title;
	public static String Validation_error;
	public static String Validation_warn;
	public static String Validation_note;
	public static String Validation_problems;
	public static String Validation_rollback;
	public static String Console_Name;

	static {
		NLS.initializeMessages(BUNDLE_NAME, ValidationMessages.class);
	}
}
