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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.validation.internal.service.ClientContext;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.model.IClientSelector;
import org.junit.Test;

/**
 * Documentation for <code>ClientContextTest</code>.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ClientContextTest {

	private static final String CLIENT_CONTEXT = "clientContext";

	@Test
	public void initWithoutid() {
		// test that initializing a context without an ID fails
		try {
			new ClientContext(new ConstraintDescriptorTest.FixtureElement(CLIENT_CONTEXT));
			fail("Should have thrown exception.");
		} catch (CoreException e) {
			// success
		}
	}

	@Test
	public void initWithoutSelector() {
		try {
			new ClientContext(ConstraintDescriptorTest.FixtureElement.build(CLIENT_CONTEXT,
					new String[][] { { "id", "junit.testContext" } }));
			fail("Should have thrown exception.");
		} catch (CoreException e) {
			// success
		}
	}

	@Test
	public void initWithInvalidEnablement() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement
					.build(CLIENT_CONTEXT, new String[][] { { "id", "junit.testContext" } });
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement")
					.addChild(new ConstraintDescriptorTest.FixtureElement(
							// unknown expression element
							"foo")));
			new ClientContext(element);
			fail("Should have thrown exception.");
		} catch (CoreException e) {
			// success
		}
	}

	@Test
	public void initWithMissingSelector() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement
					.build(CLIENT_CONTEXT, new String[][] { { "id", "junit.testContext" } });
			element.addChild(new ConstraintDescriptorTest.FixtureElement("selector"));
			new ClientContext(element);
			fail("Should have thrown exception.");
		} catch (CoreException e) {
			// success
		}
	}

	@Test
	public void initWithInvalidSelector() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement
					.build(CLIENT_CONTEXT, new String[][] { { "id", "junit.testContext" } });
			element.addChild(ConstraintDescriptorTest.FixtureElement.build("selector",
					new String[][] { { "class", InvalidSelector.class.getName() } }));
			new ClientContext(element);
			fail("Should have thrown exception.");
		} catch (CoreException e) {
			// success
		}
	}

	@Test
	public void initWithValidExpression() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement
					.build(CLIENT_CONTEXT, new String[][] { { "id", "junit.testContext" } });
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement"))
					.addChild(ConstraintDescriptorTest.FixtureElement.build("test",
							new String[][] { { "property", "com.example.foo" }, //$NON-NLS-2$
									{ "value", "bar" } }));
			new ClientContext(element);
		} catch (CoreException e) {
			fail("Should not have thrown exception: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void initWithValidSelector() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement
					.build(CLIENT_CONTEXT, new String[][] { { "id", "junit.testContext" } });
			element.addChild(ConstraintDescriptorTest.FixtureElement.build("selector",
					new String[][] { { "class", ValidSelector.class.getName() } }));
			new ClientContext(element);
		} catch (CoreException e) {
			fail("Should not have thrown exception: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void initDefault() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement
					.build(CLIENT_CONTEXT, new String[][] { { "id", "junit.testContext" } });
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement"));

			assertFalse("Should not be default", new ClientContext(element).isDefault());

			element = ConstraintDescriptorTest.FixtureElement.build(CLIENT_CONTEXT,
					new String[][] { { "id", "junit.testContext" }, { "default", "true" } });
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement"));

			assertTrue("Should be default", new ClientContext(element).isDefault());
		} catch (CoreException e) {
			fail("Should not have thrown exception: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testEquals() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement
					.build(CLIENT_CONTEXT, new String[][] { { "id", "junit.testContext" } });
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement"));

			ConstraintDescriptorTest.FixtureElement element2 = ConstraintDescriptorTest.FixtureElement
					.build(CLIENT_CONTEXT, new String[][] { { "id", "junit.testContext2" } });
			element2.addChild(new ConstraintDescriptorTest.FixtureElement("enablement"));

			ClientContext ctx1 = new ClientContext(element);
			ClientContext ctx2 = new ClientContext(element);
			ClientContext ctx3 = new ClientContext(element2);

			assertEquals(ctx1, ctx2);
			assertFalse(ctx1.equals(ctx3));
		} catch (CoreException e) {
			fail("Should not have thrown exception: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void testHashCode() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement
					.build(CLIENT_CONTEXT, new String[][] { { "id", "junit.testContext" } });
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement"));

			ConstraintDescriptorTest.FixtureElement element2 = ConstraintDescriptorTest.FixtureElement
					.build(CLIENT_CONTEXT, new String[][] { { "id", "junit.testContext2" } });
			element2.addChild(new ConstraintDescriptorTest.FixtureElement("enablement"));

			ClientContext ctx1 = new ClientContext(element);
			ClientContext ctx2 = new ClientContext(element);
			ClientContext ctx3 = new ClientContext(element2);

			assertEquals(ctx1.hashCode(), ctx2.hashCode());
			assertTrue((ctx1.hashCode() == ctx3.hashCode()) || !ctx1.equals(ctx3));
		} catch (CoreException e) {
			fail("Should not have thrown exception: " + e.getLocalizedMessage());
		}
	}

	public static final class InvalidSelector {
		public InvalidSelector() {
			super();
		}
	}

	public static final class ValidSelector implements IClientSelector {
		@Override
		public boolean selects(Object eObject) {
			return false;
		}
	}
}
