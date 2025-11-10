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
package org.eclipse.emf.validation.internal.util.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.validation.internal.service.impl.tests.ConstraintDescriptorTest;
import org.eclipse.emf.validation.internal.util.XmlExpressionSelector;
import org.eclipse.emf.validation.util.XmlConfig;
import org.junit.Before;
import org.junit.Test;

import ordersystem.Order;
import ordersystem.OrderSystemFactory;

/**
 * Unit tests for the {@link XmlConfig} utility class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class XmlExpressionSelectorTest {
	private OrderSystemFactory fact;
	private ConstraintDescriptorTest.FixtureElement expression;

	@Before
	public void setUp() {
		fact = OrderSystemFactory.eINSTANCE;

		expression = new ConstraintDescriptorTest.FixtureElement("enablement");
		expression.addChild(ConstraintDescriptorTest.FixtureElement.build("instanceof",
				new String[][] { { "value", Order.class.getName() } }));
	}

	@Test
	public void test_selects() {
		try {
			XmlExpressionSelector sel = new XmlExpressionSelector(expression);

			EvaluationContext ctx = new EvaluationContext(null, fact.createOrder());
			assertTrue(sel.selects(ctx));

			ctx = new EvaluationContext(null, fact.createProduct());
			assertFalse(sel.selects(ctx));
		} catch (CoreException e) {
			fail("Should not throw exception: " + e.getLocalizedMessage());
		}
	}

	@Test
	public void test_init() {
		try {
			expression.addChild(new ConstraintDescriptorTest.FixtureElement("foo"));

			new XmlExpressionSelector(expression);

			fail("Should throw exception.");
		} catch (CoreException e) {
			// success
		}
	}
}
