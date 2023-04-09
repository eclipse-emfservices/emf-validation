/******************************************************************************
 * Copyright (c) 2003, 2008 IBM Corporation, Zeligsoft Inc., and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *    Zeligsoft - Bug 137213
 *    SAP AG - Bug 240352
 ****************************************************************************/
package org.eclipse.emf.validation.internal.util;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.ConstraintFactory;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintParser;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintParser;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.ConstraintParserException;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;
import org.eclipse.emf.validation.xml.IXmlConstraintParser;

/**
 * <p>
 * Constraint factory implementation which parses constraints from the XML
 * plug-in manifest.
 * </p>
 *
 * @author Christian W. Damus (cdamus)
 */
@SuppressWarnings("deprecation")
public class XmlConstraintFactory extends ConstraintFactory {
	/**
	 * Extension point name for the model providers extension point.
	 */
	public static final String CONSTRAINT_PARSERS_EXT_P_NAME = "constraintParsers"; //$NON-NLS-1$

	/** Mapping of language names to parser implementations. */
	private final java.util.Map<String, IConstraintParser> parserMap = new java.util.HashMap<>();

	private final Object parsersLock = new Object();

	private final IExtensionChangeHandler extensionHandler = new IExtensionChangeHandler() {

		@Override
		public void addExtension(IExtensionTracker tracker, IExtension extension) {
			synchronized (parsersLock) {
				registerParsers(extension.getConfigurationElements());
			}
		}

		@Override
		public void removeExtension(IExtension extension, Object[] objects) {
			// constraint parsers cannot be undefined
		}
	};

	/**
	 * Initializes me. I load my parsers from the <tt>constraintParsers</tt>
	 * extension point.
	 */
	public XmlConstraintFactory() {
		super();

		initializeParsers();
	}

	// implements the inherited method
	@Override
	protected IModelConstraint createConstraint(IXmlConstraintDescriptor desc) {
		IConfigurationElement config = desc.getConfig();

		final String lang = config.getAttribute(XmlConfig.A_LANG);

		IConstraintParser parser = getParser(lang);
		IXmlConstraintParser xmlParser = null;

		if (parser instanceof IXmlConstraintParser) {
			xmlParser = (IXmlConstraintParser) parser;
		} else if ((parser instanceof IParameterizedConstraintParser)
				&& (desc instanceof IParameterizedConstraintDescriptor)) {
			return createConstraint((IParameterizedConstraintDescriptor) desc);
		}

		try {
			if (xmlParser != null) {
				return xmlParser.parseConstraint(desc);
			} else {
				Trace.trace(EMFModelValidationDebugOptions.CONSTRAINTS_DISABLED,
						"Constraint is disabled: " + desc.getId() + ".  See log for details."); //$NON-NLS-1$ //$NON-NLS-2$
				ConstraintParserException e = new ConstraintParserException(EMFModelValidationPlugin.getMessage(
						EMFModelValidationStatusCodes.CONSTRAINT_PARSER_MISSING_MSG, lang));

				Log.warning(EMFModelValidationStatusCodes.CONSTRAINT_PARSER_MISSING, e.getMessage());

				return new DisabledConstraint(desc, e);
			}
		} catch (ConstraintParserException e) {
			return new DisabledConstraint(desc, e);
		}
	}

	@Override
	protected IModelConstraint createConstraint(IConstraintDescriptor descriptor) {
		if (descriptor instanceof IXmlConstraintDescriptor) {
			return createConstraint((IXmlConstraintDescriptor) descriptor);
		} else if (descriptor instanceof IParameterizedConstraintDescriptor) {
			return createConstraint((IParameterizedConstraintDescriptor) descriptor);
		} else {
			return new DisabledConstraint(descriptor,
					new IllegalArgumentException("unsupported constraint descriptor")); //$NON-NLS-1$
		}
	}

	protected IModelConstraint createConstraint(IParameterizedConstraintDescriptor descriptor) {
		final String lang = descriptor.getLanguage();

		IConstraintParser parser = getParser(lang);
		IParameterizedConstraintParser parmParser = null;

		if (parser instanceof IParameterizedConstraintParser) {
			parmParser = (IParameterizedConstraintParser) parser;
		} else if ((parser instanceof IXmlConstraintParser) && (descriptor instanceof IXmlConstraintDescriptor)) {
			return createConstraint((IXmlConstraintDescriptor) descriptor);
		}

		try {
			if (parmParser != null) {
				return parmParser.parseConstraint(descriptor);
			} else {
				Trace.trace(EMFModelValidationDebugOptions.CONSTRAINTS_DISABLED,
						"Constraint is disabled: " + descriptor.getId() + ".  See log for details."); //$NON-NLS-1$ //$NON-NLS-2$
				ConstraintParserException e = new ConstraintParserException(EMFModelValidationPlugin.getMessage(
						EMFModelValidationStatusCodes.CONSTRAINT_PARSER_MISSING_MSG, new Object[] { lang }));

				Log.warning(EMFModelValidationStatusCodes.CONSTRAINT_PARSER_MISSING, e.getMessage());

				return new DisabledConstraint(descriptor, e);
			}
		} catch (ConstraintParserException e) {
			return new DisabledConstraint(descriptor, e);
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
			Object parser = config.createExecutableExtension(XmlConfig.A_CLASS);

			if (parser instanceof IConstraintParser) {
				registerParser(language, (IConstraintParser) parser);
			} else {
				Trace.trace(EMFModelValidationDebugOptions.PARSERS,
						"Parser could not be initialized for constraint language: " + language); //$NON-NLS-1$
				Log.warningMessage(EMFModelValidationStatusCodes.CONSTRAINT_PARSER_TYPE,
						EMFModelValidationStatusCodes.CONSTRAINT_PARSER_TYPE_MSG, new Object[] { className, language });
			}
		} catch (Exception e) {
			Trace.catching(getClass(), "registerParser", e); //$NON-NLS-1$
			Log.warningMessage(EMFModelValidationStatusCodes.CONSTRAINT_PARSER_NOT_INITED,
					EMFModelValidationStatusCodes.CONSTRAINT_PARSER_NOT_INITED_MSG,
					new Object[] { className, language }, e);
		}
	}

	public void registerParser(String language, IConstraintParser parser) {
		assert language != null;
		assert parser != null;

		String className = parser.getClass().getName();

		try {

			if (parser instanceof IXmlConstraintParser || parser instanceof IParameterizedConstraintParser) {
				parserMap.put(language.toLowerCase(), parser);

				Trace.trace(EMFModelValidationDebugOptions.PARSERS,
						"Initialized parser for constraint language: " + language); //$NON-NLS-1$
			} else {
				Trace.trace(EMFModelValidationDebugOptions.PARSERS,
						"Parser could not be initialized for constraint language: " + language); //$NON-NLS-1$
				Log.warningMessage(EMFModelValidationStatusCodes.CONSTRAINT_PARSER_TYPE,
						EMFModelValidationStatusCodes.CONSTRAINT_PARSER_TYPE_MSG, new Object[] { className, language });
			}
		} catch (Exception e) {
			Trace.catching(getClass(), "registerParser", e); //$NON-NLS-1$
			Log.warningMessage(EMFModelValidationStatusCodes.CONSTRAINT_PARSER_NOT_INITED,
					EMFModelValidationStatusCodes.CONSTRAINT_PARSER_NOT_INITED_MSG,
					new Object[] { className, language }, e);
		}

	}

	/**
	 * Obtains the parser for the specified language.
	 *
	 * @param language a constraint language (not case-sensitive)
	 * @return the parser, or <code>null</code> if it cannot be found
	 */
	private IConstraintParser getParser(String language) {
		return parserMap.get(language.toLowerCase());
	}

	/**
	 * Loads the constraint language parsers from my <tt>constraintParsers</tt>
	 * extension point.
	 */
	private void initializeParsers() {
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			IExtensionPoint extPoint = Platform.getExtensionRegistry()
					.getExtensionPoint(EMFModelValidationPlugin.getPluginId(), CONSTRAINT_PARSERS_EXT_P_NAME);

			IExtensionTracker extTracker = EMFModelValidationPlugin.getExtensionTracker();

			if (extTracker != null) {
				extTracker.registerHandler(extensionHandler, ExtensionTracker.createExtensionPointFilter(extPoint));

				for (IExtension extension : extPoint.getExtensions()) {
					extensionHandler.addExtension(extTracker, extension);
				}
			}
		}
	}

	/**
	 * Loads the constraint language parsers.
	 *
	 * @param configs the configuration elements from the <tt>constraintParsers</tt>
	 *                extension point.
	 */
	public void registerParsers(IConfigurationElement[] configs) {
		for (IConfigurationElement config : configs) {
			registerParser(config);
		}
	}
}
