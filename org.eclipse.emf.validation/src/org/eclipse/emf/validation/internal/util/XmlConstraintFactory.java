/******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.internal.util;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.ConstraintFactory;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.ConstraintParserException;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;
import org.eclipse.emf.validation.xml.IXmlConstraintParser;

import com.ibm.icu.lang.UCharacter;

/**
 * <p>
 * Constraint factory implementation which parses constraints from the
 * XML plug-in manifest.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class XmlConstraintFactory extends ConstraintFactory {
	/**
	 * Extension point name for the model providers extension point.
	 */
	private static final String CONSTRAINT_PARSERS_EXT_P_NAME =
			"constraintParsers"; //$NON-NLS-1$

	/** Mapping of language names to parser implementations. */
	private final java.util.Map parserMap = new java.util.HashMap();

	/**
	 * Initializes me.  I load my parsers from the <tt>constraintParsers</tt>
	 * extension point.
	 */
	public XmlConstraintFactory() {
		super();

		initializeParsers();
	}

	// implements the inherited method
	protected IModelConstraint createConstraint(IXmlConstraintDescriptor desc) {
		IConfigurationElement config = desc.getConfig();

		final String lang = config.getAttribute(XmlConfig.A_LANG);
		
		IXmlConstraintParser parser = getParser(lang);

		try {
			if (parser != null) {
				return parser.parseConstraint(desc);
			} else {
				Trace.trace(
						EMFModelValidationDebugOptions.CONSTRAINTS_DISABLED,
						"Constraint is disabled: " + desc.getId() + ".  See log for details."); //$NON-NLS-1$ //$NON-NLS-2$
				ConstraintParserException e = new ConstraintParserException(
					EMFModelValidationPlugin.getMessage(
						EMFModelValidationStatusCodes.CONSTRAINT_PARSER_MISSING_MSG,
						new Object[] {lang}));
				
				Log.warning(
						EMFModelValidationStatusCodes.CONSTRAINT_PARSER_MISSING,
						e.getMessage());
				
				return new DisabledConstraint(desc, e);
			}
		} catch (ConstraintParserException e) {
			return new DisabledConstraint(desc, e);
		}
	}

	/**
	 * Registers a parser implementation against the language that it provides.
	 * 
	 * @param config the Eclipse extension configuration data for the parser
	 */
	void registerParser(IConfigurationElement config) {
		assert config != null;

		String language = config.getAttribute(XmlConfig.A_LANG);
		String className = config.getAttribute(XmlConfig.A_CLASS);

		try {
			Object parser = config.createExecutableExtension(
					XmlConfig.A_CLASS);

			if (parser instanceof IXmlConstraintParser) {
				parserMap.put(UCharacter.toLowerCase(language), parser);
				
				Trace.trace(
						EMFModelValidationDebugOptions.PARSERS,
						"Initialized parser for constraint language: " + language); //$NON-NLS-1$
			} else {
				Trace.trace(
						EMFModelValidationDebugOptions.PARSERS,
						"Parser could not be initialized for constraint language: " + language); //$NON-NLS-1$
				Log.warningMessage(
					EMFModelValidationStatusCodes.CONSTRAINT_PARSER_TYPE,
					EMFModelValidationStatusCodes.CONSTRAINT_PARSER_TYPE_MSG,
					new Object[] {className, language});
			}
		} catch (Exception e) {
			Trace.catching(getClass(), "registerParser", e); //$NON-NLS-1$
			Log.warningMessage(
				EMFModelValidationStatusCodes.CONSTRAINT_PARSER_NOT_INITED,
				EMFModelValidationStatusCodes.CONSTRAINT_PARSER_NOT_INITED_MSG,
				new Object[] {className, language},
				e);
		}
	}

	/**
	 * Obtains the parser for the specified language.
	 * 
	 * @param language a constraint language (not case-sensitive)
	 * @return the parser, or <code>null</code> if it cannot be found
	 */
	private IXmlConstraintParser getParser(String language) {
		return (IXmlConstraintParser)parserMap.get(UCharacter.toLowerCase(language));
	}

	/**
	 * Loads the constraint language parsers from my <tt>constraintParsers</tt>
	 * extension point.
	 */
	private void initializeParsers() {
		IConfigurationElement[] configs = 
			Platform.getExtensionRegistry().getConfigurationElementsFor(
				EMFModelValidationPlugin.getPluginId(),
				CONSTRAINT_PARSERS_EXT_P_NAME);

		for (int i = 0; i < configs.length; i++) {
			IConfigurationElement config = configs[i];

			registerParser(config);
		}
	}
}