/*
 * $Id: TestUpdateModelValuesPhase.java,v 1.36.40.1 2006/04/12 19:33:11 ofung Exp $
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

// TestUpdateModelValuesPhase.java

package com.sun.faces.lifecycle;

import com.sun.faces.ServletFacesTestCase;
import com.sun.faces.TestBean;
import com.sun.faces.util.Util;
import org.apache.cactus.WebRequest;

import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIViewRoot;

/**
 * <B>TestUpdateModelValuesPhase</B> is a class ...
 * <p/>
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TestUpdateModelValuesPhase.java,v 1.36.40.1 2006/04/12 19:33:11 ofung Exp $
 */

public class TestUpdateModelValuesPhase extends ServletFacesTestCase {

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

    public TestUpdateModelValuesPhase() {
        super("TestUpdateModelValuesPhase");
    }


    public TestUpdateModelValuesPhase(String name) {
        super(name);
    }

//
// Class methods
//

//
// General Methods
//

    public void testUpdateNormal() {
//DebugUtil.waitForDebugger();
        UIForm form = null;
        TestUIInput userName = null;
        TestUIInput userName1 = null;
        TestUIInput userName2 = null;
        TestBean testBean = (TestBean)
            (getFacesContext().getExternalContext().getSessionMap()).get(
                "TestBean");
        String value = null;
        Phase updateModelValues = new UpdateModelValuesPhase();
        form = new UIForm();
        form.setId("form");
        form.setSubmitted(true);
        userName = new TestUIInput();
        userName.setId("userName");
        userName.setValue("one");
        userName.setValueBinding("value",
                                 Util.getValueBinding("#{TestBean.one}"));
        userName.testSetValid(true);
        form.getChildren().add(userName);
        userName1 = new TestUIInput();
        userName1.setId("userName1");
        userName1.setValue("one");
        userName1.setValueBinding("value",
                                  Util.getValueBinding("#{TestBean.one}"));
        userName1.testSetValid(true);
        form.getChildren().add(userName1);
        userName2 = new TestUIInput();
        userName2.setId("userName2");
        userName2.setValue("one");
        userName2.setValueBinding("value",
                                  Util.getValueBinding("#{TestBean.one}"));
        userName2.testSetValid(true);
        form.getChildren().add(userName2);

        UIViewRoot viewRoot = Util.getViewHandler(getFacesContext()).createView(getFacesContext(), null);
        viewRoot.getChildren().add(form);
        viewRoot.setViewId("updateModel.xul");
        getFacesContext().setViewRoot(viewRoot);

        try {
            updateModelValues.execute(getFacesContext());
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertTrue(!(getFacesContext().getRenderResponse()) &&
                   !(getFacesContext().getResponseComplete()));
        assertTrue(null == userName.getLocalValue());

        assertTrue(testBean.getOne().equals("one"));
        assertTrue(false == (getFacesContext().getMessages().hasNext()));
    }


    public void testUpdateFailed() {
        UIForm form = null;
        TestUIInput userName = null;
        TestUIInput userName1 = null;
        TestUIInput userName2 = null;
        String value = null;
        Phase
            updateModelValues = new UpdateModelValuesPhase();
        form = new UIForm();
        form.setId("form");
        form.setSubmitted(true);
        userName = new TestUIInput();
        userName.setId("userName");
        userName.setValue("one");
        userName.testSetValid(true);
        userName.setValueBinding("value",
                                 Util.getValueBinding("#{TestBean.two}"));
        form.getChildren().add(userName);
        userName1 = new TestUIInput();
        userName1.setId("userName1");
        userName1.setValue("one");
        userName1.testSetValid(true);
        userName1.setValueBinding("value",
                                  Util.getValueBinding("#{TestBean.one}"));
        form.getChildren().add(userName1);
        userName2 = new TestUIInput();
        userName2.setId("userName2");
        userName2.setValue("one");
        userName2.setValueBinding("value",
                                  Util.getValueBinding("#{TestBean.one}"));
        userName2.testSetValid(true);
        form.getChildren().add(userName2);

        UIViewRoot viewRoot = Util.getViewHandler(getFacesContext()).createView(getFacesContext(), null);
        viewRoot.getChildren().add(form);
        viewRoot.setViewId("updateModel.xul");
        getFacesContext().setViewRoot(viewRoot);

        // This stage will go to render, since there was at least one error
        // during component updates...
        try {
            updateModelValues.execute(getFacesContext());
        } catch (Throwable e) {
            e.printStackTrace();
            assertTrue(false);
        }

        assertTrue(getFacesContext().getRenderResponse());

        assertTrue(true == (getFacesContext().getMessages().hasNext()));

    }


    public static class TestUIInput extends UIInput {

        public void testSetValid(boolean validState) {
            this.setValid(validState);
        }

    }

} // end of class TestUpdateModelValuesPhase
