/*
 * $Id: TestPropertyResolverImpl.java,v 1.13.32.1 2006/04/12 19:33:05 ofung Exp $
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

// TestPropertyResolverImpl.java

package com.sun.faces.el;

import com.sun.faces.ServletFacesTestCase;
import com.sun.faces.TestBean;
import com.sun.faces.util.Util;
import org.apache.cactus.WebRequest;

import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;

import java.util.List;


/**
 * <B>TestPropertyResolverImpl</B> is a class ...
 * <p/>
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TestPropertyResolverImpl.java,v 1.13.32.1 2006/04/12 19:33:05 ofung Exp $
 */

public class TestPropertyResolverImpl extends ServletFacesTestCase {

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

    private ElBean bean = null;
    private PropertyResolverImpl resolver = null;

//
// Constructors and Initializers    
//

    public TestPropertyResolverImpl() {
        super("TestFacesContext");
    }


    public TestPropertyResolverImpl(String name) {
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
        bean = new ElBean();
        resolver = new PropertyResolverImpl();
    }


    public void tearDown() {
        resolver = null;
        bean = null;
        super.tearDown();
    }

//
// General Methods
//

    // Negative getValue() tests on a JavaBean base object
    public void testNegative() throws Exception {

        Object value = null;


        // ---------- Should Return Null ----------
        value = resolver.getValue(bean, null);
        assertNull(value);

        value = resolver.getValue(null, "booleanProperty");
        assertNull(value);

        value = resolver.getValue(null, null);
        assertNull(value);

        value = resolver.getValue(bean.getIntArray(), -1);
        assertNull(value);

        value = resolver.getValue(bean.getIntArray(), 3);
        assertNull(value);

        value = resolver.getValue(bean.getIntList(), -1);
        assertNull(value);

        value = resolver.getValue(bean.getIntList(), 5);
        assertNull(value);
            
        // ---------- Should throw EvaluationException

        try {
            value = resolver.getValue(bean, "nullStringProperty");
            fail("Should have thrown EvaluationException");
        } catch (EvaluationException e) {
            ; // Expected result
        }

        // ---------- Should Throw PropertyNotFoundException

        try {
            value = resolver.getValue(bean, "dontExist");
            fail("Should have thrown EvaluationException");
        } catch (PropertyNotFoundException e) {
            ; // Expected result
        }

    }


    public void testPristine() {

        // PENDING - test pristine condition of a new instance

    }


    // -------------------------------------------------- Indexed Variant Tests


    // Positive getValue() tests for the indexed variant against an array
    public void testIndexedGetArray() throws Exception {

        Object value = null;
        int intArray[] = bean.getIntArray();
        assertEquals(3, intArray.length);

        value = resolver.getValue(intArray, 0);
        assertNotNull(value);
        assertTrue(value instanceof Integer);
        assertEquals(1, ((Integer) value).intValue());

        value = resolver.getValue(intArray, 1);
        assertNotNull(value);
        assertTrue(value instanceof Integer);
        assertEquals(2, ((Integer) value).intValue());

        value = resolver.getValue(intArray, 2);
        assertNotNull(value);
        assertTrue(value instanceof Integer);
        assertEquals(3, ((Integer) value).intValue());

    }


    // Positive getValue() tests for the indexed variant against a List
    public void testIndexedGetList() throws Exception {

        Object value = null;
        List intList = bean.getIntList();
        assertEquals(5, intList.size());

        value = resolver.getValue(intList, 0);
        assertNotNull(value);
        assertTrue(value instanceof Integer);
        assertEquals(10, ((Integer) value).intValue());

        value = resolver.getValue(intList, 1);
        assertNotNull(value);
        assertTrue(value instanceof Integer);
        assertEquals(20, ((Integer) value).intValue());

        value = resolver.getValue(intList, 2);
        assertNotNull(value);
        assertTrue(value instanceof Integer);
        assertEquals(30, ((Integer) value).intValue());

        value = resolver.getValue(intList, 3);
        assertNotNull(value);
        assertTrue(value instanceof Integer);
        assertEquals(40, ((Integer) value).intValue());

        value = resolver.getValue(intList, 4);
        assertNotNull(value);
        assertTrue(value instanceof Integer);
        assertEquals(50, ((Integer) value).intValue());

    }


    // Positive setValue() tests for the indexed variant against an array
    public void testIndexedSetArray() throws Exception {

        // PENDING - insert tests here

    }


    // Positive setValue() tests for the indexed variant against a List
    public void testIndexedSetList() throws Exception {

        // PENDING - insert tests here

    }


    // Positive getType() tests for the indexed variant against an array
    public void testIndexedTypeArray() throws Exception {

        // PENDING - insert tests here

    }


    // Positive getType() tests for the indexed variant against a List
    public void testIndexedTypeList() throws Exception {

        // PENDING - insert tests here

    }


    // --------------------------------------------------- String Variant Tests


    // Postitive getValue() tests on a JavaBean base object
    public void testStringGetBean() throws Exception {

        Object value = null;

        value = resolver.getValue(bean, "booleanProperty");
        assertNotNull(value);
        assertTrue(value instanceof Boolean);
        assertEquals(true, ((Boolean) value).booleanValue());

        value = resolver.getValue(bean, "byteProperty");
        assertNotNull(value);
        assertTrue(value instanceof Byte);
        assertEquals((byte) 123, ((Byte) value).byteValue());

        value = resolver.getValue(bean, "doubleProperty");
        assertNotNull(value);
        assertTrue(value instanceof Double);
        assertEquals((double) 654.321, ((Double) value).doubleValue(), 0.005);

        value = resolver.getValue(bean, "floatProperty");
        assertNotNull(value);
        assertTrue(value instanceof Float);
        assertEquals((float) 123.45, ((Float) value).floatValue(), 0.5);

        value = resolver.getValue(bean, "intProperty");
        assertNotNull(value);
        assertTrue(value instanceof Integer);
        assertEquals((int) 1234, ((Integer) value).intValue());

        value = resolver.getValue(bean, "longProperty");
        assertNotNull(value);
        assertTrue(value instanceof Long);
        assertEquals((long) 54321, ((Long) value).longValue());

        value = resolver.getValue(bean, "nestedProperty");
        assertNotNull(value);
        assertTrue(value instanceof ElBean);
        assertEquals("This is a String", ((ElBean) value).getStringProperty());

        value = resolver.getValue(bean, "shortProperty");
        assertNotNull(value);
        assertTrue(value instanceof Short);
        assertEquals((short) 321, ((Short) value).shortValue());

        value = resolver.getValue(bean, "stringProperty");
        assertNotNull(value);
        assertTrue(value instanceof String);
        assertEquals("This is a String", (String) value);

    }


    // Positive getValue() tests on a Map base object
    public void testStringGetMap() throws Exception {

        getFacesContext().getExternalContext().getRequestMap().put("testValue",
                                                                   this);
        assertTrue(this == resolver.getValue(
            getFacesContext().getExternalContext().getRequestMap(),
            "testValue"));

    }


    // Postitive setValue() tests on a JavaBean base object
    public void testStringSetBean() throws Exception {

        Object value = null;

        resolver.setValue(bean, "booleanProperty", Boolean.FALSE);
        value = resolver.getValue(bean, "booleanProperty");
        assertNotNull(value);
        assertTrue(value instanceof Boolean);
        assertEquals(false, ((Boolean) value).booleanValue());

        resolver.setValue(bean, "byteProperty", new Byte((byte) 124));
        value = resolver.getValue(bean, "byteProperty");
        assertNotNull(value);
        assertTrue(value instanceof Byte);
        assertEquals((byte) 124, ((Byte) value).byteValue());

        resolver.setValue(bean, "doubleProperty", new Double(333.333));
        value = resolver.getValue(bean, "doubleProperty");
        assertNotNull(value);
        assertTrue(value instanceof Double);
        assertEquals((double) 333.333, ((Double) value).doubleValue(), 0.005);

        resolver.setValue(bean, "floatProperty", new Float(22.11));
        value = resolver.getValue(bean, "floatProperty");
        assertNotNull(value);
        assertTrue(value instanceof Float);
        assertEquals((float) 22.11, ((Float) value).floatValue(), 0.5);

        resolver.setValue(bean, "intProperty", new Integer(4321));
        value = resolver.getValue(bean, "intProperty");
        assertNotNull(value);
        assertTrue(value instanceof Integer);
        assertEquals((int) 4321, ((Integer) value).intValue());

        resolver.setValue(bean, "longProperty", new Long(12345));
        value = resolver.getValue(bean, "longProperty");
        assertNotNull(value);
        assertTrue(value instanceof Long);
        assertEquals((long) 12345, ((Long) value).longValue());

        resolver.setValue(bean, "nestedProperty", new ElBean());
        value = resolver.getValue(bean, "nestedProperty");
        assertNotNull(value);
        assertTrue(value instanceof ElBean);

        resolver.setValue(bean, "shortProperty", new Short((short) 123));
        value = resolver.getValue(bean, "shortProperty");
        assertNotNull(value);
        assertTrue(value instanceof Short);
        assertEquals((short) 123, ((Short) value).shortValue());

        resolver.setValue(bean, "stringProperty", "That was a STRING");
        value = resolver.getValue(bean, "stringProperty");
        assertNotNull(value);
        assertTrue(value instanceof String);
        assertEquals("That was a STRING", (String) value);

    }


    // Positive setValue() tests on a Map base object
    public void testStringSetMap() throws Exception {

        // PENDING - insert tests here

    }


    // Postitive getValue() tests on a JavaBean base object
    public void testStringTypeBean() throws Exception {

        Class value = null;

        value = resolver.getType(bean, "booleanProperty");
        assertNotNull(value);
        assertEquals(Boolean.TYPE, value);

        value = resolver.getType(bean, "byteProperty");
        assertNotNull(value);
        assertEquals(Byte.TYPE, value);

        value = resolver.getType(bean, "doubleProperty");
        assertNotNull(value);
        assertEquals(Double.TYPE, value);

        value = resolver.getType(bean, "floatProperty");
        assertNotNull(value);
        assertEquals(Float.TYPE, value);

        value = resolver.getType(bean, "intProperty");
        assertNotNull(value);
        assertEquals(Integer.TYPE, value);

        value = resolver.getType(bean, "longProperty");
        assertNotNull(value);
        assertEquals(Long.TYPE, value);

        bean.setNestedProperty(new ElBean());
        value = resolver.getType(bean, "nestedProperty");
        assertNotNull(value);
        assertEquals(ElBean.class, value);

        value = resolver.getType(bean, "shortProperty");
        assertNotNull(value);
        assertEquals(Short.TYPE, value);

        value = resolver.getType(bean, "stringProperty");
        assertNotNull(value);
        assertEquals(String.class, value);

    }


    // Positive getValue() tests on a Map base object
    public void testStringTypeMap() throws Exception {

        // PENDING - insert tests here

    }


    public void testReadOnlyObject() {
        ExternalContext ec = getFacesContext().getExternalContext();

        // these are mutable Maps
        assertTrue(!resolver.isReadOnly(ec.getApplicationMap(), "hello"));
        assertTrue(!resolver.isReadOnly(ec.getSessionMap(), "hello"));
        assertTrue(!resolver.isReadOnly(ec.getRequestMap(), "hello"));

        // these are immutable Maps
        assertTrue(resolver.isReadOnly(ec.getRequestParameterMap(), "hello"));
        assertTrue(resolver.isReadOnly(ec.getRequestParameterValuesMap(),
                                       "hello"));
        assertTrue(resolver.isReadOnly(ec.getRequestHeaderMap(), "hello"));
        assertTrue(resolver.isReadOnly(ec.getRequestHeaderValuesMap(),
                                       "hello"));
        assertTrue(resolver.isReadOnly(ec.getRequestCookieMap(), "hello"));
        assertTrue(resolver.isReadOnly(ec.getInitParameterMap(), "hello"));

        UIViewRoot root = Util.getViewHandler(getFacesContext()).createView(getFacesContext(), null);
        assertTrue(resolver.isReadOnly(root, "childCount"));

        TestBean testBean = (TestBean) ec.getSessionMap().get("TestBean");
        assertTrue(resolver.isReadOnly(testBean, "readOnly"));
        assertTrue(!resolver.isReadOnly(testBean, "one"));
    }


    public void testReadOnlyIndex() {
        // PENDING(edburns): implement readonly index tests.
    }


    public void testType() {
        // PENDING(edburns): implement type tests
    }

} // end of class TestPropertyResolverImpl
