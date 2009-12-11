/**
 * ******************************************************************************
 *  * Copyright (c) 2009 SAP AG and others.
 *  * All rights reserved. This program and the accompanying materials
 *  * are made available under the terms of the Eclipse Public License v1.0
 *  * which accompanies this distribution, and is available at
 *  * http://www.eclipse.org/legal/epl-v10.html
 *  *
 *  * Contributors:
 *  *    SAP AG - initial API and implementation 
 *  ****************************************************************************
 *
 * $Id: EnablementImpl.java,v 1.2 2009/12/11 19:54:51 ahunter Exp $
 */
package org.eclipse.emf.validation.internal.modeled.model.validation.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.validation.internal.modeled.model.validation.Enablement;
import org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Enablement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.EnablementImpl#getDomExpression <em>Dom Expression</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 * @since 1.4
 */
public class EnablementImpl extends ClientContextImpl implements Enablement {
	/**
	 * The default value of the '{@link #getDomExpression() <em>Dom Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomExpression()
	 * @generated
	 * @ordered
	 */
	protected static final String DOM_EXPRESSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDomExpression() <em>Dom Expression</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomExpression()
	 * @generated
	 * @ordered
	 */
	protected String domExpression = DOM_EXPRESSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EnablementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationPackage.Literals.ENABLEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDomExpression() {
		return domExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomExpression(String newDomExpression) {
		String oldDomExpression = domExpression;
		domExpression = newDomExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.ENABLEMENT__DOM_EXPRESSION, oldDomExpression, domExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ValidationPackage.ENABLEMENT__DOM_EXPRESSION:
				return getDomExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ValidationPackage.ENABLEMENT__DOM_EXPRESSION:
				setDomExpression((String)newValue);
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
			case ValidationPackage.ENABLEMENT__DOM_EXPRESSION:
				setDomExpression(DOM_EXPRESSION_EDEFAULT);
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
			case ValidationPackage.ENABLEMENT__DOM_EXPRESSION:
				return DOM_EXPRESSION_EDEFAULT == null ? domExpression != null : !DOM_EXPRESSION_EDEFAULT.equals(domExpression);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (domExpression: ");
		result.append(domExpression);
		result.append(')');
		return result.toString();
	}

} //EnablementImpl
