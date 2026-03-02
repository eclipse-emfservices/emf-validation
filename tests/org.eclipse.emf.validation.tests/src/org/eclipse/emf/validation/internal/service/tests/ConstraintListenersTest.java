/**
 * Copyright (c) 2007, 2026 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.service.tests;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.CategoryManager;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.AbstractConstraintDescriptor;
import org.eclipse.emf.validation.service.ConstraintChangeEventType;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.tests.TestBase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link ConstraintRegistry}'s support for the constraint listener
 * API.
 *
 * @author David Cummings (dcummin)
 */
public class ConstraintListenersTest {

	ConstraintListener listener = ConstraintListener.getInstance();
	ConstraintListenerTestConstraint registeredConstraint = new ConstraintListenerTestConstraint(
			"registered.constraint");
	ConstraintListenerTestConstraint unregisteredConstraint = new ConstraintListenerTestConstraint(
			"unregistered.constraint");
	Category testCategory = CategoryManager.getInstance().findCategory("junit"); // this should have been
																					// loaded from the extension point

	@BeforeEach
	public void setUp() throws Exception {
		ConstraintRegistry.getInstance().register(registeredConstraint);
		ConstraintRegistry.getInstance().addConstraintListener(ConstraintListener.getInstance());
		listener.setLastEvent(null);
		listener.setEnabled(true);
	}

	@AfterEach
	public void tearDown() throws Exception {
		listener.setEnabled(false);
		listener.setLastEvent(null);
		ConstraintRegistry.getInstance().removeConstraintListener(ConstraintListener.getInstance());
		ConstraintRegistry.getInstance().unregister(registeredConstraint);
	}

	@Test
	public void test_registerConstraint_177656() throws ConstraintExistsException {
		ConstraintRegistry.getInstance().register(unregisteredConstraint);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null");
		}

		assertSame(ConstraintChangeEventType.REGISTERED, listener.getLastEvent().getEventType(),
				"Incorrect constraint change event");
		assertSame(unregisteredConstraint, listener.getLastEvent().getConstraint(), "Incorrect constraint descriptor");
	}

	@Test
	public void test_unRegisterConstraint_177656() throws ConstraintExistsException {
		ConstraintRegistry.getInstance().unregister(unregisteredConstraint);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null");
		}

		assertSame(ConstraintChangeEventType.UNREGISTERED, listener.getLastEvent().getEventType(),
				"Incorrect constraint change event");
		assertSame(unregisteredConstraint, listener.getLastEvent().getConstraint(), "Incorrect constraint descriptor");
	}

	@Test
	public void test_enableConstraint_177656() {
		registeredConstraint.setEnabled(false); // Ensure disabled
		registeredConstraint.setEnabled(true);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null");
		}

		assertSame(ConstraintChangeEventType.ENABLED, listener.getLastEvent().getEventType(),
				"Incorrect constraint change event");
		assertSame(registeredConstraint, listener.getLastEvent().getConstraint(), "Incorrect constraint descriptor");

		// Enabled an enabled constraint, should not fire event
		listener.setLastEvent(null);
		registeredConstraint.setEnabled(true);
		assertNull(listener.getLastEvent(), "Enabling an enabled constraint incorrectly sent an event");
	}

	@Test
	public void test_disableConstraint_177656() {
		registeredConstraint.setEnabled(true); // Ensure enabled
		registeredConstraint.setEnabled(false);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null");
		}

		assertSame(ConstraintChangeEventType.DISABLED, listener.getLastEvent().getEventType(),
				"Incorrect constraint change event");
		assertSame(registeredConstraint, listener.getLastEvent().getConstraint(), "Incorrect constraint descriptor");

		// Disable a disabled constraint, should not fire event
		listener.setLastEvent(null);
		registeredConstraint.setEnabled(false);
		assertNull(listener.getLastEvent(), "Disabling a disabled constraint incorrectly sent an event");
	}

	@Test
	public void test_addCategoryToConstraint_177656() {
		registeredConstraint.addCategory(testCategory);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null");
		}

		assertSame(ConstraintChangeEventType.ADDED_CATEGORY, listener.getLastEvent().getEventType(),
				"Incorrect constraint change event");
		assertSame(registeredConstraint, listener.getLastEvent().getConstraint(), "Incorrect constraint descriptor");
		assertSame(testCategory, listener.getLastEvent().getCategory(), "Incorrect constraint category");

		// Attempt to add category again, should not fire event
		listener.setLastEvent(null);
		registeredConstraint.addCategory(testCategory);
		assertNull(listener.getLastEvent(), "Add category for already associated category incorrectly sent an event");
	}

	@Test
	public void test_removeCategoryFromConstraint_177656() {
		registeredConstraint.addCategory(testCategory); // Ensure category exists
		registeredConstraint.removeCategory(testCategory);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null");
		}

		assertSame(ConstraintChangeEventType.REMOVED_CATEGORY, listener.getLastEvent().getEventType(),
				"Incorrect constraint change event");
		assertSame(registeredConstraint, listener.getLastEvent().getConstraint(), "Incorrect constraint descriptor");
		assertSame(testCategory, listener.getLastEvent().getCategory(), "Incorrect constraint category");

		// Attempt to remove category again, should not fire event
		listener.setLastEvent(null);
		registeredConstraint.removeCategory(testCategory);
		assertNull(listener.getLastEvent(), "Remove category for non-associated category incorrectly sent an event");
	}

	public class ConstraintListenerTestConstraint extends AbstractConstraintDescriptor
			implements IConstraintDescriptor {
		String id;

		public ConstraintListenerTestConstraint(String id) {
			this.id = id;
		}

		@Override
		public String getId() {
			return id;
		}

		@Override
		public String getPluginId() {
			return TestBase.PLUGIN_ID;
		}

		@Override
		public String getMessagePattern() {
			return null;
		}

		@Override
		public String getBody() {
			return null;
		}

		@Override
		public String getName() {
			return null;
		}

		@Override
		public String getDescription() {
			return null;
		}

		@Override
		public ConstraintSeverity getSeverity() {
			return null;
		}

		@Override
		public int getStatusCode() {
			return 0;
		}

		@Override
		public EvaluationMode<?> getEvaluationMode() {
			return EvaluationMode.NULL;
		}

		@Override
		public boolean targetsTypeOf(EObject eObject) {
			return false;
		}

		@Override
		public boolean targetsEvent(Notification notification) {
			return false;
		}
	}
}
