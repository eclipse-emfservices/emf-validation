/******************************************************************************
 * Copyright (c) 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation 
 ****************************************************************************/


package org.eclipse.emf.validation.internal.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;


/**
 * Adapts a Java validation method to the constraint API.  This class wraps
 * invocations to a reflective method bearing this signature:
 * <pre>
 *    boolean <i>method-name</i>(IValidationContext ctx, Map extractions)
 * </pre>
 * 
 * @author Christian W. Damus (cdamus)
 */
class CustomConstraintAdapter
	extends AbstractConstraintWithExtractionsAdapter {
	
	/**
	 * Required signature of custom validation methods.
	 */
	private static final Class[] METHOD_SIGNATURE = new Class[]{
			org.eclipse.emf.validation.IValidationContext.class,
			java.util.Map.class,
		};
	
	/**
	 * Object on which to invoke the validation {@link #delegateMethod}.  May be
	 * <code>null</code> if the method is static.
	 * 
	 * @see #delegateMethod
	 */
	private final Object delegateInstance;
	
	/** The method that implements the validation algorithm. */
	private final Method delegateMethod;
	
	/** Reusable array for arguments, to avoid construction and GC overhead. */
	private final Object[] delegateArgs = new Object[2];
	
	/**
	 * When this is <CODE>true</CODE>, my {@link #delegateMethod} is invalid,
	 * so I had better not try to invoke it.
	 */
	private boolean delegateInvalid = false;

	/**
	 * Initializes me to adaptive a reflective method to the standard
	 * validation interface.
	 * 
	 * @param descriptor the constraint descriptor.  Must not be <code>null</code>
	 * @param clazz the class that defines the validation method.  Must not be
	 *     <code>null</code>
	 * @param methodName the name of the validation method.  Must not be
	 *     <code>null</code>
	 * 
	 * @throws NoSuchMethodException if the <code>methodName</code> is not
	 *     defined with the correct signature in the specified class
	 * @throws InstantiationException if an exception occurs in instantiating
	 *     the specified class, in the case where the method is
	 *     non-<code>static</code>
	 * @throws IllegalAccessException if the no-argument constructor of the
	 *     specified class is not <code>public</code>, in the case where the
	 *     method is non-<code>static</code>
	 */
	CustomConstraintAdapter(
			IXmlConstraintDescriptor descriptor,
			Class clazz,
			String methodName)
			throws NoSuchMethodException,
					InstantiationException,
					IllegalAccessException {
		
		super(descriptor);

		assert clazz != null;
		assert methodName != null;

		Method method = clazz.getMethod(
				methodName,
				METHOD_SIGNATURE);

		if ((method != null) && isBoolean(method)) {
			this.delegateMethod = method;
			
			if (Modifier.isStatic(method.getModifiers())) {
				this.delegateInstance = null;
			} else {
				this.delegateInstance = JavaConstraintParser.getInstance(clazz);
			}
		} else {
			throw new NoSuchMethodException(
					"[bB]oolean " + methodName //$NON-NLS-1$
						+ "(EObject, IValidationContext, Map)"); //$NON-NLS-1$
		}
	}

	/**
	 * Invokes my delegate method, if it has not been marked invalid by a
	 * previous failed invocation.
	 * 
	 * @param ctx the validation context
	 * @param bindings the extraction variable bindings provided by the
	 *    delegate method if validation should fail
	 * @return the result of the delegate method, or just <CODE>true</CODE>
	 *    if the method was previously found invalid (in order to be quiet)
	 * @deprecated
	 */
	public boolean validate(IValidationContext ctx, Map bindings) {
		Boolean result = Boolean.TRUE;

		if (!delegateInvalid) {
			try {
				delegateArgs[0] = ctx;
				delegateArgs[1]	= bindings;
				
				result = (Boolean)getDelegateMethod().invoke(
						getDelegateInstance(),
						delegateArgs);
			} catch (IllegalAccessException e) {
				delegateInvalid = true;
				
				Trace.catching(getClass(), "invokeDelegate", e); //$NON-NLS-1$
				
				Log.errorMessage(
						EMFModelValidationStatusCodes.CONSTRAINT_DISABLED,
						EMFModelValidationStatusCodes.DELEGATE_METHOD_INACCESSIBLE_MSG,
						new Object[] {
								   getDescriptor().getId(),
								   getDelegateMethod().getDeclaringClass().getName(),
								   getDelegateMethod().getName()},
						e);
			} catch (InvocationTargetException e) {
				delegateInvalid = true;
				result = Boolean.FALSE;
				getDescriptor().setError(e.getTargetException());
				
				Trace.catching(getClass(), "invokeDelegate", e); //$NON-NLS-1$
				
				Log.errorMessage(
						EMFModelValidationStatusCodes.CONSTRAINT_DISABLED,
						EMFModelValidationStatusCodes.DELEGATE_METHOD_FAILED_MSG,
						new Object[] {
								   getDescriptor().getId(),
								   getDelegateMethod().getDeclaringClass().getName(),
								   getDelegateMethod().getName()},
						e);
			}
		}

		return result.booleanValue();
	}

	/**
	 * Determines whether the return type of a <CODE>method</CODE> is boolean
	 * (either big or little 'B'; it doesn't matter).
	 * 
	 * @param method the method to be tested
	 * @return <CODE>true</CODE> if the <CODE>method</CODE>'s return type
	 *         is either the primitive or wrapper boolean type; <CODE>false
	 *         </CODE>, otherwise
	 */
	private boolean isBoolean(Method method) {
		Class returnType = method.getReturnType();

		return (returnType == Boolean.class) || (returnType == boolean.class);
	}

	/**
	 * Accesses the instance on which the delegate method is invoked.
	 * This will be <code>null</code> if the delegate method is
	 * <code>static</code>.
	 * 
	 * @return the delegate instance
	 */
	private Object getDelegateInstance() {
		return delegateInstance;
	}

	/**
	 * Accesses the method that implements the validation algorithm.
	 * 
	 * @return the method to which validation is delegated
	 */
	private Method getDelegateMethod() {
		return delegateMethod;
	}
}
