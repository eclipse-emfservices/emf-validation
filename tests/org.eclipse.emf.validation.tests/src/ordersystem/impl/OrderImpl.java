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
import java.util.Date;

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

import ordersystem.Customer;
import ordersystem.LineItem;
import ordersystem.Order;
import ordersystem.OrderSystemPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Order</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link ordersystem.impl.OrderImpl#getPlacedOn <em>Placed On</em>}</li>
 * <li>{@link ordersystem.impl.OrderImpl#getFilledOn <em>Filled On</em>}</li>
 * <li>{@link ordersystem.impl.OrderImpl#isCompleted <em>Completed</em>}</li>
 * <li>{@link ordersystem.impl.OrderImpl#getId <em>Id</em>}</li>
 * <li>{@link ordersystem.impl.OrderImpl#getOwner <em>Owner</em>}</li>
 * <li>{@link ordersystem.impl.OrderImpl#getItem <em>Item</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrderImpl extends EObjectImpl implements Order {
	/**
	 * The default value of the '{@link #getPlacedOn() <em>Placed On</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getPlacedOn()
	 * @generated
	 * @ordered
	 */
	protected static final Date PLACED_ON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPlacedOn() <em>Placed On</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getPlacedOn()
	 * @generated
	 * @ordered
	 */
	protected Date placedOn = PLACED_ON_EDEFAULT;

	/**
	 * The default value of the '{@link #getFilledOn() <em>Filled On</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFilledOn()
	 * @generated
	 * @ordered
	 */
	protected static final Date FILLED_ON_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFilledOn() <em>Filled On</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getFilledOn()
	 * @generated
	 * @ordered
	 */
	protected Date filledOn = FILLED_ON_EDEFAULT;

	/**
	 * The default value of the '{@link #isCompleted() <em>Completed</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isCompleted()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COMPLETED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCompleted() <em>Completed</em>}'
	 * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #isCompleted()
	 * @generated
	 * @ordered
	 */
	protected boolean completed = COMPLETED_EDEFAULT;

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
	 * The cached value of the '{@link #getItem() <em>Item</em>}' containment
	 * reference list. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getItem()
	 * @generated
	 * @ordered
	 */
	protected EList<LineItem> item;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected OrderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrderSystemPackage.Literals.ORDER;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Date getPlacedOn() {
		return placedOn;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setPlacedOn(Date newPlacedOn) {
		Date oldPlacedOn = placedOn;
		placedOn = newPlacedOn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ORDER__PLACED_ON, oldPlacedOn,
					placedOn));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Date getFilledOn() {
		return filledOn;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setFilledOn(Date newFilledOn) {
		Date oldFilledOn = filledOn;
		filledOn = newFilledOn;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ORDER__FILLED_ON, oldFilledOn,
					filledOn));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setCompleted(boolean newCompleted) {
		boolean oldCompleted = completed;
		completed = newCompleted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ORDER__COMPLETED, oldCompleted,
					completed));
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
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ORDER__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Customer getOwner() {
		if (eContainerFeatureID() != OrderSystemPackage.ORDER__OWNER)
			return null;
		return (Customer) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetOwner(Customer newOwner, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newOwner, OrderSystemPackage.ORDER__OWNER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setOwner(Customer newOwner) {
		if (newOwner != eInternalContainer()
				|| (eContainerFeatureID() != OrderSystemPackage.ORDER__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject) newOwner).eInverseAdd(this, OrderSystemPackage.CUSTOMER__ORDER,
						Customer.class, msgs);
			msgs = basicSetOwner(newOwner, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ORDER__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<LineItem> getItem() {
		if (item == null) {
			item = new EObjectContainmentWithInverseEList<>(LineItem.class, this,
					OrderSystemPackage.ORDER__ITEM, OrderSystemPackage.LINE_ITEM__OWNER);
		}
		return item;
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
		case OrderSystemPackage.ORDER__OWNER:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetOwner((Customer) otherEnd, msgs);
		case OrderSystemPackage.ORDER__ITEM:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getItem()).basicAdd(otherEnd, msgs);
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
		case OrderSystemPackage.ORDER__OWNER:
			return basicSetOwner(null, msgs);
		case OrderSystemPackage.ORDER__ITEM:
			return ((InternalEList<?>) getItem()).basicRemove(otherEnd, msgs);
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
		case OrderSystemPackage.ORDER__OWNER:
			return eInternalContainer().eInverseRemove(this, OrderSystemPackage.CUSTOMER__ORDER, Customer.class, msgs);
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
		case OrderSystemPackage.ORDER__PLACED_ON:
			return getPlacedOn();
		case OrderSystemPackage.ORDER__FILLED_ON:
			return getFilledOn();
		case OrderSystemPackage.ORDER__COMPLETED:
			return isCompleted();
		case OrderSystemPackage.ORDER__ID:
			return getId();
		case OrderSystemPackage.ORDER__OWNER:
			return getOwner();
		case OrderSystemPackage.ORDER__ITEM:
			return getItem();
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
		case OrderSystemPackage.ORDER__PLACED_ON:
			setPlacedOn((Date) newValue);
			return;
		case OrderSystemPackage.ORDER__FILLED_ON:
			setFilledOn((Date) newValue);
			return;
		case OrderSystemPackage.ORDER__COMPLETED:
			setCompleted((Boolean) newValue);
			return;
		case OrderSystemPackage.ORDER__ID:
			setId((String) newValue);
			return;
		case OrderSystemPackage.ORDER__OWNER:
			setOwner((Customer) newValue);
			return;
		case OrderSystemPackage.ORDER__ITEM:
			getItem().clear();
			getItem().addAll((Collection<? extends LineItem>) newValue);
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
		case OrderSystemPackage.ORDER__PLACED_ON:
			setPlacedOn(PLACED_ON_EDEFAULT);
			return;
		case OrderSystemPackage.ORDER__FILLED_ON:
			setFilledOn(FILLED_ON_EDEFAULT);
			return;
		case OrderSystemPackage.ORDER__COMPLETED:
			setCompleted(COMPLETED_EDEFAULT);
			return;
		case OrderSystemPackage.ORDER__ID:
			setId(ID_EDEFAULT);
			return;
		case OrderSystemPackage.ORDER__OWNER:
			setOwner((Customer) null);
			return;
		case OrderSystemPackage.ORDER__ITEM:
			getItem().clear();
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
		case OrderSystemPackage.ORDER__PLACED_ON:
			return PLACED_ON_EDEFAULT == null ? placedOn != null : !PLACED_ON_EDEFAULT.equals(placedOn);
		case OrderSystemPackage.ORDER__FILLED_ON:
			return FILLED_ON_EDEFAULT == null ? filledOn != null : !FILLED_ON_EDEFAULT.equals(filledOn);
		case OrderSystemPackage.ORDER__COMPLETED:
			return completed != COMPLETED_EDEFAULT;
		case OrderSystemPackage.ORDER__ID:
			return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
		case OrderSystemPackage.ORDER__OWNER:
			return getOwner() != null;
		case OrderSystemPackage.ORDER__ITEM:
			return item != null && !item.isEmpty();
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

		result.append("Order["); //$NON-NLS-1$
		result.append(id);
		result.append(", "); //$NON-NLS-1$
		result.append(placedOn);
		result.append(", "); //$NON-NLS-1$
		result.append(completed);
		result.append(", "); //$NON-NLS-1$
		result.append(filledOn);
		result.append(']');

		return result.toString();
	}

} // OrderImpl
