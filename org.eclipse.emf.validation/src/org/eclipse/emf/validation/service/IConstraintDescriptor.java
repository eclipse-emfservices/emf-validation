/******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.service;

import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.EvaluationMode;

/**
 * <p>
 * A constraint descriptor provides information about a constraint's
 * role and status in the system.  This includes such information as what
 * categories the constraint is a member of and whether it is enabled, disabled,
 * or even broken because of a run-time fault, in addition to a variety of
 * meta-data declared about severity, evaluation mode, and triggers.
 * </p>
 * <p>
 * This class is intended to be implemented by clients that have constraintProviders
 *  that are contributing {@link org.eclipse.emf.validation.model.IModelConstraint}
 *  that are not described in standard XML.
 * </p>
 *
 * @author Christian W. Damus (cdamus)
 */
public interface IConstraintDescriptor {
	/**
	 * Gets my name. This needs not be unique in any sense, and should be
	 * localized.
	 * 
	 * @return my name
	 */
	String getName();

	/**
	 * Gets my ID. This must be unique. It is recommended that the ID be
	 * prefixed by the contributing plugin ID, as is usual for IDs in Eclipse.
	 * 
	 * @return my unique identifier
	 */
	String getId();

	/**
	 * Queries the ID of the plugin which defines me.
	 * 
	 * @return my plugin's ID
	 */
	String getPluginId();

	/**
	 * Obtains a description of my purpose, if any.
	 * 
	 * @return my description, or <CODE>null</CODE> if I have none
	 */
	String getDescription();

	/**
	 * Queries the severity, as enumerated by the {@link ConstraintSeverity}
	 * class, of the problem indicated by a violation of my constraint.
	 * 
	 * @return my severity code
	 */
	ConstraintSeverity getSeverity();

	/**
	 * Obtains a status code (unique integer within the scope of the
	 * {@link #getPluginId plugin}which defines me, useful for logging.
	 * 
	 * @return a status code which is unique amongst all constraints
	 *         contributed by the plug-in that defines me
	 * @see #getPluginId
	 */
	int getStatusCode();

	/**
	 * Queries the mode (or modes) in which I can be evaluated. If I have the
	 * {@link EvaluationMode#NULL}mode, then I am never evaluated at all.
	 * 
	 * @return my evaluation mode
	 */
	EvaluationMode getEvaluationMode();

	/**
	 * Queries whether I target the type (or any supertype) of the specified
	 * <CODE>eObject</CODE>.
	 * 
	 * @param eObject an EMF object which is to be validated
	 * @return <CODE>true</CODE> if I can be applied to the specified <CODE>
	 *         eObject</CODE>;<CODE>false</CODE>, otherwise
	 */
	boolean targetsTypeOf(EObject eObject);

	/**
	 * Queries whether I apply to the specified EMF <code>notification</code>.
	 * Note that it is OK if I indiscriminately return
	 * <code>true</code>; this method merely enables an optimization that skips
	 * constraints that are known not to apply to the notification in
	 * question.
	 * <p>
	 * This method is only invoked on {@link #isLive live} constraints, because
	 * {@link #isBatch batch}constraints are not invoked in a live context.
	 * </p>
	 * 
	 * @param notification a notification of some change in an EMF model object
	 * @return <CODE>false</CODE> if I need not be executed on this
	 *         <code>notification</code>; <CODE>true</CODE>, otherwise
	 */
	boolean targetsEvent(Notification notification);

	/**
	 * Queries whether I may be applied in "batch" mode, i.e., outside of any
	 * particular editing action context.
	 * 
	 * @return <CODE>true</CODE> if I support "batch" (contextless)
	 *         invocation; <CODE>false</CODE>, otherwise
	 * @see #getEvaluationMode
	 */
	boolean isBatch();

	/**
	 * Queries whether I may be applied in "live" mode, i.e., within some
	 * particular editing action context.
	 * 
	 * @return <CODE>true</CODE> if I support "live" (contextful) invocation;
	 *         <CODE>false</CODE>, otherwise
	 * @see #getEvaluationMode
	 */
	boolean isLive();
	
	/**
	 * Queries whether the constraint is errored, i.e., not executable because
	 * of some error in initializing it.
	 * 
	 * @return whether I represent an errored constraint
	 */
	public abstract boolean isError();
	
	/**
	 * If I am an {@link #isError error} constraint, obtains the exception
	 * that caused me not to be initialized.
	 *  
	 * @return my exception
	 */
	public abstract Throwable getException();
	
	/**
	 * Queries whether the constraint is enabled.  {@link #isError Errored}
	 * constraints are never enabled; other constraints may be disabled
	 * by the user.
	 * 
	 * @return whether the constraint that I represent is enabled
	 */
	public abstract boolean isEnabled();
	
	/**
	 * Sets whether the constraint is enabled.  Note that this only has any
	 * effect on constraints that are not {@link #isError() errored} and are
	 * not in a {@link Category#isMandatory() mandatory} category.
	 * 
	 * @return whether the constraint that I represent is enabled
	 * 
	 * @see #isEnabled()
	 */
	public abstract void setEnabled(boolean enabled);
	
	/**
	 * <p>
	 * Sets my error status.
	 * </p>
	 * <p>
	 * This method should not be called outside of the validation framework.
	 * </p>
	 * 
	 * @param exception the exception that causes me to be an error constraint
	 */
	public abstract void setError(Throwable exception);
	
	/**
	 * Queries the categories that I am a member of.
	 * 
	 * @return an unmodifiable set of {@link Category}s
	 */
	public abstract Set getCategories();
	
	/**
	 * Adds a category to me.  If, previously, I was in the default category,
	 * then I will no longer be in the default category when this method
	 * returns.
	 * 
	 * @param category my category
	 * @throws IllegalArgumentException if <code>category</code> is the default
	 *     category, as this is not allowed to be set explicitly
	 */
	public abstract void addCategory(Category category);
	
	/**
	 * Removes a category from me.
	 * 
	 * @param category a category
	 */
	public abstract void removeCategory(Category category);
	
	/**
	 * Obtains the localized message pattern configured in the XML for my
	 * constraint.  It must conform to the conventions of the
	 * {@link org.eclipse.osgi.util.NLS} class.
	 * 
	 * @return my message pattern
	 * @see org.eclipse.osgi.util.NLS
	 */
	public abstract String getMessagePattern();
	
	/**
	 * If I represent an in-line constraint (whose algorithm is implemented in
	 * an XML file, script, or some other source than Java), then this method
	 * obtains its body.
	 * 
	 * @return the constraint body, if appropriate to the language
	 */
	public abstract String getBody();
}