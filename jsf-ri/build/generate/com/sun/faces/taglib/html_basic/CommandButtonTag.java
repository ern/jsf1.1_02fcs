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
import javax.faces.component.UICommand;
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


public class CommandButtonTag extends UIComponentTag {


    public static Log log = LogFactory.getLog(CommandButtonTag.class);

    //
    // Instance Variables
    //

    private java.lang.String action;
    private java.lang.String actionListener;
    private java.lang.String immediate;
    private java.lang.String value;

    private java.lang.String accesskey;
    private java.lang.String alt;
    private java.lang.String dir;
    private java.lang.String disabled;
    private java.lang.String image;
    private java.lang.String lang;
    private java.lang.String onblur;
    private java.lang.String onchange;
    private java.lang.String onclick;
    private java.lang.String ondblclick;
    private java.lang.String onfocus;
    private java.lang.String onkeydown;
    private java.lang.String onkeypress;
    private java.lang.String onkeyup;
    private java.lang.String onmousedown;
    private java.lang.String onmousemove;
    private java.lang.String onmouseout;
    private java.lang.String onmouseover;
    private java.lang.String onmouseup;
    private java.lang.String onselect;
    private java.lang.String readonly;
    private java.lang.String style;
    private java.lang.String styleClass;
    private java.lang.String tabindex;
    private java.lang.String title;
    private java.lang.String type;

    //
    // Setter Methods
    //

    public void setAction(java.lang.String action) {
        this.action = action;
    }

    public void setActionListener(java.lang.String actionListener) {
        this.actionListener = actionListener;
    }

    public void setImmediate(java.lang.String immediate) {
        this.immediate = immediate;
    }

    public void setValue(java.lang.String value) {
        this.value = value;
    }

    public void setAccesskey(java.lang.String accesskey) {
        this.accesskey = accesskey;
    }

    public void setAlt(java.lang.String alt) {
        this.alt = alt;
    }

    public void setDir(java.lang.String dir) {
        this.dir = dir;
    }

    public void setDisabled(java.lang.String disabled) {
        this.disabled = disabled;
    }

    public void setImage(java.lang.String image) {
        this.image = image;
    }

    public void setLang(java.lang.String lang) {
        this.lang = lang;
    }

    public void setOnblur(java.lang.String onblur) {
        this.onblur = onblur;
    }

    public void setOnchange(java.lang.String onchange) {
        this.onchange = onchange;
    }

    public void setOnclick(java.lang.String onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(java.lang.String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public void setOnfocus(java.lang.String onfocus) {
        this.onfocus = onfocus;
    }

    public void setOnkeydown(java.lang.String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public void setOnkeypress(java.lang.String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public void setOnkeyup(java.lang.String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public void setOnmousedown(java.lang.String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public void setOnmousemove(java.lang.String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public void setOnmouseout(java.lang.String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public void setOnmouseover(java.lang.String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public void setOnmouseup(java.lang.String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public void setOnselect(java.lang.String onselect) {
        this.onselect = onselect;
    }

    public void setReadonly(java.lang.String readonly) {
        this.readonly = readonly;
    }

    public void setStyle(java.lang.String style) {
        this.style = style;
    }

    public void setStyleClass(java.lang.String styleClass) {
        this.styleClass = styleClass;
    }

    public void setTabindex(java.lang.String tabindex) {
        this.tabindex = tabindex;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public void setType(java.lang.String type) {
        this.type = type;
    }


    //
    // General Methods
    //

    public String getRendererType() { return "javax.faces.Button"; }
    public String getComponentType() { return "javax.faces.HtmlCommandButton"; }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UICommand command = null;
        try {
            command = (UICommand)component;
        }
        catch (ClassCastException cce) {
          throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: UICommand.  Perhaps you're missing a tag?");
        }

        if (action != null) {
            if (isValueReference(action)) {
                MethodBinding vb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(action, null);
                command.setAction(vb);
            } else {
                final String outcome = action;
                MethodBinding vb = Util.createConstantMethodBinding(action);
                command.setAction(vb);
            }
        }
        if (actionListener != null) {
            if (isValueReference(actionListener)) {
                Class args[] = { ActionEvent.class };
                MethodBinding vb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(actionListener, args);
                command.setActionListener(vb);
            } else {
              Object params [] = {actionListener};
              throw new javax.faces.FacesException(Util.getExceptionMessageString(Util.INVALID_EXPRESSION_ID, params));
            }
            }
        if (immediate != null) {
            if (isValueReference(immediate)) {
                ValueBinding vb = Util.getValueBinding(immediate);
                command.setValueBinding("immediate", vb);
            } else {
                boolean _immediate = new Boolean(immediate).booleanValue();
                command.setImmediate(_immediate);
            }
        }
        if (value != null) {
            if (isValueReference(value)) {
                ValueBinding vb = Util.getValueBinding(value);
                command.setValueBinding("value", vb);
            } else {
                command.setValue(value);
            }
        }
        if (accesskey != null) {
            if (isValueReference(accesskey)) {
                ValueBinding vb = Util.getValueBinding(accesskey);
                command.setValueBinding("accesskey", vb);
            } else {
                command.getAttributes().put("accesskey", accesskey);
            }
        }
        if (alt != null) {
            if (isValueReference(alt)) {
                ValueBinding vb = Util.getValueBinding(alt);
                command.setValueBinding("alt", vb);
            } else {
                command.getAttributes().put("alt", alt);
            }
        }
        if (dir != null) {
            if (isValueReference(dir)) {
                ValueBinding vb = Util.getValueBinding(dir);
                command.setValueBinding("dir", vb);
            } else {
                command.getAttributes().put("dir", dir);
            }
        }
        if (disabled != null) {
            if (isValueReference(disabled)) {
                ValueBinding vb = Util.getValueBinding(disabled);
                command.setValueBinding("disabled", vb);
            } else {
                boolean _disabled = new Boolean(disabled).booleanValue();
                command.getAttributes().put("disabled", _disabled ? Boolean.TRUE : Boolean.FALSE);
            }
        }
        if (image != null) {
            if (isValueReference(image)) {
                ValueBinding vb = Util.getValueBinding(image);
                command.setValueBinding("image", vb);
            } else {
                command.getAttributes().put("image", image);
            }
        }
        if (lang != null) {
            if (isValueReference(lang)) {
                ValueBinding vb = Util.getValueBinding(lang);
                command.setValueBinding("lang", vb);
            } else {
                command.getAttributes().put("lang", lang);
            }
        }
        if (onblur != null) {
            if (isValueReference(onblur)) {
                ValueBinding vb = Util.getValueBinding(onblur);
                command.setValueBinding("onblur", vb);
            } else {
                command.getAttributes().put("onblur", onblur);
            }
        }
        if (onchange != null) {
            if (isValueReference(onchange)) {
                ValueBinding vb = Util.getValueBinding(onchange);
                command.setValueBinding("onchange", vb);
            } else {
                command.getAttributes().put("onchange", onchange);
            }
        }
        if (onclick != null) {
            if (isValueReference(onclick)) {
                ValueBinding vb = Util.getValueBinding(onclick);
                command.setValueBinding("onclick", vb);
            } else {
                command.getAttributes().put("onclick", onclick);
            }
        }
        if (ondblclick != null) {
            if (isValueReference(ondblclick)) {
                ValueBinding vb = Util.getValueBinding(ondblclick);
                command.setValueBinding("ondblclick", vb);
            } else {
                command.getAttributes().put("ondblclick", ondblclick);
            }
        }
        if (onfocus != null) {
            if (isValueReference(onfocus)) {
                ValueBinding vb = Util.getValueBinding(onfocus);
                command.setValueBinding("onfocus", vb);
            } else {
                command.getAttributes().put("onfocus", onfocus);
            }
        }
        if (onkeydown != null) {
            if (isValueReference(onkeydown)) {
                ValueBinding vb = Util.getValueBinding(onkeydown);
                command.setValueBinding("onkeydown", vb);
            } else {
                command.getAttributes().put("onkeydown", onkeydown);
            }
        }
        if (onkeypress != null) {
            if (isValueReference(onkeypress)) {
                ValueBinding vb = Util.getValueBinding(onkeypress);
                command.setValueBinding("onkeypress", vb);
            } else {
                command.getAttributes().put("onkeypress", onkeypress);
            }
        }
        if (onkeyup != null) {
            if (isValueReference(onkeyup)) {
                ValueBinding vb = Util.getValueBinding(onkeyup);
                command.setValueBinding("onkeyup", vb);
            } else {
                command.getAttributes().put("onkeyup", onkeyup);
            }
        }
        if (onmousedown != null) {
            if (isValueReference(onmousedown)) {
                ValueBinding vb = Util.getValueBinding(onmousedown);
                command.setValueBinding("onmousedown", vb);
            } else {
                command.getAttributes().put("onmousedown", onmousedown);
            }
        }
        if (onmousemove != null) {
            if (isValueReference(onmousemove)) {
                ValueBinding vb = Util.getValueBinding(onmousemove);
                command.setValueBinding("onmousemove", vb);
            } else {
                command.getAttributes().put("onmousemove", onmousemove);
            }
        }
        if (onmouseout != null) {
            if (isValueReference(onmouseout)) {
                ValueBinding vb = Util.getValueBinding(onmouseout);
                command.setValueBinding("onmouseout", vb);
            } else {
                command.getAttributes().put("onmouseout", onmouseout);
            }
        }
        if (onmouseover != null) {
            if (isValueReference(onmouseover)) {
                ValueBinding vb = Util.getValueBinding(onmouseover);
                command.setValueBinding("onmouseover", vb);
            } else {
                command.getAttributes().put("onmouseover", onmouseover);
            }
        }
        if (onmouseup != null) {
            if (isValueReference(onmouseup)) {
                ValueBinding vb = Util.getValueBinding(onmouseup);
                command.setValueBinding("onmouseup", vb);
            } else {
                command.getAttributes().put("onmouseup", onmouseup);
            }
        }
        if (onselect != null) {
            if (isValueReference(onselect)) {
                ValueBinding vb = Util.getValueBinding(onselect);
                command.setValueBinding("onselect", vb);
            } else {
                command.getAttributes().put("onselect", onselect);
            }
        }
        if (readonly != null) {
            if (isValueReference(readonly)) {
                ValueBinding vb = Util.getValueBinding(readonly);
                command.setValueBinding("readonly", vb);
            } else {
                boolean _readonly = new Boolean(readonly).booleanValue();
                command.getAttributes().put("readonly", _readonly ? Boolean.TRUE : Boolean.FALSE);
            }
        }
        if (style != null) {
            if (isValueReference(style)) {
                ValueBinding vb = Util.getValueBinding(style);
                command.setValueBinding("style", vb);
            } else {
                command.getAttributes().put("style", style);
            }
        }
        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = Util.getValueBinding(styleClass);
                command.setValueBinding("styleClass", vb);
            } else {
                command.getAttributes().put("styleClass", styleClass);
            }
        }
        if (tabindex != null) {
            if (isValueReference(tabindex)) {
                ValueBinding vb = Util.getValueBinding(tabindex);
                command.setValueBinding("tabindex", vb);
            } else {
                command.getAttributes().put("tabindex", tabindex);
            }
        }
        if (title != null) {
            if (isValueReference(title)) {
                ValueBinding vb = Util.getValueBinding(title);
                command.setValueBinding("title", vb);
            } else {
                command.getAttributes().put("title", title);
            }
        }
        if (type != null) {
            if (isValueReference(type)) {
                ValueBinding vb = Util.getValueBinding(type);
                command.setValueBinding("type", vb);
            } else {
                command.getAttributes().put("type", type);
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
