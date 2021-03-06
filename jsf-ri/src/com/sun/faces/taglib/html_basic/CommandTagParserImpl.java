/*
 * $Id: CommandTagParserImpl.java,v 1.11.32.1 2006/04/12 19:32:28 ofung Exp $
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

package com.sun.faces.taglib.html_basic;

import com.sun.faces.RIConstants;
import com.sun.faces.taglib.TagParser;
import com.sun.faces.taglib.ValidatorInfo;
import org.xml.sax.Attributes;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * <p> Parses the command tag attributes and verifies that the required
 * attributes are present</p>
 */
public class CommandTagParserImpl implements TagParser {

    //*********************************************************************
    // Validation and configuration state (protected)

    // PENDING(edburns): Make this localizable
    private StringBuffer failureMessages;	// failureMessages
    private boolean failed;
    private ValidatorInfo validatorInfo;

    //*********************************************************************
    // Constructor and lifecycle management

    /**
     * <p>CommandTagParserImpl constructor</p>
     */
    public CommandTagParserImpl() {
        failed = false;
        failureMessages = new StringBuffer();
    }


    /**
     * <p>Set the validator info object that has the current tag
     * information</p>
     *
     * @param ValidatorInfo object with current tag info
     */
    public void setValidatorInfo(ValidatorInfo validatorInfo) {
        this.validatorInfo = validatorInfo;
    }


    /**
     * <p>Get the failure message</p>
     *
     * @return String Failure message
     */
    public String getMessage() {
        return failureMessages.toString();
    }


    /**
     * <p>Return false if validator conditions have not been met</p>
     *
     * @return boolean false if validation conditions have not been met
     */
    public boolean hasFailed() {
        return failed;
    }


    /**
     * <p>Parse the starting element.  Parcel out to appropriate
     * handler method.</p>
     */
    public void parseStartElement() {
        String qn = validatorInfo.getQName();

        if (-1 != (qn.indexOf("commandButton"))) {
            handleCommandButton();
        }
    }


    /**
     * <p>Parse the end element</p>
     */
    public void parseEndElement() {
        //no parsing required
    }


    //*********************************************************************
    // Private methods

    /**
     * <p>set failed flag to true unless tag has a value attribute</p>.
     * <p/>
     * <p>PRECONDITION: qn is a commandButton</p>
     */
    private void handleCommandButton() {
        Attributes attrs = validatorInfo.getAttributes();
        String qn = validatorInfo.getQName();
        boolean hasValue = false;
        boolean hasImage = false;
	boolean hasBinding = false;

        for (int i = 0; i < attrs.getLength(); i++) {
            if (attrs.getQName(i).equals("value")) {
                hasValue = true;
            }
            if (attrs.getQName(i).equals("image")) {
                hasImage = true;
            }
            if (attrs.getQName(i).equals("binding")) {
                hasBinding = true;
            }
        }
        if (failed = (!hasBinding && !(hasValue || hasImage))) {
            Object[] obj = new Object[1];
            obj[0] = qn;
            ResourceBundle rb = ResourceBundle.getBundle(
                RIConstants.TLV_RESOURCE_LOCATION);
            failureMessages.append(
                MessageFormat.format(rb.getString("TLV_COMMAND_ERROR"), obj));
            failureMessages.append("\n");
        }

    }

}
