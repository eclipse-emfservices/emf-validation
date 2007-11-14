/**
 * <copyright>
 *
 * Copyright (c) 2003, 2007 IBM Corporation and others.
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

package org.eclipse.emf.validation.internal.util.tests;

import org.eclipse.emf.validation.internal.util.DisabledConstraint;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.ConstraintFactory;
import org.eclipse.emf.validation.util.XmlConfig;

import junit.framework.TestCase;

/**
 * JUnit tests for {@link ConstraintFactory} class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintFactoryTest extends TestCase {
	private final ConstraintFactory factory = ConstraintFactory.getInstance();
	
	public void test_getInstance() {
		assertSame(factory, ConstraintFactory.getInstance());
	}

	public void test_newConstraint() {
		@SuppressWarnings("deprecation")
		IModelConstraint constraint = factory.newConstraint(
				ConstraintDescriptorTest.getFixture());
		
		assertNotNull(constraint);
		assertSame(
				ConstraintDescriptorTest.getFixture(),
				constraint.getDescriptor());
	}
	
	public void test_newConstraint_disabledConstraint() {
		ConstraintDescriptorTest.FixtureElement element =
			new ConstraintDescriptorTest.FixtureElement(XmlConfig.E_CONSTRAINT);
		
		// non "class" attribute specified
		element.putAttribute(XmlConfig.A_ID, "junit.validation.util.foo"); //$NON-NLS-1$
		element.putAttribute(XmlConfig.A_NAME, "foo"); //$NON-NLS-1$
		element.putAttribute(XmlConfig.A_LANG, "Java"); //$NON-NLS-1$
		
		ConstraintDescriptorTest.FixtureElement message =
			new ConstraintDescriptorTest.FixtureElement(XmlConfig.E_MESSAGE);
		
		message.setValue("Nothing."); //$NON-NLS-1$
		
		try {
			XmlConstraintDescriptor desc = new XmlConstraintDescriptor(element);
			
			@SuppressWarnings("deprecation")
			IModelConstraint constraint = factory.newConstraint(desc);
			
			assertFalse(desc.isEnabled());
			assertTrue(desc.isError());
			assertTrue(constraint instanceof DisabledConstraint);
		} catch (ConstraintExistsException e) {
			// shouldn't happen in this test
			fail("Constraint already exists: " + e.getLocalizedMessage()); //$NON-NLS-1$
		}
	}
}
