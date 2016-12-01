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
package jsts.operation.polygonize;

import java.util.Collection;

import jsinterop.annotations.JsType;
import jsts.geom.Geometry;

/**
 * Polygonizes a set of Geometrys which contain linework that represents the
 * edges of a planar graph. All types of Geometry are accepted as input; the
 * constituent linework is extracted as the edges to be polygonized. The
 * processed edges must be correctly noded; that is, they must only meet at
 * their endpoints. Polygonization will accept incorrectly noded input but will
 * not form polygons from non-noded edges, and reports them as errors. The
 * Polygonizer reports the follow kinds of errors:
 * 
 * Dangles - edges which have one or both ends which are not incident on another
 * edge endpoint Cut Edges - edges which are connected at both ends but which do
 * not form part of polygon Invalid Ring Lines - edges which form rings which
 * are invalid (e.g. the component lines contain a self-intersection)
 * Polygonization supports extracting only polygons which form a valid polygonal
 * geometry. The set of extracted polygons is guaranteed to be edge-disjoint.
 * This is useful for situations where it is known that the input lines form a
 * valid polygonal geometry.
 */
@JsType(isNative = true)
public class Polygonizer {

	/**
	 * 
	 * Add a Geometry to the edges to be polygonized.
	 *
	 * @param geometry
	 */
	public native void add(Geometry geometry);

	/**
	 * 
	 * Gets the list of polygons formed by the polygonization.
	 *
	 * @return
	 */
	public native Collection<Geometry> getPolygons();

}
