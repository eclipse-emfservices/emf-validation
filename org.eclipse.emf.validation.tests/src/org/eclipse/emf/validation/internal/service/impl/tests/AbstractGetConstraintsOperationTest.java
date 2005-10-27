/******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


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
	protected static final Object BATCH_TOKEN = newConstraint(EvaluationMode.BATCH);
	protected static final Object LIVE_TOKEN = newConstraint(EvaluationMode.LIVE);
	
	private AbstractGetConstraintsOperation fixture;
	
	/**
	 * Constructor for AbstractGetConstraintsOperationTest.
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
	
	private static TestConstraint newConstraint(EvaluationMode mode) {
		ConstraintDescriptorTest.FixtureElement config =
			ConstraintDescriptorTest.newFixtureConfig();
		
		config.putAttribute(
				XmlConfig.A_ID,
				"aGetConOpTest@" + System.identityHashCode(config)) //$NON-NLS-1$
			.putAttribute(XmlConfig.A_MODE, mode.getName());
		
		try {
			return new TestConstraint(new XmlConstraintDescriptor(config));
		} catch (ConstraintExistsException e) {
			// will cause an ExceptionInInitializerError
			throw new RuntimeException();
		}
	}
	
	protected static class TestProvider implements IModelConstraintProvider {

		/* (non-Javadoc)
		 * Redefines/Implements/Extends the inherited method.
		 */
		public Collection getBatchConstraints(
				EObject eObject,
				Collection constraints) {
			return appendTo(constraints, BATCH_TOKEN);
		}

		/* (non-Javadoc)
		 * Redefines/Implements/Extends the inherited method.
		 */
		public Collection getLiveConstraints(
				Notification notification,
				Collection constraints) {
			return appendTo(constraints, LIVE_TOKEN);
		}
		
		private Collection appendTo(Collection c, Object o) {
			if (c == null) {
				c = new java.util.ArrayList(1);
			}
			
			c.add(o);
			
			return c;
		}
	}
	
	protected static class TestConstraint implements IModelConstraint {
		private final IConstraintDescriptor descriptor;
		
		TestConstraint(IConstraintDescriptor descriptor) {
			this.descriptor = descriptor;
		}

		public IStatus validate(IValidationContext ctx) {
			return null;
		}
		
		public IConstraintDescriptor getDescriptor() {
			return descriptor;
		}
	}
}
