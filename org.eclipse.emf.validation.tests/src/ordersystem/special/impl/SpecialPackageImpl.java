/**
 * <copyright>
 *
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */

package ordersystem.special.impl;

import ordersystem.OrderSystemPackage;
import ordersystem.impl.OrderSystemPackageImpl;
import ordersystem.special.LimitedEditionProduct;
import ordersystem.special.PreferredCustomer;
import ordersystem.special.SpecialFactory;
import ordersystem.special.SpecialPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SpecialPackageImpl extends EPackageImpl implements SpecialPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass preferredCustomerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass limitedEditionProductEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see ordersystem.special.SpecialPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SpecialPackageImpl() {
		super(eNS_URI, SpecialFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SpecialPackage init() {
		if (isInited) return (SpecialPackage)EPackage.Registry.INSTANCE.getEPackage(SpecialPackage.eNS_URI);

		// Obtain or create and register package
		SpecialPackageImpl theSpecialPackage = (SpecialPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof SpecialPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new SpecialPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		OrderSystemPackageImpl theOrderSystemPackage = (OrderSystemPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrderSystemPackage.eNS_URI) instanceof OrderSystemPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrderSystemPackage.eNS_URI) : OrderSystemPackage.eINSTANCE);

		// Create package meta-data objects
		theSpecialPackage.createPackageContents();
		theOrderSystemPackage.createPackageContents();

		// Initialize created meta-data
		theSpecialPackage.initializePackageContents();
		theOrderSystemPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSpecialPackage.freeze();

		return theSpecialPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPreferredCustomer() {
		return preferredCustomerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPreferredCustomer_Since() {
		return (EAttribute)preferredCustomerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLimitedEditionProduct() {
		return limitedEditionProductEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLimitedEditionProduct_AvailableUntil() {
		return (EAttribute)limitedEditionProductEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SpecialFactory getSpecialFactory() {
		return (SpecialFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		preferredCustomerEClass = createEClass(PREFERRED_CUSTOMER);
		createEAttribute(preferredCustomerEClass, PREFERRED_CUSTOMER__SINCE);

		limitedEditionProductEClass = createEClass(LIMITED_EDITION_PRODUCT);
		createEAttribute(limitedEditionProductEClass, LIMITED_EDITION_PRODUCT__AVAILABLE_UNTIL);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		OrderSystemPackage theOrderSystemPackage = (OrderSystemPackage)EPackage.Registry.INSTANCE.getEPackage(OrderSystemPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		preferredCustomerEClass.getESuperTypes().add(theOrderSystemPackage.getCustomer());
		limitedEditionProductEClass.getESuperTypes().add(theOrderSystemPackage.getProduct());

		// Initialize classes and features; add operations and parameters
		initEClass(preferredCustomerEClass, PreferredCustomer.class, "PreferredCustomer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getPreferredCustomer_Since(), theOrderSystemPackage.getJavaDate(), "since", null, 0, 1, PreferredCustomer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(limitedEditionProductEClass, LimitedEditionProduct.class, "LimitedEditionProduct", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getLimitedEditionProduct_AvailableUntil(), theOrderSystemPackage.getJavaDate(), "availableUntil", null, 0, 1, LimitedEditionProduct.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
	}
} //SpecialPackageImpl
