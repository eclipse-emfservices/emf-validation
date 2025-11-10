/**
 * Copyright (c) 2005, 2026 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.tests;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.IConstraintStatus;

public final class TestUtils {
	public static final String PLUGIN_ID = "org.eclipse.emf.validation.tests"; 

	public static final String ID_PREFIX = PLUGIN_ID + "."; 
	
	/**
	 * Helper method to find the status matching a constraint ID.
	 *
	 * @param statuses the statuses to search
	 * @param id       the constraint ID to look for
	 * @return the matching status, or <code>null</code> if it's not found
	 */
	public static IStatus getStatus(IStatus[] statuses, String id) {
		IStatus result = null;

		for (IStatus element : statuses) {
			IConstraintStatus next = (IConstraintStatus) element;

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
	 * @param id       the constraint ID to look for
	 * @return the matching statuses, or empty array if none found
	 *
	 * @since 1.1
	 */
	public static IStatus[] getStatuses(IStatus[] statuses, String id) {
		List<IStatus> result = new java.util.ArrayList<>();

		for (IStatus element : statuses) {
			IConstraintStatus next = (IConstraintStatus) element;

			if (next.getConstraint().getDescriptor().getId().equals(id)) {
				result.add(next);
			}
		}

		return result.toArray(new IStatus[result.size()]);
	}

	/**
	 * Helper method to convert the specified <code>status</code> to an array of
	 * statuses for uniform treatment of scalar and multi-statuses. As a special
	 * case, the scalar status indicating success because no constraints were
	 * evaluated results in an empty array being returned.
	 *
	 * @param status the status, which may be multi or not
	 * @return all of the statuses represented by the incoming <code>status</code>
	 */
	public static IStatus[] getStatuses(IStatus status) {
		if (status.getCode() == EMFModelValidationStatusCodes.NO_CONSTRAINTS_EVALUATED) {
			return new IStatus[0];
		}

		List<IStatus> result = new java.util.ArrayList<>();

		collectStatuses(status, result);

		return result.toArray(new IStatus[result.size()]);
	}

	private static void collectStatuses(IStatus status, List<IStatus> statuses) {
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
	 * @param o    an object to display
	 * @param tabs the indentation level (according to tree depth) of the object to
	 *             display
	 */
	public static void showRecursive(EObject o, int tabs) {
		for (int i = 0; i < tabs; i++) {
			System.out.print("  "); 
		}

		System.out.println(o);

		final int childTabs = tabs + 1;
		for (EObject next : o.eContents()) {
			showRecursive(next, childTabs);
		}
	}
}
