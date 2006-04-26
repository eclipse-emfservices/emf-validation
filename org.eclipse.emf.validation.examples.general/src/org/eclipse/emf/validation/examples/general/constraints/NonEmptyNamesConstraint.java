/**
 * <copyright>
 *
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */

package org.eclipse.emf.validation.examples.general.constraints;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.examples.extlibrary.Book;
import org.eclipse.emf.examples.extlibrary.Library;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;


/**
 * @author Chris McGee
 */
public class NonEmptyNamesConstraint
	extends AbstractModelConstraint {

	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.AbstractModelConstraint#validate(org.eclipse.emf.validation.IValidationContext)
	 */
	public IStatus validate(IValidationContext ctx) {
		EObject eObj = ctx.getTarget();
		EMFEventType eType = ctx.getEventType();
		
		// In the case of batch mode.
		if (eType == EMFEventType.NULL) {
			String name = null;
			if (eObj instanceof Library) {
				name = ((Library)eObj).getName();
			} else if (eObj instanceof Book) {
				name = ((Book)eObj).getTitle();
			}
			
			if (name == null || name.length() == 0) {
				return ctx.createFailureStatus(new Object[] {eObj.eClass().getName()});
			}
		// In the case of live mode.
		} else {
			Object newValue = ctx.getFeatureNewValue();
			
			if (newValue == null
				|| ((String)newValue).length() == 0) {
				return ctx.createFailureStatus(new Object[] {eObj.eClass().getName()});
			}
		}
		
		return ctx.createSuccessStatus();
	}

}
