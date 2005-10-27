/******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.xml;

import java.util.Map;

import org.eclipse.emf.validation.IValidationContext;


/**
 * Similar to the {@link org.eclipse.emf.validation.AbstractModelConstraint}
 * class, this interface defines the contract for a constraint implementation.
 * The difference is that this class supports "extraction variables", which are
 * name-value bindings of objects that are substituted for variables in the
 * error message, so that the validation method simply returns a
 * <code>boolean</code> result instead of worrying about creating status
 * objects.
 *
 * @author Christian W. Damus (cdamus)
 * @deprecated Use {@link org.eclipse.emf.validation.model.IModelConstraint} instead.
 */
public interface IConstraintWithExtractions {

	/**
	 * Evaluates me in the specified validation context with the specified
	 * map to store extraction variable values.
	 * 
	 * @param ctx the current validation context
	 * @param extractions a map into which I put extraction variables (keys are
	 *    strings, values are arbitrary objects)
	 * @return <code>true</code> if the constraint is met;
	 *    <code>false</code>, otherwise
	 */
	public abstract boolean validate(IValidationContext ctx, Map extractions);
}
