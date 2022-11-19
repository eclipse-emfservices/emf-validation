/**
 * Copyright (c) 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.service;

import org.eclipse.core.runtime.IStatus;

/**
 * Exception indicating that a validation operation was canceled. The exception
 * carries the constraint status of the cancel-severity constraint that failed.
 * 
 * @since 1.1
 *
 * @author Christian W. Damus (cdamus)
 */
public class ValidationCanceledException extends RuntimeException {
	private static final long serialVersionUID = -2541909077699487325L;

	private final IStatus status;

	/**
	 * Initializes me with the cancel-severity status of the constraint that failed.
	 * 
	 * @param status the cancel status
	 */
	public ValidationCanceledException(IStatus status) {
		super(status.getMessage());

		this.status = status;
	}

	/**
	 * Obtains the status of the constraint that failed.
	 * 
	 * @return my constraint status
	 */
	public final IStatus getStatus() {
		return status;
	}
}
