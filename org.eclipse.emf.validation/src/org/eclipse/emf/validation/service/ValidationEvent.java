/******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.service;

import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IConstraintStatus;


/**
 * Event notifying {@link IValidationListener}s that a validation operation
 * has occurred.
 * 
 * @author Christian W. Damus (cdamus)
 */
public final class ValidationEvent
	extends EventObject {

	private static final long serialVersionUID = -7900125537603879206L;
	
	private final EvaluationMode<?> mode;
	private final Map<String, Object> clientData;
	private final IStatus status;
	private final Collection<?> targets;
	private List<IConstraintStatus> results;
	
	private Collection<String> clientContextIds = null;
	
	/**
	 * Initializes me with the evaluation mode, client data, elements or
	 * notifications validated, and validation results that I will pass along
	 * to listeners.
	 * 
	 * @param <T> the kind of objects that were validated
	 * 
	 * @param mode the evaluation mode
	 * @param clientData data specific to the particular validation client
	 *      that performed the validation wishes to make available to listeners
	 * @param targets the elements or notifications (according to the evaluation
	 *      mode) that were validated
	 * @param status the validation results
	 */
	public <T> ValidationEvent(
		EvaluationMode<T> mode,
		Map<String, ?> clientData,
		Collection<? extends T> targets,
		IStatus status) {
		
		super(ModelValidationService.getInstance());
		
		this.mode = mode;
		this.status = status;
		this.targets = Collections.unmodifiableCollection(targets);
		
		if (clientData == null) {
			this.clientData = Collections.emptyMap();
		} else {
			this.clientData = Collections.unmodifiableMap(clientData);
		}
	}
	
	/**
	 * Initializes me with the evaluation mode, client data, elements or
	 * notifications validated, and validation results that I will pass along
	 * to listeners. Also, I will be initialized with the client context IDs
	 * that were involved in the validation.
	 * 
	 * 
	 * @param <T> the kind of objects that were validated
	 * 
	 * @param mode the evaluation mode
	 * @param clientData data specific to the particular validation client
	 *      that performed the validation wishes to make available to listeners
	 * @param targets the elements or notifications (according to the evaluation
	 *      mode) that were validated
	 * @param status the validation results
	 * @param clientContextIds the client context Ids that were involved in the
	 *  validation.
	 */
	public <T> ValidationEvent(
		EvaluationMode<T> mode,
		Map<String, ?> clientData,
		Collection<? extends T> targets,
		IStatus status,
		Collection<String> clientContextIds) {
		
		this(mode,clientData,targets,status);
		
		this.clientContextIds = clientContextIds;
	}
	
	/**
	 * Retrieves the client context ids that were involved in the validation
	 *  that lead to this event.
	 *  
	 * @return A collection of the client context ids in String form. These
	 *  ids should not be modified in any way as they may affect other listeners.
	 */
	public Collection<String> getClientContextIds() {
		if (clientContextIds == null) {
			clientContextIds = Collections.emptyList();
		}
		return clientContextIds;
	}
	
	/**
	 * Queries the mode in which the validation operation occurred.
	 * 
	 * @return the evaluation mode; never <code>null</code> or
	 *     even {@link EvaluationMode#NULL}
	 */
	public EvaluationMode<?> getEvaluationMode() {
		return mode;
	}
	
	/**
	 * Retrieves the client-specific data that the client that initiated the
	 * validation operation publishes to listeners.  It is up to listeners to
	 * make what they will of the information that they do or do not find in
	 * this map.  Two things the caller may be assured of:
	 * <ol>
	 *   <li>the map is never <code>null</code></li>
	 *   <li>its keys are {@link String}s</li>
	 * </ol>
	 * 
	 * @return an unmodifiable mapping of client data
	 */
	public Map<String, Object> getClientData() {
		return clientData;
	}
	
	/**
	 * Obtains the collection of {@link org.eclipse.emf.ecore.EObject}s (in the
	 * batch mode case) or {@link org.eclipse.emf.common.notify.Notification}s
	 * (in the live mode case) that were validated.
	 * 
	 * @return an unmodifiable collection of the validation targets
	 * 
	 * @see #getValidationResults()
	 */
	public Collection<?> getValidationTargets() {
		return targets;
	}
	
	/**
	 * Queries the overall severity of the validation
	 * {@linkplain #getValidationResults() results}.
	 * 
	 * @return the severity, enumerated by the {@link IStatus} interface
	 * 
	 * @see IStatus#getSeverity()
	 */
	public int getSeverity() {
		return status.getSeverity();
	}
	
	/**
	 * Queries whether the overall severity of the validation
	 * {@linkplain #getValidationResults() results} matches the specified
	 * severity mask.
	 * 
	 * @param severityMask the severity mask to match
	 * @return whether the overall severity matches
	 * 
	 * @see IStatus#matches(int)
	 */
	public boolean matches(int severityMask) {
		return status.matches(severityMask);
	}
	
	/**
	 * Obtains the results of the validation operation.
	 * 
	 * @return the validation results, as an unmodifiable list of
	 *     {@link org.eclipse.emf.validation.model.IConstraintStatus}es
	 * 
	 * @see #getValidationTargets()
	 */
	public List<IConstraintStatus> getValidationResults() {
		if (results == null) {
			// lazily compute the results
			if (status.isMultiStatus()) {
				IStatus[] children = status.getChildren();
				results = new java.util.ArrayList<IConstraintStatus>(children.length);
				for (IStatus element : children) {
					if (element instanceof IConstraintStatus) {
						results.add((IConstraintStatus) element);
					}
				}
				results = Collections.unmodifiableList(results);
			} else if (status instanceof IConstraintStatus) {
				results = Collections.singletonList((IConstraintStatus) status);
			} else {
				results = Collections.emptyList();
			}
		}
		
		return results;
	}
}
