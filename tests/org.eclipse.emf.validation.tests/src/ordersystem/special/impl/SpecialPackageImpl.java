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
package ordersystem.special.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import ordersystem.OrderSystemPackage;
import ordersystem.impl.OrderSystemPackageImpl;
import ordersystem.special.LimitedEditionProduct;
import ordersystem.special.PreferredCustomer;
import ordersystem.special.SpecialFactory;
import ordersystem.special.SpecialPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 *
 * @generated
 */
public class SpecialPackageImpl extends EPackageImpl implements SpecialPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass preferredCustomerEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass limitedEditionProductEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method
	 * {@link #init init()}, which also performs initialization of the package, or
	 * returns the registered package, if one already exists. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see ordersystem.special.SpecialPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SpecialPackageImpl() {
		super(eNS_URI, SpecialFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and
	 * for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link SpecialPackage#eINSTANCE} when that
	 * field is accessed. Clients should not invoke it directly. Instead, they
	 * should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SpecialPackage init() {
		if (isInited)
			return (SpecialPackage) EPackage.Registry.INSTANCE.getEPackage(SpecialPackage.eNS_URI);

		// Obtain or create and register package
		SpecialPackageImpl theSpecialPackage = (SpecialPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof SpecialPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI)
						: new SpecialPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		OrderSystemPackageImpl theOrderSystemPackage = (OrderSystemPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(OrderSystemPackage.eNS_URI) instanceof OrderSystemPackageImpl
						? EPackage.Registry.INSTANCE.getEPackage(OrderSystemPackage.eNS_URI)
						: OrderSystemPackage.eINSTANCE);

		// Create package meta-data objects
		theSpecialPackage.createPackageContents();
		theOrderSystemPackage.createPackageContents();

		// Initialize created meta-data
		theSpecialPackage.initializePackageContents();
		theOrderSystemPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSpecialPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SpecialPackage.eNS_URI, theSpecialPackage);
		return theSpecialPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getPreferredCustomer() {
		return preferredCustomerEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getPreferredCustomer_Since() {
		return (EAttribute) preferredCustomerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getLimitedEditionProduct() {
		return limitedEditionProductEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getLimitedEditionProduct_AvailableUntil() {
		return (EAttribute) limitedEditionProductEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SpecialFactory getSpecialFactory() {
		return (SpecialFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is guarded to
	 * have no affect on any invocation but its first. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		preferredCustomerEClass = createEClass(PREFERRED_CUSTOMER);
		createEAttribute(preferredCustomerEClass, PREFERRED_CUSTOMER__SINCE);

		limitedEditionProductEClass = createEClass(LIMITED_EDITION_PRODUCT);
		createEAttribute(limitedEditionProductEClass, LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This method is
	 * guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		OrderSystemPackage theOrderSystemPackage = (OrderSystemPackage) EPackage.Registry.INSTANCE
				.getEPackage(OrderSystemPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		preferredCustomerEClass.getESuperTypes().add(theOrderSystemPackage.getCustomer());
		limitedEditionProductEClass.getESuperTypes().add(theOrderSystemPackage.getProduct());

		// Initialize classes and features; add operations and parameters
		initEClass(preferredCustomerEClass, PreferredCustomer.class, "PreferredCustomer", !IS_ABSTRACT, !IS_INTERFACE, //$NON-NLS-1$
				IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPreferredCustomer_Since(), theOrderSystemPackage.getJavaDate(), "since", null, 0, 1, //$NON-NLS-1$
				PreferredCustomer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(limitedEditionProductEClass, LimitedEditionProduct.class, "LimitedEditionProduct", !IS_ABSTRACT, //$NON-NLS-1$
				!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLimitedEditionProduct_AvailableUntil(), theOrderSystemPackage.getJavaDate(), "availableUntil", //$NON-NLS-1$
				null, 0, 1, LimitedEditionProduct.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE,
				!IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}
} // SpecialPackageImpl
