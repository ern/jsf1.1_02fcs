/*
 * $Id: OutputMessageRenderer.java,v 1.15.6.1.4.1 2006/04/12 19:32:25 ofung Exp $
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

// OutputMessageRenderer.java

package com.sun.faces.renderkit.html_basic;

import com.sun.faces.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * <B>OutputMessageRenderer</B> is a class that renderes UIOutput
 */

public class OutputMessageRenderer extends HtmlBasicRenderer {

    // Log instance for this class
    protected static final Log log = LogFactory.getLog(
        OutputMessageRenderer.class);

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

    public OutputMessageRenderer() {
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

    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException(
                Util.getExceptionMessageString(Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
    }


    public void encodeChildren(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException(
                Util.getExceptionMessageString(Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
    }


    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException(Util.getExceptionMessageString(
                Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
        if (log.isTraceEnabled()) {
            log.trace("End encoding component " + component.getId());
        }
        String
            currentValue = null,
            style = (String) component.getAttributes().get("style"),
            styleClass = (String) component.getAttributes().get("styleClass");

        ResponseWriter writer = context.getResponseWriter();
        Util.doAssert(writer != null);

        // suppress rendering if "rendered" property on the component is
        // false.
        if (!component.isRendered()) {
            if (log.isTraceEnabled()) {
                log.trace("End encoding component " + component.getId() +
                          " since rendered attribute is set to false");
            }
            return;
        }
        Object currentObj = ((ValueHolder) component).getValue();
        if (currentObj != null) {
            if (currentObj instanceof String) {
                currentValue = (String) currentObj;
            } else {
                currentValue = currentObj.toString();
            }
        } else {
            // if the value is null, do not output anything.
            return;
        }

        ArrayList parameterList = new ArrayList();

        // get UIParameter children...

        Iterator kids = component.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();

            //PENDING(rogerk) ignore if child is not UIParameter?

            if (!(kid instanceof UIParameter)) {
                continue;
            }

            parameterList.add(((UIParameter) kid).getValue());
        }

        // If at least one substitution parameter was specified,
        // use the string as a MessageFormat instance.
        String message = null;
        if (parameterList.size() > 0) {
            message = MessageFormat.format
                (currentValue, parameterList.toArray
                               (new Object[parameterList.size()]));
        } else {
            message = currentValue;
        }

        boolean wroteSpan = false;
        if (null != styleClass || null != style ||
            Util.hasPassThruAttributes(component) ||
            shouldWriteIdAttribute(component)) {
            writer.startElement("span", component);
            writeIdAttributeIfNecessary(context, writer, component);
            wroteSpan = true;

            if (null != styleClass) {
                writer.writeAttribute("class", styleClass, "styleClass");
            }
            // style is rendered as a passthru attribute
            Util.renderPassThruAttributes(writer, component);
            Util.renderBooleanPassThruAttributes(writer, component);
        }
        Boolean escape = Boolean.TRUE;
        Object val = component.getAttributes().get("escape");
        if (val != null) {
            if (val instanceof Boolean) {
                escape = (Boolean) val;
            } else if (val instanceof String) {
                try {
                    escape = Boolean.valueOf((String) val);
                } catch (Throwable e) {
                }
            }
        }
        if (escape.booleanValue()) {
            writer.writeText(message, "value");
        } else {
            writer.write(message);
        }
        if (wroteSpan) {
            writer.endElement("span");
        }
    }

} // end of class OutputMessageRenderer
