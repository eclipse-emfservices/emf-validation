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

package ordersystem.special.impl;

import java.util.Collection;
import java.util.Date;

import ordersystem.OrderSystem;
import ordersystem.OrderSystemPackage;

import ordersystem.impl.CustomerImpl;

import ordersystem.special.PreferredCustomer;
import ordersystem.special.SpecialPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Preferred Customer</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ordersystem.special.impl.PreferredCustomerImpl#getSince <em>Since</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PreferredCustomerImpl extends CustomerImpl implements PreferredCustomer {
	/**
	 * The default value of the '{@link #getSince() <em>Since</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSince()
	 * @generated
	 * @ordered
	 */
	protected static final Date SINCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSince() <em>Since</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSince()
	 * @generated
	 * @ordered
	 */
	protected Date since = SINCE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PreferredCustomerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EClass eStaticClass() {
		return SpecialPackage.eINSTANCE.getPreferredCustomer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getSince() {
		return since;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSince(Date newSince) {
		Date oldSince = since;
		since = newSince;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SpecialPackage.PREFERRED_CUSTOMER__SINCE, oldSince, since));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case SpecialPackage.PREFERRED_CUSTOMER__OWNER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, SpecialPackage.PREFERRED_CUSTOMER__OWNER, msgs);
				case SpecialPackage.PREFERRED_CUSTOMER__ACCOUNT:
					return ((InternalEList)getAccount()).basicAdd(otherEnd, msgs);
				case SpecialPackage.PREFERRED_CUSTOMER__ORDER:
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
				case SpecialPackage.PREFERRED_CUSTOMER__OWNER:
					return eBasicSetContainer(null, SpecialPackage.PREFERRED_CUSTOMER__OWNER, msgs);
				case SpecialPackage.PREFERRED_CUSTOMER__ACCOUNT:
					return ((InternalEList)getAccount()).basicRemove(otherEnd, msgs);
				case SpecialPackage.PREFERRED_CUSTOMER__ORDER:
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
				case SpecialPackage.PREFERRED_CUSTOMER__OWNER:
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
			case SpecialPackage.PREFERRED_CUSTOMER__LAST_NAME:
				return getLastName();
			case SpecialPackage.PREFERRED_CUSTOMER__FIRST_NAME:
				return getFirstName();
			case SpecialPackage.PREFERRED_CUSTOMER__OWNER:
				return getOwner();
			case SpecialPackage.PREFERRED_CUSTOMER__ACCOUNT:
				return getAccount();
			case SpecialPackage.PREFERRED_CUSTOMER__ORDER:
				return getOrder();
			case SpecialPackage.PREFERRED_CUSTOMER__SINCE:
				return getSince();
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
			case SpecialPackage.PREFERRED_CUSTOMER__LAST_NAME:
				setLastName((String)newValue);
				return;
			case SpecialPackage.PREFERRED_CUSTOMER__FIRST_NAME:
				setFirstName((String)newValue);
				return;
			case SpecialPackage.PREFERRED_CUSTOMER__OWNER:
				setOwner((OrderSystem)newValue);
				return;
			case SpecialPackage.PREFERRED_CUSTOMER__ACCOUNT:
				getAccount().clear();
				getAccount().addAll((Collection)newValue);
				return;
			case SpecialPackage.PREFERRED_CUSTOMER__ORDER:
				getOrder().clear();
				getOrder().addAll((Collection)newValue);
				return;
			case SpecialPackage.PREFERRED_CUSTOMER__SINCE:
				setSince((Date)newValue);
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
			case SpecialPackage.PREFERRED_CUSTOMER__LAST_NAME:
				setLastName(LAST_NAME_EDEFAULT);
				return;
			case SpecialPackage.PREFERRED_CUSTOMER__FIRST_NAME:
				setFirstName(FIRST_NAME_EDEFAULT);
				return;
			case SpecialPackage.PREFERRED_CUSTOMER__OWNER:
				setOwner((OrderSystem)null);
				return;
			case SpecialPackage.PREFERRED_CUSTOMER__ACCOUNT:
				getAccount().clear();
				return;
			case SpecialPackage.PREFERRED_CUSTOMER__ORDER:
				getOrder().clear();
				return;
			case SpecialPackage.PREFERRED_CUSTOMER__SINCE:
				setSince(SINCE_EDEFAULT);
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
			case SpecialPackage.PREFERRED_CUSTOMER__LAST_NAME:
				return LAST_NAME_EDEFAULT == null ? lastName != null : !LAST_NAME_EDEFAULT.equals(lastName);
			case SpecialPackage.PREFERRED_CUSTOMER__FIRST_NAME:
				return FIRST_NAME_EDEFAULT == null ? firstName != null : !FIRST_NAME_EDEFAULT.equals(firstName);
			case SpecialPackage.PREFERRED_CUSTOMER__OWNER:
				return getOwner() != null;
			case SpecialPackage.PREFERRED_CUSTOMER__ACCOUNT:
				return account != null && !account.isEmpty();
			case SpecialPackage.PREFERRED_CUSTOMER__ORDER:
				return order != null && !order.isEmpty();
			case SpecialPackage.PREFERRED_CUSTOMER__SINCE:
				return SINCE_EDEFAULT == null ? since != null : !SINCE_EDEFAULT.equals(since);
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
		result.append(" (since: "); //$NON-NLS-1$
		result.append(since);
		result.append(')');
		return result.toString();
	}

} //PreferredCustomerImpl
