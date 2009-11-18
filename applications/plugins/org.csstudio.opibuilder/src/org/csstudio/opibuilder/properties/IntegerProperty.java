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

package org.csstudio.opibuilder.properties;

import org.csstudio.opibuilder.properties.support.IntegerPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.jdom.Element;

/**The integer property.
 * @author Xihui Chen, Sven Wende (part of the code is copied from SDS)
 *
 */
public class IntegerProperty extends AbstractWidgetProperty {

	/**
	 * Lower border for the property value.
	 */
	private int min;

	/**
	 * Upper border for the property value.
	 */
	private int max;
	
	public IntegerProperty(final String prop_id, final String description,
			final WidgetPropertyCategory category, final int defaultValue) {
		super(prop_id, description, category, Integer.valueOf(defaultValue));
		min = Integer.MIN_VALUE;
		max = Integer.MAX_VALUE;
	}
	
	public IntegerProperty(final String prop_id, final String description,
			final WidgetPropertyCategory category, final int defaultValue,
			final int minValue, final int maxValue) {
		super(prop_id, description, category, Integer.valueOf(defaultValue));
		assert minValue < maxValue;
		min = minValue;
		max = maxValue;
	}
	

	@Override
	public Object checkValue(final Object value) {
		if(value == null)
			return null;

		Integer acceptedValue = null;

		// check type
		if (!(value instanceof Integer)) {
			if (value instanceof Number) {
				acceptedValue = ((Number) value).intValue();
			} else {
				try {
					acceptedValue = Integer.parseInt(value.toString());
				} catch (NumberFormatException e) {
					acceptedValue = null;
				}
			}
		} else {
			acceptedValue = (Integer) value;
		}

		// check borders
		if (acceptedValue != null) {
			if (acceptedValue > max) {
				acceptedValue = max;
			} else if (acceptedValue < min) {
				acceptedValue = min;
			}
		}

		return acceptedValue;
	}	

	@Override
	protected PropertyDescriptor createPropertyDescriptor() {
		return new IntegerPropertyDescriptor(prop_id, description);
	}

	@Override
	public void writeToXML(Element propElement) {
		propElement.setText(getPropertyValue().toString());
	}

	@Override
	public Object readValueFromXML(Element propElement) {
		return Integer.parseInt(propElement.getValue());
	}



}
