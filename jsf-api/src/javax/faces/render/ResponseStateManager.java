/*
 * $Id: ResponseStateManager.java,v 1.13.30.1 2006/04/12 19:30:25 ofung Exp $
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

package javax.faces.render;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.application.StateManager.SerializedView;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;



/**
 * <p><strong>ResponseStateManager</strong> is the helper class to
 * {@link javax.faces.application.StateManager} that knows the specific
 * rendering technology being used to generate the response.  It is a
 * singleton abstract class, vended by the {@link RenderKit}.  This
 * class knows the mechanics of saving state, whether it be in hidden
 * fields, session, or some combination of the two.</p>
 */

public abstract class ResponseStateManager {


    /**
     *       
     * <p>Take the argument <code>state</code> and write it into
     * the output using the current {@link ResponseWriter}, which
     * must be correctly positioned already.</p>
     *
     * <p>If the {@link
     * javax.faces.application.StateManager.SerializedView} is to be
     * written out to hidden fields, the implementation must take care
     * to make all necessary character replacements to make the Strings
     * suitable for inclusion as an HTTP request paramater.</p>
     *
     * @param context The {@link FacesContext} instance for the current request
     * @param state The serialized state information previously saved
     *
     */
    public abstract void writeState(FacesContext context,
				    SerializedView state) throws IOException;
    

    /**
     * <p>The implementation must inspect the current request and return
     * the tree structure Object passed to it on a previous invocation of
     * <code>writeState()</code>.</p>
     *
     * @param context The {@link FacesContext} instance for the current request
     * @param viewId View identifier of the view to be restored
     *
     * @return the tree structure Object passed in to
     * <code>writeState</code>.  If this is the initial request, this
     * method returns null.
     */
    public abstract Object getTreeStructureToRestore(FacesContext context, 
						     String viewId);


    /**
     * <p>The implementation must inspect the current request and return
     * the component state Object passed to it on a previous invocation
     * of <code>writeState()</code>.</p>
     *
     * @param context The {@link FacesContext} instance for the current request
     *
     * @return the component state Object passed in to
     * <code>writeState</code>.
     */
    public abstract Object getComponentStateToRestore(FacesContext context);


}
