<%@ page contentType="text/plain"
%><%@ page import="java.util.Iterator"
%><%@ page import="java.util.Map"
%><%@ page import="javax.faces.FactoryFinder"
%><%@ page import="javax.faces.context.FacesContext"
%><%@ page import="javax.faces.render.RenderKit"
%><%@ page import="javax.faces.render.RenderKitFactory"
%><%@ page import="javax.faces.render.Renderer"
%><%

// This test goes through the config system to test the loading of 
// the default renderkit information as well as a custom renderkit
// consisting of one renderer.

    // Initialize list of Renderer types
    //
    String families[] = {
      "javax.faces.Command",
      "javax.faces.Command",
      "javax.faces.Data",
      "javax.faces.Form",
      "javax.faces.Graphic",
      "javax.faces.Input",
      "javax.faces.Input",
      "javax.faces.Input",
      "javax.faces.Input",
      "javax.faces.Message",
      "javax.faces.Messages",
      "javax.faces.Output",
      "javax.faces.Output",
      "javax.faces.Output",
      "javax.faces.Output",
      "javax.faces.Panel",
      "javax.faces.Panel",
      "javax.faces.SelectBoolean",
      "javax.faces.SelectMany",
      "javax.faces.SelectMany",
      "javax.faces.SelectMany",
      "javax.faces.SelectOne",
      "javax.faces.SelectOne",
      "javax.faces.SelectOne"
    };

    String defaultList[] = {
      "javax.faces.Button",
      "javax.faces.Link",
      "javax.faces.Table",
      "javax.faces.Form",
      "javax.faces.Image",
      "javax.faces.Hidden",
      "javax.faces.Secret",
      "javax.faces.Text",
      "javax.faces.Textarea",
      "javax.faces.Message",
      "javax.faces.Messages",
      "javax.faces.Format",
      "javax.faces.Label",
      "javax.faces.Link",
      "javax.faces.Text",
      "javax.faces.Grid",
      "javax.faces.Group",
      "javax.faces.Checkbox",
      "javax.faces.Checkbox",
      "javax.faces.Listbox",
      "javax.faces.Menu",
      "javax.faces.Listbox",
      "javax.faces.Menu",
      "javax.faces.Radio"
      };

    String customFamilies[] = {"SysTest"};
    String customList[] = {"Text"};

    // Acquire RenderKits and check RenderKitId(s)
    //
    RenderKitFactory renderKitFactory = (RenderKitFactory)
        FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
    Iterator renderKitIds = renderKitFactory.getRenderKitIds();

    boolean foundDefault = false; 
    boolean foundCustom= false; 
    while (renderKitIds.hasNext()) {
        String renderKitId = (String)renderKitIds.next();
        if (renderKitId.equals(RenderKitFactory.HTML_BASIC_RENDER_KIT)) {
            foundDefault = true;
        } else if (renderKitId.equals("CUSTOM")) {
            foundCustom = true;
        }
    }
    if (!foundDefault || !foundCustom) {
        out.println("/renderkit01.jsp FAILED - all renderkit ids not found");
	return;
    }

    // Check Renderers For Each RenderKit
    //
    while (renderKitIds.hasNext()) {
        String renderKitId = (String)renderKitIds.next();
	RenderKit rKit = renderKitFactory.getRenderKit(null, renderKitId);
	if (rKit == null) {
	    out.println("/renderkit01.jsp FAILED - renderkit not found");
	    return;
	}
	Renderer renderer = null;
	if (renderKitId.equals(RenderKitFactory.HTML_BASIC_RENDER_KIT)) {
	    for (int i=0; i<defaultList.length; i++) {
	        try {
	            renderer = rKit.getRenderer(families[i], defaultList[i]);
	        } catch (IllegalArgumentException ia) {
	            out.println("/renderkit01.jsp FAILED - renderer not found for type:"+
		        defaultList[i]+" in renderkit 'DEFAULT'");
		    return;
	        }
	    }
	} else if (renderKitId.equals("CUSTOM")) {
	    for (int i=0; i<customList.length; i++) {
	        try {
	            renderer = rKit.getRenderer(customFamilies[i],
                                                customList[i]);
	        } catch (IllegalArgumentException ia) {
	            out.println("/renderkit01.jsp FAILED - renderer not found for type:"+
		        customList[i]+" in renderkit 'CUSTOM'");
		    return;
	        }
	    }
	}
    }

    // All tests passed
    //
    out.println("/renderkit01.jsp PASSED");
%>
