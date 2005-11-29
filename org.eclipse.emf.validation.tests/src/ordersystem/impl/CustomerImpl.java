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

package ordersystem.impl;

import java.util.Collection;

import ordersystem.Account;
import ordersystem.Customer;
import ordersystem.Order;
import ordersystem.OrderSystem;

import ordersystem.OrderSystemPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Customer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ordersystem.impl.CustomerImpl#getLastName <em>Last Name</em>}</li>
 *   <li>{@link ordersystem.impl.CustomerImpl#getFirstName <em>First Name</em>}</li>
 *   <li>{@link ordersystem.impl.CustomerImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link ordersystem.impl.CustomerImpl#getAccount <em>Account</em>}</li>
 *   <li>{@link ordersystem.impl.CustomerImpl#getOrder <em>Order</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CustomerImpl extends EObjectImpl implements Customer {
	/**
	 * The default value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
    protected static final String LAST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastName() <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
    protected String lastName = LAST_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
    protected static final String FIRST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFirstName() <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
    protected String firstName = FIRST_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAccount() <em>Account</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getAccount()
	 * @generated
	 * @ordered
	 */
    protected EList account = null;

	/**
	 * The cached value of the '{@link #getOrder() <em>Order</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getOrder()
	 * @generated
	 * @ordered
	 */
    protected EList order = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected CustomerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return OrderSystemPackage.eINSTANCE.getCustomer();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getLastName() {
		return lastName;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setLastName(String newLastName) {
		String oldLastName = lastName;
		lastName = newLastName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.CUSTOMER__LAST_NAME, oldLastName, lastName));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getFirstName() {
		return firstName;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setFirstName(String newFirstName) {
		String oldFirstName = firstName;
		firstName = newFirstName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.CUSTOMER__FIRST_NAME, oldFirstName, firstName));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public OrderSystem getOwner() {
		if (eContainerFeatureID != OrderSystemPackage.CUSTOMER__OWNER) return null;
		return (OrderSystem)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setOwner(OrderSystem newOwner) {
		if (newOwner != eContainer || (eContainerFeatureID != OrderSystemPackage.CUSTOMER__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, OrderSystemPackage.ORDER_SYSTEM__CUSTOMER, OrderSystem.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newOwner, OrderSystemPackage.CUSTOMER__OWNER, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.CUSTOMER__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getAccount() {
		if (account == null) {
			account = new EObjectContainmentWithInverseEList(Account.class, this, OrderSystemPackage.CUSTOMER__ACCOUNT, OrderSystemPackage.ACCOUNT__OWNER);
		}
		return account;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getOrder() {
		if (order == null) {
			order = new EObjectContainmentWithInverseEList(Order.class, this, OrderSystemPackage.CUSTOMER__ORDER, OrderSystemPackage.ORDER__OWNER);
		}
		return order;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case OrderSystemPackage.CUSTOMER__OWNER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, OrderSystemPackage.CUSTOMER__OWNER, msgs);
				case OrderSystemPackage.CUSTOMER__ACCOUNT:
					return ((InternalEList)getAccount()).basicAdd(otherEnd, msgs);
				case OrderSystemPackage.CUSTOMER__ORDER:
					return ((InternalEList)getOrder()).basicAdd(otherEnd, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case OrderSystemPackage.CUSTOMER__OWNER:
					return eBasicSetContainer(null, OrderSystemPackage.CUSTOMER__OWNER, msgs);
				case OrderSystemPackage.CUSTOMER__ACCOUNT:
					return ((InternalEList)getAccount()).basicRemove(otherEnd, msgs);
				case OrderSystemPackage.CUSTOMER__ORDER:
					return ((InternalEList)getOrder()).basicRemove(otherEnd, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
		if (eContainerFeatureID >= 0) {
			switch (eContainerFeatureID) {
				case OrderSystemPackage.CUSTOMER__OWNER:
					return (eContainer).eInverseRemove(this, OrderSystemPackage.ORDER_SYSTEM__CUSTOMER, OrderSystem.class, msgs);
				default:
					return eDynamicBasicRemoveFromContainer(msgs);
			}
		}
		return (eContainer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case OrderSystemPackage.CUSTOMER__LAST_NAME:
				return getLastName();
			case OrderSystemPackage.CUSTOMER__FIRST_NAME:
				return getFirstName();
			case OrderSystemPackage.CUSTOMER__OWNER:
				return getOwner();
			case OrderSystemPackage.CUSTOMER__ACCOUNT:
				return getAccount();
			case OrderSystemPackage.CUSTOMER__ORDER:
				return getOrder();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case OrderSystemPackage.CUSTOMER__LAST_NAME:
				setLastName((String)newValue);
				return;
			case OrderSystemPackage.CUSTOMER__FIRST_NAME:
				setFirstName((String)newValue);
				return;
			case OrderSystemPackage.CUSTOMER__OWNER:
				setOwner((OrderSystem)newValue);
				return;
			case OrderSystemPackage.CUSTOMER__ACCOUNT:
				getAccount().clear();
				getAccount().addAll((Collection)newValue);
				return;
			case OrderSystemPackage.CUSTOMER__ORDER:
				getOrder().clear();
				getOrder().addAll((Collection)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case OrderSystemPackage.CUSTOMER__LAST_NAME:
				setLastName(LAST_NAME_EDEFAULT);
				return;
			case OrderSystemPackage.CUSTOMER__FIRST_NAME:
				setFirstName(FIRST_NAME_EDEFAULT);
				return;
			case OrderSystemPackage.CUSTOMER__OWNER:
				setOwner((OrderSystem)null);
				return;
			case OrderSystemPackage.CUSTOMER__ACCOUNT:
				getAccount().clear();
				return;
			case OrderSystemPackage.CUSTOMER__ORDER:
				getOrder().clear();
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case OrderSystemPackage.CUSTOMER__LAST_NAME:
				return LAST_NAME_EDEFAULT == null ? lastName != null : !LAST_NAME_EDEFAULT.equals(lastName);
			case OrderSystemPackage.CUSTOMER__FIRST_NAME:
				return FIRST_NAME_EDEFAULT == null ? firstName != null : !FIRST_NAME_EDEFAULT.equals(firstName);
			case OrderSystemPackage.CUSTOMER__OWNER:
				return getOwner() != null;
			case OrderSystemPackage.CUSTOMER__ACCOUNT:
				return account != null && !account.isEmpty();
			case OrderSystemPackage.CUSTOMER__ORDER:
				return order != null && !order.isEmpty();
		}
		return eDynamicIsSet(eFeature);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(32);
        
        result.append("Customer["); //$NON-NLS-1$
        result.append(firstName);
        result.append(' ');
        result.append(lastName);
        result.append(']');
        
        return result.toString();
    }

} //CustomerImpl
