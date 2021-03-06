/*
 * $Id: RenderKitFactoryImpl.java,v 1.20.42.1.2.1 2006/04/12 19:32:21 ofung Exp $
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

package com.sun.faces.renderkit;

import com.sun.faces.util.Util;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

import java.util.HashMap;
import java.util.Iterator;


public class RenderKitFactoryImpl extends RenderKitFactory {

//
// Protected Constants
//
    protected String renderKitId = null;
    protected String className = null;
    protected HashMap renderKits = null;

//
// Class Variables
//

// Attribute Instance Variables

// Relationship Instance Variables

//
// Constructors and Initializers
//
    /**
     * Constructor registers default Render kit.
     */
    public RenderKitFactoryImpl() {
        super();
        renderKits = new HashMap();
        addRenderKit(HTML_BASIC_RENDER_KIT, new RenderKitImpl());
    }


    public void addRenderKit(String renderKitId, RenderKit renderKit) {

        if (renderKitId == null || renderKit == null) {
            String message = Util.getExceptionMessageString
                (Util.NULL_PARAMETERS_ERROR_MESSAGE_ID);
            message = message + " renderKitId " + renderKitId + 
                " renderKit " + renderKit;
            throw new NullPointerException(message);
        }

        synchronized (renderKits) {
            renderKits.put(renderKitId, renderKit);
        }
    }


    public RenderKit getRenderKit(FacesContext context, String renderKitId) {

        if (renderKitId == null) {
            String message = Util.getExceptionMessageString
                (Util.NULL_PARAMETERS_ERROR_MESSAGE_ID);
            message = message + " renderKitId " + renderKitId;
            throw new NullPointerException(message);
        }
        //PENDING (rogerk) do something with FacesContext ...
        //
        // If an instance already exists, return it.
        //
        return (RenderKit) renderKits.get(renderKitId);
    }


    public Iterator getRenderKitIds() {
        return (renderKits.keySet().iterator());
    }

}
