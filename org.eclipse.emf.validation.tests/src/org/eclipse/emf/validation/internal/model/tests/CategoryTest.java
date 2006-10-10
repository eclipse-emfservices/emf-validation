/**
 * <copyright>
 *
 * Copyright (c) 2003, 2006 IBM Corporation and others.
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

package org.eclipse.emf.validation.internal.model.tests;

import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.CategoryManager;

import java.util.Collection;
import java.util.Iterator;

import junit.framework.TestCase;

/**
 * Tests the basic functionality of the {@link CategoryManager} and
 * {@link Category} classes.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class CategoryTest extends TestCase {
	private static CategoryManager mgr = CategoryManager.getInstance();
	
	/**
	 * Tests the access to categories.
	 */
	public void test_getCategory() {
		Trace.trace(">>> Testing getCategory"); //$NON-NLS-1$
		
		Category top = mgr.getCategory("$test");//$NON-NLS-1$
		
		assertNotNull(top);
		
		Category child = mgr.getCategory(top, "///$child");//$NON-NLS-1$
		
		assertNotNull(child);
		
		assertSame(top, child.getParent());
		
		Category childAgain = mgr.getCategory("$test/$child");//$NON-NLS-1$
		
		assertSame(child, childAgain);
		
		Category grandChild = mgr.getCategory("$test/$child/$grand");//$NON-NLS-1$
		
		assertNotNull(grandChild);
		assertSame(grandChild, mgr.getCategory(top, "$child/$grand"));//$NON-NLS-1$
	}
	
	/**
	 * Tests the access to categories.
	 */
	public void test_findCategory() {
		Trace.trace(">>> Testing findCategory"); //$NON-NLS-1$
		
		// these should all have been loaded from the extension point
		
		Category top = mgr.findCategory("junit");//$NON-NLS-1$
		
		assertNotNull(top);
		
		Category child = mgr.findCategory(top, "///validation");//$NON-NLS-1$
		
		assertNotNull(child);
		
		Category sameChild = mgr.findCategory("junit/validation");//$NON-NLS-1$
		
		assertNotNull(sameChild);
		assertSame(child, sameChild);
		
		// these should not exist
		
		Category notThere = mgr.findCategory("$boo");//$NON-NLS-1$
		
		assertNull(notThere);
		
		notThere = mgr.findCategory("junit/$boo");//$NON-NLS-1$
		
		assertNull(notThere);
		
		notThere = mgr.findCategory(top, "$boo");//$NON-NLS-1$
		
		assertNull(notThere);
	}
	
	/**
	 * Tests the consistency of the top-level categories query.
	 */
	public void test_getTopLevelCategories() {
		Collection topLevel = mgr.getTopLevelCategories();
		
		for (Iterator iter = topLevel.iterator(); iter.hasNext();) {
			assertNull(((Category)iter.next()).getParent());
		}
	}
	
	/**
	 * Tests the consistency of the mandatory categories query.
	 */
	public void test_getMandatoryCategories() {
		Collection topLevel = mgr.getMandatoryCategories();
		
		for (Iterator iter = topLevel.iterator(); iter.hasNext();) {
			assertTrue(((Category)iter.next()).isMandatory());
		}
	}
	
	/**
	 * Tests the consistency of the default category query.
	 */
	public void test_getDefaultCategory() {
		Category dflt = mgr.getDefaultCategory();
		
		assertEquals("", dflt.getId()); //$NON-NLS-1$
		assertEquals("(default)", dflt.getName()); //$NON-NLS-1$
		assertNull(dflt.getParent());
	}
	
	/**
	 * Tests the display of categories.
	 */
	public void test_dumpCategories() {
		Trace.trace(">>> Testing dumpCategories"); //$NON-NLS-1$
		
		for (Iterator iter = mgr.getTopLevelCategories().iterator(); iter.hasNext();) {
			dumpCategory((Category)iter.next());
		}
	}
	
	/**
	 * Dumps a category to stdout.
	 * 
	 * @param category the category
	 */
	private void dumpCategory(Category category) {
		Trace.trace(category.toString());
		
		for (Iterator iter = category.getConstraints().iterator(); iter.hasNext();) {
			Trace.trace("    " + iter.next()); //$NON-NLS-1$
		}
		
		for (Iterator iter = category.getChildren().iterator(); iter.hasNext();) {
			dumpCategory((Category)iter.next());
		}
	}
}
