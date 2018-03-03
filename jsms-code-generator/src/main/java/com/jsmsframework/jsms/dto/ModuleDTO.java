package com.jsmsframework.jsms.dto;

import java.util.Map;
import java.util.TreeMap;


/**
 * 模块DTO
 * 
 * @author huangwenjie
 * @date 2017-01-24
 */
public class ModuleDTO {

    private String moduleName;          // 模块名称

    private String moduleConstants;     // 模块常量

    private String moduleNameZh;        // 模块中文名称

    // key为表名
    public Map<String, TableDTO> tableMap = new TreeMap<String, TableDTO>();

    public Object getTableDTO(String id) {
        return tableMap.get(id);
    }

    public void addTableDTO(TableDTO dto) {
        tableMap.put(dto.getTableName(), dto);
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleNameZh() {
        return moduleNameZh;
    }

    public void setModuleNameZh(String moduleNameZh) {
        this.moduleNameZh = moduleNameZh;
    }

    public String getModuleConstants() {
        return moduleConstants;
    }

    public void setModuleConstants(String moduleConstants) {
        this.moduleConstants = moduleConstants;
    }

}
