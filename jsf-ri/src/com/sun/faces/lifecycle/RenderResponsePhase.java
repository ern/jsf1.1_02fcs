/*
 * $Id: RenderResponsePhase.java,v 1.14.40.1 2006/04/12 19:32:20 ofung Exp $
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

// RenderResponsePhase.java

package com.sun.faces.lifecycle;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import java.io.IOException;


/**
 * <B>Lifetime And Scope</B> <P> Same lifetime and scope as
 * DefaultLifecycleImpl.
 *
 * @version $Id: RenderResponsePhase.java,v 1.14.40.1 2006/04/12 19:32:20 ofung Exp $
 */

public class RenderResponsePhase extends Phase {

//
// Protected Constants
//
// Log instance for this class
    protected static Log log = LogFactory.getLog(RenderResponsePhase.class);

//
// Class Variables
//

//
// Instance Variables
//

// Attribute Instance Variables

// Relationship Instance Variables

//
// Constructors and Genericializers    
//

    public RenderResponsePhase() {
        super();
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
        return PhaseId.RENDER_RESPONSE;
    }


    public void execute(FacesContext facesContext) throws FacesException {
        if (log.isDebugEnabled()) {
            log.debug("Entering RenderResponsePhase");
        }
        if (log.isDebugEnabled()) {
            log.debug(
                "About to render view " +
                facesContext.getViewRoot().getViewId());
        }
        try {
            facesContext.getApplication().getViewHandler().
                renderView(facesContext, facesContext.getViewRoot());
        } catch (IOException e) {
            throw new FacesException(e.getMessage(), e);
        }
        if (log.isDebugEnabled()) {
            log.debug("Exiting RenderResponsePhase");
        }
    }



// The testcase for this class is TestRenderResponsePhase.java


} // end of class RenderResponsePhase
