package com.jsmsframework.channel.service;

import com.jsmsframework.channel.entity.JsmsComplaintList;
import com.jsmsframework.channel.entity.JsmsComplaintListExt;
import com.jsmsframework.channel.mapper.JsmsComplaintListMapper;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.OperatorType;
import com.jsmsframework.common.enums.SmsTypeEnum;
import com.ucpaas.sms.common.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author huangwenjie
 * @description 投诉明细记录表
 * @date 2018-01-09
 */
@Service
public class JsmsComplaintListServiceImpl implements JsmsComplaintListService {

    @Autowired
    private JsmsComplaintListMapper complaintListMapper;

    @Override
    public int insert(JsmsComplaintList model) {
        return this.complaintListMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsComplaintList> modelList) {
        return this.complaintListMapper.insertBatch(modelList);
    }

    @Override
    public int update(JsmsComplaintList model) {
        JsmsComplaintList old = this.complaintListMapper.getById(model.getId());
        if (old == null) {
            return 0;
        }
        return this.complaintListMapper.update(model);
    }

    @Override
    public int updateSelective(JsmsComplaintList model) {
        JsmsComplaintList old = this.complaintListMapper.getById(model.getId());
        if (old != null)
            return this.complaintListMapper.updateSelective(model);
        return 0;
    }

    @Override
    public JsmsComplaintList getById(Integer id) {
        JsmsComplaintList model = this.complaintListMapper.getById(id);
        return model;
    }

    @Override
    public List<JsmsComplaintList> getDuplicateData(Map params){
        return this.complaintListMapper.getDuplicateData(params);
    }
    @Override
    public List<JsmsComplaintList> findListGroup(Map params){
        return complaintListMapper.findListGroup(params);
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsComplaintList> list = this.complaintListMapper.queryList(page);
        page.setData(list);
        return page;
    }
    @Override
    public JsmsPage queryListExt(JsmsPage page) {
        List<JsmsComplaintList> list = this.complaintListMapper.queryListExt(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsComplaintList> findList(Map params) {
        List<JsmsComplaintList> list = this.complaintListMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String, Object> params) {
        return this.complaintListMapper.count(params);
    }
    @Override
    public int count4Channel(Map<String, Object> params) {
        return this.complaintListMapper.count4Channel(params);
    }
    //投诉搜索的求总数
    @Override
    public int countComplaint(Map<String, Object> params) {
        return this.complaintListMapper.countComplaint(params);
    }


    /**
     * 根据id删除投诉
     * @param id
     * @return
     */
    @Override
    public int deleteById(Integer id) {
        return complaintListMapper.deleteById(id);
    }

    /**
     * 搜索投诉,分页
     * @param page
     * @return
     */
    @Override
    public List<JsmsComplaintListExt> searchComplaint(JsmsPage page) {
        List<JsmsComplaintListExt> list = complaintListMapper.searchComplaint(page);
        List<JsmsComplaintListExt> ll=new ArrayList<>();
        //格式化数据
        if(list!=null && list.size()>0){
            for (int i=0;i<list.size();i++){
                JsmsComplaintListExt complaintListExt = list.get(i);
                //处理发送时间
                if(complaintListExt.getSendTime()!=null){
                    String sendTimeStr = DateUtils.formatDate(complaintListExt.getSendTime(), "yyyy-MM-dd");
                    complaintListExt.setSendTimeStr(sendTimeStr);
                }
                //处理创建时间
                if(complaintListExt.getCreateTime()!=null){
                    String createTimeStr = DateUtils.formatDate(complaintListExt.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                    complaintListExt.setCreateTimeStr(createTimeStr);
                }
                //处理短信类型(0：通知短信-，4：验证码短信，5：营销短信，6：告警短信，7：USSD，8：闪信,投诉记录类型type为通道投诉的时候可为空)
                Integer smstype = complaintListExt.getSmstype();
                if(smstype.equals(SmsTypeEnum.通知.getValue())){
                    complaintListExt.setSmsTypeStr("通知短信");
                }else if(smstype.equals(SmsTypeEnum.验证码.getValue())){
                    complaintListExt.setSmsTypeStr("验证码短信");
                }else if(smstype.equals(SmsTypeEnum.营销.getValue())){
                    complaintListExt.setSmsTypeStr("营销短信");
                }else if(smstype.equals(SmsTypeEnum.告警.getValue())){
                    complaintListExt.setSmsTypeStr("告警短信");
                }else if(smstype.equals(SmsTypeEnum.USSD.getValue())){
                    complaintListExt.setSmsTypeStr("USSD");
                }else if(smstype.equals(SmsTypeEnum.闪信.getValue())){
                    complaintListExt.setSmsTypeStr("闪信");
                }
                //处理运营商类型(0：全网1：移动2：联通3：电信4：国际,投诉记录类型type为通道投诉的时候可为空)
                Integer operatorstype = complaintListExt.getOperatorstype();
                if(operatorstype.equals(OperatorType.全网.getValue())){
                    complaintListExt.setOperatorstypeStr("全网");
                }else if (operatorstype.equals(OperatorType.移动.getValue())){
                    complaintListExt.setOperatorstypeStr("移动");
                }else if (operatorstype.equals(OperatorType.联通.getValue())){
                    complaintListExt.setOperatorstypeStr("联通");
                }else if (operatorstype.equals(OperatorType.电信.getValue())){
                    complaintListExt.setOperatorstypeStr("电信");
                }else if (operatorstype.equals(OperatorType.国际.getValue())){
                    complaintListExt.setOperatorstypeStr("国际");
                }
                ll.add(complaintListExt);
            }
        }
        return ll;
    }
}
