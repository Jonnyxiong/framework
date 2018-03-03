package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsClientDict;
import com.jsmsframework.common.service.JsmsClientDictService;
import com.jsmsframework.user.entity.JsmsClientInfoExt;
import com.jsmsframework.user.mapper.JsmsClientInfoExtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @description 用户信息扩展表
 * @author lpjLiu
 * @date 2017-09-25
 */
@Service
public class JsmsClientInfoExtServiceImpl implements JsmsClientInfoExtService {

	/**
	 * 重点审核客户
	 */
	private final static String AUDIT_KEY_ACCOUNT = "audit_key_account";

	@Autowired
	private JsmsClientDictService clientDictService;

	@Autowired
	private JsmsClientInfoExtMapper clientInfoExtMapper;

	@Override
	public int insert(JsmsClientInfoExt model) {
		return this.clientInfoExtMapper.insert(model);
	}

	@Override
	public int insertBatch(List<JsmsClientInfoExt> modelList) {
		return this.clientInfoExtMapper.insertBatch(modelList);
	}

	@Override
	public int update(JsmsClientInfoExt model) {
		JsmsClientInfoExt old = this.clientInfoExtMapper.getByClientId(model.getClientid());
		if (old == null) {
			return 0;
		}
		return this.clientInfoExtMapper.update(model);
	}

	@Override
	public int updateSelective(JsmsClientInfoExt model) {
		JsmsClientInfoExt old = this.clientInfoExtMapper.getByClientId(model.getClientid());
		if (old != null)
			return this.clientInfoExtMapper.updateSelective(model);
		return 0;
	}

	@Override
	public JsmsClientInfoExt getByClientId(String clientid) {
		JsmsClientInfoExt model = this.clientInfoExtMapper.getByClientId(clientid);
		return model;
	}

	@Override
	public List<JsmsClientInfoExt> getByClientIds(Set<String> clientIds) {
		List<JsmsClientInfoExt> modelList = this.clientInfoExtMapper.getByClientIds(clientIds);
		return modelList;
	}

	@Override
	public JsmsPage queryList(JsmsPage page) {
		List<JsmsClientInfoExt> list = this.clientInfoExtMapper.queryList(page);
		return page;
	}

	@Override
	public int count(Map<String, Object> params) {
		return this.clientInfoExtMapper.count(params);
	}

	@Override
	public List<JsmsClientInfoExt> findList(JsmsClientInfoExt model) {
		return this.clientInfoExtMapper.findList(model);
	}

	@Override
    public List<JsmsClientInfoExt> getAuditKeyAccountInfoExt() {
		List<JsmsClientDict> paramTypeList = clientDictService.getByParamType(AUDIT_KEY_ACCOUNT);
		if(paramTypeList.isEmpty()){
			return null;
		}
		Set<Integer> starLevels = new TreeSet<>();
		for(JsmsClientDict dict : paramTypeList){
			starLevels.add(Integer.parseInt(dict.getParamKey()));
		}
		if(starLevels.isEmpty() ){
            return null;
        }
        List<JsmsClientInfoExt> clientInfoExts = clientInfoExtMapper.getByStarLevels(starLevels);
        return clientInfoExts;
	}

	@Override
	@Transactional
	public int updateByClientIdOfWeb(JsmsClientInfoExt model) {
       /* JsmsInfoExt old = this.accountMapper.getByClientIdOfInfoExt(model.getClientid());
        if(old == null){
            return 0;
        }
        model.setId(old.getId());*/
		return this.clientInfoExtMapper.updateSelectiveOfInfoExt(model.getWebPassword(),model.getClientid());
	}

}
