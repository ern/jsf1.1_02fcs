/*
 * $Id: GraphTreeNodeTag.java,v 1.1.38.1 2006/04/12 19:31:08 ofung Exp $
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

// GraphTreeNodeTag.java

package components.taglib;

import components.model.Graph;
import components.model.Node;
import components.renderkit.Util;

import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentBodyTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <B>GraphMenuNodeTag</B> builds the graph as the nodes are processed.
 * This tag creates a node with specified properties. Locates the parent of
 * this node by using the node name from its immediate parent tag of the
 * type GraphTreeNodeTag. If the parent could not be located, then the created
 * node is assumed to be root.
 */

public class GraphTreeNodeTag extends UIComponentBodyTag {

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

    private String name = null;
    private String icon = null;
    private String label = null;
    private String action = null;
    private boolean expanded;
    private boolean enabled = true;
   
    // Relationship Instance Variables

    //
    // Constructors and Initializers    
    //

    public GraphTreeNodeTag() {
        super();
    }

    //
    // Class methods
    //

    //
    // General Methods
    //
    /**
     * Name of the node
     */
    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return this.name;
    }


    /**
     * Should the node appear expanded by default
     */
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }


    /**
     * Icon representing the node.
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }


    /**
     * Label for the node.
     */
    public void setLabel(String label) {
        this.label = label;
    }


    /**
     * Should the node be enabled by default
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


    /**
     * Link the node points to.
     */
    public void setAction(String action) {
        this.action = action;
    }


    public String getComponentType() {
        return null;
    }


    public String getRendererType() {
        return null;
    }


    //
    // Methods from FacesBodyTag
    //
    public int doStartTag() throws JspException {

        FacesContext context = FacesContext.getCurrentInstance();

        Graph graph = (Graph)
            ((Util.getValueBinding("#{sessionScope.graph_tree}").getValue(
                context)));
        // In the postback case, graph and the node exist already.So make sure
        // it doesn't created again.
        if (graph.findNodeByName(getName()) != null) {
            return BodyTag.EVAL_BODY_BUFFERED;
        }
        Node node = new Node(name, label, action, icon, enabled, expanded);
        
        // get the immediate ancestor/parent tag of this tag.
        GraphTreeNodeTag parentNode = null;
        try {
            parentNode = (GraphTreeNodeTag) TagSupport.findAncestorWithClass(
                this,
                GraphTreeNodeTag.class);
        } catch (Exception e) {
            System.out.println(
                "Exception while locating GraphTreeNodeTag.class");
        }    
        // if this tag has no parent that is a node tag,
        if (parentNode == null) {
            // then this should be root
            graph.setRoot(node);
        } else {
            // add the node to its parent node.
            Node nodeToAdd = graph.findNodeByName(parentNode.getName());
            // this node should exist
            if (nodeToAdd != null) {
                nodeToAdd.addChild(node);
            }
        }

        return BodyTag.EVAL_BODY_BUFFERED;
    }


    public int doEndTag() throws JspException {
        return (EVAL_PAGE);
    }

}
    

