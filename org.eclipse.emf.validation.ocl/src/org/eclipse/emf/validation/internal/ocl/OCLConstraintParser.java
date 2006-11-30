/**
 * <copyright>
 *
 * Copyright (c) 2003, 2006 IBM Corporation and others.
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
 * $Id: OCLConstraintParser.java,v 1.3 2006/11/30 22:52:54 cdamus Exp $
 */

package org.eclipse.emf.validation.internal.ocl;

import org.eclipse.emf.ocl.parser.EnvironmentFactory;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.ocl.AbstractOCLModelConstraint;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintParser;
import org.eclipse.emf.validation.xml.ConstraintParserException;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;
import org.eclipse.emf.validation.xml.IXmlConstraintParser;

/**
 * <p>
 * Simple constraint parser for creation of OCL-language constraints from the
 * XML.
 * </p>
 * <p>
 * This class is not intended to be used by clients of the validation framework.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class OCLConstraintParser
		implements IParameterizedConstraintParser, IXmlConstraintParser {
	
	/**
	 * Initializes me. 
	 */
	public OCLConstraintParser() {
		super();
	}

	// implements the inherited method
	public IModelConstraint parseConstraint(IParameterizedConstraintDescriptor desc) {
		return new AbstractOCLModelConstraint(desc) {
			protected EnvironmentFactory createEnvironmentFactory() {			
				return EnvironmentFactory.ECORE_INSTANCE;
			}
		};
	}
	
	public IModelConstraint parseConstraint(IXmlConstraintDescriptor descriptor) throws ConstraintParserException {
		return new AbstractOCLModelConstraint(descriptor) {
			protected EnvironmentFactory createEnvironmentFactory() {			
				return EnvironmentFactory.ECORE_INSTANCE;
			}
		};
	}
}
