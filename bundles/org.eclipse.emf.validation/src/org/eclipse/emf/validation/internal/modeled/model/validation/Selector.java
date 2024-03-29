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
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Selector</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Selector#getClassName
 * <em>Class Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getSelector()
 * @model
 * @generated
 * @since 1.4
 */
public interface Selector extends ClientContext {
	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getSelector_ClassName()
	 * @model required="true"
	 * @generated
	 */
	String getClassName();

	/**
	 * Sets the value of the
	 * '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Selector#getClassName
	 * <em>Class Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	void setClassName(String value);

} // Selector
