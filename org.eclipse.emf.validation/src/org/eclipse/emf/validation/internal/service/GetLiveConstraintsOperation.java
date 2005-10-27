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


package org.eclipse.emf.validation.internal.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.service.IModelConstraintProvider;

/**
 * <p>
 * Encapsulates a request to get "live" constraints for a particular event
 * on an EMF object.
 * </p>
 * 
 * @see org.eclipse.emf.validation.service.IModelConstraintProvider
 * @see org.eclipse.emf.validation.service.ModelValidationService
 * 
 * @author Christian W. Damus (cdamus)
 */
public class GetLiveConstraintsOperation
		extends
			AbstractGetConstraintsOperation {
	
	private EMFEventType eventType;
	private Notification notification;
	private List allEvents;
	
	/**
	 * Initializes me.
	 */
	public GetLiveConstraintsOperation() {
		super();
	}
	
	/**
	 * Sets the <CODE>notification</CODE> for which we are to get
	 * the live constraints.
	 * 
	 * @param notification the event to be validated (must not be
	 *            <CODE>null</CODE>)
	 */
	protected void setNotification(Notification notification) {
		setTarget((EObject)notification.getNotifier());

		EMFEventType newEventType = EMFEventType.getInstance(
				notification.getEventType());
		
		assert newEventType != null && !newEventType.isNull();

		this.eventType = newEventType;
		this.notification = notification;
	}
	
	/**
	 * Sets the events being validated.
	 * 
	 * @param events the {@link Notification}s
	 */
	protected void setAllEvents(List events) {
		this.allEvents = Collections.unmodifiableList(events);
	}

	/**
	 * Obtains the event type that triggered the validation.
	 * 
	 * @return the event type (corresponds to an EMF notification type)
	 */
	public final EMFEventType getEventType() {
		return eventType;
	}
	
	/**
	 * Obtains the events being validated in this operation.
	 * 
	 * @return the events
	 */
	public final List getAllEvents() {
		return allEvents;
	}
	
	/**
	 * Obtains the EMF notification that triggered the validation.
	 * 
	 * @return the triggering notification
	 */
	public final Notification getNotification() {
		return notification;
	}

	// implements the inherited method
	protected void executeImpl(
			IModelConstraintProvider provider,
			Collection constraints) {
		assert provider != null;

		provider.getLiveConstraints(getNotification(), constraints);
	}
	
	// implements the inherited method
	protected AbstractValidationContext createContext() {
		return new AbstractValidationContext(this) {
			// re-implements the inherited method
			public EStructuralFeature getFeature() {
				EStructuralFeature result = null;
				
				if (getNotification().getFeature() instanceof EStructuralFeature) {
					result = (EStructuralFeature)getNotification().getFeature();
				}
				
				return result;
			}
			
			/*
			 * Redefines the inherited method.
			 */
			public Object getFeatureNewValue() {
				Object result = null;
				
				if (!getEventType().isNull()) {
					switch (getNotification().getEventType()) {
					
					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
					case Notification.REMOVING_ADAPTER:
						// return the old value because that actually represents
						// the change (delta) of the feature
						result = getNotification().getOldValue();
						break;
						
					default:
						result = getNotification().getNewValue();
						break;
					}
				}
				
				return result;
			}
			
			// re-implements the inherited method
			public EMFEventType getEventType() {
				return GetLiveConstraintsOperation.this.getEventType();
			}
			
			// re-implements the inherited method
			public List getAllEvents() {
				return GetLiveConstraintsOperation.this.getAllEvents();
			}

			public Notification getNotification() {
				return GetLiveConstraintsOperation.this.getNotification();
			}};
	}
}
