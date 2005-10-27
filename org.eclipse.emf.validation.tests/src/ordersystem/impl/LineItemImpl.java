/******************************************************************************
 * Copyright (c) 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/

package ordersystem.impl;

import ordersystem.LineItem;
import ordersystem.Order;
import ordersystem.OrderSystemPackage;
import ordersystem.Product;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Line Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ordersystem.impl.LineItemImpl#getQuantity <em>Quantity</em>}</li>
 *   <li>{@link ordersystem.impl.LineItemImpl#getDiscount <em>Discount</em>}</li>
 *   <li>{@link ordersystem.impl.LineItemImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link ordersystem.impl.LineItemImpl#getProduct <em>Product</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LineItemImpl extends EObjectImpl implements LineItem {
	/**
	 * The default value of the '{@link #getQuantity() <em>Quantity</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getQuantity()
	 * @generated
	 * @ordered
	 */
    protected static final int QUANTITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getQuantity() <em>Quantity</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getQuantity()
	 * @generated
	 * @ordered
	 */
    protected int quantity = QUANTITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDiscount() <em>Discount</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDiscount()
	 * @generated
	 * @ordered
	 */
    protected static final double DISCOUNT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getDiscount() <em>Discount</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getDiscount()
	 * @generated
	 * @ordered
	 */
    protected double discount = DISCOUNT_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProduct() <em>Product</em>}' reference.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProduct()
	 * @generated
	 * @ordered
	 */
    protected Product product = null;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected LineItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected EClass eStaticClass() {
		return OrderSystemPackage.eINSTANCE.getLineItem();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public int getQuantity() {
		return quantity;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setQuantity(int newQuantity) {
		int oldQuantity = quantity;
		quantity = newQuantity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.LINE_ITEM__QUANTITY, oldQuantity, quantity));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public double getDiscount() {
		return discount;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setDiscount(double newDiscount) {
		double oldDiscount = discount;
		discount = newDiscount;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.LINE_ITEM__DISCOUNT, oldDiscount, discount));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Order getOwner() {
		if (eContainerFeatureID != OrderSystemPackage.LINE_ITEM__OWNER) return null;
		return (Order)eContainer;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setOwner(Order newOwner) {
		if (newOwner != eContainer || (eContainerFeatureID != OrderSystemPackage.LINE_ITEM__OWNER && newOwner != null)) {
			if (EcoreUtil.isAncestor(this, newOwner))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eContainer != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newOwner != null)
				msgs = ((InternalEObject)newOwner).eInverseAdd(this, OrderSystemPackage.ORDER__ITEM, Order.class, msgs);
			msgs = eBasicSetContainer((InternalEObject)newOwner, OrderSystemPackage.LINE_ITEM__OWNER, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.LINE_ITEM__OWNER, newOwner, newOwner));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Product getProduct() {
		if (product != null && product.eIsProxy()) {
			Product oldProduct = product;
			product = (Product)eResolveProxy((InternalEObject)product);
			if (product != oldProduct) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, OrderSystemPackage.LINE_ITEM__PRODUCT, oldProduct, product));
			}
		}
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Product basicGetProduct() {
		return product;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setProduct(Product newProduct) {
		Product oldProduct = product;
		product = newProduct;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.LINE_ITEM__PRODUCT, oldProduct, product));
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public double getCost() {
        if ((getProduct() != null) && (getQuantity() > 0)) {
            return getProduct().getPrice() * getQuantity() * (1.0 - getDiscount());
        } else {
            return 0.0;
        }
    }

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case OrderSystemPackage.LINE_ITEM__OWNER:
					if (eContainer != null)
						msgs = eBasicRemoveFromContainer(msgs);
					return eBasicSetContainer(otherEnd, OrderSystemPackage.LINE_ITEM__OWNER, msgs);
				default:
					return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
			}
		}
		if (eContainer != null)
			msgs = eBasicRemoveFromContainer(msgs);
		return eBasicSetContainer(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
		if (featureID >= 0) {
			switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
				case OrderSystemPackage.LINE_ITEM__OWNER:
					return eBasicSetContainer(null, OrderSystemPackage.LINE_ITEM__OWNER, msgs);
				default:
					return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
			}
		}
		return eBasicSetContainer(null, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public NotificationChain eBasicRemoveFromContainer(NotificationChain msgs) {
		if (eContainerFeatureID >= 0) {
			switch (eContainerFeatureID) {
				case OrderSystemPackage.LINE_ITEM__OWNER:
					return (eContainer).eInverseRemove(this, OrderSystemPackage.ORDER__ITEM, Order.class, msgs);
				default:
					return eDynamicBasicRemoveFromContainer(msgs);
			}
		}
		return (eContainer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public Object eGet(EStructuralFeature eFeature, boolean resolve) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case OrderSystemPackage.LINE_ITEM__QUANTITY:
				return new Integer(getQuantity());
			case OrderSystemPackage.LINE_ITEM__DISCOUNT:
				return new Double(getDiscount());
			case OrderSystemPackage.LINE_ITEM__OWNER:
				return getOwner();
			case OrderSystemPackage.LINE_ITEM__PRODUCT:
				if (resolve) return getProduct();
				return basicGetProduct();
		}
		return eDynamicGet(eFeature, resolve);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void eSet(EStructuralFeature eFeature, Object newValue) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case OrderSystemPackage.LINE_ITEM__QUANTITY:
				setQuantity(((Integer)newValue).intValue());
				return;
			case OrderSystemPackage.LINE_ITEM__DISCOUNT:
				setDiscount(((Double)newValue).doubleValue());
				return;
			case OrderSystemPackage.LINE_ITEM__OWNER:
				setOwner((Order)newValue);
				return;
			case OrderSystemPackage.LINE_ITEM__PRODUCT:
				setProduct((Product)newValue);
				return;
		}
		eDynamicSet(eFeature, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void eUnset(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case OrderSystemPackage.LINE_ITEM__QUANTITY:
				setQuantity(QUANTITY_EDEFAULT);
				return;
			case OrderSystemPackage.LINE_ITEM__DISCOUNT:
				setDiscount(DISCOUNT_EDEFAULT);
				return;
			case OrderSystemPackage.LINE_ITEM__OWNER:
				setOwner((Order)null);
				return;
			case OrderSystemPackage.LINE_ITEM__PRODUCT:
				setProduct((Product)null);
				return;
		}
		eDynamicUnset(eFeature);
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public boolean eIsSet(EStructuralFeature eFeature) {
		switch (eDerivedStructuralFeatureID(eFeature)) {
			case OrderSystemPackage.LINE_ITEM__QUANTITY:
				return quantity != QUANTITY_EDEFAULT;
			case OrderSystemPackage.LINE_ITEM__DISCOUNT:
				return discount != DISCOUNT_EDEFAULT;
			case OrderSystemPackage.LINE_ITEM__OWNER:
				return getOwner() != null;
			case OrderSystemPackage.LINE_ITEM__PRODUCT:
				return product != null;
		}
		return eDynamicIsSet(eFeature);
	}

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(32);
        
        result.append("LineItem["); //$NON-NLS-1$
        result.append(quantity);

        if (product != null ) {
            result.append(' ');
            result.append(product.getName());
        }
        
        if (discount > 0.0) {
            result.append(", "); //$NON-NLS-1$
            result.append(discount * 100.0);
            result.append(" % off"); //$NON-NLS-1$
        }
        
        result.append(']');
        
        return result.toString();
    }

} //LineItemImpl
