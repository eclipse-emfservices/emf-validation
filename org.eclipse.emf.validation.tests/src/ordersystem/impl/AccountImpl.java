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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import ordersystem.Account;
import ordersystem.Address;
import ordersystem.Customer;
import ordersystem.OrderSystemPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Account</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link ordersystem.impl.AccountImpl#getPaymentMethod <em>Payment
 * Method</em>}</li>
 * <li>{@link ordersystem.impl.AccountImpl#getAccountNumber <em>Account
 * Number</em>}</li>
 * <li>{@link ordersystem.impl.AccountImpl#getOwner <em>Owner</em>}</li>
 * <li>{@link ordersystem.impl.AccountImpl#getBillingAddress <em>Billing
 * Address</em>}</li>
 * <li>{@link ordersystem.impl.AccountImpl#getShippingAddress <em>Shipping
 * Address</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AccountImpl extends EObjectImpl implements Account {
	/**
	 * The default value of the '{@link #getPaymentMethod() <em>Payment
	 * Method</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPaymentMethod()
	 * @generated
	 * @ordered
	 */
	protected static final String PAYMENT_METHOD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPaymentMethod() <em>Payment Method</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getPaymentMethod()
	 * @generated
	 * @ordered
	 */
	protected String paymentMethod = PAYMENT_METHOD_EDEFAULT;

	/**
	 * The default value of the '{@link #getAccountNumber() <em>Account
	 * Number</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAccountNumber()
	 * @generated
	 * @ordered
	 */
	protected static final String ACCOUNT_NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAccountNumber() <em>Account Number</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getAccountNumber()
	 * @generated
	 * @ordered
	 */
	protected String accountNumber = ACCOUNT_NUMBER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBillingAddress() <em>Billing
	 * Address</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getBillingAddress()
	 * @generated
	 * @ordered
	 */
	protected Address billingAddress;

	/**
	 * The cached value of the '{@link #getShippingAddress() <em>Shipping
	 * Address</em>}' containment reference. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #getShippingAddress()
	 * @generated
	 * @ordered
	 */
	protected Address shippingAddress;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected AccountImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrderSystemPackage.Literals.ACCOUNT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPaymentMethod(String newPaymentMethod) {
		String oldPaymentMethod = paymentMethod;
		paymentMethod = newPaymentMethod;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ACCOUNT__PAYMENT_METHOD,
					oldPaymentMethod, paymentMethod));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setAccountNumber(String newAccountNumber) {
		String oldAccountNumber = accountNumber;
		accountNumber = newAccountNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ACCOUNT__ACCOUNT_NUMBER,
					oldAccountNumber, accountNumber));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Customer getOwner() {
		if (eContainerFeatureID() != OrderSystemPackage.ACCOUNT__OWNER)
			return null;
		return (Customer) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetOwner(Customer newOwner, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newOwner, OrderSystemPackage.ACCOUNT__OWNER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setOwner(Customer newOwner) {
		if (newOwner != eInternalContainer()
				|| (eContainerFeatureID() != OrderSystemPackage.ACCOUNT__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject) newOwner).eInverseAdd(this, OrderSystemPackage.CUSTOMER__ACCOUNT,
						Customer.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ACCOUNT__OWNER, newOwner,
					newOwner));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Address getBillingAddress() {
		return billingAddress;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetBillingAddress(Address newBillingAddress, NotificationChain msgs) {
		Address oldBillingAddress = billingAddress;
		billingAddress = newBillingAddress;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OrderSystemPackage.ACCOUNT__BILLING_ADDRESS, oldBillingAddress, newBillingAddress);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setBillingAddress(Address newBillingAddress) {
		if (newBillingAddress != billingAddress) {
			NotificationChain msgs = null;
			if (billingAddress != null)
				msgs = ((InternalEObject) billingAddress).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OrderSystemPackage.ACCOUNT__BILLING_ADDRESS, null, msgs);
			if (newBillingAddress != null)
				msgs = ((InternalEObject) newBillingAddress).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - OrderSystemPackage.ACCOUNT__BILLING_ADDRESS, null, msgs);
			msgs = basicSetBillingAddress(newBillingAddress, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ACCOUNT__BILLING_ADDRESS,
					newBillingAddress, newBillingAddress));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Address getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetShippingAddress(Address newShippingAddress, NotificationChain msgs) {
		Address oldShippingAddress = shippingAddress;
		shippingAddress = newShippingAddress;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					OrderSystemPackage.ACCOUNT__SHIPPING_ADDRESS, oldShippingAddress, newShippingAddress);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setShippingAddress(Address newShippingAddress) {
		if (newShippingAddress != shippingAddress) {
			NotificationChain msgs = null;
			if (shippingAddress != null)
				msgs = ((InternalEObject) shippingAddress).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - OrderSystemPackage.ACCOUNT__SHIPPING_ADDRESS, null, msgs);
			if (newShippingAddress != null)
				msgs = ((InternalEObject) newShippingAddress).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - OrderSystemPackage.ACCOUNT__SHIPPING_ADDRESS, null, msgs);
			msgs = basicSetShippingAddress(newShippingAddress, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ACCOUNT__SHIPPING_ADDRESS,
					newShippingAddress, newShippingAddress));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OrderSystemPackage.ACCOUNT__OWNER:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetOwner((Customer) otherEnd, msgs);
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
		case OrderSystemPackage.ACCOUNT__OWNER:
			return basicSetOwner(null, msgs);
		case OrderSystemPackage.ACCOUNT__BILLING_ADDRESS:
			return basicSetBillingAddress(null, msgs);
		case OrderSystemPackage.ACCOUNT__SHIPPING_ADDRESS:
			return basicSetShippingAddress(null, msgs);
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
		case OrderSystemPackage.ACCOUNT__OWNER:
			return eInternalContainer().eInverseRemove(this, OrderSystemPackage.CUSTOMER__ACCOUNT, Customer.class,
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
		case OrderSystemPackage.ACCOUNT__PAYMENT_METHOD:
			return getPaymentMethod();
		case OrderSystemPackage.ACCOUNT__ACCOUNT_NUMBER:
			return getAccountNumber();
		case OrderSystemPackage.ACCOUNT__OWNER:
			return getOwner();
		case OrderSystemPackage.ACCOUNT__BILLING_ADDRESS:
			return getBillingAddress();
		case OrderSystemPackage.ACCOUNT__SHIPPING_ADDRESS:
			return getShippingAddress();
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
		case OrderSystemPackage.ACCOUNT__PAYMENT_METHOD:
			setPaymentMethod((String) newValue);
			return;
		case OrderSystemPackage.ACCOUNT__ACCOUNT_NUMBER:
			setAccountNumber((String) newValue);
			return;
		case OrderSystemPackage.ACCOUNT__OWNER:
			setOwner((Customer) newValue);
			return;
		case OrderSystemPackage.ACCOUNT__BILLING_ADDRESS:
			setBillingAddress((Address) newValue);
			return;
		case OrderSystemPackage.ACCOUNT__SHIPPING_ADDRESS:
			setShippingAddress((Address) newValue);
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
		case OrderSystemPackage.ACCOUNT__PAYMENT_METHOD:
			setPaymentMethod(PAYMENT_METHOD_EDEFAULT);
			return;
		case OrderSystemPackage.ACCOUNT__ACCOUNT_NUMBER:
			setAccountNumber(ACCOUNT_NUMBER_EDEFAULT);
			return;
		case OrderSystemPackage.ACCOUNT__OWNER:
			setOwner((Customer) null);
			return;
		case OrderSystemPackage.ACCOUNT__BILLING_ADDRESS:
			setBillingAddress((Address) null);
			return;
		case OrderSystemPackage.ACCOUNT__SHIPPING_ADDRESS:
			setShippingAddress((Address) null);
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
		case OrderSystemPackage.ACCOUNT__PAYMENT_METHOD:
			return PAYMENT_METHOD_EDEFAULT == null ? paymentMethod != null
					: !PAYMENT_METHOD_EDEFAULT.equals(paymentMethod);
		case OrderSystemPackage.ACCOUNT__ACCOUNT_NUMBER:
			return ACCOUNT_NUMBER_EDEFAULT == null ? accountNumber != null
					: !ACCOUNT_NUMBER_EDEFAULT.equals(accountNumber);
		case OrderSystemPackage.ACCOUNT__OWNER:
			return getOwner() != null;
		case OrderSystemPackage.ACCOUNT__BILLING_ADDRESS:
			return billingAddress != null;
		case OrderSystemPackage.ACCOUNT__SHIPPING_ADDRESS:
			return shippingAddress != null;
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

		result.append("Account["); //$NON-NLS-1$
		result.append(paymentMethod);
		result.append(", "); //$NON-NLS-1$
		result.append(accountNumber);
		result.append(']');

		return result.toString();
	}

} // AccountImpl
