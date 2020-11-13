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
package ordersystem.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import ordersystem.Address;
import ordersystem.OrderSystemPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Address</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link ordersystem.impl.AddressImpl#getNumber <em>Number</em>}</li>
 *   <li>{@link ordersystem.impl.AddressImpl#getStreet <em>Street</em>}</li>
 *   <li>{@link ordersystem.impl.AddressImpl#getApartment <em>Apartment</em>}</li>
 *   <li>{@link ordersystem.impl.AddressImpl#getCity <em>City</em>}</li>
 *   <li>{@link ordersystem.impl.AddressImpl#getProvince <em>Province</em>}</li>
 *   <li>{@link ordersystem.impl.AddressImpl#getPostalCode <em>Postal Code</em>}</li>
 *   <li>{@link ordersystem.impl.AddressImpl#getCountry <em>Country</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AddressImpl extends EObjectImpl implements Address {
	/**
	 * The default value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
    protected static final String NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNumber() <em>Number</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getNumber()
	 * @generated
	 * @ordered
	 */
    protected String number = NUMBER_EDEFAULT;

	/**
	 * The default value of the '{@link #getStreet() <em>Street</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getStreet()
	 * @generated
	 * @ordered
	 */
    protected static final String STREET_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStreet() <em>Street</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getStreet()
	 * @generated
	 * @ordered
	 */
    protected String street = STREET_EDEFAULT;

	/**
	 * The default value of the '{@link #getApartment() <em>Apartment</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getApartment()
	 * @generated
	 * @ordered
	 */
    protected static final String APARTMENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getApartment() <em>Apartment</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getApartment()
	 * @generated
	 * @ordered
	 */
    protected String apartment = APARTMENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getCity() <em>City</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getCity()
	 * @generated
	 * @ordered
	 */
    protected static final String CITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCity() <em>City</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getCity()
	 * @generated
	 * @ordered
	 */
    protected String city = CITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getProvince() <em>Province</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProvince()
	 * @generated
	 * @ordered
	 */
    protected static final String PROVINCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProvince() <em>Province</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getProvince()
	 * @generated
	 * @ordered
	 */
    protected String province = PROVINCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPostalCode() <em>Postal Code</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPostalCode()
	 * @generated
	 * @ordered
	 */
    protected static final String POSTAL_CODE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPostalCode() <em>Postal Code</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getPostalCode()
	 * @generated
	 * @ordered
	 */
    protected String postalCode = POSTAL_CODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCountry() <em>Country</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getCountry()
	 * @generated
	 * @ordered
	 */
    protected static final String COUNTRY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCountry() <em>Country</em>}' attribute.
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @see #getCountry()
	 * @generated
	 * @ordered
	 */
    protected String country = COUNTRY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    protected AddressImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
				protected EClass eStaticClass() {
		return OrderSystemPackage.Literals.ADDRESS;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getNumber() {
		return number;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setNumber(String newNumber) {
		String oldNumber = number;
		number = newNumber;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ADDRESS__NUMBER, oldNumber, number));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getStreet() {
		return street;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setStreet(String newStreet) {
		String oldStreet = street;
		street = newStreet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ADDRESS__STREET, oldStreet, street));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getApartment() {
		return apartment;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setApartment(String newApartment) {
		String oldApartment = apartment;
		apartment = newApartment;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ADDRESS__APARTMENT, oldApartment, apartment));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getCity() {
		return city;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setCity(String newCity) {
		String oldCity = city;
		city = newCity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ADDRESS__CITY, oldCity, city));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getProvince() {
		return province;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setProvince(String newProvince) {
		String oldProvince = province;
		province = newProvince;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ADDRESS__PROVINCE, oldProvince, province));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getPostalCode() {
		return postalCode;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setPostalCode(String newPostalCode) {
		String oldPostalCode = postalCode;
		postalCode = newPostalCode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ADDRESS__POSTAL_CODE, oldPostalCode, postalCode));
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public String getCountry() {
		return country;
	}

	/**
	 * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
	 * @generated
	 */
    public void setCountry(String newCountry) {
		String oldCountry = country;
		country = newCountry;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OrderSystemPackage.ADDRESS__COUNTRY, oldCountry, country));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OrderSystemPackage.ADDRESS__NUMBER:
				return getNumber();
			case OrderSystemPackage.ADDRESS__STREET:
				return getStreet();
			case OrderSystemPackage.ADDRESS__APARTMENT:
				return getApartment();
			case OrderSystemPackage.ADDRESS__CITY:
				return getCity();
			case OrderSystemPackage.ADDRESS__PROVINCE:
				return getProvince();
			case OrderSystemPackage.ADDRESS__POSTAL_CODE:
				return getPostalCode();
			case OrderSystemPackage.ADDRESS__COUNTRY:
				return getCountry();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OrderSystemPackage.ADDRESS__NUMBER:
				setNumber((String)newValue);
				return;
			case OrderSystemPackage.ADDRESS__STREET:
				setStreet((String)newValue);
				return;
			case OrderSystemPackage.ADDRESS__APARTMENT:
				setApartment((String)newValue);
				return;
			case OrderSystemPackage.ADDRESS__CITY:
				setCity((String)newValue);
				return;
			case OrderSystemPackage.ADDRESS__PROVINCE:
				setProvince((String)newValue);
				return;
			case OrderSystemPackage.ADDRESS__POSTAL_CODE:
				setPostalCode((String)newValue);
				return;
			case OrderSystemPackage.ADDRESS__COUNTRY:
				setCountry((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case OrderSystemPackage.ADDRESS__NUMBER:
				setNumber(NUMBER_EDEFAULT);
				return;
			case OrderSystemPackage.ADDRESS__STREET:
				setStreet(STREET_EDEFAULT);
				return;
			case OrderSystemPackage.ADDRESS__APARTMENT:
				setApartment(APARTMENT_EDEFAULT);
				return;
			case OrderSystemPackage.ADDRESS__CITY:
				setCity(CITY_EDEFAULT);
				return;
			case OrderSystemPackage.ADDRESS__PROVINCE:
				setProvince(PROVINCE_EDEFAULT);
				return;
			case OrderSystemPackage.ADDRESS__POSTAL_CODE:
				setPostalCode(POSTAL_CODE_EDEFAULT);
				return;
			case OrderSystemPackage.ADDRESS__COUNTRY:
				setCountry(COUNTRY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case OrderSystemPackage.ADDRESS__NUMBER:
				return NUMBER_EDEFAULT == null ? number != null : !NUMBER_EDEFAULT.equals(number);
			case OrderSystemPackage.ADDRESS__STREET:
				return STREET_EDEFAULT == null ? street != null : !STREET_EDEFAULT.equals(street);
			case OrderSystemPackage.ADDRESS__APARTMENT:
				return APARTMENT_EDEFAULT == null ? apartment != null : !APARTMENT_EDEFAULT.equals(apartment);
			case OrderSystemPackage.ADDRESS__CITY:
				return CITY_EDEFAULT == null ? city != null : !CITY_EDEFAULT.equals(city);
			case OrderSystemPackage.ADDRESS__PROVINCE:
				return PROVINCE_EDEFAULT == null ? province != null : !PROVINCE_EDEFAULT.equals(province);
			case OrderSystemPackage.ADDRESS__POSTAL_CODE:
				return POSTAL_CODE_EDEFAULT == null ? postalCode != null : !POSTAL_CODE_EDEFAULT.equals(postalCode);
			case OrderSystemPackage.ADDRESS__COUNTRY:
				return COUNTRY_EDEFAULT == null ? country != null : !COUNTRY_EDEFAULT.equals(country);
		}
		return super.eIsSet(featureID);
	}

	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(32);
        
        result.append("Address["); //$NON-NLS-1$
        result.append(number);
        result.append(' ');
        result.append(street);

        if (apartment != null) {
            result.append(", Apartment "); //$NON-NLS-1$
            result.append(apartment);
        }
        
        if (city != null) {
            result.append(", "); //$NON-NLS-1$
            result.append(city);
        }
        
        if (province != null) {
            result.append(", "); //$NON-NLS-1$
            result.append(province);
        }
        
        if (postalCode != null) {
            result.append(", "); //$NON-NLS-1$
            result.append(postalCode);
        }
        
        if (country != null) {
            result.append(", "); //$NON-NLS-1$
            result.append(country);
        }
        
        result.append(']');
        
        return result.toString();
    }

} //AddressImpl
