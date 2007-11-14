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


package org.eclipse.emf.validation.internal.service;

import java.util.Collection;

import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IModelConstraintProvider;

/**
 * Interface for meta-data describing a constraint provider registered on the
 * <tt>&lt;constraintProviders&gt;</tt> extension point.
 * This implements a first-pass filter based on the meta-model ID
 * (URI namespace) and evaluation mode context to avoid loading providers
 * which do not apply to a request (see the {@link #provides} method).
 * Also contains information relevant to caching support.
 * 
 * @author Christian W. Damus (cdamus)
 */
public interface IProviderDescriptor {
	/**
	 * Queries whether the provider that I represent can potentially
	 * provide any constraints for the specified operation.
	 * 
	 * @param operation a "get constraints" request
	 * @return whether the provider has any chance of providing constraints
	 *     for this context
	 */
	boolean provides(
			IProviderOperation<? extends Collection<? extends IModelConstraint>> operation);
	
	/**
	 * Queries whether the system should cache constraints retrieved from
	 * this provider.
	 * 
	 * @return whether my constraints should be cached
	 */
	boolean isCacheEnabled();
	
	/**
	 * Queries whether I am the XML constraint provider, which declares
	 * constraints in the plug-in manifest and/or additional XML files.
	 * 
	 * @return whether I represent an instance of the XML constraint provider
	 */
	boolean isXmlProvider();
	
	/**
	 * Queries whether I am the special caching constraint provider.
	 * 
	 * @return whether I am the constraint cache
	 */
	boolean isCache();
	
	/**
	 * Obtains my provider.  It is lazily instantiated to delay the loading
	 * of the contributing plug-in.  If, for some reason, the provider
	 * cannot be initialized, then a "null provider" is returned which
	 * never does anything.
	 * 
	 * @return my provider
	 */
	IModelConstraintProvider getProvider();
}
