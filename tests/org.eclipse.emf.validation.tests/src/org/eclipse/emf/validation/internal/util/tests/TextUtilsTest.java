/**
 * Copyright (c) 2003, 2026 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.util.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.validation.internal.util.TextUtils;
import org.junit.jupiter.api.Test;

import ordersystem.OrderSystemFactory;
import ordersystem.Product;

/**
 * JUnit tests for {@link TextUtils} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class TextUtilsTest {
	static final String TEST_NAME = "Thing-o-matic 5000"; 

	@Test
	public void test_getText() {
		Product product = OrderSystemFactory.eINSTANCE.createProduct();
		// reflective item label provider factory should kick in
		product.setName(TEST_NAME);
		assertEquals(product.eClass().getName() + ' ' + TEST_NAME, TextUtils.getText(product));
	}

	/**
	 * Tests the access to extension-point-registered providers.
	 */
	public void ignore_test_getText_registered_202191() {
		EClass eclass = EcoreFactory.eINSTANCE.createEClass();
		eclass.setName("Food"); 
		assertEquals("Food", TextUtils.getText(eclass)); 
	}
}
