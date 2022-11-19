/**
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.ui.ide.quickfix;

import java.util.Iterator;

import org.eclipse.core.resources.IMarker;
import org.eclipse.emf.validation.marker.MarkerUtil;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.preferences.EMFModelValidationPreferences;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.ui.ide.internal.l10n.ValidationUIIDEMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IMarkerResolutionGenerator;

/**
 * A generator for problem marker resolutions that can fix validation problems.
 * Currently, this class only supports the "sweep it under the rug" resolution
 * that disables the constraint that reported the problem. Future capabilities
 * will include delegation to resolution generators provided by constraint
 * providers, that will know how to actually fix problems.
 *
 * @author Christian W. Damus (cdamus)
 */
public class ValidationMarkerResolution implements IMarkerResolutionGenerator {

	private static final IMarkerResolution[] EMPTY_RESOLUTIONS = new IMarkerResolution[0];

	/*
	 * (non-Javadoc) Redefines/Implements/Extends the inherited method.
	 */
	@Override
	public IMarkerResolution[] getResolutions(IMarker marker) {
		String constraintId = marker.getAttribute(MarkerUtil.RULE_ATTRIBUTE, ""); //$NON-NLS-1$

		if (constraintId.length() > 0) {
			IConstraintDescriptor constraint = ConstraintRegistry.getInstance().getDescriptor(constraintId);

			if ((constraint != null) && canFix(constraint)) {
				// only provide resolutions for constraints that are currently
				// enabled. This covers two important cases: the constraint
				// is already disabled by quick fix of a similar problem marker,
				// or the constraint is disabled by a run-time error (which is
				// what the marker actually indicates)
				return new IMarkerResolution[] { new Resolution(constraint) };
			}
		}

		return EMPTY_RESOLUTIONS;
	}

	/**
	 * Determines whether the specified <code>constraint</code> can be fixed by this
	 * resolution generator. Currently, the only reasons for a constraint not being
	 * fixable are:
	 * <ul>
	 * <li>the constraint is already fixed (i.e., it is disabled already)</li>
	 * <li>the constraint is in a mandatory category and cannot, thereforem be
	 * disabled</li>
	 * </ul>
	 *
	 * @param constraint
	 * @return
	 */
	private static boolean canFix(IConstraintDescriptor constraint) {
		boolean result = constraint.isEnabled();

		if (result) {
			// see whether this constraint is in any mandatory category

			for (Iterator<Category> iter = constraint.getCategories().iterator(); result && iter.hasNext();) {

				result = !iter.next().isMandatory();
			}
		}

		return result;
	}

	/**
	 * Implementation of a marker resolution that solves problems by sweeping them
	 * under the rug (that is, by disabling the constraints that report them).
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private static class Resolution implements IMarkerResolution {
		private final String label;
		private final IConstraintDescriptor constraint;

		/**
		 * Initializes me with the descriptor of the constraint that I will disable when
		 * (and if) the user selects me.
		 *
		 * @param constraint the constraint descriptor that I would disable
		 */
		public Resolution(IConstraintDescriptor constraint) {
			this.constraint = constraint;
			label = NLS.bind(ValidationUIIDEMessages.quickfix_label, new Object[] { constraint.getName() });
		}

		/*
		 * (non-Javadoc) Implements the inherited method.
		 */
		@Override
		public String getLabel() {
			return label;
		}

		/*
		 * (non-Javadoc) Implements the inherited method.
		 */
		@Override
		public void run(IMarker marker) {
			// Just disable the constraint (in the preferences) for subsequent
			// validation operations. The user can re-enable it via the UI
			// whenever necessary.
			EMFModelValidationPreferences.setConstraintDisabled(constraint.getId(), true);
			EMFModelValidationPreferences.save();
		}
	}
}
