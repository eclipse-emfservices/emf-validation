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

package ordersystem.impl;

import java.util.Collection;

import ordersystem.Customer;
import ordersystem.OrderSystem;
import ordersystem.OrderSystemPackage;
import ordersystem.Product;
import ordersystem.Warehouse;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>OrderSystem</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ordersystem.impl.OrderSystemImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link ordersystem.impl.OrderSystemImpl#getCustomer <em>Customer</em>}</li>
 *   <li>{@link ordersystem.impl.OrderSystemImpl#getProduct <em>Product</em>}</li>
 *   <li>{@link ordersystem.impl.OrderSystemImpl#getWarehouse <em>Warehouse</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrderSystemImpl extends EObjectImpl implements OrderSystem {
	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
    protected static final int VERSION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
    protected int version = VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getCustomer() <em>Customer</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getCustomer()
	 * @generated
	 * @ordered
	 */
    protected EList<Customer> customer;

	/**
	 * The cached value of the '{@link #getProduct() <em>Product</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProduct()
	 * @generated
	 * @ordered
	 */
    protected EList<Product> product;

	/**
	 * The cached value of the '{@link #getWarehouse() <em>Warehouse</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getWarehouse()
	 * @generated
	 * @ordered
	 */
    protected EList<Warehouse> warehouse;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected OrderSystemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				protected EClass eStaticClass() {
		return OrderSystemPackage.Literals.ORDER_SYSTEM;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public int getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setVersion(int newVersion) {
		int oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ORDER_SYSTEM__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Customer> getCustomer() {
		if (customer == null) {
			customer = new EObjectContainmentWithInverseEList<Customer>(Customer.class, this, OrderSystemPackage.ORDER_SYSTEM__CUSTOMER, OrderSystemPackage.CUSTOMER__OWNER);
		}
		return customer;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Product> getProduct() {
		if (product == null) {
			product = new EObjectContainmentWithInverseEList<Product>(Product.class, this, OrderSystemPackage.ORDER_SYSTEM__PRODUCT, OrderSystemPackage.PRODUCT__OWNER);
		}
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Warehouse> getWarehouse() {
		if (warehouse == null) {
			warehouse = new EObjectContainmentWithInverseEList<Warehouse>(Warehouse.class, this, OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE, OrderSystemPackage.WAREHOUSE__OWNER);
		}
		return warehouse;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
		@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrderSystemPackage.ORDER_SYSTEM__CUSTOMER:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getCustomer()).basicAdd(otherEnd, msgs);
			case OrderSystemPackage.ORDER_SYSTEM__PRODUCT:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getProduct()).basicAdd(otherEnd, msgs);
			case OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getWarehouse()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrderSystemPackage.ORDER_SYSTEM__CUSTOMER:
				return ((InternalEList<?>)getCustomer()).basicRemove(otherEnd, msgs);
			case OrderSystemPackage.ORDER_SYSTEM__PRODUCT:
				return ((InternalEList<?>)getProduct()).basicRemove(otherEnd, msgs);
			case OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE:
				return ((InternalEList<?>)getWarehouse()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrderSystemPackage.ORDER_SYSTEM__VERSION:
				return new Integer(getVersion());
			case OrderSystemPackage.ORDER_SYSTEM__CUSTOMER:
				return getCustomer();
			case OrderSystemPackage.ORDER_SYSTEM__PRODUCT:
				return getProduct();
			case OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE:
				return getWarehouse();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
		@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrderSystemPackage.ORDER_SYSTEM__VERSION:
				setVersion(((Integer)newValue).intValue());
				return;
			case OrderSystemPackage.ORDER_SYSTEM__CUSTOMER:
				getCustomer().clear();
				getCustomer().addAll((Collection<? extends Customer>)newValue);
				return;
			case OrderSystemPackage.ORDER_SYSTEM__PRODUCT:
				getProduct().clear();
				getProduct().addAll((Collection<? extends Product>)newValue);
				return;
			case OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE:
				getWarehouse().clear();
				getWarehouse().addAll((Collection<? extends Warehouse>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case OrderSystemPackage.ORDER_SYSTEM__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case OrderSystemPackage.ORDER_SYSTEM__CUSTOMER:
				getCustomer().clear();
				return;
			case OrderSystemPackage.ORDER_SYSTEM__PRODUCT:
				getProduct().clear();
				return;
			case OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE:
				getWarehouse().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OrderSystemPackage.ORDER_SYSTEM__VERSION:
				return version != VERSION_EDEFAULT;
			case OrderSystemPackage.ORDER_SYSTEM__CUSTOMER:
				return customer != null && !customer.isEmpty();
			case OrderSystemPackage.ORDER_SYSTEM__PRODUCT:
				return product != null && !product.isEmpty();
			case OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE:
				return warehouse != null && !warehouse.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(32);
        
        result.append("OrderSystem v. "); //$NON-NLS-1$
        result.append(version);
        
        return result.toString();
    }

} //OrderSystemImpl
