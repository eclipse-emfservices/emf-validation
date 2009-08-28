/******************************************************************************
 * Copyright (c) 2009 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API and implementation 
 ****************************************************************************/
package org.eclipse.emf.validation.internal.model.tests;

import java.util.Set;

import junit.framework.TestCase;
import ordersystem.Account;
import ordersystem.Address;
import ordersystem.OrderSystemFactory;
import ordersystem.OrderSystemPackage;

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
public class ModeledConstraintsTest extends TestCase {
	
	private static boolean setup = false;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		if ( !EMFPlugin.IS_ECLIPSE_RUNNING && !setup ) {
			setup = true;
			EPackage.Registry.INSTANCE.put(ValidationPackage.eNS_URI, ValidationPackage.eINSTANCE);
			EPackage.Registry.INSTANCE.put(OrderSystemPackage.eNS_URI, OrderSystemPackage.eINSTANCE);
			
			Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl()); //$NON-NLS-1$
			ModeledConstraintsLoader.getInstance().loadConstraintBundles(null, 
					URI.createURI(getClass().getClassLoader().getResource("order.validation").toString()), //$NON-NLS-1$
					null,
					ValidationTestsPlugin.INSTANCE); //$NON-NLS-1
		}
	}

	public void testCategory() {
		assertNotNull(CategoryManager.getInstance().findCategory("modeled.test")); //$NON-NLS-1$
		assertNotNull(CategoryManager.getInstance().findCategory("modeled.test/modeled.test.sub")); //$NON-NLS-1$
	}
	
	public void testConstraintDescriptor() {
		Category cat = CategoryManager.getInstance().findCategory("modeled.test"); //$NON-NLS-1$
		assertNotNull(cat);
		
		Set<IConstraintDescriptor> constraints = cat.getConstraints();
		assertEquals(5, constraints.size());
	}
	
	public void testValidation() {
		Account acc = OrderSystemFactory.eINSTANCE.createAccount();
		acc.setAccountNumber("12345A"); //$NON-NLS-1$
		
		IValidator<EObject> val = ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);
		
		IStatus result = val.validate(acc);
		
		assertFalse(result.isOK());
	}
	
	public void testIrrelevantTarget() {
		Address	add = OrderSystemFactory.eINSTANCE.createAddress();
	
		IValidator<EObject> val = ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);
		
		IStatus result = val.validate(add);
		
		assertTrue(result.isOK());
	}
	
	public void testInternationalization() {
		Category cat = CategoryManager.getInstance().findCategory("modeled.test"); //$NON-NLS-1$
		assertNotNull(cat);
		
		Set<IConstraintDescriptor> constraints = cat.getConstraints();

		for ( IConstraintDescriptor desc : constraints) {
			
			String id = XmlConstraintDescriptor.normalizedId("org.eclipse.emf.validation.tests", "modeled4");  //$NON-NLS-1$//$NON-NLS-2$
			if ( id.equals(desc.getId())) {
				assertEquals("Constraint failed", desc.getMessagePattern()); //$NON-NLS-1$
				assertEquals("Test constraint for modeled i18n", desc.getName()); //$NON-NLS-1$
				assertEquals("Tests message bundles for constraints", desc.getDescription()); //$NON-NLS-1$
			}
		}
	}

	
	
	public static class AccountClientSelector implements IClientSelector {

		public boolean selects(Object object) {
			return object instanceof Account;
		}
		
	}
	
	public static class AccountNumberTestConstraint extends AbstractModelConstraint {

		@Override
		public IStatus validate(IValidationContext ctx) {
			Account act = (Account) ctx.getTarget();
			
			if ( "12345C".equals(act.getAccountNumber())) { //$NON-NLS-1$
				return Status.OK_STATUS;
			}
			
			IStatus fail = new Status(IStatus.ERROR, "org.eclipse.emf.validation.tests", "failed"); //$NON-NLS-1$ //$NON-NLS-2$
			
			return fail;
		}
	}
}
