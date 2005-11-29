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

package org.eclipse.emf.validation.internal.util.tests;

import junit.framework.TestCase;
import ordersystem.OrderSystemFactory;
import ordersystem.Product;

import org.eclipse.emf.validation.internal.util.TextUtils;

/**
 * JUnit tests for {@link TextUtils} class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class TextUtilsTest extends TestCase {
	static final String TEST_NAME = "Thing-o-matic 5000"; //$NON-NLS-1$
	
	public void test_getText() {
		Product product = OrderSystemFactory.eINSTANCE.createProduct();
		
		// reflective item label provider factory should kick in
		
		product.setName(TEST_NAME);
		
		assertEquals(
				product.eClass().getName() + ' ' + TEST_NAME,
				TextUtils.getText(product));
	}
}
