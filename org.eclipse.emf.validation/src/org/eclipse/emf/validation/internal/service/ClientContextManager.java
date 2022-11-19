/******************************************************************************
 * Copyright (c) 2005, 2008 IBM Corporation, Zeligsoft Inc., and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 *    Zeligsoft - Bugs 137213, 249496, 252302
 *    Borland Software - Bug 137213
 *    SAP AG - Bug 240352
 ****************************************************************************/
package org.eclipse.emf.validation.internal.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.dynamichelpers.ExtensionTracker;
import org.eclipse.core.runtime.dynamichelpers.IExtensionChangeHandler;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.internal.util.XmlExpressionSelector;
import org.eclipse.emf.validation.model.IClientSelector;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.util.XmlConfig;

/**
 * The context manager loads contexts from the <code>constraintBindings</code>
 * extension point and makes them available to the validation framework.
 * 
 * @author Christian W. Damus
 */
public class ClientContextManager {
	private static final String E_CLIENT_CONTEXT = "clientContext"; //$NON-NLS-1$

	private static final String E_BINDING = "binding"; //$NON-NLS-1$
	private static final String A_CONTEXT = "context"; //$NON-NLS-1$

	private static final String E_CONSTRAINT = "constraint"; //$NON-NLS-1$
	private static final String E_CATEGORY = "category"; //$NON-NLS-1$
	private static final String E_EXTEND_CLIENT_CONTEXT = "extendClientContext"; //$NON-NLS-1$
	private static final String E_EXCLUDE_CONSTRAINT = "excludeConstraint"; //$NON-NLS-1$
	private static final String E_EXCLUDE_CATEGORY = "excludeCategory"; //$NON-NLS-1$
	private static final String A_REF = "ref"; //$NON-NLS-1$

	private static final ClientContextManager INSTANCE = new ClientContextManager();

	private volatile Set<IClientContext> clientContexts = new java.util.HashSet<IClientContext>();
	private volatile Map<String, IClientContext> clientContextMap = new java.util.HashMap<String, IClientContext>();

	private volatile Set<ClientContext> defaultContexts = new java.util.HashSet<ClientContext>();

	private final Object clientContextLock = new Object();

	private final IExtensionChangeHandler extensionHandler = new IExtensionChangeHandler() {

		public void addExtension(IExtensionTracker tracker, IExtension extension) {
			// must create all of the contexts before we process the bindings.
			// Hence, this will loop over the elements twice
			IConfigurationElement[] configs = extension.getConfigurationElements();

			synchronized (clientContextLock) {
				configureClientContexts(configs);
				configureBindings(configs);
			}
		}

		public void removeExtension(IExtension extension, Object[] objects) {
			// client-contexts cannot be undefined
		}
	};

	/**
	 * Not instantiable by clients.
	 */
	private ClientContextManager() {
		super();

		configureConstraintBindings();
	}

	/**
	 * Configures constraint bindings based on the <tt>constraintBindings</tt>
	 * extension configurations.
	 */
	private void configureConstraintBindings() {
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			IExtensionPoint extPoint = Platform.getExtensionRegistry().getExtensionPoint(
					EMFModelValidationPlugin.getPluginId(), EMFModelValidationPlugin.CONSTRAINT_BINDINGS_EXT_P_NAME);

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
	 * Obtains the singleton instance of this class.
	 * 
	 * @return the singleton context manager
	 */
	public static final ClientContextManager getInstance() {
		return INSTANCE;
	}

	/**
	 * Obtains a client context by its unique identifier.
	 * 
	 * @param contextId the client context ID to look for
	 * @return the matching context, or <code>null</code> if none is registered
	 *         under this ID
	 */
	public IClientContext getClientContext(String contextId) {
		return clientContextMap.get(contextId);
	}

	/**
	 * Obtains all of the client contexts registered in the system.
	 * 
	 * @return the available {@link IClientContext}s
	 */
	public Set<IClientContext> getClientContexts() {
		return clientContexts;
	}

	/**
	 * Obtains the client contexts to which the specified object belongs.
	 * 
	 * @param eObject a model element
	 * @return the collection of client contexts to which the <code>eObject</code>
	 *         belongs. This may be empty if no context selector matches this
	 *         element
	 */
	public Collection<IClientContext> getClientContextsFor(EObject eObject) {
		Set<IClientContext> result = new java.util.HashSet<IClientContext>();

		EvaluationContext ctx = new EvaluationContext(null, eObject);

		Collection<IClientContext> contextsCopy;
		synchronized (clientContextLock) {
			contextsCopy = getClientContexts();
		}
		for (Iterator<IClientContext> iter = contextsCopy.iterator(); iter.hasNext();) {

			IClientContext next = iter.next();
			IClientSelector selector = next.getSelector();

			final Object toTest = (selector instanceof XmlExpressionSelector) ? (Object) ctx : (Object) eObject;

			try {
				if (selector.selects(toTest)) {
					result.add(next);
				}
			} catch (RuntimeException e) {
				// client context selectors must not throw exceptions. This one
				// will not be trusted in future validation operations. This
				// is effected by removing it from the context manager
				synchronized (clientContextLock) {
					clientContexts.remove(next);
					clientContextMap.remove(next.getId());
					defaultContexts.remove(next); // in case it is a default context
				}

				Trace.catching(getClass(), "getClientContextsFor", e); //$NON-NLS-1$
				Log.log(IStatus.ERROR, EMFModelValidationStatusCodes.CLIENT_SELECTOR_FAILURE, EMFModelValidationPlugin
						.getMessage(ValidationMessages.client_selectorFailure_ERROR_, new Object[] { next.getId() }),
						e);
			}
		}

		if (result.size() > 1) {
			ClientContext.pruneExtensions(result);
		}

		return result;
	}

	/**
	 * Computes the constraints bound to contexts that include the specified
	 * <code>eObject</code> from amongst the specified collection.
	 * 
	 * @param eObject     a model element
	 * @param constraints a collection of {@link IModelConstraint}s
	 * @return the {@link IModelConstraint}s from amongst the specified
	 *         <code>constraints</code> that are bound to the <code>context</code>
	 */
	public <T extends IModelConstraint> Collection<T> getBindings(EObject eObject,
			Collection<? extends T> constraints) {

		Collection<T> result;

		Collection<IClientContext> contexts = getClientContextsFor(eObject);

		if (contexts.isEmpty()) {
			// no context recognizes this object? Oh, well, then there are
			// no constraints
			result = Collections.emptyList();
		} else if (contexts.size() == 1) {
			// easy when there's just one context
			result = getBindings(contexts.iterator().next(), constraints);
		} else {
			// multiple contexts require more looping
			result = getBindings(contexts, constraints);
		}

		return result;
	}

	/**
	 * Computes the constraints bound to the specified <code>context</code> from
	 * amongst the specified collection.
	 * 
	 * @param context     a client context
	 * @param constraints a collection of {@link IModelConstraint}s
	 * @return the {@link IModelConstraint}s from amongst the specified
	 *         <code>constraints</code> that are bound to the <code>context</code>
	 */
	public <T extends IModelConstraint> Collection<T> getBindings(IClientContext context,
			Collection<? extends T> constraints) {

		Collection<T> result = new java.util.ArrayList<T>(constraints.size());

		for (T constraint : constraints) {
			if (context.includes(constraint)) {
				result.add(constraint);
			} else if (context.isDefault()) {
				// maybe this constraint is implictly bound to the default
				// context, because it is not explicitly bound to any?
				if (isDefaultBinding(constraint)) {
					result.add(constraint);
				}
			}
		}

		return result;
	}

	/**
	 * Computes the constraints bound any of the specified <code>contexts</code>
	 * from amongst the specified collection.
	 * 
	 * @param contexts    a collection of {@link IClientContext}s
	 * @param constraints a collection of {@link IModelConstraint}s
	 * @return the {@link IModelConstraint}s from amongst the specified
	 *         <code>constraints</code> that are bound to the <code>context</code>
	 */
	public <T extends IModelConstraint> Collection<T> getBindings(Collection<? extends IClientContext> contexts,
			Collection<? extends T> constraints) {

		Collection<T> result = new java.util.ArrayList<T>(constraints.size());

		// use an array for performance (to avoid creating so many iterators)
		IClientContext[] ctxArray = contexts.toArray(new IClientContext[contexts.size()]);

		// in case we don't find an explicit binding, we need to know whether
		// we need to look for default bindings. This will only be the case
		// if any of the current contexts is default
		boolean anyContextIsDefault = false;

		for (T constraint : constraints) {
			boolean bound = false;

			for (IClientContext element : ctxArray) {
				bound = element.includes(constraint);

				if (bound) {
					result.add(constraint);
					break; // needn't look at any more contexts
				} else if (element.isDefault()) {
					// no need to check for default contexts as long as we
					// find explicit bindings
					anyContextIsDefault = true;
				}
			}

			if (!bound && anyContextIsDefault) {
				// maybe this constraint is implictly bound to the default
				// context, because it is not explicitly bound to any?
				if (isDefaultBinding(constraint)) {
					result.add(constraint);
				}
			}
		}

		return result;
	}

	/**
	 * Computes whether the specified <code>constraint</code> is implicitly bound to
	 * the default client contexts, by virtue of not being explicitly bound to any
	 * context (even to a default context).
	 * <p>
	 * <em>Side Effect:</em> This method adds the <code>constraint</code>, if it is
	 * indeed a default constraint, to all default client contexts, so that the
	 * computation can be averted in future.
	 * </p>
	 * 
	 * @param constraint a constraint
	 * @return whether the constraint is bound implicitly to default client contexts
	 * 
	 * @see IClientContext#isDefault()
	 */
	private boolean isDefaultBinding(IModelConstraint constraint) {
		boolean result = true;
		String id = constraint.getDescriptor().getId();

		for (Iterator<IClientContext> iter = clientContexts.iterator(); result && iter.hasNext();) {

			result = !iter.next().includes(constraint);
		}

		if (result) {
			// add the constraint to all default contexts so that we don't do
			// this computation again
			for (ClientContext next : defaultContexts) {
				next.includeConstraint(id);
			}
		}

		return result;
	}

	/**
	 * <p>
	 * Configures my providers from the Eclipse configuration <code>elements</code>
	 * representing implementations of my extension point.
	 * </p>
	 * <p>
	 * <b>NOTE</b> that this method should only be called by the EMF Model
	 * Validation Plug-in, not by any client code!
	 * </p>
	 * 
	 * @param elements the configuration elements representing constraint binding
	 *                 extensions
	 * 
	 * @deprecated 1.2 This method is no longer implemented
	 */
	@Deprecated
	public void configureConstraintBindings(IConfigurationElement[] elements) {
		// no longer implemented
	}

	/**
	 * Helper method to configure the <code>&lt;clientContext&gt;</code> occurrences
	 * amongst the <code>elements</code>.
	 * 
	 * @param elements the top-level configuration elements on the
	 *                 <code>constraintBindings</code> extension point
	 */
	public void configureClientContexts(IConfigurationElement[] elements) {
		// copy on write
		clientContexts = new java.util.HashSet<IClientContext>(clientContexts);
		clientContextMap = new java.util.HashMap<String, IClientContext>(clientContextMap);
		defaultContexts = new java.util.HashSet<ClientContext>(defaultContexts);

		for (IConfigurationElement config : elements) {
			if (E_CLIENT_CONTEXT.equals(config.getName())) {
				try {
					ClientContext context = (ClientContext) getClientContext(config.getAttribute(XmlConfig.A_ID));

					if (context != null) {
						// it was a forward declaration
						context.initialize(config);

						if (context.isDefault()) {
							defaultContexts.add(context);
						}
					} else {
						context = new ClientContext(config);

						// prevent duplicates
						if (clientContexts.add(context)) {
							clientContextMap.put(context.getId(), context);

							if (context.isDefault()) {
								defaultContexts.add(context);
							}
						}
					}
				} catch (CoreException e) {
					// this client context will not participate in validation
					Trace.catching(getClass(), "configureClientContexts", e); //$NON-NLS-1$
					Log.log(e.getStatus());
				}
			}
		}
	}

	/**
	 * Adds the specified implicit client context binding (forward reference). The
	 * client-context lock must be held when calling this method.
	 * 
	 * @param context the implicit context to add
	 */
	public void addClientContext(ClientContext context) {
		synchronized (clientContextLock) {
			// copy on write
			clientContexts = new java.util.HashSet<IClientContext>(clientContexts);
			clientContextMap = new java.util.HashMap<String, IClientContext>(clientContextMap);

			if (clientContexts.add(context)) {
				clientContextMap.put(context.getId(), context);
			}
		}
	}

	/**
	 * Helper method to configure the <code>&lt;binding&gt;</code> occurrences
	 * amongst the <code>elements</code>.
	 * 
	 * @param elements the top-level configuration elements on the
	 *                 <code>constraintBindings</code> extension point
	 */
	public void configureBindings(IConfigurationElement[] elements) {
		for (IConfigurationElement config : elements) {
			if (E_BINDING.equals(config.getName())) {
				String contextId = config.getAttribute(A_CONTEXT);

				if (contextId == null) {
					Log.errorMessage(EMFModelValidationStatusCodes.BINDING_NO_CLIENT,
							ValidationMessages.binding_noContextId_ERROR_,
							new Object[] { config.getDeclaringExtension().getNamespaceIdentifier() });
				} else {
					ClientContext context = (ClientContext) getClientContext(contextId);

					if (context == null) {
						context = new ClientContext(contextId, config.getDeclaringExtension().getNamespaceIdentifier());
						addClientContext(context);
					}

					configureBindings(context, config);
				}
			}
		}
	}

	/**
	 * Helper method to process a particular binding element for its client
	 * <code>context</code>.
	 * 
	 * @param context a client context referenced by a binding
	 * @param config  a particular <code>&lt;binding&gt;</config> element
	 */
	private void configureBindings(ClientContext context, IConfigurationElement config) {
		String id = config.getAttribute(E_CONSTRAINT);

		if (id != null) {
			context.includeConstraint(id);
		}

		id = config.getAttribute(E_CATEGORY);

		if (id != null) {
			context.includeCategory(id);
		}

		IConfigurationElement[] children = config.getChildren();
		for (IConfigurationElement element : children) {
			final String name = element.getName();
			final String ref = element.getAttribute(A_REF);

			if (ref == null) {
				if (E_CONSTRAINT.equals(name) || E_EXCLUDE_CONSTRAINT.equals(name)) {
					Log.warningMessage(EMFModelValidationStatusCodes.BINDING_NO_CONSTRAINT,
							ValidationMessages.binding_noConstraintRef_WARN_,
							new Object[] { context.getId(), config.getDeclaringExtension().getNamespaceIdentifier() });
				} else if (E_EXTEND_CLIENT_CONTEXT.equals(name)) {
					Log.warningMessage(EMFModelValidationStatusCodes.BINDING_NO_CLIENT_CONTEXT,
							ValidationMessages.binding_noClientContextRef_WARN_,
							new Object[] { context.getId(), config.getDeclaringExtension().getNamespaceIdentifier() });
				} else {
					Log.errorMessage(EMFModelValidationStatusCodes.BINDING_NO_CATEGORY,
							ValidationMessages.binding_noCategoryRef_WARN_,
							new Object[] { context.getId(), config.getDeclaringExtension().getNamespaceIdentifier() });
				}
			} else if (E_CONSTRAINT.equals(name)) {
				context.includeConstraint(ref);
			} else if (E_CATEGORY.equals(name)) {
				context.includeCategory(ref);
			} else if (E_EXTEND_CLIENT_CONTEXT.equals(name)) {
				context.extendClientContext(ref);
			} else if (E_EXCLUDE_CONSTRAINT.equals(name)) {
				context.excludeConstraint(ref);
			} else if (E_EXCLUDE_CATEGORY.equals(name)) {
				context.excludeCategory(ref);
			}
		}
	}
}
