package ${packageName}.${moduleName}.service;

import java.util.Map;
import java.util.List;

import ${packageName}.${moduleName}.entity.Jsms${table.className};

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description ${table.description}
 * @author ${author}
 * @date ${now}
 */
public interface Jsms${table.className}Service {

    int insert(Jsms${table.className} model);
    
    int insertBatch(List<Jsms${table.className}> modelList);

    int update(Jsms${table.className} model);
    
    int updateSelective(Jsms${table.className} model);

    Jsms${table.className} getBy${table.capitalObjectKey}(${table.objectKeyType} ${table.objectKey});
    
    JsmsPage queryList(JsmsPage page);

    List<Jsms${table.className}> findList(Map params);

    int count(Map<String,Object> params);
    
}
