/**
 * <copyright>
 *
 * Copyright (c) 2005, 2007 IBM Corporation and others.
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

package org.eclipse.emf.validation.marker;

import java.util.HashMap;
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
import org.eclipse.emf.validation.model.IConstraintStatus;
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
	 * Creates markers with default validation marker type for all resources that had
	 * validation failures or warnings.
	 * The status provided is the status returned by one of the {@link IValidator#validate(Object)}
	 * methods.
	 *  
	 * @param validationStatus A status object returned by a validator's validate method.
	 * @throws CoreException A core exception is thrown if there were any problems interacting
	 *  with the workspace to attach/delete markers on resources.
	 */
	public static void createMarkers(IStatus validationStatus) throws CoreException {
		createMarkers(validationStatus, VALIDATION_MARKER_TYPE, null);
	}
	
	/**
	 * Creates markers with the provided marker type for all resources that had validation
	 * failures or warnings. An options marker configurator is provided in order to populate the
	 * marker with additional information.
	 * 
	 * @param validationStatus A status object returned by a validator's validate method.
	 * @param markerType A marker type that is a subtype of the validationProblem marker type.
	 * @param configurator An optional configurator to populate marker subtype specific attributes.
	 * @throws CoreException A core exception is thrown if there were any problems interacting
	 *  with the workspace to attach/delete markers on resources.
	 * @see IValidator#validate(Object)
	 * @see IValidator#validate(java.util.Collection)
	 * @see IMarkerConfigurator
	 */
	public static void createMarkers(final IStatus validationStatus, final String markerType, final IMarkerConfigurator configurator) throws CoreException {
		if (validationStatus.isOK()) {
			return;
		}
		
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor m)
				throws CoreException {
				
				final Map visitedResources = new HashMap();
				
				if (validationStatus.isMultiStatus()) {
					IStatus[] children = validationStatus.getChildren();
					for (int i=0; i<children.length ;i++) {
						if (children[i] instanceof IConstraintStatus) {
							createMarker((IConstraintStatus)children[i], markerType, configurator, visitedResources);
						}
					}
				} else if (validationStatus instanceof IConstraintStatus) {
					createMarker((IConstraintStatus)validationStatus, markerType, configurator, visitedResources);
				}
			}
		};
		
		ResourcesPlugin.getWorkspace().run(runnable, null);
	}

	private static void createMarker(IConstraintStatus status,
			String markerType, IMarkerConfigurator configurator, Map visitedResources) throws CoreException {

		Resource r = status.getTarget().eResource();
		URI uri = r.getURI();
		
		// Normalize the URI to something that we can deal with like file or platform scheme
		uri = r.getResourceSet().getURIConverter().normalize(uri);

		IFile file = (IFile)visitedResources.get(uri);
		
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
				StringBuffer fileResourcePath = new StringBuffer();
				for (int j=1, size = uri.segmentCount(); j < size; ++j) {
					fileResourcePath.append('/');
					fileResourcePath.append(URI.decode(uri.segment(j)));
				}
				
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
}
