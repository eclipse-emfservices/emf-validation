/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Wrapper for a Java 2 {@link Collection} that provides a filtered view of its
 * contents according to a client-specified filter algorithm.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class FilteredCollection extends AbstractCollection {
	/**
	 * Indicates the end of iteration.  Cannot use 'null' for this purpose, as
	 * it may legitimately be present in a collection.
	 */
	private static final Object END_TOKEN = new Object();
	
	private final Collection filteree;
	private final Filter filter;
	
	/**
	 * Interface for the algorithm that determines which elements are in and
	 * which are out of the filtered collection.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	public static interface Filter {
		/**
		 * Determines whether to accept or reject the specified
		 * <code>element</code> from the collection.
		 * 
		 * @param element an element of the filtered collection
		 * @return <CODE>true</CODE> if the <code>element</code> should be
		 *     included in the filtered view; <CODE>false</CODE>, otherwise
		 */
		boolean accept(Object element);
	}
	
	/**
	 * <p>
	 * Initializes me to filter the specified <i>collection</i>, obtained
	 * independently.
	 * </p>
	 * <p>
	 * <b>Note</b> that it is a very bad idea to modify the
	 * wrapped <code>collection</code> after creating this filtered view on it.
	 * The results are undefined, but probably not what you want.
	 * </p>
	 * 
	 * @param collection the collection that I am to filter 
	 * @param filter the filter algorithm to apply
	 */
	public FilteredCollection(Collection collection, Filter filter) {
		this.filteree = collection;
		this.filter = filter;
	}
	
	/**
	 * Retrieves the filter with which I was initialized.  Note that the result
	 * of modifying this filter's algorithm while I am using it is undefined,
	 * but not likely to be what you want.
	 * 
	 * @return my filter
	 */
	public final Filter getFilter() {
		return filter;
	}

	/**
	 * Implements the iterator for the filtered collection, which is all that
	 * is needed to complete the {@link AbstractCollection} API.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private class Iter implements Iterator { 
		private final Iterator filteredIterator = filteree.iterator();
		private Object next = END_TOKEN;
		
		/**
		 * Queries whether I have another object that matches the
		 * {@link #getFilter filter}.
		 */
		public boolean hasNext() {
			if (next == END_TOKEN) {
				while (filteredIterator.hasNext()) {
					next = filteredIterator.next();
					
					if (getFilter().accept(next)) {
						// got it
						break;
					} else {
						// try again
						next = END_TOKEN;
					}
				}
			}
			
			return next != END_TOKEN;
		}
		
		/**
		 * Retrieves the next object that matches my {@link #getFilter filter}.
		 */
		public Object next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			Object result = next;
			
			next = END_TOKEN;
			
			return result;
		}
	
		/**
		 * Modification of the underlying collection is not supported because
		 * the filtering iterator offers only a subset of it.
		 * 
		 * @throws UnsupportedOperationException always
		 */
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Obtains an iterator that dynamically filters out unwanted items using
	 * my {@link #getFilter filter} algorithm.
	 * 
	 * @return an iterator the exposes only the elements of my wrapped
	 *     collection that match my filter
	 */
	public Iterator iterator() {
		return new Iter();
	}

	/**
	 * <p>
	 * Computes the size of the filtered view, i.e.&nbsp;the number of elements
	 * in the original collection that match my {@link #getFilter filter}, by
	 * iterating myself.
	 * </p>
	 * <p>
	 * Note that my size is recounted every time that it is requested, in case
	 * my filter's algorithm is changed or the contents of the underlying
	 * collection are changed. 
	 * </p>
	 * @return the number of elements in my wrapped collection that match my
	 *      filter
	 */
	public int size() {
		int result = 0;
		
		for (Iterator iter = iterator(); iter.hasNext(); iter.next()) {
			result++;
		}
		
		return result;
	}
}
