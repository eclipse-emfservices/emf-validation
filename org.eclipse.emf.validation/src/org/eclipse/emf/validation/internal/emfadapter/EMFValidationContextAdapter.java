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

import java.util.List;

import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;


/**
 * An adapter from the {@link IValidationContext} interface to
 * EMF API's validation context interface.  Essentially, I just shunt
 * {@link Diagnostic}s into the wrapped validation context.
 *
 * @author Christian W. Damus (cdamus)
 */
public class EMFValidationContextAdapter extends BasicDiagnostic {
	private IValidationContext adaptedContext;
	private Diagnostic lastStatus;
	
	/**
	 * Initializes me.
	 */
	public EMFValidationContextAdapter() {
		super(
			EMFModelValidationPlugin.getPluginId(),
			0,
			ValidationMessages.emfadapter_noMessage,
			null);
	}
	
	
	/* (non-Javadoc)
	 * Redefines the inherited method
	 */
	@Override
	public void add(Diagnostic diagnostic) {
		if (diagnostic.getSeverity() != Diagnostic.OK) {
			List<?> ddata = diagnostic.getData();
			
			if (ddata != null) {
				// add any EObjects that we find to our results
				
				for (Object next : ddata) {
					if (next instanceof EObject) {
						adaptedContext.addResult((EObject) next);
					}
				}
			}
		}
		
		// record the last status that was set by a constraint
		lastStatus = diagnostic;
	}
	
	/**
	 * Obtains the validation context that I adapt.
	 * 
	 * @return my validation context
	 */
	IValidationContext getAdaptedContext() {
		return adaptedContext;
	}
	
	/**
	 * Assigns me a new adapted validation context.
	 * 
	 * @param adaptedContext the new context that I adapt
	 */
	void setAdaptedContext(IValidationContext adaptedContext) {
		this.adaptedContext = adaptedContext;
		this.lastStatus = null;  // clear the last status to free memory
	}
	
	/**
	 * Obtains the last status added to me by the current constraint.
	 * 
	 * @return my last status
	 */
	Diagnostic getLastStatus() {
		return lastStatus;
	}
}
