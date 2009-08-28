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
 * $Id: BindingImpl.java,v 1.1 2009/08/28 11:39:50 bgruschko Exp $
 */
package org.eclipse.emf.validation.internal.modeled.model.validation.impl;

import java.util.Collection;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.validation.internal.modeled.model.validation.Binding;
import org.eclipse.emf.validation.internal.modeled.model.validation.Category;
import org.eclipse.emf.validation.internal.modeled.model.validation.ClientContext;
import org.eclipse.emf.validation.internal.modeled.model.validation.Constraint;
import org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.BindingImpl#getClientContexts <em>Client Contexts</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.BindingImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.BindingImpl#getExcludedConstraints <em>Excluded Constraints</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.BindingImpl#getCategories <em>Categories</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.BindingImpl#getExcludedCategories <em>Excluded Categories</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BindingImpl extends EObjectImpl implements Binding {
	/**
	 * The cached value of the '{@link #getClientContexts() <em>Client Contexts</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClientContexts()
	 * @generated
	 * @ordered
	 */
	protected EList<ClientContext> clientContexts;

	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraint> constraints;

	/**
	 * The cached value of the '{@link #getExcludedConstraints() <em>Excluded Constraints</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludedConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraint> excludedConstraints;

	/**
	 * The cached value of the '{@link #getCategories() <em>Categories</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategories()
	 * @generated
	 * @ordered
	 */
	protected EList<Category> categories;

	/**
	 * The cached value of the '{@link #getExcludedCategories() <em>Excluded Categories</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExcludedCategories()
	 * @generated
	 * @ordered
	 */
	protected EList<Category> excludedCategories;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationPackage.Literals.BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ClientContext> getClientContexts() {
		if (clientContexts == null) {
			clientContexts = new EObjectResolvingEList<ClientContext>(ClientContext.class, this, ValidationPackage.BINDING__CLIENT_CONTEXTS);
		}
		return clientContexts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Constraint> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectResolvingEList<Constraint>(Constraint.class, this, ValidationPackage.BINDING__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Constraint> getExcludedConstraints() {
		if (excludedConstraints == null) {
			excludedConstraints = new EObjectResolvingEList<Constraint>(Constraint.class, this, ValidationPackage.BINDING__EXCLUDED_CONSTRAINTS);
		}
		return excludedConstraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Category> getCategories() {
		if (categories == null) {
			categories = new EObjectResolvingEList<Category>(Category.class, this, ValidationPackage.BINDING__CATEGORIES);
		}
		return categories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Category> getExcludedCategories() {
		if (excludedCategories == null) {
			excludedCategories = new EObjectResolvingEList<Category>(Category.class, this, ValidationPackage.BINDING__EXCLUDED_CATEGORIES);
		}
		return excludedCategories;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ValidationPackage.BINDING__CLIENT_CONTEXTS:
				return getClientContexts();
			case ValidationPackage.BINDING__CONSTRAINTS:
				return getConstraints();
			case ValidationPackage.BINDING__EXCLUDED_CONSTRAINTS:
				return getExcludedConstraints();
			case ValidationPackage.BINDING__CATEGORIES:
				return getCategories();
			case ValidationPackage.BINDING__EXCLUDED_CATEGORIES:
				return getExcludedCategories();
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
			case ValidationPackage.BINDING__CLIENT_CONTEXTS:
				getClientContexts().clear();
				getClientContexts().addAll((Collection<? extends ClientContext>)newValue);
				return;
			case ValidationPackage.BINDING__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection<? extends Constraint>)newValue);
				return;
			case ValidationPackage.BINDING__EXCLUDED_CONSTRAINTS:
				getExcludedConstraints().clear();
				getExcludedConstraints().addAll((Collection<? extends Constraint>)newValue);
				return;
			case ValidationPackage.BINDING__CATEGORIES:
				getCategories().clear();
				getCategories().addAll((Collection<? extends Category>)newValue);
				return;
			case ValidationPackage.BINDING__EXCLUDED_CATEGORIES:
				getExcludedCategories().clear();
				getExcludedCategories().addAll((Collection<? extends Category>)newValue);
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
			case ValidationPackage.BINDING__CLIENT_CONTEXTS:
				getClientContexts().clear();
				return;
			case ValidationPackage.BINDING__CONSTRAINTS:
				getConstraints().clear();
				return;
			case ValidationPackage.BINDING__EXCLUDED_CONSTRAINTS:
				getExcludedConstraints().clear();
				return;
			case ValidationPackage.BINDING__CATEGORIES:
				getCategories().clear();
				return;
			case ValidationPackage.BINDING__EXCLUDED_CATEGORIES:
				getExcludedCategories().clear();
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
			case ValidationPackage.BINDING__CLIENT_CONTEXTS:
				return clientContexts != null && !clientContexts.isEmpty();
			case ValidationPackage.BINDING__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
			case ValidationPackage.BINDING__EXCLUDED_CONSTRAINTS:
				return excludedConstraints != null && !excludedConstraints.isEmpty();
			case ValidationPackage.BINDING__CATEGORIES:
				return categories != null && !categories.isEmpty();
			case ValidationPackage.BINDING__EXCLUDED_CATEGORIES:
				return excludedCategories != null && !excludedCategories.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //BindingImpl
