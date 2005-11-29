/**
 * <copyright>
 *
 * Copyright (c) 2005 IBM Corporation and others.
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

package ordersystem;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Product</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ordersystem.Product#getName <em>Name</em>}</li>
 *   <li>{@link ordersystem.Product#getSku <em>Sku</em>}</li>
 *   <li>{@link ordersystem.Product#getPrice <em>Price</em>}</li>
 *   <li>{@link ordersystem.Product#getOwner <em>Owner</em>}</li>
 * </ul>
 * </p>
 *
 * @see ordersystem.OrderSystemPackage#getProduct()
 *
 * @generated
 */
public interface Product extends EObject{
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see ordersystem.OrderSystemPackage#getProduct_Name()
	 *
	 * @generated
	 */
    String getName();

	/**
	 * Sets the value of the '{@link ordersystem.Product#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
    void setName(String value);

	/**
	 * Returns the value of the '<em><b>Sku</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Sku</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Sku</em>' attribute.
	 * @see #setSku(String)
	 * @see ordersystem.OrderSystemPackage#getProduct_Sku()
	 *
	 * @generated
	 */
    String getSku();

	/**
	 * Sets the value of the '{@link ordersystem.Product#getSku <em>Sku</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sku</em>' attribute.
	 * @see #getSku()
	 * @generated
	 */
    void setSku(String value);

	/**
	 * Returns the value of the '<em><b>Price</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Price</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Price</em>' attribute.
	 * @see #setPrice(double)
	 * @see ordersystem.OrderSystemPackage#getProduct_Price()
	 *
	 * @generated
	 */
    double getPrice();

	/**
	 * Sets the value of the '{@link ordersystem.Product#getPrice <em>Price</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Price</em>' attribute.
	 * @see #getPrice()
	 * @generated
	 */
    void setPrice(double value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ordersystem.OrderSystem#getProduct <em>Product</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Owner</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(OrderSystem)
	 * @see ordersystem.OrderSystemPackage#getProduct_Owner()
	 * @see ordersystem.OrderSystem#getProduct
	 * @model opposite="product"
	 * @generated
	 */
    OrderSystem getOwner();

	/**
	 * Sets the value of the '{@link ordersystem.Product#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
    void setOwner(OrderSystem value);

} // Product
