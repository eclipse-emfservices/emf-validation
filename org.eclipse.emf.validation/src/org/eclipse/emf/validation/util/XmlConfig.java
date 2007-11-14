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


package org.eclipse.emf.validation.util;

import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.util.ConstraintsConfigurationElement;
import org.eclipse.emf.validation.internal.util.ConstraintsContentHandler;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.internal.util.XmlConfigurationElement;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.CategoryManager;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;

import com.ibm.icu.util.StringTokenizer;

/**
 * <p>
 * Static utilities for loading the constraint provider configurations from
 * XML documents.
 * </p>
 * <p>
 * This class is not intended to be used outside of the validation framework.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class XmlConfig {
	//
	// Manifest constants for element and attribute names in the XML
	//
	
	public static final String E_CONSTRAINT_PROVIDER = "constraintProvider"; //$NON-NLS-1$
	public static final String A_ID = "id"; //$NON-NLS-1$
	public static final String A_MODE = "mode"; //$NON-NLS-1$
	public static final String A_CACHE = "cache"; //$NON-NLS-1$
	
	public static final String E_PACKAGE = "package"; //$NON-NLS-1$
	public static final String A_NAMESPACE_URI = "namespaceUri"; //$NON-NLS-1$

	public static final String E_CONSTRAINTS = "constraints"; //$NON-NLS-1$
	public static final String A_CATEGORIES = "categories"; //$NON-NLS-1$
	
	public static final String E_INCLUDED_CONSTRAINTS = "includedConstraints"; //$NON-NLS-1$
	
	public static final String E_CONSTRAINT = "constraint"; //$NON-NLS-1$
	public static final String A_LANG = "lang"; //$NON-NLS-1$
	public static final String A_SEVERITY = "severity"; //$NON-NLS-1$
	public static final String A_STATUS_CODE = "statusCode"; //$NON-NLS-1$
	public static final String A_CLASS = "class"; //$NON-NLS-1$
	
	public static final String E_INCLUDE = "include"; //$NON-NLS-1$
	public static final String A_PATH = "path"; //$NON-NLS-1$
	
	public static final String E_TARGET = "target"; //$NON-NLS-1$
	
	public static final String E_EVENT = "event"; //$NON-NLS-1$
    public static final String E_CUSTOM_EVENT = "customEvent"; //$NON-NLS-1$
	public static final String A_NAME = "name"; //$NON-NLS-1$
	
	public static final String E_FEATURE = "feature"; //$NON-NLS-1$
	
	public static final String E_DESCRIPTION = "description"; //$NON-NLS-1$
	
	public static final String E_MESSAGE = "message"; //$NON-NLS-1$
	
	public static final String E_PARAM = "param"; //$NON-NLS-1$
	public static final String A_VALUE = "value"; //$NON-NLS-1$
	
	public static final String E_CATEGORY = "category"; //$NON-NLS-1$
	public static final String A_MANDATORY = "mandatory"; //$NON-NLS-1$
	public static final String A_ENABLED = "enabled"; //$NON-NLS-1$
	
	/**
	 * Cannot be instantiated by clients.
	 */
	private XmlConfig() {
		super();
	}

	/**
	 * Parses a <tt>&lt;constraints&gt;</tt> element into an Eclipse
	 * configuration element data structure, with support for including
	 * constraints from separate XML files.
	 * 
	 * @param constraints an Eclipse configuration element obtained either
	 *   from Eclipse's extension point parser or from this utility class
	 * @return the Eclipse-ish representation of the XML constraint
	 *   configurations
	 * @throws CoreException if there is any problem either in accessing an
	 *   existing configuration element or in parsing the XML to create new ones
	 */
	public static IConfigurationElement parseConstraintsWithIncludes(
			IConfigurationElement constraints) throws CoreException {
		
		if (Trace.shouldTrace(EMFModelValidationDebugOptions.XML)) {
			String sourcePluginId = constraints
				.getDeclaringExtension()
				.getNamespaceIdentifier();

			Trace.trace(
					EMFModelValidationDebugOptions.XML,
					"Loading constraints from plugin: " + sourcePluginId); //$NON-NLS-1$
		}

		IConfigurationElement result = constraints;
		
		if (constraints.getName().equals(E_CONSTRAINTS)) {
			result = resolveCategories(constraints);
		} else if (constraints.getName().equals(E_INCLUDED_CONSTRAINTS)) {
			IConfigurationElement[] constraintses = constraints.getChildren(
					E_CONSTRAINTS);
	
			if (constraintses.length > 0) {
				result = resolveIncludedConstraints(constraints, constraintses);
			}
		}
		
		IConfigurationElement[] includes = constraints.getChildren(E_INCLUDE);

		if (includes.length > 0) {
			result = resolveIncludes(constraints, includes);
		}
		
		return result;
	}
	
	/**
	 * Gets the value of the <code>name</code>d parameter on the specified
	 * <code>constraint</code> configuration element.  If the parameter occurs
	 * more than once, only the first name will be retrieved.
	 * 
	 * @param constraint the <tt>&lt;constraint&gt;</tt> configuration element
	 * @param name the name of the parameter to retrieve
	 * @return the parameter's value, or <code>null</code> if no such parameter
	 *     is defined
	 * 
	 * @see #getParameterValues
	 */
	public static String getParameter(
		IConfigurationElement constraint,
		String name) {
		
		IConfigurationElement match = null;
		String result = null;
		
		IConfigurationElement[] parms = constraint.getChildren(E_PARAM);
		for (int i = 0; (match == null) && (i < parms.length); i++) {
			if (name.equals(parms[i].getAttribute(A_NAME))) {
				match = parms[i];
			}
		}
		
		if (match != null) {
			result = match.getAttribute(A_VALUE);
			
			if (result == null) {
				result = match.getValue();
			}
		}
		
		return result;
	}
	
	/**
	 * Gets the values of the <code>name</code>d parameter in the order in which
	 * they appear on the specified <code>constraint</code> configuration
	 * element.
	 * 
	 * @param constraint the <tt>&lt;constraint&gt;</tt> configuration element
	 * @param name the name of the parameter to retrieve
	 * @return the parameter's values, in order.  Will be an empty array (not
	 *    <code>null</code>) if no occurrences of the parameter are found
	 * 
	 * @see #getParameter
	 */
	public static String[] getParameterValues(
		IConfigurationElement constraint,
		String name) {
		
		List<String> result = new java.util.ArrayList<String>();
		
		IConfigurationElement[] parms = constraint.getChildren(E_PARAM);
		for (IConfigurationElement element : parms) {
			if (name.equals(element.getAttribute(A_NAME))) {
				String value = element.getAttribute(A_VALUE);
				
				if (value == null) {
					value = element.getValue();
				}
				
				if (value != null) {
					result.add(value);
				}
			}
		}
		
		return result.toArray(new String[result.size()]);
	}

	/**
	 * Resolves the category references in the specified
	 * <tt>&lt;constraints&gt;</tt> element to {@link Category} instances, and
	 * adds to them the constraint descriptors that are their members.
	 * 
	 * @param constraints the <tt>&lt;constraints&gt;</tt> element
	 * @return the same element
	 */
	private static IConfigurationElement resolveCategories(
			IConfigurationElement constraints) {

		final CategoryManager mgr = CategoryManager.getInstance();
		final String contributorId = constraints
			.getDeclaringExtension()
			.getNamespaceIdentifier();
		
		IConfigurationElement[] children = constraints.getChildren(
				XmlConfig.E_CONSTRAINT);
		
		String categories = constraints.getAttribute(XmlConfig.A_CATEGORIES);
		if (categories == null) {
			categories = ""; //$NON-NLS-1$
		}
		
		StringTokenizer tokens = new StringTokenizer(categories, ","); //$NON-NLS-1$
		while (tokens.hasMoreTokens()) {
			String path = tokens.nextToken().trim();
			
			if (path.length() > 0) {
				for (IConfigurationElement element : children) {
					final String id = element.getAttribute(A_ID);
					
					IConstraintDescriptor constraint =
						ConstraintRegistry.getInstance().getDescriptor(
							contributorId,
							id);
					
					if (constraint == null) {
						try {
							constraint = new XmlConstraintDescriptor(element);
						} catch (ConstraintExistsException e) {
							// shouldn't happen because I checked for existence
							continue;
						}
					} else {
					    // duplicate constraint case.  Log it
                        Log.warningMessage(
                            EMFModelValidationStatusCodes.PROVIDER_DUPLICATE_CONSTRAINT,
                            EMFModelValidationStatusCodes.PROVIDER_DUPLICATE_CONSTRAINT_MSG,
                            new Object[] {constraint.getId()});
					}
					
					Category category = mgr.findCategory(path);
					if (category != null) {
						constraint.addCategory(category);
					}
				}
			}
		}
		
		return constraints;
	}
	
	/**
	 * Passes over a <tt>&lt;constraints&gt;</tt> element, replacing occurrences
	 * of <tt>&lt;include&gt;</tt> elements with the constraints obtained from
	 * the included files.  Essentially "flattens" the include structure into a
	 * single giant <tt>&lt;constraints&gt;</tt> element.
	 * 
	 * @param constraints the original <tt>&lt;constraints&gt;</tt> element
	 * @param includes the <tt>&lt;include&gt;</tt> elements to be merged into
	 *    it
	 * @return the merged <tt>&lt;constraints&gt;</tt> element
	 * @throws CoreException on any problem accessing a configuration element
	 *    or parsing an XML file
	 */
	private static IConfigurationElement resolveIncludes(
			IConfigurationElement constraints,
			IConfigurationElement[] includes) throws CoreException {
		
		ConstraintsConfigurationElement result =
			new ConstraintsConfigurationElement(
				constraints,
				getBaseUrl(constraints));

		result.include(includes);

		return result;
	}

	/**
	 * Passes over an <tt>&lt;includedConstraints&gt;</tt> element, replacing
	 * occurrences of nested <tt>&lt;constraints&gt;</tt> elements with the
	 * constraints defined in them.  Essentially "flattens" the constraints
	 * structure into a single giant <tt>&lt;constraints&gt;</tt> element.
	 * 
	 * @param constraints the original <tt>&lt;includedConstraints&gt;</tt>
	 *    element
	 * @param constraintses the nested <tt>&lt;constraints&gt;</tt> elements to
	 *    be merged into it
	 * @return the merged <tt>&lt;constraints&gt;</tt> element
	 * @throws CoreException on any problem accessing a configuration element
	 *    or parsing an XML file
	 */
	private static IConfigurationElement resolveIncludedConstraints(
			IConfigurationElement constraints,
			IConfigurationElement[] constraintses) throws CoreException {
		
		ConstraintsConfigurationElement result =
			new ConstraintsConfigurationElement(
				constraints,
				getBaseUrl(constraints));

		result.flatten(constraintses);

		return result;
	}
	
	/**
	 * Obtains the URL from which the specified <code>element</code> was loaded.
	 * 
	 * @param element the XML configuration element
	 * @return the URL from which it was loaded
	 */
	private static URL getBaseUrl(IConfigurationElement element) {
		if (element instanceof XmlConfigurationElement) {
			return ((XmlConfigurationElement)element).getBaseUrl();
		} else {
			return Platform.getBundle(
				element.getDeclaringExtension().getNamespaceIdentifier()).getEntry("/"); //$NON-NLS-1$
		}
	}

	/**
	 * Loads a <tt>&lt;constraints&gt;</tt> element from the specified
	 * <code>url</code>.
	 * 
	 * @param parent the configuration element which is to be the parent
	 *     of the new <tt>&lt;constraints&gt;</tt> element
	 * @param url the location of the document defining the
	 *     <tt>&lt;constraints&gt;</tt> element
	 * @return the configuration element representing the XML document
	 * @throws CoreException on any problem accessing a configuration element
	 *    or parsing an XML file
	 */
	public static IConfigurationElement load(IConfigurationElement parent, URL url)
			throws CoreException {

		Trace.trace(
				EMFModelValidationDebugOptions.XML,
				"Loading constraints include file: " + url); //$NON-NLS-1$
		
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setValidating(false);

		try {
			SAXParser parser = spf.newSAXParser();

			ConstraintsContentHandler handler =
				new ConstraintsContentHandler(
					parent.getDeclaringExtension(),
					url);

			parser.parse(url.toString(), handler);

			IConfigurationElement result = handler.getResult();

			// recursively include referenced constraints files
			return parseConstraintsWithIncludes(result);
		} catch (CoreException e) {
			throw e;
		} catch (Exception e) {
			Trace.catching(XmlConfig.class, "load", e); //$NON-NLS-1$

			String sourcePluginId = parent.getDeclaringExtension().getNamespaceIdentifier();

			CoreException ce = new CoreException(new Status(
					IStatus.ERROR,
					EMFModelValidationPlugin.getPluginId(),
					EMFModelValidationStatusCodes.ERROR_PARSING_XML,
					EMFModelValidationPlugin.getMessage(
							EMFModelValidationStatusCodes.ERROR_PARSING_XML_MSG,
							new Object[] {sourcePluginId}),
					e));
			
			Trace.throwing(XmlConfig.class, "load", ce); //$NON-NLS-1$
			
			throw ce;
		}
	}
	
	/**
	 * Flushes the resource bundles that were loaded for localization of strings
	 * in an XML constraint provider's XML constraint declarations.
	 */
	public static void flushResourceBundles() {
		ConstraintsContentHandler.flushResourceBundleCache();
	}
    
    /**
     * Obtains an array including all of the <tt>event</tt> and <tt>customEvent</tt>
     * children of the specified <tt>config</tt>uration element.
     * 
     * @param config a configuration element
     * @return its event children
     * 
     * @since 1.1
     */
    public static IConfigurationElement[] getEvents(IConfigurationElement config) {
        IConfigurationElement[] events = config.getChildren(E_EVENT);
        IConfigurationElement[] customEvents = config
            .getChildren(E_CUSTOM_EVENT);

        IConfigurationElement[] result = new IConfigurationElement[events.length
            + customEvents.length];
        System.arraycopy(events, 0, result, 0, events.length);
        System.arraycopy(customEvents, 0, result, events.length,
            customEvents.length);

        return result;
    }
}
