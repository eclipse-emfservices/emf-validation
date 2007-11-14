/**
 * <copyright>
 *
 * Copyright (c) 2003, 2007 IBM Corporation and others.
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

package org.eclipse.emf.validation.tests;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * Implementation of the EMF {@link Notification} interface to "fake out"
 * EMF model change events.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class TestNotification extends NotificationImpl {
	private final EObject notifier;
	private final int featureId;
	
	/**
	 * Initializes me with the source and <code>type</code> of the event.
	 * 
	 * @param notifier my source
	 * @param type the type of notification that I am 
	 */
	public TestNotification(EObject notifier, int type) {
		
		this(notifier, type, Notification.NO_FEATURE_ID, null, null);
	}

	/**
	 * Initializes me with the source and <code>type</code> of the event, plus
	 * information about the changed feature.
	 * 
	 * @param notifier my source
	 * @param type the type of notification that I am 
	 * @param featureId the ID of the feature that is changed
	 * @param oldValue the old value of the changed feature
	 * @param newValue the new value of the changed feature
	 */
	public TestNotification(
			EObject notifier,
			int type,
			int featureId,
			Object oldValue,
			Object newValue) {
		
		super(type, oldValue, newValue);
		
		this.notifier = notifier;
		this.featureId = featureId;
	}

	// redefines the inherited method
	@Override
    public Object getNotifier() {
		return notifier;
	}
	
	// redefiness the inherited method
	@Override
    public Object getFeature() {
		if (featureId == Notification.NO_FEATURE_ID) {
			return null;
		} else {
			return notifier.eClass().getEStructuralFeature(featureId);
		}
	}
}
