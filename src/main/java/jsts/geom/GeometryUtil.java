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

import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
import jsts.JSTSFactory;
import jsts.JsAPI;
import jsts.io.WKTReader;
import jsts.io.WKTWriter;
import jsts.operation.IsSimpleOp;
import ol.Coordinate;

/**
 *
 * <p>
 * The <code>GeometryUtil</code> contains useful methods for spatial operations and tests. In addition it exposes all
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
@JsType(name = "GeometryUtil", namespace = JsAPI.NS)
public class GeometryUtil {

	public final static double	DEFAULT_PRECISION	= 0.001d;
	private static double				PRECISION					= DEFAULT_PRECISION;
	private static int					SRID							= 0;
	// private static JSTSFactory JSTS_FACTORY;

	@JsIgnore
	public static JSTSFactory getJSTSFactory() {
		// IMPORTANT: not working when used multiple times!!!
		return new JSTSFactory(new GeometryFactory(new PrecisionModel(PRECISION), SRID));
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
	public static Geometry intersect(@NotNull Geometry first, Geometry... other) {
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
	public static boolean intersects(@NotNull Geometry first, @NotNull Geometry second, double bufferValue) {
		if (bufferValue > 0)
			return first.intersects(second.buffer(bufferValue, 0));

		return first.intersects(second);
	}

	@JsMethod
	public static boolean isLineSelfIntersecting(LineString line) {
		final IsSimpleOp op = new IsSimpleOp(line);
		return !op.isSimple();
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

	public static Point createPoint(Coordinate coord) {
		return getJSTSFactory().createPoint(coord);
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
