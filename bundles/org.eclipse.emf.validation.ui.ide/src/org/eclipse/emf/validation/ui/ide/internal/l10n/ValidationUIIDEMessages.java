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
package org.eclipse.emf.validation.ui.ide.internal.l10n;

import org.eclipse.osgi.util.NLS;

/**
 * An accessor class for externalized strings.
 *
 * @author Christian Vogt (cvogt)
 */
public class ValidationUIIDEMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.emf.validation.ui.ide.internal.l10n.ValidationUIIDEMessages"; //$NON-NLS-1$

	public static String quickfix_label;

	static {
		NLS.initializeMessages(BUNDLE_NAME, ValidationUIIDEMessages.class);
	}
}
