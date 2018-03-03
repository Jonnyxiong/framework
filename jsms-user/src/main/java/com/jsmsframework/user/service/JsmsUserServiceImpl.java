package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsUser;
import com.jsmsframework.user.mapper.JsmsUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description 用户表
 * @author NiuT
 * @date 2017-08-10
 */
@Service
public class JsmsUserServiceImpl implements JsmsUserService{

    @Autowired
    private JsmsUserMapper userMapper;

    @Override
    @Transactional
    public int insert(JsmsUser model) {
        return this.userMapper.insert(model);
    }

    @Override
    @Transactional
    public int insertBatch(List<JsmsUser> modelList) {
        return this.userMapper.insertBatch(modelList);
    }

    @Override
    @Transactional
    public int update(JsmsUser model) {
        JsmsUser old = this.userMapper.getById(String.valueOf(model.getId()));
        if(old == null){
            return 0;
        }
        return this.userMapper.update(model);
    }

    @Override
    @Transactional
    public int updateSelective(JsmsUser model) {
        JsmsUser old = this.userMapper.getById(String.valueOf(model.getId()));
        if(old != null)
            return this.userMapper.updateSelective(model);
        return 0;
    }

    @Override
    public JsmsUser getById(String id) {
        JsmsUser model = this.userMapper.getById(id);
        return model;
    }

    @Override
    public JsmsUser getById2(Long id) {
        JsmsUser model = this.userMapper.getByIdByOld(id);
        return model;
    }

    @Override
    public List<JsmsUser> getByIds(Set<Long> ids) {
        List<JsmsUser> models = this.userMapper.getByIds(ids);
        return models;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsUser> list = this.userMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
        return this.userMapper.count(params);
    }

    @Override
    public List<JsmsUser> getByRealname(String realname) {
        List<JsmsUser> model = this.userMapper.getByRealname(realname);
        return model;
    }

    @Override
    public JsmsUser checkOemEmailAndMobileInUser(String email, String mobile) {
        return userMapper.checkOemEmailAndMobileInUser(email,mobile);
    }

    @Override
    public JsmsUser agentApplyCheckInUser(String email, String mobile) {
        return userMapper.agentApplyCheckInUser(email,mobile);
    }

    @Override
    public List<JsmsUser> getSaleInfoByUserId(Map<String, Object> params) {
        return userMapper.getSaleInfoByUserId(params);
    }

    @Override
    public Integer querySmsUserCountByEmail(String email) {
        return userMapper.querySmsUserCountByEmail(email);
    }
    @Override
    public Integer querySmsUserCountByMobile(String mobile) {
        return userMapper.querySmsUserCountByMobile(mobile);
    }

    @Override
    public Long getId(String email, String mobile) {
        return userMapper.getId(email,mobile);
    }

    @Override
    public List<JsmsUser> queryAll() {
        return userMapper.queryAll();
    }

    @Override
	public List<JsmsUser> getUserByRoleName(String roleName) {
        List<JsmsUser> model = this.userMapper.getUserByRoleName(roleName);
        return model;
    }
}
