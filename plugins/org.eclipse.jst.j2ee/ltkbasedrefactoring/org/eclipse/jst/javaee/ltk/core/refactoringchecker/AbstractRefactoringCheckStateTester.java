/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.jst.javaee.ltk.core.refactoringchecker;

import org.eclipse.core.resources.IProject;


public abstract class AbstractRefactoringCheckStateTester implements IRefactoringCheckStateTester{

	@Override
	public boolean testUncheckState(IProject project){
		return false;
	}
}
