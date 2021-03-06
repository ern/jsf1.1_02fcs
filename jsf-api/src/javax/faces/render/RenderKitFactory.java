/*
 * $Id: RenderKitFactory.java,v 1.19.40.1 2006/04/12 19:30:25 ofung Exp $
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


import java.util.Iterator;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;


/**
 * <p><strong>RenderKitFactory</strong> is a factory object that registers
 * and returns {@link RenderKit} instances.  Implementations of
 * JavaServer Faces must provide at least a default implementation of
 * {@link RenderKit}.  Advanced implementations (or external third party
 * libraries) may provide additional {@link RenderKit} implementations
 * (keyed by render kit identifiers) for performing different types of
 * rendering for the same components.</p>
 *
 * <p>There must be one {@link RenderKitFactory} instance per web
 * application that is utilizing JavaServer Faces.  This instance can be
 * acquired, in a portable manner, by calling:</p>
 * <pre>
 *   RenderKitFactory factory = (RenderKitFactory)
 *    FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
 * </pre>
 */

public abstract class RenderKitFactory {


    /**
     * <p>The render kit identifier of the default {@link RenderKit} instance
     * for this JavaServer Faces implementation.</p>
     */
    public static final String HTML_BASIC_RENDER_KIT = "HTML_BASIC";


    /**
     * <p>Register the specified {@link RenderKit} instance, associated with
     * the specified <code>renderKitId</code>, to be supported by this
     * {@link RenderKitFactory}, replacing any previously registered
     * {@link RenderKit} for this identifier.</p>
     *
     * @param renderKitId Identifier of the {@link RenderKit} to register
     * @param renderKit {@link RenderKit} instance that we are registering
     *
     * @exception NullPointerException if <code>renderKitId</code> or
     *  <code>renderKit</code> is <code>null</code>
     */
    public abstract void addRenderKit(String renderKitId,
                                      RenderKit renderKit);


    /**
     * <p>Return a {@link RenderKit} instance for the specified render
     * kit identifier, possibly customized based on dynamic
     * characteristics of the specified {@link FacesContext}, if
     * non-<code>null</code>.  If there is no registered {@link
     * RenderKit} for the specified identifier, return
     * <code>null</code>.  The set of available render kit identifiers
     * is available via the <code>getRenderKitIds()</code> method.</p>
     *
     * @param context FacesContext for the request currently being
     * processed, or <code>null</code> if none is available.
     * @param renderKitId Render kit identifier of the requested
     *  {@link RenderKit} instance
     *
     * @exception IllegalArgumentException if no {@link RenderKit} instance
     *  can be returned for the specified identifier
     * @exception NullPointerException if <code>renderKitId</code> is
     * <code>null</code>
     */
    public abstract RenderKit getRenderKit(FacesContext context, 
					   String renderKitId);


    /**
     * <p>Return an <code>Iterator</code> over the set of render kit
     * identifiers registered with this factory.  This set must include
     * the value specified by <code>RenderKitFactory.HTML_BASIC_RENDER_KIT</code>.
     * </p>
     */
    public abstract Iterator getRenderKitIds();


}
