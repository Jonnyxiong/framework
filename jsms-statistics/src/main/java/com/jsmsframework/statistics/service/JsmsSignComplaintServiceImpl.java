package com.jsmsframework.statistics.service;

import com.jsmsframework.channel.entity.JsmsChannel;
import com.jsmsframework.channel.entity.JsmsComplaintList;
import com.jsmsframework.channel.service.JsmsChannelService;
import com.jsmsframework.channel.service.JsmsComplaintListService;
import com.jsmsframework.common.constant.SysConstant;
import com.jsmsframework.common.dto.R;

import com.jsmsframework.common.enums.SmsTypeEnum;
import com.jsmsframework.common.enums.balckList.SmstypesType;
import com.jsmsframework.common.enums.complaint.ComplaintStatus;
import com.jsmsframework.common.enums.middleware.MiddlewareType;
import com.jsmsframework.common.enums.record.LongsmsEnum;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.common.util.DateUtil;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.common.util.StringUtils;
import com.jsmsframework.middleware.entity.JsmsMiddlewareConfigure;
import com.jsmsframework.middleware.service.JsmsMiddlewareConfigureService;
import com.jsmsframework.record.dto.RecordDTO;

import com.jsmsframework.record.record.entity.JsmsRecord;
import com.jsmsframework.record.service.JsmsRecordService;
import com.jsmsframework.redis.TrendRedisUtils;
import com.jsmsframework.statistics.exception.JsmsSignComplaintException;
import com.jsmsframework.sysConfig.entity.JsmsWhiteList;
import com.jsmsframework.sysConfig.exception.JsmsWhiteListException;
import com.jsmsframework.sysConfig.service.JsmsWhiteListService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

import java.util.*;
import java.util.regex.Pattern;

@Component
public class JsmsSignComplaintServiceImpl implements JsmsSignComplaintService {
    private Logger logger = LoggerFactory.getLogger(JsmsSignComplaintServiceImpl.class);

    @Autowired
    private JsmsRecordService jsmsRecordService;

    @Autowired
    private JsmsChannelService jsmsChannelService;

    @Autowired
    private JsmsComplaintListService jsmsComplaintListService;

    @Autowired
    private TrendRedisUtils trendRedisUtils;

    @Autowired
    private JsmsWhiteListService jsmsWhiteListService;

    @Autowired
    private JsmsMiddlewareConfigureService jsmsMiddlewareConfigureService;

   /* @Autowired
    private RedisService redisService;*/

    /**
     * 标记单条短信投诉
     *
     * @param record
     * @return
     */
    @Override
    public R signSingleComplaint(RecordDTO record) {

        String content=null;
        //1.判断是否长短信 是根据SMSID+phone及smsindex顺序合并短信内容返回
        if(Objects.equals(record.getLongsms(), LongsmsEnum.长短信.getValue())){
             content=mergeContent(record);
            if(content==null){
                R.error("查询短信记录对应通道信息为空");
            }

        }else {
            content=record.getContent();
        }

        //2.判断 （通道ID+处理日期+手机号码+签名+内容+客户ID+短信类型）是否存在
        Map<String,Object> param=new HashedMap();
        param.put("channelId",record.getChannelid());

        param.put("sendTime",DateUtil.dateToStr(record.getDate(),"yyyy-MM-dd"));
        param.put("phone",record.getPhone());
        param.put("sign",record.getSign());
        param.put("content",content);
        param.put("clientId",record.getClientid());
        param.put("smstype",record.getSmstype());

        List<JsmsComplaintList> lists=jsmsComplaintListService.findList(param);
        if(!lists.isEmpty()){
            return  R.error("投诉记录已存在");
        }

        //组装投诉明细信息
        JsmsComplaintList insertData=new JsmsComplaintList();
        insertData.setChannelId(record.getChannelid());
        insertData.setClientId(record.getClientid());
        insertData.setOperatorstype(record.getOperatorstype());
        insertData.setSmstype(record.getSmstype());
        insertData.setSendTime(record.getDate());
        insertData.setPhone(record.getPhone());
        insertData.setSign(record.getSign());
        insertData.setContent(content);
        insertData.setBelongBusiness(record.getBelongBusiness());
        insertData.setBelongSale(record.getBelongSale());
        insertData.setOperator(record.getOperator());
        insertData.setStatus(ComplaintStatus.正常.getValue());
        insertData.setCreateTime(new Date());
        insertData.setUpdateTime(new Date());
     //   insertData.setRemark(null);
        int add=jsmsComplaintListService.insert(insertData);
        if(add>0){
            logger.debug("插入投诉明细成功,操作数据为data={},成功数据为insert={}",JsonUtil.toJson(record), JsonUtil.toJson(insertData));
            R.ok("插入投诉明细成功");
        }else {
           logger.error("插入投诉明细失败,操作数据为data={},成功数据为insert={}",JsonUtil.toJson(record), JsonUtil.toJson(insertData));
            R.error("插入投诉明细失败");
            throw  new JsmsSignComplaintException("插入投诉明细失败,操作数据为data="+JsonUtil.toJson(record)+",成功数据为insert="+JsonUtil.toJson(insertData));

        }

        //3.添加号码至系统黑名单相关操作
        R r=phoneToBalckList(record.getPhone(),record.getSmstype(),record.getOperator());
        if(Objects.equals(r.getCode(),SysConstant.FAIL_CODE)){
            R.error(r.getMsg());
            throw new JsmsSignComplaintException("添加号码至系统黑名单相关操作失败！");
        }

        return R.ok("插入投诉明细成功");
    }


    /**根据SMSID+phone及smsindex顺序合并短信内容返回
     * 合并短信内容
     * @param record
     * @return
     */
    private   String  mergeContent(RecordDTO record){
        StringBuffer endContent=new StringBuffer();
        //1.查询相同

        Map<String,Object> params=new HashMap<>();
        if(StringUtils.isNotBlank(record.getTableName())){
            params.put("tableName",record.getTableName());
        }else {
            //根据channelId查identify,date转换日期
            JsmsChannel channel=jsmsChannelService.getByCid(record.getChannelid());
            if(channel==null){
                logger.error("查询短信记录对应通道信息为空,data={}", JsonUtil.toJson(record));
                return  null;
            }
            Integer identify=channel.getIdentify();
            String  dateTime= DateUtil.dateToStr(record.getDate(),"yyyyMMdd");
            String tableName= SysConstant.T_SMS_RECORD_+identify+"_"+dateTime;
            record.setTableName(tableName);
            params.put("tableName",tableName);

        }


        params.put("smsid",record.getSmsid());
        params.put("phone",record.getPhone());

        List<JsmsRecord> recordList=jsmsRecordService.findList(params);
        //2.拼接内容
        if(!recordList.isEmpty()){
            for (JsmsRecord jsmsRecord : recordList) {
                endContent.append(jsmsRecord.getContent());
            }
        }else {
            endContent.append(record.getContent());
        }


        String meshContent=endContent.toString();
        String sign="【"+record.getSign()+"】";
        if(isSigns(meshContent,sign)){

            String lastContent=endContent.toString().replace(sign,"");
            if(meshContent.indexOf(sign)==0){
                return sign+lastContent;
            }else {
                return lastContent+sign;
            }

        }else {
            return meshContent;
        }


    }


    public boolean isSigns(String parent,String son){
        int counter=0;

        counter=stringNumbers(counter,parent,son);

        return counter>1?true:false;
    }


    public  int stringNumbers(int counter,String parent,String son) {
        for (int i = 0; i <= parent.length() - son.length(); i++) {
            if (parent.substring(i, i + son.length()).equals(son)) {
                counter++;
            }
        }
        return counter;
    }


    private Long recordSmsTypeTobalckSmsType(Integer smstype){
        Long smsTypes=Long.valueOf(smstype);
        if(SmstypesType.通知.getDesc().equals(SmsTypeEnum.getDescByValue(smstype))){
            smsTypes=Long.valueOf(SmstypesType.通知.getValue());
        }
        if(SmstypesType.验证码.getDesc().equals(SmsTypeEnum.getDescByValue(smstype))){
            smsTypes=Long.valueOf(SmstypesType.验证码.getValue());
        }
        if(SmstypesType.营销.getDesc().equals(SmsTypeEnum.getDescByValue(smstype))){
            smsTypes=Long.valueOf(SmstypesType.营销.getValue());
        }
        if(SmstypesType.告警.getDesc().equals(SmsTypeEnum.getDescByValue(smstype))){
            smsTypes=Long.valueOf(SmstypesType.告警.getValue());
        }
        if(SmstypesType.USSD.getDesc().equals(SmsTypeEnum.getDescByValue(smstype))){
            smsTypes=Long.valueOf(SmstypesType.USSD.getValue());
        }
        if(SmstypesType.闪信.getDesc().equals(SmsTypeEnum.getDescByValue(smstype))){
            smsTypes=Long.valueOf(SmstypesType.闪信.getValue());
        }
        return  smsTypes;
    }

    /**
     * 手机号添加至系统黑名单操作
     * @param phone
     * @return
     */
    public R phoneToBalckList(String phone, Integer smstype, Long operator){


        Long smsTypes=recordSmsTypeTobalckSmsType(smstype);


        JsmsWhiteList check=jsmsWhiteListService.getByPhone(phone);
        if(check==null){
            JsmsWhiteList white=new JsmsWhiteList();
            white.setPhone(phone);
            white.setRemarks("标记投诉");
            white.setCreatetime(new Date());
            white.setSmstypes(smsTypes);
            white.setOperatorId(operator);
            int add=jsmsWhiteListService.insert(white);

            if(add>0){

                R r=this.balckToRedis(phone,smsTypes);
                if(Objects.equals(r.getCode(),SysConstant.FAIL_CODE)){
                    logger.error("保存系统黑名单到Redis时系统错误,phone={}",phone);
                    R.error("保存系统黑名单到Redis时系统错误");
                    throw  new JsmsSignComplaintException("保存系统黑名单到Redis时系统错误，请联系管理员");
                }
                logger.debug("标记投诉手机号码入系统黑名单成功！phone={},smstypes={}",phone,smsTypes);
                R.ok("标记投诉手机号码入系统黑名单成功！");
            }else {
                logger.error("标记投诉手机号码入系统黑名单失败,phone={}",phone);
                R.error("标记投诉手机号码入系统黑名单失败");
                throw  new JsmsSignComplaintException("标记投诉手机号码入系统黑名单失败,phone="+phone);


            }
        }else {

            smsTypes=(check.getSmstypes() & smsTypes)==smsTypes?check.getSmstypes():check.getSmstypes()+smsTypes;

            JsmsWhiteList updateData=new JsmsWhiteList();
            BeanUtil.copyProperties(check,updateData);
            updateData.setRemarks("标记投诉");
            updateData.setCreatetime(new Date());
            updateData.setSmstypes(smsTypes);
            updateData.setOperatorId(operator);

            int up=jsmsWhiteListService.update(updateData);
            if(up>0){
                R r=this.balckToRedis(phone,smsTypes);
                if(Objects.equals(r.getCode(),SysConstant.FAIL_CODE)){
                    logger.error("保存系统黑名单到Redis时系统错误,phone={}",phone);
                    R.error("保存系统黑名单到Redis时系统错误");
                    throw  new JsmsSignComplaintException("保存系统黑名单到Redis时系统错误，请联系管理员");

                }
                R.ok("保存系统黑名单成功！");
            }else {
                logger.error("标记投诉手机号码入系统黑名单失败,phone={}",phone);
                R.error("更新系统黑名单失败");
                throw  new JsmsSignComplaintException("更新系统黑名单失败,当前操作数据为"+JsonUtil.toJson(updateData));
            }

        }







        //查redis系统黑名单
     /*   String key=SysConstant.BLACK_LIST_KEY_PREFIX.concat(phone);
        Integer index=SysConstant.BLACK_LIST_DB;
        String channelId=SysConstant.SYS_BLACK_LIST_CHANNEID;

       JsmsMiddlewareConfigure middle=jsmsMiddlewareConfigureService.getByMiddlewareType(MiddlewareType.redis.getValue());

        logger.debug("获取组件表中Redis配置信息,redis={}",JsonUtil.toJson(middle));*/


       // Jedis jedis=redisUtils.getNewJedis(middle.getHostIp(),Integer.valueOf(middle.getPort()));

      //  String value = redisUtils.getSpecificDb(key, index);
       /* JedisShardInfo info=new JedisShardInfo(middle.getHostIp(),Integer.valueOf(middle.getPort()));


        //  小强版
        logger.debug("【系统黑名单】读库获取redis指定DB中key开始,redis={},key={},DB={}",JsonUtil.toJson(info),key,index);
        String value=redisService.get(info,key,index);
        logger.debug("【系统黑名单】读库获取redis指定DB中key结束,value={}",value);*/

        //
       /* String host=middle.getHostIp();
        Integer port=Integer.valueOf(middle.getPort());

        logger.debug("【系统黑名单】读库获取redis指定DB中key开始,host={},port={},key={},DB={}",host,port,key,index);
        String value= trendRedisUtils.getSpecificDb(host,port,key,index);
        logger.debug("【系统黑名单】读库获取redis指定DB中key结束,value={}",value);


        String newValue;

        if(StringUtils.isBlank(value)){
            //系统黑名单入库
            JsmsWhiteList addblack=new JsmsWhiteList();
            addblack.setPhone(phone);
            addblack.setRemarks("标记投诉");
            addblack.setCreatetime(new Date());
            int add=jsmsWhiteListService.insert(addblack);
            if (add > 0) {
                logger.debug("标记投诉手机号码入系统黑名单成功,phone={}",phone);

            }else {
                logger.error("标记投诉手机号码入系统黑名单失败,phone={}",phone);
                R.error("标记投诉手机号码入系统黑名单失败");
                throw  new JsmsSignComplaintException("标记投诉手机号码入系统黑名单失败,phone="+phone);

            }

            // 系统黑名单为全局黑名单，如果黑名单只有系统级别的保存为 0&
            newValue = channelId + "&";

            //小强版
            *//*logger.debug("【系统黑名单】读库存储redis指定DB中key开始,redis={},key={},value={},DB={}",JsonUtil.toJson(info),key,newValue,index);
            String result=redisService.stringSet(info,key,newValue, index);
            logger.debug("【系统黑名单】读库存储redis指定DB中key结束,result={}",result);*//*

            logger.debug("【系统黑名单】读库存储redis指定DB中key开始,host={},port={},key={},DB={}",host,port,key,newValue,index);
            String result= trendRedisUtils.setSpecificDb(host,port,key,newValue,index);
            logger.debug("【系统黑名单】读库存储redis指定DB中key结束,result={}",result);

            if(StringUtils.isBlank(result)){
                logger.warn("保存系统黑名单到Redis时系统错误, 关键字={}", phone);
                  R.error("保存系统黑名单到Redis时系统错误，请联系管理员");
                throw new JsmsSignComplaintException("保存系统黑名单到Redis时系统错误，请联系管理员");
            }

        }else{
            // 如果当前号码既是系统黑名单又是通道黑名单则拼接保存，格式为 0&channelId&channelId&
            Set<String> set = new HashSet<String>(Arrays.asList(value.split("&")));
            if(!set.contains(channelId)){
                set.add(channelId);
            }
            newValue =StringUtils.join(set, "&").concat("&"); // 必须以 & 结尾

            //小强版
            *//*logger.debug("【系统黑名单】读库存储redis指定DB中key开始,redis={},key={},value={},DB={}",JsonUtil.toJson(info),key,newValue,index);
            String upreuslt =redisService.stringSet(info,key, newValue, index);
            logger.debug("【系统黑名单】读库存储redis指定DB中key结束,result={}",upreuslt);*//*

            logger.debug("【系统黑名单】读库存储redis指定DB中key开始,host={},port={},key={},DB={}",host,port,key,newValue,index);
            String upreuslt = trendRedisUtils.setSpecificDb(host,port,key,newValue,index);
            logger.debug("【系统黑名单】读库存储redis指定DB中key结束,result={}",upreuslt);


            if(StringUtils.isBlank(upreuslt)){
                logger.warn("保存系统黑名单到Redis时系统错误, 关键字={}", phone);
                  R.error("保存系统黑名单到Redis时系统错误，请联系管理员");
                throw new JsmsSignComplaintException("保存系统黑名单到Redis时系统错误，请联系管理员");
            }

        }*/

        return  R.ok("手机号码至系统黑名单操作成功！");
    }


    public R balckToRedis(String phone,Long smsTypes){
        //查redis系统黑名单
        String key= SysConstant.BLACK_LIST_KEY_PREFIX.concat(phone);
        Integer index=SysConstant.BLACK_LIST_DB;
        String channelId=SysConstant.SYS_BLACK_LIST_CHANNEID;

        JsmsMiddlewareConfigure middle=jsmsMiddlewareConfigureService.getByMiddlewareType(MiddlewareType.redis.getValue());

        logger.debug("获取组件表中Redis配置信息,redis={}", JsonUtil.toJson(middle));


        //
        String host=middle.getHostIp();
        Integer port=Integer.valueOf(middle.getPort());

        logger.debug("【系统黑名单】读库获取redis指定DB中key开始,host={},port={},key={},DB={}",host,port,key,index);
        String value= trendRedisUtils.getSpecificDb(host,port,key,index);
        logger.debug("【系统黑名单】读库获取redis指定DB中key结束,value={}",value);


        String newValue="";
        String sysbalck=channelId + ":"+smsTypes+"&";
        if(com.jsmsframework.common.util.StringUtils.isBlank(value)){
            // 系统黑名单为全局黑名单，如果黑名单只有系统级别的保存为 0:短信类型&
            newValue = channelId + ":"+smsTypes+"&";

            logger.debug("【系统黑名单】读库存储redis指定DB中key  开始,host={},port={},key={},DB={}",host,port,key,newValue,index);
            String result= trendRedisUtils.setSpecificDb(host,port,key,newValue,index);
            logger.debug("【系统黑名单】读库存储redis指定DB中key结束,result={}",result);
            if(com.jsmsframework.common.util.StringUtils.isBlank(result)){
                logger.warn("保存系统黑名单到Redis时系统错误, 关键字={}", phone);
                R.error("保存系统黑名单到Redis时系统错误，请联系管理员");
                throw new JsmsSignComplaintException("保存系统黑名单到Redis时系统错误，请联系管理员");
            }

        }else{
            // 如果当前号码既是系统黑名单又是通道黑名单则拼接保存，格式为 0:短信类型&channelId&channelId&
            if(value.indexOf(":")!=-1){
                String[] listary=value.split("&");
                newValue=value.replace(listary[0]+"&","");
            }else {
                newValue=value;
            }
            newValue=sysbalck+newValue;




            logger.debug("【系统黑名单】读库存储redis指定DB中key开始,host={},port={},key={},DB={}",host,port,key,newValue,index);
            String upreuslt = trendRedisUtils.setSpecificDb(host,port,key,newValue,index);
            logger.debug("【系统黑名单】读库存储redis指定DB中key结束,result={}",upreuslt);


            if(com.jsmsframework.common.util.StringUtils.isBlank(upreuslt)){
                logger.warn("保存系统黑名单到Redis时系统错误, 关键字={}", phone);
                R.error("保存系统黑名单到Redis时系统错误，请联系管理员");
                throw new JsmsSignComplaintException("保存系统黑名单到Redis时系统错误，请联系管理员");
            }

        }

        return  R.ok("系统黑名单至redis操作成功！");



    }
}
