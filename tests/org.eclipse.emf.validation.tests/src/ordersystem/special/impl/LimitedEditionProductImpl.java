/**
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package ordersystem.special.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import ordersystem.impl.ProductImpl;
import ordersystem.special.LimitedEditionProduct;
import ordersystem.special.SpecialPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Limited
 * Edition Product</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link ordersystem.special.impl.LimitedEditionProductImpl#getAvailableUntil
 * <em>Available Until</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LimitedEditionProductImpl extends ProductImpl implements LimitedEditionProduct {
	/**
	 * The default value of the '{@link #getAvailableUntil() <em>Available
	 * Until</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getAvailableUntil()
	 * @generated
	 * @ordered
	 */
	protected static final Date AVAILABLE_UNTIL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAvailableUntil() <em>Available
	 * Until</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getAvailableUntil()
	 * @generated
	 * @ordered
	 */
	protected Date availableUntil = AVAILABLE_UNTIL_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected LimitedEditionProductImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SpecialPackage.Literals.LIMITED_EDITION_PRODUCT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Date getAvailableUntil() {
		return availableUntil;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setAvailableUntil(Date newAvailableUntil) {
		Date oldAvailableUntil = availableUntil;
		availableUntil = newAvailableUntil;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					SpecialPackage.LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL, oldAvailableUntil, availableUntil));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case SpecialPackage.LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL:
			return getAvailableUntil();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case SpecialPackage.LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL:
			setAvailableUntil((Date) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case SpecialPackage.LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL:
			setAvailableUntil(AVAILABLE_UNTIL_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case SpecialPackage.LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL:
			return AVAILABLE_UNTIL_EDEFAULT == null ? availableUntil != null
					: !AVAILABLE_UNTIL_EDEFAULT.equals(availableUntil);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (availableUntil: "); //$NON-NLS-1$
		result.append(availableUntil);
		result.append(')');
		return result.toString();
	}

} // LimitedEditionProductImpl
