/*
 * $Id: DataModelBean.java,v 1.4.40.1 2006/04/12 19:31:42 ofung Exp $
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

package standard;


import javax.faces.component.UISelectOne;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Backing file class for <code>DataModel.jsp</code>.</p>
 */

public class DataModelBean {


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Initialize the select component's value so that the first customer
     * is marked as selected the very first time we execute.</p>
     */
    public DataModelBean() {

        // System.err.println("DataModelBean:  precreating select component");
        accountId = new UISelectOne();
        accountId.setId("accountId");
        accountId.setValue("0");

    }


    // -------------------------------------------------------- Bound Components


    /**
     * <p>The <code>accountId</code> field for the current row.</p>
     */
    private UISelectOne accountId = null;


    public UISelectOne getAccountId() {
        // System.err.println("getAccountId(): returning " + accountId);
        return accountId;
    }


    public void setAccountId(UISelectOne accountId) {
        // System.err.println("setAccountId(): setting " + accountId);
        this.accountId = accountId;
    }


    // -------------------------------------------------------------- Properties


    private List accountIds = null;


    /**
     * <p>Return an array of <code>SelectItem</code>s representing the
     * account identifiers of all our customers.</p>
     */
    public List getAccountIds() {
        if (accountIds == null) {
            // System.err.println("getAccountIds(): creating list");
            getCustomers();
            accountIds = new ArrayList();
            int n = list.size();
            for (int i = 0; i < n; i++) {
                accountIds.add(new SelectItem
                    ("" + i,
                     ((CustomerBean) list.get(i)).getAccountId(),
                     null));
            }
        }
        // System.err.println("getAccountIds(): returning list");
        return (accountIds);
    }


    private DataModel customers = null;
    private List list = null;


    /**
     * <p>Return a <code>DataModel</code> containing our customer list.</p>
     */
    public DataModel getCustomers() {
        if (list == null) {
            // System.err.println("getCustomers(): creating list");
            list = new ArrayList();
            list.add(new CustomerBean
                ("123456", "Alpha Beta Company", "ABC", 1234.56));
            list.add(new CustomerBean
                ("445566", "General Services, Ltd.", "GS", 33.33));
            list.add(new CustomerBean
                ("654321", "Summa Cum Laude, Inc.", "SCL", 76543.21));
            list.add(new CustomerBean
                ("333333", "Yabba Dabba Doo", "YDD", 333.33));
            for (int i = 10; i < 20; i++) {
                list.add(new CustomerBean("8888" + i,
                                          "Customer " + i,
                                          "CU" + i,
                                          ((double) i) * 10.0));
            }
        }
        if (customers == null) {
            // System.err.println("getCustomers(): creating DataModel");
            customers = new ListDataModel(list);
        }
        // System.err.println("getCustomers(): returning DataModel");
        return (customers);
    }


    // --------------------------------------------------------- Action Handlers


    /**
     * <p>Select the customer whose account id was specified.</p>
     */
    public String select() {

        String value = (String) getAccountId().getValue();
        // System.err.println("select(" + value + ")");
        int rowIndex = Integer.parseInt(value);
        // System.err.println("setting rowIndex to " + rowIndex);
        getCustomers().setRowIndex(rowIndex);
        return (null); // Stay on the same page

    }


    // --------------------------------------------------------- Private Methods


}
