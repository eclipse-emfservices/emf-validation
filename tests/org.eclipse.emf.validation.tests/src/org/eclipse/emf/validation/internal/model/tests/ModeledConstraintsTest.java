/******************************************************************************
 * Copyright (c) 2009, 2026 SAP AG and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    SAP AG - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.internal.model.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage;
import org.eclipse.emf.validation.internal.standalone.ValidationTestsPlugin;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.CategoryManager;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IClientSelector;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.service.ModeledConstraintsLoader;
import org.junit.BeforeClass;
import org.junit.Test;

import ordersystem.Account;
import ordersystem.Address;
import ordersystem.OrderSystemFactory;
import ordersystem.OrderSystemPackage;

/**
 * <p>
 * Testcases for the modeled constraints.
 * </p>
 *
 * <p>
 * This test case can be executed in the standalone mode.
 * </p>
 *
 * @author Boris Gruschko
 *
 */
public class ModeledConstraintsTest {

	@BeforeClass
	public static void setUp() {
		if (!EMFPlugin.IS_ECLIPSE_RUNNING) {
			EPackage.Registry.INSTANCE.put(ValidationPackage.eNS_URI, ValidationPackage.eINSTANCE);
			EPackage.Registry.INSTANCE.put(OrderSystemPackage.eNS_URI, OrderSystemPackage.eINSTANCE);

			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
			ModeledConstraintsLoader.getInstance().loadConstraintBundles(null,
					URI.createURI(
							ModeledConstraintsTest.class.getClassLoader().getResource("order.validation").toString()),
					null, ValidationTestsPlugin.INSTANCE);
		}
		ModelValidationService.getInstance(); // Ensure everything is initialized
	}

	@Test
	public void testCategory() {
		assertNotNull(CategoryManager.getInstance().findCategory("modeled.test"));
		assertNotNull(CategoryManager.getInstance().findCategory("modeled.test/modeled.test.sub"));
	}

	@Test
	public void testConstraintDescriptor() {
		Category cat = CategoryManager.getInstance().findCategory("modeled.test");
		assertNotNull(cat);

		Set<IConstraintDescriptor> constraints = cat.getConstraints();
		assertEquals(5, constraints.size());
	}

	@Test
	public void testValidation() {
		Account acc = OrderSystemFactory.eINSTANCE.createAccount();
		acc.setAccountNumber("12345A");

		IValidator<EObject> val = ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);

		IStatus result = val.validate(acc);

		assertFalse(result.isOK());
	}

	@Test
	public void testIrrelevantTarget() {
		Address add = OrderSystemFactory.eINSTANCE.createAddress();

		IValidator<EObject> val = ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);

		IStatus result = val.validate(add);

		assertTrue(result.isOK());
	}

	@Test
	public void testInternationalization() {
		Category cat = CategoryManager.getInstance().findCategory("modeled.test");
		assertNotNull(cat);

		Set<IConstraintDescriptor> constraints = cat.getConstraints();

		for (IConstraintDescriptor desc : constraints) {

			String id = XmlConstraintDescriptor.normalizedId("org.eclipse.emf.validation.tests", "modeled4");
			if (id.equals(desc.getId())) {
				assertEquals("Constraint failed", desc.getMessagePattern());
				assertEquals("Test constraint for modeled i18n", desc.getName());
				assertEquals("Tests message bundles for constraints", desc.getDescription());
			}
		}
	}

	public static class AccountClientSelector implements IClientSelector {

		@Override
		public boolean selects(Object object) {
			return object instanceof Account;
		}

	}

	public static class AccountNumberTestConstraint extends AbstractModelConstraint {

		@Override
		public IStatus validate(IValidationContext ctx) {
			Account act = (Account) ctx.getTarget();

			if ("12345C".equals(act.getAccountNumber())) {
				return Status.OK_STATUS;
			}

			return new Status(IStatus.ERROR, "org.eclipse.emf.validation.tests", "failed");
		}
	}
}
