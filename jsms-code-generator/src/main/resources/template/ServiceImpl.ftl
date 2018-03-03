package ${packageName}.${moduleName}.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ${packageName}.${moduleName}.mapper.Jsms${table.className}Mapper;
import ${packageName}.${moduleName}.entity.Jsms${table.className};
import ${packageName}.${moduleName}.service.Jsms${table.className}Service;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description ${table.description}
 * @author ${author}
 * @date ${now}
 */
@Service
public class Jsms${table.className}ServiceImpl implements Jsms${table.className}Service{

    @Autowired
    private Jsms${table.className}Mapper ${table.classInstanceName}Mapper;
    
    @Override
    public int insert(Jsms${table.className} model) {
        return this.${table.classInstanceName}Mapper.insert(model);
    }

    @Override
    public int insertBatch(List<Jsms${table.className}> modelList) {
        return this.${table.classInstanceName}Mapper.insertBatch(modelList);
    }

	@Override
    public int update(Jsms${table.className} model) {
		Jsms${table.className} old = this.${table.classInstanceName}Mapper.getBy${table.capitalObjectKey}(model.get${table.capitalObjectKey}());
		if(old == null){
			return 0;
		}
		return this.${table.classInstanceName}Mapper.update(model); 
    }

    @Override
    public int updateSelective(Jsms${table.className} model) {
		Jsms${table.className} old = this.${table.classInstanceName}Mapper.getBy${table.capitalObjectKey}(model.get${table.capitalObjectKey}());
		if(old != null)
        	return this.${table.classInstanceName}Mapper.updateSelective(model);
		return 0;
    }

    @Override
    public Jsms${table.className} getBy${table.capitalObjectKey}(${table.objectKeyType} ${table.objectKey}) {
        Jsms${table.className} model = this.${table.classInstanceName}Mapper.getBy${table.capitalObjectKey}(${table.objectKey});
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<Jsms${table.className}> list = this.${table.classInstanceName}Mapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<Jsms${table.className}> findList(Map params) {
        List<Jsms${table.className}> list = this.${table.classInstanceName}Mapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.${table.classInstanceName}Mapper.count(params);
    }
    
}
