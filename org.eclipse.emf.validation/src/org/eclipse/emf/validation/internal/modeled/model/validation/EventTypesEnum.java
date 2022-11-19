/******************************************************************************
 * Copyright (c) 2009 SAP AG and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    SAP AG - initial API and implementation 
 ****************************************************************************/
package org.eclipse.emf.validation.internal.modeled.model.validation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Event Types Enum</b></em>', and utility methods for working with
 * them. <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getEventTypesEnum()
 * @model
 * @generated
 * @since 1.4
 */
public enum EventTypesEnum implements Enumerator {
	/**
	 * The '<em><b>Add</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #ADD_VALUE
	 * @generated
	 * @ordered
	 */
	ADD(0, "Add", "Add"),

	/**
	 * The '<em><b>Add Many</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #ADD_MANY_VALUE
	 * @generated
	 * @ordered
	 */
	ADD_MANY(1, "Add_Many", "Add_Many"),

	/**
	 * The '<em><b>Create</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #CREATE_VALUE
	 * @generated
	 * @ordered
	 */
	CREATE(2, "Create", "Create"),

	/**
	 * The '<em><b>Move</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #MOVE_VALUE
	 * @generated
	 * @ordered
	 */
	MOVE(3, "Move", "Move"),

	/**
	 * The '<em><b>Remove</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #REMOVE_VALUE
	 * @generated
	 * @ordered
	 */
	REMOVE(4, "Remove", "Remove"),

	/**
	 * The '<em><b>Remove Many</b></em>' literal object. <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #REMOVE_MANY_VALUE
	 * @generated
	 * @ordered
	 */
	REMOVE_MANY(5, "Remove_Many", "Remove_Many"),

	/**
	 * The '<em><b>Removing Adapter</b></em>' literal object. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #REMOVING_ADAPTER_VALUE
	 * @generated
	 * @ordered
	 */
	REMOVING_ADAPTER(6, "Removing_Adapter", "Removing_Adapter"),

	/**
	 * The '<em><b>Resolve</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #RESOLVE_VALUE
	 * @generated
	 * @ordered
	 */
	RESOLVE(7, "Resolve", "Resolve"),

	/**
	 * The '<em><b>Set</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #SET_VALUE
	 * @generated
	 * @ordered
	 */
	SET(8, "Set", "Set"),

	/**
	 * The '<em><b>Unset</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #UNSET_VALUE
	 * @generated
	 * @ordered
	 */
	UNSET(9, "Unset", "Unset");

	/**
	 * The '<em><b>Add</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Add</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #ADD
	 * @model name="Add"
	 * @generated
	 * @ordered
	 */
	public static final int ADD_VALUE = 0;

	/**
	 * The '<em><b>Add Many</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Add Many</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #ADD_MANY
	 * @model name="Add_Many"
	 * @generated
	 * @ordered
	 */
	public static final int ADD_MANY_VALUE = 1;

	/**
	 * The '<em><b>Create</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Create</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #CREATE
	 * @model name="Create"
	 * @generated
	 * @ordered
	 */
	public static final int CREATE_VALUE = 2;

	/**
	 * The '<em><b>Move</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Move</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #MOVE
	 * @model name="Move"
	 * @generated
	 * @ordered
	 */
	public static final int MOVE_VALUE = 3;

	/**
	 * The '<em><b>Remove</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Remove</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #REMOVE
	 * @model name="Remove"
	 * @generated
	 * @ordered
	 */
	public static final int REMOVE_VALUE = 4;

	/**
	 * The '<em><b>Remove Many</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Remove Many</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #REMOVE_MANY
	 * @model name="Remove_Many"
	 * @generated
	 * @ordered
	 */
	public static final int REMOVE_MANY_VALUE = 5;

	/**
	 * The '<em><b>Removing Adapter</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Removing Adapter</b></em>' literal object isn't
	 * clear, there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #REMOVING_ADAPTER
	 * @model name="Removing_Adapter"
	 * @generated
	 * @ordered
	 */
	public static final int REMOVING_ADAPTER_VALUE = 6;

	/**
	 * The '<em><b>Resolve</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Resolve</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #RESOLVE
	 * @model name="Resolve"
	 * @generated
	 * @ordered
	 */
	public static final int RESOLVE_VALUE = 7;

	/**
	 * The '<em><b>Set</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Set</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #SET
	 * @model name="Set"
	 * @generated
	 * @ordered
	 */
	public static final int SET_VALUE = 8;

	/**
	 * The '<em><b>Unset</b></em>' literal value. <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Unset</b></em>' literal object isn't clear, there
	 * really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * 
	 * @see #UNSET
	 * @model name="Unset"
	 * @generated
	 * @ordered
	 */
	public static final int UNSET_VALUE = 9;

	/**
	 * An array of all the '<em><b>Event Types Enum</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final EventTypesEnum[] VALUES_ARRAY = new EventTypesEnum[] { ADD, ADD_MANY, CREATE, MOVE, REMOVE,
			REMOVE_MANY, REMOVING_ADAPTER, RESOLVE, SET, UNSET, };

	/**
	 * A public read-only list of all the '<em><b>Event Types Enum</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<EventTypesEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Event Types Enum</b></em>' literal with the specified
	 * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static EventTypesEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EventTypesEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Event Types Enum</b></em>' literal with the specified
	 * name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static EventTypesEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			EventTypesEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Event Types Enum</b></em>' literal with the specified
	 * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static EventTypesEnum get(int value) {
		switch (value) {
		case ADD_VALUE:
			return ADD;
		case ADD_MANY_VALUE:
			return ADD_MANY;
		case CREATE_VALUE:
			return CREATE;
		case MOVE_VALUE:
			return MOVE;
		case REMOVE_VALUE:
			return REMOVE;
		case REMOVE_MANY_VALUE:
			return REMOVE_MANY;
		case REMOVING_ADAPTER_VALUE:
			return REMOVING_ADAPTER;
		case RESOLVE_VALUE:
			return RESOLVE;
		case SET_VALUE:
			return SET;
		case UNSET_VALUE:
			return UNSET;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private EventTypesEnum(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string
	 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // EventTypesEnum
