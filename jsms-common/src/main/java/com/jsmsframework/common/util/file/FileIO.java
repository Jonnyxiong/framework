package com.jsmsframework.common.util.file;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件读写工具
 *
 * @author dylan
 */
public class FileIO {
    private static final Logger logger = LoggerFactory.getLogger(FileIO.class);

    public enum Charset {
        UTF8("UTF-8"),
        GBK("GBK"),
        GBK2312("GBK2312"),
        UTF16BE("UTF-16BE"),
        Unicode("Unicode");
        private String value;

        Charset(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static Charset getCharset(String fileName) {
        int p = 0;
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName));){
            p = (bin.read() << 8) + bin.read();
        } catch (IOException e) {
            logger.error("解析编码失败  --> {}\r\n使用utf8",e);
            return Charset.UTF8;
        }
        Charset code = null;

        switch (p) {
            case 0xefbb:
                code = Charset.UTF8;
                break;
            case 0xfffe:
                code = Charset.Unicode;
                break;
            case 0xfeff:
                code = Charset.UTF16BE;
                break;
            default:
                code = Charset.GBK;
        }
        return code;
    }

    public static List<String> readAllLines(String filepath) {
        try {
            return Files.readAllLines(Paths.get(filepath), java.nio.charset.Charset.forName(getCharset(filepath).getValue()));
        } catch (IOException e) {
            logger.error("读取所有行失败 ---->{}", e);
            return null;
        }
    }

    public static String readFirstLine(String filepath, Charset charset) {
        File file = new File(filepath);
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;
        }
        try (RandomAccessFile raf = new RandomAccessFile(file, "r");) {
            long len = raf.length();
            if (len == 0L) {
                return "";
            } else {
                long pos = 0;
                while (pos < len) {
                    pos++;
                    raf.seek(pos);
                    if (raf.readByte() == '\n') {
                        break;
                    }
                }
                raf.seek(0);
                byte[] bytes = new byte[(int) (pos)];
                raf.read(bytes);
                if (charset == null) {
                    return new String(bytes);
                } else {
                    return new String(bytes, charset.value);
                }
            }
        } catch (IOException e) {
            logger.error("读取文件第一行失败 ---> {}", e);
        }
        return null;
    }

    public static String readLastLine(String filepath, Charset charset) {
        File file = new File(filepath);
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;
        }
        try (RandomAccessFile raf = new RandomAccessFile(file, "r");) {
            long len = raf.length();
            if (len == 0L) {
                return "";
            } else {
                long pos = len - 1;
                while (pos > 0) {
                    pos--;
                    raf.seek(pos);
                    if (raf.readByte() == '\n') {
                        break;
                    }
                }
                if (pos == 0) {
                    raf.seek(0);
                }
                byte[] bytes = new byte[(int) (len - pos)];
                raf.read(bytes);
                if (charset == null) {
                    return new String(bytes);
                } else {
                    return new String(bytes, charset.value);
                }
            }
        } catch (IOException e) {
            logger.error("读取文件最后一行失败 ---> {}", e);
        }
        return null;
    }

    public static boolean writeLine(String filename, String line, boolean append) {
        return writeLine(filename, line, Charset.UTF8, append, false);
    }

    public static boolean writeLine(String filename, String line, boolean append, boolean newLine) {
        return writeLine(filename, line, Charset.UTF8, append, newLine);
    }

    public static boolean writeLine(String filename, String line, Charset encode, boolean append, boolean newLine) {

        File file = new File(filename);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("创建文件异常 ----> {}", e);
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file, append);
             FileChannel outChannel = fos.getChannel();) {


            ByteBuffer src;
            if (newLine) {
                src = java.nio.charset.Charset.forName(encode.getValue()).encode("\n");
                outChannel.write(src);
            }
            src = java.nio.charset.Charset.forName(encode.getValue()).encode(line);
            // 字节缓冲的容量和limit会随着数据长度变化，不是固定不变的
            /*System.out.println("初始化容量和limit：" + src.capacity() + "," + src.limit());*/
            outChannel.write(src);
        } catch (FileNotFoundException e) {
            logger.error("找不到文件 ----> {}", e);
            return false;
        } catch (IOException e) {
            logger.error("读取文件异常 ----> {}", e);
            return false;
        }
        return true;
    }


    public static boolean writeListLine(String filename, List<String> listLine, Charset encode, boolean append, boolean newLine) {

        File file = new File(filename);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                logger.error("创建文件异常 ----> {}", e);
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file, append);
             FileChannel outChannel = fos.getChannel();) {

            ByteBuffer src;
            for (String line : listLine) {
                if (newLine) {
                    src = java.nio.charset.Charset.forName(encode.getValue()).encode("\n");
                    outChannel.write(src);
                }
                src = java.nio.charset.Charset.forName(encode.getValue()).encode(line);
                outChannel.write(src);
            }
            // 字节缓冲的容量和limit会随着数据长度变化，不是固定不变的
            /*System.out.println("初始化容量和limit：" + src.capacity() + "," + src.limit());*/
        } catch (FileNotFoundException e) {
            logger.error("找不到文件 ----> {}", e);
            return false;
        } catch (IOException e) {
            logger.error("读取文件异常 ----> {}", e);
            return false;
        }
        return true;
    }

    public static String getFileName(String filePathName){
        Pattern pattern = Pattern.compile("(?<=/)[^/]+");
        Matcher matcher = pattern.matcher(filePathName);
        String fileName = null;

        while (matcher.find()){
            fileName = matcher.group();
        }
        pattern = Pattern.compile("(?<=\\\\)[^\\\\]+");
        if(StringUtils.isBlank(fileName)){
            matcher = pattern.matcher(filePathName);
        }else {
            matcher = pattern.matcher(fileName);
        }
        while (matcher.find()){
            fileName = matcher.group();
        }

        return fileName;
    }

    public static String replaceSuffix(String orignFileName,String newSuffix){
        logger.debug("解析后的文件路径 --> {}",orignFileName);
        Pattern pattern = Pattern.compile(".+(?=\\.)");
        Matcher matcher = pattern.matcher(orignFileName);
        StringBuilder newPath = null;
        while (matcher.find()){
            newPath = new StringBuilder(matcher.group());
        }
        if (StringUtils.isBlank(newPath)){
            newPath = new StringBuilder(orignFileName);
            return newPath.append(newSuffix).toString();
        }else{
            return newPath.append(newSuffix).toString();
        }
    }

}
