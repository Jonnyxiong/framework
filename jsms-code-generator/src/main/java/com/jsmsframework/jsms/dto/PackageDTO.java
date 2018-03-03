package com.jsmsframework.jsms.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 包DTO
 * 
 * @author huangwenjie
 * @date 2017-01-24
 */
public class PackageDTO {

	private String packageName; // 包名

	private String author; // 作者

	private String date; // 日期

	private String company; // 公司

	private String destDir; // 代码保存路径

	// String key为模块的英文名
	public Map<String, ModuleDTO> moduleMap = new HashMap<String, ModuleDTO>();

	public Object getModuleDTO(String moduleName) {
		return moduleMap.get(moduleName);
	}

	public void addModuleDTO(ModuleDTO dto) {
		moduleMap.put(dto.getModuleName(), dto);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getDestDir() {
		return destDir;
	}

	public void setDestDir(String destDir) {
		this.destDir = destDir;
	}

}
