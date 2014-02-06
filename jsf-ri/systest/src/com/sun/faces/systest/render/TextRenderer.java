/*
 * $Id: TextRenderer.java,v 1.9.32.1 2006/04/12 19:32:44 ofung Exp $
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

// TextRenderer.java

package com.sun.faces.systest.render;

import com.sun.faces.util.Util;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import java.io.IOException;

/**
 * <B>TextRenderer</B> is a class ...
 *
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TextRenderer.java,v 1.9.32.1 2006/04/12 19:32:44 ofung Exp $
 * @see	Blah
 * @see	Bloo
 */

public class TextRenderer extends Renderer {

    //
    // Protected Constants
    //

    //
    // Class Variables
    //

    //
    // Instance Variables
    //

    // Attribute Instance Variables


    // Relationship Instance Variables

    //
    // Constructors and Initializers    
    //

    public TextRenderer() {
        super();
    }

    //
    // Class methods
    //

    //
    // General Methods
    //

    //
    // Methods From Renderer
    //

    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException(Util.getExceptionMessageString(
                Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
    }


    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException(Util.getExceptionMessageString(
                Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
    }


    public void encodeChildren(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException(Util.getExceptionMessageString(
                Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
    }


    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        Util.doAssert(writer != null);

        String styleClass = null;

        if (context == null || component == null) {
            throw new NullPointerException(Util.getExceptionMessageString(
                Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
        
        // suppress rendering if "rendered" property on the component is
        // false.
        if (!component.isRendered()) {
            return;
        }

        writer.writeText("This IS TEXT FROM THE CUSTOM RENDERER", null);
    }


    public String convertClientId(FacesContext context, String clientId) {
        return clientId;
    }

    /* Replaced by convertClientId() above
    public String getClientId(FacesContext context, UIComponent component){
	String clientId = null;
	NamingContainer closestContainer = null;
	UIComponent containerComponent = component;

        // Search for an ancestor that is a naming container
        while (null != (containerComponent = 
                        containerComponent.getParent())) {
            if (containerComponent instanceof NamingContainer) {
                closestContainer = (NamingContainer) containerComponent;
                break;
            }
        }

        // If none is found, see if this is a naming container
        if (null == closestContainer && component instanceof NamingContainer) {
            closestContainer = (NamingContainer) component;
        }

        if (null != closestContainer) {

            // If there is no componentId, generate one and store it
            if (component.getId() == null) {
                // Don't call setId() because it checks for
                // uniqueness.  No need.
                clientId = closestContainer.generateClientId();
            } else {
                clientId = component.getId();
            }

            // build the client side id
            containerComponent = (UIComponent) closestContainer;

            // If this is the root naming container, break
            if (null != containerComponent.getParent()) {
                clientId = containerComponent.getClientId(context) +
                    NamingContainer.SEPARATOR_CHAR + clientId;
            }
        }

        if (null == clientId) {
	    throw new NullPointerException();
	}
	return (clientId);
    }
    */

} // end of class TextRenderer
