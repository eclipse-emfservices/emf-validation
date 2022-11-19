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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.ConstraintStatus;

import ordersystem.LineItem;
import ordersystem.Order;

/**
 * Test of the static methods that allow setting of target in
 * {@link ConstraintStatus}.
 *
 * @author David Cummings (dcummin)
 * 
 * @since 1.1
 */
public class SetTargetConstraint extends AbstractModelConstraint {
	public static boolean enabled = false;

	// Documentation copied from the inherited specification
	@Override
	public IStatus validate(IValidationContext ctx) {
		if (!enabled) {
			return ctx.createSuccessStatus();
		}

		LineItem lineItem = null;

		if (ctx.getTarget() instanceof Order) {
			Order order = (Order) ctx.getTarget();
			Object obj = order.getItem().get(0);
			if (obj != null && obj instanceof LineItem) {
				lineItem = (LineItem) obj;
			}
		}

		if (lineItem == null) {
			return new org.eclipse.core.runtime.Status(IStatus.OK, EMFModelValidationPlugin.getPluginId(),
					EMFModelValidationStatusCodes.NO_CONSTRAINTS_EVALUATED,
					EMFModelValidationStatusCodes.NO_CONSTRAINTS_EVALUATED_MSG, null);
		}

		Collection<IStatus> statuses = new java.util.ArrayList<IStatus>();

		statuses.add(ConstraintStatus.createStatus(ctx, lineItem, Collections.singletonList(lineItem), IStatus.INFO, 13,
				"This is {0}.", //$NON-NLS-1$
				new Object[] { "fun" })); //$NON-NLS-1$
		statuses.add(ConstraintStatus.createStatus(ctx, lineItem, null, IStatus.WARNING, 7, "This is {0}.", //$NON-NLS-1$
				new Object[] { "silly" })); //$NON-NLS-1$
		statuses.add(ConstraintStatus.createSuccessStatus(ctx, lineItem, Collections.singletonList(ctx.getTarget())));

		return ConstraintStatus.createMultiStatus(ctx, statuses);
	}

}
