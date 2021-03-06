/*******************************************************************************
 * Copyright (c) 2007 BEA Systems, Inc and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *    BEA Systems, Inc - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.classpath.tests;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllTests extends TestCase {
    public static TestSuite suite() {
        final TestSuite suite = new TestSuite();
        
        suite.setName("All Classpath Dependency Tests");
        suite.addTest(ClasspathDependencyCreationTests.suite());
        //suite.addTest(ClasspathDependencyValidationTests.suite());
        //suite.addTest(ClasspathDependencyEARTests.suite());
        suite.addTest(ClasspathDependencyWebTests.suite());
        
        return suite;
    }

}
