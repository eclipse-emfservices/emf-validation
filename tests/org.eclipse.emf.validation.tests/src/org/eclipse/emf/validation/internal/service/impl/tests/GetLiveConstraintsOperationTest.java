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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.internal.service.GetLiveConstraintsOperation;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.tests.TestNotification;
import org.junit.Before;
import org.junit.Test;

import ordersystem.OrderSystemFactory;

/**
 * Tests for {@link GetLiveConstraintsOperation}.
 *
 * @author Christian W. Damus (cdamus)
 */
public class GetLiveConstraintsOperationTest extends AbstractGetConstraintsOperationTest {
	private EObject target;
	private Notification notification;

	@Before
	public void setUp() {
		target = OrderSystemFactory.eINSTANCE.createProduct();
		notification = new TestNotification(target, Notification.SET);
		setFixture(new TestOperation(notification));
	}

	private GetLiveConstraintsOperation getLiveFixture() {
		return (GetLiveConstraintsOperation) getFixture();
	}

	@Test
	public void test_getNotification() {
		assertSame("Wrong notification", notification, getLiveFixture().getNotification());
	}

	@Test
	public void test_getEventType() {
		assertSame("Wrong event type", EMFEventType.SET, getLiveFixture().getEventType());
	}

	@Test
	public void test_getEObject() {
		assertSame("Wrong target object", target, getFixture().getEObject());
	}

	@Test
	public void test_execute() {
		Collection<IModelConstraint> c = getFixture().execute(new TestProvider());
		assertNotNull(c);
		assertTrue("Token not found", c.contains(LIVE_TOKEN));
	}

	@Test
	public void test_getConstraints() {
		getFixture().execute(new TestProvider());

		Collection<IModelConstraint> c = getFixture().getConstraints();

		// check that the token is in this collection
		assertTrue("Token not found", c.contains(LIVE_TOKEN));

		// disable the token constraint and ensure that it is no longer
		// in the collection. This checks that the filtering is correct
		// Note that we must obtain the collection again due to the fact
		// that filtering is done before the client context is consulted
		((TestConstraint) LIVE_TOKEN).getDescriptor().setError(new Exception());
		c = getFixture().getConstraints();

		assertFalse("Token is found", c.contains(LIVE_TOKEN));
	}

	private static class TestOperation extends GetLiveConstraintsOperation {
		TestOperation(Notification notification) {
			super();

			setNotification(notification);
		}
	}
}
