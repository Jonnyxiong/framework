package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.ProductType;
import com.jsmsframework.order.entity.JsmsOemClientPool;
import com.jsmsframework.order.exception.JsmsOemAgentPoolException;
import com.jsmsframework.order.mapper.JsmsOemClientPoolMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @description OEM代理商客户短信池
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsOemClientPoolServiceImpl implements JsmsOemClientPoolService{

    private static final Logger LOGGER = LoggerFactory.getLogger(JsmsOemClientPoolServiceImpl.class);

    @Autowired
    private JsmsOemClientPoolMapper oemClientPoolMapper;
    
    @Override
	@Transactional
    public int insert(JsmsOemClientPool model) {
        return this.oemClientPoolMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOemClientPool> modelList) {
        return this.oemClientPoolMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOemClientPool model) {
		JsmsOemClientPool old = this.oemClientPoolMapper.getByClientPoolId(model.getClientPoolId());
		if(old == null){
			return 0;
		}
		return this.oemClientPoolMapper.update(model); 
    }

	@Override
    public int updateRemainNumber(JsmsOemClientPool model) {
		JsmsOemClientPool old = this.oemClientPoolMapper.getByClientPoolId(model.getClientPoolId());
		if(old == null){
			return 0;
		}
		return this.oemClientPoolMapper.updateRemainNumber(model);
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOemClientPool model) {
		JsmsOemClientPool old = this.oemClientPoolMapper.getByClientPoolId(model.getClientPoolId());
		if(old != null)
        	return this.oemClientPoolMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOemClientPool getByClientPoolId(Long clientPoolId) {
        JsmsOemClientPool model = this.oemClientPoolMapper.getByClientPoolId(clientPoolId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOemClientPool> list = this.oemClientPoolMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.oemClientPoolMapper.count(params);
    }

    @Override
    public JsmsOemClientPool getByClientPoolInfo(JsmsOemClientPool jsmsOemClientPool) {
        JsmsOemClientPool model = this.oemClientPoolMapper.getByClientPoolInfo(jsmsOemClientPool);
        return model;
    }

    @Override
    public List<JsmsOemClientPool> getListByClientPoolInfo(JsmsOemClientPool jsmsOemClientPool) {
        List<JsmsOemClientPool> list = this.oemClientPoolMapper.getListByClientPoolInfo(jsmsOemClientPool);
        return list;
    }

    @Override
    public List<JsmsOemClientPool> queryRemainQuantityClientPoolInfo(JsmsOemClientPool model) {
        return this.oemClientPoolMapper.queryRemainQuantityClientPoolInfo(model);
    }

    @Override
    public List<JsmsOemClientPool> getListByPoolInfoAndDueTimeRange(JsmsOemClientPool jsmsOemClientPool,Date beginStartTime, Date endStartTime) {
//        List<JsmsOemClientPool> list = this.oemClientPoolMapper.getListByPoolInfoAndDueTimeRange(jsmsOemClientPool,beginStartTime,endStartTime);
        Map params = new HashMap();
        params.put("model", jsmsOemClientPool);
        params.put("beginStartTime", beginStartTime);
        params.put("endStartTime", endStartTime);
//        List<JsmsOemClientPool> list = this.oemClientPoolMapper.getListByPoolInfoAndDueTimeRange(jsmsOemClientPool,beginStartTime,endStartTime);
        List<JsmsOemClientPool> list = this.oemClientPoolMapper.getListByPoolInfoAndDueTimeRange(params);
        return list;
    }

    @Override
    @Transactional
    public void updateForAddClientPoolRemainNum(Long clientPoolId, BigDecimal addNum, Integer productType, Date date) {
        JsmsOemClientPool poolAndNumInfo = new JsmsOemClientPool();

        poolAndNumInfo.setClientPoolId(clientPoolId);
        if(ProductType.国际.getValue().equals(productType)){
            poolAndNumInfo.setTotalAmount(addNum);
            poolAndNumInfo.setRemainAmount(addNum);
            poolAndNumInfo.setProductType(productType);
        }else {
            poolAndNumInfo.setTotalNumber(addNum.intValue());
            poolAndNumInfo.setRemainNumber(addNum.intValue());
            poolAndNumInfo.setProductType(productType);
        }
        poolAndNumInfo.setUpdateTime(date);
        this.oemClientPoolMapper.updateForAddClientPoolRemainNum(poolAndNumInfo);
    }

    @Override
    @Transactional
    public BigDecimal updateForReduceClientPoolRemainNum(Long clientPoolId, BigDecimal reduceNum, Integer productType, Date date) {
        int casNum = 0; // CAS 扣减短信池次数
        boolean updateSuccess = false;
        BigDecimal reduceNumActual = BigDecimal.ZERO; // 本次扣费实际扣减数量
        while (true){
            casNum ++;
            JsmsOemClientPool clientPool = this.oemClientPoolMapper.getByClientPoolId(clientPoolId);
            Map<String, Object> sqlParams = new HashMap<>();
            sqlParams.put("clientPoolId", clientPoolId);
            sqlParams.put("updateTime", date);
            sqlParams.put("productType", productType);

            if (ProductType.国际.getValue().equals(productType)) {
                // 池子里面国际短信剩余金额小于请求扣减数量
                if(clientPool.getRemainAmount().compareTo(reduceNum) == -1) {
                    reduceNumActual = clientPool.getRemainAmount();
                    sqlParams.put("reduceNumActual", reduceNumActual);
                }else{
                    reduceNumActual = reduceNum;
                    sqlParams.put("reduceNumActual", reduceNumActual);
                }
                sqlParams.put("remainAmount", clientPool.getRemainAmount());
            } else {
                // 池子里面国内短信剩余数量小于请求扣减数量
                BigDecimal clientPoolRemainNumber = new BigDecimal(clientPool.getRemainNumber());
                if(clientPoolRemainNumber.compareTo(reduceNum) == -1) {
                    reduceNumActual = new BigDecimal(clientPool.getRemainNumber());
                    sqlParams.put("reduceNumActual", reduceNumActual);
                }else{
                    reduceNumActual = reduceNum;
                    sqlParams.put("reduceNumActual", reduceNumActual);
                }
                sqlParams.put("remainNumber", clientPool.getRemainNumber());
            }

            int updateNum = this.oemClientPoolMapper.updateForReduceClientPoolRemainNum(sqlParams);

            if(updateNum == 1) {
                updateSuccess = true;
                break;
            } else if(casNum > 3){
                updateSuccess = false;
                break;
            }else{
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    LOGGER.error("OEM代理商短信池扣减时发生异常 e={}", e);
                    throw new JsmsOemAgentPoolException("OEM代理商短信池扣减时发生异常");
                }
                continue;
            }
        }

        if (updateSuccess){
            return reduceNumActual;
        }else{
            throw new JsmsOemAgentPoolException("OEM代理商短信池扣减3次失败");
        }

    }

    @Override
    public List<JsmsOemClientPool> findSUMTotal(Set clientIds) {
        return this.oemClientPoolMapper.findSUMTotal(clientIds);
    }
}
