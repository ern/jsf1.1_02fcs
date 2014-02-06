/*
 * $Id: ConverterTag.java,v 1.10.28.1 2006/04/12 19:30:27 ofung Exp $
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


import javax.faces.component.ValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.el.ValueBinding;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import javax.faces.application.ApplicationFactory;
import javax.faces.application.Application;
import javax.faces.FactoryFinder;



/**
 * <p><strong>ConverterTag</strong> is a base class for all JSP custom actions
 * that create and register a <code>Converter</code> instance on the
 * {@link ValueHolder} associated with our most immediate
 * surrounding instance of a tag whose implementation class is a subclass
 * of {@link UIComponentTag}.  To avoid creating duplicate instances when
 * a page is redisplayed, creation and registration of a {@link Converter}
 * occurs <strong>only</strong> if the corresponding {@link UIComponent} was
 * created (by the owning {@link UIComponentTag}) during the execution of the
 * current page.</p>
 *
 * <p>This class may be used directly to implement a generic converter
 * registration tag (based on the converter-id specified by the
 * <code>converterId</code> attribute), or as a base class for tag
 * instances that support specific {@link Converter} subclasses.  This
 * <code>converterId</code> attribute must refer to one of the well
 * known converter-ids, or a custom converter-id as defined in a
 * <code>faces-config.xml</code> file.</p>
 *
 * <p>Subclasses of this class must implement the
 * <code>createConverter()</code> method, which creates and returns a
 * {@link Converter} instance.  Any configuration properties that specify
 * behavior of this {@link Converter} must have been set by the
 * <code>createConverter()</code> method.  Generally, this occurs
 * by copying corresponding attribute values on the tag instance.</p>
 *
 * <p>This tag creates no output to the page currently being created.  It
 * is used solely for the side effect of {@link Converter} creation.</p>
 */

public class ConverterTag extends TagSupport {


    // -------------------------------------------------------------- Attributes

    /**
     * <p>The identifier of the {@link Converter} instance to be created.</p>
     */
    private String converterId = null;
    

    /**
     * <p>Set the identifer of the {@link Converter} instance to be created.
     *
     * @param converterId The identifier of the converter instance to be
     * created.
     */
    public void setConverterId(String converterId) {

        this.converterId = converterId;

    }


    // ---------------------------------------------------------- Public Methods


    /**
     * <p>Create a new instance of the specified {@link Converter}
     * class, and register it with the {@link UIComponent} instance associated
     * with our most immediately surrounding {@link UIComponentTag} instance, if
     * the {@link UIComponent} instance was created by this execution of the
     * containing JSP page.</p>
     *
     * @exception JspException if a JSP error occurs
     */
    public int doStartTag() throws JspException {

        // Locate our parent UIComponentTag
        UIComponentTag tag =
            UIComponentTag.getParentUIComponentTag(pageContext);
        if (tag == null) { // PENDING - i18n
            throw new JspException("Not nested in a UIComponentTag");
        }

        // Nothing to do unless this tag created a component
        if (!tag.getCreated()) {
            return (SKIP_BODY);
        }

        // Create and register an instance with the appropriate component
        Converter converter = createConverter();
        ValueHolder vh = ((ValueHolder) tag.getComponentInstance());
        vh.setConverter(converter);

        // Once the converter has been set, attempt to convert the
        // incoming "value"
        Object localValue = vh.getLocalValue();
        if (localValue instanceof String) {
            try {
                FacesContext context = FacesContext.getCurrentInstance();
                localValue = converter.getAsObject(context,
                                                   (UIComponent) vh,
                                                   (String) localValue);
                vh.setValue(localValue);
            }
            catch (ConverterException ce) {
                // PENDING - Ignore?  Throw an exception?  Set the local
                // value back to "null" and log a warning?
            }
        }

        return (SKIP_BODY);

    }


    /**
     * <p>Release references to any acquired resources.
     */
    public void release() {

        this.converterId = null;

    }


    // ------------------------------------------------------- Protected Methods


    /**
     * <p>Create and return a new {@link Converter} to be registered
     * on our surrounding {@link UIComponent}.</p>
     *
     * @exception JspException if a new instance cannot be created
     */
    protected Converter createConverter()
        throws JspException {

        try {
            FacesContext context = FacesContext.getCurrentInstance();
            String converterIdVal = converterId;
            if (UIComponentTag.isValueReference(converterId)) {
                ValueBinding vb =
                    context.getApplication().createValueBinding(converterId);
                converterIdVal = (String) vb.getValue(context);
            }
            return (context.getApplication().createConverter(converterIdVal));
        } catch (Exception e) {
            throw new JspException(e);
        }
    }


}
