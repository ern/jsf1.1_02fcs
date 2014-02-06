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
import javax.faces.component.UIInput;
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


public class InputHiddenTag extends UIComponentTag {


    public static Log log = LogFactory.getLog(InputHiddenTag.class);

    //
    // Instance Variables
    //

    private java.lang.String converter;
    private java.lang.String immediate;
    private java.lang.String required;
    private java.lang.String validator;
    private java.lang.String value;
    private java.lang.String valueChangeListener;


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


    //
    // General Methods
    //

    public String getRendererType() { return "javax.faces.Hidden"; }
    public String getComponentType() { return "javax.faces.HtmlInputHidden"; }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIInput input = null;
        try {
            input = (UIInput)component;
        }
        catch (ClassCastException cce) {
          throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: UIInput.  Perhaps you're missing a tag?");
        }

        if (converter != null) {
            if (isValueReference(converter)) {
                      ValueBinding vb = 
                          Util.getValueBinding(converter);
                  input.setValueBinding("converter", vb);
            } else {
                Converter _converter = FacesContext.getCurrentInstance().
                    getApplication().createConverter(converter);
                input.setConverter(_converter);
            }
        }

        if (immediate != null) {
            if (isValueReference(immediate)) {
                ValueBinding vb = Util.getValueBinding(immediate);
                input.setValueBinding("immediate", vb);
            } else {
                boolean _immediate = new Boolean(immediate).booleanValue();
                input.setImmediate(_immediate);
            }
        }
        if (required != null) {
            if (isValueReference(required)) {
                ValueBinding vb = Util.getValueBinding(required);
                input.setValueBinding("required", vb);
            } else {
                boolean _required = new Boolean(required).booleanValue();
                input.setRequired(_required);
            }
        }
        if (validator != null) {
            if (isValueReference(validator)) {
                Class args[] = { FacesContext.class, UIComponent.class, Object.class };
                MethodBinding vb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(validator, args);
                input.setValidator(vb);
            } else {
              Object params [] = {validator};
              throw new javax.faces.FacesException(Util.getExceptionMessageString(Util.INVALID_EXPRESSION_ID, params));
            }
            }
        if (value != null) {
            if (isValueReference(value)) {
                ValueBinding vb = Util.getValueBinding(value);
                input.setValueBinding("value", vb);
            } else {
                input.setValue(value);
            }
        }
        if (valueChangeListener != null) {
            if (isValueReference(valueChangeListener)) {
                Class args[] = { ValueChangeEvent.class };
                MethodBinding vb = FacesContext.getCurrentInstance().getApplication().createMethodBinding(valueChangeListener, args);
                input.setValueChangeListener(vb);
            } else {
              Object params [] = {valueChangeListener};
              throw new javax.faces.FacesException(Util.getExceptionMessageString(Util.INVALID_EXPRESSION_ID, params));
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
