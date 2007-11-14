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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see ordersystem.OrderSystemFactory
 * @model kind="package"
 * @generated
 */
public interface OrderSystemPackage extends EPackage{
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String eNAME = "ordersystem"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String eNS_URI = "http:///ordersystem.ecore"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    String eNS_PREFIX = "ordersystem"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    OrderSystemPackage eINSTANCE = ordersystem.impl.OrderSystemPackageImpl.init();

	/**
	 * The meta object id for the '{@link ordersystem.impl.OrderSystemImpl <em>Order System</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see ordersystem.impl.OrderSystemImpl
	 * @see ordersystem.impl.OrderSystemPackageImpl#getOrderSystem()
	 * @generated
	 */
    int ORDER_SYSTEM = 3;

	/**
	 * The meta object id for the '{@link ordersystem.impl.CustomerImpl <em>Customer</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see ordersystem.impl.CustomerImpl
	 * @see ordersystem.impl.OrderSystemPackageImpl#getCustomer()
	 * @generated
	 */
    int CUSTOMER = 6;

	/**
	 * The meta object id for the '{@link ordersystem.impl.AccountImpl <em>Account</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see ordersystem.impl.AccountImpl
	 * @see ordersystem.impl.OrderSystemPackageImpl#getAccount()
	 * @generated
	 */
    int ACCOUNT = 8;

	/**
	 * The meta object id for the '{@link ordersystem.impl.AddressImpl <em>Address</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see ordersystem.impl.AddressImpl
	 * @see ordersystem.impl.OrderSystemPackageImpl#getAddress()
	 * @generated
	 */
    int ADDRESS = 7;

	/**
	 * The meta object id for the '{@link ordersystem.impl.OrderImpl <em>Order</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see ordersystem.impl.OrderImpl
	 * @see ordersystem.impl.OrderSystemPackageImpl#getOrder()
	 * @generated
	 */
    int ORDER = 0;

	/**
	 * The feature id for the '<em><b>Placed On</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER__PLACED_ON = 0;

	/**
	 * The feature id for the '<em><b>Filled On</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER__FILLED_ON = 1;

	/**
	 * The feature id for the '<em><b>Completed</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER__COMPLETED = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER__ID = 3;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER__OWNER = 4;

	/**
	 * The feature id for the '<em><b>Item</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER__ITEM = 5;

	/**
	 * The number of structural features of the '<em>Order</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link ordersystem.impl.LineItemImpl <em>Line Item</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see ordersystem.impl.LineItemImpl
	 * @see ordersystem.impl.OrderSystemPackageImpl#getLineItem()
	 * @generated
	 */
    int LINE_ITEM = 4;

	/**
	 * The meta object id for the '{@link ordersystem.impl.ProductImpl <em>Product</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see ordersystem.impl.ProductImpl
	 * @see ordersystem.impl.OrderSystemPackageImpl#getProduct()
	 * @generated
	 */
    int PRODUCT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PRODUCT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Sku</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PRODUCT__SKU = 1;

	/**
	 * The feature id for the '<em><b>Price</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PRODUCT__PRICE = 2;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PRODUCT__OWNER = 3;

	/**
	 * The number of structural features of the '<em>Product</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int PRODUCT_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link ordersystem.impl.WarehouseImpl <em>Warehouse</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see ordersystem.impl.WarehouseImpl
	 * @see ordersystem.impl.OrderSystemPackageImpl#getWarehouse()
	 * @generated
	 */
    int WAREHOUSE = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WAREHOUSE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WAREHOUSE__OWNER = 1;

	/**
	 * The feature id for the '<em><b>Item</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WAREHOUSE__ITEM = 2;

	/**
	 * The feature id for the '<em><b>Location</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WAREHOUSE__LOCATION = 3;

	/**
	 * The number of structural features of the '<em>Warehouse</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int WAREHOUSE_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER_SYSTEM__VERSION = 0;

	/**
	 * The feature id for the '<em><b>Customer</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER_SYSTEM__CUSTOMER = 1;

	/**
	 * The feature id for the '<em><b>Product</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER_SYSTEM__PRODUCT = 2;

	/**
	 * The feature id for the '<em><b>Warehouse</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER_SYSTEM__WAREHOUSE = 3;

	/**
	 * The number of structural features of the '<em>Order System</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ORDER_SYSTEM_FEATURE_COUNT = 4;

	/**
	 * The feature id for the '<em><b>Quantity</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LINE_ITEM__QUANTITY = 0;

	/**
	 * The feature id for the '<em><b>Discount</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LINE_ITEM__DISCOUNT = 1;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LINE_ITEM__OWNER = 2;

	/**
	 * The feature id for the '<em><b>Product</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LINE_ITEM__PRODUCT = 3;

	/**
	 * The number of structural features of the '<em>Line Item</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int LINE_ITEM_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link ordersystem.impl.InventoryItemImpl <em>Inventory Item</em>}' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see ordersystem.impl.InventoryItemImpl
	 * @see ordersystem.impl.OrderSystemPackageImpl#getInventoryItem()
	 * @generated
	 */
    int INVENTORY_ITEM = 5;

	/**
	 * The feature id for the '<em><b>In Stock</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INVENTORY_ITEM__IN_STOCK = 0;

	/**
	 * The feature id for the '<em><b>Restock Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INVENTORY_ITEM__RESTOCK_THRESHOLD = 1;

	/**
	 * The feature id for the '<em><b>Next Stock Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INVENTORY_ITEM__NEXT_STOCK_DATE = 2;

	/**
	 * The feature id for the '<em><b>Warehouse</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INVENTORY_ITEM__WAREHOUSE = 3;

	/**
	 * The feature id for the '<em><b>Product</b></em>' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INVENTORY_ITEM__PRODUCT = 4;

	/**
	 * The number of structural features of the '<em>Inventory Item</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int INVENTORY_ITEM_FEATURE_COUNT = 5;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CUSTOMER__LAST_NAME = 0;

	/**
	 * The feature id for the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CUSTOMER__FIRST_NAME = 1;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CUSTOMER__OWNER = 2;

	/**
	 * The feature id for the '<em><b>Account</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CUSTOMER__ACCOUNT = 3;

	/**
	 * The feature id for the '<em><b>Order</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CUSTOMER__ORDER = 4;

	/**
	 * The number of structural features of the '<em>Customer</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int CUSTOMER_FEATURE_COUNT = 5;

	/**
	 * The feature id for the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ADDRESS__NUMBER = 0;

	/**
	 * The feature id for the '<em><b>Street</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ADDRESS__STREET = 1;

	/**
	 * The feature id for the '<em><b>Apartment</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ADDRESS__APARTMENT = 2;

	/**
	 * The feature id for the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ADDRESS__CITY = 3;

	/**
	 * The feature id for the '<em><b>Province</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ADDRESS__PROVINCE = 4;

	/**
	 * The feature id for the '<em><b>Postal Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ADDRESS__POSTAL_CODE = 5;

	/**
	 * The feature id for the '<em><b>Country</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ADDRESS__COUNTRY = 6;

	/**
	 * The number of structural features of the '<em>Address</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ADDRESS_FEATURE_COUNT = 7;

	/**
	 * The feature id for the '<em><b>Payment Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ACCOUNT__PAYMENT_METHOD = 0;

	/**
	 * The feature id for the '<em><b>Account Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ACCOUNT__ACCOUNT_NUMBER = 1;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ACCOUNT__OWNER = 2;

	/**
	 * The feature id for the '<em><b>Billing Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ACCOUNT__BILLING_ADDRESS = 3;

	/**
	 * The feature id for the '<em><b>Shipping Address</b></em>' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ACCOUNT__SHIPPING_ADDRESS = 4;

	/**
	 * The number of structural features of the '<em>Account</em>' class.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
    int ACCOUNT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '<em>Java Date</em>' data type.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see java.util.Date
	 * @see ordersystem.impl.OrderSystemPackageImpl#getJavaDate()
	 * @generated
	 */
    int JAVA_DATE = 9;


	/**
	 * Returns the meta object for class '{@link ordersystem.OrderSystem <em>Order System</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Order System</em>'.
	 * @see ordersystem.OrderSystem
	 * @generated
	 */
    EClass getOrderSystem();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.OrderSystem#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see ordersystem.OrderSystem#getVersion()
	 * @see #getOrderSystem()
	 * @generated
	 */
    EAttribute getOrderSystem_Version();

	/**
	 * Returns the meta object for the containment reference list '{@link ordersystem.OrderSystem#getCustomer <em>Customer</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Customer</em>'.
	 * @see ordersystem.OrderSystem#getCustomer()
	 * @see #getOrderSystem()
	 * @generated
	 */
    EReference getOrderSystem_Customer();

	/**
	 * Returns the meta object for the containment reference list '{@link ordersystem.OrderSystem#getProduct <em>Product</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Product</em>'.
	 * @see ordersystem.OrderSystem#getProduct()
	 * @see #getOrderSystem()
	 * @generated
	 */
    EReference getOrderSystem_Product();

	/**
	 * Returns the meta object for the containment reference list '{@link ordersystem.OrderSystem#getWarehouse <em>Warehouse</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Warehouse</em>'.
	 * @see ordersystem.OrderSystem#getWarehouse()
	 * @see #getOrderSystem()
	 * @generated
	 */
    EReference getOrderSystem_Warehouse();

	/**
	 * Returns the meta object for class '{@link ordersystem.Customer <em>Customer</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Customer</em>'.
	 * @see ordersystem.Customer
	 * @generated
	 */
    EClass getCustomer();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Customer#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see ordersystem.Customer#getLastName()
	 * @see #getCustomer()
	 * @generated
	 */
    EAttribute getCustomer_LastName();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Customer#getFirstName <em>First Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>First Name</em>'.
	 * @see ordersystem.Customer#getFirstName()
	 * @see #getCustomer()
	 * @generated
	 */
    EAttribute getCustomer_FirstName();

	/**
	 * Returns the meta object for the container reference '{@link ordersystem.Customer#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see ordersystem.Customer#getOwner()
	 * @see #getCustomer()
	 * @generated
	 */
    EReference getCustomer_Owner();

	/**
	 * Returns the meta object for the containment reference list '{@link ordersystem.Customer#getAccount <em>Account</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Account</em>'.
	 * @see ordersystem.Customer#getAccount()
	 * @see #getCustomer()
	 * @generated
	 */
    EReference getCustomer_Account();

	/**
	 * Returns the meta object for the containment reference list '{@link ordersystem.Customer#getOrder <em>Order</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Order</em>'.
	 * @see ordersystem.Customer#getOrder()
	 * @see #getCustomer()
	 * @generated
	 */
    EReference getCustomer_Order();

	/**
	 * Returns the meta object for class '{@link ordersystem.Account <em>Account</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Account</em>'.
	 * @see ordersystem.Account
	 * @generated
	 */
    EClass getAccount();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Account#getPaymentMethod <em>Payment Method</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Payment Method</em>'.
	 * @see ordersystem.Account#getPaymentMethod()
	 * @see #getAccount()
	 * @generated
	 */
    EAttribute getAccount_PaymentMethod();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Account#getAccountNumber <em>Account Number</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Account Number</em>'.
	 * @see ordersystem.Account#getAccountNumber()
	 * @see #getAccount()
	 * @generated
	 */
    EAttribute getAccount_AccountNumber();

	/**
	 * Returns the meta object for the container reference '{@link ordersystem.Account#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see ordersystem.Account#getOwner()
	 * @see #getAccount()
	 * @generated
	 */
    EReference getAccount_Owner();

	/**
	 * Returns the meta object for the containment reference '{@link ordersystem.Account#getBillingAddress <em>Billing Address</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Billing Address</em>'.
	 * @see ordersystem.Account#getBillingAddress()
	 * @see #getAccount()
	 * @generated
	 */
    EReference getAccount_BillingAddress();

	/**
	 * Returns the meta object for the containment reference '{@link ordersystem.Account#getShippingAddress <em>Shipping Address</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shipping Address</em>'.
	 * @see ordersystem.Account#getShippingAddress()
	 * @see #getAccount()
	 * @generated
	 */
    EReference getAccount_ShippingAddress();

	/**
	 * Returns the meta object for class '{@link ordersystem.Address <em>Address</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Address</em>'.
	 * @see ordersystem.Address
	 * @generated
	 */
    EClass getAddress();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Address#getNumber <em>Number</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number</em>'.
	 * @see ordersystem.Address#getNumber()
	 * @see #getAddress()
	 * @generated
	 */
    EAttribute getAddress_Number();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Address#getStreet <em>Street</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Street</em>'.
	 * @see ordersystem.Address#getStreet()
	 * @see #getAddress()
	 * @generated
	 */
    EAttribute getAddress_Street();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Address#getApartment <em>Apartment</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Apartment</em>'.
	 * @see ordersystem.Address#getApartment()
	 * @see #getAddress()
	 * @generated
	 */
    EAttribute getAddress_Apartment();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Address#getCity <em>City</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>City</em>'.
	 * @see ordersystem.Address#getCity()
	 * @see #getAddress()
	 * @generated
	 */
    EAttribute getAddress_City();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Address#getProvince <em>Province</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Province</em>'.
	 * @see ordersystem.Address#getProvince()
	 * @see #getAddress()
	 * @generated
	 */
    EAttribute getAddress_Province();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Address#getPostalCode <em>Postal Code</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Postal Code</em>'.
	 * @see ordersystem.Address#getPostalCode()
	 * @see #getAddress()
	 * @generated
	 */
    EAttribute getAddress_PostalCode();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Address#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Country</em>'.
	 * @see ordersystem.Address#getCountry()
	 * @see #getAddress()
	 * @generated
	 */
    EAttribute getAddress_Country();

	/**
	 * Returns the meta object for class '{@link ordersystem.Order <em>Order</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Order</em>'.
	 * @see ordersystem.Order
	 * @generated
	 */
    EClass getOrder();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Order#getPlacedOn <em>Placed On</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Placed On</em>'.
	 * @see ordersystem.Order#getPlacedOn()
	 * @see #getOrder()
	 * @generated
	 */
    EAttribute getOrder_PlacedOn();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Order#getFilledOn <em>Filled On</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Filled On</em>'.
	 * @see ordersystem.Order#getFilledOn()
	 * @see #getOrder()
	 * @generated
	 */
    EAttribute getOrder_FilledOn();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Order#isCompleted <em>Completed</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Completed</em>'.
	 * @see ordersystem.Order#isCompleted()
	 * @see #getOrder()
	 * @generated
	 */
    EAttribute getOrder_Completed();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Order#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see ordersystem.Order#getId()
	 * @see #getOrder()
	 * @generated
	 */
    EAttribute getOrder_Id();

	/**
	 * Returns the meta object for the container reference '{@link ordersystem.Order#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see ordersystem.Order#getOwner()
	 * @see #getOrder()
	 * @generated
	 */
    EReference getOrder_Owner();

	/**
	 * Returns the meta object for the containment reference list '{@link ordersystem.Order#getItem <em>Item</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Item</em>'.
	 * @see ordersystem.Order#getItem()
	 * @see #getOrder()
	 * @generated
	 */
    EReference getOrder_Item();

	/**
	 * Returns the meta object for class '{@link ordersystem.LineItem <em>Line Item</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Line Item</em>'.
	 * @see ordersystem.LineItem
	 * @generated
	 */
    EClass getLineItem();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.LineItem#getQuantity <em>Quantity</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Quantity</em>'.
	 * @see ordersystem.LineItem#getQuantity()
	 * @see #getLineItem()
	 * @generated
	 */
    EAttribute getLineItem_Quantity();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.LineItem#getDiscount <em>Discount</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discount</em>'.
	 * @see ordersystem.LineItem#getDiscount()
	 * @see #getLineItem()
	 * @generated
	 */
    EAttribute getLineItem_Discount();

	/**
	 * Returns the meta object for the container reference '{@link ordersystem.LineItem#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see ordersystem.LineItem#getOwner()
	 * @see #getLineItem()
	 * @generated
	 */
    EReference getLineItem_Owner();

	/**
	 * Returns the meta object for the reference '{@link ordersystem.LineItem#getProduct <em>Product</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Product</em>'.
	 * @see ordersystem.LineItem#getProduct()
	 * @see #getLineItem()
	 * @generated
	 */
    EReference getLineItem_Product();

	/**
	 * Returns the meta object for class '{@link ordersystem.Product <em>Product</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Product</em>'.
	 * @see ordersystem.Product
	 * @generated
	 */
    EClass getProduct();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Product#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see ordersystem.Product#getName()
	 * @see #getProduct()
	 * @generated
	 */
    EAttribute getProduct_Name();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Product#getSku <em>Sku</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sku</em>'.
	 * @see ordersystem.Product#getSku()
	 * @see #getProduct()
	 * @generated
	 */
    EAttribute getProduct_Sku();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Product#getPrice <em>Price</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Price</em>'.
	 * @see ordersystem.Product#getPrice()
	 * @see #getProduct()
	 * @generated
	 */
    EAttribute getProduct_Price();

	/**
	 * Returns the meta object for the container reference '{@link ordersystem.Product#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see ordersystem.Product#getOwner()
	 * @see #getProduct()
	 * @generated
	 */
    EReference getProduct_Owner();

	/**
	 * Returns the meta object for class '{@link ordersystem.Warehouse <em>Warehouse</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Warehouse</em>'.
	 * @see ordersystem.Warehouse
	 * @generated
	 */
    EClass getWarehouse();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.Warehouse#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see ordersystem.Warehouse#getName()
	 * @see #getWarehouse()
	 * @generated
	 */
    EAttribute getWarehouse_Name();

	/**
	 * Returns the meta object for the container reference '{@link ordersystem.Warehouse#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Owner</em>'.
	 * @see ordersystem.Warehouse#getOwner()
	 * @see #getWarehouse()
	 * @generated
	 */
    EReference getWarehouse_Owner();

	/**
	 * Returns the meta object for the containment reference list '{@link ordersystem.Warehouse#getItem <em>Item</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Item</em>'.
	 * @see ordersystem.Warehouse#getItem()
	 * @see #getWarehouse()
	 * @generated
	 */
    EReference getWarehouse_Item();

	/**
	 * Returns the meta object for the containment reference '{@link ordersystem.Warehouse#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Location</em>'.
	 * @see ordersystem.Warehouse#getLocation()
	 * @see #getWarehouse()
	 * @generated
	 */
    EReference getWarehouse_Location();

	/**
	 * Returns the meta object for class '{@link ordersystem.InventoryItem <em>Inventory Item</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Inventory Item</em>'.
	 * @see ordersystem.InventoryItem
	 * @generated
	 */
    EClass getInventoryItem();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.InventoryItem#getInStock <em>In Stock</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>In Stock</em>'.
	 * @see ordersystem.InventoryItem#getInStock()
	 * @see #getInventoryItem()
	 * @generated
	 */
    EAttribute getInventoryItem_InStock();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.InventoryItem#getRestockThreshold <em>Restock Threshold</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Restock Threshold</em>'.
	 * @see ordersystem.InventoryItem#getRestockThreshold()
	 * @see #getInventoryItem()
	 * @generated
	 */
    EAttribute getInventoryItem_RestockThreshold();

	/**
	 * Returns the meta object for the attribute '{@link ordersystem.InventoryItem#getNextStockDate <em>Next Stock Date</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Next Stock Date</em>'.
	 * @see ordersystem.InventoryItem#getNextStockDate()
	 * @see #getInventoryItem()
	 * @generated
	 */
    EAttribute getInventoryItem_NextStockDate();

	/**
	 * Returns the meta object for the container reference '{@link ordersystem.InventoryItem#getWarehouse <em>Warehouse</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Warehouse</em>'.
	 * @see ordersystem.InventoryItem#getWarehouse()
	 * @see #getInventoryItem()
	 * @generated
	 */
    EReference getInventoryItem_Warehouse();

	/**
	 * Returns the meta object for the reference '{@link ordersystem.InventoryItem#getProduct <em>Product</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Product</em>'.
	 * @see ordersystem.InventoryItem#getProduct()
	 * @see #getInventoryItem()
	 * @generated
	 */
    EReference getInventoryItem_Product();

	/**
	 * Returns the meta object for data type '{@link java.util.Date <em>Java Date</em>}'.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Java Date</em>'.
	 * @see java.util.Date
	 * @model instanceClass="java.util.Date"
	 * @generated
	 */
    EDataType getJavaDate();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
    OrderSystemFactory getOrderSystemFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link ordersystem.impl.OrderImpl <em>Order</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ordersystem.impl.OrderImpl
		 * @see ordersystem.impl.OrderSystemPackageImpl#getOrder()
		 * @generated
		 */
		EClass ORDER = eINSTANCE.getOrder();

		/**
		 * The meta object literal for the '<em><b>Placed On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORDER__PLACED_ON = eINSTANCE.getOrder_PlacedOn();

		/**
		 * The meta object literal for the '<em><b>Filled On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORDER__FILLED_ON = eINSTANCE.getOrder_FilledOn();

		/**
		 * The meta object literal for the '<em><b>Completed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORDER__COMPLETED = eINSTANCE.getOrder_Completed();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORDER__ID = eINSTANCE.getOrder_Id();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORDER__OWNER = eINSTANCE.getOrder_Owner();

		/**
		 * The meta object literal for the '<em><b>Item</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORDER__ITEM = eINSTANCE.getOrder_Item();

		/**
		 * The meta object literal for the '{@link ordersystem.impl.ProductImpl <em>Product</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ordersystem.impl.ProductImpl
		 * @see ordersystem.impl.OrderSystemPackageImpl#getProduct()
		 * @generated
		 */
		EClass PRODUCT = eINSTANCE.getProduct();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__NAME = eINSTANCE.getProduct_Name();

		/**
		 * The meta object literal for the '<em><b>Sku</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__SKU = eINSTANCE.getProduct_Sku();

		/**
		 * The meta object literal for the '<em><b>Price</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRODUCT__PRICE = eINSTANCE.getProduct_Price();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRODUCT__OWNER = eINSTANCE.getProduct_Owner();

		/**
		 * The meta object literal for the '{@link ordersystem.impl.WarehouseImpl <em>Warehouse</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ordersystem.impl.WarehouseImpl
		 * @see ordersystem.impl.OrderSystemPackageImpl#getWarehouse()
		 * @generated
		 */
		EClass WAREHOUSE = eINSTANCE.getWarehouse();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WAREHOUSE__NAME = eINSTANCE.getWarehouse_Name();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WAREHOUSE__OWNER = eINSTANCE.getWarehouse_Owner();

		/**
		 * The meta object literal for the '<em><b>Item</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WAREHOUSE__ITEM = eINSTANCE.getWarehouse_Item();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference WAREHOUSE__LOCATION = eINSTANCE.getWarehouse_Location();

		/**
		 * The meta object literal for the '{@link ordersystem.impl.OrderSystemImpl <em>Order System</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ordersystem.impl.OrderSystemImpl
		 * @see ordersystem.impl.OrderSystemPackageImpl#getOrderSystem()
		 * @generated
		 */
		EClass ORDER_SYSTEM = eINSTANCE.getOrderSystem();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORDER_SYSTEM__VERSION = eINSTANCE.getOrderSystem_Version();

		/**
		 * The meta object literal for the '<em><b>Customer</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORDER_SYSTEM__CUSTOMER = eINSTANCE.getOrderSystem_Customer();

		/**
		 * The meta object literal for the '<em><b>Product</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORDER_SYSTEM__PRODUCT = eINSTANCE.getOrderSystem_Product();

		/**
		 * The meta object literal for the '<em><b>Warehouse</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORDER_SYSTEM__WAREHOUSE = eINSTANCE.getOrderSystem_Warehouse();

		/**
		 * The meta object literal for the '{@link ordersystem.impl.LineItemImpl <em>Line Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ordersystem.impl.LineItemImpl
		 * @see ordersystem.impl.OrderSystemPackageImpl#getLineItem()
		 * @generated
		 */
		EClass LINE_ITEM = eINSTANCE.getLineItem();

		/**
		 * The meta object literal for the '<em><b>Quantity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE_ITEM__QUANTITY = eINSTANCE.getLineItem_Quantity();

		/**
		 * The meta object literal for the '<em><b>Discount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LINE_ITEM__DISCOUNT = eINSTANCE.getLineItem_Discount();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINE_ITEM__OWNER = eINSTANCE.getLineItem_Owner();

		/**
		 * The meta object literal for the '<em><b>Product</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LINE_ITEM__PRODUCT = eINSTANCE.getLineItem_Product();

		/**
		 * The meta object literal for the '{@link ordersystem.impl.InventoryItemImpl <em>Inventory Item</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ordersystem.impl.InventoryItemImpl
		 * @see ordersystem.impl.OrderSystemPackageImpl#getInventoryItem()
		 * @generated
		 */
		EClass INVENTORY_ITEM = eINSTANCE.getInventoryItem();

		/**
		 * The meta object literal for the '<em><b>In Stock</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INVENTORY_ITEM__IN_STOCK = eINSTANCE.getInventoryItem_InStock();

		/**
		 * The meta object literal for the '<em><b>Restock Threshold</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INVENTORY_ITEM__RESTOCK_THRESHOLD = eINSTANCE.getInventoryItem_RestockThreshold();

		/**
		 * The meta object literal for the '<em><b>Next Stock Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INVENTORY_ITEM__NEXT_STOCK_DATE = eINSTANCE.getInventoryItem_NextStockDate();

		/**
		 * The meta object literal for the '<em><b>Warehouse</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INVENTORY_ITEM__WAREHOUSE = eINSTANCE.getInventoryItem_Warehouse();

		/**
		 * The meta object literal for the '<em><b>Product</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INVENTORY_ITEM__PRODUCT = eINSTANCE.getInventoryItem_Product();

		/**
		 * The meta object literal for the '{@link ordersystem.impl.CustomerImpl <em>Customer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ordersystem.impl.CustomerImpl
		 * @see ordersystem.impl.OrderSystemPackageImpl#getCustomer()
		 * @generated
		 */
		EClass CUSTOMER = eINSTANCE.getCustomer();

		/**
		 * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOMER__LAST_NAME = eINSTANCE.getCustomer_LastName();

		/**
		 * The meta object literal for the '<em><b>First Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOMER__FIRST_NAME = eINSTANCE.getCustomer_FirstName();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CUSTOMER__OWNER = eINSTANCE.getCustomer_Owner();

		/**
		 * The meta object literal for the '<em><b>Account</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CUSTOMER__ACCOUNT = eINSTANCE.getCustomer_Account();

		/**
		 * The meta object literal for the '<em><b>Order</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CUSTOMER__ORDER = eINSTANCE.getCustomer_Order();

		/**
		 * The meta object literal for the '{@link ordersystem.impl.AddressImpl <em>Address</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ordersystem.impl.AddressImpl
		 * @see ordersystem.impl.OrderSystemPackageImpl#getAddress()
		 * @generated
		 */
		EClass ADDRESS = eINSTANCE.getAddress();

		/**
		 * The meta object literal for the '<em><b>Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__NUMBER = eINSTANCE.getAddress_Number();

		/**
		 * The meta object literal for the '<em><b>Street</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__STREET = eINSTANCE.getAddress_Street();

		/**
		 * The meta object literal for the '<em><b>Apartment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__APARTMENT = eINSTANCE.getAddress_Apartment();

		/**
		 * The meta object literal for the '<em><b>City</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__CITY = eINSTANCE.getAddress_City();

		/**
		 * The meta object literal for the '<em><b>Province</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__PROVINCE = eINSTANCE.getAddress_Province();

		/**
		 * The meta object literal for the '<em><b>Postal Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__POSTAL_CODE = eINSTANCE.getAddress_PostalCode();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADDRESS__COUNTRY = eINSTANCE.getAddress_Country();

		/**
		 * The meta object literal for the '{@link ordersystem.impl.AccountImpl <em>Account</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see ordersystem.impl.AccountImpl
		 * @see ordersystem.impl.OrderSystemPackageImpl#getAccount()
		 * @generated
		 */
		EClass ACCOUNT = eINSTANCE.getAccount();

		/**
		 * The meta object literal for the '<em><b>Payment Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__PAYMENT_METHOD = eINSTANCE.getAccount_PaymentMethod();

		/**
		 * The meta object literal for the '<em><b>Account Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACCOUNT__ACCOUNT_NUMBER = eINSTANCE.getAccount_AccountNumber();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNT__OWNER = eINSTANCE.getAccount_Owner();

		/**
		 * The meta object literal for the '<em><b>Billing Address</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNT__BILLING_ADDRESS = eINSTANCE.getAccount_BillingAddress();

		/**
		 * The meta object literal for the '<em><b>Shipping Address</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACCOUNT__SHIPPING_ADDRESS = eINSTANCE.getAccount_ShippingAddress();

		/**
		 * The meta object literal for the '<em>Java Date</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.util.Date
		 * @see ordersystem.impl.OrderSystemPackageImpl#getJavaDate()
		 * @generated
		 */
		EDataType JAVA_DATE = eINSTANCE.getJavaDate();

	}

} //OrderSystemPackage
