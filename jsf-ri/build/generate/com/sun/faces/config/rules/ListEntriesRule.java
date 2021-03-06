/*
 * $Id: ListEntriesRule.java,v 1.3.32.1 2006/04/12 19:33:24 ofung Exp $
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

package com.sun.faces.config.rules;


import org.apache.commons.digester.Rule;
import org.xml.sax.Attributes;
import com.sun.faces.config.beans.ListEntriesBean;
import com.sun.faces.config.beans.ListEntriesHolder;


/**
 * <p>Digester rule for the <code>&lt;list-entries&gt;</code> element.</p>
 */

public class ListEntriesRule extends Rule {


    private static final String CLASS_NAME =
        "com.sun.faces.config.beans.ListEntriesBean";


    // ------------------------------------------------------------ Rule Methods


    /**
     * <p>Create an empty instance of <code>ListEntriesBean</code>
     * and push it on to the object stack.</p>
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @param attributes The attribute list of this element
     *
     * @exception IllegalStateException if the parent stack element is not
     *  of type ListEntriesHolder
     */
    public void begin(String namespace, String name,
                      Attributes attributes) throws Exception {

        ListEntriesHolder leh = null;
        try {
            leh = (ListEntriesHolder) digester.peek();
        } catch (Exception e) {
            throw new IllegalStateException
                ("No parent ListEntriesHolder on object stack");
        }
        if (digester.getLogger().isDebugEnabled()) {
            digester.getLogger().debug("[ListEntriesRule]{" +
                                       digester.getMatch() +
                                       "} Push " + CLASS_NAME);
        }
        Class clazz =
            digester.getClassLoader().loadClass(CLASS_NAME);
        ListEntriesBean leb = (ListEntriesBean) clazz.newInstance();
        digester.push(leb);

    }


    /**
     * <p>No body processing is requlred.</p>
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     * @param text The text of the body of this element
     */
    public void body(String namespace, String name,
                     String text) throws Exception {
    }


    /**
     * <p>Pop the <code>ListEntriesBean</code> off the top of the stack,
     * and either add or merge it with previous information.</p>
     *
     * @param namespace the namespace URI of the matching element, or an 
     *   empty string if the parser is not namespace aware or the element has
     *   no namespace
     * @param name the local name if the parser is namespace aware, or just 
     *   the element name otherwise
     *
     * @exception IllegalStateException if the popped object is not
     *  of the correct type
     */
    public void end(String namespace, String name) throws Exception {

        ListEntriesBean top = null;
        try {
            top = (ListEntriesBean) digester.pop();
        } catch (Exception e) {
            throw new IllegalStateException("Popped object is not a " +
                                            CLASS_NAME + " instance");
        }
        ListEntriesHolder leh = (ListEntriesHolder) digester.peek();
        ListEntriesBean old = leh.getListEntries();
        if (old == null) {
            if (digester.getLogger().isDebugEnabled()) {
                digester.getLogger().debug("[ListEntriesRule]{" +
                                           digester.getMatch() +
                                           "} New");
            }
            leh.setListEntries(top);
        } else {
            if (digester.getLogger().isWarnEnabled()) {
                digester.getLogger().warn("[ManagedBeanRule]{" +
                                          digester.getMatch() +
                                          "} Merge");
            }
            mergeListEntries(top, old);
        }

    }


    /**
     * <p>No finish processing is required.</p>
     *
     */
    public void finish() throws Exception {
    }


    // ---------------------------------------------------------- Public Methods


    public String toString() {

        StringBuffer sb = new StringBuffer("ListEntriesRule[className=");
        sb.append(CLASS_NAME);
        sb.append("]");
        return (sb.toString());

    }


    // --------------------------------------------------------- Package Methods


    // Merge "top" into "old"
    static void mergeListEntries(ListEntriesBean top, ListEntriesBean old) {

        // Merge singleton properties
        if (top.getValueClass() != null) {
            old.setValueClass(top.getValueClass());
        }

        // Merge common collections

        // Merge unique collections
        String values[] = top.getValues();
        for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
                old.addNullValue();
            } else {
                old.addValue(values[i]);
            }
        }

    }


    // Merge "top" into "old"
    static void mergeListEntries(ListEntriesHolder top, ListEntriesHolder old) {

        ListEntriesBean lebt = top.getListEntries();
        if (lebt != null) {
            ListEntriesBean lebo = old.getListEntries();
            if (lebo != null) {
                mergeListEntries(lebt, lebo);
            } else {
                old.setListEntries(lebt);
            }
        }

    }

}
