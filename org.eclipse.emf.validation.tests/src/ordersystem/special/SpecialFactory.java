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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a
 * create method for each non-abstract class of the model. <!-- end-user-doc -->
 * 
 * @see ordersystem.special.SpecialPackage
 * @generated
 */
public interface SpecialFactory extends EFactory {
	/**
	 * The singleton instance of the factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	SpecialFactory eINSTANCE = ordersystem.special.impl.SpecialFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Preferred Customer</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Preferred Customer</em>'.
	 * @generated
	 */
	PreferredCustomer createPreferredCustomer();

	/**
	 * Returns a new object of class '<em>Limited Edition Product</em>'. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @return a new object of class '<em>Limited Edition Product</em>'.
	 * @generated
	 */
	LimitedEditionProduct createLimitedEditionProduct();

	/**
	 * Returns the package supported by this factory. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @return the package supported by this factory.
	 * @generated
	 */
	SpecialPackage getSpecialPackage();

} // SpecialFactory
