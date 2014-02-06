/*
 * $Id: FormatValidator.java,v 1.1.38.1 2006/04/12 19:30:53 ofung Exp $
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

package carstore;

import javax.faces.application.FacesMessage;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;


/**
 * <p><strong>FormatValidator</strong> is a Validator that checks
 * the validity of String representation of the value of the
 * associated component against a list of specified patterns.</p>
 * <ul>
 * <li>Call getValue() to retrieve the current value of the component.
 * If it is <code>null</code>, exit immediately.  (If null values
 * should not be allowed, a RequiredValidator can be configured
 * to check for this case.)</li>
 * <li><code>formatPattern</code> is a <code>|</code> separated string
 * of allowed patterns. </li>
 * <li> This validator uses the following rules to match a value against a
 * pattern.
 * <li> if the matching pattern has a "A", then corresponding character
 * in input value should be a letter.
 * <li> if the matching pattern has a "9", then corresponding character
 * in input value should be a number.
 * <li> if the matching pattern has a "#", then corresponding character
 * in input value should be a number or  a letter.
 * <li> Any other character must match literally.
 * </ul> </ul>
 *
 * Validators have to be Serializable, so you can't maintain a reference to
 * a java.sql.Connection or javax.sql.DataSource inside this class in case
 * you need to hook upto the database or some other back end resource.
 * One approach would be to use JNDI-based data source lookups or do
 * this verification in the business tier.
 */

public class FormatValidator implements Validator, StateHolder {


    // ----------------------------------------------------- Manifest Constants

    /**
     * <p>The message identifier of the Message to be created if
     * the validation fails.  The message format string for this
     * message may optionally include a <code>{0}</code> placeholder, which
     * will be replaced by list of format patterns.</p>
     */
    public static final String FORMAT_INVALID_MESSAGE_ID =
        "carstore.Format_Invalid";

    private ArrayList formatPatternsList = null;


    //
    // Constructors and Initializers    
    //
    public FormatValidator() {
        super();
    }


    /**
     * <p>Construct a FormatValidator with the specified formatPatterns
     * String. </p>
     *
     * @param formatPatterns <code>|</code> separated String of format patterns
     *                       that this validator must match against.
     */
    public FormatValidator(String formatPatterns) {
        super();
        this.formatPatterns = formatPatterns;
        parseFormatPatterns();
    }

    // 
    // General Methods
    //
    /**
     * <code>|</code> separated String of format patterns
     * that this validator must match against.
     */
    private String formatPatterns = null;


    /**
     * <p>Return the format patterns that the validator supports.
     */
    public String getFormatPatterns() {

        return (this.formatPatterns);

    }


    /**
     * <p>Set the format patterns that the validator support..</p>
     *
     * @param formatPatterns <code>|</code> separated String of format patterns
     *                       that this validator must match against.
     */
    public void setFormatPatterns(String formatPatterns) {

        this.formatPatterns = formatPatterns;
        parseFormatPatterns();
    }


    /**
     * Parses the <code>formatPatterns</code> into validPatterns
     * <code>ArrayList</code>. The delimiter must be "|".
     */
    public void parseFormatPatterns() {
        if (formatPatterns == null || formatPatterns.length() == 0) {
            return;
        }
        if (formatPatternsList != null) {
            // formatPatterns have been parsed already.
            return;
        } else {
            formatPatternsList = new ArrayList();
        }
        StringTokenizer st = new StringTokenizer(formatPatterns, "|");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            formatPatternsList.add(token);
        }
    }


    //
    // Methods from Validator
    //
    public void validate(FacesContext context, UIComponent component,
                         Object toValidate) {
        boolean valid = false;
        String value = null;
        if ((context == null) || (component == null)) {
            throw new NullPointerException();
        }
        if (!(component instanceof UIOutput)) {
            return;
        }

        if (null == formatPatternsList || null == toValidate) {
            return;
        }

        value = toValidate.toString();
        // validate the value against the list of valid patterns.
        Iterator patternIt = formatPatternsList.iterator();
        while (patternIt.hasNext()) {
            valid = isFormatValid(((String) patternIt.next()), value);
            if (valid) {
                break;
            }
        }
        if (!valid) {
            FacesMessage errMsg = MessageFactory.getMessage(context,
                                                            FORMAT_INVALID_MESSAGE_ID,
                                                            (new Object[]{
                                                                formatPatterns
                                                            }));
            throw new ValidatorException(errMsg);
        }
    }


    /**
     * Returns true if the value matches one of the valid patterns.
     */
    protected boolean isFormatValid(String pattern, String value) {
        boolean valid = true;
        // if there is no pattern to match then value is valid
        if (pattern == null || pattern.length() == 0) {
            return true;
        }
        // if the value is null or a zero length string return false.
        if (value == null || value.length() == 0) {
            return false;
        }
        // if the length of the value is not equal to the length of the
        // pattern string then the value is not valid.
        if (value.length() != pattern.length()) {
            return false;
        }
        value = value.trim();
        // rules for matching. 
        // 1. if the matching pattern has a "A", then corresponding character
        // in the value should a letter.
        // 2. if the matching pattern has a "9", then corresponding character
        // in the value should a number
        // 3. if the matching pattern has a "#", then corresponding character
        // in the value should a number or a letter
        // 4.. any other character must match literally.
        char[] input = value.toCharArray();
        char[] fmtpattern = pattern.toCharArray();
        for (int i = 0; i < fmtpattern.length; ++i) {
            if (fmtpattern[i] == 'A') {
                if (!(Character.isLetter(input[i]))) {
                    valid = false;
                }
            } else if (fmtpattern[i] == '9') {
                if (!(Character.isDigit(input[i]))) {
                    valid = false;
                }
            } else if (fmtpattern[i] == '#') {
                if ((!(Character.isDigit(input[i]))) &&
                    (!(Character.isLetter(input[i])))) {
                    valid = false;
                }
            } else {
                if (!(fmtpattern[i] == input[i])) {
                    valid = false;
                }
            }
        }
        return valid;

    }


    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = formatPatterns;
        values[1] = formatPatternsList;
        return (values);
    }


    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        formatPatterns = (String) values[0];
        formatPatternsList = (ArrayList) values[1];
    }


    private boolean transientValue = false;


    public boolean isTransient() {
        return (this.transientValue);
    }


    public void setTransient(boolean transientValue) {
        this.transientValue = transientValue;
    }
}
