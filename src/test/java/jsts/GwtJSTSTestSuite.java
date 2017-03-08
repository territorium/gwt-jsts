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

import com.google.gwt.junit.tools.GWTTestSuite;

import jsts.geom.CoordinateListTest;
import jsts.geom.CoordinateTest;
import jsts.geom.GeometryFactoryTest;
import jsts.geom.LineStringTest;
import jsts.geom.LinearRingTest;
import jsts.geom.PolygonTest;
import jsts.io.OL3ParserTest;
import junit.framework.TestSuite;

/**
 *
 * <p>
 * The <code>GwtJSTSTestSuite</code> class
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
public class GwtJSTSTestSuite extends GWTTestSuite {

	/**
	 * {@link #suite}
	 *
	 * @return
	 */
	public static TestSuite suite() {
		final TestSuite suite = new TestSuite("Tests for GwtJSTS-Wrapper");

		suite.addTestSuite(CoordinateListTest.class);
		suite.addTestSuite(CoordinateTest.class);
		suite.addTestSuite(LineStringTest.class);
		suite.addTestSuite(LinearRingTest.class);
		suite.addTestSuite(PolygonTest.class);
		suite.addTestSuite(GeometryFactoryTest.class);
		suite.addTestSuite(JSTSFactoryTest.class);
		suite.addTestSuite(JSTSUtilTest.class);
		suite.addTestSuite(OL3ParserTest.class);
		return suite;
	}

}
