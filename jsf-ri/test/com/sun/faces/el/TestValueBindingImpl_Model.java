/*
 * $Id: TestValueBindingImpl_Model.java,v 1.8.32.1 2006/04/12 19:33:06 ofung Exp $
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

// TestValueBindingImpl_Model.java

package com.sun.faces.el;

import com.sun.faces.ServletFacesTestCase;
import com.sun.faces.TestBean;
import com.sun.faces.TestBean.Inner2Bean;
import com.sun.faces.TestBean.InnerBean;
import com.sun.faces.application.ApplicationImpl;
import org.apache.cactus.WebRequest;

import javax.faces.context.FacesContext;
import javax.faces.el.PropertyNotFoundException;

/**
 * <B>TestValueBindingImpl_Model</B> is a class ...
 * <p/>
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TestValueBindingImpl_Model.java,v 1.8.32.1 2006/04/12 19:33:06 ofung Exp $
 */

public class TestValueBindingImpl_Model extends ServletFacesTestCase {

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
    ValueBindingImpl valueBinding = null;

//
// Constructors and Initializers    
//

    public TestValueBindingImpl_Model() {
        super("TestValueBindingImpl");
    }


    public TestValueBindingImpl_Model(String name) {
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

    public void setUp() {
        super.setUp();
        valueBinding = new ValueBindingImpl(getFacesContext().getApplication());
    }


    public void tearDown() {
        valueBinding = null;
        super.tearDown();
    }


    public void testSet() {
        FacesContext facesContext = getFacesContext();
        System.out.println("Testing setValue() with model bean in session ");
        TestBean testBean = new TestBean();
        InnerBean inner = new InnerBean();
        Inner2Bean innerInner = new Inner2Bean();

        getFacesContext().getExternalContext().getSessionMap().put("TestBean",
                                                                   testBean);

        // Test one level of nesting
        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("TestBean.one");
        valueBinding.setValue(getFacesContext(), "one");
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));

        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("sessionScope.TestBean.inner");
        valueBinding.setValue(getFacesContext(), inner);
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));

        // Test two levels of nesting
        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("sessionScope.TestBean.inner.two");
        valueBinding.setValue(getFacesContext(), "two");
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));

        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("sessionScope.TestBean.inner.inner2");
        valueBinding.setValue(getFacesContext(), innerInner);
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));

        // Test three levels of nesting
        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("sessionScope.TestBean.inner.inner2.three");
        valueBinding.setValue(getFacesContext(), "three");
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));
    }


    public void testSetNull() {
        FacesContext facesContext = getFacesContext();
        System.out.println(
            "Testing setValue() with model bean in session with null rValues");
        TestBean testBean = new TestBean();
        InnerBean inner = new InnerBean();
        Inner2Bean innerInner = new Inner2Bean();

        getFacesContext().getExternalContext().getSessionMap().put("TestBean",
                                                                   testBean);

        // Test one level of nesting
        valueBinding.setRef("TestBean.one");
        valueBinding.setValue(getFacesContext(), null);
        assertTrue(testBean.getOne() == null);

        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("sessionScope.TestBean.inner");
        valueBinding.setValue(getFacesContext(), inner);
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));

        valueBinding.setRef("sessionScope.TestBean.inner");
        valueBinding.setValue(getFacesContext(), null);
        assertTrue(testBean.getInner() == null);

        // Test two levels of nesting
        valueBinding.setRef("sessionScope.TestBean.inner.two");
        valueBinding.setValue(getFacesContext(), null);
        assertTrue(testBean.getInner().getTwo() == null);

        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("sessionScope.TestBean.inner.inner2");
        valueBinding.setValue(getFacesContext(), innerInner);
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));

        valueBinding.setRef("sessionScope.TestBean.inner.inner2");
        valueBinding.setValue(getFacesContext(), null);
        assertTrue(testBean.getInner().getInner2() == null);

        // Test three levels of nesting
        valueBinding.setRef("sessionScope.TestBean.inner.inner2.three");
        valueBinding.setValue(getFacesContext(), null);
        assertTrue(testBean.getInner().getInner2().getThree() == null);
    }


    public void testSetWithNoCurlyBraces() {
        FacesContext facesContext = getFacesContext();
        System.out.println("Testing setValue() with model bean in request ");
        TestBean testBean = new TestBean();
        InnerBean inner = new InnerBean();
        Inner2Bean innerInner = new Inner2Bean();

        facesContext.getExternalContext().getSessionMap().remove("TestBean");
        facesContext.getExternalContext().getRequestMap().put("TestBean",
                                                              testBean);

        // Test implicit scopes direct access to some scope objects should
        // throw an illegalArgumentException
        boolean gotException = false;
        try {
            valueBinding.setRef("header.header-one");
            valueBinding.setValue(getFacesContext(), testBean);
        } catch (PropertyNotFoundException pnf) {
            gotException = true;
        }
        assertTrue(gotException);

        // Test one level of nesting
        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("TestBean.one");
        valueBinding.setValue(getFacesContext(), "one");
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));

        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("requestScope.TestBean.inner");
        valueBinding.setValue(getFacesContext(), inner);
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));

        // Test two levels of nesting
        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("requestScope.TestBean.inner.two");
        valueBinding.setValue(getFacesContext(), "two");
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));

        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("requestScope.TestBean.inner.inner2");
        valueBinding.setValue(getFacesContext(), innerInner);
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));

        // Test three levels of nesting
        System.setProperty(TestBean.PROP, TestBean.FALSE);
        valueBinding.setRef("requestScope.TestBean.inner.inner2.three");
        valueBinding.setValue(getFacesContext(), "three");
        assertTrue(System.getProperty(TestBean.PROP).equals(TestBean.TRUE));
    }


    public void testGet() {
        FacesContext facesContext = getFacesContext();
        System.out.println("Testing getValue() with model bean in context");
        assertTrue(facesContext != null);
        TestBean testBeanResult = null, testBean = new TestBean();
        InnerBean inner = new InnerBean();
        Inner2Bean inner2 = new Inner2Bean();
        String result;

        // Init the beans
        testBean.setOne("one");
        inner.setTwo("two");
        inner2.setThree("three");
        inner.setInner2(inner2);
        testBean.setInner(inner);

        assertTrue(facesContext != null);
        assertTrue(facesContext.getExternalContext().getSession(false) != null);

        facesContext.getExternalContext().getRequestMap().remove("TestBean");
        facesContext.getExternalContext().getSessionMap().remove("TestBean");
        facesContext.getExternalContext().getApplicationMap().put("TestBean",
                                                                  testBean);

        // Test zero levels of nesting
        valueBinding.setRef("applicationScope.TestBean");
        testBeanResult = (TestBean) valueBinding.getValue(getFacesContext());
        assertTrue(testBeanResult != null);
        assertTrue(testBeanResult == testBean);

        // Test one level of nesting
        valueBinding.setRef("applicationScope.TestBean.one");
        result = (String) valueBinding.getValue(getFacesContext());
        assertTrue(result.equals("one"));

        valueBinding.setRef("applicationScope.TestBean.inner");
        inner = (InnerBean) valueBinding.getValue(getFacesContext());
        assertTrue(null != inner);

        // Test two levels of nesting
        valueBinding.setRef("applicationScope.TestBean.inner.two");
        result = (String) valueBinding.getValue(getFacesContext());
        assertTrue(result.equals("two"));

        valueBinding.setRef("applicationScope.TestBean.inner.inner2");
        inner2 = (Inner2Bean)
            valueBinding.getValue(getFacesContext());
        assertTrue(null != inner2);

        // Test three levels of nesting
        valueBinding.setRef("applicationScope.TestBean.inner.inner2.three");
        result = (String) valueBinding.getValue(getFacesContext());
        assertTrue(result.equals("three"));

    }


    public void testModelType() {
        /***************** PENDING(edburns):

         // Test model type
         System.out.println("Testing getModelType()");
         Class classType = null;
         String className = null;

         // Test zero levels of nesting
         classType = facesContext.getModelType("applicationScope.TestBean");
         assertTrue(classType != null);
         className = classType.getName();
         assertTrue(className.equals(testBean.getClass().getName()));

         classType = facesContext.getModelType("applicationScope.TestBean.inner.pin");
         assertTrue(classType != null);
         className = classType.getName();
         assertTrue(className.equals("java.lang.Integer"));

         classType = facesContext.getModelType("applicationScope.TestBean.inner.result");
         assertTrue(classType != null);
         className = classType.getName();
         assertTrue(className.equals("java.lang.Boolean"));

         classType = facesContext.getModelType("applicationScope.TestBean.one");
         assertTrue(classType != null);
         className = classType.getName();
         assertTrue(className.equals("java.lang.String"));
         *********************/
    }

} // end of class TestValueBindingImpl_Model
