/**
 * <copyright>
 *
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.validation.ui.internal.l10n;

import org.eclipse.osgi.util.NLS;

/**
 * An accessor class for externalized strings.
 * 
 * @author Christian Vogt (cvogt)
 */
public class ValidationUIMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.validation.ui.internal.l10n.ValidationUIMessages"; //$NON-NLS-1$

	public static String prefs_main_prompt;
	public static String prefs_categories_prompt;
	public static String prefs_constraints_prompt;
	public static String prefs_no_selection;
	public static String prefs_no_description_category;
	public static String prefs_no_description_constraint;
	public static String prefs_mandatory_category;
	public static String prefs_delegate_name;
	public static String prefs_mandatory_constraint;
	public static String prefs_description_constraint;
	public static String prefs_description_constraint_error;
	public static String prefs_constraints_also;
	public static String Validation_liveValidationGroupLabel;
	public static String Validation_liveValidationDestinationPrompt;
	public static String Validation_liveValidationDestination_dialogComboItem;
	public static String Validation_liveValidationDestination_consoleComboItem;
	public static String Validation_liveValidationShowConsolePrompt;
	public static String Validation_liveValidationWarnDialogPrompt;
	public static String Validation_error;
	public static String Validation_warn;
	public static String Validation_note;
	public static String Validation_problems;
	public static String Validation_liveError;
	public static String Validation_liveDialogTitle;
	public static String Validation_dontShowCheck;
	public static String Validation_outputProviderCategory;
	
	static {
		NLS.initializeMessages(BUNDLE_NAME, ValidationUIMessages.class);
	}
}
