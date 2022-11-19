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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Custom
 * Event</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.CustomEvent#getName
 * <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getCustomEvent()
 * @model
 * @generated
 * @since 1.4
 */
public interface CustomEvent extends Target {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getCustomEvent_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.validation.internal.modeled.model.validation.CustomEvent#getName
	 * <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // CustomEvent
