/*
 * $Id: FacesContextImpl.java,v 1.66.30.2.2.1 2006/04/12 19:32:05 ofung Exp $
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

package com.sun.faces.context;

import com.sun.faces.util.Util;
import org.apache.commons.collections.CursorableLinkedList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.event.FacesEvent;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FacesContextImpl extends FacesContext {

    //
    // Protected Constants
    //

    //
    // Class Variables
    //

    private static final Log log = LogFactory.getLog(FacesContextImpl.class);

    //
    // Instance Variables
    //
    private boolean released;

    // Relationship Instance Variables
    private ResponseStream responseStream = null;
    private ResponseWriter responseWriter = null;
    private CursorableLinkedList facesEvents = null;
    private ExternalContext externalContext = null;
    private Application application = null;
    private UIViewRoot viewRoot = null;
    private RenderKitFactory rkFactory;
    private RenderKit lastRk;
    private String lastRkId;

    /**
     * Store mapping of clientId to ArrayList of FacesMessage
     * instances.  The null key is used to represent FacesMessage instances
     * that are not associated with a clientId instance.
     */
    private Map componentMessageLists;       

    
    
    // Attribute Instance Variables

    private boolean renderResponse = false;
    private boolean responseComplete = false;


    //
    // Constructors and Initializers    
    // 
    public FacesContextImpl() {
    }


    public FacesContextImpl(ExternalContext ec, Lifecycle lifecycle) {
        if (null == ec || null == lifecycle) {
            throw new NullPointerException
                (
                    Util.getExceptionMessageString(
                        Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
        this.externalContext = ec;
        setCurrentInstance(this);
        
        rkFactory = (RenderKitFactory) 
              FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);        
    }

    //
    // Class methods
    //

    //
    // General Methods
    
    //
    //
    // Methods from FacesContext
    //

    public ExternalContext getExternalContext() {
        assertNotReleased();
        return externalContext;
    }


    /**
     * <p>Return the {@link Application} instance associated with this
     * web application.</p>
     */
    public Application getApplication() {
        assertNotReleased();
        if (null != application) {
            return application;
        }
        ApplicationFactory aFactory =
            (ApplicationFactory) FactoryFinder.getFactory(
                FactoryFinder.APPLICATION_FACTORY);
        application = aFactory.getApplication();
        Util.doAssert(null != application);
        return application;
    }


    public Iterator getClientIdsWithMessages() {
        assertNotReleased();
        Iterator result = null;
        if (null == componentMessageLists) {
            result = Collections.EMPTY_LIST.iterator();
        } else {
            result = componentMessageLists.keySet().iterator();

        }
        return result;
    }


    public Iterator getFacesEvents() {
        assertNotReleased();
        if (facesEvents != null) {
            return (facesEvents.cursor());
        } else {
            return (Collections.EMPTY_LIST.iterator());
        }
    }


    public Severity getMaximumSeverity() {
        assertNotReleased();
        int max = 0;
        Severity result = null;

        if (null == componentMessageLists) {
            return null;
        }
        // Get an Iterator over the ArrayList instances
        List messages = getMergedMessageLists();
        for (int i = 0, size = getMergedMessageLists().size(); i < size; i++) {
            result = ((FacesMessage) messages.get(i)).getSeverity();
            if (result.getOrdinal() > max) {
                max = result.getOrdinal();
            }

            if (result == FacesMessage.SEVERITY_FATAL) {
                break;
            }
        }
        return result;
    }


    public Iterator getMessages() {
        assertNotReleased();
        if (null == componentMessageLists) {
            return (Collections.EMPTY_LIST.iterator());
        }

        // Get an Iterator over the ArrayList instances
        List messages = getMergedMessageLists();
        if (messages.size() > 0) {
            return messages.iterator();
        } else {
            return Collections.EMPTY_LIST.iterator();
        }
    }


    public Iterator getMessages(String clientId) {
        assertNotReleased();
        // If no messages have been enqueued at all,
        // return an empty List Iterator
        if (null == componentMessageLists) {
            return (Collections.EMPTY_LIST.iterator());
        }

        List list = (List) componentMessageLists.get(clientId);
        if (list == null) {
            return (Collections.EMPTY_LIST.iterator());
        }
        return (list.iterator());
    }


    public RenderKit getRenderKit() {
        assertNotReleased();
        UIViewRoot vr = getViewRoot();
        if (vr == null) {
            return (null);
        }
        String renderKitId = vr.getRenderKitId();
        if (renderKitId == null) {
            return (null);
        }          
        
        if (renderKitId.equals(lastRkId)) {
            return lastRk;
        } else {
            lastRk = rkFactory.getRenderKit(this, renderKitId);
            lastRkId = renderKitId;
            return lastRk;
        }
                                      
    }


    public ResponseStream getResponseStream() {
        assertNotReleased();
        return responseStream;
    }


    public void setResponseStream(ResponseStream newResponseStream) {
        assertNotReleased();
        if (newResponseStream == null) {
            throw new NullPointerException(
                Util.getExceptionMessageString(
                    Util.NULL_RESPONSE_STREAM_ERROR_MESSAGE_ID));
        }
        responseStream = newResponseStream;
    }


    public UIViewRoot getViewRoot() {
        assertNotReleased();
        return viewRoot;
    }


    public void setViewRoot(UIViewRoot root) {
        assertNotReleased();
                
        if (root == null) {
            throw new NullPointerException
                (Util.getExceptionMessageString(
                    Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
        
        if (viewRoot != root) {
            facesEvents = null;
        }
        viewRoot = root;
    }


    public ResponseWriter getResponseWriter() {
        assertNotReleased();
        return responseWriter;
    }


    public void setResponseWriter(ResponseWriter newResponseWriter) {
        assertNotReleased();
        if (newResponseWriter == null) {
            throw new NullPointerException(
                Util.getExceptionMessageString(
                    Util.NULL_RESPONSE_WRITER_ERROR_MESSAGE_ID));
        }
        responseWriter = newResponseWriter;
    }


    public void addFacesEvent(FacesEvent event) {
        assertNotReleased();
        // Validate our preconditions
        if (event == null) {
            throw new NullPointerException
                (Util.getExceptionMessageString(Util.NULL_EVENT_ERROR_MESSAGE_ID));
        }

        // Add this event to our internal queue
        if (facesEvents == null) {
            facesEvents = new CursorableLinkedList();
        }
        facesEvents.add(event);
        if (log.isDebugEnabled()) {
            String id = event.getComponent().getId();
            if (id == null) {
                id = "<<NONE>>";
            }
            log.debug("Adding FacesEvent[sourceId=" + id +
                      ",type=" + event.getClass().getName());
        }

    }


    public void addMessage(String clientId, FacesMessage message) {
        assertNotReleased();
        // Validate our preconditions
        if (null == message) {
            throw new NullPointerException
                (
                    Util.getExceptionMessageString(
                        Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }

        if (componentMessageLists == null) {
            componentMessageLists = new HashMap();
        }

        // Add this message to our internal queue
        List list = (List) componentMessageLists.get(clientId);
        if (list == null) {
            list = new ArrayList();
            componentMessageLists.put(clientId, list);
        }
        list.add(message);

        if (log.isDebugEnabled()) {
            log.debug("Adding Message[sourceId=" +
                      (clientId != null ? clientId : "<<NONE>>") +
                      ",summary=" + message.getSummary() + ")");
        }

    }


    public void release() {
        released = true;
        externalContext = null;
        responseStream = null;
        responseWriter = null;
        facesEvents = null;
        componentMessageLists = null;
        renderResponse = false;
        responseComplete = false;
        viewRoot = null;

        // PENDING(edburns): write testcase that verifies that release
        // actually works.  This will be important to keep working as
        // ivars are added and removed on this class over time.

        // Make sure to clear our ThreadLocal instance.
        setCurrentInstance(null);
    }


    public void renderResponse() {
        assertNotReleased();
        renderResponse = true;
    }


    public void responseComplete() {
        assertNotReleased();
        responseComplete = true;
    }


    public boolean getRenderResponse() {
        assertNotReleased();
        return renderResponse;
    }


    public boolean getResponseComplete() {
        assertNotReleased();
        return responseComplete;
    }


    //
    // Private methods
    //
    private void assertNotReleased() {
        if (released) {
            throw new IllegalStateException();
        }
    }


    private List getMergedMessageLists() {
        List mergedList = new ArrayList();
        if (componentMessageLists != null) {
            for (Iterator i = componentMessageLists.values().iterator(); i.hasNext();) {
                for (Iterator ii = ((ArrayList) i.next()).iterator(); ii.hasNext();)
                    mergedList.add(ii.next());
            }
        }
        return mergedList;
    }
   
    // The testcase for this class is TestFacesContextImpl.java 
    // The testcase for this class is TestFacesContextImpl_Model.java

} // end of class FacesContextImpl
