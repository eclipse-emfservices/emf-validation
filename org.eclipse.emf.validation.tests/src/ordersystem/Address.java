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
package ordersystem;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Address</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link ordersystem.Address#getNumber <em>Number</em>}</li>
 *   <li>{@link ordersystem.Address#getStreet <em>Street</em>}</li>
 *   <li>{@link ordersystem.Address#getApartment <em>Apartment</em>}</li>
 *   <li>{@link ordersystem.Address#getCity <em>City</em>}</li>
 *   <li>{@link ordersystem.Address#getProvince <em>Province</em>}</li>
 *   <li>{@link ordersystem.Address#getPostalCode <em>Postal Code</em>}</li>
 *   <li>{@link ordersystem.Address#getCountry <em>Country</em>}</li>
 * </ul>
 * </p>
 *
 * @see ordersystem.OrderSystemPackage#getAddress()
 * @model
 * @generated
 */
public interface Address extends EObject{
	/**
	 * Returns the value of the '<em><b>Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Number</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Number</em>' attribute.
	 * @see #setNumber(String)
	 * @see ordersystem.OrderSystemPackage#getAddress_Number()
	 * @model
	 * @generated
	 */
    String getNumber();

	/**
	 * Sets the value of the '{@link ordersystem.Address#getNumber <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number</em>' attribute.
	 * @see #getNumber()
	 * @generated
	 */
    void setNumber(String value);

	/**
	 * Returns the value of the '<em><b>Street</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Street</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Street</em>' attribute.
	 * @see #setStreet(String)
	 * @see ordersystem.OrderSystemPackage#getAddress_Street()
	 * @model
	 * @generated
	 */
    String getStreet();

	/**
	 * Sets the value of the '{@link ordersystem.Address#getStreet <em>Street</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Street</em>' attribute.
	 * @see #getStreet()
	 * @generated
	 */
    void setStreet(String value);

	/**
	 * Returns the value of the '<em><b>Apartment</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Apartment</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Apartment</em>' attribute.
	 * @see #setApartment(String)
	 * @see ordersystem.OrderSystemPackage#getAddress_Apartment()
	 * @model
	 * @generated
	 */
    String getApartment();

	/**
	 * Sets the value of the '{@link ordersystem.Address#getApartment <em>Apartment</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Apartment</em>' attribute.
	 * @see #getApartment()
	 * @generated
	 */
    void setApartment(String value);

	/**
	 * Returns the value of the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>City</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>City</em>' attribute.
	 * @see #setCity(String)
	 * @see ordersystem.OrderSystemPackage#getAddress_City()
	 * @model
	 * @generated
	 */
    String getCity();

	/**
	 * Sets the value of the '{@link ordersystem.Address#getCity <em>City</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>City</em>' attribute.
	 * @see #getCity()
	 * @generated
	 */
    void setCity(String value);

	/**
	 * Returns the value of the '<em><b>Province</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Province</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Province</em>' attribute.
	 * @see #setProvince(String)
	 * @see ordersystem.OrderSystemPackage#getAddress_Province()
	 * @model
	 * @generated
	 */
    String getProvince();

	/**
	 * Sets the value of the '{@link ordersystem.Address#getProvince <em>Province</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Province</em>' attribute.
	 * @see #getProvince()
	 * @generated
	 */
    void setProvince(String value);

	/**
	 * Returns the value of the '<em><b>Postal Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Postal Code</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Postal Code</em>' attribute.
	 * @see #setPostalCode(String)
	 * @see ordersystem.OrderSystemPackage#getAddress_PostalCode()
	 * @model
	 * @generated
	 */
    String getPostalCode();

	/**
	 * Sets the value of the '{@link ordersystem.Address#getPostalCode <em>Postal Code</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Postal Code</em>' attribute.
	 * @see #getPostalCode()
	 * @generated
	 */
    void setPostalCode(String value);

	/**
	 * Returns the value of the '<em><b>Country</b></em>' attribute.
	 * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Country</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
	 * @return the value of the '<em>Country</em>' attribute.
	 * @see #setCountry(String)
	 * @see ordersystem.OrderSystemPackage#getAddress_Country()
	 * @model
	 * @generated
	 */
    String getCountry();

	/**
	 * Sets the value of the '{@link ordersystem.Address#getCountry <em>Country</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Country</em>' attribute.
	 * @see #getCountry()
	 * @generated
	 */
    void setCountry(String value);

} // Address
