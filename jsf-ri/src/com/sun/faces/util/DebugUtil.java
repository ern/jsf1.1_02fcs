/*
 * $Id: DebugUtil.java,v 1.23.36.1 2006/04/12 19:32:32 ofung Exp $
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

package com.sun.faces.util;

// DebugUtil.java

import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.model.SelectItem;

import java.io.PrintStream;
import java.util.Iterator;

/**
 * <B>DebugUtil</B> is a class ...
 * <p/>
 * <B>Lifetime And Scope</B> <P>
 */

public class DebugUtil extends Object {

//
// Protected Constants
//

//
// Class Variables
//

    public static boolean keepWaiting = true;

    private static int curDepth = 0;

//
// Instance Variables
//

// Attribute Instance Variables

// Relationship Instance Variables

//
// Constructors and Initializers    
//

    public DebugUtil() {
        super();
        // Util.parameterNonNull();
        this.init();
    }


    protected void init() {
        // super.init();
    }

//
// Class methods
//

    /**
     * Usage: <P>
     * <p/>
     * Place a call to this method in the earliest possible entry point of
     * your servlet app.  It will cause the app to enter into an infinite
     * loop, sleeping until the static var keepWaiting is set to false.  The
     * idea is that you attach your debugger to the servlet, then, set a
     * breakpont in this method.  When it is hit, you use the debugger to set
     * the keepWaiting class var to false.
     */

    public static void waitForDebugger() {
        while (keepWaiting) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("DebugUtil.waitForDebugger(): Exception: " +
                                   e.getMessage());
            }
        }
    }


    private static void indentPrintln(PrintStream out, String str) {
        int i = 0;

        // handle indentation
        for (i = 0; i < curDepth; i++) {
            out.print("  ");
        }
        out.print(str + "\n");
    }


    public static void printTree(UIComponent root, PrintStream out) {
        if (null == root) {
            return;
        }
        int i = 0;
        Object value = null;
    
/* PENDING
    indentPrintln(out, "===>Type:" + root.getComponentType());
*/
        indentPrintln(out, "id:" + root.getId());

        Iterator items = null;
        SelectItem curItem = null;
        int j = 0;

        if (root instanceof javax.faces.component.UISelectOne) {
            items = Util.getSelectItems(null, root);
            indentPrintln(out, " {");
            while (items.hasNext()) {
                curItem = (SelectItem) items.next();
                indentPrintln(out, "\t value=" + curItem.getValue() +
                                   " label=" + curItem.getLabel() + " description=" +
                                   curItem.getDescription());
            }
            indentPrintln(out, " }");
        } else {
	    if (root instanceof ValueHolder) {
		value = ((ValueHolder)root).getValue();
	    }
            indentPrintln(out, "value= " + value);

            Iterator it = root.getAttributes().keySet().iterator();
            if (it != null) {
                while (it.hasNext()) {
                    String attrValue = null, attrName = (String) it.next();
                    Object attrObj = root.getAttributes().get(attrName);

                    if (!(attrValue instanceof String)) {
                        // chop off the address since we don't want to print
                        // out anything that'll vary from invocation to
                        // invocation
                        attrValue = attrObj.toString();
                        int at = 0;
                        boolean doTruncate = false;
                        if (-1 == (at = attrValue.indexOf("$"))) {
                            if (-1 != (at = attrValue.indexOf("@"))) {
                                doTruncate = true;
                            }
                        } else {
                            doTruncate = true;
                        }

                        if (doTruncate) {
                            attrValue = attrValue.substring(0, at);
                        }
                    } else {
                        attrValue = (String) attrObj;
                    }

                    indentPrintln(out, "attr=" + attrName +
                                       " : " + attrValue);
                }
            }
        }

        curDepth++;
        Iterator it = root.getChildren().iterator();
	Iterator facets = root.getFacets().values().iterator();
	// print all the facets of this component
	while(facets.hasNext()) {
	    printTree((UIComponent) facets.next(), out);
	}
	// print all the children of this component
        while (it.hasNext()) {
            printTree((UIComponent) it.next(), out);
        }
        curDepth--;
    }


    public static void printTree(TreeStructure root, PrintStream out) {
        if (null == root) {
            return;
        }
        int i = 0;
        Object value = null;
    
/* PENDING
    indentPrintln(out, "===>Type:" + root.getComponentType());
*/
        indentPrintln(out, "id:" + root.id);

        Iterator items = null;
        SelectItem curItem = null;
        int j = 0;

        curDepth++;
        if (null != root.children) {
            Iterator it = root.children.iterator();
            while (it.hasNext()) {
                printTree((TreeStructure) it.next(), out);
            }
        }
        curDepth--;
    }
//
// General Methods
//


} // end of class DebugUtil
