/*******************************************************************************
 * Copyright (c) 2005, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ejb.ui.internal.actions;

import org.eclipse.jst.j2ee.internal.actions.AbstractActionDelegate;
import org.eclipse.swt.widgets.Shell;



public class EJBClientRemovalActionDelegate extends AbstractActionDelegate {
	private EJBClientRemovalAction realAction = new EJBClientRemovalAction();
    /**
     * 
     */
    public EJBClientRemovalActionDelegate() {
        super();
       	setAllowsMultiSelect(false);
    }
	@Override
	protected void primRun(Shell shell) {
		realAction.setSelection(getStructuredSelection());
		realAction.primRun(shell);
	}
	/* (non-Javadoc)
	 * @see com.ibm.etools.j2ee.common.actions.AbstractActionDelegate#isSupportedAction(java.lang.Object)
	 */
	@Override
	protected boolean isSupportedAction(Object element) {
		return false;
	}

}
