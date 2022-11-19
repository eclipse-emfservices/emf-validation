/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.emf.validation.internal;

/**
 * The debug options available for this plug-in.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class EMFModelValidationDebugOptions {
	/**
	 * This class should not be instantiated because it has only static features.
	 */
	private EMFModelValidationDebugOptions() {
		super();
	}

	public static final String DEBUG = EMFModelValidationPlugin.getPluginId() + "/debug"; //$NON-NLS-1$

	public static final String EXCEPTIONS_CATCHING = DEBUG + "/exceptions/catching"; //$NON-NLS-1$
	public static final String EXCEPTIONS_THROWING = DEBUG + "/exceptions/throwing"; //$NON-NLS-1$

	public static final String METHODS_ENTERING = DEBUG + "/methods/entering"; //$NON-NLS-1$
	public static final String METHODS_EXITING = DEBUG + "/methods/exiting"; //$NON-NLS-1$

	public static final String CACHE = DEBUG + "/cache"; //$NON-NLS-1$

	public static final String CONSTRAINTS = DEBUG + "/constraints"; //$NON-NLS-1$
	public static final String CONSTRAINTS_EVALUATION = CONSTRAINTS + "/evaluation"; //$NON-NLS-1$
	public static final String CONSTRAINTS_DISABLED = CONSTRAINTS + "/disabled"; //$NON-NLS-1$

	public static final String PARSERS = DEBUG + "/parsers"; //$NON-NLS-1$

	public static final String PROVIDERS = DEBUG + "/providers"; //$NON-NLS-1$

	public static final String LISTENERS = DEBUG + "/listeners"; //$NON-NLS-1$

	public static final String XML = DEBUG + "/xml"; //$NON-NLS-1$
}
