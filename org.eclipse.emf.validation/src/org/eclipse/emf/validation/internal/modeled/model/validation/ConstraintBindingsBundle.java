/**
 * ******************************************************************************
 *  * Copyright (c) 2009 SAP AG and others.
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *    SAP AG - initial API and implementation 
 *  ****************************************************************************
 *
 * $Id: ConstraintBindingsBundle.java,v 1.1 2009/08/28 11:39:49 bgruschko Exp $
 */
package org.eclipse.emf.validation.internal.modeled.model.validation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint Bindings Bundle</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle#getClientContexts <em>Client Contexts</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle#getBindings <em>Bindings</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintBindingsBundle()
 * @model
 * @generated
 */
public interface ConstraintBindingsBundle extends EObject {
	/**
	 * Returns the value of the '<em><b>Client Contexts</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.validation.internal.modeled.model.validation.ClientContext}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Client Contexts</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Client Contexts</em>' containment reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintBindingsBundle_ClientContexts()
	 * @model containment="true"
	 * @generated
	 */
	EList<ClientContext> getClientContexts();

	/**
	 * Returns the value of the '<em><b>Bindings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Bindings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Bindings</em>' containment reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintBindingsBundle_Bindings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Binding> getBindings();

} // ConstraintBindingsBundle
