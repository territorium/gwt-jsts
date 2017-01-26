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

import org.junit.Test;

import jsts.GwtJSTSTestCase;
import jsts.geom.Geometry;
import jsts.geom.Polygon;

/**
 *
 * <p>
 * The <code>GeometryUtilTest</code> is a test case for {@link GeometryUtil}
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
 * @version 4.0.0,26.11.2016
 * @since 4.0.0
 */
public class GeometryUtilTest extends GwtJSTSTestCase {

	private static final String	SIMPLE_POLYGON_TO_SPLIT		= "POLYGON ((260 50, 260 420, 810 420, 810 50, 260 50))";
	private static final String	SIMPLE_SPLIT_POLYGON1			= "POLYGON ((260 250, 810 250, 810 50, 260 50, 260 250))";
	private static final String	SIMPLE_SPLIT_POLYGON2			= "POLYGON ((810 250, 260 250, 260 420, 810 420, 810 250))";

	private static final String	COMPLEX_POLYGON_TO_SPLIT	= "POLYGON ((260 50, 260 420, 810 420, 810 50, 260 50),(300 300, 420 300, 420 400, 300 400, 300 300),(590 70, 770 70, 770 350, 590 350, 590 70),(310 70, 480 70, 480 200, 310 200, 310 70))";
	private static final String	COMPLEX_SPLIT_POLYGON1		= "POLYGON ((260 250, 590 250, 590 70, 770 70, 770 250, 810 250, 810 50, 260 50, 260 250), (310 70, 480 70, 480 200, 310 200, 310 70))";
	private static final String	COMPLEX_SPLIT_POLYGON2		= "POLYGON ((590 250, 260 250, 260 420, 810 420, 810 250, 770 250, 770 350, 590 350, 590 250),(300 300, 420 300, 420 400, 300 400, 300 300))";

	private static final String	CLIP_POLYGON1							= "POLYGON ((627208.115 5157142.3447, 627230 5157350, 627404.7315 5157383.3978, 627503.9529 5157350, 627607.4352 5157342.0048, 627689.6124 5157265.3061, 627559.9551 5157149.0406, 627432.1239 5157169.1284, 627412.6449 5157336.5263, 627301.8579 5157276.8718, 627315.2497 5157148.4319, 627208.115 5157142.3447))";
	private static final String	CLIP_POLYGON2							= "POLYGON ((627299.1 5157453.4, 627369.2 5157434, 627431.4 5157424.8, 627490.9 5157403.6, 627547.8 5157311, 627595.5 5157280.6, 627644.4 5157250.1, 627673.5 5157236.9, 627647 5157230.3, 627610 5157232.9, 627599.7 5157228, 627582.2 5157219.7, 627575.6 5157165.5, 627616.6 5157129.8, 627635.2 5157115.2, 627599.4 5157115.2, 627546.5 5157115.2, 627506.8 5157115.2, 627448 5157097.3, 627419.5 5157107.3, 627400.4 5157109.1, 627306.1 5157386.4, 627299.1 5157453.4), (627369.9 5157392.7, 627380.9 5157343.2, 627412.5 5157311, 627467.5 5157330.2, 627428.3 5157385.8, 627369.9 5157392.7),(627514.2 5157256, 627473 5157246.4, 627440.7 5157229.2, 627484.6 5157174.9, 627561.6 5157197.6, 627514.2 5157256))";

	private static final String	SPLIT_LINE_TOUCHING				= "LINESTRING (260 250, 810 250, 810 250)";
	private static final String	SPLIT_LINE_INTERSECTING		= "LINESTRING (200 250, 900 250)";

	@Test
	public void testCreatePolygonFromWKT() {
		inject();
		Geometry geometry = GeometryUtil.fromWKT(GwtJSTSTestCase.POLYGON);
		assertNotNull(geometry);
		assertTrue(geometry instanceof Polygon);
	}

}