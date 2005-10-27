/******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package org.eclipse.emf.validation.ui.internal.preferences;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.CheckboxTreeViewer;

import org.eclipse.emf.validation.model.Category;

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
	
	/**
	 * Initializes me.
	 * 
	 * @param tree the tree that owns me (must not be <code>null</code>)
	 * @param category the category that I represent (may be <code>null</code>
	 *        if I am an internal node)
	 * @param parent my parent node, or <code>null</code> if I am the root
	 */
	protected AbstractCategoryTreeNode(
			CheckboxTreeViewer tree,
			Category category,
			ICategoryTreeNode parent) {
		
		this.tree = tree;
		this.category = category;
		this.parent = parent;
	}
	
	/**
	 * Implemented by subclasses to lazily create my children from the current
	 * constraint category definitions.  This method will only be invoked once.
	 * 
	 * @return my children
	 */
	protected abstract List createChildren();
	
	/**
	 * Lazily initializes my children on first access.
	 * 
	 * @see #createChildren()
	 */
	private void initChildren() {
		if (children == null) {
			List childList = createChildren();
			children = (ICategoryTreeNode[])childList.toArray(
					new ICategoryTreeNode[childList.size()]);
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
		boolean result = cat.getConstraints().isEmpty();
		
		if (result) {
			// check children, too
			
			for (Iterator iter = cat.getChildren().iterator(); result && iter.hasNext();) {
				result = isRecursivelyEmpty((Category) iter.next());
			}
		}
		
		return result;
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
		
		for (int i = 0; i < currentChildren.length; i++) {
			currentChildren[i].applyToPreferences();
		}
	}
	
	// implements the inherited method
	public void revertFromPreferences() {
		ICategoryTreeNode[] currentChildren = getChildren();
		
		for (int i = 0; i < currentChildren.length; i++) {
			currentChildren[i].revertFromPreferences();
		}
	}
	
	// implements the inherited method
	public String getDescription() {
		// use direct access to category in case subclass overrides
		//   getCategory()
		return category.getDescription();
	}
	
	// redefines the inherited method
	public String toString() {
		return (getCategory() == null)
			? "" //$NON-NLS-1$
			: getCategory().getName();
	}
}
	