/**
 * <copyright>
 *
 * Copyright (c) 2005, 2007 IBM Corporation and others.
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

import junit.framework.TestCase;
import ordersystem.Order;
import ordersystem.OrderSystemFactory;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.internal.util.XmlExpressionSelector;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * Unit tests for the {@link XmlConfig} utility class.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class XmlExpressionSelectorTest extends TestCase {
	private OrderSystemFactory fact;
	private ConstraintDescriptorTest.FixtureElement expression;
	
	@Override
    protected void setUp() {
		fact = OrderSystemFactory.eINSTANCE;
		
		expression = new ConstraintDescriptorTest.FixtureElement(
			"enablement");  //$NON-NLS-1$
		expression.addChild(ConstraintDescriptorTest.FixtureElement.build(
			"instanceof", //$NON-NLS-1$
			new String[][] {
				{"value", Order.class.getName()}}));  //$NON-NLS-1$
	}
	
	public void test_selects() {
		try {
			XmlExpressionSelector sel = new XmlExpressionSelector(expression);
			
			EvaluationContext ctx = new EvaluationContext(null, fact.createOrder());
			assertTrue(sel.selects(ctx));
			
			ctx = new EvaluationContext(null, fact.createProduct());
			assertFalse(sel.selects(ctx));
		} catch (CoreException e) {
			fail("Should not throw exception: " + e.getLocalizedMessage()); //$NON-NLS-1$
		}
	}
	
	public void test_init() {
		try {
			expression.addChild(new ConstraintDescriptorTest.FixtureElement(
				"foo")); //$NON-NLS-1$
			
			new XmlExpressionSelector(expression);
			
			fail("Should throw exception."); //$NON-NLS-1$
		} catch (CoreException e) {
			// success
		}
	}
}
