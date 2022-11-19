/**
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 */
package org.eclipse.emf.validation.ui.internal.preferences;

import org.eclipse.emf.validation.ui.preferences.ConstraintsSelectionBlock;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Preference page for user to select which constraint categories are disabled.
 * Mandatory constraints are shown in disabled text colour, and inoperable
 * (error) constraints are flagged with delta-bang warning icons.
 *
 * @author Christian W. Damus (cdamus)
 * @author David Cummings (dcummin)
 */
public class ConstraintsPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private ConstraintsSelectionBlock constraintsComposite;

	// implements the inherited method
	@Override
	protected Control createContents(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();
		result.setLayout(layout);

		constraintsComposite = new ConstraintsSelectionBlock();
		constraintsComposite.createComposite(result);

		applyDialogFont(result);

		return result;
	}

	@Override
	public void init(IWorkbench workbench) {
		// nothing to do
	}

	// redefines the inherited method
	@Override
	public boolean performOk() {
		return constraintsComposite.performOk();
	}

	// extends the inherited method
	@Override
	protected void performDefaults() {
		constraintsComposite.performDefaults();
	}
}
