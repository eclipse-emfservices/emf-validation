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
 * $Id: OCLValidationExamplePlugin.java,v 1.2 2007/05/07 16:12:12 cdamus Exp $
 */

package org.eclipse.emf.validation.examples.ocl;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class OCLValidationExamplePlugin extends Plugin {

	//The shared instance.
	private static OCLValidationExamplePlugin plugin;

	/**
	 * The constructor.
	 */
	public OCLValidationExamplePlugin() {
		super();
		plugin = this;
	}

	public static String getID() {
		return getDefault().getBundle().getSymbolicName();
	}
	
	public static void log(String message, Throwable t) {
		getDefault().getLog().log(new Status(IStatus.ERROR, getID(),
				1, message, t));
	}
	
	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	/**
	 * Returns the shared instance.
	 */
	public static OCLValidationExamplePlugin getDefault() {
		return plugin;
	}
}
