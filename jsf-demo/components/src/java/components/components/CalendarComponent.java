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

package components.components;


import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * <p>CalendarComponent is a JavaServer Faces component that accepts date
 * input either via a text box or a JavaScript enabled graphical representation
 * of a month-view calendar.</p>
 */

public class CalendarComponent extends UIComponentBase {


    /**
     * <p>Return the component family for this component.</p>
     */
    public String getFamily() {

        return ("Calendar");

    }


    public String toString() {
        StringBuffer sb = new StringBuffer();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        sb.append("CalendarComponent ");
        sb.append("(").append(getId()).append(")\n");
        //sb.append("(").append(getClientId(facesContext)).append(")\n");
        Map map = getAttributes();
        Set attrSet = map.keySet();
        Iterator iter = attrSet.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            Object value = map.get(key);
            sb.append(key).append(": ").append(value).append("\n");
        }
        return sb.toString();
    }
}
