/*
 * $Id: TestRestoreViewPhase.java,v 1.18.36.1 2006/04/12 19:33:11 ofung Exp $
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

import com.sun.faces.ServletFacesTestCase;
import com.sun.faces.util.Util;

import org.apache.cactus.WebRequest;

import javax.faces.component.UICommand;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIPanel;
import javax.faces.component.UIViewRoot;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpSession;

import java.util.Locale;


/**
 * <B>TestReconstituteComponentTreePhase</B> is a class ...
 * <p/>
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TestRestoreViewPhase.java,v 1.18.36.1 2006/04/12 19:33:11 ofung Exp $
 */

public class TestRestoreViewPhase extends ServletFacesTestCase {

//
// Protected Constants
//

    public static final String TEST_URI = "/components.jsp";

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

    public TestRestoreViewPhase() {
        super("TestRestoreViewPhase");
    }


    public TestRestoreViewPhase(String name) {
        super(name);
    }

//
// Class methods
//

//
// General Methods
//

    public void beginReconstituteRequestInitial(WebRequest theRequest) {
        theRequest.setURL("localhost:8080", null, null, TEST_URI, null);
    }


    public void beginReconstituteRequestSubmit(WebRequest theRequest) {
        theRequest.setURL("localhost:8080", null, null, TEST_URI, null);
    }


    public void beginRegisterListeners(WebRequest theRequest) {
        theRequest.setURL("localhost:8080", null, null, TEST_URI, null);
    }


    public void testReconstituteRequestInitial() {
        Phase restoreView = new RestoreViewPhase();

        try {
            restoreView.execute(getFacesContext());
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertTrue((getFacesContext().getRenderResponse()) &&
                   !(getFacesContext().getResponseComplete()));

        assertTrue(null != getFacesContext().getViewRoot());
        assertTrue(RenderKitFactory.HTML_BASIC_RENDER_KIT ==
                   getFacesContext().getViewRoot().getRenderKitId());

        assertTrue(null != getFacesContext().getViewRoot().getLocale());

        UIViewRoot root = null;

        assertTrue(
            getFacesContext().getViewRoot().getViewId().equals(TEST_URI));
        root = getFacesContext().getViewRoot();
        assertTrue((root.getChildren().isEmpty()) == true);
        getFacesContext().setViewRoot(null);
    }


    public void testReconstituteRequestSubmit() {

        // precreate tree and set it in session and make sure the tree is
        // restored from session.

        UIViewRoot root = Util.getViewHandler(getFacesContext()).createView(getFacesContext(), null);
        root.setViewId(TEST_URI);

        UIForm basicForm = new UIForm();
        basicForm.setId("basicForm");
        UIInput userName = new UIInput();

        userName.setId("userName");
        root.getChildren().add(basicForm);
        basicForm.getChildren().add(userName);

        HttpSession session = (HttpSession)
            getFacesContext().getExternalContext().getSession(false);
        session.setAttribute(TEST_URI, root);
        // set a locale
        Locale locale = new Locale("France", "french");
        root.setLocale(locale);

        Phase restoreView = new RestoreViewPhase();

        try {
            restoreView.execute(getFacesContext());
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertTrue(!(getFacesContext().getRenderResponse()) &&
                   !(getFacesContext().getResponseComplete()));

        assertTrue(null != getFacesContext().getViewRoot());
        assertTrue(RenderKitFactory.HTML_BASIC_RENDER_KIT ==
                   getFacesContext().getViewRoot().getRenderKitId());

        assertTrue(locale == getFacesContext().getViewRoot().getLocale());

        assertTrue(
            getFacesContext().getViewRoot().getViewId().equals(TEST_URI));
        root = getFacesContext().getViewRoot();
        // components should exist.
        assertTrue(root.getChildCount() == 1);
        assertTrue(basicForm == root.findComponent("basicForm"));
        assertTrue(userName == basicForm.findComponent("userName"));
        getFacesContext().setViewRoot(null);
    }


    /**
     * This method will test the <code>registerActionListeners</code> method.
     * It will first create a simple tree consisting of a couple of <code>UICommand</code>
     * components added to a facet;  Then the <code>ReconstituteComponentTree.execute</code>
     * method is run;  And finally, an assertion is done to ensure that default action
     * listeners have been registered on the <code>UICommand</code> components;
     */
    public void testRegisterListeners() {

        // precreate tree and set it in session and make sure the tree is
        // restored from session.

        UIViewRoot root = Util.getViewHandler(getFacesContext()).createView(getFacesContext(), null);
        root.setViewId(TEST_URI);

        UIForm basicForm = new UIForm();
        basicForm.setId("basicForm");
        root.getChildren().add(basicForm);
        UIPanel panel = new UIPanel();
        basicForm.getChildren().add(panel);
        UIPanel commandPanel = new UIPanel();
        commandPanel.setId("commandPanel");
        UICommand command1 = new UICommand();
        UICommand command2 = new UICommand();
        commandPanel.getChildren().add(command1);
        commandPanel.getChildren().add(command2);
        panel.getFacets().put("commandPanel", commandPanel);

        HttpSession session = (HttpSession)
            getFacesContext().getExternalContext().getSession(false);
        session.setAttribute(TEST_URI, root);

        Phase restoreView = new RestoreViewPhase();

        try {
            restoreView.execute(getFacesContext());
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertTrue(!(getFacesContext().getRenderResponse()) &&
                   !(getFacesContext().getResponseComplete()));
        assertTrue(getFacesContext().getViewRoot() != null);

        // Now test with no facets... Listeners should still be registered on UICommand
        // components....
        //
        getFacesContext().setViewRoot(null);

        root = Util.getViewHandler(getFacesContext()).createView(getFacesContext(), null);
        root.setViewId(TEST_URI);

        basicForm = new UIForm();
        basicForm.setId("basicForm");
        root.getChildren().add(basicForm);
        command1 = new UICommand();
        command2 = new UICommand();
        basicForm.getChildren().add(command1);
        basicForm.getChildren().add(command2);

        session = (HttpSession)
            getFacesContext().getExternalContext().getSession(false);
        session.setAttribute(TEST_URI, root);

        restoreView = new RestoreViewPhase();

        try {
            restoreView.execute(getFacesContext());
        } catch (Throwable e) {
            assertTrue(false);
        }
        assertTrue(!(getFacesContext().getRenderResponse()) &&
                   !(getFacesContext().getResponseComplete()));

        getFacesContext().setViewRoot(null);
    }


} // end of class TestRestoreViewPhase

