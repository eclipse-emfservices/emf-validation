/**
 * <copyright>
 *
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: IParameterizedConstraintDescriptor.java,v 1.1 2006/11/30 22:53:53 cdamus Exp $
 */
package org.eclipse.emf.validation.service;


/**
 * A specialization of the {@link IConstraintDescriptor} interface that
 * additionally supplies parameters as (name, value) pairs of strings.  The
 * parameters available are determined by the constraint parser for the
 * constraint's {@linkplain #getLanguage() language}.
 * <p>
 * This interface also defines a selection of "standard" parameters, that map
 * to information in the <tt>org.eclipse.emf.validation.constraintProviders</tt>
 * extension point schema <tt>&lt;constraint&gt;</tt> element.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 * 
 * @since 1.1
 */
public interface IParameterizedConstraintDescriptor extends IConstraintDescriptor {
	/**
	 * Parameter corresponding to the "class" attribute of the <tt>&lt;constraint&gt;</tt>
	 * element in <tt>plugin.xml</tt> constraint declarations.
	 */
	String CLASS_PARAMETER = "class"; //$NON-NLS-1$
	
	/**
	 * Parameter corresponding to the implied bundle symbolic name
	 * of the <tt>&lt;constraint&gt;</tt> element in <tt>plugin.xml</tt>
	 * constraint declarations.  It is derived from the configuration element's
	 * namespace identifier.  This bundle parameter is important, to allow a
	 * constraint parser to load classes by name (from the {@link #CLASS_PARAMETER})
	 * that are not on its own classpath.
	 */
	String BUNDLE_PARAMETER = "bundle"; //$NON-NLS-1$
	
	/**
	 * Queries the "constraint language" of the described constraint, which
	 * indicates the constraint parser that can construct the constraint from
	 * this descriptor.
	 * 
	 * @return the constraint language (must not be <code>null</code>)
	 */
	String getLanguage();
	
	/**
	 * Obtains the value of the specified named parameter, as a string.
	 * In the case that a value is not available, the constraint parser must
	 * decide whether to substitute an implicit (default) value or disable
	 * the constraint.
	 * 
	 * @param name the parameter name
	 * @return the corresponding value, or <code>null</code> if no value is
	 *     specified for this parameter
	 */
	String getParameterValue(String name);
}
