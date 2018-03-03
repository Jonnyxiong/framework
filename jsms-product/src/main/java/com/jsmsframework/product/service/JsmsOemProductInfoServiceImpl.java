package com.jsmsframework.product.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsAgentClientParam;
import com.jsmsframework.common.enums.ProductType;
import com.jsmsframework.common.service.JsmsAgentClientParamService;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.product.entity.JsmsOemAgentProduct;
import com.jsmsframework.product.entity.JsmsOemProductInfo;
import com.jsmsframework.product.mapper.JsmsOemProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @description OEM产品信息表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsOemProductInfoServiceImpl implements JsmsOemProductInfoService{

    @Autowired
    private JsmsOemProductInfoMapper oemProductInfoMapper;
    @Autowired
    private JsmsAgentClientParamService jsmsAgentClientParamService;
    @Autowired
    private  JsmsOemAgentProductService jsmsOemAgentProductService;
    @Override
	@Transactional
    public int insert(JsmsOemProductInfo model) {
        return this.oemProductInfoMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOemProductInfo> modelList) {
        return this.oemProductInfoMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOemProductInfo model) {
		JsmsOemProductInfo old = this.oemProductInfoMapper.getByProductId(model.getProductId());
		if(old == null){
			return 0;
		}
		return this.oemProductInfoMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOemProductInfo model) {
		JsmsOemProductInfo old = this.oemProductInfoMapper.getByProductId(model.getProductId());
		if(old != null)
        	return this.oemProductInfoMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOemProductInfo getByProductId(Integer productId) {
        JsmsOemProductInfo model = this.oemProductInfoMapper.getByProductId(productId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOemProductInfo> list = this.oemProductInfoMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.oemProductInfoMapper.count(params);
    }

    @Override
    public JsmsPage findList(JsmsPage<JsmsOemProductInfo> page) {
        List<JsmsOemProductInfo> list = this.oemProductInfoMapper.findList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsOemProductInfo> getProductInfo(Map<String, Object> params) {
        return oemProductInfoMapper.getProductInfo(params);
    }
}
