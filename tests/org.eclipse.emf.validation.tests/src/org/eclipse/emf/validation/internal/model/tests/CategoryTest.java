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
package org.eclipse.emf.validation.internal.model.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.CategoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the basic functionality of the {@link CategoryManager} and
 * {@link Category} classes.
 *
 * @author Christian W. Damus (cdamus)
 */
public class CategoryTest {
	private CategoryManager mgr;
	
	@BeforeEach
	public void init() {
		mgr = CategoryManager.getInstance();
	}

	/**
	 * Tests the access to categories.
	 */
	@Test
	public void getCategory() {
		Trace.trace(">>> Testing getCategory");
		Category top = mgr.getCategory("$test");
		assertNotNull(top);

		Category child = mgr.getCategory(top, "///$child");
		assertNotNull(child);

		assertSame(top, child.getParent());

		Category childAgain = mgr.getCategory("$test/$child");
		assertSame(child, childAgain);

		Category grandChild = mgr.getCategory("$test/$child/$grand");
		assertNotNull(grandChild);
		assertSame(grandChild, mgr.getCategory(top, "$child/$grand"));
	}

	/**
	 * Tests the access to categories.
	 */
	@Test
	public void findCategory() {
		Trace.trace(">>> Testing findCategory");

		// these should all have been loaded from the extension point
		Category top = mgr.findCategory("junit");
		assertNotNull(top);

		Category child = mgr.findCategory(top, "///validation");
		assertNotNull(child);

		Category sameChild = mgr.findCategory("junit/validation");
		assertNotNull(sameChild);
		assertSame(child, sameChild);

		// these should not exist
		Category notThere = mgr.findCategory("$boo");
		assertNull(notThere);

		notThere = mgr.findCategory("junit/$boo");
		assertNull(notThere);

		notThere = mgr.findCategory(top, "$boo");
		assertNull(notThere);
	}

	/**
	 * Tests the consistency of the top-level categories query.
	 */
	@Test
	public void getTopLevelCategories() {
		Collection<Category> topLevel = mgr.getTopLevelCategories();

		for (Category next : topLevel) {
			assertNull(next.getParent());
		}
	}

	/**
	 * Tests the consistency of the mandatory categories query.
	 */
	@Test
	public void getMandatoryCategories() {
		Collection<Category> topLevel = mgr.getMandatoryCategories();

		for (Category next : topLevel) {
			assertTrue(next.isMandatory());
		}
	}

	/**
	 * Tests the consistency of the default category query.
	 */
	@Test
	public void getDefaultCategory() {
		Category dflt = mgr.getDefaultCategory();

		assertEquals("", dflt.getId());
		assertEquals("(default)", dflt.getName());
		assertNull(dflt.getParent());
	}

	/**
	 * Tests the display of categories.
	 */
	@Test
	public void dumpCategories() {
		Trace.trace(">>> Testing dumpCategories");

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
			Trace.trace("    " + next);
		}

		for (Category child : category.getChildren()) {
			dumpCategory(child);
		}
	}
}
