/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.IModelConstraint;

/**
 * <p>
 * Interface implemented by objects that can provide
 * {@link org.eclipse.emf.validation.model.IModelConstraint}s to the system for
 * validation of EMF objects. Implementators are registered on the
 * <tt>org.eclipse.emf.validation.constraintProviders</tt> extension point.
 * <p>
 * <p>
 * An important implementor of this interface is the
 * {@link org.eclipse.emf.validation.service.ModelValidationService} which
 * clients use to obtain constraints that they may evaluate on an EMF object.
 * </p>
 * <p>
 * This interface may be implemented by clients of the validation framework, but
 * for most situations, the
 * {@link org.eclipse.emf.validation.xml.XmlConstraintProvider} provides all of
 * the required behaviour.
 * </p>
 *
 * @see org.eclipse.emf.validation.xml.XmlConstraintProvider
 * @see org.eclipse.emf.validation.service.ModelValidationService
 *
 * @author Christian W. Damus (cdamus)
 */
public interface IModelConstraintProvider {
	/**
	 * Obtains a collection of batch
	 * {@link org.eclipse.emf.validation.model.IModelConstraint}s which will be used
	 * to validate an <CODE>eObject</CODE> on demand.
	 * <P>
	 * Clients typically should invoke this method on the service with a
	 * <CODE>null</CODE> value for the <CODE>constraints</CODE> collector parameter.
	 *
	 * @param eObject     the {@link EObject} for which constraints are to be
	 *                    obtained that can be applied to it
	 * @param constraints a <em>collector</em> parameter to which I will add any
	 *                    constraints that I provide. If this argument is
	 *                    <CODE>null</CODE>, then I create and return a new
	 *                    collection
	 * @return the collection which was passed in by the <CODE>constraints </CODE>
	 *         parameter (with additions), or a new collection if
	 *         <CODE>constraints == null</CODE>
	 */
	Collection<IModelConstraint> getBatchConstraints(EObject eObject, Collection<IModelConstraint> constraints);

	/**
	 * Obtains a collection of live
	 * {@link org.eclipse.emf.validation.model.IModelConstraint}s that will be used
	 * to validate an EMF <CODE>notification</CODE> on committing a transaction on a
	 * model.
	 * <P>
	 * Clients typically should invoke this method on the service with a
	 * <CODE>null</CODE> value for the <CODE>constraints</CODE> collector parameter.
	 *
	 * @param notification the EMF notification that is to be validated.
	 *                     Encapsulates the object and the particular change
	 * @param constraints  a <em>collector</em> parameter to which I will add any
	 *                     constraints that I provide. If this argument is
	 *                     <CODE>null</CODE>, then I create and return a new
	 *                     collection
	 * @return the collection which was passed in by the <CODE>constraints </CODE>
	 *         parameter (with additions), or a new collection if
	 *         <CODE>constraints == null</CODE>
	 */
	Collection<IModelConstraint> getLiveConstraints(Notification notification,
			Collection<IModelConstraint> constraints);
}
