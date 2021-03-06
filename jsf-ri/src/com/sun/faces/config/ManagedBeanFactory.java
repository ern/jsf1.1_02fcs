/*
 * $Id: ManagedBeanFactory.java,v 1.25.8.1.2.1 2006/04/12 19:32:04 ofung Exp $
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

package com.sun.faces.config;

import com.sun.faces.RIConstants;
import com.sun.faces.config.beans.ListEntriesBean;
import com.sun.faces.config.beans.ManagedBeanBean;
import com.sun.faces.config.beans.ManagedPropertyBean;
import com.sun.faces.config.beans.MapEntriesBean;
import com.sun.faces.config.beans.MapEntryBean;
import com.sun.faces.util.Util;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.EvaluationException;
import javax.faces.el.PropertyNotFoundException;
import javax.faces.el.ReferenceSyntaxException;
import javax.faces.el.ValueBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * This class clreates a managed bean instance. It has a contract with
 * the ManagedBeanBean class which is populated from the config file.
 * The bean instance is created lazily so a deep copy of the
 * ManagedBeanBean is required.
 * <p/>
 * The Application implementation instantiated the beans as required and
 * stores them in the appropriate scope.
 */
public class ManagedBeanFactory extends Object {

    //
    // class variables
    //

    /**
     * <p>Used to determine the shortest scope in a mixed valueBinding
     * expression.</p>
     */

    private static Map scopeMap = null;

    //
    // Protected Constants
    //
    
    /**
     * This managed-bean or managed-property is a List
     */
    private final static int TYPE_IS_LIST = 0;

    /**
     * This managed-bean or managed-property is a Map
     */
    private final static int TYPE_IS_MAP = 1;

    /**
     * This managed-bean is a bean
     */
    private final static int TYPE_IS_BEAN = 2;

    /**
     * This managed-bean is a UIComponent
     */
    private final static int TYPE_IS_UICOMPONENT = 3;

    /**
     * This managed-property is a simple property
     */
    private final static int TYPE_IS_SIMPLE = 3;
    
    private static final String MANAGED_BEAN_CREATED_STACK = 
        RIConstants.FACES_PREFIX + "managedBeanStack";

    //
    // Class Variables
    //

    /**
     * <p>The <code>Log</code> instance for this class.</p>
     */
    private static Log log = LogFactory.getLog(ManagedBeanFactory.class);



    // Attribute Instance Variables

    ManagedBeanBean managedBean;
    String scope;

    // Relationship Instance Variables


    /**
     * <p>Setting this is part of the initialization contract for this
     * class.  Not setting this is a vialotation of that contract.</p>
     *
     */

    private Map managedBeanFactoryMap = null;

    //
    // Constructors and Initializers
    //
    /**
     * Constructor
     */
    public ManagedBeanFactory(ManagedBeanBean managedBean) {
        //ManagedBeanBean clone method implemented to return deep copy
        this.managedBean = managedBean; // (ManagedBeanBean) managedBean.clone();
        //set the scope
        scope = managedBean.getManagedBeanScope();
	if (null == scopeMap) {
	    scopeMap = new HashMap();
	    scopeMap.put(RIConstants.REQUEST, new Integer(1));
	    scopeMap.put(RIConstants.SESSION, new Integer(2));
	    scopeMap.put(RIConstants.APPLICATION, new Integer(3));
	}
    }


    public void setManagedBeanBean(ManagedBeanBean newBean) {
        synchronized (this) {
            //ManagedBeanBean clone method implemented to return deep copy
            this.managedBean = newBean; // (ManagedBeanBean) newBean.clone();
            //set the scope
            scope = managedBean.getManagedBeanScope();
        }
    }

    public Map getManagedBeanFactoryMap() {
	if (null == managedBeanFactoryMap) {
            if (log.isDebugEnabled()) {
                log.trace("Contract violation: ManagedBeanFactory must be initialized with managedBeanFactoryMap after instantiation.");
            }
	}

	return managedBeanFactoryMap;
    }

    public void setManagedBeanFactoryMap(Map newManagedBeanFactoryMap) {
	managedBeanFactoryMap = newManagedBeanFactoryMap;
    }



    /**
     * Attempt to instantiate the JavaBean and set its properties.
     */
    public Object newInstance(FacesContext context) throws FacesException {
        Object
            bean = null;
        int
            beanType = -1;

        // before instantiating the bean, make sure there is no cyclic 
        // references.
        Map requestMap = context.getExternalContext().getRequestMap();
        List beanList = (List)requestMap.get(MANAGED_BEAN_CREATED_STACK);
        if (beanList == null) {
            beanList = new ArrayList();
            requestMap.put(MANAGED_BEAN_CREATED_STACK, beanList);
        }
        
        if ( beanList.contains(managedBean.getManagedBeanName())) {
            if ( log.isErrorEnabled()) {
                log.error("Possible cyclic reference to managedBean " + 
                   managedBean.getManagedBeanName() + " ");
            }
            Object[] obj = new Object[1];
            obj[0] = managedBean.getManagedBeanName();
            throw new FacesException(
                Util.getExceptionMessageString(
                    Util.CYCLIC_REFERENCE_ERROR_ID, obj)); 
        }
        
        //
        // instantiate the bean
        //
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            if (loader == null) {
                loader = FacesContext.getCurrentInstance().
                    getClass().getClassLoader();
            }
            bean = java.beans.Beans.instantiate(loader,
                                                managedBean.getManagedBeanClass());
        } catch (Exception ex) {
            Object[] obj = new Object[2];
            obj[0] = managedBean.getManagedBeanClass();
            obj[1] = managedBean.getManagedBeanName();
            throw new FacesException(
                (Util.getExceptionMessageString(
                    Util.CANT_INSTANTIATE_CLASS_ERROR_MESSAGE_ID,  obj) + ". " + 
                    ex.getMessage()), ex);
        }
        // add the bean to the managed bean stack.
        beanList.add(managedBean.getManagedBeanName());

        // populate the bean with its contents
        try {
            // what kind of bean is this?
            switch (beanType = getBeanType(bean)) {
                case TYPE_IS_LIST:
                    copyListEntriesFromConfigToList(
                        managedBean.getListEntries(),
                        (List) bean);
                    break;
                case TYPE_IS_MAP:
                    copyMapEntriesFromConfigToMap(managedBean.getMapEntries(),
                                                  (Map) bean);
                    break;
                case TYPE_IS_UICOMPONENT:
                    // intentional fall-through
                case TYPE_IS_BEAN:
                    setPropertiesIntoBean(bean, beanType, managedBean);
                    break;
                default:
                    // notreached
                    Util.doAssert(false);
                    break;
            }
        } catch (FacesException fe) {
            throw fe;
        } catch (ClassNotFoundException cnfe) {
            Object[] obj = new Object[1];
            obj[0] = managedBean.getManagedBeanClass();
            throw new FacesException(
                Util.getExceptionMessageString(
                    Util.CANT_INSTANTIATE_CLASS_ERROR_MESSAGE_ID, obj),
                cnfe);
        }
        beanList.remove(managedBean.getManagedBeanName());
        return bean;
    }


    /**
     * determine the nature of the bean
     *
     * @return the appropriate TYPE_IS_* constant
     */

    protected int getBeanType(Object bean) {
        int result = -1;
        ListEntriesBean listEntries = null;
        MapEntriesBean mapEntries = null;

        // is it a List?
        if (null != (listEntries = managedBean.getListEntries())) {
            // managed-bean instances that are Lists, must not have
            // properties or map-entries.  It is a configuration error
            // if they do.
            if (null != managedBean.getMapEntries() ||
                (null != managedBean.getManagedProperties() &&
                (managedBean.getManagedProperties().length > 0))) {
                Object[] obj = new Object[1];
                obj[0] = managedBean.getManagedBeanClass();
                throw new FacesException(
                    Util.getExceptionMessageString(
                        Util.CANT_INSTANTIATE_CLASS_ERROR_MESSAGE_ID, obj));
            }
            result = TYPE_IS_LIST;
        }

        // is it a Map?
        if (null != (mapEntries = managedBean.getMapEntries())) {
            Util.doAssert(-1 == result);

            // managed-bean instances that are Maps, must not have
            // properties or list-entries.  It is a configuration error
            // if they do.
            if (null != managedBean.getListEntries() ||
                (null != managedBean.getManagedProperties() &&
                (managedBean.getManagedProperties().length > 0))) {
                Object[] obj = new Object[1];
                obj[0] = managedBean.getManagedBeanClass();
                throw new FacesException(
                    Util.getExceptionMessageString(
                        Util.CANT_INSTANTIATE_CLASS_ERROR_MESSAGE_ID, obj));
            }
            result = TYPE_IS_MAP;
        }

        if (TYPE_IS_LIST != result && TYPE_IS_MAP != result) {
            Util.doAssert(-1 == result);

            // if it's not a List or a Map, it must be a Bean
            if (bean instanceof UIComponent) {
                result = TYPE_IS_UICOMPONENT;
            } else {
                result = TYPE_IS_BEAN;
            }
        }

        Util.doAssert(-1 != result);
        return result;
    }


    /**
     * determine the nature of the property
     *
     * @return the appropriate TYPE_IS_* constant
     */

    protected int getPropertyType(ManagedPropertyBean bean) {
        int result = -1;
        ListEntriesBean listEntries = null;
        MapEntriesBean mapEntries = null;

        // is it a List?
        if (null != (listEntries = bean.getListEntries())) {
            // managed-property instances that have list-entries must
            // not have value or map-entries.  It is a configuration
            // error if they do.
            if (null != bean.getMapEntries() ||
                null != bean.getValue() || bean.isNullValue()) {
                Object[] obj = new Object[1];
                obj[0] = bean.getPropertyName();
                throw new FacesException(
                    Util.getExceptionMessageString(
                        Util.CANT_INSTANTIATE_CLASS_ERROR_MESSAGE_ID, obj));
            }
            result = TYPE_IS_LIST;
        }

        // is it a Map?
        if (null != (mapEntries = bean.getMapEntries())) {
            Util.doAssert(-1 == result);

            // managed-property instances that have map-entries, must
            // not have value or list-entries.  It is a configuration
            // error if they do.
            if (null != bean.getListEntries() ||
                null != bean.getValue() || bean.isNullValue()) {
                Object[] obj = new Object[1];
                obj[0] = bean.getPropertyName();
                throw new FacesException(
                    Util.getExceptionMessageString(
                        Util.CANT_INSTANTIATE_CLASS_ERROR_MESSAGE_ID, obj));
            }
            result = TYPE_IS_MAP;
        }

        if (TYPE_IS_LIST != result && TYPE_IS_MAP != result) {
            Util.doAssert(-1 == result);

            if (null != bean.getValue() || bean.isNullValue()) {
                result = TYPE_IS_SIMPLE;
            }
        }

        // if the managed-property doesn't have list-entries,
        // map-entries, value, or null-value contents, this is a
        // configuration error.  The DTD doesn't allow this anyway.
        if (-1 == result && !bean.isNullValue()) {
            Object[] obj = new Object[1];
            obj[0] = bean.getPropertyName();
            throw new FacesException(
                Util.getExceptionMessageString(
                    Util.CANT_INSTANTIATE_CLASS_ERROR_MESSAGE_ID, obj));
        }
        return result;
    }


    protected Class copyListEntriesFromConfigToList(ListEntriesBean listEntries,
                                                    List valuesForBean)
        throws ClassNotFoundException {
        String[] valuesFromConfig = listEntries.getValues();
        Class valueClass = java.lang.String.class;
        Object value = null;
        String strValue = null;
        int len = 0;

        if (0 == (len = valuesFromConfig.length)) {
            if (log.isTraceEnabled()) {
                log.trace("zero length array");
            }
            return null;
        }


        // pull out the value-class
        valueClass =
            getValueClassConsideringPrimitives(listEntries.getValueClass());

        // go through the values from the config and copy them to the
        // valuesForBean.
        for (int i = 0; i < len; i++) {
            strValue = valuesFromConfig[i];

            // set our value local variable
            if (Util.isVBExpression(strValue)) {
                value = evaluateValueBindingGet(strValue);
            } else if (null == strValue) {
                value = null;
            } else {
                value = strValue;
            }
            // convert the value if necessary
            value = getConvertedValueConsideringPrimitives(value, valueClass);

            valuesForBean.add(value);
        }
        return valueClass;
    }


    void copyMapEntriesFromConfigToMap(MapEntriesBean mapEntries,
                                       Map result)
        throws ClassNotFoundException {
        Object
            key = null,
            value = null;
        MapEntryBean[] valuesFromConfig = mapEntries.getMapEntries();
        MapEntryBean curEntry = null;
        Class
            keyClass = java.lang.String.class,
            valueClass = java.lang.String.class;
        String
            strKey = null,
            strValue = null;

        if (null == mapEntries || 0 == valuesFromConfig.length) {
            if (log.isTraceEnabled()) {
                log.trace("null or zero length array");
            }
            return;
        }

        // pull out the key-class and value-class
        keyClass =
            getValueClassConsideringPrimitives(mapEntries.getKeyClass());
        valueClass =
            getValueClassConsideringPrimitives(mapEntries.getValueClass());

        for (int i = 0, len = valuesFromConfig.length; i < len; i++) {
            curEntry = valuesFromConfig[i];

            strKey = curEntry.getKey();
            strValue = curEntry.getValue();

            if (Util.isVBExpression(strKey)) {
                key = evaluateValueBindingGet(strKey);
            } else if (null == strKey) {
                key = null;
            } else {
                key = getConvertedValueConsideringPrimitives(strKey,
                                                             keyClass);
            }

            if (Util.isVBExpression(strValue)) {
                value = evaluateValueBindingGet(strValue);
            } else if (null == strValue) {
                value = null;
            } else {
                value = getConvertedValueConsideringPrimitives(strValue,
                                                               valueClass);
            }
            result.put(key, value);
        }
    }


    protected void setPropertiesIntoBean(Object bean,
                                         int beanType,
                                         ManagedBeanBean managedBean) {
        Object
            value = null;
        String
            propertyClass = null,
            propertyName = null,
            strValue = null;
        int
            propertyType = -1;
        Class valueClass = null;
        ManagedPropertyBean[] properties = managedBean.getManagedProperties();

        if (null == properties) {
            // a bean is allowed to have no properties
            return;
        }

        // iterate over the properties and load each into the bean
        for (int i = 0, len = properties.length; i < len; i++) {
            // skip null properties or properties without names
            if (null == properties[i] ||
                null == (propertyName = properties[i].getPropertyName())) {
                continue;
            }
            // determine what kind of property we have
            try {
                // this switch statement sets the "value" local variable
                // and tries to set it into the bean.
                switch (propertyType = getPropertyType(properties[i])) {
                    case TYPE_IS_LIST:
                        setArrayOrListPropertiesIntoBean(bean, properties[i]);
                        break;
                    case TYPE_IS_MAP:
                        setMapPropertiesIntoBean(bean, properties[i]);                        
                        break;
                    case TYPE_IS_SIMPLE:
                        // if the config bean has no managed-property-class
                        // defined
                        if (null ==
                            (propertyClass = properties[i].getPropertyClass())) {
                            // look at the bean property
                            if (null ==
                                (valueClass =
                                PropertyUtils.getPropertyType(bean,
                                                              propertyName))) {
                                // if the bean property class can't be
                                // determined, use the fallback.
                                valueClass =
                                    getValueClassConsideringPrimitives(
                                        propertyClass);
                            }
                        } else {
                            // the config has a managed-property-class
                            // defined
                            valueClass =
                                getValueClassConsideringPrimitives(
                                    propertyClass);
                        }

                        strValue = properties[i].getValue();
                        if (Util.isVBExpression(strValue)) {
                            value = evaluateValueBindingGet(strValue);
                        } else if (null == strValue &&
                            properties[i].isNullValue()) {
                            value = null;
                        } else {
                            value = strValue;
                        }
                        // convert the value if necessary
                        value =
                            getConvertedValueConsideringPrimitives(value,
                                                                   valueClass);
                        PropertyUtils.setSimpleProperty(bean,
                                                        propertyName,
                                                        value);
                        break;
                    default:
                        Util.doAssert(false);
                }
            } catch (FacesException fe) {
                throw fe;
            } catch (Exception ex) {
                // if the property happens to be attribute on
                // UIComponent then bean introspection set will fail.
                if (TYPE_IS_UICOMPONENT == beanType) {
                    setComponentAttribute(bean, propertyName, value);
                } else {
                    // then this is a real exception to rethrow
                    Object[] obj = new Object[1];
                    obj[0] = propertyName;
                    throw new FacesException(
                        Util.getExceptionMessageString(
                            Util.ERROR_SETTING_BEAN_PROPERTY_ERROR_MESSAGE_ID,
                            obj),
                        ex);
                }
            }
        }
    }


    /**
     * <li><p> Call the property getter, if it exists.</p></li>
     * <p/>
     * <li><p>If the getter returns null or doesn't exist, create a
     * java.util.ArrayList(), otherwise use the returned Object (an array or
     * a java.util.List).</p></li>
     * <p/>
     * <li><p>If a List was returned or created in step 2), add all
     * elements defined by nested &lt;value&gt; elements in the order
     * they are listed, converting values defined by nested
     * &lt;value&gt; elements to the type defined by
     * &lt;value-class&gt;. If a &lt;value-class&gt; is not defined, use
     * the value as-is (i.e., as a java.lang.String). Add null for each
     * &lt;null-value&gt; element.</p></li>
     * <p/>
     * <li><p> If an array was returned in step 2), create a
     * java.util.ArrayList and copy all elements from the returned array to
     * the new List, auto-boxing elements of a primitive type. Add all
     * elements defined by nested &lt;value&gt; elements as described in step
     * 3).</p></li>
     * <p/>
     * <li><p> If a new java.util.List was created in step 2) and the
     * property is of type List, set the property by calling the setter
     * method, or log an error if there is no setter method.</p></li>
     * <p/>
     * <li><p> If a new java.util.List was created in step 4), convert
     * the * List to array of the same type as the property and set the
     * property by * calling the setter method, or log an error if there
     * is no setter * method.</p></li>
     */

    private void setArrayOrListPropertiesIntoBean(Object bean,
                                                  ManagedPropertyBean property)
        throws Exception {
        Object result = null;
        boolean
            getterIsNull = true,
            getterIsArray = false;
        List
            valuesForBean = null;
        Class
            valueType = java.lang.String.class,
            propertyType = null;

        String propertyName = property.getPropertyName();

        try {
            // see if there is a getter
            result = PropertyUtils.getProperty(bean, propertyName);
            getterIsNull = (null == result) ? true : false;

            propertyType =
                PropertyUtils.getPropertyType(bean, propertyName);
            getterIsArray = propertyType.isArray();

        } catch (NoSuchMethodException nsme) {
            // it's valid to not have a getter.
        }

        // the property has to be either a List or Array
        if (!getterIsArray) {
            if (null != propertyType &&
                !java.util.List.class.isAssignableFrom(propertyType)) {               
                throw new FacesException(Util.getExceptionMessageString(
                    Util.MANAGED_BEAN_CANNOT_SET_LIST_ARRAY_PROPERTY_ID,
                    new Object[] { propertyName,
                                   managedBean.getManagedBeanName() }));
            }
        }

        //
        // Deal with the possibility of the getter returning existing
        // values.
        //

        // if the getter returned non-null
        if (!getterIsNull) {
            // if what it returned was an array
            if (getterIsArray) {
                valuesForBean = new ArrayList();
                for (int i = 0, len = Array.getLength(result); i < len; i++) {
                    // add the existing values
                    valuesForBean.add(Array.get(result, i));
                }
            } else {
                // if what it returned was not a List
                if (!(result instanceof List)) {
                    // throw an exception                    
                    throw new FacesException(
                        Util.getExceptionMessageString(
                            Util.MANAGED_BEAN_EXISTING_VALUE_NOT_LIST_ID, 
                            new Object[] { propertyName,
                                           managedBean.getManagedBeanName() }));
                }
                valuesForBean = (List) result;
            }
        } else {

            // getter returned null
            result = valuesForBean = new ArrayList();
        }

        // at this point valuesForBean contains the existing values from
        // the bean, or no values if the bean had no values.  In any
        // case, we can proceed to add values from the config file.
        valueType = copyListEntriesFromConfigToList(property.getListEntries(),
                                                    valuesForBean);

        // at this point valuesForBean has the values to be set into the
        // bean.

        if (getterIsArray) {
            // convert back to Array
            result = Array.newInstance(valueType, valuesForBean.size());
            for (int i = 0, len = valuesForBean.size(); i < len; i++) {
                if (valueType == Boolean.TYPE) {
                    Array.setBoolean(result, i,
                                     ((Boolean) valuesForBean.get(i)).booleanValue());
                } else if (valueType == Byte.TYPE) {
                    Array.setByte(result, i,
                                  ((Byte) valuesForBean.get(i)).byteValue());
                } else if (valueType == Double.TYPE) {
                    Array.setDouble(result, i,
                                    ((Double) valuesForBean.get(i)).doubleValue());
                } else if (valueType == Float.TYPE) {
                    Array.setFloat(result, i,
                                   ((Float) valuesForBean.get(i)).floatValue());
                } else if (valueType == Integer.TYPE) {
                    Array.setInt(result, i,
                                 ((Integer) valuesForBean.get(i)).intValue());
                } else if (valueType == Character.TYPE) {
                    Array.setChar(result, i,
                                  ((Character) valuesForBean.get(i)).charValue());
                } else if (valueType == Short.TYPE) {
                    Array.setShort(result, i,
                                   ((Short) valuesForBean.get(i)).shortValue());
                } else if (valueType == Long.TYPE) {
                    Array.setLong(result, i,
                                  ((Long) valuesForBean.get(i)).longValue());
                } else {
                    Array.set(result, i, valuesForBean.get(i));
                }
            }
        } else {
            result = valuesForBean;
        }

        if (getterIsNull || getterIsArray) {
            PropertyUtils.setProperty(bean, propertyName, result);
        }

    }


    /**
     * <li><p>Call the property getter, if it exists.</p></li>
     * <p/>
     * <li><p>If the getter returns null or doesn't exist, create a
     * java.util.HashMap(), otherwise use the returned
     * java.util.Map.</p></li>
     * <p/>
     * <li><p>Add all entries defined by nested &lt;map-entry&gt;
     * elements in the order they are listed, converting key values
     * defined by nested &lt;key&gt; elements to the type defined by
     * &lt;key-class&gt; and entry values defined by nested
     * &lt;value&gt; elements to the type defined by
     * &lt;value-class&gt;. If &lt;key-class&gt; and/or
     * &lt;value-class&gt; are not defined, use the value as-is (i.e.,
     * as a java.lang.String). Add null for each &lt;null-value&gt;
     * element.</p></li>
     * <p/>
     * <li><p>If a new java.util.Map was created in step 2), set the
     * property by calling the setter method, or log an error if there
     * is no setter method.</p></li>
     */
    private void setMapPropertiesIntoBean(Object bean,
                                          ManagedPropertyBean property)
        throws Exception {
        Map result = null;
        boolean getterIsNull = true;
        Class propertyType = null;
        String propertyName = property.getPropertyName();

        try {
            // see if there is a getter
            result = (Map) PropertyUtils.getProperty(bean, propertyName);
            getterIsNull = (null == result) ? true : false;

            propertyType = PropertyUtils.getPropertyType(bean, propertyName);
        } catch (NoSuchMethodException nsme) {
            // it's valid to not have a getter.
        }

        if (null != propertyType &&
            !java.util.Map.class.isAssignableFrom(propertyType)) {            
            throw new FacesException(Util.getExceptionMessageString(
                Util.MANAGED_BEAN_CANNOT_SET_MAP_PROPERTY_ID,
                new Object[] { propertyName,
                               managedBean.getManagedBeanName() }));
        }


        if (getterIsNull) {
            result = new java.util.HashMap(
                  property.getMapEntries().getMapEntries().length);
        }

        // at this point result contains the existing entries from the
        // bean, or no entries if the bean had no entries.  In any case,
        // we can proceed to add values from the config file.

        copyMapEntriesFromConfigToMap(property.getMapEntries(), result);

        if (getterIsNull) {
            PropertyUtils.setProperty(bean, propertyName, result);
        }

    }


    private Class getValueClassConsideringPrimitives(String valueClass)
        throws ClassNotFoundException {
        Class valueType = java.lang.String.class;
        if (null != valueClass && 0 < valueClass.length()) {
            if (valueClass.equals(Boolean.TYPE.getName())) {
                valueType = Boolean.TYPE;
            } else if (valueClass.equals(Byte.TYPE.getName())) {
                valueType = Byte.TYPE;
            } else if (valueClass.equals(Double.TYPE.getName())) {
                valueType = Double.TYPE;
            } else if (valueClass.equals(Float.TYPE.getName())) {
                valueType = Float.TYPE;
            } else if (valueClass.equals(Integer.TYPE.getName())) {
                valueType = Integer.TYPE;
            } else if (valueClass.equals(Character.TYPE.getName())) {
                valueType = Character.TYPE;
            } else if (valueClass.equals(Short.TYPE.getName())) {
                valueType = Short.TYPE;
            } else if (valueClass.equals(Long.TYPE.getName())) {
                valueType = Long.TYPE;
            } else {
                valueType = Util.loadClass(valueClass, this);
            }
        }
        return valueType;
    }


    private Object getConvertedValueConsideringPrimitives(Object value,
                                                          Class valueType)
        throws FacesException {
        if (null != value && null != valueType) {
            if (valueType == Boolean.TYPE ||
                valueType == java.lang.Boolean.class) {
                value = Boolean.valueOf(value.toString().toLowerCase());
            } else if (valueType == Byte.TYPE ||
                valueType == java.lang.Byte.class) {
                value = new Byte(value.toString());
            } else if (valueType == Double.TYPE ||
                valueType == java.lang.Double.class) {
                value = new Double(value.toString());
            } else if (valueType == Float.TYPE ||
                valueType == java.lang.Float.class) {
                value = new Float(value.toString());
            } else if (valueType == Integer.TYPE ||
                valueType == java.lang.Integer.class) {
                value = new Integer(value.toString());
            } else if (valueType == Character.TYPE ||
                valueType == java.lang.Character.class) {
                value = new Character(value.toString().charAt(0));
            } else if (valueType == Short.TYPE ||
                valueType == java.lang.Short.class) {
                value = new Short(value.toString());
            } else if (valueType == Long.TYPE ||
                valueType == java.lang.Long.class) {
                value = new Long(value.toString());
            } else if (valueType == String.class) {
            } else if (!valueType.isAssignableFrom(value.getClass())) {               
                throw new FacesException(Util.getExceptionMessageString(
                    Util.MANAGED_BEAN_TYPE_CONVERSION_ERROR_ID,
                    new Object[] {
                          value.toString(),
                          value.getClass(),
                          valueType,
                          managedBean.getManagedBeanName()
                    }));

            }
        }
        return value;
    }


    public String getScope() {
        return scope;
    }


    private Object evaluateValueBindingGet(String value) throws FacesException {
        Object valueBinding = null;

        if (!hasValidLifespan(value)) {
            Object[] obj = new Object[1];
            obj[0] = value;
            throw new FacesException(
                Util.getExceptionMessageString(
                    Util.INVALID_SCOPE_LIFESPAN_ERROR_MESSAGE_ID, obj));
        }

        ValueBinding binding = Util.getValueBinding(value);
        if (binding != null) {
            try {
                valueBinding =
                    binding.getValue(FacesContext.getCurrentInstance());
            } catch (PropertyNotFoundException ex) {
                Object[] obj = new Object[1];
                obj[0] = value;
                throw new FacesException(
                    Util.getExceptionMessageString(
                        Util.ERROR_GETTING_VALUEREF_VALUE_ERROR_MESSAGE_ID,
                        obj));
            }
        } else {
            Object[] obj = new Object[1];
            obj[0] = value;
            throw new FacesException(
                Util.getExceptionMessageString(
                    Util.ERROR_GETTING_VALUE_BINDING_ERROR_MESSAGE_ID, obj));
        }
        return valueBinding;
    }


    private boolean hasValidLifespan(String value) throws EvaluationException, ReferenceSyntaxException {
	String valueScope = null;

	if (Util.isMixedVBExpression(value)) {
	    valueScope = getNarrowestScopeFromExpression(value);
	}
	else {
	    valueScope = getScopeForSingleExpression(value);
	}

        //if the managed bean's scope is "none" but the scope of the
        //referenced object is not "none", scope is invalid
        if (scope == null || scope.equalsIgnoreCase(RIConstants.NONE)) {
            if (valueScope != null && 
                !(valueScope.equalsIgnoreCase(RIConstants.NONE))) {
                return false;
            }
            return true;
        }
       
        //if the managed bean's scope is "request" it is able to refer
        //to objects in any scope
        if (scope.equalsIgnoreCase(RIConstants.REQUEST)) {
            return true;
        }

        //if the managed bean's scope is "session" it is able to refer
        //to objects in other "session", "application", or "none" scopes
        if (scope.equalsIgnoreCase(RIConstants.SESSION)) {
            if (valueScope != null) {
                if (valueScope.equalsIgnoreCase(RIConstants.REQUEST)) {
                    return false;
                }
            }
            return true;
        }

        //if the managed bean's scope is "application" it is able to refer
        //to objects in other "application", or "none" scopes
        if (scope.equalsIgnoreCase(RIConstants.APPLICATION)) {
            if (valueScope != null) {
                if (valueScope.equalsIgnoreCase(RIConstants.REQUEST) ||
                    valueScope.equalsIgnoreCase(RIConstants.SESSION)) {
                    return false;
                }
            }
            return true;
        }

        //the managed bean is required to be in either "request", "session",
        //"application", or "none" scopes. One of the previous decision
        //statements must be true.
        Util.doAssert(false);
        return false;
    }

    private String getScopeForSingleExpression(String value) throws ReferenceSyntaxException, EvaluationException {
	String [] firstSegment = new String[1];
        String valueScope = Util.getScope(value, firstSegment);
	
	if (null == valueScope) {
	    // Perhaps the bean hasn't been created yet.  See what its
	    // scope would be when it is created.
	    ManagedBeanFactory otherFactory = null;
	    if (null != firstSegment[0] &&
		null != (otherFactory = (ManagedBeanFactory)
			 getManagedBeanFactoryMap().get(firstSegment[0]))) {
		valueScope = otherFactory.getScope();
	    }
	    else {
		// we are referring to a bean that doesn't exist in the
		// configuration file.  Give it a wide scope...
                valueScope = RIConstants.APPLICATION;
	    }
	}
	return valueScope;
    }

    private String getNarrowestScopeFromExpression(String expression) throws ReferenceSyntaxException {
	// break the argument expression up into its component
	// expressions, ignoring literals.
	List expressions = Util.getExpressionsFromString(expression);
	Iterator iter = expressions.iterator();
	Integer 
	    shortestScope = null,
	    currentScope = null;
	String 
	    scope = null,
	    result = null;
	
	// loop over the expressions 
	while (iter.hasNext()) {
	    scope = getScopeForSingleExpression((String)iter.next());
	    // don't consider none
	    if (null == scope || scope.equalsIgnoreCase(RIConstants.NONE)) {
		continue;
	    }
	    // look up the scope for the current expression in scopeMap
	    currentScope = (Integer) scopeMap.get(scope);
	    
	    // if we have no basis for comparison
	    if (null == shortestScope) {
		shortestScope = currentScope;
		result = scope;
	    }
	    else {
		// we have a basis for comparison
		if (currentScope.intValue() < shortestScope.intValue()) {
		    shortestScope = currentScope;
		    result = scope;
		}
	    }
	}
	return result;
    }


    /**
     * Sets the passed in property name and value as an attribute on
     * <ocde>UIComponent</code> instance.
     */
    public void setComponentAttribute(Object component, String propName,
                                      Object propValue) {
        ((UIComponent) component).getAttributes().put(propName, propValue);
    }

}
