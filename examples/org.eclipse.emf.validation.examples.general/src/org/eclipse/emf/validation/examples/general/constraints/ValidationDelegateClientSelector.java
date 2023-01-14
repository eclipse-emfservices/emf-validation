/**
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.examples.general.constraints;

import org.eclipse.emf.validation.model.IClientSelector;

/**
 * Selects constraints for the constraint binding when the
 * {@link org.eclipse.emf.validation.examples.general.actions.BatchValidationDelegate}
 * or the {@link LiveValidationContentAdapter} was the entry point into
 * validation.
 *
 * @author Chris McGee
 */
public class ValidationDelegateClientSelector

		// NOTE: This is _NOT_ a recommended approach to writing a client selector.
		// Suggested approaches:
		// -Check the resource of the EObject either by identity or by URI
		// as long as this resource is somehow unique to this application
		// -Check the identity of the resource set to ensure that it is some
		// private object
		// -Check the identity of the EObject itself to see if it belongs to
		// some private collection
		// -Check the EClass of the EObject but only if the metamodel is private
		// to this application and will not be used by other contexts

		implements IClientSelector {

	public static boolean running = false;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.emf.validation.model.IClientSelector#selects(java.lang.Object)
	 */
	@Override
	public boolean selects(Object object) {
		return running;
	}
}
