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
package jsts;

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
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
import jsts.geom.PrecisionModel;
import jsts.io.WKTReader;
import jsts.io.WKTWriter;
import jsts.operation.IsSimpleOp;
import ol.Coordinate;

/**
 *
 * <p>
 * The <code>JSTSUtil</code> contains useful methods for spatial operations and tests. In addition it exposes all
 * methods of the {@link JSTSFactory} in an static way using a certain precision. The default precision is <b>0.001d</b>
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
@JsType(name = "JSTSUtil", namespace = JsAPI.NS)
public class JSTSUtil {

	public static double				PRECISION	= 0.001d;
	private static JSTSFactory	JSTS_FACTORY;

	@JsIgnore
	public static JSTSFactory getJSTSFactory() {
		if (JSTS_FACTORY == null) {
			init(PRECISION);
		}
		return JSTS_FACTORY;
	}

	/**
	 * Inits this {@link JSTSUtil} with the given precision
	 *
	 * @param precision
	 */
	@JsIgnore
	public static void init(double precision) {
		JSTS_FACTORY = new JSTSFactory(new GeometryFactory(new PrecisionModel(precision)));
	}

	/**
	 * Inits this {@link JSTSUtil} with the given spatial-reference ID
	 *
	 * @param SRID
	 */
	@JsIgnore
	public static void init(int SRID) {
		JSTS_FACTORY = new JSTSFactory(new GeometryFactory(new PrecisionModel(PRECISION), SRID));
	}

	/**
	 * Inits this {@link JSTSUtil} with the given precision and spatial-reference ID
	 *
	 * @param precision
	 * @param SRID
	 */
	@JsMethod
	public static void init(double precision, int SRID) {
		JSTS_FACTORY = new JSTSFactory(new GeometryFactory(new PrecisionModel(precision), SRID));
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
	public static Geometry intersect(@NotNull Geometry geometry, Geometry... geoms) {
		Geometry all = geometry.copy();
		for (final Geometry geom : geoms) {
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
	 * @param geom
	 * @param point
	 * @param bufferValue
	 * @return
	 */
	@JsMethod
	public static boolean intersects(@NotNull Geometry geomA, @NotNull Geometry geomB, double bufferValue) {
		if (bufferValue > 0)
			return geomA.intersects(geomB.buffer(bufferValue, 0));

		return geomA.intersects(geomB);
	}

	@JsMethod
	public static boolean isLineSelfIntersecting(LineString line) {
		final IsSimpleOp op = new IsSimpleOp(line);
		return op.isSimple();
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
	 * @param geometryA
	 * @param geometryB
	 * @return
	 */
	@JsMethod
	public static boolean isWithin(@NotNull Geometry geometryA, @NotNull Geometry geometryB) {
		return geometryB.touches(geometryA);
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
	public static Geometry union(@NotNull Geometry geometry, @NotNull Geometry... geoms) {
		Geometry all = geometry.copy();
		for (final Geometry geom : geoms) {
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

	public static Point createPoint(Coordinate coord) {
		return getJSTSFactory().createPoint(coord);
	}

	public static LinearRing createLinearRing() {
		return getJSTSFactory().createLinearRing();
	}

	public static Polygon createPolygon(jsts.geom.Coordinate[] coordinates) {
		return getJSTSFactory().createPolygon(coordinates);
	}

	public static <G extends Geometry> G fromWKT(String wkt) {
		return getJSTSFactory().fromWKT(wkt);
	}

	public static String toWKT(Geometry geom) {
		return getJSTSFactory().toWKT(geom);
	}

}
