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

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.modeled.model.validation.Constraint;
import org.eclipse.emf.validation.internal.modeled.model.validation.ConstraintProvider;
import org.eclipse.emf.validation.internal.modeled.model.validation.Constraints;
import org.eclipse.emf.validation.internal.modeled.model.validation.ModeEnum;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.ConstraintFactory;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IModelConstraintProvider;

/**
 * <p>
 * An implementation of the
 * {@link org.eclipse.emf.validation.service.IModelConstraintProvider} interface
 * binding instances of the validation meta-model to the validation framework.
 * </p>
 * 
 * @author Boris Gruschko
 * @since 1.4
 * 
 */
public class ModeledConstraintProvider implements IModelConstraintProvider {

	private ConstraintProvider provider;

	private ArrayList<IModelConstraint> constraints = null;

	public ModeledConstraintProvider() {
	}
	
	public void setConstraintProviderModel(ConstraintProvider provider) {
		this.provider = provider;
	}

	public Collection<IModelConstraint> getBatchConstraints(EObject eObject,
			Collection<IModelConstraint> ret) {
		if (ret == null) {
			ret = new ArrayList<IModelConstraint>();
		}

		if (constraints == null) {
			constraints = new ArrayList<IModelConstraint>(provider
					.getConstraints().size());
			ConstraintFactory factory = ConstraintFactory.getInstance();

			for (Constraints cons : provider.getConstraints()) {
				for (Constraint constraint : cons.getConstraints()) {
					IConstraintDescriptor descriptor = ConstraintRegistry
							.getInstance().getDescriptor(
									provider.getPluginId(), constraint.getId());

					assert descriptor != null;

					constraints.add(factory.newConstraint(descriptor));
				}
			}

			constraints.trimToSize();
		}

		ret.addAll(constraints);

		return ret;
	}

	public Collection<IModelConstraint> getLiveConstraints(
			Notification notification, Collection<IModelConstraint> constraints) {
		// TODO implement live constraints
		return null;
	}

	public ConstraintProvider getModel() {
		return provider;
	}

	public boolean isLive() {
		return ModeEnum.LIVE.equals(provider.getMode());
	}
}
