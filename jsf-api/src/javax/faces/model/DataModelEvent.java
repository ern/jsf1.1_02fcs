/*
 * $Id: DataModelEvent.java,v 1.10.40.1 2006/04/12 19:30:23 ofung Exp $
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


import java.util.EventObject;


/**
 * <p><strong>DataModelEvent</strong> represents an event of interest to
 * registered listeners that occurred on the specified {@link DataModel}.</p>
 */

public class DataModelEvent extends EventObject {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Construct an event object that is associated with the specified
     * row index and associated data.</p>
     *
     * @param model The {@link DataModel} on which this event occurred
     * @param index The zero relative row index for which this event occurred,
     *   or -1 for no specific row association
     * @param data Representation of the data for the row specified by
     *  <code>index</code>, or <code>null</code> for no particular row data
     */
    public DataModelEvent(DataModel model, int index, Object data) {

	super(model);
	this.index = index;
	this.data = data;

    }


    // ------------------------------------------------------ Instance Variables


    private Object data = null;


    private int index = 0;


    // -------------------------------------------------------------- Properties


    /**
     * <p>Return the {@link DataModel} that fired this event.</p>
     */
    public DataModel getDataModel() {

	return ((DataModel) getSource());

    }


    /**
     * <p>Return the object representing the data for the specified row index,
     * or <code>null</code> for no associated row data.</p>
     */
    public Object getRowData() {

	return (this.data);

    }


    /**
     * <p>Return the row index for this event, or -1 for no specific row.</p>
     */
    public int getRowIndex() {

	return (this.index);

    }



}
