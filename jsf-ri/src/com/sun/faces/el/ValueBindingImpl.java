/*
 * $Id: ValueBindingImpl.java,v 1.35.28.1.2.1 2006/04/12 19:32:07 ofung Exp $
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

import javax.faces.application.Application;
import javax.faces.component.StateHolder;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.faces.el.impl.ElException;
import com.sun.faces.el.impl.Expression;
import com.sun.faces.el.impl.ExpressionInfo;
import com.sun.faces.util.Util;

public class ValueBindingImpl extends ValueBinding implements StateHolder {

//
// Private/Protected Constants
//     
    // Array of faces implicit objects
    private static final String[] FACES_IMPLICIT_OBJECTS = {
        "applicationScope",
        "sessionScope",
        "requestScope",
        "facesContext",
        "cookies",
        "header",
        "headerValues",
        "initParam",
        "param",
        "paramValues",
        "view"
    };


    static {
        // Sort for binary searching
        Arrays.sort(FACES_IMPLICIT_OBJECTS);
    }
    
//
// Class Variables
//

    private static final Log log = LogFactory.getLog(ValueBindingImpl.class);

//
// Instance Variables
//

// Attribute Instance Variables

// Relationship Instance Variables

    protected String ref;
    protected String exprString;
    protected ExpressionInfo exprInfo = new ExpressionInfo();           
   

//
// Constructors and Initializers    
//
    /**
     * <p>Necessary for {@link StateHolder} contract.</p>
     */
    public ValueBindingImpl() {
    }


    public ValueBindingImpl(Application application) {
        Util.parameterNonNull(application);
                       
        exprInfo.setVariableResolver(application.getVariableResolver());
        exprInfo.setPropertyResolver(application.getPropertyResolver());        
        
    }

//
// Class methods
//

//
// General Methods
//
 
    public void setRef(String ref) {        
        Util.parameterNonEmpty(ref);
        this.ref = ref;
        exprString = getExprString(ref);
    }

   
//
// Methods from ValueBinding
//

    public Object getValue(FacesContext context)
        throws EvaluationException, PropertyNotFoundException {
        if (context == null) {
            throw new NullPointerException(
                Util.getExceptionMessageString(Util.NULL_CONTEXT_ERROR_MESSAGE_ID));
        }
        Object result;

        if (log.isDebugEnabled()) {
            log.debug("getValue(ref=" + ref + ")");
        }
        result = getValue(context, ref);
        if (log.isTraceEnabled()) {
            log.trace("-->Returning " + result);
        }
        return result;
    }


    protected Object getValue(FacesContext context, String toEvaluate)
        throws EvaluationException, PropertyNotFoundException {
        Object result;


        try {
            exprInfo.setExpressionString(toEvaluate);
            exprInfo.setExpectedType(Object.class);
            exprInfo.setFacesContext(context);           
            result = Util.getExpressionEvaluator().evaluate(exprInfo);
            if (log.isDebugEnabled()) {
                log.debug("getValue Result:" + result);
            }
        } catch (Throwable e) {
            if (e instanceof ElException) {
                if (log.isDebugEnabled()) {
                    Throwable l = e;
                    Throwable t = ((ElException) e).getCause();
                    if (t != null) {
                        l = t;
                    }
                    log.debug("getValue Evaluation threw exception:", l);
                }                
                throw new EvaluationException(e);
            } else if (e instanceof PropertyNotFoundException) {
                if (log.isDebugEnabled()) {
                    Throwable l = e;
                    Throwable t = ((PropertyNotFoundException) e).getCause();
                    if (t != null) {
                        l = t;
                    }
                    log.debug("getValue Evaluation threw exception:", l);
                }
                // Just rethrow it to keep detailed message                
                throw (PropertyNotFoundException) e;
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("getValue Evaluation threw exception:", e);
                }               
                throw new EvaluationException(e);
            }
        } 
        
        return result;
    }


    public void setValue(FacesContext context, Object value)
        throws EvaluationException, PropertyNotFoundException {
        if (context == null) {
            throw new NullPointerException(
                Util.getExceptionMessageString(Util.NULL_CONTEXT_ERROR_MESSAGE_ID));
        }
        if (isReservedIdentifier(ref)) {
            throw new ReferenceSyntaxException(
                Util.getExceptionMessageString(
                    Util.ILLEGAL_IDENTIFIER_LVALUE_MODE_ID, new Object[]{ref}));
        }
        // PENDING(edburns): check for readOnly-ness        

        try {
            exprInfo.setExpressionString(ref);
            exprInfo.setFacesContext(context);            
            Expression expr =
                Util.getExpressionEvaluator().parseExpression(exprInfo);
            expr.setValue(exprInfo, value);                       
        } catch (Throwable e) {
            if (e instanceof ElException) {
                if (log.isDebugEnabled()) {
                    Throwable l = e;
                    Throwable t = ((ElException) e).getCause();
                    if (t != null) {
                        l = t;
                    }
                    log.debug("setValue Evaluation threw exception:", l);
                }                
                throw new EvaluationException(e);
            } else if (e instanceof PropertyNotFoundException) {
                if (log.isDebugEnabled()) {
                    Throwable l = e;
                    Throwable t = ((PropertyNotFoundException) e).getCause();
                    if (t != null) {
                        l = t;
                    }
                    log.debug("setValue Evaluation threw exception:", l);
                }
                // Just rethrow it to keep detailed message                
                throw (PropertyNotFoundException) e;
            } else if (e instanceof EvaluationException) {
                if (log.isDebugEnabled()) {
                    log.debug("setValue Evaluation threw exception:", e);
                }                
                throw ((EvaluationException) e);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("setValue Evaluation threw exception:", e);
                }                
                throw new EvaluationException(e);
            }
        } 
    }


    public boolean isReadOnly(FacesContext context)
        throws PropertyNotFoundException {
        if (context == null) {
            throw new NullPointerException(
                Util.getExceptionMessageString(Util.NULL_CONTEXT_ERROR_MESSAGE_ID));
        }

        try {
            exprInfo.setExpressionString(ref);
            exprInfo.setFacesContext(context);            
            Expression expr =
                Util.getExpressionEvaluator().parseExpression(exprInfo);
            return expr.isReadOnly(exprInfo);           
        } catch (Throwable e) {
            if (e instanceof ElException) {
                if (log.isDebugEnabled()) {
                    Throwable l = e;
                    Throwable t = ((ElException) e).getCause();
                    if (t != null) {
                        l = t;
                    }
                    log.debug("isReadOnly Evaluation threw exception:", l);
                }
                throw new EvaluationException(e);
            } else if (e instanceof PropertyNotFoundException) {
                if (log.isDebugEnabled()) {
                    Throwable l = e;
                    Throwable t = ((PropertyNotFoundException) e).getCause();
                    if (t != null) {
                        l = t;
                    }
                    log.debug("isReadOnly Evaluation threw exception:", l);
                }
                // Just rethrow it to keep detailed message
                throw (PropertyNotFoundException) e;
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("isReadOnly Evaluation threw exception:", e);
                }
                throw new EvaluationException(e);
            }
        } 
    }


    public Class getType(FacesContext context)
        throws PropertyNotFoundException {
        if (context == null) {
            throw new NullPointerException(
                Util.getExceptionMessageString(Util.NULL_CONTEXT_ERROR_MESSAGE_ID));
        }

        try {
            exprInfo.setExpressionString(ref);
            exprInfo.setFacesContext(context);            
            Expression expr =
                Util.getExpressionEvaluator().parseExpression(exprInfo);
            return expr.getType(exprInfo);            
        } catch (Throwable e) {
            if (e instanceof ElException) {
                if (log.isDebugEnabled()) {
                    Throwable l = e;
                    Throwable t = ((ElException) e).getCause();
                    if (t != null) {
                        l = t;
                    }
                    log.debug("getType Evaluation threw exception:", l);
                }
                throw new EvaluationException(e);
            } else if (e instanceof PropertyNotFoundException) {
                if (log.isDebugEnabled()) {
                    Throwable l = e;
                    Throwable t = ((PropertyNotFoundException) e).getCause();
                    if (t != null) {
                        l = t;
                    }
                    log.debug("getType Evaluation threw exception:", l);
                }
                // Just rethrow it to keep detailed message
                throw (PropertyNotFoundException) e;
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("getType Evaluation threw exception:", e);
                }
                throw new EvaluationException(e);
            }
        } 
    }


    public String getExpressionString() {
        return exprString;
    }

    /**
     * <p>Returns true if the profivided identifier is a reserved identifier,
     * otherwise false.</p>
     *
     * @param identifier the identifier to check
     * @return returns true if the profivided identifier is a
     *         reserved identifier, otherwisefalse
     */
    private boolean isReservedIdentifier(String identifier) {
        return (Arrays.binarySearch(FACES_IMPLICIT_OBJECTS, identifier) >= 0);
    }   

    // 
    // methods from StateHolder
    //

    public Object saveState(FacesContext context) {
        return ref;
    }


    public void restoreState(FacesContext context, Object state) {
        ref = state.toString();
        exprString = getExprString(ref);
        Application application = context.getApplication();
        exprInfo.setPropertyResolver(application.getPropertyResolver());
        exprInfo.setVariableResolver(application.getVariableResolver());
    }


    private boolean isTransient = false;


    public boolean isTransient() {
        return isTransient;
    }


    public void setTransient(boolean newTransientValue) {
        isTransient = newTransientValue;
    }

    // helper methods
    
    private static String getExprString(String ref) {
        return "#{" + ref + '}';
    }

} // end of class ValueBindingImpl
