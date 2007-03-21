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
 * $Id: SpecialOrderNotificationGenerator.java,v 1.1 2007/03/21 21:06:23 cdamus Exp $
 */
 
 
package org.eclipse.emf.validation.internal.model.tests;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import ordersystem.LineItem;
import ordersystem.Order;
import ordersystem.special.LimitedEditionProduct;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.service.INotificationGenerator;

public class SpecialOrderNotificationGenerator implements
		INotificationGenerator {

	/**
	 * Create special notifications for those orders who contain limited
	 * edition products
	 */ 
	public Collection generateNotifications(Collection notifications) {
		Collection newNotifications = new ArrayList();
		
		for (Iterator iter=notifications.iterator(); iter.hasNext(); ) {
			Notification notification = (Notification)iter.next();
			if (notification.getNotifier() instanceof Order) {
				Order order = (Order)notification.getNotifier();
				LimitedEditionProduct lep = null;
				
				for (Iterator itemIter=order.getItem().iterator(); itemIter.hasNext();) {
					LineItem obj = (LineItem)itemIter.next();
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
