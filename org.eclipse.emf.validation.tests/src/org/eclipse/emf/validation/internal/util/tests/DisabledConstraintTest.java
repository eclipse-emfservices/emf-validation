/**
 * <copyright>
 *
 * Copyright (c) 2003-2005 IBM Corporation and others.
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

package org.eclipse.emf.validation.internal.util.tests;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;
import ordersystem.OrderSystemFactory;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.internal.util.DisabledConstraint;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * JUnit tests for the {@link DisabledConstraint} class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class DisabledConstraintTest extends TestCase {
	public void test_validate() {
		ConstraintDescriptorTest.FixtureElement config =
			ConstraintDescriptorTest.newFixtureConfig();
		config.putAttribute(XmlConfig.A_ID, "test.disabled.id"); //$NON-NLS-1$
		config.putAttribute(XmlConfig.A_NAME, "Disabled test"); //$NON-NLS-1$
		
		try {
			IConstraintDescriptor descriptor = new XmlConstraintDescriptor(config);
			
			Exception exception = new Exception();
			EObject eObject = OrderSystemFactory.eINSTANCE.createAccount();
			IValidationContext ctx = new ValidationContext(eObject);
			
			IStatus result = new DisabledConstraint(descriptor, exception)
					.validate(ctx);
			
			assertTrue(result instanceof IConstraintStatus);
			
			assertFalse(result.isOK());
			assertFalse(result.isMultiStatus());
			
			assertTrue(descriptor.isError());
			assertFalse(descriptor.isEnabled());
			
			assertSame(exception, descriptor.getException());
			
			// try to set status back to enabled
			descriptor.setEnabled(true);
			
			// verify that, because of the error, it is still disabled
			assertFalse(descriptor.isEnabled());
		} catch (ConstraintExistsException e) {
			fail("Test constraint already exists!  Is the test being repeated?"); //$NON-NLS-1$
		}
	}
	
	public static class ValidationContext implements IValidationContext {
		private final EObject target;
		private Set resultLocus = new java.util.HashSet();
		
		public ValidationContext(EObject target) {
			this.target = target;
			resultLocus.add(target);
		}
		
		// implements the inherited method
		public String getCurrentConstraintId() {
			return "test.disabled.id"; //$NON-NLS-1$
		}

		// implements the inherited method
		public EObject getTarget() {
			return target;
		}

		// implements the inherited method
		public EMFEventType getEventType() {
			return EMFEventType.NULL;
		}

		// implements the inherited method
		public List getAllEvents() {
			return Collections.EMPTY_LIST;
		}

		// implements the inherited method
		public EStructuralFeature getFeature() {
			return null;
		}
		
		// implements the inherited method
		public Object getFeatureNewValue() {
			return null;
		}

		// implements the inherited method
		public void skipCurrentConstraintFor(EObject eObject) {
			// no need to do anything in this test fixture
		}

		// implements the inherited method
		public void skipCurrentConstraintForAll(Collection eObjects) {
			// no need to do anything in this test fixture
		}
		
		// implements the inherited method
		public void disableCurrentConstraint(Throwable exception) {
			// no need to do anything in this test fixture
		}

		// implements the inherited method
		public Object getCurrentConstraintData() {
			return null;
		}

		// implements the inherited method
		public Object putCurrentConstraintData(Object newData) {
			return null;
		}

		// implements the inherited method
		public Set getResultLocus() {
			return resultLocus;
		}
		
		// implements the inherited method
		public void addResult(EObject eObject) {
			resultLocus.add(eObject);
		}
		
		// implements the inherited method
		public void addResults(Collection eObjects) {
			resultLocus.add(eObjects);
		}

		/* (non-Javadoc)
		 * Redefines/Implements/Extends the inherited method.
		 */
		public IStatus createSuccessStatus() {
			return Status.OK_STATUS;
		}

		/* (non-Javadoc)
		 * Redefines/Implements/Extends the inherited method.
		 */
		public IStatus createFailureStatus(Object[] messageArguments) {
			return new Status(
				IStatus.ERROR,
				"org.eclipse.emf.validation.tests", //$NON-NLS-1$
				1,
				"", //$NON-NLS-1$
				null);
		}
	}
}
