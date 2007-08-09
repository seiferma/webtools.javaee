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
package org.eclipse.jst.jee.model.internal;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.jst.j2ee.internal.J2EEVersionConstants;
import org.eclipse.jst.j2ee.internal.project.J2EEProjectUtilities;
import org.eclipse.jst.j2ee.model.IEARModelProvider;
import org.eclipse.jst.javaee.application.Application;
import org.eclipse.jst.javaee.application.ApplicationDeploymentDescriptor;
import org.eclipse.jst.javaee.application.ApplicationFactory;
import org.eclipse.jst.javaee.application.Module;
import org.eclipse.jst.javaee.application.internal.util.ApplicationResourceImpl;
import org.eclipse.wst.common.componentcore.ComponentCore;
import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.componentcore.resources.IVirtualReference;

public class EAR5ModelProvider extends JEE5ModelProvider implements IEARModelProvider {
	
	private static final String EAR5_CONTENT_TYPE = "org.eclipse.jst.jee.ee5earDD"; //$NON-NLS-1$
	public EAR5ModelProvider(IProject proj) {
		super();
		this.proj = proj;
		setDefaultResourcePath(new Path(J2EEConstants.APPLICATION_DD_URI));
	}
	protected String getContentTypeDescriber() {
		return EAR5_CONTENT_TYPE;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jst.j2ee.model.IModelProvider#getModelObject(org.eclipse.core.runtime.IPath)
	 */
	public Object getModelObject(IPath modelPath) {
		ApplicationResourceImpl earRes = (ApplicationResourceImpl)getModelResource(modelPath);
		if (earRes != null && earRes.getContents().size() > 0) 
			return earRes.getApplication();
		return null;
	}

	public String getModuleURI(IVirtualComponent moduleComp) {
		IVirtualComponent comp = ComponentCore.createComponent(proj);
		IVirtualReference [] refs = comp.getReferences();
		for(int i=0; i<refs.length; i++){
			if(refs[i].getReferencedComponent().equals(moduleComp)){
				return refs[i].getArchiveName();
			}
		}
		return null;		
	}
	/**
	 * This method will return the context root in this application for the passed in web project.
	 * 
	 * @param webProject
	 * @return contextRoot String
	 */
	public String getWebContextRoot(IProject webProject) {
		if (webProject == null || !J2EEProjectUtilities.isDynamicWebProject(webProject))
			return null;
		IVirtualComponent webComp = ComponentCore.createComponent(webProject);
		String webModuleURI = getModuleURI(webComp);
		if (webModuleURI != null) {
			Module webModule = (Module) ((Application)getModelObject()).getModule(webModuleURI, null);
			if (webModule != null)
				return webModule.getWeb().getContextRoot();
		}
		return null;
	}
	public void populateRoot(XMLResourceImpl res) {
		ApplicationDeploymentDescriptor dd = ApplicationFactory.eINSTANCE.createApplicationDeploymentDescriptor();
		dd.getXMLNSPrefixMap().put("", J2EEConstants.JAVAEE_NS_URL);  //$NON-NLS-1$
		dd.getXMLNSPrefixMap().put("xsi", J2EEConstants.XSI_NS_URL); //$NON-NLS-1$
		dd.getXSISchemaLocation().put(J2EEConstants.JAVAEE_NS_URL, J2EEConstants.APPLICATION_SCHEMA_LOC_5);
		Application ear = ApplicationFactory.eINSTANCE.createApplication();
		ear.setVersion(J2EEVersionConstants.VERSION_5_0_TEXT);
		dd.setApplication(ear);
		res.getContents().add((EObject) dd);
	}

}
