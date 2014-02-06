/*
 * $Id: UniqueViewIdTestCase.java,v 1.1.32.1.2.1 2006/04/12 19:32:53 ofung Exp $
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

package com.sun.faces.systest;


import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.sun.faces.htmlunit.AbstractTestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.List;

/**
 * <p>Make sure that only unique view ids are saved in the session</p>
 */

public class UniqueViewIdTestCase extends AbstractTestCase {


    // ------------------------------------------------------------ Constructors


    /**
     * Construct a new instance of this test case.
     *
     * @param name Name of the test case
     */
    public UniqueViewIdTestCase(String name) {
        super(name);
    }


    // ------------------------------------------------------ Instance Variables


    // ---------------------------------------------------- Overall Test Methods


    /**
     * Set up instance variables required by this test case.
     */
    public void setUp() throws Exception {
        super.setUp();
    }


    /**
     * Return the tests included in this test suite.
     */
    public static Test suite() {
        return (new TestSuite(UniqueViewIdTestCase.class));
    }


    /**
     * Tear down instance variables required by this test case.
     */
    public void tearDown() {
        super.tearDown();
    }


    // ------------------------------------------------------ Instance Variables



    // ------------------------------------------------- Individual Test Methods

    /**
     *
     * <p>See that clicking the re-submit button 20 times doesn't
     * increment the view counter.</p>
     */

    public void testReSubmitDoesNotIncrementCounter() throws Exception {
	HtmlPage page = getPage("/faces/reset-statemanager.jsp");
        page = getPage("/faces/test.jsp");
	List list;
	HtmlSubmitInput button = null;
	for (int i = 0; i < 20; i++) {
	    list = getAllElementsOfGivenClass(page, null, 
					      HtmlSubmitInput.class); 
	    button = (HtmlSubmitInput) list.get(0);
	    page = (HtmlPage) button.click();
	}
        HtmlElement element = page.getHtmlElementById("com.sun.faces.VIEW");
        assertTrue(element instanceof HtmlHiddenInput);
        HtmlHiddenInput hidden = (HtmlHiddenInput) element;
        String value = hidden.getValueAttribute();
        assertTrue(value.startsWith("_id2:"));
    }

    /**
     *
     * <p>See that clicking to a new page and back again does increment
     * the view counter.</p>
     */

    public void testNewPageDoesIncrementCounter() throws Exception {
	HtmlPage page = getPage("/faces/reset-statemanager.jsp");
        page = getPage("/faces/test.jsp");
	List list;
	list = getAllElementsOfGivenClass(page, null, 
					  HtmlSubmitInput.class); 
	HtmlSubmitInput button = (HtmlSubmitInput) list.get(1);
	page = (HtmlPage) button.click();

	list = getAllElementsOfGivenClass(page, null, 
					  HtmlSubmitInput.class); 
	button = (HtmlSubmitInput) list.get(0);
	page = (HtmlPage) button.click();

        HtmlElement element = page.getHtmlElementById("com.sun.faces.VIEW");
        assertTrue(element instanceof HtmlHiddenInput);
        HtmlHiddenInput hidden = (HtmlHiddenInput) element;
        String value = hidden.getValueAttribute();
        assertEquals("_id2:_id5", value);

    }


}
