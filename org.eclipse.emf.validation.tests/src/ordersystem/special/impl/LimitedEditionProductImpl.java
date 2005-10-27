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

package ordersystem.special.impl;

import java.util.Date;

import ordersystem.OrderSystem;
import ordersystem.OrderSystemPackage;

import ordersystem.impl.ProductImpl;

import ordersystem.special.LimitedEditionProduct;
import ordersystem.special.SpecialPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Limited Edition Product</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ordersystem.special.impl.LimitedEditionProductImpl#getAvailableUntil <em>Available Until</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LimitedEditionProductImpl extends ProductImpl implements LimitedEditionProduct {
	/**
	 * The default value of the '{@link #getAvailableUntil() <em>Available Until</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAvailableUntil()
	 * @generated
	 * @ordered
	 */
	protected static final Date AVAILABLE_UNTIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAvailableUntil() <em>Available Until</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAvailableUntil()
	 * @generated
	 * @ordered
	 */
	protected Date availableUntil = AVAILABLE_UNTIL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LimitedEditionProductImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SpecialPackage.eINSTANCE.getLimitedEditionProduct();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getAvailableUntil() {
		return availableUntil;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAvailableUntil(Date newAvailableUntil) {
		Date oldAvailableUntil = availableUntil;
		availableUntil = newAvailableUntil;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SpecialPackage.LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL, oldAvailableUntil, availableUntil));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case SpecialPackage.LIMITED_EDITION_PRODUCT__OWNER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, SpecialPackage.LIMITED_EDITION_PRODUCT__OWNER, msgs);
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
				case SpecialPackage.LIMITED_EDITION_PRODUCT__OWNER:
					return eBasicSetContainer(null, SpecialPackage.LIMITED_EDITION_PRODUCT__OWNER, msgs);
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
				case SpecialPackage.LIMITED_EDITION_PRODUCT__OWNER:
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
			case SpecialPackage.LIMITED_EDITION_PRODUCT__NAME:
				return getName();
			case SpecialPackage.LIMITED_EDITION_PRODUCT__SKU:
				return getSku();
			case SpecialPackage.LIMITED_EDITION_PRODUCT__PRICE:
				return new Double(getPrice());
			case SpecialPackage.LIMITED_EDITION_PRODUCT__OWNER:
				return getOwner();
			case SpecialPackage.LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL:
				return getAvailableUntil();
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
			case SpecialPackage.LIMITED_EDITION_PRODUCT__NAME:
				setName((String)newValue);
				return;
			case SpecialPackage.LIMITED_EDITION_PRODUCT__SKU:
				setSku((String)newValue);
				return;
			case SpecialPackage.LIMITED_EDITION_PRODUCT__PRICE:
				setPrice(((Double)newValue).doubleValue());
				return;
			case SpecialPackage.LIMITED_EDITION_PRODUCT__OWNER:
				setOwner((OrderSystem)newValue);
				return;
			case SpecialPackage.LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL:
				setAvailableUntil((Date)newValue);
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
			case SpecialPackage.LIMITED_EDITION_PRODUCT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SpecialPackage.LIMITED_EDITION_PRODUCT__SKU:
				setSku(SKU_EDEFAULT);
				return;
			case SpecialPackage.LIMITED_EDITION_PRODUCT__PRICE:
				setPrice(PRICE_EDEFAULT);
				return;
			case SpecialPackage.LIMITED_EDITION_PRODUCT__OWNER:
				setOwner((OrderSystem)null);
				return;
			case SpecialPackage.LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL:
				setAvailableUntil(AVAILABLE_UNTIL_EDEFAULT);
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
			case SpecialPackage.LIMITED_EDITION_PRODUCT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SpecialPackage.LIMITED_EDITION_PRODUCT__SKU:
				return SKU_EDEFAULT == null ? sku != null : !SKU_EDEFAULT.equals(sku);
			case SpecialPackage.LIMITED_EDITION_PRODUCT__PRICE:
				return price != PRICE_EDEFAULT;
			case SpecialPackage.LIMITED_EDITION_PRODUCT__OWNER:
				return getOwner() != null;
			case SpecialPackage.LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL:
				return AVAILABLE_UNTIL_EDEFAULT == null ? availableUntil != null : !AVAILABLE_UNTIL_EDEFAULT.equals(availableUntil);
		}
		return eDynamicIsSet(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (availableUntil: "); //$NON-NLS-1$
		result.append(availableUntil);
		result.append(')');
		return result.toString();
	}

} //LimitedEditionProductImpl
