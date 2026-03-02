/**
 * Copyright (c) 2005, 2026 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.model.tests;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.service.ClientContextManager;
import org.eclipse.emf.validation.internal.service.IClientContext;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.IClientSelector;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ordersystem.Order;
import ordersystem.OrderSystemFactory;
import ordersystem.Product;

/**
 * Documentation for <code>ClientContextManagerTest</code>.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ClientContextManagerTest {

	private static final String CONSTRAINT = "constraint";
	private static final String JUNIT_CLIENT = "org.eclipse.emf.validation.tests.junit";
	private static final String TEST_CLIENT = "junit.bindings.testClient";
	private static final String TEST_CONSTRAINT = "org.eclipse.emf.validation.tests.junit.bindings.testConstraint";

	private static final ConstraintDescriptorTest.FixtureElement constraintElement = ConstraintDescriptorTest.FixtureElement
			.build(CONSTRAINT, "true", new String[][] { { "id", TEST_CONSTRAINT }, { "lang", "OCL" } });

	private static final XmlConstraintDescriptor desc;
	private static final ClientContextManager mgr;
	private static final ConstraintDescriptorTest.FixtureElement clientElement;
	private static final ConstraintDescriptorTest.FixtureElement clientElement2;

	private IClientContext ctx;
	private IClientContext ctx2;

	private Product product;
	private Order order;

	static {
		try {
			desc = new XmlConstraintDescriptor(constraintElement);
		} catch (ConstraintExistsException e) {
			// won't happen
			throw new ExceptionInInitializerError(e);
		}

		mgr = ClientContextManager.getInstance();

		clientElement = ConstraintDescriptorTest.FixtureElement.build("clientContext",
				new String[][] { { "id", TEST_CLIENT } });
		clientElement.addChild(ConstraintDescriptorTest.FixtureElement.build("selector",
				new String[][] { { "class", TestSelector.class.getName() } }));

		clientElement2 = ConstraintDescriptorTest.FixtureElement.build("clientContext",
				new String[][] { { "id", TEST_CLIENT + '2' } });
		clientElement2.addChild(ConstraintDescriptorTest.FixtureElement.build("selector",
				new String[][] { { "class", TestSelector.class.getName() } }));
	}

	@BeforeEach
	public void setUp() {
		// this setup effectively tests the configureBindings() method,
		// including the ability of the system to ignore
		// repeated client context IDs, as the setup is run several times.
		// Likewise for ignoring repeated constraint/category bindings.
		// It also tests the getClientContext(String) method.

		ConstraintDescriptorTest.FixtureElement binding = ConstraintDescriptorTest.FixtureElement.build("binding",
				new String[][] { { "context", TEST_CLIENT }, { CONSTRAINT, TEST_CONSTRAINT } });

		ConstraintDescriptorTest.FixtureElement binding2 = ConstraintDescriptorTest.FixtureElement.build("binding",
				new String[][] { { "context", TEST_CLIENT + '2' }, { CONSTRAINT, TEST_CONSTRAINT + '2' } });

		configureConstraintBindings(mgr,
				new IConfigurationElement[] { clientElement, binding, clientElement2, binding2 });

		ctx = mgr.getClientContext(TEST_CLIENT);
		ctx2 = mgr.getClientContext(TEST_CLIENT + '2');

		product = OrderSystemFactory.eINSTANCE.createProduct();
		order = OrderSystemFactory.eINSTANCE.createOrder();
	}

	@Test
	public void getClientContexts() {
		assertTrue(mgr.getClientContexts().contains(ctx), "Test client not found");
	}

	@Test
	public void getClientContext() {
		assertSame(mgr.getClientContext(TEST_CLIENT), ctx, "Test client not found");
	}

	@Test
	public void getClientContextsFor() {
		assertTrue(mgr.getClientContextsFor(product).contains(ctx), "Test client not found");
		assertFalse(mgr.getClientContextsFor(order).contains(ctx), "Test not found");
	}

	@Test
	public void getBindings_eobject() {
		IModelConstraint constraint = new TestConstraint();
		Collection<IModelConstraint> constraints = Collections.singleton(constraint);

		assertTrue(mgr.getBindings(product, constraints).contains(constraint));
		assertFalse(mgr.getBindings(order, constraints).contains(constraint));
	}

	@Test
	public void getBindings_eobject_none() {
		IModelConstraint constraint = new TestConstraint();
		Collection<IModelConstraint> constraints = Collections.singleton(constraint);

		assertTrue(mgr.getBindings(order, constraints).isEmpty());
	}

	@Test
	public void getBindings_context() {
		IModelConstraint constraint = new TestConstraint();
		Collection<IModelConstraint> constraints = Collections.singleton(constraint);

		assertTrue(mgr.getBindings(ctx, constraints).contains(constraint));
		assertFalse(mgr.getBindings(ctx2, constraints).contains(constraint));
	}

	@Test
	public void getBindings_contexts() {
		IModelConstraint constraint = new TestConstraint();
		Collection<IModelConstraint> constraints = Collections.singleton(constraint);

		assertTrue(mgr.getBindings(Arrays.asList(ctx, ctx2), constraints).contains(constraint));
		assertFalse(mgr.getBindings(Arrays.asList(ctx2, ctx2), constraints).contains(constraint));
	}

	@Test
	public void defaultBindings_context() {
		final ConstraintDescriptorTest.FixtureElement defaultElement = ConstraintDescriptorTest.FixtureElement.build(
				CONSTRAINT, "true", new String[][] { { "id", TEST_CONSTRAINT + ".default" }, { "lang", "OCL" } });

		try {
			final XmlConstraintDescriptor descr = new XmlConstraintDescriptor(defaultElement);

			IModelConstraint constraint = new IModelConstraint() {

				@Override
				public IStatus validate(IValidationContext c) {
					return Status.OK_STATUS;
				}

				@Override
				public IConstraintDescriptor getDescriptor() {
					return descr;
				}
			};

			Collection<IModelConstraint> constraints = Collections.singleton(constraint);

			IClientContext junitCtx = mgr.getClientContext(JUNIT_CLIENT);

			assertFalse(mgr.getBindings(ctx, constraints).contains(constraint));
			assertTrue(mgr.getBindings(junitCtx, constraints).contains(constraint));
		} catch (ConstraintExistsException e) {
			fail("Should not throw exception: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void defaultBindings_contexts() {
		final ConstraintDescriptorTest.FixtureElement defaultElement = ConstraintDescriptorTest.FixtureElement.build(
				CONSTRAINT, "true", new String[][] { { "id", TEST_CONSTRAINT + ".default2" }, { "lang", "OCL" } });

		try {
			final XmlConstraintDescriptor descr = new XmlConstraintDescriptor(defaultElement);

			IModelConstraint constraint = new IModelConstraint() {

				@Override
				public IStatus validate(IValidationContext c) {
					return Status.OK_STATUS;
				}

				@Override
				public IConstraintDescriptor getDescriptor() {
					return descr;
				}
			};

			Collection<IClientContext> contexts = new java.util.ArrayList<>();
			contexts.add(ctx);
			contexts.add(ctx2);

			Collection<IModelConstraint> constraints = Collections.singleton(constraint);

			IClientContext junitCtx = mgr.getClientContext(JUNIT_CLIENT);

			assertFalse(mgr.getBindings(contexts, constraints).contains(constraint));

			contexts.add(junitCtx);

			assertTrue(mgr.getBindings(contexts, constraints).contains(constraint));
		} catch (ConstraintExistsException e) {
			fail("Should not throw exception: " + e.getLocalizedMessage());
		}
	}

	//
	// Test fixtures
	//

	/**
	 * Reflective hack to insert additional client contexts and constraint bindings
	 * for dynamically-created extension configurations.
	 */
	private static void configureConstraintBindings(ClientContextManager mgr, IConfigurationElement[] configs) {

		// hack reflective access to the private configuration methods
		Class<?>[] parameterTypes = new Class<?>[] { configs.getClass() };

		try {
			Method configureMethod = mgr.getClass().getDeclaredMethod("configureClientContexts", parameterTypes);
			try {
				configureMethod.setAccessible(true);
				configureMethod.invoke(mgr, (Object) configs);
			} finally {
				configureMethod.setAccessible(false);
			}

			configureMethod = mgr.getClass().getDeclaredMethod("configureBindings", parameterTypes);
			try {
				configureMethod.setAccessible(true);
				configureMethod.invoke(mgr, (Object) configs);
			} finally {
				configureMethod.setAccessible(false);
			}
		} catch (Exception e) {
			fail("Could not configure client contexts: " + e.getLocalizedMessage());
		}
	}

	public static final class TestSelector implements IClientSelector {
		@Override
		public boolean selects(Object object) {
			return object instanceof Product;
		}
	}

	private static final class TestConstraint implements IModelConstraint {
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
