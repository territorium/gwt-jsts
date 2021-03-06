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
package jsts.geom;

import org.junit.Test;

import jsts.GwtJSTSTestCase;
import tol.j2cl.elem.global.Array;

/**
 *
 * <p>
 * The <code>LinearRingTest</code> is a test case for {@link LinearRing}
 * </p>
 * <p>
 * Copyright: 2003 - 2016 <a href="http://www.teritoriumonline.com">Territorium Online Srl.</a>
 * </p>
 * <p>
 * Via Buozzi 12, 39100 Bolzano, Italy.
 * </p>
 * <p>
 * </p>
 * @author <a href="mailto:peter.zanetti@territoriumonline.com">Peter Zanetti</a>.
 * @version 1.0,30.12.2016
 * @since 1.0.
 */
public class LinearRingTest extends GwtJSTSTestCase {

	@Test
	public void testCreateLinearRingFromCoordinateArray() {
		final GeometryFactory geometryFactory = createGeometryFactory();
		final Array<Coordinate> points = createCoordinateArray();
		final LinearRing linearRing = geometryFactory.createLinearRing(points);
		assertNotNull(linearRing);
		assertFalse(linearRing.isEmpty());
		assertTrue(linearRing.isValid());
		assertTrue(linearRing.isClosed());
		assertTrue(linearRing.getCoordinates().length == 5);
	}

	@Test
	public void testCreateLinearRingFromCoordinateSequence() {
		final GeometryFactory geometryFactory = createGeometryFactory();
		final CoordinateSequenceFactory coordSeqFactory = geometryFactory.getCoordinateSequenceFactory();
		final Coordinate[] coords = createCoordinates();
		final CoordinateSequence coordSeq = coordSeqFactory.create(coords);
		final LinearRing linearRing = new LinearRing(coordSeq, geometryFactory);
		assertNotNull(linearRing);
		assertFalse(linearRing.isEmpty());
		assertTrue(linearRing.isValid());
		assertTrue(linearRing.isClosed());
		assertTrue(linearRing.getCoordinates().length == 5);
	}
}
