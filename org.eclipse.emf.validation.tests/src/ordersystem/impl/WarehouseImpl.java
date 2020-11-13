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

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import ordersystem.Address;
import ordersystem.InventoryItem;
import ordersystem.OrderSystem;
import ordersystem.OrderSystemPackage;
import ordersystem.Warehouse;

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
    protected EList<InventoryItem> item;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' containment reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
    protected Address location;

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
    @Override
				protected EClass eStaticClass() {
		return OrderSystemPackage.Literals.WAREHOUSE;
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
		if (eContainerFeatureID() != OrderSystemPackage.WAREHOUSE__OWNER) return null;
		return (OrderSystem)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOwner(OrderSystem newOwner, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newOwner, OrderSystemPackage.WAREHOUSE__OWNER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setOwner(OrderSystem newOwner) {
		if (newOwner != eInternalContainer() || (eContainerFeatureID() != OrderSystemPackage.WAREHOUSE__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE, OrderSystem.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
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
    public EList<InventoryItem> getItem() {
		if (item == null) {
			item = new EObjectContainmentWithInverseEList<InventoryItem>(InventoryItem.class, this, OrderSystemPackage.WAREHOUSE__ITEM, OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE);
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
	@SuppressWarnings("unchecked")
		@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OrderSystemPackage.WAREHOUSE__OWNER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetOwner((OrderSystem)otherEnd, msgs);
			case OrderSystemPackage.WAREHOUSE__ITEM:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getItem()).basicAdd(otherEnd, msgs);
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
			case OrderSystemPackage.WAREHOUSE__OWNER:
				return basicSetOwner(null, msgs);
			case OrderSystemPackage.WAREHOUSE__ITEM:
				return ((InternalEList<?>)getItem()).basicRemove(otherEnd, msgs);
			case OrderSystemPackage.WAREHOUSE__LOCATION:
				return basicSetLocation(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case OrderSystemPackage.WAREHOUSE__OWNER:
				return eInternalContainer().eInverseRemove(this, OrderSystemPackage.ORDER_SYSTEM__WAREHOUSE, OrderSystem.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrderSystemPackage.WAREHOUSE__NAME:
				return getName();
			case OrderSystemPackage.WAREHOUSE__OWNER:
				return getOwner();
			case OrderSystemPackage.WAREHOUSE__ITEM:
				return getItem();
			case OrderSystemPackage.WAREHOUSE__LOCATION:
				return getLocation();
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
			case OrderSystemPackage.WAREHOUSE__NAME:
				setName((String)newValue);
				return;
			case OrderSystemPackage.WAREHOUSE__OWNER:
				setOwner((OrderSystem)newValue);
				return;
			case OrderSystemPackage.WAREHOUSE__ITEM:
				getItem().clear();
				getItem().addAll((Collection<? extends InventoryItem>)newValue);
				return;
			case OrderSystemPackage.WAREHOUSE__LOCATION:
				setLocation((Address)newValue);
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
			case OrderSystemPackage.WAREHOUSE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrderSystemPackage.WAREHOUSE__OWNER:
				return getOwner() != null;
			case OrderSystemPackage.WAREHOUSE__ITEM:
				return item != null && !item.isEmpty();
			case OrderSystemPackage.WAREHOUSE__LOCATION:
				return location != null;
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
        
        result.append("Warehouse["); //$NON-NLS-1$
        result.append(name);
        result.append(']');
        
        return result.toString();
    }

} //WarehouseImpl
