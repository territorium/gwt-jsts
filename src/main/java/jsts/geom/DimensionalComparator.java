package jsts.geom;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsType;

/**
 *
 * <p>
 * Compares two Coordinates, allowing for either a 2-dimensional or
 * 3-dimensional comparison, and handling NaN values correctly.
 * </p>
 * <p>
 * Copyright: 2003 - 2016
 * <a href="http://www.teritoriumonline.com">Territorium Online Srl.</a>
 * </p>
 * <p>
 * Via Buozzi 12, 39100 Bolzano, Italy.
 * </p>
 * <p>
 * </p>
 * @author <a href="mailto:mapaccel@teritoriumonline.com">Peter Zanetti</a>.
 * @version 1.0.0,25.11.2016
 * @since 1.0.0
 */
@JsType(name = "DimensionalComparator", namespace = "jsts.geom", isNative = true)
public class DimensionalComparator {

	/**
	 * Creates a comparator for 2 dimensional coordinates.
	 *
	 */
	@JsConstructor
	public DimensionalComparator() {}

	/**
	 * Creates a comparator for 2 or 3 dimensional coordinates, depending on the
	 * value provided.
	 *
	 */
	@JsConstructor
	public DimensionalComparator(int dimensionsToTest) {}

	/**
	 * Compare two doubles, allowing for NaN values. NaN is treated as being
	 * less than any valid number.
	 *
	 * @param a - a double
	 * @param b - a double
	 * @return -1, 0, or 1 depending on whether a is less than, equal to or
	 *         greater than b
	 */
	public native static int compare(double a, double b);

	public native int compare(Object o1, Object o2);
}