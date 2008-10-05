/******************************************************************************
 * Copyright (c) 2003, 2008 IBM Corporation, Zeligsoft Inc., and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 *    Zeligsoft - Bug 249690
 * 
 * $Id$
 * 
 ****************************************************************************/


package org.eclipse.emf.validation.service;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.validation.util.FilteredCollection;


/**
 * <p>
 * An {@link IValidator} that supports live-mode validation of
 * {@link org.eclipse.emf.common.notify.Notification}s.
 * </p>
 * <p>
 * Not intended to be implemented by clients.
 * </p>
 *
 * @see org.eclipse.emf.validation.service.ModelValidationService#newValidator(EvaluationMode)
 * @see org.eclipse.emf.validation.model.EvaluationMode#LIVE
 * 
 * @author Christian W. Damus (cdamus)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface ILiveValidator extends IValidator<Notification> {
	/**
	 * Validates the specified {@link Notification EMF event}.  This method is a
	 * synonym for the inherited {@link IValidator#validate(Object)}.
	 * 
	 * @param notification the EMF event to validate
	 * @return the validation status
	 * @see IValidator#validate(Object)
	 */
	IStatus validate(Notification notification);
	
    /**
     * Obtains a filter that is used to determine which notifications will be
     * validated (passed on to constraints to check).  The default filter, if
     * one is not set by the client of the validator, excludes any notification
     * from an object that is not attached to a resource at the time of
     * validation.
     * 
     * @return my notification filter
     * 
     * @see #setNotificationFilter(org.eclipse.emf.validation.util.FilteredCollection.Filter)
     * 
     * @since 1.1
     */
    FilteredCollection.Filter<Notification> getNotificationFilter();
    
	/**
	 * Sets the filter that is used to determine which notifications will be
     * validated (passed on to constraints to check).
	 * 
	 * @param filter the notification filter to use
     * 
     * @see #getNotificationFilter()
     * 
     * @since 1.1
	 */
	void setNotificationFilter(FilteredCollection.Filter<Notification> filter);
}
