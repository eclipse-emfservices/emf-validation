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
package org.eclipse.emf.validation.internal.service;

import org.eclipse.emf.validation.model.IClientSelector;
import org.eclipse.emf.validation.model.IModelConstraint;

/**
 * Interface of an object that defines a context to which clients of the
 * validation framework may bind constraints.  This effectively selects the
 * constraints that are applicable to the model defined by a client.
 * <p>
 * This interface is not intended to be implemented outside of the
 * validation framework.
 * </p>
 * 
 * @author Christian W. Damus
 */
public interface IClientContext {
	/**
	 * Obtains the context ID defined by the client.
	 * 
	 * @return my context ID
	 */
	String getId();
	
	/**
	 * Obtains the selector that matches determines the elements belong to me.
	 * 
	 * @return my selector
	 */
	IClientSelector getSelector();
	
	/**
	 * Queries whether I am a default client context.  A default client context
	 * is implicitly bound to constraints that have no explicit bindings (even
	 * to other default contexts).
	 * 
	 * @return whether I am a default context
	 */
	boolean isDefault();
	
	/**
	 * Queries whether I am bound to the specified <code>constraint</code>.
	 * 
	 * @param constraint a constraint
	 * @return <code>true</code> if I am bound to the <code>constraint</code>;
	 *     <code>false</code>, otherwise
	 */
	boolean includes(IModelConstraint constraint);
}
