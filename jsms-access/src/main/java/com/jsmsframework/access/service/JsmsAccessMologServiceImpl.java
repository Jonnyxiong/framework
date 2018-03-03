package com.jsmsframework.access.service;

import com.jsmsframework.access.access.entity.JsmsAccessMolog;
import com.jsmsframework.access.access.mapper.JsmsAccessMologMapper;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author huangwenjie
 * @description 客户上行明细表
 * @date 2017-11-28
 */
@Service
public class JsmsAccessMologServiceImpl implements JsmsAccessMologService {

    @Autowired
    private JsmsAccessMologMapper accessMologMapper;

    @Override
    public int insert(JsmsAccessMolog model) {
        return this.accessMologMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsAccessMolog> modelList) {
        return this.accessMologMapper.insertBatch(modelList);
    }

    @Override
    public int update(JsmsAccessMolog model) {
        JsmsAccessMolog old = this.accessMologMapper.getByMoid(model.getMoid());
        if (old == null) {
            return 0;
        }
        return this.accessMologMapper.update(model);
    }

    @Override
    public int updateSelective(JsmsAccessMolog model) {
        JsmsAccessMolog old = this.accessMologMapper.getByMoid(model.getMoid());
        if (old != null)
            return this.accessMologMapper.updateSelective(model);
        return 0;
    }

    @Override
    public JsmsAccessMolog getByMoid(String moid) {
        JsmsAccessMolog model = null;
        DateTime dateTime = DateTime.now();
        DateTime boundary = DateTime.parse("2018-01-01");

        for (int i = 0; i <= 3; i++) {
            dateTime.minusMonths(i);
            if (dateTime.compareTo(boundary) > 0) {
                model = this.accessMologMapper.getByMoidWithSuffix(moid, dateTime.toString("yyyyMM"));
            }
            if (model != null) {
                break;
            }

        }
        return model;
    }

    @Override
    public JsmsAccessMolog getByMoid(String moid, String dateSuffix) {
        JsmsAccessMolog model = this.accessMologMapper.getByMoidWithSuffix(moid, dateSuffix);
        return model;
    }

    /**
     * 用户中心5.19.2 , 对应调度系统上行回复记录开始分表
     *
     * @param page
     * @param dateSuffix 日期后缀, 格式为 yyyyMM
     * @return
     */
    @Override
    public JsmsPage queryList(JsmsPage page, String dateSuffix) {
        if (StringUtils.isBlank(dateSuffix)) {
            return page;
        }
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(dateSuffix);
        if (!matcher.find()) {
            return page;
        }
        page.getParams().put("dateSuffix", dateSuffix);
        List<JsmsAccessMolog> list = this.accessMologMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {

        return this.queryList(page, DateTime.now().toString("yyyyMM"));
    }

    @Override
    public int count(Map<String, Object> params) {
        return this.accessMologMapper.count(params);
    }

}
