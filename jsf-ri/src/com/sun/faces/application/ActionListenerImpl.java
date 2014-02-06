/*
 * $Id: ActionListenerImpl.java,v 1.10.36.1 2006/04/12 19:32:02 ofung Exp $
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

package com.sun.faces.application;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.ActionSource;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * This action listener implementation processes action events during the
 * <em>Apply Request Values</em> or <em>Invoke Application</em>
 * phase of the request processing lifecycle (depending upon the
 * <code>immediate</code> property of the {@link ActionSource} that
 * queued this event.  It invokes the specified application action method,
 * and uses the logical outcome value to invoke the default navigation handler
 * mechanism to determine which view should be displayed next.</p>
 */
public class ActionListenerImpl implements ActionListener {

    // Log instance for this class
    protected static Log log = LogFactory.getLog(ActionListenerImpl.class);


    //
    // Constructors and Initializers
    //
    public ActionListenerImpl() {
    }

    //
    // Class Methods
    //

    //
    // General Methods
    //

    public void processAction(ActionEvent event) {
        if (log.isDebugEnabled()) {
            log.debug("processAction(" + event.getComponent().getId() + ")");
        }
        UIComponent source = event.getComponent();
        ActionSource actionSource = (ActionSource) source;
        FacesContext context = FacesContext.getCurrentInstance();

        Application application = context.getApplication();

        String outcome = null;
        MethodBinding binding = null;

	binding = actionSource.getAction();
	if (binding != null) {
	    try {
		outcome = (String) binding.invoke(context, null);
	    } catch (MethodNotFoundException e) {
		throw new FacesException
                    (binding.getExpressionString() + ": " + e, e);
	    }
	    catch (EvaluationException e) {
		throw new FacesException
                    (binding.getExpressionString() + ": " + e, e);
	    }
	}

        // Retrieve the NavigationHandler instance..

        NavigationHandler navHandler = application.getNavigationHandler();

        // Invoke nav handling..

        navHandler.handleNavigation(context,
                                    (null != binding) ?
                                    binding.getExpressionString() : null,
                                    outcome);

        // Trigger a switch to Render Response if needed
        context.renderResponse();

    }
}
