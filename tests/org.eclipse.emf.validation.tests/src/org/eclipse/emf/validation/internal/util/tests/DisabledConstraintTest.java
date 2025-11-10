/**
 * Copyright (c) 2003, 2026 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.util.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
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
import org.junit.Test;

import ordersystem.OrderSystemFactory;

/**
 * JUnit tests for the {@link DisabledConstraint} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class DisabledConstraintTest {

	@Test
	public void validate() {
		ConstraintDescriptorTest.FixtureElement config = ConstraintDescriptorTest.newFixtureConfig();
		config.putAttribute(XmlConfig.A_ID, "test.disabled.id");
		config.putAttribute(XmlConfig.A_NAME, "Disabled test");

		try {
			IConstraintDescriptor descriptor = new XmlConstraintDescriptor(config);

			Exception exception = new Exception();
			EObject eObject = OrderSystemFactory.eINSTANCE.createAccount();
			IValidationContext ctx = new ValidationContext(eObject);

			IStatus result = new DisabledConstraint(descriptor, exception).validate(ctx);

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
			fail("Test constraint already exists!  Is the test being repeated?");
		}
	}

	public static class ValidationContext implements IValidationContext {
		private final EObject target;
		private final Set<EObject> resultLocus = new java.util.HashSet<>();

		public ValidationContext(EObject target) {
			this.target = target;
			resultLocus.add(target);
		}

		@Override
		public String getCurrentConstraintId() {
			return "test.disabled.id";
		}

		@Override
		public EObject getTarget() {
			return target;
		}

		@Override
		public EMFEventType getEventType() {
			return EMFEventType.NULL;
		}

		@Override
		public List<Notification> getAllEvents() {
			return Collections.emptyList();
		}

		@Override
		public EStructuralFeature getFeature() {
			return null;
		}

		@Override
		public Object getFeatureNewValue() {
			return null;
		}

		@Override
		public void skipCurrentConstraintFor(EObject eObject) {
			// no need to do anything in this test fixture
		}

		@Override
		public void skipCurrentConstraintForAll(Collection<?> eObjects) {
			// no need to do anything in this test fixture
		}

		@Override
		public void disableCurrentConstraint(Throwable exception) {
			// no need to do anything in this test fixture
		}

		@Override
		public Object getCurrentConstraintData() {
			return null;
		}

		@Override
		public Object putCurrentConstraintData(Object newData) {
			return null;
		}

		@Override
		public Set<EObject> getResultLocus() {
			return resultLocus;
		}

		@Override
		public void addResult(EObject eObject) {
			resultLocus.add(eObject);
		}

		@Override
		public void addResults(Collection<? extends EObject> eObjects) {
			resultLocus.addAll(eObjects);
		}

		@Override
		public IStatus createSuccessStatus() {
			return Status.OK_STATUS;
		}

		@Override
		public IStatus createFailureStatus(Object... messageArguments) {
			return new Status(IStatus.ERROR, "org.eclipse.emf.validation.tests", 1, "", null);
		}
	}
}
