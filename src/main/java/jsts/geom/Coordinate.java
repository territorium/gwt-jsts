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

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;

/**
 *
 * <p>
 * A lightweight class used to store coordinates on the 2-dimensional Cartesian
 * plane. It is distinct from Point, which is a subclass of Geometry. Unlike
 * objects of type Point (which contain additional information such as an
 * envelope, a precision model, and spatial reference system information), a
 * Coordinate only contains ordinate values and accessor methods.
 * </p>
 * <p>
 * Coordinates are two-dimensional points, with an additional Z-ordinate. If an
 * Z-ordinate value is not specified or not defined, constructed coordinates
 * have a Z-ordinate of NaN (which is also the value of NULL_ORDINATE). The
 * standard comparison functions ignore the Z-ordinate. Apart from the basic
 * accessor functions, JTS supports only specific operations involving the
 * Z-ordinate.
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
 * @version 1.0.0,25.11.2016
 * @since 1.0.0
 */
@JsType(isNative = true)
public class Coordinate {

	/**
	 * The x-coordinate.
	 */
	public double	x;

	/**
	 * The y-coordinate.
	 */
	public double	y;

	/**
	 * The z-coordinate.
	 */
	public double	z;

	/**
	 * Constructs a(n) {@link Coordinate} object.
	 *
	 * @param x
	 * @param y
	 */
	@JsConstructor
	public Coordinate(double x, double y) {}

	public native void setCoordinate(Coordinate other);

	public native double getOrdinate(int ordinateIndex);

	public native void setOrdinate(int ordinateIndex, double value);

	public native boolean equals2D(Coordinate other);

	public native boolean equals2D(Coordinate c, double tolerance);

	public native boolean equals3D(Coordinate other);

	public native boolean equalInZ(Coordinate c, double tolerance);

	public native Coordinate copy();

	public native double distance(Coordinate c);

	public native double distance3D(Coordinate c);

}
