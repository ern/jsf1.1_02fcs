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

import com.sun.faces.el.impl.ElException;
import com.sun.faces.el.impl.Expression;
import com.sun.faces.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;

import java.util.Iterator;
import java.util.List;

/**
 * PENDING (hans) The main reason for extending ValueBindingImpl
 * instead of ValueBinding is that the ManagedBeanFactory class
 * casts to ValueBindingImpl and calls getScope(), see the
 * comment about this method in ValueBindingImpl for details.
 */
public class MixedELValueBinding extends ValueBindingImpl {

    private static final Log log =
        LogFactory.getLog(MixedELValueBinding.class);
    
    private List expressions = null;
    
    public MixedELValueBinding() { }

    public MixedELValueBinding(Application application) {
        super(application);
        exprInfo.setExpectedType(String.class);        
    }


    public void setRef(String ref) {
        Util.parameterNonEmpty(ref);
        this.ref = ref;
        expressions = null;
    }


    public Object getValue(FacesContext context)
        throws EvaluationException, PropertyNotFoundException {
        if (context == null) {
            throw new NullPointerException(
                Util.getExceptionMessageString(Util.NULL_CONTEXT_ERROR_MESSAGE_ID));
        }
        if (expressions == null) {
            MixedELValueParser parser = new MixedELValueParser();
            try {
                expressions = parser.parse(context, ref);
            } catch (ElException e) {
                Throwable t = e;
                Throwable cause = e.getCause();
                if (cause != null) {
                    t = cause;
                }
                if (log.isDebugEnabled()) {
                    log.debug("getValue Evaluation threw exception:", t);
                }
                throw new EvaluationException(t);
            }
        }

        StringBuffer sb = new StringBuffer();
        Iterator i = expressions.iterator();                       
        exprInfo.setFacesContext(context);        
        while (i.hasNext()) {
            Object o = i.next();
            if (o instanceof Expression) {
                try {
                    Object value = ((Expression) o).evaluate(exprInfo);
                    if (value != null) {
                        sb.append(value);
                    } // null values are effectively zero length strings
                } catch (ElException e) {
                    Throwable t = e;
                    Throwable cause = e.getCause();
                    if (cause != null) {
                        t = cause;
                    }
                    if (log.isDebugEnabled()) {
                        log.debug("getValue Evaluation threw exception:", t);
                    }
                    throw new EvaluationException(t);
                } 
            } else {
                sb.append(o);
            }
        }
        return sb.toString();
    }


    public void setValue(FacesContext context, Object value)
        throws EvaluationException, PropertyNotFoundException {
        Object[] params = {ref};
        throw new PropertyNotFoundException(Util.getExceptionMessageString(
            Util.ILLEGAL_MODEL_REFERENCE_ID, params));
    }


    public boolean isReadOnly(FacesContext context)
        throws PropertyNotFoundException {
        return true;
    }


    public Class getType(FacesContext context)
        throws PropertyNotFoundException {
        return String.class;
    }


    public String getExpressionString() {
        return ref;
    }


    public String getScope(String valueBinding) {
        return null;
    }

    // StateHolder methods

    public Object saveState(FacesContext context) {
        return ref;
    }


    public void restoreState(FacesContext context, Object state) {
        super.restoreState(context, state);
        exprInfo.setExpectedType(String.class);
    }


    private boolean isTransient = false;


    public boolean isTransient() {
        return isTransient;
    }


    public void setTransient(boolean isTransient) {
        this.isTransient = isTransient;
    }
}
