/******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.preferences;

import org.eclipse.core.runtime.Preferences;

import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;

/**
 * Preferences manager for the EMF model validation plug-in.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class EMFModelValidationPreferences {
	static final String CONSTRAINT_DISABLED_PREFIX = "con.disabled/"; //$NON-NLS-1$
	
	private static final Preferences prefs =
		EMFModelValidationPlugin.getPlugin().getPluginPreferences();
	
	/**
	 * Not instantiable, as all features are static.
	 */
	private EMFModelValidationPreferences() {
		super();
	}

	/**
	 * Saves the preferences.
	 */
	public static void save() {
		EMFModelValidationPlugin.getPlugin().savePluginPreferences();
	}
	
	/**
	 * Queries whether the specified constraint <code>ID</code> is disabled.
	 * 
	 * @param id the constraint ID
	 * @return whether it is disabled
	 */
	public static boolean isConstraintDisabled(String id) {
		return prefs.getBoolean(CONSTRAINT_DISABLED_PREFIX + id);
	}

	/**
	 * Queries whether the specified constraint <code>ID</code> is disabled
	 * by default.
	 * 
	 * @param id the constraint ID
	 * @return whether it is disabled
	 */
	public static boolean isConstraintDisabledByDefault(String id) {
		return prefs.getDefaultBoolean(CONSTRAINT_DISABLED_PREFIX + id);
	}

	/**
	 * Sets whether the specified constraint <code>id</code> is disabled.
	 * 
	 * @param id the constraint ID
	 * @param disabled whether it is disabled
	 */
	public static void setConstraintDisabled(String id, boolean disabled) {
		final String prefName = CONSTRAINT_DISABLED_PREFIX + id;
		final IConstraintDescriptor constraint =
			ConstraintRegistry.getInstance().getDescriptor(id);
		
		prefs.setValue(prefName, disabled);
		
		if (constraint != null) {
			// set its enablement from the new preference value
			constraint.setEnabled(!disabled);
		} else {
			// remove this preference to declutter the prefs.ini file
			prefs.setToDefault(prefName);
		}
	}
}
