/**
 * <copyright>
 *
 * Copyright (c) 2003, 2006 IBM Corporation and others.
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
	public IStatus validate(IValidationContext ctx) {
		return ctx.createSuccessStatus();
	}
}
