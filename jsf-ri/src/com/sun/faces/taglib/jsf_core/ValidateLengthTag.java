/*
 * $Id: ValidateLengthTag.java,v 1.10.32.1 2006/04/12 19:32:31 ofung Exp $
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

// ValidateLengthTag.java

package com.sun.faces.taglib.jsf_core;

import com.sun.faces.util.Util;

import javax.faces.validator.LengthValidator;
import javax.faces.validator.Validator;
import javax.servlet.jsp.JspException;

/**
 * ValidateLengthTag is the tag handler class for
 * <code>validate_length</code> tag
 */

public class ValidateLengthTag extends MaxMinValidatorTag {

//
// Protected Constants
//

//
// Class Variables
//

//
// Instance Variables
//

// Attribute Instance Variables
    protected String maximum_ = null;
    protected int maximum = 0;
    protected String minimum_ = null;
    protected int minimum = 0;


// Relationship Instance Variables

//
// Constructors and Initializers    
//

    public ValidateLengthTag() {
        super();
    }

//
// Class methods
//

//
// General Methods
//

    public void setMaximum(String newMaximum) {
        maximumSet = true;
        maximum_ = newMaximum;
    }


    public void setMinimum(String newMinimum) {
        minimumSet = true;
        minimum_ = newMinimum;
    }

    public int doStartTag() throws JspException {
        super.setValidatorId("javax.faces.Length");
	return super.doStartTag();
    }


// 
// Methods from ValidatorTag
//

    protected Validator createValidator() throws JspException {
        LengthValidator result = null;

        result = (LengthValidator) super.createValidator();
        Util.doAssert(null != result);

        evaluateExpressions();
        if (maximumSet) {
            result.setMaximum(maximum);
        }

        if (minimumSet) {
            result.setMinimum(minimum);
        }

        return result;
    }

/* Evaluates expressions as necessary */
    private void evaluateExpressions() throws JspException {

        if (minimum_ != null) {
            if (Util.isVBExpression(minimum_)) {
                Number numberObj = (Number) Util.evaluateVBExpression(minimum_);
                Util.doAssert(null != numberObj);
                minimum = numberObj.intValue();
            } else {
                minimum = new Integer(minimum_).intValue();
            }
        }
        if (maximum_ != null) {
            if (Util.isVBExpression(maximum_)) {
                Number numberObj = (Number) Util.evaluateVBExpression(maximum_);
                Util.doAssert(null != numberObj);
                maximum = numberObj.intValue();
            } else {
                maximum = new Integer(maximum_).intValue();
            }
        }
    }

} // end of class ValidateLengthTag
