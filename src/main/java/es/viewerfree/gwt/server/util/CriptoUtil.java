package es.viewerfree.gwt.server.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;


public class CriptoUtil {



	public static String encrypt(String password,String key){
		if(key==null)
			throw new IllegalArgumentException("Key no puede ser nula");
		if(password==null)
			throw new IllegalArgumentException("Password no puede ser nula");
		return new StringEncrypter(key).encrypt(password);
	}

	public static String decrypt(String password,String key){
		if(key==null)
			throw new IllegalArgumentException("Key no puede ser nula");
		if(password==null)
			throw new IllegalArgumentException("Password no puede ser nula");
		return new StringEncrypter(key).decrypt(password);
	}


	private static class StringEncrypter {
		Cipher ecipher;
		Cipher dcipher;


		/**
		 * Constructor used to create this object.  Responsible for setting
		 * and initializing this object's encrypter and decrypter Chipher instances
		 * given a Pass Phrase and algorithm.
		 * @param passPhrase Pass Phrase used to initialize both the encrypter and
		 *                   decrypter instances.
		 */
		public StringEncrypter(String passPhrase) {

			// 8-bytes Salt
			byte[] salt = {
					(byte)0xA9, (byte)0x9B, (byte)0xC8, (byte)0x32,
					(byte)0x56, (byte)0x34, (byte)0xE3, (byte)0x03
			};

			// Iteration count
			int iterationCount = 19;

			try {

				KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt, iterationCount);
				SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

				ecipher = Cipher.getInstance(key.getAlgorithm());
				dcipher = Cipher.getInstance(key.getAlgorithm());

				// Prepare the parameters to the cipthers
				AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

				ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
				dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

			} catch (InvalidAlgorithmParameterException e) {
				System.out.println("EXCEPTION: InvalidAlgorithmParameterException");
			} catch (InvalidKeySpecException e) {
				System.out.println("EXCEPTION: InvalidKeySpecException");
			} catch (NoSuchPaddingException e) {
				System.out.println("EXCEPTION: NoSuchPaddingException");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("EXCEPTION: NoSuchAlgorithmException");
			} catch (InvalidKeyException e) {
				System.out.println("EXCEPTION: InvalidKeyException");
			}
		}


		/**
		 * Takes a single String as an argument and returns an Encrypted version
		 * of that String.
		 * @param str String to be encrypted
		 * @return <code>String</code> Encrypted version of the provided String
		 */
		public String encrypt(String str) {

			try {
				// Encode the string into bytes using utf-8
				byte[] utf8 = str.getBytes("UTF8");

				// Encrypt
				byte[] enc = ecipher.doFinal(utf8);

				// Encode bytes to base64 to get a string
				return  new Base64(true).encodeToString(enc);

			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return null;
		}


		/**
		 * Takes a encrypted String as an argument, decrypts and returns the 
		 * decrypted String.
		 * @param str Encrypted String to be decrypted
		 * @return <code>String</code> Decrypted version of the provided String
		 */
		public String decrypt(String str) {

			try {

				// Decode base64 to get bytes
				byte[] dec = new Base64(true).decode(str);

				// Decrypt
				byte[] utf8 = dcipher.doFinal(dec);

				// Decode using utf-8
				return new String(utf8, "UTF8");

			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} 
			return null;
		}

	}
	
}
