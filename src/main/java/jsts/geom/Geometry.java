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
 * The {@link Geometry} class A representation of a planar, linear vector
 * geometry.
 * 
 */
@JsType(isNative = true)
public abstract class Geometry {

	/**
	 * Constructs a(n) {@link Geometry} object.
	 *
	 * @param factory
	 */
	@JsConstructor
	public Geometry(GeometryFactory factory) {}

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

	public native boolean contains(Geometry g);

	/**
	 * 
	 * Creates and returns a full copy of this Geometry object (including all
	 * coordinates contained by it).
	 * 
	 * @return
	 */
	public native Geometry copy();

	public native boolean coveredBy(Geometry g);

	public native boolean covers(Geometry g);

	public native boolean crosses(Geometry g);

	/**
	 * 
	 * Computes a Geometry representing the closure of the point-set of the points
	 * contained in this Geometry that are not contained in the other Geometry.
	 *
	 * @param geom
	 * @return
	 */
	public native Geometry difference(Geometry geom);

	public native boolean disjoint(Geometry g);

	/**
	 * 
	 * Tests whether this geometry is topologically equal to the argument
	 * geometry.
	 *
	 * @param geom
	 * @return
	 */
	public native boolean equals(Geometry geom);

	public native boolean equalsExact(Geometry other);

	public native boolean equalsExact(Geometry other, double tolerance);

	public native boolean equalsNorm(Geometry g);

	/**
	 * 
	 * Tests whether this geometry is topologically equal to the argument geometry
	 * as defined by the SFS equals predicate.
	 *
	 * @param geom
	 * @return
	 */
	public native boolean equalsTopo(Geometry geom);

	public native void geometryChanged();

	/**
	 * 
	 * Returns the boundary, or an empty geometry of appropriate dimension if this
	 * Geometry is empty.
	 *
	 * @return
	 */
	public native Geometry getBoundary();

	public native int getBoundaryDimension();

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

	public native Geometry getEnvelope();

	public native Envelope getEnvelopeInternal();

	public native GeometryFactory getFactory();

	/**
	 * 
	 * Returns an element Geometry from a GeometryCollection (or this, if the
	 * geometry is not a collection).
	 *
	 * @param n
	 * @return
	 */
	public native Geometry getGeometryN(int n);

	public native String getGeometryType();

	/**
	 * 
	 * Returns the number of Geometrys in a GeometryCollection (or 1, if the
	 * geometry is not a collection).
	 *
	 * @return
	 */
	public native int getNumGeometries();

	public native int getSortIndex();

	public native int getSRID();

	public native Object getUserData();

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

	protected native boolean isEquivalentClass(Geometry other);

	/**
	 * Tests whether this is an instance of a general GeometryCollection, rather
	 * than a homogeneous subclass.
	 *
	 * @return true if this is a hetereogeneous GeometryCollection
	 */
	public native boolean isGeometryCollection();

	public native boolean isGeometryCollectionOrDerived();

	/**
	 * 
	 * Tests whether this Geometry is topologically valid, according to the OGC
	 * SFS specification.
	 *
	 * @return
	 */
	public native boolean isValid();

	public native boolean overlaps(Geometry g);

	public native IntersectionMatrix relate(Geometry g);

	/**
	 * <p>
	 * Tests whether the elements in the DE-9IM IntersectionMatrix for the two
	 * Geometrys match the elements in intersectionPattern. The pattern is a
	 * 9-character string, with symbols drawn from the following set:
	 * 
	 * <pre>
	 *   0 (dimension 0)
	 *   1 (dimension 1)
	 *   2 (dimension 2)
	 *   T ( matches 0, 1 or 2)
	 *   F ( matches FALSE)
	 *   * ( matches any value)
	 * </pre>
	 * 
	 * For more information on the DE-9IM, see the OpenGIS Simple Features
	 * Specification.
	 * </p>
	 *
	 * @param g
	 * @param intersectionPattern
	 * @return
	 */
	public native boolean relate(Geometry g, String intersectionPattern);

	public native void setSRID(int SRID);

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

	public native boolean within(Geometry g);

}
