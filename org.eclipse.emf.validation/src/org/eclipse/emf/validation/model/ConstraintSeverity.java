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


package org.eclipse.emf.validation.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;

/**
 * Describes the severity of failure to meet a
 * {@link IModelConstraint constraint}.  See the individual value descriptions
 * for details.  The values correspond one-to-one with the Eclipse
 * {@link IStatus} severity constants.
 * 
 * @see IStatus
 * @see IModelConstraint
 * 
 * @author Christian W. Damus (cdamus)
 */
public enum ConstraintSeverity implements Enumerator {
	/**
	 * Indicates that failure of the constraint should only generate an
	 * informational message to the user.
	 */
	INFO("INFO", ValidationMessages.severity_info, IStatus.INFO), //$NON-NLS-1$
	
	/**
	 * Indicates that failure of the constraint constitutes a warning condition.
	 */
	WARNING("WARNING", ValidationMessages.severity_warning, IStatus.WARNING), //$NON-NLS-1$

	/**
	 * Indicates that failure of the constraint constitutes an error condition.
	 */
	ERROR("ERROR", ValidationMessages.severity_error, IStatus.ERROR), //$NON-NLS-1$

	/**
	 * Indicates that failure of the constraint constitutes an error condition
	 * that should cancel the validation operation (no further constraints are
	 * evaluated).
	 */
	CANCEL("CANCEL", ValidationMessages.severity_cancel, IStatus.CANCEL), //$NON-NLS-1$
	/**
	 * This special value is a pointer-safe null value according to the
	 * <i>Null Object</i> pattern.  It is not a valid severity for
	 * a constraint.
	 */
	NULL("none", ValidationMessages.severity_null, IStatus.OK); //$NON-NLS-1$

	private static final long serialVersionUID = -5310833954198275258L;

	/** All of my values. */
	private static final List<ConstraintSeverity> instances = Collections.unmodifiableList(
			Arrays.asList(new ConstraintSeverity[]{
					INFO,
					WARNING,
					ERROR,
					CANCEL,
					NULL,
				}));

	private final String name;
	private final String localizedName;
	private final int istatusSeverity;

	/**
	 * Initializes me with my symbolic name and corresponding Eclipse
	 * severity code.
	 * 
	 * @param name my name
	 * @param localizedName my localized name
	 * @param istatusSeverity the Eclipse status severity code
	 */
	private ConstraintSeverity(String name, String localizedName, int istatusSeverity) {
		this.name = name;
		this.istatusSeverity = istatusSeverity;
		
		this.localizedName = localizedName;
	}

	/**
	 * Obtains the <code>name</code>d instance.
	 * 
	 * @param name the name to retrieve (not case-sensitive)
	 * @return the corresponding instance, or {@link #NULL} if no matching
	 *    instance exists
	 */
	public static ConstraintSeverity getInstance(String name) {
		ConstraintSeverity result = NULL;

		for (ConstraintSeverity next : instances) {
			if (next.getName().equalsIgnoreCase(name)) {
				result = next;
				break;
			}
		}

		return result;
	}

	/**
	 * Obtains all values of the enumeration.
	 * 
	 * @return all values
	 */
	public static final List<ConstraintSeverity> getAllInstances() {
		return instances;
	}

	/**
	 * Obtains my symbolic name.
	 * 
	 * @return my name
	 * @see #getInstance
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Obtains my localized (user-friendly) name.
	 * 
	 * @return a name suitable for display to the user
	 * @see #getName
	 */
	public final String getLocalizedName() {
		return localizedName;
	}

	/**
	 * Queries whether I am the <i>Null Object</i> of this enumeration.
	 * In general, <code>null</code> pointers are never used with this type.
	 * 
	 * @return whether I am the {@link #NULL} instance
	 */
	public boolean isNull() {
		return this == NULL;
	}

	/**
	 * Converts me to the Eclipse {@link IStatus} severity code.
	 * 
	 * @return my corresponding Eclipse severity code
	 */
	public int toIStatusSeverity() {
		return istatusSeverity;
	}

	// re-implements the inherited method
	@Override
	public String toString() {
		return getName();
	}
	
	public int getValue() {
		return ordinal();
	}
	
	public String getLiteral() {
		return getName();
	}
}
