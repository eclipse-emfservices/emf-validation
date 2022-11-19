/******************************************************************************
 * Copyright (c) 2007, 2008 IBM Corporation, Zeligsoft Inc., and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *    Zeligsoft - Bug 249690
 ****************************************************************************/

package org.eclipse.emf.validation.service;

import org.eclipse.emf.validation.model.Category;

/**
 * Event notifying {@link IConstraintListener}s that a constraint has been
 * changed.
 * <p>
 * This class is not intended to be extended or instantiated by clients.
 * </p>
 *
 * @since 1.1
 *
 * @author David Cummings (dcummin)
 *
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class ConstraintChangeEvent {

	private IConstraintDescriptor constraint;

	private ConstraintChangeEventType eventType;

	private Category category;

	/**
	 * Initializes me with the constraint that has changed, an event that details
	 * the change and the category associated with the event.
	 *
	 * @param constraint the constraint that has changed
	 * @param eventType  the event that details the constraint change
	 * @param category   the category associated with the event (if eventType is
	 *                   {@link ConstraintChangeEventType#ADDED_CATEGORY} or
	 *                   {@link ConstraintChangeEventType#REMOVED_CATEGORY})
	 */
	public ConstraintChangeEvent(IConstraintDescriptor constraint, ConstraintChangeEventType eventType,
			Category category) {
		this.constraint = constraint;
		this.eventType = eventType;
		this.category = category;
	}

	/**
	 * Initializes me with the constraint that has changed and the event that
	 * details the change.
	 *
	 * @param constraint the constraint that has changed
	 * @param eventType  the event that details the constraint change
	 */
	public ConstraintChangeEvent(IConstraintDescriptor constraint, ConstraintChangeEventType eventType) {
		this(constraint, eventType, null);
	}

	/**
	 * Obtains {@link IConstraintDescriptor} of the constraint associated with the
	 * event
	 *
	 * @return the constraint associated with the event
	 */
	public IConstraintDescriptor getConstraint() {
		return this.constraint;
	}

	/**
	 * Obtains {@link ConstraintChangeEventType} that details the event
	 *
	 * @return the event type for this event
	 */
	public ConstraintChangeEventType getEventType() {
		return this.eventType;
	}

	/**
	 * Obtains {@link Category} associated with this event
	 *
	 * @return the category associated with the event
	 */
	public Category getCategory() {
		return this.category;
	}

	/**
	 * Sets the constraint descriptor for re-use of an event instance in bulk
	 * notifications.
	 *
	 * @param constraint the descriptor to set
	 */
	void setConstraint(IConstraintDescriptor constraint) {
		this.constraint = constraint;
	}
}
