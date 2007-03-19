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


package org.eclipse.emf.validation.internal.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.internal.EMFModelValidationPlugin;
import org.eclipse.emf.validation.internal.EMFModelValidationStatusCodes;
import org.eclipse.emf.validation.internal.util.DisabledConstraintStatus;
import org.eclipse.emf.validation.internal.util.Log;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.emf.validation.service.IValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.service.ValidationEvent;

/**
 * Implementation (with various framework methods) of the {@link IValidator}
 * interface, which is suitable for extending to implement the specific
 * validator interfaces.
 *
 * @author Christian W. Damus (cdamus)
 */
abstract class AbstractValidator implements IValidator {
	private final EvaluationMode mode;
	private final Map clientData = new java.util.HashMap();
	private final IProviderOperationExecutor executor;
	
	private boolean reportSuccesses = false;
	
	private Collection filters = null;
	
	/**
	 * Initializes me with the evaluation <code>mode</code> that I support and
	 * the operation <code>executor</code> that I use to execute provider
	 * operations.
	 * 
	 * @param mode my evaluation mode (must not be <code>null</code> or
	 *      {@link EvaluationMode#NULL}
	 * @param executor used by me to execute operations (must not be
	 *      <code>null</code>)
	 */
	protected AbstractValidator(
			EvaluationMode mode,
			IProviderOperationExecutor executor) {
		assert mode != null && !mode.isNull();
		assert executor != null;
		
		this.mode = mode;
		this.executor = executor;
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public final EvaluationMode getEvaluationMode() {
		return mode;
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public boolean isReportSuccesses() {
		return reportSuccesses;
	}

	/* (non-Javadoc)
	 * Implements the inherited method.
	 */
	public void setReportSuccesses(boolean reportSuccesses) {
		this.reportSuccesses = reportSuccesses;
	}
	
	public void putClientData(String key, Object data) {
		assert key != null : "null key"; //$NON-NLS-1$
		
		if (data == null) {
			clientData.remove(key);
		} else {
			clientData.put(key, data);
		}
	}
	
	public Object getClientData(String key) {
		return clientData.get(key);
	}

	/**
	 * Implements the interface method by delegating to the subclass
	 * implementation of {@link #doValidate(Collection)}.  The subclass-specific
	 * validation API is also encouraged to delegate to this method after
	 * first checking that arguments are of or coerced to the correct types.
	 */
	public final IStatus validate(Object object) {
		return validate(Collections.singleton(object));
	}

	/**
	 * <p>
	 * Implements the interface method by delegating to the subclass
	 * implementation of {@link #doValidate(Collection)}.  The subclass-specific
	 * validation API is also encouraged to delegate to this method after
	 * first checking that arguments are of or coerced to the correct types.
	 * </p>
	 * <p>
	 * This implementation checks for {@link OperationCanceledException}s and
	 * returns an appropriate {@link IStatus#CANCEL} status when it catches one.
	 * </p>
	 */
	public final IStatus validate(Collection objects) {
		IStatus result;
		
		Set encounteredClientContexts = new HashSet();
		
		try {
			result = createStatus(doValidate(objects, encounteredClientContexts));
		} catch (OperationCanceledException e) {
			result = new Status(
				IStatus.CANCEL,
				EMFModelValidationPlugin.getPluginId(),
				EMFModelValidationStatusCodes.OPERATION_CANCELED,
				e.getMessage(),
				null);
		}
		
		ValidationEvent event = new ValidationEvent(
				getEvaluationMode(),
				clientData,
				objects,
				result,
				getClientContextIds(encounteredClientContexts));
		
		// notify listeners that validation has occurred
		ModelValidationService.getInstance().broadcastValidationEvent(
			event);
		
		return result;
	}
	
	/**
	 * Gives access to the identifiers of the client contexts that are 
	 *  currently running.
	 *  
	 *  @param clientContexts The client contexts whose identifiers we will extract.
	 *  
	 * @return The client contexts ids provided to this validator by the last call
	 *  to the {@link #evaluateConstraints(AbstractValidationContext, List) method.
	 */
	private Collection getClientContextIds(Collection clientContexts) {
		List contextIds = new ArrayList();
		
		if (clientContexts == null) {
			return contextIds;
		}
		
		for (Iterator i = clientContexts.iterator(); i.hasNext();) {
			IClientContext context = (IClientContext)i.next();
			contextIds.add(context.getId());
		}
		return contextIds;
	}

	/**
	 * Implemented by subclasses to validate the specified <code>objects</code>.
	 * 
	 * @param objects the objects (one or more) to validate
	 * @param clientContexts the output collection that will be populated with all
	 *  of the client contexts encountered while the validator was running.
	 * @return the {@link IStatus} results of validating the
	 *     <code>objects</code>
	 * 
	 * @throws OperationCanceledException if the validation needs to be
	 *     canceled (e.g., when a constraint returns
	 *     {@link IStatus#CANCEL} status)
	 */
	protected abstract Collection doValidate(Collection objects, Set clientContexts);
	
	/**
	 * Helper method to evaluate a bunch of constraints.  Disabled constraints
	 * are not evaluated.
	 * 
	 * @param ctx the context in which all constraints are to be evaluated.
	 *     The context encapsulates the constraints
	 * @param results a list of {@link IStatus}es indicating the results of all
	 *     constraints that were eligible for evaluation.  This list only
	 *     accumulates constraint failures
	 * @return a status describing the severity of constraint violations on the
	 *     current target (if any).  An OK status indicates no problems
	 * 
	 * @throws OperationCanceledException if the validation needs to be
	 *     canceled (e.g., when a constraint returns
	 *     {@link IStatus#CANCEL} status)
	 */
	protected IStatus evaluateConstraints(
			AbstractValidationContext ctx,
			List results) {
		IStatus resultStatus = Status.OK_STATUS;
		
		for (Iterator iter = ctx.getConstraints().iterator(); iter.hasNext();) {
			IModelConstraint next = (IModelConstraint)iter.next();

			if (!acceptConstraint(next.getDescriptor(), ctx.getTarget())) {
				continue;
			}
			
			try {
				IStatus status = next.validate(ctx);
				
				if ((status != null)
						&& (isReportSuccesses() || !status.isOK())) {
					
					if (status.getSeverity() > resultStatus.getSeverity()) {
						resultStatus = status;
						
						if (resultStatus.matches(IStatus.CANCEL)) {
							// cancel the current validation operation
							throw new OperationCanceledException(
								resultStatus.getMessage());
						}
					}
					
					results.add(status);
				}
			} catch (RuntimeException e) {
				// protect against uncaught exceptions in the validation
				
				IConstraintStatus status =
					new DisabledConstraintStatus(next, ctx.getTarget(), e);
				results.add(status);
				
				Log.warning(
						status.getCode(),
						EMFModelValidationPlugin.getMessage(
							EMFModelValidationStatusCodes.CONSTRAINT_DISABLED_MSG,
					  		new Object[] {next.getDescriptor().getId()}),
						status.getException());
				
				// ensure that the constraint does not offend again
				ctx.disableCurrentConstraint(e);
			}
		}
		
		return resultStatus;
	}
	
	/**
	 * Executes the specified <code>operation</code>.
	 * 
	 * @param operation the operation to execute
	 */
	protected final void execute(IProviderOperation operation) {
		getOperationExecutor().execute(operation);
	}
	
	/**
	 * Obtains my private operation executor.
	 * 
	 * @return my operation executor
	 */
	private IProviderOperationExecutor getOperationExecutor() {
		return executor;
	}
	
	/**
	 * Creates an {@link IStatus} from a list of <code>results</code>.
	 * The status will be a {@link IStatus#isMultiStatus multi-status} storing
	 * (and aggregating) a list of individual {@link IStatus}es if the
	 * <code>results</code> has more than one element.
	 * 
	 * @param results the constraint evaluation results
	 * @return a multi-status if more than one result; a plain {@link IStatus},
	 *     otherwise
	 */
	private IStatus createStatus(Collection results) {	
		if (results.isEmpty()) {
			return new org.eclipse.core.runtime.Status(
				IStatus.OK,
				EMFModelValidationPlugin.getPluginId(),
				EMFModelValidationStatusCodes.NO_CONSTRAINTS_EVALUATED,
				EMFModelValidationStatusCodes.NO_CONSTRAINTS_EVALUATED_MSG,
				null);
		} else if (results.size() == 1) {
			return (IStatus)results.iterator().next();
		} else {
			return new AggregateStatus(results);
		}
	}

	private boolean acceptConstraint(IConstraintDescriptor constraint, EObject target) {
        if (filters != null) {
    		for (Iterator iter = filters.iterator(); iter.hasNext();) {
    			IConstraintFilter filter = (IConstraintFilter)iter.next();
                
    			if (!filter.accept(constraint, target)) {
    				return false;
    			}
    		}
        }
        
		return true;
	}

	public void addConstraintFilter(IConstraintFilter filter) {
		if (filters == null) {
			filters = new BasicEList(4);
		}
        
		filters.add(filter);
	}

    public void removeConstraintFilter(IConstraintFilter filter) {
        if (filters != null) {
            filters.remove(filter);
        }
    }
    
	public Collection getConstraintFilters() {
		if (filters == null) {
			return Collections.EMPTY_LIST;
		}
        
		return Collections.unmodifiableCollection(filters);
	}
	
	/**
	 * A custom status type that aggregates multiple {@link IStatus}es and whose
	 * severity is the worst severity among them.
	 * 
	 * @author Christian W. Damus (cdamus)
	 */
	private static class AggregateStatus implements IStatus {
		private final Collection children;
		private final int severity;
		private final int code;
		private final String message;
		
		/**
		 * Initializes me as an aggregate of the specified
		 * <code>statuses</code>.  They will become my
		 * {@link #getChildren() children}.
		 * 
		 * @param statuses the statuses that I aggregate
		 */
		AggregateStatus(Collection statuses) {
			// aggregate the results into a multi-status

			int maxSeverity = getMaximalSeverity(statuses);
			int newCode;
			String msg;
			
			switch (maxSeverity) {
			case IStatus.ERROR:
				newCode = EMFModelValidationStatusCodes.SOME_CONSTRAINTS_ERROR;
				msg = EMFModelValidationStatusCodes.SOME_CONSTRAINTS_ERROR_MSG;
				break;
			case IStatus.WARNING:
				newCode = EMFModelValidationStatusCodes.SOME_CONSTRAINTS_WARNING;
				msg = EMFModelValidationStatusCodes.SOME_CONSTRAINTS_WARNING_MSG;
				break;
			case IStatus.INFO:
				newCode = EMFModelValidationStatusCodes.SOME_CONSTRAINTS_INFO;
				msg = EMFModelValidationStatusCodes.SOME_CONSTRAINTS_INFO_MSG;
				break;
			case IStatus.OK:
				newCode = EMFModelValidationStatusCodes.ALL_CONSTRAINTS_PASSED;
				msg = EMFModelValidationStatusCodes.ALL_CONSTRAINTS_PASSED_MSG;
				break;
			default:
				newCode = EMFModelValidationStatusCodes.SOME_CONSTRAINTS_FAILED;
				msg = EMFModelValidationStatusCodes.SOME_CONSTRAINTS_FAILED_MSG;
				break;
			}
			
			this.children = statuses;
			this.severity = maxSeverity;
			this.code = newCode;
			this.message = msg;
		}
		
		// implements the interface method
		public IStatus[] getChildren() {
			return (IStatus[])children.toArray(new IStatus[children.size()]);
		}

		// implements the interface method
		public int getSeverity() {
			return severity;
		}

		// implements the interface method
		public int getCode() {
			return code;
		}

		// implements the interface method
		public String getMessage() {
			return message;
		}

		// implements the interface method
		public Throwable getException() {
			return null;
		}

		// implements the interface method
		public String getPlugin() {
			return EMFModelValidationPlugin.getPluginId();
		}

		// implements the interface method
		public boolean isMultiStatus() {
			return true;
		}

		// implements the interface method
		public boolean isOK() {
			return severity == IStatus.OK;
		}

		// implements the interface method
		public boolean matches(int severityMask) {
			return (getSeverity() & severityMask) != 0;
		}
	
		/**
		 * Helper method to get the maximal severity from a collection of statuses.
		 * 
		 * @param statuses a collection of {@link IStatus} objects
		 * @return the maximal severity amongst the <code>statuses</code>
		 */
		private int getMaximalSeverity(Collection statuses) {
			int result = IStatus.OK;
		
			for (Iterator iter = statuses.iterator();
					(result < IStatus.ERROR) && iter.hasNext();) {
			
				IStatus next = (IStatus)iter.next();
			
				if (next.getSeverity() > result) {
					result = next.getSeverity();
				}
			}
		
			return result;
		}
	}
}
