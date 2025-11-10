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
package org.eclipse.emf.validation.internal.service.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.service.AbstractConstraintProvider;
import org.eclipse.emf.validation.util.XmlConfig;
import org.junit.Test;

/**
 * AbstractConstraintProviderTest
 *
 * @author Christian W. Damus (cdamus)
 */
public class AbstractConstraintProviderTest {
	private static final String TEST_NS_URI = "com.ibm.example";

	private static class FixtureProvider extends AbstractConstraintProvider {
		Exception exception;
	}

	private FixtureProvider fixture;

	private static ConstraintDescriptorTest.FixtureElement fixtureConfig;

	static {
		fixtureConfig = new ConstraintDescriptorTest.FixtureElement(XmlConfig.E_CONSTRAINT_PROVIDER);

		fixtureConfig.putAttribute(XmlConfig.A_CLASS, FixtureProvider.class.getName());
		fixtureConfig.putAttribute(XmlConfig.A_CACHE, Boolean.FALSE.toString());

		fixtureConfig.addChild(new ConstraintDescriptorTest.FixtureElement(XmlConfig.E_PACKAGE))
				.putAttribute(XmlConfig.A_NAMESPACE_URI, TEST_NS_URI);
	}

	private FixtureProvider getFixture() {
		if (fixture == null) {
			fixture = new FixtureProvider();

			try {
				fixture.setInitializationData(fixtureConfig, XmlConfig.A_CLASS, null);
			} catch (CoreException e) {
				// store for later retrieval
				fixture.exception = e;
			}
		}

		return fixture;
	}

	@Test
	public void test_setInitializationData() {
		if (getFixture().exception != null) {
			fail("Got exception: " + getFixture().exception.getLocalizedMessage());
		}
	}

	@Test
	public void test_getUriNamespacePrefix() {
		assertEquals(TEST_NS_URI, getFixture().getNamespaceUris()[0]);
	}
}
