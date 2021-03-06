/*******************************************************************************
 * Copyright (c) 2009 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 * IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.common.ui.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.jst.common.ui.internal"; //$NON-NLS-1$
	public static String ArchiveTitle;
	public static String ArchiveDescription;
	public static String ExternalArchiveTitle;
	public static String ExternalArchiveDescription;
	public static String ArchiveDialogNewTitle;
	public static String ArchiveDialogNewDescription;
	public static String Browse;
	public static String VariableReferenceTitle;
	public static String VariableReferenceDescription;
	public static String AddManifestEntryTaskWizardTitle;
	public static String AddManifestEntryTaskWizardDesc;
	public static String ParentProject;
	public static String CustomEntryButton;
	public static String Add;
	public static String Remove;
	public static String MoveUp;
	public static String MoveDown;
	public static String ManifestEntryColumn;
	public static String ManifestEntrySourceColumn;
	public static String ConfigureParentLink;
	public static String InvalidArchivesWarning;
	public static String WarningTitleDialog;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME + ".messages", Messages.class); //$NON-NLS-1$
	}

	private Messages() {
	}
}
