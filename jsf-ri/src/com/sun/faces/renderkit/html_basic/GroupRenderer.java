/*
 * $Id: GroupRenderer.java,v 1.22.34.1 2006/04/12 19:32:23 ofung Exp $
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

package com.sun.faces.renderkit.html_basic;

import com.sun.faces.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import java.io.IOException;
import java.util.Iterator;

/**
 * Arbitrary grouping "renderer" that simply renders its children
 * recursively in the <code>encodeEnd()</code> method.
 *
 * @version $Id: GroupRenderer.java,v 1.22.34.1 2006/04/12 19:32:23 ofung Exp $
 */
public class GroupRenderer extends HtmlBasicRenderer {

    //
    // Protected Constants
    //
    // Log instance for this class
    protected static Log log = LogFactory.getLog(GroupRenderer.class);

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

    public GroupRenderer() {
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

    public boolean getRendersChildren() {
        return true;
    }


    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        if (log.isTraceEnabled()) {
            log.trace("Begin encoding component " +
                      component.getId());
        }
        // suppress rendering if "rendered" property on the component is
        // false.
        if (!component.isRendered()) {
            if (log.isTraceEnabled()) {
                log.trace("End encoding component " +
                          component.getId() + " since " +
                          "rendered attribute is set to false ");
            }
            return;
        }

        // Render a span around this group if necessary
        String
            style = (String) component.getAttributes().get("style"),
            styleClass = (String) component.getAttributes().get("styleClass");
        ResponseWriter writer = context.getResponseWriter();

        if (spanned(component)) {
            writer.startElement("span", component);
            writeIdAttributeIfNecessary(context, writer, component);
            if (styleClass != null) {
                writer.writeAttribute("class", styleClass, "styleClass");
            }
            if (style != null) {
                writer.writeAttribute("style", style, "style");
            }
        }

    }


    public void encodeChildren(FacesContext context, UIComponent component)
        throws IOException {
        if (log.isTraceEnabled()) {
            log.trace("Begin encoding children " + component.getId());
        }
        // suppress rendering if "rendered" property on the component is
        // false.
        if (!component.isRendered()) {
            if (log.isTraceEnabled()) {
                log.trace("End encoding component " +
                          component.getId() + " since " +
                          "rendered attribute is set to false ");
            }
            return;
        }

        // Render our children recursively
        Iterator kids = getChildren(component);
        while (kids.hasNext()) {
            encodeRecursive(context, (UIComponent) kids.next());
        }
        if (log.isTraceEnabled()) {
            log.trace("End encoding children " + component.getId());
        }

    }


    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException(Util.getExceptionMessageString(
                Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
        // suppress rendering if "rendered" property on the component is
        // false.
        if (!component.isRendered()) {
            if (log.isTraceEnabled()) {
                log.trace("End encoding component " +
                          component.getId() + " since " +
                          "rendered attribute is set to false ");
            }
            return;
        }

        // Close our span element if necessary
        ResponseWriter writer = context.getResponseWriter();
        if (spanned(component)) {
            writer.endElement("span");
        }
        if (log.isTraceEnabled()) {
            log.trace("End encoding component " +
                      component.getId());
        }

    }


    /**
     * <p>Return true if we need to render a span element around this group.
     *
     * @param component <code>UIComponent</code> for this group
     */
    private boolean spanned(UIComponent component) {
        if (shouldWriteIdAttribute(component) ||
            (component.getAttributes().get("style") != null) ||
            (component.getAttributes().get("styleClass") != null)) {
            return (true);
        } else {
            return (false);
        }

    }


}
