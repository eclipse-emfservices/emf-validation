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
		public boolean accept(Integer element) {
			return (element % 2) == 0;
		}
	}
	
	private final Filter filter = new Filter();
	private final Collection<Integer> original = Arrays.asList(
		new Integer[] {
			new Integer(0),
			new Integer(1),
			new Integer(2),
			new Integer(3),
			new Integer(4),
			new Integer(5),
			});
	private final Collection<Integer> expectedFilteredResult = Arrays.asList(
		new Integer[] {
			new Integer(0),
			new Integer(2),
			new Integer(4),
			});
	private final FilteredCollection<Integer> filteredCollection = new FilteredCollection<Integer>(
			original,
			filter);

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
		// test for contents.  Note that lists can only be compared to lists
		assertEquals(expectedFilteredResult, new ArrayList<Integer>(filteredCollection));
		
		// test for ordering
		int i = 0;
		for (Integer next : filteredCollection) {
			assertEquals(i, next.intValue());
			
			i = i + 2;
		}
	}
}