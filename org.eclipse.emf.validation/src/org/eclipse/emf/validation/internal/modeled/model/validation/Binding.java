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
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Binding</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getClientContexts
 * <em>Client Contexts</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getConstraints
 * <em>Constraints</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getExcludedConstraints
 * <em>Excluded Constraints</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getCategories
 * <em>Categories</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getExcludedCategories
 * <em>Excluded Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getBinding()
 * @model
 * @generated
 * @since 1.4
 */
public interface Binding extends EObject {
	/**
	 * Returns the value of the '<em><b>Client Contexts</b></em>' reference list.
	 * The list contents are of type
	 * {@link org.eclipse.emf.validation.internal.modeled.model.validation.ClientContext}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Client Contexts</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Client Contexts</em>' reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getBinding_ClientContexts()
	 * @model
	 * @generated
	 */
	EList<ClientContext> getClientContexts();

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' reference list. The
	 * list contents are of type
	 * {@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraints</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Constraints</em>' reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getBinding_Constraints()
	 * @model
	 * @generated
	 */
	EList<Constraint> getConstraints();

	/**
	 * Returns the value of the '<em><b>Excluded Constraints</b></em>' reference
	 * list. The list contents are of type
	 * {@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Excluded Constraints</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Excluded Constraints</em>' reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getBinding_ExcludedConstraints()
	 * @model
	 * @generated
	 */
	EList<Constraint> getExcludedConstraints();

	/**
	 * Returns the value of the '<em><b>Categories</b></em>' reference list. The
	 * list contents are of type
	 * {@link org.eclipse.emf.validation.internal.modeled.model.validation.Category}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Categories</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Categories</em>' reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getBinding_Categories()
	 * @model
	 * @generated
	 */
	EList<Category> getCategories();

	/**
	 * Returns the value of the '<em><b>Excluded Categories</b></em>' reference
	 * list. The list contents are of type
	 * {@link org.eclipse.emf.validation.internal.modeled.model.validation.Category}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Excluded Categories</em>' reference list isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Excluded Categories</em>' reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getBinding_ExcludedCategories()
	 * @model
	 * @generated
	 */
	EList<Category> getExcludedCategories();

} // Binding
