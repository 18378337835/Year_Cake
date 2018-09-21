package utilpacket.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author koma
 * @date 2017/12/21
 * @describe
 */

public class DES3Utils {

    /**
     * 秘钥
     */
    private static boolean isTest = false;
    private static String testKey = "CUwTdkDPMsn7SecrWUATZyPh";
    private static String releaseKey = "vxmHABacVDk5Fct3TaXsyMTc";

    private final static String key = isTest ? testKey : releaseKey;

    /**
     * 加解密统一使用的编码方式
     */
    private final static String encoding = "utf-8" ;

    /**
     * 3DES加密
     *
     * @param plainText 普通文本
     * @return
     * @throws Exception
     */
    public static String encode(String plainText, String IV) throws Exception {
        Key deskey = null ;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede" );
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS7Padding" );
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte [] encryptData = cipher.doFinal(
                Base64.encode(plainText.getBytes(), Base64.NO_WRAP));
        return new String(Base64.encode(encryptData, Base64.NO_WRAP));
    }

    /**
     * 3DES解密
     *
     * @param encryptText 加密文本
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText, String IV) throws Exception {
        Key deskey = null ;
        DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance( "desede" );
        deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance( "desede/CBC/PKCS7Padding" );
        IvParameterSpec ips = new IvParameterSpec(IV.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

        byte [] decryptData = cipher.doFinal(Base64.decode(encryptText, Base64.NO_WRAP));

        return new String(Base64.decode(decryptData, Base64.NO_WRAP), encoding);
    }

    public static String padding(String str) {
        byte[] oldByteArray;
        try {
            oldByteArray = str.getBytes("UTF8");
            int numberToPad = 8 - oldByteArray.length % 8;
            byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
            System.arraycopy(oldByteArray, 0, newByteArray, 0,
                    oldByteArray.length);
            for (int i = oldByteArray.length; i < newByteArray.length; ++i) {
                newByteArray[i] = 0;
            }
            return new String(newByteArray, "UTF8");
        } catch (UnsupportedEncodingException e) {
            System.out.println("Crypter.padding UnsupportedEncodingException");
        }
        return null;
    }

}
