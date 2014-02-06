/*
 * $Id: TestMethodRef.java,v 1.5.32.1 2006/04/12 19:33:05 ofung Exp $
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

// TestMethodRef.java

package com.sun.faces.el;

import com.sun.faces.ServletFacesTestCase;

import javax.faces.el.MethodNotFoundException;

/**
 * <B>TestMethodRef</B> is a class ...
 * <p/>
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TestMethodRef.java,v 1.5.32.1 2006/04/12 19:33:05 ofung Exp $
 */

public class TestMethodRef extends ServletFacesTestCase {

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

    public TestMethodRef() {
        super("TestMethodRef");
    }


    public TestMethodRef(String name) {
        super(name);
    }

//
// Class methods
//

//
// General Methods
//


    public void testInvalidTrailing() throws Exception {

        MethodBindingImpl mb =
            new MethodBindingImpl(getFacesContext().getApplication(),
                                  "NewCustomerFormHandler.redLectroidsMmmm",
                                  new Class[0]);

        boolean exceptionThrown = false;
        try {
            mb.invoke(getFacesContext(), new Object[0]);
        } catch (MethodNotFoundException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);

        mb = new MethodBindingImpl(getFacesContext().getApplication(),
                                   "nonexistentBean.redLectroidsMmmm",
                                   new Class[0]);
        exceptionThrown = false;
        try {
            mb.invoke(getFacesContext(), new Object[0]);
        } catch (MethodNotFoundException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

} // end of class TestMethodRef
