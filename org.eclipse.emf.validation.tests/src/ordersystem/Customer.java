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
package ordersystem;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Customer</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ordersystem.Customer#getLastName <em>Last Name</em>}</li>
 *   <li>{@link ordersystem.Customer#getFirstName <em>First Name</em>}</li>
 *   <li>{@link ordersystem.Customer#getOwner <em>Owner</em>}</li>
 *   <li>{@link ordersystem.Customer#getAccount <em>Account</em>}</li>
 *   <li>{@link ordersystem.Customer#getOrder <em>Order</em>}</li>
 * </ul>
 * </p>
 *
 * @see ordersystem.OrderSystemPackage#getCustomer()
 * @model
 * @generated
 */
public interface Customer extends EObject{
	/**
	 * Returns the value of the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Last Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Name</em>' attribute.
	 * @see #setLastName(String)
	 * @see ordersystem.OrderSystemPackage#getCustomer_LastName()
	 * @model
	 * @generated
	 */
    String getLastName();

	/**
	 * Sets the value of the '{@link ordersystem.Customer#getLastName <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Name</em>' attribute.
	 * @see #getLastName()
	 * @generated
	 */
    void setLastName(String value);

	/**
	 * Returns the value of the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>First Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>First Name</em>' attribute.
	 * @see #setFirstName(String)
	 * @see ordersystem.OrderSystemPackage#getCustomer_FirstName()
	 * @model
	 * @generated
	 */
    String getFirstName();

	/**
	 * Sets the value of the '{@link ordersystem.Customer#getFirstName <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Name</em>' attribute.
	 * @see #getFirstName()
	 * @generated
	 */
    void setFirstName(String value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ordersystem.OrderSystem#getCustomer <em>Customer</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Owner</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(OrderSystem)
	 * @see ordersystem.OrderSystemPackage#getCustomer_Owner()
	 * @see ordersystem.OrderSystem#getCustomer
	 * @model opposite="customer"
	 * @generated
	 */
    OrderSystem getOwner();

	/**
	 * Sets the value of the '{@link ordersystem.Customer#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
    void setOwner(OrderSystem value);

	/**
	 * Returns the value of the '<em><b>Account</b></em>' containment reference list.
	 * The list contents are of type {@link ordersystem.Account}.
	 * It is bidirectional and its opposite is '{@link ordersystem.Account#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Account</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Account</em>' containment reference list.
	 * @see ordersystem.OrderSystemPackage#getCustomer_Account()
	 * @see ordersystem.Account#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
    EList<Account> getAccount();

	/**
	 * Returns the value of the '<em><b>Order</b></em>' containment reference list.
	 * The list contents are of type {@link ordersystem.Order}.
	 * It is bidirectional and its opposite is '{@link ordersystem.Order#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Order</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Order</em>' containment reference list.
	 * @see ordersystem.OrderSystemPackage#getCustomer_Order()
	 * @see ordersystem.Order#getOwner
	 * @model opposite="owner" containment="true"
	 * @generated
	 */
    EList<Order> getOrder();

} // Customer
