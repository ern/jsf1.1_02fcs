/*
 * $Id: EvaluatorBean.java,v 1.2.32.1 2006/04/12 19:32:46 ofung Exp $
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

package com.sun.faces.systest;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIOutput;
import javax.faces.el.ValueBinding;

import java.util.Date;
import java.text.SimpleDateFormat;

public class EvaluatorBean extends Object {

    public EvaluatorBean() {}

    protected int reps = 30000;
    public int getReps() {
	return reps;
    }

    public void setReps(int newReps) {
	reps = newReps;
    }

    private long start = 0;
    private long end = 0;
    private SimpleDateFormat formatter = new SimpleDateFormat("mm:ss:SS");

    public String getElapsedTime() {
	long elapsedSeconds = end - start;
	end = start = 0;
	return formatter.format(new Date(elapsedSeconds));
    }

    public void doGet(ActionEvent event) {
	int i = 0;
	FacesContext context = FacesContext.getCurrentInstance();
	String id = event.getComponent().getId();
	// strip off the first character
	id = "i" + id.substring(1);
	// get the expression to evaluate
	UIOutput output = (UIOutput) context.getViewRoot().findComponent("form" + NamingContainer.SEPARATOR_CHAR +  id);
	String expression = "#{" + output.getValue() + "}";
	ValueBinding vb = 
	    context.getApplication().createValueBinding(expression);
	// if the user wants to show results
	if (showResults) {
	    // clear the buffer for the new results
	    results = new StringBuffer();
	}
	// evaluate it as a get, reps number of times 
	start = System.currentTimeMillis();
	for (i = 0; i < reps; i++) {
	    if (showResults) {
		results.append(vb.getValue(context) + "\n");
	    }
	    else {
		vb.getValue(context);
	    }
	}
	end = System.currentTimeMillis();
    }

    protected boolean showResults = false;
    public boolean isShowResults() {
	return showResults;
    }

    public void setShowResults(boolean newShowResults) {
	showResults = newShowResults;
    }

    protected String [] expressions;
    public String [] getExpressions() {
	return expressions;
    }

    public void setExpressions(String [] newExpressions) {
	expressions = newExpressions;
    }


    protected StringBuffer results = new StringBuffer();
    public String getResults() {
	return results.toString();
    }

    public void setResults(String newResults) {
	results = new StringBuffer(newResults);
    }
    

}
