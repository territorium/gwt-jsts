package jsts;

/**
 *
 * <p>
 * The <code>GwtJSTSBaseTestCase</code> class
 * </p>
 * <p>
 * Copyright: 2003 - 2016 <a href="http://www.teritoriumonline.com">Territorium
 * Online Srl.</a>
 * </p>
 * <p>
 * Via Buozzi 12, 39100 Bolzano, Italy.
 * </p>
 * <p>
 * </p>
 * @author <a href="mailto:mapaccel@teritoriumonline.com">Peter Zanetti</a>.
 * @version 4.0.0,24.11.2016
 * @since 4.0.0
 */
public class GwtJSTSBaseTestCase extends BaseTestCase {

	public GwtJSTSBaseTestCase() {
		super("https://cdn.rawgit.com/bjornharrtell/jsts/gh-pages/1.3.0/jsts.min.js", "ol.GwtOL3Test", 10000);
	}

}
