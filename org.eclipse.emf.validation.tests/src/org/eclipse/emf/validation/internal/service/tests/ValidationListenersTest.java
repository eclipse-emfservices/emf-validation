/**
 * <copyright>
 *
 * Copyright (c) 2005, 2006 IBM Corporation and others.
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

package org.eclipse.emf.validation.internal.service.tests;

import ordersystem.OrderSystemFactory;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.service.ValidationEvent;
import org.eclipse.emf.validation.tests.TestBase;

public class ValidationListenersTest extends TestBase {
	public ValidationListenersTest(String name) {
		super(name);
	}

	public void test_UniversalListener() {
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();

		batchValidator.validate(object);

		assertTrue(UniversalValidationListener.LAST_EVENT != null);
		
		ValidationEvent event = UniversalValidationListener.LAST_EVENT;
		
		assertTrue(event.getClientContextIds().contains("org.eclipse.emf.validation.tests.junit")); //$NON-NLS-1$
	}
	
	public void test_ClientContextListener() {
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();

		batchValidator.validate(object);

		assertTrue(ClientContextValidationListener.LISTENER_CALLED);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		ClientContextValidationListener.LISTENER_CALLED = false;
		UniversalValidationListener.LAST_EVENT = null;
		UniversalValidationListener.enabled = true;
	}
	
	protected void tearDown() throws Exception {
		UniversalValidationListener.enabled = false;
		UniversalValidationListener.LAST_EVENT = null;
		ClientContextValidationListener.LISTENER_CALLED = false;
		
		super.tearDown();
	}
}
