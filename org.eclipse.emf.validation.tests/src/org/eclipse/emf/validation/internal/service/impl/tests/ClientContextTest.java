/**
 * <copyright>
 * 
 * Copyright (c) 2008 Zeligsoft Inc. and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Zeligsoft - Initial API and implementation
 * 
 * </copyright>
 *
 * $Id: ClientContextTest.java,v 1.1 2008/11/01 16:22:29 cdamus Exp $
 */

package org.eclipse.emf.validation.internal.service.impl.tests;

import java.util.Collection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.service.ClientContext;
import org.eclipse.emf.validation.internal.service.ClientContextManager;
import org.eclipse.emf.validation.internal.service.IClientContext;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.ModelValidationService;

/**
 * Tests the {@link ClientContext} class.
 * 
 * @author Christian W. Damus (cdamus)
 */
@SuppressWarnings("nls")
public class ClientContextTest
		extends TestCase {

	private IModelConstraint constraint1_1;

	private IModelConstraint constraint1_2;

	private IModelConstraint constraint2_1;

	private IModelConstraint constraint2_2;

	private IModelConstraint constraint2a_1;

	private IModelConstraint constraint2a_2;

	private IModelConstraint constraint3_1;

	private IModelConstraint constraint3_2;

	private IModelConstraint constraint3a_1;

	private IModelConstraint constraint3a_2;

	private IClientContext fixture;

	/**
	 * Initialize me with my name.
	 * 
	 * @param name
	 *            my name
	 */
	public ClientContextTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(ClientContextTest.class, "Client context tests");
	}

	public void test_includedChildOfExcludedCategory() {
		assertTrue("Constraint is excluded", fixture.includes(constraint3_1));
		assertTrue("Constraint is excluded", fixture.includes(constraint3_2));
	}

	public void test_constraintExcludedFromIncludedCategory() {
		assertFalse("Constraint is included", fixture.includes(constraint1_1));
		assertTrue("Constraint is excluded", fixture.includes(constraint1_2));
	}

	public void test_constraintIncludedInExcludedCategory() {
		assertFalse("Constraint is included", fixture.includes(constraint2_1));
		assertTrue("Constraint is excluded", fixture.includes(constraint2_2));
	}

	public void test_constraintExcludedFromIncludedCategory_nested() {
		assertFalse("Constraint is included", fixture.includes(constraint2a_1));
		assertTrue("Constraint is excluded", fixture.includes(constraint2a_2));
	}

	public void test_constraintIncludedInExcludedCategory_nested() {
		assertFalse("Constraint is included", fixture.includes(constraint3a_1));
		assertTrue("Constraint is excluded", fixture.includes(constraint3a_2));
	}

	/**
	 * Tests that the determination of client-contexts matching an object does
	 * not include any that are extended by other matching contexts.
	 */
	public void test_multipleMatchingContextsWithExclusions() {
		Collection<IClientContext> contexts;

		System.setProperty("BOGUS_SYSTEM_PROPERTY", "1");
		try {
			contexts = ClientContextManager.getInstance().getClientContextsFor(
				EcoreFactory.eINSTANCE.createEAnnotation());
		} finally {
			System.clearProperty("BOGUS_SYSTEM_PROPERTY");
		}

		boolean extenderFound = false;
		boolean extendedFound = false;
		
		for (IClientContext next : contexts) {
			extenderFound |= "org.eclipse.emf.validation.tests.testcontext".equals(next.getId());
			extendedFound |= "org.eclipse.emf.validation.tests.testContextToExtend".equals(next.getId());
		}
		
		assertTrue("Extending context not matched", extenderFound);
		assertFalse("Extended context was matched", extendedFound);
	}

	//
	// Test framework
	//

	@Override
	protected void setUp()
			throws Exception {

		super.setUp();

		fixture = ClientContextManager.getInstance().getClientContext(
			"org.eclipse.emf.validation.tests.testcontext");

		final String prefix = "org.eclipse.emf.validation.tests.clientContext.";
		final ConstraintRegistry reg = ConstraintRegistry.getInstance();

		// cause our test constraints to be created
		ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH)
			.validate(EcoreFactory.eINSTANCE.createEAnnotation());

		constraint1_1 = new TestConstraint(reg.getDescriptor(prefix + "1.1"));
		constraint1_2 = new TestConstraint(reg.getDescriptor(prefix + "1.2"));
		constraint2_1 = new TestConstraint(reg.getDescriptor(prefix + "2.1"));
		constraint2_2 = new TestConstraint(reg.getDescriptor(prefix + "2.2"));
		constraint2a_1 = new TestConstraint(reg.getDescriptor(prefix + "2a.1"));
		constraint2a_2 = new TestConstraint(reg.getDescriptor(prefix + "2a.2"));
		constraint3_1 = new TestConstraint(reg.getDescriptor(prefix + "3.1"));
		constraint3_2 = new TestConstraint(reg.getDescriptor(prefix + "3.2"));
		constraint3a_1 = new TestConstraint(reg.getDescriptor(prefix + "3a.1"));
		constraint3a_2 = new TestConstraint(reg.getDescriptor(prefix + "3a.2"));
	}

	private static final class TestConstraint
			implements IModelConstraint {

		private IConstraintDescriptor desc;

		TestConstraint(IConstraintDescriptor desc) {
			this.desc = desc;
		}

		public IStatus validate(IValidationContext c) {
			return Status.OK_STATUS;
		}

		public IConstraintDescriptor getDescriptor() {
			return desc;
		}
	}
}
