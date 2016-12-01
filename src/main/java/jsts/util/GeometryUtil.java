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
package jsts.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;
import jsts.algorithm.CGAlgorithms;
import jsts.geom.Coordinate;
import jsts.geom.CoordinateSequence;
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
import jsts.geom.impl.CoordinateArraySequence;
import jsts.io.OL3Parser;
import jsts.io.WKTReader;
import jsts.operation.polygonize.Polygonizer;

/**
 *
 * <p>
 * The <code>GeometryUtil</code> class
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
@JsType()
public class GeometryUtil {

	public static final String			DEFAULT_CS	= " ";
	public static final String			DEFAULT_TS	= ";";
	public final static double			PRECISION		= 0.0001d;

	private static GeometryFactory	GEOM_FACTORY;
	// workaround, not working when used multiple times????
	private static GeometryFactory	GEOM_FACTORY_0001;
	private static WKTReader				WKT_READER;
	private static OL3Parser				OL3_PARSER;

	public static GeometryFactory getFloatingGeomFactory() {
		if (GEOM_FACTORY == null) {
			GEOM_FACTORY = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING));
		}
		return GEOM_FACTORY;
	}

	public static GeometryFactory getSingleGeomFactory() {
		if (GEOM_FACTORY_0001 == null) {
			GEOM_FACTORY_0001 = new GeometryFactory(new PrecisionModel(GeometryUtil.PRECISION));
		}
		return GEOM_FACTORY_0001;
	}

	public static WKTReader getWKTReader() {
		if (WKT_READER == null) {
			WKT_READER = new WKTReader(GeometryUtil.getFloatingGeomFactory());
		}
		return WKT_READER;
	}

	public static OL3Parser getOL3Parser() {
		if (OL3_PARSER == null) {
			OL3_PARSER = new OL3Parser();
		}
		return OL3_PARSER;
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
	public static Polygon createPolygon(Coordinate[] coordinates) {

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
				LinearRing ring = GeometryUtil.getFloatingGeomFactory().createLinearRing(coords);
				if (CGAlgorithms.isCCW(coords)) {
					holeList.add(ring);
				} else {
					shell = ring;
				}
			}
			if (shell == null) {
				shell = GeometryUtil.getShell(holeList);
			}
			holes = new LinearRing[holeList.size()];
			holeList.toArray(holes);
		} else {
			Coordinate[] coords = coordListArray.get(0);
			if (CGAlgorithms.isCCW(coords)) {
				// Maybe an error. We invert orientation
				shell = GeometryUtil.createInvertedLinearRing(coords);
			} else {
				shell = GeometryUtil.getFloatingGeomFactory().createLinearRing(coords);
			}
		}
		return GeometryUtil.getFloatingGeomFactory().createPolygon(shell, holes);
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
	private static LinearRing getShell(List<LinearRing> holeList) {
		for (int i = holeList.size() - 1; i >= 0; i--) {
			LinearRing r1 = holeList.get(i);
			Polygon p1 = GeometryUtil.createInvertedPolygon(r1.getCoordinateSequence());
			for (int j = holeList.size() - 1; j >= 0; j--) {
				Polygon p2 = GeometryUtil.createInvertedPolygon(holeList.get(j).getCoordinateSequence());
				if (!p1.equals(p2) && p1.contains(p2)) {
					holeList.remove(i);
					return GeometryUtil.createInvertedLinearRing(r1.getCoordinateSequence());
				}
			}
		}
		return null;
	}

	private static Polygon createInvertedPolygon(CoordinateSequence coordSeq) {
		LinearRing shell = GeometryUtil.createInvertedLinearRing(coordSeq);
		return GeometryUtil.getFloatingGeomFactory().createPolygon(shell, null);
	}

	private static LinearRing createInvertedLinearRing(CoordinateSequence coordSeq) {
		return GeometryUtil.getFloatingGeomFactory().createLinearRing(GeometryUtil.invertOrientation(coordSeq));
	}

	private static LinearRing createInvertedLinearRing(Coordinate[] coords) {
		return GeometryUtil.getFloatingGeomFactory()
				.createLinearRing(GeometryUtil.invertOrientation(new CoordinateArraySequence(coords)));
	}

	private static CoordinateSequence invertOrientation(Coordinate[] coords) {
		int size = coords.length;
		Coordinate[] invertedCoords = new Coordinate[size];
		for (int i = 0; i < size; i++) {
			invertedCoords[i] = coords[size - 1 - i];
		}
		return new CoordinateArraySequence(invertedCoords);
	}

	private static CoordinateSequence invertOrientation(CoordinateSequence coordSeq) {
		return new CoordinateArraySequence(GeometryUtil.invertOrientation(coordSeq.toCoordinateArray()));
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
	public static boolean intersects(Geometry geom, Geometry point, double bufferValue) {
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
	public static boolean isMultiPart(Geometry geom) {
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
	public static Geometry difference(Geometry first, Geometry second) {
		if (first != null && second != null) {
			return first.difference(second);
		}
		return null;
	}

	/**
	 * creates the intersection {@link Geometry} of the passed geometries
	 *
	 * @param first
	 * @param second
	 * @return
	 */
	@JsIgnore
	public static Geometry intersect(Geometry first, Geometry second) {
		if (first != null && second != null) {
			return first.intersection(second);
		}
		return null;

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
	public static Geometry buffer(Geometry geom, double distance, int quadrantSegments) {
		if (geom != null) {
			return geom.buffer(distance, quadrantSegments);
		}
		return null;
	}

	/**
	 * 
	 * checks if geometryB is within geometryA
	 *
	 * @param geometryA
	 * @param geometryB
	 * @return
	 */
	public static boolean isWithin(Geometry geometryA, Geometry geometryB) {
		return geometryB.touches(geometryA);
	}

	/**
	 * 
	 * creates the union geom of the passed geometries
	 *
	 * @param firstGeometry
	 * @param secondGeometry
	 * @return
	 */
	@JsIgnore
	public static Geometry union(Geometry firstGeometry, Geometry secondGeometry) {
		if (firstGeometry != null && secondGeometry != null) {
			return firstGeometry.union(secondGeometry);
		}
		return null;
	}

	/**
	 * 
	 * gets the holes of the passed geom
	 *
	 * @param geom
	 * @return
	 */
	public static List<Geometry> getHoles(Geometry geom) {
		List<Geometry> holes = new ArrayList<Geometry>();
		if (geom != null) {
			if (geom instanceof Polygon) {
				addHoles(holes, (Polygon) geom);
			} else if (geom instanceof MultiPolygon) {
				MultiPolygon multi = (MultiPolygon) geom;
				for (int i = 0; i < multi.getNumGeometries(); i++) {
					addHoles(holes, (Polygon) multi.getGeometryN(i));
				}
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
	private static void addHoles(List<Geometry> list, Polygon polygon) {
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
	private static Geometry getHole(LineString lineString) {
		return getSingleGeomFactory().createPolygon(lineString.getCoordinates());
	}

	/**
	 * 
	 * fills the passed geometries hole
	 *
	 * @param geom
	 * @param island
	 * @return
	 */
	public static Geometry fillHole(Geometry geom, Geometry island) {
		Geometry newGeom = null;
		if (geom != null && island != null) {
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
	public static Geometry addHole(Geometry geom, Geometry hole) {
		return GeometryUtil.difference(geom, hole);
	}

	/**
	 * 
	 * creates the union geom of the passed geometries
	 *
	 * @param geometries
	 * @return
	 */
	public static Geometry union(List<Geometry> geometries) {
		Geometry all = null;
		for (Geometry geom : geometries) {
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
	 * @param geometries
	 * @return
	 */
	public static Geometry difference(List<Geometry> geometries) {
		Geometry all = null;
		for (Geometry geom : geometries) {
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
	 * @param geometries
	 * @return
	 */
	public static Geometry intersect(List<Geometry> geometries) {
		Geometry all = null;
		for (Geometry geom : geometries) {
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
	public static List<Geometry> split(Geometry geom, Geometry splitLine) {
		List<Geometry> resultGeoms = new ArrayList<Geometry>();
		if (geom instanceof Polygon || geom instanceof MultiPolygon) {
			Geometry mlString = geom.getBoundary();
			Geometry unionLines = mlString.union(splitLine);
			Polygonizer polygonizer = new Polygonizer();
			for (int i = 0; i < unionLines.getNumGeometries(); i++) {
				polygonizer.add(unionLines.getGeometryN(i));
			}
			Collection<Geometry> polygons = polygonizer.getPolygons();
			GeometryCollection polyGeom = getSingleGeomFactory().createGeometryCollection((Geometry[]) polygons.toArray());

			for (int i = 0; i < polyGeom.getNumGeometries(); i++) {
				Geometry intersectGeom = geom.intersection(polyGeom.getGeometryN(i));
				if ((intersectGeom instanceof Polygon || intersectGeom instanceof MultiPolygon)
						&& intersectGeom.getCoordinates().length > 0) {
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

	public static Geometry fromWKT(String wkt) {
		return GeometryUtil.WKT_READER.read(wkt);
	}

	public static ol.geom.Geometry toOl3(Geometry geom) {
		return GeometryUtil.getOL3Parser().write(geom);
	}

	public static Geometry fromOl3(ol.geom.Geometry geom) {
		return GeometryUtil.getOL3Parser().read(geom);
	}
}
