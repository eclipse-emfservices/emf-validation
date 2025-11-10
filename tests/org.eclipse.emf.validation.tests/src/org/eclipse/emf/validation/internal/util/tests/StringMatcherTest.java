/**
 * Copyright (c) 2006, 2026 IBM Corporation and others.
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

import org.eclipse.core.text.StringMatcher;
import org.junit.Test;

/**
 * JUnit tests for {@link StringMatcher} class, to test the adoption of ICU4J.
 *
 * @author Christian W. Damus (cdamus)
 */
public class StringMatcherTest {
	/**
	 * Tests that rework for ICU4J did not regress the matcher.
	 */
	@Test
	public void test_caseSensitive_question_114105() {
		StringMatcher matcher = new StringMatcher("aba?aba", false, false);

		assertTrue(matcher.match("abaaaba"));
		assertTrue(matcher.match("abababa"));
		assertTrue(matcher.match("abacaba"));
		assertTrue(matcher.match("abaAaba"));

		assertFalse(matcher.match("abaaba"));
		assertFalse(matcher.match("abbaaba"));
		assertFalse(matcher.match("abAAaba"));
	}

	/**
	 * Tests that rework for ICU4J did not regress the matcher.
	 */
	@Test
	public void test_caseInsensitive_question_114105() {
		StringMatcher matcher = new StringMatcher("aba?aba", true, false);

		assertTrue(matcher.match("abacaba"));
		assertTrue(matcher.match("ABAaABA"));
		assertTrue(matcher.match("ABAAABA"));

		assertFalse(matcher.match("abaaba"));
		assertFalse(matcher.match("ABAABA"));
		assertFalse(matcher.match("ABBaaba"));
	}

	/**
	 * Tests that rework for ICU4J did not regress the matcher.
	 */
	@Test
	public void test_caseSensitive_asterisk_114105() {
		StringMatcher matcher = new StringMatcher("aba*aba", false, false);

		assertTrue(matcher.match("abaaaba"));
		assertTrue(matcher.match("abababa"));
		assertTrue(matcher.match("abaaba"));
		assertTrue(matcher.match("abaABCaba"));

		assertFalse(matcher.match("abAaba"));
		assertFalse(matcher.match("abbaaba"));
	}

	/**
	 * Tests that rework for ICU4J did not regress the matcher.
	 */
	@Test
	public void test_caseInsensitive_asterisk_114105() {
		StringMatcher matcher = new StringMatcher("aba*aba", true, false);

		assertTrue(matcher.match("abacaba"));
		assertTrue(matcher.match("ABAABA"));
		assertTrue(matcher.match("ABAabcaba"));

		assertFalse(matcher.match("abbaaba"));
		assertFalse(matcher.match("ABBAaba"));
	}
}
