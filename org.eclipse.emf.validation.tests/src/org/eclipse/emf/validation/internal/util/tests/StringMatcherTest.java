/**
 * Copyright (c) 2006 IBM Corporation and others.
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

import org.eclipse.core.text.StringMatcher;

import junit.framework.TestCase;

/**
 * JUnit tests for {@link StringMatcher} class, to test the adoption of
 * ICU4J.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class StringMatcherTest extends TestCase {
	/**
	 * Tests that rework for ICU4J did not regress the matcher.
	 */
	public void test_caseSensitive_question_114105() {
		StringMatcher matcher = new StringMatcher("aba?aba", false, false); //$NON-NLS-1$
		
		assertTrue(matcher.match("abaaaba")); //$NON-NLS-1$
		assertTrue(matcher.match("abababa")); //$NON-NLS-1$
		assertTrue(matcher.match("abacaba")); //$NON-NLS-1$
		assertTrue(matcher.match("abaAaba")); //$NON-NLS-1$
		
		assertFalse(matcher.match("abaaba")); //$NON-NLS-1$
		assertFalse(matcher.match("abbaaba")); //$NON-NLS-1$
		assertFalse(matcher.match("abAAaba")); //$NON-NLS-1$
	}
	
	/**
	 * Tests that rework for ICU4J did not regress the matcher.
	 */
	public void test_caseInsensitive_question_114105() {
		StringMatcher matcher = new StringMatcher("aba?aba", true, false); //$NON-NLS-1$
		
		assertTrue(matcher.match("abacaba")); //$NON-NLS-1$
		assertTrue(matcher.match("ABAaABA")); //$NON-NLS-1$
		assertTrue(matcher.match("ABAAABA")); //$NON-NLS-1$
		
		assertFalse(matcher.match("abaaba")); //$NON-NLS-1$
		assertFalse(matcher.match("ABAABA")); //$NON-NLS-1$
		assertFalse(matcher.match("ABBaaba")); //$NON-NLS-1$
	}
	
	/**
	 * Tests that rework for ICU4J did not regress the matcher.
	 */
	public void test_caseSensitive_asterisk_114105() {
		StringMatcher matcher = new StringMatcher("aba*aba", false, false); //$NON-NLS-1$
		
		assertTrue(matcher.match("abaaaba")); //$NON-NLS-1$
		assertTrue(matcher.match("abababa")); //$NON-NLS-1$
		assertTrue(matcher.match("abaaba")); //$NON-NLS-1$
		assertTrue(matcher.match("abaABCaba")); //$NON-NLS-1$
		
		assertFalse(matcher.match("abAaba")); //$NON-NLS-1$
		assertFalse(matcher.match("abbaaba")); //$NON-NLS-1$
	}
	
	/**
	 * Tests that rework for ICU4J did not regress the matcher.
	 */
	public void test_caseInsensitive_asterisk_114105() {
		StringMatcher matcher = new StringMatcher("aba*aba", true, false); //$NON-NLS-1$
		
		assertTrue(matcher.match("abacaba")); //$NON-NLS-1$
		assertTrue(matcher.match("ABAABA")); //$NON-NLS-1$
		assertTrue(matcher.match("ABAabcaba")); //$NON-NLS-1$
		
		assertFalse(matcher.match("abbaaba")); //$NON-NLS-1$
		assertFalse(matcher.match("ABBAaba")); //$NON-NLS-1$
	}
}
