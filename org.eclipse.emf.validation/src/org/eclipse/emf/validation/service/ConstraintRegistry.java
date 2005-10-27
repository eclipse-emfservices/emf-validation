/******************************************************************************
 * Copyright (c) 2003 IBM Corporation and others.
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
	
	private final Map descriptors = new java.util.HashMap();
	
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
		return (IConstraintDescriptor)descriptors.get(id);
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
		return (IConstraintDescriptor)descriptors.get(
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
	public Collection getAllDescriptors() {
		return descriptors.values();
	}
	
	/**
	 * Registers a new constraint descriptor.
	 * 
	 * @param descriptor a new constraint descriptor, which must have a
	 *     unique ID (not <code>null</code>)
	 * @throws ConstraintExistsException if the <code>descriptor</code>'s ID
	 *     already exists in the registry
	 */
	public void register(IConstraintDescriptor descriptor)
			throws ConstraintExistsException {
		assert descriptor != null;
		
		if (descriptors.containsKey(descriptor.getId())) {
			throw new ConstraintExistsException(descriptor.getId());
		}
		
		descriptors.put(descriptor.getId(), descriptor);
	}
	
	/**
	 * Unregisters an existing constraint descriptor.  This
	 * <code>descriptor</code>'s ID will subsequently be available for re-use.
	 * 
	 * @param descriptor a constraint descriptor (not <code>null</code>)
	 */
	public void unregister(IConstraintDescriptor descriptor) {
		assert descriptor != null;
		
		descriptors.remove(descriptor.getId());
	}
}
