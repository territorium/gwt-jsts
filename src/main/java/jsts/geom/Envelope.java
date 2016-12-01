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
 * The <code>Envelope</code> class
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
public class Envelope {

	/**
	 * Constructs a(n) {@link Envelope} object.
	 *
	 */
	@JsConstructor
	public Envelope() {}

	@JsConstructor
	public Envelope(Coordinate p) {}

	@JsConstructor
	public Envelope(Coordinate p1, Coordinate p2) {}

	@JsConstructor
	public Envelope(double x1, double x2, double y1, double y2) {}

	@JsConstructor
	public Envelope(Envelope env) {}

	public native static boolean intersects(Coordinate p1, Coordinate p2, Coordinate q);

	public native static boolean intersects(Coordinate p1, Coordinate p2, Coordinate q1, Coordinate q2);

	public native void init();

	public native void init(double x1, double x2, double y1, double y2);

	public native void init(Coordinate p1, Coordinate p2);

	public native void init(Coordinate p);

	public native void init(Envelope env);

	public native void setToNull();

	public native boolean isNull();

	public native double getWidth();

	public native double getHeight();

	public native double getMinX();

	public native double getMaxX();

	public native double getMinY();

	public native double getMaxY();

	public native double getArea();

	public native double minExtent();

	public native double maxExtent();

	public native void expandToInclude(Coordinate p);

	public native void expandBy(double distance);

	public native void expandBy(double deltaX, double deltaY);

	public native void expandToInclude(double x, double y);

	public native void expandToInclude(Envelope other);

	public native void translate(double transX, double transY);

	public native Coordinate centre();

	public native Envelope intersection(Envelope env);

	public native boolean intersects(Envelope other);

	public native boolean intersects(Coordinate p);

	public native boolean intersects(double x, double y);

	public native boolean contains(Envelope other);

	public native boolean contains(Coordinate p);

	public native boolean contains(double x, double y);

	public native boolean covers(double x, double y);

	public native boolean covers(Coordinate p);

	public native boolean covers(Envelope other);

	public native double distance(Envelope env);

	public native boolean equals(Object other);

	public native int compareTo(Object o);

}
