package utilpacket.utils;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * create by Year_cake
 * description: AES加解密
 */

public class AESUtils {

    private final Cipher cipher;
    private final SecretKeySpec key;
    private AlgorithmParameterSpec spec;
    private static final String SEED="2018duoke&";

    public AESUtils() throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(SEED.getBytes("UTF-8"));
        byte[] keyBytes = new byte[32];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        key = new SecretKeySpec(keyBytes, "AES");
        spec = getIV();
    }

    private AlgorithmParameterSpec getIV() {
        byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    /**
     * 加密
     * @param plainText
     * @return
     * @throws Exception
     */
    public String encrypt(String plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8"));
        String encryptedText = new String(Base64.encode(encrypted,
                                                        Base64.NO_WRAP), "UTF-8");
        return encryptedText;
    }

    /**
     * 解密
     * @param cryptedText
     * @return
     * @throws Exception
     */
    public String decrypt(String cryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] bytes = Base64.decode(cryptedText, Base64.NO_WRAP);
        byte[] decrypted = cipher.doFinal(bytes);
        String decryptedText = new String(decrypted, "UTF-8");
        return decryptedText;
    }


}
