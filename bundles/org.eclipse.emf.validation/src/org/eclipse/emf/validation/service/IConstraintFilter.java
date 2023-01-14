/******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.service;

import org.eclipse.emf.ecore.EObject;

/**
 * Interface implemented by clients who wish to define a constraint filter on
 * their validator.
 *
 * @see IValidator#addConstraintFilter(IConstraintFilter)
 *
 * @since 1.1
 *
 * @author David Cummings (dcummin)
 */
public interface IConstraintFilter {
	/**
	 * A shared filter instance that doesn't filter out any constraints (all pass
	 * through).
	 */
	IConstraintFilter IDENTITY_INSTANCE = new IConstraintFilter() {
		@Override
		public boolean accept(IConstraintDescriptor constraint, EObject target) {
			return true;
		}
	};

	/**
	 * Determines whether a given <code>constraint</code> and <code>target</code>
	 * pair are accepted by this filter. This is applicable to both batch and live
	 * validation modes.
	 *
	 * @param constraint descriptor of a constraint to consider for filtering
	 * @param target     the object on which the <code>constraint</code> would be
	 *                   validated
	 *
	 * @return true if the pair is accepted by the filter, false otherwise
	 */
	boolean accept(IConstraintDescriptor constraint, EObject target);
}
