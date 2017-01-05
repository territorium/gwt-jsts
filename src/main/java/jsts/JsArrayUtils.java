/*
 * Copyright (c) 2001-2017 Territorium Online Srl. All Rights Reserved.
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

/**
 *
 * <p>
 * The <code>JsArrayUtils</code> class
 * </p>
 * <p>
 * Copyright: 2003 - 2017 <a href="http://www.teritoriumonline.com">Territorium
 * Online Srl.</a>
 * </p>
 * <p>
 * Via Buozzi 12, 39100 Bolzano, Italy.
 * </p>
 * <p>
 * </p>
 * @author <a href="mailto:mapaccel@teritoriumonline.com">Peter Zanetti</a>.
 * @version 4.0.0,05.01.2017
 * @since 4.0.0
 */
public abstract class JsArrayUtils {

	@SafeVarargs
	public static final <T> JsArray<T> create(@NotNull T... elements) {
		return new JsArray<>(elements);
	}

	public static final native <T> T[] toArray(JsArray<T> jsArray) /*-{
		return this;
	}-*/;

	public static final <T> JsArray<T> toJsArray(T[] array) {
		return JsArrayUtils.create(array);
	};

	/**
	 * Gets the object at a given index.
	 * 
	 * @param index the index to be retrieved
	 * @return the object at the given index, or <code>null</code> if none exists
	 */
	public static final native <T> T get(JsArray<T> jsArray, int index) /*-{
		return this[index];
	}-*/;

}
