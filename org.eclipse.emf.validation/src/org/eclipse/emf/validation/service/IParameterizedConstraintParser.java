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

import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.xml.ConstraintParserException;

/**
 * Interface implemented by an object that knows how to construct constraint
 * implementations from {@link IParameterizedConstraintDescriptor}s.  The
 * parser is associated with a particular constraint
 * {@linkplain IParameterizedConstraintDescriptor#getLanguage() language} that
 * is supports, and for which it defines the set of parameters available to
 * configure the constraint.
 *
 * @author Christian W. Damus (cdamus)
 * 
 * @since 1.1
 */
public interface IParameterizedConstraintParser extends IConstraintParser {
	/**
	 * Parses the content of a constraint <code>descriptor</code> (including
	 * parameters) to create an implementation of the model constraint interface.
	 *  
	 * @param descriptor the constraint descriptor containing the definition
	 *    of a constraint, including any 
	 * @return a constraint, if one can be created
	 * @throws ConstraintParserException if a constraint cannot be created from
	 *    this <code>descriptor</code>
	 */
	IModelConstraint parseConstraint(IParameterizedConstraintDescriptor descriptor)
				throws ConstraintParserException;
}
