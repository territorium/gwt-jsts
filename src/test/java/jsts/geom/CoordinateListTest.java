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
 * The <code>CoordinateListTest</code> is a test case for {@link CoordinateList}
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
public class CoordinateListTest extends GwtJSTSTestCase {

	@Test
	public void testCreateEmptyCoordinateList() {
		final CoordinateList coordList = new CoordinateList();
		assertNotNull(coordList);
		assertTrue(coordList.size() == 0);
	}

	@Test
	public void testCreateCoordinateList() {
		final Coordinate[] coords = new Coordinate[] { new Coordinate(0, 0), new Coordinate(0, 0) };
		final CoordinateList coordList = new CoordinateList(Array.of(coords));
		assertNotNull(coordList);
		assertTrue(coordList.size() != 0);
		assertTrue(coordList.size() == 2);
	}

	@Test
	public void testCreateCoordinateListNotRepeated() {
		final Array<Coordinate> coords = new Array<Coordinate>(new Coordinate(0, 0), new Coordinate(0, 0));
		final CoordinateList coordList = new CoordinateList(coords, false);
		assertNotNull(coordList);
		assertTrue(coordList.size() != 0);
		assertTrue(coordList.size() == 1);
	}

	@Test
	public void testCoordinateListCreate() {
		final Coordinate[] coords = createCoordinates();
		final CoordinateList coordList = CoordinateList.create(coords);
		assertNotNull(coordList);
		assertTrue(coordList.size() != 0);
		assertTrue(coordList.size() == 5);
	}

	@Test
	public void testCoordinateListAddArray() {
		final Array<Coordinate> coords = createCoordinateArray();
		final CoordinateList coordList = new CoordinateList();
		coordList.add(coords, true);
		assertTrue(coordList.size() != 0);
		assertTrue(coordList.size() == 5);
	}

	@Test
	public void testCoordinateListAdd() {
		final CoordinateList coordList = new CoordinateList();
		assertNotNull(coordList);
		coordList.add(new Coordinate(0, 0), true);
		coordList.add(new Coordinate(0, 0), true);
		assertTrue(coordList.size() != 0);
		assertTrue(coordList.size() == 2);
	}

	@Test
	public void testCoordinateListAddNotRepeated() {
		;
		final CoordinateList coordList = new CoordinateList();
		assertNotNull(coordList);
		coordList.add(new Coordinate(0, 0), false);
		coordList.add(new Coordinate(0, 0), false);
		assertTrue(coordList.size() != 0);
		assertTrue(coordList.size() == 1);
	}

	@Test
	public void testToCoordinateArray() {
		final CoordinateList coordList = createCoordinateList();
		final Coordinate[] coordsDest = coordList.toCoordinateArray();
		assertNotNull(coordsDest);
		assertTrue(coordsDest.length != 0);
		assertTrue(coordsDest.length == 5);
		assertTrue(coordsDest.length == coordList.size());
	}
}
