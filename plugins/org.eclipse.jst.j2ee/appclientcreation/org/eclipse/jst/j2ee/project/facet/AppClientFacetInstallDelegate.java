/*******************************************************************************
 * Copyright (c) 2005, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.project.facet;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jst.common.project.facet.WtpUtils;
import org.eclipse.jst.common.project.facet.core.ClasspathHelper;
import org.eclipse.jst.j2ee.applicationclient.componentcore.util.AppClientArtifactEdit;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.common.J2EEVersionUtil;
import org.eclipse.jst.j2ee.internal.common.classpath.J2EEComponentClasspathContainer;
import org.eclipse.jst.j2ee.internal.common.classpath.J2EEComponentClasspathContainerUtils;
import org.eclipse.jst.j2ee.internal.plugin.J2EEPlugin;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.datamodel.FacetDataModelProvider;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualFolder;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelOperation;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

public class AppClientFacetInstallDelegate extends J2EEFacetInstallDelegate implements IDelegate {

	public void execute(IProject project, IProjectFacetVersion fv, Object config, IProgressMonitor monitor) throws CoreException {
		if (monitor != null)
			monitor.beginTask("", 1); //$NON-NLS-1$
		try {
			IDataModel model = (IDataModel) config;
			final IJavaProject jproj = JavaCore.create(project);

			// Add WTP natures.
			WtpUtils.addNatures(project);

			// Setup the flexible project structure.
			createFlexibleProject(monitor, project, model, jproj, fv);

			// Setup the classpath.
			ClasspathHelper.removeClasspathEntries(project, fv);
			if (!ClasspathHelper.addClasspathEntries(project, fv)) {
				// TODO: Support the no runtime case.
				// ClasspathHelper.addClasspathEntries( project, fv, <something> );
			}


//			// Add main class if necessary
//			if (model.getBooleanProperty(IAppClientFacetInstallDataModelProperties.CREATE_DEFAULT_MAIN_CLASS))
//				addMainClass(monitor, model, project);

			if(J2EEComponentClasspathContainerUtils.getDefaultUseEARLibraries()){
				final IPath earLibContainer = new Path(J2EEComponentClasspathContainer.CONTAINER_ID);
				addToClasspath(jproj, JavaCore.newContainerEntry(earLibContainer));
			}
			
			try {
				((IDataModelOperation) model.getProperty(FacetDataModelProvider.NOTIFICATION_OPERATION)).execute(monitor, null);
			} catch (ExecutionException e) {
				J2EEPlugin.logError(e);
			}

			if (monitor != null)
				monitor.worked(1);
		} catch (Exception e) {
			J2EEPlugin.logError(e);
		} finally {
			if (monitor != null)
				monitor.done();
		}
	}

	protected IVirtualComponent createFlexibleProject(IProgressMonitor monitor, IProject project, IDataModel model, IJavaProject jproj, IProjectFacetVersion fv) throws Exception {
		// Create the directory structure.
		final IWorkspace ws = ResourcesPlugin.getWorkspace();
		final IPath pjpath = project.getFullPath();

		final IVirtualComponent c = ComponentCore.createComponent(project, false);
		c.create(0, null);
		setOutputFolder(model, c);
		final IVirtualFolder root = c.getRootFolder();
		
		IFolder sourceFolder = null;
		String configFolder = null;
		configFolder = model.getStringProperty(IJ2EEModuleFacetInstallDataModelProperties.CONFIG_FOLDER);
		root.createLink(new Path("/" + configFolder), 0, null); //$NON-NLS-1$
		String configFolderName = model.getStringProperty(IJ2EEModuleFacetInstallDataModelProperties.CONFIG_FOLDER);
		IPath configFolderpath = pjpath.append(configFolderName);
		sourceFolder = ws.getRoot().getFolder(configFolderpath);

		if( fv == IJ2EEFacetConstants.APPLICATION_CLIENT_60 )
		{
	        if(model.getBooleanProperty(IJ2EEFacetInstallDataModelProperties.GENERATE_DD)){
	            // Create the deployment descriptor (application-client.xml) if one doesn't exist
	            IFile appClientFile = sourceFolder.getFile(new Path(J2EEConstants.APP_CLIENT_DD_URI));
	            if (!appClientFile.exists()) {
	                try {
	                    if(!appClientFile.getParent().exists()
	                            && (appClientFile.getParent().getType() ==  IResource.FOLDER)){
	                        ((IFolder)appClientFile.getParent()).create(true, true, monitor);
	                    }
	                    //EE6TODO - Update DD header
	                    final String appClientXmlContents = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<application-client version=\"5\" xmlns=\"http://java.sun.com/xml/ns/javaee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/application-client_5.xsd\">\n <display-name> \n" + project.getName() +  "</display-name> \n </application-client>"; //$NON-NLS-1$ //$NON-NLS-2$
	                    appClientFile.create(new ByteArrayInputStream(appClientXmlContents.getBytes("UTF-8")), true, monitor); //$NON-NLS-1$
	                   
	                } catch (UnsupportedEncodingException e) {
	                	J2EEPlugin.logError(e);
	                }           
	            }
	        }
		}
		else if( fv == IJ2EEFacetConstants.APPLICATION_CLIENT_50 )
		{
	        if(model.getBooleanProperty(IJ2EEFacetInstallDataModelProperties.GENERATE_DD)){
	            // Create the deployment descriptor (application-client.xml) if one doesn't exist
	            IFile appClientFile = sourceFolder.getFile(new Path(J2EEConstants.APP_CLIENT_DD_URI));
	            if (!appClientFile.exists()) {
	                try {
	                    if(!appClientFile.getParent().exists()
	                            && (appClientFile.getParent().getType() ==  IResource.FOLDER)){
	                        ((IFolder)appClientFile.getParent()).create(true, true, monitor);
	                    }
	                    final String appClientXmlContents = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<application-client version=\"5\" xmlns=\"http://java.sun.com/xml/ns/javaee\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/application-client_5.xsd\">\n <display-name> \n" + project.getName() +  "</display-name> \n </application-client>"; //$NON-NLS-1$  //$NON-NLS-2$
	                    appClientFile.create(new ByteArrayInputStream(appClientXmlContents.getBytes("UTF-8")), true, monitor); //$NON-NLS-1$
	                   
	                } catch (UnsupportedEncodingException e) {
	                    J2EEPlugin.logError(e);
	                }           
	            }
	        }
		}
		else
		{
    		if (!sourceFolder.getFile(J2EEConstants.APP_CLIENT_DD_URI).exists()) {
    			String ver = model.getStringProperty(IFacetDataModelProperties.FACET_VERSION_STR);
    			int nVer = J2EEVersionUtil.convertVersionStringToInt(ver);
    			AppClientArtifactEdit.createDeploymentDescriptor(project, nVer);
    		}
		}
		
		// add source folder maps
		final IClasspathEntry[] cp = jproj.getRawClasspath();
		for (int i = 0; i < cp.length; i++) {
			final IClasspathEntry cpe = cp[i];
			if (cpe.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
				root.createLink(cpe.getPath().removeFirstSegments(1), 0, null);
			}
		}
		return c;
	}

}
