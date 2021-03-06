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


package org.eclipse.wtp.jee.headless.tests.web.operations;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {
	
    public static Test suite(){
        return new AllTests();
    }
    
    public AllTests(){
       super("Web Operation, Servlet, & Deploy Tests");
       addTest(WebImportOperationTest.suite());
    }    
}
