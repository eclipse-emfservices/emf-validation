/**
 * Copyright (c) 2003, 2026 IBM Corporation, Zeligsoft Inc. and others.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.service.AbstractValidationContext;
import org.eclipse.emf.validation.internal.service.GetBatchConstraintsOperation;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.model.ModelConstraint;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.emf.validation.service.ILiveValidator;
import org.eclipse.emf.validation.service.ITraversalStrategy;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.tests.Assertions;
import org.eclipse.emf.validation.tests.CancelConstraint;
import org.eclipse.emf.validation.tests.MultiConstraint;
import org.eclipse.emf.validation.tests.SetTargetConstraint;
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.emf.validation.tests.TestNotification;
import org.eclipse.emf.validation.util.XmlConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ordersystem.Address;
import ordersystem.LineItem;
import ordersystem.Order;
import ordersystem.OrderSystemFactory;
import ordersystem.OrderSystemPackage;
import ordersystem.impl.OrderImpl;
import ordersystem.special.PreferredCustomer;
import ordersystem.special.SpecialFactory;

/**
 * Basic tests of the {@link ModelValidationService} API. More advanced tests
 * that check for performance optimizations and other internal workings are
 * implemented in the {@link org.eclipse.emf.validation.tests.FrameworkTest}
 * class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ModelValidationServiceTest extends TestBase {
	@BeforeClass
	public static void initTestContext() {
		org.eclipse.emf.validation.tests.AllTests.executingUnitTests = true;
	}
	
	@AfterClass
	public static void resetTestContext() {
		org.eclipse.emf.validation.tests.AllTests.executingUnitTests = false;
	}
	
	@After
	public void tearDown() {
		CancelConstraint.enabled = false;
	}

	@Test
	public void test_getInstance() {
		ModelValidationService instance = ModelValidationService.getInstance();

		assertSame(instance, ModelValidationService.getInstance());
	}

	@Test
	public void testFindClass() {
		EClass eClass = OrderSystemPackage.eINSTANCE.getAccount();

		assertSame(eClass, ModelValidationService.findClass("http:///ordersystem.ecore", "Account"));
	}

	@Test
	public void test_validateBatchSingle() {
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		Assertions.assertAllConstraintsPresent("batch", status, ID_PREFIX + "order.hasContents",
				ID_PREFIX + "order.notFilledBeforePlacement");
	}

	@Test
	public void test_validateBatchCollection() {
		Collection<EObject> objects = new java.util.ArrayList<>();
		objects.add(OrderSystemFactory.eINSTANCE.createOrder());
		objects.add(OrderSystemFactory.eINSTANCE.createAddress());

		IStatus[] status = getStatuses(batchValidator.validate(objects));

		Assertions.assertAllTargetsPresent("batch", status, objects);
	}

	@Test
	public void test_validateSubtreeBatchSingle() {
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		LineItem item = OrderSystemFactory.eINSTANCE.createLineItem();

		order.getItem().add(item);

		IStatus[] status = getStatuses(treeValidator.validate(order));

		Assertions.assertAllTargetsPresent("batch", status, Arrays.asList(new EObject[] { order, item, }));
	}

	@Test
	public void test_validateSubtreeBatchCollection() {
		Collection<EObject> orders = new java.util.ArrayList<>();

		Order order1 = OrderSystemFactory.eINSTANCE.createOrder();
		LineItem item1 = OrderSystemFactory.eINSTANCE.createLineItem();

		order1.getItem().add(item1);
		orders.add(order1);

		Order order2 = OrderSystemFactory.eINSTANCE.createOrder();
		LineItem item2 = OrderSystemFactory.eINSTANCE.createLineItem();

		order2.getItem().add(item2);
		orders.add(order2);

		IStatus[] status = getStatuses(treeValidator.validate(orders));

		Assertions.assertAllTargetsPresent("batch", status,
				Arrays.asList(new EObject[] { order1, item1, order2, item2, }));
	}

	@Test
	public void test_validateLiveSingle() {
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();
		new XMIResourceImpl().getContents().add(object); // must be in a resource
		Notification event = new TestNotification(object, Notification.SET);

		IStatus[] status = getStatuses(liveValidator.validate(event));

		Assertions.assertAllConstraintsPresent("live", status, ID_PREFIX + "order.hasName",
				ID_PREFIX + "order.hasOwner");
	}

	@Test
	public void test_validateLiveList() {
		List<Notification> events = new java.util.ArrayList<>();
		Resource res = new XMIResourceImpl();

		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		Address address = OrderSystemFactory.eINSTANCE.createAddress();
		res.getContents().add(order); // must be in a resource
		res.getContents().add(address); // must be in a resource

		events.add(new TestNotification(order, Notification.SET));
		events.add(new TestNotification(address, Notification.SET));

		IStatus[] status = getStatuses(liveValidator.validate(events));

		List<EObject> targets = new java.util.ArrayList<>(events.size());

		for (Notification next : events) {
			targets.add((EObject) next.getNotifier());
		}

		Assertions.assertAllTargetsPresent("live", status, targets);
	}

	/**
	 * Tests the new capability of reporting multiple results from a single
	 * constraint, as a multi-status.
	 */
	@Test
	public void test_validateConstraintsReturningMultipleResults_161558() {
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		order.getItem().add(OrderSystemFactory.eINSTANCE.createLineItem());

		MultiConstraint.enabled = true;

		IStatus[] status = getStatuses(batchValidator.validate(order));

		MultiConstraint.enabled = false;

		Assertions.assertAllConstraintsPresent("batch", status, ID_PREFIX + "order.multiConstraint");

		status = getStatuses(status, ID_PREFIX + "order.multiConstraint");
		assertEquals(3, status.length);

		boolean foundDefault = false;
		boolean foundFun = false;
		boolean foundSilly = false;

		final Set<Order> justOrder = Collections.singleton(order);
		final Set<EObject> orderAndItem = new java.util.HashSet<>();
		orderAndItem.add(order);
		orderAndItem.addAll(order.getItem());

		for (IStatus element : status) {
			IConstraintStatus cstat = (IConstraintStatus) element;

			switch (cstat.getCode()) {
			case 1:
				// default status
				foundDefault = true;
				assertEquals("Nothing to say.", cstat.getMessage());
				assertEquals(IStatus.ERROR, cstat.getSeverity());
				assertEquals(justOrder, cstat.getResultLocus());
				break;
			case 7:
				// default status
				foundSilly = true;
				assertEquals("This is silly.", cstat.getMessage());
				assertEquals(IStatus.WARNING, cstat.getSeverity());
				assertEquals(justOrder, cstat.getResultLocus());
				break;
			case 13:
				// default status
				foundFun = true;
				assertEquals("This is fun.", cstat.getMessage());
				assertEquals(IStatus.INFO, cstat.getSeverity());
				assertEquals(orderAndItem, cstat.getResultLocus());
				break;
			}
		}

		assertTrue("Didn't find the default status", foundDefault);
		assertTrue("Didn't find the fun status", foundFun);
		assertTrue("Didn't find the silly status", foundSilly);
	}

	/**
	 * Tests the new capability of having the constraint set a target on the status
	 * that is different than the constraint's trigger
	 */
	@Test
	public void test_validateSetTargetOnStatus_178121() {
		Order order = OrderSystemFactory.eINSTANCE.createOrder();
		LineItem item = OrderSystemFactory.eINSTANCE.createLineItem();
		order.getItem().add(item);

		SetTargetConstraint.enabled = true;

		IStatus[] status = getStatuses(batchValidator.validate(order));

		SetTargetConstraint.enabled = false;

		Assertions.assertAllConstraintsPresent("batch", status, ID_PREFIX + "order.setTargetConstraint");

		status = getStatuses(status, ID_PREFIX + "order.setTargetConstraint");
		assertEquals(3, status.length);

		boolean foundFun = false;
		boolean foundSilly = false;
		boolean foundSuccess = false;

		final Set<LineItem> justItem = Collections.singleton(item);
		final Set<EObject> orderAndItem = new java.util.HashSet<>();
		orderAndItem.add(order);
		orderAndItem.addAll(order.getItem());

		for (IStatus element : status) {
			IConstraintStatus cstat = (IConstraintStatus) element;

			switch (cstat.getCode()) {
			case IModelConstraint.STATUS_CODE_SUCCESS:
				// success status
				foundSuccess = true;
				assertEquals(EMFModelValidationStatusCodes.CONSTRAINT_SUCCESS_MSG, cstat.getMessage());
				assertEquals(IStatus.OK, cstat.getSeverity());
				assertEquals(orderAndItem, cstat.getResultLocus());
				assertSame(item, cstat.getTarget());
				break;
			case 7:
				// silly status
				foundSilly = true;
				assertEquals("This is silly.", cstat.getMessage());
				assertEquals(IStatus.WARNING, cstat.getSeverity());
				assertEquals(justItem, cstat.getResultLocus());
				assertSame(item, cstat.getTarget());
				break;
			case 13:
				// default status
				foundFun = true;
				assertEquals("This is fun.", cstat.getMessage());
				assertEquals(IStatus.INFO, cstat.getSeverity());
				assertEquals(justItem, cstat.getResultLocus());
				assertSame(item, cstat.getTarget());
				break;
			}
		}

		assertTrue("Didn't find the success status", foundSuccess);
		assertTrue("Didn't find the silly status", foundSilly);
		assertTrue("Didn't find the fun status", foundFun);
	}

	@Test
	public void test_validateLiveConstraintFilter_177644() {
		Order order1 = OrderSystemFactory.eINSTANCE.createOrder();
		order1.setCompleted(true);
		Order order2 = OrderSystemFactory.eINSTANCE.createOrder();
		order2.setCompleted(false);

		EList<EObject> contents = new XMIResourceImpl().getContents();
		contents.add(order1);
		contents.add(order2);

		Collection<Notification> events = new ArrayList<>();
		events.add(new TestNotification(order1, Notification.SET));
		events.add(new TestNotification(order2, Notification.SET));

		ILiveValidator validator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		IConstraintFilter filter = new IConstraintFilter() {
			@Override
			public boolean accept(IConstraintDescriptor constraint, EObject object) {
				if (object instanceof Order) {
					Order order = (Order) object;
					return order.isCompleted() && constraint.getId().equals(ID_PREFIX + "order.hasName");
				}
				return false;
			}
		};
		validator.addConstraintFilter(filter);
		validator.setReportSuccesses(true);

		IStatus[] status = getStatuses(validator.validate(events));

		Assertions.assertConstraintAndTargetPresent("live", status, ID_PREFIX + "order.hasName", order1);
		Assertions.assertConstraintAndTargetNotPresent("live", status, ID_PREFIX + "order.hasOwner", order1);
		Assertions.assertConstraintAndTargetNotPresent("live", status, ID_PREFIX + "order.hasName", order2);
		Assertions.assertConstraintAndTargetNotPresent("live", status, ID_PREFIX + "order.hasOwner", order2);

		// remove the filter and assert the opposite
		validator.removeConstraintFilter(filter);

		status = getStatuses(validator.validate(events));
		Assertions.assertConstraintAndTargetPresent("live", status, ID_PREFIX + "order.hasOwner", order1);
		Assertions.assertConstraintAndTargetPresent("live", status, ID_PREFIX + "order.hasName", order2);
		Assertions.assertConstraintAndTargetPresent("live", status, ID_PREFIX + "order.hasOwner", order2);
	}

	@Test
	public void test_validateBatchConstraintFilter_177644() {
		Order order1 = OrderSystemFactory.eINSTANCE.createOrder();
		order1.setCompleted(true);
		Order order2 = OrderSystemFactory.eINSTANCE.createOrder();
		order2.setCompleted(false);

		EList<EObject> contents = new XMIResourceImpl().getContents();
		contents.add(order1);
		contents.add(order2);

		Collection<EObject> targets = new ArrayList<>();
		targets.add(order1);
		targets.add(order2);

		IBatchValidator validator = (IBatchValidator) ModelValidationService.getInstance()
				.newValidator(EvaluationMode.BATCH);
		IConstraintFilter filter = new IConstraintFilter() {
			@Override
			public boolean accept(IConstraintDescriptor constraint, EObject object) {
				if (object instanceof Order) {
					Order order = (Order) object;
					return order.isCompleted() && constraint.getId().equals(ID_PREFIX + "order.hasName");
				}
				return false;
			}
		};
		validator.addConstraintFilter(filter);
		validator.setReportSuccesses(true);
		validator.setIncludeLiveConstraints(true);

		IStatus[] status = getStatuses(validator.validate(targets));

		Assertions.assertConstraintAndTargetPresent("batch", status, ID_PREFIX + "order.hasName", order1);
		Assertions.assertConstraintAndTargetNotPresent("batch", status, ID_PREFIX + "order.hasOwner", order1);
		Assertions.assertConstraintAndTargetNotPresent("batch", status, ID_PREFIX + "order.hasName", order2);
		Assertions.assertConstraintAndTargetNotPresent("batch", status, ID_PREFIX + "order.hasOwner", order2);

		// remove the filter and assert the opposite
		validator.removeConstraintFilter(filter);

		status = getStatuses(validator.validate(targets));
		Assertions.assertConstraintAndTargetPresent("batch", status, ID_PREFIX + "order.hasOwner", order1);
		Assertions.assertConstraintAndTargetPresent("batch", status, ID_PREFIX + "order.hasName", order2);
		Assertions.assertConstraintAndTargetPresent("batch", status, ID_PREFIX + "order.hasOwner", order2);
	}

	/**
	 * Tests that evaluation of a cancel-severity constraint does not result in the
	 * constraint being disabled in batch validation.
	 */
	@Test
	public void test_cancelSeverity_batch_bug179776() {
		CancelConstraint.enabled = true;

		EObject object = OrderSystemFactory.eINSTANCE.createOrder();

		batchValidator.setIncludeLiveConstraints(true);
		IStatus[] status = getStatuses(batchValidator.validate(object));

		Assertions.assertAllConstraintsPresent("batch", status, ID_PREFIX + "order.cancelConstraint");

		IStatus particular = getStatus(status, ID_PREFIX + "order.cancelConstraint");
		assertTrue("Wrong kind of constraint", particular instanceof IConstraintStatus);
		IConstraintStatus cstat = (IConstraintStatus) particular;

		assertEquals(IStatus.CANCEL, cstat.getSeverity());
		assertFalse(cstat.getConstraint().getDescriptor().isError());
	}

	/**
	 * Tests that evaluation of a cancel-severity constraint does not result in the
	 * constraint being disabled in live validation.
	 */
	@Test
	public void test_cancelSeverity_live_bug179776() {
		CancelConstraint.enabled = true;

		Resource res = new XMIResourceImpl();
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();
		res.getContents().add(object); // must be in a resource

		Collection<Notification> events = new ArrayList<>();
		events.add(new TestNotification(object, Notification.SET));
		IStatus[] status = getStatuses(liveValidator.validate(events));

		Assertions.assertAllConstraintsPresent("live", status, ID_PREFIX + "order.cancelConstraint");

		IStatus particular = getStatus(status, ID_PREFIX + "order.cancelConstraint");
		assertTrue("Wrong kind of constraint", particular instanceof IConstraintStatus);
		IConstraintStatus cstat = (IConstraintStatus) particular;

		assertEquals(IStatus.CANCEL, cstat.getSeverity());
		assertFalse(cstat.getConstraint().getDescriptor().isError());
	}

	/**
	 * Checks that a constraint targeting an EClass from some package also gets
	 * instances of EClasses from other packages extending that EClass in some other
	 * package unknown to the constraint author.
	 */
	@Test
	public void test_validateInstanceOfExtendingEClassInOtherPackage_183917() {
		EPackage epackage = EcoreFactory.eINSTANCE.createEPackage();
		epackage.setNsURI("http://test/extending/package");
		epackage.setNsPrefix("test");
		epackage.setName("testextendingpackage");

		final EClass myOrderClass = EcoreFactory.eINSTANCE.createEClass();
		epackage.getEClassifiers().add(myOrderClass);

		class MyOrderImpl extends OrderImpl {
			@Override
			public EClass eClass() {
				return myOrderClass;
			}
		}
		myOrderClass.setName("MyOrder");
		myOrderClass.setInstanceClass(MyOrderImpl.class);
		myOrderClass.getESuperTypes().add(OrderSystemPackage.eINSTANCE.getOrder());

		Order order = new MyOrderImpl();

		IStatus[] status = getStatuses(batchValidator.validate(order));

		Assertions.assertAllConstraintsPresent("batch", status, ID_PREFIX + "order.hasContents",
				ID_PREFIX + "order.notFilledBeforePlacement");
	}

	/**
	 * Tests that the default recursive traversal strategy implementation does not
	 * repeatedly validate an element.
	 */
	@Test
	public void test_recursiveTraversalStrategy_207990() {
		Collection<EObject> objects = new java.util.ArrayList<>();

		Order order1 = OrderSystemFactory.eINSTANCE.createOrder();
		LineItem item1 = OrderSystemFactory.eINSTANCE.createLineItem();

		order1.getItem().add(item1);

		objects.add(item1); // child element precedes ancestor
		objects.add(order1);

		Set<EObject> visited = new java.util.HashSet<>();
		ITraversalStrategy traversal = batchValidator.getDefaultTraversalStrategy();

		traversal.startTraversal(objects, new NullProgressMonitor());
		while (traversal.hasNext()) {
			EObject next = traversal.next();
			assertTrue("Already traversed this element", visited.add(next));
			traversal.elementValidated(next, Status.OK_STATUS);
		}
	}

	/**
	 * Tests that using the same batch validator twice in succession does not result
	 * in creation of new traversal strategies (from the extension point).
	 */
	@Test
	public void test_traversalStrategiesNotShared_sameValidator_207992() {
		TestTraversalStrategy.reset();

		PreferredCustomer customer = SpecialFactory.eINSTANCE.createPreferredCustomer();

		IBatchValidator batchValidator1 = (IBatchValidator) ModelValidationService.getInstance()
				.newValidator(EvaluationMode.BATCH);

		IBatchValidator batchValidator2 = (IBatchValidator) ModelValidationService.getInstance()
				.newValidator(EvaluationMode.BATCH);

		batchValidator1.validate(customer);

		// sequential execution on same thread
		batchValidator2.validate(customer);

		assertTrue(TestTraversalStrategy.wasUsed());
		assertEquals(1, TestTraversalStrategy.getInstanceCount());
	}

	/**
	 * Tests that two different batch validators do result in creation of distinct
	 * traversal strategy instances (from the extension point).
	 */
	@Test
	public void test_traversalStrategiesNotShared_differentValidators_207992() {
		TestTraversalStrategy.reset();

		final PreferredCustomer customer = SpecialFactory.eINSTANCE.createPreferredCustomer();

		final IBatchValidator batchValidator1 = (IBatchValidator) ModelValidationService.getInstance()
				.newValidator(EvaluationMode.BATCH);
		final IBatchValidator batchValidator2 = (IBatchValidator) ModelValidationService.getInstance()
				.newValidator(EvaluationMode.BATCH);

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				batchValidator1.validate(customer);
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				batchValidator2.validate(customer);
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			fail("Test was interrupted");
		}

		assertTrue(TestTraversalStrategy.wasUsed());
		assertEquals(2, TestTraversalStrategy.getInstanceCount());
	}

	/**
	 * Tests that a constraint can put data into the context and get it out again
	 * later.
	 */
	@Test
	public void test_currentConstraintData_232572() {
		class MyContext extends AbstractValidationContext {

			MyContext() {
				super(new GetBatchConstraintsOperation(false));
			}
		}

		MyContext ctx = new MyContext();
		Method method = null;

		// set the current constraint
		try {
			method = AbstractValidationContext.class.getDeclaredMethod("setConstraint", IModelConstraint.class);
			method.setAccessible(true);

			ConstraintDescriptorTest.FixtureElement config = ConstraintDescriptorTest.newFixtureConfig();
			config.putAttribute(XmlConfig.A_ID, "foo.232572");

			method.invoke(ctx, new ModelConstraint(new XmlConstraintDescriptor(config)) {

				@Override
				public IStatus validate(IValidationContext ctx) {
					return Status.OK_STATUS;
				}
			});
		} catch (Exception e) {
			fail("Failed to access setCurrentConstraint() method: " + e.getLocalizedMessage());
		} finally {
			if (method != null) {
				method.setAccessible(false);
			}
		}

		// put some data
		ctx.putCurrentConstraintData(method);

		// get it back
		assertSame(method, ctx.getCurrentConstraintData());
	}

	public static class TestTraversalStrategy implements ITraversalStrategy {
		static int instances = 0;
		static boolean used;

		private final ITraversalStrategy delegate = new ITraversalStrategy.Recursive();

		public TestTraversalStrategy() {
			synchronized (TestTraversalStrategy.class) {
				instances++;
			}
		}

		synchronized static int getInstanceCount() {
			return instances;
		}

		synchronized static void reset() {
			instances = 0;
			used = false;
		}

		synchronized static boolean wasUsed() {
			return used;
		}

		@Override
		public void elementValidated(EObject element, IStatus status) {
			delegate.elementValidated(element, status);
		}

		@Override
		public boolean hasNext() {
			return delegate.hasNext();
		}

		@Override
		public boolean isClientContextChanged() {
			return delegate.isClientContextChanged();
		}

		@Override
		public EObject next() {
			return delegate.next();
		}

		@Override
		public void startTraversal(Collection<? extends EObject> traversalRoots, IProgressMonitor monitor) {
			synchronized (TestTraversalStrategy.class) {
				used = true;
			}

			delegate.startTraversal(traversalRoots, monitor);
		}

	}
}
