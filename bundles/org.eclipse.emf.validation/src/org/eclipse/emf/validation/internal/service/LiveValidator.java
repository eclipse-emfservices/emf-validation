/******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    IBM Corporation - initial API and implementation
 ****************************************************************************/
package org.eclipse.emf.validation.internal.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationWrapper;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.internal.EMFModelValidationDebugOptions;
import org.eclipse.emf.validation.internal.util.Trace;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.EventTypeService;
import org.eclipse.emf.validation.service.ILiveValidator;
import org.eclipse.emf.validation.service.INotificationGenerator;
import org.eclipse.emf.validation.util.FilteredCollection;

/**
 * Basic implementation of the {@link ILiveValidator} interface.
 *
 * @author Christian W. Damus (cdamus)
 */
public class LiveValidator extends AbstractValidator<Notification> implements ILiveValidator {
	private FilteredCollection.Filter<Notification> notificationFilter;

	/**
	 * Initializes me with the operation <code>executor</code> that I use to execute
	 * provider operations.
	 *
	 * @param executor used by me to execute operations (must not be
	 *                 <code>null</code>)
	 */
	public LiveValidator(IProviderOperationExecutor executor) {
		super(EvaluationMode.LIVE, executor);
	}

	/*
	 * (non-Javadoc) Implements the inherited method.
	 */
	@Override
	protected Collection<IStatus> doValidate(Collection<? extends Notification> objects,
			Set<IClientContext> clientContexts) {
		// Generate notifications for contributed emf event types
		Collection<Notification> notifications = generateNotifications(objects);

		// Merge similar notifications together to avoid repeated constraint
		// evaluations on the same kind of change to the same feature
		List<Notification> events = mergeNotifications(notifications);
		List<IStatus> result = new java.util.ArrayList<>(32); // anticipate moderate number

		GetLiveConstraintsOperation operation = new GetLiveConstraintsOperation();
		operation.setAllEvents(new java.util.ArrayList<Notification>(objects));
		AbstractValidationContext ctx = operation.getContext();
		ctx.setReportSuccesses(isReportSuccesses());

		for (Notification event : events) {
			Object notifier = event.getNotifier();

			// only attempt to validate notifications from EObjects
			if (notifier instanceof EObject) {
				EMFEventType eventType = EMFEventType.getInstance(event.getEventType());

				// only attempt to validate the core EMF event types. Custom
				// event types are unknown to other plug-ins that may contribute
				// constraints
				if (!eventType.isNull()) {
					// set the validation context's client contexts so that we
					// selected the most appropriate constraints to evaluate

					Collection<IClientContext> contexts = ClientContextManager.getInstance()
							.getClientContextsFor((EObject) notifier);

					ctx.setClientContexts(contexts);

					clientContexts.addAll(contexts);

					validate(ctx, event, operation, result);
				}
			}
		}

		return result;
	}

	/**
	 * Helper method for validation of a single <code>event</code>.
	 *
	 * @param ctx     the context within which to validate the <code>eObject</code>
	 * @param event   the EMF notification to validate
	 * @param the     operation to reuse for getting constraints
	 * @param results list of {@link IStatus} results of constraint evaluations
	 */
	private void validate(AbstractValidationContext ctx, Notification event, GetLiveConstraintsOperation operation,
			List<IStatus> results) {
		if (Trace.shouldTraceEntering(EMFModelValidationDebugOptions.PROVIDERS)) {
			Trace.entering(getClass(), "validate", //$NON-NLS-1$
					new Object[] { event });
		}

		operation.setNotification(event);

		execute(operation);

		evaluateConstraints(ctx, results);

		if (Trace.shouldTraceExiting(EMFModelValidationDebugOptions.PROVIDERS)) {
			Trace.exiting(getClass(), "validate"); //$NON-NLS-1$
		}
	}

	/**
	 * Merges any <code>notifications</code> in the specified list that are repeated
	 * changes to the same features into single changes, to ensure that constraints
	 * receive complete deltas without repeated invocation.
	 *
	 * @param notifications the input notifications
	 * @return the merged (possibly fewer) notifications
	 */
	private List<Notification> mergeNotifications(Collection<Notification> notifications) {
		// use a linked map to preserve list ordering
		Map<Notification, Notification> result = new java.util.LinkedHashMap<>(
				notifications.size());

		for (Notification next : notifications) {
			// only triggger constraints on EObjects that are still
			// connected to a particular resource. We will filter the
			// notification appropriately.
			if (isValidatable(next)) {
				MergeableNotification notification = new MergeableNotification(next);

				// add this notification to the result if either no similar notification
				// already exists or the other notification could not be merged
				// into an existing one
				if (!result.containsKey(notification)
						|| !((MergeableNotification) result.get(notification)).merge(notification)) {

					result.put(notification, notification);
				}
			}
		}

		return new java.util.ArrayList<>(result.keySet());
	}

	private Collection<Notification> generateNotifications(Collection<? extends Notification> notifications) {

		Collection<INotificationGenerator> generators = EventTypeService.getInstance().getNotificationGenerators();
		Collection<Notification> newNotifications = new ArrayList<>();

		// Add generated notifications for each generator
		for (INotificationGenerator next : generators) {
			newNotifications.addAll(next.generateNotifications(notifications));
		}

		// Add existing notifications
		newNotifications.addAll(notifications);

		return newNotifications;
	}

	/**
	 * Determines whether the specified notification is eligible for triggering
	 * constraints.
	 *
	 * @param notification a notification
	 * @return whether the notification is eligible based on the notification filter
	 */
	private boolean isValidatable(Notification notification) {
		return getNotificationFilter().accept(notification);
	}

	@Override
	public void setNotificationFilter(FilteredCollection.Filter<Notification> filter) {
		notificationFilter = filter;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * The default filter implementation is the
	 * <code>AttachedToResourceNotificationFilter</code>.
	 * </p>
	 */
	@Override
	public FilteredCollection.Filter<Notification> getNotificationFilter() {
		if (notificationFilter == null) {
			notificationFilter = new AttachedToResourceNotificationFilter();
		}

		return notificationFilter;
	}

	/**
	 * A notification that supports merging of create, add/add-many, and
	 * remove/remove-many events, in addition to set events. This implementation
	 * cuts corners because the validation context only needs a small subset of the
	 * information available in a notification.
	 *
	 * @author Christian W. Damus (cdamus)
	 */
	private static class MergeableNotification extends NotificationWrapper {
		private Object oldValue; // old value of the feature
		private Object newValue; // new value of the feature
		private int eventType;

		/**
		 * Initializes me with the <code>notification</code> that I wrap.
		 *
		 * @param notification the wrapped notification
		 */
		public MergeableNotification(Notification notification) {
			super(notification);

			eventType = notification.getEventType();
			oldValue = notification.getOldValue();

			if (eventType == Notification.MOVE) {
				// record the whole feature value as the new value of a MOVE
				newValue = ((EObject) notification.getNotifier()).eGet((EStructuralFeature) notification.getFeature());
			} else {
				newValue = notification.getNewValue();
			}

			// in case of oldValue or newValue being unmodifiable collections,
			// take a copy to ensure that they are modifiable. Also, this
			// avoids destroying the original notification's collection
			if (oldValue instanceof Collection) {
				oldValue = new java.util.ArrayList<Object>((Collection<?>) oldValue);
			}
			if (newValue instanceof Collection) {
				newValue = new java.util.ArrayList<Object>((Collection<?>) newValue);
			}
		}

		/*
		 * (non-Javadoc) Redefines the inherited method to return the stored event type.
		 */
		@Override
		public int getEventType() {
			return eventType;
		}

		/*
		 * (non-Javadoc) Redefines the inherited method to return the stored new feature
		 * value.
		 */
		@Override
		public Object getNewValue() {
			return newValue;
		}

		/*
		 * (non-Javadoc) Redefines the inherited method to return the stored old feature
		 * value.
		 */
		@Override
		public Object getOldValue() {
			return oldValue;
		}

		/**
		 * I consider myself equal to any {@link Notification} if that other
		 * notification has the same notifier, event type, and feature as myself. Note
		 * that the "Add" and "Add Many" event types are considered the same, as well as
		 * the "Remove" and "Remove Many", for the purposes of this comparison.
		 */
		@Override
		public boolean equals(Object o) {
			boolean result = false;

			if (o instanceof Notification) {
				Notification other = (Notification) o;

				result = (other.getNotifier() == getNotifier()) && (other.getFeature() == getFeature());

				if (result) {
					int otherType = other.getEventType();

					switch (getEventType()) {
					case Notification.ADD:
					case Notification.ADD_MANY:
						result = (otherType == Notification.ADD) || (otherType == Notification.ADD_MANY);
						break;

					case Notification.REMOVE:
					case Notification.REMOVE_MANY:
						result = (otherType == Notification.REMOVE) || (otherType == Notification.REMOVE_MANY);
						break;

					default:
						result = (otherType == getEventType());
						break;
					}
				}
			}

			return result;
		}

		/**
		 * To be consistent with the {@link #equals(Object)} method, my hash code is
		 * computed from my notifier, event type, and feature.
		 */
		@Override
		public int hashCode() {
			int localEventType = getEventType();

			// treat the "many" flavour the same as the "non-many" flavour
			if (localEventType == Notification.ADD_MANY) {
				localEventType = Notification.ADD;
			} else if (localEventType == Notification.REMOVE_MANY) {
				localEventType = Notification.REMOVE;
			}

			return System.identityHashCode(getNotifier()) ^ (37 * localEventType)
					^ (17 * System.identityHashCode(getFeature()));
		}

		/*
		 * (non-Javadoc) Redefines the inherited method to implement a merge for
		 * singleton and list features.
		 */
		@Override
		@SuppressWarnings("unchecked")
		public boolean merge(Notification other) {
			boolean result = false;

			Collection<Object> newCollection;
			Object otherValue;

			if (this.equals(other)) {
				switch (eventType) {
				case 0: // Notification.CREATE (deprecated)
				case Notification.RESOLVE:

					if (newValue instanceof Collection) {
						newCollection = (Collection<Object>) newValue;
					} else {
						newCollection = new java.util.ArrayList<>();
						newCollection.add(newValue);
					}

					otherValue = other.getNewValue();

					if (otherValue instanceof Collection) {
						newCollection.addAll((Collection<?>) otherValue);
					} else {
						newCollection.add(otherValue);
					}

					newValue = newCollection;
					result = true;
					break;

				case Notification.ADD:
				case Notification.ADD_MANY:

					if (newValue instanceof Collection) {
						newCollection = (Collection<Object>) newValue;
					} else {
						newCollection = new java.util.ArrayList<>();
						newCollection.add(newValue);
					}

					otherValue = other.getNewValue();

					if (otherValue instanceof Collection) {
						newCollection.addAll((Collection<?>) otherValue);
					} else {
						newCollection.add(otherValue);
					}

					newValue = newCollection;
					eventType = Notification.ADD_MANY;
					result = true;
					break;

				case Notification.REMOVE:
				case Notification.REMOVE_MANY:

					if (oldValue instanceof Collection) {
						newCollection = (Collection<Object>) oldValue;
					} else {
						newCollection = new java.util.ArrayList<>();
						newCollection.add(oldValue);
					}

					otherValue = other.getOldValue();

					if (otherValue instanceof Collection) {
						newCollection.addAll((Collection<?>) otherValue);
					} else {
						newCollection.add(otherValue);
					}

					oldValue = newCollection; // "oldValue" contains removals
					eventType = Notification.REMOVE_MANY;
					result = true;
					break;

				case Notification.REMOVING_ADAPTER:

					if (oldValue instanceof Collection) {
						newCollection = (Collection<Object>) oldValue;
					} else {
						newCollection = new java.util.ArrayList<>();
						newCollection.add(oldValue);
					}

					otherValue = other.getOldValue();

					if (otherValue instanceof Collection) {
						newCollection.addAll((Collection<?>) otherValue);
					} else {
						newCollection.add(otherValue);
					}

					oldValue = newCollection; // "oldValue" contains removals
					result = true;
					break;

				default:

					// just merge all sets, unsets, and moves together by
					// recording the final value of the collection
					if (getFeature() instanceof EStructuralFeature) {
						newValue = ((EObject) getNotifier()).eGet((EStructuralFeature) getFeature());

						// result will be false for non-feature-specific events
						result = true;
					}
					break;

				// default:
				}
			}

			return result;
		}
	}

	/**
	 * Notification filter that accepts notifications whose target is an
	 * <code>EObject</code> that is attached to a resource (i.e., it is not deleted)
	 */
	private class AttachedToResourceNotificationFilter implements FilteredCollection.Filter<Notification> {

		@Override
		public boolean accept(Notification element) {
			return (element.getNotifier() instanceof EObject)
					&& (((EObject) element.getNotifier()).eResource() != null);
		}
	}
}
