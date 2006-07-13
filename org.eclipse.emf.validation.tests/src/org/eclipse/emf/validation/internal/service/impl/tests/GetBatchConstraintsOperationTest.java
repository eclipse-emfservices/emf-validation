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

import java.util.Collection;

import ordersystem.OrderSystemFactory;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.internal.service.GetBatchConstraintsOperation;

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
		Object o = getFixture().execute(new TestProvider());
		assertNotNull(o);
		assertTrue("Execution result not collection", o instanceof Collection);//$NON-NLS-1$
		
		Collection c = (Collection)o;
		assertTrue("Token not found", c.contains(BATCH_TOKEN));//$NON-NLS-1$
	}

	public void test_getConstraints() {
		getFixture().execute(new TestProvider());
		
		Collection c = getFixture().getConstraints();
		
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
