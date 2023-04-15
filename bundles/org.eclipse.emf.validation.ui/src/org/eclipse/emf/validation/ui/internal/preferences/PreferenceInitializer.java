/******************************************************************************
 * Copyright (c) 2004, 2007, 2023 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.ui.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.emf.validation.ui.internal.ValidationUIPlugin;

/**
 * Default preferences initializer for the Validation UI plug-in.
 *
 * @author Christian W. Damus (cdamus)
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * Initializes me.
	 */
	public PreferenceInitializer() {
		super();
	}

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences preferenceDefaults = DefaultScope.INSTANCE.getNode(ValidationUIPlugin.getPlugin().getBundle().getSymbolicName());
		// validation preference defaults
		preferenceDefaults.put(IPreferenceConstants.VALIDATION_LIVE_PROBLEMS_DISPLAY, ValidationLiveProblemsDestination.DIALOG.getName());
		preferenceDefaults.putBoolean(IPreferenceConstants.VALIDATION_LIVE_WARNINGS_IN_DIALOG, true);
		preferenceDefaults.putBoolean(IPreferenceConstants.VALIDATION_LIVE_SHOW_CONSOLE, false);
	}

}
