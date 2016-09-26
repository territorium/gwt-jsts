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

import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * The {@link Geometry} class A representation of a planar, linear vector
 * geometry.
 * 
 */
@JsType(name = "Geometry", namespace = "jsts.geom", isNative = true)
public class Geometry {

	@JsProperty(name = "CLASS_NAME")
	public String CLASS_NAME;

	/**
	 * 
	 * Computes a buffer area around this geometry having the given width.
	 *
	 * @param distance
	 * @return
	 */
	public native Geometry buffer(double distance);

	/**
	 * 
	 * Computes a buffer area around this geometry having the given width and with
	 * a specified accuracy of approximation for circular arcs.
	 *
	 * @param distance
	 * @param quadrantSegments
	 * @return
	 */
	public native Geometry buffer(double distance, int quadrantSegments);

	/**
	 * 
	 * Computes a buffer area around this geometry having the given width and with
	 * a specified accuracy of approximation for circular arcs, and using a
	 * specified end cap style.
	 *
	 * @param distance
	 * @param quadrantSegments
	 * @param endCapStyle
	 * @return
	 */
	public native Geometry buffer(double distance, int quadrantSegments, int endCapStyle);

	/**
	 * 
	 * Creates and returns a full copy of this Geometry object (including all
	 * coordinates contained by it).
	 * 
	 * @return
	 */
	public native Geometry clone();

	/**
	 * 
	 * Returns whether this Geometry is greater than, equal to, or less than
	 * another Geometry.
	 *
	 * @param o
	 * @return
	 */
	public native int compareTo(Object o);

	/**
	 * 
	 * Computes a Geometry representing the closure of the point-set of the points
	 * contained in this Geometry that are not contained in the other Geometry.
	 *
	 * @param geom
	 * @return
	 */
	public native Geometry difference(Geometry geom);

	/**
	 * 
	 * Tests whether this geometry is topologically equal to the argument
	 * geometry.
	 *
	 * @param geom
	 * @return
	 */
	public native boolean equals(Geometry geom);

	/**
	 * 
	 * Tests whether this geometry is topologically equal to the argument geometry
	 * as defined by the SFS equals predicate.
	 *
	 * @param geom
	 * @return
	 */
	public native boolean equalsTopo(Geometry geom);

	/**
	 * 
	 * Returns the boundary, or an empty geometry of appropriate dimension if this
	 * Geometry is empty.
	 *
	 * @return
	 */
	public native Geometry getBoundary();

	/**
	 * 
	 * Returns a vertex of this Geometry (usually, but not necessarily, the first
	 * one).
	 *
	 * @return
	 */
	public native Coordinate getCoordinate();

	/**
	 * 
	 * Returns an array containing the values of all the vertices for this
	 * geometry.
	 *
	 * @return
	 */
	public native Coordinate[] getCoordinates();

	/**
	 * 
	 * Returns an element Geometry from a GeometryCollection (or this, if the
	 * geometry is not a collection).
	 *
	 * @param n
	 * @return
	 */
	public native Geometry getGeometryN(int n);

	/**
	 * 
	 * Returns the number of Geometrys in a GeometryCollection (or 1, if the
	 * geometry is not a collection).
	 *
	 * @return
	 */
	public native int getNumGeometries();

	/**
	 * 
	 * Computes a Geometry representing the point-set which is common to both this
	 * Geometry and the other Geometry.
	 *
	 * @param geom
	 * @return
	 */
	public native Geometry intersection(Geometry geom);

	/**
	 * 
	 * Tests whether this geometry intersects the argument geometry.
	 *
	 * @param geom
	 * @return
	 */
	public native boolean intersects(Geometry geom);

	/**
	 * 
	 * Tests whether the set of points covered by this Geometry is empty.
	 *
	 * @return
	 */
	public native boolean isEmpty();

	/**
	 * 
	 * Tests whether this Geometry is topologically valid, according to the OGC
	 * SFS specification.
	 *
	 * @return
	 */
	public native boolean isValid();

	/**
	 * 
	 * Tests whether this geometry touches the argument geometry.
	 *
	 * @param geom
	 * @return
	 */
	public native boolean touches(Geometry geom);

	/**
	 * 
	 * Computes a Geometry representing the point-set which is contained in both
	 * this Geometry and the other Geometry.
	 *
	 * @param geom
	 * @return
	 */
	public native Geometry union(Geometry geom);

}
