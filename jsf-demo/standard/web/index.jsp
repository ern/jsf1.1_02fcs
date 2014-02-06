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

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- $Id: index.jsp,v 1.4.36.1 2006/04/12 19:31:47 ofung Exp $ -->
<html>
  <head>
    <title>JavaServer Faces 1.0 Standard RenderKit Demo</title>
  </head>

  <body>

<%
  pageContext.removeAttribute("list");
%>

    <h1>JavaServer Faces 1.0 Standard RenderKit Demo</h1>

	<ul>

	  <li><p><a href="faces/UICommand.jsp">UICommand</a>
	  </p></li>

	  <li><p><a href="faces/UIData.jsp">UIData</a>
	  </p></li>

	  <li><p><a href="faces/UIGraphic.jsp">UIGraphic</a>
	  </p></li>

	  <li><p><a href="faces/UIInput.jsp">UIInput</a>
	  </p></li>

	  <li><p><a href="faces/UIOutput.jsp">UIOutput</a>
          </p></li>

	  <li><p><a href="faces/UIPanel.jsp">UIPanel</a>
	  </p></li>

	  <li><p><a href="faces/UISelectBoolean.jsp">UISelectBoolean</a>
	  </p></li>

	  <li><p><a href="faces/UISelectMany.jsp">UISelectMany</a>
	  </p></li>

	  <li><p><a href="faces/UISelectOne.jsp">UISelectOne</a>
	  </p></li>

	  <li><p><a href="faces/DataModel.jsp">DataModel</a>
	  </p></li>

	</ul>

  </body>
</html>
