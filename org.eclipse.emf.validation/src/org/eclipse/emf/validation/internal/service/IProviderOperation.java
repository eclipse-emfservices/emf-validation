/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.internal.service;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IModelConstraintProvider;

/**
 * Interface describing an {@link IModelConstraintProvider} operation.
 *
 * @param <T> the result type of the operation
 *
 * @author Christian W. Damus (cdamus)
 */
public interface IProviderOperation<T extends Collection<? extends IModelConstraint>> {
	/**
	 * Obtains the EMF object that is to be validated.
	 *
	 * @return the EMF object
	 */
	EObject getEObject();

	/**
	 * Obtains the constraints that I have gathered from the available providers.
	 *
	 * @return the constraints
	 */
	T getConstraints();

	/**
	 * Executes me on the specified constraint <code>provider</code>.
	 *
	 * @param provider a constraint provider
	 * @return my {@link #getConstraints constraints collection}
	 */
	T execute(IModelConstraintProvider provider);
}
