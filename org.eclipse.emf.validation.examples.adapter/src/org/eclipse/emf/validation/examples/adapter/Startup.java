/**
 * Copyright (c) 2005 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.examples.adapter;

import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.examples.extlibrary.EXTLibraryPackage;
import org.eclipse.ui.IStartup;

/**
 * Installs an EMF <code>EValidator</code> on the Library package when we start
 * up. This validator adapts EMF's <code>EValidator</code> API to the EMF Model
 * Validation Service API.
 */
public class Startup implements IStartup {

	/**
	 * Initializes me.
	 */
	public Startup() {
		super();
	}

	/**
	 * Install the validator.
	 */
	public void earlyStartup() {
		EValidator.Registry.INSTANCE.put(EXTLibraryPackage.eINSTANCE, new EValidatorAdapter());
	}
}
