/**
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package ordersystem.special;

import java.util.Date;

import ordersystem.Product;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Limited
 * Edition Product</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link ordersystem.special.LimitedEditionProduct#getAvailableUntil
 * <em>Available Until</em>}</li>
 * </ul>
 * </p>
 *
 * @see ordersystem.special.SpecialPackage#getLimitedEditionProduct()
 * @model
 * @generated
 */
public interface LimitedEditionProduct extends Product {
	/**
	 * Returns the value of the '<em><b>Available Until</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Available Until</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @return the value of the '<em>Available Until</em>' attribute.
	 * @see #setAvailableUntil(Date)
	 * @see ordersystem.special.SpecialPackage#getLimitedEditionProduct_AvailableUntil()
	 * @model dataType="ordersystem.JavaDate"
	 * @generated
	 */
	Date getAvailableUntil();

	/**
	 * Sets the value of the
	 * '{@link ordersystem.special.LimitedEditionProduct#getAvailableUntil
	 * <em>Available Until</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @param value the new value of the '<em>Available Until</em>' attribute.
	 * @see #getAvailableUntil()
	 * @generated
	 */
	void setAvailableUntil(Date value);

} // LimitedEditionProduct
