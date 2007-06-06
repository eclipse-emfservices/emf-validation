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


package org.eclipse.emf.validation.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * <p>
 * Central point by which clients discover the available
 * {@link Category categories} and {@link IConstraintDescriptor constraints}
 * in the system.  The <code>CategoryManager</code> is responsible for
 * loading and maintaining all <code>Category</code> instances.
 * </p>
 * <p>
 * This class is intended to be used by clients of the validation framework.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class CategoryManager {
	static final String DEFAULT_CATEGORY_NAME = ValidationMessages.category_default_name;
	static final String DEFAULT_CATEGORY_DESCRIPTION = ValidationMessages.category_default_desc;
	
	private static final CategoryManager INSTANCE = new CategoryManager();
	
	private final Category globalCategory = Category.GLOBAL_NAMESPACE;
	
	static {
		// these methods (transitively) need to access the INSTANCE through
		//   the getInstance() method, so it must already be assigned before
		//   these methods are called
		INSTANCE.initDefaultCategory();
		INSTANCE.loadCategories();
	}
	
	/**
	 * I cannot be instantiated by clients.
	 */
	private CategoryManager() {
		super();
	}

	/**
	 * Obtains the singleton instance of this class.
	 * 
	 * @return the category manager instance
	 */
	public static CategoryManager getInstance() {
		return INSTANCE;
	}
	
	/**
	 * The top-level categories.
	 * 
	 * @return an unmodifiable set of {@link Category}s, sorted by their
	 *     names
	 */
	public SortedSet getTopLevelCategories() {
		return globalCategory.getChildren();
	}
	
	/**
	 * Retrieves the default category which contains all constraints that are
	 * not explicitly categorized.
	 * 
	 * @return the default category
	 */
	public Category getDefaultCategory() {
		return Category.DEFAULT_CATEGORY;
	}

	/**
	 * Obtains the category that has the specified absolute <code>path</code>.
	 * If this category does not yet exist, it is implicitly created.
	 * 
	 * @param path the absolute path of the category
	 * @return the specified category (never <code>null</code>)
	 */
	public Category getCategory(String path) {
		return globalCategory.getDescendent(path, true);
	}

	/**
	 * Obtains the category that has the specified <code>path</code> relative
	 * to the specified <code>parent</code> category.  If this category does
	 * not yet exist, it is implicitly created.
	 * 
	 * @param parent the parent category, or <code>null</code> to indicate that
	 *    the path is absolute
	 * @param path the path relative to the <code>parent</code>, or the absolute
	 *    path if <code>parent == null</code>
	 * @return the specified category (never <code>null</code>)
	 */
	public Category getCategory(Category parent, String path) {
		if (parent == null) {
			return globalCategory.getDescendent(path, true);
		} else {
			return parent.getDescendent(path, true);
		}
	}

	/**
	 * Finds the category that has the specified absolute <code>path</code>.
	 * Unlike the {@link #getCategory(String)} method, this method will not
	 * implictly create the sought-after category.
	 * 
	 * @param path the absolute path of the category
	 * @return the specified category or <code>null</code> if it is not found
	 */
	public Category findCategory(String path) {
		return globalCategory.getDescendent(path, false);
	}

	/**
	 * Finds the category that has the specified <code>path</code> relative
	 * to the specified <code>parent</code> category.  Unlike the
	 * {@link #getCategory(Category, String)} method, this method will not
	 * implictly create the sought-after category.
	 * 
	 * @param parent the parent category, or <code>null</code> to indicate that
	 *    the path is absolute
	 * @param path the path relative to the <code>parent</code>, or the absolute
	 *    path if <code>parent == null</code>
	 * @return the specified category or <code>null</code> if it is not found
	 */
	public Category findCategory(Category parent, String path) {
		if (parent == null) {
			return globalCategory.getDescendent(path, false);
		} else {
			return parent.getDescendent(path, false);
		}
	}
	
	/**
	 * Removes the specified <code>category</code> from the category manager.
	 * <p>
	 * <b>Use extreme caution</b> when invoking this method.  This method
	 * recursively removes all descendent categories and their constraint from
	 * the category manager.  The constraints will still operate as they did
	 * previously, but the user will not see them in the UI or be able to
	 * control their enablement.  In general, you should only remove categories
	 * that you have added and whose constraints you control.
	 * </p>
	 * 
	 * @param category the category to remove
	 * 
	 * @see #removeCategory(String)
	 */
	public void removeCategory(Category category) {
		// we cannot remove the root category
		if (category.getParent() == null) {
			throw new IllegalArgumentException();
		}
		
		// first, recursively remove the child categories
		Collection children = new java.util.ArrayList(category.getChildren());
		for (Iterator iter = children.iterator(); iter.hasNext();) {
			removeCategory((Category)iter.next());
		}
		
		// purge the constraints from this category, so that they know they
		//   are no longer in it
		Collection constraints = new java.util.ArrayList(
				category.getConstraints());
		for (Iterator iter = constraints.iterator(); iter.hasNext();) {
			category.removeConstraint((IConstraintDescriptor)iter.next());
		}
		
		// finally, remove the category from its parent
		category.getParent().removeChild(category.getId());
	}
	
	/**
	 * Removes the specified category from the category manager.
	 * <p>
	 * <b>Use extreme caution</b> when invoking this method.  This method
	 * recursively removes all descendent categories and their constraint from
	 * the category manager.  The constraints will still operate as they did
	 * previously, but the user will not see them in the UI or be able to
	 * control their enablement.  In general, you should only remove categories
	 * that you have added and whose constraints you control.
	 * </p>
	 * 
	 * @param path the ID {@link Category#getPath() path} of the category to
	 *        remove
	 * 
	 * @see #removeCategory(Category)
	 */
	public void removeCategory(String path) {
		Category category = findCategory(path);
		
		if (category != null) {
			removeCategory(category);
		}
	}

	/**
	 * Initializes the default category which is the category that includes
	 * all constraints that are not explicitly members of any other category.
	 */
	private void initDefaultCategory() {
		Category.DEFAULT_CATEGORY.setName(DEFAULT_CATEGORY_NAME);
		Category.DEFAULT_CATEGORY.setDescription(DEFAULT_CATEGORY_DESCRIPTION);
	}
	
	/**
	 * Loads the category definitions from my constraintCategories extension
	 * point.
	 */
	private void loadCategories() {
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getExtensionPoint(
				EMFModelValidationPlugin.getPluginId(),
				EMFModelValidationPlugin.CONSTRAINT_PROVIDERS_EXT_P_NAME)
					.getConfigurationElements();
		
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement next = elements[i];
			
			if (next.getName().equals(XmlConfig.E_CATEGORY)) {
				loadCategories(globalCategory, next);
			}
		}
	}
	
	/**
	 * Loads subcategories of the specified <code>parent</code> category.
	 * @param parent
	 * @param element
	 */
	private void loadCategories(Category parent, IConfigurationElement element) {
		String path = element.getAttribute(XmlConfig.A_ID);
		
		if ((path != null) && (path.length() > 0)) {
			Category category = getCategory(parent, path);
			
			String name = element.getAttribute(XmlConfig.A_NAME);
			
			if (name != null) {
				category.setName(name);
			}
			
			category.setDescription(element.getValue());
			
			String mandatory = element.getAttribute(XmlConfig.A_MANDATORY);
			
			if (mandatory != null) {
				category.setMandatory(
						Boolean.valueOf(mandatory).booleanValue());
			}

			IConfigurationElement[] subcategories = element.getChildren(
					XmlConfig.E_CATEGORY);
		
			for (int i = 0; i < subcategories.length; i++) {
				// recursively load the child categories, if any
				loadCategories(category, subcategories[i]);
			}
		} else {
			Trace.trace(
					EMFModelValidationDebugOptions.XML,
					"No ID found for category: " //$NON-NLS-1$
						+ element.getAttribute(XmlConfig.A_NAME));
		}
	}

	/**
	 * Obtains all of the mandatory categories.
	 *  
	 * @return the mandatory categories
	 */
	public Collection getMandatoryCategories() {
		Collection result = new java.util.ArrayList();
		
		globalCategory.getMandatoryCategories(result);
		
		return result;
	}
}
