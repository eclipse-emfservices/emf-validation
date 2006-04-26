package org.eclipse.emf.validation.examples.internal;

import org.eclipse.ui.plugin.AbstractUIPlugin;


public class ValidationExamplesPlugin
	extends AbstractUIPlugin {

	// The shared instance.
	private static ValidationExamplesPlugin plugin;
	
	public ValidationExamplesPlugin() {
		super();
		plugin = this;
	}
	
	/**
	 * Returns the shared instance.
	 */
	public static ValidationExamplesPlugin getDefault() {
		return plugin;
	}
}
