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
package org.eclipse.jem.internal.proxy.ide;
/*


 */

import org.eclipse.jem.internal.proxy.core.IStringBeanProxy;
/**
 * IDE Implementation of the BeanType proxy for java.lang.String
 */
final class IDEStringBeanTypeProxy extends IDEBeanTypeProxy {
	// Cache a proxy to an empty string for speed
	protected final IDEStringBeanProxy emptyStringProxy;

IDEStringBeanTypeProxy(IDEProxyFactoryRegistry aRegistry, Class aClass) {
	super(aRegistry, aClass);
	// Cache a proxy to an empty string for speed
	emptyStringProxy = new IDEStringBeanProxy(aRegistry, "",this);	//$NON-NLS-1$
}
/**
 * Helper to create string beans
 * Package protected because no-one else outside the package should use this, they
 * must go through the API where you look up a factory first.
 */
IStringBeanProxy createStringBeanProxy(String aString){
	if ( aString.equals("") ) {//$NON-NLS-1$
		return emptyStringProxy;
	} else {
		return new IDEStringBeanProxy(fProxyFactoryRegistry,aString,this);
	}
}
/* Specialized from IDEBeanTypeProxy to ensure IStringBeanProxies are created correctly
 */
protected IIDEBeanProxy newBeanProxy(Object anObject){

	return (IIDEBeanProxy)createStringBeanProxy( (String)anObject );

}
}
