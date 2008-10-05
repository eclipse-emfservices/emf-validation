/******************************************************************************
 * Copyright (c) 2003, 2008 IBM Corporation, Zeligsoft Inc., and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 *    Zeligsoft - Bug 249690
 ****************************************************************************/


package org.eclipse.emf.validation.model;

import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

/**
 * <p>
 * A specialization of the Eclipse {@link IStatus} interface which provides
 * additional information about the success or failure of the validation of
 * a particular constraint on a particular model element.
 * </p><p>
 * This interface should not be implemented outside of the Validation Framework.
 * </p>
 *
 * @author Christian W. Damus (cdamus)
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IConstraintStatus extends IStatus {
	/**
	 * Obtains the constraint which either succeeded or failed, according to
	 * what I have to say.
	 * 
	 * @return my constraint
	 */
	IModelConstraint getConstraint();
	
	/**
	 * Obtains the target object, on which the constraint was evaluated.
	 * 
	 * @return the target of the validation operation
	 */
	EObject getTarget();
	
	/**
	 * Obtains the objects which are involved in the failure of the constraint.
	 * These are objects which caused the constraint to fail, and would be
	 * useful to link to from some display of the error message.
	 * 
	 * @return the objects which caused the constraint to fail.  In cases of
	 *     successful validation, the result is an empty collection.  The result
	 *     is never <code>null</code>
	 */
	Set<EObject> getResultLocus();
}
