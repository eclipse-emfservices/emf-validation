/**
 * Copyright (c) 2004, 2026 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.service.impl.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.service.LiveValidator;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.ILiveValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.tests.Assertions;
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.emf.validation.tests.TestNotification;
import org.eclipse.emf.validation.tests.TestUtils;
import org.eclipse.emf.validation.util.FilteredCollection;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ordersystem.Customer;
import ordersystem.LineItem;
import ordersystem.Order;
import ordersystem.OrderSystem;
import ordersystem.OrderSystemFactory;
import ordersystem.OrderSystemPackage;
import ordersystem.Product;
import ordersystem.special.SpecialFactory;

/**
 * Tests for {@link LiveValidator}.
 *
 * @author Christian W. Damus (cdamus)
 */
public class LiveValidatorTest extends TestBase {
	private LiveValidator validator;
	
	@BeforeClass
	public static void initTestContext() {
		org.eclipse.emf.validation.tests.AllTests.executingUnitTests = true;
	}
	
	@AfterClass
	public static void resetTestContext() {
		org.eclipse.emf.validation.tests.AllTests.executingUnitTests = false;
	}

	@Before
	public void setUp() {
		validator = new LiveValidator(new BatchValidatorTest.TestExecutor());
	}

	@Test
	public void getEvaluationMode() {
		assertSame("Wrong evaluation mode", EvaluationMode.LIVE, validator.getEvaluationMode());
	}

	@Test
	public void isReportSuccesses() {
		validator.setReportSuccesses(true);
		assertTrue("Not reporting successes", validator.isReportSuccesses());

		validator.setReportSuccesses(false);
		assertFalse("Should not report successes", validator.isReportSuccesses());
	}

	/*
	 * Class to test for IStatus validate(Object)
	 */
	@Test
	public void validate_object() {
		Notification target = new TestNotification(OrderSystemFactory.eINSTANCE.createProduct(), Notification.SET,
				OrderSystemPackage.PRODUCT__SKU, "123", null);

		try {
			validator.validate(target);
		} catch (Exception e) {
			fail("Should not throw.");
		}
	}

	/*
	 * Class to test for IStatus validate(Notification)
	 */
	@Test
	public void validate_notification() {
		Notification target = new TestNotification(OrderSystemFactory.eINSTANCE.createProduct(), Notification.SET,
				OrderSystemPackage.PRODUCT__SKU, "123", null);

		try {
			validator.validate(target);
		} catch (Exception e) {
			fail("Should not throw.");
		}
	}

	/*
	 * Class to test for IStatus validate(Collection)
	 */
	@Test
	public void validateCollection() {
		Notification target = new TestNotification(OrderSystemFactory.eINSTANCE.createProduct(), Notification.SET,
				OrderSystemPackage.PRODUCT__SKU, "123", null);

		try {
			validator.validate(Collections.singleton(target));
			validator.validate(Collections.<Notification>emptySet());
		} catch (Exception e) {
			fail("Should not throw.");
		}
	}

	private OrderSystem createOrderSystem() {
		OrderSystem result = OrderSystemFactory.eINSTANCE.createOrderSystem();
		new XMLResourceImpl().getContents().add(result); // must be in a resource
		return result;
	}

	@Test
	public void mergeNotifications_add_one() {
		OrderSystem os = createOrderSystem();
		NotificationGatherer ng = new NotificationGatherer();
		os.eAdapters().add(ng);

		// make sure that we can detect whether the constraint has been called
		if (NotificationMergingTestConstraint.instance != null) {
			NotificationMergingTestConstraint.instance.clear();
		}

		// add one customer
		Customer c = OrderSystemFactory.eINSTANCE.createCustomer();
		os.getCustomer().add(c);

		// validate the changes, using a real validator
		ILiveValidator realValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		realValidator.validate(ng.getNotifications());

		// check that the constraint was evaluated
		assertNotNull(NotificationMergingTestConstraint.instance);

		// check that the constraint was invoked exactly once
		assertEquals(1, NotificationMergingTestConstraint.instance.getInvocationCount());

		// check that the merged notification has the correct event type
		assertSame(EMFEventType.ADD, NotificationMergingTestConstraint.instance.getEventType());

		// check that the merged notification has the correct new value
		assertSame(c, NotificationMergingTestConstraint.instance.getFeatureNewValue());
	}

	@Test
	public void mergeNotifications_add_many() {
		OrderSystem os = createOrderSystem();
		NotificationGatherer ng = new NotificationGatherer();
		os.eAdapters().add(ng);

		// make sure that we can detect whether the constraint has been called
		if (NotificationMergingTestConstraint.instance != null) {
			NotificationMergingTestConstraint.instance.clear();
		}

		// add a few customers, individually, to record separate notifications
		List<Customer> customers = new java.util.ArrayList<>();
		Customer c = OrderSystemFactory.eINSTANCE.createCustomer();
		os.getCustomer().add(c); // one
		customers.add(c);
		c = OrderSystemFactory.eINSTANCE.createCustomer();
		os.getCustomer().add(c); // two
		customers.add(c);
		c = OrderSystemFactory.eINSTANCE.createCustomer();
		os.getCustomer().add(c); // three
		customers.add(c);

		// validate the changes, using a real validator
		ILiveValidator realValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		realValidator.validate(ng.getNotifications());

		// check that the constraint was evaluated
		assertNotNull(NotificationMergingTestConstraint.instance);

		// check that the constraint was invoked exactly once
		assertEquals(1, NotificationMergingTestConstraint.instance.getInvocationCount());

		// check that the merged notification has the correct event type
		assertSame(EMFEventType.ADD_MANY, NotificationMergingTestConstraint.instance.getEventType());

		// check that the merged notification has the correct new value
		assertEquals(customers, NotificationMergingTestConstraint.instance.getFeatureNewValue());
	}

	@Test
	public void mergeNotifications_remove_one() {
		OrderSystem os = createOrderSystem();

		// add a few customers
		List<Customer> customers = new java.util.ArrayList<>();
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		os.getCustomer().addAll(customers);

		NotificationGatherer ng = new NotificationGatherer();
		os.eAdapters().add(ng);

		// make sure that we can detect whether the constraint has been called
		if (NotificationMergingTestConstraint.instance != null) {
			NotificationMergingTestConstraint.instance.clear();
		}

		// remove a customer
		Customer c = customers.get(0);
		os.getCustomer().remove(c);

		// validate the changes, using a real validator
		ILiveValidator realValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		realValidator.validate(ng.getNotifications());

		// check that the constraint was evaluated
		assertNotNull(NotificationMergingTestConstraint.instance);

		// check that the constraint was invoked exactly once
		assertEquals(1, NotificationMergingTestConstraint.instance.getInvocationCount());

		// check that the merged notification has the correct event type
		assertSame(EMFEventType.REMOVE, NotificationMergingTestConstraint.instance.getEventType());

		// check that the merged notification has the correct new value
		assertSame(c, NotificationMergingTestConstraint.instance.getFeatureNewValue());
	}

	@Test
	public void mergeNotifications_remove_many() {
		OrderSystem os = createOrderSystem();

		// add a few customers
		List<Customer> customers = new java.util.ArrayList<>();
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		os.getCustomer().addAll(customers);

		NotificationGatherer ng = new NotificationGatherer();
		os.eAdapters().add(ng);

		// make sure that we can detect whether the constraint has been called
		if (NotificationMergingTestConstraint.instance != null) {
			NotificationMergingTestConstraint.instance.clear();
		}

		// remove a few customers, individually, to record separate notifications
		os.getCustomer().remove(customers.get(0)); // one
		os.getCustomer().remove(customers.get(1)); // two
		os.getCustomer().remove(customers.get(2)); // three

		// validate the changes, using a real validator
		ILiveValidator realValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		realValidator.validate(ng.getNotifications());

		// check that the constraint was evaluated
		assertNotNull(NotificationMergingTestConstraint.instance);

		// check that the constraint was invoked exactly once
		assertEquals(1, NotificationMergingTestConstraint.instance.getInvocationCount());

		// check that the merged notification has the correct event type
		assertSame(EMFEventType.REMOVE_MANY, NotificationMergingTestConstraint.instance.getEventType());

		// check that the merged notification has the correct new value
		assertEquals(customers, NotificationMergingTestConstraint.instance.getFeatureNewValue());
	}

	@Test
	public void mergeNotifications_move_one() {
		OrderSystem os = createOrderSystem();

		// add a few customers
		List<Customer> customers = new java.util.ArrayList<>();
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		os.getCustomer().addAll(customers);

		NotificationGatherer ng = new NotificationGatherer();
		os.eAdapters().add(ng);

		// make sure that we can detect whether the constraint has been called
		if (NotificationMergingTestConstraint.instance != null) {
			NotificationMergingTestConstraint.instance.clear();
		}

		// move a customer
		os.getCustomer().move(0, 2);

		// validate the changes, using a real validator
		ILiveValidator realValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		realValidator.validate(ng.getNotifications());

		// check that the constraint was evaluated
		assertNotNull(NotificationMergingTestConstraint.instance);

		// check that the constraint was invoked exactly once
		assertEquals(1, NotificationMergingTestConstraint.instance.getInvocationCount());

		// check that the merged notification has the correct event type
		assertSame(EMFEventType.MOVE, NotificationMergingTestConstraint.instance.getEventType());

		// check that the merged notification has the correct new value
		assertEquals(os.getCustomer(), NotificationMergingTestConstraint.instance.getFeatureNewValue());
	}

	@Test
	public void mergeNotifications_move_many() {
		OrderSystem os = createOrderSystem();

		// add a few customers
		List<Customer> customers = new java.util.ArrayList<>();
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		customers.add(OrderSystemFactory.eINSTANCE.createCustomer());
		os.getCustomer().addAll(customers);

		NotificationGatherer ng = new NotificationGatherer();
		os.eAdapters().add(ng);

		// make sure that we can detect whether the constraint has been called
		if (NotificationMergingTestConstraint.instance != null) {
			NotificationMergingTestConstraint.instance.clear();
		}

		// move a few customers, individually, to record separate notifications
		os.getCustomer().move(0, 2); // one
		os.getCustomer().move(1, 2); // two
		os.getCustomer().move(0, 1); // three

		// validate the changes, using a real validator
		ILiveValidator realValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		realValidator.validate(ng.getNotifications());

		// check that the constraint was evaluated
		assertNotNull(NotificationMergingTestConstraint.instance);

		// check that the constraint was invoked exactly once
		assertEquals(1, NotificationMergingTestConstraint.instance.getInvocationCount());

		// check that the merged notification has the correct event type
		assertSame(EMFEventType.MOVE, NotificationMergingTestConstraint.instance.getEventType());

		// check that the merged notification has the correct new value
		assertEquals(os.getCustomer(), NotificationMergingTestConstraint.instance.getFeatureNewValue());
	}

	@Test
	public void mergeNotifications_removingAdapter_one() {
		OrderSystem os = createOrderSystem();

		NotificationGatherer ng = new NotificationGatherer();
		os.eAdapters().add(ng);

		// make sure that we can detect whether the constraint has been called
		if (NotificationMergingTestConstraint.instance != null) {
			NotificationMergingTestConstraint.instance.clear();
		}

		// remove an adapter
		os.eAdapters().remove(ng);

		// validate the changes, using a real validator
		ILiveValidator realValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		realValidator.validate(ng.getNotifications());

		// check that the constraint was evaluated
		assertNotNull(NotificationMergingTestConstraint.instance);

		// check that the constraint was invoked exactly once
		assertEquals(1, NotificationMergingTestConstraint.instance.getInvocationCount());

		// check that the merged notification has the correct event type
		assertSame(EMFEventType.REMOVING_ADAPTER, NotificationMergingTestConstraint.instance.getEventType());

		// check that the merged notification has the correct new value
		assertSame(ng, NotificationMergingTestConstraint.instance.getFeatureNewValue());
	}

	@Test
	public void mergeNotifications_removingAdapter_many() {
		OrderSystem os = createOrderSystem();

		NotificationGatherer ng = new NotificationGatherer();
		os.eAdapters().add(ng);

		// make sure that we can detect whether the constraint has been called
		if (NotificationMergingTestConstraint.instance != null) {
			NotificationMergingTestConstraint.instance.clear();
		}

		// remove the adapter few times
		os.eAdapters().remove(ng); // one
		os.eAdapters().add(ng);
		os.eAdapters().remove(ng); // two
		os.eAdapters().add(ng);
		os.eAdapters().remove(ng); // three

		// validate the changes, using a real validator
		ILiveValidator realValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		realValidator.validate(ng.getNotifications());

		// check that the constraint was evaluated
		assertNotNull(NotificationMergingTestConstraint.instance);

		// check that the constraint was invoked exactly once
		assertEquals(1, NotificationMergingTestConstraint.instance.getInvocationCount());

		// check that the merged notification has the correct event type
		assertSame(EMFEventType.REMOVING_ADAPTER, NotificationMergingTestConstraint.instance.getEventType());

		// check that the merged notification has the correct new value
		assertEquals(Collections.nCopies(3, ng), NotificationMergingTestConstraint.instance.getFeatureNewValue());
	}

	@Test
	public void mergeNotifications_set_one() {
		OrderSystem os = createOrderSystem();
		NotificationGatherer ng = new NotificationGatherer();
		os.eAdapters().add(ng);

		// make sure that we can detect whether the constraint has been called
		if (NotificationMergingTestConstraint.instance != null) {
			NotificationMergingTestConstraint.instance.clear();
		}

		// set a value
		os.setVersion(3);

		// validate the changes, using a real validator
		ILiveValidator realValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		realValidator.validate(ng.getNotifications());

		// check that the constraint was evaluated
		assertNotNull(NotificationMergingTestConstraint.instance);

		// check that the constraint was invoked exactly once
		assertEquals(1, NotificationMergingTestConstraint.instance.getInvocationCount());

		// check that the merged notification has the correct event type
		assertSame(EMFEventType.SET, NotificationMergingTestConstraint.instance.getEventType());

		// check that the merged notification has the correct new value
		assertEquals(Integer.valueOf(3), NotificationMergingTestConstraint.instance.getFeatureNewValue());
	}

	@Test
	public void mergeNotifications_set_many() {
		OrderSystem os = createOrderSystem();
		NotificationGatherer ng = new NotificationGatherer();
		os.eAdapters().add(ng);

		// make sure that we can detect whether the constraint has been called
		if (NotificationMergingTestConstraint.instance != null) {
			NotificationMergingTestConstraint.instance.clear();
		}

		// set a value a few times
		os.setVersion(1);
		os.setVersion(2);
		os.setVersion(3);

		// validate the changes, using a real validator
		ILiveValidator realValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		realValidator.validate(ng.getNotifications());

		// check that the constraint was evaluated
		assertNotNull(NotificationMergingTestConstraint.instance);

		// check that the constraint was invoked exactly once
		assertEquals(1, NotificationMergingTestConstraint.instance.getInvocationCount());

		// check that the merged notification has the correct event type
		assertSame(EMFEventType.SET, NotificationMergingTestConstraint.instance.getEventType());

		// check that the merged notification has the correct new value
		assertEquals(Integer.valueOf(3), NotificationMergingTestConstraint.instance.getFeatureNewValue());
	}

	@Test
	public void notificationFilterDefault_177653() {
		EObject object = OrderSystemFactory.eINSTANCE.createOrder(); // Don't add to a resource
		Notification event = new TestNotification(object, Notification.SET);

		ILiveValidator localValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		localValidator.setReportSuccesses(true);
		IStatus[] status = TestUtils.getStatuses(localValidator.validate(event));

		// No constraints present because notification should have been ignored
		Assertions.assertAllConstraintsNotPresent("live", status, TestUtils.ID_PREFIX + "order.hasName",
				TestUtils.ID_PREFIX + "order.hasOwner");
	}

	@Test
	public void notficationFilterCustom_177653() {
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();
		Notification event = new TestNotification(object, Notification.SET);

		ILiveValidator localValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		localValidator.setReportSuccesses(true);

		// Set notification filter which accepts eObjects that are not attached
		// to a resource
		localValidator.setNotificationFilter(new FilteredCollection.Filter<Notification>() {
			@Override
			public boolean accept(Notification element) {
				return (element.getNotifier() instanceof EObject);
			}
		});

		IStatus[] status = TestUtils.getStatuses(localValidator.validate(event));

		Assertions.assertAllConstraintsPresent("live", status, TestUtils.ID_PREFIX + "order.hasName",
				TestUtils.ID_PREFIX + "order.hasOwner");
	}

	@Test
	public void notificationGenerator_177647() {
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		LineItem item = OrderSystemFactory.eINSTANCE.createLineItem();
		Product product = SpecialFactory.eINSTANCE.createLimitedEditionProduct();

		Resource res = new XMIResourceImpl();

		res.getContents().add(order);
		res.getContents().add(item);
		res.getContents().add(product);

		item.setProduct(product);
		order.getItem().add(item);

		Notification event = new TestNotification(order, Notification.SET);

		ILiveValidator localValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		localValidator.setReportSuccesses(true);

		IStatus[] status = TestUtils.getStatuses(localValidator.validate(event));

		Assertions.assertAllConstraintsPresent("live", status,
				TestUtils.ID_PREFIX + "limitedEdition.canIncludeInSpecial");

		// Ensure that a constraint targeted at ALL events (eg using wildcard),
		// does not get triggered see
		// XMLConstraintDescriptor#targetsEvent(Notification)
		Assertions.assertAllConstraintsNotPresent("live", status, TestUtils.ID_PREFIX + "limitedEdition.hasDates");
	}

	private static class NotificationGatherer extends AdapterImpl {
		private final List<Notification> notifications = new java.util.ArrayList<>();

		@Override
		public void notifyChanged(Notification msg) {
			notifications.add(msg);
		}

		List<Notification> getNotifications() {
			return notifications;
		}
	}

	public static class NotificationMergingTestConstraint extends AbstractModelConstraint {
		static NotificationMergingTestConstraint instance = null;

		private EMFEventType eventType;
		private Object featureNewValue;
		private int invocationCount = 0;

		public NotificationMergingTestConstraint() {
			super();
		}

		@Override
		public IStatus validate(IValidationContext ctx) {
			instance = this;
			invocationCount++;

			eventType = ctx.getEventType();
			featureNewValue = ctx.getFeatureNewValue();

			return ctx.createSuccessStatus();
		}

		int getInvocationCount() {
			return invocationCount;
		}

		EMFEventType getEventType() {
			return eventType;
		}

		Object getFeatureNewValue() {
			return featureNewValue;
		}

		void clear() {
			instance = null;
			invocationCount = 0;
		}
	}
}
