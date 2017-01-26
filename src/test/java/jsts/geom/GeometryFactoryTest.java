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

import org.junit.Test;

import jsts.GwtJSTSTestCase;
import tol.j2cl.elem.global.Array;

/**
 *
 * <p>
 * The <code>GeometryFactoryTest</code> is a test for {@link GeometryFactory}
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
 * @author <a href="mailto:peter.zanetti@territoriumonline.com">Peter
 *         Zanetti</a>.
 * @version 1.0,30.12.2016
 * @since 1.0.
 */
public class GeometryFactoryTest extends GwtJSTSTestCase {

	private Coordinate[] createPolygonCoordinates() {
		return new Coordinate[] {
				new Coordinate(260, 250), new Coordinate(810, 250), new Coordinate(810, 50), new Coordinate(260, 50),
				new Coordinate(260, 250) };
	}

	@Test
	public void testCreateGeometryFactory() {
		inject();
		GeometryFactory geometryFactory = createGeometryFactory();
		assertNotNull(geometryFactory);
	}

	@Test
	public void testGetCoordinateSequenceFactory() {
		inject();
		GeometryFactory geometryFactory = createGeometryFactory();
		assertNotNull(geometryFactory);

		CoordinateSequenceFactory coordSeqFactory = geometryFactory.getCoordinateSequenceFactory();
		assertNotNull(coordSeqFactory);
	}

	@Test
	public void testCreateEmptyCoordinateSequence() {
		inject();
		GeometryFactory geometryFactory = createGeometryFactory();
		assertNotNull(geometryFactory);

		CoordinateSequenceFactory coordSeqFactory = geometryFactory.getCoordinateSequenceFactory();
		assertNotNull(coordSeqFactory);

		CoordinateSequence coordSeq = coordSeqFactory.create(1, 2);
		assertNotNull(coordSeq);
		assertTrue(coordSeq.size() != 0);
		assertTrue(coordSeq.size() == 1);
		assertTrue(coordSeq.getDimension() == 2);
	}

	@Test
	public void testCreateCoordinateSequence() {
		inject();
		GeometryFactory geometryFactory = createGeometryFactory();
		assertNotNull(geometryFactory);

		CoordinateSequenceFactory coordSeqFactory = geometryFactory.getCoordinateSequenceFactory();
		assertNotNull(coordSeqFactory);

		Array<Coordinate> coords = createCoordinateArray();

		CoordinateSequence coordSeq = coordSeqFactory.create(coords);
		assertNotNull(coordSeq);
		assertTrue(coordSeq.size() == 5);
		assertTrue(coordSeq.getDimension() == 3);
		assertTrue(coordSeq.getX(0) == 260);
	}

	@Test
	public void testCreateCoordinateArraySequence() {
		inject();
		GeometryFactory geometryFactory = createGeometryFactory();
		assertNotNull(geometryFactory);

		CoordinateSequenceFactory coordSeqFactory = geometryFactory.getCoordinateSequenceFactory();
		assertNotNull(coordSeqFactory);

		CoordinateList coordList = createCoordinateList();
		Coordinate[] coords = coordList.toCoordinateArray();
		assertNotNull(coords);

		CoordinateSequence coordSeq = coordSeqFactory.create(coords);
		assertNotNull(coordSeq);
		assertTrue(coordSeq.size() == 5);
		assertTrue(coordSeq.getDimension() == 3);
		assertTrue(coordSeq.getX(0) == 260);
	}

	@Test
	public void testCreateLinearRing() {
		inject();
		GeometryFactory geometryFactory = createGeometryFactory();
		assertNotNull(geometryFactory);

		Coordinate coordinates[] = createPolygonCoordinates();
		LinearRing linearRing = geometryFactory.createLinearRing(coordinates);
		assertNotNull(linearRing);
	}

	@Test
	public void testCreateEmptyPolygon() {
		inject();
		GeometryFactory geometryFactory = createGeometryFactory();
		assertNotNull(geometryFactory);
		Polygon polygon = geometryFactory.createPolygon();
		assertNotNull(polygon);
	}

	@Test
	public void testCreatePolygonFromArray() {
		inject();
		GeometryFactory geometryFactory = createGeometryFactory();
		assertNotNull(geometryFactory);

		Coordinate coordinates[] = createPolygonCoordinates();
		Polygon polygon = geometryFactory.createPolygon(coordinates);
		assertNotNull(polygon);
	}

	@Test
	public void testCreatePolygonFromCoordinateSequence() {
		inject();
		GeometryFactory geometryFactory = createGeometryFactory();
		assertNotNull(geometryFactory);

		Coordinate coordinates[] = createPolygonCoordinates();
		CoordinateSequenceFactory coordSeqFactory = geometryFactory.getCoordinateSequenceFactory();
		assertNotNull(coordSeqFactory);

		CoordinateSequence coordSeq = coordSeqFactory.create(coordinates);
		assertNotNull(coordSeq);
		assertTrue(coordSeq.size() != 0);
		assertTrue(coordSeq.size() == 5);

		Polygon polygon = geometryFactory.createPolygon(coordSeq);
		assertNotNull(polygon);
	}

	@Test
	public void testCreatePolygonFromLinearRing() {
		inject();
		GeometryFactory geometryFactory = createGeometryFactory();
		assertNotNull(geometryFactory);

		Coordinate coordinates[] = createPolygonCoordinates();
		LinearRing linearRing = geometryFactory.createLinearRing(coordinates);
		assertNotNull(linearRing);

		// Polygon polygon = geometryFactory.createPolygon(linearRing);
		Polygon polygon = new Polygon(linearRing, null, geometryFactory);
		assertNotNull(polygon);
	}

}
