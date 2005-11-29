/**
 * <copyright>
 *
 * Copyright (c) 2003-2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */

package org.eclipse.emf.validation.tests;

import org.eclipse.core.runtime.IStatus;

import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

/**
 * Constraint implementation which is used to test the trapping of run-time
 * exceptions in the execution of constraints.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ThrowsConstraint extends AbstractModelConstraint {
	/**
	 * Initializes me.
	 */
	public ThrowsConstraint() {
		super();
	}

	/**
	 * I always throw an exception.
	 * 
	 * @throws RuntimeException always
	 */
	public IStatus validate(IValidationContext ctx) {
		throw new RuntimeException("I was meant to abend."); //$NON-NLS-1$
	}
}
