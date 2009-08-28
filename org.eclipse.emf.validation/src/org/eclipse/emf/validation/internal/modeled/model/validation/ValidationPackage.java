/**
 * <copyright>
 * </copyright>
 *
 * $Id: ValidationPackage.java,v 1.1 2009/08/28 11:39:50 bgruschko Exp $
 */
package org.eclipse.emf.validation.internal.modeled.model.validation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ValidationFactory
 * @model kind="package"
 * @generated
 */
public interface ValidationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "validation";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/emf/2009/Validation";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "validation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ValidationPackage eINSTANCE = org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.CategoryImpl <em>Category</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.CategoryImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getCategory()
	 * @generated
	 */
	int CATEGORY = 0;

	/**
	 * The feature id for the '<em><b>Sub Categories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__SUB_CATEGORIES = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__ID = 1;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__MANDATORY = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__NAME = 3;

	/**
	 * The feature id for the '<em><b>Parent Category</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY__PARENT_CATEGORY = 4;

	/**
	 * The number of structural features of the '<em>Category</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CATEGORY_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl <em>Constraint Provider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getConstraintProvider()
	 * @generated
	 */
	int CONSTRAINT_PROVIDER = 1;

	/**
	 * The feature id for the '<em><b>Cache</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROVIDER__CACHE = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROVIDER__DESCRIPTION = 1;

	/**
	 * The feature id for the '<em><b>Target</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROVIDER__TARGET = 2;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROVIDER__MODE = 3;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROVIDER__CLASS_NAME = 4;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROVIDER__CONSTRAINTS = 5;

	/**
	 * The feature id for the '<em><b>Package</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROVIDER__PACKAGE = 6;

	/**
	 * The feature id for the '<em><b>Plugin Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROVIDER__PLUGIN_ID = 7;

	/**
	 * The number of structural features of the '<em>Constraint Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROVIDER_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.TargetImpl <em>Target</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.TargetImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getTarget()
	 * @generated
	 */
	int TARGET = 2;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__FEATURE = 0;

	/**
	 * The feature id for the '<em><b>EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET__ECLASS = 1;

	/**
	 * The number of structural features of the '<em>Target</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TARGET_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.EventImpl <em>Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.EventImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getEvent()
	 * @generated
	 */
	int EVENT = 3;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__FEATURE = TARGET__FEATURE;

	/**
	 * The feature id for the '<em><b>EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__ECLASS = TARGET__ECLASS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT__NAME = TARGET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_FEATURE_COUNT = TARGET_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.CustomEventImpl <em>Custom Event</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.CustomEventImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getCustomEvent()
	 * @generated
	 */
	int CUSTOM_EVENT = 4;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_EVENT__FEATURE = TARGET__FEATURE;

	/**
	 * The feature id for the '<em><b>EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_EVENT__ECLASS = TARGET__ECLASS;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_EVENT__NAME = TARGET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Custom Event</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CUSTOM_EVENT_FEATURE_COUNT = TARGET_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.FeatureImpl <em>Feature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.FeatureImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getFeature()
	 * @generated
	 */
	int FEATURE = 5;

	/**
	 * The number of structural features of the '<em>Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl <em>Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getConstraint()
	 * @generated
	 */
	int CONSTRAINT = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__SEVERITY = 2;

	/**
	 * The feature id for the '<em><b>Status Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__STATUS_CODE = 3;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__CLASS_NAME = 4;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__MODE = 5;

	/**
	 * The feature id for the '<em><b>Is Enabled By Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__IS_ENABLED_BY_DEFAULT = 6;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__DESCRIPTION = 7;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__MESSAGE = 8;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__PARAMETERS = 9;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__TARGET = 10;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT__LANG = 11;

	/**
	 * The number of structural features of the '<em>Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_FEATURE_COUNT = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ParameterImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 7;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsImpl <em>Constraints</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getConstraints()
	 * @generated
	 */
	int CONSTRAINTS = 8;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS__CONSTRAINTS = 0;

	/**
	 * The feature id for the '<em><b>Include</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS__INCLUDE = 1;

	/**
	 * The feature id for the '<em><b>Categories</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS__CATEGORIES = 2;

	/**
	 * The number of structural features of the '<em>Constraints</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsBundleImpl <em>Constraints Bundle</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsBundleImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getConstraintsBundle()
	 * @generated
	 */
	int CONSTRAINTS_BUNDLE = 9;

	/**
	 * The feature id for the '<em><b>Providers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS_BUNDLE__PROVIDERS = 0;

	/**
	 * The feature id for the '<em><b>Categories</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS_BUNDLE__CATEGORIES = 1;

	/**
	 * The feature id for the '<em><b>Constraint Bindings Bundles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS_BUNDLE__CONSTRAINT_BINDINGS_BUNDLES = 2;

	/**
	 * The feature id for the '<em><b>Parsers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS_BUNDLE__PARSERS = 3;

	/**
	 * The feature id for the '<em><b>Message Bundle Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS_BUNDLE__MESSAGE_BUNDLE_PATH = 4;

	/**
	 * The number of structural features of the '<em>Constraints Bundle</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINTS_BUNDLE_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.UnparsedConstraintImpl <em>Unparsed Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.UnparsedConstraintImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getUnparsedConstraint()
	 * @generated
	 */
	int UNPARSED_CONSTRAINT = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__ID = CONSTRAINT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__NAME = CONSTRAINT__NAME;

	/**
	 * The feature id for the '<em><b>Severity</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__SEVERITY = CONSTRAINT__SEVERITY;

	/**
	 * The feature id for the '<em><b>Status Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__STATUS_CODE = CONSTRAINT__STATUS_CODE;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__CLASS_NAME = CONSTRAINT__CLASS_NAME;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__MODE = CONSTRAINT__MODE;

	/**
	 * The feature id for the '<em><b>Is Enabled By Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__IS_ENABLED_BY_DEFAULT = CONSTRAINT__IS_ENABLED_BY_DEFAULT;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__DESCRIPTION = CONSTRAINT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Message</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__MESSAGE = CONSTRAINT__MESSAGE;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__PARAMETERS = CONSTRAINT__PARAMETERS;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__TARGET = CONSTRAINT__TARGET;

	/**
	 * The feature id for the '<em><b>Lang</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__LANG = CONSTRAINT__LANG;

	/**
	 * The feature id for the '<em><b>Body</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT__BODY = CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Unparsed Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNPARSED_CONSTRAINT_FEATURE_COUNT = CONSTRAINT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.OclConstraintImpl <em>Ocl Constraint</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.OclConstraintImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getOclConstraint()
	 * @generated
	 */
	int OCL_CONSTRAINT = 11;

	/**
	 * The number of structural features of the '<em>Ocl Constraint</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OCL_CONSTRAINT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ParserImpl <em>Parser</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ParserImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getParser()
	 * @generated
	 */
	int PARSER = 12;

	/**
	 * The feature id for the '<em><b>Language</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARSER__LANGUAGE = 0;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARSER__CLASS_NAME = 1;

	/**
	 * The number of structural features of the '<em>Parser</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARSER_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.TraversalStrategyImpl <em>Traversal Strategy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.TraversalStrategyImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getTraversalStrategy()
	 * @generated
	 */
	int TRAVERSAL_STRATEGY = 13;

	/**
	 * The feature id for the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAVERSAL_STRATEGY__CLASS = 0;

	/**
	 * The feature id for the '<em><b>Package</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAVERSAL_STRATEGY__PACKAGE = 1;

	/**
	 * The number of structural features of the '<em>Traversal Strategy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRAVERSAL_STRATEGY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintBindingsBundleImpl <em>Constraint Bindings Bundle</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintBindingsBundleImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getConstraintBindingsBundle()
	 * @generated
	 */
	int CONSTRAINT_BINDINGS_BUNDLE = 14;

	/**
	 * The feature id for the '<em><b>Client Contexts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_BINDINGS_BUNDLE__CLIENT_CONTEXTS = 0;

	/**
	 * The feature id for the '<em><b>Bindings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_BINDINGS_BUNDLE__BINDINGS = 1;

	/**
	 * The number of structural features of the '<em>Constraint Bindings Bundle</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_BINDINGS_BUNDLE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ClientContextImpl <em>Client Context</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ClientContextImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getClientContext()
	 * @generated
	 */
	int CLIENT_CONTEXT = 15;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIENT_CONTEXT__ID = 0;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIENT_CONTEXT__DEFAULT = 1;

	/**
	 * The number of structural features of the '<em>Client Context</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLIENT_CONTEXT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.BindingImpl <em>Binding</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.BindingImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getBinding()
	 * @generated
	 */
	int BINDING = 16;

	/**
	 * The feature id for the '<em><b>Client Contexts</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__CLIENT_CONTEXTS = 0;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__CONSTRAINTS = 1;

	/**
	 * The feature id for the '<em><b>Excluded Constraints</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__EXCLUDED_CONSTRAINTS = 2;

	/**
	 * The feature id for the '<em><b>Categories</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__CATEGORIES = 3;

	/**
	 * The feature id for the '<em><b>Excluded Categories</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING__EXCLUDED_CATEGORIES = 4;

	/**
	 * The number of structural features of the '<em>Binding</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.EnablementImpl <em>Enablement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.EnablementImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getEnablement()
	 * @generated
	 */
	int ENABLEMENT = 17;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENABLEMENT__ID = CLIENT_CONTEXT__ID;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENABLEMENT__DEFAULT = CLIENT_CONTEXT__DEFAULT;

	/**
	 * The feature id for the '<em><b>Dom Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENABLEMENT__DOM_EXPRESSION = CLIENT_CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enablement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENABLEMENT_FEATURE_COUNT = CLIENT_CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.SelectorImpl <em>Selector</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.SelectorImpl
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getSelector()
	 * @generated
	 */
	int SELECTOR = 18;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTOR__ID = CLIENT_CONTEXT__ID;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTOR__DEFAULT = CLIENT_CONTEXT__DEFAULT;

	/**
	 * The feature id for the '<em><b>Class Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTOR__CLASS_NAME = CLIENT_CONTEXT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Selector</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SELECTOR_FEATURE_COUNT = CLIENT_CONTEXT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.EventTypesEnum <em>Event Types Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.EventTypesEnum
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getEventTypesEnum()
	 * @generated
	 */
	int EVENT_TYPES_ENUM = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum <em>Mode Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getModeEnum()
	 * @generated
	 */
	int MODE_ENUM = 20;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.SeverityEnum <em>Severity Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.SeverityEnum
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getSeverityEnum()
	 * @generated
	 */
	int SEVERITY_ENUM = 21;


	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Category <em>Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Category</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Category
	 * @generated
	 */
	EClass getCategory();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Category#getSubCategories <em>Sub Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Sub Categories</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Category#getSubCategories()
	 * @see #getCategory()
	 * @generated
	 */
	EReference getCategory_SubCategories();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Category#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Category#getId()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Category#isMandatory <em>Mandatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mandatory</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Category#isMandatory()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_Mandatory();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Category#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Category#getName()
	 * @see #getCategory()
	 * @generated
	 */
	EAttribute getCategory_Name();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Category#getParentCategory <em>Parent Category</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Parent Category</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Category#getParentCategory()
	 * @see #getCategory()
	 * @generated
	 */
	EReference getCategory_ParentCategory();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider <em>Constraint Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Provider</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider
	 * @generated
	 */
	EClass getConstraintProvider();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#isCache <em>Cache</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cache</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#isCache()
	 * @see #getConstraintProvider()
	 * @generated
	 */
	EAttribute getConstraintProvider_Cache();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getDescription()
	 * @see #getConstraintProvider()
	 * @generated
	 */
	EAttribute getConstraintProvider_Description();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Target</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getTarget()
	 * @see #getConstraintProvider()
	 * @generated
	 */
	EReference getConstraintProvider_Target();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getMode()
	 * @see #getConstraintProvider()
	 * @generated
	 */
	EAttribute getConstraintProvider_Mode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getClassName()
	 * @see #getConstraintProvider()
	 * @generated
	 */
	EAttribute getConstraintProvider_ClassName();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getConstraints()
	 * @see #getConstraintProvider()
	 * @generated
	 */
	EReference getConstraintProvider_Constraints();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Package</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getPackage()
	 * @see #getConstraintProvider()
	 * @generated
	 */
	EReference getConstraintProvider_Package();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getPluginId <em>Plugin Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Plugin Id</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider#getPluginId()
	 * @see #getConstraintProvider()
	 * @generated
	 */
	EAttribute getConstraintProvider_PluginId();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Target <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Target</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Target
	 * @generated
	 */
	EClass getTarget();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Target#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Feature</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Target#getFeature()
	 * @see #getTarget()
	 * @generated
	 */
	EReference getTarget_Feature();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Target#getEClass <em>EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EClass</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Target#getEClass()
	 * @see #getTarget()
	 * @generated
	 */
	EReference getTarget_EClass();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Event <em>Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Event
	 * @generated
	 */
	EClass getEvent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Event#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Event#getName()
	 * @see #getEvent()
	 * @generated
	 */
	EAttribute getEvent_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.CustomEvent <em>Custom Event</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Custom Event</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.CustomEvent
	 * @generated
	 */
	EClass getCustomEvent();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.CustomEvent#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.CustomEvent#getName()
	 * @see #getCustomEvent()
	 * @generated
	 */
	EAttribute getCustomEvent_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Feature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Feature
	 * @generated
	 */
	EClass getFeature();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint <em>Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint
	 * @generated
	 */
	EClass getConstraint();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getId()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getName()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getSeverity <em>Severity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Severity</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getSeverity()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Severity();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getStatusCode <em>Status Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status Code</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getStatusCode()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_StatusCode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getClassName()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_ClassName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getMode()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Mode();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#isIsEnabledByDefault <em>Is Enabled By Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Enabled By Default</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#isIsEnabledByDefault()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_IsEnabledByDefault();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getDescription()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getMessage <em>Message</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getMessage()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Message();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Parameters</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getParameters()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_Parameters();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getTarget()
	 * @see #getConstraint()
	 * @generated
	 */
	EReference getConstraint_Target();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getLang <em>Lang</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lang</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraint#getLang()
	 * @see #getConstraint()
	 * @generated
	 */
	EAttribute getConstraint_Lang();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.EString" keyRequired="true"
	 *        valueDataType="org.eclipse.emf.ecore.EString" valueRequired="true"
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraints</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraints
	 * @generated
	 */
	EClass getConstraints();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraints#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraints#getConstraints()
	 * @see #getConstraints()
	 * @generated
	 */
	EReference getConstraints_Constraints();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraints#getInclude <em>Include</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Include</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraints#getInclude()
	 * @see #getConstraints()
	 * @generated
	 */
	EAttribute getConstraints_Include();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Constraints#getCategories <em>Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Categories</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Constraints#getCategories()
	 * @see #getConstraints()
	 * @generated
	 */
	EReference getConstraints_Categories();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle <em>Constraints Bundle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraints Bundle</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle
	 * @generated
	 */
	EClass getConstraintsBundle();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getProviders <em>Providers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Providers</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getProviders()
	 * @see #getConstraintsBundle()
	 * @generated
	 */
	EReference getConstraintsBundle_Providers();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getCategories <em>Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Categories</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getCategories()
	 * @see #getConstraintsBundle()
	 * @generated
	 */
	EReference getConstraintsBundle_Categories();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getConstraintBindingsBundles <em>Constraint Bindings Bundles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraint Bindings Bundles</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getConstraintBindingsBundles()
	 * @see #getConstraintsBundle()
	 * @generated
	 */
	EReference getConstraintsBundle_ConstraintBindingsBundles();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getParsers <em>Parsers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parsers</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getParsers()
	 * @see #getConstraintsBundle()
	 * @generated
	 */
	EReference getConstraintsBundle_Parsers();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getMessageBundlePath <em>Message Bundle Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Message Bundle Path</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintsBundle#getMessageBundlePath()
	 * @see #getConstraintsBundle()
	 * @generated
	 */
	EAttribute getConstraintsBundle_MessageBundlePath();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.UnparsedConstraint <em>Unparsed Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unparsed Constraint</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.UnparsedConstraint
	 * @generated
	 */
	EClass getUnparsedConstraint();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.UnparsedConstraint#getBody <em>Body</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Body</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.UnparsedConstraint#getBody()
	 * @see #getUnparsedConstraint()
	 * @generated
	 */
	EAttribute getUnparsedConstraint_Body();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.OclConstraint <em>Ocl Constraint</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Ocl Constraint</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.OclConstraint
	 * @generated
	 */
	EClass getOclConstraint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Parser <em>Parser</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parser</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Parser
	 * @generated
	 */
	EClass getParser();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Parser#getLanguage <em>Language</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Language</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Parser#getLanguage()
	 * @see #getParser()
	 * @generated
	 */
	EAttribute getParser_Language();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Parser#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Parser#getClassName()
	 * @see #getParser()
	 * @generated
	 */
	EAttribute getParser_ClassName();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.TraversalStrategy <em>Traversal Strategy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Traversal Strategy</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.TraversalStrategy
	 * @generated
	 */
	EClass getTraversalStrategy();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.TraversalStrategy#getClass_ <em>Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.TraversalStrategy#getClass_()
	 * @see #getTraversalStrategy()
	 * @generated
	 */
	EAttribute getTraversalStrategy_Class();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.TraversalStrategy#getPackage <em>Package</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Package</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.TraversalStrategy#getPackage()
	 * @see #getTraversalStrategy()
	 * @generated
	 */
	EReference getTraversalStrategy_Package();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle <em>Constraint Bindings Bundle</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Bindings Bundle</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle
	 * @generated
	 */
	EClass getConstraintBindingsBundle();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle#getClientContexts <em>Client Contexts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Client Contexts</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle#getClientContexts()
	 * @see #getConstraintBindingsBundle()
	 * @generated
	 */
	EReference getConstraintBindingsBundle_ClientContexts();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle#getBindings <em>Bindings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bindings</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintBindingsBundle#getBindings()
	 * @see #getConstraintBindingsBundle()
	 * @generated
	 */
	EReference getConstraintBindingsBundle_Bindings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ClientContext <em>Client Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Client Context</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ClientContext
	 * @generated
	 */
	EClass getClientContext();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ClientContext#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ClientContext#getId()
	 * @see #getClientContext()
	 * @generated
	 */
	EAttribute getClientContext_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ClientContext#isDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ClientContext#isDefault()
	 * @see #getClientContext()
	 * @generated
	 */
	EAttribute getClientContext_Default();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding <em>Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binding</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Binding
	 * @generated
	 */
	EClass getBinding();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getClientContexts <em>Client Contexts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Client Contexts</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getClientContexts()
	 * @see #getBinding()
	 * @generated
	 */
	EReference getBinding_ClientContexts();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Constraints</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getConstraints()
	 * @see #getBinding()
	 * @generated
	 */
	EReference getBinding_Constraints();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getExcludedConstraints <em>Excluded Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Excluded Constraints</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getExcludedConstraints()
	 * @see #getBinding()
	 * @generated
	 */
	EReference getBinding_ExcludedConstraints();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getCategories <em>Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Categories</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getCategories()
	 * @see #getBinding()
	 * @generated
	 */
	EReference getBinding_Categories();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getExcludedCategories <em>Excluded Categories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Excluded Categories</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Binding#getExcludedCategories()
	 * @see #getBinding()
	 * @generated
	 */
	EReference getBinding_ExcludedCategories();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Enablement <em>Enablement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enablement</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Enablement
	 * @generated
	 */
	EClass getEnablement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Enablement#getDomExpression <em>Dom Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dom Expression</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Enablement#getDomExpression()
	 * @see #getEnablement()
	 * @generated
	 */
	EAttribute getEnablement_DomExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Selector <em>Selector</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Selector</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Selector
	 * @generated
	 */
	EClass getSelector();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.validation.internal.modeled.model.validation.Selector#getClassName <em>Class Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Class Name</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.Selector#getClassName()
	 * @see #getSelector()
	 * @generated
	 */
	EAttribute getSelector_ClassName();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.validation.internal.modeled.model.validation.EventTypesEnum <em>Event Types Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Event Types Enum</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.EventTypesEnum
	 * @generated
	 */
	EEnum getEventTypesEnum();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum <em>Mode Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Mode Enum</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum
	 * @generated
	 */
	EEnum getModeEnum();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.validation.internal.modeled.model.validation.SeverityEnum <em>Severity Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Severity Enum</em>'.
	 * @see org.eclipse.emf.validation.internal.modeled.model.validation.SeverityEnum
	 * @generated
	 */
	EEnum getSeverityEnum();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ValidationFactory getValidationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.CategoryImpl <em>Category</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.CategoryImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getCategory()
		 * @generated
		 */
		EClass CATEGORY = eINSTANCE.getCategory();

		/**
		 * The meta object literal for the '<em><b>Sub Categories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORY__SUB_CATEGORIES = eINSTANCE.getCategory_SubCategories();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__ID = eINSTANCE.getCategory_Id();

		/**
		 * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__MANDATORY = eINSTANCE.getCategory_Mandatory();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CATEGORY__NAME = eINSTANCE.getCategory_Name();

		/**
		 * The meta object literal for the '<em><b>Parent Category</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CATEGORY__PARENT_CATEGORY = eINSTANCE.getCategory_ParentCategory();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl <em>Constraint Provider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getConstraintProvider()
		 * @generated
		 */
		EClass CONSTRAINT_PROVIDER = eINSTANCE.getConstraintProvider();

		/**
		 * The meta object literal for the '<em><b>Cache</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT_PROVIDER__CACHE = eINSTANCE.getConstraintProvider_Cache();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT_PROVIDER__DESCRIPTION = eINSTANCE.getConstraintProvider_Description();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_PROVIDER__TARGET = eINSTANCE.getConstraintProvider_Target();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT_PROVIDER__MODE = eINSTANCE.getConstraintProvider_Mode();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT_PROVIDER__CLASS_NAME = eINSTANCE.getConstraintProvider_ClassName();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_PROVIDER__CONSTRAINTS = eINSTANCE.getConstraintProvider_Constraints();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_PROVIDER__PACKAGE = eINSTANCE.getConstraintProvider_Package();

		/**
		 * The meta object literal for the '<em><b>Plugin Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT_PROVIDER__PLUGIN_ID = eINSTANCE.getConstraintProvider_PluginId();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.TargetImpl <em>Target</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.TargetImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getTarget()
		 * @generated
		 */
		EClass TARGET = eINSTANCE.getTarget();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET__FEATURE = eINSTANCE.getTarget_Feature();

		/**
		 * The meta object literal for the '<em><b>EClass</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TARGET__ECLASS = eINSTANCE.getTarget_EClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.EventImpl <em>Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.EventImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getEvent()
		 * @generated
		 */
		EClass EVENT = eINSTANCE.getEvent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EVENT__NAME = eINSTANCE.getEvent_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.CustomEventImpl <em>Custom Event</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.CustomEventImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getCustomEvent()
		 * @generated
		 */
		EClass CUSTOM_EVENT = eINSTANCE.getCustomEvent();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CUSTOM_EVENT__NAME = eINSTANCE.getCustomEvent_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.FeatureImpl <em>Feature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.FeatureImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getFeature()
		 * @generated
		 */
		EClass FEATURE = eINSTANCE.getFeature();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl <em>Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getConstraint()
		 * @generated
		 */
		EClass CONSTRAINT = eINSTANCE.getConstraint();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__ID = eINSTANCE.getConstraint_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__NAME = eINSTANCE.getConstraint_Name();

		/**
		 * The meta object literal for the '<em><b>Severity</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__SEVERITY = eINSTANCE.getConstraint_Severity();

		/**
		 * The meta object literal for the '<em><b>Status Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__STATUS_CODE = eINSTANCE.getConstraint_StatusCode();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__CLASS_NAME = eINSTANCE.getConstraint_ClassName();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__MODE = eINSTANCE.getConstraint_Mode();

		/**
		 * The meta object literal for the '<em><b>Is Enabled By Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__IS_ENABLED_BY_DEFAULT = eINSTANCE.getConstraint_IsEnabledByDefault();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__DESCRIPTION = eINSTANCE.getConstraint_Description();

		/**
		 * The meta object literal for the '<em><b>Message</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__MESSAGE = eINSTANCE.getConstraint_Message();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT__PARAMETERS = eINSTANCE.getConstraint_Parameters();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT__TARGET = eINSTANCE.getConstraint_Target();

		/**
		 * The meta object literal for the '<em><b>Lang</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT__LANG = eINSTANCE.getConstraint_Lang();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ParameterImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__KEY = eINSTANCE.getParameter_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__VALUE = eINSTANCE.getParameter_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsImpl <em>Constraints</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getConstraints()
		 * @generated
		 */
		EClass CONSTRAINTS = eINSTANCE.getConstraints();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINTS__CONSTRAINTS = eINSTANCE.getConstraints_Constraints();

		/**
		 * The meta object literal for the '<em><b>Include</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINTS__INCLUDE = eINSTANCE.getConstraints_Include();

		/**
		 * The meta object literal for the '<em><b>Categories</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINTS__CATEGORIES = eINSTANCE.getConstraints_Categories();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsBundleImpl <em>Constraints Bundle</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintsBundleImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getConstraintsBundle()
		 * @generated
		 */
		EClass CONSTRAINTS_BUNDLE = eINSTANCE.getConstraintsBundle();

		/**
		 * The meta object literal for the '<em><b>Providers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINTS_BUNDLE__PROVIDERS = eINSTANCE.getConstraintsBundle_Providers();

		/**
		 * The meta object literal for the '<em><b>Categories</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINTS_BUNDLE__CATEGORIES = eINSTANCE.getConstraintsBundle_Categories();

		/**
		 * The meta object literal for the '<em><b>Constraint Bindings Bundles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINTS_BUNDLE__CONSTRAINT_BINDINGS_BUNDLES = eINSTANCE.getConstraintsBundle_ConstraintBindingsBundles();

		/**
		 * The meta object literal for the '<em><b>Parsers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINTS_BUNDLE__PARSERS = eINSTANCE.getConstraintsBundle_Parsers();

		/**
		 * The meta object literal for the '<em><b>Message Bundle Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINTS_BUNDLE__MESSAGE_BUNDLE_PATH = eINSTANCE.getConstraintsBundle_MessageBundlePath();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.UnparsedConstraintImpl <em>Unparsed Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.UnparsedConstraintImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getUnparsedConstraint()
		 * @generated
		 */
		EClass UNPARSED_CONSTRAINT = eINSTANCE.getUnparsedConstraint();

		/**
		 * The meta object literal for the '<em><b>Body</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNPARSED_CONSTRAINT__BODY = eINSTANCE.getUnparsedConstraint_Body();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.OclConstraintImpl <em>Ocl Constraint</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.OclConstraintImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getOclConstraint()
		 * @generated
		 */
		EClass OCL_CONSTRAINT = eINSTANCE.getOclConstraint();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ParserImpl <em>Parser</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ParserImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getParser()
		 * @generated
		 */
		EClass PARSER = eINSTANCE.getParser();

		/**
		 * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARSER__LANGUAGE = eINSTANCE.getParser_Language();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARSER__CLASS_NAME = eINSTANCE.getParser_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.TraversalStrategyImpl <em>Traversal Strategy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.TraversalStrategyImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getTraversalStrategy()
		 * @generated
		 */
		EClass TRAVERSAL_STRATEGY = eINSTANCE.getTraversalStrategy();

		/**
		 * The meta object literal for the '<em><b>Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRAVERSAL_STRATEGY__CLASS = eINSTANCE.getTraversalStrategy_Class();

		/**
		 * The meta object literal for the '<em><b>Package</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRAVERSAL_STRATEGY__PACKAGE = eINSTANCE.getTraversalStrategy_Package();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintBindingsBundleImpl <em>Constraint Bindings Bundle</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintBindingsBundleImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getConstraintBindingsBundle()
		 * @generated
		 */
		EClass CONSTRAINT_BINDINGS_BUNDLE = eINSTANCE.getConstraintBindingsBundle();

		/**
		 * The meta object literal for the '<em><b>Client Contexts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_BINDINGS_BUNDLE__CLIENT_CONTEXTS = eINSTANCE.getConstraintBindingsBundle_ClientContexts();

		/**
		 * The meta object literal for the '<em><b>Bindings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_BINDINGS_BUNDLE__BINDINGS = eINSTANCE.getConstraintBindingsBundle_Bindings();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ClientContextImpl <em>Client Context</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ClientContextImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getClientContext()
		 * @generated
		 */
		EClass CLIENT_CONTEXT = eINSTANCE.getClientContext();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLIENT_CONTEXT__ID = eINSTANCE.getClientContext_Id();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLIENT_CONTEXT__DEFAULT = eINSTANCE.getClientContext_Default();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.BindingImpl <em>Binding</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.BindingImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getBinding()
		 * @generated
		 */
		EClass BINDING = eINSTANCE.getBinding();

		/**
		 * The meta object literal for the '<em><b>Client Contexts</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINDING__CLIENT_CONTEXTS = eINSTANCE.getBinding_ClientContexts();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINDING__CONSTRAINTS = eINSTANCE.getBinding_Constraints();

		/**
		 * The meta object literal for the '<em><b>Excluded Constraints</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINDING__EXCLUDED_CONSTRAINTS = eINSTANCE.getBinding_ExcludedConstraints();

		/**
		 * The meta object literal for the '<em><b>Categories</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINDING__CATEGORIES = eINSTANCE.getBinding_Categories();

		/**
		 * The meta object literal for the '<em><b>Excluded Categories</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BINDING__EXCLUDED_CATEGORIES = eINSTANCE.getBinding_ExcludedCategories();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.EnablementImpl <em>Enablement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.EnablementImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getEnablement()
		 * @generated
		 */
		EClass ENABLEMENT = eINSTANCE.getEnablement();

		/**
		 * The meta object literal for the '<em><b>Dom Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENABLEMENT__DOM_EXPRESSION = eINSTANCE.getEnablement_DomExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.SelectorImpl <em>Selector</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.SelectorImpl
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getSelector()
		 * @generated
		 */
		EClass SELECTOR = eINSTANCE.getSelector();

		/**
		 * The meta object literal for the '<em><b>Class Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SELECTOR__CLASS_NAME = eINSTANCE.getSelector_ClassName();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.EventTypesEnum <em>Event Types Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.EventTypesEnum
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getEventTypesEnum()
		 * @generated
		 */
		EEnum EVENT_TYPES_ENUM = eINSTANCE.getEventTypesEnum();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum <em>Mode Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getModeEnum()
		 * @generated
		 */
		EEnum MODE_ENUM = eINSTANCE.getModeEnum();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.validation.internal.modeled.model.validation.SeverityEnum <em>Severity Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.SeverityEnum
		 * @see org.eclipse.emf.validation.internal.modeled.model.validation.impl.ValidationPackageImpl#getSeverityEnum()
		 * @generated
		 */
		EEnum SEVERITY_ENUM = eINSTANCE.getSeverityEnum();

	}

} //ValidationPackage
