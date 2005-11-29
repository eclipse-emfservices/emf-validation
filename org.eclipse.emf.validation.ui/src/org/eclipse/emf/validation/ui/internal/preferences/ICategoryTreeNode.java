/**
 * <copyright>
 *
 * Copyright (c) 2004-2005 IBM Corporation and others.
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

import java.util.List;

import org.eclipse.jface.viewers.CheckStateChangedEvent;

import org.eclipse.emf.validation.model.Category;

/**
 * Interface representing a node in the category preferences
 * tree.  A category-tree node has three possible states:
 * <ul>
 *     <li>unchecked:  the corresponding category and all of its descendents
 *                     and constraints are not selected (i.e., disabled)</li>
 *     <li>checked:    the corresponding category and all of its descendents
 *                     and constraints are selected (i.e., enabled)</li>
 *     <li>gray-checked: the corresponding category has some descendents and/or
 *                     constraints that are selected and some that are not</li>
 * </ul>
 * Note that internal nodes in the tree never contain constraints and,
 * therefore, their state only reflects the state of their descendent leaf nodes
 * which do contain constraints.  For internal-level categories that do
 * actually have constraints assigned to them, the tree delegates the
 * constraints to a fabricated child node that is a leaf.  This provides
 * separate control of the category hierarchy from the enablement of the
 * constraints in the category. 
 * 
 * @author Christian W. Damus (cdamus)
 */
interface ICategoryTreeNode {
	/**
	 * Obtains the description of the category, to show in the GUI.
	 * 
	 * @return the description of the category that I represent
	 */
	String getDescription();
	
	/**
	 * Queries whether I am checked.  When I am checked, either
	 * <ul>
	 *     <li>if I am a leaf node, then I am checked if any of my constraints
	 *         is checked</li>
	 *     <li>if I am not a leaf node, then at least one of my descendents
	 *         is checked (recursively)</li>
	 * </ul>
	 * 
	 * @return whether I show a check mark in the GUI
	 */
	boolean isChecked();
	
	/**
	 * Queries whether I am grayed.  Gray state indicates that at least one of
	 * my descendents or constraints is checked and at least one is not.
	 * 
	 * @return whether I am shown as a gray check mark in the GUI
	 */
	boolean isGrayed();
	
	/**
	 * Obtains my parent, if any, in the tree.
	 * 
	 * @return my parent, or <code>null</code> if I am the root node
	 */
	ICategoryTreeNode getParent();
	
	/**
	 * Queries whether I have children.
	 * 
	 * @return <code>true</code> if I have children; <code>false</code> if I
	 *     am a leaf
	 */
	boolean hasChildren();
	
	/**
	 * Obtains my children.
	 * 
	 * @return my children, or an empty array if I am a leaf
	 */
	ICategoryTreeNode[] getChildren();
	
	/**
	 * Obtains my constraints.
	 * 
	 * @return my constraints, or an empty list if I am not a leaf node
	 */
	List getConstraints();
	
	/**
	 * Obtains the category that I represent if I am a leaf node.
	 * 
	 * @return my category, or <code>null</code> if I am an internal node
	 */
	Category getCategory();
	
	/**
	 * Obtains the constraints that are selected in me, if I am a leaf node.
	 * 
	 * @return my selected constraints, or <code>[]</code> if I am an
	 *    internal node
	 */
	IConstraintNode[] getSelectedConstraints();
	
	/**
	 * Causes me to transition to a new check and/or gray state, according to
	 * the given <code>event</code>.
	 * 
	 * @param event a check-state event in the GUI
	 */
	void checkStateChanged(CheckStateChangedEvent event);
	
	/**
	 * Updates my state to reflect a change in the specified <code>child</code>.
	 * 
	 * @param child my changed child
	 */
	void updateCheckState(ICategoryTreeNode child);
	
	/**
	 * Updates my state to reflect a change in the specified
	 * <code>constraint</code> selection.
	 * 
	 * @param constraint my changed constraint
	 */
	void updateCheckState(IConstraintNode constraint);
	
	/**
	 * Applies, recursively, my state to the constraint enablement preferences.
	 */
	void applyToPreferences();
	
	/**
	 * Reverts, recursively, my state from the current constraint enablement
	 * preferences.
	 */
	void revertFromPreferences();
}
