/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

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
