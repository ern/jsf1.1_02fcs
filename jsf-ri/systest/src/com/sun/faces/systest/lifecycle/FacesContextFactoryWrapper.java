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

package com.sun.faces.systest.lifecycle;

import javax.faces.context.FacesContextFactory;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.FacesException;

public class FacesContextFactoryWrapper extends FacesContextFactory {
    
    private FacesContextFactory oldFactory = null;
    
    public FacesContextFactoryWrapper(FacesContextFactory yourOldFactory) {
	oldFactory = yourOldFactory;
    }
    
    public FacesContext getFacesContext(Object context, Object request,
					Object response, 
					Lifecycle lifecycle) throws FacesException {
	return oldFactory.getFacesContext(context, request, response, 
					  lifecycle);
    }

    public String toString() {
	return "FacesContextFactoryWrapper";
    }

}
