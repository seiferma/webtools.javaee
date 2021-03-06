/*******************************************************************************
 * Copyright (c) 2003, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.common.internal.annotations.registry;

/**
 * This method will be called by the AnnotationTagRegistry
 * when it is time to register the tags for a given
 * TagSet.  An AnnotationTagDynamicInitializer defined
 * using the annotationTagDynamicInitializer.
 * 
 * @see com.ibm.wtp.annotations.registry.AnnotationTagRegistry
 */
public interface AnnotationTagDynamicInitializer {
	void registerTags();
}