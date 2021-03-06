/*******************************************************************************
 * Copyright (c) 2003, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.j2ee.bindingshelper.tests;

public class FooBindingsHelper extends TestBindingsHelper {

	public static String getFileName(){
		return "foo.txt"; //$NON-NLS-1$
	}
	
	public String getBindingsFileName() {
		return getFileName();
	}
}
