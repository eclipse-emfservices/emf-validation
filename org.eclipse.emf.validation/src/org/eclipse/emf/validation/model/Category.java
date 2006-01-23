/******************************************************************************
 * Copyright (c) 2003-2006 IBM Corporation and others.
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
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;

import org.eclipse.emf.validation.service.IConstraintDescriptor;

import com.ibm.icu.text.Collator;
import com.ibm.icu.text.UTF16;


/**
 * <p>
 * A constraint category, defining a hierarchical organization of
 * constraints.  Categories can be individually and separately (no dependency
 * on the hierarchy) enabled or disabled by the user.  Enablement indicates
 * whether the constraints in the category should be applied to the user's
 * models.
 * </p>
 * <p>
 * Categories are naturally sorted by {@link #getName name} for display
 * purposes.
 * </p>
 * <p>
 * This class is intended to be used by clients of the validation framework.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class Category implements Comparable {
	static final String SLASH = "/"; //$NON-NLS-1$
	
	/**
	 * The global namespace is the parent of all top-level categories.
	 * This ensures that I never have to worry about NULL parents (the global
	 * namespace is the only namespace that has no parent).
	 */
	static final Category GLOBAL_NAMESPACE = new Category("", null); //$NON-NLS-1$
	
	/**
	 * The "default" category is the one that contains all constraints that are
	 * not otherwise assigned to a known category.
	 */
	static final Category DEFAULT_CATEGORY = new Category("", GLOBAL_NAMESPACE); //$NON-NLS-1$
	
	private static Collator collator;
	
	private final String id;
	
	private String path;
	
	private String name;
	private String qualifiedName;
	private String description;
	
	private boolean mandatory;
	
	private final Category parent;
	private final java.util.Map children = new java.util.HashMap();
	
	private final Set constraints = new java.util.HashSet();
	
	/**
	 * Initializes me with my ID and parent category.
	 * 
	 * @param id my ID (must not be <code>null</code>)
	 * @param parent my parent category, or <code>null</code> if none
	 *    (which should only be the case for the {@link #GLOBAL_NAMESPACE}
	 */
	private Category(String id, Category parent) {
		assert id != null;
		
		this.id = id;
		this.parent = parent;
		
		this.name = id;  // reasonable default, esp. for the global namespace
		
		if (parent != null) {
			parent.addChild(this);
		}
	}

	/**
	 * Obtains my ID, which is unique within my parent's ID (or just unique
	 * within the global namespace if I have no parent).
	 * 
	 * @return my ID
	 */
	public final String getId() {
		return id;
	}
	
	/**
	 * Obtains my path, which is my fully-qualified slash-separated ID that
	 * is unique within the global namespace.
	 * 
	 * @return my unique path
	 */
	public final String getPath() {
		if (path == null) {
			if (getParent() == null) {
				path = getId();
			} else {
				path = getParent().getPath() + SLASH + getId();
			}
		}
		
		return path;
	}
	
	/**
	 * Obtains my user-presentable name.
	 * 
	 * @return my name
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * Obtains my qualified name, which includes my ancestors' names separated
	 * by slashes.
	 * 
	 * @return my qualified name
	 */
	public final String getQualifiedName() {
		if (qualifiedName == null) {
			if (getParent() == null) {
				qualifiedName = getName();
			} else {
				qualifiedName = getParent().getQualifiedName() + SLASH
					+ getName();
			}
		}
		
		return qualifiedName;
	}
	
	/**
	 * Obtains a string which (briefly) describes my purpose to the user.
	 * 
	 * @return my description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Obtains my constraints.
	 * 
	 * @return the {@link IConstraintDescriptor}s that are members of me as an
	 *     unmodifiable set
	 */
	public Set getConstraints() {
		return Collections.unmodifiableSet(constraints);
	}
	
	/**
	 * Adds a constraint to me.
	 * 
	 * @param constraint my constraint
	 */
	public void addConstraint(IConstraintDescriptor constraint) {
		if (!constraints.contains(constraint)) {
			constraints.add(constraint);
			constraint.addCategory(this);
		}
	}
	
	/**
	 * Removes a constraint from me.
	 * 
	 * @param constraint a constraint
	 */
	public void removeConstraint(IConstraintDescriptor constraint) {
		if (constraints.contains(constraint)) {
			constraints.remove(constraint);
			constraint.removeCategory(this);
		}
	}
	
	/**
	 * Obtains my parent category, or <code>null</code> if I am a top-level
	 * category.
	 * 
	 * @return my parent, if I have one
	 */
	public Category getParent() {
		Category result = getParentInternal();
		
		if (result == GLOBAL_NAMESPACE) {
			result = null;
		}
		
		return result;
	}
	
	/**
	 * Gets my real parent (which may be the {@link #GLOBAL_NAMESPACE} if I am
	 * a top-level category.
	 * 
	 * @return my real parent
	 */
	private Category getParentInternal() {
		return parent;
	}
	
	/**
	 * Obtains my children.
	 * 
	 * @return an unmodifiable set of the {@link Category}s that are
	 *     my children, sorted by {@link #getName name}.  May be an empty set
	 */
	public SortedSet getChildren() {
		return Collections.unmodifiableSortedSet(
				new java.util.TreeSet(children.values()));
	}
	
	/**
	 * Obtains the child category of mine that has the specified
	 * <code>childId</code>.
	 * 
	 * @param childId the ID to find
	 * @return the matching category, or <code>null</code> if not found
	 */
	public Category getChild(String childId) {
		return (Category)children.get(childId);
	}
	
	/**
	 * Obtains the descendent category of mine that has the specified
	 * <code>descendentPath</code> relative to my path.
	 * 
	 * @param descendentPath the relative path to find
	 * @return the matching category, or <code>null</code> if not found
	 * 
	 * @see #getPath
	 */
	public Category getDescendent(String descendentPath) {
		return getDescendent(descendentPath, false);
	}
	
	/**
	 * Obtains my descendent category having the specified <code>descendentPath</code>
	 * relative to me.  It will be <code>create</code>d, if desired by the
	 * caller, if it does not already exist.
	 * 
	 * @param descendentPath the relative path of my descendent
	 * @param create whether to create it if it does not exist
	 * @return the descendent, or <code>null</code> if it was not found and
	 *     <code>create</code> is <code>false</code>
	 */
	Category getDescendent(String descendentPath, boolean create) {
		// recursively strip leading slashes until we have an ID at the start
		if (descendentPath.startsWith(SLASH)) {
			return getDescendent(descendentPath.substring(1), create);
		}
		
		int slash = UTF16.indexOf(descendentPath, SLASH);
		if (slash < 0) {
			Category result = getChild(descendentPath);
			
			if ((result == null && create)) {
				result = new Category(descendentPath, this);
			}
					
			return result;
		} else {
			final String childId = descendentPath.substring(0, slash);
			Category child = getChild(childId);
			
			if (child == null) {
				if (!create) {
					return null;
				} else {
					child = new Category(childId, this);
				}
			}
			
			return child.getDescendent(descendentPath.substring(slash + 1), create);
		}
	}
	
	/**
	 * Sets my localized name.
	 * 
	 * @param name my name (may not be <code>null</code>)
	 */
	public final void setName(String name) {
		assert name != null;
		
		this.name = name;
		this.qualifiedName = null; // force lazy recalculation
	}
	
	/**
	 * Sets my localized description.
	 * 
	 * @param description my description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Queries whether I am a mandatory category, which the user may not
	 * deselect.
	 * 
	 * @return whether I am mandatory
	 */
	public boolean isMandatory() {
		return mandatory;
	}
	
	/**
	 * Sets whether I am mandatory.  If I am mandatory, then I change my state
	 * to enabled if it wasn't already.
	 * 
	 * @param b whether I am mandatory
	 */
	public void setMandatory(boolean b) {
		mandatory = b;
	}
	
	/**
	 * Adds the specified <code>child</code> category to me.  Note that this
	 * must only be called from the constructor of <code>child</code>.
	 * 
	 * @param child my new child category
	 */
	private void addChild(Category child) {
		final String newId = child.getId();
		
		if (children.containsKey(newId)) {
			// remove any existing child having the incoming child's ID
			removeChild(newId);
		}
		
		children.put(newId, child);
	}
	
	/**
	 * Removes the child having the specified ID from me.
	 * 
	 * @param childId the ID of the child category to be removed
	 */
	void removeChild(String childId) {
		children.remove(childId);
	}
	
	// redefines the inherited method
	public int hashCode() {
		return getPath().hashCode();
	}
	
	// redefines the inherited method
	public boolean equals(Object other) {
		return (other instanceof Category)
			&& ((Category)other).getPath().equals(getPath());
	}
	
	static Collator getCollator() {
		if (collator == null) {
			collator = Collator.getInstance();
		}
		
		return collator;
	}
	
	// implements the interface method
	public int compareTo(Object o) {
		// this will throw if o has the wrong type.  That's expected
		Category other = (Category)o;

		Collator collator = getCollator();
		
		int result = collator.compare(getName(), other.getName());
		
		if (result == 0) {
			// sort by path to ensure that equivalent sort implies equality
			//    and vice-versa
			result = collator.compare(getPath(), other.getPath());
		}
		
		return result;
	}
	
	// redefines the inherited method
	public String toString() {
		StringBuffer result = new StringBuffer(32);
		
		result.append("Category[path="); //$NON-NLS-1$
		result.append(getPath());
		result.append(", name="); //$NON-NLS-1$
		result.append(getName());
		result.append(']');
		
		return result.toString();
	}

	/**
	 * Fills the specified <code>collection</code> with the categories that
	 * are mandatory in my sub-tree.
	 * 
	 * @param collection the collection of mandatory categories to which I append
	 */
	void getMandatoryCategories(Collection collection) {
		if (isMandatory()) {
			collection.add(this);
		}
		
		for (Iterator iter = getChildren().iterator(); iter.hasNext(); ) {
			Category next = (Category)iter.next();
			
			next.getMandatoryCategories(collection);
		}
	}
}
