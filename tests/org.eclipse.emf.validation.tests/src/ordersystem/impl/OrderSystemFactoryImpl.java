/**
 * Copyright (c) 2005, 2014 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package ordersystem.impl;

import java.util.Date;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import ordersystem.Account;
import ordersystem.Address;
import ordersystem.Customer;
import ordersystem.InventoryItem;
import ordersystem.LineItem;
import ordersystem.Order;
import ordersystem.OrderSystem;
import ordersystem.OrderSystemFactory;
import ordersystem.OrderSystemPackage;
import ordersystem.Product;
import ordersystem.Warehouse;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class OrderSystemFactoryImpl extends EFactoryImpl implements OrderSystemFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public static OrderSystemFactory init() {
		try {
			OrderSystemFactory theOrderSystemFactory = (OrderSystemFactory) EPackage.Registry.INSTANCE
					.getEFactory(OrderSystemPackage.eNS_URI);
			if (theOrderSystemFactory != null) {
				return theOrderSystemFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new OrderSystemFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 *
	 * @generated
	 */
	public OrderSystemFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case OrderSystemPackage.ORDER:
			return createOrder();
		case OrderSystemPackage.PRODUCT:
			return createProduct();
		case OrderSystemPackage.WAREHOUSE:
			return createWarehouse();
		case OrderSystemPackage.ORDER_SYSTEM:
			return createOrderSystem();
		case OrderSystemPackage.LINE_ITEM:
			return createLineItem();
		case OrderSystemPackage.INVENTORY_ITEM:
			return createInventoryItem();
		case OrderSystemPackage.CUSTOMER:
			return createCustomer();
		case OrderSystemPackage.ADDRESS:
			return createAddress();
		case OrderSystemPackage.ACCOUNT:
			return createAccount();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
		case OrderSystemPackage.JAVA_DATE:
			return createJavaDateFromString(eDataType, initialValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
		case OrderSystemPackage.JAVA_DATE:
			return convertJavaDateToString(eDataType, instanceValue);
		default:
			throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public OrderSystem createOrderSystem() {
		OrderSystemImpl orderSystem = new OrderSystemImpl();
		return orderSystem;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Customer createCustomer() {
		CustomerImpl customer = new CustomerImpl();
		return customer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Account createAccount() {
		AccountImpl account = new AccountImpl();
		return account;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Address createAddress() {
		AddressImpl address = new AddressImpl();
		return address;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Order createOrder() {
		OrderImpl order = new OrderImpl();
		return order;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public LineItem createLineItem() {
		LineItemImpl lineItem = new LineItemImpl();
		return lineItem;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Product createProduct() {
		ProductImpl product = new ProductImpl();
		return product;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Warehouse createWarehouse() {
		WarehouseImpl warehouse = new WarehouseImpl();
		return warehouse;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public InventoryItem createInventoryItem() {
		InventoryItemImpl inventoryItem = new InventoryItemImpl();
		return inventoryItem;
	}

	/**
	 * Dates are converted to/from string representation in the XML by their
	 * internal millisecond value.
	 */
	public Date createJavaDateFromString(EDataType eDataType, String initialValue) {
		if ((initialValue != null) && !initialValue.equals("")) { //$NON-NLS-1$
			return new Date(Long.parseLong(initialValue));
		} else {
			return null;
		}
	}

	/**
	 * Dates are converted to/from string representation in the XML by their
	 * internal millisecond value.
	 */
	public String convertJavaDateToString(EDataType eDataType, Object instanceValue) {
		if (instanceValue != null) {
			return String.valueOf(((Date) instanceValue).getTime());
		} else {
			return null;
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public OrderSystemPackage getOrderSystemPackage() {
		return (OrderSystemPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static OrderSystemPackage getPackage() {
		return OrderSystemPackage.eINSTANCE;
	}
} // OrderSystemFactoryImpl
