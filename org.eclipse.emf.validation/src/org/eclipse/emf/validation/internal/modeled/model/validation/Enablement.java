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
 * $Id: Enablement.java,v 1.1 2009/08/28 11:39:50 bgruschko Exp $
 */
package org.eclipse.emf.validation.internal.modeled.model.validation;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Enablement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Enablement#getDomExpression <em>Dom Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getEnablement()
 * @model
 * @generated
 */
public interface Enablement extends ClientContext {
	/**
	 * Returns the value of the '<em><b>Dom Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Dom Expression</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Dom Expression</em>' attribute.
	 * @see #setDomExpression(String)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getEnablement_DomExpression()
	 * @model
	 * @generated
	 */
	String getDomExpression();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Enablement#getDomExpression <em>Dom Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Dom Expression</em>' attribute.
	 * @see #getDomExpression()
	 * @generated
	 */
	void setDomExpression(String value);

} // Enablement
