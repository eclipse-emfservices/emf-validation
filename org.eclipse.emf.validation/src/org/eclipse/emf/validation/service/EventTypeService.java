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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.emf.validation.internal.util.Trace;

/**
 * The Event Type Service is responsible for loading the <tt>eventTypes</tt>
 * Eclipse extension point and creating {@link EMFEventType}s for the
 * event types that have been contributed.
 * 
 * It provides accessor methods for retrieving {@link INotificationGenerator}s
 * that have been contributed along with the {@link EMFEventType}s.
 * 
 * @since 1.1
 *
 * @author David Cummings (dcummin)
 */
public class EventTypeService {

	private static EventTypeService instance = new EventTypeService();
	
	private static final String A_NAME = "name"; //$NON-NLS-1$
	private static final String A_FEATURE_SPECIFIC = "featureSpecific"; //$NON-NLS-1$
	private static final String A_NOTIFICATION_GENERATOR = "notificationGenerator"; //$NON-NLS-1$
	
	private final Map<String, INotificationGenerator> notificationGenerators;
	
	/**
	 * Cannot be instantiated by clients.
	 */
	private EventTypeService() {
		notificationGenerators = new HashMap<String, INotificationGenerator>();
		
		configureEventTypes();
	}
    
    /**
     * Configures custom event types based on the 
     * <tt>eventTypes</tt> extension configuration
     */
    private void configureEventTypes() {
        IConfigurationElement[] configs =
            Platform.getExtensionRegistry().getConfigurationElementsFor(
                EMFModelValidationPlugin.getPluginId(),
                EMFModelValidationPlugin.EVENT_TYPES_EXT_P_NAME);
        
        for (IConfigurationElement element : configs) {
            if (element.getName().equals("eventType")) { //$NON-NLS-1$
                try {
                    String name = element.getAttribute(A_NAME);
                    if ((name != null) && (name.length() > 0)) {
                        EMFEventType.addEventType(name,
                                Boolean.valueOf(element.getAttribute(A_FEATURE_SPECIFIC)).booleanValue()
                                );
                        
                        String notificationGenerator = element.getAttribute(A_NOTIFICATION_GENERATOR);
                        if ((notificationGenerator != null) && (notificationGenerator.length() > 0)) {
                            notificationGenerators.put(
                                name,
                                (INotificationGenerator) element.createExecutableExtension(A_NOTIFICATION_GENERATOR));
                        }
                    }
                } catch (CoreException e) {
                    Trace.catching(getClass(), "configureEventTypes()", e); //$NON-NLS-1$
                    
                    Log.log(e.getStatus());
                }
            }
        }
    }

	/**
	 * Obtains the instance of this class.
	 * 
	 * @return the <em>Singleton</em> instance
	 */
	public static EventTypeService getInstance() {
		return instance;
	}

	/**
	 * <p>
	 * Configures event types from the Eclipse configuration
	 * <code>elements</code> representing implementations of my extension point.
	 * </p>
	 * <p>
	 * <b>NOTE</b> that this method should only be called by the EMF Model
	 * Validation Plug-in, not by any client code!
	 * </p>
	 * 
	 * @param elements 
	 * 
	 * @deprecated 1.2 This method is no longer implemented
	 */
	@Deprecated
    public void configureEventTypes(IConfigurationElement[] elements) {
		// no longer implemented
	}
	
	/**
	 * Retrieves the available notification generators. 
	 * 
	 * @return collection of notification generators
	 */
	public Collection<INotificationGenerator> getNotificationGenerators() {
		return Collections.unmodifiableCollection(notificationGenerators.values());
	}
	
	/**
	 * Retrieves the {@link INotificationGenerator} associated with the given
	 * event name.
	 * 
	 * @param eventName 
	 * @return notification generator associated with the given event name,
	 *         null otherwise
	 */
	public INotificationGenerator getNotificationGenerator(String eventName) {
		return notificationGenerators.get(eventName);
	}
}
