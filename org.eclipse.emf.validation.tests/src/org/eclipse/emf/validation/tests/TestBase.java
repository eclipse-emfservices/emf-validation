/**
 * <copyright>
 *
 * Copyright (c) 2003, 2007 IBM Corporation and others.
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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ILiveValidator;
import org.eclipse.emf.validation.service.ITraversalStrategy;
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
	protected final ILiveValidator liveValidator;
	
	public TestBase(String name) {
		super(name);
		
		batchValidator = ModelValidationService.getInstance().newValidator(
				EvaluationMode.BATCH);
		batchValidator.setTraversalStrategy(new ITraversalStrategy.Flat());
		batchValidator.setReportSuccesses(true);
		
		treeValidator = ModelValidationService.getInstance().newValidator(
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
			String... ids) {
		
		Collection<String> found = new java.util.ArrayList<String>();
		
		for (IStatus element : statuses) {
			IModelConstraint next = ((IConstraintStatus)element).getConstraint();
			
			String id = next.getDescriptor().getId();
			
			if (Arrays.asList(ids).contains(id)) {
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
			Collection<IModelConstraint> constraints,
			String... ids) {
		
		Collection<String> found = new java.util.ArrayList<String>();
		
		for (IModelConstraint next : constraints) {
			String id = next.getDescriptor().getId();
			
			if (Arrays.asList(ids).contains(id)) {
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
			String... ids) {
		
		Collection<String> notFound = new java.util.LinkedList<String>(Arrays.asList(ids));
		
		for (IStatus element : statuses) {
			IModelConstraint next = ((IConstraintStatus)element).getConstraint();
			
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
			Collection<IModelConstraint> constraints,
			String... ids) {
		
		Collection<String> notFound = new java.util.LinkedList<String>(Arrays.asList(ids));
		
		for (IModelConstraint next : constraints) {
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
			Collection<? extends EObject> targets) {
		
		Collection<EObject> notFound = new java.util.LinkedList<EObject>(targets);
		
		for (IStatus element : statuses) {
			IConstraintStatus next = (IConstraintStatus)element;
			
			notFound.remove(next.getTarget());
		}
		
		if (!notFound.isEmpty()) {
			fail("Did not find " + constraintType + " targets: " + notFound); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	protected void assertConstraintAndTargetNotPresent(String constraintType,
			IStatus[] statuses, String constraintId, EObject target) {
		for (IStatus element : statuses) {
			IConstraintStatus status = (IConstraintStatus)element;
			IModelConstraint constraint  = ((IConstraintStatus)element).getConstraint();
			
			if (target.equals(status.getTarget()) && constraintId.equals(constraint.getDescriptor().getId())) {
				fail("Found unwanted " + constraintType + " constraint " + constraintId + " on " + target); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}
	}

	protected void assertConstraintAndTargetPresent(String constraintType,
			IStatus[] statuses, String constraintId, EObject target) {
		for (IStatus element : statuses) {
			IConstraintStatus status = (IConstraintStatus)element;
			IModelConstraint constraint  = ((IConstraintStatus)element).getConstraint();
			
			if (target.equals(status.getTarget()) && constraintId.equals(constraint.getDescriptor().getId())) {
				return;
			}
		}
		fail("Did not find " + constraintType + " constraint " + constraintId + " on " + target); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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
		
		for (IStatus element : statuses) {
			IConstraintStatus next = (IConstraintStatus)element;
			
			if (next.getConstraint().getDescriptor().getId().equals(id)) {
				result = next;
				break;
			}
		}
		return result;
	}

	/**
	 * Helper method to find multiple statuses matching a constraint ID.
	 * 
	 * @param statuses the statuses to search
	 * @param id the constraint ID to look for
	 * @return the matching statuses, or empty array if none found
	 * 
	 * @since 1.1
	 */
	protected IStatus[] getStatuses(IStatus[] statuses, String id) {
		List<IStatus> result = new java.util.ArrayList<IStatus>();
		
		for (IStatus element : statuses) {
			IConstraintStatus next = (IConstraintStatus) element;
			
			if (next.getConstraint().getDescriptor().getId().equals(id)) {
				result.add(next);
			}
		}
		
		return result.toArray(new IStatus[result.size()]);
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
		if (status.getCode() == EMFModelValidationStatusCodes.NO_CONSTRAINTS_EVALUATED) {
			return new IStatus[0];
		}
		
		List<IStatus> result = new java.util.ArrayList<IStatus>();
		
		collectStatuses(status, result);
		
		return result.toArray(new IStatus[result.size()]);
	}
	
	private void collectStatuses(IStatus status, List<IStatus> statuses) {
		if (status.isMultiStatus()) {
			IStatus[] children = status.getChildren();
			
			for (IStatus element : children) {
				collectStatuses(element, statuses);
			}
		} else {
			statuses.add(status);
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
		for (EObject next : o.eContents()) {
			showRecursive(next, childTabs);
		}
	}

}
