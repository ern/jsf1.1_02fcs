/*
 * $Id: TagParser.java,v 1.4.44.1 2006/04/12 19:32:28 ofung Exp $
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

package com.sun.faces.taglib;

/**
 * <p>Interface defining the Validator Tag Parser implementation methods</p>
 */
public interface TagParser {

    /**
     * <p>Return the failure message for a failed validation</p>
     *
     * @return String Failure message
     */
    public String getMessage();


    /**
     * <p>Return false if validator conditions have not been met</p>
     *
     * @return boolean false if validation conditions have not been met
     */
    public boolean hasFailed();


    /**
     * <p>Set the Validator Info Bean</p>
     */
    public void setValidatorInfo(ValidatorInfo validatorInfo);


    /**
     * <p>Parse the starting element.  Parcel out to appropriate
     * handler method.</p>
     */
    public void parseStartElement();


    /**
     * <p>Parse the ending element.  Parcel out to appropriate
     * handler method.</p>
     */
    public void parseEndElement();
}
