/******************************************************************************
 * Copyright (c) 2003, 2008 IBM Corporation, Zeligsoft Inc., and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *    Borland Software - Bug 137213
 *    Zeligsoft - Bug 137213
 *    SAP AG - Bug 240352
  ****************************************************************************/

package org.eclipse.emf.validation.model;

import java.util.Collection;
import java.util.SortedSet;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin.Tracing;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.internal.modeled.ModeledConstraintsConfig;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.ModeledConstraintsLoader;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * <p>
 * Central point by which clients discover the available {@link Category
 * categories} and {@link IConstraintDescriptor constraints} in the system. The
 * <code>CategoryManager</code> is responsible for loading and maintaining all
 * <code>Category</code> instances.
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

	private final IExtensionChangeHandler extensionHandler = new IExtensionChangeHandler() {

		@Override
		public void addExtension(IExtensionTracker tracker, IExtension extension) {
			for (IConfigurationElement next : extension.getConfigurationElements()) {

				if (next.getName().equals(XmlConfig.E_CATEGORY)) {
					loadCategories(globalCategory, next);
				}
			}
		}

		@Override
		public void removeExtension(IExtension extension, Object[] objects) {
			// category definitions cannot be removed
		}
	};

	static {
		// these methods (transitively) need to access the INSTANCE through
		// the getInstance() method, so it must already be assigned before
		// these methods are called
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
	 * @return an unmodifiable set of {@link Category}s, sorted by their names
	 */
	public SortedSet<Category> getTopLevelCategories() {
		return globalCategory.getChildren();
	}

	/**
	 * Retrieves the default category which contains all constraints that are not
	 * explicitly categorized.
	 *
	 * @return the default category
	 */
	public Category getDefaultCategory() {
		return Category.DEFAULT_CATEGORY;
	}

	/**
	 * Obtains the category that has the specified absolute <code>path</code>. If
	 * this category does not yet exist, it is implicitly created.
	 *
	 * @param path the absolute path of the category
	 * @return the specified category (never <code>null</code>)
	 */
	public Category getCategory(String path) {
		return globalCategory.getDescendent(path, true);
	}

	/**
	 * Obtains the category that has the specified <code>path</code> relative to the
	 * specified <code>parent</code> category. If this category does not yet exist,
	 * it is implicitly created.
	 *
	 * @param parent the parent category, or <code>null</code> to indicate that the
	 *               path is absolute
	 * @param path   the path relative to the <code>parent</code>, or the absolute
	 *               path if <code>parent == null</code>
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
	 * Finds the category that has the specified absolute <code>path</code>. Unlike
	 * the {@link #getCategory(String)} method, this method will not implictly
	 * create the sought-after category.
	 *
	 * @param path the absolute path of the category
	 * @return the specified category or <code>null</code> if it is not found
	 */
	public Category findCategory(String path) {
		return globalCategory.getDescendent(path, false);
	}

	/**
	 * Finds the category that has the specified <code>path</code> relative to the
	 * specified <code>parent</code> category. Unlike the
	 * {@link #getCategory(Category, String)} method, this method will not implictly
	 * create the sought-after category.
	 *
	 * @param parent the parent category, or <code>null</code> to indicate that the
	 *               path is absolute
	 * @param path   the path relative to the <code>parent</code>, or the absolute
	 *               path if <code>parent == null</code>
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
	 * <b>Use extreme caution</b> when invoking this method. This method recursively
	 * removes all descendent categories and their constraint from the category
	 * manager. The constraints will still operate as they did previously, but the
	 * user will not see them in the UI or be able to control their enablement. In
	 * general, you should only remove categories that you have added and whose
	 * constraints you control.
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
		Collection<Category> children = category.getChildren();
		Category[] childrenArray = children.toArray(new Category[children.size()]);
		for (Category child : childrenArray) {
			removeCategory(child);
		}

		// purge the constraints from this category, so that they know they
		// are no longer in it
		Collection<IConstraintDescriptor> constraints = category.getConstraints();
		IConstraintDescriptor[] constraintsArray = constraints.toArray(new IConstraintDescriptor[constraints.size()]);
		for (IConstraintDescriptor constraint : constraintsArray) {
			category.removeConstraint(constraint);
		}

		// finally, remove the category from its parent
		category.getParent().removeChild(category.getId());
	}

	/**
	 * Removes the specified category from the category manager.
	 * <p>
	 * <b>Use extreme caution</b> when invoking this method. This method recursively
	 * removes all descendent categories and their constraint from the category
	 * manager. The constraints will still operate as they did previously, but the
	 * user will not see them in the UI or be able to control their enablement. In
	 * general, you should only remove categories that you have added and whose
	 * constraints you control.
	 * </p>
	 *
	 * @param path the ID {@link Category#getPath() path} of the category to remove
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
	 * Initializes the default category which is the category that includes all
	 * constraints that are not explicitly members of any other category.
	 */
	private void initDefaultCategory() {
		Category.DEFAULT_CATEGORY.setName(DEFAULT_CATEGORY_NAME);
		Category.DEFAULT_CATEGORY.setDescription(DEFAULT_CATEGORY_DESCRIPTION);
	}

	/**
	 * For stand-alone applications loads the categories from the configuration
	 * elements.
	 *
	 * @param configurationElements the configuration elements
	 *
	 * @since 1.12.1
	 */
	public void loadCategories(IConfigurationElement[] configurationElements) {
		for (IConfigurationElement next : configurationElements) {
			if (next.getName().equals(XmlConfig.E_CATEGORY)) {
				loadCategories(globalCategory, next);
			}
		}
	}

	/**
	 * Loads the category definitions from my constraintCategories extension point.
	 */
	private void loadCategories() {
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			IExtensionPoint extPoint = Platform.getExtensionRegistry().getExtensionPoint(
					EMFModelValidationPlugin.getPluginId(), EMFModelValidationPlugin.CONSTRAINT_PROVIDERS_EXT_P_NAME);

			IExtensionTracker extTracker = EMFModelValidationPlugin.getExtensionTracker();
			if (extTracker != null) {
				extTracker.registerHandler(extensionHandler, ExtensionTracker.createExtensionPointFilter(extPoint));

				for (IExtension extension : extPoint.getExtensions()) {
					extensionHandler.addExtension(extTracker, extension);
				}
			}

			IExtensionPoint modeledConstraintsExtensionPoint = Platform.getExtensionRegistry().getExtensionPoint(
					EMFModelValidationPlugin.getPluginId(),
					EMFModelValidationPlugin.MODELED_CONSTRAINT_PROVIDERS_EXT_P_NAME);

			for (IExtension ext : modeledConstraintsExtensionPoint.getExtensions()) {
				for (IConfigurationElement cfg : ext.getConfigurationElements()) {
					if (ModeledConstraintsConfig.E_PROVIDER.equals(cfg.getName())) {
						String uri = cfg.getAttribute(ModeledConstraintsConfig.A_CONSTRAINT_RESOURCE_URI);
						if (uri != null) {
							try {
								ModeledConstraintsLoader.getInstance().loadCategories(null, URI.createURI(uri),
										Platform.getBundle(ext.getContributor().getName()));
							} catch (Exception e) {
								Tracing.catching(EMFModelValidationDebugOptions.EXCEPTIONS_CATCHING,
										CategoryManager.class, "loadCategories", e); //$NON-NLS-1$
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Loads subcategories of the specified <code>parent</code> category.
	 *
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
				category.setMandatory(Boolean.valueOf(mandatory).booleanValue());
			}

			IConfigurationElement[] subcategories = element.getChildren(XmlConfig.E_CATEGORY);

			for (IConfigurationElement element2 : subcategories) {
				// recursively load the child categories, if any
				loadCategories(category, element2);
			}
		} else {
			Trace.trace(EMFModelValidationDebugOptions.XML, "No ID found for category: " //$NON-NLS-1$
					+ element.getAttribute(XmlConfig.A_NAME));
		}
	}

	/**
	 * Obtains all of the mandatory categories.
	 *
	 * @return the mandatory categories
	 */
	public Collection<Category> getMandatoryCategories() {
		Collection<Category> result = new java.util.ArrayList<>();

		globalCategory.getMandatoryCategories(result);

		return result;
	}
}
