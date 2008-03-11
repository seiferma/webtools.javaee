/***********************************************************************
 * Copyright (c) 2008 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 ***********************************************************************/
package org.eclipse.jst.jee.model.tests;

import static org.junit.Assert.assertTrue;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jst.j2ee.model.IModelProvider;
import org.eclipse.jst.javaee.ejb.EJBJar;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;

/**
 * @author Kiril Mitov k.mitov@sap.com
 * 
 */
public abstract class AbstractAnnotationModelTest {

	protected static final String localInterfaceContent = "package com.sap;" + "import javax.ejb.Local;"
			+ "@Local public interface SessionBeanLocal {}";
	protected static final String beanContent = "package com.sap;" + "import javax.ejb.Stateless;"
			+ "@Stateless public class SessionBean implements SessionBeanLocal {}";

	protected static IFacetedProject facetedProject;

	protected static IProject clientProject;

	protected IModelProvider fixture;

	protected void saveFileAndUpdate(IFile beanFile, String beanContent) throws Exception, InterruptedException {
		SynchronousModelChangedListener listener = new SynchronousModelChangedListener(1);
		getFixture().addListener(listener);
		AbstractTest.saveFile(beanFile, beanContent);
		assertTrue(listener.waitForEvents());
		getFixture().removeListener(listener);
	}

	protected void deleteFileAndUpdate(IFile beanFile) throws Exception, InterruptedException {
		SynchronousModelChangedListener listener = new SynchronousModelChangedListener(1);
		getFixture().addListener(listener);
		AbstractTest.deleteFile(beanFile);
		assertTrue(listener.waitForEvents());
		getFixture().removeListener(listener);
	}

	protected static void createSessionBeanLocal(IPackageFragment fragment) throws Exception {
		IFile file = ((IContainer) fragment.getResource()).getFile(new Path("SessionBeanLocal.java"));
		AbstractTest.saveFile(file, localInterfaceContent);
	}

	protected static void createSessionBean(IPackageFragment fragment) throws Exception {
		IFile file = ((IContainer) fragment.getResource()).getFile(new Path("SessionBean.java"));
		AbstractTest.saveFile(file, beanContent);
	}

	/**
	 * @return the reader
	 */
	protected IModelProvider getFixture() {
		return fixture;
	}

	protected EJBJar getEJBJar() {
		return (EJBJar) getFixture().getModelObject();
	}

}
