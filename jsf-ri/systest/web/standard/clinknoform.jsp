<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>clinknoform</title>
    <%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
    <%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
  </head>
  <body>
      <f:view>
          <p id="link1">
              <h:commandLink id="clink1" value="Link1"/>
          </p>
          <p id="link2">
              <h:commandLink id="clink2">
                  <h:outputText value="Link2"/>
              </h:commandLink>
          </p>
          <p id="link3">
              <h:commandLink id="clink3" value="Click me once">
                <h:outputText value=" and click me twice"/>                  
              </h:commandLink>
          </p>
      </f:view>
  </body>
</html>