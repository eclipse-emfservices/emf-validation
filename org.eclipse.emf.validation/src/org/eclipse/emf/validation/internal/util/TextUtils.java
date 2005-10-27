/******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.internal.util;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.provider.IItemLabelProvider;

/**
 * Utilities for working with text in EMF models.  So far, the following
 * capabilities are available:
 * <ul>
 *   <li>obtaining text representations of model elements for use in the UI
 *       (see {@link #getText(EObject)})</li>
 * </ul>
 *
 * @author Christian W. Damus (cdamus)
 */
public class TextUtils {
	private static AdapterFactory defaultFactory =
		new EcoreItemProviderAdapterFactory();
	
	/**
	 * Not instantiable by clients.
	 */
	private TextUtils() {
		super();
	}		
	
	/**
	 * Obtains a textual representation of the specified model element, as for
	 * display in error messages.  If no suitable factory is registered, then
	 * the EMF reflective item provider is used.
	 * 
	 * @param eObject the model element for which to get text
	 * @return the corresponding text
	 */
	public static String getText(EObject eObject) {
		IItemLabelProvider provider =
			(IItemLabelProvider)getRegisteredAdapter(
				eObject,
				IItemLabelProvider.class);

		if (provider == null) {
			provider = (IItemLabelProvider)defaultFactory.adapt(
					eObject,
					IItemLabelProvider.class);
		}
		
		String result = provider.getText(eObject);
		
		if (result != null) {
			// don't want leading or trailing blanks in messages
			result = result.trim();
		}
		
		return result;
	}
	
	/**
	 * Similar to the {@link EcoreUtil#getRegisteredAdapter(EObject, Object)}
	 * method, attempts to adapt the given <code>eObject</code> to the
	 * specified <code>type</code> using adapter factories registered on its
	 * resource set.  The difference is, that this method anticipates that
	 * adapter factories from multiple disjoint metamodels may be registered,
	 * that adapt different kinds of objects to the same types.  This method
	 * will try them all until it either gets a successful adaptation or runs
	 * out of factories.
	 * 
	 * @param eObject the model element to adapt
	 * @param type indicates the type of adapter to obtain
	 * @return the available registered adapter, or <code>null</code> if no
	 *     suitable adapter factory is found
	 */
	private static Object getRegisteredAdapter(EObject eObject, Object type) {
		Object result = EcoreUtil.getExistingAdapter(eObject, type);
		
		if (result == null) {
			Resource resource = eObject.eResource();
			
			if (resource != null) {
				ResourceSet resourceSet = resource.getResourceSet();
				
				if (resourceSet != null) {
					List factories = resourceSet.getAdapterFactories();
					
					// iterate only as long as we don't find an adapter factory
					//    that successfully adapted the eObject
					for (Iterator iter = factories.iterator(); iter.hasNext() && (result == null);) {
						AdapterFactory next = (AdapterFactory) iter.next();
						
						if (next.isFactoryForType(type)) {
							result = next.adapt(eObject, type);
						}
					}
				}
			}
		}
		
		return result;
	}
}
