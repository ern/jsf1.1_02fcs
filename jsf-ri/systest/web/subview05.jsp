<!--
 Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->
<%@ page contentType="text/html" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<f:view>
<html>
<head>
<title>subview05</title>
</head>
<body>
<h:outputText value="Begin test jsp:include with subview and iterator tag in included page"/>
<br>
<jsp:include page="subviewIterator01.jsp"/>
<h:outputText value="Text from subview05.jsp"/>
<h:outputText value="End test jsp:include with subview and iterator tag in included page"/>
</body>
</html>
</f:view>
