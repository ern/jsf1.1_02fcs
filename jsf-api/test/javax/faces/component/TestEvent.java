/*
 * $Id: TestEvent.java,v 1.4.40.1 2006/04/12 19:30:31 ofung Exp $
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

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

public class TestEvent extends FacesEvent {

    public TestEvent(UIComponent component) {
        this(component, null);
    }

    public TestEvent(UIComponent component, String id) {
        super(component);
        this.id = id;
    }

    private String id;

    public String getId() {
        return (this.id);
    }

    public  boolean isAppropriateListener(FacesListener listener) {
        return (listener instanceof TestListener);
    }

    public void processListener(FacesListener listener) {
        ((TestListener) listener).processTest(this);
    }


}
