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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.validation.util.XmlConfig;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link XmlConfig} utility class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class XmlConfigTest {
	private static final String PLUGIN_ID = "org.eclipse.emf.validation.tests";
	private static final String TEST_ID = "test-include";
	private static final String TEST_NAME = "Test Include";

	private IConfigurationElement getFixture() {
		// work around a bug in the IPluginDescriptor.getExtension(String id)
		// method that does not account for extensions that do not have IDs
		// and, therefore, throws NPEs
		IExtension[] exts = Platform.getExtensionRegistry().getExtensions(PLUGIN_ID);

		for (IExtension ext : exts) {
			if ((ext.getSimpleIdentifier() != null) && ext.getSimpleIdentifier().equals(TEST_ID)) {
				return ext.getConfigurationElements()[0];
			}
		}
		return null;
	}

	@Test
	public void test_parseConstraintsWithIncludes() {
		IConfigurationElement constraints = getFixture();

		IConfigurationElement newConstraints = null;
		try {
			newConstraints = XmlConfig.parseConstraintsWithIncludes(constraints);
		} catch (CoreException e) {
			fail("Failed to parse: " + e.getLocalizedMessage());
		}

		assertNotSame(constraints, newConstraints);
		assertEquals(constraints.getName(), newConstraints.getName());

		IConfigurationElement[] children = newConstraints.getChildren();

		// the <include> element is replaced by a single <constraint> element
		assertEquals(1, children.length);
		assertEquals(XmlConfig.E_CONSTRAINT, children[0].getName());
		assertEquals(TEST_ID, children[0].getAttribute(XmlConfig.A_ID));
		assertEquals("OCL", children[0].getAttribute(XmlConfig.A_LANG));
		assertEquals("1", children[0].getAttribute(XmlConfig.A_STATUS_CODE));
		assertEquals("true", children[0].getValue());

		IConfigurationElement[] grandchildren = children[0].getChildren();

		assertEquals(1, grandchildren.length);

		IConfigurationElement message = grandchildren[0];

		assertEquals(XmlConfig.E_MESSAGE, message.getName());
		assertEquals("This is a message.", message.getValue());
	}

	@Test
	public void test_constraintLocalization() {
		IConfigurationElement constraints = getFixture();

		IConfigurationElement newConstraints = null;
		try {
			newConstraints = XmlConfig.parseConstraintsWithIncludes(constraints);
		} catch (CoreException e) {
			fail("Failed to parse: " + e.getLocalizedMessage());
		}

		IConfigurationElement[] children = newConstraints.getChildren();

		// the <include> element is replaced by a single <constraint> element
		assertTrue(children.length > 0, "Not enough child elements");
		assertEquals(TEST_NAME, children[0].getAttribute(XmlConfig.A_NAME));
	}
}
