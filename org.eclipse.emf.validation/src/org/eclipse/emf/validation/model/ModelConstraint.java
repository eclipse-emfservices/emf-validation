/******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation, Zeligsoft Inc., and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *    Zeligsoft - Bug 249690
 ****************************************************************************/

package org.eclipse.emf.validation.model;

import org.eclipse.emf.validation.service.IConstraintDescriptor;

/**
 * An abstract model constraint implementation. This class is intended to be
 * used by
 * {@linkplain org.eclipse.emf.validation.service.IModelConstraintProvider
 * constraint providers} in order to provide live/batch validation constraints.
 *
 * @author Chris McGee (cmcgee)
 */
public abstract class ModelConstraint implements IModelConstraint {

	private IConstraintDescriptor descriptor;

	/**
	 * Constructs me with the provided non-null constraint descriptor.
	 *
	 * @param descriptor A non-null constraint descriptor that describes this model
	 *                   constraint.
	 */
	public ModelConstraint(IConstraintDescriptor descriptor) {
		super();
		assert descriptor != null;
		this.descriptor = descriptor;
	}

	@Override
	public final IConstraintDescriptor getDescriptor() {
		return descriptor;
	}
}
