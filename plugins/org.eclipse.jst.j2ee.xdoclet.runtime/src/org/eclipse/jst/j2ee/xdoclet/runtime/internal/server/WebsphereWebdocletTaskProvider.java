/***************************************************************************************************
 * Copyright (c) 2005 Eteration A.S. and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors: Eteration A.S. - initial API and implementation
 **************************************************************************************************/
package org.eclipse.jst.j2ee.xdoclet.runtime.internal.server;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jst.j2ee.xdoclet.runtime.internal.XDocletTaskProviderImpl;

public class WebsphereWebdocletTaskProvider extends XDocletTaskProviderImpl {

	public static String NAMESPACE = "webdoclet.websphere.";

	public String getTask() {
		StringBuffer buffer = new StringBuffer(256);
		buffer.append("<webspherewebxml ");
		buildAttributes(buffer);
		buffer.append(" />");
		return buffer.toString();
	}

	public String getNamespace() {
		return NAMESPACE;
	}

	public IStatus validate() {
		return Status.OK_STATUS;
	}
}
