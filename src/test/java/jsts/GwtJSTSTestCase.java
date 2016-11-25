package jsts;

import com.google.gwt.junit.client.GWTTestCase;

/**
 *
 * <p>
 * The <code>GwtJSTSTestCase</code> class
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
 * @version 1.0.0,24.11.2016
 * @since 1.0.0
 */
public class GwtJSTSTestCase extends GWTTestCase {

	private static final String	MODUL_NAME	= "jsts.GwtJSTSTest";
	private static final int		TEST_DELAY	= 10000;

	@Override
	public String getModuleName() {
		return GwtJSTSTestCase.MODUL_NAME;
	}

}
