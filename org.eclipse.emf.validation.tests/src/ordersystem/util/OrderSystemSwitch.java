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

package ordersystem.util;

import java.util.List;

import ordersystem.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see ordersystem.OrderSystemPackage
 * @generated
 */
public class OrderSystemSwitch {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected static OrderSystemPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public OrderSystemSwitch() {
		if (modelPackage == null) {
			modelPackage = OrderSystemPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
    public Object doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch((EClass)eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected Object doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case OrderSystemPackage.ORDER: {
				Order order = (Order)theEObject;
				Object result = caseOrder(order);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrderSystemPackage.PRODUCT: {
				Product product = (Product)theEObject;
				Object result = caseProduct(product);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrderSystemPackage.WAREHOUSE: {
				Warehouse warehouse = (Warehouse)theEObject;
				Object result = caseWarehouse(warehouse);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrderSystemPackage.ORDER_SYSTEM: {
				OrderSystem orderSystem = (OrderSystem)theEObject;
				Object result = caseOrderSystem(orderSystem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrderSystemPackage.LINE_ITEM: {
				LineItem lineItem = (LineItem)theEObject;
				Object result = caseLineItem(lineItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrderSystemPackage.INVENTORY_ITEM: {
				InventoryItem inventoryItem = (InventoryItem)theEObject;
				Object result = caseInventoryItem(inventoryItem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrderSystemPackage.CUSTOMER: {
				Customer customer = (Customer)theEObject;
				Object result = caseCustomer(customer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrderSystemPackage.ADDRESS: {
				Address address = (Address)theEObject;
				Object result = caseAddress(address);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case OrderSystemPackage.ACCOUNT: {
				Account account = (Account)theEObject;
				Object result = caseAccount(account);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>OrderSystem</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>OrderSystem</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseOrderSystem(OrderSystem object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Customer</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Customer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseCustomer(Customer object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Account</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Account</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseAccount(Account object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Address</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Address</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseAddress(Address object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Order</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Order</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseOrder(Order object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Line Item</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Line Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseLineItem(LineItem object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Product</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Product</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseProduct(Product object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Warehouse</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Warehouse</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseWarehouse(Warehouse object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>Inventory Item</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>Inventory Item</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
    public Object caseInventoryItem(InventoryItem object) {
		return null;
	}

	/**
	 * Returns the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
    public Object defaultCase(EObject object) {
		return null;
	}

} //OrderSystemSwitch
