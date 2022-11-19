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
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Target</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Target#getFeature
 * <em>Feature</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Target#getEClass
 * <em>EClass</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getTarget()
 * @model abstract="true"
 * @generated
 * @since 1.4
 */
public interface Target extends EObject {
	/**
	 * Returns the value of the '<em><b>Feature</b></em>' reference list. The list
	 * contents are of type {@link org.eclipse.emf.ecore.EStructuralFeature}. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Feature</em>' reference list isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Feature</em>' reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getTarget_Feature()
	 * @model
	 * @generated
	 */
	EList<EStructuralFeature> getFeature();

	/**
	 * Returns the value of the '<em><b>EClass</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EClass</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>EClass</em>' reference.
	 * @see #setEClass(EClassifier)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getTarget_EClass()
	 * @model required="true"
	 * @generated
	 */
	EClassifier getEClass();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Target#getEClass
	 * <em>EClass</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>EClass</em>' reference.
	 * @see #getEClass()
	 * @generated
	 */
	void setEClass(EClassifier value);

} // Target
