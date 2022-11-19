/**
 * Copyright (c) 2005 IBM Corporation and others.
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.validation.internal.service.ClientContext;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.model.IClientSelector;

import junit.framework.TestCase;

/**
 * Documentation for <code>ClientContextTest</code>.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ClientContextTest extends TestCase {

	public void test_initWithoutid() {
		// test that initializing a context without an ID fails
		try {
			new ClientContext(new ConstraintDescriptorTest.FixtureElement("clientContext")); //$NON-NLS-1$
			fail("Should have thrown exception."); //$NON-NLS-1$
		} catch (CoreException e) {
			// success
		}
	}

	public void test_initWithoutSelector() {
		try {
			new ClientContext(ConstraintDescriptorTest.FixtureElement.build("clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext" } })); //$NON-NLS-1$//$NON-NLS-2$
			fail("Should have thrown exception."); //$NON-NLS-1$
		} catch (CoreException e) {
			// success
		}
	}

	public void test_initWithInvalidEnablement() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement.build(
					"clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext" } }); //$NON-NLS-1$//$NON-NLS-2$
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement") //$NON-NLS-1$
					.addChild(new ConstraintDescriptorTest.FixtureElement(
							// unknown expression element
							"foo"))); //$NON-NLS-1$
			new ClientContext(element);
			fail("Should have thrown exception."); //$NON-NLS-1$
		} catch (CoreException e) {
			// success
		}
	}

	public void test_initWithMissingSelector() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement.build(
					"clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext" } }); //$NON-NLS-1$//$NON-NLS-2$
			element.addChild(new ConstraintDescriptorTest.FixtureElement("selector")); //$NON-NLS-1$
			new ClientContext(element);
			fail("Should have thrown exception."); //$NON-NLS-1$
		} catch (CoreException e) {
			// success
		}
	}

	public void test_initWithInvalidSelector() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement.build(
					"clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext" } }); //$NON-NLS-1$//$NON-NLS-2$
			element.addChild(ConstraintDescriptorTest.FixtureElement.build("selector", //$NON-NLS-1$
					new String[][] { { "class", InvalidSelector.class.getName() } })); //$NON-NLS-1$
			new ClientContext(element);
			fail("Should have thrown exception."); //$NON-NLS-1$
		} catch (CoreException e) {
			// success
		}
	}

	public void test_initWithValidExpression() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement.build(
					"clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext" } }); //$NON-NLS-1$//$NON-NLS-2$
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement")) //$NON-NLS-1$
					.addChild(ConstraintDescriptorTest.FixtureElement.build("test", new String[][] { { "property", "com.example.foo" }, //$NON-NLS-2$ //$NON-NLS-3$
									{ "value", "bar" } })); //$NON-NLS-1$ //$NON-NLS-2$
			new ClientContext(element);
		} catch (CoreException e) {
			fail("Should not have thrown exception: " + e.getLocalizedMessage()); //$NON-NLS-1$
		}
	}

	public void test_initWithValidSelector() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement.build(
					"clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext" } }); //$NON-NLS-1$//$NON-NLS-2$
			element.addChild(ConstraintDescriptorTest.FixtureElement.build("selector", //$NON-NLS-1$
					new String[][] { { "class", ValidSelector.class.getName() } })); //$NON-NLS-1$
			new ClientContext(element);
		} catch (CoreException e) {
			fail("Should not have thrown exception: " + e.getLocalizedMessage()); //$NON-NLS-1$
		}
	}

	public void test_initDefault() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement.build(
					"clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext" } }); //$NON-NLS-1$//$NON-NLS-2$
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement")); //$NON-NLS-1$

			assertFalse("Should not be default", //$NON-NLS-1$
					new ClientContext(element).isDefault());

			element = ConstraintDescriptorTest.FixtureElement.build("clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext" }, //$NON-NLS-1$//$NON-NLS-2$
							{ "default", "true" } }); //$NON-NLS-1$//$NON-NLS-2$
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement")); //$NON-NLS-1$

			assertTrue("Should be default", //$NON-NLS-1$
					new ClientContext(element).isDefault());
		} catch (CoreException e) {
			fail("Should not have thrown exception: " + e.getLocalizedMessage()); //$NON-NLS-1$
		}
	}

	public void test_equals() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement.build(
					"clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext" } }); //$NON-NLS-1$//$NON-NLS-2$
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement")); //$NON-NLS-1$

			ConstraintDescriptorTest.FixtureElement element2 = ConstraintDescriptorTest.FixtureElement.build(
					"clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext2" } }); //$NON-NLS-1$//$NON-NLS-2$
			element2.addChild(new ConstraintDescriptorTest.FixtureElement("enablement")); //$NON-NLS-1$

			ClientContext ctx1 = new ClientContext(element);
			ClientContext ctx2 = new ClientContext(element);
			ClientContext ctx3 = new ClientContext(element2);

			assertEquals(ctx1, ctx2);
			assertFalse(ctx1.equals(ctx3));
		} catch (CoreException e) {
			fail("Should not have thrown exception: " + e.getLocalizedMessage()); //$NON-NLS-1$
		}
	}

	public void test_hashCode() {
		try {
			ConstraintDescriptorTest.FixtureElement element = ConstraintDescriptorTest.FixtureElement.build(
					"clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext" } }); //$NON-NLS-1$//$NON-NLS-2$
			element.addChild(new ConstraintDescriptorTest.FixtureElement("enablement")); //$NON-NLS-1$

			ConstraintDescriptorTest.FixtureElement element2 = ConstraintDescriptorTest.FixtureElement.build(
					"clientContext", //$NON-NLS-1$
					new String[][] { { "id", "junit.testContext2" } }); //$NON-NLS-1$//$NON-NLS-2$
			element2.addChild(new ConstraintDescriptorTest.FixtureElement("enablement")); //$NON-NLS-1$

			ClientContext ctx1 = new ClientContext(element);
			ClientContext ctx2 = new ClientContext(element);
			ClientContext ctx3 = new ClientContext(element2);

			assertEquals(ctx1.hashCode(), ctx2.hashCode());
			assertTrue((ctx1.hashCode() == ctx3.hashCode()) || !ctx1.equals(ctx3));
		} catch (CoreException e) {
			fail("Should not have thrown exception: " + e.getLocalizedMessage()); //$NON-NLS-1$
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
