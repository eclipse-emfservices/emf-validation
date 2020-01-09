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
 ****************************************************************************/
package org.eclipse.emf.validation.internal.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;

import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IModelConstraintProvider;

/**
 * <p>
 * Caches constraints provided by {@link IModelConstraintProvider}s.
 * Constraints are cached by EMF class, as follows:
 * <ul>
 *     <li>One bucket for each {@link EClass}, containing:
 *         <ul>
 *             <li>the batch constraints for the <code>EClass</code></li>
 *             <li>the live constraints for the <code>EClass</code>:
 *                 <ul>
 *                     one bucket for each triggering event type<li></li>
 *                 </ul></li>
 *             <li>the feature constraints for the <code>EClass</code>:
 *                 <ul>
 *                     one bucket for each {@link EStructuralFeature}<li></li>
 *                 </ul></li>
 *         </ul></li>
 * </ul>
 * </p> 
 * 
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintCache implements IModelConstraintProvider {
	/**
	 * Cache bucket key for a non-feature used for notifications that are not
	 * related to any specific feature).
	 */
	private static final String NOT_A_FEATURE_NAME = "$none$"; //$NON-NLS-1$
	
	/**
	 * Mapping of {@link EClass} ==&gt; {@link EClassBucket}.  The map is a
	 * weak map to avoid interfering with garbage-collection of EMF metamodels
	 * (and to clean up the cache when metamodels disappear!).
	 */
	private final Map<EClass, EClassBucket> buckets =
		new java.util.WeakHashMap<EClass, EClassBucket>();
	
	/** The cacheable providers. */
	private final Collection<IProviderDescriptor> providers =
		new java.util.ArrayList<IProviderDescriptor>();
	
	/**
	 * A container for the constraints provided by all cacheable providers
	 * for a particular EMF type ({@link EClass}).
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private static class EClassBucket {
		private Collection<IModelConstraint> batchConstraints;
		private final Map<EMFEventType, Map<String, Collection<IModelConstraint>>>
		liveConstraints = new java.util.HashMap<EMFEventType, Map<String, Collection<IModelConstraint>>>();
		
		/**
		 * Initializes me.
		 */
		EClassBucket() {
			super();
		}
		
		/**
		 * Obtains the batch constraints for my EMF class.
		 * 
		 * @return my batch constraints, or <code>null</code> if they have not
		 *    yet been retrieved from my registered providers
		 */
		Collection<IModelConstraint> getBatchConstraints() {
			return batchConstraints;
		}
		
		/**
		 * Assigns the batch constraints for my EMF class.
		 * 
		 * @param constraints the batch constraints
		 */
		void cacheBatchConstraints(Collection<IModelConstraint> constraints) {
			batchConstraints = new java.util.ArrayList<IModelConstraint>(constraints);
		}
		
		/**
		 * Obtains the live constraints for my EMF class, for the specified
		 * <code>eventType</code> and feature name.
		 * 
		 * @param eventType the EMF notification event type
		 * @param featureName the name of the feature that produced the
		 *     notification (may be <code>null</code> if the notification was
		 *     not a feature change)
		 * @return the corresponding constraints, or <code>null</code> if they
		 *     have not yet been retrieved from my registered providers
		 */
		Collection<IModelConstraint> getLiveConstraints(EMFEventType eventType, String featureName) {
			if (featureName == null) {
				featureName = NOT_A_FEATURE_NAME;
			}
			
			Map<String, Collection<IModelConstraint>> constraintMap =
				liveConstraints.get(eventType);
			
			if (constraintMap != null) {
				return constraintMap.get(featureName);
			} else {
				return null;
			}
		}
		
		/**
		 * Assigns the live constraints for my EMF class, for the specified
		 * <code>eventType</code> and feature name.
		 * 
		 * @param eventType the EMF notification event type
		 * @param featureName the name of the feature that produced the
		 *     notification (may be <code>null</code> if the notification was
		 *     not a feature change)
		 * @param constraints the corresponding constraints
		 */
		void cacheLiveConstraints(
				EMFEventType eventType,
				String featureName,
				Collection<IModelConstraint> constraints) {
			
			if (featureName == null) {
				featureName = NOT_A_FEATURE_NAME;
			}
	
			Map<String, Collection<IModelConstraint>> constraintMap =
				liveConstraints.get(eventType);
	
			if (constraintMap == null) {
				constraintMap = new java.util.HashMap<String, Collection<IModelConstraint>>();
				liveConstraints.put(eventType, constraintMap);
			}
			
			constraintMap.put(
					featureName,
					new java.util.ArrayList<IModelConstraint>(constraints));
		}
		
		/**
		 * Replaces a constraint in the bucket with an alternative
		 * implementation.
		 * 
		 * @param oldConstraint the constraint to be replaced
		 * @param newConstraint the new constraint to replace it
		 */
		void replace(IModelConstraint oldConstraint, IModelConstraint newConstraint) {
			// replace in the batch constraints, if appropriate
			if ((batchConstraints != null) && batchConstraints.remove(oldConstraint)) {
				batchConstraints.add(newConstraint);
			}
			
			// replace in the live constraints, if appropriate
			for (Map<String, Collection<IModelConstraint>> next : liveConstraints.values()) {
				for (Collection<IModelConstraint> constraints : next.values()) {
					if ((constraints != null) && constraints.remove(oldConstraint)) {
						constraints.add(newConstraint);
					}
				}
			}
		}
	}
	
	/**
	 * Initializes me.
	 */
	public ConstraintCache() {
		super();
	}
	
	/**
	 * Obtains a descriptor that can adequately represent me.
	 * 
	 * @return my descriptor
	 */
	public IProviderDescriptor getDescriptor() {
		return new IProviderDescriptor() {
			// the cache is assumed to always have an answer
			public boolean provides(
					IProviderOperation<? extends Collection<? extends IModelConstraint>> operation) {
				return true;
			}

			// the cache is not cache-enabled, because it is the cache!
			public boolean isCacheEnabled() {
				return false;
			}
			
			// yes, I am the cache
			public boolean isCache() {
				return true;
			}

			// I am definitely not an XML constraint provider
			public boolean isXmlProvider() {
				return false;
			}
			
			// the cache descriptor describes the cache
			public IModelConstraintProvider getProvider() {
				return ConstraintCache.this;
			}};
	}
	
	/**
	 * Obtains the collection of providers whose constraints I cache.
	 * 
	 * @return the cached providers
	 */
	public Collection<IProviderDescriptor> getProviders() {
		return providers;
	}

	/**
	 * Adds a constraint provider to the cache.
	 * 
	 * @param provider the provider (must be
	 *     {@linkplain IProviderDescriptor#isCacheEnabled cacheable})
	 */
	public void addProvider(IProviderDescriptor provider) {
		assert provider != null;
		assert provider.isCacheEnabled();
		
		getProviders().add(provider);
	}
	
	/**
	 * Obtains the cache bucket for the specified EMF type.
	 * 
	 * @param clazz the EMF type
	 * @return the corresponding bucket
	 */
	private EClassBucket getBucket(EClass clazz) {
		EClassBucket result = buckets.get(clazz);
		
		if (result == null) {
			result = new EClassBucket();
			buckets.put(clazz, result);
		}
		
		return result;
	}

	/**
	 * Executes the specified <code>operation</code> on all of my providers.
	 * 
	 * @param operation the operation to execute
	 * @return the constraints retrieved by the operation
	 */
	private Collection<IModelConstraint> execute(AbstractGetConstraintsOperation operation) {
		for (Iterator<IProviderDescriptor> iter = getProviders().iterator(); iter.hasNext(); ) {
			IProviderDescriptor next = iter.next();

			if (next.provides(operation)) {
				try {
					operation.execute(next.getProvider());
				} catch (RuntimeException e) {
					Trace.catching(getClass(), "execute", e); //$NON-NLS-1$
					Log.l7dWarning(
							EMFModelValidationStatusCodes.PROVIDER_FAILURE,
							EMFModelValidationStatusCodes.PROVIDER_FAILURE_MSG,
							e);
					
					iter.remove();  // don't try the offending provider, again
				}
			}
		}
		
		return operation.getUnfilteredConstraints();
	}

	// implements the interface method
	public Collection<IModelConstraint> getLiveConstraints(
			Notification notification,
			Collection<IModelConstraint> constraints) {
		
		assert notification != null;
		
		Collection<IModelConstraint> result = constraints;

		if (result == null) {
			result = new java.util.ArrayList<IModelConstraint>();
		}

		if (notification.getNotifier() instanceof EObject) {
			final EObject eObject = (EObject)notification.getNotifier();
			final EMFEventType eventType = EMFEventType.getInstance(
					notification.getEventType());
			
			String featureName = null;
			if (notification.getFeature() instanceof EStructuralFeature) {
				featureName = ((EStructuralFeature)notification.getFeature())
						.getName();
			}
			
			EClassBucket bucket = getBucket(eObject.eClass());
			Collection<IModelConstraint> cached = bucket.getLiveConstraints(
				eventType, featureName);
			
			if (cached == null) {
				if (Trace.shouldTrace(EMFModelValidationDebugOptions.CACHE)) {
					Trace.trace(
							EMFModelValidationDebugOptions.CACHE,
							"Cache missed live constraints for: " //$NON-NLS-1$
								+ Trace.toString(new Object[] {
											qualifiedName(eObject.eClass()),
											eventType,
											featureName}));
				}
				
				// not cached, yet?  Ask my providers
				GetLiveConstraintsOperation operation =
					new GetLiveConstraintsOperation();
				operation.setNotification(notification);
				
				cached = execute(operation);
				bucket.cacheLiveConstraints(
						eventType,
						featureName,
						cached);
			}
			
			result.addAll(cached);
		}
		
		return result;
	}

	// implements the interface method
	public Collection<IModelConstraint> getBatchConstraints(
			EObject eObject,
			Collection<IModelConstraint> constraints) {

		Collection<IModelConstraint> result = constraints;

		if (result == null) {
			result = new java.util.ArrayList<IModelConstraint>();
		}

		EClassBucket bucket = getBucket(eObject.eClass());
		Collection<IModelConstraint> cached = bucket.getBatchConstraints();
		if (cached == null) {
			if (Trace.shouldTrace(EMFModelValidationDebugOptions.CACHE)) {
				Trace.trace(
						EMFModelValidationDebugOptions.CACHE,
						"Cache missed batch constraints for: " //$NON-NLS-1$
							+ qualifiedName(eObject.eClass()));
			}
			
			// not cached, yet?  Ask my providers
			GetBatchConstraintsOperation operation =
				new GetBatchConstraintsOperation(
					false);  // must use false to cache live constraints also

			operation.setTarget(eObject);
			
			cached = execute(operation);
			bucket.cacheBatchConstraints(cached);
		}

		result.addAll(bucket.getBatchConstraints());
		
		return result;
	}
	
	/**
	 * Obtains the fully-qualified name (with namespace URI) of an EClass.
	 * 
	 * @param eClass the EClass
	 * @return the fully-qualified name
	 */
	private String qualifiedName(EClass eClass) {
		StringBuffer result = new StringBuffer(32);
		
		appendQualifiedName(eClass.getEPackage(), result);
		result.append(eClass.getName());
		
		return result.toString();
	}
	
	/**
	 * Appends an EMF package's fully-qualified name to a string
	 * <code>buf</code>fer, including the namespace URI.
	 * 
	 * @param ePackage the EMF package
	 * @param buf the String buffer to append its name to
	 */
	private void appendQualifiedName(EPackage ePackage, StringBuffer buf) {
		if (ePackage.getESuperPackage() != null) {
			appendQualifiedName(ePackage.getESuperPackage(), buf);
		} else {
			buf.append(ePackage.getNsURI());
			buf.append('/');
		}
		
		buf.append(ePackage.getName());
		buf.append('.');
	}
	
	/**
	 * Replaces a constraint in the cache with an alternative implementation.
	 * 
	 * @param oldConstraint the constraint to be replaced
	 * @param newConstraint the new constraint to replace it
	 */
	public void replace(IModelConstraint oldConstraint, IModelConstraint newConstraint) {
		if (Trace.shouldTrace(EMFModelValidationDebugOptions.CACHE)) {
			Trace.trace(
					EMFModelValidationDebugOptions.CACHE,
					"Cache replacing: " + oldConstraint //$NON-NLS-1$
					+ " with: " + newConstraint); //$NON-NLS-1$
		}
		
		// ask each bucket to replace the old constraint with the new, if
		// that constraint is in that bucket
		for (EClassBucket next : buckets.values()) {
			next.replace(oldConstraint, newConstraint);
		}
	}
}
