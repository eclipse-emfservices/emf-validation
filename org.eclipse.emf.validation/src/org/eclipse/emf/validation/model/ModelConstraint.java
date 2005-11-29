/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.emf.validation.model;

import org.eclipse.emf.validation.service.IConstraintDescriptor;


/**
 * An abstract model constraint implementation. This class is intended
 *  to be used by constraint providers 
 *  {@link org.eclipse.emf.validation.service.IModelConstraintProvider}
 *  in order to provide live/batch validation constraints.
 * 
 * @author Chris McGee (cmcgee)
 */
public abstract class ModelConstraint
	implements IModelConstraint {

	private IConstraintDescriptor descriptor;

	/**
	 * Constructs me with the provided non-null constraint descriptor.
	 * 
	 * @param descriptor A non-null constraint descriptor that describes
	 *  this model constraint.
	 */
	public ModelConstraint(IConstraintDescriptor descriptor) {
		super();
		assert descriptor != null;
		this.descriptor = descriptor;
	}

	public final IConstraintDescriptor getDescriptor() {
		return descriptor;
	}
}
