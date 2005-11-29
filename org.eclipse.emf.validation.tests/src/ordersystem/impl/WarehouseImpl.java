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

import ordersystem.Address;
import ordersystem.InventoryItem;
import ordersystem.OrderSystem;
import ordersystem.OrderSystemPackage;
import ordersystem.Warehouse;

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
 * An implementation of the model object '<em><b>Warehouse</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ordersystem.impl.WarehouseImpl#getName <em>Name</em>}</li>
 *   <li>{@link ordersystem.impl.WarehouseImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link ordersystem.impl.WarehouseImpl#getItem <em>Item</em>}</li>
 *   <li>{@link ordersystem.impl.WarehouseImpl#getLocation <em>Location</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WarehouseImpl extends EObjectImpl implements Warehouse {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
    protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
    protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getItem() <em>Item</em>}' containment reference list.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getItem()
	 * @generated
	 * @ordered
	 */
    protected EList item = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
    protected Address location = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected WarehouseImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return OrderSystemPackage.eINSTANCE.getWarehouse();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.WAREHOUSE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public OrderSystem getOwner() {
		if (eContainerFeatureID != OrderSystemPackage.WAREHOUSE__OWNER) return null;
		return (OrderSystem)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setOwner(OrderSystem newOwner) {
		if (newOwner != eContainer || (eContainerFeatureID != OrderSystemPackage.WAREHOUSE__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE, OrderSystem.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newOwner, OrderSystemPackage.WAREHOUSE__OWNER, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.WAREHOUSE__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public EList getItem() {
		if (item == null) {
			item = new EObjectContainmentWithInverseEList(InventoryItem.class, this, OrderSystemPackage.WAREHOUSE__ITEM, OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE);
		}
		return item;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Address getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain basicSetLocation(Address newLocation, NotificationChain msgs) {
		Address oldLocation = location;
		location = newLocation;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, OrderSystemPackage.WAREHOUSE__LOCATION, oldLocation, newLocation);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setLocation(Address newLocation) {
		if (newLocation != location) {
			NotificationChain msgs = null;
			if (location != null)
				msgs = ((InternalEObject)location).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - OrderSystemPackage.WAREHOUSE__LOCATION, null, msgs);
			if (newLocation != null)
				msgs = ((InternalEObject)newLocation).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - OrderSystemPackage.WAREHOUSE__LOCATION, null, msgs);
			msgs = basicSetLocation(newLocation, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.WAREHOUSE__LOCATION, newLocation, newLocation));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case OrderSystemPackage.WAREHOUSE__OWNER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, OrderSystemPackage.WAREHOUSE__OWNER, msgs);
				case OrderSystemPackage.WAREHOUSE__ITEM:
					return ((InternalEList)getItem()).basicAdd(otherEnd, msgs);
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
				case OrderSystemPackage.WAREHOUSE__OWNER:
					return eBasicSetContainer(null, OrderSystemPackage.WAREHOUSE__OWNER, msgs);
				case OrderSystemPackage.WAREHOUSE__ITEM:
					return ((InternalEList)getItem()).basicRemove(otherEnd, msgs);
				case OrderSystemPackage.WAREHOUSE__LOCATION:
					return basicSetLocation(null, msgs);
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
				case OrderSystemPackage.WAREHOUSE__OWNER:
					return (eContainer).eInverseRemove(this, OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE, OrderSystem.class, msgs);
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
			case OrderSystemPackage.WAREHOUSE__NAME:
				return getName();
			case OrderSystemPackage.WAREHOUSE__OWNER:
				return getOwner();
			case OrderSystemPackage.WAREHOUSE__ITEM:
				return getItem();
			case OrderSystemPackage.WAREHOUSE__LOCATION:
				return getLocation();
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
			case OrderSystemPackage.WAREHOUSE__NAME:
				setName((String)newValue);
				return;
			case OrderSystemPackage.WAREHOUSE__OWNER:
				setOwner((OrderSystem)newValue);
				return;
			case OrderSystemPackage.WAREHOUSE__ITEM:
				getItem().clear();
				getItem().addAll((Collection)newValue);
				return;
			case OrderSystemPackage.WAREHOUSE__LOCATION:
				setLocation((Address)newValue);
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
			case OrderSystemPackage.WAREHOUSE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrderSystemPackage.WAREHOUSE__OWNER:
				setOwner((OrderSystem)null);
				return;
			case OrderSystemPackage.WAREHOUSE__ITEM:
				getItem().clear();
				return;
			case OrderSystemPackage.WAREHOUSE__LOCATION:
				setLocation((Address)null);
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
			case OrderSystemPackage.WAREHOUSE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrderSystemPackage.WAREHOUSE__OWNER:
				return getOwner() != null;
			case OrderSystemPackage.WAREHOUSE__ITEM:
				return item != null && !item.isEmpty();
			case OrderSystemPackage.WAREHOUSE__LOCATION:
				return location != null;
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
        
        result.append("Warehouse["); //$NON-NLS-1$
        result.append(name);
        result.append(']');
        
        return result.toString();
    }

} //WarehouseImpl
