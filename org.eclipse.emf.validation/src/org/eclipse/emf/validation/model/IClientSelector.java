/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.emf.validation.model;

/**
 * Interface of an object that knows how to match
 * {@link org.eclipse.emf.ecore.EObject}s against a client context.
 * <p>
 * This interface is intended to be implemented by clients.
 * </p>
 * 
 * @author Christian W. Damus
 */
public interface IClientSelector {
	/**
	 * Queries whether I select the specified object, which indicates that it
	 * belongs to my client context.
	 * 
	 * @param object a model element of some kind
	 * @return <code>true</code> if the <code>object</code> matches my client
	 *         context; <code>false</code>, otherwise
	 */
	boolean selects(Object object);
}
