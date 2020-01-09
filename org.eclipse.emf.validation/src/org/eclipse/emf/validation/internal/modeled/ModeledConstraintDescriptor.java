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
package org.eclipse.emf.validation.internal.modeled;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.modeled.model.validation.Constraint;
import org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum;
import org.eclipse.emf.validation.internal.modeled.model.validation.SeverityEnum;
import org.eclipse.emf.validation.internal.modeled.model.validation.UnparsedConstraint;
import org.eclipse.emf.validation.internal.util.XmlConstraintDescriptor;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.preferences.EMFModelValidationPreferences;
import org.eclipse.emf.validation.service.AbstractConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;

/**
 * @author Boris Gruschko
 * @since 1.4
 * 
 */
public class ModeledConstraintDescriptor extends AbstractConstraintDescriptor
		implements IParameterizedConstraintDescriptor {

	private final String pluginId;
	private final ClassProvider classProvider;
	
	// values computed from constraint model
	private String body = null;
	private	EvaluationMode<?> evaluationMode = null;
	private String id = null;
	private String messagePattern = null;
	private String description = null;
	private	String name = null;
	private ConstraintSeverity constraintSeverity = null;
	private int statusCode;
	private String language;
	private Map<String,String>	parameters = null;
	private EClassifier	targetClassifier;
	
	public ModeledConstraintDescriptor( String pluginId,
			ClassProvider classProvider) {
		this.pluginId = pluginId;
		this.classProvider = classProvider;

		
	}
	
	/**
	 * Sets the constraint. No reference to the constraint object will be held after this method had been executed.
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 * 
	 * @param constraint constraint model
	 */
	public final void setInitializationData(Constraint constraint) {
		if (constraint instanceof UnparsedConstraint) {
			this.body = ((UnparsedConstraint) constraint).getBody();
		}

		{
			ModeEnum mode = constraint.getMode();
	
			if (mode.equals(ModeEnum.BATCH)) {
				this.evaluationMode = EvaluationMode.BATCH;
			} else if (mode.equals(ModeEnum.LIVE)) {
				this.evaluationMode = EvaluationMode.LIVE;
				throw new UnsupportedOperationException("Live modeled constraints are not supported yet."); //$NON-NLS-1$
			} else {
				this.evaluationMode = EvaluationMode.NULL;
			}
		}

		{
			this.messagePattern = classProvider.bind(constraint.getMessage(), null);
			this.description = classProvider.bind(constraint.getDescription(), null);
			this.name = classProvider.bind(constraint.getName(), null); 
		}
		
		{
			SeverityEnum severity = constraint.getSeverity();

			switch (severity.getValue()) {
			case SeverityEnum.CANCEL_VALUE:
				this.constraintSeverity = ConstraintSeverity.CANCEL;
				break;
			case SeverityEnum.ERROR_VALUE:
				this.constraintSeverity = ConstraintSeverity.ERROR;
				break;
			case SeverityEnum.INFO_VALUE:
				this.constraintSeverity = ConstraintSeverity.INFO;
				break;
			case SeverityEnum.WARNING_VALUE:
				this.constraintSeverity = ConstraintSeverity.WARNING;
				break;
			default:
				throw new IllegalArgumentException();
			}
	
		}
		
		{
			EMap<String,String> localParameters = constraint.getParameters();
			
			if ( localParameters == null || localParameters.size() == 0 ) {
				this.parameters = Collections.emptyMap();
			} else {
				this.parameters = new HashMap<String, String>( localParameters.size()*2, 0.7f);
				this.parameters.putAll(localParameters.map());
			}
		}
		
		this.statusCode = constraint.getStatusCode();
		this.language = constraint.getLang();
		if ( constraint.getTarget() != null ) {
			this.targetClassifier = constraint.getTarget().getEClass();
		}
		this.id = XmlConstraintDescriptor.normalizedId(pluginId, constraint
				.getId());
		
		if (EMFPlugin.IS_ECLIPSE_RUNNING) {
			EMFModelValidationPreferences.setConstraintDisabledDefault(getId(),
					!constraint.isIsEnabledByDefault());
		}
	}

	public String getBody() {
		return body;
	}

	public EvaluationMode<?> getEvaluationMode() {
		return evaluationMode;
	}

	public String getId() {
		return id;
	}

	public String getMessagePattern() {
		return messagePattern;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getName() {
		return name;
	}

	public String getPluginId() {
		return pluginId;
	}

	public ConstraintSeverity getSeverity() {
		return constraintSeverity;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public boolean targetsEvent(Notification notification) {
		// TODO implement live constraints
		return false;
	}

	public boolean targetsTypeOf(EObject eObject) {
		return targetClassifier.isInstance(eObject);
	}

	public String getLanguage() {
		return language;
	}

	public String getParameterValue(String name) {
		return parameters.get(name);
	}

	public ClassProvider getClassProvider() {
		return classProvider;
	}

}
