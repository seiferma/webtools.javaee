/*******************************************************************************
 * Copyright (c) 2001, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jem.java.internal.impl;
/*


 */
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.jem.internal.java.instantiation.IInstantiationHandler;
import org.eclipse.jem.internal.java.instantiation.IInstantiationHandlerFactoryAdapter;

/**
 * The factory to use JavaRef packages. It will use the
 * IInstantiationHandler, if there is one, to do the
 * factory requests. Else it will let the superclass handle it.
 */
public class JavaFactoryImpl extends EFactoryImpl {
	
	private IInstantiationHandler instantiationHandler;
	private boolean retrievedHandler;

	/**
	 * Constructor for JavaFactoryImpl.
	 */
	public JavaFactoryImpl() {
		super();
	}

	protected IInstantiationHandler getInstantiationHandler() {
		if (!retrievedHandler) {
			// Need to retrieve handler lazily because when factory is created it does not yet know what ResourceSet it is in.
			// Can't know that until the first time we need a handler.
			ResourceSet rset = getEPackage().eResource().getResourceSet();
			if (rset != null) {
				retrievedHandler = true;
				IInstantiationHandlerFactoryAdapter factory = (IInstantiationHandlerFactoryAdapter) EcoreUtil.getExistingAdapter(rset, IInstantiationHandlerFactoryAdapter.ADAPTER_KEY);
				if (factory != null)
					instantiationHandler = factory.getInstantiationHandler(this);
			}
		}
		return instantiationHandler;
	}
	
	/**
	 * @see org.eclipse.emf.ecore.EFactory#create(EClass)
	 */
	public EObject create(EClass eClass) {
		IInstantiationHandler ia = getInstantiationHandler();
		if (ia == null || !ia.handlesClass(eClass))
			return super.create(eClass);
		else
			return ia.create(eClass);
	}

}
