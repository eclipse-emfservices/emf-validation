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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.internal.util.JavaConstraintParser;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintParser;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.ConstraintParserException;
import org.junit.Test;

/**
 * JUnit tests for the {@link JavaConstraintParser} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class JavaConstraintParserTest {
	IParameterizedConstraintParser parser = new JavaConstraintParser();

	@Test
	public void test_parseConstraint() {
		try {
			// this descriptor is known to exist and be parsable
			parser.parseConstraint((IParameterizedConstraintDescriptor) ConstraintRegistry.getInstance()
					.getDescriptor("org.eclipse.emf.validation.tests", "bad.constraint.disabled.runtime"));
		} catch (ConstraintParserException e) {
			fail("Got exception: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void test_parseConstraint_throws() {
		ConstraintDescriptorTest.FixtureElement element = new ConstraintDescriptorTest.FixtureElement(
				XmlConfig.E_CONSTRAINT);

		// non "class" attribute specified
		element.putAttribute(XmlConfig.A_ID, "junit.validation.util.foo");
		element.putAttribute(XmlConfig.A_NAME, "foo");
		element.putAttribute(XmlConfig.A_LANG, "Java");

		ConstraintDescriptorTest.FixtureElement message = new ConstraintDescriptorTest.FixtureElement(
				XmlConfig.E_MESSAGE);

		message.setValue("Nothing.");

		try {
			XmlConstraintDescriptor desc = new XmlConstraintDescriptor(element);

			try {
				parser.parseConstraint(desc);

				fail("Did not get an exception.");
			} catch (ConstraintParserException e) {
				// success
			}
		} catch (ConstraintExistsException e) {
			// shouldn't happen in this test
			fail("Constraint already exists: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void test_getInstance() {
		ConstraintDescriptorTest.FixtureElement config = ConstraintDescriptorTest.FixtureElement.build(
				XmlConfig.E_CONSTRAINT,
				new String[][] { { XmlConfig.A_ID, "constraint.parser.test.constraint1" },
						{ XmlConfig.A_NAME, "Constraint Parser Test Constraint" }, { XmlConfig.A_LANG, "OCL" },
						{ XmlConfig.A_STATUS_CODE, "1" },
						{ XmlConfig.A_CLASS, ConstraintUniquenessTest.class.getName() } });
		config.addChild(ConstraintDescriptorTest.FixtureElement.build(XmlConfig.E_MESSAGE, "No message."));

		try {
			assertEquals(0, ConstraintUniquenessTest.instanceCount);

			IModelConstraint constraint1 = parser.parseConstraint(new XmlConstraintDescriptor(config));

			assertEquals(1, ConstraintUniquenessTest.instanceCount);

			// change the ID so we don't get an exists exception
			config.putAttribute(XmlConfig.A_ID, "constraint.parser.test.constraint2");
			IModelConstraint constraint2 = parser.parseConstraint(new XmlConstraintDescriptor(config));

			// must not have created a new instance
			assertEquals(1, ConstraintUniquenessTest.instanceCount);

			// must get different constraint objects, but implemented by the
			// same instance underneath
			assertNotSame(constraint1, constraint2);
		} catch (ConstraintExistsException e) {
			fail("Got exception: " + e.getLocalizedMessage());
		} catch (ConstraintParserException e) {
			fail("Got exception: " + e.getLocalizedMessage());
		}
	}

	/**
	 * A test constraint implementation for checking that multiple constraints
	 * implemented by a single class do not result in multiple instantations of that
	 * class.
	 */
	public static class ConstraintUniquenessTest extends AbstractModelConstraint {
		static int instanceCount = 0;

		public ConstraintUniquenessTest() {
			instanceCount++;
		}

		// Dummy implementation to make the compiler happy
		@Override
		public IStatus validate(IValidationContext ctx) {
			return Status.OK_STATUS;
		}
	}
}
