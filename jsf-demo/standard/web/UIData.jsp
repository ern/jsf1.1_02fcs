<%--
 The contents of this file are subject to the terms
 of the Common Development and Distribution License
 (the License). You may not use this file except in
 compliance with the License.
 
 You can obtain a copy of the License at
 https://javaserverfaces.dev.java.net/CDDL.html or
 legal/CDDLv1.0.txt. 
 See the License for the specific language governing
 permission and limitations under the License.
 
 When distributing Covered Code, include this CDDL
 Header Notice in each file and include the License file
 at legal/CDDLv1.0.txt.    
 If applicable, add the following below the CDDL Header,
 with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"
 
 [Name of File] [ver.__] [Date]
 
 Copyright 2006 Sun Microsystems Inc. All Rights Reserved
--%>

<!--
 Copyright 2004 Sun Microsystems, Inc. All Rights Reserved.
 
 Redistribution and use in source and binary forms, with or
 without modification, are permitted provided that the following
 conditions are met:
 
 - Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.
 
 - Redistribution in binary form must reproduce the above
   copyright notice, this list of conditions and the following
   disclaimer in the documentation and/or other materials
   provided with the distribution.
    
 Neither the name of Sun Microsystems, Inc. or the names of
 contributors may be used to endorse or promote products derived
 from this software without specific prior written permission.
  
 This software is provided "AS IS," without a warranty of any
 kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
  
 You acknowledge that this software is not designed, licensed or
 intended for use in the design, construction, operation or
 maintenance of any nuclear facility.
-->

<%-- $Id: UIData.jsp,v 1.18.40.1 2006/04/12 19:31:45 ofung Exp $ --%>

<%@ page import="standard.CustomerBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt"  prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>

<%
  // Construct a preconfigured customer list in session scope
  List list = (List)
    pageContext.getAttribute("list", PageContext.SESSION_SCOPE);
  if (list == null) {
    list = new ArrayList();
    list.add(new CustomerBean("123456", "Alpha Beta Company", "ABC", 1234.56));
    list.add(new CustomerBean("445566", "General Services, Ltd.", "GS", 33.33));
    list.add(new CustomerBean("654321", "Summa Cum Laude, Inc.", "SCL", 76543.21));
    list.add(new CustomerBean("333333", "Yabba Dabba Doo", "YDD",  333.33));
    for (int i = 10; i < 20; i++) {
      list.add(new CustomerBean("8888" + i,
                                "Customer " + i,
                                "CU" + i,
                                ((double) i) * 10.0));
    }
    pageContext.setAttribute("list", list,
                             PageContext.SESSION_SCOPE);
  }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<f:view>
<html>
<head>
  <title>UIData</title>
  <link rel="stylesheet" type="text/css"
       href='<%= request.getContextPath() + "/stylesheet.css" %>'>
</head>
<body>

  <h1>UIData</h1>

  <c:if test="${not empty message}">
    The following events and <code>UIDataBean</code> activities were performed:
    <ul><c:out value="${message}" escapeXml="false"/></ul>
    <hr>
  </c:if>

  <h:form id="standardRenderKitForm">

  <h:dataTable columnClasses="list-column-center,
                               list-column-center, list-column-left,
                               list-column-center, list-column-right,
                               list-column-center"
                      binding="#{UIDataBean.data}"
                  footerClass="list-footer"
                  headerClass="list-header"
                           id="table"
	                 rows="5"
                   rowClasses="list-row-even, list-row-odd"
                   styleClass="list-background"
                        value="#{list}"
                          var="customer">

    <f:facet             name="header">
      <h:outputText    value="Overall Table Header"/>
    </f:facet>

    <f:facet             name="footer">
      <h:outputText    value="Overall Table Footer"/>
    </f:facet>

    <h:column>
      <%-- Visible checkbox for selection --%>
      <h:selectBooleanCheckbox
                           id="checked"
                      binding="#{UIDataBean.checked}"/>
      <%-- Invisible checkbox for "created" flag --%>
      <h:selectBooleanCheckbox
                           id="created"
                      binding="#{UIDataBean.created}"
                     rendered="false"/>
    </h:column>

    <h:column>
      <f:facet           name="header">
        <h:outputText  value="Account Id"/>
      </f:facet>
      <f:facet           name="footer">
        <h:outputText  value="A.I. Footer"/>
      </f:facet>
      <h:inputText        id="accountId"
                      binding="#{UIDataBean.accountId}"
                     required="true"
                         size="6"
                        value="#{customer.accountId}">
        <f:valueChangeListener
                         type="standard.LogValueChangedListener"/>
      </h:inputText>
      <h:message          for="accountId"/>
    </h:column>

    <h:column>
      <f:facet           name="header">
        <h:outputText  value="Customer Name"/>
      </f:facet>
      <f:facet           name="footer">
        <h:outputText  value="C.N. Footer"/>
      </f:facet>
      <h:inputText        id="name"
                     required="true"
                         size="50"
                        value="#{customer.name}">
        <f:valueChangeListener
                         type="standard.LogValueChangedListener"/>
      </h:inputText>
      <h:message          for="name"/>
    </h:column>

    <h:column>
      <f:facet           name="header">
        <h:outputText  value="Symbol"/>
      </f:facet>
      <f:facet           name="footer">
        <h:outputText  value="S. Footer"/>
      </f:facet>
      <h:inputText        id="symbol"
                     required="true"
                         size="6"
                        value="#{customer.symbol}">
        <f:validateLength
                      maximum="6"
                      minimum="2"/>
        <f:valueChangeListener
                         type="standard.LogValueChangedListener"/>
      </h:inputText>
      <h:message          for="symbol"/>
    </h:column>

    <h:column>
      <f:facet           name="header">
        <h:outputText  value="Total Sales"/>
      </f:facet>
      <f:facet           name="footer">
        <h:outputText  value="T.S. Footer"/>
      </f:facet>
      <h:outputText       id="totalSales"
                        value="#{customer.totalSales}">
        <f:convertNumber type="currency"/>
      </h:outputText>
    </h:column>

    <h:column>
      <f:facet           name="header">
        <h:commandButton  id="headerButton"
                       action="#{UIDataBean.header}"
                    immediate="true"
                        value="Header"
                         type="SUBMIT"/>
      </f:facet>
      <f:facet           name="footer">
        <h:commandButton  id="footerButton"
                       action="#{UIDataBean.footer}"
                    immediate="true"
                        value="Footer"
                         type="SUBMIT"/>
      </f:facet>
      <h:commandButton    id="press"
                    action="#{UIDataBean.press}"
                    immediate="true"
                        value="#{UIDataBean.pressLabel}"
                         type="SUBMIT"/>
      <h:commandLink id="click"
                    action="#{UIDataBean.click}"
                    immediate="true">
        <h:outputText
                        value="#{UIDataBean.clickLabel}"/>
      </h:commandLink>
    </h:column>

  </h:dataTable>

  <h:commandButton        id="create1"
                    action="#{UIDataBean.create}"
                    immediate="true"
                        value="Create New Row (immediate=true)"
                         type="SUBMIT"/>

  <h:commandButton        id="create2"
                    action="#{UIDataBean.create}"
                    immediate="false"
                        value="Create New Row (immediate=false)"
                         type="SUBMIT"/>

  <h:commandButton        id="delete1"
                    action="#{UIDataBean.deleteImmediate}"
                    immediate="true"
                        value="Delete Checked (immediate=true)"
                         type="SUBMIT"/>

  <h:commandButton        id="delete2"
                    action="#{UIDataBean.deleteDeferred}"
                    immediate="false"
                        value="Delete Checked (immediate=false)"
                         type="SUBMIT"/>

  <h:commandButton        id="first"
                    action="#{UIDataBean.first}"
                    immediate="true"
                        value="First Page"
                         type="SUBMIT"/>

  <h:commandButton        id="last"
                    action="#{UIDataBean.last}"
                    immediate="true"
                        value="Last Page"
                         type="SUBMIT"/>

  <h:commandButton        id="next"
                    action="#{UIDataBean.next}"
                    immediate="true"
                        value="Next Page"
                         type="SUBMIT"/>

  <h:commandButton        id="previous"
                    action="#{UIDataBean.previous}"
                    immediate="true"
                        value="Prev Page"
                         type="SUBMIT"/>

  <h:commandButton        id="reset"
                    action="#{UIDataBean.reset}"
                    immediate="true"
                        value="Reset Changes"
                         type="SUBMIT"/>

  <h:commandButton        id="update"
                    action="#{UIDataBean.update}"
                    immediate="false"
                        value="Save Changes"
                         type="SUBMIT"/>

  </h:form>

  <hr>
  <p><a href='<%= request.getContextPath() + "/" %>'>Back</a>
  to home page.</p>

</body>
</html>
</f:view>
