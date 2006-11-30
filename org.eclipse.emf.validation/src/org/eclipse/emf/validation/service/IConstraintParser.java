/**
 * <copyright>
 *
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: IConstraintParser.java,v 1.1 2006/11/30 22:53:54 cdamus Exp $
 */
package org.eclipse.emf.validation.service;

/**
 * Marker interface for extensions on the
 * <tt>org.eclipse.emf.validation.constraintParsers</tt> extension point, defining
 * an object that knows how to parse {@link IConstraintDescriptor}s to create
 * the constraint implementations.
 *
 * @author Christian W. Damus (cdamus)
 * 
 * @see IParameterizedConstraintParser
 * 
 * @since 1.1
 */
public interface IConstraintParser {
	// marker interface
}
