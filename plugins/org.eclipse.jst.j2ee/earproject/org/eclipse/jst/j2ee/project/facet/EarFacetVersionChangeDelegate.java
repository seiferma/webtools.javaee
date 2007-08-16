/******************************************************************************
 * Copyright (c) 2005 BEA Systems, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Konstantin Komissarchik - initial API and implementation
 ******************************************************************************/

package org.eclipse.jst.j2ee.project.facet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
/**
 * Warning clean-up 12/05/2005
 */   
//import org.eclipse.wst.common.componentcore.ComponentCore;
//import org.eclipse.wst.common.componentcore.resources.IVirtualComponent;
import org.eclipse.wst.common.project.facet.core.IDelegate;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * @author <a href="mailto:kosta@bea.com">Konstantin Komissarchik</a>
 */

public final class EarFacetVersionChangeDelegate 

    implements IDelegate
    
{
    public void execute( final IProject project, 
                         final IProjectFacetVersion fv,
                         final Object cfg,
                         final IProgressMonitor monitor )
    
        throws CoreException
        
    {
        if( monitor != null )
        {
            monitor.beginTask( "", 1 );
        }
        
        try
        {
         /**
          * Warning clean-up 12/05/2005
          */   
            //final IVirtualComponent c = ComponentCore.createComponent(project);
            
//            final StructureEdit edit 
//                = StructureEdit.getStructureEditForWrite( project );
//            
//            try
//            {
//                final ComponentType oldctype 
//                    = StructureEdit.getComponentType( c );
//                
//                final ComponentType newctype 
//                    = ComponentcoreFactory.eINSTANCE.createComponentType();
//                
//                newctype.setComponentTypeId( oldctype.getComponentTypeId() );
//                newctype.setVersion( fv.getVersionString() );
//                newctype.getProperties().addAll( oldctype.getProperties() );
//                
//                StructureEdit.setComponentType( c, newctype );
//                
//                edit.saveIfNecessary( null );
//            }
//            finally
//            {
//                edit.dispose();
//            }
            
            if( monitor != null )
            {
                monitor.worked( 1 );
            }
        }
        finally
        {
            if( monitor != null )
            {
                monitor.done();
            }
        }
    }

}
