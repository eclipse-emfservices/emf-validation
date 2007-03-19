/******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/
package org.eclipse.emf.validation.service;

import org.eclipse.emf.ecore.EObject;

/**
 * Interface implemented by clients who wish to define a constraint
 * filter on their validator.
 * 
 * @see IValidator#addConstraintFilter(IConstraintFilter)
 *
 * @since 1.1
 *
 * @author David Cummings (dcummin)
 */
public interface IConstraintFilter {
	/**
	 * Determines whether a given <code>constraint</code> and
	 * <code>target</code> pair are accepted by this filter.  This is
     * applicable to both batch and live validation modes.
	 * 
	 * @param constraint descriptor of a constraint to consider for filtering
	 * @param target the object on which the <code>constraint</code> would
     *     be validated
     * 
	 * @return true if the pair is accepted by the filter, false otherwise
	 */
	boolean accept(IConstraintDescriptor constraint, EObject target);
}
