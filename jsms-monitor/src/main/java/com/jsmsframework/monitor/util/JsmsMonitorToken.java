package com.jsmsframework.monitor.util;

import com.jsmsframework.common.util.Encodes;
import com.ucpaas.sms.common.util.MD5;
import org.joda.time.DateTime;

public class JsmsMonitorToken {
    private static final String SALT = "d&#$3ifj2K1!@tg%&SD*";
    /**
     * token生成工具, 携带时间信息
     * (1) 盐值 + 时间, md5加密
     * (2) md5加密后 + $$$ + 时间 base64 加密
     */
    public static String generateToken(DateTime now) {
        String nowStr = now.toString("yyyyMMddHHmmss");
        StringBuilder stringBuilder = new StringBuilder(SALT);
        stringBuilder.append(nowStr);

        StringBuilder md5StringBuilder = new StringBuilder(MD5.md5(stringBuilder.toString()));
        md5StringBuilder.append("$$$")
                .append(nowStr);
        return Encodes.encodeBase64(stringBuilder.toString());
    }
}
