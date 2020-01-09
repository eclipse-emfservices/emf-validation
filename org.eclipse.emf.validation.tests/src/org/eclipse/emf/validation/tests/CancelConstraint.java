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
package org.eclipse.emf.validation.tests;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

/**
 * A test constraint registered with CANCEL severity.
 *
 * @author Christian W. Damus (cdamus)
 */
public class CancelConstraint extends AbstractModelConstraint {
	public static boolean enabled = false;
	
	// Documentation copied from the inherited specification
	@Override
    public IStatus validate(IValidationContext ctx) {
		if (!enabled) {
			return ctx.createSuccessStatus();
		}
		
		return ctx.createFailureStatus(new Object[] {ctx.getTarget()});
	}

}
