/*
 * $Id: TestFacesContextImpl.java,v 1.47.40.1 2006/04/12 19:33:04 ofung Exp $
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

// TestFacesContextImpl.java

package com.sun.faces.context;

import com.sun.faces.ServletFacesTestCase;
import com.sun.faces.lifecycle.LifecycleImpl;
import com.sun.faces.util.Util;

import javax.faces.application.FacesMessage;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

/**
 * <B>TestFacesContextImpl</B> is a class ...
 * <p/>
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TestFacesContextImpl.java,v 1.47.40.1 2006/04/12 19:33:04 ofung Exp $
 */

public class TestFacesContextImpl extends ServletFacesTestCase {

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

    public TestFacesContextImpl() {
        super("TestFacesContext");
    }


    public TestFacesContextImpl(String name) {
        super(name);
    }
//
// Class methods
//

//
// Methods from TestCase
//
    public void setUp() {
        super.setUp();
        UIViewRoot viewRoot = Util.getViewHandler(getFacesContext()).createView(getFacesContext(), null);
        viewRoot.setViewId("viewId");
        getFacesContext().setViewRoot(viewRoot);
    }

//
// General Methods
//

    public void testConstructor() {
        ExternalContextImpl ecImpl =
            new ExternalContextImpl(getConfig().getServletContext(),
                                    getRequest(), getResponse());
        LifecycleImpl lifeImpl = new LifecycleImpl();
        try {
            FacesContextImpl fImpl = new FacesContextImpl(null, null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(true);
        }
        try {
            FacesContextImpl fImpl = new FacesContextImpl(ecImpl, null);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(true);
        }
        try {
            FacesContextImpl fImpl = new FacesContextImpl(null, lifeImpl);
            assertTrue(false);
        } catch (NullPointerException npe) {
            assertTrue(true);
        }
        try {
            FacesContextImpl fImpl = new FacesContextImpl(ecImpl, lifeImpl);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }


    public void testAccessors() {
        boolean result = false;
        boolean exceptionThrown = false;
        ServletRequest req = null;
        ServletResponse resp = null;
        ServletContext sc = null;

        UIViewRoot page = Util.getViewHandler(getFacesContext()).createView(getFacesContext(), null);
        page.setViewId("viewId");
        getFacesContext().setViewRoot(page);
        UIViewRoot root = getFacesContext().getViewRoot();
        result = null != root;
        System.out.println("Testing getViewRoot: " + result);
        assertTrue(result);

        ResponseStream responseStream = new ResponseStream() {
            public void write(int b) {
            }
        };
        getFacesContext().setResponseStream(responseStream);
        result = responseStream == getFacesContext().getResponseStream();
        assertTrue(result);
        System.out.println("Testing responseStream: " + result);

//PENDING(rogerk) JSF_API_20030718 - implement (ResponseWriter related mods..
        ResponseWriter responseWriter = new ResponseWriter() {
            public void close() {
            }


            public void flush() {
            }


            public void write(char[] cbuf, int off, int len) {
            }


            public ResponseWriter cloneWithWriter(Writer writer) {
                return null;
            }


            public void writeText(char text[], int off, int len) {
            }


            public void writeText(char text[]) {
            }


            public void writeText(char text) {
            }


            public void writeText(Object text) {
            }


            public void writeComments(Object text) {
            }


            public void writeComment(Object text) {
            }


            public void writeURIAttribute(String name, Object value) {
            }


            public void writeAttribute(String name, Object value) {
            }


            public void endElement(String name) {
            }


            public void startElement(String name) {
            }


            public void endDocument() {
            }


            public void startDocument() {
            }


            public String getCharacterEncoding() {
                return null;
            }


            public String getContentType() {
                return null;
            }


            public void startElement(String name, UIComponent componentForElement)
                throws IOException {
            }


            public void writeAttribute(String name, Object value, String componentPropertyName)
                throws IOException {
            }


            public void writeURIAttribute(String name, Object value, String componentPropertyName)
                throws IOException {
            }


            public void writeText(Object text, String componentPropertyName)
                throws IOException {
            }
        };
/*    ResponseWriter responseWriter = null;
    try {
        responseWriter = getFacesContext().getResponseWriter();
    } catch ( Exception e ) {
        assertTrue(false);
    }    
*/
        getFacesContext().setResponseWriter(responseWriter);
        result = responseWriter == getFacesContext().getResponseWriter();
        assertTrue(result);
        System.out.println("Testing responseWriter: " + result);

        // test null response writer exception //
        try {
            getFacesContext().setResponseWriter(null);
        } catch (Exception e) {
            if (-1 == e.getMessage().indexOf("esponseWriter")) {
                assertTrue(false);
            }
        }
    }


    public void testRenderingControls() {
        System.out.println("Testing renderResponse()");
        getFacesContext().renderResponse();
        assertTrue(getFacesContext().getRenderResponse());
        System.out.println("Testing responseComplete()");
        getFacesContext().responseComplete();
        assertTrue(getFacesContext().getResponseComplete());
    }


    public void testCurrentInstance() {
        System.out.println("Testing getCurrentInstance()");
        FacesContext context = getFacesContext();
        assertTrue(context == FacesContext.getCurrentInstance());
    }


    public void testMessageMethodsNull() {
        boolean gotException = false;

        FacesContext fc = getFacesContext();
        assertTrue(fc != null);

        try {
            fc.addMessage(null, null);
        } catch (NullPointerException fe) {
            gotException = true;
        }
        assertTrue(gotException);
        gotException = false;

        try {
            fc.addMessage(null, null);
        } catch (NullPointerException fe) {
            gotException = true;
        }
        assertTrue(gotException);
        gotException = false;

    }


    public void testMessageMethods() {
        FacesContext fc = getFacesContext();
        assertTrue(fc != null);

        System.out.println("Testing add methods");
        FacesMessage msg1 = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                             "summary1", "detail1");
        fc.addMessage(null, msg1);

        FacesMessage msg2 = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                             "summary2", "detail2");
        fc.addMessage(null, msg2);

        UICommand command = new UICommand();
        FacesMessage msg3 = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                             "summary3", "detail3");
        fc.addMessage(command.getClientId(fc), msg3);

        FacesMessage msg4 = new FacesMessage(FacesMessage.SEVERITY_WARN,
                                             "summary4", "detail4");
        fc.addMessage(command.getClientId(fc), msg4);

        System.out.println("Testing get methods");
        assertTrue(fc.getMaximumSeverity() == FacesMessage.SEVERITY_FATAL);

        Iterator it = fc.getMessages();
        while (it.hasNext()) {
            FacesMessage result = (FacesMessage) it.next();
            assertTrue(result.equals(msg1) || result.equals(msg2) ||
                       result.equals(msg3) || result.equals(msg4));
        }

        it = null;
        it = fc.getMessages(command.getClientId(fc));
        while (it.hasNext()) {
            FacesMessage result = (FacesMessage) it.next();
            assertTrue(result.equals(msg3) || result.equals(msg4));
        }

        it = null;
        it = fc.getMessages(null);
        while (it.hasNext()) {
            FacesMessage result = (FacesMessage) it.next();
            //System.out.println("summary " + result.getSummary());
            assertTrue(result.equals(msg1) || result.equals(msg2));
        }
    }


    public void testGetApplication() {
        FacesContext fc = getFacesContext();
        assertTrue(fc != null);

        assertTrue(null != fc.getApplication());
    }


    public void testRelease() {
        System.out.println("Testing release method");
        FacesContext context = getFacesContext();
        context.release();
        boolean exceptionThrown = false;
        try {
            context.getViewRoot();
        } catch (IllegalStateException ise) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        exceptionThrown = false;
        try {
            context.getResponseStream();
        } catch (IllegalStateException ise) {
            exceptionThrown = true;
        }

        exceptionThrown = false;
        try {
            context.getResponseWriter();
        } catch (IllegalStateException ise) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        // remainder of FacesContext methods are tested in TCK
    }


// Unit tests to update and retrieve values from model objects
// are in TestFacesContextImpl_Model.java
} // end of class TestFacesContextImpl
