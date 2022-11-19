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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.service.AbstractGetConstraintsOperation;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IModelConstraintProvider;
import org.eclipse.emf.validation.tests.TestBase;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * Tests for {@link AbstractGetConstraintsOperation}.
 *
 * @author Christian W. Damus (cdamus)
 */
abstract class AbstractGetConstraintsOperationTest extends TestBase {
	protected static final IModelConstraint BATCH_TOKEN = newConstraint(EvaluationMode.BATCH);
	protected static final IModelConstraint LIVE_TOKEN = newConstraint(EvaluationMode.LIVE);

	private AbstractGetConstraintsOperation fixture;

	/**
	 * Constructor for AbstractGetConstraintsOperationTest.
	 *
	 * @param name
	 */
	public AbstractGetConstraintsOperationTest(String name) {
		super(name);
	}

	protected final AbstractGetConstraintsOperation getFixture() {
		return fixture;
	}

	protected final void setFixture(AbstractGetConstraintsOperation fixture) {
		this.fixture = fixture;
	}

	private static TestConstraint newConstraint(EvaluationMode<?> mode) {
		ConstraintDescriptorTest.FixtureElement config = ConstraintDescriptorTest.newFixtureConfig();

		config.putAttribute(XmlConfig.A_ID, "aGetConOpTest@" + System.identityHashCode(config)) //$NON-NLS-1$
				.putAttribute(XmlConfig.A_MODE, mode.getName());

		try {
			return new TestConstraint(new XmlConstraintDescriptor(config));
		} catch (ConstraintExistsException e) {
			// will cause an ExceptionInInitializerError
			throw new RuntimeException();
		}
	}

	protected static class TestProvider implements IModelConstraintProvider {

		/*
		 * (non-Javadoc) Redefines/Implements/Extends the inherited method.
		 */
		@Override
		public Collection<IModelConstraint> getBatchConstraints(EObject eObject,
				Collection<IModelConstraint> constraints) {
			return appendTo(constraints, BATCH_TOKEN);
		}

		/*
		 * (non-Javadoc) Redefines/Implements/Extends the inherited method.
		 */
		@Override
		public Collection<IModelConstraint> getLiveConstraints(Notification notification,
				Collection<IModelConstraint> constraints) {
			return appendTo(constraints, LIVE_TOKEN);
		}

		private Collection<IModelConstraint> appendTo(Collection<IModelConstraint> c, IModelConstraint constraint) {

			if (c == null) {
				c = new java.util.ArrayList<>(1);
			}

			c.add(constraint);

			return c;
		}
	}

	protected static class TestConstraint implements IModelConstraint {
		private final IConstraintDescriptor descriptor;

		TestConstraint(IConstraintDescriptor descriptor) {
			this.descriptor = descriptor;
		}

		@Override
		public IStatus validate(IValidationContext ctx) {
			return null;
		}

		@Override
		public IConstraintDescriptor getDescriptor() {
			return descriptor;
		}
	}
}
