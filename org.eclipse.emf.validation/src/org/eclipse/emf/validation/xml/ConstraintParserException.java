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
package org.eclipse.emf.validation.xml;

/**
 * <p>
 * A checked exception indicating failure to parse a constraint.
 * </p>
 * <p>
 * This class is not intended to be used by implementors of the
 * {@link org.eclipse.emf.validation.xml.IXmlConstraintParser} interface.
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
	 * @param cause   the original exception which caused parsing to fail
	 */
	public ConstraintParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
