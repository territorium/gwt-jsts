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

import javax.validation.constraints.NotNull;

import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 *
 * <p>
 * The <code>JsArray</code> class
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
 * @author <a href="mailto:peter.zanetti@territoriumonline.com">Peter
 *         Zanetti</a>.
 * @version 1.0,30.12.2016
 * @since 1.0.
 */
@JsType(name = "Array", namespace = JsPackage.GLOBAL, isNative = true)
public class JsArray<T> {

	@SafeVarargs
	public JsArray(@NotNull T... elements) {}

	@JsProperty(name = "length")
	public native int length();

	/**
	 * Gets the object at a given index.
	 * 
	 * @param index the index to be retrieved
	 * @return the object at the given index, or <code>null</code> if none exists
	 */
	// @JsOverlay
	// public final native T get(int index) /*-{
	// return this[index];
	// }-*/;
	//
	// @JsOverlay
	// public final native T[] toArray(JsArray<T> jsArray) /*-{
	// return this;
	// }-*/;

	public native void sort();

	public native int push(T element);
}
