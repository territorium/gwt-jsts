/*
 * Copyright (c) 2001-2017 Territorium Online Srl. All Rights Reserved.
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
package jsts.operation;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;
import jsts.algorithm.BoundaryNodeRule;
import jsts.geom.Coordinate;
import jsts.geom.Geometry;

@JsType(isNative = true)
public class IsSimpleOp {

	/**
	 * Creates a simplicity checker using the default SFS Mod-2 Boundary Node Rule
	 *
	 * @param geom the geometry to test
	 */
	@JsConstructor
	public IsSimpleOp(Geometry geom) {}

	/**
	 * Creates a simplicity checker using a given {@link BoundaryNodeRule}
	 *
	 * @param geom the geometry to test
	 * @param boundaryNodeRule the rule to use.
	 */
	@JsConstructor
	public IsSimpleOp(Geometry geom, BoundaryNodeRule boundaryNodeRule) {}

	/**
	 * Gets a coordinate for the location where the geometry fails to be simple.
	 *
	 * @return
	 */
	public native Coordinate getNonSimpleLocation();

	/**
	 * Tests whether the geometry is simple.
	 *
	 * @return
	 */
	public native boolean isSimple();

}
