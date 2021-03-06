/*
 * $Id: TreeStructure.java,v 1.7.32.1 2006/04/12 19:32:33 ofung Exp $
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

import javax.faces.FacesException;
import javax.faces.component.UIComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

/**
 * TreeStructure is a class that represents the structure of a UIComponent
 * instance. This class plays a key role in saving and restoring the structure
 * of the component tree.
 */
public class TreeStructure implements java.io.Serializable {

    ArrayList children = null;
    HashMap facets = null;
    String className = null;
    String id = null;


    public TreeStructure() {
    }


    public TreeStructure(UIComponent component) {
        Util.parameterNonNull(component);
        this.id = component.getId();
        className = component.getClass().getName();
    }


    /**
     * Returns the className of the UIComponent that this TreeStructure
     * represents.
     */
    public String getClazzName() {
        return className;
    }


    /**
     * Returns the iterator over className of the children that are attached to
     * the UIComponent that this TreeStructure represents.
     */
    public Iterator getChildren() {
        if (children != null) {
            return (children.iterator());
        } else {
            return (Collections.EMPTY_LIST.iterator());
        }
    }


    /**
     * Returns the iterator over className of the facets that are attached to
     * the UIComponent that this TreeStructure represents.
     */
    public Iterator getFacetNames() {
        if (facets != null) {
            return (facets.keySet().iterator());
        } else {
            return (Collections.EMPTY_LIST.iterator());
        }
    }


    /**
     * Adds treeStruct as a child of this TreeStructure instance.
     */
    public void addChild(TreeStructure treeStruct) {
        Util.parameterNonNull(treeStruct);
        if (children == null) {
            children = new ArrayList();
        }
        children.add(treeStruct);
    }


    /**
     * Adds treeStruct as a facet belonging to this TreeStructure instance.
     */
    public void addFacet(String facetName, TreeStructure treeStruct) {
        Util.parameterNonNull(facetName);
        Util.parameterNonNull(treeStruct);
        if (facets == null) {
            facets = new HashMap();
        }
        facets.put(facetName, treeStruct);
    }


    /**
     * Returns a TreeStructure representing a facetName by looking up
     * the facet list
     */
    public TreeStructure getTreeStructureForFacet(String facetName) {
        Util.parameterNonNull(facetName);
        if (facets != null) {
            return ((TreeStructure) (facets.get(facetName)));
        } else {
            return null;
        }
    }


    /**
     * Creates and returns the UIComponent that this TreeStructure
     * represents using the structure information available.
     */
    public UIComponent createComponent() {
        UIComponent component = null;
        // create the UIComponent based on the className stored.
        try {
            Class clazz = Util.loadClass(className, this);
            component = ((UIComponent) clazz.newInstance());
        } catch (Exception e) {
            Object params[] = {className};
            throw new FacesException(Util.getExceptionMessageString(
                Util.MISSING_CLASS_ERROR_MESSAGE_ID,
                params));
        }
        Util.doAssert(component != null);
        component.setId(id);
        return component;
    }
}
