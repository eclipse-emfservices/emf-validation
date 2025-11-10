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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.service.GetBatchConstraintsOperation;
import org.eclipse.emf.validation.internal.service.GetLiveConstraintsOperation;
import org.eclipse.emf.validation.internal.service.ProviderDescriptor;
import org.eclipse.emf.validation.service.AbstractConstraintProvider;
import org.eclipse.emf.validation.service.IModelConstraintProvider;
import org.eclipse.emf.validation.tests.TestNotification;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.XmlConstraintProvider;
import org.junit.Before;
import org.junit.Test;

import ordersystem.OrderSystemFactory;

/**
 * Tests for the {@link ProviderDescriptor} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ProviderDescriptorTest {
	private ConstraintDescriptorTest.FixtureElement config;
	private ProviderDescriptor fixture;

	@Before
	public void setUp() {

		config = ConstraintDescriptorTest.FixtureElement.build(XmlConfig.E_CONSTRAINT_PROVIDER, new String[][] {
				{ XmlConfig.A_CLASS, XmlConstraintProvider.class.getName() }, { XmlConfig.A_CACHE, "false" } });

		config.addChild(ConstraintDescriptorTest.FixtureElement.build(XmlConfig.E_PACKAGE,
				new String[][] { { XmlConfig.A_NAMESPACE_URI, "http:///ordersystem.ecore" } }));
	}

	private ConstraintDescriptorTest.FixtureElement getConfig() {
		return config;
	}

	private ProviderDescriptor getFixture() {
		if (fixture == null) {
			try {
				fixture = new ProviderDescriptor(getConfig());
			} catch (CoreException e) {
				fail("Exception on initializing fixture: " + e.getLocalizedMessage());
			}
		}

		return fixture;
	}

	@Test
	public void provides_batch() {
		class TestOp extends GetBatchConstraintsOperation {
			TestOp(EObject target) {
				super(true);
				setTarget(target);
			}
		}

		// let the fixture be a batch constraint provider
		getConfig().putAttribute(XmlConfig.A_MODE, "Batch").addChild(ConstraintDescriptorTest.FixtureElement
				.build(XmlConfig.E_TARGET, new String[][] { { XmlConfig.A_CLASS, "Product" } }));

		GetBatchConstraintsOperation op = new TestOp(OrderSystemFactory.eINSTANCE.createProduct());
		assertTrue("Batch operation not provided", getFixture().provides(op));

		op = new TestOp(OrderSystemFactory.eINSTANCE.createWarehouse());
		assertFalse("Batch operation is provided", getFixture().provides(op));
	}

	@Test
	public void provides_live() {
		class TestOp extends GetLiveConstraintsOperation {
			TestOp(Notification notification) {
				setNotification(notification);
			}
		}

		// let the fixture be a live constraint provider
		getConfig().putAttribute(XmlConfig.A_MODE, "Live")
				.addChild(ConstraintDescriptorTest.FixtureElement
						.build(XmlConfig.E_TARGET, new String[][] { { XmlConfig.A_CLASS, "Product" } })
						.addChild(ConstraintDescriptorTest.FixtureElement.build(XmlConfig.E_EVENT,
								new String[][] { { XmlConfig.A_NAME, "Remove" } })));

		final EObject target = OrderSystemFactory.eINSTANCE.createProduct();

		GetLiveConstraintsOperation op = new TestOp(new TestNotification(target, Notification.REMOVE));
		assertTrue("Live operation not provided", getFixture().provides(op));

		op = new TestOp(new TestNotification(target, Notification.ADD));
		assertFalse("Live operation is provided", getFixture().provides(op));
	}

	@Test
	public void isCacheEnabled() {
		assertFalse("Cache should not be enabled", getFixture().isCacheEnabled());
	}

	@Test
	public void isCache() {
		assertFalse("Should not be a cache", getFixture().isCache());
	}

	@Test
	public void isXmlProvider() {
		assertTrue("Should be an XML provider", getFixture().isXmlProvider());
	}

	@Test
	public void getProvider() {
		IModelConstraintProvider provider = getFixture().getProvider();

		assertNotNull("Provider is null", provider);
		assertSame("Provider initialized twice", provider, getFixture().getProvider());
	}

	@Test
	public void concurrentInitialization_207780() {

		config = ConstraintDescriptorTest.FixtureElement.build(XmlConfig.E_CONSTRAINT_PROVIDER, new String[][] {
				{ XmlConfig.A_CLASS, ConcurrencyTestProvider.class.getName() }, { XmlConfig.A_CACHE, "false" } });

		config.addChild(ConstraintDescriptorTest.FixtureElement.build(XmlConfig.E_PACKAGE,
				new String[][] { { XmlConfig.A_NAMESPACE_URI, "http:///ordersystem.ecore" } }));

		try {
			final ProviderDescriptor desc = new ProviderDescriptor(config);
			final CyclicBarrier barrier = new CyclicBarrier(2);

			Runnable run = new Runnable() {
				@Override
				public void run() {
					try {
						barrier.await();
					} catch (Exception e) { // broken barrier, interrupted
						// shouldn't happen because we aren't interrupting
					}

					desc.getProvider();
				}
			};

			Thread t1 = new Thread(run);
			Thread t2 = new Thread(run);

			t1.start();
			t2.start();
			t1.join();
			t2.join();

			assertFalse("Provider initialized on two threads concurrently", ConcurrencyTestProvider.count.get() >= 2);
		} catch (Exception e) { // core, interrupted
			fail("Caught exception: " + e.getLocalizedMessage());
		}
	}

	static class ConcurrencyTestProvider extends AbstractConstraintProvider {
		static AtomicInteger count = new AtomicInteger(0);

		public ConcurrencyTestProvider() {
			super();
		}

		@Override
		public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
				throws CoreException {

			try {
				Thread.sleep(500);
				count.incrementAndGet();
				Thread.sleep(500);
			} catch (Exception e) { // interrupted
				throw new CoreException(
						new Status(IStatus.ERROR, "org.eclipse.emf.validation.tests", e.getLocalizedMessage()));
			}

			super.setInitializationData(config, propertyName, data);
		}
	}
}
