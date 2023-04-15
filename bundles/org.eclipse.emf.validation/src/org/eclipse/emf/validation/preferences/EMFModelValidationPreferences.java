/******************************************************************************
 * Copyright (c) 2003, 2007, 2023 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.preferences;

import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.osgi.service.prefs.BackingStoreException;

/**
 * Preferences manager for the EMF model validation plug-in.
 *
 * @author Christian W. Damus (cdamus)
 */
public class EMFModelValidationPreferences {
	static final String CONSTRAINT_DISABLED_PREFIX = "con.disabled/"; //$NON-NLS-1$

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
		try {
			InstanceScope.INSTANCE.getNode(EMFModelValidationPlugin.getPluginId()).flush();
		} catch (BackingStoreException e) {
			EMFModelValidationPlugin.getPlugin().getLog().warn(e.getMessage(), e);
		}
	}

	/**
	 * Queries whether the specified constraint <code>ID</code> is disabled.
	 *
	 * @param id the constraint ID
	 * @return whether it is disabled
	 */
	public static boolean isConstraintDisabled(String id) {
		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(EMFModelValidationPlugin.getPluginId());
		return preferences.getBoolean(CONSTRAINT_DISABLED_PREFIX + id, false);
	}

	/**
	 * Queries whether the specified constraint <code>ID</code> is disabled by
	 * default.
	 *
	 * @param id the constraint ID
	 * @return whether it is disabled
	 */
	public static boolean isConstraintDisabledByDefault(String id) {
		IEclipsePreferences preferenceDefaults = DefaultScope.INSTANCE.getNode(EMFModelValidationPlugin.getPluginId());
		return preferenceDefaults.getBoolean(CONSTRAINT_DISABLED_PREFIX + id, false);
	}

	/**
	 * Sets whether the specified constraint <code>id</code> is disabled.
	 *
	 * @param id       the constraint ID
	 * @param disabled whether it is disabled
	 */
	public static void setConstraintDisabled(String id, boolean disabled) {
		final String prefName = CONSTRAINT_DISABLED_PREFIX + id;
		final IConstraintDescriptor constraint = ConstraintRegistry.getInstance().getDescriptor(id);

		IEclipsePreferences preferences = InstanceScope.INSTANCE.getNode(EMFModelValidationPlugin.getPluginId());
		preferences.putBoolean(prefName, disabled);

		if (constraint != null) {
			// set its enablement from the new preference value
			constraint.setEnabled(!disabled);
		} else {
			// remove this preference to declutter the prefs.ini file
			preferences.remove(prefName);
		}
	}

	/**
	 * @since 1.4
	 */
	public static void setConstraintDisabledDefault(String id, boolean disabled) {
		IEclipsePreferences preferenceDefaults = DefaultScope.INSTANCE.getNode(EMFModelValidationPlugin.getPluginId());
		preferenceDefaults.putBoolean(CONSTRAINT_DISABLED_PREFIX + id, disabled);
	}
}
