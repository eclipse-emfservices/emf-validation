/**
 * Copyright (c) 2003, 2006 IBM Corporation and others.
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

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import ordersystem.OrderSystem;
import ordersystem.OrderSystemFactory;
import ordersystem.special.SpecialFactory;

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

/**
 * A suite of JUnit test cases covering the capabilities of the core
 * constraints framework.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class FrameworkTest extends TestBase {
	/**
	 * Set this system property to "true" when running JUnit if you wish to
	 * start from a fresh, new test document.  This is not normally necessary.
	 */
	public static final String CREATE_NEW_TEST_DOCUMENT_PROPERTY =
		"emf.test.newdocument"; //$NON-NLS-1$
	
	private static OrderSystem orderSystem = null;

	public FrameworkTest(String name) {
		super(name);
	}
	
	/**
	 * Load the static example Order System model (<tt>test.ordersystem</tt>)
	 * on the first invocation of this method.
	 */
	@Override
	protected void setUp() throws Exception {
		if (orderSystem == null) {
			ResourceSet resourceSet = new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();

			java.net.URL file = FileLocator.find(
				Platform.getBundle(PLUGIN_ID),
				new Path("test.ordersystem"), null); //$NON-NLS-1$
			
			URI uri = URI.createURI(file.toExternalForm());
			Resource res = resourceSet.getResource(uri, true);

			orderSystem = (OrderSystem)res.getContents().get(0);

			if (Boolean.getBoolean(CREATE_NEW_TEST_DOCUMENT_PROPERTY)) {
				createTestDocument(res, orderSystem);
			}
		}
	}

	/**
	 * This method creates an example Order System document.
	 * 
	 * @param res the EMF resource to create
	 * @param os the top-level Order System model object
	 * @throws IOException if there is any problem saving the resource
	 */
	private void createTestDocument(Resource res, OrderSystem os)
			throws IOException {
		
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
		Trace.trace(">>> Testing showContents"); //$NON-NLS-1$
		
		showRecursive(orderSystem, 0);
	}

	/**
	 * Tests that the service accesses distinct providers for distinct
	 * namespaces.
	 */
	public void test_providerNameSpaces() {
		Trace.trace(">>> Testing providerNameSpaces"); //$NON-NLS-1$
		
		// this test has two parts
		orderSystemNameSpace();
	}

	/**
	 * Helper for the namespaces test, verifying that a known "marker" test is
	 * found in the Order System namespace.
	 */
	private void orderSystemNameSpace() {
		// find the 'marker' constraint for a known type in the ordersystem
		// namespace
		EObject object = OrderSystemFactory.eINSTANCE.createAddress();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		for (IStatus element : status) {
			IModelConstraint constraint = ((IConstraintStatus)element).getConstraint();
			
			if (constraint.getDescriptor().getId().equals(ID_PREFIX + "ordersystem.marker")) { //$NON-NLS-1$
				return;
			}
		}
		
		fail("ordersystem.marker constraint not found"); //$NON-NLS-1$
	}

	/**
	 * Tests that a certain known set of batch constraints is correctly
	 * retrieved.
	 */
	public void test_batchConstraints() {
		Trace.trace(">>> Testing batchConstraints"); //$NON-NLS-1$
		
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		assertAllConstraintsPresent(
				"batch", //$NON-NLS-1$
				status,
				ID_PREFIX + "order.hasContents", //$NON-NLS-1$
				ID_PREFIX + "order.notFilledBeforePlacement"); //$NON-NLS-1$
	}
	
	/**
	 * Tests that a certain known set of live constraints is correctly
	 * retrieved.
	 */
	public void test_liveConstraints() {
		Trace.trace(">>> Testing liveConstraints"); //$NON-NLS-1$
		
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();
		new XMLResourceImpl().getContents().add(object); // must be in a resource
		Notification event = new TestNotification(object, Notification.SET);

		IStatus[] status = getStatuses(liveValidator.validate(event));

		assertAllConstraintsPresent(
				"live", //$NON-NLS-1$
				status,
				ID_PREFIX + "order.hasName", //$NON-NLS-1$
				ID_PREFIX + "order.hasOwner"); //$NON-NLS-1$
	}

	/**
	 * Tests that in cases of multiple events for the same object, multiple
	 * executions of the same constraint do not occur.
	 */
	public void test_multiLiveConstraints() {
		Trace.trace(">>> Testing multiLiveConstraints"); //$NON-NLS-1$
		
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();
		Notification event = new TestNotification(object, Notification.SET);

		IStatus[] status = getStatuses(liveValidator.validate(
					Collections.nCopies(5, event)));

		Collection<IModelConstraint> evaluatedConstraints =
			new java.util.HashSet<IModelConstraint>();
		
		for (IStatus element : status) {
			IConstraintStatus next = (IConstraintStatus)element;
			
			evaluatedConstraints.add(next.getConstraint());
		}
		
		assertEquals(
				"Some constraint was evaluated more than once:", //$NON-NLS-1$
				evaluatedConstraints.size(),
				status.length);
	}

	/**
	 * Tests that constraints are only instantiated (lazily) when they are
	 * actually evaluated.
	 */
	public void test_lazyConstraintInstantiation() {
		Trace.trace(">>> Testing lazyConstraintInstantiation"); //$NON-NLS-1$
		Trace.trace(""); //$NON-NLS-1$
		
		int currentCount = LazyTestModelConstraint.getInstanceCount();
		
		EObject object = OrderSystemFactory.eINSTANCE.createWarehouse();
		new XMLResourceImpl().getContents().add(object); // must be in a resource
		TestNotification notification = new TestNotification(
				object,
				Notification.REMOVE);
		
		// first, disable the constraint so that it will not be evaluated
		ConstraintRegistry.getInstance()
			.getDescriptor(PLUGIN_ID, "lazy.marker") //$NON-NLS-1$
			.setEnabled(false);
		
		liveValidator.validate(notification);
		
		assertEquals(
				"Constraint prematurely instantiated", //$NON-NLS-1$
				currentCount,
				LazyTestModelConstraint.getInstanceCount());
		
		// now, re-enable the category so that the constraint will be evaluated
		ConstraintRegistry.getInstance()
		.getDescriptor(PLUGIN_ID, "lazy.marker") //$NON-NLS-1$
			.setEnabled(true);
		
		liveValidator.validate(notification);
		
		assertTrue(
				"Constraint not lazily instantiated", //$NON-NLS-1$
				currentCount < LazyTestModelConstraint.getInstanceCount());
	}
	
	/**
	 * Tests that the constraint cache only accesses the constraint providers
	 * once for each discrete context.
	 */
	public void test_constraintCache() {
		Trace.trace(">>> Testing constraintCache"); //$NON-NLS-1$
		
		EObject object = OrderSystemFactory.eINSTANCE.createOrder();
		
		// get the constraints 
		batchValidator.validate(object);

		// get them a second time (should hit the cache)
		batchValidator.validate(object);
		
		assertEquals("Cache was not hit!", //$NON-NLS-1$
				1,
				CachedTestProvider.getInstance().getHitCount(object.eClass()));
	}

	/**
	 * Tests that problems in initializing constraints because of bad XML
	 * content or other static problems are handled gracefully.
	 */
	public void test_constraintXmlErrors() {
		Trace.trace(">>> Testing constraintXmlErrors"); //$NON-NLS-1$
		
		EObject object = OrderSystemFactory.eINSTANCE.createWarehouse();

		IStatus[] status = getStatuses(batchValidator.validate(object));
		
		assertAllConstraintsNotPresent(
				"batch", //$NON-NLS-1$
				status,
				ID_PREFIX + "bad.constraint.xml"); //$NON-NLS-1$
	}

	/**
	 * Tests that run-time problems in evaluating constraints
	 * are handled gracefully.
	 */
	public void test_constraintDynamicErrors() {
		Trace.trace(">>> Testing constraintDynamicErrors"); //$NON-NLS-1$
		
		EObject object = OrderSystemFactory.eINSTANCE.createWarehouse();

		IStatus[] status = getStatuses(batchValidator.validate(object));
		
		String[] ids = new String[] {
				ID_PREFIX + "bad.constraint.disabled.java", //$NON-NLS-1$
				ID_PREFIX + "bad.constraint.disabled.ocl", //$NON-NLS-1$
				ID_PREFIX + "bad.constraint.disabled.bsh", //$NON-NLS-1$
				ID_PREFIX + "bad.constraint.disabled.runtime", //$NON-NLS-1$
			};
		
		for (String id : ids) {
			IStatus nextStatus = getStatus(status, id);
			if (nextStatus != null) {
				assertTrue(nextStatus.matches(IStatus.INFO));
			}
		}
		
		// on a second validation, these constraints must not be evaluated
		status = getStatuses(batchValidator.validate(object));
		
		assertAllConstraintsNotPresent("batch", status, ids); //$NON-NLS-1$
	}

	/**
	 * Tests that problems in initializing providers because of bad XML content
	 * are handled gracefully.
	 */
	public void test_providerXmlErrors() {
		Trace.trace(">>> Testing providerXmlErrors"); //$NON-NLS-1$
		
		EObject object = EcoreFactory.eINSTANCE.createEAnnotation();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		assertNotNull(status);
		
		assertTrue(
				"Provider for ecore namespace prefix was initialized.", //$NON-NLS-1$
				TestBadXmlConfigProvider.getInstance("http://www.eclipse.org/emf/2002/Ecore") == null); //$NON-NLS-1$
	}

	/**
	 * Tests that run-time problems in invoking providers
	 * are handled gracefully.
	 */
	public void test_providerDynamicErrors() {
		Trace.trace(">>> Testing providerDynamicErrors"); //$NON-NLS-1$
		
		EObject object = OrderSystemFactory.eINSTANCE.createOrderSystem();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		for (IStatus element : status) {
			IModelConstraint constraint = ((IConstraintStatus)element).getConstraint();
			assertFalse("Got constraints from ordersystem namespace prefix provider.", constraint.getDescriptor().getId().startsWith(ID_PREFIX + "ordersystem")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		assertTrue(
				"Provider for ordersystem namespace prefix was not initialized.", //$NON-NLS-1$
				TestBadXmlConfigProvider.getInstance("http:///ordersystem.ecore") != null); //$NON-NLS-1$
	}

	/**
	 * Tests the batch-mode filters at the constraint provider level.
	 */
	public void test_providerBatchFilter() {
		Trace.trace(">>> Testing providerBatchFilter"); //$NON-NLS-1$
		
		// first, check that the batch provider filter correctly includes the
		//   InventoryItem type (the constraint otherwise would apply to all
		//   types) and that the live and feature filters exclude their batch
		//   mode constraints
		
		EObject object = OrderSystemFactory.eINSTANCE.createInventoryItem();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		assertAllConstraintsPresent(
				"batch", //$NON-NLS-1$
				status,
				ID_PREFIX + "providertarget.batch1"); //$NON-NLS-1$

		assertAllConstraintsNotPresent(
				"batch", //$NON-NLS-1$
				status,
				ID_PREFIX + "providertarget.batch2"); //$NON-NLS-1$
		
		// also check that the provider filter correctly excludes the
		//   Order type (the constraint otherwise would apply to all types,
		//   including Order)
		
		object = OrderSystemFactory.eINSTANCE.createOrder();

		status = getStatuses(batchValidator.validate(object));

		assertAllConstraintsNotPresent(
				"batch", //$NON-NLS-1$
				status,
				ID_PREFIX + "providertarget.batch1"); //$NON-NLS-1$
	}
	
	/**
	 * Tests the live-mode filters at the constraint provider level.
	 */
	public void test_providerLiveFilter() {
		Trace.trace(">>> Testing providerLiveFilter"); //$NON-NLS-1$
		
		// first, check that the live provider filter correctly includes the
		//   InventoryItem type (the constraint otherwise would apply to all
		//   types) and that the batch and feature filters exclude their live
		//   mode constraints
		
		EObject object = OrderSystemFactory.eINSTANCE.createInventoryItem();
		new XMLResourceImpl().getContents().add(object); // must be in a resource
		Notification event = new TestNotification(object, Notification.SET);

		IStatus[] status = getStatuses(liveValidator.validate(event));

		assertAllConstraintsPresent(
				"live", //$NON-NLS-1$
				status,
				ID_PREFIX + "providertarget.live2"); //$NON-NLS-1$

		assertAllConstraintsNotPresent(
				"live", //$NON-NLS-1$
				status,
				ID_PREFIX + "providertarget.live1"); //$NON-NLS-1$
		
		// also check that the provider filter correctly excludes the
		//   Order type (the constraint otherwise would apply to all types,
		//   including Order)
		
		object = OrderSystemFactory.eINSTANCE.createOrder();
		event = new TestNotification(object, Notification.SET);

		status = getStatuses(liveValidator.validate(event));
		
		assertAllConstraintsNotPresent(
				"live", //$NON-NLS-1$
				status,
				ID_PREFIX + "providertarget.live2"); //$NON-NLS-1$
		
		// finally, check that the provider filter correctly excludes an
		//   event type that is not "Set"
		
		object = OrderSystemFactory.eINSTANCE.createInventoryItem();
		event = new TestNotification(object, Notification.ADD);

		status = getStatuses(liveValidator.validate(event));
		
		assertAllConstraintsNotPresent(
				"live", //$NON-NLS-1$
				status,
				ID_PREFIX + "providertarget.live2"); //$NON-NLS-1$
	}
	
	/**
	 * Tests the support for multiple EPackages in the same provider.
	 */
	public void test_multiPackagesPerProvider() {
		Trace.trace(">>> Testing multiPackagesPerProvider"); //$NON-NLS-1$
		
		EObject object = SpecialFactory.eINSTANCE.createPreferredCustomer();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		assertAllConstraintsPresent(
				"batch", //$NON-NLS-1$
				status,
				ID_PREFIX + "multipackage.test"); //$NON-NLS-1$
	}
	
	/**
	 * Tests the support for inheritance of constraints by EClasses in a
	 * different EPackage.
	 */
	public void test_subclassInOtherPackage() {
		Trace.trace(">>> Testing subclassInOtherPackage"); //$NON-NLS-1$
		
		EObject object = SpecialFactory.eINSTANCE.createLimitedEditionProduct();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		assertAllConstraintsPresent(
				"batch", //$NON-NLS-1$
				status,
				ID_PREFIX + "inheritance.test"); //$NON-NLS-1$
	}
	
	/**
	 * Tests the support for disambiguating like-named classes in different
	 * packages by a qualified name.
	 */
	public void test_qualifiedName() {
		Trace.trace(">>> Testing qualifiedName"); //$NON-NLS-1$
		
		EObject object = SpecialFactory.eINSTANCE.createLimitedEditionProduct();

		IStatus[] status = getStatuses(batchValidator.validate(object));

		assertAllConstraintsPresent(
				"batch", //$NON-NLS-1$
				status,
				ID_PREFIX + "qualifiedName.test"); //$NON-NLS-1$
	}
}
