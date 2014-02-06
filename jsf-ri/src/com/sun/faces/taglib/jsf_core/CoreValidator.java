/*
 * $Id: CoreValidator.java,v 1.11.32.1 2006/04/12 19:32:29 ofung Exp $
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

package com.sun.faces.taglib.jsf_core;

import com.sun.faces.taglib.FacesValidator;
import com.sun.faces.taglib.ValidatorInfo;
import com.sun.faces.util.Util;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * <p>A TagLibrary Validator class to allow a TLD to mandate that
 * JSF tag must have an id if it is a child or sibling of a JSTL
 * conditional or iteration tag</p>
 *
 * @author Justyna Horwat
 */
public class CoreValidator extends FacesValidator {

    //*********************************************************************
    // Constants

    //*********************************************************************
    // Validation and configuration state (protected)

    private ValidatorInfo validatorInfo;
    private IdTagParserImpl idTagParser;

    //*********************************************************************
    // Constructor and lifecycle management

    /**
     * <p>CoreValidator constructor</p>
     */
    public CoreValidator() {
        super();
        init();
    }


    /**
     * <p>Initialize state</p>
     */
    protected void init() {
        super.init();
        failed = false;
        validatorInfo = new ValidatorInfo();

        idTagParser = new IdTagParserImpl();
        idTagParser.setValidatorInfo(validatorInfo);
    }


    /**
     * <p>Release and re-initialize state</p>
     */
    public void release() {
        super.release();
        init();
    }

    //
    // Superclass overrides.
    // 

    /**
     * <p>Get the validator handler</p>
     */
    protected DefaultHandler getSAXHandler() {
        DefaultHandler h = new CoreValidatorHandler();
        return h;
    }


    /**
     * <p>Create failure message from any failed validations</p>
     *
     * @param prefix Tag library prefix
     * @param uri    Tag library uri
     */
    protected String getFailureMessage(String prefix, String uri) {
        // we should only get called if this Validator failed
        Util.doAssert(failed);
        StringBuffer result = new StringBuffer();

        if (idTagParser.hasFailed()) {
            result.append(idTagParser.getMessage());
        }
        return result.toString();
    }


    //*********************************************************************
    // SAX handler

    /**
     * <p>The handler that provides the base of the TLV implementation.</p>
     */
    private class CoreValidatorHandler extends DefaultHandler {

        /**
         * Parse the starting element. If it is a specific JSTL tag
         * make sure that the nested JSF tags have IDs.
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
            validatorInfo.setValidator(CoreValidator.this);

            idTagParser.parseStartElement();

            if (idTagParser.hasFailed()) {
                failed = true;
            }
        }


        /**
         * <p>Parse the ending element. If it is a specific JSTL tag
         * make sure that the nested count is decreased.</p>
         *
         * @param ln Element local name.
         * @param qn Element QName.
         * @param a  Element's Attribute list.
         */
        public void endElement(String ns, String ln, String qn) {
            validatorInfo.setQName(qn);
            idTagParser.parseEndElement();
        }
    }
}
