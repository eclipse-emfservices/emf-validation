/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.EMFPlugin;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.validation.internal.l10n.ValidationMessages;
import org.eclipse.emf.validation.internal.service.ClientContextManager;
import org.eclipse.emf.validation.service.EventTypeService;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * Plug-in class for the EMF Model Validation framework.
 * </p>
 * <p>
 * This class is not intended to be used by clients.
 * </p>
 * 
 * @author Christian W. Damus (cdamus)
 */
public final class EMFModelValidationPlugin extends EMFPlugin {
	//TODO This plugin class contains many of the tracing options in common with other plugins. Perhaps these should be conglomerated so that they share alot of this code?

	///
	// TRACING STRINGS
	//
	
	/**
	 * String containing an open parenthesis.
	 * 
	 */
	protected static final String PARENTHESIS_OPEN = "("; //$NON-NLS-1$

	/**
	 * String containing a close parenthesis.
	 * 
	 */
	protected static final String PARENTHESIS_CLOSE = ")"; //$NON-NLS-1$

	/**
	 * Prefix for tracing the changing of values.
	 * 
	 */
	protected static final String PREFIX_CHANGING = "CHANGING "; //$NON-NLS-1$
	
	/**
	 * Prefix for tracing the catching of throwables.
	 * 
	 */
	protected static final String PREFIX_CATCHING = "CAUGHT "; //$NON-NLS-1$

	/**
	 * Prefix for tracing the throwing of throwables.
	 * 
	 */
	protected static final String PREFIX_THROWING = "THROWN "; //$NON-NLS-1$

	/**
	 * Prefix for tracing the entering of methods.
	 * 
	 */
	protected static final String PREFIX_ENTERING = "ENTERING "; //$NON-NLS-1$

	/**
	 * Prefix for tracing the exiting of methods.
	 * 
	 */
	protected static final String PREFIX_EXITING = "EXITING "; //$NON-NLS-1$

	/**
	 * Separator for methods.
	 * 
	 */
	protected static final String SEPARATOR_METHOD = "#"; //$NON-NLS-1$

	/**
	 * Separator for parameters.
	 * 
	 */
	protected static final String SEPARATOR_PARAMETER = ", "; //$NON-NLS-1$

	/**
	 * Separator for return values.
	 * 
	 */
	protected static final String SEPARATOR_RETURN = ":"; //$NON-NLS-1$

	/**
	 * Separator containing a space.
	 * 
	 */
	protected static final String SEPARATOR_SPACE = " "; //$NON-NLS-1$
	
	/**
	 * Label indicating old value.
	 * 
	 */
	protected static final String LABEL_OLD_VALUE = "old="; //$NON-NLS-1$

	/**
	 * Label indicating new value.
	 * 
	 */
	protected static final String LABEL_NEW_VALUE = "new="; //$NON-NLS-1$
	
	/** Key for list separator. */
	private static final String LIST_SEPARATOR = ValidationMessages.list_separator;
		
	/** Key for list prefix. */
	private static final String LIST_PREFIX = ValidationMessages.list_prefix;
	
	/** Key for list suffix. */
	private static final String LIST_SUFFIX = ValidationMessages.list_suffix;
	
	/** Key for default list separator. */
	private static final String DEFAULT_LIST_SEPARATOR = ", "; //$NON-NLS-1$
	
	/** Key for default list prefix. */
	private static final String DEFAULT_LIST_PREFIX = ""; //$NON-NLS-1$
	
	/** Key for default list suffix. */
	private static final String DEFAULT_LIST_SUFFIX = ""; //$NON-NLS-1$
	
	/**
	 * Extension point name for the constraint providers extension point.
	 */
	public static final String CONSTRAINT_PROVIDERS_EXT_P_NAME =
		"constraintProviders"; //$NON-NLS-1$

	/**
	 * Extension point name for the constraint bindings extension point.
	 */
	public static final String CONSTRAINT_BINDINGS_EXT_P_NAME =
		"constraintBindings"; //$NON-NLS-1$

	/**
	 * Extension point name for the validation listeners extension point.
	 */
	public static final String VALIDATION_LISTENERS_EXT_P_NAME =
		"validationListeners"; //$NON-NLS-1$

	/**
	 * Extension point name for the event types extension point.
	 */
	public static final String EVENT_TYPES_EXT_P_NAME =
		"eventTypes"; //$NON-NLS-1$
	
	
	public static final EMFModelValidationPlugin INSTANCE =
		new EMFModelValidationPlugin();

	private static Implementation plugin;

	/**
	 * Initializes me.
	 */
	public EMFModelValidationPlugin() {
		super(new ResourceLocator[]{});
	}

	// implements the inherited method
	@Override
	public ResourceLocator getPluginResourceLocator() {
		return plugin;
	}

	/**
	 * Obtains the Eclipse plug-in that I implement.
	 * 
	 * @return my Eclipse plug-in self
	 */
	public static Implementation getPlugin() {
		return plugin;
	}

	/**
	 * Obtains my plug-in identifier.
	 * 
	 * @return my plug-in unique ID
	 */
	public static String getPluginId() {
		return getPlugin().getBundle().getSymbolicName();
	}

	/**
	 * The definition of the Eclipse plug-in flavour of this EMF plug-in.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	public static class Implementation extends EMFPlugin.EclipsePlugin {
		/**
		 * Initializes me with my Eclipse plug-in descriptor.
		 */
		public Implementation() {
			super();

			// Remember the static instance.
			//
			EMFModelValidationPlugin.plugin = this;
		}

		// extends the inherited method
		@Override
		public void start(BundleContext context) throws Exception {
			super.start(context);

			configureEventTypes();
			configureConstraints();
			configureConstraintBindings();
			configureValidationListeners();
		}

		/**
		 * Configures validation constraint providers based on the
		 * <tt>constraintProviders</tt> extension configurations.
		 */
		protected void configureConstraints() {
			IConfigurationElement[] configs =
				Platform.getExtensionRegistry().getConfigurationElementsFor(
					getPluginId(),
					CONSTRAINT_PROVIDERS_EXT_P_NAME);
			
			ModelValidationService.getInstance().configureProviders(configs);
		}

		/**
		 * Configures constraint bindings based on the
		 * <tt>constraintBindings</tt> extension configurations.
		 */
		protected void configureConstraintBindings() {
			IConfigurationElement[] configs =
				Platform.getExtensionRegistry().getConfigurationElementsFor(
					getPluginId(),
					CONSTRAINT_BINDINGS_EXT_P_NAME);
			
			ClientContextManager.getInstance().configureConstraintBindings(configs);
		}

		/**
		 * Configures validation listeners based on the
		 * <tt>validationListeners</tt> extension configurations.
		 */
		protected void configureValidationListeners() {
			IConfigurationElement[] configs =
				Platform.getExtensionRegistry().getConfigurationElementsFor(
					getPluginId(),
					VALIDATION_LISTENERS_EXT_P_NAME);
			
			ModelValidationService.getInstance().configureListeners(configs);
		}
		
		/**
		 * Configures custom event types based on the 
		 * <tt>eventTypes</tt> extension configuration
         *
         * @since 1.1
		 */
		protected void configureEventTypes() {
			IConfigurationElement[] configs =
				Platform.getExtensionRegistry().getConfigurationElementsFor(
					getPluginId(),
					EVENT_TYPES_EXT_P_NAME);
			
			EventTypeService.getInstance().configureEventTypes(configs);
		}
	}
	
    public static class Tracing {
    	/**
    	 * The cached debug options (for optimization).
    	 */
    	private static final Map<String, Boolean> cachedOptions = new HashMap<String, Boolean>();

    	/**
    	 * Retrieves a Boolean value indicating whether tracing is enabled.
    	 * 
    	 * @return Whether tracing is enabled for the plug-in.
    	 * 
    	 */
    	protected static boolean shouldTrace() {
    		return plugin.isDebugging();
    	}

    	/**
    	 * Retrieves a Boolean value indicating whether tracing is enabled for the
    	 * specified debug option.
    	 * 
    	 * @return Whether tracing is enabled for the debug option of the plug-in.
    	 * @param option The debug option for which to determine trace enablement.
    	 * 
    	 */
    	public static boolean shouldTrace(String option) {
    		if (shouldTrace()) {
    			Boolean value = null;
    			
    			synchronized (cachedOptions) {
    				value = cachedOptions.get(option);
    	
    				if (null == value) {
    					value =
    						Boolean.valueOf(
    								org.eclipse.core.runtime.Platform.getDebugOption(option));
    	
    					cachedOptions.put(option, value);
    				}
    			}
    			
    			return value.booleanValue();
    		}

    		return false;
    	}

    	/**
    	 * Retrieves a textual representation of the specified argument.
    	 * 
    	 * @return A textual representation of the specified argument.
    	 * @param argument The argument for which to retrieve a textual
    	 *                  representation.
    	 * 
    	 */
    	protected static String getArgumentString(Object argument) {
    		return String.valueOf(argument);
    	}

    	/**
    	 * Retrieves a textual representation of the specified arguments.
    	 * 
    	 * @return A textual representation of the specified arguments.
    	 * @param arguments The arguments for which to retrieve a textual
    	 *                   representation.
    	 * 
    	 */
    	protected static String getArgumentsString(Object[] arguments) {
    		StringBuffer buffer = new StringBuffer();

    		for (int i = 0; i < arguments.length; i++) {
    			buffer.append(getArgumentString(arguments[i]));

    			if (i < arguments.length - 1) {
    				buffer.append(SEPARATOR_PARAMETER);
    			}
    		}

    		return buffer.toString();
    	}

    	/**
    	 * Traces the specified message.
    	 * 
    	 * @param message The message to be traced.
    	 * 
    	 */
    	public static void trace(String message) {
    		if (shouldTrace()) {
    			System.out.println(message);
    		}
    	}

    	/**
    	 * Traces the specified message for the specified
    	 * debug option.
    	 * 
    	 * @param option The debug option for which to trace.
    	 * @param message The message to be traced.
    	 * 
    	 */
    	public static void trace(String option, String message) {
    		if (shouldTrace(option)) {
    			trace(message);
    		}
    	}
    	
    	/**
    	 * Traces the changing of a value.
    	 * 
    	 * @param option The debug option for which to trace.
    	 * @param valueDescription The description of the value which is changing.
    	 * @param oldValue The old value.
    	 * @param newValue The new value.
    	 */
    	public static void changing(
    	        String option,
    	        String valueDescription,
    	        Object oldValue,
    	        Object newValue) {
    		    
    	        if (shouldTrace(option)) {
    	            trace(
    	                PREFIX_CHANGING
    	                	+ valueDescription
    	                	+ SEPARATOR_SPACE
    	                	+ LABEL_OLD_VALUE
    	                	+ getArgumentString(oldValue)
    	                	+ SEPARATOR_PARAMETER
    	                   	+ LABEL_NEW_VALUE
    	                	+ getArgumentString(newValue)
    						);
    	        }
    	    }
    	
    	/**
    	 * 
    	 * @param option The debug option for which to trace.
    	 * @param clazz The class in which the value is changing.
    	 * @param methodName The name of the method in which the value is changing.
    	 * @param valueDescription The description of the value which is changing.
    	 * @param oldValue The old value.
    	 * @param newValue The new value.
    	 */
    	public static void changing(
            String option,
            Class<?> clazz,
            String methodName,
            String valueDescription,
            Object oldValue,
            Object newValue) {
    	    
            if (shouldTrace(option)) {
                trace(
                    PREFIX_CHANGING
                    	+ valueDescription
                    	+ SEPARATOR_SPACE
                    	+ LABEL_OLD_VALUE
                    	+ getArgumentString(oldValue)
                    	+ SEPARATOR_PARAMETER
                       	+ LABEL_NEW_VALUE
                    	+ getArgumentString(newValue)
                    	+ SEPARATOR_SPACE
                    	+ PARENTHESIS_OPEN
    					+ clazz.getName()
    					+ SEPARATOR_METHOD
    					+ methodName
    					+ PARENTHESIS_CLOSE
    					);
            }
        }

    	/**
    	 * Traces the catching of the specified throwable in the specified method of
    	 * the specified class.
    	 * 
    	 * @param option The debug option for which to trace.
    	 * @param clazz The class in which the throwable is being caught.
    	 * @param methodName The name of the method in which the throwable is being
    	 *                    caught.
    	 * @param throwable The throwable that is being caught.
    	 * 
    	 */
    	public static void catching(
    		String option,
    		Class<?> clazz,
    		String methodName,
    		Throwable throwable) {

    		if (shouldTrace(option)) {

    			trace(
    				PREFIX_CATCHING
    					+ throwable.getMessage()
    					+ SEPARATOR_SPACE
    					+ PARENTHESIS_OPEN
    					+ clazz.getName()
    					+ SEPARATOR_METHOD
    					+ methodName
    					+ PARENTHESIS_CLOSE);

    			throwable.printStackTrace(System.err);
    		}
    	}

    	/**
    	 * Traces the throwing of the specified throwable from the specified method
    	 * of the specified class.
    	 * 
    	 * @param option The debug option for which to trace.
    	 * @param clazz The class from which the throwable is being thrown.
    	 * @param methodName The name of the method from which the throwable is
    	 *                    being thrown.
    	 * @param throwable The throwable that is being thrown.
    	 * 
    	 */
    	public static void throwing(
    		String option,
    		Class<?> clazz,
    		String methodName,
    		Throwable throwable) {

    		if (shouldTrace(option)) {

    			trace(
    				PREFIX_THROWING
    					+ throwable.getMessage()
    					+ SEPARATOR_SPACE
    					+ PARENTHESIS_OPEN
    					+ clazz.getName()
    					+ SEPARATOR_METHOD
    					+ methodName
    					+ PARENTHESIS_CLOSE);

    			throwable.printStackTrace(System.err);
    		}
    	}

    	/**
    	 * Traces the entering into the specified method of the specified class,
    	 * with the specified parameters.
    	 * 
    	 * @param option The debug option for which to trace.
    	 * @param clazz The class whose method is being entered.
    	 * @param methodName The name of method that is being entered.
    	 * @param parameters The parameters to the method being entered.
    	 * 
    	 */
    	public static void entering(
    		String option,
    		Class<?> clazz,
    		String methodName,
    		Object... parameters) {

    		if (shouldTrace(option)) {

    			trace(
    				PREFIX_ENTERING
    					+ clazz.getName()
    					+ SEPARATOR_METHOD
    					+ methodName
    					+ PARENTHESIS_OPEN
    					+ getArgumentsString(parameters)
    					+ PARENTHESIS_CLOSE);
    		}
    	}

    	/**
    	 * Traces the exiting from the specified method of the specified class.
    	 * 
    	 * @param option The debug option for which to trace.
    	 * @param clazz The class whose method is being exited.
    	 * @param methodName The name of method that is being exited.
    	 * 
    	 */
    	public static void exiting(
    		String option,
    		Class<?> clazz,
    		String methodName) {

    		if (shouldTrace(option)) {

    			trace(
    				PREFIX_EXITING
    					+ clazz.getName()
    					+ SEPARATOR_METHOD
    					+ methodName);
    		}
    	}

    	/**
    	 * Traces the exiting from the specified method of the specified class,
    	 * with the specified return value.
    	 * 
    	 * @param option The debug option for which to trace.
    	 * @param clazz The class whose method is being exited.
    	 * @param methodName The name of method that is being exited.
    	 * @param returnValue The return value of the method being exited.
    	 * 
    	 */
    	public static void exiting(
    		String option,
    		Class<?> clazz,
    		String methodName,
    		Object returnValue) {

    		if (shouldTrace(option)) {

    			trace(
    				PREFIX_EXITING
    					+ clazz.getName()
    					+ SEPARATOR_METHOD
    					+ methodName
    					+ SEPARATOR_RETURN
    					+ getArgumentString(returnValue));
    		}
    	}
    }

	public static void catching(Class<?> class1, String functionName, Throwable exception) {
		Tracing.catching(EMFModelValidationDebugOptions.EXCEPTIONS_CATCHING, class1, functionName, exception);
	}
	
	public static void throwing(Class<?> class1, String functionName, Throwable exception) {
		Tracing.throwing(EMFModelValidationDebugOptions.EXCEPTIONS_THROWING, class1, functionName, exception);
	}

	public static void log(int code, String message, Throwable exception) {
		Status s = new Status(IStatus.INFO, plugin.getSymbolicName(),
			code, message != null ? message : "", exception); //$NON-NLS-1$
		
		getPlugin().log(s);
	}

	public static void warning(int code, String message, Throwable exception) {
		Status s = new Status(IStatus.WARNING, plugin.getSymbolicName(),
			code, message != null ? message : "", exception); //$NON-NLS-1$
		
		getPlugin().log(s);
	}

	/**
	 * Creates a localized, parameterized message from the specified pattern
	 * and argument keys in the resource bundle.
	 * 
	 * @param messagePattern resource bundle key of the message pattern
	 * @param args literal values as arguments to the pattern
	 * @return the formatted message
	 * 
	 * @see org.eclipse.osgi.util.NLS
	 */
	public static String getMessage(String messagePattern, Object... args) {
		return formatMessage(messagePattern, args);
	}
	
	/**
	 * Creates a localized, parameterized message from the specified pattern
	 * in the resource bundle.
	 * 
	 * @param messagePattern the message pattern
	 * @param args objects to substitute into the <tt>{0}</tt>, <tt>{1}</tt>,
	 *     etc. parameters in the message pattern
	 * @return the formatted message
	 * 
	 * @see org.eclipse.osgi.util.NLS
	 */
	private static String formatMessage(String messagePattern, Object... args) {
		try {
			return NLS.bind(messagePattern, args);
		} catch (Exception e) {
			// formats may throw IllegalArgumentExceptions and others
			catching(EMFModelValidationPlugin.class,
					"formatMessage", //$NON-NLS-1$
					e);
			
			return messagePattern;  // better than nothing?
		}
	}
	
	/**
	 * <p>
	 * Formats a collection of objects according to the conventions of the
	 * locale.
	 * For example, in English locales, the result is a comma-separated list
	 * with "and" preceding the last item (no commas if there are only two
	 * items).
	 * </p>
	 * <p>
	 * The individual elements of the collection are converted to strings using
	 * the {@link String#valueOf(java.lang.Object)} method.
	 * </p>
	 * 
	 * @param items an array of objects to format into a list
	 * @return the list, <code>strings[0]</code> if there is only one element,
	 *    or <code>""</code> if the array has no elements
	 */
	public static String formatList(Collection<?> items) {
		switch (items.size()) {
			case 0 :
				return ""; //$NON-NLS-1$
			case 1 :
				return String.valueOf(items.iterator().next());
			case 2 :
				return formatPair(items);
			default :
				return formatList2(items);
		}
	}
	
	/**
	 * Helper method to format a list of more than two items.
	 * 
	 * @param mgr the common core plug-in's resource manager, which is used to
	 *     retrieve the localized components of a list
	 * @param items the list of items (must be more than two)
	 * @return the list as a string
	 * 
	 * @see #formatList(Collection)
	 */
	private static String formatList2(Collection<?> items) {
		Iterator<?> iter = items.iterator();
		int max = items.size() - 1;

		final String sep = getString(
				LIST_SEPARATOR,
				DEFAULT_LIST_SEPARATOR);

		StringBuffer result = new StringBuffer(32);

		result.append(getString(LIST_PREFIX, DEFAULT_LIST_PREFIX));

		for (int i = 0; i <= max; i++) {
			if (i == 1) {
				result.append(getString(LIST_SEPARATOR, sep));
			} else if (i == max) {
				result.append(getString(LIST_SEPARATOR, sep));
			} else if (i > 1) {
				result.append(sep);
			}

			result.append(iter.next());
		}

		result.append(getString(LIST_SUFFIX, DEFAULT_LIST_SUFFIX));

		return result.toString();
	}
	
	private static String getString(String message, String defaultResult) {
		return message != null ? message : defaultResult;
	}
	
	/**
	 * Helper method to format a two-item list (which in some locales looks
	 * different from a list of more than two items).
	 * 
	 * @param mgr the common core plug-in's resource manager, which is used to
	 *     retrieve the localized components of a list
	 * @param items the pair of items (must be exactly two)
	 * @return the pair as a string
	 * 
	 * @see #formatList(Collection)
	 */
	private static String formatPair(Collection<?> items) {
		Iterator<?> iter = items.iterator();

		StringBuffer result = new StringBuffer(32);

		result.append(iter.next());

		result.append(getString(LIST_SEPARATOR, DEFAULT_LIST_SEPARATOR));

		result.append(iter.next());

		return result.toString();
	}
}
