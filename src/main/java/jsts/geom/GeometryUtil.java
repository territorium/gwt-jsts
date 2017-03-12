/*
 * Copyright (c) 2001-2017 Territorium Online Srl. All Rights Reserved.
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
package jsts.geom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
import jsts.JSTSFactory;
import jsts.JsAPI;
import jsts.io.WKTReader;
import jsts.io.WKTWriter;
import jsts.operation.union.UnaryUnionOp;
import ol.Coordinate;
import ol.Extent;
import tol.j2cl.elem.global.Array;

/**
 *
 * <p>
 * The <code>GeometryUtil</code> contains useful methods for spatial operations and tests. In addition it exposes all
 * methods of the {@link JSTSFactory} and {@link GeometryFactory} in an static way using a certain precision. The
 * default precision is <b>0.001d</b>
 * </p>
 * <p>
 * Copyright: 2003 - 2017 <a href="http://www.teritoriumonline.com">Territorium Online Srl.</a>
 * </p>
 * <p>
 * Via Buozzi 12, 39100 Bolzano, Italy.
 * </p>
 * <p>
 * </p>
 * @author <a href="mailto:development@tol.info">Peter Zanetti</a>.
 * @version 1.0,07.03.2017
 * @since 1.0.
 */
@JsType(name = "GeometryUtil", namespace = JsAPI.NS)
public class GeometryUtil {

	public final static double	DEFAULT_PRECISION	= 0.001d;
	private static double				PRECISION					= DEFAULT_PRECISION;
	private static int					SRID							= 0;
	// private static JSTSFactory JSTS_FACTORY;
	// private static GeometryFactory GEOMETRY_FACTORY;

	@JsIgnore
	public static JSTSFactory getJSTSFactory() {
		// IMPORTANT: not working when used multiple times!!!
		return new JSTSFactory(new GeometryFactory(new PrecisionModel(PRECISION), SRID));
	}

	@JsIgnore
	public static GeometryFactory getGeometryFactory() {
		// IMPORTANT: not working when used multiple times!!!
		return new GeometryFactory(new PrecisionModel(PRECISION), SRID);
	}

	/**
	 * Inits this {@link GeometryUtil} with the given precision
	 *
	 * @param precision
	 */
	@JsIgnore
	public static void init(double precision) {
		GeometryUtil.PRECISION = precision;
	}

	/**
	 * Inits this {@link GeometryUtil} with the given spatial-reference ID
	 *
	 * @param SRID
	 */
	@JsIgnore
	public static void init(int SRID) {
		GeometryUtil.SRID = SRID;
	}

	/**
	 * Inits this {@link GeometryUtil} with the given precision and spatial-reference ID
	 *
	 * @param precision
	 * @param SRID
	 */
	@JsMethod
	public static void init(double precision, int SRID) {
		GeometryUtil.PRECISION = precision;
		GeometryUtil.SRID = SRID;
	}

	/**
	 * 
	 * adds the passed hole to the geom
	 *
	 * @param geometry
	 * @param hole
	 * @return
	 */
	@JsMethod
	public static Geometry addHole(@NotNull Geometry geometry, @NotNull Geometry hole) {
		return geometry.difference(hole);
	}

	/**
	 * 
	 * creates a buffer of the passed geom
	 *
	 * @param geom
	 * @param distance
	 * @param quadrantSegments
	 * @return
	 */
	@JsMethod
	public static Geometry buffer(@NotNull Geometry geom, double distance, int quadrantSegments) {
		return geom.buffer(distance, quadrantSegments);
	}

	/**
	 * {@link #createEnvelope}
	 *
	 * @param extent
	 * @return
	 */
	public static Envelope createEnvelope(Extent extent) {
		return new Envelope(extent.getLowerLeftX(), extent.getUpperRightX(), extent.getLowerLeftY(),
				extent.getUpperRightY());
	}

	// --------------------------------- Delegated methods --------------------------------------//

	@JsMethod
	public static Geometry difference(@NotNull Geometry geometry, @NotNull Geometry... geoms) {
		Geometry all = geometry.copy();
		for (final Geometry geom : geoms) {
			if (geom != null)
				all = all.difference(geom);
		}
		return all.buffer(0);
	}

	/**
	 * 
	 * creates the difference geom of the passed geometries
	 *
	 * @param geoms
	 * @return
	 */
	@JsMethod
	public static Geometry differenceAll(@NotNull Collection<Geometry> geoms) {
		Geometry all = null;
		for (final Geometry geom : geoms) {
			if (geom == null)
				continue;
			if (all == null)
				all = geom.copy();
			else
				all = all.difference(geom);
		}
		return all.buffer(0);
	}

	// @JsIgnore
	// public static Geometry intersect(@NotNull Geometry first, @NotNull Geometry
	// second) {
	// return first.intersection(second);
	// }

	/**
	 * creates the intersection {@link Geometry} of the passed geometries
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	@JsMethod
	public static Geometry intersect(@Nullable Geometry first, Geometry... other) {
		if (first == null)
			return first;

		Geometry all = first.copy();
		for (final Geometry geom : other) {
			if (geom != null)
				all = all.intersection(geom);
		}
		return all.buffer(0);
	}

	/**
	 * 
	 * creates the intersect geom of the passed geometries
	 *
	 * @param geoms
	 * @return
	 */
	@JsMethod
	public static Geometry intersectAll(@NotNull Collection<Geometry> geoms) {
		Geometry all = null;
		for (final Geometry geom : geoms) {
			if (geom == null)
				continue;
			if (all == null)
				all = geom.copy();
			else
				all = all.intersection(geom);
		}
		return all.buffer(0);
	}

	/**
	 * 
	 * checks if the passed geom intersects the passed point
	 *
	 * @param first
	 * @param second
	 * @param bufferValue
	 * @return
	 */
	@JsMethod
	public static boolean intersects(@Nullable Geometry first, @NotNull Geometry second, double bufferValue) {
		if (first == null)
			return false;

		if (bufferValue > 0)
			return first.intersects(second.buffer(bufferValue, 0));

		return first.intersects(second);
	}

	@JsIgnore
	public static boolean intersects(@Nullable Geometry first, @NotNull jsts.geom.Coordinate coord) {
		return first != null && first.intersects(createPoint(coord));
	}

	@JsMethod
	public static boolean isLineSelfIntersecting(LineString line) {
		// final IsSimpleOp op = new IsSimpleOp(line);
		// return !op.isSimple();
		final UnaryUnionOp unaryUnionOp = new UnaryUnionOp(line);
		final Geometry multiLine = unaryUnionOp.union();
		return !multiLine.isSimple();
	}

	/**
	 * 
	 * checks if the passed geom is a multipart geom
	 *
	 * @param geom
	 * @return
	 */
	@JsMethod
	public static boolean isMultiPart(@NotNull Geometry geom) {
		return geom instanceof GeometryCollection || geom instanceof MultiLineString || geom instanceof MultiPoint
				|| geom instanceof MultiPolygon;
	}

	/**
	 * 
	 * checks if geometryB is within geometryA
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	@JsMethod
	public static boolean isWithin(@NotNull Geometry first, @NotNull Geometry second) {
		return second.touches(first);
	}

	// @JsIgnore
	// public static Geometry union(@NotNull Geometry first, @NotNull Geometry
	// second) {
	// return first.union(second);
	// }

	/**
	 * 
	 * creates the union geom of the passed geometries
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	@JsMethod
	public static Geometry union(@NotNull Geometry first, @NotNull Geometry... other) {
		Geometry all = first.copy();
		for (final Geometry geom : other) {
			if (geom != null)
				all = all.union(geom);
		}
		return all.buffer(0);
	}

	/**
	 * 
	 * creates the union geom of the passed geometries
	 *
	 * @param geoms
	 * @return
	 */
	@JsMethod
	public static Geometry unionAll(@NotNull Collection<Geometry> geoms) {
		Geometry all = null;
		for (final Geometry geom : geoms) {
			if (geom == null)
				continue;
			if (all == null)
				all = geom.copy();
			else
				all = all.union(geom);
		}
		return all.buffer(0);
	}

	@JsMethod
	public static LinearRing createEmptyLinearRing() {
		return getJSTSFactory().createEmptyLinearRing();
	}

	@JsMethod
	public static Geometry fillHole(Geometry geom, Geometry island) {
		return getJSTSFactory().fillHole(geom, island);
	}

	@JsMethod
	public static List<Geometry> getHoles(Geometry geom) {
		return getJSTSFactory().getHoles(geom);
	}

	@JsMethod
	public static List<Geometry> split(Geometry geom, Geometry splitLine) {
		return getJSTSFactory().split(geom, splitLine);
	}

	public static WKTReader getWKTReader() {
		return getJSTSFactory().getWKTReader();
	}

	public static WKTWriter getWKTWriter() {
		return getJSTSFactory().getWKTWriter();
	}

	@JsIgnore
	public static Point createPoint(Coordinate coordinate) {
		return getJSTSFactory().createPoint(coordinate);
	}

	@JsIgnore
	public static Polygon createPolygon(jsts.geom.Coordinate... coordinates) {
		return getJSTSFactory().createPolygon(coordinates);
	}

	public static <G extends Geometry> G fromWKT(String wkt) {
		return getJSTSFactory().fromWKT(wkt);
	}

	public static String toWKT(Geometry geom) {
		return getJSTSFactory().toWKT(geom);
	}

	/**
	 * @param envelope
	 * @return
	 * @see jsts.geom.GeometryFactory#toGeometry(jsts.geom.Envelope)
	 */
	public static Geometry toGeometry(Envelope envelope) {
		return getGeometryFactory().toGeometry(envelope);
	}

	/**
	 * @param geometries
	 * @return
	 * @see jsts.geom.GeometryFactory#toGeometryArray(java.util.ArrayList)
	 */
	public static Geometry[] toGeometryArray(Collection<Geometry> geometries) {
		final Geometry[] geomArray = new Geometry[geometries.size()];
		return geometries.toArray(geomArray);
	}

	/**
	 * @param geometries
	 * @return
	 * @see jsts.geom.GeometryFactory#buildGeometry(java.util.Collection)
	 */
	public static Geometry buildGeometry(Collection<Geometry> geometries) {
		return getGeometryFactory().buildGeometry(geometries);
	}

	/**
	 * @param g
	 * @return
	 * @see jsts.geom.GeometryFactory#createGeometry(jsts.geom.Collection)
	 */
	public static Geometry createGeometry(Geometry g) {
		return getGeometryFactory().createGeometry(g);
	}

	/**
	 * @param geometries
	 * @return
	 * @see jsts.geom.GeometryFactory#createGeometryCollection(tol.j2cl.elem.global.Array)
	 */
	@JsIgnore
	public static GeometryCollection createGeometryCollection(Array<Geometry> geometries) {
		return getGeometryFactory().createGeometryCollection(geometries);
	}

	/**
	 * @param geometries
	 * @return
	 * @see jsts.geom.GeometryFactory#createGeometryCollection(jsts.geom.Geometry[])
	 */
	public static final GeometryCollection createGeometryCollection(Geometry... geometries) {
		return getGeometryFactory().createGeometryCollection(geometries);
	}

	/**
	 * @param geometries
	 * @return
	 * @see jsts.geom.GeometryFactory#createGeometryCollection(java.util.ArrayList)
	 */
	@JsIgnore
	public static final GeometryCollection createGeometryCollection(ArrayList<Geometry> geometries) {
		return getGeometryFactory().createGeometryCollection(toGeometryArray(geometries));
	}

	/**
	 * @param coordinates
	 * @return
	 * @see jsts.geom.GeometryFactory#createLinearRing(tol.j2cl.elem.global.Array)
	 */
	@JsIgnore
	public static LinearRing createLinearRing(Array<jsts.geom.Coordinate> coordinates) {
		return getGeometryFactory().createLinearRing(coordinates);
	}

	/**
	 * @param coordinates
	 * @return
	 * @see jsts.geom.GeometryFactory#createLinearRing(jsts.geom.Coordinate[])
	 */
	public static final LinearRing createLinearRing(jsts.geom.Coordinate... coordinates) {
		return getGeometryFactory().createLinearRing(coordinates);
	}

	/**
	 * @param invertOrientation
	 * @return
	 * @see jsts.geom.GeometryFactory#createLinearRing(jsts.geom.CoordinateSequence)
	 */
	@JsIgnore
	public static LinearRing createLinearRing(CoordinateSequence invertOrientation) {
		return getGeometryFactory().createLinearRing(invertOrientation);
	}

	/**
	 * @param coordinates
	 * @return
	 * @see jsts.geom.GeometryFactory#createLineString(tol.j2cl.elem.global.Array)
	 */
	@JsIgnore
	public static LineString createLineString(Array<jsts.geom.Coordinate> coordinates) {
		return getGeometryFactory().createLineString(coordinates);
	}

	/**
	 * @param coordinates
	 * @return
	 * @see jsts.geom.GeometryFactory#createLineString(jsts.geom.Coordinate[])
	 */
	@JsIgnore
	public static final LineString createLineString(jsts.geom.Coordinate... coordinates) {
		return getGeometryFactory().createLineString(coordinates);
	}

	@JsIgnore
	public static final LineString createLineString(List<jsts.geom.Coordinate> coordinates) {
		final jsts.geom.Coordinate[] coords = new jsts.geom.Coordinate[coordinates.size()];
		for (int i = 0; i < coords.length; i++) {
			coords[i] = coordinates.get(i);
		}
		return getGeometryFactory().createLineString(coords);
	}

	/**
	 * @param coordinates
	 * @return
	 * @see jsts.geom.GeometryFactory#createLineString(jsts.geom.CoordinateSequence)
	 */
	@JsIgnore
	public static LineString createLineString(CoordinateSequence coordinates) {
		return getGeometryFactory().createLineString(coordinates);
	}

	/**
	 * @param coordinate
	 * @return
	 * @see jsts.geom.GeometryFactory#createPoint(jsts.geom.Coordinate)
	 */
	public static Point createPoint(jsts.geom.Coordinate coordinate) {
		return getGeometryFactory().createPoint(coordinate);
	}

	/**
	 * @param coordinates
	 * @return
	 * @see jsts.geom.GeometryFactory#createPolygon(tol.j2cl.elem.global.Array)
	 */
	@JsIgnore
	public static Polygon createPolygon(Array<jsts.geom.Coordinate> coordinates) {
		return getGeometryFactory().createPolygon(coordinates);
	}

	/**
	 * @param coordinates
	 * @return
	 * @see jsts.geom.GeometryFactory#createPolygon(jsts.geom.CoordinateSequence)
	 */
	@JsIgnore
	public static Polygon createPolygon(CoordinateSequence coordinates) {
		return getGeometryFactory().createPolygon(coordinates);
	}

	/**
	 * @param shell
	 * @return
	 * @see jsts.geom.GeometryFactory#createPolygon(jsts.geom.LinearRing)
	 */
	@JsIgnore
	public static Polygon createPolygon(LinearRing shell) {
		return getGeometryFactory().createPolygon(shell);
	}

	/**
	 * @param shell
	 * @param holes
	 * @return
	 * @see jsts.geom.GeometryFactory#createPolygon(jsts.geom.LinearRing, tol.j2cl.elem.global.Array)
	 */
	@JsIgnore
	public static Polygon createPolygon(LinearRing shell, Array<LinearRing> holes) {
		return getGeometryFactory().createPolygon(shell, holes);
	}

	/**
	 * @param shell
	 * @param holes
	 * @return
	 * @see jsts.geom.GeometryFactory#createPolygon(jsts.geom.LinearRing, jsts.geom.LinearRing[])
	 */
	public static final Polygon createPolygon(LinearRing shell, LinearRing... holes) {
		return getGeometryFactory().createPolygon(shell, holes);
	}

	@JsIgnore
	public static final native <T> T cast(Object o) /*-{
		return o;
	}-*/;

}
