/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.alarm.beast.msghist.model;

import java.util.HashMap;

import org.csstudio.platform.model.IProcessVariable;

/** A log message that provides IProcessVariable via the NAME property.
 *  @author Kay Kasemir
 */
public class PVMessage extends Message implements IProcessVariable
{    
    public PVMessage(final int sequence, final int id, final HashMap<String, String> properties)
    {
    	super(sequence, id, properties);
    }

    /** @see IProcessVariable */
	public String getTypeId()
	{
		return IProcessVariable.TYPE_ID;
	}

    /** @return "NAME" property
     *  @see IProcessVariable
     */
	public String getName()
	{
		return getProperty(Message.NAME);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public Object getAdapter(Class adapter)
	{
		return null;
	}    
}