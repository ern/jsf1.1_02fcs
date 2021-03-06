/*
 * $Id: MethodBindingImpl.java,v 1.6.32.1 2006/04/12 19:32:06 ofung Exp $
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

package com.sun.faces.el;


import com.sun.faces.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.application.Application;
import javax.faces.component.StateHolder;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * <p>Implementation of {@link MethodBinding}.</p>
 */

public class MethodBindingImpl extends MethodBinding implements StateHolder {


    private static final Log log = LogFactory.getLog(MethodBindingImpl.class);

    // ------------------------------------------------------------ Constructors

    public MethodBindingImpl() {
    }


    public MethodBindingImpl(Application application, String ref,
                             Class args[]) {

        if ((application == null) || (ref == null)) {
            throw new NullPointerException();
        }
        if (!(ref.startsWith("#{") && ref.endsWith("}"))) {
            if (log.isErrorEnabled()) {
                log.error(" Expression " + ref +
                          " does not follow the syntax #{...}");
            }
            throw new ReferenceSyntaxException(ref);
        }
        rawRef = ref;
        ref = Util.stripBracketsIfNecessary(ref);

        this.args = args;
        String vbRef = null;

        if (ref.endsWith("]")) {
            int left = ref.lastIndexOf("[");
            if (left < 0) {
                if (log.isDebugEnabled()) {
                    log.debug("Expression syntax error: Missing '[' in " + ref);
                }
                throw new ReferenceSyntaxException(ref);
            }
            // createValueBinding expects the expression in the VBL syntax,
            // which is of the form #{....}". So make ref conform to that.
            vbRef = "#{" + (ref.substring(0, left)) + "}";
            this.vb = application.createValueBinding(vbRef);
            this.name = ref.substring(left + 1);
            this.name = this.name.substring(0, this.name.length() - 1);
        } else {
            int period = ref.lastIndexOf(".");
            if (period < 0) {
                if (log.isDebugEnabled()) {
                    log.debug("Expression syntax error: Missing '.' in " + ref);
                }
                throw new ReferenceSyntaxException(ref);
            }
            // createValueBinding expects the expression in the VBL syntax,
            // which is of the form #{....}". So make ref conform to that.
            vbRef = "#{" + (ref.substring(0, period)) + "}";
            this.vb = application.createValueBinding(vbRef);
            this.name = ref.substring(period + 1);
        }
        if (this.name.length() < 1) {
            if (log.isDebugEnabled()) {
                log.debug(
                    "Expression syntax error: Missing name after period in:" +
                    ref);
            }
            throw new ReferenceSyntaxException(ref);
        }

    }


    // ------------------------------------------------------ Instance Variables


    private Class args[];
    private String name;
    private String rawRef;
    private ValueBinding vb;


    // --------------------------------------------------- MethodBinding Methods


    public Object invoke(FacesContext context, Object params[])
        throws EvaluationException, MethodNotFoundException {

        if (context == null) {
            throw new NullPointerException();
        }
        Object base = vb.getValue(context);
        Method method = method(base);
        try {
            return (method.invoke(base, params));
        } catch (IllegalAccessException e) {
            throw new EvaluationException(e);
        } catch (InvocationTargetException ite) {
            throw new EvaluationException(ite.getTargetException());
        }

    }


    public Class getType(FacesContext context) {

        Object base = vb.getValue(context);
        Method method = method(base);
        Class returnType = method.getReturnType();
        if (log.isDebugEnabled()) {
            log.debug("Method return type:" + returnType.getName());
        }
        if ("void".equals(returnType.getName())) {
            return (Void.class);
        } else {
            return (returnType);
        }

    }


    public String getExpressionString() {
        return rawRef;
    }


    // ----------------------------------------------------- StateHolder Methods

    public Object saveState(FacesContext context) {
        Object values[] = new Object[4];
        values[0] = name;
        values[1] = UIComponentBase.saveAttachedState(context, vb);
        values[2] = args;
        values[3] = rawRef;
        return (values);
    }


    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        name = (String) values[0];
        vb = (ValueBinding) UIComponentBase.restoreAttachedState(context,
                                                                 values[1]);
        args = (Class[]) values[2];
        rawRef = (String) values[3];
    }


    private boolean transientFlag = false;


    public boolean isTransient() {
        return (this.transientFlag);
    }


    public void setTransient(boolean transientFlag) {
        this.transientFlag = transientFlag;
    }



    // --------------------------------------------------------- Private Methods


    private Method method(Object base) {
        if (null == base) {
            throw new MethodNotFoundException(name);
        }

        Class clazz = base.getClass();
        try {
            return (clazz.getMethod(name, args));
        } catch (NoSuchMethodException e) {
            throw new MethodNotFoundException(name + ": " + e.getMessage());
        }

    }


}
