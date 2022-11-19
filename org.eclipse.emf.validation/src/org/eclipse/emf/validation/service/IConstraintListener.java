/******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
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

/**
 * Interface implemented by clients who wish to receive notification whenever
 * constraints are changed.
 *
 * @since 1.1
 *
 * @author David Cummings (dcummin)
 */
public interface IConstraintListener {
	/**
	 * Notifies me that a constraint change event has taken place. The event
	 * provides information about the constraint that has changed and the operation
	 * that took place (registration, enablement etc.)
	 *
	 * @param event provides information about the constraint change
	 */
	public void constraintChanged(ConstraintChangeEvent event);
}
