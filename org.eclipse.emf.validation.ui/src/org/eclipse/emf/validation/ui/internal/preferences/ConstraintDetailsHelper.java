/**
 * <copyright>
 *
 * Copyright (c) 2003-2006 IBM Corporation and others.
 * All rights reserved.   This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.emf.validation.ui.internal.preferences;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.ui.internal.l10n.ValidationUIMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;

/**
 * Helper utilities for dealing with the display of constraint details.
 * 
 * @author Christian W. Damus (cdamus)
 */
class ConstraintDetailsHelper {
	
	private static final String BOLD_START = "<b>"; //$NON-NLS-1$
	private static final String BOLD_END = "</b>"; //$NON-NLS-1$

	static final String PLATFORM_NEWLINE = System
		.getProperty("line.separator"); //$NON-NLS-1$
	
	static final String ALSO_IN_CATEGORIES = 
		PLATFORM_NEWLINE + PLATFORM_NEWLINE +
		ValidationUIMessages.prefs_constraints_also;
	
	static final String CONSTRAINT_DESCRIPTION_PATTERN = 
		ValidationUIMessages.prefs_description_constraint;
	static final String ERROR_CONSTRAINT_DESCRIPTION_PATTERN = 
		ValidationUIMessages.prefs_description_constraint_error;

	static final String NO_CONSTRAINT_DESCRIPTION =
		ValidationUIMessages.prefs_no_description_constraint;
	
	/**
	 * Formats the description of the specified <code>constraint</code> and
	 * returns it.  The list of <code>styles</code> is populated with the
	 * {@link StyleRange}s implementing the bold-text highlights parsed from the
	 * description pattern.
	 * 
	 * @param constraint the constraint whose description is to be formatted
	 * @param selectedCategory the currently selected category.  This is used
	 *     to determine which categories other than the current selection the
	 *     <code>constraint</code> belongs to
	 * @param styles collects the style information to be passed to the text
	 *     widget
	 * @return the description text, formatted according to the pattern in the
	 *     localized resource file
	 */
	static String formatConstraintDescription(
			IConstraintNode constraint,
			Category selectedCategory,
			List styles) {
		
		String description = constraint.getDescription();
		
		if (description == null) {
			description = NO_CONSTRAINT_DESCRIPTION;
		}

		String messagePattern;
		if (constraint.isErrored()) {
			messagePattern = ERROR_CONSTRAINT_DESCRIPTION_PATTERN;
		} else {
			messagePattern = CONSTRAINT_DESCRIPTION_PATTERN;
		}
		
		String text = NLS.bind(
				messagePattern,
				new Object[] {
						   constraint.getId(),
						   constraint.getEvaluationMode(),
						   description,
						   constraint.getSeverity()});
		
		Collection categories = constraint.getCategories();
		if (categories.size() > 1) {
			// also display the other categories that contain this constraint
			text = text + getOtherCategories(selectedCategory, categories);
		}
		
		return parseStyles(text, styles);
	}
	
	/**
	 * Obtains a string listing the other categories in which the currently
	 * selected constraint is a member that are not the currently selected
	 * category.
	 * 
	 * @param selected the currently selected category
	 * @param categories the categories which contain the constraint
	 * @return the other categories than the currently selected one
	 */
	private static String getOtherCategories(
			Category selected,
			Collection categories) {
		
		StringBuffer result = new StringBuffer(64);
		result.append(ALSO_IN_CATEGORIES);
		
		for (Iterator iter = categories.iterator(); iter.hasNext();) {
			Category next = (Category)iter.next();
			
			if (next != selected) {
				result.append(PLATFORM_NEWLINE);
				result.append(next.getQualifiedName());
			}
		}
		
		return result.toString();
	}
	
	/**
	 * Parses out the <tt>&lt;b&gt;...&lt;/b&gt;</tt> style information from the
	 * specified <code>text</code> and adds corresponding {@link StyleRange}s
	 * to the <code>styles</code> collector parameter.  The result is the
	 * <code>text</code> minus the style markup.
	 * 
	 * @param text marked up text
	 * @param styles a list which collects the corresponding style ranges
	 * @return the original <code>text</code> minus the markup
	 */
	private static String parseStyles(String text, List styles) {
		int pos = -1;
		int lastPos = 0;
		
		StringBuffer result = new StringBuffer(text.length());
		
		while (lastPos < text.length()) {
			pos = text.indexOf(BOLD_START, lastPos);  // known BMP characters
			
			if (pos < 0) {
				break;
			} else {
				result.append(text.substring(lastPos, pos));
				
				lastPos = pos + BOLD_START.length();
				
				pos = text.indexOf(BOLD_END, lastPos);  // known BMP characters
				
				if (pos < 0) {
					// implied </b> at end of input
					pos = text.length();
				}
				
				styles.add(new StyleRange(
								result.length(), pos - lastPos,
								null, null,
								SWT.BOLD));
				
				result.append(text.substring(lastPos, pos));
				
				lastPos = Math.min(pos + BOLD_END.length(), text.length()); 
			}
		}
		
		result.append(text.substring(lastPos, text.length()));
		
		return result.toString();
	}
}
