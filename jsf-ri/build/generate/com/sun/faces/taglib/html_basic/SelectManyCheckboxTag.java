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
import javax.faces.component.UISelectMany;
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


public class SelectManyCheckboxTag extends UIComponentTag {


    public static Log log = LogFactory.getLog(SelectManyCheckboxTag.class);

    //
    // Instance Variables
    //

    private java.lang.String converter;
    private java.lang.String immediate;
    private java.lang.String required;
    private java.lang.String validator;
    private java.lang.String value;
    private java.lang.String valueChangeListener;

    private java.lang.String accesskey;
    private java.lang.String border;
    private java.lang.String dir;
    private java.lang.String disabled;
    private java.lang.String disabledClass;
    private java.lang.String enabledClass;
    private java.lang.String lang;
    private java.lang.String layout;
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

    //
    // Setter Methods
    //

    public void setConverter(java.lang.String converter) {
        this.converter = converter;
    }

    public void setImmediate(java.lang.String immediate) {
        this.immediate = immediate;
    }

    public void setRequired(java.lang.String required) {
        this.required = required;
    }

    public void setValidator(java.lang.String validator) {
        this.validator = validator;
    }

    public void setValue(java.lang.String value) {
        this.value = value;
    }

    public void setValueChangeListener(java.lang.String valueChangeListener) {
        this.valueChangeListener = valueChangeListener;
    }

    public void setAccesskey(java.lang.String accesskey) {
        this.accesskey = accesskey;
    }

    public void setBorder(java.lang.String border) {
        this.border = border;
    }

    public void setDir(java.lang.String dir) {
        this.dir = dir;
    }

    public void setDisabled(java.lang.String disabled) {
        this.disabled = disabled;
    }

    public void setDisabledClass(java.lang.String disabledClass) {
        this.disabledClass = disabledClass;
    }

    public void setEnabledClass(java.lang.String enabledClass) {
        this.enabledClass = enabledClass;
    }

    public void setLang(java.lang.String lang) {
        this.lang = lang;
    }

    public void setLayout(java.lang.String layout) {
        this.layout = layout;
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


    //
    // General Methods
    //

    public String getRendererType() { return "javax.faces.Checkbox"; }
    public String getComponentType() { return "javax.faces.HtmlSelectManyCheckbox"; }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UISelectMany selectmany = null;
        try {
            selectmany = (UISelectMany)component;
        }
        catch (ClassCastException cce) {
          throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: UISelectMany.  Perhaps you're missing a tag?");
        }

        if (converter != null) {
            if (isValueReference(converter)) {
                      ValueBinding vb = 
                          Util.getValueBinding(converter);
                  selectmany.setValueBinding("converter", vb);
            } else {
                Converter _converter = FacesContext.getCurrentInstance().
                    getApplication().createConverter(converter);
                selectmany.setConverter(_converter);
            }
        }

        if (immediate != null) {
            if (isValueReference(immediate)) {
                ValueBinding vb = Util.getValueBinding(immediate);
                selectmany.setValueBinding("immediate", vb);
            } else {
                boolean _immediate = new Boolean(immediate).booleanValue();
                selectmany.setImmediate(_immediate);
            }
        }
        if (required != null) {
            if (isValueReference(required)) {
                ValueBinding vb = Util.getValueBinding(required);
                selectmany.setValueBinding("required", vb);
            } else {
                boolean _required = new Boolean(required).booleanValue();
                selectmany.setRequired(_required);
            }
        }
        if (validator != null) {
            if (isValueReference(validator)) {
                Class args[] = { FacesContext.class, UIComponent.class, Object.class };
                MethodBinding vb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(validator, args);
                selectmany.setValidator(vb);
            } else {
              Object params [] = {validator};
              throw new javax.faces.FacesException(Util.getExceptionMessageString(Util.INVALID_EXPRESSION_ID, params));
            }
            }
        if (value != null) {
            if (isValueReference(value)) {
                ValueBinding vb = Util.getValueBinding(value);
                selectmany.setValueBinding("value", vb);
            } else {
                selectmany.setValue(value);
            }
        }
        if (valueChangeListener != null) {
            if (isValueReference(valueChangeListener)) {
                Class args[] = { ValueChangeEvent.class };
                MethodBinding vb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(valueChangeListener, args);
                selectmany.setValueChangeListener(vb);
            } else {
              Object params [] = {valueChangeListener};
              throw new javax.faces.FacesException(Util.getExceptionMessageString(Util.INVALID_EXPRESSION_ID, params));
            }
            }
        if (accesskey != null) {
            if (isValueReference(accesskey)) {
                ValueBinding vb = Util.getValueBinding(accesskey);
                selectmany.setValueBinding("accesskey", vb);
            } else {
                selectmany.getAttributes().put("accesskey", accesskey);
            }
        }
        if (border != null) {
            if (isValueReference(border)) {
                ValueBinding vb = Util.getValueBinding(border);
                selectmany.setValueBinding("border", vb);
            } else {
                int _border = new Integer(border).intValue();
                if (_border != Integer.MIN_VALUE) {
                    selectmany.getAttributes().put("border", new Integer(_border));
                }
            }
        }
        if (dir != null) {
            if (isValueReference(dir)) {
                ValueBinding vb = Util.getValueBinding(dir);
                selectmany.setValueBinding("dir", vb);
            } else {
                selectmany.getAttributes().put("dir", dir);
            }
        }
        if (disabled != null) {
            if (isValueReference(disabled)) {
                ValueBinding vb = Util.getValueBinding(disabled);
                selectmany.setValueBinding("disabled", vb);
            } else {
                boolean _disabled = new Boolean(disabled).booleanValue();
                selectmany.getAttributes().put("disabled", _disabled ? Boolean.TRUE : Boolean.FALSE);
            }
        }
        if (disabledClass != null) {
            if (isValueReference(disabledClass)) {
                ValueBinding vb = Util.getValueBinding(disabledClass);
                selectmany.setValueBinding("disabledClass", vb);
            } else {
                selectmany.getAttributes().put("disabledClass", disabledClass);
            }
        }
        if (enabledClass != null) {
            if (isValueReference(enabledClass)) {
                ValueBinding vb = Util.getValueBinding(enabledClass);
                selectmany.setValueBinding("enabledClass", vb);
            } else {
                selectmany.getAttributes().put("enabledClass", enabledClass);
            }
        }
        if (lang != null) {
            if (isValueReference(lang)) {
                ValueBinding vb = Util.getValueBinding(lang);
                selectmany.setValueBinding("lang", vb);
            } else {
                selectmany.getAttributes().put("lang", lang);
            }
        }
        if (layout != null) {
            if (isValueReference(layout)) {
                ValueBinding vb = Util.getValueBinding(layout);
                selectmany.setValueBinding("layout", vb);
            } else {
                selectmany.getAttributes().put("layout", layout);
            }
        }
        if (onblur != null) {
            if (isValueReference(onblur)) {
                ValueBinding vb = Util.getValueBinding(onblur);
                selectmany.setValueBinding("onblur", vb);
            } else {
                selectmany.getAttributes().put("onblur", onblur);
            }
        }
        if (onchange != null) {
            if (isValueReference(onchange)) {
                ValueBinding vb = Util.getValueBinding(onchange);
                selectmany.setValueBinding("onchange", vb);
            } else {
                selectmany.getAttributes().put("onchange", onchange);
            }
        }
        if (onclick != null) {
            if (isValueReference(onclick)) {
                ValueBinding vb = Util.getValueBinding(onclick);
                selectmany.setValueBinding("onclick", vb);
            } else {
                selectmany.getAttributes().put("onclick", onclick);
            }
        }
        if (ondblclick != null) {
            if (isValueReference(ondblclick)) {
                ValueBinding vb = Util.getValueBinding(ondblclick);
                selectmany.setValueBinding("ondblclick", vb);
            } else {
                selectmany.getAttributes().put("ondblclick", ondblclick);
            }
        }
        if (onfocus != null) {
            if (isValueReference(onfocus)) {
                ValueBinding vb = Util.getValueBinding(onfocus);
                selectmany.setValueBinding("onfocus", vb);
            } else {
                selectmany.getAttributes().put("onfocus", onfocus);
            }
        }
        if (onkeydown != null) {
            if (isValueReference(onkeydown)) {
                ValueBinding vb = Util.getValueBinding(onkeydown);
                selectmany.setValueBinding("onkeydown", vb);
            } else {
                selectmany.getAttributes().put("onkeydown", onkeydown);
            }
        }
        if (onkeypress != null) {
            if (isValueReference(onkeypress)) {
                ValueBinding vb = Util.getValueBinding(onkeypress);
                selectmany.setValueBinding("onkeypress", vb);
            } else {
                selectmany.getAttributes().put("onkeypress", onkeypress);
            }
        }
        if (onkeyup != null) {
            if (isValueReference(onkeyup)) {
                ValueBinding vb = Util.getValueBinding(onkeyup);
                selectmany.setValueBinding("onkeyup", vb);
            } else {
                selectmany.getAttributes().put("onkeyup", onkeyup);
            }
        }
        if (onmousedown != null) {
            if (isValueReference(onmousedown)) {
                ValueBinding vb = Util.getValueBinding(onmousedown);
                selectmany.setValueBinding("onmousedown", vb);
            } else {
                selectmany.getAttributes().put("onmousedown", onmousedown);
            }
        }
        if (onmousemove != null) {
            if (isValueReference(onmousemove)) {
                ValueBinding vb = Util.getValueBinding(onmousemove);
                selectmany.setValueBinding("onmousemove", vb);
            } else {
                selectmany.getAttributes().put("onmousemove", onmousemove);
            }
        }
        if (onmouseout != null) {
            if (isValueReference(onmouseout)) {
                ValueBinding vb = Util.getValueBinding(onmouseout);
                selectmany.setValueBinding("onmouseout", vb);
            } else {
                selectmany.getAttributes().put("onmouseout", onmouseout);
            }
        }
        if (onmouseover != null) {
            if (isValueReference(onmouseover)) {
                ValueBinding vb = Util.getValueBinding(onmouseover);
                selectmany.setValueBinding("onmouseover", vb);
            } else {
                selectmany.getAttributes().put("onmouseover", onmouseover);
            }
        }
        if (onmouseup != null) {
            if (isValueReference(onmouseup)) {
                ValueBinding vb = Util.getValueBinding(onmouseup);
                selectmany.setValueBinding("onmouseup", vb);
            } else {
                selectmany.getAttributes().put("onmouseup", onmouseup);
            }
        }
        if (onselect != null) {
            if (isValueReference(onselect)) {
                ValueBinding vb = Util.getValueBinding(onselect);
                selectmany.setValueBinding("onselect", vb);
            } else {
                selectmany.getAttributes().put("onselect", onselect);
            }
        }
        if (readonly != null) {
            if (isValueReference(readonly)) {
                ValueBinding vb = Util.getValueBinding(readonly);
                selectmany.setValueBinding("readonly", vb);
            } else {
                boolean _readonly = new Boolean(readonly).booleanValue();
                selectmany.getAttributes().put("readonly", _readonly ? Boolean.TRUE : Boolean.FALSE);
            }
        }
        if (style != null) {
            if (isValueReference(style)) {
                ValueBinding vb = Util.getValueBinding(style);
                selectmany.setValueBinding("style", vb);
            } else {
                selectmany.getAttributes().put("style", style);
            }
        }
        if (styleClass != null) {
            if (isValueReference(styleClass)) {
                ValueBinding vb = Util.getValueBinding(styleClass);
                selectmany.setValueBinding("styleClass", vb);
            } else {
                selectmany.getAttributes().put("styleClass", styleClass);
            }
        }
        if (tabindex != null) {
            if (isValueReference(tabindex)) {
                ValueBinding vb = Util.getValueBinding(tabindex);
                selectmany.setValueBinding("tabindex", vb);
            } else {
                selectmany.getAttributes().put("tabindex", tabindex);
            }
        }
        if (title != null) {
            if (isValueReference(title)) {
                ValueBinding vb = Util.getValueBinding(title);
                selectmany.setValueBinding("title", vb);
            } else {
                selectmany.getAttributes().put("title", title);
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
