/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 *    Radek Dvorak (Borland) - Bugzilla 165661
 ****************************************************************************/
package org.eclipse.emf.validation.internal.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.text.StringMatcher;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.AbstractConstraintProvider;
import org.eclipse.emf.validation.service.IModelConstraintProvider;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.XmlConstraintProvider;

/**
 * Primary implementation of the {@link IProviderDescriptor} interface.
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ProviderDescriptor implements IProviderDescriptor {
	private final IConfigurationElement myConfig;
	private IConfigurationElement[] targets;
	private String[] nsUris;
	private final StringMatcher[] nsUriMatchers;
	private IModelConstraintProvider provider = null;
	private final boolean shouldCacheConstraints;

	private final EvaluationMode<?> mode;

	// map of (String => Boolean) caching whether a namespace is provided
	private final Map<String, Boolean> providedNamespaces = new java.util.HashMap<String, Boolean>();

	/**
	 * The "null" provider never provides any constraints. It is used as a
	 * placeholder for a provider which could not be initialized.
	 */
	private static class NullProvider extends AbstractConstraintProvider {
		/** Initializes me. */
		NullProvider() {
			super();
		}

		@Override
		public Collection<IModelConstraint> getBatchConstraints(EObject eObject,
				Collection<IModelConstraint> constraints) {
			return noOp(constraints);
		}

		@Override
		public Collection<IModelConstraint> getLiveConstraints(Notification notification,
				Collection<IModelConstraint> constraints) {
			return noOp(constraints);
		}

		private Collection<IModelConstraint> noOp(Collection<IModelConstraint> constraints) {
			return (constraints == null) ? new java.util.ArrayList<IModelConstraint>() : constraints;
		}
	}

	/**
	 * Initializes me with the XML extension point data describing a provider.
	 * 
	 * @param config my extension point data
	 * @throws CoreException on any error in initializing the descriptor
	 */
	public ProviderDescriptor(IConfigurationElement config) throws CoreException {
		this.myConfig = config;

		this.mode = getMode(config);

		Set<String> uriSet = new java.util.HashSet<String>();
		Map<String, StringMatcher> uriMatcherMap = new java.util.HashMap<String, StringMatcher>();

		// backward compatibility for the namespaceUri attribute
		String uri = config.getAttribute(XmlConfig.A_NAMESPACE_URI);
		if (uri != null) {
			uri = uri.trim();

			if (uri.indexOf('*') >= 0) { // known BMP code point
				// this is a URI matcher with wildcards. Key on the lowercase
				// to avoid case-insensitive duplicates
				uriMatcherMap.put(uri.toLowerCase(), new StringMatcher(uri, true, false));
			} else {
				uriSet.add(uri);
			}
		}

		IConfigurationElement[] pkgs = config.getChildren(XmlConfig.E_PACKAGE);
		for (IConfigurationElement element : pkgs) {
			uri = element.getAttribute(XmlConfig.A_NAMESPACE_URI);
			if (uri != null) {
				uri = uri.trim();

				if (uri.indexOf('*') >= 0) {
					// this is a URI matcher with wildcards. Key on the
					// lowercase to avoid case-insensitive duplicates
					uriMatcherMap.put(uri.toLowerCase(), new StringMatcher(uri, true, false));
				} else {
					uriSet.add(uri);
				}
			}
		}

		if (uriSet.isEmpty() && uriMatcherMap.isEmpty()) {
			CoreException e = new CoreException(new Status(IStatus.ERROR, EMFModelValidationPlugin.getPluginId(),
					EMFModelValidationStatusCodes.PROVIDER_NO_NAMESPACE_URI,
					EMFModelValidationStatusCodes.PROVIDER_NO_NAMESPACE_URI_MSG, null));

			Trace.throwing(AbstractConstraintProvider.class, "setInitializationData()", //$NON-NLS-1$
					e);

			throw e;
		}

		nsUris = uriSet.toArray(new String[uriSet.size()]);
		nsUriMatchers = uriMatcherMap.values().toArray(new StringMatcher[uriMatcherMap.size()]);

		String shouldCache = config.getAttribute(XmlConfig.A_CACHE);
		shouldCacheConstraints = (shouldCache == null) ? true : Boolean.valueOf(shouldCache).booleanValue();

		targets = config.getChildren(XmlConfig.E_TARGET);

		if ((targets != null) && (targets.length == 0)) {
			// without any target elements, I apply globally
			// (i.e., to any EObject) within my namespace
			targets = null;
		}

		Trace.trace(EMFModelValidationDebugOptions.PROVIDERS, "Parsed constraint provider: " + this);//$NON-NLS-1$
	}

	/**
	 * Obtains the evaluation mode of the provider, which indicates the mode of all
	 * constraints that it defines.
	 * 
	 * @return the evaluation mode, or {@link EvaluationMode#NULL} if the provider
	 *         provides constraints in mixed modes
	 */
	private EvaluationMode<?> getMode() {
		return mode;
	}

	/**
	 * Queries whether the provider that I represent can potentially provide any
	 * constraints for the specified operation.
	 * 
	 * @param operation a "get constraints" request
	 * @return whether the provider has any chance of providing constraints for this
	 *         context
	 */
	public boolean provides(IProviderOperation<? extends Collection<? extends IModelConstraint>> operation) {
		if (operation instanceof GetLiveConstraintsOperation) {
			return providesLiveConstraints(operation);
		} else if (operation instanceof GetBatchConstraintsOperation) {
			return providesBatchConstraints(operation);
		}

		return false;
	}

	/**
	 * Obtains the Eclipse extension configuration element from which I was
	 * initialized.
	 * 
	 * @return my source configuration element
	 */
	final IConfigurationElement getConfig() {
		return myConfig;
	}

	/**
	 * Queries whether the system should cache constraints retrieved from this
	 * provider.
	 * 
	 * @return whether my constraints should be cached
	 */
	public final boolean isCacheEnabled() {
		return shouldCacheConstraints;
	}

	// implements the interface method
	public boolean isCache() {
		return false;
	}

	// implements the interface method
	public boolean isXmlProvider() {
		String className = getConfig().getAttribute(XmlConfig.A_CLASS);

		return (className == null) || className.equals(XmlConstraintProvider.class.getName());
	}

	/**
	 * Obtains my provider. It is lazily instantiated to delay the loading of the
	 * contributing plug-in. If, for some reason, the provider cannot be
	 * initialized, then a "null provider" is returned which never does anything.
	 * 
	 * @return my provider
	 */
	public synchronized IModelConstraintProvider getProvider() {
		if (provider == null) {
			try {
				Trace.trace(EMFModelValidationDebugOptions.PROVIDERS, "Initializing provider: " + this);//$NON-NLS-1$

				if (getConfig().getAttribute(XmlConfig.A_CLASS) == null) {
					// the implicit default is the XML constraint provider
					provider = new XmlConstraintProvider();
					((XmlConstraintProvider) provider).setInitializationData(getConfig(), XmlConfig.A_CLASS, null);
				} else {
					provider = (IModelConstraintProvider) getConfig().createExecutableExtension(XmlConfig.A_CLASS);
				}

				Trace.trace(EMFModelValidationDebugOptions.PROVIDERS, "Provider initialized. ");//$NON-NLS-1$
			} catch (CoreException ce) {
				Trace.catching(getClass(), "getProvider", ce); //$NON-NLS-1$
				Log.errorMessage(EMFModelValidationStatusCodes.PROVIDER_NOT_INITED,
						EMFModelValidationStatusCodes.PROVIDER_NOT_INITED_MSG,
						getConfig().getAttribute(XmlConfig.A_CLASS), ce);

				Trace.trace(EMFModelValidationDebugOptions.PROVIDERS, "Provider is disabled. ");//$NON-NLS-1$

				provider = new NullProvider();
			}
		}

		return provider;
	}

	/**
	 * Determines whether I can provide live constraints for the specified
	 * <code>operation</code>.
	 * 
	 * @param operation a "get constraints" request
	 * @return <CODE>false</CODE> if my provider's configuration excludes the
	 *         possibility of it providing any constraints; <CODE>true</CODE>,
	 *         otherwise
	 */
	private boolean providesLiveConstraints(
			IProviderOperation<? extends Collection<? extends IModelConstraint>> operation) {

		Trace.entering(EMFModelValidationDebugOptions.PROVIDERS, getClass(), "providesLiveConstraints"); //$NON-NLS-1$

		boolean result = false;

		if (isLive()) {
			GetLiveConstraintsOperation op = (GetLiveConstraintsOperation) operation;

			if (targets == null) {
				// as a special case, the absence of any "target" elements
				// indicates that I apply to all elements, features, and events
				// in my namespace
				result = providerHandlesNamespace(op.getEObject());
			} else {
				EObject eObject = op.getEObject();

				for (IConfigurationElement next : targets) {
					if (isLive() && providerHandlesEObject(eObject, next)
							&& providerHandlesEvent(op.getEventType(), next)) {
						result = true;
						break;
					}
				}
			}
		}

		Trace.exiting(getClass(), "providesLiveConstraints", //$NON-NLS-1$
				result ? Boolean.TRUE : Boolean.FALSE);

		return result;
	}

	/**
	 * Determines whether I can provide batch constraints for the specified
	 * <code>operation</code>.
	 * 
	 * @param operation a "get constraints" request
	 * @return <CODE>false</CODE> if my provider's configuration excludes the
	 *         possibility of it providing any constraints; <CODE>true</CODE>,
	 *         otherwise
	 */
	private boolean providesBatchConstraints(
			IProviderOperation<? extends Collection<? extends IModelConstraint>> operation) {

		Trace.entering(EMFModelValidationDebugOptions.PROVIDERS, getClass(), "providesBatchConstraints"); //$NON-NLS-1$

		boolean result = false;

		if (isBatch()) {
			GetBatchConstraintsOperation op = (GetBatchConstraintsOperation) operation;

			if (targets == null) {
				// as a special case, the absence of any "target" elements
				// indicates that I apply to all elements, features, and events
				// in my namespace
				result = providerHandlesNamespace(op.getEObject());
			} else {
				EObject eObject = op.getEObject();

				for (IConfigurationElement next : targets) {
					if (providerHandlesEObject(eObject, next)) {
						result = true;
						break;
					}
				}
			}
		}

		Trace.exiting(getClass(), "providesBatchConstraints", //$NON-NLS-1$
				result ? Boolean.TRUE : Boolean.FALSE);

		return result;
	}

	/**
	 * Determines whether my provider can provide any constraints for an EMF object
	 * according to its type.
	 * 
	 * @param eObject an EMF object
	 * @param target  the data from a &lt;target&gt; element in the provider XML
	 *                which indicates one of the EMF types for which the provider
	 *                can supply constraints
	 * @return whether this EMF object's type may be recognized by the provider
	 */
	private boolean providerHandlesEObject(EObject eObject, IConfigurationElement target) {

		Trace.entering(EMFModelValidationDebugOptions.PROVIDERS, getClass(), "providerHandlesEObject"); //$NON-NLS-1$

		boolean result = providerHandlesNamespace(eObject);

		if (result) {
			String targetType = target.getAttribute(XmlConfig.A_CLASS);

			if (targetType != null) {
				result = false; // looking for particular target type

				for (int i = 0; !result && (i < nsUris.length); i++) {
					EClass eClass = ModelValidationService.findClass(nsUris[i], targetType);

					result = (eClass == null) ? false : eClass.isInstance(eObject);
				}
			}
		}

		Trace.exiting(getClass(), "providerHandlesEObject", //$NON-NLS-1$
				result ? Boolean.TRUE : Boolean.FALSE);

		return result;
	}

	/**
	 * Helper method to determine whether my provider handles the namespace in which
	 * an EMF object's type is defined.
	 * 
	 * @param eObject an EMF object
	 * @return whether this EMF object's metamodel is recognized by my provider
	 */
	private boolean providerHandlesNamespace(EObject eObject) {
		Trace.entering(EMFModelValidationDebugOptions.PROVIDERS, getClass(), "providerHandlesNamespace"); //$NON-NLS-1$

		EPackage epkg = eObject.eClass().getEPackage();
		String targetNsUri = epkg.getNsURI();

		Boolean result = providedNamespaces.get(targetNsUri);
		if (result == null) {
			result = providerHandlesNamespace(targetNsUri, targetNsUri);

			if (result == null) {
				// look for EPackages that this package extends
				Set<EPackage> extended = getExtendedEPackages(epkg);

				if (!extended.isEmpty()) {
					for (Iterator<EPackage> iter = extended.iterator(); iter.hasNext() && (result == null);) {

						EPackage next = iter.next();
						result = providerHandlesNamespace(targetNsUri, next.getNsURI());
					}
				}
			}

			if (result == null) {
				// cache a miss on this namespace
				result = Boolean.FALSE;
			}

			// cache the result for quick lookup next time
			providedNamespaces.put(targetNsUri, result);
		}

		Trace.exiting(getClass(), "providerHandlesNamespace", result); //$NON-NLS-1$

		return result.booleanValue();
	}

	/**
	 * Queries whether this provider has any constraints for the specified
	 * namespace.
	 * 
	 * @param originalTargetNamespace the namespace of the type of the object being
	 *                                validated
	 * @param namespace               a namespace for which, perhaps, this provider
	 *                                defines constraints
	 * @return <code>Boolean.TRUE</code> if this provider targets the specified
	 *         namespace; <code>null</code> otherwise (to trigger a continued
	 *         search)
	 */
	private Boolean providerHandlesNamespace(String originalTargetNamespace, String namespace) {
		boolean result = false;

		for (int i = 0; !result && (i < nsUris.length); i++) {
			result = namespace.equals(nsUris[i]);

			if (result && !namespace.equals(originalTargetNamespace)) {
				// we found a package that extends the declared target. Cache it
				addTargetNamespaceURI(originalTargetNamespace);
			}
		}

		for (int i = 0; !result && (i < nsUriMatchers.length); i++) {
			result = nsUriMatchers[i].match(namespace);

			if (result) {
				// we found a pattern match. Cache it
				addTargetNamespaceURI(originalTargetNamespace);
			}
		}

		return result ? Boolean.TRUE : null;
	}

	/**
	 * Adds the specified namespace URI to the list of namespaces that I target.
	 * This may be the result of a pattern match or it may be a namespace that has
	 * types that I target by inheritance.
	 * 
	 * @param namespaceURI a namespace that I target
	 */
	private synchronized void addTargetNamespaceURI(String namespaceURI) {
		String[] newURIs = new String[nsUris.length + 1];
		System.arraycopy(nsUris, 0, newURIs, 0, nsUris.length);
		newURIs[nsUris.length] = namespaceURI;
		nsUris = newURIs;
	}

	/**
	 * Obtains the set of all packages that the specified <code>epackage</code>
	 * extends, by having classifiers that extend some classifier(s) in those
	 * packages.
	 * 
	 * @param epackage a package
	 * @return all of the packages containing classifiers extended by this package's
	 *         classifiers, not including the original package
	 */
	private Set<EPackage> getExtendedEPackages(EPackage epackage) {
		Set<EPackage> result = new java.util.HashSet<EPackage>();

		getExtendedEPackages(epackage, result);
		result.remove(epackage);

		return result;
	}

	/**
	 * Recursive helper implementation of {@link #getExtendedEPackages(EPackage)}.
	 */
	private void getExtendedEPackages(EPackage epackage, Set<EPackage> result) {
		for (Object next : epackage.getEClassifiers()) {
			if (next instanceof EClass) {
				for (EClass zuper : ((EClass) next).getESuperTypes()) {
					EPackage nextPackage = zuper.getEPackage();

					if ((nextPackage != epackage) && !result.contains(nextPackage)) {
						result.add(nextPackage);
						getExtendedEPackages(nextPackage, result);
					}
				}
			}
		}
	}

	/**
	 * Helper method to determine whether my provider handles the specified event
	 * type.
	 * 
	 * @param eventType an EMF event type
	 * @param config    the data from an &lt;event&gt; element in the provider XML
	 *                  which indicates one of the EMF events for which the provider
	 *                  can supply constraints
	 * @return whether this EMF event type is recognized by my provider
	 */
	private boolean providerHandlesEvent(EMFEventType eventType, IConfigurationElement config) {

		Trace.entering(EMFModelValidationDebugOptions.PROVIDERS, getClass(), "providerHandlesEvent"); //$NON-NLS-1$

		IConfigurationElement[] events = XmlConfig.getEvents(config);
		boolean result = false;

		if (events.length == 0) {
			// it is implied that all events are supported
			result = true;
		} else {
			for (IConfigurationElement element : events) {
				final String eventName = element.getAttribute(XmlConfig.A_NAME);

				if (eventType.getName().equalsIgnoreCase(eventName)) {
					result = true;
					break;
				}
			}
		}

		Trace.exiting(getClass(), "providerHandlesEvent", //$NON-NLS-1$
				result ? Boolean.TRUE : Boolean.FALSE);

		return result;
	}

	/**
	 * Determines whether my provider provides live constraints.
	 * 
	 * @return <CODE>true</CODE> if my provider specifies live mode or no mode at
	 *         all
	 */
	private boolean isLive() {
		return getMode().isNull() || getMode().isLive();
	}

	/**
	 * Determines whether my provider provides batch constraints.
	 * 
	 * @return <CODE>true</CODE> if my provider specifies batch mode or no mode at
	 *         all
	 */
	private boolean isBatch() {
		return getMode().isNull() || getMode().isBatchOnly();
	}

	/**
	 * Parses the mode from the &lt;constraintProvider&gt; XML configuration
	 * element.
	 * 
	 * @param config the constraint provider element
	 * @return the evaluation mode of the constraint provider, or
	 *         {@link EvaluationMode#NULL} if it has none
	 */
	private EvaluationMode<?> getMode(IConfigurationElement config) {
		String result = config.getAttribute(XmlConfig.A_MODE);

		if (result == null) {
			return EvaluationMode.NULL;
		} else {
			return EvaluationMode.getInstance(result);
		}
	}

	// redefines the inherited method
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer(64);

		result.append("ConstraintProvider[nsUris="); //$NON-NLS-1$
		result.append(nsUris);
		result.append(", cache="); //$NON-NLS-1$
		result.append(isCacheEnabled());
		result.append(", mode="); //$NON-NLS-1$
		result.append(getMode());
		result.append(']');

		return result.toString();
	}
}