package com.qzl.base_tools.utils.aes;


/**
 * AES工具类，密钥必须是16位字符串
 * liqq
 */
public class AESUtils {
    /*注意：此处为洛阳app的授权码*/
    public static final String AUTHCODE = "ec19479f79b2a225";

    static AesEncryptHandleImpl aesEncrypt = new AesEncryptHandleImpl(EncryptConstants.ALGORITHM_AES_ECB_PKCS5);

    public static String encrypt(String content) {
        try {
            return aesEncrypt.encrypt(content, AUTHCODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String content) {
        return aesEncrypt.decrypt(content, AUTHCODE);
    }

    public static void main(String[] args) {
        String plainText = AESUtils.encrypt("脱贫");
        System.out.println("aes加密后: " + plainText);
        System.out.println("aes解密后: " + AESUtils.decrypt(plainText));

    }

}
