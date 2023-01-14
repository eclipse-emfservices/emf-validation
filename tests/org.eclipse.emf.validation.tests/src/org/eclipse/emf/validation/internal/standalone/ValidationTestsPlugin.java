/******************************************************************************
 * Copyright (c) 2009 SAP AG and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * Contributors:
 *    SAP AG - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.internal.standalone;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;

/**
 * Provides the resource locator for the standalone test.
 *
 * @author Boris Gruschko
 *
 */
public class ValidationTestsPlugin extends EMFPlugin {

	public static final ValidationTestsPlugin INSTANCE = new ValidationTestsPlugin();

	/**
	 * @param delegateResourceLocators
	 */
	public ValidationTestsPlugin() {
		super(new ResourceLocator[] {});
	}

	@Override
	public ResourceLocator getPluginResourceLocator() {
		return null;
	}
}
