/**
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Radek Dvorak (Borland) - Bugzilla 165458
 */
package org.eclipse.emf.validation.internal.ocl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.ocl.AbstractOCLModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintParser;
import org.eclipse.emf.validation.xml.ConstraintParserException;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;
import org.eclipse.emf.validation.xml.IXmlConstraintParser;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;

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
@SuppressWarnings("deprecation")
public class OCLConstraintParser implements IParameterizedConstraintParser, IXmlConstraintParser {

	/**
	 * Initializes me.
	 */
	public OCLConstraintParser() {
		super();
	}

	// implements the inherited method
	@Override
	public IModelConstraint parseConstraint(IParameterizedConstraintDescriptor desc) {
		return new EcoreOCLConstraint(desc);
	}

	@Override
	public IModelConstraint parseConstraint(IXmlConstraintDescriptor descriptor) throws ConstraintParserException {
		return new EcoreOCLConstraint(descriptor);
	}

	/**
	 * A concrete implementation of OCL constraints for the Ecore metamodel.
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private static class EcoreOCLConstraint
			extends AbstractOCLModelConstraint<EClassifier, Constraint, EClass, EObject> {

		EcoreOCLConstraint(IConstraintDescriptor descriptor) {
			super(descriptor);
		}

		@Override
		protected EcoreEnvironmentFactory createOCLEnvironmentFactory() {
			return EcoreEnvironmentFactory.INSTANCE;
		}
	}
}
