package org.eclipse.cdt.make.internal.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;

import org.eclipse.core.resources.IProject;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.ui.IStartup;

import org.eclipse.cdt.make.ui.actions.UpdateMakeProjectAction;

public class MakeStartup implements IStartup {

    public void earlyStartup() {
        final IProject[] oldProject = UpdateMakeProjectAction.getOldProjects();
        if (oldProject.length > 0) {
            Display.getDefault().asyncExec(new Runnable() {
                public void run() {
                    Shell shell = MakeUIPlugin.getDefault().getShell();
                    boolean shouldUpdate = MessageDialog.openQuestion(shell,
                        MakeUIPlugin.getResourceString("MakeUIPlugin.update_project"), //$NON-NLS-1$
                        MakeUIPlugin.getResourceString("MakeUIPlugin.update_project_message")); //$NON-NLS-1$

                    if (shouldUpdate) {
                        ProgressMonitorDialog pd = new ProgressMonitorDialog(shell);
                        UpdateMakeProjectAction.run(false, pd, oldProject);
                    }
                }
            });
        }
    }

}
