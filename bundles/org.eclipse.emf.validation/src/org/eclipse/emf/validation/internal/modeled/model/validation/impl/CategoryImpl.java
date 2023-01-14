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
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.validation.internal.modeled.ValidationModelOperationsImpl;
import org.eclipse.emf.validation.internal.modeled.model.validation.Category;
import org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Category</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.CategoryImpl#getSubCategories
 * <em>Sub Categories</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.CategoryImpl#getId
 * <em>Id</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.CategoryImpl#isMandatory
 * <em>Mandatory</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.CategoryImpl#getName
 * <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.CategoryImpl#getParentCategory
 * <em>Parent Category</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 * @since 1.4
 */
public class CategoryImpl extends EObjectImpl implements Category {
	/**
	 * The cached value of the '{@link #getSubCategories() <em>Sub Categories</em>}'
	 * containment reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getSubCategories()
	 * @generated
	 * @ordered
	 */
	protected EList<Category> subCategories;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #isMandatory() <em>Mandatory</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MANDATORY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMandatory() <em>Mandatory</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isMandatory()
	 * @generated
	 * @ordered
	 */
	protected boolean mandatory = MANDATORY_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected CategoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationPackage.Literals.CATEGORY;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<Category> getSubCategories() {
		if (subCategories == null) {
			subCategories = new EObjectContainmentWithInverseEList<>(Category.class, this,
					ValidationPackage.CATEGORY__SUB_CATEGORIES, ValidationPackage.CATEGORY__PARENT_CATEGORY);
		}
		return subCategories;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CATEGORY__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setMandatory(boolean newMandatory) {
		boolean oldMandatory = mandatory;
		mandatory = newMandatory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CATEGORY__MANDATORY, oldMandatory,
					mandatory));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CATEGORY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Category getParentCategory() {
		if (eContainerFeatureID() != ValidationPackage.CATEGORY__PARENT_CATEGORY)
			return null;
		return (Category) eContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetParentCategory(Category newParentCategory, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newParentCategory, ValidationPackage.CATEGORY__PARENT_CATEGORY,
				msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setParentCategory(Category newParentCategory) {
		if (newParentCategory != eInternalContainer()
				|| (eContainerFeatureID() != ValidationPackage.CATEGORY__PARENT_CATEGORY
						&& newParentCategory != null)) {
			if (EcoreUtil.isAncestor(this, newParentCategory))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newParentCategory != null)
				msgs = ((InternalEObject) newParentCategory).eInverseAdd(this,
						ValidationPackage.CATEGORY__SUB_CATEGORIES, Category.class, msgs);
			msgs = basicSetParentCategory(newParentCategory, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CATEGORY__PARENT_CATEGORY,
					newParentCategory, newParentCategory));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public String getPath() {
		return ValidationModelOperationsImpl.categoryGetPath(this);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ValidationPackage.CATEGORY__SUB_CATEGORIES:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getSubCategories()).basicAdd(otherEnd, msgs);
		case ValidationPackage.CATEGORY__PARENT_CATEGORY:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetParentCategory((Category) otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ValidationPackage.CATEGORY__SUB_CATEGORIES:
			return ((InternalEList<?>) getSubCategories()).basicRemove(otherEnd, msgs);
		case ValidationPackage.CATEGORY__PARENT_CATEGORY:
			return basicSetParentCategory(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case ValidationPackage.CATEGORY__PARENT_CATEGORY:
			return eInternalContainer().eInverseRemove(this, ValidationPackage.CATEGORY__SUB_CATEGORIES, Category.class,
					msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ValidationPackage.CATEGORY__SUB_CATEGORIES:
			return getSubCategories();
		case ValidationPackage.CATEGORY__ID:
			return getId();
		case ValidationPackage.CATEGORY__MANDATORY:
			return isMandatory();
		case ValidationPackage.CATEGORY__NAME:
			return getName();
		case ValidationPackage.CATEGORY__PARENT_CATEGORY:
			return getParentCategory();
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
		case ValidationPackage.CATEGORY__SUB_CATEGORIES:
			getSubCategories().clear();
			getSubCategories().addAll((Collection<? extends Category>) newValue);
			return;
		case ValidationPackage.CATEGORY__ID:
			setId((String) newValue);
			return;
		case ValidationPackage.CATEGORY__MANDATORY:
			setMandatory((Boolean) newValue);
			return;
		case ValidationPackage.CATEGORY__NAME:
			setName((String) newValue);
			return;
		case ValidationPackage.CATEGORY__PARENT_CATEGORY:
			setParentCategory((Category) newValue);
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
		case ValidationPackage.CATEGORY__SUB_CATEGORIES:
			getSubCategories().clear();
			return;
		case ValidationPackage.CATEGORY__ID:
			setId(ID_EDEFAULT);
			return;
		case ValidationPackage.CATEGORY__MANDATORY:
			setMandatory(MANDATORY_EDEFAULT);
			return;
		case ValidationPackage.CATEGORY__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ValidationPackage.CATEGORY__PARENT_CATEGORY:
			setParentCategory((Category) null);
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
		case ValidationPackage.CATEGORY__SUB_CATEGORIES:
			return subCategories != null && !subCategories.isEmpty();
		case ValidationPackage.CATEGORY__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case ValidationPackage.CATEGORY__MANDATORY:
			return mandatory != MANDATORY_EDEFAULT;
		case ValidationPackage.CATEGORY__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case ValidationPackage.CATEGORY__PARENT_CATEGORY:
			return getParentCategory() != null;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", mandatory: ");
		result.append(mandatory);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} // CategoryImpl
