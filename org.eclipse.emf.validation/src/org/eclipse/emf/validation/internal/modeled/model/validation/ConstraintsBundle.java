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
 * A representation of the model object '<em><b>Constraints Bundle</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getProviders <em>Providers</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getCategories <em>Categories</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getConstraintBindingsBundles <em>Constraint Bindings Bundles</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getParsers <em>Parsers</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getMessageBundlePath <em>Message Bundle Path</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintsBundle()
 * @model
 * @generated
 * @since 1.4
 */
public interface ConstraintsBundle extends EObject {
	/**
	 * Returns the value of the '<em><b>Providers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Providers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Providers</em>' containment reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintsBundle_Providers()
	 * @model containment="true"
	 * @generated
	 */
	EList<ConstraintProvider> getProviders();

	/**
	 * Returns the value of the '<em><b>Categories</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.validation.internal.modeled.model.validation.Category}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Categories</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Categories</em>' containment reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintsBundle_Categories()
	 * @model containment="true"
	 * @generated
	 */
	EList<Category> getCategories();

	/**
	 * Returns the value of the '<em><b>Constraint Bindings Bundles</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraint Bindings Bundles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraint Bindings Bundles</em>' containment reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintsBundle_ConstraintBindingsBundles()
	 * @model containment="true"
	 * @generated
	 */
	EList<ConstraintBindingsBundle> getConstraintBindingsBundles();

	/**
	 * Returns the value of the '<em><b>Parsers</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.validation.internal.modeled.model.validation.Parser}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parsers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parsers</em>' containment reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintsBundle_Parsers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parser> getParsers();

	/**
	 * Returns the value of the '<em><b>Message Bundle Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Message Bundle Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Message Bundle Path</em>' attribute.
	 * @see #isSetMessageBundlePath()
	 * @see #unsetMessageBundlePath()
	 * @see #setMessageBundlePath(String)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintsBundle_MessageBundlePath()
	 * @model unsettable="true"
	 * @generated
	 */
	String getMessageBundlePath();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getMessageBundlePath <em>Message Bundle Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Message Bundle Path</em>' attribute.
	 * @see #isSetMessageBundlePath()
	 * @see #unsetMessageBundlePath()
	 * @see #getMessageBundlePath()
	 * @generated
	 */
	void setMessageBundlePath(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getMessageBundlePath <em>Message Bundle Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMessageBundlePath()
	 * @see #getMessageBundlePath()
	 * @see #setMessageBundlePath(String)
	 * @generated
	 */
	void unsetMessageBundlePath();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getMessageBundlePath <em>Message Bundle Path</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Message Bundle Path</em>' attribute is set.
	 * @see #unsetMessageBundlePath()
	 * @see #getMessageBundlePath()
	 * @see #setMessageBundlePath(String)
	 * @generated
	 */
	boolean isSetMessageBundlePath();

} // ConstraintsBundle
