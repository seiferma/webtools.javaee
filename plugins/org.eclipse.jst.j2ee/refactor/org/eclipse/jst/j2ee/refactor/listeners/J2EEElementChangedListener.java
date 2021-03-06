/*******************************************************************************
 * Copyright (c) 2005, 2007 BEA Systems, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 * rfrost@bea.com - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.refactor.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.plugin.J2EEPlugin;
import org.eclipse.jst.j2ee.project.JavaEEProjectUtilities;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.ModuleCoreNature;
import org.eclipse.wst.common.componentcore.internal.ComponentResource;
import org.eclipse.wst.common.componentcore.internal.StructureEdit;
import org.eclipse.wst.common.componentcore.internal.WorkbenchComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;

/**
 * Implementation of <code>IElementChangedListener that updates mappings for src folders
 * in the .component file in response to changes in a project's Java classpath. 
 */
public class J2EEElementChangedListener implements IElementChangedListener {

	/**
	 * Name of the Job family in which all component update jobs belong.
	 */
	public static final String PROJECT_COMPONENT_UPDATE_JOB_FAMILY =  "org.eclipse.jst.j2ee.refactor.component"; //$NON-NLS-1$
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.core.IElementChangedListener#elementChanged(org.eclipse.jdt.core.ElementChangedEvent)
	 */
	@Override
	public void elementChanged(final ElementChangedEvent event) {
		processJavaElementDelta(event.getDelta());
	}
	
	private void processJavaElementDelta(final IJavaElementDelta delta) {
		final int kind = delta.getKind();
		if (kind == IJavaElementDelta.CHANGED) {
			final int flags = delta.getFlags();
			final IJavaElement element = delta.getElement();
			if (element instanceof IJavaModel) {
				if ((flags & IJavaElementDelta.F_CHILDREN) == IJavaElementDelta.F_CHILDREN) {
					final IJavaElementDelta[] children = delta.getChangedChildren();
					for (int i = 0; i < children.length; i++) {
						// handle all of the IJavaProject children
						processJavaElementDelta(children[i]);
					}
				} else {
					// not a Java project (i.e. could be an EAR project)
					processResourceDeltas(flags, kind, delta);
				}
			} else if (element instanceof IJavaProject) {
				processJavaProject((IJavaProject) element, flags, kind, delta);
			}
		}
	}
	
	private void processJavaProject(final IJavaProject jproject,
			final int flags, final int kind, final IJavaElementDelta delta) {

		final IProject project = jproject.getProject();
		final List pathsToAdd = new ArrayList();
		final List pathsToRemove = new ArrayList();
		final List changedJavaPaths = new ArrayList();

		// make certain this is a J2EE project
		if (ModuleCoreNature.isFlexibleProject(project)) {

			WorkspaceJob job = new WorkspaceJob(
					RefactorMessages.J2EEElementChangedListener_J2EE_Component_Mapping_Update_) {
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor)
						throws CoreException {// Did the classpath change?
					if ((flags & IJavaElementDelta.F_CHILDREN) == IJavaElementDelta.F_CHILDREN) {
						final boolean cpChanged = (flags & IJavaElementDelta.F_CLASSPATH_CHANGED) != 0;
						getJavaSrcMappings(delta.getAffectedChildren(),
								cpChanged, jproject, pathsToAdd, pathsToRemove,
								changedJavaPaths);
					}

					// Did a non-Java folder change name?
					final IResourceDelta[] deltas = delta.getResourceDeltas();
					if (deltas != null && deltas.length > 0) {
						getNonJavaFolderMappings(deltas, project, pathsToAdd,
								pathsToRemove, changedJavaPaths);
					}

					for(int i=0;i<pathsToAdd.size(); i++){
						Object[] toAdd = (Object[]) pathsToAdd.get(i);
						final IVirtualFolder destFolder = (IVirtualFolder) toAdd[1];
						final IPath pathToAdd = (IPath) toAdd[0];
						destFolder.createLink(pathToAdd, 0, monitor);
					}
					for(int i=0;i<pathsToRemove.size(); i++){
						Object[] toRemove = (Object[]) pathsToRemove.get(i);
						final IVirtualFolder destFolder = (IVirtualFolder) toRemove[1];
						final IPath pathToRemove = (IPath) toRemove[0];
						destFolder.removeLink(pathToRemove, 0, monitor);
					}
					return Status.OK_STATUS;
				
					
				}

				@Override
				public boolean belongsTo(final Object family) {
					return PROJECT_COMPONENT_UPDATE_JOB_FAMILY.equals(family);
				}
			};
			job.setRule(ResourcesPlugin.getWorkspace().getRoot());
			job.setSystem(true);
			job.schedule();

			
		}
	}
	
	private void processResourceDeltas(final int flags, final int kind, final IJavaElementDelta delta) {
		final List pathsToAdd = new ArrayList();
		final List pathsToRemove = new ArrayList();

		final IResourceDelta[] deltas = delta.getResourceDeltas();
		if (deltas != null && deltas.length > 0) {
			try {
				getNonJavaFolderMappings(deltas, null, pathsToAdd, pathsToRemove, Collections.EMPTY_LIST);
			} catch (CoreException ce) {
				J2EEPlugin.logError(ce);
				return;
			}
		}
		
		updateMappingsInJob(pathsToAdd, pathsToRemove);
	}
	
	/*
	 * Computes the virtual component path mapping changes the need to be made due to 
	 * Java src path changes.
	 */ 
	private void getJavaSrcMappings(final IJavaElementDelta[] children, final boolean cpChanged, final IJavaProject jproject, final List pathsToAdd, final List pathsToRemove, final List changedPaths) 
		throws CoreException {
		
		IVirtualComponent c = ComponentCore.createComponent(jproject.getProject());
		if(c == null)
			return;
		// get the default destination folder
		final IVirtualFolder defaultDestFolder = getDestinationFolder(c);
		
		for (int i = 0; i < children.length; i++) {
			final IJavaElementDelta delta = children[i];
			final IJavaElement element = delta.getElement();
			if(element.getElementType() == IJavaElement.PACKAGE_FRAGMENT_ROOT){
				final IPackageFragmentRoot root = (IPackageFragmentRoot) element;
				int cpeKind = IPackageFragmentRoot.K_SOURCE;
				boolean abortAdd = false;
				try {
					cpeKind = root.getKind();
				} catch (JavaModelException jme) {
					// this is thrown if the folder corresponding to the CPE has been deleted
					// since it could represent another error, we need to abort adding. 
					abortAdd = true;
				}
				// only update if we know it is a src folder
				if (cpeKind == IPackageFragmentRoot.K_SOURCE) {
					final int kind = delta.getKind();					
					if (!cpChanged) {
						// if the classpath is not changed, save modifications to the Java src path
						if (kind == IJavaElementDelta.CHANGED || kind == IJavaElementDelta.REMOVED) {
							changedPaths.add(root.getPath().removeFirstSegments(1));		
						}
					} else {
					
						// kind and flags for CP additions are somewhat sporadic; either:
						// -kind is ADDED and flags are 0
						//   or
						// -kind is CHANGED and flags are F_ADDED_TO_CLASSPATH
						final int flags = delta.getFlags();

						if (kind == IJavaElementDelta.ADDED || 
								(flags & IJavaElementDelta.F_ADDED_TO_CLASSPATH) == IJavaElementDelta.F_ADDED_TO_CLASSPATH) {
							if (!abortAdd) {
								final IPath pathToAdd = root.getPath().removeFirstSegments(1);
								pathsToAdd.add(new Object[]{pathToAdd, defaultDestFolder});
								// if the added src path was moved from another location, remove any mapping for that
								// location
								if ((flags & IJavaElementDelta.F_MOVED_FROM) == IJavaElementDelta.F_MOVED_FROM) {
									final IJavaElement movedFromElement = delta.getMovedFromElement();
									final IPath pathToRemove = movedFromElement.getPath().removeFirstSegments(1);
									pathsToRemove.add(new Object[]{pathToRemove, defaultDestFolder});
								}
							}
							// getting a kind = REMOVED and flags = 0 for removal of the folder (w/o removing the CPE), probably
							// should not be generated
						} else if (kind == IJavaElementDelta.REMOVED || (flags & IJavaElementDelta.F_REMOVED_FROM_CLASSPATH) == IJavaElementDelta.F_REMOVED_FROM_CLASSPATH) {
							IPath path = root.getPath().removeFirstSegments(1);
							pathsToRemove.add(new Object[]{path, defaultDestFolder});
						} 
					}			
				}
			}
		}
	}
	
	/*
	 * Computes the virtual component path mapping changes the need to be made due to changes to
	 * non-Java folders. 
	 */ 
	private void getNonJavaFolderMappings(final IResourceDelta[] deltas, final IProject project, final List pathsToAdd, final List pathsToRemove, final List changedJavaPaths) throws CoreException {
		IVirtualFolder rootFolder = null;
		IVirtualComponent c = ComponentCore.createComponent(project);
		if(c == null)
			return;
		if (project != null) {
			rootFolder = c.getRootFolder();
		}
		Map sourceToRuntime = null;
		if (project != null) {
			sourceToRuntime = getResourceMappings(project);
		}
		for (int i = 0; i < deltas.length; i++) {
			final IResourceDelta delta = deltas[i];
			processResourceDelta(delta, rootFolder, sourceToRuntime, pathsToAdd, pathsToRemove, changedJavaPaths);
		}
	}
	
	/*
	 * Processes a single IResourceDelta.
	 */
	private void processResourceDelta(final IResourceDelta delta, IVirtualFolder rootFolder, Map sourceToRuntime, final List pathsToAdd, final List pathsToRemove, final List changedJavaPaths) throws CoreException {
		IVirtualFolder localRootFolder = rootFolder;
		Map localSourceToRuntime = sourceToRuntime;
		final int kind = delta.getKind();
		if (kind == IResourceDelta.CHANGED) {
			IResourceDelta[] childDeltas = delta.getAffectedChildren();
			for (int i = 0; i < childDeltas.length; i++) {
				processResourceDelta(childDeltas[i], localRootFolder, localSourceToRuntime, pathsToAdd, pathsToRemove, changedJavaPaths);
			}
		} else {
			final int flags = delta.getFlags();
			if ((flags & IResourceDelta.MOVED_FROM) == IResourceDelta.MOVED_FROM) {
				if (localRootFolder == null) {
					final IProject project = delta.getResource().getProject();
					// make certain this is a J2EE project
					if (ModuleCoreNature.getModuleCoreNature(project) != null) {
						IVirtualComponent c = ComponentCore.createComponent(project);
						localRootFolder = c.getRootFolder();
						localSourceToRuntime = getResourceMappings(project);
					} else {
						// not a J2EE project
						return;
					}
				}
				final IPath movedFrom = delta.getMovedFromPath().removeFirstSegments(1);
				if (changedJavaPaths.contains(movedFrom)) {
					// don't update renamed Java src paths
					return;
				}
				final IPath movedTo = delta.getFullPath().removeFirstSegments(1);
				final IPath runtimePath = (IPath) localSourceToRuntime.get(movedFrom);
				// does the old path have a virtual component mapping?
				if (runtimePath != null) {
					final IVirtualFolder folder = localRootFolder.getFolder(runtimePath);
					// only add if the project relative paths are not equal (these can be equal when the root folder is mapped and the project is renamed)
					if (!movedFrom.equals(movedTo)) {
						pathsToRemove.add(new Object[]{movedFrom, folder});
						pathsToAdd.add(new Object[]{movedTo, folder});
					}
				}
			}
		}
	}
	
	private Map getResourceMappings(final IProject project){
		final Map sourceToRuntime = new HashMap();
		StructureEdit core = null;
		try {
			core = StructureEdit.getStructureEditForRead(project);
			final WorkbenchComponent component = core.getComponent();
			if (null != component) {
				final List currentResources = new ArrayList(component.getResources());
				for (Iterator iter = currentResources.iterator(); iter.hasNext();) {
					final ComponentResource resource = (ComponentResource) iter.next();
					sourceToRuntime.put(resource.getSourcePath().makeRelative(), resource.getRuntimePath());
				}
			}
		} catch (NullPointerException e) {
			J2EEPlugin.logError(e);
		} finally {
			if (core != null)
				core.dispose();
		}
		return sourceToRuntime;
	}

	/*
	 * Retrieves the IVirtualFolder to which Java src folders should be mapped
	 */
	private IVirtualFolder getDestinationFolder(final IVirtualComponent c) throws CoreException {
		final IVirtualFolder root = c.getRootFolder();
		
		if( JavaEEProjectUtilities.usesJavaEEComponent(c )){
			if (JavaEEProjectUtilities.isDynamicWebProject(c.getProject())) {
				return root.getFolder(new Path(J2EEConstants.WEB_INF_CLASSES));
			}
			return root;
		}
		//get source folders
		List<IPath> srcFolders = getSourceFolders(c.getProject());
		//get existing deploy mappings
		Map mapping = getResourceMappings(c.getProject());
		IPath deployPath = null;
		
		//if existing source folder has mapping set for this one
		for( IPath srcFolder : srcFolders )
		{
			if( mapping.containsKey(srcFolder) ){
				deployPath = (IPath)mapping.get(srcFolder);
				if( deployPath == null )
					return root;
				return root.getFolder(deployPath);
			}
		}
		return root;
	}
	
	private void updateMappingsInJob(final List pathsToAdd, final List pathsToRemove) {
		// If there are corrections to the virtual path mappings, execute them in a Job
		if (!pathsToAdd.isEmpty() || !pathsToRemove.isEmpty()) {
			WorkspaceJob job = new WorkspaceJob(RefactorMessages.J2EEElementChangedListener_J2EE_Component_Mapping_Update_) {							
				@Override
				public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
					for(int i=0;i<pathsToAdd.size(); i++){
						Object[] toAdd = (Object[]) pathsToAdd.get(i);
						final IVirtualFolder destFolder = (IVirtualFolder) toAdd[1];
						final IPath pathToAdd = (IPath) toAdd[0];
						destFolder.createLink(pathToAdd, 0, monitor);
					}
					for(int i=0;i<pathsToRemove.size(); i++){
						Object[] toRemove = (Object[]) pathsToRemove.get(i);
						final IVirtualFolder destFolder = (IVirtualFolder) toRemove[1];
						final IPath pathToRemove = (IPath) toRemove[0];
						destFolder.removeLink(pathToRemove, 0, monitor);
					}
					return Status.OK_STATUS;
				}
				@Override
				public boolean belongsTo(final Object family) {
					return PROJECT_COMPONENT_UPDATE_JOB_FAMILY.equals(family);
				}
			};
			job.setRule(ResourcesPlugin.getWorkspace().getRoot());
			job.setSystem(true);
			job.schedule();
		}						
	}

	protected static List<IPath> getSourceFolders(IProject project) {
		
		IJavaProject javaProj = JavaCore.create(project);
		if (javaProj == null)
			return null;
		if( !javaProj.exists() )
			return null;
		
		IClasspathEntry[] cp = null;
		try {
			cp = javaProj.getRawClasspath();
		} catch (JavaModelException ex) {
			J2EEPlugin.logError(ex);
			return null;
		}
		List sourcePaths = new ArrayList();
		for (int i = 0; i < cp.length; i++) {
			if (cp[i].getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				sourcePaths.add(cp[i].getPath().removeFirstSegments(1));
			}
		}
		return sourcePaths;
	}
	
}
