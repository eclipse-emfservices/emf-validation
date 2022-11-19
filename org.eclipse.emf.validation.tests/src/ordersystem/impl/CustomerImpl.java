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

import ordersystem.Account;
import ordersystem.Customer;
import ordersystem.Order;
import ordersystem.OrderSystem;
import ordersystem.OrderSystemPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Customer</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link ordersystem.impl.CustomerImpl#getLastName <em>Last Name</em>}</li>
 * <li>{@link ordersystem.impl.CustomerImpl#getFirstName <em>First
 * Name</em>}</li>
 * <li>{@link ordersystem.impl.CustomerImpl#getOwner <em>Owner</em>}</li>
 * <li>{@link ordersystem.impl.CustomerImpl#getAccount <em>Account</em>}</li>
 * <li>{@link ordersystem.impl.CustomerImpl#getOrder <em>Order</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CustomerImpl extends EObjectImpl implements Customer {
	/**
	 * The default value of the '{@link #getLastName() <em>Last Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastName() <em>Last Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getLastName()
	 * @generated
	 * @ordered
	 */
	protected String lastName = LAST_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFirstName() <em>First Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
	protected static final String FIRST_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFirstName() <em>First Name</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getFirstName()
	 * @generated
	 * @ordered
	 */
	protected String firstName = FIRST_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAccount() <em>Account</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAccount()
	 * @generated
	 * @ordered
	 */
	protected EList<Account> account;

	/**
	 * The cached value of the '{@link #getOrder() <em>Order</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOrder()
	 * @generated
	 * @ordered
	 */
	protected EList<Order> order;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected CustomerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrderSystemPackage.Literals.CUSTOMER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setLastName(String newLastName) {
		String oldLastName = lastName;
		lastName = newLastName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.CUSTOMER__LAST_NAME, oldLastName,
					lastName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setFirstName(String newFirstName) {
		String oldFirstName = firstName;
		firstName = newFirstName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.CUSTOMER__FIRST_NAME, oldFirstName,
					firstName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public OrderSystem getOwner() {
		if (eContainerFeatureID() != OrderSystemPackage.CUSTOMER__OWNER)
			return null;
		return (OrderSystem) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetOwner(OrderSystem newOwner, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newOwner, OrderSystemPackage.CUSTOMER__OWNER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOwner(OrderSystem newOwner) {
		if (newOwner != eInternalContainer()
				|| (eContainerFeatureID() != OrderSystemPackage.CUSTOMER__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject) newOwner).eInverseAdd(this, OrderSystemPackage.ORDER_SYSTEM__CUSTOMER,
						OrderSystem.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.CUSTOMER__OWNER, newOwner,
					newOwner));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Account> getAccount() {
		if (account == null) {
			account = new EObjectContainmentWithInverseEList<Account>(Account.class, this,
					OrderSystemPackage.CUSTOMER__ACCOUNT, OrderSystemPackage.ACCOUNT__OWNER);
		}
		return account;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EList<Order> getOrder() {
		if (order == null) {
			order = new EObjectContainmentWithInverseEList<Order>(Order.class, this, OrderSystemPackage.CUSTOMER__ORDER,
					OrderSystemPackage.ORDER__OWNER);
		}
		return order;
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
		case OrderSystemPackage.CUSTOMER__OWNER:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetOwner((OrderSystem) otherEnd, msgs);
		case OrderSystemPackage.CUSTOMER__ACCOUNT:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getAccount()).basicAdd(otherEnd, msgs);
		case OrderSystemPackage.CUSTOMER__ORDER:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getOrder()).basicAdd(otherEnd, msgs);
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
		case OrderSystemPackage.CUSTOMER__OWNER:
			return basicSetOwner(null, msgs);
		case OrderSystemPackage.CUSTOMER__ACCOUNT:
			return ((InternalEList<?>) getAccount()).basicRemove(otherEnd, msgs);
		case OrderSystemPackage.CUSTOMER__ORDER:
			return ((InternalEList<?>) getOrder()).basicRemove(otherEnd, msgs);
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
		case OrderSystemPackage.CUSTOMER__OWNER:
			return eInternalContainer().eInverseRemove(this, OrderSystemPackage.ORDER_SYSTEM__CUSTOMER,
					OrderSystem.class, msgs);
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
		case OrderSystemPackage.CUSTOMER__LAST_NAME:
			return getLastName();
		case OrderSystemPackage.CUSTOMER__FIRST_NAME:
			return getFirstName();
		case OrderSystemPackage.CUSTOMER__OWNER:
			return getOwner();
		case OrderSystemPackage.CUSTOMER__ACCOUNT:
			return getAccount();
		case OrderSystemPackage.CUSTOMER__ORDER:
			return getOrder();
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
		case OrderSystemPackage.CUSTOMER__LAST_NAME:
			setLastName((String) newValue);
			return;
		case OrderSystemPackage.CUSTOMER__FIRST_NAME:
			setFirstName((String) newValue);
			return;
		case OrderSystemPackage.CUSTOMER__OWNER:
			setOwner((OrderSystem) newValue);
			return;
		case OrderSystemPackage.CUSTOMER__ACCOUNT:
			getAccount().clear();
			getAccount().addAll((Collection<? extends Account>) newValue);
			return;
		case OrderSystemPackage.CUSTOMER__ORDER:
			getOrder().clear();
			getOrder().addAll((Collection<? extends Order>) newValue);
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
		case OrderSystemPackage.CUSTOMER__LAST_NAME:
			setLastName(LAST_NAME_EDEFAULT);
			return;
		case OrderSystemPackage.CUSTOMER__FIRST_NAME:
			setFirstName(FIRST_NAME_EDEFAULT);
			return;
		case OrderSystemPackage.CUSTOMER__OWNER:
			setOwner((OrderSystem) null);
			return;
		case OrderSystemPackage.CUSTOMER__ACCOUNT:
			getAccount().clear();
			return;
		case OrderSystemPackage.CUSTOMER__ORDER:
			getOrder().clear();
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
		case OrderSystemPackage.CUSTOMER__LAST_NAME:
			return LAST_NAME_EDEFAULT == null ? lastName != null : !LAST_NAME_EDEFAULT.equals(lastName);
		case OrderSystemPackage.CUSTOMER__FIRST_NAME:
			return FIRST_NAME_EDEFAULT == null ? firstName != null : !FIRST_NAME_EDEFAULT.equals(firstName);
		case OrderSystemPackage.CUSTOMER__OWNER:
			return getOwner() != null;
		case OrderSystemPackage.CUSTOMER__ACCOUNT:
			return account != null && !account.isEmpty();
		case OrderSystemPackage.CUSTOMER__ORDER:
			return order != null && !order.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(32);

		result.append("Customer["); //$NON-NLS-1$
		result.append(firstName);
		result.append(' ');
		result.append(lastName);
		result.append(']');

		return result.toString();
	}

} // CustomerImpl
