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

import jsts.algorithm.CGAlgorithms;
import jsts.geom.Coordinate;
import jsts.geom.CoordinateSequence;
import jsts.geom.Geometry;
import jsts.geom.GeometryCollection;
import jsts.geom.GeometryFactory;
import jsts.geom.LineString;
import jsts.geom.LinearRing;
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
 * The <code>Ol3GeometryUtil</code> class
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
 * @version 4.0.0,25.11.2016
 * @since 4.0.0
 */
public class Ol3GeometryUtil {

	public static final String			DEFAULT_CS				= " ";
	public static final String			DEFAULT_TS				= ";";

	private static GeometryFactory	GEOMETRY_FACTORY	= new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING));
	private static WKTReader				WKT_READER				= new WKTReader(Ol3GeometryUtil.GEOMETRY_FACTORY);

	// workaround, not working when used multiple times????
	// private static GeometryFactory geometryFactory = new GeometryFactory(new
	// PrecisionModel(0.0001));
	public static final OL3Parser		Ol3Parser					= new OL3Parser();

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
				LinearRing ring = Ol3GeometryUtil.GEOMETRY_FACTORY.createLinearRing(coords);
				if (CGAlgorithms.isCCW(coords)) {
					holeList.add(ring);
				} else {
					shell = ring;
				}
			}
			if (shell == null) {
				shell = Ol3GeometryUtil.getShell(holeList);
			}
			holes = new LinearRing[holeList.size()];
			holeList.toArray(holes);
		} else {
			Coordinate[] coords = coordListArray.get(0);
			if (CGAlgorithms.isCCW(coords)) {
				// Maybe an error. We invert orientation
				shell = Ol3GeometryUtil.createInvertedLinearRing(coords);
			} else {
				shell = Ol3GeometryUtil.GEOMETRY_FACTORY.createLinearRing(coords);
			}
		}
		return Ol3GeometryUtil.GEOMETRY_FACTORY.createPolygon(shell, holes);
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
			Polygon p1 = Ol3GeometryUtil.createInvertedPolygon(r1.getCoordinateSequence());
			for (int j = holeList.size() - 1; j >= 0; j--) {
				Polygon p2 = Ol3GeometryUtil.createInvertedPolygon(holeList.get(j).getCoordinateSequence());
				if (!p1.equals(p2) && p1.contains(p2)) {
					holeList.remove(i);
					return Ol3GeometryUtil.createInvertedLinearRing(r1.getCoordinateSequence());
				}
			}
		}
		return null;
	}

	private static Polygon createInvertedPolygon(CoordinateSequence coordSeq) {
		LinearRing shell = Ol3GeometryUtil.createInvertedLinearRing(coordSeq);
		return Ol3GeometryUtil.GEOMETRY_FACTORY.createPolygon(shell, null);
	}

	private static LinearRing createInvertedLinearRing(CoordinateSequence coordSeq) {
		return Ol3GeometryUtil.GEOMETRY_FACTORY.createLinearRing(Ol3GeometryUtil.invertOrientation(coordSeq));
	}

	private static LinearRing createInvertedLinearRing(Coordinate[] coords) {
		return Ol3GeometryUtil.GEOMETRY_FACTORY
				.createLinearRing(Ol3GeometryUtil.invertOrientation(new CoordinateArraySequence(coords)));
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
		return new CoordinateArraySequence(Ol3GeometryUtil.invertOrientation(coordSeq.toCoordinateArray()));
	}

	/**
	 * 
	 * checks if the passed geometry intersects the passed point
	 *
	 * @param geometry
	 * @param point
	 * @param bufferValue
	 * @return
	 */
	public static boolean intersects(Geometry geometry, Geometry point, double bufferValue) {
		if (bufferValue > 0)
			point = point.buffer(bufferValue, 0);

		return geometry.intersects(point);
	}

	/**
	 * 
	 * checks if the passed geometry is a multipart geometry
	 *
	 * @param geometry
	 * @return
	 */
	public static boolean isMultiPart(Geometry geometry) {
		if (geometry instanceof MultiPolygon)
			return true;

		if (geometry.CLASS_NAME == "jsts.geom.MultiPoint" || geometry.CLASS_NAME == "jsts.geom.MultiLineString"
				|| geometry.CLASS_NAME == "jsts.geom.MultiPolygon" || geometry.CLASS_NAME == "jsts.geom.GeometryCollection") {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * creates the difference of the passed geometries
	 *
	 * @param first
	 * @param second
	 * @return
	 */
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
	public static Geometry intersect(Geometry first, Geometry second) {
		if (first != null && second != null) {
			return first.intersection(second);
		}
		return null;

	}

	/**
	 * 
	 * creates a buffer of the passed geometry
	 *
	 * @param geometry
	 * @param distance
	 * @param quadrantSegments
	 * @return
	 */
	public static Geometry buffer(Geometry geometry, double distance, int quadrantSegments) {
		if (geometry != null) {
			return geometry.buffer(distance, quadrantSegments);
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
	 * creates the union geometry of the passed geometries
	 *
	 * @param firstGeometry
	 * @param secondGeometry
	 * @return
	 */
	public static Geometry union(Geometry firstGeometry, Geometry secondGeometry) {
		if (firstGeometry != null && secondGeometry != null) {
			return firstGeometry.union(secondGeometry);
		}
		return null;
	}

	/**
	 * 
	 * gets the holes of the passed geometry
	 *
	 * @param geometry
	 * @return
	 */
	public static List<Geometry> getHoles(Geometry geometry) {
		List<Geometry> holes = new ArrayList<Geometry>();
		if (geometry != null) {
			if (geometry instanceof Polygon) {
				addHoles(holes, (Polygon) geometry);
			} else if (geometry instanceof MultiPolygon) {
				MultiPolygon multi = (MultiPolygon) geometry;
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
	 * creates a Polygon geometry from the passed linestring
	 *
	 * @param lineString
	 * @return
	 */
	private static Geometry getHole(LineString lineString) {
		return new GeometryFactory(new PrecisionModel(0.0001)).createPolygon(lineString.getCoordinates());
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
		GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(0.0001));
		Geometry newGeom = null;
		if (geom != null && island != null) {
			if (geom instanceof MultiPolygon) {
				MultiPolygon multiPoly = (MultiPolygon) geom;
				int iSourcePoly = -1;
				for (int iPoly = 0; iPoly < multiPoly.getNumGeometries(); iPoly++) {
					Polygon polyN = (Polygon) multiPoly.getGeometryN(iPoly);
					for (int iRing = 0; iRing < polyN.getNumInteriorRing(); iRing++) {
						LineString ringN = polyN.getInteriorRingN(iRing);
						Polygon islandN = geometryFactory.createPolygon(ringN.getCoordinateSequence());
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
							Polygon shell = geometryFactory.createPolygon(ringN.getCoordinateSequence());
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
	 * adds the passed hole to the geometry
	 *
	 * @param geometry
	 * @param hole
	 * @return
	 */
	public static Geometry addHole(Geometry geometry, Geometry hole) {
		return Ol3GeometryUtil.difference(geometry, hole);
	}

	/**
	 * 
	 * creates the union geometry of the passed geometries
	 *
	 * @param geometries
	 * @return
	 */
	public static Geometry union(List<Geometry> geometries) {
		Geometry all = null;
		for (Geometry geometry : geometries) {
			if (geometry == null)
				continue;
			if (all == null)
				all = geometry;
			else
				all = all.union(geometry);
		}
		return all;
	}

	/**
	 * 
	 * creates the difference geometry of the passed geometries
	 *
	 * @param geometries
	 * @return
	 */
	public static Geometry difference(List<Geometry> geometries) {
		Geometry all = null;
		for (Geometry geometry : geometries) {
			if (geometry == null)
				continue;
			if (all == null)
				all = geometry;
			else
				all = all.difference(geometry);
		}
		return all;
	}

	/**
	 * 
	 * creates the intersect geometry of the passed geometries
	 *
	 * @param geometries
	 * @return
	 */
	public static Geometry intersect(List<Geometry> geometries) {
		Geometry all = null;
		for (Geometry geometry : geometries) {
			if (geometry == null)
				continue;
			if (all == null)
				all = geometry;
			else
				all = all.intersection(geometry);
		}
		return all;
	}

	/**
	 * 
	 * splits a geometry by the passed line
	 *
	 * @param geometry
	 * @param splitLine
	 * @return
	 */
	public static List<Geometry> split(Geometry geometry, Geometry splitLine) {
		List<Geometry> resultGeoms = new ArrayList<Geometry>();
		if (geometry instanceof Polygon || geometry instanceof MultiPolygon) {
			Geometry mlString = geometry.getBoundary();
			Geometry unionLines = mlString.union(splitLine);
			Polygonizer polygonizer = new Polygonizer();
			for (int i = 0; i < unionLines.getNumGeometries(); i++) {
				polygonizer.add(unionLines.getGeometryN(i));
			}
			Collection<Geometry> polygons = polygonizer.getPolygons();
			GeometryCollection polyGeom = new GeometryFactory(new PrecisionModel(0.0001))
					.createGeometryCollection((Geometry[]) polygons.toArray());

			for (int i = 0; i < polyGeom.getNumGeometries(); i++) {
				Geometry intersectGeom = geometry.intersection(polyGeom.getGeometryN(i));
				if ((intersectGeom instanceof Polygon || intersectGeom instanceof MultiPolygon)
						&& intersectGeom.getCoordinates().length > 0) {
					resultGeoms.add(intersectGeom);
				}
			}
		} else if (geometry instanceof LineString) {
			Geometry lineGeom = geometry.difference(splitLine);
			for (int i = 0; i < lineGeom.getNumGeometries(); i++) {
				resultGeoms.add(lineGeom.getGeometryN(i));
			}
		}
		return resultGeoms;
	}

	public static Geometry readWKT(String wkt) {
		return Ol3GeometryUtil.WKT_READER.read(wkt);
	}

}
