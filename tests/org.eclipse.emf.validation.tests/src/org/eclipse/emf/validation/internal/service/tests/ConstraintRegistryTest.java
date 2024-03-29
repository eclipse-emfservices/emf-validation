/**
 * Copyright (c) 2003, 2007 IBM Corporation and others.
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

import java.util.Collection;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.AbstractConstraintDescriptor;
import org.eclipse.emf.validation.service.AbstractConstraintProvider;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.tests.TestBase;

/**
 * JUnit tests for the {@link ConstraintRegistryTest} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintRegistryTest extends TestBase {
	private static final String TEST_ID = "test.registry"; //$NON-NLS-1$
	private static IConstraintDescriptor descriptor;

	static {
		descriptor = new FixtureDescriptor(TestBase.ID_PREFIX + TEST_ID);

		try {
			ConstraintRegistry.getInstance().register(descriptor);
		} catch (ConstraintExistsException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Constructor for ConstraintRegistryTest.
	 *
	 * @param name
	 */
	public ConstraintRegistryTest(String name) {
		super(name);
	}

	public void testGetInstance() {
		ConstraintRegistry reg = ConstraintRegistry.getInstance();

		assertNotNull("Registry is null", reg); //$NON-NLS-1$

		assertSame("Registry is not singleton", //$NON-NLS-1$
				reg, ConstraintRegistry.getInstance());
	}

	/*
	 * Class to test for IConstraintDescriptor getDescriptor(String)
	 */
	public void test_getDescriptor_String() {
		IConstraintDescriptor found = ConstraintRegistry.getInstance().getDescriptor(TestBase.ID_PREFIX + TEST_ID);

		assertNotNull("Test descriptor not found", found); //$NON-NLS-1$
		assertSame("Wrong test descriptor found", descriptor, found); //$NON-NLS-1$
	}

	/*
	 * Class to test for IConstraintDescriptor getDescriptor(String, String)
	 */
	public void testGetDescriptorStringString() {
		IConstraintDescriptor found = ConstraintRegistry.getInstance().getDescriptor(TestBase.PLUGIN_ID, TEST_ID);

		assertNotNull("Test descriptor not found", found); //$NON-NLS-1$
		assertSame("Wrong test descriptor found", descriptor, found); //$NON-NLS-1$
	}

	public void testGetAllDescriptors() {
		Collection<IConstraintDescriptor> allFound = ConstraintRegistry.getInstance().getAllDescriptors();

		assertNotNull(allFound);

		assertTrue("Test descriptor missing", allFound.contains(descriptor)); //$NON-NLS-1$

		// there should be plenty other descriptors registered
		assertTrue("Not enough descriptors found", allFound.size() > 1); //$NON-NLS-1$
	}

	public void testRegister() {
		// setUp() tests successful registration. Make sure that the exception
		// occurs when necessary

		try {
			FixtureDescriptor duplicate = new FixtureDescriptor(descriptor.getId());
			ConstraintRegistry.getInstance().register(duplicate);
			fail("ConstraintRegistry.register() did not throw."); //$NON-NLS-1$
		} catch (ConstraintExistsException e) {
			// success
		} catch (Exception e) {
			fail("Unexpected exception type thrown by register(): " + e); //$NON-NLS-1$
		}
	}

	public void testUnregister() {
		ConstraintRegistry reg = ConstraintRegistry.getInstance();

		reg.unregister(descriptor);

		assertNull("Descriptor not unregistered", //$NON-NLS-1$
				reg.getDescriptor(descriptor.getId()));

		try {
			reg.register(descriptor);
		} catch (ConstraintExistsException e) {
			fail("ConstraintRegistry.register() should not have thrown."); //$NON-NLS-1$
		}
	}

	public void test_repeatedlyRegisterSameDescriptor() {
		try {
			ConstraintRegistry.getInstance().unregister(descriptor);

			ConstraintListener.getInstance().reset();

			ConstraintRegistry.getInstance().register(descriptor);
			ConstraintRegistry.getInstance().register(descriptor);
			ConstraintRegistry.getInstance().register(descriptor);

			// only one event sent
			assertEquals(1, ConstraintListener.getInstance().getEventCount());
		} catch (ConstraintExistsException e) {
			// success
			fail("ConstraintRegistry.register() should not have thrown: " + e.getLocalizedMessage()); //$NON-NLS-1$
		} catch (Exception e) {
			fail("Unexpected exception type thrown by register(): " + e); //$NON-NLS-1$
		}
	}

	public void test_repeatedlyUnregisterSameDescriptor() {
		ConstraintRegistry reg = ConstraintRegistry.getInstance();

		try {
			ConstraintListener.getInstance().reset();

			reg.unregister(descriptor);
			reg.unregister(descriptor);
			reg.unregister(descriptor);

			// only one event sent
			assertEquals(1, ConstraintListener.getInstance().getEventCount());
		} finally {
			try {
				reg.register(descriptor);
			} catch (ConstraintExistsException e) {
				fail("ConstraintRegistry.register() should not have thrown."); //$NON-NLS-1$
			}
		}
	}

	public void test_bulkRegister() {
		ConstraintListener.getInstance().reset();

		Collection<IModelConstraint> constraints = new java.util.ArrayList<>();
		constraints.add(new FixtureConstraint());
		constraints.add(new FixtureConstraint());
		constraints.add(new FixtureConstraint());
		constraints.add(new FixtureConstraint());

		class ProviderAccess extends AbstractConstraintProvider {
			@Override
			public void registerConstraints(Collection<? extends IModelConstraint> constraints)
					throws ConstraintExistsException {
				super.registerConstraints(constraints);
			}
		}

		try {
			new ProviderAccess().registerConstraints(constraints);
		} catch (ConstraintExistsException e) {
			fail("Should not have thrown ConstraintExistsException: " + e.getLocalizedMessage()); //$NON-NLS-1$
		}

		// must get all events
		assertEquals(constraints.size(), ConstraintListener.getInstance().getEventCount());
	}

	//
	// Test fixtures
	//

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		ConstraintListener listener = ConstraintListener.getInstance();
		ConstraintRegistry.getInstance().addConstraintListener(listener);

		listener.reset();
		listener.setEnabled(true);
	}

	@Override
	protected void tearDown() throws Exception {
		ConstraintListener listener = ConstraintListener.getInstance();

		listener.setEnabled(false);
		listener.reset();

		ConstraintRegistry.getInstance().removeConstraintListener(listener);

		super.tearDown();
	}

	private static class FixtureDescriptor extends AbstractConstraintDescriptor {
		private final String id;

		FixtureDescriptor(String id) {
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
			return null;
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

	private static class FixtureConstraint implements IModelConstraint {
		private static int counter = 0;
		private final FixtureDescriptor desc = new FixtureDescriptor(descriptor.getId() + "$" + (counter++)); //$NON-NLS-1$

		@Override
		public IConstraintDescriptor getDescriptor() {
			return desc;
		}

		@Override
		public IStatus validate(IValidationContext ctx) {
			return Status.OK_STATUS;
		}
	}
}
