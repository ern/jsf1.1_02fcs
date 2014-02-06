/*
 * @(#)Options2.java  1.6 03/01/17
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

package com.sun.tdk.jcov.tools;

import java.util.Hashtable;
import java.util.Vector;
import java.io.StreamTokenizer;
import java.io.PrintStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Extend Options to allow options to be read from a file
 * 
 * @author ed burns
 */
public class Options2 extends Options {

    public Options2() {
    }

    public Options2(String[] valid_options) {
        super(valid_options);
    }

    public Options2(OptionDescr[] valid_options) {
	super(valid_options);
    }

    /**
     * Override superclass parse to support the -option_file argument.
     * This option points to a resolvable path to a file that contains
     * one option per line.
     */
    public boolean parse(String option) throws Exception {
	String opt_name = null, opt_val = null;
	if (null != option && option.startsWith("-option_file")) {
	    int delim_ind = option.indexOf(OPT_VAL_DELIM);
	    if (delim_ind >= 0) {
		opt_name = option.substring(1, delim_ind);
		opt_val  = option.substring(delim_ind + 1);
	    }	
	    // interpret the file to contain one option per line
	    BufferedReader file = new BufferedReader(new FileReader(opt_val));
	    String curLine = null;
	    while (null != (curLine = file.readLine())) {
		if (!super.parse(curLine)) {
		    return false;
		}
	    }
	} 
	else {
	    return super.parse(option);
	}
	return true;
    }
}
