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
package jsts.algorithm;

import jsinterop.annotations.JsType;
import jsts.geom.Coordinate;
import jsts.geom.CoordinateSequence;

/**
 *
 * <p>
 * The <code>CGAlgorithms</code> class
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
 * @version 4.0.0,25.11.2016
 * @since 4.0.0
 */

@JsType(name = "CGAlgorithms", namespace = "jsts.algorithm", isNative = true)
public class CGAlgorithms {

	public native static double computeLength(CoordinateSequence pts);

	public native static int computeOrientation(Coordinate p1, Coordinate p2, Coordinate q);

	public native static double distanceLineLine(Coordinate A, Coordinate B, Coordinate C, Coordinate D);

	public native static double distancePointLine(Coordinate p, Coordinate A, Coordinate B);

	public native static double distancePointLine(Coordinate p, Coordinate[] line);

	public native static double distancePointLinePerpendicular(Coordinate p, Coordinate A, Coordinate B);

	public native static boolean isCCW(Coordinate[] ring);

	public native static boolean isOnLine(Coordinate p, Coordinate[] pt);

	public native static boolean isPointInRing(Coordinate p, Coordinate[] ring);

	public native static int locatePointInRing(Coordinate p, Coordinate[] ring);

	public native static int orientationIndex(Coordinate p1, Coordinate p2, Coordinate q);

	public native static double signedArea(Coordinate[] ring);

	public native static double signedArea(CoordinateSequence ring);

}
