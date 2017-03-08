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

/**
 *
 * <p>
 * The <code>CoordinateTest</code> is a test case for {@link Coordinate}
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
public class CoordinateTest extends GwtJSTSTestCase {

	@Test
	public void testCreateEmptyCoordinate() {
		final Coordinate coord = new Coordinate();
		assertNotNull(coord);
	}

	@Test
	public void testCreateXYCoordinate() {
		final Coordinate coord = new Coordinate(0d, 1d);
		assertNotNull(coord);
		assertTrue(0d == coord.getX());
		assertTrue(1d == coord.getY());
	}

	@Test
	public void testCreateXYZCoordinate() {
		final Coordinate coord = new Coordinate(0d, 1d, 2d);
		assertNotNull(coord);
		assertTrue(2d == coord.getZ());
	}

	@Test
	public void testDistance() {
		final Coordinate coord1 = new Coordinate(0d, 0d);
		final Coordinate coord2 = new Coordinate(0d, 1d);
		assertNotNull(coord1);
		assertNotNull(coord2);
		assertTrue(1d == coord1.distance(coord2));
	}
}
