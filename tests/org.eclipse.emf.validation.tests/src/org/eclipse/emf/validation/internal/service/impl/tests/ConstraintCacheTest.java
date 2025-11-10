/**
 * Copyright (c) 2004, 2026 IBM Corporation and others.
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.service.ConstraintCache;
import org.eclipse.emf.validation.internal.service.IProviderDescriptor;
import org.eclipse.emf.validation.internal.service.IProviderOperation;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IModelConstraintProvider;
import org.eclipse.emf.validation.tests.TestNotification;
import org.junit.Before;
import org.junit.Test;

import ordersystem.OrderSystemFactory;

/**
 * Tests for {@link ConstraintCache}.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintCacheTest {
	static int batchHits = 0;
	static int liveHits = 0;

	private ConstraintCache fixture;
	private IProviderDescriptor provider;

	@Before
	public void setUp() {
		provider = new TestDescriptor();
		fixture = new ConstraintCache();
		fixture.addProvider(provider);
	}

	private ConstraintCache getFixture() {
		return fixture;
	}

	@Test
	public void test_getDescriptor() {
		IProviderDescriptor desc = getFixture().getDescriptor();

		assertNotNull("Descriptor is null", desc);
		assertTrue("Descriptor is not caching", desc.isCache());
		assertFalse("Descriptor is cacheable", desc.isCacheEnabled());
		assertFalse("Descriptor is XML", desc.isXmlProvider());
	}

	@Test
	public void test_getProviders() {
		Collection<IProviderDescriptor> c = getFixture().getProviders();

		assertNotNull("Collection is null", c);
		assertFalse("Collection is empty", c.isEmpty());
	}

	@Test
	public void test_addProvider() {
		Collection<IProviderDescriptor> c = getFixture().getProviders();

		assertNotNull("Collection is null", c);
		assertTrue("Provider not found", c.contains(provider));
	}

	@Test
	public void test_getBatchConstraints() {
		assertTrue("Hit count should be zero", batchHits == 0);

		Collection<IModelConstraint> c = getFixture().getBatchConstraints(OrderSystemFactory.eINSTANCE.createProduct(),
				null);

		assertNotNull("Collection is null", c);
		assertEquals("Wrong number of constraints.", 1, c.size());
		assertEquals("Source provider not hit.", 1, batchHits);

		// hit the cache again, and check that it it did not miss
		c = getFixture().getBatchConstraints(OrderSystemFactory.eINSTANCE.createProduct(), null);

		assertNotNull("Second collection is null", c);
		assertEquals("Wrong number of constraints on second pass.", 1, c.size());
		assertEquals("Source provider hit again.", 1, batchHits);
	}

	@Test
	public void test_getLiveConstraints() {
		assertTrue("Hit count should be zero", liveHits == 0);

		Collection<IModelConstraint> c = getFixture().getLiveConstraints(
				new TestNotification(OrderSystemFactory.eINSTANCE.createProduct(), Notification.SET), null);

		assertNotNull("Collection is null", c);
		assertEquals("Wrong number of constraints.", 1, c.size());
		assertEquals("Source provider not hit.", 1, liveHits);

		// hit the cache again, and check that it it did not miss
		c = getFixture().getLiveConstraints(
				new TestNotification(OrderSystemFactory.eINSTANCE.createProduct(), Notification.SET), null);

		assertNotNull("Second collection is null", c);
		assertEquals("Wrong number of constraints on second pass.", 1, c.size());
		assertEquals("Source provider hit again.", 1, liveHits);
	}

	static class TestDescriptor implements IProviderDescriptor {
		private final IModelConstraintProvider testProvider = new TestProvider();

		@Override
		public boolean provides(IProviderOperation<? extends Collection<? extends IModelConstraint>> operation) {
			return true;
		}

		@Override
		public boolean isCacheEnabled() {
			return true;
		}

		@Override
		public boolean isXmlProvider() {
			return false;
		}

		@Override
		public boolean isCache() {
			return false;
		}

		@Override
		public IModelConstraintProvider getProvider() {
			return testProvider;
		}
	}

	static class TestProvider extends AbstractGetConstraintsOperationTest.TestProvider {

		@Override
		public Collection<IModelConstraint> getBatchConstraints(EObject eObject,
				Collection<IModelConstraint> constraints) {

			batchHits++;
			return super.getBatchConstraints(eObject, constraints);
		}

		@Override
		public Collection<IModelConstraint> getLiveConstraints(Notification notification,
				Collection<IModelConstraint> constraints) {

			liveHits++;
			return super.getLiveConstraints(notification, constraints);
		}
	}
}
