<!--
 Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>selectBoolean test</title>
    <%@ taglib uri="http://java.sun.com/jsf/core"  prefix="f" %>
    <%@ taglib uri="http://java.sun.com/jsf/html"  prefix="h" %>
  </head>

  <body>
    <h1>selectBoolean test</h1>

<h2>How this testcase works.</h2>

<p>This is the regression test for bugtraq 5016123.</p>

<p>The system test for this page does the following</p>


	<ol>

	  <li><p>presses the button with the id "replace" twice.</p>

          <p>The first time pressed, it replaces the default
          PropertyResolver with one that logs calls to setValue in the
          "valueChanged" property of bean named test3.  The second time
          pressed, you'll actually see that the setValue was called.</p>

          </li>

	  <li><p>When the page loads from the second button press, it
	  looks for the string "setValue() called" and verifies it is
	  not in the page. </p></li>

	  <li><p>presses the button with the id "restore".
	  </p></li>

	</ol>


<f:view>

  <h:form>

    <h:commandButton id="replace" value="submit and replace PropertyResolver with Logging PropertyResolver" 
                     actionListener="#{test3.replacePropertyResolver}" />

    <h:panelGrid columns="2">

      <h:selectBooleanCheckbox value="#{test3.booleanProperty2}" />

      <h:outputText value="checkbox" />

      <h:outputText value="valueChanged:" />

      <h:outputText value="#{test3.valueChangeMessage}" />

      <h:messages />

    </h:panelGrid>

    <h:commandButton id="restore" 
                     value="submit and restore original PropertyResolver" 
                     actionListener="#{test3.restorePropertyResolver}" />


  </h:form>

</f:view>

    <hr>
  </body>
</html>
