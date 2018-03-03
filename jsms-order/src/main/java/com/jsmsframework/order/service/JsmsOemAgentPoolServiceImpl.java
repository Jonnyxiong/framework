package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.enums.ProductType;
import com.jsmsframework.common.util.HttpUtil;
import com.jsmsframework.order.entity.JsmsOemAgentPool;
import com.jsmsframework.order.exception.JsmsOemAgentPoolException;
import com.jsmsframework.order.mapper.JsmsOemAgentPoolMapper;
import com.ucpaas.sms.common.util.DateUtils;
import com.ucpaas.sms.common.util.FmtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;


/**
 * @description OEM代理商短信池
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsOemAgentPoolServiceImpl implements JsmsOemAgentPoolService {
	private static final Logger LOGGER = LoggerFactory.getLogger(JsmsOemAgentPoolServiceImpl.class);

	@Autowired
	private JsmsOemAgentPoolMapper oemAgentPoolMapper;

	@Override
	@Transactional
	public int insert(JsmsOemAgentPool model) {
		return this.oemAgentPoolMapper.insert(model);
	}

	@Override
	@Transactional
	public int insertBatch(List<JsmsOemAgentPool> modelList) {
		return this.oemAgentPoolMapper.insertBatch(modelList);
	}

	@Override
	@Transactional
	public int update(JsmsOemAgentPool model) {
		JsmsOemAgentPool old = this.oemAgentPoolMapper.getByAgentPoolId(model.getAgentPoolId());
		if (old == null) {
			return 0;
		}
		return this.oemAgentPoolMapper.update(model);
	}

	@Override
	@Transactional
	public int updateSelective(JsmsOemAgentPool model) {
		JsmsOemAgentPool old = this.oemAgentPoolMapper.getByAgentPoolId(model.getAgentPoolId());
		if (old != null)
			return this.oemAgentPoolMapper.updateSelective(model);
		return 0;
	}

	@Override
	@Transactional
	public JsmsOemAgentPool getByAgentPoolId(Long agentPoolId) {
		JsmsOemAgentPool model = this.oemAgentPoolMapper.getByAgentPoolId(agentPoolId);
		return model;
	}

	@Override
	@Transactional
	public JsmsOemAgentPool getByAgentPoolInfo(JsmsOemAgentPool jsmsOemAgentPool) {
		JsmsOemAgentPool model = this.oemAgentPoolMapper.getByAgentPoolInfo(jsmsOemAgentPool);
		return model;
	}


    @Override
    public List<JsmsOemAgentPool> getListByAgentPoolInfo(JsmsOemAgentPool jsmsOemAgentPool) {
        List<JsmsOemAgentPool> list = this.oemAgentPoolMapper.getListByAgentPoolInfo(jsmsOemAgentPool);
        return list;
    }


	@Override
	@Transactional
	public JsmsPage queryList(JsmsPage page) {
		List<JsmsOemAgentPool> list = this.oemAgentPoolMapper.queryList(page);
		page.setData(list);
		return page;
	}

	@Override
	@Transactional
	public int count(Map<String, Object> params) {
		return this.oemAgentPoolMapper.count(params);
    }

    @Override
    @Transactional
    public BigDecimal updateForReduceAgentPoolRemainNum(Long agentPoolId, BigDecimal reduceNum, Integer productType, Date updateTime) {
        int casNum = 0; // CAS 扣减短信池次数
        boolean updateSuccess = false;
        BigDecimal reduceNumActual = BigDecimal.ZERO; // 本次扣费实际扣减数量
        while (true){
            casNum ++;
            JsmsOemAgentPool agentPool = this.oemAgentPoolMapper.getByAgentPoolId(agentPoolId);

            Map<String, Object> sqlParams = new HashMap<>();
			sqlParams.put("agentPoolId", agentPoolId);
			sqlParams.put("updateTime", updateTime);
			sqlParams.put("productType", productType);
            if (ProductType.国际.getValue().equals(productType)) {
                // 池子里面国际短信剩余金额小于请求扣减数量
                if(agentPool.getRemainAmount().compareTo(reduceNum) == -1) {
                    reduceNumActual = agentPool.getRemainAmount();
                    sqlParams.put("reduceNumActual", reduceNumActual);
                }else{
                    reduceNumActual = reduceNum;
					sqlParams.put("reduceNumActual", reduceNumActual);
                }
                sqlParams.put("remainAmount", agentPool.getRemainAmount());
            } else {
                // 池子里面国内短信剩余数量小于请求扣减数量
                if(new BigDecimal(agentPool.getRemainNumber()).compareTo(reduceNum) == -1) {
                    reduceNumActual = new BigDecimal(agentPool.getRemainNumber());
                    sqlParams.put("reduceNumActual", reduceNumActual);
                }else{
					reduceNumActual = reduceNum;
                	sqlParams.put("reduceNumActual", reduceNum);
                }
                sqlParams.put("remainNumber", agentPool.getRemainNumber());
            }

            int updateNum = this.oemAgentPoolMapper.reduceAgentPoolRemainNum(sqlParams);

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
					LOGGER.debug("OEM代理商短信池扣减时发生异常, e = {}", e);
					throw new JsmsOemAgentPoolException("OEM代理商短信池扣减时发生异常");
				}
				continue;
            }
        }

        if (updateSuccess){
            return reduceNumActual;
        }else{
			LOGGER.warn("OEM代理商短信池扣减3次失败");
            throw new JsmsOemAgentPoolException("您充值的数量大于剩余的数量，请重新充值!");
        }
    }

    @Override
    public int updateForAddAgentPoolRemainNum(Long oemAgentPoolId, BigDecimal addNum, Integer productType, Date updateTime) {
        JsmsOemAgentPool sqlParams = new JsmsOemAgentPool();
        sqlParams.setAgentPoolId(oemAgentPoolId);
        sqlParams.setUpdateTime(updateTime);
        if (ProductType.国际.getValue().equals(productType)) {
            // 充值国际短信
            sqlParams.setRemainAmount(addNum);
        } else {
            // 充值国内短信产品
            sqlParams.setRemainNumber(addNum.intValue());
        }
        return this.oemAgentPoolMapper.updateForAddAgentPoolRemainNum(sqlParams);
    }

	@Override
	public List<JsmsOemAgentPool> findList(JsmsOemAgentPool jsmsOemAgentPool) {
		return this.oemAgentPoolMapper.findList(jsmsOemAgentPool);
	}

	private R checkAgentPool(JsmsOemAgentPool jsmsOemAgentPool) {
		if (jsmsOemAgentPool == null)
			return R.error("参数为空");

		if (jsmsOemAgentPool.getAgentId() == null)
			return R.error("代理商ID不能为空");

		if (jsmsOemAgentPool.getProductType() == null)
			return R.error("产品类型不能为空");

		if (jsmsOemAgentPool.getOperatorCode() == null)
			return R.error("运营商不能为空");

		if (jsmsOemAgentPool.getAgentId() == null)
			return R.error("区域不能为空");

		return null;
	}

	/**
	 *
	 * R的data为Set列表，存储String类型的单价
	 *
	 * @param jsmsOemAgentPool
	 * @return
	 */
	@Override
	public R findPriceList(JsmsOemAgentPool jsmsOemAgentPool) {
		R r = checkAgentPool(jsmsOemAgentPool);
		if (r != null) {
			return r;
		}

		// 将单价设为空
		jsmsOemAgentPool.setUnitPrice(null);

		List<JsmsOemAgentPool> list = this.findList(jsmsOemAgentPool);

		if (list == null || list.isEmpty()) {
			return R.ok("获取代理商池价格列表成功", null);
		}

		// 将重复的去掉
		Set<String> prices = new TreeSet<>();
		for (JsmsOemAgentPool agentPool : list) {
			String unitPrice = FmtUtils.roundDownAsString(agentPool.getUnitPrice(), 4);
			if (prices.contains(unitPrice)) {
				continue;
			}

			prices.add(unitPrice);
		}

		return R.ok("获取代理商池价格列表成功", prices);
	}

	/**
	 *
	 * R的data为List列表，存储JsmsOemAgentPool对象
	 *
	 * @param jsmsOemAgentPool
	 * @return
	 */
	@Override
	public R findDuetimeList(JsmsOemAgentPool jsmsOemAgentPool) {
		R r = checkAgentPool(jsmsOemAgentPool);
		if (r != null) {
			return r;
		}

		if (jsmsOemAgentPool.getUnitPrice() == null)
			return R.error("单价不能为空");

		List<JsmsOemAgentPool> list = this.findList(jsmsOemAgentPool);
		if (list == null || list.isEmpty()) {
			return R.ok("获取代理商池到期列表成功", null);
		}

		// 将重复的去掉, 仅保留最大的ID的那一条
		Map<String, JsmsOemAgentPool> maps = new HashMap<>();
		for (JsmsOemAgentPool agentPool : list) {

			String date = DateUtils.formatDate(agentPool.getDueTime(), "yyyyMMdd");
			JsmsOemAgentPool oem = maps.get(date);
			if (oem == null) {
				maps.put(date, agentPool);
				continue;
			}

			// 若已存在, 若ID大于已存储的ID， 替换
			if (agentPool.getAgentId() > oem.getAgentId()) {
				maps.put(date, agentPool);
			}
		}

		// 排序后生成新的List
		List<Map.Entry<String, JsmsOemAgentPool>> newList = new ArrayList<>(maps.entrySet());
		Collections.sort(newList, new Comparator<Map.Entry<String, JsmsOemAgentPool>>() {
			// 升序排序
			public int compare(Map.Entry<String, JsmsOemAgentPool> o1, Map.Entry<String, JsmsOemAgentPool> o2) {
				return o1.getValue().getDueTime().compareTo(o2.getValue().getDueTime());
			}
		});

		list.clear();
		for (Map.Entry<String, JsmsOemAgentPool> agentPool : newList) {
			list.add(agentPool.getValue());
		}

		return R.ok("获取代理商池到期列表成功", list);
	}

}
