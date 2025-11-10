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
package org.eclipse.emf.validation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ordersystem.OrderSystem;
import ordersystem.OrderSystemFactory;
import ordersystem.special.SpecialFactory;

/**
 * A suite of JUnit test cases covering the capabilities of the core constraints
 * framework.
 *
 * @author Christian W. Damus (cdamus)
 */
public class FrameworkTest extends TestBase {
	/**
	 * Set this system property to "true" when running JUnit if you wish to start
	 * from a fresh, new test document. This is not normally necessary.
	 */
	public static final String CREATE_NEW_TEST_DOCUMENT_PROPERTY = "emf.test.newdocument";

	private static OrderSystem orderSystem = null;

	@BeforeClass
	public static void initTestContext() {
		org.eclipse.emf.validation.tests.AllTests.executingUnitTests = true;
	}

	@AfterClass
	public static void resetTestContext() {
		org.eclipse.emf.validation.tests.AllTests.executingUnitTests = false;
	}

	/**
	 * Load the static example Order System model (<tt>test.ordersystem</tt>) on the
	 * first invocation of this method.
	 * 
	 * @throws IOException
	 */
	@Before
	public void setUp() throws IOException {
		if (orderSystem == null) {
			ResourceSet resourceSet = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();

			java.net.URL file = FileLocator.find(Platform.getBundle(PLUGIN_ID), new Path("test.ordersystem"), null);

			URI uri = URI.createURI(file.toExternalForm());
			Resource res = resourceSet.getResource(uri, true);

			orderSystem = (OrderSystem) res.getContents().get(0);

			if (Boolean.getBoolean(CREATE_NEW_TEST_DOCUMENT_PROPERTY)) {
				createTestDocument(res, orderSystem);
			}
		}
	}

	/**
	 * This method creates an example Order System document.
	 *
	 * @param res the EMF resource to create
	 * @param os  the top-level Order System model object
	 * @throws IOException if there is any problem saving the resource
	 */
	private void createTestDocument(Resource res, OrderSystem os) throws IOException {
		os.getCustomer().clear();
		os.getWarehouse().clear();
		os.getProduct().clear();
		Example1.create(os);
		res.save(Collections.EMPTY_MAP);
	}

	/**
	 * This "test" just dumps the contents of the example model file to stdout.
	 * Remove the 'zz' from the method name to enable this test
	 */
	public void zztest_showContents() {
		Trace.trace(">>> Testing showContents");

		showRecursive(orderSystem, 0);
	}

	/**
	 * Tests that the service accesses distinct providers for distinct namespaces.
	 */
	@Test
	public void test_providerNameSpaces() {
		Trace.trace(">>> Testing providerNameSpaces");

		// this test has two parts
		orderSystemNameSpace();
	}

	/**
	 * Helper for the namespaces test, verifying that a known "marker" test is found
	 * in the Order System namespace.
	 */
	private void orderSystemNameSpace() {
		// find the 'marker' constraint for a known type in the ordersystem
		// namespace
		EObject object = OrderSystemFactory.eINSTANCE.createAddress();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		for (IStatus element : status) {
			IModelConstraint constraint = ((IConstraintStatus) element).getConstraint();

			if (constraint.getDescriptor().getId().equals(ID_PREFIX + "ordersystem.marker")) {
				return;
			}
		}

		fail("ordersystem.marker constraint not found");
	}

	/**
	 * Tests that a certain known set of batch constraints is correctly retrieved.
	 */
	@Test
	public void test_batchConstraints() {
		Trace.trace(">>> Testing batchConstraints");

		EObject object = OrderSystemFactory.eINSTANCE.createOrder();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		Assertions.assertAllConstraintsPresent("batch", status, ID_PREFIX + "order.hasContents",
				ID_PREFIX + "order.notFilledBeforePlacement");
	}

	/**
	 * Tests that a certain known set of live constraints is correctly retrieved.
	 */
	@Test
	public void test_liveConstraints() {
		Trace.trace(">>> Testing liveConstraints");

		EObject object = OrderSystemFactory.eINSTANCE.createOrder();
		new XMLResourceImpl().getContents().add(object); // must be in a resource
		Notification event = new TestNotification(object, Notification.SET);

		IStatus[] status = getStatuses(liveValidator.validate(event));

		Assertions.assertAllConstraintsPresent("live", status, ID_PREFIX + "order.hasName",
				ID_PREFIX + "order.hasOwner");
	}

	/**
	 * Tests that in cases of multiple events for the same object, multiple
	 * executions of the same constraint do not occur.
	 */
	@Test
	public void test_multiLiveConstraints() {
		Trace.trace(">>> Testing multiLiveConstraints");

		EObject object = OrderSystemFactory.eINSTANCE.createOrder();
		Notification event = new TestNotification(object, Notification.SET);

		IStatus[] status = getStatuses(liveValidator.validate(Collections.nCopies(5, event)));

		Collection<IModelConstraint> evaluatedConstraints = new java.util.HashSet<>();

		for (IStatus element : status) {
			IConstraintStatus next = (IConstraintStatus) element;

			evaluatedConstraints.add(next.getConstraint());
		}

		assertEquals("Some constraint was evaluated more than once:", evaluatedConstraints.size(), status.length);
	}

	/**
	 * Tests that constraints are only instantiated (lazily) when they are actually
	 * evaluated.
	 */
	@Test
	public void test_lazyConstraintInstantiation() {
		Trace.trace(">>> Testing lazyConstraintInstantiation");
		Trace.trace("");

		int currentCount = LazyTestModelConstraint.getInstanceCount();

		EObject object = OrderSystemFactory.eINSTANCE.createWarehouse();
		new XMLResourceImpl().getContents().add(object); // must be in a resource
		TestNotification notification = new TestNotification(object, Notification.REMOVE);

		// first, disable the constraint so that it will not be evaluated
		ConstraintRegistry.getInstance().getDescriptor(PLUGIN_ID, "lazy.marker").setEnabled(false);

		liveValidator.validate(notification);

		assertEquals("Constraint prematurely instantiated", currentCount, LazyTestModelConstraint.getInstanceCount());

		// now, re-enable the category so that the constraint will be evaluated
		ConstraintRegistry.getInstance().getDescriptor(PLUGIN_ID, "lazy.marker").setEnabled(true);

		liveValidator.validate(notification);

		assertTrue("Constraint not lazily instantiated", currentCount < LazyTestModelConstraint.getInstanceCount());
	}

	/**
	 * Tests that the constraint cache only accesses the constraint providers once
	 * for each discrete context.
	 */
	@Test
	public void test_constraintCache() {
		Trace.trace(">>> Testing constraintCache");

		EObject object = OrderSystemFactory.eINSTANCE.createOrder();

		// get the constraints
		batchValidator.validate(object);

		// get them a second time (should hit the cache)
		batchValidator.validate(object);

		assertEquals("Cache was not hit!", 1, CachedTestProvider.getInstance().getHitCount(object.eClass()));
	}

	/**
	 * Tests that problems in initializing constraints because of bad XML content or
	 * other static problems are handled gracefully.
	 */
	@Test
	public void test_constraintXmlErrors() {
		Trace.trace(">>> Testing constraintXmlErrors");

		EObject object = OrderSystemFactory.eINSTANCE.createWarehouse();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		Assertions.assertAllConstraintsNotPresent("batch", status, ID_PREFIX + "bad.constraint.xml");
	}

	/**
	 * Tests that run-time problems in evaluating constraints are handled
	 * gracefully.
	 */
	@Test
	public void test_constraintDynamicErrors() {
		Trace.trace(">>> Testing constraintDynamicErrors");

		EObject object = OrderSystemFactory.eINSTANCE.createWarehouse();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		String[] ids = new String[] { ID_PREFIX + "bad.constraint.disabled.java",
				ID_PREFIX + "bad.constraint.disabled.ocl", ID_PREFIX + "bad.constraint.disabled.bsh",
				ID_PREFIX + "bad.constraint.disabled.runtime", };

		for (String id : ids) {
			IStatus nextStatus = getStatus(status, id);
			if (nextStatus != null) {
				assertTrue(nextStatus.matches(IStatus.INFO));
			}
		}

		// on a second validation, these constraints must not be evaluated
		status = getStatuses(batchValidator.validate(object));

		Assertions.assertAllConstraintsNotPresent("batch", status, ids);
	}

	/**
	 * Tests that problems in initializing providers because of bad XML content are
	 * handled gracefully.
	 */
	@Test
	public void test_providerXmlErrors() {
		Trace.trace(">>> Testing providerXmlErrors");

		EObject object = EcoreFactory.eINSTANCE.createEAnnotation();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		assertNotNull(status);

		assertTrue("Provider for ecore namespace prefix was initialized.",
				TestBadXmlConfigProvider.getInstance("http://www.eclipse.org/emf/2002/Ecore") == null);
	}

	/**
	 * Tests that run-time problems in invoking providers are handled gracefully.
	 */
	@Test
	public void test_providerDynamicErrors() {
		Trace.trace(">>> Testing providerDynamicErrors");

		EObject object = OrderSystemFactory.eINSTANCE.createOrderSystem();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		for (IStatus element : status) {
			IModelConstraint constraint = ((IConstraintStatus) element).getConstraint();
			assertFalse("Got constraints from ordersystem namespace prefix provider.",
					constraint.getDescriptor().getId().startsWith(ID_PREFIX + "ordersystem"));
		}

		assertTrue("Provider for ordersystem namespace prefix was not initialized.",
				TestBadXmlConfigProvider.getInstance("http:///ordersystem.ecore") != null);
	}

	/**
	 * Tests the batch-mode filters at the constraint provider level.
	 */
	@Test
	public void test_providerBatchFilter() {
		Trace.trace(">>> Testing providerBatchFilter");

		// first, check that the batch provider filter correctly includes the
		// InventoryItem type (the constraint otherwise would apply to all
		// types) and that the live and feature filters exclude their batch
		// mode constraints

		EObject object = OrderSystemFactory.eINSTANCE.createInventoryItem();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		Assertions.assertAllConstraintsPresent("batch", status, ID_PREFIX + "providertarget.batch1");

		Assertions.assertAllConstraintsNotPresent("batch", status, ID_PREFIX + "providertarget.batch2");

		// also check that the provider filter correctly excludes the
		// Order type (the constraint otherwise would apply to all types,
		// including Order)

		object = OrderSystemFactory.eINSTANCE.createOrder();

		status = getStatuses(batchValidator.validate(object));

		Assertions.assertAllConstraintsNotPresent("batch", status, ID_PREFIX + "providertarget.batch1");
	}

	/**
	 * Tests the live-mode filters at the constraint provider level.
	 */
	@Test
	public void test_providerLiveFilter() {
		Trace.trace(">>> Testing providerLiveFilter");

		// first, check that the live provider filter correctly includes the
		// InventoryItem type (the constraint otherwise would apply to all
		// types) and that the batch and feature filters exclude their live
		// mode constraints

		EObject object = OrderSystemFactory.eINSTANCE.createInventoryItem();
		new XMLResourceImpl().getContents().add(object); // must be in a resource
		Notification event = new TestNotification(object, Notification.SET);

		IStatus[] status = getStatuses(liveValidator.validate(event));

		Assertions.assertAllConstraintsPresent("live", status, ID_PREFIX + "providertarget.live2");

		Assertions.assertAllConstraintsNotPresent("live", status, ID_PREFIX + "providertarget.live1");

		// also check that the provider filter correctly excludes the
		// Order type (the constraint otherwise would apply to all types,
		// including Order)

		object = OrderSystemFactory.eINSTANCE.createOrder();
		event = new TestNotification(object, Notification.SET);

		status = getStatuses(liveValidator.validate(event));

		Assertions.assertAllConstraintsNotPresent("live", status, ID_PREFIX + "providertarget.live2");

		// finally, check that the provider filter correctly excludes an
		// event type that is not "Set"

		object = OrderSystemFactory.eINSTANCE.createInventoryItem();
		event = new TestNotification(object, Notification.ADD);

		status = getStatuses(liveValidator.validate(event));

		Assertions.assertAllConstraintsNotPresent("live", status, ID_PREFIX + "providertarget.live2");
	}

	/**
	 * Tests the support for multiple EPackages in the same provider.
	 */
	@Test
	public void test_multiPackagesPerProvider() {
		Trace.trace(">>> Testing multiPackagesPerProvider");

		EObject object = SpecialFactory.eINSTANCE.createPreferredCustomer();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		Assertions.assertAllConstraintsPresent("batch", status, ID_PREFIX + "multipackage.test");
	}

	/**
	 * Tests the support for inheritance of constraints by EClasses in a different
	 * EPackage.
	 */
	@Test
	public void test_subclassInOtherPackage() {
		Trace.trace(">>> Testing subclassInOtherPackage");

		EObject object = SpecialFactory.eINSTANCE.createLimitedEditionProduct();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		Assertions.assertAllConstraintsPresent("batch", status, ID_PREFIX + "inheritance.test");
	}

	/**
	 * Tests the support for disambiguating like-named classes in different packages
	 * by a qualified name.
	 */
	@Test
	public void test_qualifiedName() {
		Trace.trace(">>> Testing qualifiedName");

		EObject object = SpecialFactory.eINSTANCE.createLimitedEditionProduct();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		Assertions.assertAllConstraintsPresent("batch", status, ID_PREFIX + "qualifiedName.test");
	}
}
