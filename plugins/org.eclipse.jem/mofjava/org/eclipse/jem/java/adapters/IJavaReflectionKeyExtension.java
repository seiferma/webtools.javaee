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
/*


 */
package org.eclipse.jem.java.adapters;

/**
 * Extension interface to add an additional reflection key in to handle
 * other types of key's not specifically handled.
 * 
 * @since 1.0.0
 */
public interface IJavaReflectionKeyExtension {

	/**
	 * Get the object from the given id.
	 * @param id
	 * @param reflectionKey
	 * @return the object (it should add the id with object to the resource's id table before returning it) or <code>null</code> if not one it handles.
	 * 
	 * @since 1.0.0
	 */
	public Object getObject(String id, IJavaReflectionKey reflectionKey);
	
}



