package com.jsmsframework.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZIPUtil{
    private static Logger logger = LoggerFactory.getLogger(GZIPUtil.class);

    public static final int DEFAULT_BUFFER_SIZE = 1024;
    public static final String UTF_8 = "UTF-8";
    public static final String ISO_8859_1 = "ISO-8859-1";

    private enum ReturnType{

        字节数组(1),
        字符串(2);
        private Integer value;
        ReturnType(Integer value) {
            this.value = value;
        }
        public Integer getValue() {
            return value;
        }

        public static String getDescByValue(Integer value) {
            if(value == null){ return null;}
            String result = null;
            for (ReturnType s : ReturnType.values()) {
                if (value == s.getValue()) {
                    break;
                }
            }
            return result;
        }

    }

    public static byte[] gzip(byte[] bytes){
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        byte[] gzipBytes = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();GZIPOutputStream gzip = new GZIPOutputStream(out)){
            gzip.write(bytes);
            gzip.finish();
            gzipBytes = out.toByteArray();
        } catch (IOException e) {
            logger.error("gzip压缩异常 ---> {}",e);
        }
        return gzipBytes;
    }

    public static byte[] ungzip(byte[] bytes, int bufferSize) {
        return (byte[]) commonUnZIP(bytes,null, bufferSize,ReturnType.字节数组);
    }
    private static Object commonUnZIP(byte[] bytes,String encoding,int bufferSize,ReturnType returnType){
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        Object ungzipResult = null;

        try (GZIPInputStream ungzip = new GZIPInputStream(in);ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buf = new byte[bufferSize];
            int num = -1;
            while ((num = ungzip.read(buf, 0, buf.length)) != -1) {
                out.write(buf, 0, num);
            }
            in.close();
            if (ReturnType.字节数组.equals(returnType)){
                ungzipResult = out.toByteArray();
            }else if (ReturnType.字符串.equals(returnType)){
                ungzipResult = out.toString(encoding);
            }
            out.flush();
        } catch (IOException e) {
            logger.error("gzip解压异常 ---> {}",e);
        }
        return ungzipResult;
    }

    public static byte[] gzip(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        byte[] bytes = new byte[0];
        try {
            bytes = str.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            logger.error("gzip压缩异常, 编码异常 ---> {}",e);
        }
        return gzip(bytes);
    }

    public static byte[] gzip(String str) {
        return gzip(str, UTF_8);
    }

    public static byte[] ungzip(byte[] bytes) {
        return ungzip(bytes, DEFAULT_BUFFER_SIZE);
    }



    public static String ungzipToString(byte[] bytes, String encoding) {
        return ungzipToString(bytes, encoding, 1);
//        DatatypeConverter.printBase64Binary(bytes);
    }

    public static String ungzipToString(byte[] bytes, String encoding,int bufferSize) {
        return (String) commonUnZIP(bytes,encoding, bufferSize,ReturnType.字符串);
    }

    public static String ungzipToString(byte[] bytes) {
        return ungzipToString(bytes, UTF_8);
    }

    public static void main(String[] args) throws IOException {
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        System.out.println("字符串长度："+s.length());
        System.out.println("压缩后：："+Arrays.toString(gzip(s)) + " \r\n 压缩后长度：："+ gzip(s).length);
        System.out.println("解压后："+ Arrays.toString(ungzip(gzip(s))) + "\r\n 解压后长度："+ ungzip(gzip(s)).length);
        System.out.println("解压字符串后：："+ ungzipToString(gzip(s)));
        System.out.println("解压字符串后长度：："+ ungzipToString(gzip(s)).length());
    }

}
