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
 *    Radek Dvorak (Borland) - Bugzilla 165661
 ****************************************************************************/
package org.eclipse.emf.validation.xml;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.internal.util.XmlConfigurationElement;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.AbstractConstraintProvider;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * <p>
 * A convenient implementation of the
 * {@link org.eclipse.emf.validation.service.IModelConstraintProvider}
 * interface which loads constraints from a plug-in's manifest XML.
 * </p>
 * <p>
 * Constraints may be specified in the <tt>plugin.xml</tt> file or in separate
 * XML files containing the <tt>&lt;constraints&gt;</tt> element as their root
 * and included from the plug-in manifest via a <tt>&lt;include&gt;</tt>
 * element.  Includes can nest to any depth.
 * </p>
 * <p>
 * Constraints may be specified in any language for which some plug-in provides
 * an implementation via the <tt>constraintParsers</tt> extension point.
 * Currently, the "Java" language is provided by the core EMF Model Validation
 * plug-in.
 * </p>
 * <p>
 * This class is intended to be used by clients by reference in the
 * <tt>constraintProviders</tt> extension point.  It is not intended to be
 * subclassed or otherwise used by client <i>code</i>.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class XmlConstraintProvider extends AbstractConstraintProvider
		implements
			IExecutableExtension {

	/**
	 * Message for the text to replace an constraint unavailable
	 * constraint name.
	 */
	static final String NO_NAME = ValidationMessages.constraint_not_init_name;
	
	/**
	 * Message for the text indicating that a constraint has no ID.
	 */
	static final String REASON_NO_ID = ValidationMessages.constraint_reason_no_id;

	/**
	 * Message key for the text indicating that the XML file with
	 * errors is unknown.
	 */
	static final String UNKNOWN_FILE = ValidationMessages.xml_unknown_file;
	
	/**
	 * Extends the inherited method to configure myself with my meta-data of
	 * one or more constraints.
	 * 
	 * @throws CoreException if the superclass implementation of this
	 *    method throws or on an error in accessing the <code>config</code>
	 */
	@Override
	public void setInitializationData(
			IConfigurationElement config,
			String propertyName,
			Object data) throws CoreException {
		
		super.setInitializationData(config, propertyName, data);

		IConfigurationElement[] constraintses = config.getChildren(
				XmlConfig.E_CONSTRAINTS);
		
		for (IConfigurationElement element : constraintses) {
			IConfigurationElement next =
				XmlConfig.parseConstraintsWithIncludes(element);
			
			IConfigurationElement[] configs = next.getChildren();

			for (IConfigurationElement element2 : configs) {
				addConstraint(element2);
			}
		}
		
		XmlConfig.flushResourceBundles();
	}
	
	/**
	 * Adds a constraint to my collection, constructed from the specified XML
	 * content.
	 * 
	 * @param config the <TT>&lt;constraint&gt;</TT> element
	 */
	@SuppressWarnings("deprecation")
	private void addConstraint(IConfigurationElement config) {
		final String contributorId = config
			.getDeclaringExtension()
			.getNamespaceIdentifier();
		
		String id = config.getAttribute(XmlConfig.A_ID);
		if (id == null) {
			String name = config.getAttribute(XmlConfig.A_NAME);
			if (name == null) {
				String fileName;
				
				if (config instanceof XmlConfigurationElement) {
					fileName = ((XmlConfigurationElement)config).getFileName();
				} else {
					fileName = UNKNOWN_FILE;
				}
				
				name = EMFModelValidationPlugin.getMessage(
						NO_NAME,
						new Object[] {fileName});
			}
			
			Log.warningMessage(
					EMFModelValidationStatusCodes.CONSTRAINT_NOT_INITED,
					EMFModelValidationStatusCodes.CONSTRAINT_NOT_INITED_MSG,
					new Object[] {
					   name,
					   REASON_NO_ID,
					});
		} else {
			IConstraintDescriptor constraint =
				ConstraintRegistry.getInstance().getDescriptor(
					contributorId,
					id);
			
			if (constraint == null) {
				// why wasn't it already created?
				try {
					constraint = new XmlConstraintDescriptor(config);
				} catch (ConstraintExistsException e) {
					// shouldn't happen because I checked for existence.
					//   Just leave 'constraint' null to skip it
				}
			}
	
			if (constraint instanceof IXmlConstraintDescriptor) {
				IXmlConstraintDescriptor xmlConstraint =
					(IXmlConstraintDescriptor)constraint;
				
				xmlConstraint.resolveTargetTypes(getNamespaceUris());
	
				IModelConstraint proxy = createModelConstraintProxy(xmlConstraint);
			
				getConstraints().add(proxy);
				
				Trace.trace(
						EMFModelValidationDebugOptions.PROVIDERS,
						"Added constraint proxy: " + constraint); //$NON-NLS-1$
			}
		}
	}
}
