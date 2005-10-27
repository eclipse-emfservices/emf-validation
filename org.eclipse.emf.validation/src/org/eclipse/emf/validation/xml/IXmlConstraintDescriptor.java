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


package org.eclipse.emf.validation.xml;

import org.eclipse.core.runtime.IConfigurationElement;

import org.eclipse.emf.validation.service.IConstraintDescriptor;

/**
 * Interface provided by constraint descriptors that are parsed from XML
 * configuration data (such as is implemented in plug-in manifests).
 *
 * @author Christian W. Damus (cdamus)
 */
public interface IXmlConstraintDescriptor extends IConstraintDescriptor {
	/**
	 * Obtains the Eclipse configuration element from which I was initialized.
	 * 
	 * @return my Eclipse extension configuration data
	 */
	public IConfigurationElement getConfig();
	
	/**
	 * <p>
	 * I resolve all class names registered with me from <tt>%lt;target&gt;</tt>
	 * elements in the constraint XML to {@link org.eclipse.emf.ecore.EClass}
	 * instances in the specified URI namespace.
	 * </p>
	 * <p>
	 * This method must be invoked before any requests for constraints are
	 * received by the framework. 
	 * </p>
	 * 
	 * @param namespaceUris the namespace URIs of the EPackages in which to
	 *    search for {@link org.eclipse.emf.ecore.EClass}es by name
	 */
	public void resolveTargetTypes(String[] namespaceUris);
}
