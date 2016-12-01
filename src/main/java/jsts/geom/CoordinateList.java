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
import jsinterop.annotations.JsType;

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
	 * @param coord
	 */
	@JsConstructor
	public CoordinateList(Coordinate[] coord) {}

	/**
	 * Constructs a(n) {@link CoordinateList} object.
	 *
	 * @param coord
	 * @param allowRepeated
	 */
	@JsConstructor
	public CoordinateList(Coordinate[] coord, boolean allowRepeated) {}

	/*
	 * @see com.vividsolutions.jts.geom.CoordinateList#getCoordinate(int)
	 */

	public native Coordinate getCoordinate(int i);

	/*
	 * @see
	 * com.vividsolutions.jts.geom.CoordinateList#add(com.vividsolutions.jts.geom.
	 * Coordinate[], boolean, int, int)
	 */

	public native boolean add(Coordinate[] coord, boolean allowRepeated, int start, int end);

	/*
	 * @see
	 * com.vividsolutions.jts.geom.CoordinateList#add(com.vividsolutions.jts.geom.
	 * Coordinate[], boolean, boolean)
	 */

	public native boolean add(Coordinate[] coord, boolean allowRepeated, boolean direction);

	/*
	 * @see
	 * com.vividsolutions.jts.geom.CoordinateList#add(com.vividsolutions.jts.geom.
	 * Coordinate[], boolean)
	 */

	public native boolean add(Coordinate[] coord, boolean allowRepeated);

	/*
	 * @see com.vividsolutions.jts.geom.CoordinateList#add(java.lang.Object,
	 * boolean)
	 */

	public native boolean add(Object obj, boolean allowRepeated);
	/*
	 * @see
	 * com.vividsolutions.jts.geom.CoordinateList#add(com.vividsolutions.jts.geom.
	 * Coordinate, boolean)
	 */

	public native void add(Coordinate coord, boolean allowRepeated);
	/*
	 * @see com.vividsolutions.jts.geom.CoordinateList#add(int,
	 * com.vividsolutions.jts.geom.Coordinate, boolean)
	 */

	public native void add(int i, Coordinate coord, boolean allowRepeated);

	/*
	 * @see
	 * com.vividsolutions.jts.geom.CoordinateList#addAll(java.util.Collection,
	 * boolean)
	 */

	public native boolean addAll(Collection<Coordinate> coll, boolean allowRepeated);

	/*
	 * @see com.vividsolutions.jts.geom.CoordinateList#closeRing()
	 */

	public native void closeRing();

	/*
	 * @see com.vividsolutions.jts.geom.CoordinateList#toCoordinateArray()
	 */

	public native Coordinate[] toCoordinateArray();

}
