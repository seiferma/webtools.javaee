/*******************************************************************************
 * Copyright (c) 2002, 2003,2004 Eteration Bilisim A.S.
 * Naci Dai and others.
 * 
 * Parts developed under contract ref:FT/R&D/MAPS/AMS/2004-09-09/AL are 
 * Copyright France Telecom, 2004.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Eteration Bilisim A.S. - initial API and implementation
 *     Naci Dai
 * For more information on eteration, please see
 * <http://www.eteration.com/>.
 ***************************************************************************/

package org.eclipse.jst.j2ee.ejb.annotations.internal.xdoclet.ui;

import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.eclipse.jst.j2ee.ejb.annotations.internal.xdoclet.XDocletPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;

public class XDocletWebPreferencePage extends PropertyPreferencePage implements SelectionListener {

	private static ResourceBundle bundle = ResourceBundle
			.getBundle("org.eclipse.jst.j2ee.ejb.annotations.internal.xdoclet.ui.preferences");

	private static final String[][] weboptions = {
			{ XDocletPreferenceStore.WEB_JBOSS, "JBoss", bundle.getString("desc_webdoclet_jboss"), "CHECK",
					"2.4,3.0,3.0.1,3.0.2,3.0.3,3.2,4.0", "2.4" },
			{ XDocletPreferenceStore.WEB_JONAS, "JOnAS", bundle.getString("desc_webdoclet_jonas"), "CHECK", "2.3,2.4,2.5,2.6,3.0",
					"2.6" },
			{ XDocletPreferenceStore.WEB_WEBLOGIC, "WebLogic", bundle.getString("desc_webdoclet_weblogic"), "CHECK",
					"6.0,6.1,7.0,8.1", "6.1" },
			{ XDocletPreferenceStore.WEB_WEBSPHERE, "WebSphere", bundle.getString("desc_webdoclet_websphere"), "CHECK", "all", "all" }

	};

	DialogPanel panel;

	public XDocletWebPreferencePage() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	protected Composite createContainer(Composite parent) {
		panel = new DialogPanel(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.makeColumnsEqualWidth = false;
		panel.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		panel.setLayoutData(gridData);
		return panel;
	}

	/*
	 * @see PreferencePage#createContents(Composite)
	 */
	protected Control createContents(Composite parent) {
		// noDefaultAndApplyButton();
		Composite composite = createContainer(parent);
		GridLayout gridLayout = new GridLayout();
		gridLayout.marginHeight = 0;
		gridLayout.marginWidth = 0;
		composite.setLayout(gridLayout);

		Composite defPanel = new Composite(composite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		defPanel.setLayout(layout);
		GridData gridData = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL);
		defPanel.setLayoutData(gridData);

		Label label = new Label(defPanel, SWT.WRAP);
		gridData = new GridData();
		gridData.horizontalSpan = 4;
		label.setLayoutData(gridData);
		label.setText(bundle.getString("label_set_webdoclet_preference"));

		panel.preferences = new Control[weboptions.length];
		panel.fActive = new Button[weboptions.length];

		for (int i = 0; i < weboptions.length; i++) {
			String versions[] = parseVersions(weboptions[i][4]);
			panel.preferences[i] = panel.createLabeledCombo(i, getStore().isPropertyActive(weboptions[i][0]), weboptions[i][1] + ":",
					weboptions[i][2], getStore().getProperty(weboptions[i][0] + "_VERSION"), versions, defPanel);
		}

		return composite;
	}

	/**
	 * @param string
	 * @return
	 */
	private String[] parseVersions(String string) {
		StringTokenizer tokenizer = new StringTokenizer(string, ",");
		int i = 0, count = tokenizer.countTokens();
		String[] versions = new String[count];
		while (tokenizer.hasMoreTokens()) {
			versions[i++] = tokenizer.nextToken();
		}
		return versions;
	}

	public boolean performOk() {
		for (int i = 0; i < weboptions.length; i++) {
			Combo combo = ((Combo) panel.preferences[i]);
			boolean itemActive = panel.fActive[i].getSelection();
			String itemValue = combo.getItem(combo.getSelectionIndex());
			getStore().setProperty(weboptions[i][0] + "_VERSION", itemValue);
			getStore().setPropertyActive(weboptions[i][0], itemActive);
		}
		getStore().save();
		return super.performOk();
	}

}