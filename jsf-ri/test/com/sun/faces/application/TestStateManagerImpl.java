/*
 * $Id: TestStateManagerImpl.java,v 1.10.34.1.2.1 2006/04/12 19:33:00 ofung Exp $
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

import com.sun.faces.RIConstants;
import com.sun.faces.ServletFacesTestCase;
import com.sun.faces.util.Util;

import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;


/**
 * This class tests the <code>StateManagerImpl</code> class
 * functionality.
 */
public class TestStateManagerImpl extends ServletFacesTestCase {


    //
    // Constructors/Initializers
    //
    public TestStateManagerImpl() {
        super("TestStateManagerImpl");
    }


    public TestStateManagerImpl(String name) {
        super(name);
    }
    
    //
    // Test Methods
    //
    
    // Verify saveSerializedView() throws IllegalStateException
    // if duplicate component id's are detected on non-transient 
    // components.
    public void testDuplicateIdDetectionServer() throws Exception {

        FacesContext context = getFacesContext();
        UIViewRoot root = null;

        UIComponent comp1 = null;

        UIComponent comp2 = null;

        UIComponent comp3 = null;

        UIComponent facet1 = null;

        UIComponent facet2 = null;
        
        // construct a view
        root = Util.getViewHandler(getFacesContext()).createView(context, null); 
        root.setViewId("/test");
        root.setId("root");

        comp1 = new UIInput();
        comp1.setId("comp1");

        comp2 = new UIOutput();
        comp2.setId("comp2");

        comp3 = new UIGraphic();
        comp3.setId("comp3");

        facet1 = new UIOutput();
        facet1.setId("comp4");

        facet2 = new UIOutput();
        facet2.setId("comp2");

        comp2.getFacets().put("facet1", facet1);
        comp2.getFacets().put("facet2", facet2);

        root.getChildren().add(comp1);
        root.getChildren().add(comp2);
        root.getChildren().add(comp3);

        HttpSession session =
            (HttpSession) context.getExternalContext().getSession(false);
        session.setAttribute("/test", root);


        context.setViewRoot(root);

        StateManagerImpl stateManager = (StateManagerImpl) context.getApplication()
            .getStateManager();

        boolean exceptionThrown = false;
        try {
            stateManager.saveSerializedView(context);
        } catch (IllegalStateException ise) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        
        
        // multiple componentns with a null ID should not
        // trigger an exception
        // construct a view
        root = Util.getViewHandler(getFacesContext()).createView(context, null); 
        root.setViewId("/test");
        root.setId("root");

        comp1 = new UIInput();
        comp1.setId("comp1");

        comp2 = new UIOutput();
        comp2.setId(null);

        comp3 = new UIGraphic();
        comp3.setId(null);

        facet1 = new UIOutput();
        facet1.setId("comp4");

        facet2 = new UIOutput();
        facet2.setId("comp2");

        comp2.getFacets().put("facet1", facet1);
        comp2.getFacets().put("facet2", facet2);

        root.getChildren().add(comp1);
        root.getChildren().add(comp2);
        root.getChildren().add(comp3);

        session.setAttribute("/test", root);

        context.setViewRoot(root);

        exceptionThrown = false;
        try {
            stateManager.saveSerializedView(context);
        } catch (IllegalStateException ise) {
            exceptionThrown = true;
        }
        assertTrue(!exceptionThrown);
        
        // transient components with duplicate ids should 
        // trigger an error condition
        // construct a view
        root = Util.getViewHandler(getFacesContext()).createView(context, null); 
        root.setViewId("/test");
        root.setId("root");

        comp1 = new UIInput();
        comp1.setId("comp1");
        comp1.setTransient(true);

        comp2 = new UIOutput();
        comp2.setId("comp1");
        comp2.setTransient(true);

        comp3 = new UIGraphic();
        comp3.setId("comp3");

        facet1 = new UIOutput();
        facet1.setId("comp4");

        facet2 = new UIOutput();
        facet2.setId("comp2");

        comp2.getFacets().put("facet1", facet1);
        comp2.getFacets().put("facet2", facet2);

        root.getChildren().add(comp1);
        root.getChildren().add(comp2);
        root.getChildren().add(comp3);

        session.setAttribute("/test", root);

        context.setViewRoot(root);

        exceptionThrown = false;
        try {
            stateManager.saveSerializedView(context);
        } catch (IllegalStateException ise) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }


    public void testDuplicateIdDetectionClient() throws Exception {
        StateManagerImpl wrapper =
            new StateManagerImpl() {
                public boolean isSavingStateInClient(FacesContext context) {
                    return true;
                }
            };
        getFacesContext().getApplication().setStateManager(wrapper);
        testDuplicateIdDetectionServer();
    }

}
