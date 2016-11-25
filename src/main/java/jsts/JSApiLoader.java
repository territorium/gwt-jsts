/*
 * Copyright (c) 2001-2016 Territorium Online Srl. All Rights Reserved.
 *
 * This file contains Original Code and/or Modifications of Original Code as
 * defined in and that are subject to the Territorium Online License Version
 * 1.0. You may not use this file except in compliance with the License. Please
 * obtain a copy of the License at http://www.tol.info/license/ and read it
 * before using this file.
 *
 * The Original Code and all software distributed under the License are
 * distributed on an 'AS IS' basis, WITHOUT WARRANTY OF ANY KIND, EITHER EXPRESS
 * OR IMPLIED, AND TERRITORIUM ONLINE HEREBY DISCLAIMS ALL SUCH WARRANTIES,
 * INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY, FITNESS FOR
 * A PARTICULAR PURPOSE, QUIET ENJOYMENT OR NON-INFRINGEMENT. Please see the
 * License for the specific language governing rights and limitations under the
 * License.
 */
package jsts;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

/**
 *
 * <p>
 * The <code>JSApiLoader</code> class is used to inject the JSTS JavaScript file
 * </p>
 * <p>
 * Copyright: 2003 - 2016 <a href="http://www.teritoriumonline.com">Territorium
 * Online Srl.</a>
 * </p>
 * <p>
 * Via Buozzi 12, 39100 Bolzano, Italy.
 * </p>
 * <p>
 * </p>
 * @author <a href="mailto:mapaccel@teritoriumonline.com">Peter Zanetti</a>.
 * @version 1.0.0,24.11.2016
 * @since 1.0.0
 */
public abstract class JSApiLoader {

	private static JsResources	res;
	private static boolean			loading	= false;

	private static JsResources get() {
		if (res == null) {
			res = GWT.create(JsResources.class);
		}
		return res;
	}

	/**
	 * <p>
	 * Injects the JavaScript libraries needed
	 * </p>
	 *
	 */
	public static void load() {
		if (!loading) {
			// Start loading the library.
			loading = true;
			ScriptInjector.fromString(get().jsts().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
		}
	}


	/**
	 *
	 * <p>
	 * The <code>JsResources</code> interface is used to define the JavaScript
	 * files as {@link TextResource}'s
	 * </p>
	 * <p>
	 * Copyright: 2003 - 2016
	 * <a href="http://www.teritoriumonline.com">Territorium Online Srl.</a>
	 * </p>
	 * <p>
	 * Via Buozzi 12, 39100 Bolzano, Italy.
	 * </p>
	 * <p>
	 * </p>
	 * @author <a href="mailto:mapaccel@teritoriumonline.com">Peter Zanetti</a>.
	 * @version 1.0.0,25.11.2016
	 * @since 1.0.0
	 */
	private interface JsResources extends ClientBundle {

		@Source("jsts.min-1.3.0.js")
		TextResource jsts();
	}

}