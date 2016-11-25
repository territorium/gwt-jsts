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
import jsts.geom.Coordinate;
import jsts.geom.Geometry;
import jsts.geom.GeometryFactory;
import jsts.geom.Polygon;
import jsts.geom.PrecisionModel;
import jsts.util.Ol3GeometryUtil;

/**
 *
 * <p>
 * The <code>OL3ParserTest</code> class
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

	public static final String	MULTIPOLYGON	= "MULTIPOLYGON(((1581583.5419439 4997507.10335156,1581574.98984152 4997515.27535796,1581581.07133663 4997535.61035827,1581581.26138646 4997543.21222623,1581588.10306247 4997552.71455566,1581599.12577581 4997552.52451319,1581604.82717494 4997543.97241081,1581614.70960403 4997516.98578065,1581615.46978861 4997502.16213702,1581612.04894324 4997493.80008447,1581606.34754411 4997489.99915048,1581589.05329689 4997491.70956949,1581583.92203988 4997497.98111074,1581583.5419439 4997507.10335156)))";
	public static final String	POLYGON				= "POLYGON ((1581583.5419439 4997507.10335156, 1581574.98984152 4997515.27535796, 1581581.07133663 4997535.61035827, 1581581.26138646 4997543.21222623, 1581588.10306247 4997552.71455566, 1581599.12577581 4997552.52451319, 1581604.82717494 4997543.97241081, 1581614.70960403 4997516.98578065, 1581615.46978861 4997502.16213702, 1581612.04894324 4997493.80008447, 1581606.34754411 4997489.99915048, 1581589.05329689 4997491.70956949, 1581583.92203988 4997497.98111074, 1581583.5419439 4997507.10335156))";

	@Test
	public void testReadWKTPolygon() {
		Geometry geometry = Ol3GeometryUtil.readWKT(OL3ParserTest.POLYGON);
		assertNotNull(geometry);
	}

	@Test
	public void testReadGeometry() {
		OL3Parser parser = new OL3Parser();
		assertNotNull(parser);

		GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel());
		assertNotNull(geometryFactory);
		// WKTReader wktReader = new WKTReader(geometryFactory);

		Coordinate coordinates[] = new Coordinate[5];
		coordinates[0] = new Coordinate(673265.5071993746, 5149915.308173436);
		coordinates[1] = new Coordinate(677659.9646521873, 5151922.225750311);
		coordinates[2] = new Coordinate(675134.0166674997, 5146247.493291561);
		coordinates[3] = new Coordinate(675134.0166674997, 5146247.493291561);
		coordinates[4] = new Coordinate(673265.5071993746, 5149915.308173436);
		Polygon jtsGeom = geometryFactory.createPolygon(coordinates);
		assertNotNull(jtsGeom);

		// ol.geom.Geometry olGeom = parser.write(jtsGeom);
		// assertNotNull(olGeom);
	}

}
