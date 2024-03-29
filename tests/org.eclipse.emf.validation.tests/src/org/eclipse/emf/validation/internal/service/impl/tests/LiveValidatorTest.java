/**
 * Copyright (c) 2004, 2007 IBM Corporation and others.
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
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.emf.validation.tests.TestNotification;
import org.eclipse.emf.validation.util.FilteredCollection;

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

	/**
	 * Constructor for BatchValidatorTest.
	 *
	 * @param name
	 */
	public LiveValidatorTest(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc) Extends the inherited method.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();

		validator = new LiveValidator(new BatchValidatorTest.TestExecutor());
	}

	private LiveValidator getValidator() {
		return validator;
	}

	public void test_getEvaluationMode() {
		assertSame("Wrong evaluation mode", //$NON-NLS-1$
				EvaluationMode.LIVE, validator.getEvaluationMode());
	}

	public void test_isReportSuccesses() {
		getValidator().setReportSuccesses(true);
		assertTrue("Not reporting successes", //$NON-NLS-1$
				getValidator().isReportSuccesses());

		getValidator().setReportSuccesses(false);
		assertFalse("Should not report successes", //$NON-NLS-1$
				getValidator().isReportSuccesses());
	}

	/*
	 * Class to test for IStatus validate(Object)
	 */
	public void test_validate_object() {
		Notification target = new TestNotification(OrderSystemFactory.eINSTANCE.createProduct(), Notification.SET,
				OrderSystemPackage.PRODUCT__SKU, "123", //$NON-NLS-1$
				null);

		try {
			getValidator().validate(target);
		} catch (Exception e) {
			fail("Should not throw."); //$NON-NLS-1$
		}
	}

	/*
	 * Class to test for IStatus validate(Notification)
	 */
	public void test_validate_notification() {
		Notification target = new TestNotification(OrderSystemFactory.eINSTANCE.createProduct(), Notification.SET,
				OrderSystemPackage.PRODUCT__SKU, "123", //$NON-NLS-1$
				null);

		try {
			getValidator().validate(target);
		} catch (Exception e) {
			fail("Should not throw."); //$NON-NLS-1$
		}
	}

	/*
	 * Class to test for IStatus validate(Collection)
	 */
	public void test_validateCollection() {
		Notification target = new TestNotification(OrderSystemFactory.eINSTANCE.createProduct(), Notification.SET,
				OrderSystemPackage.PRODUCT__SKU, "123", //$NON-NLS-1$
				null);

		try {
			getValidator().validate(Collections.singleton(target));
			getValidator().validate(Collections.<Notification>emptySet());
		} catch (Exception e) {
			fail("Should not throw."); //$NON-NLS-1$
		}
	}

	private OrderSystem createOrderSystem() {
		OrderSystem result = OrderSystemFactory.eINSTANCE.createOrderSystem();
		new XMLResourceImpl().getContents().add(result); // must be in a resource
		return result;
	}

	public void test_mergeNotifications_add_one() {
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

	public void test_mergeNotifications_add_many() {
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

	public void test_mergeNotifications_remove_one() {
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

	public void test_mergeNotifications_remove_many() {
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

	public void test_mergeNotifications_move_one() {
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

	public void test_mergeNotifications_move_many() {
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

	public void test_mergeNotifications_removingAdapter_one() {
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

	public void test_mergeNotifications_removingAdapter_many() {
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

	public void test_mergeNotifications_set_one() {
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

	public void test_mergeNotifications_set_many() {
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

	public void test_notificationFilterDefault_177653() {
		EObject object = OrderSystemFactory.eINSTANCE.createOrder(); // Don't add to a resource
		Notification event = new TestNotification(object, Notification.SET);

		ILiveValidator localValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		localValidator.setReportSuccesses(true);
		IStatus[] status = getStatuses(localValidator.validate(event));

		// No constraints present because notification should have been ignored
		assertAllConstraintsNotPresent("live", //$NON-NLS-1$
				status, ID_PREFIX + "order.hasName", //$NON-NLS-1$
				ID_PREFIX + "order.hasOwner"); //$NON-NLS-1$
	}

	public void test_notficationFilterCustom_177653() {
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

		IStatus[] status = getStatuses(localValidator.validate(event));

		assertAllConstraintsPresent("live", //$NON-NLS-1$
				status, ID_PREFIX + "order.hasName", //$NON-NLS-1$
				ID_PREFIX + "order.hasOwner"); //$NON-NLS-1$
	}

	public void test_notificationGenerator_177647() {
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

		IStatus[] status = getStatuses(localValidator.validate(event));

		assertAllConstraintsPresent("live", //$NON-NLS-1$
				status, ID_PREFIX + "limitedEdition.canIncludeInSpecial"); //$NON-NLS-1$

		// Ensure that a constraint targeted at ALL events (eg using wildcard),
		// does not get triggered see
		// XMLConstraintDescriptor#targetsEvent(Notification)
		assertAllConstraintsNotPresent("live", status, //$NON-NLS-1$
				ID_PREFIX + "limitedEdition.hasDates"); //$NON-NLS-1$
	}

	private static class NotificationGatherer extends AdapterImpl {
		private final List<Notification> notifications = new java.util.ArrayList<>();

		/*
		 * (non-Javadoc) Redefines/Implements/Extends the inherited method.
		 */
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

		/*
		 * (non-Javadoc) Redefines/Implements/Extends the inherited method.
		 */
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
