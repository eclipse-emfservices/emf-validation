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
 * Constraint implementation which is used to test the lazy instantiation of
 * constraint objects.
 *
 * @author Christian W. Damus (cdamus)
 */
public class LazyTestModelConstraint extends AbstractModelConstraint {
	private static int instanceCount = 0;

	/**
	 * Initializes me.
	 */
	public LazyTestModelConstraint() {
		tickInstanceCount();
	}

	/**
	 * Queries the number of times that this class has been instantiated.
	 *
	 * @return the number of instances that were ever created
	 */
	public static int getInstanceCount() {
		return instanceCount;
	}

	/**
	 * Increments the instance count.
	 */
	private synchronized static void tickInstanceCount() {
		instanceCount++;
	}

	// implements the inherited method
	@Override
	public IStatus validate(IValidationContext ctx) {
		return ctx.createSuccessStatus();
	}
}
