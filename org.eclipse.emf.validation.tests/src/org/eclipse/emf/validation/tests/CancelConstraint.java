/**
 * <copyright>
 *
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * $Id: CancelConstraint.java,v 1.1 2007/03/29 16:50:24 cdamus Exp $
 * 
 * </copyright>
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
	public IStatus validate(IValidationContext ctx) {
		if (!enabled) {
			return ctx.createSuccessStatus();
		}
		
		return ctx.createFailureStatus(new Object[] {ctx.getTarget()});
	}

}
