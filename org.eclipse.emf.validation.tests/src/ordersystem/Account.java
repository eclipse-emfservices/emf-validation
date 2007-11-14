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

package ordersystem;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Account</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ordersystem.Account#getPaymentMethod <em>Payment Method</em>}</li>
 *   <li>{@link ordersystem.Account#getAccountNumber <em>Account Number</em>}</li>
 *   <li>{@link ordersystem.Account#getOwner <em>Owner</em>}</li>
 *   <li>{@link ordersystem.Account#getBillingAddress <em>Billing Address</em>}</li>
 *   <li>{@link ordersystem.Account#getShippingAddress <em>Shipping Address</em>}</li>
 * </ul>
 * </p>
 *
 * @see ordersystem.OrderSystemPackage#getAccount()
 * @model
 * @generated
 */
public interface Account extends EObject{
	/**
	 * Returns the value of the '<em><b>Payment Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Payment Method</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Payment Method</em>' attribute.
	 * @see #setPaymentMethod(String)
	 * @see ordersystem.OrderSystemPackage#getAccount_PaymentMethod()
	 * @model
	 * @generated
	 */
    String getPaymentMethod();

	/**
	 * Sets the value of the '{@link ordersystem.Account#getPaymentMethod <em>Payment Method</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Payment Method</em>' attribute.
	 * @see #getPaymentMethod()
	 * @generated
	 */
    void setPaymentMethod(String value);

	/**
	 * Returns the value of the '<em><b>Account Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Account Number</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Account Number</em>' attribute.
	 * @see #setAccountNumber(String)
	 * @see ordersystem.OrderSystemPackage#getAccount_AccountNumber()
	 * @model
	 * @generated
	 */
    String getAccountNumber();

	/**
	 * Sets the value of the '{@link ordersystem.Account#getAccountNumber <em>Account Number</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Account Number</em>' attribute.
	 * @see #getAccountNumber()
	 * @generated
	 */
    void setAccountNumber(String value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ordersystem.Customer#getAccount <em>Account</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Owner</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(Customer)
	 * @see ordersystem.OrderSystemPackage#getAccount_Owner()
	 * @see ordersystem.Customer#getAccount
	 * @model opposite="account"
	 * @generated
	 */
    Customer getOwner();

	/**
	 * Sets the value of the '{@link ordersystem.Account#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
    void setOwner(Customer value);

	/**
	 * Returns the value of the '<em><b>Billing Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Billing Address</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Billing Address</em>' containment reference.
	 * @see #setBillingAddress(Address)
	 * @see ordersystem.OrderSystemPackage#getAccount_BillingAddress()
	 * @model containment="true" required="true"
	 * @generated
	 */
    Address getBillingAddress();

	/**
	 * Sets the value of the '{@link ordersystem.Account#getBillingAddress <em>Billing Address</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Billing Address</em>' containment reference.
	 * @see #getBillingAddress()
	 * @generated
	 */
    void setBillingAddress(Address value);

	/**
	 * Returns the value of the '<em><b>Shipping Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Shipping Address</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Shipping Address</em>' containment reference.
	 * @see #setShippingAddress(Address)
	 * @see ordersystem.OrderSystemPackage#getAccount_ShippingAddress()
	 * @model containment="true" required="true"
	 * @generated
	 */
    Address getShippingAddress();

	/**
	 * Sets the value of the '{@link ordersystem.Account#getShippingAddress <em>Shipping Address</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Shipping Address</em>' containment reference.
	 * @see #getShippingAddress()
	 * @generated
	 */
    void setShippingAddress(Address value);

} // Account
