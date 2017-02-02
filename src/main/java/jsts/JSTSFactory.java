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
package jsts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;
import jsts.algorithm.CGAlgorithms;
import jsts.geom.Coordinate;
import jsts.geom.CoordinateSequence;
import jsts.geom.CoordinateSequenceFactory;
import jsts.geom.Geometry;
import jsts.geom.GeometryCollection;
import jsts.geom.GeometryFactory;
import jsts.geom.LineString;
import jsts.geom.LinearRing;
import jsts.geom.MultiLineString;
import jsts.geom.MultiPoint;
import jsts.geom.MultiPolygon;
import jsts.geom.Polygon;
import jsts.geom.PrecisionModel;
import jsts.io.OL3Parser;
import jsts.io.WKTReader;
import jsts.operation.polygonize.Polygonizer;

/**
 *
 * <p>
 * The <code>JSTSFactory</code> class
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
@JsType(name = "JSTSFactory", namespace = JsAPI.NS, isNative = false)
public class JSTSFactory {

	public static final String			DEFAULT_CS	= " ";
	public static final String			DEFAULT_TS	= ";";
	public final static double			PRECISION		= 0.0001d;

	private static GeometryFactory	GEOM_FACTORY;
	// workaround, not working when used multiple times????
	private static GeometryFactory	GEOM_FACTORY_0001;
	private static WKTReader				WKT_READER;
	private static OL3Parser				OL3_PARSER;

	@JsMethod
	public static GeometryFactory getFloatingGeomFactory() {
		if (GEOM_FACTORY == null) {
			GEOM_FACTORY = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING));
		}
		return GEOM_FACTORY;
	}

	@JsMethod
	public static GeometryFactory getSingleGeomFactory() {
		if (GEOM_FACTORY_0001 == null) {
			GEOM_FACTORY_0001 = new GeometryFactory(new PrecisionModel(JSTSFactory.PRECISION));
		}
		return GEOM_FACTORY_0001;
	}

	@JsMethod
	public static WKTReader getWKTReader() {
		if (WKT_READER == null) {
			WKT_READER = new WKTReader(JSTSFactory.getFloatingGeomFactory());
		}
		return WKT_READER;
	}

	@JsMethod
	public static OL3Parser getOL3Parser() {
		if (OL3_PARSER == null) {
			OL3_PARSER = new OL3Parser();
		}
		return OL3_PARSER;
	}

	@JsMethod
	public static WKTReader createWKTReader(GeometryFactory geometryFactory) {
		return new WKTReader(geometryFactory);
	}

	/**
	 * <p>
	 * Creates a <code>Polygon</code> from the given coordinates considering
	 * holes.
	 * </p>
	 * <p>
	 * <b>IMPORTANT:</b>In case of a Polygon it follows the ArcSDE, Oracle Spatial
	 * specification and not those of Shapefiles. That means that the orientation
	 * of the exterior ring of a polygon is counterclockwise!!
	 * </p>
	 * 
	 * @param coordinates the array of <code>Coordinate</code>
	 * @return a <code>Polygon</code>
	 */
	@JsMethod
	public static Polygon createPolygon(@NotNull Coordinate[] coordinates) {

		// Isolate coordinates which forms a ring
		ArrayList<Coordinate[]> coordListArray = new ArrayList<Coordinate[]>();
		// CoordinateList coordList = new CoordinateList();
		Coordinate coord;
		Coordinate firstCoord = coordinates[0];
		int size = 0;
		int start = 0;
		for (int i = 0; i < coordinates.length; i++) {
			coord = coordinates[i];
			if ((i > start) && (coord.equals2D(firstCoord))) {
				size = (i - start) + 1;
				// Create new coordinate array
				Coordinate[] coordList = new Coordinate[size];
				// Copy coordinates
				System.arraycopy(coordinates, start, coordList, 0, size);
				// Add coordinates to array
				coordListArray.add(coordList);
				// Reset start point and start coordinate
				start = i + 1;
				if (start < coordinates.length) {
					firstCoord = coordinates[start];
				}
			}
		}

		LinearRing[] holes = null;
		LinearRing shell = null;
		if (coordListArray.size() > 1) {
			// Test if the ring is a hole or not
			List<LinearRing> holeList = new ArrayList<LinearRing>(coordListArray.size() - 1);
			for (int i = 0; i < coordListArray.size(); i++) {
				Coordinate[] coords = coordListArray.get(i);
				LinearRing ring = JSTSFactory.getFloatingGeomFactory().createLinearRing(coords);
				if (CGAlgorithms.isCCW(coords)) {
					holeList.add(ring);
				} else {
					shell = ring;
				}
			}
			if (shell == null) {
				shell = JSTSFactory.getShell(holeList);
			}
			holes = new LinearRing[holeList.size()];
			holeList.toArray(holes);
		} else {
			Coordinate[] coords = coordListArray.get(0);
			if (CGAlgorithms.isCCW(coords)) {
				// Maybe an error. We invert orientation
				shell = JSTSFactory.createInvertedLinearRing(coords);
			} else {
				shell = JSTSFactory.getFloatingGeomFactory().createLinearRing(coords);
			}
		}
		return JSTSFactory.getFloatingGeomFactory().createPolygon(shell, holes);
	}

	/**
	 * <p>
	 * Gets the outher shell of a {@link List} of CCW {@link LinearRing}'s
	 * representing holes and removes the shell from the holeList.
	 * </p>
	 * 
	 * @param holeList
	 * @return a CW {@link LinearRing} or null
	 */
	private static LinearRing getShell(@NotNull List<LinearRing> holeList) {
		for (int i = holeList.size() - 1; i >= 0; i--) {
			LinearRing r1 = holeList.get(i);
			Polygon p1 = JSTSFactory.createInvertedPolygon(r1.getCoordinateSequence());
			for (int j = holeList.size() - 1; j >= 0; j--) {
				Polygon p2 = JSTSFactory.createInvertedPolygon(holeList.get(j).getCoordinateSequence());
				if (!p1.equals(p2) && p1.contains(p2)) {
					holeList.remove(i);
					return JSTSFactory.createInvertedLinearRing(r1.getCoordinateSequence());
				}
			}
		}
		return null;
	}

	private static Polygon createInvertedPolygon(CoordinateSequence coordSeq) {
		LinearRing shell = JSTSFactory.createInvertedLinearRing(coordSeq);
		return JSTSFactory.getFloatingGeomFactory().createPolygon(shell);
	}

	private static LinearRing createInvertedLinearRing(CoordinateSequence coordSeq) {
		return JSTSFactory.getFloatingGeomFactory().createLinearRing(JSTSFactory.invertOrientation(coordSeq));
	}

	private static LinearRing createInvertedLinearRing(Coordinate[] coords) {
		GeometryFactory geometryFactory = JSTSFactory.getFloatingGeomFactory();
		return geometryFactory
				.createLinearRing(JSTSFactory.invertOrientation(geometryFactory.getCoordinateSequenceFactory().create(coords)));
	}

	private static CoordinateSequence invertOrientation(Coordinate[] coords) {
		int size = coords.length;
		Coordinate[] invertedCoords = new Coordinate[size];
		for (int i = 0; i < size; i++) {
			invertedCoords[i] = coords[size - 1 - i];
		}
		return JSTSFactory.getFloatingGeomFactory().getCoordinateSequenceFactory().create(invertedCoords);
	}

	private static CoordinateSequence invertOrientation(CoordinateSequence coordSeq) {
		return JSTSFactory.getFloatingGeomFactory().getCoordinateSequenceFactory()
				.create(JSTSFactory.invertOrientation(coordSeq.toCoordinateArray()));
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
	public static boolean intersects(@NotNull Geometry geom, @NotNull Geometry point, double bufferValue) {
		if (bufferValue > 0)
			point = point.buffer(bufferValue, 0);

		return geom.intersects(point);
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
		return (geom instanceof GeometryCollection || geom instanceof MultiLineString || geom instanceof MultiPoint
				|| geom instanceof MultiPolygon);
	}

	/**
	 * 
	 * creates the difference of the passed geometries
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	@JsIgnore
	public static Geometry difference(@NotNull Geometry first, @NotNull Geometry second) {
		return first.difference(second);
	}

	/**
	 * creates the intersection {@link Geometry} of the passed geometries
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	@JsIgnore
	public static Geometry intersect(@NotNull Geometry first, @NotNull Geometry second) {
		return first.intersection(second);
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

	/**
	 * 
	 * creates the union geom of the passed geometries
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	@JsIgnore
	public static Geometry union(@NotNull Geometry first, @NotNull Geometry second) {
		return first.union(second);
	}

	/**
	 * 
	 * gets the holes of the passed geom
	 *
	 * @param geom
	 * @return
	 */
	@JsMethod
	public static List<Geometry> getHoles(@NotNull Geometry geom) {
		List<Geometry> holes = new ArrayList<Geometry>();
		if (geom instanceof Polygon) {
			addHoles(holes, (Polygon) geom);
		} else if (geom instanceof MultiPolygon) {
			MultiPolygon multi = (MultiPolygon) geom;
			for (int i = 0; i < multi.getNumGeometries(); i++) {
				addHoles(holes, (Polygon) multi.getGeometryN(i));
			}
		}
		return holes;
	}

	/**
	 * 
	 * adds the polygons holes to the list
	 *
	 * @param list
	 * @param polygon
	 */
	private static void addHoles(@NotNull List<Geometry> list, @NotNull Polygon polygon) {
		for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
			LineString hole = polygon.getInteriorRingN(i);
			list.add(getHole(hole));
		}
	}

	/**
	 * 
	 * creates a Polygon geom from the passed linestring
	 *
	 * @param lineString
	 * @return
	 */
	private static Geometry getHole(@NotNull LineString lineString) {
		return getSingleGeomFactory().createPolygon(lineString.getCoordinateSequence());
	}

	/**
	 * 
	 * fills the passed geometries hole
	 *
	 * @param geom
	 * @param island
	 * @return
	 */

	@JsMethod
	public static Geometry fillHole(@NotNull Geometry geom, @NotNull Geometry island) {
		Geometry newGeom = null;
		if (geom instanceof MultiPolygon) {
			MultiPolygon multiPoly = (MultiPolygon) geom;
			int iSourcePoly = -1;
			for (int iPoly = 0; iPoly < multiPoly.getNumGeometries(); iPoly++) {
				Polygon polyN = (Polygon) multiPoly.getGeometryN(iPoly);
				for (int iRing = 0; iRing < polyN.getNumInteriorRing(); iRing++) {
					LineString ringN = polyN.getInteriorRingN(iRing);
					Polygon islandN = getSingleGeomFactory().createPolygon(ringN.getCoordinateSequence());
					if (island.equals(islandN)) {
						iSourcePoly = iPoly;
						break;
					}
				}
				if (iSourcePoly > -1) {
					break;
				}
			}
			if (iSourcePoly > -1) {
				Polygon polyWithHole = (Polygon) multiPoly.getGeometryN(iSourcePoly);
				Geometry otherShellGeom = null;
				Geometry otherGeom = null;
				for (int iPoly = 0; iPoly < multiPoly.getNumGeometries(); iPoly++) {
					if (iPoly != iSourcePoly) {
						Polygon polyN = (Polygon) multiPoly.getGeometryN(iPoly);
						LineString ringN = polyN.getExteriorRing();
						Polygon shell = getSingleGeomFactory().createPolygon(ringN.getCoordinateSequence());
						otherShellGeom = (otherShellGeom == null) ? shell : otherShellGeom.union(shell);
						otherGeom = (otherGeom == null) ? polyN : otherGeom.union(polyN);
					}
				}

				// Create new Geometry stepwise
				newGeom = polyWithHole.union(otherGeom);
				Geometry diffGeom = island.difference(otherShellGeom);
				if (diffGeom != null && !diffGeom.isEmpty()) {
					newGeom = newGeom.union(diffGeom);
				} else {
					newGeom = newGeom.union(island);
				}
			}
		} else {
			newGeom = geom.union(island);
		}
		return newGeom;
	}

	/**
	 * 
	 * adds the passed hole to the geom
	 *
	 * @param geom
	 * @param hole
	 * @return
	 */

	@JsMethod
	public static Geometry addHole(@NotNull Geometry geom, @NotNull Geometry hole) {
		return JSTSFactory.difference(geom, hole);
	}

	/**
	 * 
	 * creates the union geom of the passed geometries
	 *
	 * @param geoms
	 * @return
	 */

	@JsMethod
	public static Geometry union(@NotNull Collection<Geometry> geoms) {
		Geometry all = null;
		for (Geometry geom : geoms) {
			if (geom == null)
				continue;
			if (all == null)
				all = geom;
			else
				all = all.union(geom);
		}
		return all;
	}

	/**
	 * 
	 * creates the difference geom of the passed geometries
	 *
	 * @param geoms
	 * @return
	 */

	@JsMethod
	public static Geometry difference(@NotNull Collection<Geometry> geoms) {
		Geometry all = null;
		for (Geometry geom : geoms) {
			if (geom == null)
				continue;
			if (all == null)
				all = geom;
			else
				all = all.difference(geom);
		}
		return all;
	}

	/**
	 * 
	 * creates the intersect geom of the passed geometries
	 *
	 * @param geoms
	 * @return
	 */

	@JsMethod
	public static Geometry intersect(@NotNull Collection<Geometry> geoms) {
		Geometry all = null;
		for (Geometry geom : geoms) {
			if (geom == null)
				continue;
			if (all == null)
				all = geom;
			else
				all = all.intersection(geom);
		}
		return all;
	}

	/**
	 * 
	 * splits a geom by the passed line
	 *
	 * @param geom
	 * @param splitLine
	 * @return
	 */
	@NotNull
	@JsMethod
	public static List<Geometry> split(@NotNull Geometry geom, @NotNull Geometry splitLine) {
		List<Geometry> resultGeoms = new ArrayList<Geometry>();
		if (geom instanceof Polygon || geom instanceof MultiPolygon) {
			Geometry mlString = geom.getBoundary();
			Geometry unionLines = mlString.union(splitLine);
			Polygonizer polygonizer = new Polygonizer();
			for (int i = 0; i < unionLines.getNumGeometries(); i++) {
				polygonizer.add(unionLines.getGeometryN(i));
			}
			ArrayList<Geometry> polygons = polygonizer.getPolygons();
			GeometryCollection polyGeom = getSingleGeomFactory().createGeometryCollection(polygons);

			for (int i = 0; i < polyGeom.getNumGeometries(); i++) {
				Geometry intersectGeom = geom.intersection(polyGeom.getGeometryN(i));
				if ((intersectGeom instanceof Polygon || intersectGeom instanceof MultiPolygon) && !intersectGeom.isEmpty()) {
					resultGeoms.add(intersectGeom);
				}
			}
		} else if (geom instanceof LineString) {
			Geometry lineGeom = geom.difference(splitLine);
			for (int i = 0; i < lineGeom.getNumGeometries(); i++) {
				resultGeoms.add(lineGeom.getGeometryN(i));
			}
		}
		return resultGeoms;
	}

	@SuppressWarnings("unchecked")
	@JsMethod
	public static <G extends Geometry> G fromWKT(@NotNull String wkt) {
		return (G) JSTSFactory.getWKTReader().read(wkt);
	}

	@JsMethod
	public static ol.geom.Geometry toOl3(@NotNull Geometry geom) {
		return JSTSFactory.getOL3Parser().write(geom);
	}

	@JsMethod
	public static Geometry fromOl3(@NotNull ol.geom.Geometry geom) {
		return JSTSFactory.getOL3Parser().read(geom);
	}

	@JsMethod
	public static LinearRing createEmptyLinearRing() {
		GeometryFactory geometryFactory = getSingleGeomFactory();
		Coordinate[] coords = new Coordinate[] { new Coordinate(0, 0) };
		LinearRing linearRing = geometryFactory.createLinearRing(coords);
		return linearRing;
	}

	@JsMethod
	public static LinearRing createLinearRing() {
		GeometryFactory geometryFactory = getSingleGeomFactory();
		Coordinate[] coords = new Coordinate[] {
				new Coordinate(260, 250), new Coordinate(810, 250), new Coordinate(810, 50), new Coordinate(260, 50),
				new Coordinate(260, 250) };
		CoordinateSequenceFactory coordSeqFactory = geometryFactory.getCoordinateSequenceFactory();
		CoordinateSequence coordSeq = coordSeqFactory.create(coords);
		LinearRing linearRing = new LinearRing(coordSeq, geometryFactory);
		return linearRing;
	}

	public static final native <T> T cast(Object o) /*-{
		return o;
	}-*/;
}
