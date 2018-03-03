package com.jsmsframework.jsms.parse;

/**
 * Java类型信息
 * @author huangwenjie
 * @date 2017-01-28
 */
public class JdbcTypeInformation {

    private String jdbcTypeName;

    private String javaClassName;

    public JdbcTypeInformation(String jdbcTypeName, String javaClassName) {
        this.jdbcTypeName = jdbcTypeName;
        this.javaClassName = javaClassName;
    }

    public String getJdbcTypeName() {
        return jdbcTypeName;
    }

    public void setJdbcTypeName(String jdbcTypeName) {
        this.jdbcTypeName = jdbcTypeName;
    }

    public String getJavaClassName() {
        return javaClassName;
    }

    public void setJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
    }

}
