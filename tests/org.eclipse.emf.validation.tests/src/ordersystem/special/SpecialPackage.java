/**
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package ordersystem.special;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import ordersystem.OrderSystemPackage;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains
 * accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 *
 * @see ordersystem.special.SpecialFactory
 * @model kind="package"
 * @generated
 */
public interface SpecialPackage extends EPackage {
	/**
	 * The package name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNAME = "special"; //$NON-NLS-1$

	/**
	 * The package namespace URI. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_URI = "http:///ordersystem/special.ecore"; //$NON-NLS-1$

	/**
	 * The package namespace name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	String eNS_PREFIX = "ordersystem.special"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	SpecialPackage eINSTANCE = ordersystem.special.impl.SpecialPackageImpl.init();

	/**
	 * The meta object id for the
	 * '{@link ordersystem.special.impl.PreferredCustomerImpl <em>Preferred
	 * Customer</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see ordersystem.special.impl.PreferredCustomerImpl
	 * @see ordersystem.special.impl.SpecialPackageImpl#getPreferredCustomer()
	 * @generated
	 */
	int PREFERRED_CUSTOMER = 0;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PREFERRED_CUSTOMER__LAST_NAME = OrderSystemPackage.CUSTOMER__LAST_NAME;

	/**
	 * The feature id for the '<em><b>First Name</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PREFERRED_CUSTOMER__FIRST_NAME = OrderSystemPackage.CUSTOMER__FIRST_NAME;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PREFERRED_CUSTOMER__OWNER = OrderSystemPackage.CUSTOMER__OWNER;

	/**
	 * The feature id for the '<em><b>Account</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PREFERRED_CUSTOMER__ACCOUNT = OrderSystemPackage.CUSTOMER__ACCOUNT;

	/**
	 * The feature id for the '<em><b>Order</b></em>' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PREFERRED_CUSTOMER__ORDER = OrderSystemPackage.CUSTOMER__ORDER;

	/**
	 * The feature id for the '<em><b>Since</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PREFERRED_CUSTOMER__SINCE = OrderSystemPackage.CUSTOMER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Preferred Customer</em>' class.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int PREFERRED_CUSTOMER_FEATURE_COUNT = OrderSystemPackage.CUSTOMER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the
	 * '{@link ordersystem.special.impl.LimitedEditionProductImpl <em>Limited
	 * Edition Product</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @see ordersystem.special.impl.LimitedEditionProductImpl
	 * @see ordersystem.special.impl.SpecialPackageImpl#getLimitedEditionProduct()
	 * @generated
	 */
	int LIMITED_EDITION_PRODUCT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LIMITED_EDITION_PRODUCT__NAME = OrderSystemPackage.PRODUCT__NAME;

	/**
	 * The feature id for the '<em><b>Sku</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LIMITED_EDITION_PRODUCT__SKU = OrderSystemPackage.PRODUCT__SKU;

	/**
	 * The feature id for the '<em><b>Price</b></em>' attribute. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LIMITED_EDITION_PRODUCT__PRICE = OrderSystemPackage.PRODUCT__PRICE;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' container reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LIMITED_EDITION_PRODUCT__OWNER = OrderSystemPackage.PRODUCT__OWNER;

	/**
	 * The feature id for the '<em><b>Available Until</b></em>' attribute. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL = OrderSystemPackage.PRODUCT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Limited Edition Product</em>'
	 * class. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	int LIMITED_EDITION_PRODUCT_FEATURE_COUNT = OrderSystemPackage.PRODUCT_FEATURE_COUNT + 1;

	/**
	 * Returns the meta object for class
	 * '{@link ordersystem.special.PreferredCustomer <em>Preferred Customer</em>}'.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Preferred Customer</em>'.
	 * @see ordersystem.special.PreferredCustomer
	 * @generated
	 */
	EClass getPreferredCustomer();

	/**
	 * Returns the meta object for the attribute
	 * '{@link ordersystem.special.PreferredCustomer#getSince <em>Since</em>}'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Since</em>'.
	 * @see ordersystem.special.PreferredCustomer#getSince()
	 * @see #getPreferredCustomer()
	 * @generated
	 */
	EAttribute getPreferredCustomer_Since();

	/**
	 * Returns the meta object for class
	 * '{@link ordersystem.special.LimitedEditionProduct <em>Limited Edition
	 * Product</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for class '<em>Limited Edition Product</em>'.
	 * @see ordersystem.special.LimitedEditionProduct
	 * @generated
	 */
	EClass getLimitedEditionProduct();

	/**
	 * Returns the meta object for the attribute
	 * '{@link ordersystem.special.LimitedEditionProduct#getAvailableUntil
	 * <em>Available Until</em>}'. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the meta object for the attribute '<em>Available Until</em>'.
	 * @see ordersystem.special.LimitedEditionProduct#getAvailableUntil()
	 * @see #getLimitedEditionProduct()
	 * @generated
	 */
	EAttribute getLimitedEditionProduct_AvailableUntil();

	/**
	 * Returns the factory that creates the instances of the model. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SpecialFactory getSpecialFactory();

	/**
	 * <!-- begin-user-doc --> Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the
		 * '{@link ordersystem.special.impl.PreferredCustomerImpl <em>Preferred
		 * Customer</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see ordersystem.special.impl.PreferredCustomerImpl
		 * @see ordersystem.special.impl.SpecialPackageImpl#getPreferredCustomer()
		 * @generated
		 */
		EClass PREFERRED_CUSTOMER = eINSTANCE.getPreferredCustomer();

		/**
		 * The meta object literal for the '<em><b>Since</b></em>' attribute feature.
		 * <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute PREFERRED_CUSTOMER__SINCE = eINSTANCE.getPreferredCustomer_Since();

		/**
		 * The meta object literal for the
		 * '{@link ordersystem.special.impl.LimitedEditionProductImpl <em>Limited
		 * Edition Product</em>}' class. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @see ordersystem.special.impl.LimitedEditionProductImpl
		 * @see ordersystem.special.impl.SpecialPackageImpl#getLimitedEditionProduct()
		 * @generated
		 */
		EClass LIMITED_EDITION_PRODUCT = eINSTANCE.getLimitedEditionProduct();

		/**
		 * The meta object literal for the '<em><b>Available Until</b></em>' attribute
		 * feature. <!-- begin-user-doc --> <!-- end-user-doc -->
		 *
		 * @generated
		 */
		EAttribute LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL = eINSTANCE.getLimitedEditionProduct_AvailableUntil();

	}

} // SpecialPackage
