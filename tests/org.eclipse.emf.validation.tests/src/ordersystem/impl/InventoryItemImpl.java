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

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import ordersystem.InventoryItem;
import ordersystem.OrderSystemPackage;
import ordersystem.Product;
import ordersystem.Warehouse;

/**
 * <!-- begin-user-doc --> An implementation of the model object
 * '<em><b>Inventory Item</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link ordersystem.impl.InventoryItemImpl#getInStock <em>In
 * Stock</em>}</li>
 * <li>{@link ordersystem.impl.InventoryItemImpl#getRestockThreshold <em>Restock
 * Threshold</em>}</li>
 * <li>{@link ordersystem.impl.InventoryItemImpl#getNextStockDate <em>Next Stock
 * Date</em>}</li>
 * <li>{@link ordersystem.impl.InventoryItemImpl#getWarehouse
 * <em>Warehouse</em>}</li>
 * <li>{@link ordersystem.impl.InventoryItemImpl#getProduct
 * <em>Product</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InventoryItemImpl extends EObjectImpl implements InventoryItem {
	/**
	 * The default value of the '{@link #getInStock() <em>In Stock</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getInStock()
	 * @generated
	 * @ordered
	 */
	protected static final int IN_STOCK_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getInStock() <em>In Stock</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getInStock()
	 * @generated
	 * @ordered
	 */
	protected int inStock = IN_STOCK_EDEFAULT;

	/**
	 * The default value of the '{@link #getRestockThreshold() <em>Restock
	 * Threshold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getRestockThreshold()
	 * @generated
	 * @ordered
	 */
	protected static final int RESTOCK_THRESHOLD_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getRestockThreshold() <em>Restock
	 * Threshold</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getRestockThreshold()
	 * @generated
	 * @ordered
	 */
	protected int restockThreshold = RESTOCK_THRESHOLD_EDEFAULT;

	/**
	 * The default value of the '{@link #getNextStockDate() <em>Next Stock
	 * Date</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getNextStockDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date NEXT_STOCK_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNextStockDate() <em>Next Stock
	 * Date</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getNextStockDate()
	 * @generated
	 * @ordered
	 */
	protected Date nextStockDate = NEXT_STOCK_DATE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProduct() <em>Product</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see #getProduct()
	 * @generated
	 * @ordered
	 */
	protected Product product;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected InventoryItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrderSystemPackage.Literals.INVENTORY_ITEM;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getInStock() {
		return inStock;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setInStock(int newInStock) {
		int oldInStock = inStock;
		inStock = newInStock;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.INVENTORY_ITEM__IN_STOCK,
					oldInStock, inStock));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getRestockThreshold() {
		return restockThreshold;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRestockThreshold(int newRestockThreshold) {
		int oldRestockThreshold = restockThreshold;
		restockThreshold = newRestockThreshold;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.INVENTORY_ITEM__RESTOCK_THRESHOLD,
					oldRestockThreshold, restockThreshold));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Date getNextStockDate() {
		return nextStockDate;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setNextStockDate(Date newNextStockDate) {
		Date oldNextStockDate = nextStockDate;
		nextStockDate = newNextStockDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.INVENTORY_ITEM__NEXT_STOCK_DATE,
					oldNextStockDate, nextStockDate));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Warehouse getWarehouse() {
		if (eContainerFeatureID() != OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE)
			return null;
		return (Warehouse) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetWarehouse(Warehouse newWarehouse, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newWarehouse, OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWarehouse(Warehouse newWarehouse) {
		if (newWarehouse != eInternalContainer()
				|| (eContainerFeatureID() != OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE && newWarehouse != null)) {
			if (EcoreUtil.isAncestor(this, newWarehouse))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newWarehouse != null)
				msgs = ((InternalEObject) newWarehouse).eInverseAdd(this, OrderSystemPackage.WAREHOUSE__ITEM,
						Warehouse.class, msgs);
			msgs = basicSetWarehouse(newWarehouse, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE,
					newWarehouse, newWarehouse));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Product getProduct() {
		if (product != null && product.eIsProxy()) {
			InternalEObject oldProduct = (InternalEObject) product;
			product = (Product) eResolveProxy(oldProduct);
			if (product != oldProduct) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
							OrderSystemPackage.INVENTORY_ITEM__PRODUCT, oldProduct, product));
			}
		}
		return product;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public Product basicGetProduct() {
		return product;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setProduct(Product newProduct) {
		Product oldProduct = product;
		product = newProduct;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.INVENTORY_ITEM__PRODUCT,
					oldProduct, product));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE:
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			return basicSetWarehouse((Warehouse) otherEnd, msgs);
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
		case OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE:
			return basicSetWarehouse(null, msgs);
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
		case OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE:
			return eInternalContainer().eInverseRemove(this, OrderSystemPackage.WAREHOUSE__ITEM, Warehouse.class, msgs);
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
		case OrderSystemPackage.INVENTORY_ITEM__IN_STOCK:
			return getInStock();
		case OrderSystemPackage.INVENTORY_ITEM__RESTOCK_THRESHOLD:
			return getRestockThreshold();
		case OrderSystemPackage.INVENTORY_ITEM__NEXT_STOCK_DATE:
			return getNextStockDate();
		case OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE:
			return getWarehouse();
		case OrderSystemPackage.INVENTORY_ITEM__PRODUCT:
			if (resolve)
				return getProduct();
			return basicGetProduct();
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
		case OrderSystemPackage.INVENTORY_ITEM__IN_STOCK:
			setInStock((Integer) newValue);
			return;
		case OrderSystemPackage.INVENTORY_ITEM__RESTOCK_THRESHOLD:
			setRestockThreshold((Integer) newValue);
			return;
		case OrderSystemPackage.INVENTORY_ITEM__NEXT_STOCK_DATE:
			setNextStockDate((Date) newValue);
			return;
		case OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE:
			setWarehouse((Warehouse) newValue);
			return;
		case OrderSystemPackage.INVENTORY_ITEM__PRODUCT:
			setProduct((Product) newValue);
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
		case OrderSystemPackage.INVENTORY_ITEM__IN_STOCK:
			setInStock(IN_STOCK_EDEFAULT);
			return;
		case OrderSystemPackage.INVENTORY_ITEM__RESTOCK_THRESHOLD:
			setRestockThreshold(RESTOCK_THRESHOLD_EDEFAULT);
			return;
		case OrderSystemPackage.INVENTORY_ITEM__NEXT_STOCK_DATE:
			setNextStockDate(NEXT_STOCK_DATE_EDEFAULT);
			return;
		case OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE:
			setWarehouse((Warehouse) null);
			return;
		case OrderSystemPackage.INVENTORY_ITEM__PRODUCT:
			setProduct((Product) null);
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
		case OrderSystemPackage.INVENTORY_ITEM__IN_STOCK:
			return inStock != IN_STOCK_EDEFAULT;
		case OrderSystemPackage.INVENTORY_ITEM__RESTOCK_THRESHOLD:
			return restockThreshold != RESTOCK_THRESHOLD_EDEFAULT;
		case OrderSystemPackage.INVENTORY_ITEM__NEXT_STOCK_DATE:
			return NEXT_STOCK_DATE_EDEFAULT == null ? nextStockDate != null
					: !NEXT_STOCK_DATE_EDEFAULT.equals(nextStockDate);
		case OrderSystemPackage.INVENTORY_ITEM__WAREHOUSE:
			return getWarehouse() != null;
		case OrderSystemPackage.INVENTORY_ITEM__PRODUCT:
			return product != null;
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

		result.append("InventoryItem["); //$NON-NLS-1$
		result.append(inStock);

		if (product != null) {
			result.append(' ');
			result.append(product.getName());
		}

		result.append(", min "); //$NON-NLS-1$
		result.append(restockThreshold);

		if (nextStockDate != null) {
			result.append(", re-stock on "); //$NON-NLS-1$
			result.append(nextStockDate);
		}

		result.append(']');

		return result.toString();
	}

} // InventoryItemImpl
