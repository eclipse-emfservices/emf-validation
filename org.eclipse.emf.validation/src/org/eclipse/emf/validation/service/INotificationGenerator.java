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

import java.util.Collection;

/**
 * Interface implemented by clients who wish to define a notification
 * generator.
 * <p>
 * Notification generators are contributed through the
 * <tt>eventTypes</tt> Eclipse extension point.
 * </p>
 * 
 * @see {@link EventTypeService#getNotificationGenerator(String)}
 * @see {@link EventTypeService#getNotificationGenerators()}
 *
 * @since 1.1
 *
 * @author David Cummings (dcummin)
 */
public interface INotificationGenerator {
	/**
	 * Generates and returns custom notifications from a list of 
	 * notifications that are eligible for validation.
	 * 
	 * @param notifications that are eligible for validation
	 * @return collection of newly generated notifications
	 */
	public Collection generateNotifications(Collection notifications);
}
