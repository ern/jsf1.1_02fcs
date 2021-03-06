<!--
 Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>
<%@ taglib prefix="s" uri="/WEB-INF/taglib.tld" %>

<f:view>
<html>
<head>
<title>jstl-if-02</title>
</head>
<body>
<h:outputText value="[First]"/>
<c:if test="${param.component}">
  <s:facets id="comp" value="Second">
    <c:if test="${param.header}">
      <f:facet name="header">
        <h:outputText id="head" value="Header"/>
      </f:facet>
    </c:if>
    <c:if test="${param.footer}">
      <f:facet name="footer">
        <h:outputText id="foot" value="Footer"/>
      </f:facet>
    </c:if>
  </s:facets>
</c:if>
<h:outputText value="[Third]"/>
</body>
</html>
</f:view>
