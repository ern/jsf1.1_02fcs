/*
 * $Id: ChartItem.java,v 1.1.38.1 2006/04/12 19:31:03 ofung Exp $
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

package components.model;

/**
 * This class represents an individual graphable item for the chart conmponent.
 */
public class ChartItem {

    public ChartItem() {
       super();
    }
    
    public ChartItem(String label, int value, String color) {
        setLabel(label);
	setValue(value);
	setColor(color);
    }

    /**
     * <p>The label for this item.</p>
     */
    private String label = null;
    /**
     *<p>Return the label for this item.</p>
     */
    public String getLabel() {
        return label;
    }
    /**
     * <p>Set the label for this item.</p>
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * <p>The value for this item.</p>
     */
    private int value = 0;
    /**
     *<p>Return the value for this item.</p>
     */
    public int getValue() {
        return value;
    }
    /**
     * <p>Set the value for this item.</p>
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * <p>The color for this item.</p>
     */
    private String color = null;
    /**
     *<p>Return the color for this item.</p>
     */
    public String getColor() {
        return color;
    }
    /**
     * <p>Set the color for this item.</p>
     */
    public void setColor(String color) {
        this.color = color;
    }
}
