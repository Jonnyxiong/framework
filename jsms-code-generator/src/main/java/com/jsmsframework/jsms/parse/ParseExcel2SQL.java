package com.jsmsframework.jsms.parse;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析数据库中的表
 *
 * @author huangwenjie
 * @date 2017-01-24
 */
public class ParseExcel2SQL {
    private static Logger logger = LoggerFactory.getLogger(ParseExcel2SQL.class);

    /**
     * @param
     * @return
     * @throws Exception
     * @title 获取表的信息
     */
    public String generateSqlByExcel(String filePath) {

        logger.debug("文件路径 --> {}", filePath);
        File excel = new File(filePath);

        if (!excel.exists()) {
            logger.debug("文件不存在");
            return null;
        }

        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(0);
        List<Map> tempList = ExcelImportUtil.importExcel(excel, Map.class, importParams);
        StringBuilder sql = new StringBuilder("CREATE TABLE `");
        int rank = 0;
        String comment = null;
        for (Map map : tempList) {
            SqlLine sqlLine = this.new SqlLine();
            if (rank == 0) {
                String tableDesc = (String) map.get("column_0");
                logger.debug("tableDesc ==> {}", tableDesc);

                Pattern pattern = Pattern.compile("\\w+");
                Matcher matcher = pattern.matcher(tableDesc);
                if (matcher.find()) {
                    sql.append(matcher.group()).append("`(");
                } else {
                    throw new IllegalArgumentException("解析失败, 未找到对应的表名称");
                }
                pattern = Pattern.compile("(?<=\\(|（).+(?=\\）|\\))");
                matcher = pattern.matcher(tableDesc);
                if (matcher.find()) {
                    comment = matcher.group();
                }
            } else if (rank == 1) {
                rank++;
                continue;
            } else {
                sqlLine.setId(map.get("column_0").toString());
                sqlLine.setColumn(String.valueOf(map.get("column_1")));
                sqlLine.setComment(String.valueOf(map.get("column_2")));
                sqlLine.setType(String.valueOf(map.get("column_3")));
                sqlLine.setLength(String.valueOf(map.get("column_4")));
                sqlLine.setFloa(String.valueOf(map.get("column_5")));
                String ableNull = String.valueOf(map.get("column_6"));
                if (StringUtils.isNotBlank(ableNull) && ableNull.contains("是")) {
                    sqlLine.setAbleNull(true);
                }
                if(map.get("column_7") != null){
                    sqlLine.setDefaultValue(String.valueOf(map.get("column_7")));
                }

                sql.append(sqlLine.toSql());
            }
            rank++;

        }
        sql.deleteCharAt(sql.length() - 1);
        sql.append("\r\n)ENGINE=InnoDB DEFAULT CHARSET=utf8");
        if (StringUtils.isNoneBlank(comment)) {
            sql.append(" COMMENT=\"").append(comment).append("\"");
        }
        sql.append(";");
        return sql.toString();
    }

    public static void main(String[] args) {
        String filePath = "E:\\Desktop\\新建文件夹 (3)\\工作簿1.xlsx";
        ParseExcel2SQL thiss = new ParseExcel2SQL();
        String sql = thiss.generateSqlByExcel(filePath);
        System.out.println("\n\r"+sql);

    }

    private class SqlLine {
        private String id;
        private String column;
        private String type;
        private boolean ableNull;
        private String defaultValue;
        private String floa;
        private String length;
        private String comment;
        private String split = "`";

        public String getSplit() {
            return split;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isAbleNull() {
            return ableNull;
        }

        public void setAbleNull(boolean ableNull) {
            this.ableNull = ableNull;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getFloa() {
            return floa;
        }

        public void setFloa(String floa) {
            this.floa = floa;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String toSql() {
            final StringBuffer sb = new StringBuffer("\n\r`");
            sb.append(column).append("` ")
                    .append(type.trim());
            if (StringUtils.isNotBlank(length) && length.matches("\\d+")) {
                if (!type.toLowerCase().contains("date")) {
                    sb.append("(").append(length);
                    if (type.equalsIgnoreCase("FLOAT")
                            || type.equalsIgnoreCase("DOUBLE")
                            || type.equalsIgnoreCase("DECIMAL")) {
                        sb.append(",").append(floa.trim());
                    }
                    sb.append(")");
                }
            }
            if (StringUtils.isNotBlank(comment)
                    && (comment.contains("自增") || comment.contains("递增"))) {
                sb.append(" NOT NULL AUTO_INCREMENT PRIMARY KEY");

            }else if (StringUtils.isNotBlank(comment)
                    && comment.contains("主键")) {
                sb.append(" PRIMARY KEY");

            } else if (ableNull) {
                if (StringUtils.isNoneBlank(defaultValue)) {
                    sb.append(" DEFAULT '").append(defaultValue.trim()).append("'");
                } else {
                    sb.append(" DEFAULT NULL");
                }
            } else {
                sb.append(" NOT NULL");
                if (StringUtils.isNotBlank(defaultValue)){
                    sb.append(" DEFAULT '").append(defaultValue.trim()).append("'");
                }
            }
            if (StringUtils.isNoneBlank(comment)) {
                sb.append(" COMMENT \"").append(comment.replaceAll("\\\\s*|\\t|\\r|\\n","")).append("\"");
            }
            sb.append(",");
            return sb.toString();
        }
    }
}
