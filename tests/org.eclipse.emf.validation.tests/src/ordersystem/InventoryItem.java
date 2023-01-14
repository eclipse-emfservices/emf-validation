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

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object
 * '<em><b>Inventory Item</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 * <li>{@link ordersystem.InventoryItem#getInStock <em>In Stock</em>}</li>
 * <li>{@link ordersystem.InventoryItem#getRestockThreshold <em>Restock
 * Threshold</em>}</li>
 * <li>{@link ordersystem.InventoryItem#getNextStockDate <em>Next Stock
 * Date</em>}</li>
 * <li>{@link ordersystem.InventoryItem#getWarehouse <em>Warehouse</em>}</li>
 * <li>{@link ordersystem.InventoryItem#getProduct <em>Product</em>}</li>
 * </ul>
 * </p>
 *
 * @see ordersystem.OrderSystemPackage#getInventoryItem()
 * @model
 * @generated
 */
public interface InventoryItem extends EObject {
	/**
	 * Returns the value of the '<em><b>In Stock</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Stock</em>' attribute isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>In Stock</em>' attribute.
	 * @see #setInStock(int)
	 * @see ordersystem.OrderSystemPackage#getInventoryItem_InStock()
	 * @model
	 * @generated
	 */
	int getInStock();

	/**
	 * Sets the value of the '{@link ordersystem.InventoryItem#getInStock <em>In
	 * Stock</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>In Stock</em>' attribute.
	 * @see #getInStock()
	 * @generated
	 */
	void setInStock(int value);

	/**
	 * Returns the value of the '<em><b>Restock Threshold</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Restock Threshold</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Restock Threshold</em>' attribute.
	 * @see #setRestockThreshold(int)
	 * @see ordersystem.OrderSystemPackage#getInventoryItem_RestockThreshold()
	 * @model
	 * @generated
	 */
	int getRestockThreshold();

	/**
	 * Sets the value of the '{@link ordersystem.InventoryItem#getRestockThreshold
	 * <em>Restock Threshold</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value the new value of the '<em>Restock Threshold</em>' attribute.
	 * @see #getRestockThreshold()
	 * @generated
	 */
	void setRestockThreshold(int value);

	/**
	 * Returns the value of the '<em><b>Next Stock Date</b></em>' attribute. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Next Stock Date</em>' attribute isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Next Stock Date</em>' attribute.
	 * @see #setNextStockDate(Date)
	 * @see ordersystem.OrderSystemPackage#getInventoryItem_NextStockDate()
	 * @model dataType="ordersystem.JavaDate"
	 * @generated
	 */
	Date getNextStockDate();

	/**
	 * Sets the value of the '{@link ordersystem.InventoryItem#getNextStockDate
	 * <em>Next Stock Date</em>}' attribute. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value the new value of the '<em>Next Stock Date</em>' attribute.
	 * @see #getNextStockDate()
	 * @generated
	 */
	void setNextStockDate(Date value);

	/**
	 * Returns the value of the '<em><b>Warehouse</b></em>' container reference. It
	 * is bidirectional and its opposite is '{@link ordersystem.Warehouse#getItem
	 * <em>Item</em>}'. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Warehouse</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Warehouse</em>' container reference.
	 * @see #setWarehouse(Warehouse)
	 * @see ordersystem.OrderSystemPackage#getInventoryItem_Warehouse()
	 * @see ordersystem.Warehouse#getItem
	 * @model opposite="item"
	 * @generated
	 */
	Warehouse getWarehouse();

	/**
	 * Sets the value of the '{@link ordersystem.InventoryItem#getWarehouse
	 * <em>Warehouse</em>}' container reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @param value the new value of the '<em>Warehouse</em>' container reference.
	 * @see #getWarehouse()
	 * @generated
	 */
	void setWarehouse(Warehouse value);

	/**
	 * Returns the value of the '<em><b>Product</b></em>' reference. <!--
	 * begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Product</em>' reference isn't clear, there really
	 * should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 *
	 * @return the value of the '<em>Product</em>' reference.
	 * @see #setProduct(Product)
	 * @see ordersystem.OrderSystemPackage#getInventoryItem_Product()
	 * @model required="true"
	 * @generated
	 */
	Product getProduct();

	/**
	 * Sets the value of the '{@link ordersystem.InventoryItem#getProduct
	 * <em>Product</em>}' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the new value of the '<em>Product</em>' reference.
	 * @see #getProduct()
	 * @generated
	 */
	void setProduct(Product value);

} // InventoryItem
