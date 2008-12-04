/********************************************************************************
 * Copyright (c) 2008 IBM Corporation. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is 
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Initial Contributors:
 * The following IBM employees contributed to the Remote System Explorer
 * component that contains this file: David McKnight.
 * 
 * Contributors:
 * {Name} (company) - description of contribution.
 ********************************************************************************/
package org.eclipse.rse.internal.subsystems.terminals.core;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.rse.core.model.IHost;
import org.eclipse.rse.core.subsystems.ISubSystem;
import org.eclipse.rse.internal.services.terminals.ITerminalService;
import org.eclipse.rse.internal.services.terminals.ITerminalShell;
import org.eclipse.rse.services.IService;
import org.eclipse.rse.services.clientserver.messages.SystemMessageException;

public class DelegatingTerminalService implements ITerminalService {

	private IHost _host;
	private ITerminalService _realService;

	public DelegatingTerminalService(IHost host) {
		_host = host;
	}

	private ITerminalService getRealService() {
		if (_host != null && _realService == null) {
			ISubSystem[] subSystems = _host.getSubSystems();
			if (subSystems != null) {
				for (int i = 0; i < subSystems.length && _realService == null; i++) {
					ISubSystem subsys = subSystems[i];

					IService svc = subsys.getSubSystemConfiguration()
							.getService(_host);
					if (svc != null) {
						ITerminalService tsvc = (ITerminalService) svc
								.getAdapter(ITerminalService.class);
						if (tsvc != null && tsvc != this) {
							_realService = tsvc;
						}
					}
				}
			}
		}

		return _realService;
	}

	public ITerminalShell launchTerminal(String ptyType, String encoding,
			String[] environment, String initialWorkingDirectory,
			String commandToRun, IProgressMonitor monitor)
			throws SystemMessageException {
		return getRealService().launchTerminal(ptyType, encoding, environment,
				initialWorkingDirectory, commandToRun, monitor);
	}

	public String getDescription() {
		return "Generic Terminal Service";
	}

	public String getName() {
		return "Terminal Service";
	}

	public void initService(IProgressMonitor monitor) {
		getRealService().initService(monitor);
	}

	public void uninitService(IProgressMonitor monitor) {
		getRealService().uninitService(monitor);
	}

	public Object getAdapter(Class adapter) {
		return getRealService().getAdapter(adapter);
	}

}
