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

import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.ConstraintParserException;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;
import org.eclipse.emf.validation.xml.IXmlConstraintParser;

/**
 * <p>
 * A constraint parser that knows how to create constraints specified in the
 * "Java" constraint language.
 * </p>
 * <p>
 * This class is not intended to be used outside of the validation framework.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public class JavaConstraintParser implements IXmlConstraintParser {
	
	/**
	 * Mapping of constraint implementation classes to instances, to support
	 * the single-instance model of {@link AbstractModelConstraint} any class
	 * implementing one or more ad hoc validation method signatures.
	 */
	private static final Map constraintImplementationMap = new java.util.HashMap();
	
	/**
	 * Adapts instances of {@link AbstractModelConstraint} to the internal
	 * constraint API.
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private static class ConstraintAdapter implements IModelConstraint {
		private final AbstractModelConstraint delegate;
		private final IConstraintDescriptor descriptor;
		
		ConstraintAdapter(
			IConstraintDescriptor descriptor,
			AbstractModelConstraint delegate) {
			
			this.descriptor = descriptor;
			this.delegate = delegate;
		}
		
		/* (non-Javadoc)
		 * Implements the inherited method.
		 */
		public IStatus validate(IValidationContext ctx) {
			return delegate.validate(ctx);
		}

		/* (non-Javadoc)
		 * Implements the inherited method.
		 */
		public IConstraintDescriptor getDescriptor() {
			return descriptor;
		}
	}

	/**
	 * Initializes me.
	 */
	public JavaConstraintParser() {
		super();
	}

	// implements the interface method
	public IModelConstraint parseConstraint(IXmlConstraintDescriptor descriptor)
			throws ConstraintParserException {
		
		String className = descriptor.getConfig()
				.getAttribute(XmlConfig.A_CLASS);

		if (className == null) {
			ConstraintParserException cpe = new ConstraintParserException(
					"No class name."); //$NON-NLS-1$
			
			Trace.throwing(getClass(), "parseConstraint", cpe); //$NON-NLS-1$
			throw cpe;
		}

		return createCustomConstraint(
				className,
				descriptor);
	}

	/**
	 * Helper method which creates an {@link IModelConstraint} adapter for the
	 * specified subclass of {@link AbstractModelConstraint}.
	 * 
	 * @param className the name of a class implementing the constraint
	 * @param descriptor the constraint's descriptor
	 * @return a constraint as described above
	 * @throws ConstraintParserException if the constraint cannot be created
	 *     for some reason.
	 */
	private IModelConstraint createCustomConstraint(
			String className,
			IXmlConstraintDescriptor descriptor) throws ConstraintParserException {

		IModelConstraint result = null;
		Throwable pendingException = null;
		String pendingMessage = null;

		Bundle bundle = Platform.getBundle(descriptor
			.getConfig()
			.getDeclaringExtension()
			.getNamespaceIdentifier());
		
		try {
			Class resultType = bundle.loadClass(className);

			if (AbstractModelConstraint.class.isAssignableFrom(resultType)) {
				// instantiate the class extending AbstractModelConstraint
				result = new ConstraintAdapter(
					descriptor,
					(AbstractModelConstraint)getInstance(resultType));
			}
		} catch (ClassNotFoundException e) {
			pendingException = e;
			pendingMessage = EMFModelValidationPlugin.getMessage(
					EMFModelValidationStatusCodes.DELEGATE_CLASS_NOT_FOUND_MSG,
					new Object[] {descriptor.getId(), className});
		} catch (InstantiationException e) {
			pendingException = e;
			pendingMessage = EMFModelValidationPlugin.getMessage(
					EMFModelValidationStatusCodes.DELEGATE_INSTANTIATION_MSG,
					new Object[] {descriptor.getId(), className});
		} catch (IllegalAccessException e) {
			pendingException = e;
			pendingMessage = EMFModelValidationPlugin.getMessage(
					EMFModelValidationStatusCodes.DELEGATE_METHOD_INACCESSIBLE_MSG,
					new Object[] {descriptor.getId(), className, null});
		}

		if (pendingException != null) {
			Trace.catching(getClass(), "createCustomConstraint", pendingException); //$NON-NLS-1$
			Log.error(
					EMFModelValidationStatusCodes.CONSTRAINT_NOT_INITED,
					pendingMessage,
					pendingException);
			
			ConstraintParserException cpe = new ConstraintParserException(
					pendingException.getLocalizedMessage(),
					pendingException);
			
			Trace.throwing(getClass(), "createCustomConstraint", cpe); //$NON-NLS-1$
			throw cpe;
		}
		
		return result;
	}
	
	/**
	 * Obtains an instance of the specified constraint implementation class,
	 * which should either extend {@link AbstractModelConstraint} or
	 * implement an appropriate validation method signature.  The instances are
	 * pooled to support the sharing of instances as described in the
	 * documentation of the <code>AbstractModelConstraint</code> class.
	 * 
	 * @param constraintClass the constraint implementation type
	 * @return the shared instance of the <code>constraintClass</code>
	 * @throws InstantiationException if the instance needs to be created
	 *     and an exception occurs in instantiating it
	 * @throws IllegalAccessException if the instance needs to be created
	 *     and the default constructor is not accessible
	 */
	static Object getInstance(Class constraintClass)
			throws InstantiationException, IllegalAccessException {
		
		Object result = constraintImplementationMap.get(constraintClass);
		
		if (result == null) {
			result = constraintClass.newInstance();
			constraintImplementationMap.put(constraintClass, result);
		}
		
		return result;
	}
}
