/* 
 * $Id: StateManagerImpl.java,v 1.24.12.2.2.1 2006/04/12 19:32:03 ofung Exp $ 
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


// StateManagerImpl.java 

package com.sun.faces.application;

import com.sun.faces.RIConstants;
import com.sun.faces.util.TreeStructure;
import com.sun.faces.util.Util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.collections.LRUMap;

import javax.faces.application.StateManager;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.faces.component.NamingContainer;

/**
 * <B>StateManagerImpl</B> is the default implementation class for
 * StateManager.
 *
 * @version $Id: StateManagerImpl.java,v 1.24.12.2.2.1 2006/04/12 19:32:03 ofung Exp $
 * @see javax.faces.application.ViewHandler
 */
public class StateManagerImpl extends StateManager {

    private static final Log log = LogFactory.getLog(StateManagerImpl.class);
    private static final String NUMBER_OF_VIEWS_IN_SESSION =
        RIConstants.FACES_PREFIX + "NUMBER_OF_VIEWS_IN_SESSION";
    private static final int DEFAULT_NUMBER_OF_VIEWS_IN_SESSION = 15;

    private static final String NUMBER_OF_VIEWS_IN_LOGICAL_VIEW_IN_SESSION =
        RIConstants.FACES_PREFIX + "NUMBER_OF_VIEWS_IN_LOGICAL_VIEW_IN_SESSION";
    private static final int DEFAULT_NUMBER_OF_VIEWS_IN_LOGICAL_VIEW_IN_SESSION = 15;
    

    /**
     * Number of views in logical view to be saved in session.
     */
    int noOfViews = 0;
    int noOfViewsInLogicalView = 0;
    
    public SerializedView saveSerializedView(FacesContext context) 
        throws IllegalStateException{
        SerializedView result = null;
	Object treeStructure = null;
	Object componentState = null;
	// irrespective of method to save the tree, if the root is transient
	// no state information needs to  be persisted.
	UIViewRoot viewRoot = context.getViewRoot();
	if (viewRoot.isTransient()) {
            return result;
	}
	
	// honor the requirement to check for id uniqueness
	checkIdUniqueness(context, viewRoot, new HashSet());

	
 	if (log.isDebugEnabled()) {
            log.debug("Begin creating serialized view for " 
                    + viewRoot.getViewId());
 	}
	result = new SerializedView(treeStructure = 
				    getTreeStructureToSave(context),
				    componentState =
				    getComponentStateToSave(context));
	Util.doAssert(treeStructure instanceof Serializable);
	Util.doAssert(componentState instanceof Serializable);

 	if (log.isDebugEnabled()) {
            log.debug("End creating serialized view " + viewRoot.getViewId());
 	}
        if (!isSavingStateInClient(context)) {
            //
            // Server Side state saving is handled stored in two nested LRU maps
            // in the session.
            //
            // The first map is called the LOGICAL_VIEW_MAP.  A logical view
            // is a top level view that may have one or more actual views inside
            // of it.  This will be the case when you have a frameset, or an
            // application that has multiple windows operating at the same time.
            // The LOGICAL_VIEW_MAP map contains 
            // an entry for each logical view, up to the limit specified by the
            // numberOfViewsParameter.  Each entry in the LOGICAL_VIEW_MAP
            // is an LRU Map, configured with the numberOfViewsInLogicalView
            // parameter.  
            //
            // The motivation for this is to allow better memory tuning for 
            // apps that need this multi-window behavior.
            
            String id = null,
                   idInActualMap = null,
                   idInLogicalMap = (String)
                context.getExternalContext().getRequestMap().get(RIConstants.LOGICAL_VIEW_MAP);
            LRUMap logicalMap = null, actualMap = null;
            int 
                logicalMapSize = getNumberOfViewsParameter(context),
                actualMapSize = getNumberOfViewsInLogicalViewParameter(context);
            
            Object stateArray[] = { treeStructure, componentState };
            Map sessionMap = Util.getSessionMap(context);
            
 	    synchronized (this) {
                if (null == (logicalMap = (LRUMap) sessionMap.get(RIConstants.LOGICAL_VIEW_MAP))) {
                    logicalMap = new LRUMap(logicalMapSize);
 		    sessionMap.put(RIConstants.LOGICAL_VIEW_MAP, logicalMap);
                }
                Util.doAssert(null != logicalMap); 

                if (null == idInLogicalMap) {
                    idInLogicalMap = createUniqueRequestId();
                }
                Util.doAssert(null != idInLogicalMap);
 
                idInActualMap = createUniqueRequestId();
 		if (null == (actualMap = (LRUMap) 
                        logicalMap.get(idInLogicalMap))) {
		    actualMap = new LRUMap(actualMapSize);
                    logicalMap.put(idInLogicalMap, actualMap);
 		}
                id = idInLogicalMap + NamingContainer.SEPARATOR_CHAR + 
                        idInActualMap;
		result = new SerializedView(id, null);
                actualMap.put(idInActualMap, stateArray);
 	    }
 	}
	
        return result;
    }


    char requestIdSerial = 0;

    private String createUniqueRequestId() {
	if (requestIdSerial++ == Character.MAX_VALUE) {
	    requestIdSerial = 0;
	}
	return UIViewRoot.UNIQUE_ID_PREFIX + ((int) requestIdSerial);
    }


    protected void checkIdUniqueness(FacesContext context,
        UIComponent component, Set componentIds) throws IllegalStateException{
        UIComponent kid;
        // deal with children that are marked transient.
        Iterator kids = component.getChildren().iterator();
        String id;
        String messageString;
        while (kids.hasNext()) {
            kid = (UIComponent) kids.next();
	    // check for id uniqueness
	    id = kid.getClientId(context);       
	    if (id != null && !componentIds.add(id)) {
                messageString = Util.getExceptionMessageString(
                        Util.DUPLICATE_COMPONENT_ID_ERROR_ID,
                        new Object[]{id});
                if (log.isErrorEnabled()) {
                    log.error(messageString);
                }
		throw new IllegalStateException(messageString);
	    }

	    checkIdUniqueness(context, kid, componentIds);
        }
        // deal with facets that are marked transient.
        kids = component.getFacets().values().iterator();
        while (kids.hasNext()) {
            kid = (UIComponent) kids.next();
	    // check for id uniqueness
	    id = kid.getClientId(context);
	    if (id != null && !componentIds.add(id)) {
                messageString = Util.getExceptionMessageString(
                        Util.DUPLICATE_COMPONENT_ID_ERROR_ID,
                        new Object[]{id});
                if (log.isErrorEnabled()) {
                    log.error(messageString);
                }
		throw new IllegalStateException(messageString);
	    }

	    checkIdUniqueness(context, kid, componentIds);

        }
    }


    protected Object getComponentStateToSave(FacesContext context) {
	return context.getViewRoot().processSaveState(context);
    }


    protected Object getTreeStructureToSave(FacesContext context) {
        TreeStructure structRoot = null;
        UIComponent viewRoot = context.getViewRoot();
        if (!(viewRoot.isTransient())) {
            structRoot = new TreeStructure(viewRoot);
            buildTreeStructureToSave(context, viewRoot, structRoot, null);
        }
        return structRoot;
    }


    public UIViewRoot restoreView(FacesContext context, String viewId,
                                  String renderKitId) {
        if (null == renderKitId) {
            String message = Util.getExceptionMessageString
                (Util.NULL_PARAMETERS_ERROR_MESSAGE_ID);
            message = message + " renderKitId " + renderKitId;
            throw new IllegalArgumentException(message);
        }

        UIViewRoot viewRoot = null;
        if (isSavingStateInClient(context)) {
            // restore view from response.
           if (log.isDebugEnabled()) {
                log.debug("Begin restoring view from response " 
                        + viewId);
            }
            viewRoot = restoreTreeStructure(context, viewId, renderKitId);
            if (viewRoot != null) {
                restoreComponentState(context, viewRoot, renderKitId);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Possibly a new request. Tree structure could not "
                            + " be restored for " + viewId);
                }
            }
            if (log.isDebugEnabled()) {
                log.debug("End restoring view from response " + viewId);
            }
        } else {
            // restore tree from session.
            // The ResponseStateManager implementation may be using the new methods or
            // deprecated methods.  We need to know which one to call.
            Object id = null;
            ResponseStateManager rsm = Util.getResponseStateManager(context, renderKitId);
	    id = rsm.getTreeStructureToRestore(context, viewId);

	    if (null != id) {
	        if (log.isDebugEnabled()) {
                    log.debug( "Begin restoring view in session for viewId " 
                            + viewId);
		}
                String idString = (String) id,
                       idInLogicalMap = null,
                       idInActualMap = null;
                
                int sep = idString.indexOf(NamingContainer.SEPARATOR_CHAR);
                Util.doAssert(-1 != sep);
                Util.doAssert(sep < idString.length());
                
                idInLogicalMap = idString.substring(0, sep);
                idInActualMap = idString.substring(sep + 1);
                		
                ExternalContext externalCtx = context.getExternalContext();
                Object sessionObj = externalCtx.getSession(false);
                
                // stop evaluating if the session is not available
                if (sessionObj == null) {
                    if (log.isDebugEnabled()) {
                        log.debug( "Can't Restore Server View State, session expired for viewId: "
                                + viewId);
                    }
                    return null;
                }
                
                Map logicalMap = null,
                actualMap = null,
                sessionMap = externalCtx.getSessionMap();
                
		TreeStructure structRoot = null;
		Object [] stateArray = null;
		synchronized (sessionObj) {
		    logicalMap = (Map) sessionMap.get(RIConstants.LOGICAL_VIEW_MAP);
                    if (logicalMap != null) {
                        actualMap = (Map) logicalMap.get(idInLogicalMap);
                        if (actualMap != null) {
                            context.getExternalContext().getRequestMap().put(RIConstants.LOGICAL_VIEW_MAP, 
                                idInLogicalMap);
                            stateArray = (Object []) actualMap.get(idInActualMap);
                        }
                    }
		}
                if (stateArray == null) {
                    if (log.isDebugEnabled()) {
                        log.debug( "Session Available, but View State does not exist for viewId: "
                            + viewId);
                    }
                    return null;
                }
		structRoot = (TreeStructure)stateArray[0];
		viewRoot = (UIViewRoot) structRoot.createComponent();
		restoreComponentTreeStructure(structRoot, viewRoot);
		
		viewRoot.processRestoreState(context, stateArray[1]);
		
		if (log.isDebugEnabled()) {
                    log.debug("End restoring view in session for viewId " 
                            + viewId);
		}
	    }
        }
        return viewRoot;
    }


    protected void restoreComponentState(FacesContext context,
                                         UIViewRoot root, String renderKitId) {
        if (null == renderKitId) {
            String message = Util.getExceptionMessageString
                (Util.NULL_PARAMETERS_ERROR_MESSAGE_ID);
            message = message + " renderKitId " + renderKitId;
            throw new IllegalArgumentException(message);
        }
        Object state = null;
        ResponseStateManager rsm = Util.getResponseStateManager(context, renderKitId);
	state = rsm.getComponentStateToRestore(context);
        root.processRestoreState(context, state);
    }


    protected UIViewRoot restoreTreeStructure(FacesContext context,
                                              String viewId, String renderKitId) {
        if (null == renderKitId) {
            String message = Util.getExceptionMessageString
                (Util.NULL_PARAMETERS_ERROR_MESSAGE_ID);
            message = message + " renderKitId " + renderKitId;
            throw new IllegalArgumentException(message);
        }
        UIComponent viewRoot = null;
        TreeStructure structRoot = null;
        ResponseStateManager rsm = Util.getResponseStateManager(context, renderKitId);
	structRoot = (TreeStructure)rsm.getTreeStructureToRestore(context, viewId);
        if (structRoot == null) {
            return null;
        }
        viewRoot = structRoot.createComponent();
        restoreComponentTreeStructure(structRoot, viewRoot);
        return ((UIViewRoot) viewRoot);
    }


    public void writeState(FacesContext context, SerializedView state)
        throws IOException {
        String renderKitId = context.getViewRoot().getRenderKitId();
        ResponseStateManager rsm = Util.getResponseStateManager(context, renderKitId);
	rsm.writeState(context, state);
    }

    /**
     * Builds a hierarchy of TreeStrucure objects simulating the component
     * tree hierarchy.
     */
    public void buildTreeStructureToSave(FacesContext context,
                                         UIComponent component,
                                         TreeStructure treeStructure,
                                         Set componentIds) {
        // traverse the component hierarchy and save the tree structure 
        // information for every component.
        
        // Set for catching duplicate IDs
        if (null == componentIds) {
            componentIds = new HashSet();
        }
        
        // save the structure info of the children of the component 
        // being processed.
        Iterator kids = component.getChildren().iterator();
        String id;
        String messageString = null;
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();

	    // check for id uniqueness
	    id = kid.getClientId(context);
	    if (id != null && !componentIds.add(id)) {
                messageString = Util.getExceptionMessageString(
                        Util.DUPLICATE_COMPONENT_ID_ERROR_ID,
                        new Object[]{id});
                if (log.isErrorEnabled()) {
                    log.error(messageString);
                }
		throw new IllegalStateException(messageString);
	    }
            
            // if a component is marked transient do not persist its state as
            // well as its children.
            if (!kid.isTransient()) {
                TreeStructure treeStructureChild = new TreeStructure(kid);
                treeStructure.addChild(treeStructureChild);
                buildTreeStructureToSave(context, kid, treeStructureChild,
                                         componentIds);
            }
        }

        // save structure info of the facets of the component currenly being 
        // processed.
        Iterator facets = component.getFacets().keySet().iterator();
        while (facets.hasNext()) {
            String facetName = (String) facets.next();
            UIComponent facetComponent = (UIComponent) component.getFacets().
                get(facetName);

	    // check for id uniqueness
	    id = facetComponent.getClientId(context);
	    if (id != null && !componentIds.add(id)) {
                messageString = Util.getExceptionMessageString(
                        Util.DUPLICATE_COMPONENT_ID_ERROR_ID,
                        new Object[]{id});
                if (log.isErrorEnabled()) {
                    log.error(messageString);
                }
		throw new IllegalStateException(messageString);
	    }
            
            // if a facet is marked transient do not persist its state as well as
            // its children.
            if (!(facetComponent.isTransient())) {
                TreeStructure treeStructureFacet =
                    new TreeStructure(facetComponent);
                treeStructure.addFacet(facetName, treeStructureFacet);
                // process children of facet.
                buildTreeStructureToSave(context,
                                         facetComponent, treeStructureFacet,
                                         componentIds);
            }
        }
    }


    /**
     * Reconstitutes the component tree from TreeStructure hierarchy
     */
    public void restoreComponentTreeStructure(TreeStructure treeStructure,
                                              UIComponent component) {
        // traverse the tree strucure hierarchy and restore component
        // structure.
      
        // restore the structure of the children of the component being processed.
        Iterator kids = treeStructure.getChildren();
        while (kids.hasNext()) {
            TreeStructure kid = (TreeStructure) kids.next();
            UIComponent child = kid.createComponent();
            component.getChildren().add(child);
            restoreComponentTreeStructure(kid, child);
        }
        
        // process facets
        Iterator facets = treeStructure.getFacetNames();
        while (facets.hasNext()) {
            String facetName = (String) facets.next();
            TreeStructure facetTreeStructure =
                treeStructure.getTreeStructureForFacet(facetName);
            UIComponent facetComponent = facetTreeStructure.createComponent();
            component.getFacets().put(facetName, facetComponent);
            restoreComponentTreeStructure(facetTreeStructure, facetComponent);
        }
    }
    
    /**
     * Returns the <code> UIViewRoot</code> corresponding the 
     * <code> viewId </code> by restoring the view structure and state.
     */
    protected UIViewRoot restoreSerializedView(FacesContext context, 
        SerializedView sv, String viewId) {
        if ( sv == null) {
            if (log.isDebugEnabled()) {
                log.debug("Possibly a new request. Tree structure could not "
                        + " be restored for " + viewId);
            }
            return null;
        }
        TreeStructure structRoot = (TreeStructure) sv.getStructure();
        if (structRoot == null) {
            return null;
        }
        UIComponent viewRoot = structRoot.createComponent();
        if (viewRoot != null) {
             restoreComponentTreeStructure(structRoot, viewRoot);
             Object state = sv.getState();
             viewRoot.processRestoreState(context, state);
        }
        return ((UIViewRoot)viewRoot);
    }
    
    /**
     * Returns the value of ServletContextInitParameter that specifies the
     * maximum number of logical views to be saved in session. If none is specified
     * returns <code>DEFAULT_NUMBER_OF_VIEWS_IN_SESSION</code>.
     */
    protected int getNumberOfViewsParameter(FacesContext context) {
        if (noOfViews != 0) { 
            return noOfViews;
        }
        noOfViews = DEFAULT_NUMBER_OF_VIEWS_IN_SESSION;
        String noOfViewsStr = context.getExternalContext().
                getInitParameter(NUMBER_OF_VIEWS_IN_SESSION);
        if (noOfViewsStr != null) {
            try {
                noOfViews = Integer.valueOf(noOfViewsStr).intValue();
            } catch (NumberFormatException nfe) {
                if (log.isDebugEnabled()) {
                    log.debug("Error parsing the servetInitParameter " +
                            NUMBER_OF_VIEWS_IN_SESSION + ". Using default " + 
                            noOfViews);
                }
            }
        } 
        return noOfViews;
    }

    /**
     * Returns the value of ServletContextInitParameter that specifies the
     * maximum number of views to be saved in this logical view. If none is specified
     * returns <code>DEFAULT_NUMBER_OF_VIEWS_IN_LOGICAL_VIEW_IN_SESSION</code>.
     */
    protected int getNumberOfViewsInLogicalViewParameter(FacesContext context) {
        if (noOfViewsInLogicalView != 0) { 
            return noOfViewsInLogicalView;
        }
        noOfViewsInLogicalView = DEFAULT_NUMBER_OF_VIEWS_IN_LOGICAL_VIEW_IN_SESSION;
        String noOfViewsStr = context.getExternalContext().
                getInitParameter(NUMBER_OF_VIEWS_IN_LOGICAL_VIEW_IN_SESSION);
        if (noOfViewsStr != null) {
            try {
                noOfViewsInLogicalView = Integer.valueOf(noOfViewsStr).intValue();
            } catch (NumberFormatException nfe) {
                if (log.isDebugEnabled()) {
                    log.debug("Error parsing the servetInitParameter " +
                            NUMBER_OF_VIEWS_IN_LOGICAL_VIEW_IN_SESSION + ". Using default " + 
                            noOfViewsInLogicalView);
                }
            }
        } 
        return noOfViewsInLogicalView;
    }

}
