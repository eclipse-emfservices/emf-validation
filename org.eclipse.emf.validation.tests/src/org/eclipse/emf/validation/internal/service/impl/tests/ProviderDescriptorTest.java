/**
 * <copyright>
 *
 * Copyright (c) 2004, 2006 IBM Corporation and others.
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

package org.eclipse.emf.validation.internal.service.impl.tests;

import ordersystem.OrderSystemFactory;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.internal.service.GetBatchConstraintsOperation;
import org.eclipse.emf.validation.internal.service.GetLiveConstraintsOperation;
import org.eclipse.emf.validation.internal.service.ProviderDescriptor;
import org.eclipse.emf.validation.service.IModelConstraintProvider;
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.emf.validation.tests.TestNotification;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.XmlConstraintProvider;

/**
 * Tests for the {@link ProviderDescriptor} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ProviderDescriptorTest extends TestBase {
	private ConstraintDescriptorTest.FixtureElement config;
	private ProviderDescriptor fixture;
	
	/**
	 * Constructor for ProviderDescriptorTest.
	 * @param name
	 */
	public ProviderDescriptorTest(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * Extends the inherited method.
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		config = ConstraintDescriptorTest.FixtureElement.build(
				XmlConfig.E_CONSTRAINT_PROVIDER,
				new String[][] {
					{XmlConfig.A_CLASS, XmlConstraintProvider.class.getName()},
					{XmlConfig.A_CACHE, "false"}});//$NON-NLS-1$
		
		config.addChild(ConstraintDescriptorTest.FixtureElement.build(
			XmlConfig.E_PACKAGE,
			new String[][] {
				{XmlConfig.A_NAMESPACE_URI, "http:///ordersystem.ecore"}})); //$NON-NLS-1$
	}
	
	private ConstraintDescriptorTest.FixtureElement getConfig() {
		return config;
	}
	
	private ProviderDescriptor getFixture() {
		if (fixture == null) {
			try {
				fixture = new ProviderDescriptor(getConfig());
			} catch (CoreException e) {
				fail("Exception on initializing fixture: " + e.getLocalizedMessage()); //$NON-NLS-1$
			}
		}
		
		return fixture;
	}

	public void test_provides_batch() {
		class TestOp extends GetBatchConstraintsOperation {
			TestOp(EObject target) {
				super(true);
				setTarget(target);
			}}
		
		// let the fixture be a batch constraint provider
		getConfig().putAttribute(XmlConfig.A_MODE, "Batch") //$NON-NLS-1$
			.addChild(ConstraintDescriptorTest.FixtureElement.build(
				XmlConfig.E_TARGET,
				new String[][] {{XmlConfig.A_CLASS, "Product"}}));//$NON-NLS-1$

		GetBatchConstraintsOperation op = new TestOp(
				OrderSystemFactory.eINSTANCE.createProduct());
		assertTrue("Batch operation not provided", getFixture().provides(op)); //$NON-NLS-1$
		
		op = new TestOp(OrderSystemFactory.eINSTANCE.createWarehouse());
		assertFalse("Batch operation is provided", getFixture().provides(op)); //$NON-NLS-1$
	}

	public void test_provides_live() {
		class TestOp extends GetLiveConstraintsOperation {
			TestOp(Notification notification) {
				setNotification(notification);
			}}
		
		// let the fixture be a live constraint provider
		getConfig().putAttribute(XmlConfig.A_MODE, "Live") //$NON-NLS-1$
			.addChild(ConstraintDescriptorTest.FixtureElement.build(
						XmlConfig.E_TARGET,
						new String[][] {{XmlConfig.A_CLASS, "Product"}})//$NON-NLS-1$
				.addChild(ConstraintDescriptorTest.FixtureElement.build(
						XmlConfig.E_EVENT,
						new String[][] {{XmlConfig.A_NAME, "Remove"}}))); //$NON-NLS-1$
		
		final EObject target = OrderSystemFactory.eINSTANCE.createProduct();

		GetLiveConstraintsOperation op = new TestOp(
				new TestNotification(target, Notification.REMOVE));
		assertTrue("Live operation not provided", getFixture().provides(op)); //$NON-NLS-1$

		op = new TestOp(new TestNotification(target, Notification.ADD));
		assertFalse("Live operation is provided", getFixture().provides(op)); //$NON-NLS-1$
	}

	public void test_isCacheEnabled() {
		assertFalse("Cache should not be enabled", getFixture().isCacheEnabled());//$NON-NLS-1$
	}

	public void test_isCache() {
		assertFalse("Should not be a cache", getFixture().isCache());//$NON-NLS-1$
	}

	public void test_isXmlProvider() {
		assertTrue("Should be an XML provider", getFixture().isXmlProvider());//$NON-NLS-1$
	}

	public void test_getProvider() {
		IModelConstraintProvider provider = getFixture().getProvider();
		
		assertNotNull("Provider is null", provider); //$NON-NLS-1$
		assertSame("Provider initialized twice", provider, getFixture().getProvider()); //$NON-NLS-1$
	}
}
