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

import junit.framework.TestCase;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.service.AbstractConstraintProvider;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * AbstractConstraintProviderTest
 * 
 * @author Christian W. Damus (cdamus)
 */
public class AbstractConstraintProviderTest extends TestCase {
	private static final String TEST_NS_URI = "com.ibm.example"; //$NON-NLS-1$
	
	private static class FixtureProvider extends AbstractConstraintProvider {
		Exception exception;
	}
	
	private FixtureProvider fixture;
	
	private static ConstraintDescriptorTest.FixtureElement fixtureConfig;
	
	static {
		fixtureConfig = new ConstraintDescriptorTest.FixtureElement(
				XmlConfig.E_CONSTRAINT_PROVIDER);
		
		fixtureConfig.putAttribute(XmlConfig.A_CLASS, FixtureProvider.class.getName());
		fixtureConfig.putAttribute(XmlConfig.A_CACHE, Boolean.FALSE.toString());
		
		fixtureConfig.addChild(new ConstraintDescriptorTest.FixtureElement(
			XmlConfig.E_PACKAGE)).putAttribute(
				XmlConfig.A_NAMESPACE_URI,
				TEST_NS_URI);
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
	
	public void test_setInitializationData() {
		if (getFixture().exception != null) {
			fail("Got exception: " + getFixture().exception.getLocalizedMessage()); //$NON-NLS-1$
		}
	}
	
	public void test_getUriNamespacePrefix() {
		assertEquals(TEST_NS_URI, getFixture().getNamespaceUris()[0]);
	}
}
