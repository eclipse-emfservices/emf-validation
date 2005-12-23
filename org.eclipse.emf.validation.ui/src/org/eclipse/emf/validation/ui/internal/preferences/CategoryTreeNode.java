/**
 * <copyright>
 *
 * Copyright (c) 2003-2005 IBM Corporation and others.
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
package org.eclipse.emf.validation.ui.internal.preferences;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.CategoryManager;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.ui.internal.l10n.ValidationUIMessages;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.osgi.util.NLS;

/**
 * Concrete implementation of a node in the category selection tree.
 * 
 * @author Christian W. Damus (cdamus)
 */
class CategoryTreeNode extends AbstractCategoryTreeNode {
	private boolean grayed;
	private boolean checked;
	
	/**
	 * For internal nodes that have associated constraints, the delegate is a
	 * special child node that allows the user to enable or disable those
	 * constraints.  The constraints are "delegated to" this node.
	 */
	private ICategoryTreeNode delegate;
	
	private List constraints;
	
	/**
	 * Implementation of the root node of the category selection tree.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private static class RootNode extends AbstractCategoryTreeNode {
		/**
		 * Initializes me with just the tree that owns me.
		 * 
		 * @param tree the tree that owns me
		 */
		RootNode(CheckboxTreeViewer tree) {
			super(tree, null, null);
		}
		
		// implements the inherited method to wrap the top-level categories
		protected List createChildren() {
			List result = new java.util.ArrayList(
					CategoryManager.getInstance().getTopLevelCategories());
			
			for (ListIterator iter = result.listIterator(); iter.hasNext();) {
				Category next = (Category) iter.next();
				
				if (isRecursivelyEmpty(next)) {
					iter.remove();
				} else {
					iter.set(
							new CategoryTreeNode(
									getTree(),
									next,
									this));
				}
			}
			
			return result;
		}
		
		// implements the inherited method
		public boolean isChecked() {
			return false;
		}

		// implements the inherited method
		public boolean isGrayed() {
			return false;
		}

		// implements the inherited method
		public void checkStateChanged(CheckStateChangedEvent event) {
			// I cannot be selected by the user, so there is never a transition
		}

		// implements the inherited method
		public void updateCheckState(ICategoryTreeNode child) {
			// I am never visible and don't represent a category, anyway
		}

		// implements the inherited method
		public void updateCheckState(IConstraintNode constraint) {
			// I am never visible and don't represent a category, anyway
		}
		
		// redefines the inherited method
		public String getDescription() {
			return ""; //$NON-NLS-1$
		}
		
		// redefines the inherited method
		public List getConstraints() {
			return Collections.EMPTY_LIST;
		}
	}
	
	/**
	 * A special leaf node that acts as the delegate of an internal node that
	 * has constraints.
	 * 
	 * @author Christian W. Damus (cdamus)
	 * 
	 * @see CategoryTreeNode#delegate
	 */
	private static class DelegateNode extends CategoryTreeNode {
		/**
		 * Initializes me.
		 * 
		 * @param tree the tree that owns me
		 * @param category the category that I represent
		 * @param parent my parent, which delegates to me
		 */
		DelegateNode(
			CheckboxTreeViewer tree,
			Category category,
			ICategoryTreeNode parent) {
			
			super(tree, category, parent);
		}
		
		// redefines the inherited method
		protected List createChildren() {
			// delegates are leaf nodes, by definition
			return Collections.EMPTY_LIST;
		}
		
		// extends the inherited method
		public String toString() {
			String result = super.toString();
			
			StringBuffer buf = new StringBuffer(result.length() + 2);
			
			buf.append(NLS.bind(ValidationUIMessages.prefs_delegate_name,
				new Object[] {result}));
			
			result = buf.toString(); 
			
			return result;
		}
	}
	
	/**
	 * Initializes me.
	 * 
	 * @param tree the tree that owns me
	 * @param category the category that I represent
	 * @param parent my parent in the tree
	 */
	private CategoryTreeNode(
			CheckboxTreeViewer tree,
			Category category,
			ICategoryTreeNode parent) {
		
		super(tree, category, parent);
	}
	
	/**
	 * Creates a root node for the specified <code>tree</code> viewer.
	 * This root node will automatically discover the complete tree structure,
	 * as needed.
	 * 
	 * @param tree the tree viewer for which to create a root node
	 * @return a suitable root node
	 */
	public static ICategoryTreeNode createRoot(CheckboxTreeViewer tree) {
		ICategoryTreeNode result = new RootNode(tree);
		
		result.revertFromPreferences();
		
		return result;
	}
	
	// implements the interface method
	public boolean isChecked() {
		return checked;
	}
	
	// implements the interface method
	public boolean isGrayed() {
		return grayed;
	}
	
	// implements the inherited method
	protected List createChildren() {
		final Category category = getCategory();
		Collection categoryChildren = category.getChildren();
		
		List result;
		
		if (!categoryChildren.isEmpty() && !category.getConstraints().isEmpty()) {
			// add one for the delegate child that contains my own constraints
			result = new java.util.ArrayList(categoryChildren.size() + 1);
			
			// add my delegate
			delegate = new DelegateNode(getTree(), category, this);
			result.add(delegate);
		} else {
			result = new java.util.ArrayList(categoryChildren.size());
		}
		
		for (Iterator iter = categoryChildren.iterator(); iter.hasNext();) {
			Category next = (Category) iter.next();
			
			if (!isRecursivelyEmpty(next)) {
				result.add(new CategoryTreeNode(
						getTree(),
						next,
						this));
			}
		}
		
		return result;
	}
	
	// implements the interface method
	public void checkStateChanged(CheckStateChangedEvent event) {
		boolean newState = event.getChecked();
		
		if (isGrayed()) {
			// always transition to fully checked from the grayed state
			internalSetChecked(true);
		} else {
			// transition from unchecked to fully checked or vice-versa
			internalSetChecked(newState);
		}
	}
	
	/**
	 * Sets my new check state and propagates it down the tree.  This either
	 * completely checks or completely unchecks (according to the
	 * <code>newState</code>, and minding mandatory categories) the entire
	 * subtree rooted at me.  My state propagates up the tree, also, to possibly
	 * change the gray-state of my ancestors.
	 *  
	 * @param newState my new checked state
	 */
	protected void internalSetChecked(boolean newState) {
		if (!hasDelegate() && getCategory().isMandatory()) {
			checked = true;
		} else {
			checked = newState;
		}
		
		grayed = false;
		
		propagateToConstraints(newState);
		propagateDown(newState);
		
		updateTree();  // propagates my state up the tree, too
	}
	
	/**
	 * Propagates my new selection state to my constraints.  If any of my
	 * constraints cannot be made to reflect my new checked state, then I will
	 * be grayed.
	 * 
	 * @param newState the new checked state to propagate to my constraints
	 *     (those that will accept it)
	 */
	private void propagateToConstraints(boolean newChecked) {
		if (!hasDelegate()) {
			for (Iterator iter = getConstraints().iterator(); iter.hasNext();) {
				IConstraintNode next = (IConstraintNode) iter.next();
				
				// my constraints update my check state as necessary
				next.setChecked(newChecked);
			}
		}
	}
	
	/**
	 * Updates the GUI to reflect my current checked state, and propagates my
	 * state up the tree to my ancestors.
	 */
	private void updateTree() {
		CheckboxTreeViewer tree = getTree();
		
		if (tree.getChecked(this) != isChecked()) {
			tree.setChecked(this, isChecked());
		}
		
		if (tree.getGrayed(this) != isGrayed()) {
			tree.setGrayed(this, isGrayed());
		}
		
		updateParent();
	}
	
	/**
	 * Updates my parent, if I have one, to reflect my new state.
	 */
	private void updateParent() {
		if (getParent() != null) {
			getParent().updateCheckState(this);
		}
	}
	
	// implements the interface method
	public void updateCheckState(ICategoryTreeNode child) {
		if (child.isGrayed()) {
			// easy case
			grayed = true;
			checked = true;
			
			updateTree();
		} else {
			boolean newValue = child.isChecked();
			
			ICategoryTreeNode[] children = getChildren();
			
			for (int i = 0; i < children.length; i++) {
				if (children[i].isGrayed()
						|| (children[i].isChecked() != newValue)) {
					grayed = true;
					checked = true;
					
					updateTree();
					
					return;
				}
			}
			
			// children have the same check state, and none is gray, so I'm
			// not gray and have the same check-state as they
			
			grayed = false;
			checked = newValue;
			
			updateTree();
		}
	}
	
	// implements the interface method
	public void updateCheckState(IConstraintNode constraint) {
		boolean newValue = constraint.isChecked();
		boolean newChecked = newValue;
		boolean newGrayed = false;
		
		for (Iterator iter = getConstraints().iterator(); iter.hasNext();) {
			IConstraintNode next = (IConstraintNode) iter.next();
			
			if (next != constraint) {
				if (next.isChecked() != newValue) {
					newGrayed = true;
					newChecked = true;
					
					// can quit as soon as I have found that I am gray-checked
					break;
				}
			}
		}
		
		checked = newChecked;
		grayed = newGrayed;
		
		updateTree();
	}
	
	/**
	 * Recursively propagates a new <code>checkedState</code> down my sub-tree.
	 * Note that if <code>checkedState</code> is <code>false</code>, mandatory
	 * categories will nonetheless remain checked.
	 * 
	 * @param checkedState the new state
	 */
	private void propagateDown(boolean checkedState) {
		ICategoryTreeNode[] children = getChildren();
		
		for (int i = 0; i < children.length; i++) {
			((CategoryTreeNode)children[i]).internalSetChecked(checkedState);
		}
	}
	
	// extends the inherited method
	public void applyToPreferences() {
		if (!hasDelegate()) {
			for (Iterator iter = getConstraints().iterator(); iter.hasNext();) {
				IConstraintNode next = (IConstraintNode) iter.next();
				
				next.applyToPreferences();
			}
		}
		
		super.applyToPreferences();
	}
	
	// extends the inherited method
	public void revertFromPreferences() {
		if (!hasDelegate()) {
			IConstraintNode node = null;
			
			for (Iterator iter = getConstraints().iterator(); iter.hasNext();) {
				node = (IConstraintNode) iter.next();
				
				node.revertFromPreferences();
			}
			
			if (node != null) {
				// force a recomputation of my check state
				updateCheckState(node);
			}
		}
		
		super.revertFromPreferences();
	}
	
	// extends the inherited method
	public void restoreDefaults() {
		if (!hasDelegate()) {
			IConstraintNode node = null;
			
			for (Iterator iter = getConstraints().iterator(); iter.hasNext();) {
				node = (IConstraintNode) iter.next();
				
				node.restoreDefaults();
			}
			
			if (node != null) {
				// force a recomputation of my check state
				updateCheckState(node);
			}
		}
		
		super.restoreDefaults();
	}
	
	/**
	 * Extends the superclass method to return <code>null</code> if I have
	 * a child node to which I delegate my own category's constraints.
	 * 
	 * @return <code>null</code> if I have a delegate node; the superclass
	 *     result, otherwise
	 */
	public Category getCategory() {
		if (hasDelegate()) {
			return null;
		} else {
			return super.getCategory();
		}
	}
	
	// implements the inherited method
	public List getConstraints() {
		if (hasDelegate()) {
			constraints = Collections.EMPTY_LIST;
		} else if (constraints == null) {
			constraints = new java.util.ArrayList(
				getCategory().getConstraints());
			
			for (ListIterator iter = constraints.listIterator();
					iter.hasNext();) {
				
				IConstraintNode node = ConstraintNode.getInstance(
					(IConstraintDescriptor)iter.next());
				
				node.addCategory(this);
				iter.set(node);
			}
		}
		
		return constraints;
	}
	
	// implements the inherited method
	public IConstraintNode[] getSelectedConstraints() {
		List result = new java.util.ArrayList(getConstraints().size());
		
		for (Iterator iter = getConstraints().iterator(); iter.hasNext();) {
			IConstraintNode next = (IConstraintNode) iter.next();
			
			if (next.isChecked()) {
				result.add(next);
			}
		}
		
		return (IConstraintNode[]) result.toArray(
			new IConstraintNode[result.size()]);
	}
	
	/**
	 * Queries whether I have a delegate node that actually makes my
	 * category's constraints available as a leaf node.
	 * 
	 * @return whether I have a delegate check-state node
	 */
	private boolean hasDelegate() {
		return delegate != null;
	}
	
	/**
	 * Overrides the superclass behaviour to ensure that when I don't have a
	 * category (because my delegate has it), I return my delegate's category's
	 * name.
	 */
	public String toString() {
		if ((getCategory() == null) && hasDelegate()) {
			return delegate.getCategory().getName();
		} else {
			return super.toString();
		}
	}
}
