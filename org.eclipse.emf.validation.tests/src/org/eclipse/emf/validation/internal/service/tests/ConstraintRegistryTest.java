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

package org.eclipse.emf.validation.internal.service.tests;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.AbstractConstraintDescriptor;
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
		descriptor = new FixtureDescriptor(
				TestBase.ID_PREFIX + TEST_ID);
		
		try {
			ConstraintRegistry.getInstance().register(descriptor);
		} catch (ConstraintExistsException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * Constructor for ConstraintRegistryTest.
	 * @param name
	 */
	public ConstraintRegistryTest(String name) {
		super(name);
	}

	public void testGetInstance() {
		ConstraintRegistry reg = ConstraintRegistry.getInstance();
		
		assertNotNull("Registry is null", reg); //$NON-NLS-1$
		
		assertSame(
				"Registry is not singleton", //$NON-NLS-1$
				reg,
				ConstraintRegistry.getInstance());
	}

	/*
	 * Class to test for IConstraintDescriptor getDescriptor(String)
	 */
	public void test_getDescriptor_String() {
		IConstraintDescriptor found =
			ConstraintRegistry.getInstance().getDescriptor(
				TestBase.ID_PREFIX + TEST_ID);
		
		assertNotNull("Test descriptor not found", found); //$NON-NLS-1$
		assertSame("Wrong test descriptor found", descriptor, found); //$NON-NLS-1$
	}

	/*
	 * Class to test for IConstraintDescriptor getDescriptor(String, String)
	 */
	public void testGetDescriptorStringString() {
		IConstraintDescriptor found =
			ConstraintRegistry.getInstance().getDescriptor(
				TestBase.PLUGIN_ID,
				TEST_ID);
		
		assertNotNull("Test descriptor not found", found); //$NON-NLS-1$
		assertSame("Wrong test descriptor found", descriptor, found); //$NON-NLS-1$
	}

	public void testGetAllDescriptors() {
		Collection allFound =
			ConstraintRegistry.getInstance().getAllDescriptors();
		
		assertNotNull(allFound);
		
		assertTrue("Test descriptor missing", allFound.contains(descriptor)); //$NON-NLS-1$
		
		// there should be plenty other descriptors registered
		assertTrue("Not enough descriptors found", allFound.size() > 1); //$NON-NLS-1$
	}

	public void testRegister() {
		// setUp() tests successful registration.  Make sure that the exception
		//    occurs when necessary
		
		try {
			ConstraintRegistry.getInstance().register(descriptor);
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
		
		assertNull(
				"Descriptor not unregistered", //$NON-NLS-1$
				reg.getDescriptor(descriptor.getId()));
		
		try {
			reg.register(descriptor);
		} catch (ConstraintExistsException e) {
			fail("ConstraintRegistry.register() should not have thrown."); //$NON-NLS-1$
		}
	}

	private static class FixtureDescriptor extends AbstractConstraintDescriptor {
		private final String id;
		
		FixtureDescriptor(String id) {
			this.id = id;
		}
				
		public String getId() {
			return id;
		}

		public String getPluginId() {
			return TestBase.PLUGIN_ID;
		}

		public String getMessagePattern() {
			return null;
		}

		public String getBody() {
			return null;
		}

		public String getName() {
			return null;
		}

		public String getDescription() {
			return null;
		}

		public ConstraintSeverity getSeverity() {
			return null;
		}

		public int getStatusCode() {
			return 0;
		}
		
		public EvaluationMode getEvaluationMode() {
			return null;
		}

		public boolean targetsTypeOf(EObject eObject) {
			return false;
		}

		public boolean targetsEvent(Notification notification) {
			return false;
		}

		public boolean targetsFeature(EObject eObject, String feature) {
			return false;
		}
	}
}
