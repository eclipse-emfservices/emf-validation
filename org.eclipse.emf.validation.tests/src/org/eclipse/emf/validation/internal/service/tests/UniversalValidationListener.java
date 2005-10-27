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

package org.eclipse.emf.validation.internal.service.tests;

import org.eclipse.emf.validation.service.IValidationListener;
import org.eclipse.emf.validation.service.ValidationEvent;

public class UniversalValidationListener implements IValidationListener {
	public static ValidationEvent LAST_EVENT = null;
	
	public void validationOccurred(ValidationEvent event) {
		LAST_EVENT = event;
	}
}
