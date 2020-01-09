/**
 * Copyright (c) 2008 Zeligsoft Inc. and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Zeligsoft - Initial API and implementation
 */
package org.eclipse.emf.validation.internal.service;

import java.util.Collections;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.AbstractConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintDescriptor;

/**
 * A marker status indicating a {@link Resource} covered by a validation
 * operation.
 * 
 * @author Christian W. Damus (cdamus)
 * 
 * @since 1.3
 */
public final class ResourceStatus
		extends ConstraintStatus {

	private static IModelConstraint dummy = new IModelConstraint() {

		private final IConstraintDescriptor descriptor = new AbstractConstraintDescriptor() {

			public boolean targetsTypeOf(EObject object) {
				return false;
			}

			public boolean targetsEvent(Notification notification) {
				return false;
			}

			public int getStatusCode() {
				return 0;
			}

			public ConstraintSeverity getSeverity() {
				return ConstraintSeverity.INFO;
			}

			public String getPluginId() {
				return EMFModelValidationPlugin.getPluginId();
			}

			public String getName() {
				return ""; //$NON-NLS-1$
			}

			public String getMessagePattern() {
				return ""; //$NON-NLS-1$
			}

			public String getId() {
				return getPluginId() + ".resourceMarker"; //$NON-NLS-1$
			}

			public EvaluationMode<?> getEvaluationMode() {
				return EvaluationMode.BATCH;
			}

			public String getDescription() {
				return null;
			}

			public String getBody() {
				return null;
			}
		};

		public IStatus validate(IValidationContext ctx) {
			return ctx.createSuccessStatus();
		}

		public IConstraintDescriptor getDescriptor() {
			return descriptor;
		}
	};

	/**
	 * Initializes me with my resource.
	 * 
	 * @param resource
	 *            a resource covered by the validation operation
	 */
	public ResourceStatus(EObject root) {
		super(dummy, root, IStatus.OK, 0,
			ValidationMessages.eval_all_pass_INFO_, Collections.singleton(root));
	}

}
