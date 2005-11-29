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

package org.eclipse.emf.validation.tests;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ITraversalStrategy;
import org.eclipse.emf.validation.service.IValidator;
import org.eclipse.emf.validation.service.ModelValidationService;

/**
 * Common services for test cases in this test plug-in.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class TestBase extends TestCase {
	public static final String PLUGIN_ID =
		"org.eclipse.emf.validation.tests"; //$NON-NLS-1$

	public static final String ID_PREFIX =
		PLUGIN_ID + "."; //$NON-NLS-1$
	
	protected final IBatchValidator batchValidator;
	protected final IBatchValidator treeValidator;
	protected final IValidator liveValidator;
	
	public TestBase(String name) {
		super(name);
		
		batchValidator = (IBatchValidator)
			ModelValidationService.getInstance().newValidator(
				EvaluationMode.BATCH);
		batchValidator.setTraversalStrategy(new ITraversalStrategy.Flat());
		batchValidator.setReportSuccesses(true);
		
		treeValidator = (IBatchValidator)
			ModelValidationService.getInstance().newValidator(
				EvaluationMode.BATCH);
		treeValidator.setTraversalStrategy(new ITraversalStrategy.Recursive());
		treeValidator.setReportSuccesses(true);
		
		liveValidator = ModelValidationService.getInstance().newValidator(
				EvaluationMode.LIVE);
		liveValidator.setReportSuccesses(true);
	}

	/**
	 * Helper method that asserts that none of the specified constraints were
	 * retrieved by the service.
	 * 
	 * @param constraintType the type of constraint (batch, live, feature)
	 * @param statuses the constraint statuses that resulted from validation
	 * @param ids the taboo constraint IDs
	 */
	protected void assertAllConstraintsNotPresent(
			String constraintType,
			IStatus[] statuses,
			Collection ids) {
		
		Collection found = new java.util.ArrayList();
		
		for (int i = 0; i < statuses.length; i++) {
			IModelConstraint next = ((IConstraintStatus)statuses[i]).getConstraint();
			
			String id = next.getDescriptor().getId();
			
			if (ids.contains(id)) {
				found.add(id);
			}
		}
		
		if (!found.isEmpty()) {
			fail("Found unwanted " + constraintType + " constraints: " + found); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Helper method that asserts that none of the specified constraints were
	 * retrieved from a specific provider.
	 * 
	 * @param constraintType the type of constraint (batch, live, feature)
	 * @param constraints the constraints obtained from the provider
	 * @param ids the taboo constraint IDs
	 */
	protected void assertAllConstraintsNotPresent(
			String constraintType,
			Collection constraints,
			Collection ids) {
		
		Collection found = new java.util.ArrayList();
		
		for (Iterator iter = constraints.iterator(); iter.hasNext();) {
			IModelConstraint next = (IModelConstraint)iter.next();
			
			String id = next.getDescriptor().getId();
			
			if (ids.contains(id)) {
				found.add(id);
			}
		}
		
		if (!found.isEmpty()) {
			fail("Found unwanted " + constraintType + " constraints: " + found); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Helper method that asserts that all specified constraints were retrieved
	 * by the service.
	 * 
	 * @param constraintType the type of constraint (batch, live, feature)
	 * @param statuses the constraint statuses that resulted from validation
	 * @param ids the expected constraint IDs
	 */
	protected void assertAllConstraintsPresent(
			String constraintType,
			IStatus[] statuses,
			Collection ids) {
		
		Collection notFound = new java.util.LinkedList(ids);
		
		for (int i = 0; i < statuses.length; i++) {
			IModelConstraint next = ((IConstraintStatus)statuses[i]).getConstraint();
			
			notFound.remove(next.getDescriptor().getId());
		}
		
		if (!notFound.isEmpty()) {
			fail("Did not find " + constraintType + " constraints: " + notFound); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Helper method that asserts that all specified constraints were retrieved
	 * from a specific provider.
	 * 
	 * @param constraintType the type of constraint (batch, live, feature)
	 * @param constraints the constraints obtained from the provider
	 * @param ids the expected constraint IDs
	 */
	protected void assertAllConstraintsPresent(
			String constraintType,
			Collection constraints,
			Collection ids) {
		
		Collection notFound = new java.util.LinkedList(ids);
		
		for (Iterator iter = constraints.iterator(); iter.hasNext();) {
			IModelConstraint next = (IModelConstraint)iter.next();
			
			notFound.remove(next.getDescriptor().getId());
		}
		
		if (!notFound.isEmpty()) {
			fail("Did not find " + constraintType + " constraints: " + notFound); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Helper method that asserts that all specified targets has status reports
	 * generated by the service.
	 * 
	 * @param constraintType the type of constraint (batch, live, feature)
	 * @param statuses the constraint statuses that resulted from validation
	 * @param targets the expected target objects
	 */
	protected void assertAllTargetsPresent(
			String constraintType,
			IStatus[] statuses,
			Collection targets) {
		
		Collection notFound = new java.util.LinkedList(targets);
		
		for (int i = 0; i < statuses.length; i++) {
			IConstraintStatus next = (IConstraintStatus)statuses[i];
			
			notFound.remove(next.getTarget());
		}
		
		if (!notFound.isEmpty()) {
			fail("Did not find " + constraintType + " targets: " + notFound); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Helper method to find the status matching a constraint ID.
	 * 
	 * @param statuses the statuses to search
	 * @param id the constraint ID to look for
	 * @return the matching status, or <code>null</code> if it's not found
	 */
	protected IStatus getStatus(IStatus[] statuses, String id) {
		IStatus result = null;
		
		for (int i = 0; i < statuses.length; i++) {
			IConstraintStatus next = (IConstraintStatus)statuses[i];
			
			if (next.getConstraint().getDescriptor().getId().equals(id)) {
				result = next;
				break;
			}
		}
		return result;
	}

	/**
	 * Helper method to convert the specified <code>status</code> to an array
	 * of statuses for uniform treatment of scalar and multi-statuses.  As a
	 * special case, the scalar status indicating success because no constraints
	 * were evaluated results in an empty array being returned.
	 * 
	 * @param status the status, which may be multi or not
	 * @return all of the statuses represented by the incoming
	 *    <code>status</code>
	 */
	protected IStatus[] getStatuses(IStatus status) {
		if (status.isMultiStatus()) {
			return status.getChildren();
		} else if (status.getCode() == EMFModelValidationStatusCodes.NO_CONSTRAINTS_EVALUATED){
			return new IStatus[0];
		} else {
			return new IStatus[] {status};
		}
	}

	/**
	 * Helper method for recursively displaying the example model on stdout.
	 * 
	 * @param o an object to display
	 * @param tabs the indentation level (according to tree depth) of the
	 *     object to display
	 */
	protected void showRecursive(EObject o, int tabs) {
		for (int i = 0; i < tabs; i++) {
			System.out.print("  "); //$NON-NLS-1$
		}
	
		System.out.println(o);
	
		final int childTabs = tabs + 1;
		for (Iterator iter = o.eContents().iterator(); iter.hasNext(); ) {
			showRecursive((EObject)iter.next(), childTabs);
		}
	}

}
