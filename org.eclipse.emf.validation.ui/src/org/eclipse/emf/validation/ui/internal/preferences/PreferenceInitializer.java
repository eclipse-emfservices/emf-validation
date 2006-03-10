/******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.ui.internal.preferences;

import org.eclipse.core.runtime.Preferences;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.emf.validation.ui.internal.ValidationUIPlugin;


/**
 * Default preferences initializer for the Validation UI plug-in.
 *
 * @author Christian W. Damus (cdamus)
 */
public class PreferenceInitializer
	extends AbstractPreferenceInitializer {

	/**
	 * Initializes me.
	 */
	public PreferenceInitializer() {
		super();
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public void initializeDefaultPreferences() {
		Preferences prefs = ValidationUIPlugin.getPlugin().getPluginPreferences();
		
		//validation preference defaults
        prefs.setDefault(IPreferenceConstants.VALIDATION_LIVE_PROBLEMS_DISPLAY, ValidationLiveProblemsDestination.DIALOG.getName());
        prefs.setDefault(IPreferenceConstants.VALIDATION_LIVE_WARNINGS_IN_DIALOG, true);
        prefs.setDefault(IPreferenceConstants.VALIDATION_LIVE_SHOW_CONSOLE, false);
	}

}
