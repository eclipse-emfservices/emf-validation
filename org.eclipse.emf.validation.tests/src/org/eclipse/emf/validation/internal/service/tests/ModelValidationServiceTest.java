/**
 * <copyright>
 *
 * Copyright (c) 2003, 2006 IBM Corporation and others.
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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import ordersystem.Address;
import ordersystem.LineItem;
import ordersystem.Order;
import ordersystem.OrderSystemFactory;
import ordersystem.OrderSystemPackage;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.emf.validation.tests.TestNotification;

/**
 * Basic tests of the {@link ModelValidationService} API.  More advanced tests
 * that check for performance optimizations and other internal workings are
 * implemented in the
 * {@link org.eclipse.emf.validation.tests.FrameworkTest} class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ModelValidationServiceTest extends TestBase {
	public ModelValidationServiceTest(String name) {
		super(name);
	}
	
	public void test_getInstance() {
		ModelValidationService instance = ModelValidationService.getInstance();
		
		assertSame(instance, ModelValidationService.getInstance());
	}

	public void testFindClass() {
		EClass eClass = OrderSystemPackage.eINSTANCE.getAccount();
		
		assertSame(
				eClass,
				ModelValidationService.findClass(
						"http:///ordersystem.ecore", //$NON-NLS-1$
						"Account")); //$NON-NLS-1$
	}

	public void test_validateBatchSingle() {
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		assertAllConstraintsPresent(
				"batch", //$NON-NLS-1$
				status,
				Arrays.asList(new String[] {
						ID_PREFIX + "order.hasContents", //$NON-NLS-1$
						ID_PREFIX + "order.notFilledBeforePlacement", //$NON-NLS-1$
				}));
	}

	public void test_validateBatchCollection() {
		Collection objects = new java.util.ArrayList();
		objects.add(OrderSystemFactory.eINSTANCE.createOrder());
		objects.add(OrderSystemFactory.eINSTANCE.createAddress());

		IStatus[] status = getStatuses(batchValidator.validate(objects));

		assertAllTargetsPresent(
				"batch", //$NON-NLS-1$
				status,
				objects);
	}

	public void test_validateSubtreeBatchSingle() {
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		LineItem item = OrderSystemFactory.eINSTANCE.createLineItem();
		
		order.getItem().add(item);

		IStatus[] status = getStatuses(treeValidator.validate(order));

		assertAllTargetsPresent(
				"batch", //$NON-NLS-1$
				status,
				Arrays.asList(new Object[] {
						order,
						item,
				}));
	}

	public void test_validateSubtreeBatchCollection() {
		Collection orders = new java.util.ArrayList();
		
		Order order1 = OrderSystemFactory.eINSTANCE.createOrder();
		LineItem item1 = OrderSystemFactory.eINSTANCE.createLineItem();
		
		order1.getItem().add(item1);
		orders.add(order1);

		Order order2 = OrderSystemFactory.eINSTANCE.createOrder();
		LineItem item2 = OrderSystemFactory.eINSTANCE.createLineItem();
		
		order2.getItem().add(item2);
		orders.add(order2);
		
		IStatus[] status = getStatuses(treeValidator.validate(orders));

		assertAllTargetsPresent(
				"batch", //$NON-NLS-1$
				status,
				Arrays.asList(new Object[] {
						order1,
						item1,
						order2,
						item2,
				}));
	}

	public void test_validateLiveSingle() {
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();
		new XMIResourceImpl().getContents().add(object);  // must be in a resource
		Notification event = new TestNotification(object, Notification.SET);
		
		IStatus[] status = getStatuses(liveValidator.validate(event));

		assertAllConstraintsPresent(
				"live", //$NON-NLS-1$
				status,
				Arrays.asList(new String[] {
						ID_PREFIX + "order.hasName", //$NON-NLS-1$
						ID_PREFIX + "order.hasOwner", //$NON-NLS-1$
				}));
	}

	public void test_validateLiveList() {
		List events = new java.util.ArrayList();
		Resource res = new XMIResourceImpl();
		
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		Address address = OrderSystemFactory.eINSTANCE.createAddress();
		res.getContents().add(order);    // must be in a resource
		res.getContents().add(address);  // must be in a resource
		
		events.add(new TestNotification(
						order,
						Notification.SET));
		events.add(new TestNotification(
						address,
						Notification.SET));

		IStatus[] status = getStatuses(liveValidator.validate(events));

		List targets = new java.util.LinkedList(events);
		
		for (ListIterator iter = targets.listIterator(); iter.hasNext();) {
			iter.set(((Notification)iter.next()).getNotifier());
		}
		
		assertAllTargetsPresent(
				"live", //$NON-NLS-1$
				status,
				targets);
	}
}
