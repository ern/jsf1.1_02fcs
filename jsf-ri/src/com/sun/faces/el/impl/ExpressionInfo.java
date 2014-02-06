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

/*
 * %W% %G%
 */

package com.sun.faces.el.impl;

import javax.faces.context.FacesContext;
import javax.faces.el.PropertyResolver;
import javax.faces.el.VariableResolver;

public class ExpressionInfo {

    private FacesContext facesContext;
    private FunctionMapper functionMapper;
    private VariableResolver variableResolver;
    private PropertyResolver propertyResolver;
    private String expressionString;
    /**
     * This is public for performance reasons.  
     */
    public final JSPExpressionString jspExpressionString = new JSPExpressionString();
    private Class expectedType;

    /**
     * <p>Reset the state of this instance to be the same as a freshly
     * instantiated instance.</p>
     */
    public void reset() {
	facesContext = null;
	functionMapper = null;
	variableResolver = null;
	propertyResolver = null;
	expressionString = null;
	jspExpressionString.set(null);
    }


    /**
     * Returns the {@see FacesContext}.
     *
     * @return the FacesContext
     */
    public FacesContext getFacesContext() {
        return facesContext;
    }


    /**
     * Sets the {@see FacesContext}.
     *
     * @param facesContext FacesContext
     */
    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }


    /**
     * TODO
     *
     * @return
     */
    public FunctionMapper getFunctionMapper() {
        return functionMapper;
    }


    /**
     * TODO
     *
     * @param functionMapper
     */
    public void setFunctionMapper(FunctionMapper functionMapper) {
        this.functionMapper = functionMapper;
    }


    /**
     * TODO
     *
     * @return
     */
    public VariableResolver getVariableResolver() {
        return variableResolver;
    }


    /**
     * TODO
     *
     * @param variableResolver
     */
    public void setVariableResolver(VariableResolver variableResolver) {
        this.variableResolver = variableResolver;
    }


    public PropertyResolver getPropertyResolver() {
        return propertyResolver;
    }


    public void setPropertyResolver(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }


    /**
     * Returns the expression string with the "${}" delimiters that
     * the JSP 2.0 evaluator requires. An alternative would be to
     * modify the parser, but I think that can wait until a merged
     * JSF/JSP evaluator is designed.
     *
     * @return
     */
    public String getExpressionString() {
        return "${" + expressionString + "}";
    }


    /**
     * TODO
     *
     * @param expressionString
     */
    public void setExpressionString(String expressionString) {
        int index;
        //ExpressionString may contain more than one expression.
        //If it does, change delimeters to ones recognized by the
        //JSP parser.
        while ((index = expressionString.indexOf("#{")) != -1) {
            StringBuffer buf = new StringBuffer();
            buf.append(expressionString.substring(0, index));
            buf.append("$");
            buf.append(expressionString.substring(index + 1));
            expressionString = buf.toString();
        }
        this.expressionString = expressionString;
	this.jspExpressionString.set(expressionString);
    }


    /**
     * TODO
     *
     * @return
     */
    public Class getExpectedType() {
        return expectedType;
    }


    /**
     * TODO
     *
     * @param expectedType
     */
    public void setExpectedType(Class expectedType) {
        this.expectedType = expectedType;
    }

    public static class JSPExpressionString extends Object {

    public static final int ALLOC_INCR = 256;

    public JSPExpressionString() {
	buf = new char[ALLOC_INCR];
	buf[0] = '$';
	buf[1] = '{';
    }

    public char buf[] = null;
    public int bufLen = 0;
    public int bufSize = ALLOC_INCR;
    
    public void set(String newString) {
	if (null == newString) {
	    bufLen = 0;
	    return;
	}
	int newBufSize, len;
	if (bufSize < (newBufSize = ((len = newString.length()) + 3))) {
	    bufSize = newBufSize;
	    buf = new char[bufSize];
	    buf[0] = '$';
	    buf[1] = '{';
	}
	char [] src = newString.toCharArray();
	System.arraycopy(src, 0, buf, 2, len);
	buf[len + 2] = '}'; 
	bufLen = len + 3;
    }

    public String toString() {
	return new String(buf, 0, bufLen);
    }
}
}
