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

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.service.GetBatchConstraintsOperation;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.junit.Before;
import org.junit.Test;

import ordersystem.OrderSystemFactory;

/**
 * Tests for {@link GetBatchConstraintsOperation}.
 *
 * @author Christian W. Damus (cdamus)
 */
public class GetBatchConstraintsOperationTest extends AbstractGetConstraintsOperationTest {
	private EObject target;

	@Before
	public void setUp() {
		target = OrderSystemFactory.eINSTANCE.createProduct();
		setFixture(new TestOperation(target));
	}

	@Test
	public void test_getEObject() {
		assertSame("Wrong target object", target, getFixture().getEObject());
	}

	@Test
	public void test_execute() {
		Collection<IModelConstraint> c = getFixture().execute(new TestProvider());
		assertNotNull(c);

		assertTrue("Token not found", c.contains(BATCH_TOKEN));
	}

	@Test
	public void test_getConstraints() {
		getFixture().execute(new TestProvider());

		Collection<IModelConstraint> c = getFixture().getConstraints();

		// check that the token is in this collection
		assertTrue("Token not found", c.contains(BATCH_TOKEN));

		// disable the token constraint and ensure that it is no longer
		// in the collection. This checks that the filtering is correct.
		// Note that we must obtain the collection again due to the fact
		// that filtering is done before the client context is consulted
		((TestConstraint) BATCH_TOKEN).getDescriptor().setError(new Exception());
		c = getFixture().getConstraints();

		assertFalse("Token is found", c.contains(BATCH_TOKEN));
	}

	private static class TestOperation extends GetBatchConstraintsOperation {
		TestOperation(EObject target) {
			super(true);
			setTarget(target);
		}
	}
}
