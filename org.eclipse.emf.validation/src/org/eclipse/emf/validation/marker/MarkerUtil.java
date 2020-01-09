/**
 * Copyright (c) 2005, 2010 IBM Corporation, Zeligsoft Inc., and others.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   IBM - Initial API and implementation
 *   Damien Thivolle - Bug 218765
 *   Zeligsoft - Bug 218765 (completion)
 */
package org.eclipse.emf.validation.marker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.validation.internal.service.ResourceStatus;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.IBatchValidator;
import org.eclipse.emf.validation.service.IValidator;

/**
 * This class provides utility methods that aid in the creation of {@link IResource} markers
 *  ({@link IMarker}). Markers created by this utility will have the
 *  {@link org.eclipse.emf.validation.ui.ide.quickfix.ValidationMarkerResolution}
 *  registered as a resolution that simply disables the constraint unless they are using a
 *  marker subtype. In that case they will have to explicitly register the ValidationMarkerResolution
 *  against their marker type.
 *  
 * @author cmcgee
 */
public final class MarkerUtil {
	public static final String VALIDATION_MARKER_TYPE = "org.eclipse.emf.validation.problem"; //$NON-NLS-1$
	public static final String RULE_ATTRIBUTE = "rule"; //$NON-NLS-1$
	
	private static final String PLATFORM_SCHEME = "platform"; //$NON-NLS-1$
	private static final String FILE_SCHEME = "file"; //$NON-NLS-1$
	private static final String RESOURCE_SEGMENT = "resource"; //$NON-NLS-1$

	/**
	 * <p>
	 * Creates markers with default validation marker type for all resources that had
	 * validation failures or warnings.
	 * The status provided is the status returned by one of the {@link IValidator#validate(Object)}
	 * methods.
	 * </p><p>
	 * To update the markers on a resource that already has markers, use the
	 * {@link #updateMarkers(IStatus)} method.
	 * </p>
	 *  
	 * @param validationStatus A status object returned by a validator's validate method.
	 * @throws CoreException A core exception is thrown if there were any problems interacting
	 *  with the workspace to attach/delete markers on resources.
	 *  
	 * @see #updateMarkers(IStatus)
	 */
	public static void createMarkers(IStatus validationStatus) throws CoreException {
		createMarkers(validationStatus, VALIDATION_MARKER_TYPE, null);
	}

	/**
	 * <p>
	 * Creates markers with default validation marker type for all resources that had
	 * validation failures or warnings.
	 * The status provided is the status returned by one of the {@link IValidator#validate(Object)}
	 * methods.
	 * </p><p>
	 * To update the markers on a resource that already has markers, use the
	 * {@link #updateMarkers(IStatus, int)} method.
	 * </p>
	 *  
	 * @param validationStatus A status object returned by a validator's validate method.
	 * @param severityMask mask selecting which severities to create markers for
	 * @throws CoreException A core exception is thrown if there were any problems interacting
	 *  with the workspace to attach/delete markers on resources.
	 * 
	 * @since 1.2
	 * 
	 * @see #updateMarkers(IStatus, int)
	 */
	public static void createMarkers(IStatus validationStatus, int severityMask) throws CoreException {
		createMarkers(validationStatus, severityMask, VALIDATION_MARKER_TYPE, null);
	}
	
	/**
	 * <p>
	 * Creates markers with the provided marker type for all resources that had validation
	 * failures or warnings. An options marker configurator is provided in order to populate the
	 * marker with additional information.
	 * </p><p>
	 * To update the markers on a resource that already has markers, use the
	 * {@link #updateMarkers(IStatus, String, IMarkerConfigurator)} method.
	 * </p>
	 * 
	 * @param validationStatus A status object returned by a validator's validate method.
	 * @param markerType A marker type that is a subtype of the validationProblem marker type.
	 * @param configurator An optional configurator to populate marker subtype specific attributes.
	 * @throws CoreException A core exception is thrown if there were any problems interacting
	 *  with the workspace to attach/delete markers on resources.
	 * @see IValidator#validate(Object)
	 * @see IValidator#validate(java.util.Collection)
	 * @see IMarkerConfigurator
	 * @see #updateMarkers(IStatus, String, IMarkerConfigurator)
	 */
	public static void createMarkers(IStatus validationStatus, String markerType,
			IMarkerConfigurator configurator) throws CoreException {
		createMarkers(validationStatus, 0xFFFF, markerType, configurator);
	}
	
	/**
	 * <p>
	 * Creates markers with the provided marker type for all resources that had validation
	 * failures or warnings. An options marker configurator is provided in order to populate the
	 * marker with additional information.
	 * </p><p>
	 * To update the markers on a resource that already has markers, use the
	 * {@link #updateMarkers(IStatus, int, String, IMarkerConfigurator)} method.
	 * </p>
	 * 
	 * @param validationStatus A status object returned by a validator's validate method.
	 * @param severityMask mask selecting which severities to create markers for
	 * @param markerType A marker type that is a subtype of the validationProblem marker type.
	 * @param configurator An optional configurator to populate marker subtype specific attributes.
	 * @throws CoreException A core exception is thrown if there were any problems interacting
	 *  with the workspace to attach/delete markers on resources.
	 * @see IValidator#validate(Object)
	 * @see IValidator#validate(java.util.Collection)
	 * @see IMarkerConfigurator
	 * @see #updateMarkers(IStatus, int, String, IMarkerConfigurator)
	 * 
	 * @since 1.2
	 */
	public static void createMarkers(final IStatus validationStatus,
			final int severityMask, final String markerType,
			final IMarkerConfigurator configurator) throws CoreException {
		
		if (validationStatus.isOK()) {
			return;
		}
		
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor m)
				throws CoreException {
				
				final Map<URI, IFile> visitedResources = new HashMap<URI, IFile>();
				
				if (validationStatus.isMultiStatus()) {
					createMarkers(validationStatus, severityMask, markerType, configurator, visitedResources);
				} else if (validationStatus.matches(severityMask)
						&& (validationStatus instanceof IConstraintStatus)) {
					
					createMarker((IConstraintStatus)validationStatus, markerType, configurator, visitedResources);
				}
			}
		};
		
		ResourcesPlugin.getWorkspace().run(runnable, null);
	}
	
	private static void createMarkers(IStatus validationStatus,
            int severityMask, String markerType,
            IMarkerConfigurator configurator, Map<URI, IFile> visitedResources)
            throws CoreException {

        IStatus[] children = validationStatus.getChildren();
        for (IStatus element : children) {
            // recursively unwrap all children of multi-statuses
            if (element.isMultiStatus()) {
                createMarkers(element, severityMask, markerType, configurator, visitedResources);
            } else if (element.matches(severityMask)
                    && (element instanceof IConstraintStatus)) {

                createMarker((IConstraintStatus)element, markerType, configurator, visitedResources);
            }
        }
    }

	private static void createMarker(IConstraintStatus status,
			String markerType, IMarkerConfigurator configurator,
			Map<URI, IFile> visitedResources) throws CoreException {

		Resource r = status.getTarget().eResource();
		URI uri = r.getURI();
		
		// Normalize the URI to something that we can deal with like file or platform scheme
		uri = r.getResourceSet().getURIConverter().normalize(uri);

		IFile file = visitedResources.get(uri);
		
		if (file == null) {
			if (PLATFORM_SCHEME.equals(uri.scheme()) && uri.segmentCount() > 1
				&& RESOURCE_SEGMENT.equals(uri.segment(0))) {
				StringBuffer platformResourcePath = new StringBuffer();
				for (int j = 1, size = uri.segmentCount(); j < size; ++j) {
					platformResourcePath.append('/');
					platformResourcePath.append(URI.decode(uri.segment(j)));
				}
	
				file = ResourcesPlugin.getWorkspace().getRoot().getFile(
					new Path(platformResourcePath.toString()));
			} else if (FILE_SCHEME.equals(uri.scheme())) {
				Path fileResourcePath = new Path(uri.toFileString());
				file = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(
					new Path(fileResourcePath.toString()));
			}
			
			if (file != null) {
				file.deleteMarkers(VALIDATION_MARKER_TYPE, true, IResource.DEPTH_ZERO);
				visitedResources.put(uri,file);
			}
		}
		
		if (file != null) {
			if (!status.matches(IStatus.INFO | IStatus.ERROR | IStatus.WARNING
                | IStatus.CANCEL)) {
                return;
            }
			
			IMarker marker = file.createMarker(markerType);

			switch (status.getSeverity()) {
                case IStatus.INFO:
                    marker.setAttribute(IMarker.SEVERITY,
                        IMarker.SEVERITY_INFO);
                    marker.setAttribute(IMarker.PRIORITY,
                        IMarker.PRIORITY_LOW);
                    break;
				case IStatus.WARNING:
					marker.setAttribute(IMarker.SEVERITY,
						IMarker.SEVERITY_WARNING);
					marker.setAttribute(IMarker.PRIORITY,
						IMarker.PRIORITY_NORMAL);
					break;
				case IStatus.ERROR:
				case IStatus.CANCEL:
					marker.setAttribute(IMarker.SEVERITY,
						IMarker.SEVERITY_ERROR);
					marker.setAttribute(IMarker.PRIORITY,
						IMarker.PRIORITY_HIGH);
                    break;
			}

			marker.setAttribute(IMarker.MESSAGE, status.getMessage());
			marker.setAttribute(EValidator.URI_ATTRIBUTE, EcoreUtil
				.getURI(status.getTarget()).toString());
			marker.setAttribute(RULE_ATTRIBUTE, status.getConstraint()
				.getDescriptor().getId());

			if (configurator != null) {
				configurator.appendMarkerConfiguration(marker, status);
			}
		}
	}

	/**
	 * <p>
	 * Creates or updates markers with default validation marker type for all
	 * resources that had validation failures or warnings. If there is no failed
	 * validation on a given resource, its previously attached markers are
	 * deleted. If a resource has validation failures or warnings, its
	 * previously attached markers are deleted and new ones are created. The
	 * status provided is the status returned by one of the
	 * {@link IValidator#validate(Object)} methods.
	 * </p><p>
	 * <b>Note</b> that, in order to correctly update the markers for resources
	 * that reported no problems, the validation operation must have been
	 * performed with the {@link IBatchValidator#OPTION_TRACK_RESOURCES} option
	 * enabled.
	 * </p>
	 * 
	 * @param validationStatus
	 *            A status object returned by a validator's validate method.
	 * @throws CoreException
	 *             A core exception is thrown if there were any problems
	 *             interacting with the workspace to attach/delete markers on
	 *             resources.
	 * 
	 * @since 1.3
	 */
	public static void updateMarkers(IStatus validationStatus)
			throws CoreException {
		updateMarkers(validationStatus, VALIDATION_MARKER_TYPE, null);
	}

	/**
	 * <p>
	 * Creates markers with default validation marker type for all resources
	 * that had validation failures or warnings. If there is no failed
	 * validation on a given resource, its previously attached markers are
	 * deleted. If a resource has validation failures or warnings, its
	 * previously attached markers are deleted and new ones are created. The
	 * status provided is the status returned by one of the
	 * {@link IValidator#validate(Object)} methods.
	 * </p><p>
	 * <b>Note</b> that, in order to correctly update the markers for resources
	 * that reported no problems, the validation operation must have been
	 * performed with the {@link IBatchValidator#OPTION_TRACK_RESOURCES} option
	 * enabled.
	 * </p>
	 * 
	 * @param validationStatus
	 *            A status object returned by a validator's validate method.
	 * @param severityMask
	 *            mask selecting which severities to create markers for
	 * @throws CoreException
	 *             A core exception is thrown if there were any problems
	 *             interacting with the workspace to attach/delete markers on
	 *             resources.
	 * 
	 * @since 1.3
	 */
	public static void updateMarkers(IStatus validationStatus, int severityMask)
			throws CoreException {
		updateMarkers(validationStatus, severityMask, VALIDATION_MARKER_TYPE,
			null);
	}

	/**
	 * <p>
	 * Creates markers with the provided marker type for all resources that had
	 * validation failures or warnings. If there is no failed validation on a
	 * given resource, its previously attached markers are deleted. If a
	 * resource has validation failures or warnings, its previously attached
	 * markers are deleted and new ones are created. An optional marker
	 * configurator is provided in order to populate the marker with additional
	 * information.
	 * </p><p>
	 * that reported no problems, the validation operation must have been
	 * performed with the {@link IBatchValidator#OPTION_TRACK_RESOURCES} option
	 * enabled.
	 * </p>
	 * 
	 * @param validationStatus
	 *            A status object returned by a validator's validate method.
	 * @param markerType
	 *            A marker type that is a subtype of the validationProblem
	 *            marker type.
	 * @param configurator
	 *            An optional configurator to populate marker subtype specific
	 *            attributes.
	 * @throws CoreException
	 *             A core exception is thrown if there were any problems
	 *             interacting with the workspace to attach/delete markers on
	 *             resources.
	 * @see IValidator#validate(Object)
	 * @see IValidator#validate(java.util.Collection)
	 * @see IMarkerConfigurator
	 * 
	 * @since 1.3
	 */
	public static void updateMarkers(IStatus validationStatus,
			String markerType, IMarkerConfigurator configurator)
			throws CoreException {
		updateMarkers(validationStatus, 0xFFFF, markerType, configurator);
	}

	/**
	 * <p>
	 * Creates markers with the provided marker type for all resources that had
	 * validation failures or warnings. If there is no failed validation on a
	 * given resource, its previously attached markers are deleted. If a
	 * resource has validation failures or warnings, its previously attached
	 * markers are deleted and new ones are created. An optional marker
	 * configurator is provided in order to populate the marker with additional
	 * </p><p>
	 * <b>Note</b> that, in order to correctly update the markers for resources
	 * that reported no problems, the validation operation must have been
	 * performed with the {@link IBatchValidator#OPTION_TRACK_RESOURCES} option
	 * enabled.
	 * </p>
	 * information.
	 * 
	 * @param validationStatus
	 *            A status object returned by a validator's validate method.
	 * @param severityMask
	 *            mask selecting which severities to create markers for
	 * @param markerType
	 *            A marker type that is a subtype of the validationProblem
	 *            marker type.
	 * @param configurator
	 *            An optional configurator to populate marker subtype specific
	 *            attributes.
	 * @throws CoreException
	 *             A core exception is thrown if there were any problems
	 *             interacting with the workspace to attach/delete markers on
	 *             resources.
	 * @see IValidator#validate(Object)
	 * @see IValidator#validate(java.util.Collection)
	 * @see IMarkerConfigurator
	 * 
	 * @since 1.3
	 */
	public static void updateMarkers(final IStatus validationStatus,
			final int severityMask, final String markerType,
			final IMarkerConfigurator configurator)
			throws CoreException {

		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {

			public void run(IProgressMonitor m)
					throws CoreException {

				final Map<URI, FileStatusListPair> resourcesToVisit = new HashMap<URI, FileStatusListPair>();

				// First we need to inspect the status and if it is a
				// multistatus then
				// we group all status related to the same file together. This needs 
				// to be done recursively for all occurring multi-statuses. Each
				// file
				// is associated with a status list. This way we can achieve a file by
				// file
				// approach, allowing us to first delete the previous markers of
				// a
				// file and then attaching to it the markers corresponding to
				// its
				// status list.
				if (validationStatus.isMultiStatus()) {
					updateMarkers(validationStatus, severityMask, resourcesToVisit);
				} else if (shouldAddStatus(validationStatus, severityMask)) {
					addStatus((IConstraintStatus) validationStatus,
						resourcesToVisit);
				}

				// For each file, we update its markers according to the status
				// list extracted
				// from the validation status.
				for (Map.Entry<URI, FileStatusListPair> currentItem : resourcesToVisit
					.entrySet()) {
					FileStatusListPair fileStatusList = currentItem.getValue();
					updateFileMarkers(fileStatusList.getFile(), fileStatusList
						.getStatuses(), markerType, configurator);
				}
			}
		};

		ResourcesPlugin.getWorkspace().run(runnable, null);
	}
	
	private static void updateMarkers(IStatus validationStatus,
            int severityMask, Map<URI, FileStatusListPair> resourcesToVisit) {

        if (validationStatus.isMultiStatus()) {
            IStatus[] children = validationStatus.getChildren();
            for (IStatus next : children) {
                // recursively unwrap all children of multi-statuses
                if (next.isMultiStatus()) {
                    updateMarkers(next, severityMask, resourcesToVisit);
                } else if (shouldAddStatus(next, severityMask)) {
                    addStatus((IConstraintStatus) next,
                        resourcesToVisit);
                }
            }
        } else if (shouldAddStatus(validationStatus, severityMask)) {
            addStatus((IConstraintStatus) validationStatus,
                resourcesToVisit);
        }

    }
	
	private static boolean shouldAddStatus(IStatus status, int severityMask) {
		return (status.matches(severityMask) && (status instanceof IConstraintStatus))
			|| (status instanceof ResourceStatus);
	}

	/**
	 * Adds a status to the status list of its corresponding file in the
	 * resourceToVisit variable.
	 * 
	 * @param status
	 *            the status to add to resourceToVisit.
	 * @param resourcesToVisit
	 *            A map of files and status lists.
	 */
	private static void addStatus(IConstraintStatus status,
			Map<URI, FileStatusListPair> resourcesToVisit) {

		Resource r = status.getTarget().eResource();
		URI uri = r.getURI();

		// Normalize the URI to something that we can deal with like file or
		// platform scheme
		uri = r.getResourceSet().getURIConverter().normalize(uri);

		FileStatusListPair fileStatusList = resourcesToVisit.get(uri);
		if (fileStatusList == null) {
			fileStatusList = new FileStatusListPair();

			if (PLATFORM_SCHEME.equals(uri.scheme()) && uri.segmentCount() > 1
				&& RESOURCE_SEGMENT.equals(uri.segment(0))) {
				StringBuffer platformResourcePath = new StringBuffer();
				for (int j = 1, size = uri.segmentCount(); j < size; ++j) {
					platformResourcePath.append('/');
					platformResourcePath.append(URI.decode(uri.segment(j)));
				}

				fileStatusList.setFile(ResourcesPlugin.getWorkspace().getRoot()
					.getFile(new Path(platformResourcePath.toString())));
			} else if (FILE_SCHEME.equals(uri.scheme())) {
			    Path fileResourcePath = new Path(uri.toFileString());
				fileStatusList.setFile(ResourcesPlugin.getWorkspace().getRoot()
					.getFileForLocation(fileResourcePath));
			}

			if (fileStatusList.getFile() != null) {
				fileStatusList.addStatus(status);
				resourcesToVisit.put(uri, fileStatusList);
			}
		} else {
			fileStatusList.addStatus(status);
		}
	}

	/**
	 * Creates a marker for each status in the status list and attaches it to
	 * the file argument.
	 * 
	 * @param file
	 *            The file to mark.
	 * @param statuses
	 *            The list of statuses.
	 * @param markerType
	 *            Defines the type of the markers.
	 * @param configurator
	 *            An optional configurator to populate marker subtype specific
	 *            attributes.
	 * @throws CoreException
	 *             A core exception is thrown if there were any problems
	 *             interacting with the workspace to attach/delete markers on
	 *             resources.
	 */
	private static void updateFileMarkers(IFile file,
			List<IConstraintStatus> statuses, String markerType,
			IMarkerConfigurator configurator)
			throws CoreException {

		file.deleteMarkers(markerType, true, IResource.DEPTH_ZERO);

		for (IConstraintStatus status : statuses) {
			if (!status.isOK()) {
				IMarker marker = file.createMarker(markerType);

				switch (status.getSeverity()) {
					case IStatus.INFO :
						marker.setAttribute(IMarker.SEVERITY,
							IMarker.SEVERITY_INFO);
						marker.setAttribute(IMarker.PRIORITY,
							IMarker.PRIORITY_LOW);
						break;
					case IStatus.WARNING :
						marker.setAttribute(IMarker.SEVERITY,
							IMarker.SEVERITY_WARNING);
						marker.setAttribute(IMarker.PRIORITY,
							IMarker.PRIORITY_NORMAL);
						break;
					case IStatus.ERROR :
					case IStatus.CANCEL :
						marker.setAttribute(IMarker.SEVERITY,
							IMarker.SEVERITY_ERROR);
						marker.setAttribute(IMarker.PRIORITY,
							IMarker.PRIORITY_HIGH);
						break;
				}

				marker.setAttribute(IMarker.MESSAGE, status.getMessage());
				marker.setAttribute(EValidator.URI_ATTRIBUTE, EcoreUtil.getURI(
					status.getTarget()).toString());
				marker.setAttribute(RULE_ATTRIBUTE, status.getConstraint()
					.getDescriptor().getId());

				if (configurator != null) {
					configurator.appendMarkerConfiguration(marker, status);
				}

			}
		}

	}

	/**
	 * A helper class for pairing an {@link IFile} and a {@link List} of
	 * {@link IConstraintStatus}.
	 * 
	 * @author Damien Thivolle
	 * 
	 * @since 1.3
	 */
	private static class FileStatusListPair {

		private IFile file;

		private List<IConstraintStatus> statuses;

		FileStatusListPair() {
			this.file = null;
			this.statuses = new java.util.ArrayList<IConstraintStatus>();
		}

		void setFile(IFile file) {
			this.file = file;
		}

		void addStatus(IConstraintStatus status) {
			this.statuses.add(status);
		}

		List<IConstraintStatus> getStatuses() {
			return this.statuses;
		}

		Boolean includes(IConstraintStatus status) {
			return this.statuses.contains(status);
		}

		IFile getFile() {
			return this.file;
		}
	}
}
