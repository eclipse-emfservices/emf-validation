/**
 * <copyright>
 *
 * Copyright (c) 2003-2005 IBM Corporation and others.
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
 * $Id$
 */

package org.eclipse.emf.validation.internal.ocl;

import org.eclipse.emf.validation.model.IModelConstraint;
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
public class OclConstraintParser implements IXmlConstraintParser {
	/**
	 * Initializes me. 
	 */
	public OclConstraintParser() {
		super();
	}

	// implements the inherited method
	public IModelConstraint parseConstraint(IXmlConstraintDescriptor desc) {
		return new OclModelConstraint(desc);
	}
}
