/*
 * Copyright (c) 2006 Stiftung Deutsches Elektronen-Synchroton,
 * Member of the Helmholtz Association, (DESY), HAMBURG, GERMANY.
 *
 * THIS SOFTWARE IS PROVIDED UNDER THIS LICENSE ON AN "../AS IS" BASIS.
 * WITHOUT WARRANTY OF ANY KIND, EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR PARTICULAR PURPOSE AND
 * NON-INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE. SHOULD THE SOFTWARE PROVE DEFECTIVE
 * IN ANY RESPECT, THE USER ASSUMES THE COST OF ANY NECESSARY SERVICING, REPAIR OR
 * CORRECTION. THIS DISCLAIMER OF WARRANTY CONSTITUTES AN ESSENTIAL PART OF THIS LICENSE.
 * NO USE OF ANY SOFTWARE IS AUTHORIZED HEREUNDER EXCEPT UNDER THIS DISCLAIMER.
 * DESY HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS,
 * OR MODIFICATIONS.
 * THE FULL LICENSE SPECIFYING FOR THE SOFTWARE THE REDISTRIBUTION, MODIFICATION,
 * USAGE AND OTHER RIGHTS AND OBLIGATIONS IS INCLUDED WITH THE DISTRIBUTION OF THIS
 * PROJECT IN THE FILE LICENSE.HTML. IF THE LICENSE IS NOT INCLUDED YOU MAY FIND A COPY
 * AT HTTP://WWW.DESY.DE/LEGAL/LICENSE.HTM
 */

package org.csstudio.dal;


/**
 * <code>SimpleEnumProperty</code> provdes access to remote enumeration
 * type of values. Declares accessors and characteristics for enumeration. It supports two
 * <code>DataAccess</code> types. Long data acces is for monitoring enum as
 * long index, where each index means one enumerated state. Object data access
 * is for operating with the enumerated objects directly.
 *
 * @author Igor Kriznar (igor.kriznarATcosylab.com)
 *
 */
public interface EnumSimpleProperty extends NumericSimpleProperty<Long,Long>,
    EnumPropertyCharacteristics
{
    /**
     * Returns all allowed enumeration values.
     *
     * @return all enumerated values
     *
     * @throws DataExchangeException if remote layer is not operational
     */
    public Object[] getEnumValues() throws DataExchangeException;

    /**
     * Returns descriptions strings for enumerated values.
     *
     * @return descriptions for enumerated values
     *
     * @throws DataExchangeException if remote layer is not operational
     */
    public String[] getEnumDescriptions() throws DataExchangeException;

    /**
     * Returns index of enumerated value.
     *
     * @param enumerated enumerated values
     *
     * @return index
     */
    public long indexOf(Object enumerated);

    /**
     * Returns enumerated value for provided index.
     *
     * @param index the index of enumerated value
     *
     * @return enumerated value for the index
     */
    public Object valueOf(long index);
}

/* __oOo__ */
