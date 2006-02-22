/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.internal.util.XmlExpressionSelector;
import org.eclipse.emf.validation.model.IClientSelector;
import org.eclipse.emf.validation.model.IModelConstraint;


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
	
	private static final String A_CONSTRAINT = "constraint"; //$NON-NLS-1$
	private static final String A_CATEGORY = "category"; //$NON-NLS-1$
	private static final String A_REF = "ref"; //$NON-NLS-1$
	
	private static final ClientContextManager INSTANCE = new ClientContextManager();
	
	private final Set clientContexts = new java.util.HashSet();
	private final Map clientContextMap = new java.util.HashMap();
	
	private final Set defaultContexts = new java.util.HashSet();
	
	/**
	 * Not instantiable by clients.
	 */
	private ClientContextManager() {
		super();
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
	 *     under this ID
	 */
	public IClientContext getClientContext(String contextId) {
		return (IClientContext) clientContextMap.get(contextId);
	}
	
	/**
	 * Obtains all of the client contexts registered in the system.
	 * 
	 * @return the available {@link IClientContext}s
	 */
	public Set getClientContexts() {
		return clientContexts;
	}

	/**
	 * Obtains the client contexts to which the specified object belongs.
	 *  
	 * @param eObject a model element
	 * @return the collection of client contexts to which the
	 *    <code>eObject</code> belongs.  This may be empty if no context
	 *    selector matches this element
	 */
	public Collection getClientContextsFor(EObject eObject) {
		Collection result = new java.util.ArrayList();
		
		EvaluationContext ctx = new EvaluationContext(null, eObject);
		
		for (Iterator iter = getClientContexts().iterator(); iter.hasNext();) {
			IClientContext next = (IClientContext) iter.next();
			IClientSelector selector = next.getSelector();
			
			final Object toTest = (selector instanceof XmlExpressionSelector)
					? (Object) ctx
					: (Object) eObject;
			
			try {
				if (selector.selects(toTest)) {
					result.add(next);
				}
			} catch (RuntimeException e) {
				// client context selectors must not throw exceptions.  This one
				//   will not be trusted in future validation operations.  This
				//   is effected by removing it from the context manager
				iter.remove();
				clientContextMap.remove(next.getId());
				defaultContexts.remove(next); // in case it is a default context
				
				Trace.catching(getClass(), "getClientContextsFor", e); //$NON-NLS-1$
				Log.log(
					IStatus.ERROR,
					EMFModelValidationStatusCodes.CLIENT_SELECTOR_FAILURE,
					EMFModelValidationPlugin.getMessage(
						ValidationMessages.client_selectorFailure_ERROR_,
						new Object[] {
							next.getId()}),
					e);
			}
		}
		
		return result;
	}
	
	/**
	 * Computes the constraints bound to contexts that include the specified
	 * <code>eObject</code> from amongst the specified collection.
	 * 
	 * @param eObject a model element
	 * @param constraints a collection of {@link IModelConstraint}s
	 * @return the {@link IModelConstraint}s from amongst the specified
	 *     <code>constraints</code> that are bound to the <code>context</code>
	 */
	public Collection getBindings(EObject eObject, Collection constraints) {
		Collection result;
		
		Collection contexts = getClientContextsFor(eObject);
		
		if (contexts.isEmpty()) {
			// no context recognizes this object?  Oh, well, then there are
			//   no constraints
			result = Collections.EMPTY_LIST;
		} else if (contexts.size() == 1) {
			// easy when there's just one context
			result = getBindings(
				(IClientContext) contexts.iterator().next(),
				constraints); 
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
	 * @param context a client context
	 * @param constraints a collection of {@link IModelConstraint}s
	 * @return the {@link IModelConstraint}s from amongst the specified
	 *     <code>constraints</code> that are bound to the <code>context</code>
	 */
	public Collection getBindings(IClientContext context, Collection constraints) {
		Collection result = new java.util.ArrayList(constraints.size());
		
		for (Iterator iter = constraints.iterator(); iter.hasNext();) {
			IModelConstraint constraint = (IModelConstraint) iter.next();
			
			if (context.includes(constraint)) {
				result.add(constraint);
			} else if (context.isDefault()) {
				// maybe this constraint is implictly bound to the default
				//   context, because it is not explicitly bound to any?
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
	 * @param contexts a collection of {@link IClientContext}s
	 * @param constraints a collection of {@link IModelConstraint}s
	 * @return the {@link IModelConstraint}s from amongst the specified
	 *     <code>constraints</code> that are bound to the <code>context</code>
	 */
	public Collection getBindings(Collection contexts, Collection constraints) {
		Collection result = new java.util.ArrayList(constraints.size());
		
		// use an array for performance (to avoid creating so many iterators)
		IClientContext[] ctxArray = (IClientContext[]) contexts.toArray(
			new IClientContext[contexts.size()]);
		
		// in case we don't find an explicit binding, we need to know whether
		//   we need to look for default bindings.  This will only be the case
		//   if any of the current contexts is default
		boolean anyContextIsDefault = false;
		
		for (Iterator iter = constraints.iterator(); iter.hasNext();) {
			IModelConstraint constraint = (IModelConstraint) iter.next();
			boolean bound = false;
			
			for (int i = 0; i < ctxArray.length; i++) {
				bound = ctxArray[i].includes(constraint);
				
				if (bound) {
					result.add(constraint);
					break;  // needn't look at any more contexts
				} else if (ctxArray[i].isDefault()) {
					// no need to check for default contexts as long as we
					//   find explicit bindings
					anyContextIsDefault = true;
				}
			}
			
			if (!bound && anyContextIsDefault) {
				// maybe this constraint is implictly bound to the default
				//   context, because it is not explicitly bound to any?
				if (isDefaultBinding(constraint)) {
					result.add(constraint);
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Computes whether the specified <code>constraint</code> is implicitly
	 * bound to the default client contexts, by virtue of not being explicitly
	 * bound to any context (even to a default context).
	 * <p>
	 * <em>Side Effect:</em>  This method adds the <code>constraint</code>, if
	 * it is indeed a default constraint, to all default client contexts, so
	 * that the computation can be averted in future.
	 * </p>
	 * 
	 * @param constraint a constraint
	 * @return whether the constraint is bound implicitly to default client
	 *    contexts
	 * 
	 * @see IClientContext#isDefault()
	 */
	private boolean isDefaultBinding(IModelConstraint constraint) {
		boolean result = true;
		String id = constraint.getDescriptor().getId();
		
		for (Iterator iter = clientContexts.iterator(); result && iter.hasNext();) {
			result = !((IClientContext) iter.next()).includes(constraint);
		}
		
		if (result) {
			// add the constraint to all default contexts so that we don't do
			//   this computation again
			for (Iterator iter = defaultContexts.iterator(); iter.hasNext();) {
				((ClientContext) iter.next()).bindConstraint(id);
			}
		}
		
		return result;
	}
	
	/**
	 * <p>
	 * Configures my providers from the Eclipse configuration
	 * <code>elements</code> representing implementations of my extension point.
	 * </p>
	 * <p>
	 * <b>NOTE</b> that this method should only be called by the EMF Model
	 * Validation Plug-in, not by any client code!
	 * </p>
	 * 
	 * @param elements the configuration elements representing constraint
	 *     binding extensions
	 */
	public void configureConstraintBindings(IConfigurationElement[] elements) {
		// must create all of the contexts before we process the bindings.
		//   Hence, this will loop over the elements twice
		configureClientContexts(elements);
		configureBindings(elements);
	}
	
	/**
	 * Helper method to configure the <code>&lt;clientContext&gt;</code>
	 * occurrences amongst the <code>elements</code>.
	 * 
	 * @param elements the top-level configuration elements on the
	 *     <code>constraintBindings</code> extension point
	 */
	private void configureClientContexts(IConfigurationElement[] elements) {
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement config = elements[i];
			
			if (E_CLIENT_CONTEXT.equals(config.getName())) {
				try {
					ClientContext context = new ClientContext(config);
					
					// prevent duplicates
					if (clientContexts.add(context)) {
						clientContextMap.put(context.getId(), context);
						
						if (context.isDefault()) {
							defaultContexts.add(context);
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
	 * Helper method to configure the <code>&lt;binding&gt;</code>
	 * occurrences amongst the <code>elements</code>.
	 * 
	 * @param elements the top-level configuration elements on the
	 *     <code>constraintBindings</code> extension point
	 */
	private void configureBindings(IConfigurationElement[] elements) {
		for (int i = 0; i < elements.length; i++) {
			IConfigurationElement config = elements[i];
			
			if (E_BINDING.equals(config.getName())) {
				String contextId = config.getAttribute(A_CONTEXT);
				
				if (contextId == null) {
					Log.errorMessage(
						EMFModelValidationStatusCodes.BINDING_NO_CLIENT,
						ValidationMessages.binding_noContextId_ERROR_,
						new Object[] {
							config.getDeclaringExtension().getNamespaceIdentifier()});
				} else {
					ClientContext context = (ClientContext) getClientContext(contextId);
					
					if (context == null) {
						Log.errorMessage(
							EMFModelValidationStatusCodes.BINDING_NO_SUCH_CLIENT,
							ValidationMessages.binding_noSuchContext_ERROR_,
							new Object[] {
								contextId,
								config.getDeclaringExtension().getNamespaceIdentifier()});
					} else {
						configureBindings(context, config);
					}
				}
			}
		}
	}
	
	/**
	 * Helper method to process a particular binding element for its client
	 * <code>context</code>.
	 * 
	 * @param context a client context referenced by a binding
	 * @param config a particular <code>&lt;binding&gt;</config> element
	 */
	private void configureBindings(ClientContext context, IConfigurationElement config) {
		String id = config.getAttribute(A_CONSTRAINT);
		
		if (id != null) {
			context.bindConstraint(id);
		}
		
		id = config.getAttribute(A_CATEGORY);
		
		if (id != null) {
			context.bindCategory(id);
		}
		
		IConfigurationElement[] children = config.getChildren(A_CONSTRAINT);
		for (int i = 0; i < children.length; i++) {
			final String ref = children[i].getAttribute(A_REF);
			
			if (ref == null) {
				Log.warningMessage(
					EMFModelValidationStatusCodes.BINDING_NO_CONSTRAINT,
					ValidationMessages.binding_noConstraintRef_WARN_,
					new Object[] {
						context.getId(),
						config.getDeclaringExtension().getNamespaceIdentifier()});
			} else {
				context.bindConstraint(ref);
			}
		}
		
		children = config.getChildren(A_CATEGORY);
		for (int i = 0; i < children.length; i++) {
			final String ref = children[i].getAttribute(A_REF);
			
			if (ref == null) {
				Log.errorMessage(
					EMFModelValidationStatusCodes.BINDING_NO_CATEGORY,
					ValidationMessages.binding_noCategoryRef_WARN_,
					new Object[] {
						context.getId(),
						config.getDeclaringExtension().getNamespaceIdentifier()});
			} else {
				context.bindCategory(ref);
			}
		}
	}
}
