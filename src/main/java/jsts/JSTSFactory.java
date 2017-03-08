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
import java.util.List;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import jsinterop.annotations.JsConstructor;
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
import jsts.geom.MultiPolygon;
import jsts.geom.Point;
import jsts.geom.Polygon;
import jsts.geom.PrecisionModel;
import jsts.io.OL3Parser;
import jsts.io.WKTReader;
import jsts.io.WKTWriter;
import jsts.operation.polygonize.Polygonizer;

/**
 *
 * <p>
 * The <code>JSTSFactory</code> class represents a factory for the
 * generation/instantiation of objects from the <code>jsts.geom</code> package
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

	public final static String		DEFAULT_CS	= " ";
	public final static String		DEFAULT_TS	= ";";

	// workaround, not working when used multiple times????
	private final GeometryFactory	geometryFactory;
	private static JSTSFactory		INSTANCE;

	@JsMethod
	public static JSTSFactory getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new JSTSFactory();
		}
		return INSTANCE;
	}

	/**
	 * Constructs a(n) {@link JSTSFactory} object.
	 *
	 * @param geometryFactory
	 */
	@JsConstructor
	public JSTSFactory(GeometryFactory geometryFactory) {
		this.geometryFactory = geometryFactory;
	}

	/**
	 * Constructs a(n) {@link JSTSFactory} object with
	 * {@link PrecisionModel#FLOATING}
	 *
	 */
	@JsIgnore
	public JSTSFactory() {
		this(new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING)));
	}

	@JsMethod
	public WKTReader getWKTReader() {
		return new WKTReader(geometryFactory);
	}

	@JsMethod
	public WKTWriter getWKTWriter() {
		return new WKTWriter(geometryFactory);
	}

	@JsMethod
	public static OL3Parser getOL3Parser() {
		return new OL3Parser();
	}

	@JsMethod
	public static WKTReader createWKTReader(GeometryFactory geometryFactory) {
		return new WKTReader(geometryFactory);
	}

	@JsMethod
	public Point createPoint(ol.Coordinate coord) {
		return geometryFactory.createPoint(new Coordinate(coord.getX(), coord.getY(), coord.getZ()));
	}

	@JsMethod
	public LinearRing createLinearRing() {
		final Coordinate[] coords = new Coordinate[] {
				new Coordinate(260, 250), new Coordinate(810, 250), new Coordinate(810, 50), new Coordinate(260, 50),
				new Coordinate(260, 250) };
		final CoordinateSequenceFactory coordSeqFactory = geometryFactory.getCoordinateSequenceFactory();
		final CoordinateSequence coordSeq = coordSeqFactory.create(coords);
		final LinearRing linearRing = new LinearRing(coordSeq, geometryFactory);
		return linearRing;
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
	public Polygon createPolygon(@NotNull Coordinate[] coordinates) {

		// Isolate coordinates which forms a ring
		final ArrayList<Coordinate[]> coordListArray = new ArrayList<Coordinate[]>();
		// CoordinateList coordList = new CoordinateList();
		Coordinate coord;
		Coordinate firstCoord = coordinates[0];
		int size = 0;
		int start = 0;
		for (int i = 0; i < coordinates.length; i++) {
			coord = coordinates[i];
			if (i > start && coord.equals2D(firstCoord)) {
				size = i - start + 1;
				// Create new coordinate array
				final Coordinate[] coordList = new Coordinate[size];
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
			final List<LinearRing> holeList = new ArrayList<LinearRing>(coordListArray.size() - 1);
			for (int i = 0; i < coordListArray.size(); i++) {
				final Coordinate[] coords = coordListArray.get(i);
				final LinearRing ring = geometryFactory.createLinearRing(coords);
				if (CGAlgorithms.isCCW(coords)) {
					holeList.add(ring);
				} else {
					shell = ring;
				}
			}
			if (shell == null) {
				shell = getShell(holeList);
			}
			holes = new LinearRing[holeList.size()];
			holeList.toArray(holes);
		} else {
			final Coordinate[] coords = coordListArray.get(0);
			if (CGAlgorithms.isCCW(coords)) {
				// Maybe an error. We invert orientation
				shell = createInvertedLinearRing(coords);
			} else {
				shell = geometryFactory.createLinearRing(coords);
			}
		}
		return geometryFactory.createPolygon(shell, holes);
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
	private LinearRing getShell(@NotNull List<LinearRing> holeList) {
		for (int i = holeList.size() - 1; i >= 0; i--) {
			final LinearRing r1 = holeList.get(i);
			final Polygon p1 = createInvertedPolygon(r1.getCoordinateSequence());
			for (int j = holeList.size() - 1; j >= 0; j--) {
				final Polygon p2 = createInvertedPolygon(holeList.get(j).getCoordinateSequence());
				if (!p1.equals(p2) && p1.contains(p2)) {
					holeList.remove(i);
					return createInvertedLinearRing(r1.getCoordinateSequence());
				}
			}
		}
		return null;
	}

	private Polygon createInvertedPolygon(CoordinateSequence coordSeq) {
		final LinearRing shell = createInvertedLinearRing(coordSeq);
		return geometryFactory.createPolygon(shell);
	}

	private LinearRing createInvertedLinearRing(CoordinateSequence coordSeq) {
		return geometryFactory.createLinearRing(invertOrientation(coordSeq));
	}

	private LinearRing createInvertedLinearRing(Coordinate[] coords) {
		return geometryFactory
				.createLinearRing(invertOrientation(geometryFactory.getCoordinateSequenceFactory().create(coords)));
	}

	private CoordinateSequence invertOrientation(Coordinate[] coords) {
		final int size = coords.length;
		final Coordinate[] invertedCoords = new Coordinate[size];
		for (int i = 0; i < size; i++) {
			invertedCoords[i] = coords[size - 1 - i];
		}
		return geometryFactory.getCoordinateSequenceFactory().create(invertedCoords);
	}

	private CoordinateSequence invertOrientation(CoordinateSequence coordSeq) {
		return geometryFactory.getCoordinateSequenceFactory().create(invertOrientation(coordSeq.toCoordinateArray()));
	}

	@JsMethod
	public LinearRing createEmptyLinearRing() {
		final Coordinate[] coords = new Coordinate[] { new Coordinate(0, 0) };
		final LinearRing linearRing = geometryFactory.createLinearRing(coords);
		return linearRing;
	}

	/**
	 * 
	 * gets the holes of the passed geom
	 *
	 * @param geom
	 * @return
	 */
	@JsMethod
	public List<Geometry> getHoles(@NotNull Geometry geom) {
		final List<Geometry> holes = new ArrayList<Geometry>();
		if (geom instanceof Polygon) {
			addHoles(holes, (Polygon) geom);
		} else if (geom instanceof MultiPolygon) {
			final MultiPolygon multi = (MultiPolygon) geom;
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
	@JsIgnore
	private void addHoles(@NotNull List<Geometry> list, @NotNull Polygon polygon) {
		for (int i = 0; i < polygon.getNumInteriorRing(); i++) {
			final LineString hole = polygon.getInteriorRingN(i);
			list.add(getHole(hole));
		}
	}

	/**
	 * 
	 * creates a Polygon geom from the passed linestring
	 *
	 * @param lineString
	 * @return
	 * 
	 */
	@JsIgnore
	private Geometry getHole(@NotNull LineString lineString) {
		return geometryFactory.createPolygon(lineString.getCoordinateSequence());
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
	public Geometry fillHole(@NotNull Geometry geom, @NotNull Geometry island) {
		Geometry newGeom = null;
		if (geom instanceof MultiPolygon) {
			final MultiPolygon multiPoly = (MultiPolygon) geom;
			int iSourcePoly = -1;
			for (int iPoly = 0; iPoly < multiPoly.getNumGeometries(); iPoly++) {
				final Polygon polyN = (Polygon) multiPoly.getGeometryN(iPoly);
				for (int iRing = 0; iRing < polyN.getNumInteriorRing(); iRing++) {
					final LineString ringN = polyN.getInteriorRingN(iRing);
					final Polygon islandN = geometryFactory.createPolygon(ringN.getCoordinateSequence());
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
				final Polygon polyWithHole = (Polygon) multiPoly.getGeometryN(iSourcePoly);
				Geometry otherShellGeom = null;
				Geometry otherGeom = null;
				for (int iPoly = 0; iPoly < multiPoly.getNumGeometries(); iPoly++) {
					if (iPoly != iSourcePoly) {
						final Polygon polyN = (Polygon) multiPoly.getGeometryN(iPoly);
						final LineString ringN = polyN.getExteriorRing();
						final Polygon shell = geometryFactory.createPolygon(ringN.getCoordinateSequence());
						otherShellGeom = otherShellGeom == null ? shell : otherShellGeom.union(shell);
						otherGeom = otherGeom == null ? polyN : otherGeom.union(polyN);
					}
				}

				// Create new Geometry stepwise
				newGeom = polyWithHole.union(otherGeom);
				final Geometry diffGeom = island.difference(otherShellGeom);
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
	 * splits a geom by the passed line
	 *
	 * @param geom
	 * @param splitLine
	 * @return
	 */
	@NotNull
	@JsMethod
	public List<Geometry> split(@NotNull Geometry geom, @NotNull Geometry splitLine) {
		final List<Geometry> resultGeoms = new ArrayList<Geometry>();
		if (geom instanceof Polygon || geom instanceof MultiPolygon) {
			final Geometry mlString = geom.getBoundary();
			final Geometry unionLines = mlString.union(splitLine);
			final Polygonizer polygonizer = new Polygonizer();
			for (int i = 0; i < unionLines.getNumGeometries(); i++) {
				polygonizer.add(unionLines.getGeometryN(i));
			}
			final ArrayList<Geometry> polygons = polygonizer.getPolygons();
			final GeometryCollection polyGeom = geometryFactory.createGeometryCollection(polygons);

			for (int i = 0; i < polyGeom.getNumGeometries(); i++) {
				final Geometry intersectGeom = geom.intersection(polyGeom.getGeometryN(i));
				if ((intersectGeom instanceof Polygon || intersectGeom instanceof MultiPolygon) && !intersectGeom.isEmpty()) {
					resultGeoms.add(intersectGeom);
				}
			}
		} else if (geom instanceof LineString) {
			final Geometry lineGeom = geom.difference(splitLine);
			for (int i = 0; i < lineGeom.getNumGeometries(); i++) {
				resultGeoms.add(lineGeom.getGeometryN(i));
			}
		}
		return resultGeoms;
	}

	@SuppressWarnings("unchecked")
	@JsMethod
	public <G extends Geometry> G fromWKT(@NotNull String wkt) {
		return (G) getWKTReader().read(wkt);
	}

	@JsMethod
	public String toWKT(@NotNull Geometry geom) {
		return getWKTWriter().write(geom);
	}

	@JsMethod
	public static ol.geom.Geometry toOl3(@Nullable Geometry geom) {
		if (geom != null) {
			return getOL3Parser().write(geom);
		}
		return null;
	}

	@JsMethod
	public static Geometry fromOl3(@Nullable ol.geom.Geometry geom) {
		if (geom != null) {
			return getOL3Parser().read(geom);
		}
		return null;
	}

	@JsIgnore
	public static final native <T> T cast(Object o) /*-{
		return o;
	}-*/;
}
