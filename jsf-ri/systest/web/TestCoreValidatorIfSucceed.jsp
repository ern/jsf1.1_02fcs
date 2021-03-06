<!--
 Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<html>
<title>Validator Test Page</title>
<head>
    <%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
    <%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>

<h1>TLV c:if with JSF id</h1>
This page should succeed.
<br>
<br>

<% request.setAttribute("one", new String("one")); %>

<f:view>

  <c:if test="${one == 'one'}">
    <h:outputText id="has_id" value="HAS ID"/>
  </c:if>

</f:view>

</body>
</head>
</html>
