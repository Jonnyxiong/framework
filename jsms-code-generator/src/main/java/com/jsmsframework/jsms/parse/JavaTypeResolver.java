package com.jsmsframework.jsms.parse;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @title Java类型解析信息
 * @author huangwenjie
 * @date 2015年4月21日
 */
public final class JavaTypeResolver {

    public static Map<Integer, JdbcTypeInformation> typeMap = null;

    static {
        typeMap = new HashMap<Integer, JdbcTypeInformation>();

        typeMap.put(Types.ARRAY,new JdbcTypeInformation("ARRAY", Object.class.getName()));
        typeMap.put(Types.BIGINT,new JdbcTypeInformation("BIGINT", Long.class.getName()));
        typeMap.put(Types.BINARY, new JdbcTypeInformation("BINARY", "byte[]"));
        typeMap.put(Types.BIT,new JdbcTypeInformation("BIT", Boolean.class.getName()));
        typeMap.put(Types.BLOB, new JdbcTypeInformation("BLOB", "byte[]"));
        typeMap.put(Types.BOOLEAN, new JdbcTypeInformation("BOOLEAN",Boolean.class.getName()));
        typeMap.put(Types.CHAR,new JdbcTypeInformation("CHAR", String.class.getName()));
        typeMap.put(Types.CLOB,new JdbcTypeInformation("CLOB", String.class.getName()));
        typeMap.put(Types.DATALINK, new JdbcTypeInformation("DATALINK",Object.class.getName()));
        typeMap.put(Types.DATE,new JdbcTypeInformation("DATE", Date.class.getName()));
        typeMap.put(Types.DISTINCT, new JdbcTypeInformation("DISTINCT",Object.class.getName()));
        typeMap.put(Types.DOUBLE, new JdbcTypeInformation("DOUBLE",Double.class.getName()));
        typeMap.put(Types.FLOAT,new JdbcTypeInformation("FLOAT", Double.class.getName()));
        typeMap.put(Types.INTEGER, new JdbcTypeInformation("INTEGER",Integer.class.getName()));
        typeMap.put(Types.DECIMAL, new JdbcTypeInformation("DECIMAL",BigDecimal.class.getName()));
        typeMap.put(Types.NUMERIC, new JdbcTypeInformation("NUMERIC",BigDecimal.class.getName()));
        typeMap.put(Types.JAVA_OBJECT, new JdbcTypeInformation("JAVA_OBJECT",Object.class.getName()));
        typeMap.put(Types.LONGNVARCHAR, new JdbcTypeInformation("LONGNVARCHAR",String.class.getName()));
        typeMap.put(Types.LONGVARBINARY, new JdbcTypeInformation("LONGVARBINARY","byte[]"));
        typeMap.put(Types.LONGVARCHAR, new JdbcTypeInformation("LONGVARCHAR",String.class.getName()));
        typeMap.put(Types.NCHAR, new JdbcTypeInformation("NCHAR",String.class.getName()));
        typeMap.put(Types.NCLOB, new JdbcTypeInformation("NCLOB",String.class.getName()));
        typeMap.put(Types.NVARCHAR, new JdbcTypeInformation("NVARCHAR",String.class.getName()));
        typeMap.put(Types.NULL,new JdbcTypeInformation("NULL", Object.class.getName()));
        typeMap.put(Types.OTHER,new JdbcTypeInformation("OTHER", Object.class.getName()));
        typeMap.put(Types.REAL,new JdbcTypeInformation("REAL", Float.class.getName()));
        typeMap.put(Types.REF,new JdbcTypeInformation("REF", Object.class.getName()));
        typeMap.put(Types.SMALLINT, new JdbcTypeInformation("SMALLINT",Short.class.getName()));
        typeMap.put(Types.STRUCT, new JdbcTypeInformation("STRUCT",Object.class.getName()));
        typeMap.put(Types.TIME,new JdbcTypeInformation("TIME", Date.class.getName()));
        typeMap.put(Types.TIMESTAMP, new JdbcTypeInformation("TIMESTAMP",Date.class.getName()));
        typeMap.put(Types.TINYINT, new JdbcTypeInformation("TINYINT",Byte.class.getName()));
        typeMap.put(Types.VARBINARY, new JdbcTypeInformation("VARBINARY","byte[]"));
        typeMap.put(Types.VARCHAR, new JdbcTypeInformation("VARCHAR",String.class.getName()));
        
    }
    
    public static String getColumnTypeName(int columnType){
        return typeMap.get(columnType).getJdbcTypeName();
    }

    public static String getColumnClassName(int columnType){
        return typeMap.get(columnType).getJavaClassName();
    }
    
}
