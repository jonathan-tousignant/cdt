/*******************************************************************************
 * Copyright (c) 2008 ARM Limited and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * ARM Limited - Initial API and implementation
 *******************************************************************************/

package org.eclipse.cdt.debug.internal.ui.disassembly.editor;

import org.eclipse.cdt.debug.internal.ui.preferences.ICDebugPreferenceConstants;
import org.eclipse.cdt.debug.ui.CDebugUIPlugin;
import org.eclipse.cdt.debug.ui.ICDebugUIConstants;
import org.eclipse.cdt.debug.ui.disassembly.IDocumentPresentation;
import org.eclipse.debug.internal.ui.viewers.model.provisional.PresentationContext;

/**
 * org.eclipse.cdt.debug.internal.ui.disassembly.viewer.DisassemblyEditorPresentation: 
 * //TODO Add description.
 */
public class DisassemblyEditorPresentation extends PresentationContext implements IDocumentPresentation {

    private boolean fShowInstructions = true;
    private boolean fShowSource = false;

    public DisassemblyEditorPresentation() {
        super( ICDebugUIConstants.ID_DEFAULT_DISASSEMBLY_EDITOR );
        fShowInstructions = CDebugUIPlugin.getDefault().getPreferenceStore().getBoolean( ICDebugPreferenceConstants.PREF_DISASM_SHOW_INSTRUCTIONS );
        fShowSource = CDebugUIPlugin.getDefault().getPreferenceStore().getBoolean( ICDebugPreferenceConstants.PREF_DISASM_SHOW_SOURCE );
    }

    public boolean showIntstructions() {
        return fShowInstructions;
    }

    public void setShowIntstructions( boolean showIntstructions ) {
        fShowInstructions = showIntstructions;
    }

    public boolean showSource() {
        return fShowSource;
    }

    public void setShowSource( boolean showSource ) {
        fShowSource = showSource;
    }
}
