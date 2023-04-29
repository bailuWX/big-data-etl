package com.bigdata.omp.util;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * Created by wangzb on 2017-2-24.
 */
public class AESPasswordEncoder{

	// @NotNull
	private String secretKey;
	// @NotNull
	private String ivParameter;

	public AESPasswordEncoder() {
	}

	public AESPasswordEncoder(String secretKey, String ivParameter) {
		this.secretKey = secretKey;
		this.ivParameter = ivParameter;
	}

	public String encode(String s) {
		try {
			String str = decryptJs(s);
			// String str=s;
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();
			byte[] dataBytes = str.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
			SecretKeySpec keyspec = new SecretKeySpec(secretKey.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);
			return new sun.misc.BASE64Encoder().encode(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setIvParameter(String ivParameter) {
		this.ivParameter = ivParameter;
	}

    public static String decryptJs(String text) {
		String output = null;
        int[] alterText = new int[100];
        int[] varCost = new int[100];
        int textSize = text.length();
		int i;
        for (i = 0; i < textSize - 1; i++) {
            alterText[i] = text.charAt(i);
            varCost[i] = text.charAt(i + 1);
        }
        for (i = 0; i < textSize; i = i + 2) {
			output += (char) (alterText[i] - varCost[i]);
		}
		return output.substring(output.indexOf("null") + 4);
	}

    public static String ecryptJs(String text) {
		String output = null;
        int[] alterText = new int[100];
        int[] varCost = new int[100];
        int textSize = text.length();
		int t;
        for (int i = 0; i < textSize; i++) {
			t = (int) Math.round(Math.random() * 111) + 77;
            alterText[i] = text.charAt(i) + t;
			varCost[i] = t;
		}
        for (int i = 0; i < textSize; i++) {
			output += fromCharCode(alterText[i], varCost[i]);
		}
		return output.substring(output.indexOf("null") + 4);
	}

	public static String fromCharCode(int... codePoints) {
		StringBuilder builder = new StringBuilder(codePoints.length);
		for (int codePoint : codePoints) {
			builder.append(Character.toChars(codePoint));
		}
		return builder.toString();
	}

	public static void main(String[] args) {
		String enc = AESPasswordEncoder.ecryptJs("5541855");

		System.out.println(enc);
		enc = "Ã\u008E\u0089T²~Ê\u0099\u008DUé´é´";
		String des = AESPasswordEncoder.decryptJs(enc);
		System.out.println(des);
	}

}
