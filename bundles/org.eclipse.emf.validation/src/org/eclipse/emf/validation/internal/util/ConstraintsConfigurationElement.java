/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.internal.util;

import java.net.URL;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * A custom implementation of the Eclipse configuration element API which is
 * parsed from the <tt>&lt;constraints&gt;</tt> XML element. This unifies the
 * representation of constraint data obtained from "included" XML files with
 * constraint data parsed by Eclipse from the <tt>plugin.xml</tt>.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintsConfigurationElement extends XmlConfigurationElement {
	/**
	 * Initializes me as a copy of <CODE>original</CODE>, without any
	 * <TT>&lt;include&gt;</TT> children.
	 *
	 * @param original the original <TT>&lt;constraints&gt;</TT> element
	 * @param url      the URL from which the element was loaded, originally
	 */
	public ConstraintsConfigurationElement(IConfigurationElement original, URL url) {
		super(original.getName(), original.getDeclaringExtension(), url);

		setValue(original.getValue());

		String[] names = original.getAttributeNames();

		for (String name : names) {
			putAttribute(name, original.getAttribute(name));
		}

		IConfigurationElement[] originalChildren = original.getChildren();

		for (IConfigurationElement child : originalChildren) {
			if (!XmlConfig.E_INCLUDE.equals(child.getName())) {
				// "<include>" elements will be resolved afterwards to
				// insert the referenced constraints
				addChild(child);
			}
		}
	}
}
