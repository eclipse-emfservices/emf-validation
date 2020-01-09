/**
 * Copyright (c) 2005 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.examples.adapter.constraints;

import org.eclipse.core.runtime.IStatus;

import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;


/**
 * A simple example constraint to demonstrate delegation from EMF to the
 * EMF Validation Framework.  This constraint will always fail, in order
 * to emit an informational message proving that it was invoked. */
public class ExampleConstraint
	extends AbstractModelConstraint {

	/**
	 * Initializes me.
	 */
	public ExampleConstraint() {
		super();
	}

	/**
	 * I fail on every object that I see.
	 */
	@Override
	public IStatus validate(IValidationContext ctx) {
		return ctx.createFailureStatus(ctx.getTarget());
	}
}
