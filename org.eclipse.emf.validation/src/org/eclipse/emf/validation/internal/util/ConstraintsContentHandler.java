/******************************************************************************
 * Copyright (c) 2003, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.internal.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;

/**
 * A SAX content handler for parsing the <tt>&lt;constraints&gt;</tt> XML.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintsContentHandler extends DefaultHandler {
	/**
	 * Processing instruction target for EMF Validation plug-in:
	 * <tt>&lt;?emf-validation?&gt;</tt>.
	 */
	static final String EMF_VALIDATION_INSTRUCTION = "emf-validation"; //$NON-NLS-1$
	
	/**
	 * Processing instruction parameter indicating the version of the
	 * constraint markup language.  The default (and currently only) version
	 * is 1.0.
	 */ 
	static final String VERSION_PARAMETER = "version"; //$NON-NLS-1$
	
	/**
	 * Processing instruction parameter indicating the translated
	 * <tt>.properties</tt> file for localization (a.k.a. "national language"
	 * handling) of strings in the XML.
	 */ 
	static final String NL_PARAMETER = "nl"; //$NON-NLS-1$
	
	/** Currently the only allowed version of the constraint markup. */
	private static final String EXPECTED_VERSION = "1.0"; //$NON-NLS-1$
	
	/** Used to cache resource bundles for localized constraint strings. */
	private static final ResourceBundleCache resourceBundleCache =
		new ResourceBundleCache();
	
	/** The extension which defines the constraints. */
	private final IExtension extension;
	
	/** Base URL on which relative URLs are constructed. */
	private final URL baseUrl;
	
	/** A stack of XML elements. */
	private final Stack stack = new Stack();

	/**
	 * The resulting configuration element that I construct.
	 */
	private IConfigurationElement resultElement;
	
	/**
	 * My resource bundle for localization, if any is specified by a
	 * <tt>&lt;?emf-validation?&gt;</tt> processing instruction.
	 */
	private ResourceBundle resourceBundle;

	/**
	 * Maintains a stack of XML elements as they are encountered by the SAX
	 * parser.  Elements are popped onto and off of the stack as the SAX parser
	 * sees them come and go.  Whenever an element is on the top of the stack,
	 * it can have more data added to it.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private class Stack {
		private final List contents = new java.util.ArrayList();
		private final List bodies = new java.util.ArrayList();
		private int lastIndex = -1;
		
		/**
		 * Queries whether the stack is empty.
		 * 
		 * @return <CODE>true</CODE> if the stack has no elements;
		 *     <CODE>false</CODE>, otherwise
		 */
		public boolean isEmpty() {
			return contents.isEmpty();
		}

		/**
		 * Pushes a new element onto the stack.
		 * 
		 * @param element the new top element
		 */
		public void push(XmlConfigurationElement element) {
			assert element != null;

			contents.add(element);
			bodies.add(new StringBuffer(32));
			
			lastIndex++;
		}

		/**
		 * Pops an element from the top of the stack.  At this point, the
		 * body of the element which has been accumulating is assigned to it.
		 * 
		 * @return the former top element.
		 * @throws SAXException if the stack is empty (in which case it cannot
		 *     be popped)
		 */
		public XmlConfigurationElement pop() throws SAXException {
			if (isEmpty()) {
				SAXException se = new SAXException(
					EMFModelValidationStatusCodes.XML_CANNOT_POP_STACK_MSG);
				
				Trace.throwing(getClass(), "pop", se); //$NON-NLS-1$
				
				throw se;
			}

			XmlConfigurationElement result =
				(XmlConfigurationElement)contents.get(lastIndex);
			
			result.setValue(localize(getBody().toString().trim()));

			contents.remove(lastIndex);
			bodies.remove(lastIndex);

			lastIndex--;
			
			return result;
		}

		/**
		 * Obtains the top element of the stack.
		 * 
		 * @return the top element
		 * @throws SAXException if the stack is empty (in which case it cannot
		 *     be peeked)
		 */
		public XmlConfigurationElement peek() throws SAXException {
			if (isEmpty()) {
				SAXException se = new SAXException(
					EMFModelValidationStatusCodes.XML_CANNOT_PEEK_STACK_MSG);
				
				Trace.throwing(getClass(), "peek", se); //$NON-NLS-1$
				
				throw se;
			}

			return (XmlConfigurationElement)contents.get(lastIndex);
		}

		/**
		 * Gets the string buffer which is accumulating the body of the current
		 * top element of the stack.
		 * 
		 * @return the top element's body
		 * @throws SAXException if the stack is empty (in which case there is
		 *     no body)
		 */
		public StringBuffer getBody() throws SAXException {
			if (isEmpty()) {
				SAXException se = new SAXException(
					EMFModelValidationStatusCodes.XML_NO_STACK_BODY_MSG);
				
				Trace.throwing(getClass(), "getBody", se); //$NON-NLS-1$
				
				throw se;
			}

			return (StringBuffer)bodies.get(lastIndex);
		}
	}
	
	/**
	 * Helper class that caches resource bundles by host OSGI bundle and
	 * name.
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private static final class ResourceBundleCache {
		private final Map map = new java.util.HashMap();
		
		/**
		 * Obtains the resource bundle named <code>baseName</code> in the
		 * specified OSGI bundle's classpath, if it exists in this cache.
		 * 
		 * @param osgiBundle an OSGI bundle that defines a classpath
		 * @param baseName the base of the possibly locale-extended
		 *     resource file name
		 * @return the cached resource bundle, or <code>null</code> if it does
		 *     not exist in the cache
		 */
		ResourceBundle get(Bundle osgiBundle, String baseName) {
			ResourceBundle result = null;
			Map secondLevel = (Map) map.get(osgiBundle);
			
			if (secondLevel != null) {
				result = (ResourceBundle) secondLevel.get(baseName);
			}
			
			return result;
		}
		
		/**
		 * Puts the resource bundle named <code>baseName</code>, in the
		 * specified OSGI bundle's classpath, into this cache.
		 * 
		 * @param osgiBundle an OSGI bundle that defines a classpath
		 * @param baseName the base of the possibly locale-extended
		 *     resource file name
		 * @param rb the resource bundle to cache
		 */
		void put(Bundle osgiBundle, String baseName, ResourceBundle rb) {
			Map secondLevel = (Map) map.get(osgiBundle);
			
			if (secondLevel == null) {
				secondLevel = new java.util.HashMap();
				map.put(osgiBundle, secondLevel);
			}
			
			secondLevel.put(baseName, rb);
		}
		
		/**
		 * Fluhes the entire contents of the cache.
		 */
		void flush() {
			map.clear();
		}
	}

	/**
	 * Initializes me with the extension which defines the XML that I parse.
	 * The extension is used to gain information about the source plug-in.
	 * 
	 * @param extension the source extension
	 * @param baseUrl base URL on which any relative URLs of referenced XML
	 *     files are constructed
	 */
	public ConstraintsContentHandler(
			IExtension extension,
			URL baseUrl) {
		
		this.extension = extension;
		this.baseUrl = baseUrl;
	}

	/**
	 * Obtains the configuration element that I have parsed.
	 * Should only be called when parsing is complete.
	 * 
	 * @return my element, which will be an <tt>&lt;includedConstraints&gt;</tt>
	 */
	public IConfigurationElement getResult() {
		return resultElement;
	}

	/**
	 * Pushes a new element onto the top of the stack.
	 */
	public void startElement(
			String namespaceURI,
			String localName,
			String qName,
			Attributes atts) {
		int attCount = atts.getLength();
		Map attMap = new java.util.HashMap();
		
		for (int i = 0; i < attCount; i++) {
			attMap.put(atts.getQName(i), localize(atts.getValue(i)));
		}
		
		stack.push(
				new XmlConfigurationElement(qName, attMap, extension, baseUrl));
	}

	/**
	 * Pops an element from the top of the stack.  This is retained as my
	 * result if it was the last element, otherwise it is added to the new top
	 * as a child element.
	 * 
	 * @see #getResult
	 */
	public void endElement(
			String namespaceURI,
			String localName,
			String qName) throws SAXException {
		
		resultElement = stack.pop();

		if (!stack.isEmpty()) {
			stack.peek().addChild(resultElement);
			resultElement = null;
		}

	}

	/**
	 * Appends text to the body of the top element on the stack.
	 */
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		
		stack.getBody().append(ch, start, length);
	}
	
	/**
	 * Implements the handling of the <tt>&lt;?emf-validation?&gt;</tt>
	 * processing instruction for the constraints markup.
	 * 
	 * @throws SAXException if a version is indicated in the processing
	 *     instruction that is not a supported version 
	 */
	public void processingInstruction(String target, String data)
			throws SAXException {
		if (target.equals(EMF_VALIDATION_INSTRUCTION)) {
			int[] index = new int[1];
			
			for (int i = data.indexOf('='); i >= 0;) { // known BMP code point
				String parm = data.substring(index[0], i).trim();
				
				index[0] = i + 1; // known BMP code point
				String value = extractQuotedString(data, index);
				
				if (value == null) {
					// no valid parameter value found.  Just break out
					break;
				}
				
				// currently, these are the only supported parameters
				if (parm.equals(VERSION_PARAMETER)) {
					handleVersionInstruction(value);
				} else if (parm.equals(NL_PARAMETER)) {
					handleNlInstruction(value);
				}
				
				i = data.indexOf('=', index[0]); // known BMP code point
			}
		}
	}
	
	/**
	 * Extracts the value of a quoted string starting at the specified
	 * <code>index</code> into the <code>text</code>.
	 * 
	 * @param text the string from which to extract a quoted string
	 * @param index an IN-OUT parameter, containing the index in the
	 *        <code>text</code> from which to extract the string and returning
	 *        the position immediately after the extracted string.
	 * @return the extracted string, without the double-quotes ("), or
	 *        <code>null</code> if no valid quoted string was found
	 */
	private static String extractQuotedString(String text, int[] index) {
		int start = index[0];
		
		if (text.charAt(start) != '"') { // known BMP code point
			return null;
		} else {
			start++; // known BMP code point
			int end = text.indexOf('"', start); // known BMP code point
			
			if (end < 0) {
				return null;
			} else {
				index[0] = end + 1; // known BMP code point
				
				return text.substring(start, end);
			}
		}
	}
	
	/**
	 * Handles an <tt>&lt;?emf-validation nl="<i>bundle-name</i>"?&gt;</tt>
	 * processing instruction by finding the bundle for use in subsequent
	 * string translation.
	 * 
	 * @param resourceBundleName the resource bundle name
	 */
	private void handleNlInstruction(String resourceBundleName) {
		try {
			Bundle bundle = Platform.getBundle(extension.getNamespaceIdentifier());
			
			resourceBundle = resourceBundleCache.get(bundle, resourceBundleName);
			if (resourceBundle == null) {
				InputStream input = findLocalizedResource(
					bundle,
					resourceBundleName);

				if (input != null) {
					resourceBundle = new PropertyResourceBundle(input);
					input.close();
					
					resourceBundleCache.put(
						bundle,
						resourceBundleName,
						resourceBundle);
				}
			}
		} catch (Exception e) {
			Trace.catching(getClass(), "handleNlInstruction", e); //$NON-NLS-1$
			
			Log.warningMessage(
				EMFModelValidationStatusCodes.XML_RESOURCE_BUNDLE_NOT_FOUND,
				EMFModelValidationStatusCodes.XML_RESOURCE_BUNDLE_NOT_FOUND_MSG,
				new Object[] {getFileName(), resourceBundleName});
		}
	}
	
	/**
	 * Finds a resource file matching the longest possible concatenation of the
	 * specified <code>baseName</code> and the current locale, for localization,
	 * and returns it as an input stream.
	 * 
	 * @param bundle the OSGI bundle in which to find the resource file
	 * @param baseName the base of the locale-extended resource file name
	 * @return the input stream if a resource file is found that matches the
	 *     <code>baseName</code> and the current locale, else <code>null</code>
	 * @throws IOException if a URL is found that locates the resource file,
	 *     but an exception occurs in opening that URL
	 */
	private static InputStream findLocalizedResource(
			Bundle bundle,
			String baseName) throws IOException {
		
		InputStream result = null;
		String locale = Locale.getDefault().toString();
		
		String searchName = baseName + '_' + locale + ".properties"; //$NON-NLS-1$
		while ((result == null) && (searchName != null)) {
			URL url = bundle.getResource(searchName);
			
			if (url != null) {
				result = url.openStream();
			} else {
				if (locale == null) {
					// stop the search:  the base name didn't work, so give up
					searchName = null;
				} else {
					int lastUnderscore = locale.lastIndexOf('_'); // known BMP code point
					if (lastUnderscore > 0) {
						locale = locale.substring(0, lastUnderscore);
						
						// shorten the search name to a more general locale
						searchName = baseName + '_' + locale + ".properties"; //$NON-NLS-1$;
					} else {
						// ran out of locale information.
						// just look for the base name on the next time around
						locale = null;
						searchName = baseName + ".properties"; //$NON-NLS-1$;
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Flushes the resource bundle cache.  This should be called when no more
	 * constraint XML will be parsed, and the cache therefore is no longer
	 * needed.
	 */
	public static void flushResourceBundleCache() {
		resourceBundleCache.flush();
	}
	
	/**
	 * Handles an <tt>&lt;?emf-validation version="<i>number</i>"?&gt;</tt>
	 * processing instruction by ensuring that it is version 1.0.
	 * 
	 * @param version the version number indicated in the instruction
	 * @throws SAXException if the version is not 1.0
	 */
	private void handleVersionInstruction(String version) throws SAXException {
		if (!version.equals(EXPECTED_VERSION)) {
			SAXException e = new SAXException(
				EMFModelValidationPlugin.getMessage(
					EMFModelValidationStatusCodes.XML_WRONG_VERSION_MSG,
						new Object[] {version}));
			
			Trace.throwing(getClass(), "handleVersionInstruction", e); //$NON-NLS-1$
			
			throw e;
		}
	}
	
	/**
	 * Implements the <tt>%<i>key</i></tt> localization mechanism in the
	 * XML file that I am parsing.  Uses my extension to look up the resource,
	 * unless a <tt>&lt;?emf-validation?&gt;</tt> processing instruction
	 * has indicated a different resource bundle.
	 * 
	 * @param s the string to localize, if it starts with a % character
	 * @return the localized string, or just <code>s</code> if no localization
	 *     was needed
	 */
	protected String localize(String s) {
		if ((s == null) || !s.startsWith("%")) { //$NON-NLS-1$
			return s;
		} else if (resourceBundle == null) {
			return Platform.getResourceString(
					Platform.getBundle(extension.getNamespaceIdentifier()),
					s);
		} else {
			return localize(s, resourceBundle);
		}
	}

	/**
	 * Implements the <tt>%<i>key</i></tt> localization mechanism using the
	 * specified resource <code>bundle</code>.
	 * 
	 * @param s the string to localize
	 * @param bundle the resource bundle to look it up in
	 * @return the localized string, or just <code>s</code> if no localization
	 *     was needed or the resource was not found
	 */
	protected String localize(String s, ResourceBundle bundle) {
		try {
			// strip off the initial '%'
			return bundle.getString(s.substring(1));
		} catch (MissingResourceException e) {
			Trace.catching(getClass(), "localize", e); //$NON-NLS-1$
			
			// just return the original string (it's the best we can do)
			return s;
		}
	}
	
	//
	// Handle errors my logging the problem and passing to super
	//
	
	// extends the inherited method
	public void fatalError(SAXParseException e) throws SAXException {
		Log.errorMessage(
				EMFModelValidationStatusCodes.ERROR_PARSING_XML_FILE,
				EMFModelValidationStatusCodes.ERROR_PARSING_XML_FILE_MSG,
				new Object[] {getFileName()},
				e);

		super.fatalError(e);
	}
	
	// extends the inherited method
	public void error(SAXParseException e) throws SAXException {
		Log.errorMessage(
				EMFModelValidationStatusCodes.ERROR_PARSING_XML_FILE,
				EMFModelValidationStatusCodes.ERROR_PARSING_XML_FILE_MSG,
				new Object[] {getFileName()},
				e);
		
		super.error(e);
	}
	
	// extends the inherited method
	public void warning(SAXParseException e) throws SAXException {
		Log.warningMessage(
				EMFModelValidationStatusCodes.ERROR_PARSING_XML_FILE,
				EMFModelValidationStatusCodes.ERROR_PARSING_XML_FILE_MSG,
				new Object[] {getFileName()},
				e);
		
		super.warning(e);
	}
	
	/**
	 * Obtains my file name.
	 * 
	 * @return my file name
	 */
	private String getFileName() {
		return baseUrl.getFile();
	}
}
