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
import javax.faces.component.UIPanel;
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


public class PanelGridTag extends UIComponentTag {


    public static Log log = LogFactory.getLog(PanelGridTag.class);

    //
    // Instance Variables
    //


    private java.lang.String bgcolor;
    private java.lang.String border;
    private java.lang.String cellpadding;
    private java.lang.String cellspacing;
    private java.lang.String columnClasses;
    private java.lang.String columns;
    private java.lang.String dir;
    private java.lang.String footerClass;
    private java.lang.String frame;
    private java.lang.String headerClass;
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
    private java.lang.String rowClasses;
    private java.lang.String rules;
    private java.lang.String style;
    private java.lang.String styleClass;
    private java.lang.String summary;
    private java.lang.String title;
    private java.lang.String width;

    //
    // Setter Methods
    //

    public void setBgcolor(java.lang.String bgcolor) {
        this.bgcolor = bgcolor;
    }

    public void setBorder(java.lang.String border) {
        this.border = border;
    }

    public void setCellpadding(java.lang.String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public void setCellspacing(java.lang.String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public void setColumnClasses(java.lang.String columnClasses) {
        this.columnClasses = columnClasses;
    }

    public void setColumns(java.lang.String columns) {
        this.columns = columns;
    }

    public void setDir(java.lang.String dir) {
        this.dir = dir;
    }

    public void setFooterClass(java.lang.String footerClass) {
        this.footerClass = footerClass;
    }

    public void setFrame(java.lang.String frame) {
        this.frame = frame;
    }

    public void setHeaderClass(java.lang.String headerClass) {
        this.headerClass = headerClass;
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

    public void setRowClasses(java.lang.String rowClasses) {
        this.rowClasses = rowClasses;
    }

    public void setRules(java.lang.String rules) {
        this.rules = rules;
    }

    public void setStyle(java.lang.String style) {
        this.style = style;
    }

    public void setStyleClass(java.lang.String styleClass) {
        this.styleClass = styleClass;
    }

    public void setSummary(java.lang.String summary) {
        this.summary = summary;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public void setWidth(java.lang.String width) {
        this.width = width;
    }


    //
    // General Methods
    //

    public String getRendererType() { return "javax.faces.Grid"; }
    public String getComponentType() { return "javax.faces.HtmlPanelGrid"; }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIPanel panel = null;
        try {
            panel = (UIPanel)component;
        }
        catch (ClassCastException cce) {
          throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: UIPanel.  Perhaps you're missing a tag?");
        }

        if (bgcolor != null) {
            if (isValueReference(bgcolor)) {
                ValueBinding vb = Util.getValueBinding(bgcolor);
                panel.setValueBinding("bgcolor", vb);
            } else {
                panel.getAttributes().put("bgcolor", bgcolor);
            }
        }
        if (border != null) {
            if (isValueReference(border)) {
                ValueBinding vb = Util.getValueBinding(border);
                panel.setValueBinding("border", vb);
            } else {
                int _border = new Integer(border).intValue();
                if (_border != Integer.MIN_VALUE) {
                    panel.getAttributes().put("border", new Integer(_border));
                }
            }
        }
        if (cellpadding != null) {
            if (isValueReference(cellpadding)) {
                ValueBinding vb = Util.getValueBinding(cellpadding);
                panel.setValueBinding("cellpadding", vb);
            } else {
                panel.getAttributes().put("cellpadding", cellpadding);
            }
        }
        if (cellspacing != null) {
            if (isValueReference(cellspacing)) {
                ValueBinding vb = Util.getValueBinding(cellspacing);
                panel.setValueBinding("cellspacing", vb);
            } else {
                panel.getAttributes().put("cellspacing", cellspacing);
            }
        }
        if (columnClasses != null) {
            if (isValueReference(columnClasses)) {
                ValueBinding vb = Util.getValueBinding(columnClasses);
                panel.setValueBinding("columnClasses", vb);
            } else {
                panel.getAttributes().put("columnClasses", columnClasses);
            }
        }
        if (columns != null) {
            if (isValueReference(columns)) {
                ValueBinding vb = Util.getValueBinding(columns);
                panel.setValueBinding("columns", vb);
            } else {
                int _columns = new Integer(columns).intValue();
                if (_columns != Integer.MIN_VALUE) {
                    panel.getAttributes().put("columns", new Integer(_columns));
                }
            }
        }
        if (dir != null) {
            if (isValueReference(dir)) {
                ValueBinding vb = Util.getValueBinding(dir);
                panel.setValueBinding("dir", vb);
            } else {
                panel.getAttributes().put("dir", dir);
            }
        }
        if (footerClass != null) {
            if (isValueReference(footerClass)) {
                ValueBinding vb = Util.getValueBinding(footerClass);
                panel.setValueBinding("footerClass", vb);
            } else {
                panel.getAttributes().put("footerClass", footerClass);
            }
        }
        if (frame != null) {
            if (isValueReference(frame)) {
                ValueBinding vb = Util.getValueBinding(frame);
                panel.setValueBinding("frame", vb);
            } else {
                panel.getAttributes().put("frame", frame);
            }
        }
        if (headerClass != null) {
            if (isValueReference(headerClass)) {
                ValueBinding vb = Util.getValueBinding(headerClass);
                panel.setValueBinding("headerClass", vb);
            } else {
                panel.getAttributes().put("headerClass", headerClass);
            }
        }
        if (lang != null) {
            if (isValueReference(lang)) {
                ValueBinding vb = Util.getValueBinding(lang);
                panel.setValueBinding("lang", vb);
            } else {
                panel.getAttributes().put("lang", lang);
            }
        }
        if (onclick != null) {
            if (isValueReference(onclick)) {
                ValueBinding vb = Util.getValueBinding(onclick);
                panel.setValueBinding("onclick", vb);
            } else {
                panel.getAttributes().put("onclick", onclick);
            }
        }
        if (ondblclick != null) {
            if (isValueReference(ondblclick)) {
                ValueBinding vb = Util.getValueBinding(ondblclick);
                panel.setValueBinding("ondblclick", vb);
            } else {
                panel.getAttributes().put("ondblclick", ondblclick);
            }
        }
        if (onkeydown != null) {
            if (isValueReference(onkeydown)) {
                ValueBinding vb = Util.getValueBinding(onkeydown);
                panel.setValueBinding("onkeydown", vb);
            } else {
                panel.getAttributes().put("onkeydown", onkeydown);
            }
        }
        if (onkeypress != null) {
            if (isValueReference(onkeypress)) {
                ValueBinding vb = Util.getValueBinding(onkeypress);
                panel.setValueBinding("onkeypress", vb);
            } else {
                panel.getAttributes().put("onkeypress", onkeypress);
            }
        }
        if (onkeyup != null) {
            if (isValueReference(onkeyup)) {
                ValueBinding vb = Util.getValueBinding(onkeyup);
                panel.setValueBinding("onkeyup", vb);
            } else {
                panel.getAttributes().put("onkeyup", onkeyup);
            }
        }
        if (onmousedown != null) {
            if (isValueReference(onmousedown)) {
                ValueBinding vb = Util.getValueBinding(onmousedown);
                panel.setValueBinding("onmousedown", vb);
            } else {
                panel.getAttributes().put("onmousedown", onmousedown);
            }
        }
        if (onmousemove != null) {
            if (isValueReference(onmousemove)) {
                ValueBinding vb = Util.getValueBinding(onmousemove);
                panel.setValueBinding("onmousemove", vb);
            } else {
                panel.getAttributes().put("onmousemove", onmousemove);
            }
        }
        if (onmouseout != null) {
            if (isValueReference(onmouseout)) {
                ValueBinding vb = Util.getValueBinding(onmouseout);
                panel.setValueBinding("onmouseout", vb);
            } else {
                panel.getAttributes().put("onmouseout", onmouseout);
            }
        }
        if (onmouseover != null) {
            if (isValueReference(onmouseover)) {
                ValueBinding vb = Util.getValueBinding(onmouseover);
                panel.setValueBinding("onmouseover", vb);
            } else {
                panel.getAttributes().put("onmouseover", onmouseover);
            }
        }
        if (onmouseup != null) {
            if (isValueReference(onmouseup)) {
                ValueBinding vb = Util.getValueBinding(onmouseup);
                panel.setValueBinding("onmouseup", vb);
            } else {
                panel.getAttributes().put("onmouseup", onmouseup);
            }
        }
        if (rowClasses != null) {
            if (isValueReference(rowClasses)) {
                ValueBinding vb = Util.getValueBinding(rowClasses);
                panel.setValueBinding("rowClasses", vb);
            } else {
                panel.getAttributes().put("rowClasses", rowClasses);
            }
        }
        if (rules != null) {
            if (isValueReference(rules)) {
                ValueBinding vb = Util.getValueBinding(rules);
                panel.setValueBinding("rules", vb);
            } else {
                panel.getAttributes().put("rules", rules);
            }
        }
        if (style != null) {
            if (isValueReference(style)) {
                ValueBinding vb = Util.getValueBinding(style);
                panel.setValueBinding("style", vb);
            } else {
                panel.getAttributes().put("style", style);
            }
        }
        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = Util.getValueBinding(styleClass);
                panel.setValueBinding("styleClass", vb);
            } else {
                panel.getAttributes().put("styleClass", styleClass);
            }
        }
        if (summary != null) {
            if (isValueReference(summary)) {
                ValueBinding vb = Util.getValueBinding(summary);
                panel.setValueBinding("summary", vb);
            } else {
                panel.getAttributes().put("summary", summary);
            }
        }
        if (title != null) {
            if (isValueReference(title)) {
                ValueBinding vb = Util.getValueBinding(title);
                panel.setValueBinding("title", vb);
            } else {
                panel.getAttributes().put("title", title);
            }
        }
        if (width != null) {
            if (isValueReference(width)) {
                ValueBinding vb = Util.getValueBinding(width);
                panel.setValueBinding("width", vb);
            } else {
                panel.getAttributes().put("width", width);
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
