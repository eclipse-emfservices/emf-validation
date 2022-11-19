/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/

package org.eclipse.emf.validation.service;

import java.util.EventListener;

/**
 * Interface implemented by clients who wish to receive notification whenever
 * validation operations are performed.
 *
 * @author Christian W. Damus (cdamus)
 */
public interface IValidationListener extends EventListener {
	/**
	 * Notifies me that a validation operation has been completed. The event
	 * provides information about whether the validation was a batch or live mode
	 * operation, and what the results were.
	 *
	 * @param event provides the validation operation results
	 */
	void validationOccurred(ValidationEvent event);
}
