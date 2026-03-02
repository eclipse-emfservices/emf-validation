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
package org.eclipse.emf.validation.internal.xml.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.ITraversalStrategy;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.tests.Assertions;
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.emf.validation.tests.TestNotification;
import org.eclipse.emf.validation.tests.TestPlugin;
import org.eclipse.emf.validation.tests.TestUtils;
import org.eclipse.emf.validation.xml.XmlConstraintProvider;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import ordersystem.OrderSystemFactory;
import ordersystem.OrderSystemPackage;

/**
 * Tests the XML Constraint Provider class.
 *
 * @author Christian W. Damus (cdamus)
 */
//Must be run first, before any other test which initializes ModelValidationService
@Order(1)
public class XmlConstraintProviderTest extends TestBase {
	/**
	 * The instance of the fixture class that is created by the validation framework
	 * when it loads the XML.
	 */
	private static Fixture fixture;

	static {
		// validate an object for which the test plug-in does not define any
		// constraints at all, to force the providers to be loaded (so
		// that the static "fixture" instance will be initialized)
		IBatchValidator validator = ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);
		validator.setTraversalStrategy(new ITraversalStrategy.Flat());
		validator.validate(OrderSystemFactory.eINSTANCE.createInventoryItem());
	}

	public static class Fixture extends XmlConstraintProvider {
		public Fixture() {
			super();
			fixture = this; // record the instance of this class
		}

		private boolean initializationDataWasSet = false;

		// gives the outer class access to the protected superclass method
		@Override
		protected List<IModelConstraint> getConstraints() {
			return super.getConstraints();
		}

		// extends the inherited method to record the fact of this being set
		@Override
		public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
				throws CoreException {
			super.setInitializationData(config, propertyName, data);

			this.initializationDataWasSet = true;
		}

		boolean wasInitializationDataSet() {
			return initializationDataWasSet;
		}
	}

	@Test
	public void test_setInitializationData() {
		assertTrue(fixture.wasInitializationDataSet());
	}

	@Test
	public void test_getConstraints() {
		Collection<IModelConstraint> result = fixture.getConstraints();

		Assertions.assertAllConstraintsPresent("various", 
				result, TestUtils.ID_PREFIX + "product.batch1", 
				TestUtils.ID_PREFIX + "product.batch2", 
				TestUtils.ID_PREFIX + "product.live1", 
				TestUtils.ID_PREFIX + "product.live2"); 

		// check that all of the constraints are proxies
		for (IModelConstraint next : result) {
			System.out.println("test_getConstraints: " + next.getClass().getName());
			assertTrue(next.getClass().getName().endsWith("$ConstraintProxy")); 
		}
	}

	@Test
	public void test_getBatchConstraints() {
		EObject object = OrderSystemFactory.eINSTANCE.createProduct();

		Collection<IModelConstraint> result = new java.util.HashSet<>();

		fixture.getBatchConstraints(object, result);

		Assertions.assertAllConstraintsPresent("batch", 
				result, TestUtils.ID_PREFIX + "product.batch1", 
				TestUtils.ID_PREFIX + "product.batch2"); 
	}

	@Test
	public void test_getLiveConstraints() {
		EObject object = OrderSystemFactory.eINSTANCE.createProduct();

		Collection<IModelConstraint> result = new java.util.HashSet<>();

		fixture.getLiveConstraints(new TestNotification(object, Notification.SET), result);

		Assertions.assertAllConstraintsPresent("live", 
				result, TestUtils.ID_PREFIX + "product.live1"); 

		Assertions.assertAllConstraintsNotPresent("live", 
				result, TestUtils.ID_PREFIX + "product.live2"); 
	}

	@Test
	public void test_getLiveConstraintsForFeature() {
		EObject object = OrderSystemFactory.eINSTANCE.createProduct();

		Collection<IModelConstraint> result = new java.util.HashSet<>();

		fixture.getLiveConstraints(
				new TestNotification(object, Notification.SET, OrderSystemPackage.PRODUCT__SKU, null, "12345"), 
				result);

		// "live1" does not specify any features, so it applies to all
		Assertions.assertAllConstraintsPresent("live", 
				result, TestUtils.ID_PREFIX + "product.live1", 
				TestUtils.ID_PREFIX + "product.live2"); 
	}

	@Test
	public void test_duplicateConstraintsLogged_207988() {
		List<IStatus> statuses = TestPlugin.getLogCapture()
				.getLogs(EMFModelValidationStatusCodes.PROVIDER_DUPLICATE_CONSTRAINT);
		assertFalse(statuses.isEmpty(), "Duplicate constraint not logged"); 
	}

	@Test
	public void test_getConstraintDisabledByDefault() {
		final String TEST_INACTIVE_CONSTRAINT_ID = TestUtils.ID_PREFIX + "defaultTestNotActiveConstraint"; 
		final String TEST_ACTIVE_CONSTRAINT_ID = TestUtils.ID_PREFIX + "defaultTestActiveConstraint"; 
		final String TEST_NOINFO_CONSTRAINT_ID = TestUtils.ID_PREFIX + "defaultTestConstraintWithoutDefaultEnablementInformation"; 

		// Test the inactive constraint
		IConstraintDescriptor desc = ConstraintRegistry.getInstance().getDescriptor(TEST_INACTIVE_CONSTRAINT_ID);
		assertNotNull(desc);
		assertFalse(desc.isEnabled());

		// Test the active constraint
		desc = ConstraintRegistry.getInstance().getDescriptor(TEST_ACTIVE_CONSTRAINT_ID);
		assertNotNull(desc);
		assertTrue(desc.isEnabled());

		// Test the constraint without the default information, so we don't break
		// anything
		desc = ConstraintRegistry.getInstance().getDescriptor(TEST_NOINFO_CONSTRAINT_ID);
		assertNotNull(desc);
		assertTrue(desc.isEnabled());
	}
}
