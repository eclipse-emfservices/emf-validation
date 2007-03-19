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


package org.eclipse.emf.validation.service;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.CategoryManager;

/**
 * <p>
 * A partial implementation of the {@link IConstraintDescriptor} interface
 * that is useful for subclassing.
 * </p>
 * <p>
 * This class may be extended by constraint providers.
 * </p>
 *
 * @author Christian W. Damus (cdamus)
 */
public abstract class AbstractConstraintDescriptor implements IConstraintDescriptor {
	private final Set categories = new java.util.HashSet();
	private final Set unmodCategories =
		java.util.Collections.unmodifiableSet(categories);
	
	private Throwable exception;
	
	private boolean enabled = true;
	
	
	/**
	 * Default initialization.
	 */
	protected AbstractConstraintDescriptor() {
		super();
		
		categories.add(CategoryManager.getInstance().getDefaultCategory());
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public final boolean isError() {
		return getException() != null;
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public final Throwable getException() {
		return exception;
	}

	/* (non-Javadoc)
	 * Redefines/Implements/Extends the inherited method.
	 */
	public final void setError(Throwable exception) {
		assert exception != null;
		
		this.exception = exception;
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public final boolean isEnabled() {
		return !isError() && enabled;
	}
	
	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public final void setEnabled(boolean enabled) {
		if (!enabled) {
			// if we are trying to disable a constraint, first check that
			// we are allowed to (i.e., it is not mandatory)
			enabled = isMandatory();
		}
		
		if (this.enabled != enabled) {
			this.enabled = enabled;
			
			ConstraintChangeEventType eventType = (this.enabled) ? ConstraintChangeEventType.ENABLED
					: ConstraintChangeEventType.DISABLED;

			ConstraintRegistry.getInstance().broadcastConstraintChangeEvent(
					new ConstraintChangeEvent(this, eventType));
		}
	}
	
	/**
	 * Computes whether I am mandatory, meaning that the user may not disable
	 * me.
	 * 
	 * @return <code>true</code> if I am a member of any mandatory categories;
	 *    <code>false</code>, otherwise
	 */
	private boolean isMandatory() {
		boolean result = false;
		
		for (Iterator iter = getCategories().iterator();
				!result && iter.hasNext();) {
			
			result = ((Category) iter.next()).isMandatory();
		}
		
		return result;
	}
	
	// implements the interface method
	public Set getCategories() {
		return unmodCategories;
	}
	
	// implements the interface method
	public void addCategory(Category category) {
		Category defaultCategory =
			CategoryManager.getInstance().getDefaultCategory();

		if (category.equals(defaultCategory)) {
			throw new IllegalArgumentException();
		}
		
		if (categories.contains(defaultCategory)) {
			// on entering the first explicit category, exit the default category
			categories.remove(defaultCategory);
			
			ConstraintRegistry.getInstance()
					.broadcastConstraintChangeEvent(
							new ConstraintChangeEvent(this,
									ConstraintChangeEventType.REMOVED_CATEGORY,
									defaultCategory));
		}
		
		if (!categories.contains(category)) {
			categories.add(category);
			category.addConstraint(this);
			
			ConstraintRegistry.getInstance()
					.broadcastConstraintChangeEvent(
							new ConstraintChangeEvent(this,
									ConstraintChangeEventType.ADDED_CATEGORY,
									category));
		}
	}
	
	// implements the interface method
	public void removeCategory(Category category) {
		if (categories.contains(category)) {
			categories.remove(category);
			category.removeConstraint(this);
			
			ConstraintRegistry.getInstance()
			.broadcastConstraintChangeEvent(
					new ConstraintChangeEvent(this,
							ConstraintChangeEventType.REMOVED_CATEGORY,
							category));
		}
		
		if (categories.isEmpty()) {
			Category defaultCategory =
				CategoryManager.getInstance().getDefaultCategory();

			// on exiting the last category, add the default category
			categories.add(defaultCategory);
			
			ConstraintRegistry.getInstance()
					.broadcastConstraintChangeEvent(
							new ConstraintChangeEvent(this,
									ConstraintChangeEventType.ADDED_CATEGORY,
									defaultCategory));
		}
	}

	// implements the interface method
	public boolean isBatch() {
		return getEvaluationMode().isBatch();
	}

	// implements the interface method
	public boolean isLive() {
		return getEvaluationMode().isLive();
	}
	
	// implements the interface method
	public final IConstraintDescriptor getDescriptor() {
		return this;
	}
	
	// redefines the inherited method
	public int hashCode() {
		return (getId() == null) ? 0 : getId().hashCode();
	}
	
	/**
	 * Equality is defined by equality of {@link #getId() ID}s.
	 * 
	 * @see #getId()
	 */
	public boolean equals(Object other) {
		return (other instanceof IConstraintDescriptor)
			&& ((IConstraintDescriptor)other).getId().equals(getId());
	}
	
	// redefines the inherited method
	public String toString() {
		StringBuffer result = new StringBuffer(64);
		
		if (!isEnabled()) {
			result.append("Disabled "); //$NON-NLS-1$
		}
		
		result.append("Constraint[id="); //$NON-NLS-1$
		result.append(getId());
		result.append(", code="); //$NON-NLS-1$
		result.append(getStatusCode());
		result.append(", severity="); //$NON-NLS-1$
		result.append(getSeverity());
		result.append(", mode="); //$NON-NLS-1$
		result.append(getEvaluationMode());
		result.append(']');
		
		return result.toString();
	}
}
