<!--
 Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->
<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<f:view>
<html>
<head>
<title>subview04</title>
</head>
<body>
<h:outputText value="[A]"/>
<f:subview id="foo02">
<jsp:include page="Begin test <c:include> with subview tag in including page"/>
</f:subview>
<h:outputText value="subview04"/>
<f:subview id="bar02">
<jsp:include page="bar02.jsp"/>
</f:subview>
<h:outputText value="End test <c:include> with subview tag in including page"/>
</body>
</html>
</f:view>
