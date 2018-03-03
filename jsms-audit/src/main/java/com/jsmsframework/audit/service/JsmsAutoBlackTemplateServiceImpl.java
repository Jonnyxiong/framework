package com.jsmsframework.audit.service;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jsmsframework.audit.dto.JsmsBalckTemplateDTO;
import com.jsmsframework.audit.exception.JsmsAutoBlackTemplateException;
import com.jsmsframework.common.constant.SysConstant;
import com.jsmsframework.common.dto.JxlExcel;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.AutoTemplateType;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.AutoTemplateTypeEnum;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.BalckTemplateState;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.TemplateLevel;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.common.util.JxlExcelUtils;
import com.jsmsframework.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.audit.mapper.JsmsAutoBlackTemplateMapper;
import com.jsmsframework.audit.entity.JsmsAutoBlackTemplate;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 智能黑模板表
 * @author huangwenjie
 * @date 2017-11-30
 */
@Service
public class JsmsAutoBlackTemplateServiceImpl implements JsmsAutoBlackTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsmsAutoBlackTemplateServiceImpl.class);

    private static final Pattern SIGN_PATTERN = Pattern.compile("^[a-zA-Z0-9\u4E00-\u9FA5]+$");

    private static final Pattern CONTENT_PATTERN = Pattern.compile("([\\u4E00-\\u9FA5]|[\\（\\）\\《\\》\\——\\；\\，\\。\\“\\”\\！\\【\\】])");

    private static final Pattern ISSTATIC_TEMPLATE_PATTERN = Pattern.compile("\\{.*?\\}");
    @Autowired
    private JsmsAutoBlackTemplateMapper autoBlackTemplateMapper;

    @Override
    @Transactional("message")
    public int insert(JsmsAutoBlackTemplate model) {
        return this.autoBlackTemplateMapper.insert(model);
    }

    @Override
    @Transactional("message")
    public int insertBatch(List<JsmsAutoBlackTemplate> modelList) {
        return this.autoBlackTemplateMapper.insertBatch(modelList);
    }

    @Override
    @Transactional("message")
    public int update(JsmsAutoBlackTemplate model) {
        JsmsAutoBlackTemplate old = this.autoBlackTemplateMapper.getByTemplateId(model.getTemplateId());
        if (old == null) {
            return 0;
        }
        return this.autoBlackTemplateMapper.update(model);
    }

    @Override
    @Transactional("message")
    public int updateSelective(JsmsAutoBlackTemplate model) {
        JsmsAutoBlackTemplate old = this.autoBlackTemplateMapper.getByTemplateId(model.getTemplateId());
        if (old != null)
            return this.autoBlackTemplateMapper.updateSelective(model);
        return 0;
    }

    @Override
    public JsmsAutoBlackTemplate getByTemplateId(Integer templateId) {
        JsmsAutoBlackTemplate model = this.autoBlackTemplateMapper.getByTemplateId(templateId);
        return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAutoBlackTemplate> list = this.autoBlackTemplateMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String, Object> params) {
        return this.autoBlackTemplateMapper.count(params);
    }

    @Override
    @Transactional("message")
    public R addBalackTemplate(JsmsBalckTemplateDTO balckTemplatedto) {
        //校验黑模板字段属性
        R r = this.checkBalckTemplate(balckTemplatedto);
        if (Objects.equals(r.getCode(), SysConstant.FAIL_CODE)) {
            return r;
        }
        //校验添加黑模板是否非法或已存在
        r = this.checkBalckTemplate4Rightful(balckTemplatedto);
        if (!Objects.equals(r.getCode(), SysConstant.SUCCESS_CODE) && !Objects.equals(r.getCode(), SysConstant.DEL_CODE)) {
            return r;
        }
        JsmsAutoBlackTemplate insertTemplate = new JsmsAutoBlackTemplate();
        BeanUtil.copyProperties(balckTemplatedto, insertTemplate);
        insertTemplate.setCreateTime(new Date());
        insertTemplate.setUpdateTime(new Date());
        int add = this.insert(insertTemplate);
        if (add > 0) {
            if (Objects.equals(r.getCode(), SysConstant.DEL_CODE) && Objects.equals(insertTemplate.getTemplateLevel(), TemplateLevel.全局级别.getValue()) &&
                    StringUtils.isBlank(insertTemplate.getSign())) {
                //添加空签名通用黑模板存在有签名黑模板 执行删除状态更新
                JsmsBalckTemplateDTO delquery = new JsmsBalckTemplateDTO();

                delquery.setTemplateLevel(balckTemplatedto.getTemplateLevel());
                delquery.setTemplateType(balckTemplatedto.getTemplateType());
                delquery.setContent(balckTemplatedto.getContent());
                delquery.setSmsType(balckTemplatedto.getSmsType());

                List<JsmsAutoBlackTemplate> delTempates = this.autoBlackTemplateMapper.queryAll(delquery);
                for (JsmsAutoBlackTemplate tempate : delTempates) {
                    if (tempate.getSign() != null) {
                        r = this.delBalckTemplate(tempate.getTemplateId());
                        if (Objects.equals(r.getCode(), SysConstant.FAIL_CODE)) {
                            throw new JsmsAutoBlackTemplateException("删除通用有签名黑模板失败！,data=" + JsonUtil.toJson(tempate));
                        }
                    }
                }
            }

        } else {
            R.error("添加黑模板失败！");
            throw new JsmsAutoBlackTemplateException("添加黑模板失败！,data=" + JsonUtil.toJson(balckTemplatedto));
        }


        return R.ok("添加黑模板成功！");
    }

    @Override
    public boolean isExistBalckTemplate(JsmsAutoBlackTemplate balckTemplate) {
        return this.autoBlackTemplateMapper.isExist(balckTemplate) > 0;
    }

    @Override
    public List<JsmsAutoBlackTemplate> queryAll(JsmsBalckTemplateDTO balckTemplate) {
        return this.autoBlackTemplateMapper.queryAll(balckTemplate);
    }

    @Override
    public R delBalckTemplate(Integer templateId) {
        JsmsAutoBlackTemplate deltemplate = new JsmsAutoBlackTemplate();
        deltemplate.setTemplateId(templateId);
        deltemplate.setState(BalckTemplateState.删除.getValue());
        deltemplate.setUpdateTime(new Date());
        int del = this.autoBlackTemplateMapper.updateState(deltemplate);

        LOGGER.debug("删除黑模板操作 {},{}", JsonUtil.toJson(deltemplate), del > 0 ? "删除成功" : "删除失败");

        return del > 0 ? R.ok("删除成功") : R.error("删除失败");
    }

    @Override
    public R updateState4BalckTemplate(Integer templateId, Integer state) {
        JsmsBalckTemplateDTO updatetemplate = new JsmsBalckTemplateDTO();
        if (templateId == null) {
            return R.error("模板ID不能为空！");
        }
        if (state == null) {
            return R.error("模板状态为空！");
        }
        updatetemplate.setTemplateId(templateId);
        if (!isExistBalckTemplate(updatetemplate)) {
            return R.error("模板不存在");
        }
        if (BalckTemplateState.getDescByValue(state) == null) {
            return R.error("参数非法！");
        }

        updatetemplate.setState(state);
        updatetemplate.setUpdateTime(new Date());
        int up = this.autoBlackTemplateMapper.updateState(updatetemplate);
        LOGGER.debug("更新黑模板状态操作 {},{}", JsonUtil.toJson(updatetemplate), up > 0 ? "成功" : "失败");
        return up > 0 ? R.ok("更新成功") : R.error("更新失败");
    }

    /**
     * 编辑通用、用户黑模板
     *
     * @param balckTemplatedto
     * @return
     */
    @Override
    public R editBalckTemplate(JsmsBalckTemplateDTO balckTemplatedto) {
        //校验黑模板字段属性
        R r = this.checkBalckTemplate(balckTemplatedto);
        if (Objects.equals(r.getCode(), SysConstant.FAIL_CODE)) {
            return r;
        }
        //校验黑模板是否非法或已存在
        r = this.checkBalckTemplate4Rightful(balckTemplatedto);
        if (!Objects.equals(r.getCode(), SysConstant.SUCCESS_CODE) && !Objects.equals(r.getCode(), SysConstant.DEL_CODE)) {
            return r;
        }
        JsmsAutoBlackTemplate updateTemplate = new JsmsAutoBlackTemplate();
        BeanUtil.copyProperties(balckTemplatedto, updateTemplate);
        updateTemplate.setUpdateTime(new Date());
        int edit = this.updateSelective(updateTemplate);
        if (edit > 0) {
            if (Objects.equals(r.getCode(), SysConstant.DEL_CODE) && Objects.equals(updateTemplate.getTemplateLevel(), TemplateLevel.全局级别.getValue()) &&
                    StringUtils.isBlank(updateTemplate.getSign())) {
                //编辑空签名通用黑模板存在有签名黑模板 执行删除状态更新
                JsmsBalckTemplateDTO delquery = new JsmsBalckTemplateDTO();

                delquery.setTemplateLevel(updateTemplate.getTemplateLevel());
                delquery.setTemplateType(updateTemplate.getTemplateType());
                delquery.setContent(updateTemplate.getContent());
                delquery.setSmsType(updateTemplate.getSmsType());

                List<JsmsAutoBlackTemplate> delTempates = this.queryAll(delquery);
                for (JsmsAutoBlackTemplate tempate : delTempates) {
                    if (tempate.getSign() != null) {
                        r = this.delBalckTemplate(tempate.getTemplateId());
                        if (Objects.equals(r.getCode(), SysConstant.FAIL_CODE)) {
                            throw new JsmsAutoBlackTemplateException("删除通用有签名黑模板失败！,data=" + JsonUtil.toJson(tempate));
                        }
                    }

                }
            }

        } else {
            R.error("编辑黑模板失败！");
            throw new JsmsAutoBlackTemplateException("编辑黑模板失败！,data=" + JsonUtil.toJson(balckTemplatedto));
        }


        return R.ok("编辑黑模板成功！");
    }

    /**
     * 校验是否合法，已存在相同黑模板
     *
     * @param balckTemplate
     * @return
     */
    @Override
    public R checkBalckTemplate4Rightful(JsmsBalckTemplateDTO balckTemplate) {
        boolean isSureDel = false;
        if (balckTemplate.getSureDel() != null) {
            isSureDel = balckTemplate.getSureDel();
        }
        JsmsBalckTemplateDTO queryExist = new JsmsBalckTemplateDTO();

        queryExist.setContent(balckTemplate.getContent());
        queryExist.setSmsType(balckTemplate.getSmsType());
        queryExist.setTemplateType(balckTemplate.getTemplateType());

        if (balckTemplate.getTemplateId() != null) {
            queryExist.setUpdateTemId(balckTemplate.getTemplateId());
        }
        Integer isadd = queryExist.getUpdateTemId();
        //校验是存在【模板属性+模板类型+模板内容】相同通用黑模板
        if (!isExistBalckTemplate(queryExist)) {
            return R.ok("黑模板不存在相同,可继续" + isadd == null ? "添加" : "编辑");
        }

        queryExist.setTemplateLevel(TemplateLevel.全局级别.getValue());
        queryExist.setSign("-1");
        //校验是否存在通用黑模板空签名
        LOGGER.debug("——————————————————————————开始校验是否存在空签名相同内容黑模板——————————————————————————");
        if (isExistBalckTemplate(queryExist)){
            return R.error("已存在相同类型通用黑模板");
        }
        LOGGER.debug("——————————————————————————结束校验存在空签名相同内容黑模板已不存在——————————————————————————");
        LOGGER.debug("——————————————————————————开始校验添加的相同内容黑模板——————————————————————————");

        R r = new R();
        //添加编辑为空签名 通用模板
        if (Objects.equals(balckTemplate.getTemplateLevel(), TemplateLevel.全局级别.getValue()) && StringUtils.isBlank(balckTemplate.getSign())) {

            if (isSureDel) {
                r.setCode(SysConstant.DEL_CODE);
                r.setMsg("确定添加,原【相同内容+签名】的通用模板将被删除!");
                LOGGER.debug("——————————————————————————确定添加,原【相同内容+签名】的通用模板将被删除!——————————————————————————");
                return r;
            } else {

                queryExist.setSign("-2");

                String addOrEdit = "";

                if (isExistBalckTemplate(queryExist)) {
                    List<JsmsAutoBlackTemplate> dellist=this.queryAll(queryExist);
                    r.setCode(SysConstant.SUER_DEL_CODE);
                    addOrEdit= isadd == null ? "添加" : "编辑";
                    r.setMsg("已存在【相同内容+签名】的通用模板，如确定" + addOrEdit  + "，则原【相同内容+签名】的通用模板将被删除");
                    LOGGER.debug("——————————————————————————已存在【相同内容+签名】的通用模板，如确定" + addOrEdit == null ? "添加" : "编辑" + "，则原【相同内容+签名】的通用模板将被删除,相同内容的通用模板有{}——————————————————————————",JsonUtil.toJson(dellist));
                    return r;
                }
                LOGGER.debug("——————————————————————————通用黑模板空签名可" + addOrEdit == null ? "添加" : "编辑" + "——————————————————————————");
                return R.ok("通用黑模板空签名可" + addOrEdit == null ? "添加" : "编辑");

            }

        }
        //添加编辑为有签名,通用模板
        if (Objects.equals(balckTemplate.getTemplateLevel(), TemplateLevel.全局级别.getValue()) && StringUtils.isNotBlank(balckTemplate.getSign())) {
            queryExist.setSign(balckTemplate.getSign());
            if (isExistBalckTemplate(queryExist)) {
                LOGGER.error("——————————————————————————已存在相同类型的有签名通用黑模板——————————————————————————");
                return R.error("已存在相同类型的通用黑模板");
            }
            LOGGER.debug("——————————————————————————通用黑模板有签名可" + isadd == null ? "添加" : "编辑" + "——————————————————————————");
            return R.ok("通用黑模板有签名可" + isadd == null ? "添加" : "编辑");

        }
        //添加编辑 用户模板
        if (Objects.equals(balckTemplate.getTemplateLevel(), TemplateLevel.用户级别.getValue())) {
            queryExist.setSign(balckTemplate.getSign());
            if (isExistBalckTemplate(queryExist)) {
                LOGGER.error("——————————————————————————已存在相同类型的有签名通用黑模板——————————————————————————");
                return R.error("已存在相同类型的通用黑模板");
            }
            queryExist.setTemplateLevel(balckTemplate.getTemplateLevel());
            queryExist.setClientId(balckTemplate.getClientId());
            if (isExistBalckTemplate(queryExist)) {
                LOGGER.error("——————————————————————————已存在相同类型的用户黑模板——————————————————————————");
                return R.error("已存在相同类型的用户黑模板");
            }
            return R.ok("用户黑模板可" + isadd == null ? "添加" : "编辑");
        }


        return R.error("非法操作！");
    }

    /**
     * 提供给不同平台的黑模板校验接口(针对白模板)
     *
     * @param balckTemplate
     * @param webid
     * @return
     */
    @Override
    public R checkBalckTemplate4AutoTemplate(JsmsBalckTemplateDTO balckTemplate, Integer webid) {
        //模板级别
        Integer templateLevel = balckTemplate.getTemplateLevel();
        //签名
        String sign = balckTemplate.getSign();

        JsmsBalckTemplateDTO queryExist = new JsmsBalckTemplateDTO();
        //智能模板校验黑模板状态为启用
        queryExist.setState(BalckTemplateState.启用.getValue());
        //模板内容
        queryExist.setContent(balckTemplate.getContent());
        //模板属性
        queryExist.setSmsType(balckTemplate.getSmsType());
        //模板类型
        queryExist.setTemplateType(balckTemplate.getTemplateType());

        /**
         * 通用黑模板校验:无论用户模板还是通用模板都要经过通用黑模板校验
         */
        //模板级别
        queryExist.setTemplateLevel(TemplateLevel.全局级别.getValue());
        //通用模板clientID为*
        queryExist.setClientId(SysConstant.ALL);
        //设置空签名
        queryExist.setSign("-1");//这里传-1是为了Mapper查询时的条件判断为空
        //校验是否存在【空签名+模板属性+模板类型+模板内容+模板状态为启用】的通用黑模板
        if (isExistBalckTemplate(queryExist)) {
            return R.error("模板中含违规信息");
        }

        //校验是否存在是否存在【签名+模板属性+模板类型+模板内容+模板状态为启用】的通用黑模板
        if (StringUtils.isBlank(sign) && Objects.equals(TemplateLevel.全局级别.getValue(), templateLevel)) {
            queryExist.setSign("-2");//这里传-2是为了Mapper查询时的条件判断为非空
            if (isExistBalckTemplate(queryExist)) {
                return R.error("模板中含违规信息");
            }
        } else {
            queryExist.setSign(sign);
            if (isExistBalckTemplate(queryExist)) {
                return R.error("模板中含违规信息");
            }
        }

        //如果是用户模板,则校验是否存在【账号+签名+模板属性+模板类型+模板内容+模板状态为启用】的用户黑模板
        if (Objects.equals(TemplateLevel.用户级别.getValue(),templateLevel)) {
            //设置模板级别
            queryExist.setTemplateLevel(templateLevel);
            //账号(全局时为*)
            queryExist.setClientId(balckTemplate.getClientId());
            if (isExistBalckTemplate(queryExist)) {
                return R.error("模板中含违规信息");
            }
        }
        return R.ok("黑模板校验成功！");
    }


    /**
     * 黑模板字段常规校验
     *
     * @param balckTemplatedto
     * @return
     */
    @Override
    public R checkBalckTemplate(JsmsBalckTemplateDTO balckTemplatedto) {
        if (balckTemplatedto == null) {
            return R.error("黑模板不能为空");
        }
        if (Objects.equals(balckTemplatedto.getTemplateLevel(), TemplateLevel.全局级别.getValue()) && StringUtils.isBlank(balckTemplatedto.getSign())) {
            //黑模板通用模板签名可为空，不校验
        } else {
            try {
                String sign = balckTemplatedto.getSign();
                if (StringUtils.isBlank(sign) && Objects.equals(balckTemplatedto.getTemplateLevel(), TemplateLevel.用户级别.getValue())) {
                    return R.error("用户级别黑模板签名不能为空!");
                }

                    if ((balckTemplatedto.getSign().length() < 2
                            || balckTemplatedto.getSign().length() > 12)) {
                        return R.error("黑模板的短信签名长度必须介于 2 和 12 之间 ");
                    }
                    Matcher m = SIGN_PATTERN.matcher(sign);
                    if (!m.matches()) {
                        return R.error("签名格式为中文或者英文字母或者数字");
                    }


            } catch (Throwable e) {
                LOGGER.error("验证短信签名错误", e);
                return R.error("签名格式校验错误");
            }
        }


        try {
            String content = balckTemplatedto.getContent();
            if (StringUtils.isBlank(content)) {
                return R.error("模板内容不能为空");
            }
            Matcher m = CONTENT_PATTERN.matcher(content);
            if (m.find()) {
                if (content.indexOf("【") != -1 || content.indexOf("】") != -1 || content.indexOf("【】") != -1) {
                    return R.error("中文短信模板内容不能包含【,】和【】");
                }
            }
        } catch (Throwable e) {
            LOGGER.error("验证模板内容校验错误", e);
            return R.error("验证模板内容错误");
        }

        try {

            Matcher matcher = ISSTATIC_TEMPLATE_PATTERN.matcher(balckTemplatedto.getContent());
            if (matcher.find()) {
                if (AutoTemplateType.固定模板.getValue().equals(balckTemplatedto.getTemplateType())) {
                    return R.error("固定模板中不能包含'{}'");
                }
            }
        } catch (Throwable e) {
            LOGGER.error("验证模板内容错误", e);
            return R.error("验证模板内容错误");
        }



        if(Objects.equals(balckTemplatedto.getTemplateLevel(),TemplateLevel.用户级别.getValue())){
            if(StringUtils.isBlank(balckTemplatedto.getClientId())||Objects.equals(SysConstant.ALL,balckTemplatedto.getClientId())){
                return R.error("黑模板的用户账号非法");
            }
        }


        if (balckTemplatedto.getSmsType() == null || AutoTemplateTypeEnum.getDescByValue(balckTemplatedto.getSmsType()) == null) {
            return R.error("黑模板的模板属性不存在");
        }

        if (balckTemplatedto.getTemplateType() == null
                || AutoTemplateType.getDescByValue(balckTemplatedto.getTemplateType()) == null) {
            return R.error("黑模板的模板类型不存在");
        }


        if (StringUtils.isBlank(balckTemplatedto.getContent()) || balckTemplatedto.getContent().length() > 500) {
            return R.error("黑模板的模板内容长度必须介于 1 和 500 之间");
        }


        if (balckTemplatedto.getState() != null) {
            if (BalckTemplateState.getDescByValue(balckTemplatedto.getState()) == null) {
                return R.error("黑模板的状态不存在");
            }

        }


        if (Objects.equals(balckTemplatedto.getTemplateType(), AutoTemplateType.变量模板.getValue())) {
            int index = balckTemplatedto.getContent().indexOf("{}");
            if (index == -1) {
                return R.error("黑模板的模板类型为变量模板，最少需要一个变量");
            }
        }

        return R.ok("校验黑模板成功！");
    }



}
