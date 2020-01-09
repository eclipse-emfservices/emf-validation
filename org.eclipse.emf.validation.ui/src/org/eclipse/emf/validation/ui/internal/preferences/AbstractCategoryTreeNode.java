/**
 * Copyright (c) 2004, 2008 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.ui.internal.preferences;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.jface.viewers.CheckboxTreeViewer;

/**
 * Partial implementation of the {@link ICategoryTreeNode} interface, useful for
 * subclassing.
 * 
 * @author Christian W. Damus (cdamus)
 */
abstract class AbstractCategoryTreeNode implements ICategoryTreeNode {
	// the tree that owns me
	private final CheckboxTreeViewer tree;
	
	private ICategoryTreeNode[] children;
	private final Category category;
	private final ICategoryTreeNode parent;
	private final IConstraintFilter filter;
	
	/**
	 * Initializes me.
	 * 
	 * @param tree the tree that owns me (must not be <code>null</code>)
	 * @param category the category that I represent (may be <code>null</code>
	 *        if I am an internal node)
	 * @param parent my parent node, or <code>null</code> if I am the root
     * @param filter a filter that defines which constraints I am interested in
	 */
	protected AbstractCategoryTreeNode(
			CheckboxTreeViewer tree,
			Category category,
			ICategoryTreeNode parent,
			IConstraintFilter filter) {
		
		this.tree = tree;
		this.category = category;
		this.parent = parent;
		this.filter = filter;
	}
	
	/**
	 * Implemented by subclasses to lazily create my children from the current
	 * constraint category definitions.  This method will only be invoked once.
	 * 
	 * @return my children
	 */
	protected abstract List<ICategoryTreeNode> createChildren();
	
	/**
	 * Lazily initializes my children on first access.
	 * 
	 * @see #createChildren()
	 */
	private void initChildren() {
		if (children == null) {
			List<ICategoryTreeNode> childList = createChildren();
			children = childList.toArray(new ICategoryTreeNode[childList.size()]);
		}
	}
	
	/**
	 * Queries whether the specified category has no constraints and,
	 * recursively, doesn't have any sub-categories that have constraints.
	 * 
	 * @param cat a constraint category
	 * @return <code>true</code> if the <code>cat</code>egory and all of its
	 *     descendents have no constraints; <code>false</code>, otherwise
	 */
	protected boolean isRecursivelyEmpty(Category cat) {
		boolean result = getConstraints(cat, getFilter()).isEmpty();
		
		if (result) {
			// check children, too
			
			for (Iterator<Category> iter = cat.getChildren().iterator();
			        result && iter.hasNext();) {
				result = isRecursivelyEmpty(iter.next());
			}
		}
		
		return result;
	}
    
    /**
     * Obtains a filtered list of a category's constraints.
     * 
     * @param category a category, whose constraints are to be filtered
     * @param filter a filter determining which of my constraints to return
     * 
     * @return the {@link IConstraintDescriptor}s that are members of me and that
     *     match the provided filter
     * 
     * @since 1.1
     */
    protected static Set<IConstraintDescriptor> getConstraints(Category category, IConstraintFilter filter) {
        Set<IConstraintDescriptor> filteredConstraints =
        	new java.util.HashSet<IConstraintDescriptor>();
        
        for (IConstraintDescriptor descriptor : category.getConstraints()) {
            if (filter.accept(descriptor, null)) {
                filteredConstraints.add(descriptor);
            }
        }
        
        return filteredConstraints;
    }
	
	/**
	 * Provides subclasses with access to the tree that I belong to.
	 * 
	 * @return my tree
	 */
	protected final CheckboxTreeViewer getTree() {
		return tree;
	}
	
	// implements the inherited method
	public boolean hasChildren() {
		return getChildren().length > 0;
	}
	
	// implements the inherited method
	public ICategoryTreeNode[] getChildren() {
		initChildren();
		
		return children;
	}
	
	// implements the interface method
	public final ICategoryTreeNode getParent() {
		return parent;
	}
	
	// implements the inherited method
	public Category getCategory() {
		return category;
	}
	
	// implements the inherited method
	public IConstraintNode[] getSelectedConstraints() {
		return new IConstraintNode[0];
	}
	
	// implements the inherited method
	public void applyToPreferences() {
		ICategoryTreeNode[] currentChildren = getChildren();
		
		for (ICategoryTreeNode element : currentChildren) {
			element.applyToPreferences();
		}
	}
	
	// implements the inherited method
	public void revertFromPreferences() {
		ICategoryTreeNode[] currentChildren = getChildren();
		
		for (ICategoryTreeNode element : currentChildren) {
			element.revertFromPreferences();
		}
	}

	// implements the inherited method
	public void restoreDefaults() {
		ICategoryTreeNode[] currentChildren = getChildren();
		
		for (ICategoryTreeNode element : currentChildren) {
			element.restoreDefaults();
		}
	}
	
	// implements the inherited method
	public String getDescription() {
		// use direct access to category in case subclass overrides
		//   getCategory()
		return category.getDescription();
	}
	
	// redefines the inherited method
	@Override
	public String toString() {
		return (getCategory() == null)
			? "" //$NON-NLS-1$
			: getCategory().getName();
	}
	
	// implments the inherited method
	public IConstraintFilter getFilter() {
		return filter;
	}
}
	