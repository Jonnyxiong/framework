package ${packageName}.${moduleName}.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import ${packageName}.${moduleName}.entity.Jsms${table.className};
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description ${table.description}
 * @author ${author}
 * @date ${now}
 */
@Repository
public interface Jsms${table.className}Mapper{

	int insert(Jsms${table.className} model);
	
	int insertBatch(List<Jsms${table.className}> modelList);

	
	int update(Jsms${table.className} model);
	
	int updateSelective(Jsms${table.className} model);

    Jsms${table.className} getBy${table.capitalObjectKey}(${table.objectKeyType} ${table.objectKey});

	@SimpleCountSQL
	List<Jsms${table.className}> queryList(JsmsPage<Jsms${table.className}> page);

	List<Jsms${table.className}> findList(Map params);

	int count(Map<String,Object> params);

}