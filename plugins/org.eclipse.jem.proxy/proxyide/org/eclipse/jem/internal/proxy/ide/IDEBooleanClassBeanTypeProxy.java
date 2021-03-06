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

import org.eclipse.jem.internal.proxy.core.*;

final public class IDEBooleanClassBeanTypeProxy extends IDEBeanTypeProxy {

	// Cache these instances. Since the BeanType proxies are cached also, only one instance
	// of true/false will be in system.
	protected final IDEBooleanBeanProxy trueProxy;
	protected final IDEBooleanBeanProxy falseProxy;

IDEBooleanClassBeanTypeProxy(IDEProxyFactoryRegistry aRegistry, Class aClass) {

	super(aRegistry, aClass);
	trueProxy = new IDEBooleanBeanProxy( fProxyFactoryRegistry, Boolean.TRUE, this );
	falseProxy = new IDEBooleanBeanProxy( fProxyFactoryRegistry, Boolean.FALSE, this);	
}
IBooleanBeanProxy createBooleanBeanProxy(Boolean aBoolean) {
	if ( aBoolean.booleanValue() ){
		return trueProxy;
	} else {
		return falseProxy;
	}
}
/* Specialized from IDEBeanTypeProxy to ensure IBooleanBeanProxies are created correctly
 */
protected IIDEBeanProxy newBeanProxy(Object anObject){

	if ( anObject == null || anObject == Boolean.FALSE ) {
		return falseProxy;
	} else {
		return trueProxy;
	}
}
}
