/*
 * $Id: UISelectManyTestCase.java,v 1.27.28.1 2006/04/12 19:30:38 ofung Exp $
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

package javax.faces.component;


import junit.framework.Test;
import junit.framework.TestSuite;

import javax.faces.el.ValueBinding;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Unit tests for {@link UISelectMany}.</p>
 */

public class UISelectManyTestCase extends UIInputTestCase {


    // ------------------------------------------------------------ Constructors


    /**
     * Construct a new instance of this test case.
     *
     * @param name Name of the test case
     */
    public UISelectManyTestCase(String name) {
        super(name);
    }


    // ---------------------------------------------------- Overall Test Methods


    // Set up instance variables required by this test case.
    public void setUp() {
        super.setUp();
        component = new UISelectMany();
        expectedFamily = UISelectMany.COMPONENT_FAMILY;
        expectedRendererType = "javax.faces.Listbox";
    }

    
    // Return the tests included in this test case.
    public static Test suite() {
        return (new TestSuite(UISelectManyTestCase.class));
    }


    // Tear down instance variables required by ths test case
    public void tearDown() {
        super.tearDown();
    }


    // ------------------------------------------------- Individual Test Methods


    // Test the compareValues() method
    public void testCompareValues() {

        TestSelectMany selectMany = new TestSelectMany();
        Object values1a[] = new Object[] { "foo", "bar", "baz" };
        Object values1b[] = new Object[] { "foo", "baz", "bar" };
        Object values1c[] = new Object[] { "baz", "foo", "bar" };
        Object values2[] = new Object[] { "foo", "bar" };
        Object values3[] = new Object[] { "foo", "bar", "baz", "bop" };
        Object values4[] = null;

        assertTrue(!selectMany.compareValues(values1a, values1a));
        assertTrue(!selectMany.compareValues(values1a, values1b));
        assertTrue(!selectMany.compareValues(values1a, values1c));
        assertTrue(!selectMany.compareValues(values2, values2));
        assertTrue(!selectMany.compareValues(values3, values3));
        assertTrue(!selectMany.compareValues(values4, values4));

        assertTrue(selectMany.compareValues(values1a, values2));
        assertTrue(selectMany.compareValues(values1a, values3));
        assertTrue(selectMany.compareValues(values1a, values4));
        assertTrue(selectMany.compareValues(values2, values3));
        assertTrue(selectMany.compareValues(values2, values4));
        assertTrue(selectMany.compareValues(values4, values1a));
        assertTrue(selectMany.compareValues(values4, values2));
        assertTrue(selectMany.compareValues(values4, values3));

    }


    // Test a pristine UISelectMany instance
    public void testPristine() {

        super.testPristine();
        UISelectMany selectMany = (UISelectMany) component;

        assertNull("no selectedValues", selectMany.getSelectedValues());

    }


    // Test setting properties to invalid values
    public void testPropertiesInvalid() throws Exception {

        super.testPropertiesInvalid();
        UISelectMany selectMany = (UISelectMany) component;

    }


    // Test setting properties to valid values
    public void testPropertiesValid() throws Exception {

        super.testPropertiesValid();
        UISelectMany selectMany = (UISelectMany) component;

        Object values[] = new Object[] { "foo", "bar" };

        selectMany.setSelectedValues(values);
        assertEquals(values, selectMany.getSelectedValues());
        assertEquals(values, selectMany.getValue());
        selectMany.setSelectedValues(null);
        assertNull(selectMany.getSelectedValues());
        assertNull(selectMany.getValue());

        // Test transparency between "value" and "selectedValues" properties
        selectMany.setValue(values);
        assertEquals(values, selectMany.getSelectedValues());
        assertEquals(values, selectMany.getValue());
        selectMany.setValue(null);
        assertNull(selectMany.getSelectedValues());
        assertNull(selectMany.getValue());

        // Transparency applies to value bindings as well
        assertNull(selectMany.getValueBinding("selectedValues"));
        assertNull(selectMany.getValueBinding("value"));
        request.setAttribute("foo", new Object[] { "bar", "baz" });
        ValueBinding vb = application.createValueBinding("#{foo}");
        selectMany.setValueBinding("selectedValues", vb);
        assertTrue(vb == selectMany.getValueBinding("selectedValues"));
        assertTrue(vb == selectMany.getValueBinding("value"));
        selectMany.setValueBinding("selectedValues", null);
        assertNull(selectMany.getValueBinding("selectedValues"));
        assertNull(selectMany.getValueBinding("value"));
        selectMany.setValueBinding("value", vb);
        assertTrue(vb == selectMany.getValueBinding("selectedValues"));
        assertTrue(vb == selectMany.getValueBinding("value"));
        selectMany.setValueBinding("selectedValues", null);
        assertNull(selectMany.getValueBinding("selectedValues"));
        assertNull(selectMany.getValueBinding("value"));

    }



    // Test validation of value against the valid list
    public void testValidation() throws Exception {

        // Put our component under test in a tree under a UIViewRoot
        UIViewRoot root = facesContext.getApplication().getViewHandler().createView(facesContext, null);
        root.getChildren().add(component);

        // Add valid options to the component under test
        UISelectMany selectMany = (UISelectMany) component;
        selectMany.getChildren().add(new UISelectItemSub("foo", null, null));
        selectMany.getChildren().add(new UISelectItemSub("bar", null, null));
        selectMany.getChildren().add(new UISelectItemSub("baz", null, null));

        // Validate two values that are on the list
        selectMany.setValid(true);
        selectMany.setSubmittedValue(new Object[] { "foo", "baz" });
        selectMany.validate(facesContext);
        assertTrue(selectMany.isValid());

        // Validate one value on the list and one not on the list
        selectMany.setValid(true);
        selectMany.setSubmittedValue(new Object[] { "bar", "bop"});
        selectMany.setRendererType(null); // We don't have any renderers
        selectMany.validate(facesContext);
        assertTrue(!selectMany.isValid());

    }


    // Test validation of component with UISelectItems pointing to map
    public void testValidation2() throws Exception {

         // Put our component under test in a tree under a UIViewRoot
        UIViewRoot root = facesContext.getApplication().getViewHandler().createView(facesContext, null);
        root.getChildren().add(component);

        // Add valid options to the component under test
        Map map = new HashMap();
        map.put("key_foo", "foo");
        map.put("key_bar", "bar");
        map.put("key_baz", "baz");
        UISelectItems items = new UISelectItems();
        items.setValue(map);
        UISelectMany selectMany = (UISelectMany) component;
        selectMany.getChildren().add(items);

        // Validate two values that are on the list
        selectMany.setValid(true);
        selectMany.setSubmittedValue(new Object[] { "foo", "baz" });
        selectMany.validate(facesContext);
        assertTrue(selectMany.isValid());

        // Validate one value on the list and one not on the list
        selectMany.setValid(true);
        selectMany.setSubmittedValue(new Object[] { "bar", "bop"});
        selectMany.setRendererType(null); // We don't have any renderers
        selectMany.validate(facesContext);
        assertTrue(!selectMany.isValid());

    }


    private String legalValues[] =
    { "A1", "A2", "A3", "B1", "B2", "B3", "C1", "C2", "C3" };


    private String illegalValues[] =
    { "D1", "D2", "Group A", "Group B", "Group C" };

    // Test validation against a nested list of available options
    public void testValidateNested() throws Exception {

        // Set up UISelectMany with nested UISelectItems
        UIViewRoot root = facesContext.getApplication().getViewHandler().createView(facesContext, null);
        root.getChildren().add(component);
        UISelectMany selectMany = (UISelectMany) component;
        UISelectItems selectItems = new UISelectItems();
        selectItems.setValue(setupOptions());
        selectMany.getChildren().add(selectItems);
        selectMany.setRequired(true);
        checkMessages(0);

        // Verify that all legal values will validate
        for (int i = 0; i < legalValues.length; i++) {
            selectMany.setValid(true);
            selectMany.setSubmittedValue
                (new Object[] { legalValues[0], legalValues[i] });
            selectMany.validate(facesContext);
            assertTrue("Value '" + legalValues[i] + "' found",
                       selectMany.isValid());
            checkMessages(0);
        }

        // Verify that illegal values will not validate
        for (int i = 0; i < illegalValues.length; i++) {
            selectMany.setValid(true);
            selectMany.setSubmittedValue
                (new Object[] { legalValues[0], illegalValues[i] });
            selectMany.validate(facesContext);
            assertTrue("Value '" + illegalValues[i] + "' not found",
                       !selectMany.isValid());
            checkMessages(i + 1);
        }


    }


    // Test validation of a required field
    public void testValidateRequired() throws Exception {

        UIViewRoot root = facesContext.getApplication().getViewHandler().createView(facesContext, null);
        root.getChildren().add(component);
        UISelectMany selectMany = (UISelectMany) component;
        selectMany.getChildren().add(new UISelectItemSub("foo", null, null));
        selectMany.getChildren().add(new UISelectItemSub("bar", null, null));
        selectMany.getChildren().add(new UISelectItemSub("baz", null, null));
        selectMany.setRequired(true);
        checkMessages(0);

        selectMany.setValid(true);
        selectMany.setSubmittedValue(new Object[] { "foo" });
        selectMany.validate(facesContext);
        checkMessages(0);
        assertTrue(selectMany.isValid());

        selectMany.setValid(true);
        selectMany.setSubmittedValue(new Object[] { "" });
        selectMany.validate(facesContext);
        checkMessages(1);
        assertTrue(!selectMany.isValid());

        selectMany.setValid(true);
        selectMany.setSubmittedValue(null);
	// this execution of validate shouldn't add any messages to the
	// queue, since a value of null means "don't validate".  This is
	// different behavior than in previous versions of this
	// testcase, which expected the UISelectMany.validate() to
	// operate on the previously validated value, which is not
	// correct.
        selectMany.validate(facesContext);
        checkMessages(1);
	// since we're setting the submitted value to null, we don't
	// want validation to occurr, therefore, the valid state of the
	// componet should be as we left it.
        assertTrue(selectMany.isValid());

    }


    // Test that appropriate properties are value binding enabled
    public void testValueBindings() {

	super.testValueBindings();
	UISelectMany test = (UISelectMany) component;

	// "value" property
	request.setAttribute("foo", "bar");
	test.setValue(null);
	assertNull(test.getValue());
	test.setValueBinding("value", application.createValueBinding("#{foo}"));
	assertNotNull(test.getValueBinding("value"));
	assertEquals("bar", test.getValue());
	test.setValue("baz");
	assertEquals("baz", test.getValue());
	test.setValue(null);
	assertEquals("bar", test.getValue());
	test.setValueBinding("value", null);
	assertNull(test.getValueBinding("value"));
	assertNull(test.getValue());

    }


    // --------------------------------------------------------- Support Methods


    // Create a pristine component of the type to be used in state holder tests
    protected UIComponent createComponent() {
        UIComponent component = new UISelectMany();
        component.setRendererType(null);
        return (component);
    }


    protected void setupNewValue(UIInput input) {

        input.setSubmittedValue(new Object[] { "foo" });
        UISelectItem si = new UISelectItem();
        si.setItemValue("foo");
        si.setItemLabel("foo label");
        input.getChildren().add(si);

    }


    // Create an options list with nested groups
    protected List setupOptions() {
        SelectItemGroup group, subgroup;
        subgroup = new SelectItemGroup("Group C");
        subgroup.setSelectItems(new SelectItem[]
            { new SelectItem("C1"),
              new SelectItem("C2"),
              new SelectItem("C3") });
        List options = new ArrayList();
        options.add(new SelectItem("A1"));
        group = new SelectItemGroup("Group B");
        group.setSelectItems(new SelectItem[]
            { new SelectItem("B1"),
              subgroup,
              new SelectItem("B2"),
              new SelectItem("B3") });
        options.add(group);
        options.add(new SelectItem("A2"));
        options.add(new SelectItem("A3"));
        return (options);
    }



}
