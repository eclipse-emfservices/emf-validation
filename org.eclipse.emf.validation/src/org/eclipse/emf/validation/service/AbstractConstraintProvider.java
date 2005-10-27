/******************************************************************************
 * Copyright (c) 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.service;

import java.util.Collection;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * <p>
 * For situations in which the
 * {@link org.eclipse.emf.validation.xml.XmlConstraintProvider} class
 * does not suffice (i.e., where a plug-in provides constraints dynamically,
 * rather than statically registered in XML), this is a useful class to extend
 * as it provides a partial implementation of the
 * {@link IModelConstraintProvider} interface.  In particular, it extracts the
 * URI namespace prefix information from the extension point XML and provides
 * no-op implementations of the provider methods (redefine only those which
 * need an implementation).
 * </p>
 * <p>
 * This class may be subclassed by clients of the validation framework.
 * </p>
 * 
 * @see org.eclipse.emf.validation.xml.XmlConstraintProvider
 * 
 * @author Christian W. Damus (cdamus)
 */
public abstract class AbstractConstraintProvider
		implements
			IModelConstraintProvider,
			IExecutableExtension {

	private String[] namespaceUris;
	
	/** Initializes me. */
	protected AbstractConstraintProvider() {
		super();
	}

	/**
	 * Obtains the namespace URIs of the EMF packages that I provide constraints
	 * for.
	 * 
	 * @return my packages' namespace URIs
	 */
	public final String[] getNamespaceUris() {
		return namespaceUris;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws CoreException if the subclass implementation of this
	 *    method throws on an error in accessing the <code>config</code> or for
	 *    any other reason (see the subclass documentation)
	 */
	public void setInitializationData(
			IConfigurationElement config,
			String propertyName,
			Object data) throws CoreException {
		
		Set uris = new java.util.HashSet();
		
		// backwards compatibility to the namespaceUri attribute
		String uri = config.getAttribute(
				XmlConfig.A_NAMESPACE_URI);
		if (uri != null) {
			uris.add(uri.trim());
		}
		
		IConfigurationElement[] pkgs = config.getChildren(XmlConfig.E_PACKAGE);
		
		for (int i = 0; i < pkgs.length; i++) {
			uri = pkgs[i].getAttribute(XmlConfig.A_NAMESPACE_URI);
			if (uri != null) {
				uris.add(uri.trim());
			}
		}

		if (uris.isEmpty()) {
			CoreException e = new CoreException(new Status(
				IStatus.ERROR,
				EMFModelValidationPlugin.getPluginId(),
				EMFModelValidationStatusCodes.PROVIDER_NO_NAMESPACE_URI,
				EMFModelValidationStatusCodes.PROVIDER_NO_NAMESPACE_URI_MSG,
				null));
			
			Trace.throwing(
				AbstractConstraintProvider.class,
				"setInitializationData()", //$NON-NLS-1$
				e);
			
			throw e;
		}
		
		namespaceUris = (String[]) uris.toArray(new String[uris.size()]);
	}
	
	/**
	 * This is a no-op implementation.  Subclasses that need to implement it
	 * should redefine this method.  There is no need to call
	 * <code>super</code>.
	 */
	public Collection getBatchConstraints(
			EObject eObject,
			Collection constraints) {
		return noOp(constraints);
	}

	/**
	 * This is a no-op implementation.  Subclasses that need to implement it
	 * should redefine this method.  There is no need to call
	 * <code>super</code>.
	 */
	public Collection getLiveConstraints(
			Notification notification,
			Collection constraints) {
		return noOp(constraints);
	}
	
	/**
	 * Helper to implement the no-op methods.
	 * 
	 * @param constraints the constraints passed to the provider method
	 * @return <code>constraints</code> if it is not <code>null</code>;
	 *     a new modifiable collection, otherwise
	 */
	private Collection noOp(Collection constraints) {
		return (constraints == null)
			? new java.util.ArrayList()
			: constraints;
	}
}
