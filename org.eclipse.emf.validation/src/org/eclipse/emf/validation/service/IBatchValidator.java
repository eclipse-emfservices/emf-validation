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

import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;


/**
 * <p>
 * An {@link IValidator} that supports batch-mode validation of
 * {@link org.eclipse.emf.ecore.EObject}s.
 * </p>
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 * 
 * @see org.eclipse.emf.validation.service.ModelValidationService#newValidator(EvaluationMode)
 * @see org.eclipse.emf.validation.model.EvaluationMode#BATCH
 * 
 * @author Christian W. Damus (cdamus)
 */
public interface IBatchValidator extends IValidator {
	/**
	 * <p>
	 * Queries whether I also evaluate applicable live constraints on each
	 * object that I validate.  This supports those clients that do not
	 * implement a transaction model for which live validation would be
	 * appropriate, but which still need to ensure that vital data integrity
	 * and other live constraints are satisfied by their models.
	 * </p>
	 * <p>
	 * By default, I only apply batch constraints (not live constraints).
	 * </p>
	 * 
	 * @return <code>true</code> if I apply both batch and live constraints;
	 *    <code>false</code> if I apply only batch constraints (the default) 
	 */
	boolean isIncludeLiveConstraints();
	
	/**
	 * Sets whether live constraints are to be included in validation.
	 * 
	 * @param includeLiveConstraints whether to include live constraints
	 * @see #isIncludeLiveConstraints
	 */
	void setIncludeLiveConstraints(boolean includeLiveConstraints);
	
	/**
	 * Obtains the traversal strategy that I employ to walk the model starting
	 * from the elements selected by the client.
	 * 
	 * @return my current traversal strategy
	 */
	ITraversalStrategy getTraversalStrategy();
	
	/**
	 * Sets my traversal strategy.
	 * 
	 * @param strategy the new traversal strategy.  Must not be
	 *     <code>null</code>
	 *
	 * @throws IllegalArgumentException on <code>null</code> strategies
	 *  
	 * @see #getTraversalStrategy
	 */
	void setTraversalStrategy(ITraversalStrategy strategy);
	
	/**
	 * Obtains the default traversal strategy, which can be assigned to me to
	 * {@link #setTraversalStrategy restore} the default behavior of recursive
	 * validation.  The default strategy makes use of all available customized
	 * {@link ITraversalStrategy} implementations contributed by plug-ins for
	 * iteration of the model sub-trees selected for recursive validation.
	 * This is different from the {@link ITraversalStrategy.Recursive} strategy,
	 * which simply iterates the entire contents of a model subtree.
	 * 
	 * @return the default traversal strategy
	 * 
	 * @see #setTraversalStrategy
	 */
	ITraversalStrategy getDefaultTraversalStrategy();
	
	/**
	 * Validates the specified {@link EObject EMF element}, using the specified
	 * progress <code>monitor</code> to monitor progress of validation (which
	 * is especially useful for recursive validation).
	 * 
	 * @param eObject the EMF element to validate
	 * @param monitor the progress monitor to track validation progress, or
	 *      <code>null</code> if no progress monitoring is required
	 * @return the validation status
	 * @see IValidator#validate(Object)
	 */
	IStatus validate(EObject eObject, IProgressMonitor monitor);
	
	/**
	 * Validates the specified {@link EObject EMF elements}, using the specified
	 * progress monitor to monitor progress of validation.
	 * 
	 * @param eObjects the EMF elements to validate
	 * @param monitor the progress monitor to track validation progress, or
	 *      <code>null</code> if no progress monitoring is required
	 * @return the validation status
	 * @see IValidator#validate(Object)
	 */
	IStatus validate(Collection eObjects, IProgressMonitor monitor);
	
	/**
	 * Validates a single {@link EObject EMF element} without using any
	 * progress monitor.
	 * 
	 * @param object must be an {@link org.eclipse.emf.ecore.EObject}
	 * @throws ClassCastException if <code>object</code> is not an
	 *     {@link org.eclipse.emf.ecore.EObject}
	 */
	IStatus validate(Object object);
	
	/**
	 * Validates multiple {@link EObject EMF elements} without using any
	 * progress monitor.
	 * 
	 * @param objects must all be {@link org.eclipse.emf.ecore.EObject}s
	 * @throws ClassCastException if any of the <code>objects</code> is
	 *     not an {@link org.eclipse.emf.ecore.EObject}
	 */
	IStatus validate(Collection objects);
}
