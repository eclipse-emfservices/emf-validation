/**
 * <copyright>
 *
 * Copyright (c) 1004, 2006 IBM Corporation and others.
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
import java.util.Iterator;

import org.eclipse.jface.viewers.CheckStateChangedEvent;

import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.preferences.EMFModelValidationPreferences;
import org.eclipse.emf.validation.service.IConstraintDescriptor;


/**
 * Concrete implementation of the {@link IConstraintNode} interface.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintNode
	implements IConstraintNode {

	private static final java.util.Map instanceMap = new java.util.HashMap();
	
	private final IConstraintDescriptor constraint;
	private Boolean mandatory;
	private boolean checked = false;
	
	private final java.util.Set categories = new java.util.HashSet();
	
	/**
	 * Initializes me with the constraint that I represent.
	 * 
	 * @param constraint my constraint
	 */
	private ConstraintNode(IConstraintDescriptor constraint) {
		this.constraint = constraint;
		checked = constraint.isEnabled();
	}
	
	/**
	 * Obtains the cached node instance corresponding to the specified
	 * <code>constraint</code>.  Constraints are mapped one-to-one to nodes.
	 * 
	 * @param constraint a validation constraint descriptor
	 * @return the corresponding node
	 */
	static IConstraintNode getInstance(IConstraintDescriptor constraint) {
		String id = constraint.getId();
		IConstraintNode result = null;
		
		if (id != null) {
			result = (IConstraintNode) instanceMap.get(id);
			
			if (result == null) {
				result = new ConstraintNode(constraint);
				instanceMap.put(id, result);
			}
		}
		
		return result;
	}
	
	/**
	 * Flushes the current cache of constraint nodes.  This should only be
	 * called when the nodes are no longer in use.
	 */
	static void flushCache() {
		instanceMap.clear();
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public String getId() {
		return constraint.getId();
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public String getName() {
		return constraint.getName();
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public String getDescription() {
		return constraint.getDescription();
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public Collection getCategories() {
		return constraint.getCategories();
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public String getEvaluationMode() {
		return constraint.getEvaluationMode().getLocalizedName();
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public String getSeverity() {
		return constraint.getSeverity().getLocalizedName();
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public boolean isChecked() {
		return checked;
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public void setChecked(boolean checked) {
		if (checked != isChecked()) {
			if (isMandatory()) {
				// reject the attempt to uncheck me
				this.checked = true;
			} else if (isErrored()) {
				// reject the attempt to check me
				this.checked = false;
			} else {
				this.checked = checked;
			}
			
			updateCategories();
		}
	}
	
	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public boolean isMandatory() {
		if (mandatory == null) {
			boolean m = false;
			
			for (Iterator iter = constraint.getCategories().iterator();
					!m && iter.hasNext();) {
				
				if (((Category) iter.next()).isMandatory()) {
					m = true;
				}
			}
			
			mandatory = m ? Boolean.TRUE : Boolean.FALSE;
		}
		
		return mandatory.booleanValue();
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public boolean isErrored() {
		return constraint.isError();
	}
	
	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public void addCategory(ICategoryTreeNode category) {
		categories.add(category);
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public void checkStateChanged(CheckStateChangedEvent event) {
		if (event.getChecked() != isChecked()) {
			if (isMandatory() && !event.getChecked()) {
				// reject the attempt to uncheck me
				event.getCheckable().setChecked(this, true);
			} else if (isErrored() && event.getChecked()) {
				// reject the attempt to check me
				event.getCheckable().setChecked(this, false);
			} else {
				checked = event.getChecked();
			}
			
			updateCategories();
		}
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public void applyToPreferences() {
		// set the preference
		EMFModelValidationPreferences.setConstraintDisabled(
			constraint.getId(),
			!isChecked());
		
		// tell the constraint, too
		constraint.setEnabled(isChecked());
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public void revertFromPreferences() {
		setChecked(!EMFModelValidationPreferences.isConstraintDisabled(
			constraint.getId()));
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public void restoreDefaults() {
		setChecked(!EMFModelValidationPreferences.isConstraintDisabledByDefault(
				constraint.getId()));
	}

	/**
	 * Informs the categories that include me that my checked state has changed.
	 * This allows them to update theirs, to match.
	 */
	private void updateCategories() {
		for (Iterator iter = categories.iterator(); iter.hasNext();) {
			ICategoryTreeNode next = (ICategoryTreeNode) iter.next();
			
			next.updateCheckState(this);
		}
	}
}
