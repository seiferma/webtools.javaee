/*******************************************************************************
 * Copyright (c) 2003, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.datamodel.properties;

import org.eclipse.jst.j2ee.archive.IArchiveExportParticipant;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProperties;
/**
 * <p>
 * IJ2EEComponentExportDataModelProperties provides properties to the IDataModel associated with the 
 * J2EEComponentExportDataModelProvider as well as all extending interfaces extending 
 * IJ2EEComponentExportDataModelProperties specifically all J2EE component specific exports.
 * NOTE: The associated Provider and Operations will be created during M5
 * </p>
 * <p>
 * This interface is not intended to be implemented by clients.
 * </p>
 * 
 * @see org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider
 * @see org.eclipse.wst.common.frameworks.datamodel.DataModelFactory
 * @see org.eclipse.wst.common.frameworks.datamodel.IDataModelProperties
 * 
 * @plannedfor 1.0
 */
public interface IJ2EEComponentExportDataModelProperties extends IDataModelProperties {

	/**
     * Required, type String. The user defined name of the project to be exported.
     */
    public static final String PROJECT_NAME = "IJ2EEComponentExportDataModelProperties.PROJECT_NAME"; //$NON-NLS-1$

    /**
     * Required, type IPath.  The user defined export location including fully qualified Path and archive file
     * to be created name. 
     */
    public static final String ARCHIVE_DESTINATION = "IJ2EEComponentExportDataModelProperties.ARCHIVE_DESTINATION"; //$NON-NLS-1$
    /**
     * Optional, type Boolean.  If <code>Boolean.TRUE</code> source and class fields will both be archived and exported.
     * Otherwise if <code>Boolean.FALSE</code> only output files are exported.
     */
    public static final String EXPORT_SOURCE_FILES = "IJ2EEComponentExportDataModelProperties.EXPORT_SOURCE_FILES"; //$NON-NLS-1$
    /**
     * Optional, type Boolean.  If <code>Boolean.TRUE</code> and an archive with the same name exists in the 
     * ARCHIVE_DESTINATION, the existing will be overwritten by the archive to be created.  Otherwise,
     * if <code>Boolean.FALSE</code> a error message will be show indicating name collisions.
     */
    public static final String OVERWRITE_EXISTING = "IJ2EEComponentExportDataModelProperties.OVERWRITE_EXISTING"; //$NON-NLS-1$
    /**
     * Optional, type boolean, Default <code>Boolean.TRUE</code> indicating a Build runs before exporting thus output files are created
     * and archived.  However, if <code>Boolean.FALSE</code> the component is archived as is.
     */
    public static final String RUN_BUILD = "IJ2EEComponentExportDataModelProperties.RUN_BUILD"; //$NON-NLS-1$
    
    public static final String COMPONENT = "IJ2EEComponentExportDataModelProperties.COMPONENT"; //$NON-NLS-1$
    
    /**
     * @since 3.0
     */
    
    public static final String OPTIMIZE_FOR_SPECIFIC_RUNTIME = "IJ2EEComponentExportDataModelProperties.OPTIMIZE_FOR_SPECIFIC_RUNTIME"; //$NON-NLS-1$
    
    /**
     * @since 3.0
     */
    
    public static final String RUNTIME = "IJ2EEComponentExportDataModelProperties.RUNTIME"; //$NON-NLS-1$
    
    /**
     * @since 3.0
     */
    
    public static final String RUNTIME_SPECIFIC_PARTICIPANTS 
        = "IJ2EEComponentExportDataModelProperties.RUNTIME_SPECIFIC_PARTICIPANTS"; //$NON-NLS-1$
    
    /**
     * @since 3.0
     */
    
    interface IArchiveExportParticipantData
    {
        String getId();
        IArchiveExportParticipant getParticipant();
        IDataModel getDataModel();
    }
    
}
