/*******************************************************************************
 * Copyright (c) 2000, 2004 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     QNX Software Systems - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.debug.mi.core.cdi.event;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.cdt.core.IAddress;
import org.eclipse.cdt.debug.core.cdi.event.ICDIMemoryChangedEvent;
import org.eclipse.cdt.debug.core.cdi.model.ICDIObject;
import org.eclipse.cdt.debug.mi.core.cdi.Session;
import org.eclipse.cdt.debug.mi.core.cdi.model.MemoryBlock;
import org.eclipse.cdt.debug.mi.core.event.MIMemoryChangedEvent;

/**
 */
public class MemoryChangedEvent implements ICDIMemoryChangedEvent {

	Session session;
	MemoryBlock source;
	MIMemoryChangedEvent miMem;

	public MemoryChangedEvent(Session s, MemoryBlock block, MIMemoryChangedEvent mem) {
		session = s;
		source = block;
		miMem = mem;
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.event.ICDIEvent#getAddresses()
	 */
	public IAddress[] getAddresses() {
	 	/* But only returns the address that are in the block.  */
		IAddress[] mi_addresses = miMem.getAddresses();
		List aList = new ArrayList(mi_addresses.length);
		for (int i = 0; i < mi_addresses.length; i++) {
			if (source.contains(mi_addresses[i])) {
				aList.add(mi_addresses[i]);
			}
		}
		return (IAddress[])aList.toArray(new IAddress[0]);
	}

	/**
	 * @see org.eclipse.cdt.debug.core.cdi.event.ICDIEvent#getSource()
	 */
	public ICDIObject getSource() {
		return source;
	}

}
