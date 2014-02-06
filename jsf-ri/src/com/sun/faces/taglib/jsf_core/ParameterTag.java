/*
 * $Id: ParameterTag.java,v 1.15.40.1 2006/04/12 19:32:30 ofung Exp $
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

// ParameterTag.java

package com.sun.faces.taglib.jsf_core;

import com.sun.faces.taglib.BaseComponentTag;
import com.sun.faces.util.Util;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;

public class ParameterTag extends BaseComponentTag {

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

    public ParameterTag() {
        super();
    }

//
// Class methods
//

//
// Accessors
//

//
// General Methods
//

    public String getRendererType() {
        return null;
    }


    public String getComponentType() {
        return "javax.faces.Parameter";
    }


    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        UIParameter parameter = (UIParameter) component;

        if (name != null) {
            if (isValueReference(name)) {
                component.setValueBinding("name",
                                          Util.getValueBinding(name));
            } else {
                parameter.setName(name);
            }
        }
        // if component has non null value, do not call setValue().
        if (value != null) {
            if (isValueReference(value)) {
                component.setValueBinding("value",
                                          Util.getValueBinding(value));
            } else {
                parameter.setValue(value);
            }
        }
    }
}
