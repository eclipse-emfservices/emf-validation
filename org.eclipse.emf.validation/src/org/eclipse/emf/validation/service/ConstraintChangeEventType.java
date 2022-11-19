/******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/

package org.eclipse.emf.validation.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * Enumeration for a constraint change event type.
 *
 * @since 1.1
 *
 * @author David Cummings (dcummin)
 */
public enum ConstraintChangeEventType implements Enumerator {

	/**
	 * Registered constraint change event type
	 */
	REGISTERED("Registered"), //$NON-NLS-1$

	/**
	 * Unregistered constraint change event type
	 */
	UNREGISTERED("Unregistered"), //$NON-NLS-1$

	/**
	 * Enabled constraint change event type
	 */
	ENABLED("Enabled"), //$NON-NLS-1$

	/**
	 * Disabled constraint change event type
	 */
	DISABLED("Disabled"), //$NON-NLS-1$

	/**
	 * Added category constraint change event type
	 */
	ADDED_CATEGORY("Added Category"), //$NON-NLS-1$

	/**
	 * Removed category constraint change event type
	 */
	REMOVED_CATEGORY("Removed Category"); //$NON-NLS-1$

	private static final long serialVersionUID = 1L;

	private static final ConstraintChangeEventType[] VALUES = { REGISTERED, UNREGISTERED, ENABLED, DISABLED,
			ADDED_CATEGORY, REMOVED_CATEGORY };

	private final String name;

	/**
	 * Constructs a new constraint change event type with the specified name and
	 * ordinal.
	 *
	 * @param name The name of the constraint change event type
	 */
	private ConstraintChangeEventType(String name) {
		this.name = name;
	}

	/**
	 * Obtains the collection of predefined constraint change event types
	 *
	 * @return an unmodifiable collection of the event types
	 */
	protected List<ConstraintChangeEventType> getValues() {
		return Collections.unmodifiableList(Arrays.asList(VALUES));
	}

	@Override
	public int getValue() {
		return ordinal();
	}

	@Override
	public String getLiteral() {
		return getName();
	}

	@Override
	public String getName() {
		return name;
	}
}
