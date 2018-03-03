package ${packageName}.${moduleName}.entity;

<#if table.typeMap??>
    <#list table.typeMap?keys as itemKey>
import ${table.typeMap[itemKey]};
    </#list>
</#if>

/**
 * @description ${table.description}
 * @author ${author}
 * @date ${now}
 */
public class Jsms${table.className} {
    
    <#list table.columnMap?keys as itemKey>
            <#if !filterColumns?seq_contains('${table.columnMap[itemKey].propertyName}') >
    // ${table.columnMap[itemKey].comment}
    private ${table.columnMap[itemKey].javaType} ${table.columnMap[itemKey].propertyName};
            </#if>
    </#list>    
    
    <#list table.columnMap?keys as itemKey>
        <#if !filterColumns?seq_contains('${table.columnMap[itemKey].propertyName}') >
    public ${table.columnMap[itemKey].javaType} get${table.columnMap[itemKey].capitalPropertyName}() {
        return ${table.columnMap[itemKey].propertyName};
    }
    
    public void set${table.columnMap[itemKey].capitalPropertyName}(${table.columnMap[itemKey].javaType} ${table.columnMap[itemKey].propertyName}) {
        this.${table.columnMap[itemKey].propertyName} = ${table.columnMap[itemKey].propertyName} ;
    }
    
        </#if>
    </#list>
}