/**
 * <copyright>
 *
 * Copyright (c) 2003, 2007 IBM Corporation and others.
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

package org.eclipse.emf.validation.internal.xml.tests;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import ordersystem.OrderSystemFactory;
import ordersystem.OrderSystemPackage;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.ITraversalStrategy;
import org.eclipse.emf.validation.service.IValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.emf.validation.tests.TestNotification;
import org.eclipse.emf.validation.tests.TestPlugin;
import org.eclipse.emf.validation.xml.XmlConstraintProvider;

/**
 * Tests the XML Constraint Provider class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class XmlConstraintProviderTest extends TestBase {
	/**
	 * The instance of the fixture class that is created by the validation
	 * framework when it loads the XML.
	 */
	private static Fixture fixture;
	
	static {
		// validate an object for which the test plug-in does not define any
		//    constraints at all, to force the providers to be loaded (so
		//    that the static "fixture" instance will be initialized)
		IValidator validator = ModelValidationService.getInstance().newValidator(
				EvaluationMode.BATCH);
		((IBatchValidator)validator).setTraversalStrategy(
			new ITraversalStrategy.Flat());
		validator.validate(OrderSystemFactory.eINSTANCE.createInventoryItem());
	}
	
	public static class Fixture extends XmlConstraintProvider {
		public Fixture() {
			super();
			fixture = this;  // record the instance of this class
		}
		
		private boolean initializationDataWasSet = false;
		
		// gives the outer class access to the protected superclass method
		protected List getConstraints() {
			return super.getConstraints();
		}
		
		// extends the inherited method to record the fact of this being set
		public void setInitializationData(IConfigurationElement config,
				String propertyName, Object data) throws CoreException {
			super.setInitializationData(config, propertyName, data);
			
			this.initializationDataWasSet = true;
		}
		
		boolean wasInitializationDataSet() {
			return initializationDataWasSet;
		}
	}
	
	public XmlConstraintProviderTest(String name) {
		super(name);
	}

	public void test_setInitializationData() {
		assertTrue(fixture.wasInitializationDataSet());
	}
	
	public void test_getConstraints() {
		Collection result = fixture.getConstraints();
		
		assertAllConstraintsPresent(
				"various", //$NON-NLS-1$
				result,
				Arrays.asList(new Object[] {
						ID_PREFIX + "product.batch1", //$NON-NLS-1$
						ID_PREFIX + "product.batch2", //$NON-NLS-1$
						ID_PREFIX + "product.live1", //$NON-NLS-1$
						ID_PREFIX + "product.live2", //$NON-NLS-1$
					}));
		
		// check that all of the constraints are proxies
		for (Iterator iter = result.iterator(); iter.hasNext();) {
			IModelConstraint next = (IModelConstraint)iter.next();
			
			assertTrue(next.getClass().getName().endsWith("$ConstraintProxy")); //$NON-NLS-1$
		}
	}

	public void test_getBatchConstraints() {
		EObject object = OrderSystemFactory.eINSTANCE.createProduct();
		
		Collection result = new java.util.HashSet();
		
		fixture.getBatchConstraints(object, result);
		
		assertAllConstraintsPresent(
				"batch", //$NON-NLS-1$
				result,
				Arrays.asList(new Object[] {
						ID_PREFIX + "product.batch1", //$NON-NLS-1$
						ID_PREFIX + "product.batch2", //$NON-NLS-1$
					}));
	}

	public void test_getLiveConstraints() {
		EObject object = OrderSystemFactory.eINSTANCE.createProduct();
		
		Collection result = new java.util.HashSet();
		
		fixture.getLiveConstraints(
				new TestNotification(object, Notification.SET),
				result);
		
		assertAllConstraintsPresent(
				"live", //$NON-NLS-1$
				result,
				Arrays.asList(new Object[] {
						ID_PREFIX + "product.live1", //$NON-NLS-1$
					}));
		
		assertAllConstraintsNotPresent(
				"live", //$NON-NLS-1$
				result,
				Arrays.asList(new Object[] {
						ID_PREFIX + "product.live2", //$NON-NLS-1$
					}));
	}

	public void test_getLiveConstraintsForFeature() {
		EObject object = OrderSystemFactory.eINSTANCE.createProduct();
		
		Collection result = new java.util.HashSet();
		
		fixture.getLiveConstraints(
				new TestNotification(
						object,
						Notification.SET,
						OrderSystemPackage.PRODUCT__SKU,
						null,
						"12345"), //$NON-NLS-1$
				result);

		// "live1" does not specify any features, so it applies to all
		assertAllConstraintsPresent(
				"live", //$NON-NLS-1$
				result,
				Arrays.asList(new Object[] {
						ID_PREFIX + "product.live1", //$NON-NLS-1$
						ID_PREFIX + "product.live2", //$NON-NLS-1$
					}));
	}
    
    public void test_duplicateConstraintsLogged_207988() {
        List statuses = TestPlugin.getLogCapture().getLogs(
            EMFModelValidationStatusCodes.PROVIDER_DUPLICATE_CONSTRAINT);
        assertFalse("Duplicate constraint not logged", statuses.isEmpty()); //$NON-NLS-1$
    }
}
