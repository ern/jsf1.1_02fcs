/*
 * $Id: MockApplication.java,v 1.22.36.1 2006/04/12 19:30:39 ofung Exp $
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

package javax.faces.mock;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectMany;
import javax.faces.component.UISelectOne;
import javax.faces.convert.Converter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.PropertyResolver;
import javax.faces.el.ValueBinding;
import javax.faces.el.VariableResolver;
import javax.faces.event.ActionListener;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;
import javax.faces.validator.Validator;


public class MockApplication extends Application {


    public MockApplication() {
        addComponent("TestNamingContainer",
                     "javax.faces.webapp.TestNamingContainer");
        addComponent("TestComponent", "javax.faces.webapp.TestComponent");
        addComponent("TestInput", "javax.faces.component.UIInput");
        addComponent("TestOutput", "javax.faces.component.UIOutput");
        addConverter("Integer", "javax.faces.convert.IntegerConverter");
        addConverter("javax.faces.Number", 
		     "javax.faces.convert.NumberConverter");
        addConverter("javax.faces.Long", 
		     "javax.faces.convert.LongConverter");
        addValidator("Length", "javax.faces.validator.LengthValidator");
    }


    private ActionListener actionListener = null;
    private static boolean processActionCalled = false;
    public ActionListener getActionListener() {
	if (null == actionListener) {
	    actionListener = new ActionListener() {
		    public void processAction(ActionEvent e) {
			processActionCalled = true;
		    }
		    // see if the other object is the same as our
		    // anonymous inner class implementation.
		    public boolean equals(Object otherObj) {
			if (!(otherObj instanceof ActionListener)) {
			    return false;
			}
			ActionListener other = (ActionListener) otherObj;

			processActionCalled = false;
			other.processAction(null);
			boolean result = processActionCalled;
			processActionCalled = false;
			return result;
		    }
		};
	}
	
        return (this.actionListener);
    }
    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }


    private NavigationHandler navigationHandler = null;
    public NavigationHandler getNavigationHandler() {
        return (this.navigationHandler);
    }
    public void setNavigationHandler(NavigationHandler navigationHandler) {
        this.navigationHandler = navigationHandler;
    }


    private PropertyResolver propertyResolver = null;
    public PropertyResolver getPropertyResolver() {
        if (propertyResolver == null) {
            propertyResolver = new MockPropertyResolver();
        }
        return (this.propertyResolver);
    }
    public void setPropertyResolver(PropertyResolver propertyResolver) {
        this.propertyResolver = propertyResolver;
    }


    public MethodBinding createMethodBinding(String ref, Class params[]) {
        if (ref == null) {
            throw new NullPointerException();
        } else {
            return (new MockMethodBinding(this, ref, params));
        }
    }


    public ValueBinding createValueBinding(String ref) {
        if (ref == null) {
            throw new NullPointerException();
        } else {
            return (new MockValueBinding(this, ref));
        }
    }


    private VariableResolver variableResolver = null;
    public VariableResolver getVariableResolver() {
        if (variableResolver == null) {
            variableResolver = new MockVariableResolver();
        }
        return (this.variableResolver);
    }
    public void setVariableResolver(VariableResolver variableResolver) {
        this.variableResolver = variableResolver;
    }

    private ViewHandler viewHandler = null;
    public ViewHandler getViewHandler() {
	if (null == viewHandler) {
	    viewHandler = new MockViewHandler();
	}
        return (this.viewHandler);
    }
    public void setViewHandler(ViewHandler viewHandler) {
        this.viewHandler = viewHandler;
    }


    private StateManager stateManager = null;
    public StateManager getStateManager() {
	if (null == stateManager) {
	    stateManager = new MockStateManager();
	}
        return (this.stateManager);
    }
    public void setStateManager(StateManager stateManager) {
        this.stateManager = stateManager;
    }

    private Map components = new HashMap();
    public void addComponent(String componentType, String componentClass) {
        components.put(componentType, componentClass);
    }
    public UIComponent createComponent(String componentType) {
        String componentClass = (String) components.get(componentType);
        try {
            Class clazz = Class.forName(componentClass);
            return ((UIComponent) clazz.newInstance());
        } catch (Exception e) {
            throw new FacesException(e);
        }
    }
    public UIComponent createComponent(ValueBinding componentBinding,
                                       FacesContext context,
                                       String componentType)
        throws FacesException {
	throw new FacesException(new UnsupportedOperationException());
    }
    public Iterator getComponentTypes() {
        return (components.keySet().iterator());
    }


    private Map converters = new HashMap();
    public void addConverter(String converterId, String converterClass) {
        converters.put(converterId, converterClass);
    }
    public void addConverter(Class targetClass, String converterClass) {
        throw new UnsupportedOperationException();
    }
    public Converter createConverter(String converterId) {
        String converterClass = (String) converters.get(converterId);
        try {
            Class clazz = Class.forName(converterClass);
            return ((Converter) clazz.newInstance());
        } catch (Exception e) {
            throw new FacesException(e);
        }
    }
    public Converter createConverter(Class targetClass) {
        throw new UnsupportedOperationException();
    }
    public Iterator getConverterIds() {
        return (converters.keySet().iterator());
    }
    public Iterator getConverterTypes() {
        throw new UnsupportedOperationException();
    }
    
    private String messageBundle = null;
    public void setMessageBundle(String messageBundle) {
	this.messageBundle = messageBundle;
    }

    public String getMessageBundle() {
	return messageBundle;
    }

    private Map validators = new HashMap();
    public void addValidator(String validatorId, String validatorClass) {
        validators.put(validatorId, validatorClass);
    }
    public Validator createValidator(String validatorId) {
        String validatorClass = (String) validators.get(validatorId);
        try {
            Class clazz = Class.forName(validatorClass);
            return ((Validator) clazz.newInstance());
        } catch (Exception e) {
            throw new FacesException(e);
        }
    }
    public Iterator getValidatorIds() {
        return (validators.keySet().iterator());
    }

    public Iterator getSupportedLocales() {
	return Collections.EMPTY_LIST.iterator();
    }

    public void setSupportedLocales(Collection newLocales) {
    }

    public Locale getDefaultLocale(){
	return Locale.getDefault();
    }

    public void setDefaultLocale(Locale newLocale) {
    }

    public String getDefaultRenderKitId() { 
	return null;
    }

    public void setDefaultRenderKitId(String renderKitId) {
    }

}
