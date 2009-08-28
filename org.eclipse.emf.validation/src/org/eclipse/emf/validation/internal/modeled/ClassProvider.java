/******************************************************************************
 * Copyright (c) 2009 SAP AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    SAP AG - initial API and implementation 
 ****************************************************************************/
package org.eclipse.emf.validation.internal.modeled;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

/**
 * Provides classes in standalone scenario.
 * 
 * @author Boris Gruschko
 *
 */
public interface ClassProvider {
	public Class<?> loadClass(String name) throws ClassNotFoundException;
	public String bind(String string, Object[] args);

	
	public static class ClassLoaderProvider implements ClassProvider {

		private final ResourceLocator locator;

		public ClassLoaderProvider(ResourceLocator locator) {
			this.locator = locator;
		}
		
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			return locator.getClass().getClassLoader().loadClass(name);
		}

		public String bind(String string, Object[] args) {
			if ( string == null ) {
				return null;
			}
			
			try {
				if ( string.startsWith("%") ) { //$NON-NLS-1$
					return locator.getString(string.substring(1), args, true);
				} else {
					return locator.getString(string, args, true);
				}
			} catch (MissingResourceException e ) {
				return string;
			}
		}

		
	}
	
	public static class BundleProvider implements ClassProvider {

		private final Bundle bundle;

		public BundleProvider(Bundle bundle) {
			this.bundle = bundle;
		}
		
		public Class<?> loadClass(String name)
				throws ClassNotFoundException {
			return bundle.loadClass(name);
		}
		
		public ResourceBundle getResourceBundle(String name) {
			return Platform.getResourceBundle(bundle);
		}

		public String bind(String string, Object[] args) {
			if ( string == null )
				return null;
			
			return NLS.bind(Platform.getResourceString(bundle, string), args);
		}
		
	}
}