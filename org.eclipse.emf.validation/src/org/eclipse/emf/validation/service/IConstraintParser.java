/**
 * Copyright (c) 2006 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.service;

/**
 * Marker interface for extensions on the
 * <tt>org.eclipse.emf.validation.constraintParsers</tt> extension point,
 * defining an object that knows how to parse {@link IConstraintDescriptor}s to
 * create the constraint implementations.
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
