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
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Specifies the precision model of the Coordinates in a Geometry. In other
 * words, specifies the grid of allowable points for all Geometrys. The
 * makePrecise(Coordinate) method allows rounding a coordinate to a "precise"
 * value; that is, one whose precision is known exactly.
 * 
 * Coordinates are assumed to be precise in geometries. That is, the coordinates
 * are assumed to be rounded to the precision model given for the geometry. JTS
 * input routines automatically round coordinates to the precision model before
 * creating Geometries. All internal operations assume that coordinates are
 * rounded to the precision model. Constructive methods (such as boolean
 * operations) always round computed coordinates to the appropriate precision
 * model.
 * 
 * Currently three types of precision model are supported:
 * 
 * FLOATING - represents full double precision floating point. This is the
 * default precision model used in JTS FLOATING_SINGLE - represents single
 * precision floating point. FIXED - represents a model with a fixed number of
 * decimal places. A Fixed Precision Model is specified by a scale factor. The
 * scale factor specifies the size of the grid which numbers are rounded to.
 * Input coordinates are mapped to fixed coordinates according to the following
 * equations: jtsPt.x = round( (inputPt.x * scale ) / scale jtsPt.y = round(
 * (inputPt.y * scale ) / scale For example, to specify 3 decimal places of
 * precision, use a scale factor of 1000. To specify -3 decimal places of
 * precision (i.e. rounding to the nearest 1000), use a scale factor of 0.001.
 * Coordinates are represented internally as Java double-precision values. Since
 * Java uses the IEEE-394 floating point standard, this provides 53 bits of
 * precision. (Thus the maximum precisely representable integer is
 * 9,007,199,254,740,992 - or almost 16 decimal digits of precision).
 * 
 * JTS binary methods currently do not handle inputs which have different
 * precision models. The precision model of any constructed geometric value is
 * undefined.
 */
@JsType(name = "PrecisionModel", namespace = "jsts.geom", isNative = true)
public class PrecisionModel {

	@JsProperty(name = "FIXED")
	public static Type	FIXED;

	@JsProperty(name = "FLOATING")
	public static Type	FLOATING;

	@JsProperty(name = "FLOATING_SINGLE")
	public static Type	FLOATING_SINGLE;

	/**
	 * Constructs a(n) {@link PrecisionModel} Creates a PrecisionModel with a
	 * default precision of FLOATING..
	 *
	 */
	@JsConstructor
	public PrecisionModel() {}

	/**
	 * 
	 * Creates a PrecisionModel that specifies Fixed precision.
	 *
	 * @param scale
	 */
	@JsConstructor
	public PrecisionModel(double scale) {}

	/**
	 * Constructs a(n) {@link PrecisionModel} that specifies an explicit precision
	 * model type.
	 *
	 * @param modelType
	 */
	@JsConstructor
	public PrecisionModel(Type modelType) {}

	/**
	 * Returns the scale factor used to specify a fixed precision model.
	 *
	 * @return the scale factor for the fixed precision model
	 */
	public native double getScale();

	public native double setScale(double scale);
}
