/*
 * $Id: MessageFactory.java,v 1.7.28.1 2006/04/12 19:33:29 ofung Exp $
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

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.Locale;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.FacesException;
import java.text.MessageFormat;
import java.io.IOException;

/**
 * 
 * <p>supported filters: <code>package</code> and
 * <code>protection</code>.</p>
 */

public class MessageFactory extends Object
{
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

    private MessageFactory() {
    }

    //
    // Class methods
    //

    //
    // General Methods
    //
    
    public static String substituteParams(Locale locale, String msgtext, Object params[]) {
        String localizedStr = null;
        
        if (params == null || msgtext == null ) {
            return msgtext;
        }    
        StringBuffer b = new StringBuffer(100);
        MessageFormat mf = new MessageFormat(msgtext);
        if (locale != null) {
            mf.setLocale(locale);
            b.append(mf.format(params));
            localizedStr = b.toString();
        }    
        return localizedStr;
    }

    /**

    * This version of getMessage() is used in the RI for localizing RI
    * specific messages.

    */

    public static FacesMessage getMessage(String messageId, Object params[]) {
        Locale locale = null;
        FacesContext context = FacesContext.getCurrentInstance();
        // context.getViewRoot() may not have been initialized at this point.
        if (context != null && context.getViewRoot() != null) {
            locale = context.getViewRoot().getLocale();
            if (locale == null) {
                locale = Locale.getDefault();
            }
        } else {
            locale = Locale.getDefault();
        }
        
	return getMessage(locale, messageId, params);
    }

    public static FacesMessage getMessage(Locale locale, String messageId, 
					   Object params[]) {
	FacesMessage result = null;
	String 
	    summary = null,
	    detail = null,
	    bundleName = null;
	ResourceBundle bundle = null;

	// see if we have a user-provided bundle
	if (null != (bundleName = getApplication().getMessageBundle())) {
	    if (null != 
		(bundle = 
		 ResourceBundle.getBundle(bundleName, locale,
					  getCurrentLoader(bundleName)))) {
		// see if we have a hit
		try {
		    summary = bundle.getString(messageId);
		}
		catch (MissingResourceException e) {
		}
	    }
	}
	
	// we couldn't find a summary in the user-provided bundle
	if (null == summary) {
	    // see if we have a summary in the app provided bundle
	    bundle = ResourceBundle.getBundle(FacesMessage.FACES_MESSAGES, 
					      locale,
					      getCurrentLoader(bundleName));
	    if (null == bundle) {
		throw new NullPointerException();
	    }
	    // see if we have a hit
	    try {
		summary = bundle.getString(messageId);
	    }
	    catch (MissingResourceException e) {
	    }
	}
	
	// we couldn't find a summary anywhere!  Return null
	if (null == summary) {
	    return null;
	}

	// At this point, we have a summary and a bundle.
	if (null == summary || null == bundle) {
            throw new NullPointerException(" summary " + summary + " bundle " + 
                bundle);
	}
	summary = substituteParams(locale, summary, params);

	try {
	    detail = substituteParams(locale,
				      bundle.getString(messageId + "_detail"), 
				      params);
	}
	catch (MissingResourceException e) {
	}

        return (new FacesMessage(summary, detail));
    }


    //
    // Methods from MessageFactory
    // 
    public static FacesMessage getMessage(FacesContext context, String messageId) {
        return getMessage(context, messageId, null);
    }    
    
    public static FacesMessage getMessage(FacesContext context, String messageId,
					   Object params[]) {
        if (context == null || messageId == null ) {
            throw new NullPointerException(" context " + context + " messageId " + 
                messageId);
        }
        Locale locale = null;
        // viewRoot may not have been initialized at this point.
        if (context != null && context.getViewRoot() != null) {
            locale = context.getViewRoot().getLocale();
        } else {
            locale = Locale.getDefault();
        }
	if (null == locale) {
	    throw new NullPointerException(" locale " + locale);
	}
        FacesMessage message = getMessage(locale, messageId, params);
        if (message != null) {
            return message;
        }
        locale = Locale.getDefault();
        return (getMessage(locale, messageId, params));
    }  
    
    public static FacesMessage getMessage(FacesContext context, String messageId,
                                       Object param0) {
        return getMessage(context, messageId, new Object[]{param0});                                       
    }                                       
    
    public static FacesMessage getMessage(FacesContext context, String messageId,
                                       Object param0, Object param1) {
         return getMessage(context, messageId, new Object[]{param0, param1});                                        
    }                                       

    public static FacesMessage getMessage(FacesContext context, String messageId,
                                       Object param0, Object param1,
                                       Object param2) {
         return getMessage(context, messageId, 
             new Object[]{param0, param1, param2});                                        
    }                                       

    public static FacesMessage getMessage(FacesContext context, String messageId,
                                       Object param0, Object param1,
                                       Object param2, Object param3) {
         return getMessage(context, messageId, 
                 new Object[]{param0, param1, param2, param3});                                        
    }                                       

    protected static Application getApplication() {
	FacesContext context = FacesContext.getCurrentInstance();
	if (context != null) {
	    return (FacesContext.getCurrentInstance().getApplication());
	}
	ApplicationFactory afactory = (ApplicationFactory)
	    FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
	return (afactory.getApplication());
    }

    protected static ClassLoader getCurrentLoader(Object fallbackClass) {
        ClassLoader loader =
	    Thread.currentThread().getContextClassLoader();
	if (loader == null) {
	    loader = fallbackClass.getClass().getClassLoader();
	}
	return loader;
    }


} // end of class MessageFactory
