/******************************************************************************
 * Copyright (c) 2009 SAP AG and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    SAP AG - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.internal.modeled.model.validation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.validation.internal.modeled.model.validation.Category;
import org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle;
import org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider;
import org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle;
import org.eclipse.emf.validation.internal.modeled.model.validation.Parser;
import org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Constraints Bundle</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsBundleImpl#getProviders
 * <em>Providers</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsBundleImpl#getCategories
 * <em>Categories</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsBundleImpl#getConstraintBindingsBundles
 * <em>Constraint Bindings Bundles</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsBundleImpl#getParsers
 * <em>Parsers</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsBundleImpl#getMessageBundlePath
 * <em>Message Bundle Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 * @since 1.4
 */
public class ConstraintsBundleImpl extends EObjectImpl implements ConstraintsBundle {
	/**
	 * The cached value of the '{@link #getProviders() <em>Providers</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getProviders()
	 * @generated
	 * @ordered
	 */
	protected EList<ConstraintProvider> providers;

	/**
	 * The cached value of the '{@link #getCategories() <em>Categories</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getCategories()
	 * @generated
	 * @ordered
	 */
	protected EList<Category> categories;

	/**
	 * The cached value of the '{@link #getConstraintBindingsBundles()
	 * <em>Constraint Bindings Bundles</em>}' containment reference list. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getConstraintBindingsBundles()
	 * @generated
	 * @ordered
	 */
	protected EList<ConstraintBindingsBundle> constraintBindingsBundles;

	/**
	 * The cached value of the '{@link #getParsers() <em>Parsers</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getParsers()
	 * @generated
	 * @ordered
	 */
	protected EList<Parser> parsers;

	/**
	 * The default value of the '{@link #getMessageBundlePath() <em>Message Bundle
	 * Path</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getMessageBundlePath()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_BUNDLE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessageBundlePath() <em>Message Bundle
	 * Path</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getMessageBundlePath()
	 * @generated
	 * @ordered
	 */
	protected String messageBundlePath = MESSAGE_BUNDLE_PATH_EDEFAULT;

	/**
	 * This is true if the Message Bundle Path attribute has been set. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean messageBundlePathESet;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ConstraintsBundleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationPackage.Literals.CONSTRAINTS_BUNDLE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<ConstraintProvider> getProviders() {
		if (providers == null) {
			providers = new EObjectContainmentEList<>(ConstraintProvider.class, this,
					ValidationPackage.CONSTRAINTS_BUNDLE__PROVIDERS);
		}
		return providers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Category> getCategories() {
		if (categories == null) {
			categories = new EObjectContainmentEList<>(Category.class, this,
					ValidationPackage.CONSTRAINTS_BUNDLE__CATEGORIES);
		}
		return categories;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<ConstraintBindingsBundle> getConstraintBindingsBundles() {
		if (constraintBindingsBundles == null) {
			constraintBindingsBundles = new EObjectContainmentEList<>(
					ConstraintBindingsBundle.class, this,
					ValidationPackage.CONSTRAINTS_BUNDLE__CONSTRAINT_BINDINGS_BUNDLES);
		}
		return constraintBindingsBundles;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Parser> getParsers() {
		if (parsers == null) {
			parsers = new EObjectContainmentEList<>(Parser.class, this,
					ValidationPackage.CONSTRAINTS_BUNDLE__PARSERS);
		}
		return parsers;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getMessageBundlePath() {
		return messageBundlePath;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMessageBundlePath(String newMessageBundlePath) {
		String oldMessageBundlePath = messageBundlePath;
		messageBundlePath = newMessageBundlePath;
		boolean oldMessageBundlePathESet = messageBundlePathESet;
		messageBundlePathESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ValidationPackage.CONSTRAINTS_BUNDLE__MESSAGE_BUNDLE_PATH, oldMessageBundlePath, messageBundlePath,
					!oldMessageBundlePathESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetMessageBundlePath() {
		String oldMessageBundlePath = messageBundlePath;
		boolean oldMessageBundlePathESet = messageBundlePathESet;
		messageBundlePath = MESSAGE_BUNDLE_PATH_EDEFAULT;
		messageBundlePathESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET,
					ValidationPackage.CONSTRAINTS_BUNDLE__MESSAGE_BUNDLE_PATH, oldMessageBundlePath,
					MESSAGE_BUNDLE_PATH_EDEFAULT, oldMessageBundlePathESet));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetMessageBundlePath() {
		return messageBundlePathESet;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ValidationPackage.CONSTRAINTS_BUNDLE__PROVIDERS:
			return ((InternalEList<?>) getProviders()).basicRemove(otherEnd, msgs);
		case ValidationPackage.CONSTRAINTS_BUNDLE__CATEGORIES:
			return ((InternalEList<?>) getCategories()).basicRemove(otherEnd, msgs);
		case ValidationPackage.CONSTRAINTS_BUNDLE__CONSTRAINT_BINDINGS_BUNDLES:
			return ((InternalEList<?>) getConstraintBindingsBundles()).basicRemove(otherEnd, msgs);
		case ValidationPackage.CONSTRAINTS_BUNDLE__PARSERS:
			return ((InternalEList<?>) getParsers()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ValidationPackage.CONSTRAINTS_BUNDLE__PROVIDERS:
			return getProviders();
		case ValidationPackage.CONSTRAINTS_BUNDLE__CATEGORIES:
			return getCategories();
		case ValidationPackage.CONSTRAINTS_BUNDLE__CONSTRAINT_BINDINGS_BUNDLES:
			return getConstraintBindingsBundles();
		case ValidationPackage.CONSTRAINTS_BUNDLE__PARSERS:
			return getParsers();
		case ValidationPackage.CONSTRAINTS_BUNDLE__MESSAGE_BUNDLE_PATH:
			return getMessageBundlePath();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ValidationPackage.CONSTRAINTS_BUNDLE__PROVIDERS:
			getProviders().clear();
			getProviders().addAll((Collection<? extends ConstraintProvider>) newValue);
			return;
		case ValidationPackage.CONSTRAINTS_BUNDLE__CATEGORIES:
			getCategories().clear();
			getCategories().addAll((Collection<? extends Category>) newValue);
			return;
		case ValidationPackage.CONSTRAINTS_BUNDLE__CONSTRAINT_BINDINGS_BUNDLES:
			getConstraintBindingsBundles().clear();
			getConstraintBindingsBundles().addAll((Collection<? extends ConstraintBindingsBundle>) newValue);
			return;
		case ValidationPackage.CONSTRAINTS_BUNDLE__PARSERS:
			getParsers().clear();
			getParsers().addAll((Collection<? extends Parser>) newValue);
			return;
		case ValidationPackage.CONSTRAINTS_BUNDLE__MESSAGE_BUNDLE_PATH:
			setMessageBundlePath((String) newValue);
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
		case ValidationPackage.CONSTRAINTS_BUNDLE__PROVIDERS:
			getProviders().clear();
			return;
		case ValidationPackage.CONSTRAINTS_BUNDLE__CATEGORIES:
			getCategories().clear();
			return;
		case ValidationPackage.CONSTRAINTS_BUNDLE__CONSTRAINT_BINDINGS_BUNDLES:
			getConstraintBindingsBundles().clear();
			return;
		case ValidationPackage.CONSTRAINTS_BUNDLE__PARSERS:
			getParsers().clear();
			return;
		case ValidationPackage.CONSTRAINTS_BUNDLE__MESSAGE_BUNDLE_PATH:
			unsetMessageBundlePath();
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
		case ValidationPackage.CONSTRAINTS_BUNDLE__PROVIDERS:
			return providers != null && !providers.isEmpty();
		case ValidationPackage.CONSTRAINTS_BUNDLE__CATEGORIES:
			return categories != null && !categories.isEmpty();
		case ValidationPackage.CONSTRAINTS_BUNDLE__CONSTRAINT_BINDINGS_BUNDLES:
			return constraintBindingsBundles != null && !constraintBindingsBundles.isEmpty();
		case ValidationPackage.CONSTRAINTS_BUNDLE__PARSERS:
			return parsers != null && !parsers.isEmpty();
		case ValidationPackage.CONSTRAINTS_BUNDLE__MESSAGE_BUNDLE_PATH:
			return isSetMessageBundlePath();
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
		result.append(" (messageBundlePath: ");
		if (messageBundlePathESet)
			result.append(messageBundlePath);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} // ConstraintsBundleImpl
