/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.service;

import org.eclipse.emf.validation.internal.util.DisabledConstraint;
import org.eclipse.emf.validation.internal.util.XmlConstraintFactory;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;

/**
 * <p>
 * A constraint factory creates constraint implementations from descriptors.
 * The intent is to support registration of custom factory implementations,
 * but currently, the only implementation is the {@link XmlConstraintFactory}.
 * </p>
 * <p>
 * This class is not intended to be used outside of the validation framework.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public abstract class ConstraintFactory {
	private static ConstraintFactory instance = new XmlConstraintFactory();

	/**
	 *  Initializes me.
	 */
	protected ConstraintFactory() {
		super();
	}

	/**
	 * Obtains the currently registered factory instance.
	 * 
	 * @return the constraint factory instance
	 */
	public static ConstraintFactory getInstance() {
		return instance;
	}

	/**
	 * Creates the constraint represented by the specified
	 * <code>descriptor</code>.  This method never fails to return a valid
	 * constraint implementation (though it may be a proxy for a disabled 
	 * constraint if the <code>descriptor</code> is invalid).  This method
	 * delegates to the superclass implementation of the
	 * {@link #createConstraint} method.
	 *  
	 * @param descriptor the constraint descriptor
	 * @return the corresponding constraint implementation
	 * @see #createConstraint
	 * 
	 * @deprecated Use the {@link #newConstraint(IConstraintDescriptor)}
	 *   method, instead
	 */
	public final IModelConstraint newConstraint(
			IXmlConstraintDescriptor descriptor) {
		if (descriptor.isError()) {
			return new DisabledConstraint(
					descriptor,
					descriptor.getException());
		} else {
			return createConstraint(descriptor);
		}
	}
	
	/**
	 * Implemented by subclasses to do the hard work of creating a constraint.
	 * 
	 * @param descriptor a descriptor of the constraint to be created
	 * @return the appropriate implementation of the constraint
	 * 
	 * @deprecated Use the {@link #createConstraint(IConstraintDescriptor)}
	 *   method, instead
	 */
	protected abstract IModelConstraint createConstraint(
			IXmlConstraintDescriptor descriptor);

	/**
	 * Creates the constraint represented by the specified
	 * <code>descriptor</code>.  This method never fails to return a valid
	 * constraint implementation (though it may be a proxy for a disabled 
	 * constraint if the <code>descriptor</code> is invalid).  This method
	 * delegates to the superclass implementation of the
	 * {@link #createConstraint} method.
	 *  
	 * @param descriptor the constraint descriptor
	 * @return the corresponding constraint implementation
	 * @see #createConstraint
	 * 
	 * @since 1.1
	 */
	public final IModelConstraint newConstraint(
			IConstraintDescriptor descriptor) {
		if (descriptor.isError()) {
			return new DisabledConstraint(
					descriptor,
					descriptor.getException());
		} else {
			return createConstraint(descriptor);
		}
	}
	
	/**
	 * Implemented by subclasses to do the hard work of creating a constraint.
	 * 
	 * @param descriptor a descriptor of the constraint to be created
	 * @return the appropriate implementation of the constraint
	 * 
	 * @since 1.1
	 */
	protected abstract IModelConstraint createConstraint(
			IConstraintDescriptor descriptor);
}
