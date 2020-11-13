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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Constraint Provider</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#isCache <em>Cache</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getMode <em>Mode</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getPackage <em>Package</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getPluginId <em>Plugin Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintProvider()
 * @model
 * @generated
 * @since 1.4
 */
public interface ConstraintProvider extends EObject {
	/**
	 * Returns the value of the '<em><b>Cache</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cache</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cache</em>' attribute.
	 * @see #setCache(boolean)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintProvider_Cache()
	 * @model
	 * @generated
	 */
	boolean isCache();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#isCache <em>Cache</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cache</em>' attribute.
	 * @see #isCache()
	 * @generated
	 */
	void setCache(boolean value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintProvider_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

	/**
	 * Returns the value of the '<em><b>Target</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.validation.internal.modeled.model.validation.Target}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Target</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Target</em>' containment reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintProvider_Target()
	 * @model containment="true"
	 * @generated
	 */
	EList<Target> getTarget();

	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum
	 * @see #setMode(ModeEnum)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintProvider_Mode()
	 * @model
	 * @generated
	 */
	ModeEnum getMode();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum
	 * @see #getMode()
	 * @generated
	 */
	void setMode(ModeEnum value);

	/**
	 * Returns the value of the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Name</em>' attribute.
	 * @see #setClassName(String)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintProvider_ClassName()
	 * @model
	 * @generated
	 */
	String getClassName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getClassName <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class Name</em>' attribute.
	 * @see #getClassName()
	 * @generated
	 */
	void setClassName(String value);

	/**
	 * Returns the value of the '<em><b>Constraints</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraints}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constraints</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constraints</em>' containment reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintProvider_Constraints()
	 * @model containment="true"
	 * @generated
	 */
	EList<Constraints> getConstraints();

	/**
	 * Returns the value of the '<em><b>Package</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.EPackage}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package</em>' reference list.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintProvider_Package()
	 * @model required="true"
	 * @generated
	 */
	EList<EPackage> getPackage();

	/**
	 * Returns the value of the '<em><b>Plugin Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plugin Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plugin Id</em>' attribute.
	 * @see #setPluginId(String)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getConstraintProvider_PluginId()
	 * @model required="true"
	 * @generated
	 */
	String getPluginId();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getPluginId <em>Plugin Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Plugin Id</em>' attribute.
	 * @see #getPluginId()
	 * @generated
	 */
	void setPluginId(String value);

} // ConstraintProvider
