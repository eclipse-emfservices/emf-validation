/**
 * Copyright (c) 2007 IBM Corporation and others.
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

/**
 * Tests the {@link ConstraintRegistry}'s support for the constraint listener
 * API.
 *
 * @author David Cummings (dcummin)
 */
public class ConstraintListenersTest extends TestBase {

	ConstraintListener listener = ConstraintListener.getInstance();
	ConstraintListenerTestConstraint registeredConstraint = new ConstraintListenerTestConstraint(
			"registered.constraint"); //$NON-NLS-1$
	ConstraintListenerTestConstraint unregisteredConstraint = new ConstraintListenerTestConstraint(
			"unregistered.constraint"); //$NON-NLS-1$
	Category testCategory = CategoryManager.getInstance().findCategory("junit"); //$NON-NLS-1$ // this should have been
																					// loaded from the extension point

	public ConstraintListenersTest(String name) {
		super(name);
	}

	public void test_registerConstraint_177656() throws ConstraintExistsException {
		ConstraintRegistry.getInstance().register(unregisteredConstraint);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null"); //$NON-NLS-1$
		}

		assertSame("Incorrect constraint change event", ConstraintChangeEventType.REGISTERED, //$NON-NLS-1$
				listener.getLastEvent().getEventType());
		assertSame("Incorrect constraint descriptor", unregisteredConstraint, listener.getLastEvent().getConstraint()); //$NON-NLS-1$
	}

	public void test_unRegisterConstraint_177656() throws ConstraintExistsException {
		ConstraintRegistry.getInstance().unregister(unregisteredConstraint);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null"); //$NON-NLS-1$
		}

		assertSame("Incorrect constraint change event", ConstraintChangeEventType.UNREGISTERED, //$NON-NLS-1$
				listener.getLastEvent().getEventType());
		assertSame("Incorrect constraint descriptor", unregisteredConstraint, listener.getLastEvent().getConstraint()); //$NON-NLS-1$
	}

	public void test_enableConstraint_177656() {
		registeredConstraint.setEnabled(false); // Ensure disabled
		registeredConstraint.setEnabled(true);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null"); //$NON-NLS-1$
		}

		assertSame("Incorrect constraint change event", ConstraintChangeEventType.ENABLED, //$NON-NLS-1$
				listener.getLastEvent().getEventType());
		assertSame("Incorrect constraint descriptor", registeredConstraint, listener.getLastEvent().getConstraint()); //$NON-NLS-1$

		// Enabled an enabled constraint, should not fire event
		listener.setLastEvent(null);
		registeredConstraint.setEnabled(true);
		assertNull("Enabling an enabled constraint incorrectly sent an event", listener.getLastEvent()); //$NON-NLS-1$
	}

	public void test_disableConstraint_177656() {
		registeredConstraint.setEnabled(true); // Ensure enabled
		registeredConstraint.setEnabled(false);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null"); //$NON-NLS-1$
		}

		assertSame("Incorrect constraint change event", ConstraintChangeEventType.DISABLED, //$NON-NLS-1$
				listener.getLastEvent().getEventType());
		assertSame("Incorrect constraint descriptor", registeredConstraint, listener.getLastEvent().getConstraint()); //$NON-NLS-1$

		// Disable a disabled constraint, should not fire event
		listener.setLastEvent(null);
		registeredConstraint.setEnabled(false);
		assertNull("Disabling a disabled constraint incorrectly sent an event", listener.getLastEvent()); //$NON-NLS-1$
	}

	public void test_addCategoryToConstraint_177656() {
		registeredConstraint.addCategory(testCategory);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null"); //$NON-NLS-1$
		}

		assertSame("Incorrect constraint change event", ConstraintChangeEventType.ADDED_CATEGORY, //$NON-NLS-1$
				listener.getLastEvent().getEventType());
		assertSame("Incorrect constraint descriptor", registeredConstraint, listener.getLastEvent().getConstraint()); //$NON-NLS-1$
		assertSame("Incorrect constraint category", testCategory, listener.getLastEvent().getCategory()); //$NON-NLS-1$

		// Attempt to add category again, should not fire event
		listener.setLastEvent(null);
		registeredConstraint.addCategory(testCategory);
		assertNull("Add category for already associated category incorrectly sent an event", listener.getLastEvent()); //$NON-NLS-1$
	}

	public void test_removeCategoryFromConstraint_177656() {
		registeredConstraint.addCategory(testCategory); // Ensure category exists
		registeredConstraint.removeCategory(testCategory);

		if (listener.getLastEvent() == null) {
			fail("Constraint change event was null"); //$NON-NLS-1$
		}

		assertSame("Incorrect constraint change event", ConstraintChangeEventType.REMOVED_CATEGORY, //$NON-NLS-1$
				listener.getLastEvent().getEventType());
		assertSame("Incorrect constraint descriptor", registeredConstraint, listener.getLastEvent().getConstraint()); //$NON-NLS-1$
		assertSame("Incorrect constraint category", testCategory, listener.getLastEvent().getCategory()); //$NON-NLS-1$

		// Attempt to remove category again, should not fire event
		listener.setLastEvent(null);
		registeredConstraint.removeCategory(testCategory);
		assertNull("Remove category for non-associated category incorrectly sent an event", listener.getLastEvent()); //$NON-NLS-1$
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		ConstraintRegistry.getInstance().register(registeredConstraint);

		ConstraintRegistry.getInstance().addConstraintListener(ConstraintListener.getInstance());

		listener.setLastEvent(null);
		listener.setEnabled(true);
	}

	@Override
	protected void tearDown() throws Exception {
		listener.setEnabled(false);
		listener.setLastEvent(null);

		ConstraintRegistry.getInstance().removeConstraintListener(ConstraintListener.getInstance());

		ConstraintRegistry.getInstance().unregister(registeredConstraint);

		super.tearDown();
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
