/*
 * Copyright (c) 2001-2017 Territorium Online Srl. All Rights Reserved.
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
package jsts.algorithm;

import org.junit.Test;

import jsts.GwtJSTSTestCase;
import jsts.geom.Coordinate;

/**
 *
 * <p>
 * The <code>CGAlgorithmsTest</code> class
 * </p>
 * <p>
 * Copyright: 2003 - 2017 <a href="http://www.teritoriumonline.com">Territorium Online Srl.</a>
 * </p>
 * <p>
 * Via Buozzi 12, 39100 Bolzano, Italy.
 * </p>
 * <p>
 * </p>
 * @author <a href="mailto:development@tol.info">Peter Zanetti</a>.
 * @version 4.0,08.03.2017
 * @since 4.0.
 */
public class CGAlgorithmsTest extends GwtJSTSTestCase {

	@Test
	public void testIsCCW() {
		final Coordinate ring[] = createPolygonCoordinates();
		assertFalse(CGAlgorithms.isCCW(ring));
	}
}
