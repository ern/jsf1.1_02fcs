/*
 * $Id: UpdateModelValuesPhase.java,v 1.36.32.1 2006/04/12 19:32:20 ofung Exp $
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

package com.sun.faces.lifecycle;

import com.sun.faces.util.MessageFactory;
import com.sun.faces.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;


/**
 * UpdateModelValuesPhase executes <code>processUpdates</code> on each
 * component in the tree so that it may have a chance to update its model value.
 */
public class UpdateModelValuesPhase extends Phase {

//
// Protected Constants
//

//
// Class Variables
//

//
// Instance Variables
//
// Log instance for this class
    protected static Log log = LogFactory.getLog(UpdateModelValuesPhase.class);

// Attribute Instance Variables

// Relationship Instance Variables


//
// Constructors and Genericializers    
//

    public UpdateModelValuesPhase() {
    }

//
// Class methods
//

//
// General Methods
//

//
// Methods from Phase
//

    public PhaseId getId() {
        return PhaseId.UPDATE_MODEL_VALUES;
    }


    public void execute(FacesContext facesContext) {
        if (log.isDebugEnabled()) {
            log.debug("Entering UpdateModelValuesPhase");
        }
        UIComponent component = facesContext.getViewRoot();
        Util.doAssert(null != component);
        String exceptionMessage = null;

        try {
            component.processUpdates(facesContext);
        } catch (IllegalStateException e) {
            exceptionMessage = e.getMessage();
        } catch (FacesException fe) {
            exceptionMessage = fe.getMessage();
        }

        if (null != exceptionMessage) {
            Object[] params = new Object[3];
            params[2] = exceptionMessage;
            facesContext.addMessage(component.getClientId(facesContext),
                                    MessageFactory.getMessage(facesContext,
                                                              Util.MODEL_UPDATE_ERROR_MESSAGE_ID,
                                                              params));
            if (log.isErrorEnabled()) {
                log.error(exceptionMessage);
            }
            if (log.isDebugEnabled()) {
                log.debug("Exiting UpdateModelValuesPhase");
            }
        }
    }



// The testcase for this class is TestUpdateModelValuesPhase.java


} // end of class UpdateModelValuesPhase
