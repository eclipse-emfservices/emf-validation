/**
 * <copyright>
 *
 * Copyright (c) 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id: ConstraintsSelectionBlock.java,v 1.5 2007/11/14 18:03:47 cdamus Exp $
 */
package org.eclipse.emf.validation.ui.preferences;

import java.text.MessageFormat;
import java.util.List;

import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.preferences.EMFModelValidationPreferences;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.ui.internal.ValidationUIPlugin;
import org.eclipse.emf.validation.ui.internal.l10n.ValidationUIMessages;
import org.eclipse.emf.validation.ui.internal.preferences.CategoryTreeNode;
import org.eclipse.emf.validation.ui.internal.preferences.ConstraintDetailsHelper;
import org.eclipse.emf.validation.ui.internal.preferences.ConstraintNode;
import org.eclipse.emf.validation.ui.internal.preferences.ICategoryTreeNode;
import org.eclipse.emf.validation.ui.internal.preferences.IConstraintNode;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * Encapsulation of the control for browsing and manipulating constraints in the
 * constraints preference page.
 * <p>
 * Clients can use this class to implement their own filtered constraints
 * preference page.
 * </p>
 *
 * @since 1.1
 *
 * @author David Cummings (dcummin)
 * @author Christian W. Damus (cdamus)
 */
public class ConstraintsSelectionBlock {

	/** name of the lock icon file */
	private static final String LOCK_ICON = "full/lock.gif"; //$NON-NLS-1$

	static final String CATEGORIES_PROMPT = ValidationUIMessages.prefs_categories_prompt;
	static final String CONSTRAINTS_PROMPT = ValidationUIMessages.prefs_constraints_prompt;
	static final String NO_SELECTION = ValidationUIMessages.prefs_no_selection;
	static final String NO_CATEGORY_DESCRIPTION = ValidationUIMessages.prefs_no_description_category;

	private CheckboxTreeViewer categoryTree;
	private CheckboxTableViewer constraintList;
	private StyledText detailsArea;

	private Mediator mediator;

	private ICategoryTreeNode rootcategory;

	private final IConstraintFilter filter;

	static {
		// ensure that the statically declared (in XML) constraints are
		// available for display to the user
		ModelValidationService.getInstance().loadXmlConstraintDeclarations();
	}

	/**
	 * Initializes me without a constraint filter.
	 */
	public ConstraintsSelectionBlock() {
		this(IConstraintFilter.IDENTITY_INSTANCE);
	}

	/**
	 * Initializes me with a constraint filter.
	 *
	 * @param filter used to filter the constraints which are presented in this
	 *               composite (must not be <code>null</code>)
	 */
	public ConstraintsSelectionBlock(IConstraintFilter filter) {
		if (filter == null) {
			throw new IllegalArgumentException("null filter"); //$NON-NLS-1$
		}

		this.filter = filter;
	}

	/**
	 * Content provider for the category tree.
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private class CategoryTreeContents implements ITreeContentProvider {

		// implements the inherited method
		@Override
		public Object[] getChildren(Object parentElement) {
			return ((ICategoryTreeNode) parentElement).getChildren();
		}

		// implements the inherited method
		@Override
		public Object getParent(Object element) {
			return ((ICategoryTreeNode) element).getParent();
		}

		// implements the inherited method
		@Override
		public boolean hasChildren(Object element) {
			return ((ICategoryTreeNode) element).hasChildren();
		}

		// implements the inherited method
		@Override
		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		// implements the inherited method
		@Override
		public void dispose() {
			// no cached resources to dispose
		}

		// implements the inherited method
		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// nothing to do
		}
	}

	/**
	 * Content provider for the constraints list.
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private class ConstraintListContents implements IStructuredContentProvider, ICheckStateListener {

		private CheckboxTableViewer viewer;
		private ICategoryTreeNode category;

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement == null) {
				return new Object[0];
			} else {
				category = (ICategoryTreeNode) inputElement;

				return category.getConstraints().toArray();
			}
		}

		@Override
		public void dispose() {
			// nothing to dispose
		}

		@Override
		public void inputChanged(Viewer newViewer, Object oldInput, Object newInput) {
			if (viewer != null) {
				viewer.removeCheckStateListener(this);
			}

			viewer = (CheckboxTableViewer) newViewer;
			category = (ICategoryTreeNode) newInput;

			if (viewer != null) {
				viewer.addCheckStateListener(this);
			}
		}

		/*
		 * (non-Javadoc) Redefines/Implements/Extends the inherited method.
		 */
		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			category.updateCheckState((IConstraintNode) event.getElement());
		}
	}

	/**
	 * A <i>Mediator</i> to coordinate the category tree, constraints list, and
	 * details pane in the GUI.
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private class Mediator implements ISelectionChangedListener, ICheckStateListener {

		private boolean respondingToUserSelection;

		// implements the interface method
		@Override
		public void checkStateChanged(CheckStateChangedEvent event) {
			Object element = event.getElement();

			if (element instanceof ICategoryTreeNode) {
				ICategoryTreeNode node = (ICategoryTreeNode) element;

				if (!respondingToUserSelection) {
					respondingToUserSelection = true;

					try {
						node.checkStateChanged(event);

						// update the constraint selections of the currently
						// selected category (because the one that changed
						// might be an ancestory
						IStructuredSelection selection = (IStructuredSelection) getCategoryTree().getSelection();

						if (!selection.isEmpty()) {
							selectCategory((ICategoryTreeNode) selection.getFirstElement());
						}
					} finally {
						respondingToUserSelection = false;
					}
				}
			} else {
				IConstraintNode node = (IConstraintNode) element;

				if (!respondingToUserSelection) {
					respondingToUserSelection = true;

					try {
						node.checkStateChanged(event);
					} finally {
						respondingToUserSelection = false;
					}
				}
			}
		}

		// implements the interface method
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();

			if (event.getSource().equals(getCategoryTree())) {
				handleCategorySelection(selection);
			} else if (event.getSource().equals(getConstraintList())) {
				handleConstraintSelection(selection);
			}
		}

		/**
		 * Handles a selection change in the category tree.
		 *
		 * @param selection the new selection
		 */
		private void handleCategorySelection(IStructuredSelection selection) {
			if (!selection.isEmpty()) {
				selectCategory((ICategoryTreeNode) selection.getFirstElement());
			} else {
				getConstraintList().setInput(null);
				clearDetailsArea();
			}
		}

		/**
		 * Selects the specified category in the constraints list.
		 *
		 * @param category the category to select
		 */
		private void selectCategory(ICategoryTreeNode category) {
			getConstraintList().setInput(category);
			selectConstraints(category);
			setDetails(category);
		}

		/**
		 * Select, in the table viewer, the currently enabled constraints.
		 *
		 * @param categoryNode the currently selected category node
		 */
		private void selectConstraints(ICategoryTreeNode categoryNode) {
			getConstraintList().setCheckedElements(categoryNode.getSelectedConstraints());
		}

		/**
		 * Handles a selection change in the constraints list.
		 *
		 * @param selection the new selection
		 */
		private void handleConstraintSelection(IStructuredSelection selection) {
			if (!selection.isEmpty()) {
				setDetails((IConstraintNode) selection.getFirstElement());
			} else {
				clearDetailsArea();
			}
		}

		/**
		 * Clears my details area, showing the "no selection" message.
		 */
		void clearDetailsArea() {
			getDetailsArea().setText(NO_SELECTION);
		}

		/**
		 * Sets the details area to show the currently selected <code>category</code>'s
		 * category details.
		 *
		 * @param category the category in the category tree
		 */
		private void setDetails(ICategoryTreeNode category) {
			String description = (category == null) ? null : category.getDescription();

			if (description == null) {
				description = NO_CATEGORY_DESCRIPTION;
			}

			Category actualCategory = null;
			if (category != null) {
				actualCategory = category.getCategory();
			}
			// If we are a mandatory category then we must provide some cue to this fact.
			if (actualCategory != null && actualCategory.isMandatory()) {
				getDetailsArea().setText(MessageFormat.format(ValidationUIMessages.prefs_mandatory_category, description));

			} else {
				getDetailsArea().setText(description);
			}
		}

		/**
		 * Sets the details area to show the currently selected
		 * <code>constraint</code>'s details.
		 *
		 * @param constraint the constraint meta-data
		 */
		private void setDetails(IConstraintNode constraint) {
			// lots of style info
			List<StyleRange> styles = new java.util.ArrayList<>(32);
			String text = ConstraintDetailsHelper.formatConstraintDescription(constraint, getCurrentCategorySelection(),
					styles);

			getDetailsArea().setText(text);
			getDetailsArea().setStyleRanges(styles.toArray(new StyleRange[styles.size()]));
		}
	}

	/**
	 * Creates the constraints selection composite on the given parent and filters
	 * the composite based on the provided filter.
	 *
	 * @param parent parent for the newly created composite
	 * @return the resulting constraint selection composite
	 */
	public Composite createComposite(Composite parent) {
		SashForm result = new SashForm(parent, SWT.VERTICAL);

		result.setFont(parent.getFont());

		SashForm topPart = new SashForm(result, SWT.HORIZONTAL);
		createCategoryTree(topPart);
		createConstraintList(topPart);

		createDetailsArea(result);

		result.setWeights(new int[] { 70, 30 });

		return result;
	}

	/**
	 * Helper method to create the category-tree part of the GUI. The result is a
	 * form containing the checkbox tree and a prompt label.
	 *
	 * @param parent the parent composite in which to create the tree
	 * @return the tree part of the GUI (itself a composite form)
	 */
	private Control createCategoryTree(Composite parent) {
		Composite form = new Composite(parent, SWT.NONE);
		FormLayout layout = new FormLayout();
		form.setLayout(layout);

		Label prompt = new Label(form, SWT.NONE);
		prompt.setText(CATEGORIES_PROMPT);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		prompt.setLayoutData(data);

		categoryTree = new CheckboxTreeViewer(form);
		data = new FormData();
		data.top = new FormAttachment(prompt, 4);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(100, 0);
		categoryTree.getControl().setLayoutData(data);

		rootcategory = CategoryTreeNode.createRoot(categoryTree, filter);
		categoryTree.setLabelProvider(new LabelProvider() {
			private final Image lockImage = ValidationUIPlugin.getImageDescriptor(LOCK_ICON).createImage(true);

			@Override
			public void dispose() {
				lockImage.dispose();
				super.dispose();
			}

			@Override
			public Image getImage(Object element) {
				ICategoryTreeNode node = (ICategoryTreeNode) element;

				if (node.getCategory() != null) {
					if (node.getCategory().isMandatory()) {
						return lockImage;
					}
				}

				return null;
			}
		});
		categoryTree.setContentProvider(new CategoryTreeContents());
		categoryTree.setInput(rootcategory);
		markEnabledCategories(rootcategory);

		categoryTree.addCheckStateListener(getMediator());
		categoryTree.addSelectionChangedListener(getMediator());

		return categoryTree.getTree();
	}

	/**
	 * Helper method to create the constraint-list part of the GUI. The result is a
	 * form containing the constraints list and a prompt label.
	 *
	 * @param parent the parent composite in which to create the list
	 * @return the list part of the GUI (itself a composite form)
	 */
	private Control createConstraintList(Composite parent) {
		Composite form = new Composite(parent, SWT.NONE);
		FormLayout layout = new FormLayout();
		form.setLayout(layout);

		Label prompt = new Label(form, SWT.NONE);
		prompt.setText(CONSTRAINTS_PROMPT);
		FormData data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		prompt.setLayoutData(data);

		constraintList = CheckboxTableViewer.newCheckList(form, SWT.CHECK | SWT.BORDER);
		data = new FormData();
		data.top = new FormAttachment(prompt, 4);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(100, 0);
		constraintList.getControl().setLayoutData(data);

		constraintList.setContentProvider(new ConstraintListContents());

		constraintList.setLabelProvider(new LabelProvider() {
			private final Image lockImage = ValidationUIPlugin.getImageDescriptor(LOCK_ICON).createImage(true);

			// extends the inherited method
			@Override
			public void dispose() {
				lockImage.dispose();
				super.dispose();
			}

			// redefines the inherited method
			@Override
			public Image getImage(Object element) {
				IConstraintNode constraint = (IConstraintNode) element;

				if (constraint.isErrored()) {
					return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
				} else if (constraint.isMandatory()) {
					return lockImage;
				} else {
					return null;
				}
			}

			// redefines the inherited method
			@Override
			public String getText(Object element) {
				return ((IConstraintNode) element).getName();
			}
		});

		constraintList.setComparator(new ViewerComparator());
		constraintList.addCheckStateListener(getMediator());
		constraintList.addSelectionChangedListener(getMediator());

		return constraintList.getControl();
	}

	/**
	 * Helper method to create the details are of the GUI.
	 *
	 * @param parent the parent composite in which to create the details area
	 * @return the details text area
	 */
	private Control createDetailsArea(Composite parent) {
		detailsArea = new StyledText(parent, SWT.READ_ONLY | SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);

		getMediator().clearDetailsArea();

		return detailsArea;
	}

	/**
	 * Obtains my category tree.
	 *
	 * @return the tree
	 */
	private CheckboxTreeViewer getCategoryTree() {
		return categoryTree;
	}

	/**
	 * Obtains my constraints list.
	 *
	 * @return the list
	 */
	private CheckboxTableViewer getConstraintList() {
		return constraintList;
	}

	/**
	 * Obtains my details area.
	 *
	 * @return the details text area
	 */
	private StyledText getDetailsArea() {
		return detailsArea;
	}

	/**
	 * Obtains my mediator.
	 *
	 * @return my mediator
	 */
	private Mediator getMediator() {
		if (mediator == null) {
			mediator = new Mediator();
		}

		return mediator;
	}

	/**
	 * Saves the constraint enablement changes made in the composite to the
	 * validation preferences
	 */
	public boolean performOk() {
		rootcategory.applyToPreferences();
		EMFModelValidationPreferences.save();

		return true;
	}

	/**
	 * Restores the defaults for the constraints listed in the composite
	 */
	public void performDefaults() {
		rootcategory.restoreDefaults();

		// update the checked state of the current contents of the Constraints
		// list (if any)
		CheckboxTableViewer viewer = getConstraintList();
		Object input = viewer.getInput();

		if (input != null) {
			Object[] elements = ((IStructuredContentProvider) getConstraintList().getContentProvider())
					.getElements(input);

			if (elements != null) {
				int length = elements.length;

				for (int i = 0; i < length; i++) {
					IConstraintNode node = (IConstraintNode) elements[i];
					viewer.setChecked(node, node.isChecked());
				}
			}
		}
	}

	/**
	 * Obtains the currently selected category, if any.
	 *
	 * @return the current category
	 */
	private Category getCurrentCategorySelection() {
		IStructuredSelection selection = (IStructuredSelection) getCategoryTree().getSelection();

		if (selection.isEmpty()) {
			return null;
		} else {
			return ((ICategoryTreeNode) selection.getFirstElement()).getCategory();
		}
	}

	/**
	 * Helper method to set the currently enabled categories in the tree. Also sets
	 * gray states as appropriate.
	 *
	 * @param root the root of the tree model
	 */
	private void markEnabledCategories(ICategoryTreeNode root) {
		markEnabledCategories(root.getChildren());
	}

	private void markEnabledCategories(ICategoryTreeNode[] categories) {
		for (ICategoryTreeNode next : categories) {
			getCategoryTree().setChecked(next, next.isChecked());
			getCategoryTree().setGrayed(next, next.isGrayed());

			markEnabledCategories(next.getChildren());
		}
	}

	/*
	 * (non-Javadoc) Extends the inherited method.
	 */
	public void dispose() {
		// clean up the cached constraint nodes
		ConstraintNode.flushCache();
	}
}
