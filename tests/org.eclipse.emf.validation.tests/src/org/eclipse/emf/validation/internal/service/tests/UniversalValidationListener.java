/**
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.service.tests;

import org.eclipse.emf.validation.service.IValidationListener;
import org.eclipse.emf.validation.service.ValidationEvent;

public class UniversalValidationListener implements IValidationListener {
	public static ValidationEvent LAST_EVENT = null;
	public static boolean enabled = false;

	@Override
	public void validationOccurred(ValidationEvent event) {
		if (enabled) {
			LAST_EVENT = event;
		}
	}
}
