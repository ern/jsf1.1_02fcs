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
import javax.faces.component.UIMessages;
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


public class MessagesTag extends UIComponentTag {


    public static Log log = LogFactory.getLog(MessagesTag.class);

    //
    // Instance Variables
    //

    private java.lang.String globalOnly;
    private java.lang.String showDetail;
    private java.lang.String showSummary;

    private java.lang.String errorClass;
    private java.lang.String errorStyle;
    private java.lang.String fatalClass;
    private java.lang.String fatalStyle;
    private java.lang.String infoClass;
    private java.lang.String infoStyle;
    private java.lang.String layout;
    private java.lang.String style;
    private java.lang.String styleClass;
    private java.lang.String title;
    private java.lang.String tooltip;
    private java.lang.String warnClass;
    private java.lang.String warnStyle;

    //
    // Setter Methods
    //

    public void setGlobalOnly(java.lang.String globalOnly) {
        this.globalOnly = globalOnly;
    }

    public void setShowDetail(java.lang.String showDetail) {
        this.showDetail = showDetail;
    }

    public void setShowSummary(java.lang.String showSummary) {
        this.showSummary = showSummary;
    }

    public void setErrorClass(java.lang.String errorClass) {
        this.errorClass = errorClass;
    }

    public void setErrorStyle(java.lang.String errorStyle) {
        this.errorStyle = errorStyle;
    }

    public void setFatalClass(java.lang.String fatalClass) {
        this.fatalClass = fatalClass;
    }

    public void setFatalStyle(java.lang.String fatalStyle) {
        this.fatalStyle = fatalStyle;
    }

    public void setInfoClass(java.lang.String infoClass) {
        this.infoClass = infoClass;
    }

    public void setInfoStyle(java.lang.String infoStyle) {
        this.infoStyle = infoStyle;
    }

    public void setLayout(java.lang.String layout) {
        this.layout = layout;
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

    public void setTooltip(java.lang.String tooltip) {
        this.tooltip = tooltip;
    }

    public void setWarnClass(java.lang.String warnClass) {
        this.warnClass = warnClass;
    }

    public void setWarnStyle(java.lang.String warnStyle) {
        this.warnStyle = warnStyle;
    }


    //
    // General Methods
    //

    public String getRendererType() { return "javax.faces.Messages"; }
    public String getComponentType() { return "javax.faces.HtmlMessages"; }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIMessages messages = null;
        try {
            messages = (UIMessages)component;
        }
        catch (ClassCastException cce) {
          throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: UIMessages.  Perhaps you're missing a tag?");
        }

        if (globalOnly != null) {
            if (isValueReference(globalOnly)) {
                ValueBinding vb = Util.getValueBinding(globalOnly);
                messages.setValueBinding("globalOnly", vb);
            } else {
                boolean _globalOnly = new Boolean(globalOnly).booleanValue();
                messages.setGlobalOnly(_globalOnly);
            }
        }
        if (showDetail != null) {
            if (isValueReference(showDetail)) {
                ValueBinding vb = Util.getValueBinding(showDetail);
                messages.setValueBinding("showDetail", vb);
            } else {
                boolean _showDetail = new Boolean(showDetail).booleanValue();
                messages.setShowDetail(_showDetail);
            }
        }
        if (showSummary != null) {
            if (isValueReference(showSummary)) {
                ValueBinding vb = Util.getValueBinding(showSummary);
                messages.setValueBinding("showSummary", vb);
            } else {
                boolean _showSummary = new Boolean(showSummary).booleanValue();
                messages.setShowSummary(_showSummary);
            }
        }
        if (errorClass != null) {
            if (isValueReference(errorClass)) {
                ValueBinding vb = Util.getValueBinding(errorClass);
                messages.setValueBinding("errorClass", vb);
            } else {
                messages.getAttributes().put("errorClass", errorClass);
            }
        }
        if (errorStyle != null) {
            if (isValueReference(errorStyle)) {
                ValueBinding vb = Util.getValueBinding(errorStyle);
                messages.setValueBinding("errorStyle", vb);
            } else {
                messages.getAttributes().put("errorStyle", errorStyle);
            }
        }
        if (fatalClass != null) {
            if (isValueReference(fatalClass)) {
                ValueBinding vb = Util.getValueBinding(fatalClass);
                messages.setValueBinding("fatalClass", vb);
            } else {
                messages.getAttributes().put("fatalClass", fatalClass);
            }
        }
        if (fatalStyle != null) {
            if (isValueReference(fatalStyle)) {
                ValueBinding vb = Util.getValueBinding(fatalStyle);
                messages.setValueBinding("fatalStyle", vb);
            } else {
                messages.getAttributes().put("fatalStyle", fatalStyle);
            }
        }
        if (infoClass != null) {
            if (isValueReference(infoClass)) {
                ValueBinding vb = Util.getValueBinding(infoClass);
                messages.setValueBinding("infoClass", vb);
            } else {
                messages.getAttributes().put("infoClass", infoClass);
            }
        }
        if (infoStyle != null) {
            if (isValueReference(infoStyle)) {
                ValueBinding vb = Util.getValueBinding(infoStyle);
                messages.setValueBinding("infoStyle", vb);
            } else {
                messages.getAttributes().put("infoStyle", infoStyle);
            }
        }
        if (layout != null) {
            if (isValueReference(layout)) {
                ValueBinding vb = Util.getValueBinding(layout);
                messages.setValueBinding("layout", vb);
            } else {
                messages.getAttributes().put("layout", layout);
            }
        }
        if (style != null) {
            if (isValueReference(style)) {
                ValueBinding vb = Util.getValueBinding(style);
                messages.setValueBinding("style", vb);
            } else {
                messages.getAttributes().put("style", style);
            }
        }
        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = Util.getValueBinding(styleClass);
                messages.setValueBinding("styleClass", vb);
            } else {
                messages.getAttributes().put("styleClass", styleClass);
            }
        }
        if (title != null) {
            if (isValueReference(title)) {
                ValueBinding vb = Util.getValueBinding(title);
                messages.setValueBinding("title", vb);
            } else {
                messages.getAttributes().put("title", title);
            }
        }
        if (tooltip != null) {
            if (isValueReference(tooltip)) {
                ValueBinding vb = Util.getValueBinding(tooltip);
                messages.setValueBinding("tooltip", vb);
            } else {
                boolean _tooltip = new Boolean(tooltip).booleanValue();
                messages.getAttributes().put("tooltip", _tooltip ? Boolean.TRUE : Boolean.FALSE);
            }
        }
        if (warnClass != null) {
            if (isValueReference(warnClass)) {
                ValueBinding vb = Util.getValueBinding(warnClass);
                messages.setValueBinding("warnClass", vb);
            } else {
                messages.getAttributes().put("warnClass", warnClass);
            }
        }
        if (warnStyle != null) {
            if (isValueReference(warnStyle)) {
                ValueBinding vb = Util.getValueBinding(warnStyle);
                messages.setValueBinding("warnStyle", vb);
            } else {
                messages.getAttributes().put("warnStyle", warnStyle);
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
