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


package org.eclipse.emf.validation.service;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;

/**
 * <p>
 * A centralized registry of {@link IConstraintDescriptor descriptors} for
 * the constraints that are available in the validation system.  Clients can
 * use this registry to look up the meta-data for constraints (given the
 * constraint IDs), and constraint providers can register the constraints that
 * they provide in the registry.
 * </p>
 * <p>
 * <b>Note</b> for dynamic constraint providers:  registering constraints in the
 * registry is not absolutely necessary if none of the following capabilities
 * of the system is required for your constraints:
 * <ul>
 *   <li>display in the preference page GUI under the constraint's categories
 *       </li>
 *   <li>meta-data lookup by other constraints or plug-ins</li>
 *   <li>pre-requisite (constraint dependency) controls, replacement/extension
 *       of constraints, and other future facilities that require the lookup
 *       mechanism</li>
 * </ul> 
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintRegistry {
	private static final ConstraintRegistry INSTANCE = new ConstraintRegistry();
	
	private final Map<String, IConstraintDescriptor> descriptors =
	    new java.util.HashMap<String, IConstraintDescriptor>();
	
	private volatile IConstraintListener[] constraintListeners;
	
	/**
	 * Initializes me.
	 */
	private ConstraintRegistry() {
		super();
	}

	/**
	 * Obtains the instance of the constraint registry.
	 * 
	 * @return the <i>Singleton</i> instance
	 */
	public static ConstraintRegistry getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Obtains the unique constraint descriptor having the specified ID.
	 * 
	 * @param id the ID of the constraint descriptor to retrieve
	 *     (not <code>null</code>)
	 * @return the matching constraint descriptor, or <code>null</code> if it
	 *     does not exist
	 */
	public IConstraintDescriptor getDescriptor(String id) {
		return descriptors.get(id);
	}
	
	/**
	 * Obtains the unique constraint descriptor having the specified ID.
	 * The ID is prepended by the supplied plug-in ID, to generate a
	 * fully-qualified ID of the form <tt>&lt;pluginId&gt;.&lt;id&gt;</tt>.
	 * 
	 * @param pluginId the ID of the plug-in that contributes the constraint
	 * @param id the constraint's ID, relative to the plug-in ID
	 * @return the matching constraint descriptor, or <code>null</code> if it
	 *     does not exist
	 */
	public IConstraintDescriptor getDescriptor(String pluginId, String id) {
		return descriptors.get(
				XmlConstraintDescriptor.normalizedId(pluginId, id));
	}
	
	/**
	 * Obtains the descriptors for all registered constraints, in no particular
	 * order.  Note that all disabled (for whatever reason) constraints are
	 * included in the result. 
	 * 
	 * @return the available constraint descriptors, as an unmodifiable
	 *     collection
	 */
	public Collection<IConstraintDescriptor> getAllDescriptors() {
	    synchronized (descriptors) {
	        return new java.util.ArrayList<IConstraintDescriptor>(descriptors.values());
	    }
	}
	
	/**
	 * Registers a constraint descriptor.
	 * 
	 * @param descriptor a new constraint descriptor, which must have a
	 *     unique ID (not <code>null</code>)
	 * @throws ConstraintExistsException if a different descriptor is already
	 *     registered under the given <code>descriptor</code>'s ID
	 */
	public void register(IConstraintDescriptor descriptor)
			throws ConstraintExistsException {
	    
	    boolean registered;
	    
	    synchronized (descriptors) {
	        registered = doRegister(descriptor);
	    }
	    
        if (registered) {
            broadcastConstraintChangeEvent(new ConstraintChangeEvent(
                descriptor, ConstraintChangeEventType.REGISTERED));
        }
    }
	
	/**
     * Unregisters an existing constraint descriptor. This
     * <code>descriptor</code>'s ID will subsequently be available for
     * re-use.
     * 
     * @param descriptor
     *            a constraint descriptor (not <code>null</code>)
     */
	public void unregister(IConstraintDescriptor descriptor) {
		assert descriptor != null;
		
		boolean unregistered;
		
		synchronized (descriptors) {
		    unregistered = descriptors.remove(descriptor.getId()) != null;
		}
		
		if (unregistered) {
    		broadcastConstraintChangeEvent(new ConstraintChangeEvent(descriptor,
    				ConstraintChangeEventType.UNREGISTERED));
		}
	}
	
	/**
	 * Adds an <code>IConstraintListener</code> to receive constraint change
	 * events.  This method has no effect if the <code>IConstraintListener
	 * </code> is already registered.
	 * 
	 * @param listener a new constraint listener
	 * 
	 * @since 1.1
	 */
	public synchronized void addConstraintListener(IConstraintListener listener) {
		if (indexOf(listener) < 0) {
			if (constraintListeners == null) {
				constraintListeners = new IConstraintListener[] {listener};
			} else {
				IConstraintListener[] newListeners =
					new IConstraintListener[constraintListeners.length + 1];
				
				System.arraycopy(constraintListeners, 0, newListeners, 0, constraintListeners.length);
				newListeners[constraintListeners.length] = listener;
				constraintListeners = newListeners;
			}
			
			if (Trace.shouldTrace(EMFModelValidationDebugOptions.LISTENERS)) {
				Trace.trace(
						EMFModelValidationDebugOptions.LISTENERS,
						"Registered constraint listener: " + listener.getClass().getName()); //$NON-NLS-1$
			}
		}
	}
	
	/**
	 * Removes the <code>IConstraintListener</code> from the list of listeners.
	 * This method has no effect if the <code>IConstraintListener</code> is not
	 * currently registered.
	 * 
	 * @param listener a constraint listener
	 * 
	 * @since 1.1
	 */
	public synchronized void removeConstraintListener(IConstraintListener listener) {
		int index = indexOf(listener);
		
		if (index >= 0) {
			IConstraintListener[] newListeners =
				new IConstraintListener[constraintListeners.length - 1];
			
			System.arraycopy(constraintListeners, 0, newListeners, 0, index);
			System.arraycopy(constraintListeners, index + 1, newListeners, index, constraintListeners.length - index - 1);
			constraintListeners = newListeners;
			
			if (Trace.shouldTrace(EMFModelValidationDebugOptions.LISTENERS)) {
				Trace.trace(
						EMFModelValidationDebugOptions.LISTENERS,
						"Deregistered constraint listener: " + listener.getClass().getName()); //$NON-NLS-1$
			}
		}
	}
	
	/**
	 * Computes the index of a specified <code>IConstraintListener</code> in
	 * the array of registered listeners.
	 * 
	 * @param listener a constraint listener
	 * @return the <code>constraint listener</code>'s index, or -1 if it is not
	 *         in my list
	 */
	private int indexOf(IConstraintListener listener) {
		int result = -1;
		if (constraintListeners != null) {
			for (int i = 0; i < constraintListeners.length; i++) {
				if (constraintListeners[i] == listener) {
					result = i;
					break;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Broadcasts the specified <code>ConstraintChangeEvent</code> to all 
     * constraint listeners.  This method is used internally by constraints
     * to send notifications when they have changed.
	 * <p>
     * <b>Note</b> that this method should only be invoked by implementation of
     * of the {@link IConstraintDescriptor} interface.
     * </p>
     * 
	 * @param event a constraint change event to broadcast
	 * 
	 * @since 1.1
	 */
	public void broadcastConstraintChangeEvent(ConstraintChangeEvent event) {
		// Check if listeners exist
		if (constraintListeners == null) {
			return;
        }
				
		IConstraintListener[] array = constraintListeners; // copy the reference
		
		for (int i = 0; i < array.length; i++) {
			try {
				array[i].constraintChanged(event);
			} catch (Exception e) {
				Trace.catching(getClass(), "broadcastConstraintChangeEvent", e); //$NON-NLS-1$
				
				if (Trace.shouldTrace(EMFModelValidationDebugOptions.LISTENERS)) {
					Trace.trace(
							EMFModelValidationDebugOptions.LISTENERS,
							"Uncaught exception in constraint listener: " + array[i].getClass().getName()); //$NON-NLS-1$
				}
				
				Log.l7dWarning(
					EMFModelValidationStatusCodes.LISTENER_UNCAUGHT_EXCEPTION,
					EMFModelValidationStatusCodes.LISTENER_UNCAUGHT_EXCEPTION_MSG,
					e);
			}
		}
	}
	
	/**
	 * Implements the registration of a constraint.  <b>This method requires
	 * that the caller synchronize on the <tt>descriptors</tt> map</b>.
	 * 
	 * @param descriptor a descriptor to register
	 * @return whether the descriptor was added to the registry or not
	 *    (<code>false</code> in the case the same descriptor was already
	 *    registered, which is OK)
	 * 
	 * @throws ConstraintExistsException if a different descriptor was already
	 *    registered under the same ID
	 */
	private boolean doRegister(IConstraintDescriptor descriptor)
            throws ConstraintExistsException {
	    
        boolean result = false;
        String id = descriptor.getId();

        Object existing = descriptors.get(id);

        if (existing == null) {
            result = true;
            descriptors.put(id, descriptor);
        } else if (existing != descriptor) {
            throw new ConstraintExistsException(id);
        }

        return result;
    }
	
	/**
	 * Performs a bulk registration of constraints for efficiency.
	 * 
	 * @param constraints the constraints to register
	 * 
	 * @throws ConstraintExistsException if any constraint's ID is already
	 *    registered under a different descriptor
	 */
	void bulkRegister(Collection<? extends IConstraintDescriptor> constraints)
	throws ConstraintExistsException {
	    Collection<IConstraintDescriptor> registered =
	        new java.util.ArrayList<IConstraintDescriptor>(constraints.size());
	    
	    synchronized (descriptors) {
    	    for (IConstraintDescriptor next : constraints) {
    	        if (doRegister(next)) {
    	            registered.add(next);
    	        }
    	    }
	    }
	    
	    if (!registered.isEmpty()) {
	        ConstraintChangeEvent event =
	            new ConstraintChangeEvent(null, ConstraintChangeEventType.REGISTERED);
	        
    	    for (IConstraintDescriptor next : registered) {
    	        event.setConstraint(next);
                broadcastConstraintChangeEvent(event);
    	    }
	    }
	}
}
