/*
 * $Id: BaseComponentBodyTag.java,v 1.6.38.1 2006/04/12 19:32:27 ofung Exp $
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

// BaseComponentTag.java

package com.sun.faces.taglib;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * <B>BaseComponentTag</B> is a base class for most tags in the Faces Tag
 * library.  Its primary purpose is to centralize common tag functions
 * to a single base class. <P>
 */

public abstract class BaseComponentBodyTag extends BaseComponentTag
    implements BodyTag {

    //
    // Protected Constants
    //

    // Log instance for this class
    protected static Log log = LogFactory.getLog(BaseComponentBodyTag.class);

    //
    // Class Variables
    //

    //
    // Instance Variables
    //

    // Relationship Instance Variables

    /**
     * <p>The <code>bodyContent</code> for this tag handler.</p>
     */
    protected BodyContent bodyContent = null;


    //
    // Constructors and Initializers    
    //

    public BaseComponentBodyTag() {
        super();
    }

    //
    // Class methods
    //

    // 
    // Accessors
    //

    // 
    // Methods from BodyTag
    //

    public void doInitBody() throws JspException {

        ; // Default implementation does nothing

    }


    public void setBodyContent(BodyContent bodyContent) {
        this.bodyContent = bodyContent;
    }


    public BodyContent getBodyContent() {
        return (this.bodyContent);
    }


    public JspWriter getPreviousOut() {
        return (this.bodyContent.getEnclosingWriter());
    }


    //
    // Methods from Tag
    // 

    public void release() {
        bodyContent = null;
        super.release();
    }

    // 
    // methods from UIComponentTag
    // 

    protected int getDoStartValue() throws JspException {
        return (BodyTag.EVAL_BODY_BUFFERED);
    }


    public int doAfterBody() throws JspException {
        return (getDoAfterBodyValue());
    }


    protected int getDoEndValue() throws JspException {
        return (EVAL_PAGE);
    }


    protected int getDoAfterBodyValue() throws JspException {

        return (SKIP_BODY);

    }


} // end of class BaseComponentBodyTag
