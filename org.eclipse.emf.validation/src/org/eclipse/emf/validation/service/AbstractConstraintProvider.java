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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.util.DisabledConstraint;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.model.IModelConstraint;
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
 * implementations of the provider methods accessing the constraints loaded by this
 * provider. Additionally, support for lazy initialization of the actual model constraints 
 * implementations is provided.
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
	private List myConstraints = new java.util.ArrayList();
	
	/**
	 * A proxy for a lazily instantiated implementation of the
	 * {@link IModelConstraint} interface. 
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private class ConstraintProxy implements IModelConstraint {
		private final IConstraintDescriptor descriptor;
		private IModelConstraint delegate = null;

		/**
		 * Initializes me with the descriptor that I can provide with little
		 * performance cost.
		 * 
		 * @param descriptor my descriptor
		 */
		ConstraintProxy(IConstraintDescriptor descriptor) {
			this.descriptor = descriptor;
		}

		/* (non-Javadoc)
		 * Implements the interface method.
		 */
		public IConstraintDescriptor getDescriptor() {
			return descriptor;
		}
		
		/**
		 * Lazily initializes my delegate constraint before invoking it,
		 * if necessary.
		 */
		public IStatus validate(IValidationContext ctx) {
			IStatus result;
			
			if (delegate == null) {
				if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
					Trace.trace(
							EMFModelValidationDebugOptions.CONSTRAINTS,
							"Initializing constraint delegate: " + descriptor); //$NON-NLS-1$
				}
				
				// this will throw if the delegate could not be created
				delegate = createModelConstraint(descriptor);
			}

			try {
				if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS_EVALUATION)) {
					Trace.trace(
							EMFModelValidationDebugOptions.CONSTRAINTS_EVALUATION,
							"Delegating validate() method to: " + delegate //$NON-NLS-1$
								+ " for: " + descriptor); //$NON-NLS-1$
				}
				
				result = delegate.validate(ctx);
			} catch (RuntimeException e) {
				Trace.catching(getClass(), "validate()", e); //$NON-NLS-1$
				
				Trace.trace(EMFModelValidationDebugOptions.CONSTRAINTS_DISABLED,
						"Constraint is disabled: " + descriptor); //$NON-NLS-1$
				
				// the disabled constraint is a placeholder for the missing
				//   functionality.  It will log a disablement message and
				//   thereafter be silent
				delegate = new DisabledConstraint(descriptor, e);
				
				// won't throw an exception, this time!
				// (the disabled constraint never does; it returns an INFO
				// status to report the problem to the user)
				result = delegate.validate(ctx);
			}
			
			// replace me with my delegate in the list of constraints, to avoid
			//   the delegation in future invocations and to free some memory
			for (ListIterator iter = getConstraints().listIterator();
					iter.hasNext();) {
				
				if (iter.next() == this) {
					iter.set(delegate);
					break;
				}
			}
			
			// in case this provider is cached, also replace me in the
			// validation service's cache
			ModelValidationService.getInstance().replaceInCache(this, delegate);
			
			return result;
		}
	}		
	
	/** Initializes me. */
	protected AbstractConstraintProvider() {
		super();
	}

	/**
	 * Creates the model constraint implementation from the descriptor.  This
	 * default implementation delegates to the {@link ConstraintFactory}
	 * to create the constraint, if the specified <tt>descriptor</tt> is of a
	 * known type and a registered {@link IConstraintParser} is available to
	 * parse the descriptor.  If either of these conditions does not hold, then
	 * a disabled constraint implementation will be returned (one that always
	 * returns an info status indicating that it is disabled).
	 * <p>
	 * A constraint provider that uses a descriptor type not defined by the
	 * framework or a language for which it is not registering a parser should
	 * override this method to create an appropriate constraint implementation.
	 * </p>
	 * 
	 * @param descriptor the descriptor of the constraint to be created
	 * @return actual constraint implementation 
	 * 
	 * @since 1.1
	 */
	protected IModelConstraint createModelConstraint(IConstraintDescriptor descriptor) {
		return ConstraintFactory.getInstance().createConstraint(descriptor);
	}
	
	/**
	 * Creates a contraint proxy which lazily initializes the actual
	 * constraint implementation represented by the given descriptor.
	 * <p>
	 * This method is to be used by the concrete provider to initialize its
	 * constraints list if it's the intention to initialize the actual
	 * constraints lazily.
	 * 
	 * @param descriptor the descriptor of the constraint for which a proxy is to be created
	 * @return a proxy for the actual constraint implementation
	 * 
	 * @since 1.1
	 */
	protected IModelConstraint createModelConstraintProxy(IConstraintDescriptor descriptor) {
		return new ConstraintProxy(descriptor);
	}
	
	/**
	 * Obtains my constraints.
	 * 
	 * @return a list of constraints
	 * 
	 * @since 1.1
	 */
	protected List getConstraints() {
		return myConstraints;
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
	 * @since 1.1
	 */
	public Collection getLiveConstraints(
			Notification notification,
			Collection constraints) {
		
		assert notification != null;
		
		if (Trace.shouldTraceEntering(EMFModelValidationDebugOptions.PROVIDERS)) {
			Trace.entering(getClass(), "getLiveConstraints"); //$NON-NLS-1$
		}
		
		Collection result = constraints;

		if (result == null) {
			result = new java.util.ArrayList();
		}
		
		if (notification.getNotifier() instanceof EObject) {
			EObject eObject = (EObject)notification.getNotifier();
			
			for (Iterator iter = getConstraints().iterator(); iter.hasNext(); ) {
				IModelConstraint next = (IModelConstraint)iter.next();
				IConstraintDescriptor desc = next.getDescriptor();
	
				if (desc.isLive() && desc.targetsTypeOf(eObject)
						&& desc.targetsEvent(notification)) {
					result.add(next);
				}
			}
		}

		if (Trace.shouldTraceExiting(EMFModelValidationDebugOptions.PROVIDERS)) {
			Trace.exiting(getClass(), "getLiveConstraints"); //$NON-NLS-1$
		}
		
		return result;
	}

	
	/**
	 * @since 1.1
	 */
	public Collection getBatchConstraints(
			EObject eObject,
			Collection constraints) {		
		
		if (Trace.shouldTraceEntering(EMFModelValidationDebugOptions.PROVIDERS)) {
			Trace.entering(getClass(), "getBatchConstraints"); //$NON-NLS-1$
		}
		
		Collection result = constraints;

		if (result == null) {
			result = new java.util.ArrayList();
		}

		for (Iterator iter = getConstraints().iterator(); iter.hasNext(); ) {
			IModelConstraint next = (IModelConstraint)iter.next();
			IConstraintDescriptor desc = next.getDescriptor();

			if (desc.isBatch() && desc.targetsTypeOf(eObject)) {
				result.add(next);
			}
		}

		if (Trace.shouldTraceExiting(EMFModelValidationDebugOptions.PROVIDERS)) {
			Trace.exiting(getClass(), "getBatchConstraints"); //$NON-NLS-1$
		}
		
		return result;
	}
}
