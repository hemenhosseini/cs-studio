/*******************************************************************************
 * Copyright (c) 2014 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.vtype.pv.pva;

import org.epics.pvdata.pv.PVScalar;
import org.epics.pvdata.pv.PVStructure;
import org.diirt.vtype.VDouble;
import org.diirt.vtype.VType;
import org.diirt.vtype.VTypeToString;

/** Hold/decode data of {@link PVStructure} in {@link VType}
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
class VTypeForDouble extends VTypeTimeAlarmDisplayBase implements VDouble
{
    final private double value;

    public VTypeForDouble(final PVStructure struct)
    {
        super(struct);
        value = PVStructureHelper.convert.toDouble(struct.getSubField(PVScalar.class, "value"));
    }

    @Override
    public Double getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return VTypeToString.toString(this);
    }
}
