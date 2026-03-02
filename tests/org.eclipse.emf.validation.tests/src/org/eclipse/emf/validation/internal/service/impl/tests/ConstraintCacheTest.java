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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.service.ConstraintCache;
import org.eclipse.emf.validation.internal.service.IProviderDescriptor;
import org.eclipse.emf.validation.internal.service.IProviderOperation;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IModelConstraintProvider;
import org.eclipse.emf.validation.tests.TestNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

	@BeforeEach
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

		assertNotNull(desc, "Descriptor is null");
		assertTrue(desc.isCache(), "Descriptor is not caching");
		assertFalse(desc.isCacheEnabled(), "Descriptor is cacheable");
		assertFalse(desc.isXmlProvider(), "Descriptor is XML");
	}

	@Test
	public void test_getProviders() {
		Collection<IProviderDescriptor> c = getFixture().getProviders();

		assertNotNull(c, "Collection is null");
		assertFalse(c.isEmpty(), "Collection is empty");
	}

	@Test
	public void test_addProvider() {
		Collection<IProviderDescriptor> c = getFixture().getProviders();

		assertNotNull(c, "Collection is null");
		assertTrue(c.contains(provider), "Provider not found");
	}

	@Test
	public void test_getBatchConstraints() {
		assertTrue(batchHits == 0, "Hit count should be zero");

		Collection<IModelConstraint> c = getFixture().getBatchConstraints(OrderSystemFactory.eINSTANCE.createProduct(),
				null);

		assertNotNull(c, "Collection is null");
		assertEquals(1, c.size(), "Wrong number of constraints.");
		assertEquals(1, batchHits, "Source provider not hit.");

		// hit the cache again, and check that it it did not miss
		c = getFixture().getBatchConstraints(OrderSystemFactory.eINSTANCE.createProduct(), null);

		assertNotNull(c, "Second collection is null");
		assertEquals(1, c.size(), "Wrong number of constraints on second pass.");
		assertEquals(1, batchHits, "Source provider hit again.");
	}

	@Test
	public void test_getLiveConstraints() {
		assertTrue(liveHits == 0, "Hit count should be zero");

		Collection<IModelConstraint> c = getFixture().getLiveConstraints(
				new TestNotification(OrderSystemFactory.eINSTANCE.createProduct(), Notification.SET), null);

		assertNotNull(c, "Collection is null");
		assertEquals(1, c.size(), "Wrong number of constraints.");
		assertEquals(1, liveHits, "Source provider not hit.");

		// hit the cache again, and check that it it did not miss
		c = getFixture().getLiveConstraints(
				new TestNotification(OrderSystemFactory.eINSTANCE.createProduct(), Notification.SET), null);

		assertNotNull(c, "Second collection is null");
		assertEquals(1, c.size(), "Wrong number of constraints on second pass.");
		assertEquals(1, liveHits, "Source provider hit again.");
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
