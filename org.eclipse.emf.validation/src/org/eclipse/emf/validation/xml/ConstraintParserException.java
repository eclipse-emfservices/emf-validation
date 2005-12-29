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


package org.eclipse.emf.validation.xml;

/**
 * <p>
 * A checked exception indicating failure to parse a constraint.
 * </p>
 * <p>
 * This class is not intended to be used by implementors of the
 * {@link org.eclipse.emf.validation.xml.IXmlConstraintParser}
 * interface.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintParserException extends Exception {
	private static final long serialVersionUID = -341693432752691706L;

	/**
	 * Initializes me with a user-friendly message but no cause.
	 * 
	 * @param message the user-friendly message
	 */
	public ConstraintParserException(String message) {
		this(message, null);
	}

	/**
	 * Initializes me with a user-friendly message and a root cause.
	 * 
	 * @param message the user-friendly message
	 * @param cause the original exception which caused parsing to fail
	 */
	public ConstraintParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
