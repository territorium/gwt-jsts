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
package jsts.geom;

import jsinterop.annotations.JsType;

/**
 * Represents a polygon with linear edges, which may include holes. The outer
 * boundary (shell) and inner boundaries (holes) of the polygon are represented
 * by LinearRings. The boundary rings of the polygon may have any orientation.
 * Polygons are closed, simple geometries by definition. The polygon model
 * conforms to the assertions specified in the OpenGIS Simple Features
 * Specification for SQL.
 * 
 * A Polygon is topologically valid if and only if:
 * 
 * the coordinates which define it are valid coordinates the linear rings for
 * the shell and holes are valid (i.e. are closed and do not self-intersect)
 * holes touch the shell or another hole at at most one point (which implies
 * that the rings of the shell and holes must not cross) the interior of the
 * polygon is connected, or equivalently no sequence of touching holes makes the
 * interior of the polygon disconnected (i.e. effectively split the polygon into
 * two pieces).
 */
@JsType(name = "Polygon", namespace = "jsts.geom", isNative = true)
public class Polygon extends Geometry {

	/**
	 * 
	 * get the interior ring at position n
	 *
	 * @param i
	 * @return
	 */
	public native LineString getInteriorRingN(int i);

	/**
	 * 
	 * get number of interior rings
	 *
	 * @return
	 */
	public native int getNumInteriorRing();

	/**
	 * 
	 * get exterior ring
	 *
	 * @return
	 */
	public native LineString getExteriorRing();

}
