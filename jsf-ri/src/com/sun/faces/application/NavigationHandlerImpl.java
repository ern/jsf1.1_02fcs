/*
 * $Id: NavigationHandlerImpl.java,v 1.34.28.1.2.1 2006/04/12 19:32:03 ofung Exp $
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

package com.sun.faces.application;

import com.sun.faces.util.Util;
import com.sun.faces.config.ConfigureListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p><strong>NavigationHandlerImpl</strong> is the class that implements
 * default navigation handling. Refer to section 7.4.2 of the specification for
 * more details.
 */

public class NavigationHandlerImpl extends NavigationHandler {

    //
    // Protected Constants
    //

    private static final Log log = LogFactory.getLog(
        NavigationHandlerImpl.class);

    //
    // Class Variables
    //

    // Instance Variables

    /**
     * Application associate that contains navigation mappings loaded
     * from configuration file(s).
     */

    private Map caseListMap;
    private Set wildCardSet;
    boolean associateSet;
    /**
     * This constructor uses the current <code>Application</code>
     * instance to obtain the navigation mappings used to make
     * navigational decisions.
     */
    public NavigationHandlerImpl() {
        super();
        if (log.isDebugEnabled()) {
            log.debug("Created NavigationHandler instance ");
        }
        // if the user is using the decorator pattern, this would cause
        // our ApplicationAssociate to be created, if it isn't already
        // created.
        ApplicationFactory aFactory =
              (ApplicationFactory) FactoryFinder.getFactory(
                    FactoryFinder.APPLICATION_FACTORY);
        aFactory.getApplication();
        ApplicationAssociate associate =
              ApplicationAssociate
                    .getInstance(
                          ConfigureListener.getExternalContextDuringInitialize());
        if (associate != null) {
            caseListMap = associate.getNavigationCaseListMappings();
            wildCardSet = associate.getNavigationWildCardList();
            associateSet = true;
        }
    }


    /**
     * Determine the next view based on the current view
     * (<code>from-view-id</code> stored in <code>FacesContext</code>),
     * <code>fromAction</code> and <code>outcome</code>.
     *
     * @param context    The <code>FacesContext</code>
     * @param fromAction the action reference string
     * @param outcome    the outcome string
     */
    public void handleNavigation(FacesContext context, String fromAction,
                                 String outcome) {
        if (context == null) {
            String message = Util.getExceptionMessageString
                (Util.NULL_PARAMETERS_ERROR_MESSAGE_ID);
            message = message + " context " + context;
            throw new NullPointerException(message);
        }
        if (outcome == null) {
            if (log.isDebugEnabled()) {
                log.debug("No navigation rule found for outcome " + outcome +
                          "and viewId " + context.getViewRoot().getViewId() +
                          " Explicitly remain on the current view ");
            }
            return; // Explicitly remain on the current view
        }
        CaseStruct caseStruct = getViewId(context, fromAction, outcome);
        ExternalContext extContext = context.getExternalContext();
        if (caseStruct != null) {
            ViewHandler viewHandler = Util.getViewHandler(context);            

            if (caseStruct.navCase.hasRedirect()) {
                // perform a 302 redirect.
                String newPath =
                    viewHandler.getActionURL(context, caseStruct.viewId);
                try {
                    if (log.isDebugEnabled()) {
                        log.debug("Redirecting to path " + newPath
                                  + " for outcome " + outcome +
                                  "and viewId " + caseStruct.viewId);
                    }
                    extContext.redirect(newPath);
                } catch (java.io.IOException ioe) {
                    String message = "Redirect to " + newPath + " failed.";
                    if (log.isErrorEnabled()) {
                        log.error(message);
                    }
                    throw new FacesException(message, ioe);
                }
                context.responseComplete();
                if (log.isDebugEnabled()) {
                    log.debug("Response complete for " + caseStruct.viewId);
                }
            } else {
                UIViewRoot newRoot = viewHandler.createView(context,
                                                            caseStruct.viewId);
                context.setViewRoot(newRoot);
                if (log.isDebugEnabled()) {
                    log.debug(
                        "Set new view in FacesContext for " +
                        caseStruct.viewId);
                }
            }
        }
    }


    /**
     * This method uses helper methods to determine the new <code>view</code> identifier.
     * Refer to section 7.4.2 of the specification for more details.
     *
     * @param context    The Faces Context
     * @param fromAction The action reference string
     * @param outcome    The outcome string
     * @return The <code>view</code> identifier.
     */
    private CaseStruct getViewId(FacesContext context, String fromAction,
                                 String outcome) {

        String viewId = context.getViewRoot().getViewId();

        CaseStruct caseStruct = findExactMatch(viewId, fromAction, outcome);

        if (caseStruct == null) {
            caseStruct = findWildCardMatch(viewId, fromAction, outcome);
        }

        if (caseStruct == null) {
            caseStruct = findDefaultMatch(fromAction, outcome);
        }

        return caseStruct;
    }


    /**
     * This method finds the List of cases for the current <code>view</code> identifier.
     * After the cases are found, the <code>from-action</code> and <code>from-outcome</code>
     * values are evaluated to determine the new <code>view</code> identifier.
     * Refer to section 7.4.2 of the specification for more details.
     *
     * @param viewId     The current <code>view</code> identifier.
     * @param fromAction The action reference string.
     * @param outcome    The outcome string.
     * @return The <code>view</code> identifier.
     */

    private CaseStruct findExactMatch(String viewId,
                                      String fromAction,
                                      String outcome) {       

        // if the user has elected to replace the Application instance
        // entirely
        if (!associateSet) {
            return null;
        }        

        List caseList = (List) caseListMap.get(viewId);

        if (caseList == null) {
            return null;
        }

        // We've found an exact match for the viewId.  Now we need to evaluate
        // from-action/outcome in the following order:
        // 1) elements specifying both from-action and from-outcome
        // 2) elements specifying only from-outcome
        // 3) elements specifying only from-action
        // 4) elements where both from-action and from-outcome are null


        return determineViewFromActionOutcome(caseList, fromAction, outcome);
    }


    /**
     * This method traverses the wild card match List (containing <code>from-view-id</code>
     * strings and finds the List of cases for each <code>from-view-id</code> string.
     * Refer to section 7.4.2 of the specification for more details.
     *
     * @param viewId     The current <code>view</code> identifier.
     * @param fromAction The action reference string.
     * @param outcome    The outcome string.
     * @return The <code>view</code> identifier.
     */

    private CaseStruct findWildCardMatch(String viewId,
                                         String fromAction,
                                         String outcome) {
        CaseStruct result = null;

        // if the user has elected to replace the Application instance
        // entirely
        if (!associateSet) {
            return null;
        }

        

        Iterator iter = wildCardSet.iterator();
        while (iter.hasNext()) {
            String fromViewId = (String) iter.next();

// See if the entire wildcard string (without the trailing "*" is
// contained in the incoming viewId.  Ex: /foobar is contained with /foobarbaz
// If so, then we have found our largest pattern match..
// If not, then continue on to the next case;

            if (viewId.indexOf(fromViewId, 0) == -1) {
                continue;
            }

// Append the trailing "*" so we can do our map lookup;

            String wcFromViewId = fromViewId + '*';
            List caseList = (List) caseListMap.get(wcFromViewId);

            if (caseList == null) {
                return null;
            }

// If we've found a match, then we need to evaluate
// from-action/outcome in the following order:
// 1) elements specifying both from-action and from-outcome
// 2) elements specifying only from-outcome
// 3) elements specifying only from-action
// 4) elements where both from-action and from-outcome are null

            result = determineViewFromActionOutcome(caseList, fromAction,
                                                    outcome);
            if (result != null) {
                break;
            }
        }
        return result;
    }


    /**
     * This method will extract the cases for which a <code>from-view-id</code> is
     * an asterisk "*".
     * Refer to section 7.4.2 of the specification for more details.
     *
     * @param fromAction The action reference string.
     * @param outcome    The outcome string.
     * @return The <code>view</code> identifier.
     */

    private CaseStruct findDefaultMatch(String fromAction,
                                        String outcome) {        

        // if the user has elected to replace the Application instance
        // entirely
        if (!associateSet) {
            return null;
        }
        
        List caseList = (List) caseListMap.get("*");

        if (caseList == null) {
            return null;
        }

        // We need to evaluate from-action/outcome in the follow
        // order:  1)elements specifying both from-action and from-outcome
        // 2) elements specifying only from-outcome
        // 3) elements specifying only from-action
        // 4) elements where both from-action and from-outcome are null

        return determineViewFromActionOutcome(caseList, fromAction, outcome);
    }


    /**
     * This method will attempt to find the <code>view</code> identifier based on action reference
     * and outcome.  Refer to section 7.4.2 of the specification for more details.
     *
     * @param caseList   The list of navigation cases.
     * @param fromAction The action reference string.
     * @param outcome    The outcome string.
     * @return The <code>view</code> identifier.
     */

    private CaseStruct determineViewFromActionOutcome(List caseList,
                                                      String fromAction,
                                                      String outcome) {

        CaseStruct result = new CaseStruct();
        for (int i = 0, size = caseList.size(); i < size; i++) {        
            ConfigNavigationCase cnc = (ConfigNavigationCase) caseList.get(i);
            String cncFromAction = cnc.getFromAction();
            String fromOutcome = cnc.getFromOutcome();
            String toViewId = cnc.getToViewId();
            if ((cncFromAction != null) && (fromOutcome != null)) {
                if ((cncFromAction.equals(fromAction)) &&
                    (fromOutcome.equals(outcome))) {
                    result.viewId = toViewId;
                    result.navCase = cnc;
                    return result;
                }
            }
            
             if ((cncFromAction == null) && (fromOutcome != null)) {
                if (fromOutcome.equals(outcome)) {
                    result.viewId = toViewId;
                    result.navCase = cnc;
                    return result;
                }
            }
            
            if ((cncFromAction != null) && (fromOutcome == null)) {
                if (cncFromAction.equals(fromAction)) {
                    result.viewId = toViewId;
                    result.navCase = cnc;
                    return result;
                }
            }
            
            if ((cncFromAction == null) && (fromOutcome == null)) {
                result.viewId = toViewId;
                result.navCase = cnc;
                return result;
            }
        }                 

        return null;
    }


    private static class CaseStruct {
        String viewId;
        ConfigNavigationCase navCase;
    }

}
