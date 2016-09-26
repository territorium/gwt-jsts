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

/**
 * OpenLayers 3 Geometry parser and writer
 */
@JsType(name = "OL3Parser", namespace = "jsts.io", isNative = true)
public class OL3Parser {

	/**
	 * 
	 * Constructs a(n) {@link OL3Parser} object.
	 *
	 */
	@JsConstructor
	public OL3Parser() {};

	/**
	 * 
	 * converts a openlayer geometry into a jts geometry
	 *
	 * @param geom
	 * @return
	 */
	public native Geometry read(ol.geom.Geometry geom);

	/**
	 * 
	 * converts a jts geometry into a openlayer geometry
	 *
	 * @param geom
	 * @return
	 */
	public native ol.geom.Geometry write(Geometry geom);

}
