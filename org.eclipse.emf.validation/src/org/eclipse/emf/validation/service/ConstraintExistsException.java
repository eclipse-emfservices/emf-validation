/******************************************************************************
 * Copyright (c) 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.service;

/**
 * Constraint thrown to indicate that a constraint descriptor cannot be
 * {@link ConstraintRegistry#register(IConstraintDescriptor) registered} because
 * a constraint is already registered under the same ID.
 *
 * @author Christian W. Damus (cdamus)
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
