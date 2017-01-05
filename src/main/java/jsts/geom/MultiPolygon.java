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

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;

/**
 * Models a collection of Polygons. As per the OGC SFS specification, the
 * Polygons in a MultiPolygon may not overlap, and may only touch at single
 * points. This allows the topological point-set semantics to be well-defined.
 */
@JsType(isNative = true)
public class MultiPolygon extends GeometryCollection {

	/**
	 * 
	 * Constructs a(n) {@link MultiPolygon} object.
	 *
	 * @param polygons
	 * @param geometryFactory
	 */
	@JsConstructor
	public MultiPolygon(Polygon[] polygons, GeometryFactory geometryFactory) {}

}
