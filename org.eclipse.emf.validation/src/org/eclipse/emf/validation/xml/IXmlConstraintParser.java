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

import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintParser;
import org.eclipse.emf.validation.service.IParameterizedConstraintParser;

/**
 * <p>
 * Interface implemented by objects that know how to create the constraint
 * implementation described by a constraint descriptor.
 * </p>
 * <p>
 * This interface is intended to be implemented by plug-ins that supply
 * constraint parsers via the <tt>constraintParsers</tt> extension point.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 * 
 * @deprecated Use the {@link IParameterizedConstraintParser} interface,
 *     instead.  Note that the provided implementations of this interface (for
 *     OCL, Java, and EMF languages) also implement
 *     <code>IParameterizedConstraintParser</code>.
 */
public interface IXmlConstraintParser extends IConstraintParser {
	/**
	 * Parses the XML content of a constraint <code>descriptor</code> to create
	 * an implementation of the model constraint interface.
	 *  
	 * @param descriptor the constraint descriptor containing XML data in the
	 *    form of {@link org.eclipse.core.runtime.IConfigurationElement}s
	 * @return a constraint, if one can be created
	 * @throws ConstraintParserException if a constraint cannot be created from
	 *    this <code>descriptor</code>
	 */
	IModelConstraint parseConstraint(IXmlConstraintDescriptor descriptor)
				throws ConstraintParserException;
}
