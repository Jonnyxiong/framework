package com.jsmsframework.audit.service;

import com.jsmsframework.audit.entity.JsmsAudit;
import com.jsmsframework.audit.entity.JsmsAuditCgroupRefClient;
import com.jsmsframework.audit.entity.JsmsAuditClientGroup;
import com.jsmsframework.audit.exception.JsmsAuditClientGroupException;
import com.jsmsframework.audit.mapper.JsmsAuditClientGroupMapper;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 审核用户组
 * @author huangwenjie
 * @date 2017-10-31
 */
@Service
public class JsmsAuditClientGroupServiceImpl implements JsmsAuditClientGroupService{

    private static Logger logger = LoggerFactory.getLogger(JsmsAuditClientGroupServiceImpl.class);

    @Autowired
    private JsmsAuditClientGroupMapper auditClientGroupMapper;
    @Autowired
    private JsmsAuditCgroupRefClientService jsmsAuditCgroupRefClientService;
    @Override
	@Transactional
    public int insert(JsmsAuditClientGroup model) {
        return this.auditClientGroupMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAuditClientGroup> modelList) {
        return this.auditClientGroupMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAuditClientGroup model) {
		JsmsAuditClientGroup old = this.auditClientGroupMapper.getByCgroupId(model.getCgroupId());
		if(old == null){
			return 0;
		}
		return this.auditClientGroupMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAuditClientGroup model) {
		JsmsAuditClientGroup old = this.auditClientGroupMapper.getByCgroupId(model.getCgroupId());
		if(old != null)
        	return this.auditClientGroupMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAuditClientGroup getByCgroupId(Integer cgroupId) {
        JsmsAuditClientGroup model = this.auditClientGroupMapper.getByCgroupId(cgroupId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAuditClientGroup> list = this.auditClientGroupMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.auditClientGroupMapper.count(params);
    }

    @Override
    public int checkcgroupName(String cgroupName,Integer cgroupId) {
        return auditClientGroupMapper.checkCgroupName(cgroupName,cgroupId);
    }

    @Override
    public int checkcgroupNameOfInsert(String cgroupName) {
        return auditClientGroupMapper.checkcgroupNameOfInsert(cgroupName);
    }

    @Override
    public int deteleJsmsAuditClientGroup(Integer cgroupId) {
        int deteleFlag =0;
        int unboundFlag =0;
        JsmsAuditClientGroup jsmsAuditClientGroup = auditClientGroupMapper.getByCgroupId(cgroupId);
        logger.debug("删除用户组, jsmsAuditClientGroup={}", JsonUtil.toJson(jsmsAuditClientGroup));
        deteleFlag = auditClientGroupMapper.deteleJsmsAuditClientGroup(cgroupId);
        if(deteleFlag>0){
            //删除用户组要解除绑定的用户
            JsmsPage page = new JsmsPage();
            page.setRows(-1);
            page.getParams().put("cgroupId",cgroupId);
            List<JsmsAuditCgroupRefClient> list = jsmsAuditCgroupRefClientService.queryList(page).getData();
            logger.debug("删除用户组关联的信息, jsmsAuditCgroupRefClients={}", JsonUtil.toJson(list));
            unboundFlag = jsmsAuditCgroupRefClientService.deteleJsmsAuditCgroupRefClient(cgroupId);
        }
        if(deteleFlag>0){
            return 1;
        }else{
            throw new JsmsAuditClientGroupException("删除用户组失败");
        }
    }

    @Override
    public int getKgroupIdToClient(Integer kgroupId) {
        return auditClientGroupMapper.getKgroupIdToClient(kgroupId);
    }

    @Override
    public List<JsmsAuditClientGroup> getKgroupIdsByClientId(String clientId) {
        List<JsmsAuditClientGroup> auditClientGroups = auditClientGroupMapper.getKgroupIdsByClientId(clientId);
        if(auditClientGroups == null || auditClientGroups.isEmpty()){
            return auditClientGroupMapper.getDefaultKgroupIds();
        }
        return auditClientGroups;
    }


    @Override
    public List<JsmsAuditClientGroup> getByKgroupId(Integer kgroupId) {
        return auditClientGroupMapper.getByKgroupId(kgroupId);
    }
}
