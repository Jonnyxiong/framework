package com.jsmsframework.user.service;

import com.google.common.collect.Lists;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsClientidSequence;
import com.jsmsframework.user.exception.JsmsClientidSequenceException;
import com.jsmsframework.user.mapper.JsmsClientidSequenceMapper;
import com.ucpaas.sms.common.util.NumConverUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 客户账号clientid序列表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsClientidSequenceServiceImpl implements JsmsClientidSequenceService{
    /**
     * 获取客户账号时, 为避免并发, 作5次超时处理
     */
    private static final int MAX_TRY_GENERATE_CLINETID_COUNT = 5;
    @Autowired
    private JsmsClientidSequenceMapper clientidSequenceMapper;
    
    @Override
	@Transactional
    public int insert(JsmsClientidSequence model) {
        return this.clientidSequenceMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsClientidSequence> modelList) {
        return this.clientidSequenceMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsClientidSequence model) {
		JsmsClientidSequence old = this.clientidSequenceMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.clientidSequenceMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsClientidSequence model) {
		JsmsClientidSequence old = this.clientidSequenceMapper.getById(model.getId());
		if(old != null)
        	return this.clientidSequenceMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsClientidSequence getById(Long id) {
        JsmsClientidSequence model = this.clientidSequenceMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsClientidSequence> list = this.clientidSequenceMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.clientidSequenceMapper.count(params);
    }

    /**
     * @Description: 从clientid序列表中(按规则生成共6位,36位递增,首位为a-z,末位为0-9),获取当前可用clientid(默认去可用状态中的最小的序列)
     * @author: Niu.T
     * @date: 2016年9月3日 下午4:32:21
     * @return: String
     */
    @Override
//    @Transactional
    public synchronized String getOrAddId() {
        int count = 0;
        return doGetOrAddId(count);
    }

    private String doGetOrAddId(int count) {
        Map<String, Object> data = new HashMap<String, Object>();

        // 随机取出未使用的序列
        JsmsClientidSequence clientIdSequence = this.clientidSequenceMapper.getUnusedRandom();

        // 生成帐号
        if (clientIdSequence == null) {
            String max = this.clientidSequenceMapper.getMax();

            List<String> numbers = Lists.newLinkedList();

            String clientId = "azzzz9";// 默认 9zzzz是 值a0000小1的36进制数字,最后一个9时占位的
            // ,a0000(三十六进制) = 16796160(十进制)
            if (StringUtils.isNotBlank(max)) {
                clientId = max;
            }
            long id = NumConverUtil.converToDecimal(clientId.substring(0, clientId.length() - 1));
            for (int i = 1; i <= 1000; i++) {
                for (int j = 0; j < 10; j++) {
                    numbers.add(NumConverUtil.converTo36HEX((id + i), "") + j);
                }
            }

            this.clientidSequenceMapper.batchAdd(numbers);

            // 重新获取
            clientIdSequence = this.clientidSequenceMapper.getUnusedRandom();
        }

        // 更新lock字段为1表示临时占用，更新lock_start_time表示开始占用时间，ucpaas-sms-task会检查占用超过30分钟的记录并修改lock为0
        int lock = this.clientidSequenceMapper.lock(clientIdSequence.getClientid());

        while(lock < 1){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
            count++;
            if(count > MAX_TRY_GENERATE_CLINETID_COUNT){
                throw new JsmsClientidSequenceException("当前访问量较大，请稍等片刻");
            }
            return doGetOrAddId(count);
        }
        return clientIdSequence.getClientid();
    }

    @Override
    public boolean updateClientIdStatus(String clientId) {
        boolean result = false;
        if (StringUtils.isNotBlank(clientId)) {
            result = this.clientidSequenceMapper.updateStatus(clientId) > 0;
        }
        return result;
    }
}
