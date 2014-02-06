/*
 * @(#)InstrMain2.java    1.10 03/11/19
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

package com.sun.tdk.jcov;

import com.sun.tdk.jcov.tools.Options2;


/**
 * Main class of the instrumenter utility
 */
public class InstrMain2 extends InstrMain {

    public static void main(String[] args) {
        InstrMain2 instrumenter = new InstrMain2();
        Options2 opts = new Options2(VALID_OPTIONS);
        int i = 0;
        try {
            for (i = 0; i < args.length && opts.parse(args[i]); i++);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            instrumenter.usage();
            System.exit(1);
        }
        int src_total = args.length - i;
        if (src_total <= 0) {
            System.out.println("no input files specified");
            instrumenter.usage();
            System.exit(0);
        }
        String[] srcs = new String[src_total];
        System.arraycopy(args, i, srcs, 0, src_total);
        try {
            instrumenter.run(opts, srcs, instrumenter.log);
        } catch (Exception e) {
            instrumenter.log.error(e.getMessage());
            System.exit(1);
        }
        System.exit(0);
    }

}
