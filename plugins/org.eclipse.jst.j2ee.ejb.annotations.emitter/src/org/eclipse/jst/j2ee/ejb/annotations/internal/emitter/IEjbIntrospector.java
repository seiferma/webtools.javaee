/***************************************************************************************************
 * Copyright (c) 2005 Eteration A.S. and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors: Eteration A.S. - initial API and implementation
 **************************************************************************************************/

package org.eclipse.jst.j2ee.ejb.annotations.internal.emitter;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jst.j2ee.ejb.EnterpriseBean;



public interface IEjbIntrospector {
	
	public abstract EnterpriseBean getEnterpriseBean();
	public abstract void setCompilationUnit(ICompilationUnit compilationUnit);
}
