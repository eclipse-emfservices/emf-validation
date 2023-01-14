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
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Traversal Strategy</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.TraversalStrategy#getClass_
 * <em>Class</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.TraversalStrategy#getPackage
 * <em>Package</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getTraversalStrategy()
 * @model
 * @generated
 * @since 1.4
 */
public interface TraversalStrategy extends EObject {
	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Class</em>' attribute.
	 * @see #setClass(String)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getTraversalStrategy_Class()
	 * @model required="true"
	 * @generated
	 */
	String getClass_();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.validation.internal.modeled.model.validation.TraversalStrategy#getClass_
	 * <em>Class</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Class</em>' attribute.
	 * @see #getClass_()
	 * @generated
	 */
	void setClass(String value);

	/**
	 * Returns the value of the '<em><b>Package</b></em>' reference list. The list
	 * contents are of type {@link org.eclipse.emf.ecore.EPackage}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Package</em>' reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getTraversalStrategy_Package()
	 * @model
	 * @generated
	 */
	EList<EPackage> getPackage();

} // TraversalStrategy
