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

import jsinterop.annotations.JsType;

/**
 * Models an OGC-style LineString. A LineString consists of a sequence of two or
 * more vertices, along with all points along the linearly-interpolated curves
 * (line segments) between each pair of consecutive vertices. Consecutive
 * vertices may be equal. The line segments in the line may intersect each other
 * (in other words, the linestring may "curl back" in itself and self-intersect.
 * Linestrings with exactly two identical points are invalid. A linestring must
 * have either 0 or 2 or more points. If these conditions are not met, the
 * constructors throw an IllegalArgumentException
 */
@JsType(name = "LineString", namespace = "jsts.geom", isNative = true)
public class LineString extends Geometry {

	/**
	 * 
	 * {@link #getCoordinateSequence}
	 *
	 * @return
	 */
	public native CoordinateSequence getCoordinateSequence();

}
