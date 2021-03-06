/*
 * $Id: ApplyRequestValuesPhase.java,v 1.17.32.1 2006/04/12 19:32:19 ofung Exp $
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

import com.sun.faces.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

/**
 * ApplyRequestValuesPhase executes <code>processDecodes</code> on each
 * component in the tree so that it may update it's current value from the
 * information included in the current request (parameters, headers, c
 * cookies and so on.)
 */
public class ApplyRequestValuesPhase extends Phase {

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
    protected static Log log = LogFactory.getLog(ApplyRequestValuesPhase.class);

    // Relationship Instance Variables

    //
    // Constructors and Genericializers    
    //

    public ApplyRequestValuesPhase() {
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
        return PhaseId.APPLY_REQUEST_VALUES;
    }


    public void execute(FacesContext facesContext) throws FacesException {

        if (log.isDebugEnabled()) {
            log.debug("Entering ApplyRequestValuesPhase");
        }

        UIComponent component = facesContext.getViewRoot();
        Util.doAssert(null != component);

        try {
            component.processDecodes(facesContext);
        } catch (RuntimeException re) {
            String exceptionMessage = re.getMessage();
            if (null != exceptionMessage) {
                if (log.isErrorEnabled()) {
                    log.error(exceptionMessage, re);
                }
            }
            throw new FacesException(exceptionMessage, re);
        }
        if (log.isDebugEnabled()) {
            log.debug("Exiting ApplyRequestValuesPhase");
        }
    }

    // The testcase for this class is TestApplyRequestValuesPhase.java

} // end of class ApplyRequestValuesPhase
