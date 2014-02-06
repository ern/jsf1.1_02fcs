/*
 * $Id: LifecycleImpl.java,v 1.45.28.2.2.1.2.1 2006/04/12 19:32:20 ofung Exp $
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
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * <p><b>LifecycleImpl</b> is the stock implementation of the standard
 * Lifecycle in the JavaServer Faces RI.</p>
 */

public class LifecycleImpl extends Lifecycle {


    // -------------------------------------------------------- Static Variables


    // Log instance for this class
    private static final Log log = LogFactory.getLog(LifecycleImpl.class);


    // ------------------------------------------------------ Instance Variables


    // The set of PhaseListeners registered with this Lifecycle instance
    private ArrayList listeners = new ArrayList();


    // The set of Phase instances that are executed by the execute() method
    // in order by the ordinal property of each phase
    private Phase phases[] = {
        null, // ANY_PHASE placeholder, not a real Phase
        new RestoreViewPhase(),
        new ApplyRequestValuesPhase(),
        new ProcessValidationsPhase(),
        new UpdateModelValuesPhase(),
        new InvokeApplicationPhase()
    };


    // The Phase instance for the render() method
    private Phase response = new RenderResponsePhase();


    // ------------------------------------------------------- Lifecycle Methods


    // Execute the phases up to but not including Render Response
    public void execute(FacesContext context) throws FacesException {

        if (context == null) {
            throw new NullPointerException
                (Util.getExceptionMessageString
                 (Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }

        if (log.isDebugEnabled()) {
            log.debug("execute(" + context + ")");
        }

        for (int i = 1; i < phases.length; i++) { // Skip ANY_PHASE placeholder

            if (context.getRenderResponse() ||
                context.getResponseComplete()) {
                break;
            }

            phase((PhaseId) PhaseId.VALUES.get(i), phases[i], context);

            if (reload((PhaseId) PhaseId.VALUES.get(i), context)) {
                if (log.isDebugEnabled()) {
                    log.debug("Skipping rest of execute() because of a reload");
                }
                context.renderResponse();
            }
        }

    }


    // Execute the Render Response phase
    public void render(FacesContext context) throws FacesException {

        if (context == null) {
            throw new NullPointerException
                (Util.getExceptionMessageString
                 (Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }

        if (log.isDebugEnabled()) {
            log.debug("render(" + context + ")");
        }

        if (!context.getResponseComplete()) {
            phase(PhaseId.RENDER_RESPONSE, response, context);
        }

    }


    // Add a new PhaseListener to the set of registered listeners
    public void addPhaseListener(PhaseListener listener) {

        if (listener == null) {
            throw new NullPointerException
                (Util.getExceptionMessageString
                 (Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
        if (log.isDebugEnabled()) {
            log.debug("addPhaseListener(" + listener.getPhaseId().toString()
                      + "," + listener);
        }
        synchronized (listeners) {
            listeners.add(listener);
        }

    }


    // Return the set of PhaseListeners that have been registered
    public PhaseListener[] getPhaseListeners() {

        synchronized (listeners) {
            PhaseListener results[] = new PhaseListener[listeners.size()];
            return ((PhaseListener[]) listeners.toArray(results));
        }

    }


    // Remove a registered PhaseListener from the set of registered listeners
    public void removePhaseListener(PhaseListener listener) {

        if (listener == null) {
            throw new NullPointerException
                (Util.getExceptionMessageString
                 (Util.NULL_PARAMETERS_ERROR_MESSAGE_ID));
        }
        if (log.isDebugEnabled()) {
            log.debug("removePhaseListener(" +
                      listener.getPhaseId().toString()
                      + "," + listener);
        }
        synchronized (listeners) {
            ArrayList temp = (ArrayList) listeners.clone();
            temp.remove(listener);
            listeners = temp;
        }

    }


    // --------------------------------------------------------- Private Methods


    // Execute the specified phase, calling all listeners as well
    private void phase(PhaseId phaseId, Phase phase, FacesContext context)
        throws FacesException {

        if (log.isTraceEnabled()) {
            log.trace("phase(" + phaseId.toString() + "," + context + ")");
        }

        // grab a pointer to our listeners
        List ourListeners = this.listeners;
        int numListeners = ourListeners.size();
        
        // build a list of all listeners fired
        List firedListeners = new ArrayList(numListeners);
        
        // if we have PhaseListeners to fire
        if (numListeners > 0) {
            // instantiate all of our variables
            PhaseEvent event = new PhaseEvent(context, phaseId, this);
            PhaseListener listener;
            PhaseId listenerPhaseId;
            int i = 0;
            
            // call all relevant PhaseListeners before
            try {
                while (i < numListeners) {
                    listener = (PhaseListener) ourListeners.get(i);
                    listenerPhaseId = listener.getPhaseId();
                    if (phaseId.equals(listenerPhaseId)
                        || PhaseId.ANY_PHASE.equals(listenerPhaseId)) {
                        listener.beforePhase(event);
                        firedListeners.add(listener);
                    }
                    i++;
                }
            }
            catch (Throwable e) {
                if (log.isTraceEnabled()) {
                    log.trace("beforePhase(" + phaseId.toString() + "," + context + 
                            ") threw exception: " + e + " " + e.getMessage() +
                            "\n" + Util.getStackTraceString(e));
                }
            }
            try
            {
                // if response is not already handled, execute phase
                if (!skipping(phaseId, context)) {
            		phase.execute(context);
            	}
            }
            finally {
                // call all relevant PhaseListeners after
                i = firedListeners.size() - 1;
                try {
                    while (i >= 0) {
                        listener = (PhaseListener) firedListeners.get(i);
                        listener.afterPhase(event);
                        i--;
                    }
                } catch (Throwable e) {
                    if (log.isTraceEnabled()) {
                        log.trace("afterPhase(" + phaseId.toString() + "," + context + 
                                ") threw exception: " + e + " " + e.getMessage() +
                                "\n" + Util.getStackTraceString(e));
                    }
                }
            }
        } else { // else no PhaseListeners and simply execute the Phase
            // if response is not already handled, execute phase
            if (!skipping(phaseId, context)) {
                phase.execute(context);
            }
        }
    }

    // Return "true" if this request is a browser reload and we just
    // completed the Restore View phase
    private boolean reload(PhaseId phaseId, FacesContext context) {

        if (!phaseId.equals(PhaseId.RESTORE_VIEW)) {
            return (false);
        }
        if (!(context.getExternalContext().getRequest() instanceof
            HttpServletRequest)) {
            return (false);
        }
        HttpServletRequest request = (HttpServletRequest)
            context.getExternalContext().getRequest();
        String method = request.getMethod();

        // Is this a GET request with query parameters?
        if ("GET".equals(method)) {
            Iterator names = context.getExternalContext().
                getRequestParameterNames();
            if (names.hasNext()) {
                return (false);
            }
        }

        // Is this a POST or PUT request?
        if ("POST".equals(method) || "PUT".equals(method)) {
            return (false);
        }

        // Assume this is a reload
        return (true);

    }


    // Return "true" if we should be skipping the actual phase execution
    private boolean skipping(PhaseId phaseId, FacesContext context) {

        if (context.getResponseComplete()) {
            return (true);
        } else if (context.getRenderResponse() &&
            !phaseId.equals(PhaseId.RENDER_RESPONSE)) {
            return (true);
        } else {
            return (false);
        }

    }


}
