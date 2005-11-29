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
 * A representation of the model object '<em><b>Warehouse</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ordersystem.Warehouse#getName <em>Name</em>}</li>
 *   <li>{@link ordersystem.Warehouse#getOwner <em>Owner</em>}</li>
 *   <li>{@link ordersystem.Warehouse#getItem <em>Item</em>}</li>
 *   <li>{@link ordersystem.Warehouse#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @see ordersystem.OrderSystemPackage#getWarehouse()
 *
 * @generated
 */
public interface Warehouse extends EObject{
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
	 * @see ordersystem.OrderSystemPackage#getWarehouse_Name()
	 *
	 * @generated
	 */
    String getName();

	/**
	 * Sets the value of the '{@link ordersystem.Warehouse#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
    void setName(String value);

	/**
	 * Returns the value of the '<em><b>Owner</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link ordersystem.OrderSystem#getWarehouse <em>Warehouse</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Owner</em>' container reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner</em>' container reference.
	 * @see #setOwner(OrderSystem)
	 * @see ordersystem.OrderSystemPackage#getWarehouse_Owner()
	 * @see ordersystem.OrderSystem#getWarehouse
	 * @model opposite="warehouse"
	 * @generated
	 */
    OrderSystem getOwner();

	/**
	 * Sets the value of the '{@link ordersystem.Warehouse#getOwner <em>Owner</em>}' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner</em>' container reference.
	 * @see #getOwner()
	 * @generated
	 */
    void setOwner(OrderSystem value);

	/**
	 * Returns the value of the '<em><b>Item</b></em>' containment reference list.
	 * The list contents are of type {@link ordersystem.InventoryItem}.
	 * It is bidirectional and its opposite is '{@link ordersystem.InventoryItem#getWarehouse <em>Warehouse</em>}'.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Item</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Item</em>' containment reference list.
	 * @see ordersystem.OrderSystemPackage#getWarehouse_Item()
	 * @see ordersystem.InventoryItem#getWarehouse
	 * @model type="ordersystem.InventoryItem" opposite="Warehouse" containment="true"
	 * @generated
	 */
    EList getItem();

	/**
	 * Returns the value of the '<em><b>Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Location</em>' containment reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Location</em>' containment reference.
	 * @see #setLocation(Address)
	 * @see ordersystem.OrderSystemPackage#getWarehouse_Location()
	 * @model containment="true" required="true"
	 * @generated
	 */
    Address getLocation();

	/**
	 * Sets the value of the '{@link ordersystem.Warehouse#getLocation <em>Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Location</em>' containment reference.
	 * @see #getLocation()
	 * @generated
	 */
    void setLocation(Address value);

} // Warehouse
