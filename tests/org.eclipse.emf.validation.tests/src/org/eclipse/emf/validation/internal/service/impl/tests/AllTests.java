/**
 * Copyright (c) 2003, 2014 IBM Corporation, Zeligsoft Inc., CEA, and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Zeligsoft - Bug 249496
 *   Christian W. Damus (CEA) - bug 433050
 */
package org.eclipse.emf.validation.internal.service.impl.tests;

import junit.framework.TestSuite;

/**
 * Test suite encapsulating all of the JUnit tests in this package.
 *
 * @author Christian W. Damus (cdamus)
 */
public final class AllTests extends TestSuite {
	/**
	 * Creates the test suite.
	 *
	 * @return the test suite
	 */
	public AllTests() {
		super("Test for org.eclipse.emf.validation.internal.service package"); //$NON-NLS-1$

		addTestSuite(ConstraintDescriptorTest.class);

		addTestSuite(GetBatchConstraintsOperationTest.class);
		addTestSuite(GetLiveConstraintsOperationTest.class);

		addTestSuite(ProviderDescriptorTest.class);
		addTestSuite(ConstraintCacheTest.class);

		addTestSuite(BatchValidatorTest.class);
		addTestSuite(LiveValidatorTest.class);

		addTest(ClientContextTest.suite());

		addTestSuite(TraversalStrategyManagerTest.class);
	}
}
