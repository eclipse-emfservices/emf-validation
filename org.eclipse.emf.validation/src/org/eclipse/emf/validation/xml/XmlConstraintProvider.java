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


package org.eclipse.emf.validation.xml;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.internal.util.DisabledConstraint;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.internal.util.XmlConfigurationElement;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.AbstractConstraintProvider;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.ConstraintFactory;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.ModelValidationService;
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
	
	private List myConstraints = java.util.Collections.EMPTY_LIST;

	/**
	 * A proxy for a lazily instantiated implementation of the
	 * {@link IModelConstraint} interface. 
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private class ConstraintProxy implements IModelConstraint {
		private final IXmlConstraintDescriptor descriptor;
		private IModelConstraint delegate = null;

		/**
		 * Initializes me with the descriptor that I can provide with little
		 * performance cost.
		 * 
		 * @param descriptor my descriptor
		 */
		ConstraintProxy(IXmlConstraintDescriptor descriptor) {
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
				delegate = ConstraintFactory.getInstance()
						.newConstraint(descriptor);
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

	/**
	 * Extends the inherited method to configure myself with my meta-data of
	 * one or more constraints.
	 * 
	 * @throws CoreException if the superclass implementation of this
	 *    method throws or on an error in accessing the <code>config</code>
	 */
	public void setInitializationData(
			IConfigurationElement config,
			String propertyName,
			Object data) throws CoreException {
		
		super.setInitializationData(config, propertyName, data);

		IConfigurationElement[] constraintses = config.getChildren(
				XmlConfig.E_CONSTRAINTS);

		this.myConstraints = new java.util.ArrayList();
		
		for (int i = 0; i < constraintses.length; i++) {
			IConfigurationElement next =
				XmlConfig.parseConstraintsWithIncludes(constraintses[i]);
			
			IConfigurationElement[] configs = next.getChildren();

			for (int j = 0; j < configs.length; j++) {
				addConstraint(configs[j]);
			}
		}
		
		XmlConfig.flushResourceBundles();
	}

	// implements the provider interface
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

	// implements the provider interface
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

	/**
	 * Obtains my constraint proxies which lazily initialize the actual
	 * constraint implementations.
	 * 
	 * @return a list of constraint proxies
	 */
	protected List getConstraints() {
		return myConstraints;
	}

	/**
	 * Adds a constraint to my collection, constructed from the specified XML
	 * content.
	 * 
	 * @param config the <TT>&lt;constraint&gt;</TT> element
	 */
	private void addConstraint(IConfigurationElement config) {
		final String contributorId = config
			.getDeclaringExtension()
			.getNamespace();
		
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
	
				IModelConstraint proxy = new ConstraintProxy(xmlConstraint);
			
				getConstraints().add(proxy);
				
				Trace.trace(
						EMFModelValidationDebugOptions.PROVIDERS,
						"Added constraint proxy: " + constraint); //$NON-NLS-1$
			}
		}
	}
}
