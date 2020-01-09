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
 *    Zeligsoft - Bugs 249496, 252302
 *    SAP AG - Bug 240352
 ****************************************************************************/
package org.eclipse.emf.validation.internal.service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.internal.util.XmlExpressionSelector;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.IClientSelector;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.osgi.util.NLS;


/**
 * The implementation of the client context interface.
 * <p>
 * This class is not intended to be used by clients.
 * </p>
 * 
 * @author Christian W. Damus
 */
public class ClientContext
	implements IClientContext {

	private static final String A_DEFAULT = "default"; //$NON-NLS-1$
	
	private static final String E_ENABLEMENT = "enablement"; //$NON-NLS-1$
	private static final String E_SELECTOR = "selector"; //$NON-NLS-1$
	
	private String id;
	private IClientSelector selector;
	private boolean isDefault;
	
	// map of String constraint IDs that are bound to me, the boolean value
	// indicating absolute inclusion or exclusion.  Absence of a value means
	// that we still need to compute
	private final Map<String, Boolean> constraintBindings = new java.util.HashMap<String, Boolean>();
	
	// set of String category IDs that are bound to me
	private BindingFilter filter = BindingFilter.NULL;
	
	private Collection<String> extendedClientContexts = new java.util.ArrayList<String>(
		2);
	
	/**
	 * Initializes me with my XML configuration.
	 * 
	 * @param config my XML configuration element
	 * @throws CoreException on any problem in accessing the
	 *    <code>config</code>uration or if anything is missing or incorrect
	 */
	public ClientContext(IConfigurationElement config) throws CoreException {
		initialize(config);
	}
	
	/**
	 * Initializes me as an implicit client context, required by some binding in
	 * the specified <tt>bindingContributorID</tt> plug-in. Later processing of
	 * an extension that defines my details will complete my definition.
	 * 
	 * @param id
	 *            my ID
	 * @param bindingContributorID
	 *            the ID of a plug-in that is binding some constraints to me
	 * 
	 * @since 1.3
	 */
	public ClientContext(String id, final String bindingContributorID) {
		this.id = id;
		this.isDefault = false;
		this.selector = new IClientSelector() {

			public boolean selects(Object object) {
				// if my selector definition isn't eventually specified, then
				// I basically don't exist
				throw new IllegalStateException(NLS.bind(
					ValidationMessages.binding_noSuchContext_ERROR_,
					bindingContributorID));
			}
		};
	}
	
	/**
	 * Initializes me with my XML configuration.
	 * 
	 * @param config
	 *            my XML configuration element
	 * @throws CoreException
	 *             on any problem in accessing the <code>config</code>uration or
	 *             if anything is missing or incorrect
	 * 
	 * @since 1.3
	 */
	void initialize(IConfigurationElement config) throws CoreException {
		id = initializeId(config);
		selector = initializeSelector(config);
		isDefault = initializeDefault(config);
	}
	
	/**
	 * Gets my ID from the specified XML <code>config</code>.
	 * 
	 * @param config my XML configuration
	 * @return my ID (never <code>null</code>)
	 * @throws CoreException if my ID is not specified
	 */
	private String initializeId(IConfigurationElement config) throws CoreException {
		String result = config.getAttribute(XmlConfig.A_ID);
		
		if (result == null) {
			CoreException ce = new CoreException(
				new Status(
					IStatus.ERROR,
					EMFModelValidationPlugin.getPluginId(),
					EMFModelValidationStatusCodes.CLIENT_NO_ID,
					EMFModelValidationPlugin.getMessage(
						ValidationMessages.client_noId_ERROR_,
						new Object[] {
							config.getDeclaringExtension().getNamespaceIdentifier()}),
					null));
			Trace.throwing(getClass(), "initializeId", ce); //$NON-NLS-1$
			
			throw ce;
		}
		
		return result;
	}
	
	/**
	 * Gets my selector from the specified XML <code>config</code>.
	 * 
	 * @param config my XML configuration
	 * @return my selector (never <code>null</code>)
	 * @throws CoreException if my selector is not specified or something went
	 *     wrong in initializing it
	 */
	private IClientSelector initializeSelector(IConfigurationElement config) throws CoreException {
		IClientSelector result = null;
		
		IConfigurationElement[] enablement = config.getChildren(E_ENABLEMENT);
		if (enablement.length > 0) {
			result = initializeExpressionSelector(enablement[0]);
		} else {
			IConfigurationElement[] custom = config.getChildren(E_SELECTOR);
			if (custom.length > 0) {
				result = initializeCustomSelector(custom[0]);
			}
		}
		
		if (result == null) {
			CoreException ce = new CoreException(
				new Status(
					IStatus.ERROR,
					EMFModelValidationPlugin.getPluginId(),
					EMFModelValidationStatusCodes.CLIENT_NO_SELECTOR,
					EMFModelValidationPlugin.getMessage(
						ValidationMessages.client_noSelector_ERROR_,
						new Object[] {
							getId(),  // already initialized (and final)
							config.getDeclaringExtension().getNamespaceIdentifier()}),
					null));
			Trace.throwing(getClass(), "initializeSelector", ce); //$NON-NLS-1$
			
			throw ce;
		}
		
		return result;
	}
	
	/**
	 * Creates an expression-based selector from the specified XML
	 * <code>enablement</code> expression.
	 * 
	 * @param enablement my XML expression
	 * @return the selector (never <code>null</code>)
	 * @throws CoreException if something is malformed in the expression
	 */
	private IClientSelector initializeExpressionSelector(IConfigurationElement enablement) throws CoreException {
		try {
			return new XmlExpressionSelector(enablement);
		} catch (CoreException e) {
			Trace.catching(getClass(), "initializeExpressionSelector", e); //$NON-NLS-1$
			
			CoreException ce = new CoreException(
				new Status(
					IStatus.ERROR,
					EMFModelValidationPlugin.getPluginId(),
					EMFModelValidationStatusCodes.CLIENT_INVALID_EXPRESSION,
					EMFModelValidationPlugin.getMessage(
						ValidationMessages.client_badExpression_ERROR_,
						new Object[] {
							getId(),  // already initialized (and final)
							enablement.getDeclaringExtension().getNamespaceIdentifier()}),
					e));
			
			Trace.throwing(getClass(), "initializeExpressionSelector", ce); //$NON-NLS-1$
			
			throw ce;
		}
	}
	
	/**
	 * Instantiates a custom selector class specified in the XML.
	 * 
	 * @param config a selector configuration element
	 * @return the selector (never <code>null</code>)
	 * @throws CoreException if something is malformed in the expression
	 */
	private IClientSelector initializeCustomSelector(IConfigurationElement config) throws CoreException {
		Object result = config.createExecutableExtension(XmlConfig.A_CLASS);
		
		if (!(result instanceof IClientSelector)) {
			CoreException ce = new CoreException(
				new Status(
					IStatus.ERROR,
					EMFModelValidationPlugin.getPluginId(),
					EMFModelValidationStatusCodes.CLIENT_SELECTOR_WRONG_CLASS,
					EMFModelValidationPlugin.getMessage(
						ValidationMessages.client_selectorClass_ERROR_,
						new Object[] {
							result.getClass().getName(),
							IClientSelector.class.getName(),
							getId(), // already initialized (and final)
							config.getDeclaringExtension().getNamespaceIdentifier()}),
					null));
			Trace.throwing(getClass(), "initializeCustomSelector", ce); //$NON-NLS-1$
			
			throw ce;
		}
		
		return (IClientSelector) result;
	}
	
	/**
	 * Gets my default-ness from my <code>config</code>uration element.
	 * 
	 * @param config my configuration element
	 * @return whether I am default
	 */
	private boolean initializeDefault(IConfigurationElement config) {
		boolean result = false;
		String string = config.getAttribute(A_DEFAULT);
		
		if (string != null) {
			result = Boolean.valueOf(string).booleanValue();
		}
		
		return result;
	}

	public final String getId() {
		return id;
	}

	public final IClientSelector getSelector() {
		return selector;
	}
	
	public void setSelector(IClientSelector selector) {
		this.selector = selector;
	}

	public final boolean isDefault() {
		return isDefault;
	}

	public boolean includes(IModelConstraint constraint) {
		IConstraintDescriptor desc = constraint.getDescriptor();
		
		return (desc != null) && includes(desc);
	}
	
	boolean includes(IConstraintDescriptor constraint) {
		Boolean result = constraintBindings.get(constraint.getId());
		
		if (result == null) {
			// cache the result for this constraint
			result = filter.getBinding(constraint);
			constraintBindings.put(constraint.getId(), result);
		}
		
		return result;
	}
	
	/**
	 * Adds a constraint inclusion binding to me.
	 * 
	 * @param constraintId the ID of a constraint that is to be included in me
	 */
	public void includeConstraint(String constraintId) {
		filter = filter.includeConstraint(constraintId);
	}

	/**
	 * Adds a constraint exclusion binding to me.
	 * 
	 * @param constraintId the ID of a constraint that is to be excluded from me
	 */
	public void excludeConstraint(String constraintId) {
		filter = filter.excludeConstraint(constraintId);
	}

	/**
	 * Adds a constraint category inclusion to me.
	 * 
	 * @param categoryId the qualified ID (path) of a constraint category that
	 *     is to be included in me
	 */
	public void includeCategory(String categoryId) {
		filter = filter.includeCategory(categoryId);
	}

	/**
	 * Adds a constraint category exclusion to me.
	 * 
	 * @param categoryId
	 *            the qualified ID (path) of a constraint category that is to be
	 *            excluded from me
	 */
	public void excludeCategory(String categoryId) {
		filter = filter.excludeCategory(categoryId);
	}

	/**
	 * Adds a client-context extension to me.
	 * 
	 * @param clientContextID
	 *            a client-context to extend
	 */
	public void extendClientContext(String clientContextID) {
		filter = filter.extendClientContext(clientContextID);
		
		if (!extendedClientContexts.contains(clientContextID)) {
			extendedClientContexts.add(clientContextID);
		}
	}
	
	/**
	 * Obtains all of the client-contexts, recursively, that I extend.  As this
	 * is recursive, the result includes contexts extended by contexts that I
	 * extend.  Note that it is an error for a client-context to, transitively
	 * or not, extend itself.
	 * 
	 * @return all of my extended client contexts
	 */
	Collection<? extends IClientContext> allExtendedContexts() {
		Set<IClientContext> result = new java.util.HashSet<IClientContext>();
		
		allExtendedContexts(this, result);
		
		return result;
	}

	/**
	 * Recursive helper for the client-context extension gathering.
	 * 
	 * @param self
	 *            an extending client-context
	 * @param contexts
	 *            its extensions
	 */
	private static void allExtendedContexts(ClientContext self,
			Set<? super ClientContext> contexts) {
		
		ClientContextManager mgr = ClientContextManager.getInstance();
		
		for (String next : self.extendedClientContexts) {
			ClientContext extended = (ClientContext) mgr.getClientContext(next);
			
			if (contexts.add(extended)) {
				allExtendedContexts(extended, contexts);
			}
		}
	}

	/**
	 * Removes client-contexts from a set that are extended by other contexts
	 * already in the set.
	 * 
	 * @param contexts
	 *            a set of client-contexts to optimize
	 */
	static void pruneExtensions(Set<? extends IClientContext> contexts) {
		boolean repeat;
		do {
			repeat = false;
			
			for (IClientContext next : contexts) {
				if ((next instanceof ClientContext)
					&& contexts.removeAll(((ClientContext) next)
						.allExtendedContexts())) {
					
					repeat = true;
					break;
				}
			}
		} while (repeat);
	}

	/**
	 * The context ID fully determines equality.
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof ClientContext)
			&& ((ClientContext) obj).getId().equals(getId());
	}
	
	/**
	 * The context ID fully determines equality.
	 */
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public String toString() {
		return "ClientContext[" + getId() + ']'; //$NON-NLS-1$
	}
	
	
	/**
	 * A chain-structured constraint-binding filter. Filters are chained in the
	 * order in which they are parsed from the extension point. The head of the
	 * filter chain applies its filter and, if it doesn't find any match,
	 * delegates down the chain. The chain is terminated by the {@link #NULL}
	 * filter, which always excludes the constraint.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private static class BindingFilter {

		private BindingFilter next;

		/** A filter that excludes every constraint. */
		static final BindingFilter NULL = new BindingFilter() {

			boolean getBinding(IConstraintDescriptor constraint) {
				return false;
			}
		};

		/**
		 * Queries whether the specified constraint is definitely included (
		 * <code>true</code>) or excluded (<code>false</code>) from the client
		 * context. If I don't have definitive knowledge of this constraint, I
		 * delegate to the next in the chain.
		 * 
		 * @param constraint
		 *            a constraint descriptor
		 * @return whether the constraint is included
		 */
		boolean getBinding(IConstraintDescriptor constraint) {
			return isExcluded(constraint)
				? false
				: isIncluded(constraint)
					? true
					: next().getBinding(constraint);
		}

		/**
		 * Queries whether I know that a constraint is included.
		 * 
		 * @param constraint
		 *            a constraint descriptor
		 * @return <code>true</code> if the constraint is included, or
		 *         <code>false</code> if I do not know
		 */
		boolean isExcluded(IConstraintDescriptor constraint) {
			return false;
		}

		/**
		 * Queries whether I know that a constraint is excluded.
		 * 
		 * @param constraint
		 *            a constraint descriptor
		 * @return <code>true</code> if the constraint is excluded, or
		 *         <code>false</code> if I do not know
		 */
		boolean isIncluded(IConstraintDescriptor constraint) {
			return false;
		}

		/**
		 * Obtains the next filter in my chain.
		 * 
		 * @return my next, or <code>null</code> if I am the end of the chain
		 */
		BindingFilter next() {
			return next;
		}

		/**
		 * Assigns my next filter.
		 * 
		 * @param next
		 *            my new next
		 */
		void setNext(BindingFilter next) {
			this.next = next;
		}

		/**
		 * Obtains a filter, chaining me, that definitively includes the
		 * specified category and all of its constraints and sub-categories. The
		 * result may be optimized to be myself augmented with this category, if
		 * I am a filter of the appropriate kind. Or, the result may be a new
		 * filter chain.
		 * 
		 * @param category
		 *            a category to include
		 * 
		 * @return a filter that includes the category
		 */
		BindingFilter includeCategory(String category) {
			CategoryInclusion result = new CategoryInclusion(category);
			result.setNext(this);
			return result;
		}

		/**
		 * Obtains a filter, chaining me, that definitively excludes the
		 * specified category and all of its constraints and sub-categories. The
		 * result may be optimized to be myself augmented with this category, if
		 * I am a filter of the appropriate kind. Or, the result may be a new
		 * filter chain.
		 * 
		 * @param category
		 *            a category to exclude
		 * 
		 * @return a filter that excludes the category
		 */
		BindingFilter excludeCategory(String category) {
			CategoryExclusion result = new CategoryExclusion(category);
			result.setNext(this);
			return result;
		}

		/**
		 * Obtains a filter, chaining me, that definitively includes the
		 * specified constraint. The result may be optimized to be myself
		 * augmented with this constraint, if I am a filter of the appropriate
		 * kind. Or, the result may be a new filter chain.
		 * 
		 * @param constraint
		 *            a constraint to include
		 * 
		 * @return a filter that includes the constraint
		 */
		BindingFilter includeConstraint(String constraint) {
			ConstraintInclusion result = new ConstraintInclusion(constraint);
			result.setNext(this);
			return result;
		}

		/**
		 * Obtains a filter, chaining me, that definitively excludes the
		 * specified constraint. The result may be optimized to be myself
		 * augmented with this constraint, if I am a filter of the appropriate
		 * kind. Or, the result may be a new filter chain.
		 * 
		 * @param constraint
		 *            a constraint to exclude
		 * 
		 * @return a filter that excludes the constraint
		 */
		BindingFilter excludeConstraint(String constraint) {
			ConstraintExclusion result = new ConstraintExclusion(constraint);
			result.setNext(this);
			return result;
		}

		/**
		 * Obtains a filter, chaining me, that inherits the constraints bound to
		 * the specified client-context. The result may be optimized to be
		 * myself myself augmented with this client-context, if I am a filter of
		 * the appropriate kind. Or, the result may be a new filter chain.
		 * 
		 * @param clientContext
		 *            a client-context to extend
		 * 
		 * @return a filter that extends the client-context
		 */
		BindingFilter extendClientContext(String clientContext) {
			ContextExtension result = new ContextExtension(clientContext);
			result.setNext(this);
			return result;
		}
	}

	/**
	 * A binding filter that definitively includes one or more constraints.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private static class ConstraintInclusion
			extends BindingFilter {

		private final Set<String> constraints = new java.util.HashSet<String>();

		ConstraintInclusion(String constraint) {
			constraints.add(constraint);
		}

		@Override
		boolean isIncluded(IConstraintDescriptor constraint) {
			return constraints.contains(constraint.getId());
		}

		@Override
		BindingFilter includeConstraint(String constraint) {
			constraints.add(constraint);
			return this;
		}
	}

	/**
	 * A binding filter that definitively excludes one or more constraints.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private static class ConstraintExclusion
			extends BindingFilter {

		private final Set<String> constraints = new java.util.HashSet<String>();

		ConstraintExclusion(String constraint) {
			constraints.add(constraint);
		}

		@Override
		boolean isExcluded(IConstraintDescriptor constraint) {
			return constraints.contains(constraint.getId());
		}

		@Override
		BindingFilter excludeConstraint(String constraint) {
			constraints.add(constraint);
			return this;
		}
	}

	/**
	 * A binding filter that definitively includes one or more categories.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private static class CategoryInclusion
			extends BindingFilter {

		private final CategorySet categories;

		CategoryInclusion(String category) {
			categories = new CategorySet(category);
		}

		@Override
		boolean isIncluded(IConstraintDescriptor constraint) {
			return categories.containsAny(constraint.getCategories());
		}

		@Override
		BindingFilter includeCategory(String category) {
			categories.add(category);
			return this;
		}
	}

	/**
	 * A binding filter that definitively excludes one or more categories.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private static class CategoryExclusion
			extends BindingFilter {

		private final CategorySet categories;

		CategoryExclusion(String category) {
			categories = new CategorySet(category);
		}

		@Override
		boolean isExcluded(IConstraintDescriptor constraint) {
			return categories.containsAny(constraint.getCategories());
		}

		@Override
		BindingFilter excludeCategory(String category) {
			categories.add(category);
			return this;
		}
	}

	private static final class CategorySet {

		private final Set<String> categories = new java.util.HashSet<String>();

		CategorySet(String category) {
			categories.add(category);
		}

		boolean containsAny(Collection<? extends Category> categories) {
			boolean result = false;

			for (Category next : categories) {
				if (contains(next)) {
					result = true;
					break;
				}
			}

			return result;
		}

		boolean contains(Category category) {
			boolean result = false;

			String path = category.getPath();
			result = categories.contains(path);

			if (!result) {
				// search the ancestors
				Category ancestor = category.getParent();

				while ((ancestor != null) && !result) {
					result = categories.contains(ancestor.getPath());
					ancestor = ancestor.getParent();
				}

				if (result) {
					// cache the original category for quicker results on the
					// next category that it contains
					add(path);
				}
			}

			return result;
		}

		void add(String category) {
			categories.add(category);
		}
	}

	/**
	 * A binding filter that inherits the bindings of another context.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private static class ContextExtension
			extends BindingFilter {

		private final Set<String> extendedContextIDs = new java.util.HashSet<String>();

		private volatile Set<ClientContext> extendedContexts;

		ContextExtension(String clientContext) {
			extendedContextIDs.add(clientContext);
		}

		@Override
		boolean isIncluded(IConstraintDescriptor constraint) {
			if (extendedContexts == null) {
				// do this lazily because we don't know the order in which
				// client-contexts will be discovered in the extension registry
				ClientContextManager mgr = ClientContextManager.getInstance();
				Set<ClientContext> contexts = new java.util.HashSet<ClientContext>();

				synchronized (extendedContextIDs) {
					for (String next : extendedContextIDs) {
						contexts
							.add((ClientContext) mgr.getClientContext(next));
					}
					
					extendedContexts = contexts;
				}
			}
			
			for (ClientContext extended : extendedContexts) {
				if (extended.includes(constraint)) {
					return true;
				}
			}

			return false;
		}

		@Override
		BindingFilter extendClientContext(String clientContext) {
			synchronized (extendedContextIDs) {
				extendedContexts = null; // purge
				extendedContextIDs.add(clientContext);
			}

			return this;
		}
	}
}
