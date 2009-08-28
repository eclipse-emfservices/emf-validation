/**
 * <copyright>
 * </copyright>
 *
 * $Id: Event.java,v 1.1 2009/08/28 11:39:49 bgruschko Exp $
 */
package org.eclipse.emf.validation.internal.modeled.model.validation;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.Event#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getEvent()
 * @model
 * @generated
 */
public interface Event extends Target {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.validation.internal.modeled.model.validation.EventTypesEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.EventTypesEnum
	 * @see #setName(EventTypesEnum)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getEvent_Name()
	 * @model
	 * @generated
	 */
	EventTypesEnum getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Event#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.EventTypesEnum
	 * @see #getName()
	 * @generated
	 */
	void setName(EventTypesEnum value);

} // Event
