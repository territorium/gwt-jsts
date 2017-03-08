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
package jsts.algorithm;

import jsinterop.annotations.JsType;
import jsts.operation.IsSimpleOp;

/**
 * An interface for rules which determine whether node points which are in
 * boundaries of {@link Lineal} geometry components are in the boundary of the
 * parent geometry collection. The SFS specifies a single kind of boundary node
 * rule, the {@link Mod2BoundaryNodeRule} rule. However, other kinds of Boundary
 * Node Rules are appropriate in specific situations (for instance, linear
 * network topology usually follows the {@link EndPointBoundaryNodeRule}.) Some
 * JTS operations allow the BoundaryNodeRule to be specified, and respect this
 * rule when computing the results of the operation.
 *
 * @author Martin Davis
 * @version 1.7
 *
 * @see RelateOp
 * @see IsSimpleOp
 * @see PointLocator
 */
@JsType(isNative = true)
public interface BoundaryNodeRule {

	/**
	 * Tests whether a point that lies in <tt>boundaryCount</tt> geometry
	 * component boundaries is considered to form part of the boundary of the
	 * parent geometry.
	 * 
	 * @param boundaryCount the number of component boundaries that this point
	 *          occurs in
	 * @return true if points in this number of boundaries lie in the parent
	 *         boundary
	 */
	boolean isInBoundary(int boundaryCount);
}
