/**
 * Copyright (c) 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.examples.ocl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.ocl.AbstractOCLModelConstraint;
import org.eclipse.ocl.EnvironmentFactory;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.OCL;

/**
 * Implementation of the constraint that our provider provides.
 *
 * @author Christian W. Damus (cdamus)
 */
class OCLConstraint extends AbstractOCLModelConstraint<EClassifier, Constraint, EClass, EObject> {
	private final OCL.Query query;
	
	OCLConstraint(OCLConstraintDescriptor desc, OCL ocl) {
		super(desc);
		
		this.query = ocl.createQuery(desc.getConstraint());
	}
	
	// override this method to indicate that we are doing new-style OCL
	@Override
	protected EnvironmentFactory<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, Constraint, EClass, EObject>
	createOCLEnvironmentFactory() {
		return query.getOCL().getEnvironment().getFactory();
	}
	
	@Override
	public Query<EClassifier, EClass, EObject> getConstraintCondition(EObject target) {
		return query;
	}
}
