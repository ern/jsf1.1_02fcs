/*
 * $Id: OutputVerbatimTag.java,v 1.6.44.1 2006/04/12 19:32:40 ofung Exp $
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

package com.sun.faces.systest;


import javax.faces.component.UIOutput;
import javax.faces.webapp.UIComponentBodyTag;
import javax.servlet.jsp.JspException;


/**
 * <p><code>UIComponentBodyTag</code> for <code>Verbatim</code> rendering.
 * In other words, the body content of this tag is captured and then
 * rendered as the value of this component.</p>
 */

public class OutputVerbatimTag extends UIComponentBodyTag {


    // -------------------------------------------------------------- Attributes


    // ---------------------------------------------------------- Public Methods


    public String getComponentType() {
        return ("Output");
    }


    public String getRendererType() {
        return ("Text");
    }


    // Assign the trimmed body content of this tag as the value of the
    // current component.
    public int doAfterBody() throws JspException {

        // Save the trimmed body content of this tag (if any)
        if (getBodyContent() != null) {
            String value = getBodyContent().getString().trim();
            if (value != null) {
                ((UIOutput) getComponentInstance()).setValue(value);
            }
        }

        // Perform normal superclass processing
        return (super.doAfterBody());

    }


    // ------------------------------------------------------- Protected Methods


}
