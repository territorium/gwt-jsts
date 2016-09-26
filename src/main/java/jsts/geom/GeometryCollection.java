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
 * Models a collection of Geometrys of arbitrary type and dimension.
 */
@JsType(name = "GeometryCollection", namespace = "jsts.geom", isNative = true)
public class GeometryCollection extends Geometry {

	// /**
	// *
	// * Returns an element Geometry from a GeometryCollection (or this, if the
	// * geometry is not a collection).
	// *
	// * @param n
	// * @return
	// */
	// public native Geometry getGeometryN(int n);
	//
	// /**
	// *
	// * Returns the number of Geometrys in a GeometryCollection (or 1, if the
	// * geometry is not a collection).
	// *
	// * @return
	// */
	// public native int getNumGeometries();

}
