/**
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
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
	@Override
	public IStatus validate(IValidationContext ctx) {
		throw new RuntimeException("I was meant to abend."); //$NON-NLS-1$
	}
}
