package es.viewerfree.gwt.server.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CriptoUtilTest {

	

	private static final String PASSWORD = "PASSWORD";
	private static final String KEY = "asdfghtet";

	@Test
	public void testEncryptDecrypt() {
		String decrypt = CryptoUtil.encrypt(PASSWORD, KEY);
		assertEquals(PASSWORD,CryptoUtil.decrypt(decrypt, KEY));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEncryptKeyNullCheck() throws Exception {
		CryptoUtil.encrypt(PASSWORD, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testEncryptPasswordNullCheck() throws Exception {
		CryptoUtil.encrypt(null, KEY);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDecryptKeyNullCheck() throws Exception {
		CryptoUtil.decrypt(PASSWORD, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDecryptPasswordNullCheck() throws Exception {
		CryptoUtil.decrypt(null, KEY);
	}

}
