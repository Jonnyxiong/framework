package com.jsmsframework.sms.send.util;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.enums.Code;
import com.jsmsframework.common.enums.smsSend.SmsSendFileType;
import com.jsmsframework.common.util.RegexUtils;
import com.jsmsframework.common.util.file.FileIO;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author dylan
 */
public enum JsmsParseMobilesFile {
    INSTANCE;
    private Logger logger = LoggerFactory.getLogger(JsmsParseMobilesFile.class);

    /**
     * @param originFilePath 路径 + 文件名
     * @param sysSavePath    保存解析文件路径(不含月份子文件夹)
     * @param maxImportLimit
     * @return
     */
    public ResultVO parseFileAndOutput(String originFilePath, String sysSavePath, int maxImportLimit) {
        logger.debug("文件路径 --> {}", originFilePath);

        if (StringUtils.isBlank(originFilePath)) {
            return ResultVO.failure(Code.OPT_ERR_NOT_FOUND, "未找上传的文件");
        }
        Pattern pattern = Pattern.compile("(?<=\\.)[^\\.]+");
        Matcher matcher = pattern.matcher(originFilePath);
        if (!matcher.find()) {
            return ResultVO.failure(Code.OPT_ERR_NOT_FOUND, "不支持的文件格式");
        }
        String suffix = matcher.group(matcher.groupCount());
        ResultVO resultVO;
        if (StringUtils.equalsIgnoreCase(suffix, "csv")) { // 0：号码池(页面输入)，1：xlsx或xls格式导入，2：txt格式导入,3：csv格式导入
            resultVO = parseTxtOrCsv(originFilePath, maxImportLimit, SmsSendFileType.csv格式导入);
        } else if (StringUtils.equalsIgnoreCase(suffix, "txt")) {
            resultVO = parseTxtOrCsv(originFilePath, maxImportLimit, SmsSendFileType.txt格式导入);
        } else if (StringUtils.equalsIgnoreCase(suffix, "xls")
                || StringUtils.equalsIgnoreCase(suffix, "xlsx")) {
            resultVO = parseExcel(originFilePath, maxImportLimit);
        } else {
            resultVO = ResultVO.failure(Code.OPT_ERR_NOT_ALLOWED, "不支持的文件类型");
        }
        if (resultVO.isSuccess()) {
            Map data = (Map) resultVO.getData();
            Set<String> mobileSet = (Set<String>) data.get("mobileSet");
            ParseMobilesInfo parseMobilesInfo = (ParseMobilesInfo) data.get("parseMobilesInfo");

            List<String> mobileLines = convertToLine(mobileSet);
            String newFileName = FileIO.replaceSuffix(FileIO.getFileName(originFilePath), ".dat");
            boolean write = write(newFileName, sysSavePath, parseMobilesInfo, mobileLines);
            if (write) {
                return ResultVO.successDefault(parseMobilesInfo);
            } else {
                return ResultVO.failure(Code.OPT_ERR, "文件解析失败");
            }
        }
        return resultVO;
    }

    /**
     * @param originFilePath 路径 + 文件名
     * @param maxImportLimit
     * @return
     */
    private ResultVO parseExcel(String originFilePath, int maxImportLimit) {
        File excel = new File(originFilePath);

        if (!excel.exists() || !excel.isFile()) {
            logger.debug("文件不存在");
            return ResultVO.failure("文件不存在");
        }
        logger.debug("即将解析的文件大小  ==> {}, 名称 ==> {}",excel.length(),excel.getName());
        List<Map> tempList;
        try {
            ImportParams importParams = new ImportParams();
            tempList = ExcelImportUtil.importExcel(excel, Map.class, importParams);
        } catch (Exception e) {
            return ResultVO.failure("文件解析失败，请尝试其他格式");

        }
        logger.debug("号码Excel 读取完成  ----------> 开始解析");
        if (tempList == null) {
            logger.error("解析excel失败：filePath=" + originFilePath);
            return ResultVO.failure("导入文件格式错误，请使用模板");

        }
        if (tempList.size() > maxImportLimit) {
            logger.error("excel 数量限制超过限制：maxImportLimit = {}, 实际导入数 >= {}", maxImportLimit,
                    tempList.size());
            return ResultVO.failure("导入号码量超过限制，不能超过" + maxImportLimit);
        }
        int errNum = 0;
        int total = 0;

        Set<String> phoneSet = new TreeSet();
        String phone = null;
        for (Map map : tempList) {
            for (Object object : map.values()) {
                if (object == null) {
                    continue;
                }
                ++total;

                if (total > maxImportLimit) {
                    logger.error("csv或txt 导入数量超过限制：maxImportLimit = {}, 实际导入数 >= {}", maxImportLimit, total);
                    return ResultVO.failure("导入号码量超过限制，不能超过" + maxImportLimit);
                }
                if (object instanceof Double) {
                    BigDecimal bigDecimal = new BigDecimal((double) object);
                    phone = bigDecimal.toString();
                    if (RegexUtils.isMobile(phone) || RegexUtils.isOverSeaMobile(phone)) {
                        phoneSet.add(phone);
                    } else {
                        if (StringUtils.isNoneBlank(phone)) {
                            ++errNum;
                        }
                    }
                } else if (object instanceof String) {
                    if (RegexUtils.isMobile((String) object) || RegexUtils.isOverSeaMobile((String) object)) {
                        phoneSet.add((String) object);
                    } else {
                        if (StringUtils.isNoneBlank((String) object)) {
                            ++errNum;
                        }
                    }
                } else {
                    try {
                        phone = String.valueOf(object);
                        if (RegexUtils.isMobile(phone) || RegexUtils.isOverSeaMobile(phone)) {
                            phoneSet.add(phone);
                        } else {
                            if (StringUtils.isNoneBlank(phone)) {
                                ++errNum;
                            }
                        }
                    } catch (Exception e) {
                        logger.error("导入号码: {} 无法装换", object);
                        ++errNum;
                    }
                }
            }
        }

        int submitTotal = phoneSet.size();
        Map result = new HashMap();
        ParseMobilesInfo mobileParseInfo = new ParseMobilesInfo();
        mobileParseInfo.setErrNum(errNum);
        mobileParseInfo.setRepeatNum(total - submitTotal - errNum);
        mobileParseInfo.setSubmitTotal(submitTotal);
        mobileParseInfo.setFileType(SmsSendFileType.xlsx或xls格式导入.getValue()); //0：号码池(页面输入)，1：xlsx或xls格式导入，2：txt格式导入,3：csv格式导入
        result.put("mobileSet", phoneSet);
        result.put("parseMobilesInfo", mobileParseInfo);

        return ResultVO.successDefault(result);
    }

    private List<String> convertToLine(Set<String> phoneSet) {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        int size = phoneSet.size();
        List<String> mobileLines = new ArrayList<>(size / 100 + 1);
        for (String mobile : phoneSet) {
            stringBuilder.append(mobile).append(",");
            ++count;
            if (count % 100 == 0) {
                mobileLines.add(stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString());
                stringBuilder.delete(0, stringBuilder.length());
            }
        }
        if (size % 100 != 0) {
            mobileLines.add(stringBuilder.deleteCharAt(stringBuilder.length() - 1).toString());
            stringBuilder.delete(0, stringBuilder.length());
        }
        return mobileLines;
    }

    /**
     * @param fileName         文件名
     * @param sysSaveDir       保存解析文件路径(不含月份子文件夹)
     * @param parseMobilesInfo
     * @param mobileLines
     * @return
     */
    private boolean write(String fileName, String sysSaveDir, ParseMobilesInfo parseMobilesInfo, List<String> mobileLines) {
        logger.debug("写入解析文件 ---> start，  fileName--> {},sysSavePath -->{}, parseMobilesInfo --> {}", fileName, sysSaveDir, JSON.toJSONString(parseMobilesInfo));
        String filePathName = new StringBuilder(sysSaveDir)
                .append(File.separatorChar)
                .append(DateTime.now().toString("yyyyMM"))
                .append(File.separatorChar).append(fileName).toString();

        String parseInfo = new StringBuilder(parseMobilesInfo.getClass().getName())
                .append("=")
                .append(JSON.toJSONString(parseMobilesInfo)).toString();
        boolean writeLine = FileIO.writeLine(filePathName, parseInfo, true);
        if (writeLine) {
            boolean writeListLine = FileIO.writeListLine(filePathName, mobileLines, FileIO.Charset.UTF8, true, true);
            logger.debug("写入解析文件 ---> end，文件路径 ==> {}, 写入{}行",filePathName, mobileLines.size());
            return writeListLine;
        }
        return false;
    }

    public JsmsParseMobilesFile.ParseMobilesInfo parseFirstLine(String firstLine) {
        logger.debug("读取解析文件的第一行内容  ==> {}", firstLine);

        try {
            Pattern pattern = Pattern.compile("(?<=\\=).+");
            Matcher matcher = pattern.matcher(firstLine);
            if (matcher.find()) {
                String group = matcher.group();
                ParseMobilesInfo parseMobilesInfo = JSON.parseObject(group, ParseMobilesInfo.class);
                return parseMobilesInfo;
            }
        } catch (Exception e) {
            logger.debug("解析文件第一行失败  ==> {}",e);
        }
        return null;
    }

    /**
     * 判断导入文件对应的解析文件是否存在
     *
     * @param orignFilename 原文件名(不含路径)
     * @param sysSavePath   保存解析文件的路径
     * @return
     */
    public ResultVO isParseFileExist(String orignFilename, String sysSavePath) {
        DateTime now = DateTime.now();
        String newFileName = FileIO.replaceSuffix(orignFilename, ".dat");
        String newFilePath = new StringBuilder(sysSavePath)
                .append(File.separatorChar)
                .append(now.toString("yyyyMM"))
                .append(File.separatorChar).append(newFileName).toString(); // todo 获取新文件路径
        logger.debug("在当前月文件夹下查找解析后的文件 --> {}", newFilePath);
        File file = new File(newFilePath);
        if (file.exists() && file.isFile()) {
            return ResultVO.successDefault(newFilePath);
        }
        newFilePath = new StringBuilder(sysSavePath)
                .append(File.separatorChar)
                .append(now.minusMonths(1).toString("yyyyMM"))
                .append(File.separatorChar).append(newFileName).toString();
        logger.debug("在上个月文件夹下查找解析后的文件 --> {}", newFilePath);
        file = new File(newFilePath);
        if (file.exists() && file.isFile()) {
            return ResultVO.successDefault(newFilePath);
        }
        return ResultVO.failure("找不到文件");
    }

    /**
     * @param mobiles
     * @param maxImportLimit
     * @return
     */
    public ResultVO parseMobiles(String mobiles, int maxImportLimit) {
        if (StringUtils.isBlank(mobiles)) {
            return ResultVO.failure("号码为空");
        }
        Set<String> phoneSet = new TreeSet();
        int errNum = 0;
        int total = 0;

        String[] mobileArr = mobiles.split("\\D");
        for (String mobile : mobileArr) {
            if (StringUtils.isBlank(mobile)) {
                continue;
            }
            ++total;
            if (RegexUtils.isMobile(mobile) || RegexUtils.isOverSeaMobile(mobile)) {
                phoneSet.add(mobile);
            } else {
                ++errNum;
            }

            if (total > maxImportLimit) {
                logger.error("号码池导入数量超过限制：maxImportLimit = {}, 实际导入数 >= {}", maxImportLimit, total);
                return ResultVO.failure(Code.OPT_ERR_NOT_ALLOWED, "导入号码量超过限制，不能超过" + maxImportLimit);
            }
        }

        int submitTotal = phoneSet.size();
        Map result = new HashMap();
        ParseMobilesInfo mobileParseInfo = new ParseMobilesInfo();
        mobileParseInfo.setErrNum(errNum);
        mobileParseInfo.setRepeatNum(total - submitTotal - errNum);
        mobileParseInfo.setSubmitTotal(submitTotal);

        result.put("mobileLines", convertToLine(phoneSet));
        result.put("parseMobilesInfo", mobileParseInfo);

        return ResultVO.successDefault(result);
    }

    /**
     * @param originFilePath 路径 + 文件名
     * @param maxImportLimit
     * @param fileType
     * @return
     */
    private ResultVO parseTxtOrCsv(String originFilePath, int maxImportLimit, SmsSendFileType fileType) {
        File text = new File(originFilePath);

        if (!text.exists() || !text.isFile()) {
            logger.debug("文件不存在");
            return ResultVO.failure("文件不存在");
        }

        logger.debug("即将解析的文件大小  ==> {}, 名称 ==> {}",text.length(),text.getName());

        List<String> strings = FileIO.readAllLines(originFilePath);
        if (strings == null) {
            return ResultVO.failure(Code.OPT_ERR, "文件解析失败");
        }
        Set<String> phoneSet = new TreeSet();
        int errNum = 0;
        int total = 0;
        for (String string : strings) {
            if (StringUtils.isBlank(string)) {
                continue;
            }
            String[] mobiles = string.split("\\D");
            for (String mobile : mobiles) {
                if (StringUtils.isBlank(mobile)) {
                    continue;
                }
                ++total;
                if (RegexUtils.isMobile(mobile) || RegexUtils.isOverSeaMobile(mobile)) {
                    phoneSet.add(mobile);
                } else {
                    ++errNum;
                }

                if (total > maxImportLimit) {
                    logger.error("csv或txt 导入数量超过限制：maxImportLimit = {}, 实际导入数 >= {}", maxImportLimit, total);
                    return ResultVO.failure(Code.OPT_ERR_NOT_ALLOWED, "导入号码量超过限制，不能超过" + maxImportLimit);
                }
            }

        }
        int submitTotal = phoneSet.size();
        Map result = new HashMap();
        ParseMobilesInfo mobileParseInfo = new ParseMobilesInfo();
        mobileParseInfo.setErrNum(errNum);
        mobileParseInfo.setRepeatNum(total - submitTotal - errNum);
        mobileParseInfo.setSubmitTotal(submitTotal);
        mobileParseInfo.setFileType(fileType.getValue()); //0：号码池(页面输入)，1：xlsx或xls格式导入，2：txt格式导入,3：csv格式导入

        result.put("mobileSet", phoneSet);
        result.put("parseMobilesInfo", mobileParseInfo);

        return ResultVO.successDefault(result);
    }

    public static class ParseMobilesInfo {
        private int errNum;
        private int submitTotal;
        private int repeatNum;
        private int fileType; //0：号码池(页面输入)，1：xlsx或xls格式导入，2：txt格式导入,3：csv格式导入

        public int getErrNum() {
            return errNum;
        }

        public void setErrNum(int errNum) {
            this.errNum = errNum;
        }

        public int getSubmitTotal() {
            return submitTotal;
        }

        public void setSubmitTotal(int submitTotal) {
            this.submitTotal = submitTotal;
        }

        public int getRepeatNum() {
            return repeatNum;
        }

        public void setRepeatNum(int repeatNum) {
            this.repeatNum = repeatNum;
        }

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }
    }
}
