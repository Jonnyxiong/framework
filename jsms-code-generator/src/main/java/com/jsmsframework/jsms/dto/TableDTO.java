package com.jsmsframework.jsms.dto;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.jsmsframework.util.NameUtil;

/**
 * 表DTO
 * 
 * @author huangwenjie
 * @date 2017-01-24
 */
public class TableDTO {

	private String tableName; // 表名

	private String className; // 类名

	private String classInstanceName; // 类名


	private String path; // url路径

	private String menuClass; // 菜单编码所在的常量类，包括包名

	private String description; // 描述

	private String dbKey; // 主键字段，如果设置了，在JSP将生成复选框以满足批量操作

	private String objectKey; // dbKey对应的类id

	private String capitalObjectKey; // dbKey对应的类id，首部大写
	
	private String objectKeyType; // dbKey对应的类id的类型

	private String overwrite;


	private String tablePrefix;

	public Map<String, String> typeMap;



	// key为字段名
	public Map<String, ColumnDTO> columnMap = new LinkedHashMap<String, ColumnDTO>();

	
	
	public void setObjectKey(String objectKey) {
		this.objectKey = objectKey;
	}


	public String getCapitalObjectKey() {
		return NameUtil.capital(getObjectKey());
	}
 

	public Object getColumnDTO(String columnName) {
		return columnMap.get(columnName);
	}

	public void addColumnDTO(ColumnDTO dto) {
		columnMap.put(dto.getColumnName(), dto);
	}

	public Map<String, ColumnDTO> getColumnMap() {
		return columnMap;
	}

	public void setColumnMap(Map<String, ColumnDTO> columnMap) {
		this.columnMap = columnMap;
	}

	public void addTypeMap(String javaType, String columnClassName) {
		if (typeMap == null) {
			typeMap = new HashMap();
		}

		typeMap.put(javaType, columnClassName);
	}

	public Map<String, String> getTypeMap() {
		return typeMap;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String comment) {
		this.description = comment;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMenuClass() {
		return menuClass;
	}

	public void setMenuClass(String menuClass) {
		this.menuClass = menuClass;
	}

	 

	public String getDbKey() {
		return dbKey;
	}

	public void setDbKey(String dbKey) {
		this.dbKey = dbKey;
	}

	
	public String getObjectKey() {
		return NameUtil.convertToPropertiesNameByColumnName(this.dbKey);
	}

	
	
	public String getObjectKeyType() {
		return objectKeyType;
	}

	public void setObjectKeyType(String objectKeyType) {
		this.objectKeyType = objectKeyType;
	}

 
	public String getClassInstanceName() {
		if (classInstanceName == null)
			return NameUtil.convertToPropertiesNameByColumnName(className);
		return classInstanceName;
	}

	public void setClassInstanceName(String classInstanceName) {
		this.classInstanceName = classInstanceName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getTablePrefix() {
		return tablePrefix;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}


	public String getOverwrite() {
		return overwrite;
	}

	public void setOverwrite(String overwrite) {
		this.overwrite = overwrite;
	}
}
