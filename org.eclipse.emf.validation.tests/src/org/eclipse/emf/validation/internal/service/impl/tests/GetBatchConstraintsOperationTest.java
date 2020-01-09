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

import java.util.Collection;

import ordersystem.OrderSystemFactory;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.internal.service.GetBatchConstraintsOperation;
import org.eclipse.emf.validation.model.IModelConstraint;

/**
 * Tests for {@link GetBatchConstraintsOperation}.
 *
 * @author Christian W. Damus (cdamus)
 */
public class GetBatchConstraintsOperationTest
		extends AbstractGetConstraintsOperationTest {
	private EObject target;

	/**
	 * Constructor for GetBatchConstraintsOperationTest.
	 * @param name
	 */
	public GetBatchConstraintsOperationTest(String name) {
		super(name);
	}

	/* (non-Javadoc)
	 * Extends the inherited method.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		target = OrderSystemFactory.eINSTANCE.createProduct();
		setFixture(new TestOperation(target));
	}

	public void test_getEObject() {
		assertSame(
				"Wrong target object", //$NON-NLS-1$
				target,
				getFixture().getEObject());
	}

	public void test_execute() {
		Collection<IModelConstraint> c = getFixture().execute(new TestProvider());
		assertNotNull(c);
		
		assertTrue("Token not found", c.contains(BATCH_TOKEN));//$NON-NLS-1$
	}

	public void test_getConstraints() {
		getFixture().execute(new TestProvider());
		
		Collection<IModelConstraint> c = getFixture().getConstraints();
		
		// check that the token is in this collection
		assertTrue("Token not found", c.contains(BATCH_TOKEN)); //$NON-NLS-1$
		
		// disable the token constraint and ensure that it is no longer
		//   in the collection.  This checks that the filtering is correct.
		//   Note that we must obtain the collection again due to the fact
		//   that filtering is done before the client context is consulted
		((TestConstraint)BATCH_TOKEN).getDescriptor().setError(new Exception());
		c = getFixture().getConstraints();
		
		assertFalse("Token is found", c.contains(BATCH_TOKEN)); //$NON-NLS-1$
	}
	
	private static class TestOperation extends GetBatchConstraintsOperation {
		TestOperation(EObject target) {
			super(true);
			
			setTarget(target);
		}
	}
}
