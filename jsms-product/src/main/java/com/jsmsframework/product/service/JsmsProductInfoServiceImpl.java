package com.jsmsframework.product.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.ProductStatus;
import com.jsmsframework.common.enums.ProductType;
import com.jsmsframework.product.entity.JsmsClientProduct;
import com.jsmsframework.product.entity.JsmsProductInfo;
import com.jsmsframework.product.mapper.JsmsProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author huangwenjie
 * @description 产品信息表
 * @date 2017-08-16
 */
@Service
public class JsmsProductInfoServiceImpl implements JsmsProductInfoService {

    @Autowired
    private JsmsProductInfoMapper productInfoMapper;
    @Autowired
    private JsmsClientProductService jsmsClientProductService;

    @Override
    @Transactional
    public int insert(JsmsProductInfo model) {
        return this.productInfoMapper.insert(model);
    }

    @Override
    @Transactional
    public int insertBatch(List<JsmsProductInfo> modelList) {
        return this.productInfoMapper.insertBatch(modelList);
    }

    @Override
    @Transactional
    public int update(JsmsProductInfo model) {
        JsmsProductInfo old = this.productInfoMapper.getByProductId(model.getProductId());
        if (old == null) {
            return 0;
        }
        return this.productInfoMapper.update(model);
    }

    @Override
    @Transactional
    public int updateSelective(JsmsProductInfo model) {
        JsmsProductInfo old = this.productInfoMapper.getByProductId(model.getProductId());
        if (old != null)
            return this.productInfoMapper.updateSelective(model);
        return 0;
    }

    @Override
    @Transactional
    public JsmsProductInfo getByProductId(Integer productId) {
        JsmsProductInfo model = this.productInfoMapper.getByProductId(productId);
        return model;
    }

    @Override
    @Transactional
    public List<JsmsProductInfo> getByProductIds(Set<Integer> productIds) {
        List<JsmsProductInfo> model = this.productInfoMapper.getByProductIds(productIds);
        return model;
    }

    @Override
    @Transactional
    public List<JsmsProductInfo> getByProductCode(String productCode) {
        List<JsmsProductInfo> model = this.productInfoMapper.getByProductCode(productCode);
        return model;
    }

    @Override
    @Transactional
    public JsmsPage queryList (JsmsPage page){
        List<JsmsProductInfo> list = this.productInfoMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    @Transactional
    public JsmsPage queryNotAgentProductList (JsmsPage page,Integer agentId){
        page.getParams().put("agentId", agentId);
        List<JsmsProductInfo> list = this.productInfoMapper.queryNotAgentProductList(page);
        page.setData(list);
        return page;
    }

    @Override
    @Transactional
    public List<JsmsProductInfo> queryNotAgentProductList (Integer agentId){
        List<JsmsProductInfo> list = this.productInfoMapper.queryNotAgentProductById(agentId);
        return list;
    }

    @Override
    @Transactional
    public int count(Map<String, Object> params) {
        return this.productInfoMapper.count(params);
    }

    @Override
    public List<JsmsProductInfo> getByProductCodes(List<String> productCodes) {
        return this.productInfoMapper.getByProductCodes(productCodes);
    }

    @Override
    @Transactional
    public JsmsPage queryProxiedList(JsmsPage page,Integer agentId,String clientid) {
        List<JsmsProductInfo> notProxiedProducts = queryNotAgentProductList(agentId);

        if(notProxiedProducts != null && !notProxiedProducts.isEmpty()) {
            List<Integer> productIdNotIn = new ArrayList<>();
            for(JsmsProductInfo notProduct:notProxiedProducts){
                productIdNotIn.add(notProduct.getProductId());
            }
            page.getParams().put("productIdNotIn",productIdNotIn);
        }
        page.getParams().put("status", ProductStatus.已上架.getValue());

        page = queryList(page);

        if(page.getData()!=null&&((List<JsmsProductInfo>) page.getData()).size()>0) {
            for (JsmsProductInfo productInfo : (List<JsmsProductInfo>) page.getData()) {
                //获取折扣价
                JsmsClientProduct clientProduct = jsmsClientProductService.getByClientidAndProductId(agentId, clientid, productInfo.getProductId());
                if (clientProduct == null){
                    continue;
                }else if(ProductType.国际.getValue().equals(productInfo.getProductType())) {
                    productInfo.setProductPrice(productInfo.getProductPrice().multiply(clientProduct.getPriceDiscount()));
                }else{
                    productInfo.setProductPrice(clientProduct.getGnDiscountPrice());
                }
            }
        }

        return page;
    }
}
