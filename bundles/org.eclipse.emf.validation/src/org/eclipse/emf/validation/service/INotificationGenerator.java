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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;

/**
 * Interface implemented by clients who wish to define a notification generator.
 * <p>
 * Notification generators are contributed through the <tt>eventTypes</tt>
 * Eclipse extension point.
 * </p>
 *
 * @see EventTypeService#getNotificationGenerator(String)
 * @see EventTypeService#getNotificationGenerators()
 *
 * @since 1.1
 *
 * @author David Cummings (dcummin)
 */
public interface INotificationGenerator {
	/**
	 * Generates and returns custom notifications from a list of notifications that
	 * are eligible for validation.
	 *
	 * @param notifications that are eligible for validation
	 * @return collection of newly generated notifications
	 */
	public Collection<Notification> generateNotifications(Collection<? extends Notification> notifications);
}
