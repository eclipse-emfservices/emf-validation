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


package org.eclipse.emf.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.service.EventTypeService;
import org.eclipse.osgi.util.NLS;


/**
 * An enumeration of named EMF event types.  There are two types of EMF event
 * types.  The first type correspond to the <code>int</code> constants defined
 * by the {@link Notification} interface in the EMF API.  The second type
 * correspond to event types contributed through the <tt>eventTypes<tt>
 * extension point XML.
 * 
 * This enumeration assigns names corresponding to the event names
 * in the <tt>constraintProviders</tt> extension point XML.
 * 
 * @see Notification
 * 
 * @author Christian W. Damus (cdamus)
 * @author David Cummings (dcummin)
 */
public final class EMFEventType implements Serializable {
	private static final long serialVersionUID = 5785536193334824241L;

	private static int nextNotificationTypeCode = 0;
	
    private static final Map<String, EMFEventType> nameToInstance =
    	new java.util.HashMap<String, EMFEventType>();
    
	/**
	 * The EMF "Add" event (corresponds to {@link Notification#ADD}).
	 */
	public static final EMFEventType ADD = new EMFEventType(
			"Add", //$NON-NLS-1$
			true,
			Notification.ADD);
	
	/**
	 * The EMF "Add Many" event (corresponds to {@link Notification#ADD_MANY}).
	 */
	public static final EMFEventType ADD_MANY = new EMFEventType(
			"Add Many", //$NON-NLS-1$
			true,
			Notification.ADD_MANY);
	
	/**
	 * The EMF "Move" event (corresponds to {@link Notification#MOVE}).
	 */
	public static final EMFEventType MOVE = new EMFEventType(
			"Move", //$NON-NLS-1$
			true,
			Notification.MOVE);
	
	/**
	 * The EMF "Remove" event (corresponds to {@link Notification#REMOVE}).
	 */
	public static final EMFEventType REMOVE = new EMFEventType(
			"Remove", //$NON-NLS-1$
			true,
			Notification.REMOVE);
	
	/**
	 * The EMF "Remove Many" event (corresponds to {@link Notification#REMOVE_MANY}).
	 */
	public static final EMFEventType REMOVE_MANY = new EMFEventType(
			"Remove Many", //$NON-NLS-1$
			true,
			Notification.REMOVE_MANY);
	
	/**
	 * The EMF "Set" event (corresponds to {@link Notification#SET}).
	 */
	public static final EMFEventType SET = new EMFEventType(
			"Set", //$NON-NLS-1$
			true,
			Notification.SET);
	
	/**
	 * The EMF "Unset" event (corresponds to {@link Notification#UNSET}).
	 */
	public static final EMFEventType UNSET = new EMFEventType(
			"Unset", //$NON-NLS-1$
			true,
			Notification.UNSET);
	
	/**
	 * The EMF "Resolve" event (corresponds to {@link Notification#RESOLVE}).
	 */
	public static final EMFEventType RESOLVE = new EMFEventType(
			"Resolve", //$NON-NLS-1$
			true,
			Notification.RESOLVE);
	
	/**
	 * The EMF "Removing Adapter" event (corresponds to {@link Notification#REMOVING_ADAPTER}).
	 */
	public static final EMFEventType REMOVING_ADAPTER = new EMFEventType(
			"Removing Adapter", //$NON-NLS-1$
			false,
			Notification.REMOVING_ADAPTER);
	
	/**
	 * A custom "Create" event, which is not implemented by EMF any longer but
	 * may be simulated by clients of the validation framework.
	 * (Corresponds to <code>0</code>).
	 */
	public static final EMFEventType CREATE = new EMFEventType(
			"Create", //$NON-NLS-1$
			true,
			0);  // use the literal value from MSL because EMF deprecates it
	
	/**
	 * This special value is a pointer-safe null value according to the
	 * <i>Null Object</i> pattern.  It indicates the absence of an EMF event
	 * trigger.
	 */
	public static final EMFEventType NULL = new EMFEventType(
			"none", //$NON-NLS-1$
			false,
			-1);

	/** All of my values. */
	private static final List<EMFEventType> predefinedInstances = Collections.unmodifiableList(
			Arrays.asList(new EMFEventType[]{
					ADD,
					ADD_MANY,
					MOVE,
					REMOVE,
					REMOVE_MANY,
					SET,
					UNSET,
					RESOLVE,
					REMOVING_ADAPTER,
					CREATE,
					NULL,
				}));

	private static final List<EMFEventType> instances = new ArrayList<EMFEventType>(
			predefinedInstances);
	
	static {
	    EventTypeService.getInstance(); // initialize custom event types
	}
	
	private final String name;
	private final boolean featureSpecific;
	private final int notificationTypeCode;
	
	/**
	 * Initializes me with my symbolic name and corresponding EMF
	 * {@link Notification} type code.
	 * 
	 * @param name my name
	 * @param featureSpecific whether the event is specific to features only,
	 *        not to objects
	 * @param notificationTypeCode the EMF notification type code
     * 
     * @throws IllegalArgumentException on attempt to define an event type with
     *     a name that is already used
	 */
	private EMFEventType(String name, boolean featureSpecific,
            int notificationTypeCode) {

        if (nameToInstance.containsKey(name)) {
            IllegalArgumentException e = new IllegalArgumentException(
                "Duplicate event type name: " + name); //$NON-NLS-1$

            Trace.throwing(EMFEventType.class, "<init>", e); //$NON-NLS-1$

            EMFModelValidationPlugin.log(
                EMFModelValidationStatusCodes.DUPLICATE_EVENT_TYPE, NLS.bind(
                    EMFModelValidationStatusCodes.DUPLICATE_EVENT_TYPE_MSG,
                    name), e);

            throw e;
        }

        this.name = name;
        this.featureSpecific = featureSpecific;
        this.notificationTypeCode = notificationTypeCode;
        nextNotificationTypeCode = (notificationTypeCode + 1 > nextNotificationTypeCode) ? notificationTypeCode + 1
            : nextNotificationTypeCode;

        nameToInstance.put(name, this);
    }
	
	/**
	 * Initalizes me with my my symbolic name and the next available 
	 * {@link Notification} type code.
	 * 
	 * @param name my name
	 * @param featureSpecific whether the event is specific to features only,
	 *        not to objects
	 */
	private EMFEventType(String name,
			boolean featureSpecific) {
		this(name, featureSpecific, nextNotificationTypeCode++);
	}
			
	/**
	 * Obtains the <code>name</code>d instance.
	 * 
	 * @param name the name to retrieve (not case-sensitive)
	 * @return the corresponding instance, or {@link #NULL} if no matching
	 *    instance exists
	 */
	public static EMFEventType getInstance(String name) {
		EMFEventType result = NULL;

		for (EMFEventType next : instances) {
			if (next.getName().equalsIgnoreCase(name)) {
				result = next;
				break;
			}
		}

		return result;
	}

	/**
	 * Obtains the instance corresponding to the specified EMF notification
	 * type <code>code</code>.
	 * 
	 * @param code the EMF {@link Notification} type code
	 * @return the corresponding instance of this class, or {@link #NULL} if
	 *    no matching instance exists
	 */
	public static EMFEventType getInstance(int code) {
		EMFEventType result = NULL;

		for (EMFEventType next : instances) {
			if (next.toNotificationType() == code) {
				result = next;
				break;
			}
		}

		return result;
	}

	/**
	 * Obtains all values of the enumeration.
	 * 
	 * @return all values
	 */
	public static final List<EMFEventType> getAllInstances() {
		return instances;
	}

	/**
	 * Obtains all predefined values of the enumeration, that is, those
	 * who were not contributed through the extension point XML
	 * 
	 * @since 1.1
	 * 
	 * @return all predefined values
	 */
	public static final List<EMFEventType> getPredefinedInstances() {
		return predefinedInstances;
	}
	
	/**
	 * Obtains my symbolic name.
	 * 
	 * @return my name
	 * @see #getInstance
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * Adds a custom event type to the list of event types
	 * 
	 * @since 1.1
     *
 	 * @param name my name
	 * @param featureSpecific whether the event is specific to features only,
	 *        not to objects
     * 
     * @throws IllegalArgumentException on attempt to define an event type with
     *     a name that is already used
	 */
	public static void addEventType(String name, boolean featureSpecific) {
		instances.add(new EMFEventType(name, featureSpecific));
	}

	/**
	 * Queries whether I am an event that notifies for changes in features only.
	 * As a special case, the {@link #NULL} event type is not feature-specific.
	 * 
	 * @return <code>false</code> if I can indicate a change in the state of
	 *     an object that is not feature-specific; <code>true</code>, otherwise
	 */
	public final boolean isFeatureSpecific() {
		return featureSpecific;
	}
	
	/**
	 * Queries whether I am the <i>Null Object</i> of this enumeration.
	 * In general, <code>null</code> pointers are never used with this type.
	 * 
	 * @return whether I am the {@link #NULL} instance
	 */
	public boolean isNull() {
		return this == NULL;
	}

	/**
	 * Converts me to the EMF {@link Notification} type code.
	 * 
	 * @return my corresponding EMF notification type
	 */
	public int toNotificationType() {
		return notificationTypeCode;
	}

	// re-implements the inherited method
	@Override
	public String toString() {
		return getName();
	}

	/**
	 * Implements the instance substitution protocol defined by the Java
	 * Serialization Specification.
	 * 
	 * @return the correct pre-defined instance of the enumeration
	 */
	private Object readResolve() {
		return nameToInstance.get(name);
	}
}
