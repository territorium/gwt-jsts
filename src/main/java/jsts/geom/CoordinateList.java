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
package jsts.geom;

import java.util.Collection;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;
import tol.j2cl.elem.global.Array;

/**
 *
 * <p>
 * The <code>CoordinateList</code> class
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
 * @version 1.0.0,28.11.2016
 * @since 1.0.0
 */
@JsType(isNative = true)
public class CoordinateList {

	/**
	 * Constructs a(n) {@link CoordinateList} object.
	 *
	 */
	@JsConstructor
	public CoordinateList() {}

	/**
	 * Constructs a(n) {@link CoordinateList} object.
	 *
	 * @param coords
	 */
	@JsConstructor
	public CoordinateList(Array<Coordinate> coords) {}

	/**
	 * Constructs a(n) {@link CoordinateList} object.
	 *
	 * @param coord
	 * @param allowRepeated
	 */
	@JsConstructor
	public CoordinateList(Array<Coordinate> coords, boolean allowRepeated) {}

	public native Coordinate getCoordinate(int i);

	public native boolean add(Coordinate coord);

	public native void add(Coordinate coord, boolean allowRepeated);

	public native void add(Array<Coordinate> coord, boolean allowRepeated);

	public native boolean add(Array<Coordinate> coord, boolean allowRepeated, int start, int end);

	public native boolean add(Array<Coordinate> coord, boolean allowRepeated, boolean direction);

	public native void add(int i, Coordinate coord, boolean allowRepeated);

	public native boolean addAll(Collection<Coordinate> coll);

	public native boolean addAll(Collection<Coordinate> coll, boolean allowRepeated);

	public native void closeRing();

	public native Coordinate[] toCoordinateArray();

	public native int size();

	@JsOverlay
	public static final CoordinateList create(Coordinate[] coords) {
		return new CoordinateList(Array.of(coords), true);
	}
}
