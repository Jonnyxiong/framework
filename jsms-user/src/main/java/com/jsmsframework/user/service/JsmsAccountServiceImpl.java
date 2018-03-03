package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsAccount;
import com.jsmsframework.user.mapper.JsmsAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description 用户信息表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsAccountServiceImpl implements JsmsAccountService{

    @Autowired
    private JsmsAccountMapper accountMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAccount model) {
        return this.accountMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAccount> modelList) {
        return this.accountMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAccount model) {
		JsmsAccount old = this.accountMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.accountMapper.update(model); 
    }

	@Override
	@Transactional
    public int updateByClientId(JsmsAccount model) {
		JsmsAccount old = this.accountMapper.getByClientId(model.getClientid());
		if(old == null){
			return 0;
		}
        model.setId(old.getId());
        return this.accountMapper.updateSelective(model);
    }

    @Override
    public int updateSelective(JsmsAccount model) {
		JsmsAccount old = this.accountMapper.getById(model.getId());
		if(old != null)
        	return this.accountMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsAccount getById(String id) {
        JsmsAccount model = this.accountMapper.getById(id);
		return model;
    }

    @Override
    public JsmsAccount getByClientId(String clientid) {
        JsmsAccount model = this.accountMapper.getByClientId(clientid);
		return model;
    }

    @Override
    public List<JsmsAccount> getByIds(Set<String> clientIds) {
        List<JsmsAccount> model = this.accountMapper.getByIds(clientIds);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAccount> list = this.accountMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsAccount> queryAll(Map params) {
        return this.accountMapper.queryAll(params);
    }

    @Override
    public List<JsmsAccount> getLikeIdAndName(Map params) {
        return this.accountMapper.getLikeIdAndName(params);
    }

    @Override
    public List<JsmsAccount> findAllList(String clientId) {
        return this.accountMapper.findAllList(clientId);
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.accountMapper.count(params);
    }

    @Override
    @Transactional
    public int updateBelongSale(JsmsAccount model) {
        return this.accountMapper.updateBelongSale(model);
    }

    @Override
    public List<JsmsAccount> findAllListOfOperation(Long belongSale) {
        return this.accountMapper.findAllListOfOperation(belongSale);
    }

    @Override
    public List<JsmsAccount> findListForReturnQuantity(Integer agentId) {
        return this.accountMapper.findListForReturnQuantity(agentId);
    }

    @Override
    public List<JsmsAccount> getByName(String name) {
        List<JsmsAccount> model = this.accountMapper.getByName(name);
        return model;
    }

    @Override
    public List<Map<String, Object>> findALLAccountForSearch() {
        return this.accountMapper.findALLAccountForSearch();
    }

    @Override
    public List<JsmsAccount> getAllAccount(String leftContent) {
        return accountMapper.getAllAccount(leftContent);
    }

    @Override
    public List<JsmsAccount> getAllAccountOfExist(Integer cgroupId,String rightContent) {
        return accountMapper.getAllAccountOfExist(cgroupId,rightContent);
    }

    @Override
    public List<JsmsAccount> findList(JsmsAccount model, Set agentIds) {
        return accountMapper.findList(model, agentIds);
    }

    @Override
    public List<JsmsAccount> findAllListExist(String clientId, String type) {
        return accountMapper.findAllListExist(clientId, type);
    }

    @Override
    public boolean checkAccountIsCancel(String clientId) {
        int count = accountMapper.checkAccountIsCancel(clientId);
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<JsmsAccount> loadAllForSelect(String webId) {
        return accountMapper.loadAllForSelect(webId);
    }



    @Override
    public List<JsmsAccount> getAllAccountWithoutSysClientGroup(String leftContent) {
        return this.accountMapper.getAllAccountWithoutSysClientGroup(leftContent);
    }

    @Override
    public List<JsmsAccount> getAccountOfSysClientGroup(Integer cgroupId, String rightContent) {
        return this.accountMapper.getAccountOfSysClientGroup(cgroupId,rightContent);
    }
}
