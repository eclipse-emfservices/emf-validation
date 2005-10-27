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

package ordersystem.impl;

import ordersystem.OrderSystem;
import ordersystem.OrderSystemPackage;
import ordersystem.Product;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Product</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ordersystem.impl.ProductImpl#getName <em>Name</em>}</li>
 *   <li>{@link ordersystem.impl.ProductImpl#getSku <em>Sku</em>}</li>
 *   <li>{@link ordersystem.impl.ProductImpl#getPrice <em>Price</em>}</li>
 *   <li>{@link ordersystem.impl.ProductImpl#getOwner <em>Owner</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProductImpl extends EObjectImpl implements Product {
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
	 * The default value of the '{@link #getSku() <em>Sku</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSku()
	 * @generated
	 * @ordered
	 */
    protected static final String SKU_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSku() <em>Sku</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getSku()
	 * @generated
	 * @ordered
	 */
    protected String sku = SKU_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrice() <em>Price</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPrice()
	 * @generated
	 * @ordered
	 */
    protected static final double PRICE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getPrice() <em>Price</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPrice()
	 * @generated
	 * @ordered
	 */
    protected double price = PRICE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected ProductImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return OrderSystemPackage.eINSTANCE.getProduct();
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.PRODUCT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getSku() {
		return sku;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setSku(String newSku) {
		String oldSku = sku;
		sku = newSku;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.PRODUCT__SKU, oldSku, sku));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public double getPrice() {
		return price;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPrice(double newPrice) {
		double oldPrice = price;
		price = newPrice;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.PRODUCT__PRICE, oldPrice, price));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public OrderSystem getOwner() {
		if (eContainerFeatureID != OrderSystemPackage.PRODUCT__OWNER) return null;
		return (OrderSystem)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setOwner(OrderSystem newOwner) {
		if (newOwner != eContainer || (eContainerFeatureID != OrderSystemPackage.PRODUCT__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, OrderSystemPackage.ORDER_SYSTEM__PRODUCT, OrderSystem.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newOwner, OrderSystemPackage.PRODUCT__OWNER, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.PRODUCT__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case OrderSystemPackage.PRODUCT__OWNER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, OrderSystemPackage.PRODUCT__OWNER, msgs);
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
				case OrderSystemPackage.PRODUCT__OWNER:
					return eBasicSetContainer(null, OrderSystemPackage.PRODUCT__OWNER, msgs);
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
				case OrderSystemPackage.PRODUCT__OWNER:
					return (eContainer).eInverseRemove(this, OrderSystemPackage.ORDER_SYSTEM__PRODUCT, OrderSystem.class, msgs);
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
			case OrderSystemPackage.PRODUCT__NAME:
				return getName();
			case OrderSystemPackage.PRODUCT__SKU:
				return getSku();
			case OrderSystemPackage.PRODUCT__PRICE:
				return new Double(getPrice());
			case OrderSystemPackage.PRODUCT__OWNER:
				return getOwner();
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
			case OrderSystemPackage.PRODUCT__NAME:
				setName((String)newValue);
				return;
			case OrderSystemPackage.PRODUCT__SKU:
				setSku((String)newValue);
				return;
			case OrderSystemPackage.PRODUCT__PRICE:
				setPrice(((Double)newValue).doubleValue());
				return;
			case OrderSystemPackage.PRODUCT__OWNER:
				setOwner((OrderSystem)newValue);
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
			case OrderSystemPackage.PRODUCT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OrderSystemPackage.PRODUCT__SKU:
				setSku(SKU_EDEFAULT);
				return;
			case OrderSystemPackage.PRODUCT__PRICE:
				setPrice(PRICE_EDEFAULT);
				return;
			case OrderSystemPackage.PRODUCT__OWNER:
				setOwner((OrderSystem)null);
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
			case OrderSystemPackage.PRODUCT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OrderSystemPackage.PRODUCT__SKU:
				return SKU_EDEFAULT == null ? sku != null : !SKU_EDEFAULT.equals(sku);
			case OrderSystemPackage.PRODUCT__PRICE:
				return price != PRICE_EDEFAULT;
			case OrderSystemPackage.PRODUCT__OWNER:
				return getOwner() != null;
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
        
        result.append("Product["); //$NON-NLS-1$
        result.append(name);
        result.append(", "); //$NON-NLS-1$
        result.append(sku);
        result.append(", "); //$NON-NLS-1$
        result.append(price);
        result.append(']');
        
        return result.toString();
    }

} //ProductImpl
