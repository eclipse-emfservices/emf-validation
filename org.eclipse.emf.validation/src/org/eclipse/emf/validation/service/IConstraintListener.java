/******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.service;


/**
 * Interface implemented by clients who wish to receive notification
 * whenever constraints are changed.
 *
 * @since 1.1
 *
 * @author David Cummings (dcummin)
 */
public interface IConstraintListener {
	/**
	 * Notifies me that a constraint change event has taken place.  The
	 * event provides information about the constraint that has changed
	 * and the operation that took place (registration, enablement etc.)
	 * 
	 * @param event provides information about the constraint change
	 */
	public void constraintChanged(ConstraintChangeEvent event);
}
