/*
 * $Id: ValidateRequiredTag.java,v 1.11.40.1 2006/04/12 19:32:31 ofung Exp $
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

// ValidateRequiredTag.java

package com.sun.faces.taglib.jsf_core;

import javax.faces.webapp.ValidatorTag;
import javax.servlet.jsp.JspException;


/**
 * <B>ValidateRequiredTag</B> is a class ...
 * <p/>
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: ValidateRequiredTag.java,v 1.11.40.1 2006/04/12 19:32:31 ofung Exp $
 */

public class ValidateRequiredTag extends ValidatorTag {

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

    public ValidateRequiredTag() {
        super();
    }

//
// Class methods
//

//
// General Methods
//

    public int doStartTag() throws JspException {
        super.setValidatorId("javax.faces.Required");
	return super.doStartTag();
    }


// 
// Methods from ValidatorTag
// 

} // end of class ValidateRequiredTag
