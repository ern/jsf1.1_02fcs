/*
 * $Id: COPYRIGHT,v 1.2 2004/01/27 20:13:27 eburns Exp $
 */

/*
 * Copyright 2003-2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.faces.taglib.html_basic;


import com.sun.faces.util.Util;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.convert.Converter;
import javax.faces.el.ValueBinding;
import javax.faces.el.MethodBinding;
import javax.faces.webapp.UIComponentTag;
import javax.faces.webapp.UIComponentBodyTag;
import javax.servlet.jsp.JspException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class OutputFormatTag extends UIComponentTag {


    public static Log log = LogFactory.getLog(OutputFormatTag.class);

    //
    // Instance Variables
    //

    private java.lang.String converter;
    private java.lang.String value;

    private java.lang.String escape;
    private java.lang.String style;
    private java.lang.String styleClass;
    private java.lang.String title;

    //
    // Setter Methods
    //

    public void setConverter(java.lang.String converter) {
        this.converter = converter;
    }

    public void setValue(java.lang.String value) {
        this.value = value;
    }

    public void setEscape(java.lang.String escape) {
        this.escape = escape;
    }

    public void setStyle(java.lang.String style) {
        this.style = style;
    }

    public void setStyleClass(java.lang.String styleClass) {
        this.styleClass = styleClass;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    //
    // General Methods
    //

    public String getRendererType() { return "javax.faces.Format"; }
    public String getComponentType() { return "javax.faces.HtmlOutputFormat"; }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIOutput output = null;
        try {
            output = (UIOutput)component;
        }
        catch (ClassCastException cce) {
          throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: UIOutput.  Perhaps you're missing a tag?");
        }

        if (converter != null) {
            if (isValueReference(converter)) {
                      ValueBinding vb = 
                          Util.getValueBinding(converter);
                  output.setValueBinding("converter", vb);
            } else {
                Converter _converter = FacesContext.getCurrentInstance().
                    getApplication().createConverter(converter);
                output.setConverter(_converter);
            }
        }

        if (value != null) {
            if (isValueReference(value)) {
                ValueBinding vb = Util.getValueBinding(value);
                output.setValueBinding("value", vb);
            } else {
                output.setValue(value);
            }
        }
        if (escape != null) {
            if (isValueReference(escape)) {
                ValueBinding vb = Util.getValueBinding(escape);
                output.setValueBinding("escape", vb);
            } else {
                boolean _escape = new Boolean(escape).booleanValue();
                output.getAttributes().put("escape", _escape ? Boolean.TRUE : Boolean.FALSE);
            }
        }
        if (style != null) {
            if (isValueReference(style)) {
                ValueBinding vb = Util.getValueBinding(style);
                output.setValueBinding("style", vb);
            } else {
                output.getAttributes().put("style", style);
            }
        }
        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = Util.getValueBinding(styleClass);
                output.setValueBinding("styleClass", vb);
            } else {
                output.getAttributes().put("styleClass", styleClass);
            }
        }
        if (title != null) {
            if (isValueReference(title)) {
                ValueBinding vb = Util.getValueBinding(title);
                output.setValueBinding("title", vb);
            } else {
                output.getAttributes().put("title", title);
            }
        }
    }

    //
    // Methods From TagSupport
    //

    public int doStartTag() throws JspException {
        int rc = 0;
        try {
            rc = super.doStartTag();
        } catch (JspException e) {
            if (log.isDebugEnabled()) {
                log.debug(getDebugString(), e);
            }
            throw e;
        } catch (Throwable t) {
            if (log.isDebugEnabled()) {
                log.debug(getDebugString(), t);
            }
            throw new JspException(t);
        }
        return rc;
    }

    public int doEndTag() throws JspException {
        int rc = 0;
        try {
            rc = super.doEndTag();
        } catch (JspException e) {
            if (log.isDebugEnabled()) {
                log.debug(getDebugString(), e);
            }
            throw e;
        } catch (Throwable t) {
            if (log.isDebugEnabled()) {
                log.debug(getDebugString(), t);
            }
            throw new JspException(t);
        }
        return rc;
    }

    public String getDebugString() {
        String result = "id: "+this.getId()+" class: "+this.getClass().getName();
        return result;
    }

}
