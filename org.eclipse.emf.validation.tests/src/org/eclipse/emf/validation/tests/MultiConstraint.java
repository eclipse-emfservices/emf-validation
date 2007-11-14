/**
 * <copyright>
 *
 * Copyright (c) 2006, 2007 IBM Corporation and others.
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
 * $Id: MultiConstraint.java,v 1.2 2007/11/14 18:03:43 cdamus Exp $
 */
package org.eclipse.emf.validation.tests;

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;

/**
 * Test of the multiple results capability
 *
 * @author Christian W. Damus (cdamus)
 * 
 * @since 1.1
 */
public class MultiConstraint extends AbstractModelConstraint {
	public static boolean enabled = false;
	
	// Documentation copied from the inherited specification
	@Override
	public IStatus validate(IValidationContext ctx) {
		if (!enabled) {
			return ctx.createSuccessStatus();
		}
		
		Collection<IStatus> statuses = new java.util.ArrayList<IStatus>();
		
		statuses.add(ctx.createFailureStatus(new Object[] {"Nothing to say."})); //$NON-NLS-1$
		
		// try a nested multi-status, just for fun
		Collection<IStatus> nested = new java.util.ArrayList<IStatus>();
		nested.add(ConstraintStatus.createStatus(
				ctx,
				ctx.getTarget().eContents(),
				IStatus.INFO, 13,
				"This is {0}.", //$NON-NLS-1$
				new Object[] {"fun"})); //$NON-NLS-1$
		nested.add(ConstraintStatus.createStatus(
				ctx,
				null,
				IStatus.WARNING, 7,
				"This is {0}.", //$NON-NLS-1$
				new Object[] {"silly"})); //$NON-NLS-1$
		statuses.add(ConstraintStatus.createMultiStatus(ctx, nested));
		
		return ConstraintStatus.createMultiStatus(ctx, statuses);
	}

}
