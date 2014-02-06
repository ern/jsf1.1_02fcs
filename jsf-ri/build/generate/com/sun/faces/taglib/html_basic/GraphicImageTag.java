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
import javax.faces.component.UIGraphic;
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


public class GraphicImageTag extends UIComponentTag {


    public static Log log = LogFactory.getLog(GraphicImageTag.class);

    //
    // Instance Variables
    //

    private java.lang.String url;
    private java.lang.String value;

    private java.lang.String alt;
    private java.lang.String dir;
    private java.lang.String height;
    private java.lang.String ismap;
    private java.lang.String lang;
    private java.lang.String longdesc;
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
    private java.lang.String style;
    private java.lang.String styleClass;
    private java.lang.String title;
    private java.lang.String usemap;
    private java.lang.String width;

    //
    // Setter Methods
    //

    public void setUrl(java.lang.String url) {
        this.url = url;
    }

    public void setValue(java.lang.String value) {
        this.value = value;
    }

    public void setAlt(java.lang.String alt) {
        this.alt = alt;
    }

    public void setDir(java.lang.String dir) {
        this.dir = dir;
    }

    public void setHeight(java.lang.String height) {
        this.height = height;
    }

    public void setIsmap(java.lang.String ismap) {
        this.ismap = ismap;
    }

    public void setLang(java.lang.String lang) {
        this.lang = lang;
    }

    public void setLongdesc(java.lang.String longdesc) {
        this.longdesc = longdesc;
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

    public void setStyle(java.lang.String style) {
        this.style = style;
    }

    public void setStyleClass(java.lang.String styleClass) {
        this.styleClass = styleClass;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public void setUsemap(java.lang.String usemap) {
        this.usemap = usemap;
    }

    public void setWidth(java.lang.String width) {
        this.width = width;
    }


    //
    // General Methods
    //

    public String getRendererType() { return "javax.faces.Image"; }
    public String getComponentType() { return "javax.faces.HtmlGraphicImage"; }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIGraphic graphic = null;
        try {
            graphic = (UIGraphic)component;
        }
        catch (ClassCastException cce) {
          throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: UIGraphic.  Perhaps you're missing a tag?");
        }

        if (url != null) {
            if (isValueReference(url)) {
                ValueBinding vb = Util.getValueBinding(url);
                graphic.setValueBinding("url", vb);
            } else {
                graphic.setUrl(url);
            }
        }
        if (value != null) {
            if (isValueReference(value)) {
                ValueBinding vb = Util.getValueBinding(value);
                graphic.setValueBinding("value", vb);
            } else {
                graphic.setValue(value);
            }
        }
        if (alt != null) {
            if (isValueReference(alt)) {
                ValueBinding vb = Util.getValueBinding(alt);
                graphic.setValueBinding("alt", vb);
            } else {
                graphic.getAttributes().put("alt", alt);
            }
        }
        if (dir != null) {
            if (isValueReference(dir)) {
                ValueBinding vb = Util.getValueBinding(dir);
                graphic.setValueBinding("dir", vb);
            } else {
                graphic.getAttributes().put("dir", dir);
            }
        }
        if (height != null) {
            if (isValueReference(height)) {
                ValueBinding vb = Util.getValueBinding(height);
                graphic.setValueBinding("height", vb);
            } else {
                graphic.getAttributes().put("height", height);
            }
        }
        if (ismap != null) {
            if (isValueReference(ismap)) {
                ValueBinding vb = Util.getValueBinding(ismap);
                graphic.setValueBinding("ismap", vb);
            } else {
                boolean _ismap = new Boolean(ismap).booleanValue();
                graphic.getAttributes().put("ismap", _ismap ? Boolean.TRUE : Boolean.FALSE);
            }
        }
        if (lang != null) {
            if (isValueReference(lang)) {
                ValueBinding vb = Util.getValueBinding(lang);
                graphic.setValueBinding("lang", vb);
            } else {
                graphic.getAttributes().put("lang", lang);
            }
        }
        if (longdesc != null) {
            if (isValueReference(longdesc)) {
                ValueBinding vb = Util.getValueBinding(longdesc);
                graphic.setValueBinding("longdesc", vb);
            } else {
                graphic.getAttributes().put("longdesc", longdesc);
            }
        }
        if (onclick != null) {
            if (isValueReference(onclick)) {
                ValueBinding vb = Util.getValueBinding(onclick);
                graphic.setValueBinding("onclick", vb);
            } else {
                graphic.getAttributes().put("onclick", onclick);
            }
        }
        if (ondblclick != null) {
            if (isValueReference(ondblclick)) {
                ValueBinding vb = Util.getValueBinding(ondblclick);
                graphic.setValueBinding("ondblclick", vb);
            } else {
                graphic.getAttributes().put("ondblclick", ondblclick);
            }
        }
        if (onkeydown != null) {
            if (isValueReference(onkeydown)) {
                ValueBinding vb = Util.getValueBinding(onkeydown);
                graphic.setValueBinding("onkeydown", vb);
            } else {
                graphic.getAttributes().put("onkeydown", onkeydown);
            }
        }
        if (onkeypress != null) {
            if (isValueReference(onkeypress)) {
                ValueBinding vb = Util.getValueBinding(onkeypress);
                graphic.setValueBinding("onkeypress", vb);
            } else {
                graphic.getAttributes().put("onkeypress", onkeypress);
            }
        }
        if (onkeyup != null) {
            if (isValueReference(onkeyup)) {
                ValueBinding vb = Util.getValueBinding(onkeyup);
                graphic.setValueBinding("onkeyup", vb);
            } else {
                graphic.getAttributes().put("onkeyup", onkeyup);
            }
        }
        if (onmousedown != null) {
            if (isValueReference(onmousedown)) {
                ValueBinding vb = Util.getValueBinding(onmousedown);
                graphic.setValueBinding("onmousedown", vb);
            } else {
                graphic.getAttributes().put("onmousedown", onmousedown);
            }
        }
        if (onmousemove != null) {
            if (isValueReference(onmousemove)) {
                ValueBinding vb = Util.getValueBinding(onmousemove);
                graphic.setValueBinding("onmousemove", vb);
            } else {
                graphic.getAttributes().put("onmousemove", onmousemove);
            }
        }
        if (onmouseout != null) {
            if (isValueReference(onmouseout)) {
                ValueBinding vb = Util.getValueBinding(onmouseout);
                graphic.setValueBinding("onmouseout", vb);
            } else {
                graphic.getAttributes().put("onmouseout", onmouseout);
            }
        }
        if (onmouseover != null) {
            if (isValueReference(onmouseover)) {
                ValueBinding vb = Util.getValueBinding(onmouseover);
                graphic.setValueBinding("onmouseover", vb);
            } else {
                graphic.getAttributes().put("onmouseover", onmouseover);
            }
        }
        if (onmouseup != null) {
            if (isValueReference(onmouseup)) {
                ValueBinding vb = Util.getValueBinding(onmouseup);
                graphic.setValueBinding("onmouseup", vb);
            } else {
                graphic.getAttributes().put("onmouseup", onmouseup);
            }
        }
        if (style != null) {
            if (isValueReference(style)) {
                ValueBinding vb = Util.getValueBinding(style);
                graphic.setValueBinding("style", vb);
            } else {
                graphic.getAttributes().put("style", style);
            }
        }
        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = Util.getValueBinding(styleClass);
                graphic.setValueBinding("styleClass", vb);
            } else {
                graphic.getAttributes().put("styleClass", styleClass);
            }
        }
        if (title != null) {
            if (isValueReference(title)) {
                ValueBinding vb = Util.getValueBinding(title);
                graphic.setValueBinding("title", vb);
            } else {
                graphic.getAttributes().put("title", title);
            }
        }
        if (usemap != null) {
            if (isValueReference(usemap)) {
                ValueBinding vb = Util.getValueBinding(usemap);
                graphic.setValueBinding("usemap", vb);
            } else {
                graphic.getAttributes().put("usemap", usemap);
            }
        }
        if (width != null) {
            if (isValueReference(width)) {
                ValueBinding vb = Util.getValueBinding(width);
                graphic.setValueBinding("width", vb);
            } else {
                graphic.getAttributes().put("width", width);
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
