/**
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.service.tests;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.validation.service.ValidationEvent;
import org.eclipse.emf.validation.tests.AllTests;
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.ui.PlatformUI;

import ordersystem.Order;
import ordersystem.OrderSystemFactory;

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
	
	public void liveValidationJUnitLockupTest() {
		AllTests.executingUnitTests = true;
		
		// Uncomment the following line to ensure that this
		//  test will lock up.
		//org.eclipse.jface.dialogs.ErrorDialog.AUTOMATED_MODE = false;
		
		Resource r = new ResourceImpl();
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		r.getContents().add(order);
		
		final Notification[] notification = new Notification[1];
		notification[0] = null;
		order.eAdapters().add(new AdapterImpl() {
			@Override
            public void notifyChanged(Notification msg) {
				notification[0] = msg;
			}
		});
		
		order.setId("id"); //$NON-NLS-1$
		
		// We need the workbench to be running in order to simulate this
		//  problem with the JUnits getting locked up by the live validation
		//  dialog.
		assert PlatformUI.isWorkbenchRunning() : "The workbench must be up in order for this test to make any sense."; //$NON-NLS-1$
		
		liveValidator.validate(notification[0]);
		
		// If this test case doesn't lock up then we have proven
		//  that the live validation dialog will not lock up JUnit
		//  test cases.
	}
	
	@Override
    protected void setUp() throws Exception {
		super.setUp();
		
		ClientContextValidationListener.LISTENER_CALLED = false;
		UniversalValidationListener.LAST_EVENT = null;
		UniversalValidationListener.enabled = true;
	}
	
	@Override
    protected void tearDown() throws Exception {
		UniversalValidationListener.enabled = false;
		UniversalValidationListener.LAST_EVENT = null;
		ClientContextValidationListener.LISTENER_CALLED = false;
		
		super.tearDown();
	}
}
