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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;

/**
 * <p>
 * Describes the context in which a {@link IModelConstraint} is evaluated.
 * See the individual value descriptions for more information.
 * </p>
 * <p>
 * <b>Note</b> that both batch and live constraints are evaluated in batch mode.
 * This serves applications that do not work in a transactional context, but
 * still want critical constraints to be evaluated on batch invocations.
 * </p>
 * 
 * @param <T> the kind of object that is validated in an evaluation mode
 * 
 * @author Christian W. Damus (cdamus)
 */
public final class EvaluationMode<T> implements Serializable {
	
	private static final long serialVersionUID = 1862313226055912569L;

	/**
	 * <p>
	 * Constraints executed in the "Live" context are intended to be
	 * constraints that are requirements for committing a transaction in an
	 * application that implements a transactional model for changes to the
	 * data.  The intent is that if any live constraints fail, the transaction
	 * may not be committed and may have to be rolled back if the application
	 * cannot fix the data. 
	 * </p>
	 * <p>
	 * <b>Note</b> that "live" mode constraints are also evaluated in batch
	 * contexts.
	 * </b> 
	 */
	public static final EvaluationMode<Notification> LIVE = new EvaluationMode<Notification>("Live", //$NON-NLS-1$
		ValidationMessages.mode_live);
	
	/**
	 * Constraints executed in the "Batch" context are intended to be
	 * constraints that are evaluated on demand (when the user elects to
	 * evaluate them).  These do not, therefore, usually define conditions for
	 * data integrity, but rather semantic rules that guide a user to creating
	 * a better model.
	 */
	public static final EvaluationMode<EObject> BATCH = new EvaluationMode<EObject>("Batch", //$NON-NLS-1$
		ValidationMessages.mode_batch);

	/**
	 * This special value is a pointer-safe null value according to the
	 * <i>Null Object</i> pattern.  It is not a valid evaluation mode for
	 * a constraint.
	 */
	public static final EvaluationMode<Void> NULL = new EvaluationMode<Void>("none", //$NON-NLS-1$
		ValidationMessages.mode_unknown);

	/** All valid instances. */
	private static final List<EvaluationMode<?>> instances = Collections.unmodifiableList(
		Arrays.asList(new EvaluationMode<?>[] {LIVE, BATCH, NULL}));

	private final String name;
	private final String localizedName;

	/**
	 * Initializes me with my <code>name</code>.  I get the next ordinal in the
	 * sequence.
	 * 
	 * @param name
	 * @param localizedNamey my localized name
	 */
	private EvaluationMode(String name, String localizedName) {
		this.name = name;
		this.localizedName = localizedName;
	}

	/**
	 * Obtains the <code>name</code>d instance.  If the specified
	 * <code>name</code> is not recognized as the name of a valid instance or
	 * is <code>null</code>, then the result is the special {@link #NULL}
	 * instance.
	 * 
	 * @param name the name of the instance to retrieve (not case-sensitive)
	 * @return the named instance, or {@link #NULL} if no such instance exists
	 */
	@SuppressWarnings("unchecked")
	public static <T> EvaluationMode<T> getInstance(String name) {
		EvaluationMode<T> result = (EvaluationMode<T>) NULL;

		for (EvaluationMode<?> next : instances) {
			if (next.getName().equalsIgnoreCase(name)) {
				result = (EvaluationMode<T>) next;
				break;
			}
		}

		return result;
	}

	/**
	 * Obtains all of the enumeration values.
	 * 
	 * @return all values
	 */
	public static final List<EvaluationMode<?>> getAllInstances() {
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
	 * Obtains my localized name, for display to the user.
	 * 
	 * @return my localized name
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
	 * Queries whether I am evaluated in live mode.
	 * 
	 * @return whether I support live evaluation
	 */
	public boolean isLive() {
		return this == LIVE;
	}

	/**
	 * Queries whether I am evaluated in batch mode.
	 * 
	 * @return whether I support batch evaluation
	 */
	public boolean isBatch() {
		return isBatchOnly() || isLive();
	}

	/**
	 * Queries whether I am evaluated in batch mode only (not also in live
	 * mode).
	 * 
	 * @return whether I support only batch evaluation
	 */
	public boolean isBatchOnly() {
		return this == BATCH;
	}

	// re-implements the inherited method
	@Override
	public String toString() {
		return getName();
	}
	
	private Object readResolve() {
		return getInstance(getName());
	}
}
