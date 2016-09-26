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
package jsts.io;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;
import jsts.geom.Geometry;
import jsts.geom.GeometryFactory;

/**
 * Converts a geometry in Well-Known Text format to a Geometry. WKTReader
 * supports extracting Geometry objects from either Readers or Strings. This
 * allows it to function as a parser to read Geometry objects from text blocks
 * embedded in other data formats (e.g. XML).
 * 
 * A WKTReader is parameterized by a GeometryFactory, to allow it to create
 * Geometry objects of the appropriate implementation. In particular, the
 * GeometryFactory determines the PrecisionModel and SRID that is used.
 * 
 * The WKTReader converts all input numbers to the precise internal
 * representation.
 * 
 * Notes:
 * 
 * Keywords are case-insensitive. The reader supports non-standard "LINEARRING"
 * tags. The reader uses Double.parseDouble to perform the conversion of ASCII
 * numbers to floating point. This means it supports the Java syntax for
 * floating point literals (including scientific notation).
 */
@JsType(name = "WKTReader", namespace = "jsts.io", isNative = true)
public class WKTReader {

	/**
	 * 
	 * Creates a reader that creates objects using the default GeometryFactory.
	 *
	 */
	@JsConstructor
	public WKTReader() {}

	/**
	 * 
	 * Creates a reader that creates objects using the given GeometryFactory.
	 *
	 * @param geometryFactory
	 */
	@JsConstructor
	public WKTReader(GeometryFactory geometryFactory) {}

	/**
	 * 
	 * Reads a Well-Known Text representation of a Geometry from a String.
	 *
	 * @param wkt
	 * @return
	 */
	public native Geometry read(String wkt);

}
