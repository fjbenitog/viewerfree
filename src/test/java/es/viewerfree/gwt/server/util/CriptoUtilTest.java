package es.viewerfree.gwt.server.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CriptoUtilTest {

	

	private static final String PASSWORD = "PASSWORD";
	private static final String KEY = "asdfghtet";

	@Test
	public void testEncryptDecrypt() {
		String decrypt = CriptoUtil.encrypt(PASSWORD, KEY);
		assertEquals(PASSWORD,CriptoUtil.decrypt(decrypt, KEY));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEncryptKeyNullCheck() throws Exception {
		CriptoUtil.encrypt(PASSWORD, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEncryptPasswordNullCheck() throws Exception {
		CriptoUtil.encrypt(null, KEY);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDecryptKeyNullCheck() throws Exception {
		CriptoUtil.decrypt(PASSWORD, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDecryptPasswordNullCheck() throws Exception {
		CriptoUtil.decrypt(null, KEY);
	}

}
