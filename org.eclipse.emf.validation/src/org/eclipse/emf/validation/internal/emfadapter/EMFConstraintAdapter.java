/******************************************************************************
 * Copyright (c) 2004, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.internal.emfadapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.internal.util.TextUtils;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;


/**
 * A constraint implementation that forwards (adapts) validation to the
 * EMF API's validation methods.
 *
 * @author Christian W. Damus (cdamus)
 */
public class EMFConstraintAdapter implements IModelConstraint {
	/**
	 * A canonical mapping of Aurora validation contexts to corresponding
	 * "context maps" passed to EMF constraints.  The mapping is canonical, as
	 * the EMF context serves a similar function to our validation
	 * context, but we don't want to expose the map that is implemented in
	 * the Aurora validation context.  The mapping is weak so that the EMF
	 * context can be GCed when the Aurora context is (both contexts only
	 * enduring as long as the validation operation).  There is no danger of
	 * elements in the EMF context holding references to our context,
	 * since the EMF APIs have no access to it and there is nothing else in
	 * this adapter layer maintains a reference to it.
	 */
	private static final Map<IValidationContext, Map<Object, Object>> contextMapCache =
		new java.util.WeakHashMap<IValidationContext, Map<Object,Object>>();
	
	private final IConstraintDescriptor descriptor;
	private final Method validationMethod;
	
	// store as an array for convenient passing to the delegate
	//    validation method in the EMF API reflectively
	private final Object[] validationArgs = new Object[2];
	
	/**
	 * Initializes me with my descriptor and the method in the EMF API that I
	 * delegate to.
	 * 
	 * @param descriptor my descriptor
	 * @param validationMethod the method on the EMF interface that implements
	 *      my constraint logic.  This must not be <code>null</code>, and must
	 *      be applicable to all element types that I target
	 */
	public EMFConstraintAdapter(
			IConstraintDescriptor descriptor,
			Method validationMethod) {
		this.descriptor = descriptor;		
		this.validationMethod = validationMethod;
		
		validationArgs[0] = new EMFValidationContextAdapter();
		validationArgs[1] = null;
	}

	// implements the interface method
	public IStatus validate(IValidationContext ctx) {
		EMFValidationContextAdapter ctxAdapter =
			(EMFValidationContextAdapter)validationArgs[0];
		
		ctxAdapter.setAdaptedContext(ctx);
		validationArgs[1] = getEMFContextFor(ctx);
		
		try {
			boolean success = ((Boolean)validationMethod.invoke(
				ctx.getTarget(),
				validationArgs)).booleanValue();
			
			return success ? ctx.createSuccessStatus() : fail(ctxAdapter);
		} catch (IllegalAccessException e) {
			ctx.disableCurrentConstraint(e);
			return disabledInfo(ctx, e);
		} catch (InvocationTargetException e) {
			ctx.disableCurrentConstraint(e);
			return disabledInfo(ctx, e);
		} finally {
			// clear this reference so that, if I am the last constraint to
			//    be evaluated in the current validation operation, the
			//    validation context can be GCed ASAP to free the EMF map
			ctxAdapter.setAdaptedContext(null);
			validationArgs[1] = null;
		}
	}
	
	
	/* (non-Javadoc)
	 * Implements the interface method.
	 */
	public IConstraintDescriptor getDescriptor() {
		return descriptor;
	}
	
	/**
	 * Creates a failure status from the information available from the
	 * EMF validation context adapter.  Note that the status details are
	 * as defined in the Aurora validation XML, not in the Status object
	 * provided by the EMF.
	 * 
	 * @param ctxAdapter the validation context adapter
	 * @return the failure status
	 */
	private IStatus fail(EMFValidationContextAdapter ctxAdapter) {
		final IValidationContext ctx = ctxAdapter.getAdaptedContext();
		
		List<EObject> resultLocus = new java.util.ArrayList<EObject>(ctx.getResultLocus());
		resultLocus.remove(ctx.getTarget());
		
		Diagnostic status = ctxAdapter.getLastStatus();
		
		// The message arguments supported are:
		//   0 - the error message from EMF (which should include the target
		//       element name)
		//   1 - the result locus minus the target(as a collection of
		//       model elements)
		return ctx.createFailureStatus(
				(status == null) ? null : status.getMessage(),
				resultLocus);
	}
	
	/**
	 * Constructs an informational status object indicating that the constraint
	 * is disabled because of a run-time exception.
	 * 
	 * @param ctx the current validation context
	 * @param e the run-time exception that was thrown by the
	 *        EMF validation method
	 * @return a suitable informational status
	 */
	private IStatus disabledInfo(IValidationContext ctx, Throwable e) {
		IStatus result = new ConstraintStatus(
			this,
			ctx.getTarget(),
			IStatus.INFO,
			getDescriptor().getStatusCode(),
			EMFModelValidationPlugin.getMessage(
				ValidationMessages.emfadapter_disabled_WARN_,
				getDescriptor().getName()),
			Collections.<EObject>emptySet());
		
		EMFModelValidationPlugin.getPlugin().getLog().log(
			new Status(
				IStatus.WARNING,
				result.getPlugin(),
				result.getCode(),
				result.getMessage(),
				e));
		
		return result;
	}
	
	/**
	 * Returns the (lazily instantiated and stored) cached EMF context map
	 * corresponding to the specified Aurora validation context.
	 * 
	 * @param ctx our validation context
	 * @return the corresponding EMF validation context
	 */
	private static Map<Object, Object> getEMFContextFor(IValidationContext ctx) {
		Map<Object, Object> result = contextMapCache.get(ctx);
		
		if (result == null) {
			result = new java.util.HashMap<Object, Object>();
			
			// add a substitution label provider for good measure
			result.put(
				EValidator.SubstitutionLabelProvider.class,
				SubstitutionProvider.INSTANCE);
			
			contextMapCache.put(ctx, result);
		}
		
		return result;
	}
	
	/**
	 * A substitution label provider to include in the context map for EMF
	 * validation.  Provides labels from the item providers, where possible.
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private static class SubstitutionProvider implements EValidator.SubstitutionLabelProvider {
		static final SubstitutionProvider INSTANCE = new SubstitutionProvider();
		
		/** Cannot be instantiated by clients. */
		private SubstitutionProvider() {
			super();
		}
		
		public String getObjectLabel(EObject eObject) {
			return TextUtils.getText(eObject);
		}

		public String getFeatureLabel(EStructuralFeature eStructuralFeature) {
			return eStructuralFeature.getName();
		}

		public String getValueLabel(EDataType eDataType, Object value) {
			return EcoreUtil.convertToString(eDataType, value);
		}
	}
}
