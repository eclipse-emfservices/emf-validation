/******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
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
import java.util.Iterator;
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
	
	private final String id;
	private final IClientSelector selector;
	private final boolean isDefault;
	
	// set of String constraint IDs that are bound to me
	private final Set<String> constraintBindings = new java.util.HashSet<String>();
	
	// set of String category IDs that are bound to me
	private final Set<String> categoryBindings = new java.util.HashSet<String>();
	
	/**
	 * Initializes me with my XML configuration.
	 * 
	 * @param config my XML configuration element
	 * @throws CoreException on any problem in accessing the
	 *    <code>config</code>uration or if anything is missing or incorrect
	 */
	public ClientContext(IConfigurationElement config) throws CoreException {
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

	public final boolean isDefault() {
		return isDefault;
	}

	public boolean includes(IModelConstraint constraint) {
		boolean result = false;
		
		IConstraintDescriptor descriptor = constraint.getDescriptor();
		
		if (descriptor != null) {
			result = constraintBindings.contains(descriptor.getId());
			
			if (!result && !categoryBindings.isEmpty()) {
				// look for a bound category
				result = hasCategoryBinding(descriptor.getCategories());
				
				if (result) {
					// cache the result for this constraint
					bindConstraint(descriptor.getId());
				}
			}
		}
		
		return result;
	}
	
	/**
	 * Determines whether any of the specified <code>categories</code> is bound
	 * to me.
	 * 
	 * @param categories a collection of categories (usually from a constraint)
	 * @return <code>true</code> if any of the <code>categories</code> is bound,
	 *     or if any of their ancestors is bound; <code>false</code>, otherwise
	 */
	private boolean hasCategoryBinding(Collection<Category> categories) {
		boolean result = false;
		
		for (Iterator<Category> iter = categories.iterator(); !result && iter.hasNext();) {
			Category category = iter.next();
			
			result = categoryBindings.contains(category.getPath());
			
			if (!result) {
				// search the ancestors
				Category ancestor = category.getParent();
				
				while ((ancestor != null) && !result) {
					result = categoryBindings.contains(ancestor.getPath());
					ancestor = ancestor.getParent();
				}
				
				if (result) {
					// cache the original category for quicker results on the
					//    next constraint that it includes
					bindCategory(category.getPath());
				}
			}
		}
		
		return result;
	}

	/**
	 * Binds a constraint to me.
	 * 
	 * @param constraintId the ID of a constraint that is to be bound to me
	 */
	void bindConstraint(String constraintId) {
		constraintBindings.add(constraintId);
	}

	/**
	 * Binds a constraint category to me.
	 * 
	 * @param categoryId the qualified ID (path) of a constraint category that
	 *     is to be bound to me
	 */
	void bindCategory(String categoryId) {
		categoryBindings.add(categoryId);
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
}
