/**
 * Copyright (c) 2005 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.examples.adapter.expressions;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * A property tester for use in XML enablement expressions on {@link EObject}s.
 * This tester supports the following properties:
 * <dl>
 * <dt>ePackage</dt>
 * <dd>value is the expected namespace URI of the {@link EPackage} containing
 * the object's {@link org.eclipse.emf.ecore.EClass}</dd>
 * </dl>
 */
public class EObjectPropertyTester extends PropertyTester {

	/**
	 * Initializes me.
	 */
	public EObjectPropertyTester() {
		super();
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;

		if ("ePackage".equals(property)) { //$NON-NLS-1$
			EPackage actual = ((EObject) receiver).eClass().getEPackage();

			// check for null just in case
			result = (actual != null) && actual.getNsURI().equals(expectedValue);
		}

		return result;
	}

}
