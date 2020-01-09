/******************************************************************************
 * Copyright (c) 2009 SAP AG and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    SAP AG - initial API and implementation 
 ****************************************************************************/
package org.eclipse.emf.validation.internal.modeled.model.validation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraints</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraints#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraints#getInclude <em>Include</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraints#getCategories <em>Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraints()
 * @model
 * @generated
 * @since 1.4
 */
public interface Constraints extends EObject {
	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraints_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<Constraint> getConstraints();

	/**
	 * Returns the value of the '<em><b>Include</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Include</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Include</em>' attribute list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraints_Include()
	 * @model
	 * @generated
	 */
	EList<String> getInclude();

	/**
	 * Returns the value of the '<em><b>Categories</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.validation.internal.modeled.model.validation.Category}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Categories</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Categories</em>' reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraints_Categories()
	 * @model
	 * @generated
	 */
	EList<Category> getCategories();

} // Constraints
