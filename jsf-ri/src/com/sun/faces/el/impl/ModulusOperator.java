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

/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999 The Apache Software Foundation.  All rights 
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer. 
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:  
 *       "This product includes software developed by the 
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Tomcat", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written 
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package com.sun.faces.el.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigInteger;

/**
 * <p>The implementation of the modulus operator
 *
 * @author Nathan Abramson - Art Technology Group
 * @version $Change: 181177 $$DateTime: 2001/06/26 08:45:09 $$Author: ofung $
 */

public class ModulusOperator
    extends BinaryOperator {

    //-------------------------------------
    // Constants
    //-------------------------------------
    private static Log log = LogFactory.getLog(ModulusOperator.class);
    
    //-------------------------------------
    // Singleton
    //-------------------------------------

    public static final ModulusOperator SINGLETON =
        new ModulusOperator();

    //-------------------------------------
    /**
     * Constructor
     */
    public ModulusOperator() {
    }

    //-------------------------------------
    // Expression methods
    //-------------------------------------
    /**
     * Returns the symbol representing the operator
     */
    public String getOperatorSymbol() {
        return "%";
    }

    //-------------------------------------
    /**
     * Applies the operator to the given value
     */
    public Object apply(Object pLeft, Object pRight)
        throws ElException {
        if (pLeft == null &&
            pRight == null) {
            if (log.isWarnEnabled()) {
                log.warn(MessageUtil.getMessageWithArgs(
                    Constants.ARITH_OP_NULL, getOperatorSymbol()));
            }
            return PrimitiveObjects.getInteger(0);
        }

        if ((pLeft != null &&
            (Coercions.isFloatingPointType(pLeft) ||
            Coercions.isFloatingPointString(pLeft))) ||
            Coercions.isBigDecimal(pLeft) ||
            (pRight != null &&
            (Coercions.isFloatingPointType(pRight) ||
            Coercions.isFloatingPointString(pRight) ||
            Coercions.isBigDecimal(pRight)))) {
            double left =
                Coercions.coerceToPrimitiveNumber(pLeft, Double.class).
                doubleValue();
            double right =
                Coercions.coerceToPrimitiveNumber(pRight, Double.class).
                doubleValue();

            try {
                return PrimitiveObjects.getDouble(left % right);
            } catch (Exception exc) {
                if (log.isErrorEnabled()) {
                    String message = MessageUtil.getMessageWithArgs(
                        Constants.ARITH_ERROR,
                        getOperatorSymbol(),
                        "" + left,
                        "" + right);
                    log.error(message);
                    throw new ElException(message);
                }
                return PrimitiveObjects.getInteger(0);
            }
        } else if (Coercions.isBigInteger(pLeft) ||
            Coercions.isBigInteger(pRight)) {
            BigInteger left = (BigInteger)
                Coercions.coerceToPrimitiveNumber(pLeft, BigInteger.class);
            BigInteger right = (BigInteger)
                Coercions.coerceToPrimitiveNumber(pRight, BigInteger.class);

            try {
                return left.remainder(right);
            } catch (Exception exc) {
                if (log.isErrorEnabled()) {
                    String message = MessageUtil.getMessageWithArgs(
                        Constants.ARITH_ERROR,
                        getOperatorSymbol(),
                        "" + left,
                        "" + right);
                    log.error(message);
                    throw new ElException(message);
                }
                return PrimitiveObjects.getInteger(0);
            }
        } else {
            long left =
                Coercions.coerceToPrimitiveNumber(pLeft, Long.class).
                longValue();
            long right =
                Coercions.coerceToPrimitiveNumber(pRight, Long.class).
                longValue();

            try {
                return PrimitiveObjects.getLong(left % right);
            } catch (Exception exc) {
                if (log.isErrorEnabled()) {
                    String message = MessageUtil.getMessageWithArgs(
                        Constants.ARITH_ERROR,
                        getOperatorSymbol(),
                        "" + left,
                        "" + right);
                    log.error(message);
                    throw new ElException(message);
                }
                return PrimitiveObjects.getInteger(0);
            }
        }
    }

    //-------------------------------------
}