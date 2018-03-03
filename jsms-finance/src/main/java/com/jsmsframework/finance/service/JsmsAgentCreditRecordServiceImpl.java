package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.finance.dto.JsmsAgentCreditRecordDTO;
import com.jsmsframework.finance.entity.JsmsAgentCreditRecord;
import com.jsmsframework.finance.mapper.JsmsAgentCreditRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description 代理商授信记录
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsAgentCreditRecordServiceImpl implements JsmsAgentCreditRecordService {

    @Autowired
    private JsmsAgentCreditRecordMapper agentCreditRecordMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAgentCreditRecord model) {
        return this.agentCreditRecordMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAgentCreditRecord> modelList) {
        return this.agentCreditRecordMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentCreditRecord model) {
		JsmsAgentCreditRecord old = this.agentCreditRecordMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.agentCreditRecordMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentCreditRecord model) {
		JsmsAgentCreditRecord old = this.agentCreditRecordMapper.getById(model.getId());
		if(old != null)
        	return this.agentCreditRecordMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAgentCreditRecord getById(Long id) {
        JsmsAgentCreditRecord model = this.agentCreditRecordMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentCreditRecord> list = this.agentCreditRecordMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.agentCreditRecordMapper.count(params);
    }


    @Override
    public List<JsmsAgentCreditRecordDTO> creditHistories(Integer agentId) {
        List<JsmsAgentCreditRecordDTO> result = new ArrayList<>();
        JsmsPage page = new JsmsPage();
        page.getParams().put("agentId",agentId);
        page.setRows(99999);
        List<JsmsAgentCreditRecord> jsmsAgentCreditRecords = this.agentCreditRecordMapper.queryList(page);
        if(jsmsAgentCreditRecords==null||jsmsAgentCreditRecords.isEmpty())
            return result;

        Collections.sort(jsmsAgentCreditRecords, new Comparator<JsmsAgentCreditRecord>() { //时间倒序排序
            @Override
            public int compare(JsmsAgentCreditRecord o1, JsmsAgentCreditRecord o2) {
                if(o1.getCreateTime().getTime()<o2.getCreateTime().getTime())
                    return 1;
                if(o1.getCreateTime().getTime()>o2.getCreateTime().getTime())
                    return -1;
                return 0;
            }
        });

        for(int i=0;i<jsmsAgentCreditRecords.size();i++){
            JsmsAgentCreditRecord jsmsAgentCreditRecord = jsmsAgentCreditRecords.get(i);
            JsmsAgentCreditRecordDTO dto = new JsmsAgentCreditRecordDTO();
            BeanUtil.copyProperties(jsmsAgentCreditRecord,dto);
            dto.setBeginTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dto.getCreateTime()));

            result.add(dto);
        }

        for(int i=0;i<result.size();i++){
            JsmsAgentCreditRecordDTO dto = result.get(i);
            if(i==0){
                dto.setEndTIme("至今");
            }
            if(result.size()-1>i){
                JsmsAgentCreditRecordDTO before = result.get(i+1);
                before.setEndTIme(dto.getBeginTime());
            }
        }
        return result;
    }
}
