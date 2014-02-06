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
import javax.faces.component.UIForm;
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


public class FormTag extends UIComponentTag {


    public static Log log = LogFactory.getLog(FormTag.class);

    //
    // Instance Variables
    //


    private java.lang.String accept;
    private java.lang.String acceptcharset;
    private java.lang.String dir;
    private java.lang.String enctype;
    private java.lang.String lang;
    private java.lang.String onclick;
    private java.lang.String ondblclick;
    private java.lang.String onkeydown;
    private java.lang.String onkeypress;
    private java.lang.String onkeyup;
    private java.lang.String onmousedown;
    private java.lang.String onmousemove;
    private java.lang.String onmouseout;
    private java.lang.String onmouseover;
    private java.lang.String onmouseup;
    private java.lang.String onreset;
    private java.lang.String onsubmit;
    private java.lang.String style;
    private java.lang.String styleClass;
    private java.lang.String target;
    private java.lang.String title;

    //
    // Setter Methods
    //

    public void setAccept(java.lang.String accept) {
        this.accept = accept;
    }

    public void setAcceptcharset(java.lang.String acceptcharset) {
        this.acceptcharset = acceptcharset;
    }

    public void setDir(java.lang.String dir) {
        this.dir = dir;
    }

    public void setEnctype(java.lang.String enctype) {
        this.enctype = enctype;
    }

    public void setLang(java.lang.String lang) {
        this.lang = lang;
    }

    public void setOnclick(java.lang.String onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(java.lang.String ondblclick) {
        this.ondblclick = ondblclick;
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

    public void setOnreset(java.lang.String onreset) {
        this.onreset = onreset;
    }

    public void setOnsubmit(java.lang.String onsubmit) {
        this.onsubmit = onsubmit;
    }

    public void setStyle(java.lang.String style) {
        this.style = style;
    }

    public void setStyleClass(java.lang.String styleClass) {
        this.styleClass = styleClass;
    }

    public void setTarget(java.lang.String target) {
        this.target = target;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }


    //
    // General Methods
    //

    public String getRendererType() { return "javax.faces.Form"; }
    public String getComponentType() { return "javax.faces.HtmlForm"; }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIForm form = null;
        try {
            form = (UIForm)component;
        }
        catch (ClassCastException cce) {
          throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: UIForm.  Perhaps you're missing a tag?");
        }

        if (accept != null) {
            if (isValueReference(accept)) {
                ValueBinding vb = Util.getValueBinding(accept);
                form.setValueBinding("accept", vb);
            } else {
                form.getAttributes().put("accept", accept);
            }
        }
        if (acceptcharset != null) {
            if (isValueReference(acceptcharset)) {
                ValueBinding vb = Util.getValueBinding(acceptcharset);
                form.setValueBinding("acceptcharset", vb);
            } else {
                form.getAttributes().put("acceptcharset", acceptcharset);
            }
        }
        if (dir != null) {
            if (isValueReference(dir)) {
                ValueBinding vb = Util.getValueBinding(dir);
                form.setValueBinding("dir", vb);
            } else {
                form.getAttributes().put("dir", dir);
            }
        }
        if (enctype != null) {
            if (isValueReference(enctype)) {
                ValueBinding vb = Util.getValueBinding(enctype);
                form.setValueBinding("enctype", vb);
            } else {
                form.getAttributes().put("enctype", enctype);
            }
        }
        if (lang != null) {
            if (isValueReference(lang)) {
                ValueBinding vb = Util.getValueBinding(lang);
                form.setValueBinding("lang", vb);
            } else {
                form.getAttributes().put("lang", lang);
            }
        }
        if (onclick != null) {
            if (isValueReference(onclick)) {
                ValueBinding vb = Util.getValueBinding(onclick);
                form.setValueBinding("onclick", vb);
            } else {
                form.getAttributes().put("onclick", onclick);
            }
        }
        if (ondblclick != null) {
            if (isValueReference(ondblclick)) {
                ValueBinding vb = Util.getValueBinding(ondblclick);
                form.setValueBinding("ondblclick", vb);
            } else {
                form.getAttributes().put("ondblclick", ondblclick);
            }
        }
        if (onkeydown != null) {
            if (isValueReference(onkeydown)) {
                ValueBinding vb = Util.getValueBinding(onkeydown);
                form.setValueBinding("onkeydown", vb);
            } else {
                form.getAttributes().put("onkeydown", onkeydown);
            }
        }
        if (onkeypress != null) {
            if (isValueReference(onkeypress)) {
                ValueBinding vb = Util.getValueBinding(onkeypress);
                form.setValueBinding("onkeypress", vb);
            } else {
                form.getAttributes().put("onkeypress", onkeypress);
            }
        }
        if (onkeyup != null) {
            if (isValueReference(onkeyup)) {
                ValueBinding vb = Util.getValueBinding(onkeyup);
                form.setValueBinding("onkeyup", vb);
            } else {
                form.getAttributes().put("onkeyup", onkeyup);
            }
        }
        if (onmousedown != null) {
            if (isValueReference(onmousedown)) {
                ValueBinding vb = Util.getValueBinding(onmousedown);
                form.setValueBinding("onmousedown", vb);
            } else {
                form.getAttributes().put("onmousedown", onmousedown);
            }
        }
        if (onmousemove != null) {
            if (isValueReference(onmousemove)) {
                ValueBinding vb = Util.getValueBinding(onmousemove);
                form.setValueBinding("onmousemove", vb);
            } else {
                form.getAttributes().put("onmousemove", onmousemove);
            }
        }
        if (onmouseout != null) {
            if (isValueReference(onmouseout)) {
                ValueBinding vb = Util.getValueBinding(onmouseout);
                form.setValueBinding("onmouseout", vb);
            } else {
                form.getAttributes().put("onmouseout", onmouseout);
            }
        }
        if (onmouseover != null) {
            if (isValueReference(onmouseover)) {
                ValueBinding vb = Util.getValueBinding(onmouseover);
                form.setValueBinding("onmouseover", vb);
            } else {
                form.getAttributes().put("onmouseover", onmouseover);
            }
        }
        if (onmouseup != null) {
            if (isValueReference(onmouseup)) {
                ValueBinding vb = Util.getValueBinding(onmouseup);
                form.setValueBinding("onmouseup", vb);
            } else {
                form.getAttributes().put("onmouseup", onmouseup);
            }
        }
        if (onreset != null) {
            if (isValueReference(onreset)) {
                ValueBinding vb = Util.getValueBinding(onreset);
                form.setValueBinding("onreset", vb);
            } else {
                form.getAttributes().put("onreset", onreset);
            }
        }
        if (onsubmit != null) {
            if (isValueReference(onsubmit)) {
                ValueBinding vb = Util.getValueBinding(onsubmit);
                form.setValueBinding("onsubmit", vb);
            } else {
                form.getAttributes().put("onsubmit", onsubmit);
            }
        }
        if (style != null) {
            if (isValueReference(style)) {
                ValueBinding vb = Util.getValueBinding(style);
                form.setValueBinding("style", vb);
            } else {
                form.getAttributes().put("style", style);
            }
        }
        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = Util.getValueBinding(styleClass);
                form.setValueBinding("styleClass", vb);
            } else {
                form.getAttributes().put("styleClass", styleClass);
            }
        }
        if (target != null) {
            if (isValueReference(target)) {
                ValueBinding vb = Util.getValueBinding(target);
                form.setValueBinding("target", vb);
            } else {
                form.getAttributes().put("target", target);
            }
        }
        if (title != null) {
            if (isValueReference(title)) {
                ValueBinding vb = Util.getValueBinding(title);
                form.setValueBinding("title", vb);
            } else {
                form.getAttributes().put("title", title);
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
