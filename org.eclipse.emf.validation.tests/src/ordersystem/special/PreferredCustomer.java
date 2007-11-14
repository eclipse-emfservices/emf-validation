/**
 * <copyright>
 *
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */

package ordersystem.special;

import java.util.Date;

import ordersystem.Customer;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Preferred Customer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ordersystem.special.PreferredCustomer#getSince <em>Since</em>}</li>
 * </ul>
 * </p>
 *
 * @see ordersystem.special.SpecialPackage#getPreferredCustomer()
 * @model
 * @generated
 */
public interface PreferredCustomer extends Customer {
	/**
	 * Returns the value of the '<em><b>Since</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Since</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Since</em>' attribute.
	 * @see #setSince(Date)
	 * @see ordersystem.special.SpecialPackage#getPreferredCustomer_Since()
	 * @model dataType="ordersystem.JavaDate"
	 * @generated
	 */
	Date getSince();

	/**
	 * Sets the value of the '{@link ordersystem.special.PreferredCustomer#getSince <em>Since</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Since</em>' attribute.
	 * @see #getSince()
	 * @generated
	 */
	void setSince(Date value);

} // PreferredCustomer
