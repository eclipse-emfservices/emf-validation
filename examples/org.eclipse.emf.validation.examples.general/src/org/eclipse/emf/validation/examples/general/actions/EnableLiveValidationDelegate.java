/**
 * Copyright (c) 2005, 2008 IBM Corporation, Zeligsoft Inc. and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Zeligsoft - Action enablement fixes (bug 233213)
 */
package org.eclipse.emf.validation.examples.general.actions;

import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.emf.examples.extlibrary.presentation.EXTLibraryEditor;
import org.eclipse.emf.validation.examples.general.internal.l10n.ValidationMessages;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * This action delegate calls upon the validation service to provide a batch
 * validation of the selected EObjects and their children.
 *
 */
public class EnableLiveValidationDelegate implements IEditorActionDelegate, IActionDelegate2 {

	/**
	 * Error message to display when an exception occured
	 */
	protected static final String MESSAGE_EXCEPTION = ValidationMessages.message_exception;

	/**
	 * The shell this action is hosted in
	 */
	protected Shell shell = null;

	/**
	 * The active editor
	 */
	protected EXTLibraryEditor editor = null;

	/**
	 * Selected EObjects
	 */
	protected Collection<Resource> selectedResources = null;

	String title = ValidationMessages.EnableLiveValidationDelegate_title;

	/*
	 * @see
	 * org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.
	 * IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void selectionChanged(IAction action, final ISelection selection) {
		this.selectedResources = null;
		action.setEnabled(false);

		try {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;

				Collection<?> selectedHunh = structuredSelection.toList();

				for (Object next : selectedHunh) {
					if (!(next instanceof Resource)) {
						action.setEnabled(false);
						break;
					} else if (resourceHasAdapter((Resource) next)) {
						action.setEnabled(false);
						break;
					} else {
						action.setEnabled(true);
					}
				}

				if (action.isEnabled()) {
					this.selectedResources = (Collection<Resource>) selectedHunh;
				} else {
					this.selectedResources = null;
				}
			}
		} catch (Exception e) {
			// Exceptions are not expected
			MessageDialog.openInformation(shell, title, MESSAGE_EXCEPTION);
			throw new RuntimeException(e);
		} finally {
			action.setEnabled((null != selectedResources));
		}
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate2#dispose()
	 */
	@Override
	public void dispose() {
		// No-op
	}

	/*
	 * @see
	 * org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action
	 * .IAction, org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.editor = (EXTLibraryEditor) targetEditor;
		if (targetEditor != null) {
			this.shell = targetEditor.getSite().getShell();
		}
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate2#init(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void init(IAction action) {
		// No-op
	}

	/*
	 * @see org.eclipse.ui.IActionDelegate2#runWithEvent(org.eclipse.jface.action.
	 * IAction, org.eclipse.swt.widgets.Event)
	 */
	@Override
	public void runWithEvent(IAction action, Event event) {
		run(action);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		for (Resource r : selectedResources) {
			if (!resourceHasAdapter(r)) {
				EContentAdapter liveValidationContentAdapter = new LiveValidationContentAdapter(this);
				r.eAdapters().add(liveValidationContentAdapter);
			}
		}
	}

	private boolean resourceHasAdapter(Resource r) {
		for (Adapter next : r.eAdapters()) {
			if (next instanceof LiveValidationContentAdapter) {
				return true;
			}
		}

		return false;
	}
}
