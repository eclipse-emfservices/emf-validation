/******************************************************************************
 * Copyright (c) 2003, 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.internal.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.InvalidRegistryObjectException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.util.XmlConfig;
import org.osgi.framework.Bundle;

/**
 * A custom implementation of the Eclipse configuration element API which is
 * parsed from XML.  This unifies the
 * representation of constraint data obtained from "included" XML files with
 * constraint data parsed by Eclipse from the <tt>plugin.xml</tt>.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class XmlConfigurationElement implements IConfigurationElement {
	private final String myName;
	private final IExtension extension;
	private final URL baseUrl;

	private String value;
	private final Map<String, String> attributes;
	private final List<IConfigurationElement> children =
		new java.util.ArrayList<IConfigurationElement>();
	private Object parent;

	/**
	 * Initializes me with my XML tag name and source extension.
	 * 
	 * @param name my tag name
	 * @param extension the extension which defines me
	 * @param baseUrl the base of any relative URL of files that I load
	 */
	XmlConfigurationElement(String name, IExtension extension, URL baseUrl) {
		this(name, new java.util.HashMap<String, String>(), extension, baseUrl);
	}

	/**
	 * Initializes me with my XML tag name, attributes, and source extension.
	 * 
	 * @param name my tag name
	 * @param attributes my attribute values, which I am free to retain as is
	 *        and modify (no defensive copy required)
	 * @param extension the extension which defines me
	 * @param baseUrl the base of any relative URL of files that I load
	 */
	XmlConfigurationElement(
			String name,
			Map<String, String> attributes,
			IExtension extension,
			URL baseUrl) {
		this.myName = name;
		this.extension = extension;
		this.baseUrl = baseUrl;
		this.attributes = attributes;
	}

	/**
	 * The custom configuration element implementation cannot create
	 * executable extensions.  However, this doesn't matter because it should
	 * never be asked to do so.
	 * 
	 * @throws CoreException always
	 */
	public Object createExecutableExtension(String propertyName)
			throws CoreException {
		
		String message = EMFModelValidationPlugin.getMessage(
				EMFModelValidationStatusCodes.XML_CREATE_EXTENSION_MSG,
				new Object[] {getName()});
		
		CoreException ce = new CoreException(
				new Status(
						IStatus.ERROR,
						EMFModelValidationPlugin.getPluginId(),
						EMFModelValidationStatusCodes.ERROR_PARSING_XML,
						message,
						null));
		
		Trace.throwing(getClass(), "createExecutableExtension", ce); //$NON-NLS-1$
		throw ce;
	}

	// implements the interface method
	public String getAttribute(String name) {
		return attributes.get(name);
	}

	// implements the interface method
	public String getAttributeAsIs(String name) {
		return getAttribute(name);
	}

	// implements the interface method
	public String[] getAttributeNames() {
		return attributes.keySet().toArray(new String[attributes.size()]);
	}

	/**
	 * Sets an attribute value.
	 * 
	 * @param name the attribute name
	 * @param newValue its new value
	 */
	protected final void putAttribute(String name, String newValue) {
		attributes.put(name, newValue);
	}

	// implements the interface method
	public IConfigurationElement[] getChildren() {
		return children.toArray(new IConfigurationElement[children.size()]);
	}

	// implements the interface method
	public IConfigurationElement[] getChildren(String name) {
		java.util.List<IConfigurationElement> result =
			new java.util.ArrayList<IConfigurationElement>(children.size());

		for (IConfigurationElement next : children) {
			if (next.getName().equals(name)) {
				result.add(next);
			}
		}

		return result.toArray(new IConfigurationElement[result.size()]);
	}

	/**
	 * Adds a child element to me.
	 * 
	 * @param child the new child element
	 */
	protected final void addChild(IConfigurationElement child) {
		children.add(child);
		
		if (child instanceof XmlConfigurationElement) {
			((XmlConfigurationElement)child).setParent(this);
		}
	}

	/**
	 * Removes a child element from me.
	 * 
	 * @param child the new child element
	 */
	protected final void removeChild(IConfigurationElement child) {
		children.remove(child);
	}
	
	// implements the interface method
	public IExtension getDeclaringExtension() {
		return extension;
	}

	// implements the interface method
	public String getName() {
		return myName;
	}

	// implements the interface method
	public String getValue() {
		return value;
	}

	/**
	 * Sets my value (body text).
	 * 
	 * @param value my new body text
	 */
	protected final void setValue(String value) {
		this.value = value;
	}

	// implements the interface method
	public String getValueAsIs() {
		return getValue();
	}

	/**
	 * Absorbs the constraints defined in the nested
	 * <tt>&lt;constraints&gt;</tt> elements into me.
	 *  
	 * @param constraintses some <tt>&lt;constraints&gt;</tt> elements
	 * @throws CoreException if there is any problem in parsing the nested
	 *     constraintses
	 */
	public void flatten(IConfigurationElement[] constraintses) throws CoreException {
		for (IConfigurationElement element : constraintses) {
			flatten(element);
			removeChild(element);
		}
	}

	/**
	 * Absorbs the constraints defined in the nested
	 * <tt>&lt;constraints&gt;</tt> element into me.
	 *  
	 * @param constraints a <tt>&lt;constraints&gt;</tt> elements
	 * @throws CoreException if there is any problem in parsing the nested
	 *     element
	 */
	private void flatten(IConfigurationElement constraints) throws CoreException {
		IConfigurationElement flattened =
			XmlConfig.parseConstraintsWithIncludes(constraints);

		absorb(flattened);
	}

	/**
	 * Absorbs the constraints defined in the files specified by some
	 * <tt>&lt;include&gt;</tt> elements into me.
	 *  
	 * @param includes some <tt>&lt;include&gt;</tt> elements
	 * @throws CoreException if there is any problem in parsing the included
	 *     files
	 */
	public void include(IConfigurationElement[] includes) throws CoreException {
		for (IConfigurationElement element : includes) {
			include(element);
		}
	}

	/**
	 * Absorbs the constraints defined in the file specified by an
	 * <tt>&lt;include&gt;</tt> element into me.
	 *  
	 * @param include an <tt>&lt;include&gt;</tt> elements
	 * @throws CoreException if there is any problem in parsing the included
	 *     file
	 */
	private void include(IConfigurationElement include) throws CoreException {
		final String path = include.getAttribute(XmlConfig.A_PATH);

		assert path != null;

		Bundle contributor = Platform.getBundle(
			getDeclaringExtension().getNamespaceIdentifier());

		URL url;
		
		if ((getBaseUrl() == null) || path.startsWith("/")) { //$NON-NLS-1$
			// relative to the plug-in directory (pseudo-absolute)
			url = FileLocator.find(contributor, new Path(path), null);
		} else {
			// relative to my base URL
			
			try {
				url = new URL(getBaseUrl(), path);
			} catch (MalformedURLException mue) {
				url = null;
			}
		}

		if (url == null) {
			String message = EMFModelValidationPlugin.getMessage(
					EMFModelValidationStatusCodes.XML_INCLUDE_FILE_MSG,
					new Object[] {contributor.getSymbolicName(), path});
		
			CoreException ce = new CoreException(
					new Status(
							IStatus.ERROR,
							EMFModelValidationPlugin.getPluginId(),
							EMFModelValidationStatusCodes.ERROR_PARSING_XML,
							message,
							null));
		
			Trace.throwing(getClass(), "createExecutableExtension", ce); //$NON-NLS-1$
			throw ce;
		}

		try {
			IConfigurationElement includedConstraints = XmlConfig.load(this, url);
			absorb(includedConstraints);
		} catch (CoreException e) {
			Trace.catching(getClass(), "include", e); //$NON-NLS-1$
			
			// couldn't load this include file.  Oh, well.  Log the problem
			//   for later diagnosis
			
			Log.log(e.getStatus());
		}
	}
	
	/**
	 * Absorbs the children of the specified <tt>&lt;includedConstraints&gt;</tt>
	 * element into me.
	 * 
	 * @param includedConstraints the element to absorb
	 */
	private void absorb(IConfigurationElement includedConstraints) {
		IConfigurationElement[] elements = includedConstraints.getChildren();
		
		for (IConfigurationElement element : elements) {
			addChild(element);
		}
	}

	// redefines the inherited method
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(64);

		result.append("ConfigurationElement["); //$NON-NLS-1$
		result.append(getName());
		result.append(": "); //$NON-NLS-1$
		result.append(attributes);
		result.append("]"); //$NON-NLS-1$
		return result.toString();
	}

	/**
	 * Obtains the URL on which any additional relative URLs are based that I
	 * may load.
	 * 
	 * @return my base URL
	 */
	public URL getBaseUrl() {
		return baseUrl;
	}
	
	/**
	 * Obtains the name of the XML file from which I was loaded.
	 * 
	 * @return my originating file name
	 */
	public String getFileName() {
		return getBaseUrl().getFile();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IConfigurationElement#getParent()
	 */
	public Object getParent() {
		return parent;
	}

	/**
	 * Sets my parent configuration element (or whatever).
	 * 
	 * @param parent
	 */
	void setParent(Object parent) {
		this.parent = parent;
	}

	public String getNamespace() throws InvalidRegistryObjectException {
		return null;
	}

	public boolean isValid() {
		return false;
	}

	public String getNamespaceIdentifier() throws InvalidRegistryObjectException {
		return null;
	}

	public IContributor getContributor() throws InvalidRegistryObjectException {
		return null;
	}

	public String getAttribute(String attrName, String locale)
			throws InvalidRegistryObjectException {
		return null;
	}

	public String getValue(String locale) throws InvalidRegistryObjectException {
		return null;
	}
}
