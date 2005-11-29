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

package org.eclipse.emf.validation.examples.constraints;

import org.eclipse.emf.validation.model.IClientSelector;


/**
 * Selects constraints for the constraint binding when the {@link org.eclipse.emf.validation.examples.actions.BatchValidationDelegate}
 *  was the entry point into validation.
 * 
 * @author Chris McGee
 */
public class ValidationDelegateClientSelector
	implements IClientSelector {

	public static boolean running = false;
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.validation.model.IClientSelector#selects(java.lang.Object)
	 */
	public boolean selects(Object object) {
		return running;
	}
}
