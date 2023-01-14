/******************************************************************************
 * Copyright (c) 2004, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.internal.emfadapter;

import java.lang.reflect.Method;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintParser;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.ConstraintParserException;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;
import org.eclipse.emf.validation.xml.IXmlConstraintParser;
import org.osgi.framework.Bundle;

/**
 * A constraint parser that creates constraints to adapt the EMF API's
 * validation methods to the Aurora {@link IModelConstraint} interface.
 *
 * @author Christian W. Damus (cdamus)
 */
@SuppressWarnings("deprecation")
public class EMFConstraintParser implements IParameterizedConstraintParser, IXmlConstraintParser {
	private static final String PARAMETER_CLASS = "class"; //$NON-NLS-1$
	private static final String PARAMETER_METHOD = "method"; //$NON-NLS-1$

	private static final String NO_INTERFACE = ValidationMessages.emfadapter_noInterface_WARN_;
	private static final String NO_METHOD = ValidationMessages.emfadapter_noMethod_WARN_;
	private static final String NOT_BOOLEAN = ValidationMessages.emfadapter_notBoolean_WARN_;
	private static final String ILLEGAL_ACCESS = ValidationMessages.emfadapter_illegalAccess_WARN_;
	private static final String INTERFACE_NOT_FOUND = ValidationMessages.emfadapter_interfaceNotFound_WARN_;
	private static final String METHOD_NOT_FOUND = ValidationMessages.emfadapter_methodNotFound_WARN_;

	/** The signature of validation methods in the EMF API. */
	private static final Class<?>[] VALIDATION_METHOD_SIGNATURE = new Class<?>[] { DiagnosticChain.class,
			java.util.Map.class, };

	/**
	 * Initializes me.
	 */
	public EMFConstraintParser() {
		super();
	}

	// implements the interface method
	@Override
	public IModelConstraint parseConstraint(IParameterizedConstraintDescriptor descriptor)
			throws ConstraintParserException {
		// the EMF interface name and method must be specified in the XML
		String className = descriptor.getParameterValue(PARAMETER_CLASS);
		String methodName = descriptor.getParameterValue(PARAMETER_METHOD);

		return parseConstraint(className, methodName, descriptor);
	}

	// implements the interface method
	@Override
	public IModelConstraint parseConstraint(IXmlConstraintDescriptor descriptor) throws ConstraintParserException {
		// the EMF interface name and method must be specified in the XML
		String className = XmlConfig.getParameter(descriptor.getConfig(), PARAMETER_CLASS);
		String methodName = XmlConfig.getParameter(descriptor.getConfig(), PARAMETER_METHOD);

		return parseConstraint(className, methodName, descriptor);
	}

	// implements the interface method
	private IModelConstraint parseConstraint(String className, String methodName, IConstraintDescriptor descriptor)
			throws ConstraintParserException {

		if (className == null) {
			throw new ConstraintParserException(NO_INTERFACE);
		}

		if (methodName == null) {
			throw new ConstraintParserException(NO_METHOD);
		}

		EMFConstraintAdapter result;

		Bundle bundle = Platform.getBundle(descriptor.getPluginId());

		try {
			// use the contributing plug-in's class loader to get the interface
			// type (in case the plug-in isn't my own)
			Class<?> emfInterface = bundle.loadClass(className);

			// get the method, which we know has always the same signature in
			// the EMF API
			Method validationMethod = emfInterface.getMethod(methodName, VALIDATION_METHOD_SIGNATURE);

			// must have some kind of boolean result type
			if ((validationMethod.getReturnType() != boolean.class)
					&& (validationMethod.getReturnType() != Boolean.class)) {
				throw new ConstraintParserException(NOT_BOOLEAN);
			}

			result = new EMFConstraintAdapter(descriptor, validationMethod);
		} catch (SecurityException e) {
			throw new ConstraintParserException(ILLEGAL_ACCESS, e);
		} catch (ClassNotFoundException e) {
			throw new ConstraintParserException(
					EMFModelValidationPlugin.getMessage(INTERFACE_NOT_FOUND, new Object[] { className }), e);
		} catch (NoSuchMethodException e) {
			throw new ConstraintParserException(
					EMFModelValidationPlugin.getMessage(METHOD_NOT_FOUND, new Object[] { className, methodName }), e);
		}

		return result;
	}

}
