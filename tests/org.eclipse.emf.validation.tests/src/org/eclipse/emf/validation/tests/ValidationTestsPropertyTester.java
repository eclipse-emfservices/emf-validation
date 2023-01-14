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
package org.eclipse.emf.validation.tests;

import org.eclipse.core.expressions.PropertyTester;

/**
 * Documentation for <code>ValidationTestsPropertyTester</code>.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ValidationTestsPropertyTester extends PropertyTester {
	private static final String RUNNING_TESTS_PROPERTY = "runningTests"; //$NON-NLS-1$

	/**
	 * Initializes me.
	 */
	public ValidationTestsPropertyTester() {
		super();
	}

	@Override
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;

		if (property.equals(RUNNING_TESTS_PROPERTY)) {
			result = AllTests.isExecutingUnitTests();
		}

		return result;
	}
}
