/******************************************************************************
 * Copyright (c) 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.internal.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.IConstraintWithExtractions;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;


/**
 * Adapts a constraint supporting the extraction variable mechanism to the
 * core constraints API.  This class provides management of the extraction
 * variables and handling of the bindings; the constraint algorithm is delegated
 * either to the wrapped constraint implementation or to a reflectively-invoked
 * method (according to the concrete subclass).
 * 
 * @author Christian W. Damus (cdamus)
 */
abstract class AbstractConstraintWithExtractionsAdapter
	implements IConstraintWithExtractions, IModelConstraint {
	
	/** Parameter used to define extraction variables. */
	private static final String PARAMETER_EXTRACTION = "extraction"; //$NON-NLS-1$

	private final IConstraintDescriptor descriptor;

	/**
	 * The names of the extraction variables to be obtained from an invocation
	 * of the validation {@link #delegateMethod}.
	 */
	private final List extractions = new java.util.ArrayList();

	/**
	 * Initializes me to with my constraint <code>descriptor</code>.  I parse
	 * the declared extraction variables from it.
	 * 
	 * @param descriptor the constraint descriptor.  Must not be <code>null</code>
	 */
	AbstractConstraintWithExtractionsAdapter(
			IXmlConstraintDescriptor descriptor) {
		
		this.descriptor = descriptor;
		
		initializeExtractions(descriptor);
	}

	/**
	 * I delegate the validation to my encapsulated method.
	 */
	public IStatus validate(IValidationContext ctx) {
		Map bindings = new HashMap();

		boolean success = validate(ctx, bindings);

		if (success) {
			return ctx.createSuccessStatus();
		} else {
			return ctx.createFailureStatus(
					extractBindings(ctx.getTarget(), bindings));
		}
	}
	
	/**
	 * Parses my extraction variable definitions from the specified
	 * <code>desc</code>riptor.
	 * 
	 * @param desc my constraint descriptor
	 */
	private void initializeExtractions(IXmlConstraintDescriptor desc) {
		String[] extractionVars = XmlConfig.getParameterValues(
			desc.getConfig(),
			PARAMETER_EXTRACTION);
	
		for (int i = 0; i < extractionVars.length; i++) {
			extractions.add(extractionVars[i]);
		}
	}
	/**
	 * Obtains my extraction variable names.
	 * 
	 * @return my extraction variables, as an unmodifiable list of strings
	 */
	private List getExtractions() {
		return extractions;
	}

	/**
	 * Extracts the variable bindings from the map filled by my delegate method.
	 * 
	 * @param eObject the first (index zero) binding is always the target
	 *    of the validation operation
	 * @param bindings the bindings provided by my delegate method
	 * @return an array containing the <code>eObject</code> followed by the
	 *    values of the extraction variables provided by my delegate method 
	 */
	private Object[] extractBindings(EObject eObject, Map bindings) {
		Collection vars = getExtractions();

		Object[] result = new Object[vars.size() + 1];

		int index = 0;

		result[index++] = eObject;

		for (Iterator iter = vars.iterator(); iter.hasNext(); ) {
			result[index++] = bindings.get(iter.next());
		}

		return result;
	}
	
	/* (non-Javadoc)
	 * Implements the interface method.
	 */
	public IConstraintDescriptor getDescriptor() {
		return descriptor;
	}
}
