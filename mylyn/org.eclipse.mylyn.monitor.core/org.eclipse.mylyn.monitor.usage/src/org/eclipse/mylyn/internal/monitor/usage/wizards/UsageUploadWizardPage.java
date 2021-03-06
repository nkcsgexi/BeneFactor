/*******************************************************************************
 * Copyright (c) 2004, 2009 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.internal.monitor.usage.wizards;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.mylyn.internal.monitor.usage.StudyParameters;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Page to upload the file to the server
 * 
 * @author Shawn Minto
 * @author Mik Kersten
 */
public class UsageUploadWizardPage extends WizardPage {

	// private static final int MAX_NUM_LINES = 1000;

	/** A text box to hold the address of the server */
	private Text serverAddrText;

	/** A text box to hold the location of the usage statistics file */
	private Text usageFileText;

	// /** A text box to hold the location of the log file */
	// private Text logFileText;

	/** A text file to show the id of the user */
	private Text idText;

	private final UsageSubmissionWizard wizard;

	private final StudyParameters studyParameters;

	/**
	 * Constructor
	 */
	public UsageUploadWizardPage(UsageSubmissionWizard wizard, StudyParameters studyParameters) {
		super(Messages.UsageUploadWizardPage_Usage_Data_Submission_Wizard);
		this.studyParameters = studyParameters;

		setTitle(Messages.UsageUploadWizardPage_Usage_Data_Submission);
		if (studyParameters.getCustomizingPlugin() != null) {
			String customizedTitle = studyParameters.getTitle();
			if (customizedTitle != null && !customizedTitle.equals("")) { //$NON-NLS-1$
				setTitle(NLS.bind(Messages.UsageUploadWizardPage_X_Usage_Data_Upload, customizedTitle));
			}
		}

		setDescription(Messages.UsageUploadWizardPage_Usage_File_Below_Uploaded);
		// setDescription(
		// "The files listed below will be uploaded. Information about program
		// elements that you "
		// + "worked with is obfuscated to ensure privacy.");
		this.wizard = wizard;
	}

	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 1;

		Composite topContainer = new Composite(container, SWT.NULL);
		GridLayout topContainerLayout = new GridLayout();
		topContainer.setLayout(topContainerLayout);
		topContainerLayout.numColumns = 2;
		topContainerLayout.verticalSpacing = 9;

		Label label;
		if (studyParameters.getCustomizingPlugin() != null) {
			label = new Label(parent, SWT.NULL);
			label.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT));
			label.setText(studyParameters.getCustomizedByMessage());
		}

		label = new Label(topContainer, SWT.NULL);
		label.setText(Messages.UsageUploadWizardPage_Upload_Url);

		serverAddrText = new Text(topContainer, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		serverAddrText.setLayoutData(gd);
		serverAddrText.setEditable(false);
		serverAddrText.setText(studyParameters.getUploadServletUrl());

		label = new Label(topContainer, SWT.NULL);
		label.setText(Messages.UsageUploadWizardPage_Usage_File_Location);

		usageFileText = new Text(topContainer, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		usageFileText.setLayoutData(gd);
		usageFileText.setEditable(false);

		usageFileText.setText(wizard.getMonitorFileName());

		Composite bottomContainer = new Composite(container, SWT.NULL);
		GridLayout bottomContainerLayout = new GridLayout();
		bottomContainer.setLayout(bottomContainerLayout);
		bottomContainerLayout.numColumns = 2;

		Label submissionLabel = new Label(bottomContainer, SWT.NONE);
		submissionLabel.setText(studyParameters.getFilteredIdSubmissionText());

		setControl(container);
	}

	@Override
	public IWizardPage getNextPage() {
		return null;
	}

	public void updateUid() {
		if (idText != null && !idText.isDisposed()) {
			idText.setText(wizard.getUid() + ""); //$NON-NLS-1$
		}
	}
}
