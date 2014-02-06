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


package com.sun.faces.el.impl;

/**
 * <p>This class is used to customize the way an ExpressionEvaluator resolves
 * variable references at evaluation time. For example, instances of this class
 * can implement their own variable lookup mechanisms, or introduce the notion
 * of "implicit variables" which override any other variables.
 * An instance of this class should be passed when evaluating an expression.</p>
 * <p/>
 * <p>An instance of this class includes the context against which resolution will happen.</p>
 */

public abstract class VariableResolver {

    /**
     * <p>Resolves the specified variable.  Returns null
     * if the variable is not found</p>
     *
     * @param variableName the name of the variable to resolve
     * @return the result of the variable resolution, or null
     *         if the variable cannot be found.
     * @throws ElException if a failure occurred during variable
     *                     resolution
     */
    public abstract Object resolve(String variableName) throws ElException;
}
