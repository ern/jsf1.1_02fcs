/*
 * $Id: HtmlBasicValidator.java,v 1.10.16.1.2.1 2006/04/12 19:32:28 ofung Exp $
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

import com.sun.faces.taglib.FacesValidator;
import com.sun.faces.taglib.ValidatorInfo;
import com.sun.faces.util.Util;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import com.sun.faces.RIConstants;


/**
 * <p>Top level validator for the html_basic tld</p>
 *
 * @author Justyna Horwat
 * @author Ed Burns
 */
public class HtmlBasicValidator extends FacesValidator {

    //*********************************************************************
    // Validation and configuration state (protected)
    private ValidatorInfo validatorInfo;
    private CommandTagParserImpl commandTagParser;


    //*********************************************************************
    // Constructor and lifecycle management

    public HtmlBasicValidator() {
        super();
        init();
    }


    protected void init() {
        super.init();
        failed = false;
        validatorInfo = new ValidatorInfo();

        commandTagParser = new CommandTagParserImpl();
        commandTagParser.setValidatorInfo(validatorInfo);
    }


    public void release() {
        super.release();
        init();
    }


    protected DefaultHandler getSAXHandler() {
	// don't run the TLV if we're in designTime, or the RIConstants
	// says not to.
	
	if (java.beans.Beans.isDesignTime() || 
	    !RIConstants.HTML_TLV_ACTIVE) {
	    return null;
	}
	
        DefaultHandler h = new HtmlBasicValidatorHandler();
        return h;
    }


    protected String getFailureMessage(String prefix, String uri) {       

        StringBuffer result = new StringBuffer();
        if (commandTagParser.hasFailed()) {
            result.append(commandTagParser.getMessage());
        }
        return result.toString();
    }
	    
    //*********************************************************************
    // SAX handler

    /**
     * The handler that provides the base of the TLV implementation.
     */
    private class HtmlBasicValidatorHandler extends DefaultHandler {

        /**
         * Parse the starting element.  Parcel out to appropriate
         * handler method.
         *
         * @param ns Element name space.
         * @param ln Element local name.
         * @param qn Element QName.
         * @param a  Element's Attribute list.
         */
        public void startElement(String ns,
                                 String ln,
                                 String qn,
                                 Attributes attrs) {
            maybeSnagTLPrefixes(qn, attrs);
            validatorInfo.setQName(qn);
            validatorInfo.setAttributes(attrs);

            commandTagParser.parseStartElement();
            if (commandTagParser.hasFailed()) {
                failed = true;
            }
        }


        /**
         * Parse the ending element. If it is a specific JSTL tag
         * make sure that the nested count is decreased.
         *
         * @param ln Element local name.
         * @param qn Element QName.
         * @param a  Element's Attribute list.
         */
        public void endElement(String ns, String ln, String qn) {
        }
    }
}
