/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package ordersystem;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Order</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ordersystem.Order#getPlacedOn <em>Placed On</em>}</li>
 *   <li>{@link ordersystem.Order#getFilledOn <em>Filled On</em>}</li>
 *   <li>{@link ordersystem.Order#isCompleted <em>Completed</em>}</li>
 *   <li>{@link ordersystem.Order#getId <em>Id</em>}</li>
 *   <li>{@link ordersystem.Order#getOwner <em>Owner</em>}</li>
 *   <li>{@link ordersystem.Order#getItem <em>Item</em>}</li>
 * </ul>
 * </p>
 *
 * @see ordersystem.OrderSystemPackage#getOrder()
 * @model 
 * @generated
 */
public interface Order extends EObject{
	/**
	 * Returns the value of the '<em><b>Placed On</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Placed On</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Placed On</em>' attribute.
	 * @see #setPlacedOn(Date)
	 * @see ordersystem.OrderSystemPackage#getOrder_PlacedOn()
	 * @model dataType="ordersystem.JavaDate"
	 * @generated
	 */
    Date getPlacedOn();

	/**
	 * Sets the value of the '{@link ordersystem.Order#getPlacedOn <em>Placed On</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Placed On</em>' attribute.
	 * @see #getPlacedOn()
	 * @generated
	 */
    void setPlacedOn(Date value);

	/**
	 * Returns the value of the '<em><b>Filled On</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Filled On</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Filled On</em>' attribute.
	 * @see #setFilledOn(Date)
	 * @see ordersystem.OrderSystemPackage#getOrder_FilledOn()
	 * @model dataType="ordersystem.JavaDate"
	 * @generated
	 */
    Date getFilledOn();

	/**
	 * Sets the value of the '{@link ordersystem.Order#getFilledOn <em>Filled On</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Filled On</em>' attribute.
	 * @see #getFilledOn()
	 * @generated
	 */
    void setFilledOn(Date value);

	/**
	 * Returns the value of the '<em><b>Completed</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Completed</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Completed</em>' attribute.
	 * @see #setCompleted(boolean)
	 * @see ordersystem.OrderSystemPackage#getOrder_Completed()
	 * @model 
	 * @generated
	 */
    boolean isCompleted();

	/**
	 * Sets the value of the '{@link ordersystem.Order#isCompleted <em>Completed</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Completed</em>' attribute.
	 * @see #isCompleted()
	 * @generated
	 */
    void setCompleted(boolean value);

	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see ordersystem.OrderSystemPackage#getOrder_Id()
	 * @model 
	 * @generated
	 */
    String getId();

	/**
	 * Sets the value of the '{@link ordersystem.Order#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
    void setId(String value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ordersystem.Customer#getOrder <em>Order</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Owner</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(Customer)
	 * @see ordersystem.OrderSystemPackage#getOrder_Owner()
	 * @see ordersystem.Customer#getOrder
	 * @model opposite="order"
	 * @generated
	 */
    Customer getOwner();

	/**
	 * Sets the value of the '{@link ordersystem.Order#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
    void setOwner(Customer value);

	/**
	 * Returns the value of the '<em><b>Item</b></em>' containment reference list.
	 * The list contents are of type {@link ordersystem.LineItem}.
	 * It is bidirectional and its opposite is '{@link ordersystem.LineItem#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Item</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Item</em>' containment reference list.
	 * @see ordersystem.OrderSystemPackage#getOrder_Item()
	 * @see ordersystem.LineItem#getOwner
	 * @model type="ordersystem.LineItem" opposite="owner" containment="true"
	 * @generated
	 */
    EList getItem();

} // Order
