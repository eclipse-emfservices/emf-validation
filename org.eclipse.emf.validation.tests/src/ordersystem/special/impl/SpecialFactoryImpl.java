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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import ordersystem.special.LimitedEditionProduct;
import ordersystem.special.PreferredCustomer;
import ordersystem.special.SpecialFactory;
import ordersystem.special.SpecialPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class SpecialFactoryImpl extends EFactoryImpl implements SpecialFactory {
	/**
	 * Creates the default factory implementation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	public static SpecialFactory init() {
		try {
			SpecialFactory theSpecialFactory = (SpecialFactory) EPackage.Registry.INSTANCE
					.getEFactory(SpecialPackage.eNS_URI);
			if (theSpecialFactory != null) {
				return theSpecialFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SpecialFactoryImpl();
	}

	/**
	 * Creates an instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @generated
	 */
	public SpecialFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case SpecialPackage.PREFERRED_CUSTOMER:
			return createPreferredCustomer();
		case SpecialPackage.LIMITED_EDITION_PRODUCT:
			return createLimitedEditionProduct();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public PreferredCustomer createPreferredCustomer() {
		PreferredCustomerImpl preferredCustomer = new PreferredCustomerImpl();
		return preferredCustomer;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public LimitedEditionProduct createLimitedEditionProduct() {
		LimitedEditionProductImpl limitedEditionProduct = new LimitedEditionProductImpl();
		return limitedEditionProduct;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public SpecialPackage getSpecialPackage() {
		return (SpecialPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SpecialPackage getPackage() {
		return SpecialPackage.eINSTANCE;
	}
} // SpecialFactoryImpl
