/**
 * Copyright (c) 2003, 2007 IBM Corporation and others.
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.validation.util.FilteredCollection;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * JUnit tests for the {@link FilteredCollection} class.
 *
 * @author Christian W. Damus (cdamus)
 */
public class FilteredCollectionTest extends TestCase {
	private class Filter implements FilteredCollection.Filter<Integer> {
		// filters out odd integers
		@Override
		public boolean accept(Integer element) {
			return (element % 2) == 0;
		}
	}

	private final Filter filter = new Filter();
	private final Collection<Integer> original = Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1),
			Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5), });
	private final Collection<Integer> expectedFilteredResult = Arrays
			.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(2), Integer.valueOf(4), });
	private final FilteredCollection<Integer> filteredCollection = new FilteredCollection<>(original, filter);

	public static Test suite() {
		return new TestSuite(FilteredCollectionTest.class);
	}

	/** Tests for correct return of the filter algorithm. */
	public void test_getFilter() {
		assertSame(filter, filteredCollection.getFilter());
	}

	/** Tests for correct computation of size. */
	public void test_size() {
		assertEquals(expectedFilteredResult.size(), filteredCollection.size());
	}

	/** Tests for correct iteration. */
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
