/*
 * Copyright (c) 2001-2016 Territorium Online Srl. All Rights Reserved.
 *
 * This file contains Original Code and/or Modifications of Original Code as defined in and that are subject to the
 * Territorium Online License Version 1.0. You may not use this file except in compliance with the License. Please
 * obtain a copy of the License at http://www.tol.info/license/ and read it before using this file.
 *
 * The Original Code and all software distributed under the License are distributed on an 'AS IS' basis, WITHOUT
 * WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, AND TERRITORIUM ONLINE HEREBY DISCLAIMS ALL SUCH WARRANTIES,
 * INCLUDING WITHOUT LIMITATION, ANY WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, QUIET ENJOYMENT OR
 * NON-INFRINGEMENT. Please see the License for the specific language governing rights and limitations under the
 * License.
 */
package jsts.io;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;
import jsts.geom.Coordinate;
import jsts.geom.Geometry;
import jsts.geom.GeometryCollection;
import jsts.geom.GeometryFactory;
import jsts.geom.LineString;
import jsts.geom.LinearRing;
import jsts.geom.MultiLineString;
import jsts.geom.MultiPoint;
import jsts.geom.MultiPolygon;
import jsts.geom.Point;
import jsts.geom.Polygon;

/**
 * OpenLayers 3 Geometry parser and writer
 */
@JsType(isNative = true)
public class OL3Parser {

	/**
	 * 
	 * Constructs a(n) {@link OL3Parser} object.
	 *
	 */
	@JsConstructor
	public OL3Parser() {};

	@JsConstructor
	public OL3Parser(GeometryFactory factory) {};

	/**
	 *
	 * converts a jts geometry into a openlayer geometry
	 *
	 * @param geom
	 * @return
	 */
	public native ol.geom.Geometry write(Geometry geom);

	public native ol.geom.Point convertToPoint(Coordinate coordinate);

	public native ol.geom.LineString convertToLineString(LineString lineString);

	public native ol.geom.LinearRing convertToLinearRing(LinearRing linearRing);

	public native ol.geom.Polygon convertToPolygon(Polygon polygon);

	public native ol.geom.MultiPoint convertToMultiPoint(MultiPoint multiPoint);

	public native ol.geom.MultiLineString convertToMultiLineString(MultiLineString multiLineString);

	public native ol.geom.MultiPolygon convertToMultiPolygon(MultiPolygon multiPolygon);

	public native ol.geom.GeometryCollection convertToCollection(GeometryCollection geometryCollection);

	/**
	 *
	 * converts a openlayer geometry into a jts geometry
	 *
	 * @param geom
	 * @return
	 */
	public native Geometry read(ol.geom.Geometry geom);

	public native Point convertFromPoint(ol.geom.Point point);

	public native LineString convertFromLineString(ol.geom.LineString lineString);

	public native LinearRing convertFromLinearRing(ol.geom.LinearRing linearRing);

	public native Polygon convertFromPolygon(ol.geom.Polygon polygon);

	public native MultiPoint convertFromMultiPoint(ol.geom.MultiPoint multiPoint);

	public native MultiLineString convertFromMultiLineString(ol.geom.MultiLineString multiLineString);

	public native MultiPolygon convertFromMultiPolygon(ol.geom.MultiPolygon multiPolygon);

	public native GeometryCollection convertFromCollection(ol.geom.GeometryCollection geometryCollection);

}
