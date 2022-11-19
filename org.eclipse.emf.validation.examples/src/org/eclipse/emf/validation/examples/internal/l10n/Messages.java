/**
 * Copyright (c) 2008 Zeligsoft Inc. and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Zeligsoft - Initial API and implementation
 */

package org.eclipse.emf.validation.examples.internal.l10n;

import org.eclipse.osgi.util.NLS;

/**
 * Localized messages for the examples plug-in.
 *
 * @author Christian W. Damus (cdamus)
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.validation.examples.internal.l10n.messages"; //$NON-NLS-1$

	public static String AbstractExampleWizard_unzipping;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
		super();
	}
}
