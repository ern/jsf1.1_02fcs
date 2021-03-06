/*
 * $Id: DataModelListener.java,v 1.6.40.1 2006/04/12 19:30:24 ofung Exp $
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

package javax.faces.model;


import java.util.EventListener;


/**
 * <p><strong>DataModelListener</strong> represents an event listener that
 * wishes to be notified of {@link DataModelEvent}s occurring on a
 * particular {@link DataModel} instance.</p>
 */

public interface DataModelListener extends EventListener {


    /**
     * <p>Notification that a particular row index, with the associated
     * row data, has been selected for processing.</p>
     *
     * @param event The {@link DataModelEvent} we are processing
     */
    public void rowSelected(DataModelEvent event);


}
