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
 * $Id: ConstraintBindingsBundleImpl.java,v 1.2 2009/12/11 19:54:51 ahunter Exp $
 */
package org.eclipse.emf.validation.internal.modeled.model.validation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.validation.internal.modeled.model.validation.Binding;
import org.eclipse.emf.validation.internal.modeled.model.validation.ClientContext;
import org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle;
import org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constraint Bindings Bundle</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintBindingsBundleImpl#getClientContexts <em>Client Contexts</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintBindingsBundleImpl#getBindings <em>Bindings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 * @since 1.4
 */
public class ConstraintBindingsBundleImpl extends EObjectImpl implements ConstraintBindingsBundle {
	/**
	 * The cached value of the '{@link #getClientContexts() <em>Client Contexts</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClientContexts()
	 * @generated
	 * @ordered
	 */
	protected EList<ClientContext> clientContexts;

	/**
	 * The cached value of the '{@link #getBindings() <em>Bindings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindings()
	 * @generated
	 * @ordered
	 */
	protected EList<Binding> bindings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstraintBindingsBundleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationPackage.Literals.CONSTRAINT_BINDINGS_BUNDLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ClientContext> getClientContexts() {
		if (clientContexts == null) {
			clientContexts = new EObjectContainmentEList<ClientContext>(ClientContext.class, this, ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__CLIENT_CONTEXTS);
		}
		return clientContexts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Binding> getBindings() {
		if (bindings == null) {
			bindings = new EObjectContainmentEList<Binding>(Binding.class, this, ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__BINDINGS);
		}
		return bindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__CLIENT_CONTEXTS:
				return ((InternalEList<?>)getClientContexts()).basicRemove(otherEnd, msgs);
			case ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__BINDINGS:
				return ((InternalEList<?>)getBindings()).basicRemove(otherEnd, msgs);
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
			case ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__CLIENT_CONTEXTS:
				return getClientContexts();
			case ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__BINDINGS:
				return getBindings();
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
			case ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__CLIENT_CONTEXTS:
				getClientContexts().clear();
				getClientContexts().addAll((Collection<? extends ClientContext>)newValue);
				return;
			case ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__BINDINGS:
				getBindings().clear();
				getBindings().addAll((Collection<? extends Binding>)newValue);
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
			case ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__CLIENT_CONTEXTS:
				getClientContexts().clear();
				return;
			case ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__BINDINGS:
				getBindings().clear();
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
			case ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__CLIENT_CONTEXTS:
				return clientContexts != null && !clientContexts.isEmpty();
			case ValidationPackage.CONSTRAINT_BINDINGS_BUNDLE__BINDINGS:
				return bindings != null && !bindings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ConstraintBindingsBundleImpl
