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
 * The <code>LineSegment</code> class
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
public class LineSegment {

	/**
	 * Constructs a(n) {@link LineSegment} object.
	 *
	 */
	@JsConstructor
	public LineSegment() {}

	/**
	 * Constructs a(n) {@link LineSegment} object.
	 *
	 * @param ls
	 */
	@JsConstructor
	public LineSegment(LineSegment ls) {}

	/**
	 * Constructs a(n) {@link LineSegment} object.
	 *
	 * @param p0
	 * @param p1
	 */
	@JsConstructor
	public LineSegment(Coordinate p0, Coordinate p1) {

	}

	/**
	 * Constructs a(n) {@link LineSegment} object.
	 *
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 */
	@JsConstructor
	public LineSegment(double x0, double y0, double x1, double y1) {}

	public native Coordinate getCoordinate(int i);

	public native void setCoordinates(LineSegment ls);

	public native void setCoordinates(Coordinate p0, Coordinate p1);

	public native double getLength();

	public native boolean isHorizontal();

	public native boolean isVertical();

	public native int orientationIndex(LineSegment seg);

	public native int orientationIndex(Coordinate p);

	public native void reverse();

	public native void normalize();

	public native double angle();

	public native Coordinate midPoint();

	public native double distance(LineSegment ls);

	public native double distance(Coordinate p);

	public native double distancePerpendicular(Coordinate p);

	public native Coordinate pointAlong(double segmentLengthFraction);

	public native Coordinate pointAlongOffset(double segmentLengthFraction, double offsetDistance);

	public native double projectionFactor(Coordinate p);

	public native double segmentFraction(Coordinate inputPt);

	public native Coordinate project(Coordinate p);

	public native LineSegment project(LineSegment seg);

	public native Coordinate closestPoint(Coordinate p);

	public native Coordinate[] closestPoints(LineSegment line);

	public native Coordinate intersection(LineSegment line);

	public native Coordinate lineIntersection(LineSegment line);

	public native LineString toGeometry(GeometryFactory geomFactory);

	public native boolean equalsTopo(LineSegment other);

}
