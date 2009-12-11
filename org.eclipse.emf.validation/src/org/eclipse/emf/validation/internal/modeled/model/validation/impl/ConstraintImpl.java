/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConstraintImpl.java,v 1.2 2009/12/11 19:54:50 ahunter Exp $
 */
package org.eclipse.emf.validation.internal.modeled.model.validation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.validation.internal.modeled.model.validation.Constraint;
import org.eclipse.emf.validation.internal.modeled.model.validation.Constraints;
import org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum;
import org.eclipse.emf.validation.internal.modeled.model.validation.Parameter;
import org.eclipse.emf.validation.internal.modeled.model.validation.SeverityEnum;
import org.eclipse.emf.validation.internal.modeled.model.validation.Target;
import org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getSeverity <em>Severity</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getStatusCode <em>Status Code</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#isIsEnabledByDefault <em>Is Enabled By Default</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintImpl#getLang <em>Lang</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 * @since 1.4
 */
public abstract class ConstraintImpl extends EObjectImpl implements Constraint {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected static final SeverityEnum SEVERITY_EDEFAULT = SeverityEnum.INFO;

	/**
	 * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected SeverityEnum severity = SEVERITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getStatusCode() <em>Status Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusCode()
	 * @generated
	 * @ordered
	 */
	protected static final int STATUS_CODE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStatusCode() <em>Status Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatusCode()
	 * @generated
	 * @ordered
	 */
	protected int statusCode = STATUS_CODE_EDEFAULT;

	/**
	 * The default value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getClassName() <em>Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClassName()
	 * @generated
	 * @ordered
	 */
	protected String className = CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected static final ModeEnum MODE_EDEFAULT = ModeEnum.BATCH;

	/**
	 * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected ModeEnum mode = MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #isIsEnabledByDefault() <em>Is Enabled By Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsEnabledByDefault()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_ENABLED_BY_DEFAULT_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isIsEnabledByDefault() <em>Is Enabled By Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsEnabledByDefault()
	 * @generated
	 * @ordered
	 */
	protected boolean isEnabledByDefault = IS_ENABLED_BY_DEFAULT_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> parameters;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected Target target;

	/**
	 * The default value of the '{@link #getLang() <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLang()
	 * @generated
	 * @ordered
	 */
	protected static final String LANG_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLang() <em>Lang</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLang()
	 * @generated
	 * @ordered
	 */
	protected String lang = LANG_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationPackage.Literals.CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SeverityEnum getSeverity() {
		return severity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSeverity(SeverityEnum newSeverity) {
		SeverityEnum oldSeverity = severity;
		severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__SEVERITY, oldSeverity, severity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatusCode(int newStatusCode) {
		int oldStatusCode = statusCode;
		statusCode = newStatusCode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__STATUS_CODE, oldStatusCode, statusCode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClassName(String newClassName) {
		String oldClassName = className;
		className = newClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__CLASS_NAME, oldClassName, className));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModeEnum getMode() {
		return mode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMode(ModeEnum newMode) {
		ModeEnum oldMode = mode;
		mode = newMode == null ? MODE_EDEFAULT : newMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__MODE, oldMode, mode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsEnabledByDefault() {
		return isEnabledByDefault;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsEnabledByDefault(boolean newIsEnabledByDefault) {
		boolean oldIsEnabledByDefault = isEnabledByDefault;
		isEnabledByDefault = newIsEnabledByDefault;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__IS_ENABLED_BY_DEFAULT, oldIsEnabledByDefault, isEnabledByDefault));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__MESSAGE, oldMessage, message));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getParameters() {
		if (parameters == null) {
			parameters = new EcoreEMap<String,String>(ValidationPackage.Literals.PARAMETER, ParameterImpl.class, this, ValidationPackage.CONSTRAINT__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Target getTarget() {
		if (target != null && target.eIsProxy()) {
			InternalEObject oldTarget = (InternalEObject)target;
			target = (Target)eResolveProxy(oldTarget);
			if (target != oldTarget) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ValidationPackage.CONSTRAINT__TARGET, oldTarget, target));
			}
		}
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Target basicGetTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(Target newTarget) {
		Target oldTarget = target;
		target = newTarget;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__TARGET, oldTarget, target));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLang(String newLang) {
		String oldLang = lang;
		lang = newLang;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT__LANG, oldLang, lang));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ValidationPackage.CONSTRAINT__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ValidationPackage.CONSTRAINT__ID:
				return getId();
			case ValidationPackage.CONSTRAINT__NAME:
				return getName();
			case ValidationPackage.CONSTRAINT__SEVERITY:
				return getSeverity();
			case ValidationPackage.CONSTRAINT__STATUS_CODE:
				return getStatusCode();
			case ValidationPackage.CONSTRAINT__CLASS_NAME:
				return getClassName();
			case ValidationPackage.CONSTRAINT__MODE:
				return getMode();
			case ValidationPackage.CONSTRAINT__IS_ENABLED_BY_DEFAULT:
				return isIsEnabledByDefault();
			case ValidationPackage.CONSTRAINT__DESCRIPTION:
				return getDescription();
			case ValidationPackage.CONSTRAINT__MESSAGE:
				return getMessage();
			case ValidationPackage.CONSTRAINT__PARAMETERS:
				if (coreType) return getParameters();
				else return getParameters().map();
			case ValidationPackage.CONSTRAINT__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case ValidationPackage.CONSTRAINT__LANG:
				return getLang();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ValidationPackage.CONSTRAINT__ID:
				setId((String)newValue);
				return;
			case ValidationPackage.CONSTRAINT__NAME:
				setName((String)newValue);
				return;
			case ValidationPackage.CONSTRAINT__SEVERITY:
				setSeverity((SeverityEnum)newValue);
				return;
			case ValidationPackage.CONSTRAINT__STATUS_CODE:
				setStatusCode((Integer)newValue);
				return;
			case ValidationPackage.CONSTRAINT__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case ValidationPackage.CONSTRAINT__MODE:
				setMode((ModeEnum)newValue);
				return;
			case ValidationPackage.CONSTRAINT__IS_ENABLED_BY_DEFAULT:
				setIsEnabledByDefault((Boolean)newValue);
				return;
			case ValidationPackage.CONSTRAINT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ValidationPackage.CONSTRAINT__MESSAGE:
				setMessage((String)newValue);
				return;
			case ValidationPackage.CONSTRAINT__PARAMETERS:
				((EStructuralFeature.Setting)getParameters()).set(newValue);
				return;
			case ValidationPackage.CONSTRAINT__TARGET:
				setTarget((Target)newValue);
				return;
			case ValidationPackage.CONSTRAINT__LANG:
				setLang((String)newValue);
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
			case ValidationPackage.CONSTRAINT__ID:
				setId(ID_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT__SEVERITY:
				setSeverity(SEVERITY_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT__STATUS_CODE:
				setStatusCode(STATUS_CODE_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT__MODE:
				setMode(MODE_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT__IS_ENABLED_BY_DEFAULT:
				setIsEnabledByDefault(IS_ENABLED_BY_DEFAULT_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT__PARAMETERS:
				getParameters().clear();
				return;
			case ValidationPackage.CONSTRAINT__TARGET:
				setTarget((Target)null);
				return;
			case ValidationPackage.CONSTRAINT__LANG:
				setLang(LANG_EDEFAULT);
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
			case ValidationPackage.CONSTRAINT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case ValidationPackage.CONSTRAINT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ValidationPackage.CONSTRAINT__SEVERITY:
				return severity != SEVERITY_EDEFAULT;
			case ValidationPackage.CONSTRAINT__STATUS_CODE:
				return statusCode != STATUS_CODE_EDEFAULT;
			case ValidationPackage.CONSTRAINT__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case ValidationPackage.CONSTRAINT__MODE:
				return mode != MODE_EDEFAULT;
			case ValidationPackage.CONSTRAINT__IS_ENABLED_BY_DEFAULT:
				return isEnabledByDefault != IS_ENABLED_BY_DEFAULT_EDEFAULT;
			case ValidationPackage.CONSTRAINT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ValidationPackage.CONSTRAINT__MESSAGE:
				return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
			case ValidationPackage.CONSTRAINT__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case ValidationPackage.CONSTRAINT__TARGET:
				return target != null;
			case ValidationPackage.CONSTRAINT__LANG:
				return LANG_EDEFAULT == null ? lang != null : !LANG_EDEFAULT.equals(lang);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(", severity: ");
		result.append(severity);
		result.append(", statusCode: ");
		result.append(statusCode);
		result.append(", className: ");
		result.append(className);
		result.append(", mode: ");
		result.append(mode);
		result.append(", isEnabledByDefault: ");
		result.append(isEnabledByDefault);
		result.append(", description: ");
		result.append(description);
		result.append(", message: ");
		result.append(message);
		result.append(", lang: ");
		result.append(lang);
		result.append(')');
		return result.toString();
	}

} //ConstraintImpl
