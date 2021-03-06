/*
 * $Id: UIForm.java,v 1.45.34.1 2006/04/12 19:30:12 ofung Exp $
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


import java.util.Iterator;
import javax.faces.context.FacesContext;


/**
 * <p><strong>UIForm</strong> is a {@link UIComponent} that represents an
 * input form to be presented to the user, and whose child components represent
 * (among other things) the input fields to be included when the form is
 * submitted.</p>
 *
 * <p>By default, the <code>rendererType</code> property must be set to
 * "<code>javax.faces.Form</code>".  This value can be changed by calling the
 * <code>setRendererType()</code> method.</p>
 */

public class UIForm extends UIComponentBase implements NamingContainer {


    // ------------------------------------------------------ Manifest Constants


    /**
     * <p>The standard component type for this component.</p>
     */
    public static final String COMPONENT_TYPE = "javax.faces.Form";


    /**
     * <p>The standard component family for this component.</p>
     */
    public static final String COMPONENT_FAMILY = "javax.faces.Form";


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Create a new {@link UIForm} instance with default property
     * values.</p>
     */
    public UIForm() {

        super();
        setRendererType("javax.faces.Form");

    }


    // ------------------------------------------------------ Instance Variables


    // -------------------------------------------------------------- Properties


    public String getFamily() {

        return (COMPONENT_FAMILY);

    }


    /**
     * <p>The form submitted flag for this {@link UIForm}.</p>
     */
    private boolean submitted = false;


    /**
     * <p>Returns the current value of the <code>submitted</code>
     * property.  The default value is <code>false</code>.  See {@link
     * #setSubmitted} for details.</p>
     *
     */
    public boolean isSubmitted() {

        return (this.submitted);

    }


    /**
     * <p>If <strong>this<strong> <code>UIForm</code> instance (as
     * opposed to other forms in the page) is experiencing a submit
     * during this request processing lifecycle, this method must be
     * called, with <code>true</code> as the argument, during the {@link
     * UIComponent#decode} for this <code>UIForm</code> instance.  If
     * <strong>this</strong> <code>UIForm</code> instance is
     * <strong>not</strong> experiencing a submit, this method must be
     * called, with <code>false</code> as the argument, during the
     * {@link UIComponent#decode} for this <code>UIForm</code>
     * instance.</p>
     *
     * <p>The value of a <code>UIForm</code>'s submitted property must
     * not be saved as part of its state.</p>
     */
    public void setSubmitted(boolean submitted) {

        this.submitted = submitted;

    }


    // ----------------------------------------------------- UIComponent Methods


    /**
     * <p>Override {@link UIComponent#processDecodes} to ensure that the
     * form is decoded <strong>before</strong> its children.  This is
     * necessary to allow the <code>submitted</code> property to be
     * correctly set.</p>
     *
     * @exception NullPointerException {@inheritDoc}
     */
    public void processDecodes(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }
	
        // Process this component itself
        decode(context);

	// if we're not the submitted form, don't process children.
	if (!isSubmitted()) {
	    return;
	}

        // Process all facets and children of this component
        Iterator kids = getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            kid.processDecodes(context);
        }
	
    }


    /**
     * <p>Override {@link UIComponent#processValidators} to ensure that
     * the children of this <code>UIForm</code> instance are only
     * processed if {@link #isSubmitted} returns <code>true</code>.</p>
     * 
     * @exception NullPointerException {@inheritDoc}
     */
    public void processValidators(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }
	if (!isSubmitted()) {
	    return;
	}

        // Process all the facets and children of this component
        Iterator kids = getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            kid.processValidators(context);
        }

    }


    /**
     * <p>Override {@link UIComponent#processUpdates} to ensure that the
     * children of this <code>UIForm</code> instance are only processed
     * if {@link #isSubmitted} returns <code>true</code>.</p>
     * 
     * @exception NullPointerException {@inheritDoc}
     */
    public void processUpdates(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }
	if (!isSubmitted()) {
	    return;
	}

        // Process all facets and children of this component
        Iterator kids = getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            kid.processUpdates(context);
        }

    }

}
