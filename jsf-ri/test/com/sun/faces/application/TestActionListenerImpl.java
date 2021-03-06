/*
 * $Id: TestActionListenerImpl.java,v 1.22.44.1 2006/04/12 19:32:59 ofung Exp $
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

// TestActionListenerImpl.java

package com.sun.faces.application;

import com.sun.faces.ServletFacesTestCase;
import com.sun.faces.util.Util;
import org.apache.cactus.WebRequest;

import javax.faces.FacesException;
import javax.faces.component.UICommand;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.MethodNotFoundException;
import javax.faces.event.ActionEvent;


/**
 *
 *  <B>TestActionListenerImpl</B> is a class ...
 *
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TestActionListenerImpl.java,v 1.22.44.1 2006/04/12 19:32:59 ofung Exp $
 */

/**
 * This class tests the <code>ActionListenerImpl</code> class
 * functionality.  It uses the xml configuration file:
 * <code>web/test/WEB-INF/faces-navigation.xml</code>.
 */
public class TestActionListenerImpl extends ServletFacesTestCase {

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

    public TestActionListenerImpl() {
        super("TestActionListenerImpl");
    }


    public TestActionListenerImpl(String name) {
        super(name);
    }
//
// Class methods
//

//
// Methods from TestCase
//

//
// General Methods
//

    public void testProcessAction() {
        loadFromInitParam("/WEB-INF/faces-navigation.xml");
        FacesContext context = getFacesContext();

        System.out.println("Testing With Action Literal Set...");

        UICommand command = new UICommand();
        command.setAction(
            context.getApplication().createMethodBinding(
                "#{newCustomer.loginRequired}", null));
        UIViewRoot page = Util.getViewHandler(context).createView(context, null);
        page.setViewId("/login.jsp");
        context.setViewRoot(page);

        ActionListenerImpl actionListener = new ActionListenerImpl();
        ActionEvent actionEvent = new ActionEvent(command);

        actionListener.processAction(actionEvent);

        String newViewId = context.getViewRoot().getViewId();
        assertTrue(newViewId.equals("/must-login-first.jsp"));

        System.out.println("Testing With Action Set...");

        command = new UICommand();
        MethodBinding binding =
            context.getApplication().createMethodBinding("#{userBean.login}",
                                                         null);
        command.setAction(binding);

        UserBean user = new UserBean();
        context.getExternalContext().getSessionMap().put("userBean", user);
        assertTrue(
            user ==
            context.getExternalContext().getSessionMap().get("userBean"));

        page = Util.getViewHandler(context).createView(context, null);
        page.setViewId("/login.jsp");
        context.setViewRoot(page);

        actionEvent = new ActionEvent(command);
        actionListener.processAction(actionEvent);

        newViewId = context.getViewRoot().getViewId();
        // expected outcome should be view id corresponding to "page/outcome" search..

        assertTrue(newViewId.equals("/home.jsp"));
    }


    public void testIllegalArgException() {
        boolean exceptionThrown = false;

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot page = Util.getViewHandler(getFacesContext()).createView(context, null);
        page.setViewId("/login.jsp");
        context.setViewRoot(page);
        UserBean user = new UserBean();
        context.getExternalContext().getApplicationMap().put("UserBean", user);

        assertTrue(
            user ==
            context.getExternalContext().getApplicationMap().get("UserBean"));

        UICommand command = new UICommand();
        MethodBinding binding =
            context.getApplication().createMethodBinding("#{UserBean.noMeth}",
                                                         null);
        command.setAction(binding);
        ActionEvent actionEvent = new ActionEvent(command);

        ActionListenerImpl actionListener = new ActionListenerImpl();
        try {
            actionListener.processAction(actionEvent);
        } catch (FacesException e) {
            assertTrue(e.getCause() instanceof MethodNotFoundException);
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }


    public static class UserBean extends Object {

        public String login() {
            return ("success");
        }

    }

} // end of class TestActionListenerImpl

