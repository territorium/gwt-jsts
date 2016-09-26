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
 * Supplies a set of utility methods for building Geometry objects from lists of
 * Coordinates. Note that the factory constructor methods do not change the
 * input coordinates in any way. In particular, they are not rounded to the
 * supplied PrecisionModel. It is assumed that input Coordinates meet the given
 * precision.
 */
@JsType(name = "GeometryFactory", namespace = "jsts.geom", isNative = true)
public class GeometryFactory {

	/**
	 * 
	 * Constructs a GeometryFactory that generates Geometries having a floating
	 * PrecisionModel and a spatial-reference ID of 0.
	 *
	 */
	@JsConstructor
	public GeometryFactory() {};

	/**
	 * 
	 * Constructs a GeometryFactory that generates Geometries having the given
	 * PrecisionModel and the default CoordinateSequence implementation.
	 *
	 * @param precisionModel
	 */
	@JsConstructor
	public GeometryFactory(PrecisionModel precisionModel) {};

	/**
	 * 
	 * Constructs a Polygon with the given exterior boundary.
	 *
	 * @param coordinates
	 * @return
	 */
	public native Polygon createPolygon(Coordinate[] coordinates);

	/**
	 * 
	 * Constructs a Polygon with the given exterior boundary.
	 *
	 * @param coordinates
	 * @return
	 */
	public native Polygon createPolygon(CoordinateSequence coordinates);

	/**
	 * 
	 * Creates a GeometryCollection using the given Geometries; a null or empty
	 * array will create an empty GeometryCollection.
	 *
	 * @param geometries
	 * @return
	 */
	public native GeometryCollection createGeometryCollection(Geometry[] geometries);

}
