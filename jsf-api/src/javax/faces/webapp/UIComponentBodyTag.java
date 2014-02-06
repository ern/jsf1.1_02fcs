/*
 * $Id: UIComponentBodyTag.java,v 1.4.34.1 2006/04/12 19:30:28 ofung Exp $
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

package javax.faces.webapp;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.Tag;


/**
 * <p><strong>UIComponentBodyTag</strong> is a base class for all JSP custom
 * actions, related to a UIComponent, that need to process their tag bodies.
 * </p>
 */

public abstract class UIComponentBodyTag extends UIComponentTag
    implements BodyTag {


    // ----------------------------------------------------- Instance Variables


    /**
     * <p>The <code>bodyContent</code> for this tag handler.</p>
     */
    protected BodyContent bodyContent = null;


    // -------------------------------------------------------- BodyTag Methods


    /**
     * <p>Handle the ending of the nested body content for this tag.  The
     * default implementation simply calls <code>getDoAfterBodyValue()</code>
     * to retrieve the flag value to be returned.</p>
     *
     * <p>It should be noted that if this method returns
     * <code>IterationTag.EVAL_BODY_AGAIN</code>, any nested
     * {@link UIComponentTag} <em>must</em> have an explicit ID set.</p>
     *
     * @exception JspException if an error is encountered
     */
    public int doAfterBody() throws JspException {

        return (getDoAfterBodyValue());

    }


    /**
     * <p>Prepare for evaluation of the body.  This method is invoked by the
     * JSP page implementation object after <code>setBodyContent()</code>
     * and before the first time the body is to be evaluated.  This method
     * will not be invoked for empty tags or for non-empty tags whose
     * <code>doStartTag()</code> method returns <code>SKIP_BODY</code>
     * or <code>EVAL_BODY_INCLUDE</code>.</p>
     *
     * @exception JspException if an error is encountered
     */
    public void doInitBody() throws JspException {

        ; // Default implementation does nothing

    }

    public void release() {

        bodyContent = null;
        super.release();

    }


    /**
     * <p>Set the <code>bodyContent</code> for this tag handler.  This method
     * is invoked by the JSP page implementation object at most once per
     * action invocation, before <code>doInitiBody()</code>.  This method
     * will not be invoked for empty tags or for non-empty tags whose
     * <code>doStartTag()</code> method returns <code>SKIP_BODY</code> or
     * <code>EVAL_BODY_INCLUDE</code>.</p>
     *
     * @param bodyContent The new <code>BodyContent</code> for this tag
     */
    public void setBodyContent(BodyContent bodyContent) {

        this.bodyContent = bodyContent;

    }


    // --------------------------------------------------------- Public Methods


    /**
     * <p>Return the <code>BodyContent</code> for this tag handler.</p>
     */
    public BodyContent getBodyContent() {

        return (this.bodyContent);

    }


    /**
     * <p>Get the <code>JspWriter</code> from our <code>BodyContent</code>.
     * </p>
     */
    public JspWriter getPreviousOut() {

        return (this.bodyContent.getEnclosingWriter());

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * <p>Return the flag value that should be returned from the
     * <code>doAfterBody()</code> method when it is called.  Subclasses
     * may override this method to return the appropriate value.</p>
     */
    protected int getDoAfterBodyValue() throws JspException {

        return (SKIP_BODY);

    }


    protected int getDoStartValue() throws JspException {

        return (EVAL_BODY_BUFFERED);

    }


}
