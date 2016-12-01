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
 * Writes the Well-Known Text representation of a Geometry. The Well-Known Text
 * format is defined in the OGC Simple Features Specification for SQL. See
 * WKTReader for a formal specification of the format syntax. The WKTWriter
 * outputs coordinates rounded to the precision model. Only the maximum number
 * of decimal places necessary to represent the ordinates to the required
 * precision will be output.
 * 
 * The SFS WKT spec does not define a special tag for LinearRings. Under the
 * spec, rings are output as LINESTRINGs. In order to allow precisely specifying
 * constructed geometries, JTS also supports a non-standard LINEARRING tag which
 * is used to output LinearRings.
 */
@JsType(isNative = true)
public class WKTWriter {

	@JsConstructor
	public WKTWriter() {

	}

	@JsConstructor
	public WKTWriter(GeometryFactory geometryFactory) {}

	/**
	 * 
	 * Converts a Geometry to its Well-known Text representation.
	 *
	 * @param geometry
	 * @return
	 */
	public native String write(Geometry geometry);
}
