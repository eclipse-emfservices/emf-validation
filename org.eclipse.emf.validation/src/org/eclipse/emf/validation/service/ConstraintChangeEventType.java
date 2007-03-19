/******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.AbstractEnumerator;

/**
 * Enumeration for a constraint change event type.
 * 
 * @since 1.1
 * 
 * @author David Cummings (dcummin)
 */
public final class ConstraintChangeEventType
	extends AbstractEnumerator {

	private static final long serialVersionUID = 1L;

	/**
	 * An internal unique identifier for this enumerated type.
	 */
	private static int nextOrdinal = 0;

	/**
	 * Registered constraint change event type
	 */
	public static final ConstraintChangeEventType REGISTERED = new ConstraintChangeEventType(
		"Registered"); //$NON-NLS-1$

	/**
	 * Unregistered constraint change event type
	 */
	public static final ConstraintChangeEventType UNREGISTERED = new ConstraintChangeEventType(
		"Unregistered"); //$NON-NLS-1$

	/**
	 * Enabled constraint change event type
	 */
	public static final ConstraintChangeEventType ENABLED = new ConstraintChangeEventType(
		"Enabled"); //$NON-NLS-1$
	
	/**
	 * Disabled constraint change event type
	 */
	public static final ConstraintChangeEventType DISABLED = new ConstraintChangeEventType(
		"Disabled"); //$NON-NLS-1$
	
	/**
	 * Added category constraint change event type
	 */
	public static final ConstraintChangeEventType ADDED_CATEGORY  = new ConstraintChangeEventType(
		"Added Category"); //$NON-NLS-1$
	
	/**
	 * Removed category constraint change event type
	 */
	public static final ConstraintChangeEventType REMOVED_CATEGORY = new ConstraintChangeEventType(
		"Removed Category"); //$NON-NLS-1$

	
	private static final ConstraintChangeEventType[] VALUES = { REGISTERED,
																UNREGISTERED,
																ENABLED,
																DISABLED,
																ADDED_CATEGORY,
																REMOVED_CATEGORY };

	/**
	 * Constructs a new constraint change event type with the specified name and
	 * ordinal.
	 * 
	 * @param name The name of the constraint change event type
	 * @param ordinal The ordinal for the constraint change event type.
	 */
	private ConstraintChangeEventType(String name, int ordinal) {
		super(ordinal, name);
	}

	/**
	 * Constructs a new constraint change event type with the specified name.
	 * 
	 * @param name The name of the new constraint change event type.
	 */
	private ConstraintChangeEventType(String name) {
		this(name, nextOrdinal++);
	}

	/**
	 * Obtains the collection of predefined constraint change event types
	 * 
	 * @return an unmodifiable collection of the event types
	 */
	protected List getValues() {
		return Collections.unmodifiableList(Arrays.asList(VALUES));
	}
}
