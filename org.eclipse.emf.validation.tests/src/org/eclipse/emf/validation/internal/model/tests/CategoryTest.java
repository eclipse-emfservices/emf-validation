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
package org.eclipse.emf.validation.internal.model.tests;

import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.CategoryManager;

import java.util.Collection;
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
		Collection<Category> topLevel = mgr.getTopLevelCategories();
		
		for (Category next : topLevel) {
			assertNull(next.getParent());
		}
	}
	
	/**
	 * Tests the consistency of the mandatory categories query.
	 */
	public void test_getMandatoryCategories() {
		Collection<Category> topLevel = mgr.getMandatoryCategories();
		
		for (Category next : topLevel) {
			assertTrue(next.isMandatory());
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
		
		for (Category next : mgr.getTopLevelCategories()) {
			dumpCategory(next);
		}
	}
	
	/**
	 * Dumps a category to stdout.
	 * 
	 * @param category the category
	 */
	private void dumpCategory(Category category) {
		Trace.trace(category.toString());
		
		for (Object next : category.getConstraints()) {
			Trace.trace("    " + next); //$NON-NLS-1$
		}
		
		for (Category child : category.getChildren()) {
			dumpCategory(child);
		}
	}
}
