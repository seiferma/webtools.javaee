/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.wtp.j2ee.headless.tests.ejb.operations;

import org.eclipse.jst.j2ee.datamodel.properties.IJ2EEComponentImportDataModelProperties;
import org.eclipse.jst.j2ee.internal.ejb.project.operations.EJBComponentImportDataModelProvider;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wtp.j2ee.headless.tests.j2ee.operations.ModuleImportOperationTest;

public class EJBImportOperationBaseTest extends ModuleImportOperationTest{
	
	public EJBImportOperationBaseTest() {
		super("EJBImportOperationBaseTest");
	}
	
	public EJBImportOperationBaseTest(String name) {
		super(name);
	}
	
	@Override
	protected String getModuleExtension() {
		return ".jar";
	}
	
	@Override
	protected IDataModel getImportDataModel(String filePath, String projectName, IDataModel creationModel, boolean closeArchiveOnDispose) {
		return getEJBImportDataModel(filePath, projectName, creationModel, closeArchiveOnDispose);
	}
	
    public static IDataModel getEJBImportDataModel(String filePath, String projectName, IDataModel creationModel, boolean closeArchiveOnDispose) {
    	IDataModel importModel = DataModelFactory.createDataModel(new EJBComponentImportDataModelProvider());
    	
    	importModel.setProperty(IJ2EEComponentImportDataModelProperties.FILE_NAME, filePath);
    	importModel.setProperty(IJ2EEComponentImportDataModelProperties.PROJECT_NAME, projectName);
    	importModel.setProperty(IJ2EEComponentImportDataModelProperties.CLOSE_ARCHIVE_ON_DISPOSE, closeArchiveOnDispose);
    	
    	if(creationModel != null) {
    		importModel.setProperty(IJ2EEComponentImportDataModelProperties.NESTED_MODEL_J2EE_COMPONENT_CREATION, creationModel);
    	}
    	
    	return importModel;
    }
	
	@Override
	protected IDataModel getExportDataModel(String projectName, String destination, boolean exportSource, boolean runBuild, boolean overwriteExisting) {
		return EJBExportOperationTest.getEJBExportDataModel(projectName, destination, exportSource, runBuild, overwriteExisting);
	}

}
