/******************************************************************************
 * Copyright (c) 2003, 2008 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.internal.util;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.preferences.EMFModelValidationPreferences;
import org.eclipse.emf.validation.service.AbstractConstraintDescriptor;
import org.eclipse.emf.validation.service.ConstraintExistsException;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;

/**
 * <p>
 * A light-weight descriptor for a constraint, containing all of the meta-data
 * required by the framework to determine when a constraint should be evaluated.
 * Its primary purpose is to defer the instantiation of a
 * {@link org.eclipse.emf.validation.model.IModelConstraint constraint} until it
 * needs to be evaluated, as it is anticipated that this may be a costly
 * operation (involving parsing a script from XML, loading a plug-in, etc.).
 * </p>
 * <p>
 * This class is intended to be used by clients of the validation framework for
 * constraint discovery purposes.
 * </p>
 *
 * @author Christian W. Damus (cdamus)
 */
public final class XmlConstraintDescriptor extends AbstractConstraintDescriptor
		implements IXmlConstraintDescriptor, IParameterizedConstraintDescriptor {

	static final String RULE_INCOMPLETE = ValidationMessages.rule_incomplete_ERROR_;
	static final String RULE_ID = ValidationMessages.rule_id;
	static final String RULE_NAME = ValidationMessages.rule_name;
	static final String RULE_BODY = ValidationMessages.rule_body;
	static final String MESSAGE_PATTERN = ValidationMessages.rule_message;

	private static final String NAMESPACE_URI_SEPARATOR = ":"; //$NON-NLS-1$
	private static final String EOBJECT_CLASS_NAME = "org.eclipse.emf.ecore.EObject"; //$NON-NLS-1$

	private final IConfigurationElement config;

	private String name;
	private String id;
	private int statusCode;
	private final String pluginId;

	private String description;
	private ConstraintSeverity severity = ConstraintSeverity.ERROR;
	private EvaluationMode<?> mode;

	private Map<Object, TargetDescriptor> targetMap = new java.util.HashMap<>();

	private String messagePattern;

	private String body;

	private boolean resolved = false;
	private boolean isEnabledByDefault = true;

	/**
	 * Initializes me from the specified Eclipse configuration element.
	 *
	 * @param config the configuration element
	 * @throws ConstraintExistsException if the <code>config</code>uration element
	 *                                   specified a constraint ID that already
	 *                                   exists in the system
	 */
	public XmlConstraintDescriptor(IConfigurationElement config) throws ConstraintExistsException {
		this.config = config;

		name = config.getAttribute(XmlConfig.A_NAME);
		String statusCodeStr = config.getAttribute(XmlConfig.A_STATUS_CODE);
		pluginId = config.getDeclaringExtension().getNamespaceIdentifier();

		// constraint ID is required to start with contributing plugin ID
		id = normalizedId(pluginId, config.getAttribute(XmlConfig.A_ID));

		int newStatusCode;

		try {
			newStatusCode = Integer.parseInt(statusCodeStr);
		} catch (NumberFormatException nfe) {
			newStatusCode = 1;
		}

		this.statusCode = newStatusCode;

		try {
			assertNotNull(id, RULE_ID);
			assertNotNull(name, RULE_NAME);

			parseDescription(config);

			parseSeverity(config);
			parseTargets(config);
			parseIsEnabledByDefault(config);
			parseMessagePattern(config);

			setBody(config.getValue());

			ConstraintRegistry.getInstance().register(this);

			// ensure that I get my default enablement state
			if (EMFPlugin.IS_ECLIPSE_RUNNING) {
				setEnabled(!EMFModelValidationPreferences.isConstraintDisabled(id));
			}

			if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
				Trace.trace("Initialized constraint " + id); //$NON-NLS-1$
			}
		} catch (CoreException e) {
			Trace.catching(XmlConstraintDescriptor.class, "<init>", e); //$NON-NLS-1$

			if (this.id == null) {
				this.id = "$error." + System.identityHashCode(this); //$NON-NLS-1$
			}

			if (this.name == null) {
				this.name = this.id;
			}

			this.statusCode = EMFModelValidationStatusCodes.CONSTRAINT_NOT_INITED;

			setError(e);
			setMessagePattern(e.getLocalizedMessage());

			if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
				Trace.trace("Initialized constraint " + id //$NON-NLS-1$
						+ " as error constraint."); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Returns the normalized constraint ID, which is prefixed by the contributing
	 * plug-in's ID.
	 *
	 * @param pluginId the ID of the plug-in that contributes the constraint
	 * @param id       the constraint's ID, as declared in the XML
	 * @return the normalized ID, which either the original ID if it already starts
	 *         with the plug-in ID; the original ID prefixed by the plug-in ID,
	 *         otherwise
	 */
	public static String normalizedId(String pluginId, String id) {
		assert pluginId != null;
		assert id != null;

		String result = id;

		if (!result.startsWith(pluginId)) {
			result = pluginId + '.' + result;
		}

		return result;
	}

	// implements the interface method
	@Override
	public IConfigurationElement getConfig() {
		return config;
	}

	// implements the interface method
	@Override
	public String getName() {
		return name;
	}

	// implements the interface method
	@Override
	public String getId() {
		return id;
	}

	// implements the interface method
	@Override
	public String getPluginId() {
		return pluginId;
	}

	// implements the interface method
	@Override
	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	// implements the interface method
	@Override
	public ConstraintSeverity getSeverity() {
		return severity;
	}

	private void setSeverity(ConstraintSeverity severity) {
		assert severity != null && !severity.isNull();

		this.severity = severity;
	}

	// implements the interface method
	@Override
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Obtains the target descriptor corresponding to the specified <code>key</code>
	 * which indicates the EMF object type. The descriptor is lazily instantiated,
	 * if it does not already exist.
	 *
	 * @param key the EMF object type, as a class name (string) or {@link EClass}
	 * @return the descriptor
	 */
	private TargetDescriptor getTarget(Object key) {
		if (Trace.shouldTraceEntering()) {
			Trace.entering(getClass(), "getTarget", //$NON-NLS-1$
					new Object[] { key });
		}

		TargetDescriptor result = targetMap.get(key);

		if (result == null) {
			result = new TargetDescriptor();
			targetMap.put(key, result);

			if (key instanceof EClass) {
				// "inherit" supertype descriptors
				inheritTriggers((EClass) key, result);
			}
		}

		if (Trace.shouldTraceExiting()) {
			Trace.exiting(getClass(), "getTarget", result); //$NON-NLS-1$
		}

		return result;
	}

	// implements the interface method
	@Override
	public EvaluationMode<?> getEvaluationMode() {
		return mode;
	}

	private void setEvaluationMode(EvaluationMode<?> mode) {
		this.mode = mode;
	}

	/*
	 * (non-Javadoc) Implements the inherited method.
	 */
	@Override
	public void resolveTargetTypes(String[] namespaceUris) {
		if (resolved) {
			// I already did this. Don't bother me about it again
			return;
		}

		resolved = true;

		Map<Object, TargetDescriptor> oldMap = targetMap;
		targetMap = new java.util.HashMap<>();

		for (Map.Entry<Object, TargetDescriptor> next : oldMap.entrySet()) {
			String typeName = (String) next.getKey();

			EClass targetEClass = null;
			// See if the typeName value from the extension has a qualified
			// namespace URI in it. This might be used in cases where there
			// are two EClasses with the same name in two different EPackages.
			int separatorPosition = typeName.indexOf(NAMESPACE_URI_SEPARATOR);
			if (separatorPosition != -1) {
				String namespaceUri = typeName.substring(separatorPosition + 1);
				typeName = typeName.substring(0, separatorPosition);
				targetEClass = ModelValidationService.findClass(namespaceUri, typeName);
			} else {
				// Otherwise, we have to go through all of the namespace URI's provided
				// and find the EClass with the name in the first EPackage.
				for (int i = 0; (targetEClass == null) && (i < namespaceUris.length); i++) {
					targetEClass = ModelValidationService.findClass(namespaceUris[i], typeName);
				}
			}

			if (targetEClass != null) {
				targetMap.put(targetEClass, next.getValue());

				if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
					Trace.trace(EMFModelValidationDebugOptions.CONSTRAINTS, "Resolved target: " + typeName //$NON-NLS-1$
							+ " for: " + this); //$NON-NLS-1$
				}
			} else {
				// didn't resolve it
				if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
					Trace.trace(EMFModelValidationDebugOptions.CONSTRAINTS, "Failed to resolve target: " + typeName //$NON-NLS-1$
							+ " for: " + this); //$NON-NLS-1$
				}

				Log.warningMessage(EMFModelValidationStatusCodes.CONSTRAINT_NOT_INITED,
						EMFModelValidationStatusCodes.TARGET_TYPE_NOT_FOUND_MSG, new Object[] { getId(), typeName });
			}
		}

		// now, let each target descriptor "inherit" triggers from descriptors
		// that are for ancestor EClasses. This is an O{n**2) algorithm, but
		// there are not expected ever to be more than a handful of targets in
		// any given constraint
		for (Map.Entry<Object, TargetDescriptor> next : targetMap.entrySet()) {
			inheritTriggers((EClass) next.getKey(), next.getValue());
		}
	}

	/**
	 * Causes the specified <code>target</code>'s <code>descriptor</code> to inherit
	 * trigger definitions from the descriptors of any registers ancestor EClasses.
	 *
	 * @param target     a target EClass
	 * @param descriptor its descriptor
	 */
	private void inheritTriggers(EClass target, TargetDescriptor descriptor) {
		for (Map.Entry<Object, TargetDescriptor> next : targetMap.entrySet()) {
			EClass otherTarget = (EClass) next.getKey();

			if ((otherTarget != target) && (otherTarget.isSuperTypeOf(target))) {
				descriptor.merge(next.getValue());
			}
		}
	}

	/**
	 * Queries whether I am a universal constraint, supporting all target types,
	 * events, and features.
	 *
	 * @return whether I was declared without any target definitions
	 */
	private boolean isUniversal() {
		return targetMap.isEmpty();
	}

	// implements the interface method
	@Override
	public boolean targetsTypeOf(EObject eObject) {
		return (eObject == null) ? false : isUniversal() || targetsType(eObject.eClass());
	}

	/**
	 * Determines whether I explicitly support this type.
	 *
	 * @param eClass the target type to test
	 * @return whether I support it or not
	 */
	private boolean targetsType(EClass eClass) {
		return getTarget(eClass).isExplicit();
	}

	/**
	 * Adds a supported target type name.
	 *
	 * @param typeName the EMF type for which batch evaluation is supported
	 * @return the descriptor that I have added
	 */
	private TargetDescriptor addTargetType(String typeName) {
		TargetDescriptor result;
		if (typeName == null) {
			// EObject is implied
			result = getTarget(EOBJECT_CLASS_NAME);
		} else {
			result = getTarget(typeName);
		}

		// ensure that the descriptor explicitly supports this EClass type
		result.setExplicit();

		return result;
	}

	// implements the interface method
	@Override
	public boolean targetsEvent(Notification notification) {
		if (notification.getNotifier() instanceof EObject) {
			EObject eObject = (EObject) notification.getNotifier();
			EMFEventType eventType = EMFEventType.getInstance(notification.getEventType());

			EStructuralFeature changedFeature = null;
			if (notification.getFeature() instanceof EStructuralFeature) {
				changedFeature = (EStructuralFeature) notification.getFeature();
			}

			// if we specify no targets ("universal case"), then we match if
			// and only if the event type is an EMF-standard type. Only if
			// we are not universal do we check whether we match a custom event
			return ((eObject == null) || eventType.isNull()) ? false
					: (isUniversal() && EMFEventType.getPredefinedInstances().contains(eventType))
							|| (!isUniversal() && targetsEvent(eObject.eClass(), eventType, changedFeature));
		} else {
			return false;
		}
	}

	/**
	 * Queries whether the specified <CODE>eventType</CODE> is one that I explicitly
	 * support.
	 *
	 * @param eClass    the EMF type
	 * @param eventType the EMF event type
	 * @param feature   the particular feature that is changed (may be
	 *                  <code>null</code> for some kinds of notifications)
	 * @return whether the event type is supported for the <code>eClass</code>
	 */
	private boolean targetsEvent(EClass eClass, EMFEventType eventType, EStructuralFeature feature) {
		return getTarget(eClass).hasEvent(eventType, feature);
	}

	// implements the interface method
	@Override
	public String getMessagePattern() {
		return messagePattern;
	}

	private void setMessagePattern(String messagePattern) {
		this.messagePattern = messagePattern;
	}

	// implements the interface method
	@Override
	public String getBody() {
		return body;
	}

	private void setBody(String body) {
		this.body = body;
	}

	@Override
	public String getLanguage() {
		return getConfig().getAttribute(XmlConfig.A_LANG);
	}

	@Override
	public String getParameterValue(String name) {
		String result = XmlConfig.getParameter(getConfig(), name);

		if (result == null) {
			// some parameters have other ways of being derived
			if (CLASS_PARAMETER.equals(name)) {
				result = getConfig().getAttribute(XmlConfig.A_CLASS);
			} else if (BUNDLE_PARAMETER.equals(name)) {
				result = getConfig().getDeclaringExtension().getNamespaceIdentifier();
			}
		}

		return result;
	}

	public boolean isEnabledByDefault() {
		return isEnabledByDefault;
	}

	/**
	 * Parses the <tt>&lt;description&gt;</tt> element from the XML.
	 *
	 * @param extConfig the Eclipse configuration element representing the XML
	 *                  extension data
	 */
	private void parseDescription(IConfigurationElement extConfig) {
		IConfigurationElement[] descConfig = extConfig.getChildren(XmlConfig.E_DESCRIPTION);

		if (descConfig.length > 0) {
			setDescription(descConfig[0].getValue());
		}

		if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
			Trace.trace("Parsed constraint " + id + " description"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Parses the <tt>&lt;message&gt;</tt> element from the XML.
	 *
	 * @param extConfig the Eclipse configuration element representing the XML
	 *                  extension data
	 * @throws CoreException on any failure to obtain the message pattern from the
	 *                       XML
	 */
	private void parseMessagePattern(IConfigurationElement extConfig) throws CoreException {
		IConfigurationElement[] msgConfig = extConfig.getChildren(XmlConfig.E_MESSAGE);

		String newMessagePattern = null;

		if (msgConfig.length > 0) {
			newMessagePattern = msgConfig[0].getValue();
		}

		assertNotNull(newMessagePattern, MESSAGE_PATTERN);

		setMessagePattern(newMessagePattern);

		if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
			Trace.trace("Parsed constraint " + id + " message: " + newMessagePattern); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Parses the <tt>&lt;constraint&gt;</tt> element's <tt>mode</tt> attribute from
	 * the XML.
	 *
	 * @param extConfig the Eclipse configuration element representing the XML
	 *                  extension data
	 */
	private void parseMode(IConfigurationElement extConfig) {
		String modeName = extConfig.getAttribute(XmlConfig.A_MODE);

		if (modeName == null) {
			setEvaluationMode(EvaluationMode.BATCH);
		} else {
			setEvaluationMode(EvaluationMode.getInstance(modeName));
		}

		if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
			Trace.trace("Parsed constraint " + id + " mode: " + modeName); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Parses the <tt>&lt;constraint&gt;</tt> element's <tt>severity</tt> attribute
	 * from the XML.
	 *
	 * @param extConfig the Eclipse configuration element representing the XML
	 *                  extension data
	 */
	private void parseSeverity(IConfigurationElement extConfig) {
		String severityName = extConfig.getAttribute(XmlConfig.A_SEVERITY);

		if (severityName == null) {
			setSeverity(ConstraintSeverity.ERROR);
		} else {
			setSeverity(ConstraintSeverity.getInstance(severityName));
		}

		if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
			Trace.trace("Parsed constraint " + id + " severity: " + severityName); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * Parses the <tt>&lt;target&gt;</tt> elements from the XML.
	 *
	 * @param extConfig the Eclipse configuration element representing the XML
	 *                  extension data
	 */
	private void parseTargets(IConfigurationElement extConfig) {
		parseMode(extConfig);

		IConfigurationElement[] targets = extConfig.getChildren(XmlConfig.E_TARGET);

		for (final IConfigurationElement target : targets) {
			TargetDescriptor targetType = addTargetType(target.getAttribute(XmlConfig.A_CLASS));

			if (getEvaluationMode().isLive()) {
				// only live constraints are applied automatically in response
				// to events in the model
				IConfigurationElement[] events = XmlConfig.getEvents(target);

				for (IConfigurationElement element : events) {
					EMFEventType eventType = EMFEventType.getInstance(element.getAttribute(XmlConfig.A_NAME));

					if (!eventType.isNull()) {
						// add specific supported features, if any
						IConfigurationElement[] features = element.getChildren(XmlConfig.E_FEATURE);

						if ((features == null) || (features.length == 0)) {
							targetType.addEvent(eventType);
						} else {
							for (IConfigurationElement element2 : features) {
								targetType.addEvent(eventType, element2.getAttribute(XmlConfig.A_NAME));
							}
						}
					}
				}
			}

			if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
				Trace.trace("Parsed constraint " + id //$NON-NLS-1$
						+ " target: " + target.getAttribute(XmlConfig.A_CLASS) //$NON-NLS-1$
						+ ": " + targetType); //$NON-NLS-1$
			}
		}
	}

	/**
	 * Parses the <tt>&lt;constraint&gt;</tt> element's <tt>isEnabledByDefault</tt>
	 * attribute from the XML.
	 *
	 * @param extConfig the Eclipse configuration element representing the XML
	 *                  extension data
	 */
	private void parseIsEnabledByDefault(IConfigurationElement extConfig) {
		String attr = extConfig.getAttribute(XmlConfig.A_IS_ENABLED_BY_DEFAULT);

		this.isEnabledByDefault = true;

		if (attr != null) {
			isEnabledByDefault = Boolean.parseBoolean(attr);
		}

		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			EMFModelValidationPreferences.setConstraintDisabledDefault(id, !isEnabledByDefault);
		}

		if (Trace.shouldTrace(EMFModelValidationDebugOptions.CONSTRAINTS)) {
			Trace.trace("Parsed constraint " + id //$NON-NLS-1$
					+ " isEnabledByDefault: " + attr); //$NON-NLS-1$
		}
	}

	/**
	 * Asserts that a <code>value</code> be not <code>null</code>.
	 *
	 * @param value       the value which must not be <code>null</code>
	 * @param missingItem I18N key of the item which has the specified
	 *                    <code>value</code>
	 * @throws CoreException if the <code>value</code> is <code>null</code>
	 * @see XmlConstraintDescriptor#RULE_ID
	 * @see XmlConstraintDescriptor#RULE_NAME, etc.
	 */
	private void assertNotNull(Object value, String missingItem) throws CoreException {
		if (value == null) {
			CoreException ce = new CoreException(new Status(IStatus.ERROR, EMFModelValidationPlugin.getPluginId(),
					EMFModelValidationStatusCodes.CONSTRAINT_NOT_INITED,
					EMFModelValidationPlugin.getMessage(RULE_INCOMPLETE, missingItem), null));

			Trace.throwing(getClass(), "assertNotNull", ce); //$NON-NLS-1$

			throw ce;
		}
	}

	/**
	 * <p>
	 * Describes the applicability of a constraint, including the target object type
	 * and event/feature context according to the evaluation mode of the constraint.
	 * </p>
	 * <p>
	 * A target may be either {@link #isSupported supported} or unsupported for a
	 * constraint. This is the only information maintained for batch-mode
	 * constraints. Live-mode and feature-mode constraints have additional
	 * information describing which events or features, respectively, they apply to.
	 * </p>
	 * <p>
	 * Finally, target descriptors are organized into a type hierarchy according to
	 * the meta-model. Descriptors have zero or more 'parents' from which they may
	 * 'inherit' the applicability characteristics described above. There is
	 * currently no means for excluding inherited characteristics, but only to add
	 * to them.
	 * </p>
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private static class TargetDescriptor {
		private Collection<EMFEventType> nonFeatureSpecificEvents;
		private Map<EMFEventType, Collection<String>> featureSpecificEvents;

		/**
		 * Indicates whether I explicitly support my target EClass, or whether I was
		 * just created lazily on a query for support.
		 */
		private boolean explicit;

		/**
		 * Initializes me.
		 */
		TargetDescriptor() {
			super();
		}

		/**
		 * Does lazy initialization of the <code>nonFeatureSpecificEvents</code> map.
		 *
		 * @return the <code>nonFeatureSpecificEvents</code> map; never null
		 */
		private Collection<EMFEventType> getNonFeatureSpecificEvents() {
			if (nonFeatureSpecificEvents == null) {
				nonFeatureSpecificEvents = new java.util.HashSet<>();
			}
			return nonFeatureSpecificEvents;
		}

		/**
		 * Does lazy initialization of the <code>featureSpecificEvents</code> map.
		 *
		 * @return the <code>featureSpecificEvents</code> map; never null
		 */
		private Map<EMFEventType, Collection<String>> getFeatureSpecificEvents() {
			if (featureSpecificEvents == null) {
				featureSpecificEvents = new java.util.HashMap<>();
			}
			return featureSpecificEvents;
		}

		/**
		 * Queries whether I explicitly support my target EClass, or whether I was just
		 * created lazily on a query for support.
		 *
		 * @return <code>true</code> if I explicitly declare support for my
		 *         corresponding EClass type; <code>false</code>, otherwise
		 */
		boolean isExplicit() {
			return explicit;
		}

		/**
		 * Sets me as explicitly supporting my associated EClass type.
		 */
		void setExplicit() {
			this.explicit = true;
		}

		/**
		 * Adds an event type that was not designated for any specific feature. If the
		 * specified event is a {@link EMFEventType#isFeatureSpecific()
		 * feature-specific} event type, then this event will be triggered by all
		 * features. Otherwise, it will only be triggered by an event pertaining (such
		 * as "create") to the object as a whole.
		 *
		 * @param eventType the event type
		 */
		void addEvent(EMFEventType eventType) {
			getNonFeatureSpecificEvents().add(eventType);
		}

		/**
		 * Appends a supported event type, for live constraints, with an optional
		 * feature designation.
		 *
		 * @param eventType   the event type
		 * @param featureName the feature name for which to register support of the
		 *                    specified <code>eventType</code>
		 */
		void addEvent(EMFEventType eventType, String featureName) {
			if (featureName != null) {
				Collection<String> currentFeatures = getFeatureSpecificEvents().get(eventType);

				if (currentFeatures == null) {
					currentFeatures = new java.util.HashSet<>();
					getFeatureSpecificEvents().put(eventType, currentFeatures);
				}

				currentFeatures.add(featureName);
			}
		}

		/**
		 * Queries whether I support the specified event type.
		 *
		 * @param eventType the event type
		 * @param feature   the particular feature that I must support, or
		 *                  <code>null</code> the event is not for any feature
		 * @return whether I support this event type for this feature
		 */
		boolean hasEvent(EMFEventType eventType, EStructuralFeature feature) {
			if (Trace.shouldTraceEntering()) {
				Trace.entering(getClass(), "hasEvent", //$NON-NLS-1$
						new Object[] { eventType, feature });
			}

			String featureName = (feature == null) ? null : feature.getName();

			// if no events are registered, and I explicitly support the EClass,
			// then it is implied that I support all predefined events
			boolean result = ((nonFeatureSpecificEvents == null) || nonFeatureSpecificEvents.isEmpty())
					&& ((featureSpecificEvents == null) || featureSpecificEvents.isEmpty()) && isExplicit()
					&& EMFEventType.getPredefinedInstances().contains(eventType);

			if (!result) {
				// do I support this event for all features? If so, the
				// answer is easy
				if ((nonFeatureSpecificEvents != null) && nonFeatureSpecificEvents.contains(eventType)) {
					result = true;
				} else {
					// null feature name can't be included in the feature list
					// for the event
					if (featureName != null) {
						// see whether the collection of supported event
						// types includes this feature
						if ((featureSpecificEvents != null) && featureSpecificEvents.containsKey(eventType)) {
							Collection<String> eventFeatures = featureSpecificEvents.get(eventType);

							// if no features are registered and I am supported and
							// I have no parents, then it is implied that I
							// support all features
							result = eventFeatures.contains(featureName);
						}
					}
				}
			}

			if (Trace.shouldTraceExiting()) {
				Trace.exiting(getClass(), "hasEvent", //$NON-NLS-1$
						result ? Boolean.TRUE : Boolean.FALSE);
			}

			return result;
		}

		/**
		 * Merge's a supertype's target descriptor into me. I will inherit its
		 * applicability characteristics.
		 *
		 * @param parent a descriptor for a parent type
		 */
		void merge(TargetDescriptor parent) {
			// if my ancestor EClass is explicitly supported, then obviously
			// so am I
			if (parent.isExplicit()) {
				setExplicit();
			}

			if (parent.nonFeatureSpecificEvents != null) {
				getNonFeatureSpecificEvents().addAll(parent.nonFeatureSpecificEvents);
			}

			if (parent.featureSpecificEvents != null) {
				for (Map.Entry<EMFEventType, Collection<String>> next : parent.featureSpecificEvents.entrySet()) {
					EMFEventType eventType = next.getKey();
					Collection<String> features = next.getValue();

					Collection<String> myFeatures = this.getFeatureSpecificEvents().get(eventType);
					if (myFeatures == null) {
						// add all of my parent's features. Need to create a copy
						// because I might inherit other features from a different
						// parent
						getFeatureSpecificEvents().put(eventType, new java.util.HashSet<>(features));
					} else {
						myFeatures.addAll(features);
					}
				}
			}
		}
	}
}
