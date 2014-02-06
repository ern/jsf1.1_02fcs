/*
 * $Id: VariableResolverImpl.java,v 1.20.28.1.2.1 2006/04/12 19:32:07 ofung Exp $
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

import com.sun.faces.RIConstants;

import com.sun.faces.application.ApplicationAssociate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.VariableResolver;


/**
 * <p>Concrete implementation of <code>VariableResolver</code>.</p>
 */

public class VariableResolverImpl extends VariableResolver {

    private static final Log log = LogFactory.getLog(
        VariableResolverImpl.class);

    //
    // Relationship Instance Variables
    // 

    // Specified by javax.faces.el.VariableResolver.resolveVariable()
    public Object resolveVariable(FacesContext context, String name)
        throws EvaluationException {

        ExternalContext ec = context.getExternalContext();

        if (RIConstants.APPLICATION_SCOPE.equals(name)) {
            return (ec.getApplicationMap());
        } else if (RIConstants.COOKIE_IMPLICIT_OBJ.equals(name)) {
            return (ec.getRequestCookieMap());
        } else if (RIConstants.FACES_CONTEXT_IMPLICIT_OBJ.equals(name)){
            return (context);
        } else if (RIConstants.HEADER_IMPLICIT_OBJ.equals(name)) {
            return (ec.getRequestHeaderMap());
        } else if (RIConstants.HEADER_VALUES_IMPLICIT_OBJ.equals(name)){
            return (ec.getRequestHeaderValuesMap());
        } else if (RIConstants.INIT_PARAM_IMPLICIT_OBJ.equals(name)) {
            return (ec.getInitParameterMap());
        } else if (RIConstants.PARAM_IMPLICIT_OBJ.equals(name)) {
            return (ec.getRequestParameterMap());
        } else if (RIConstants.PARAM_VALUES_IMPLICIT_OBJ.equals(name)) {
            return (ec.getRequestParameterValuesMap());
        } else if (RIConstants.REQUEST_SCOPE.equals(name)) {
            return (ec.getRequestMap());
        } else if (RIConstants.SESSION_SCOPE.equals(name)) {
            return (ec.getSessionMap());
        } else if (RIConstants.VIEW_IMPLICIT_OBJ.equals(name)) {
            return (context.getViewRoot());
        } else {
            // do the scoped lookup thing
            Object value = null;

            if (null == (value = ec.getRequestMap().get(name))) {
                if (null == (value = ec.getSessionMap().get(name))) {
                    if (null == (value = ec.getApplicationMap().get(name))) {
// if it's a managed bean try and create it
			ApplicationAssociate associate = 
			    ApplicationAssociate.getInstance(context.getExternalContext());
			
                        if (null != associate) {
                            value =
                            associate.createAndMaybeStoreManagedBeans(context, 
								      name);
                        }
                    }
                }
            }
            if (log.isDebugEnabled()) {
                log.debug("resolveVariable: Resolved variable:" + value);
            }
            return (value);
        }

    }
}
