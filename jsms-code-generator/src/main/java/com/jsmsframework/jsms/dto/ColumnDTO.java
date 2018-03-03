package com.jsmsframework.jsms.dto;

import com.jsmsframework.util.NameUtil;

/**
 * 字段DTO
 * 
 * @author huangwenjie
 * @date 2017-01-24
 */
public class ColumnDTO {

	private String columnType; // 字段类型

	private String columnName; // 字段名

	private String propertyName; // 字段名对应的Java属性名

	private String capitalPropertyName; // 首字母大写的Java属性名

	private String javaType; // 不含包名

	private String fullJavaType; // 包含包名

	private boolean isNull; // 列是否可为空，可以为空为true，否则为false

	private String accessType;

	private int columnSize;

	private String comment; // 注释

	private String jdbcType; // jdbc类型

	public String getCapitalPropertyName() {
		return NameUtil.capital(this.propertyName);
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getFullJavaType() {
		return fullJavaType;
	}

	public void setFullJavaType(String fullJavaType) {
		this.fullJavaType = fullJavaType;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public boolean getIsNull() {
		return isNull;
	}

	public void setIsNull(boolean isNull) {
		this.isNull = isNull;
	}

}
