/**
 * <copyright>
 *
 * Copyright (c) 2005 IBM Corporation and others.
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

import org.eclipse.core.expressions.PropertyTester;



/**
 * Documentation for <code>ValidationTestsPropertyTester</code>.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ValidationTestsPropertyTester
	extends PropertyTester {
	private static final String RUNNING_TESTS_PROPERTY = "runningTests"; //$NON-NLS-1$
	
	/**
	 * Initializes me.
	 */
	public ValidationTestsPropertyTester() {
		super();
	}
	
	public boolean test(Object receiver, String property, Object[] args, Object expectedValue) {
		boolean result = false;
		
		if (property.equals(RUNNING_TESTS_PROPERTY)) {
			result = AllTests.isExecutingUnitTests();
		}
		
		return result;
	}
}
