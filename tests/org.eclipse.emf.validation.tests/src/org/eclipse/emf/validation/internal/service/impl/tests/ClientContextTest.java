/**
 * Copyright (c) 2008, 2025 Zeligsoft Inc. and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Zeligsoft - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.service.impl.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link ClientContext} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ClientContextTest {

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

	@BeforeEach
	public void setUp() {

		fixture = ClientContextManager.getInstance().getClientContext("org.eclipse.emf.validation.tests.testcontext");

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

	@Test
	public void includedChildOfExcludedCategory() {
		assertTrue(fixture.includes(constraint3_1), "Constraint is excluded");
		assertTrue(fixture.includes(constraint3_2), "Constraint is excluded");
	}

	@Test
	public void constraintExcludedFromIncludedCategory() {
		assertFalse(fixture.includes(constraint1_1), "Constraint is included");
		assertTrue(fixture.includes(constraint1_2), "Constraint is excluded");
	}

	@Test
	public void constraintIncludedInExcludedCategory() {
		assertFalse(fixture.includes(constraint2_1), "Constraint is included");
		assertTrue(fixture.includes(constraint2_2), "Constraint is excluded");
	}

	@Test
	public void constraintExcludedFromIncludedCategory_nested() {
		assertFalse(fixture.includes(constraint2a_1), "Constraint is included");
		assertTrue(fixture.includes(constraint2a_2), "Constraint is excluded");
	}

	@Test
	public void constraintIncludedInExcludedCategory_nested() {
		assertFalse(fixture.includes(constraint3a_1), "Constraint is included");
		assertTrue(fixture.includes(constraint3a_2), "Constraint is excluded");
	}

	/**
	 * Tests that the determination of client-contexts matching an object does not
	 * include any that are extended by other matching contexts.
	 */
	@Test
	public void multipleMatchingContextsWithExclusions() {
		Collection<IClientContext> contexts;

		System.setProperty("BOGUS_SYSTEM_PROPERTY", "1");
		try {
			contexts = ClientContextManager.getInstance()
					.getClientContextsFor(EcoreFactory.eINSTANCE.createEAnnotation());
		} finally {
			System.clearProperty("BOGUS_SYSTEM_PROPERTY");
		}

		boolean extenderFound = false;
		boolean extendedFound = false;

		for (IClientContext next : contexts) {
			extenderFound |= "org.eclipse.emf.validation.tests.testcontext".equals(next.getId());
			extendedFound |= "org.eclipse.emf.validation.tests.testContextToExtend".equals(next.getId());
		}

		assertTrue(extenderFound, "Extending context not matched");
		assertFalse(extendedFound, "Extended context was matched");
	}

	private static final class TestConstraint implements IModelConstraint {

		private final IConstraintDescriptor desc;

		TestConstraint(IConstraintDescriptor desc) {
			this.desc = desc;
		}

		@Override
		public IStatus validate(IValidationContext c) {
			return Status.OK_STATUS;
		}

		@Override
		public IConstraintDescriptor getDescriptor() {
			return desc;
		}
	}
}
