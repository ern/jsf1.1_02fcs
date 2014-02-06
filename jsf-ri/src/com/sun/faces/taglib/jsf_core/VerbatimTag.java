/*
 * $Id: VerbatimTag.java,v 1.9.32.1 2006/04/12 19:32:32 ofung Exp $
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

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentBodyTag;
import javax.servlet.jsp.JspException;

/**
 * <p>Tag implementation that creates a {@link UIOutput} instance
 * and allows the user to write raw markup.</p>
 */

public class VerbatimTag extends UIComponentBodyTag {


    // ------------------------------------------------------------- Attributes


    private String escape = null;


    public void setEscape(String escape) {
        this.escape = escape;
    }


    // --------------------------------------------------------- Public Methods


    public String getRendererType() {
        return "javax.faces.Text";
    }


    public String getComponentType() {
        return "javax.faces.Output";
    }


    protected void setProperties(UIComponent component) {

        super.setProperties(component);
        if (null != escape) {
            if (isValueReference(escape)) {
                ValueBinding vb =
                    FacesContext.getCurrentInstance().getApplication().
                    createValueBinding(escape);
                component.setValueBinding("escape", vb);
            } else {
                boolean _escape = new Boolean(escape).booleanValue();
                component.getAttributes().put
                    ("escape", _escape ? Boolean.TRUE : Boolean.FALSE);
            }
        } else {
            component.getAttributes().put("escape", Boolean.FALSE);
        }
        component.setTransient(true);

    }


    /**
     * <p>Set the local value of this component to reflect the nested
     * body content of this JSP tag.</p>
     */
    public int doAfterBody() throws JspException {

        if (getBodyContent() != null) {
            String value = getBodyContent().getString().trim();
            if (value != null) {
                UIOutput output = (UIOutput) getComponentInstance();
                output.setValue(value);
            }
        }
        return (getDoAfterBodyValue());

    }


}
