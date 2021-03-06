/*
 * $Id: LongRangeValidator.java,v 1.37.28.1 2006/04/12 19:30:26 ofung Exp $
 */

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt. 
 * See the License for the specific language governing
 * permission and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.    
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * [Name of File] [ver.__] [Date]
 * 
 * Copyright 2006 Sun Microsystems Inc. All Rights Reserved
 */

package javax.faces.validator;


import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


/**
 * <p><strong>LongRangeValidator</strong> is a {@link Validator} that checks
 * the value of the corresponding component against specified minimum and
 * maximum values.  The following algorithm is implemented:</p>
 * <ul>
 * <li>If the passed value is <code>null</code>, exit immediately.</li>
 * <li>If the current component value is not a floating point type, or
 *     a String that is convertible to long,
 *      throw a {@link ValidatorException} containing a
 *     TYPE_MESSAGE_ID message.</li>
 * <li>If both a <code>maximum</code> and <code>minimum</code> property
 *     has been configured on this {@link Validator}, check the component
 *     value against both limits.  If the component value is not within
 *     this specified range, throw a {@link ValidatorException} containing a
 *     {@link Validator#NOT_IN_RANGE_MESSAGE_ID} message.</li>
 * <li>If a <code>maximum</code> property has been configured on this
 *     {@link Validator}, check the component value against
 *     this limit.  If the component value is greater than the
 *     specified maximum, throw a {@link ValidatorException} containing a
 *     MAXIMUM_MESSAGE_ID message.</li>
 * <li>If a <code>minimum</code> property has been configured on this
 *     {@link Validator}, check the component value against
 *     this limit.  If the component value is less than the
 *     specified minimum, throw a {@link ValidatorException} containing a
 *     MINIMUM_MESSAGE_ID message.</li>
 * </ul>
 * 
 * <p>For all of the above cases that cause a {@link ValidatorException}
 * to be thrown, if there are parameters to the message that match up
 * with validator parameters, the values of these parameters must be
 * converted using the {@link Converter} registered in the application
 * under the converter id <code>javax.faces.Number</code>.  This allows
 * the values to be localized according to the current
 * <code>Locale</code>.</p>
 */

public class LongRangeValidator implements Validator, StateHolder {


    // ------------------------------------------------------ Manifest Constants


    /**
     * <p>The standard converter id for this converter.</p>
     */
    public static final String VALIDATOR_ID = "javax.faces.LongRange";


    /**
     * <p>The message identifier of the {@link FacesMessage} to be created if
     * the maximum value check fails.  The message format string for this
     * message may optionally include a <code>{0}</code> placeholder, which
     * will be replaced by the configured maximum value.</p>
     */
    public static final String MAXIMUM_MESSAGE_ID =
        "javax.faces.validator.LongRangeValidator.MAXIMUM";


    /**
     * <p>The message identifier of the {@link FacesMessage} to be created if
     * the minimum value check fails.  The message format string for this
     * message may optionally include a <code>{0}</code> placeholder, which
     * will be replaced by the configured minimum value.</p>
     */
    public static final String MINIMUM_MESSAGE_ID =
        "javax.faces.validator.LongRangeValidator.MINIMUM";


    /**
     * <p>The message identifier of the {@link FacesMessage} to be created if
     * the current value of this component is not of the correct type.
     */
    public static final String TYPE_MESSAGE_ID =
        "javax.faces.validator.LongRangeValidator.TYPE";


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Construct a {@link Validator} with no preconfigured limits.</p>
     */
    public LongRangeValidator() {

        super();

    }


    /**
     * <p>Construct a {@link Validator} with the specified preconfigured
     * limit.</p>
     *
     * @param maximum Maximum value to allow
     */
    public LongRangeValidator(long maximum) {

        super();
        setMaximum(maximum);

    }


    /**
     * <p>Construct a {@link Validator} with the specified preconfigured
     * limits.</p>
     *
     * @param maximum Maximum value to allow
     * @param minimum Minimum value to allow
     *
     */
    public LongRangeValidator(long maximum, long minimum) {

        super();
        setMaximum(maximum);
        setMinimum(minimum);

    }


    // -------------------------------------------------------------- Properties


    private long maximum = 0;
    private boolean maximumSet = false;


    /**
     * <p>Return the maximum value to be enforced by this {@link Validator}.</p>
     */
    public long getMaximum() {

        return (this.maximum);

    }


    /**
     * <p>Set the maximum value to be enforced by this {@link Validator}.</p>
     *
     * @param maximum The new maximum value
     *
     */
    public void setMaximum(long maximum) {

        this.maximum = maximum;
        this.maximumSet = true;

    }


    private long minimum = 0;
    private boolean minimumSet = false;


    /**
     * <p>Return the minimum value to be enforced by this {@link Validator}.</p>
     */
    public long getMinimum() {

        return (this.minimum);

    }


    /**
     * <p>Set the minimum value to be enforced by this {@link Validator}.</p>
     *
     * @param minimum The new minimum value
     *
     */
    public void setMinimum(long minimum) {

        this.minimum = minimum;
        this.minimumSet = true;

    }


    // ------------------------------------------------------- Validator Methods

    /**
     * @exception NullPointerException {@inheritDoc}     
     * @exception ValidatorException {@inheritDoc}     
     */ 
    public void validate(FacesContext context,
                         UIComponent  component,
                         Object       value) throws ValidatorException {

        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        if (value != null) {
            try {
                long converted = longValue(value);
                if (maximumSet &&
                    (converted > maximum)) {
		    if (minimumSet) {
                        throw new ValidatorException(MessageFactory.getMessage
					   (context,
					    Validator.NOT_IN_RANGE_MESSAGE_ID,
					    new Object[] {
					    stringValue(component,
							new Long(minimum)),
					    stringValue(component,
							new Long(maximum)) }));
			
		    }
		    else {
                        throw new ValidatorException(MessageFactory.getMessage
					   (context,
					    MAXIMUM_MESSAGE_ID,
					    new Object[] {
					    stringValue(component,
							new Long(maximum)) }));
		    }
                }
                if (minimumSet &&
                    (converted < minimum)) {
		    if (maximumSet) {
                        throw new ValidatorException(MessageFactory.getMessage
					   (context,
					    Validator.NOT_IN_RANGE_MESSAGE_ID,
					    new Object[] {
				            stringValue(component,
							new Long(minimum)),
					    stringValue(component,
							new Long(maximum)) }));
			
		    }
		    else {
                        throw new ValidatorException(MessageFactory.getMessage
					   (context,
					    MINIMUM_MESSAGE_ID,
					    new Object[] {
					    stringValue(component,
							new Long(minimum)) }));
		    }
                }
            } catch (NumberFormatException e) {
                throw new ValidatorException(MessageFactory.getMessage
                        (context, TYPE_MESSAGE_ID ));
            }
        }

    }


    public boolean equals(Object otherObj) {

	if (!(otherObj instanceof LongRangeValidator)) {
	    return false;
	}
	LongRangeValidator other = (LongRangeValidator) otherObj;
	return ((maximum == other.maximum) &&
                (minimum == other.minimum) &&
		(maximumSet == other.maximumSet) &&
                (minimumSet == other.minimumSet));

    }


    // --------------------------------------------------------- Private Methods


    /**
     * <p>Return the specified attribute value, converted to a
     * <code>long</code>.</p>
     *
     * @param attributeValue The attribute value to be converted
     *
     * @exception NumberFormatException if conversion is not possible
     */
    private long longValue(Object attributeValue)
        throws NumberFormatException {

        if (attributeValue instanceof Number) {
            return ( ((Number) attributeValue).longValue() );
        } else {
            return (Long.parseLong(attributeValue.toString()));
        }

    }

    private String stringValue(UIComponent component, Long toConvert) {
	String result = null;
	Converter converter = null;
	FacesContext context = FacesContext.getCurrentInstance();

	converter = (Converter)
	    context.getApplication().createConverter("javax.faces.Number");
	result = converter.getAsString(context, component, toConvert);
	return result;
    }




    // ----------------------------------------------------- StateHolder Methods
    

    public Object saveState(FacesContext context) {

        Object values[] = new Object[4];
        values[0] = new Long(maximum);
        values[1] = maximumSet ? Boolean.TRUE : Boolean.FALSE;
        values[2] = new Long(minimum);
        values[3] = minimumSet ? Boolean.TRUE : Boolean.FALSE;
        return (values);

    }


    public void restoreState(FacesContext context, Object state) {

        Object values[] = (Object[]) state;
        maximum = ((Long) values[0]).longValue();
        maximumSet = ((Boolean) values[1]).booleanValue();
        minimum = ((Long) values[2]).longValue();
        minimumSet = ((Boolean) values[3]).booleanValue();

    }


    private boolean transientValue = false;


    public boolean isTransient() {

        return (this.transientValue);

    }


    public void setTransient(boolean transientValue) {

        this.transientValue = transientValue;

    }

}
