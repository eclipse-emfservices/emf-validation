/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConstraintProviderImpl.java,v 1.1 2009/08/28 11:39:50 bgruschko Exp $
 */
package org.eclipse.emf.validation.internal.modeled.model.validation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider;
import org.eclipse.emf.validation.internal.modeled.model.validation.Constraints;
import org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum;
import org.eclipse.emf.validation.internal.modeled.model.validation.Target;
import org.eclipse.emf.validation.internal.modeled.model.validation.ValidationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constraint Provider</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl#isCache <em>Cache</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl#getClassName <em>Class Name</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link org.eclipse.emf.validation.internal.modeled.model.validation.impl.ConstraintProviderImpl#getPluginId <em>Plugin Id</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConstraintProviderImpl extends EObjectImpl implements ConstraintProvider {
	/**
	 * The default value of the '{@link #isCache() <em>Cache</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCache()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CACHE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isCache() <em>Cache</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCache()
	 * @generated
	 * @ordered
	 */
	protected boolean cache = CACHE_EDEFAULT;

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
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected EList<Target> target;

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
	 * The cached value of the '{@link #getConstraints() <em>Constraints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<Constraints> constraints;

	/**
	 * The cached value of the '{@link #getPackage() <em>Package</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackage()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> package_;

	/**
	 * The default value of the '{@link #getPluginId() <em>Plugin Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginId()
	 * @generated
	 * @ordered
	 */
	protected static final String PLUGIN_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPluginId() <em>Plugin Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPluginId()
	 * @generated
	 * @ordered
	 */
	protected String pluginId = PLUGIN_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstraintProviderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ValidationPackage.Literals.CONSTRAINT_PROVIDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCache() {
		return cache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCache(boolean newCache) {
		boolean oldCache = cache;
		cache = newCache;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT_PROVIDER__CACHE, oldCache, cache));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT_PROVIDER__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Target> getTarget() {
		if (target == null) {
			target = new EObjectContainmentEList<Target>(Target.class, this, ValidationPackage.CONSTRAINT_PROVIDER__TARGET);
		}
		return target;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT_PROVIDER__MODE, oldMode, mode));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT_PROVIDER__CLASS_NAME, oldClassName, className));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Constraints> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectContainmentEList<Constraints>(Constraints.class, this, ValidationPackage.CONSTRAINT_PROVIDER__CONSTRAINTS);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EPackage> getPackage() {
		if (package_ == null) {
			package_ = new EObjectResolvingEList<EPackage>(EPackage.class, this, ValidationPackage.CONSTRAINT_PROVIDER__PACKAGE);
		}
		return package_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPluginId() {
		return pluginId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPluginId(String newPluginId) {
		String oldPluginId = pluginId;
		pluginId = newPluginId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ValidationPackage.CONSTRAINT_PROVIDER__PLUGIN_ID, oldPluginId, pluginId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ValidationPackage.CONSTRAINT_PROVIDER__TARGET:
				return ((InternalEList<?>)getTarget()).basicRemove(otherEnd, msgs);
			case ValidationPackage.CONSTRAINT_PROVIDER__CONSTRAINTS:
				return ((InternalEList<?>)getConstraints()).basicRemove(otherEnd, msgs);
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
			case ValidationPackage.CONSTRAINT_PROVIDER__CACHE:
				return isCache();
			case ValidationPackage.CONSTRAINT_PROVIDER__DESCRIPTION:
				return getDescription();
			case ValidationPackage.CONSTRAINT_PROVIDER__TARGET:
				return getTarget();
			case ValidationPackage.CONSTRAINT_PROVIDER__MODE:
				return getMode();
			case ValidationPackage.CONSTRAINT_PROVIDER__CLASS_NAME:
				return getClassName();
			case ValidationPackage.CONSTRAINT_PROVIDER__CONSTRAINTS:
				return getConstraints();
			case ValidationPackage.CONSTRAINT_PROVIDER__PACKAGE:
				return getPackage();
			case ValidationPackage.CONSTRAINT_PROVIDER__PLUGIN_ID:
				return getPluginId();
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
			case ValidationPackage.CONSTRAINT_PROVIDER__CACHE:
				setCache((Boolean)newValue);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__TARGET:
				getTarget().clear();
				getTarget().addAll((Collection<? extends Target>)newValue);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__MODE:
				setMode((ModeEnum)newValue);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__CLASS_NAME:
				setClassName((String)newValue);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__CONSTRAINTS:
				getConstraints().clear();
				getConstraints().addAll((Collection<? extends Constraints>)newValue);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__PACKAGE:
				getPackage().clear();
				getPackage().addAll((Collection<? extends EPackage>)newValue);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__PLUGIN_ID:
				setPluginId((String)newValue);
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
			case ValidationPackage.CONSTRAINT_PROVIDER__CACHE:
				setCache(CACHE_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__TARGET:
				getTarget().clear();
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__MODE:
				setMode(MODE_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__CLASS_NAME:
				setClassName(CLASS_NAME_EDEFAULT);
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__CONSTRAINTS:
				getConstraints().clear();
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__PACKAGE:
				getPackage().clear();
				return;
			case ValidationPackage.CONSTRAINT_PROVIDER__PLUGIN_ID:
				setPluginId(PLUGIN_ID_EDEFAULT);
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
			case ValidationPackage.CONSTRAINT_PROVIDER__CACHE:
				return cache != CACHE_EDEFAULT;
			case ValidationPackage.CONSTRAINT_PROVIDER__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ValidationPackage.CONSTRAINT_PROVIDER__TARGET:
				return target != null && !target.isEmpty();
			case ValidationPackage.CONSTRAINT_PROVIDER__MODE:
				return mode != MODE_EDEFAULT;
			case ValidationPackage.CONSTRAINT_PROVIDER__CLASS_NAME:
				return CLASS_NAME_EDEFAULT == null ? className != null : !CLASS_NAME_EDEFAULT.equals(className);
			case ValidationPackage.CONSTRAINT_PROVIDER__CONSTRAINTS:
				return constraints != null && !constraints.isEmpty();
			case ValidationPackage.CONSTRAINT_PROVIDER__PACKAGE:
				return package_ != null && !package_.isEmpty();
			case ValidationPackage.CONSTRAINT_PROVIDER__PLUGIN_ID:
				return PLUGIN_ID_EDEFAULT == null ? pluginId != null : !PLUGIN_ID_EDEFAULT.equals(pluginId);
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
		result.append(" (cache: ");
		result.append(cache);
		result.append(", description: ");
		result.append(description);
		result.append(", mode: ");
		result.append(mode);
		result.append(", className: ");
		result.append(className);
		result.append(", pluginId: ");
		result.append(pluginId);
		result.append(')');
		return result.toString();
	}

} //ConstraintProviderImpl
