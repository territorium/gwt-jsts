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

import java.util.ArrayList;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsType;
import tol.j2cl.elem.global.Array;

/**
 * Supplies a set of utility methods for building Geometry objects from lists of
 * Coordinates. Note that the factory constructor methods do not change the
 * input coordinates in any way. In particular, they are not rounded to the
 * supplied PrecisionModel. It is assumed that input Coordinates meet the given
 * precision.
 */
@JsType(isNative = true)
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
	 * Constructs a(n) {@link GeometryFactory} that generates Geometries having
	 * the given PrecisionModel and spatial-reference ID, and the default
	 * CoordinateSequence implementation..
	 *
	 * @param precisionModel
	 * @param SRID
	 */
	@JsConstructor
	public GeometryFactory(PrecisionModel precisionModel, int SRID) {};

	public native Geometry toGeometry(Envelope envelope);

	public native Geometry[] toGeometryArray(ArrayList<Geometry> geometries);

	public native Geometry buildGeometry(ArrayList<Geometry> geometries);

	public native Geometry createGeometry(Geometry g);

	public native GeometryCollection createGeometryCollection();

	public native GeometryCollection createGeometryCollection(Array<Geometry> geometries);

	@JsOverlay
	public final GeometryCollection createGeometryCollection(Geometry[] geometries) {
		return createGeometryCollection(Array.of(geometries));
	}

	@JsOverlay
	public final GeometryCollection createGeometryCollection(ArrayList<Geometry> geometries) {
		return createGeometryCollection(toGeometryArray(geometries));
	}

	public native LinearRing createLinearRing();

	public native LinearRing createLinearRing(Array<Coordinate> coordinates);

	@JsOverlay
	public final LinearRing createLinearRing(Coordinate[] coordinates) {
		return createLinearRing(Array.of(coordinates));
	}

	public native LinearRing createLinearRing(CoordinateSequence invertOrientation);

	public native LineString createLineString();

	public native LineString createLineString(Array<Coordinate> coordinates);

	@JsOverlay
	public final LineString createLineString(Coordinate[] coordinates) {
		return createLineString(Array.of(coordinates));
	}

	public native LineString createLineString(CoordinateSequence coordinates);

	// public native MultiLineString createMultiLineString();
	//
	// public native MultiLineString createMultiLineString(LineString[]
	// lineStrings);
	//
	// public native MultiPoint createMultiPoint();
	//
	// public native MultiPoint createMultiPoint(CoordinateSequence coordinates);
	//
	// public native MultiPoint createMultiPoint(Point[] point);
	//
	// public native MultiPoint createMultiPointFromCoords(Coordinate[]
	// coordinates);
	//
	// public native MultiPolygon createMultiPolygon();
	//
	// public native MultiPolygon createMultiPolygon(Polygon[] polygons);
	//
	public native Point createPoint();

	public native Point createPoint(Coordinate coordinate);

	public native Polygon createPolygon();

	public native Polygon createPolygon(Array<Coordinate> coordinates);

	@JsOverlay
	public final Polygon createPolygon(Coordinate[] coordinates) {
		return createPolygon(Array.of(coordinates));
	}

	public native Polygon createPolygon(CoordinateSequence coordinates);

	public native Polygon createPolygon(LinearRing shell);

	public native Polygon createPolygon(LinearRing shell, Array<LinearRing> holes);

	@JsOverlay
	public final Polygon createPolygon(LinearRing shell, LinearRing[] holes) {
		return createPolygon(shell, Array.of(holes));
	}

	public native PrecisionModel getPrecisionModel();

	public native CoordinateSequenceFactory getCoordinateSequenceFactory();

	public static native CoordinateSequenceFactory getDefaultCoordinateSequenceFactory();

	public native int getSRID();
}
