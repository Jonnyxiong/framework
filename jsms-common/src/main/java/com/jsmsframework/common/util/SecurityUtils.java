package com.jsmsframework.common.util;

import com.ucpaas.sms.common.util.security.Cryptos;
import com.ucpaas.sms.common.util.security.Des3Utils;
import com.ucpaas.sms.common.util.security.Digests;

/**
 * Created by xiongfenglin on 2017/11/25.
 *
 * @author: xiongfenglin
 */
public class SecurityUtils {
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;
    private static final String KEY = "8989621";
    private static final String SID_BASE_STRING = "123321";

    public SecurityUtils() {
    }

    public static String encodeDes3(String str) {
        return Des3Utils.encodeDes3(str);
    }

    public static String decodeDes3(String str) {
        return Des3Utils.decodeDes3(str);
    }

    public static String generateSid() {
        return getSignature("123321" + System.currentTimeMillis(), "8989621");
    }

    public static String getSignature(String dataStr, String keyStr) {
        byte[] data = dataStr.getBytes();
        byte[] key = keyStr.getBytes();
        return Encodes.encodeHex(Digests.md5(Cryptos.hmacSha1(data, key)));
    }

    public static String encryptSHA(String plainPassword) {
        byte[] salt = Digests.generateSalt(8);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, 1024);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    public static String encryptMD5(String plainPassword) {
        return Encodes.encodeHex(Digests.md5(plainPassword));
    }

    public static boolean validatePassword(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, 1024);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }

    public static void main(String[] args) {
        System.out.println(generateSid());
    }
}
