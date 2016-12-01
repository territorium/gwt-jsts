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
package jsts.geom.impl;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;
import jsts.geom.Coordinate;
import jsts.geom.CoordinateSequence;

/**
 *
 * <p>
 * The <code>CoordinateArraySequence</code> class
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
public class CoordinateArraySequence implements CoordinateSequence {

	@JsConstructor
	public CoordinateArraySequence(Coordinate[] coordinates) {}

	@JsConstructor
	public CoordinateArraySequence(Coordinate[] coordinates, int dimension) {}

	@JsConstructor
	public CoordinateArraySequence(CoordinateSequence coordSeq) {}

	@JsConstructor
	public CoordinateArraySequence(int size) {}

	@JsConstructor
	public CoordinateArraySequence(int size, int dimension) {}

	@Override
	public native Coordinate[] toCoordinateArray();

}
