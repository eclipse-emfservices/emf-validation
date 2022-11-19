/******************************************************************************
 * Copyright (c) 2003, 2008 IBM Corporation, Zeligsoft Inc., and others.
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

package org.eclipse.emf.validation.service;

/**
 * Constraint thrown to indicate that a constraint descriptor cannot be
 * {@link ConstraintRegistry#register(IConstraintDescriptor) registered} because
 * a constraint is already registered under the same ID.
 * <p>
 * This class is not intended to be extended or instantiated by clients.
 * </p>
 *
 * @author Christian W. Damus (cdamus)
 * 
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class ConstraintExistsException extends Exception {
	private static final long serialVersionUID = 5637732649693164987L;

	/**
	 * Initializes me with a message.
	 * 
	 * @param s my message
	 */
	public ConstraintExistsException(String s) {
		super(s);
	}
}
