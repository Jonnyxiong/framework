package com.jsmsframework.sms.send.service;

import com.jsmsframework.common.constant.SysConstant;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.entity.JsmsParam;
import com.jsmsframework.common.enums.Code;
import com.jsmsframework.common.enums.SmsFrom;
import com.jsmsframework.common.enums.WebId;
import com.jsmsframework.common.enums.middleware.MiddlewareType;
import com.jsmsframework.common.enums.smsSend.SmsSendStatus;
import com.jsmsframework.common.enums.smsSend.TaskSubmitTypeEnum;
import com.jsmsframework.common.service.JsmsParamService;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.middleware.entity.JsmsComponentConfigure;
import com.jsmsframework.middleware.service.JsmsComponentConfigureService;
import com.jsmsframework.sms.send.dto.TimerSendTaskDTO;
import com.jsmsframework.sms.send.entity.JsmsTimerSendTask;
import com.jsmsframework.sms.send.mapper.JsmsTimerSendTaskMapper;
import com.jsmsframework.sms.send.util.JsmsSendParam;
import com.jsmsframework.user.entity.JsmsAccount;
import com.jsmsframework.user.entity.JsmsAgentInfo;
import com.jsmsframework.user.service.JsmsAccountService;
import com.jsmsframework.user.service.JsmsAgentInfoService;
import com.ucpaas.sms.common.util.DateUtils;
import com.ucpaas.sms.common.util.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @description 定时短信发送任务信息表
 * @author Don
 * @date 2018-01-04
 */
@Service
public class JsmsTimerSendTaskServiceImpl implements JsmsTimerSendTaskService {
    private Logger logger = LoggerFactory.getLogger(JsmsTimerSendTaskServiceImpl.class);

    @Autowired
    private JsmsTimerSendTaskMapper timerSendTaskMapper;
    @Autowired
    private JsmsParamService jsmsParamService;
    @Autowired
    private JsmsSendParam jsmsSendParam;
    @Autowired
    private JsmsTimerSendPhonesService jsmsTimerSendPhonesService;
    @Autowired
    private JsmsAccountService jsmsAccountService;
    @Autowired
    private JsmsAgentInfoService jsmsAgentInfoService;
    @Autowired
    private JsmsComponentConfigureService jsmsComponentConfigureService;


    @Override
    public int insert(JsmsTimerSendTask model) {
        return this.timerSendTaskMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsTimerSendTask> modelList) {
        return this.timerSendTaskMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsTimerSendTask model) {
		JsmsTimerSendTask old = this.timerSendTaskMapper.getByTaskId(model.getTaskId());
		if(old == null){
			return 0;
		}
		return this.timerSendTaskMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsTimerSendTask model) {
		JsmsTimerSendTask old = this.timerSendTaskMapper.getByTaskId(model.getTaskId());
		if(old != null)
        	return this.timerSendTaskMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsTimerSendTask getByTaskId(String taskId) {
        JsmsTimerSendTask model = this.timerSendTaskMapper.getByTaskId(taskId);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsTimerSendTask> list = this.timerSendTaskMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsTimerSendTask> findList(Map params) {
        List<JsmsTimerSendTask> list = this.timerSendTaskMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.timerSendTaskMapper.count(params);
    }

    /**
     * 校验是否可以修改定时任务
     *
     * @param taskId
     * @return
     */
    @Override
    public R checkCanChange(String taskId) {
        List<JsmsParam> params=jsmsParamService.getByParamKey(SysConstant.TIMER_SEND_CFG);
        if(!params.isEmpty()){
            JsmsParam param=params.get(0);
            String[] paramArray=param.getParamValue().split(";");
            if(paramArray.length!=6){
                return  R.error("系统参数定时短信发送配置错误");
            }
            Integer min=Integer.valueOf(paramArray[5]).intValue();


            JsmsTimerSendTask task=this.getByTaskId(taskId);
            if(task!=null){
                Date sendTime=task.getSetSendTime();
                if(sendTime!=null){
                    Date  endTime =DateUtils.addMinutes(new Date(),min);
                    boolean canChange= sendTime.after(endTime);
                    if(canChange){
                        return  R.ok("未到限制时间,可进行修改操作");
                    }else {
                        return  R.error("达到限制时间，不能进行操作");
                    }
                }else {
                    return R.error("设定发送时间为空");
                }

            }


        }

        return R.error("系统参数定时短信发送配置为空");
    }

    @Override
    public JsmsPage queryPageList(JsmsPage page,WebId webId,String id) {
        int index =0;
        List<JsmsTimerSendTask> list = this.timerSendTaskMapper.queryPageList(page);
        List<TimerSendTaskDTO> timerSendTaskDTOList = new ArrayList<>();
        if(webId.getValue().equals(WebId.OEM代理商平台.getValue())||webId.getValue().equals(WebId.客户端.getValue())){
            if(list.size()>0){
                for(int i=0;i<list.size();i++){
                    index =index+1;
                    TimerSendTaskDTO timerSendTaskDTO = new TimerSendTaskDTO();
                    BeanUtil.copyProperties(list.get(i),timerSendTaskDTO);
                    String phoneStr;
                    //获取总计费数
                    /*phoneStr = jsmsTimerSendPhonesService.getAllPhone(list.get(i).getSubmitTime(),list.get(i).getTaskId());
                    List<String> phoneList = java.util.Arrays.asList(phoneStr.split(","));*/
                    String chargeNumTotal= String.valueOf(list.get(i).getTotalPhonesNum()*list.get(i).getChargeNum());
                    timerSendTaskDTO.setChargeNumTotal(chargeNumTotal);
                    //创建者
                    if(StringUtils.isNotBlank(String.valueOf(list.get(i).getSubmittype()))){
                        if(list.get(i).getSubmittype().equals(TaskSubmitTypeEnum.子账户.getValue())){//客户创建
                            if(list.get(i).getClientid()!=null && StringUtils.isNotBlank(list.get(i).getClientid())){
                                if(StringUtils.isNotBlank(id)&&id !=null){
                                    if(id.equals(String.valueOf(list.get(i).getClientid()))){
                                        if(list.get(i).getStatus().equals(SmsSendStatus.等待中.getValue())){
                                            timerSendTaskDTO.setCancelFlag(1);
                                        }else{
                                            timerSendTaskDTO.setCancelFlag(0);
                                        }
                                        if(list.get(i).getStatus().equals(SmsSendStatus.已取消.getValue())){
                                            timerSendTaskDTO.setUpdateFlag(1);
                                        }else{
                                            timerSendTaskDTO.setUpdateFlag(0);
                                        }
                                    }
                                }
                                if(list.get(i).getStatus().equals(SmsSendStatus.处理中.getValue())){
                                    timerSendTaskDTO.setGetAllPhoneFlag(0);
                                }else{
                                    timerSendTaskDTO.setGetAllPhoneFlag(1);
                                }
                                JsmsAccount jsmsAccount =  jsmsAccountService.getByClientId(list.get(i).getClientid());
                                timerSendTaskDTO.setCreator(jsmsAccount.getName());
                            }
                        }else if(list.get(i).getSubmittype().equals(TaskSubmitTypeEnum.代理商.getValue())){//代理商创建
                            if(list.get(i).getAgentId()!=null){
                                if(StringUtils.isNotBlank(id)&&id !=null){
                                    if(id.equals(String.valueOf(list.get(i).getAgentId()))){
                                        if(list.get(i).getStatus().equals(SmsSendStatus.等待中.getValue())){
                                            timerSendTaskDTO.setCancelFlag(1);
                                        }else{
                                            timerSendTaskDTO.setCancelFlag(0);
                                        }
                                        if(list.get(i).getStatus().equals(SmsSendStatus.已取消.getValue())){
                                            timerSendTaskDTO.setUpdateFlag(1);
                                        }else{
                                            timerSendTaskDTO.setUpdateFlag(0);
                                        }
                                    }
                                }
                                if(list.get(i).getStatus().equals(SmsSendStatus.处理中.getValue())){
                                    timerSendTaskDTO.setGetAllPhoneFlag(0);
                                }else{
                                    timerSendTaskDTO.setGetAllPhoneFlag(1);
                                }
                                JsmsAgentInfo jsmsAgentInfo = jsmsAgentInfoService.getByAgentId(list.get(i).getAgentId());
                                timerSendTaskDTO.setCreator(jsmsAgentInfo.getAgentName());
                            }
                        }
                    }
                    /*if(!list.get(i).getStatus().equals("0")){
                        timerSendTaskDTO.setGetAllPhoneFlag(1);
                    }*/
                    timerSendTaskDTO.setRowNum(index);
                    timerSendTaskDTOList.add(timerSendTaskDTO);
                }
            }
        }
        page.setData(timerSendTaskDTOList);
        return page;
    }

    /**
     * 取消发送任务, 提供幂等性接口(判断任务状态)
     * @param taskId 定时发送任务id
     * @return
     */
    @Override
    public ResultVO cancelSendTask(String taskId,DateTime dateTime,String creatorId) {
        JsmsTimerSendTask oldModel = this.timerSendTaskMapper.getByTaskId(taskId);
        if (oldModel == null){
            return ResultVO.failure(Code.OPT_ERR_NOT_FOUND, "定时任务不存在，请刷新后再试");
        }
        if (TaskSubmitTypeEnum.代理商.getValue().equals(oldModel.getSubmittype())
                && !oldModel.getAgentId().toString().equals(creatorId)){
            return ResultVO.failure(Code.OPT_ERR_FORBIDDEN, "您不是当前短信任务的创建者，不能取消改任务！");
        }else if(TaskSubmitTypeEnum.子账户.getValue().equals(oldModel.getSubmittype())
                && !oldModel.getClientid().equals(creatorId)){
            return ResultVO.failure(Code.OPT_ERR_FORBIDDEN, "您不是当前短信任务的创建者，不能取消改任务！");
        }

        if(! SmsSendStatus.等待中.getValue().equals(oldModel.getStatus())){
            return ResultVO.failure(Code.OPT_ERR_FORBIDDEN, "只有等待中的短信任务可以取消！");
        }
        DateTime sendTime = new DateTime(oldModel.getSetSendTime());
        if (dateTime.plusMinutes(jsmsSendParam.getSendCFG(JsmsSendParam.TimeSendCFG.取消发送最小间隔)).compareTo(sendTime) > 0){
            /*StringBuilder msg = new StringBuilder("距离实际发送时间小于");
            msg.append(TimeSendCFG.取消发送最小间隔.getValue())
                    .append("分钟，不能取消");*/
            return ResultVO.failure(Code.OPT_ERR_FORBIDDEN, "本短信即将发送，无法取消！");
        }
        JsmsTimerSendTask newModel = new JsmsTimerSendTask();
        newModel.setStatus(SmsSendStatus.已取消.getValue());
//        this.timerSendTaskMapper.updateSelective();
        int i = this.timerSendTaskMapper.updateIdempotent(oldModel, newModel);
        if(i > 0){
            return ResultVO.successDefault("发送任务已取消");
        }else{
            return ResultVO.failure(Code.OPT_ERR_NOT_FOUND,"当前记录不是最新记录，请刷新");
        }
    }


    @Override
    public R updateComponentId(String taskId,String newComponentId) {
        JsmsTimerSendTask timerSendTask = timerSendTaskMapper.getByTaskId(taskId);
        if(timerSendTask==null){
            return R.error(Code.OPT_ERR,"定时任务不存在");
        }
        if (StringUtils.isEmpty(newComponentId)){
            return R.error(Code.OPT_ERR,"组件id为空");
        }
        //校验状态
        if (SmsSendStatus.等待中.getValue()!=timerSendTask.getStatus()){
            return R.error(Code.OPT_ERR,"本短信即将发送，无法更改组件！");
        }
        //组件类型校验（如果客户选择发送的smsfrom类型为http，新选择的只能是http类型组件，否则为c2s）
        JsmsComponentConfigure oldConf = jsmsComponentConfigureService.getByComponentId(timerSendTask.getComponentId());
        JsmsComponentConfigure componentConfigure = jsmsComponentConfigureService.getByComponentId(Integer.valueOf(newComponentId));
        if (!oldConf.getComponentType().equals(componentConfigure.getComponentType())){
            String componentTypeStr = "";
            if ("00".equals(oldConf.getComponentType())){
                componentTypeStr = "smsp_c2s";
            }else if ("08".equals(oldConf.getComponentType())){
                componentTypeStr = "smsp_http";
            }
            return R.error(Code.OPT_ERR,"组件类型不一致，请选择类型为"+componentTypeStr+"的组件");
        }
        //校验前(TIMER_SEND_CFG设置的取消定时短信时发送时间的最小间隔)分钟
        Date SendTime = timerSendTask.getSetSendTime();
        Integer diff = jsmsSendParam.getSendCFG(JsmsSendParam.TimeSendCFG.取消发送最小间隔);
        if(System.currentTimeMillis() + (diff * 60 * 1000) > SendTime.getTime()){
            return R.error(Code.OPT_ERR,"本短信即将发送，无法更改组件！");
        }
        timerSendTask.setComponentId(Integer.valueOf(newComponentId));
        int i = timerSendTaskMapper.updateComponentId(timerSendTask);
        if(i<0){
            return R.error(Code.OPT_ERR,"更改组件失败！");
        }
        return R.ok("更改组件成功！");
    }
}
