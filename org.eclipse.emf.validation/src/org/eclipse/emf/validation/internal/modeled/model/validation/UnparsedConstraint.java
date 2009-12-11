/**
 * <copyright>
 * </copyright>
 *
 * $Id: UnparsedConstraint.java,v 1.2 2009/12/11 19:54:50 ahunter Exp $
 */
package org.eclipse.emf.validation.internal.modeled.model.validation;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Unparsed Constraint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.UnparsedConstraint#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getUnparsedConstraint()
 * @model
 * @generated
 * @since 1.4
 */
public interface UnparsedConstraint extends Constraint {
	/**
	 * Returns the value of the '<em><b>Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Body</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Body</em>' attribute.
	 * @see #setBody(String)
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage#getUnparsedConstraint_Body()
	 * @model
	 * @generated
	 */
	String getBody();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.UnparsedConstraint#getBody <em>Body</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Body</em>' attribute.
	 * @see #getBody()
	 * @generated
	 */
	void setBody(String value);

} // UnparsedConstraint
