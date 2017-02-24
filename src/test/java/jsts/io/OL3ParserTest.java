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
package jsts.io;

import org.junit.Test;

import jsts.GwtJSTSTestCase;
import jsts.JSTSFactory;
import jsts.geom.Geometry;
import jsts.geom.Polygon;

/**
 *
 * <p>
 * The <code>OL3ParserTest</code> is a test case for {@link OL3Parser}
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
 * @version 1.0.0,24.11.2016
 * @since 1.0.0
 */
public class OL3ParserTest extends GwtJSTSTestCase {

	@Test
	public void testCreateOL3Parser() {
		inject();
		OL3Parser parser = new OL3Parser();
		assertNotNull(parser);
	}

	@Test
	public void testWriteGeometry() {
		inject();
		OL3Parser parser = new OL3Parser();
		assertNotNull(parser);

		Geometry geometry = JSTSFactory.fromWKT(GwtJSTSTestCase.POLYGON_A);
		assertNotNull(geometry);
		Polygon jtsPolygon = JSTSFactory.cast(geometry);

		ol.geom.Geometry olGeom = parser.write(jtsPolygon);
		assertNotNull(olGeom);
		assertTrue(olGeom instanceof ol.geom.Polygon);

		ol.geom.Polygon ol3Polygon = (ol.geom.Polygon) olGeom;
		assertTrue(jtsPolygon.getCoordinates().length == ol3Polygon.getCoordinates()[0].length);
	}
}
