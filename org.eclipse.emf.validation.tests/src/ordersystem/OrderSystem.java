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

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>OrderSystem</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ordersystem.OrderSystem#getVersion <em>Version</em>}</li>
 *   <li>{@link ordersystem.OrderSystem#getCustomer <em>Customer</em>}</li>
 *   <li>{@link ordersystem.OrderSystem#getProduct <em>Product</em>}</li>
 *   <li>{@link ordersystem.OrderSystem#getWarehouse <em>Warehouse</em>}</li>
 * </ul>
 * </p>
 *
 * @see ordersystem.OrderSystemPackage#getOrderSystem()
 *
 * @generated
 */
public interface OrderSystem extends EObject{
	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(int)
	 * @see ordersystem.OrderSystemPackage#getOrderSystem_Version()
	 *
	 * @generated
	 */
    int getVersion();

	/**
	 * Sets the value of the '{@link ordersystem.OrderSystem#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
    void setVersion(int value);

	/**
	 * Returns the value of the '<em><b>Customer</b></em>' containment reference list.
	 * The list contents are of type {@link ordersystem.Customer}.
	 * It is bidirectional and its opposite is '{@link ordersystem.Customer#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Customer</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Customer</em>' containment reference list.
	 * @see ordersystem.OrderSystemPackage#getOrderSystem_Customer()
	 * @see ordersystem.Customer#getOwner
	 * @model type="ordersystem.Customer" opposite="owner" containment="true"
	 * @generated
	 */
    EList getCustomer();

	/**
	 * Returns the value of the '<em><b>Product</b></em>' containment reference list.
	 * The list contents are of type {@link ordersystem.Product}.
	 * It is bidirectional and its opposite is '{@link ordersystem.Product#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Product</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Product</em>' containment reference list.
	 * @see ordersystem.OrderSystemPackage#getOrderSystem_Product()
	 * @see ordersystem.Product#getOwner
	 * @model type="ordersystem.Product" opposite="owner" containment="true"
	 * @generated
	 */
    EList getProduct();

	/**
	 * Returns the value of the '<em><b>Warehouse</b></em>' containment reference list.
	 * The list contents are of type {@link ordersystem.Warehouse}.
	 * It is bidirectional and its opposite is '{@link ordersystem.Warehouse#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Warehouse</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Warehouse</em>' containment reference list.
	 * @see ordersystem.OrderSystemPackage#getOrderSystem_Warehouse()
	 * @see ordersystem.Warehouse#getOwner
	 * @model type="ordersystem.Warehouse" opposite="owner" containment="true"
	 * @generated
	 */
    EList getWarehouse();

} // OrderSystem
