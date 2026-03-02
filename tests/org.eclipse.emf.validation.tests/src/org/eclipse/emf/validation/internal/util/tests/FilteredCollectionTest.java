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
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.validation.util.FilteredCollection;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for the {@link FilteredCollection} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class FilteredCollectionTest {
	private class Filter implements FilteredCollection.Filter<Integer> {
		// filters out odd integers
		@Override
		public boolean accept(Integer element) {
			return (element % 2) == 0;
		}
	}

	private final Filter filter = new Filter();
	private final Collection<Integer> original = List.of(0, 1, 2, 3, 4, 5);
	private final Collection<Integer> expectedFilteredResult = List.of(0, 2, 4);
	private final FilteredCollection<Integer> filteredCollection = new FilteredCollection<>(original, filter);

	/** Tests for correct return of the filter algorithm. */
	@Test
	public void test_getFilter() {
		assertSame(filter, filteredCollection.getFilter());
	}

	/** Tests for correct computation of size. */
	@Test
	public void test_size() {
		assertEquals(expectedFilteredResult.size(), filteredCollection.size());
	}

	/** Tests for correct iteration. */
	@Test
	public void test_iterator() {
		// test for contents. Note that lists can only be compared to lists
		assertEquals(expectedFilteredResult, new ArrayList<>(filteredCollection));

		// test for ordering
		int i = 0;
		for (Integer next : filteredCollection) {
			assertEquals(i, next.intValue());

			i = i + 2;
		}
	}
}
