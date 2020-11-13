/**
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.model.tests;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.service.INotificationGenerator;

import ordersystem.LineItem;
import ordersystem.Order;
import ordersystem.special.LimitedEditionProduct;

public class SpecialOrderNotificationGenerator implements
		INotificationGenerator {

	/**
	 * Create special notifications for those orders who contain limited
	 * edition products
	 */ 
	public Collection<Notification> generateNotifications(Collection<? extends Notification> notifications) {
		Collection<Notification> newNotifications = new ArrayList<Notification>();
		
		for (Notification notification : notifications) {
			if (notification.getNotifier() instanceof Order) {
				Order order = (Order) notification.getNotifier();
				LimitedEditionProduct lep = null;
				
				for (LineItem obj : order.getItem()) {
					if (obj.getProduct() instanceof LimitedEditionProduct)  {
						lep = (LimitedEditionProduct)obj.getProduct();
					}
				}
				
				if (lep != null) {
					newNotifications.add(new ENotificationImpl((InternalEObject)lep, EMFEventType.getInstance("Special Order").toNotificationType(), Notification.NO_FEATURE_ID, null, null)); //$NON-NLS-1$
				}
			}
		}
		return newNotifications;
	}

}
