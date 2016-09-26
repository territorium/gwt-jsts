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

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * A lightweight class used to store coordinates on the 2-dimensional Cartesian
 * plane. It is distinct from Point, which is a subclass of Geometry. Unlike
 * objects of type Point (which contain additional information such as an
 * envelope, a precision model, and spatial reference system information), a
 * Coordinate only contains ordinate values and accessor methods. Coordinates
 * are two-dimensional points, with an additional Z-ordinate. If an Z-ordinate
 * value is not specified or not defined, constructed coordinates have a
 * Z-ordinate of NaN (which is also the value of NULL_ORDINATE). The standard
 * comparison functions ignore the Z-ordinate. Apart from the basic accessor
 * functions, JTS supports only specific operations involving the Z-ordinate.
 */
@JsType(name = "Coordinate", namespace = "jsts.geom", isNative = true)
public class Coordinate {

	/**
	 * The x-coordinate.
	 */
	@JsProperty(name = "x")
	public double	x;

	/**
	 * The y-coordinate.
	 */
	@JsProperty(name = "y")
	public double	y;

	/**
	 * The z-coordinate.
	 */
	@JsProperty(name = "z")
	public double	z;

}
