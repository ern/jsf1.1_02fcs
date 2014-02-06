/*
 * $Id: LinkRenderer.java,v 1.10.38.1.2.1 2006/04/12 19:32:24 ofung Exp $
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

// LinkRenderer.java

package com.sun.faces.renderkit.html_basic;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;

import java.io.IOException;


/**
 * <B>LinkRenderer</B> acts as superclass for CommandLinkRenderer and
 * OutputLinkRenderer.
 */

public abstract class LinkRenderer extends HtmlBasicRenderer {

    
    // ------------------------------------------------------- Protected Methods
    
    protected void writeCommonLinkAttributes(ResponseWriter writer,
                                             UIComponent component) 
    throws IOException {
        
        // render type attribute that is common only to link renderers
        String type = (String) component.getAttributes().get("type");
        
        if (type != null) {
            writer.writeAttribute("type", type, "type");
        }
        
        // handle styleClass
        String styleClass = (String) 
             component.getAttributes().get("styleClass");
        if (styleClass != null) {
            writer.writeAttribute("class", styleClass, "styleClass");
        }
    }

   

} // end of class LinkRenderer
