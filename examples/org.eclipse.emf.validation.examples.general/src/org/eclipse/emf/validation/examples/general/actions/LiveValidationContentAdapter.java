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
package org.eclipse.emf.validation.examples.general.actions;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.validation.examples.general.constraints.ValidationDelegateClientSelector;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.ILiveValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.jface.dialogs.MessageDialog;

/**
 * A content adapter that performs live validation in response to changes in the
 * model.
 * <p>
 * TODO: This class was an inner class in the
 * {@link EnableLiveValidationDelegate} class, but was factored out to work
 * around Bugzilla
 * <a href="https://bugs.eclipse.org/bugs/show_bug.cgi?id=97632">97632</a>. It
 * should be reinstated as an inner class when that bug is fixed.
 * </p>
 */
class LiveValidationContentAdapter extends EContentAdapter {
	private final EnableLiveValidationDelegate actionDelegate;
	private ILiveValidator validator = null;

	/**
	 * @param delegate
	 */
	LiveValidationContentAdapter(EnableLiveValidationDelegate delegate) {
		actionDelegate = delegate;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.eclipse.emf.ecore.util.EContentAdapter#notifyChanged(org.eclipse.emf.
	 * common.notify.Notification)
	 */
	@Override
	public void notifyChanged(final Notification notification) {
		super.notifyChanged(notification);

		actionDelegate.shell.getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				if (validator == null) {
					validator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
				}

				ValidationDelegateClientSelector.running = true;

				IStatus status = validator.validate(notification);

				if (!status.isOK()) {
					if (status.isMultiStatus()) {
						status = status.getChildren()[0];
					}

					MessageDialog.openError(LiveValidationContentAdapter.this.actionDelegate.shell,
							LiveValidationContentAdapter.this.actionDelegate.title, status.getMessage());
				}

				ValidationDelegateClientSelector.running = false;
			}
		});
	}
}
