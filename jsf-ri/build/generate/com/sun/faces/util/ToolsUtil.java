/*
 * $Id: ToolsUtil.java,v 1.4.4.2.2.1 2006/04/12 19:33:28 ofung Exp $
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

import java.util.ResourceBundle;
import java.util.Locale;
import java.text.MessageFormat;

/**
 * Message constants and static utilities.
 */
public class ToolsUtil {

    private static final String RESOURCE_BUNDLE_BASE_NAME =
        "com.sun.faces.resources.JsfToolsMessages";


    // --------------------------------------------------- Message Key Constants


    public static final String MANAGED_BEAN_NO_MANAGED_BEAN_NAME_ID =
        "com.sun.faces.MANAGED_BEAN_NO_MANAGED_BEAN_NAME";
    
    public static final String MANAGED_BEAN_NO_MANAGED_BEAN_CLASS_ID =
        "com.sun.faces.MANAGED_BEAN_NO_MANAGED_BEAN_CLASS";
    
    public static final String MANAGED_BEAN_NO_MANAGED_BEAN_SCOPE_ID =
        "com.sun.faces.MANAGED_BEAN_NO_MANAGED_BEAN_SCOPE";
    
    public static final String MANAGED_BEAN_INVALID_SCOPE_ID =
        "com.sun.faces.MANAGED_BEAN_INVALID_SCOPE";
    
    public static final String MANAGED_BEAN_AS_LIST_CONFIG_ERROR_ID =
        "com.sun.faces.MANAGED_BEAN_AS_LIST_CONFIG_ERROR";
    
    public static final String MANAGED_BEAN_AS_MAP_CONFIG_ERROR_ID = 
        "com.sun.faces.MANAGED_BEAN_AS_MAP_CONFIG_ERROR";
    
    public static final String MANAGED_BEAN_LIST_PROPERTY_CONFIG_ERROR_ID =
        "com.sun.faces.MANAGED_BEAN_LIST_PROPERTY_CONFIG_ERROR";
    
    public static final String MANAGED_BEAN_MAP_PROPERTY_CONFIG_ERROR_ID =
        "com.sun.faces.MANAGED_BEAN_MAP_PROPERTY_CONFIG_ERROR";
    
    public static final String MANAGED_BEAN_PROPERTY_CONFIG_ERROR_ID = 
        "com.sun.faces.MANAGED_BEAN_PROPERTY_CONFIG_ERROR";
    
    public static final String MANAGED_BEAN_NO_MANAGED_PROPERTY_NAME_ID =
        "com.sun.faces.MANAGED_BEAN_NO_MANAGED_PROPERTY_NAME";


    // ---------------------------------------------------------- Public Methods


    public static String getMessage(String messageKey, Object[] params) {

        ResourceBundle bundle =
            ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME,
                Locale.getDefault(),
                Thread.currentThread().getContextClassLoader());
        return MessageFormat.format(bundle.getString(messageKey), params);

    } // END getMessage


    public static String getMessage(String messageKey) {

        return getMessage(messageKey, null);

    } // END getMessage

}
