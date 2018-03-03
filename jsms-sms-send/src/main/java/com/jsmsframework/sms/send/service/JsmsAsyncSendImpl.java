package com.jsmsframework.sms.send.service;

import com.alibaba.fastjson.JSON;
import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.entity.JsmsParam;
import com.jsmsframework.common.enums.HttpProtocolType;
import com.jsmsframework.common.service.JsmsParamService;
import com.jsmsframework.common.util.file.FileIO;
import com.jsmsframework.sms.send.entity.JsmsSubmitProgress;
import com.jsmsframework.sms.send.exception.JsmsAsyncSendException;
import com.jsmsframework.sms.send.po.*;
import com.jsmsframework.sms.send.util.JsmsCacheMap;
import com.jsmsframework.sms.send.util.JsmsHttpSend;
import com.jsmsframework.user.entity.JsmsAccount;
import com.jsmsframework.user.service.JsmsAccountService;
import net.sf.ehcache.util.NamedThreadFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class JsmsAsyncSendImpl implements JsmsAsyncSend {
    /**
     * t_sms_param表中提交速率param_key对应值
     */
    private static final String SUBMIT_RATE = "SUBMIT_RATE";
    private static final String DEFAULT_SEND_THREAD_NAME = "async-send-thread";
    /**
     * 提交请求的线程池线程数
     */
    private static final int SUBMIT_THREAD_NUM = 50;
    /**
     * 向队列添加对象尝试次数
     */
    private static final int TRY_PUT_TIMES = 1;

    private Logger logger = LoggerFactory.getLogger(JsmsAsyncSendImpl.class);
    private LinkedBlockingQueue<JsmsAccessSmsProgress> linkedBlockingQueue = new LinkedBlockingQueue<>();
    private JsmsCacheMap<Object, Object> jsmsCache = JsmsCacheMap.getDefault();
    private static String errDataPath;
    private JsmsSendRate jsmsSendRate;
    @Autowired
    private JsmsParamService jsmsParamService;
    @Autowired
    private JsmsSubmitProgressService jsmsSubmitProgressService;
    @Autowired
    private JsmsAccountService jsmsAccountService;

    /**
     * 初始化或更新速率
     */
    private void initOrSetSendRate() {
        logger.debug("初始化或重新加载速率  --> start");
        List<JsmsParam> paramList = jsmsParamService.getByParamKey(SUBMIT_RATE);
        Integer sendRate = null;
        if (!paramList.isEmpty()) {
            JsmsParam jsmsParam = paramList.get(0);
            try {
                sendRate = Integer.parseInt(jsmsParam.getParamValue());
                logger.debug("数据库中设置的速率 SUBMIT_RATE ==> {}", sendRate);
            } catch (NumberFormatException e) {
                logger.error("短信提交速率参数SUBMIT_RATE配置异常 --> {}", jsmsParam.getParamValue());
            }
        }

        if (jsmsSendRate == null) { // 不存在, 则初始化
            jsmsSendRate = new JsmsSendRate();
            if (sendRate != null) {
                jsmsSendRate.getRate().set(sendRate);
                jsmsSendRate.getCount().set(jsmsSendRate.getRate().intValue());
            }
        } else { // 如果对象已经存在, 则只更新速率
            if (sendRate != null) {
                jsmsSendRate.getRate().set(sendRate);
            }
        }
        long oldTimeStamp = jsmsSendRate.getTimeStamp().longValue();
        if (linkedBlockingQueue.isEmpty()) {
            jsmsSendRate.getTimeStamp().compareAndSet(oldTimeStamp, System.currentTimeMillis());
        }
        logger.debug("初始化或重新加载速率  --> end , 当前速率为 ==> {}", jsmsSendRate.getRate().intValue());

    }


    @Override
    public void initSendThread(final String url4Json,final String url4Form, String errDataPath, String sendThreadName) {
        if (StringUtils.isBlank(errDataPath)) {
            throw new IllegalArgumentException("未配置异常发送数据保存地址, 用于保存发送重试依然无法发送的短信记录");
        }
        this.errDataPath = errDataPath;
        initOrSetSendRate(); //
        if (StringUtils.isBlank(sendThreadName)) {
            sendThreadName = DEFAULT_SEND_THREAD_NAME;
        }
        ThreadFactory sendThread = new NamedThreadFactory(sendThreadName + System.currentTimeMillis());
        Thread daemonThread = sendThread.newThread(new Runnable() {
            @Override
            public void run() {
                ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2); // 线程数量 = (cpu数量 * 2)
                while (true) {
                    try {
                        logger.debug("准备从队列中取出任务, 剩余 ==> {},速率 ==> {}, 任务计数器 ==> {}", linkedBlockingQueue.size(), jsmsSendRate.getRate().intValue(), jsmsSendRate.getCount().intValue());
                        JsmsAccessSmsProgress jsmsAccessSmsProgress = linkedBlockingQueue.take();
//                        exec.execute(new InvokeHttpThread(url, jsmsSendRate, jsmsAccessSmsProgress, linkedBlockingQueue));
                        exec.submit(new InvokeHttpThread(url4Json,url4Form,jsmsSendRate, jsmsAccessSmsProgress, linkedBlockingQueue));

                        /*Integer submitNum = submit.get();
                        logger.debug("成功提交 --> {}", submitNum);*/
                    } catch (InterruptedException e) {
                        logger.error("linkedBlockingQueue.take() 异常 -->{}", e);
                    } /*catch (ExecutionException e) {
                        logger.error("短信按速率异步提交异常 ---> {}", e);
                    }*/
                }
            }
        });
        daemonThread.setDaemon(true);
        daemonThread.start();

    }

    @Override
    public void putListIntoSendQueue(List jsmsAccessObjList) {
        putListIntoSendQueue(jsmsAccessObjList, TRY_PUT_TIMES);
    }

    /*@Override
    public void putIntoSendQueue(Object jsmsAccessObj) {
        putIntoSendQueue(jsmsAccessObj,TRY_PUT_TIMES);
    }*/

    @Override
    public void putListIntoSendQueue(List jsmsAccessObjList, int tryTimes) {
        initOrSetSendRate();
        logger.debug("添加发送任务到队列  --> start, 任务数 ==> {}", jsmsAccessObjList.size());
        for (Object jsmsAccess : jsmsAccessObjList) {
            try {
                putIntoSendQueue(jsmsAccess, tryTimes);
            } catch (JsmsAsyncSendException e) {
                // 记录 jsmsAccess
            }

        }
        logger.debug("添加发送任务到队列  --> end");

    }

    private void putIntoSendQueue(Object jsmsAccessObj, int tryTimes) {
        tryTimes--;
        if (tryTimes < 0) {
            if (tryTimes == -1) {
                throw new JsmsAsyncSendException("尝试添加对象到发送任务队列失败");
            }
        }
        if (jsmsAccessObj instanceof JsmsAccessSmsProgress) {
            try {
                linkedBlockingQueue.put((JsmsAccessSmsProgress) jsmsAccessObj);
            } catch (InterruptedException e) {
                logger.error("添加对象到发送任务队列失败, 异常 -->{}\r\n 睡眠1毫秒后重试", e);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e1) {
                    logger.error("添加对象到发送任务队列失败, 睡眠异常 ---- > {}", e1);
                }
                putIntoSendQueue(jsmsAccessObj, tryTimes);
            }
        }
    }

    private JsmsAccessSms getAccessSms(int progressId) {
        JsmsAccessSms jsmsAccessSms = (JsmsAccessSms) jsmsCache.get("JsmsSubmitProgress_" + progressId);
        if (jsmsAccessSms != null){
            return jsmsAccessSms.clone();
        }
        Lock lock = new ReentrantLock();
        lock.lock();
        JsmsAccessSms newObj;
        try {
            jsmsAccessSms = (JsmsAccessSms) jsmsCache.get("JsmsSubmitProgress_" + progressId);

            if (jsmsAccessSms == null) {
                JsmsSubmitProgress submitProgress = jsmsSubmitProgressService.getById(progressId);
                JsmsAccount jsmsAccount = jsmsAccountService.getByClientId(submitProgress.getClientId());
                if(HttpProtocolType.HTTPS_JSON.getValue().equals(jsmsAccount.getHttpProtocolType())){
                    jsmsAccessSms = new JsmsSendJson();
                }else if(HttpProtocolType.HTTPS_GET_POST.getValue().equals(jsmsAccount.getHttpProtocolType())){
                    jsmsAccessSms = new JsmsSendForm();
                }else{
                    jsmsAccessSms = new JsmsAccessSms();
                }
                jsmsAccessSms.setClientid(submitProgress.getClientId());
                try {
                    jsmsAccessSms.setPassword(com.jsmsframework.common.util.StringUtils.getMD5(jsmsAccount.getPassword()));
                } catch (NoSuchAlgorithmException e) {
                    logger.error("发送短信, md5加密失败 -->{}", e);
                }
                jsmsAccessSms.setSmstype(submitProgress.getSmstype().toString());
                StringBuilder content = new StringBuilder("【")
                        .append(submitProgress.getSign()).append("】")
                        .append(submitProgress.getContent());
                jsmsAccessSms.setContent(content.toString());
                jsmsCache.put("JsmsSubmitProgress_" + progressId, jsmsAccessSms);
            }
            newObj = jsmsAccessSms.clone();
        } finally {
            lock.unlock();
        }

        return newObj;
    }

    /**
     * 调用后台http组件
     */
    class InvokeHttpThread implements Callable<Integer> {
        private final Logger logger = LoggerFactory.getLogger(InvokeHttpThread.class);
        private String url4Json;
        private String url4Form;
        private JsmsSendRate jsmsSendRate;
        private JsmsAccessSmsProgress jsmsAccessSmsProgress;
        private BlockingQueue blockingQueue;

        public InvokeHttpThread(String url4Json,String url4Form, JsmsSendRate jsmsSendRate, JsmsAccessSmsProgress jsmsAccessSmsProgress, BlockingQueue blockingQueue) {
            this.url4Json = url4Json;
            this.url4Form = url4Form;
            this.jsmsSendRate = jsmsSendRate;
            this.jsmsAccessSmsProgress = jsmsAccessSmsProgress;
            this.blockingQueue = blockingQueue;

        }

        @Override
        public Integer call() {
            // 本批次提交的开始时间
            long timeStamp = jsmsSendRate.getTimeStamp().longValue();
            int count = jsmsSendRate.getCount().decrementAndGet();
            logger.debug("本批次提交的开始时间的开始时间 ==> {},任务计数器 ==> {}", timeStamp, count);
            int send = 0;
            if (count < 0) {
                sleep(timeStamp);
                call();
            } else {
                send = send(timeStamp);
            }
            return send;
        }

        private int send(long timeStamp) {
            int progressId = jsmsAccessSmsProgress.getProgressId();
            String mobile = jsmsAccessSmsProgress.getMobileBatch();
            JsmsAccessSms jsmsAccessSms = getAccessSms(progressId);
            jsmsAccessSms.setMobile(mobile);
            // 提交到后台http组件
            ResultVO resultVO;
            if(jsmsAccessSms instanceof JsmsSendJson){
                String smsAccessUrl = url4Json.replaceFirst("\\{[^\\}]*\\}", jsmsAccessSms.getClientid());
                resultVO = JsmsHttpSend.INSTANCE.invokeHttpToSend(smsAccessUrl, JSON.toJSONString(jsmsAccessSms), ContentType.APPLICATION_JSON, JsmsAccessSmsRespone.class);
            }else if(jsmsAccessSms instanceof JsmsSendForm){
                String smsAccessUrl = url4Form.replaceFirst("\\{[^\\}]*\\}", jsmsAccessSms.getClientid());
                String formString = ((JsmsSendForm) jsmsAccessSms).toFormString();
                resultVO = JsmsHttpSend.INSTANCE.invokeHttpToSend(smsAccessUrl,formString,ContentType.APPLICATION_FORM_URLENCODED,JsmsAccessSmsRespone.class);
            }else{
                String smsAccessUrl = url4Json.replaceFirst("\\{[^\\}]*\\}", jsmsAccessSms.getClientid());
                resultVO = JsmsHttpSend.INSTANCE.invokeHttpToSend(smsAccessUrl, JSON.toJSONString(jsmsAccessSms), JsmsAccessSmsRespone.class);
            }

            if (resultVO.isSuccess()) {
                // 更新数据库提交进度
                int mobileNum = mobile.split("\\D").length;
                logger.debug("实际发送的号码数量 ==> {}", mobileNum);
                int i = jsmsSubmitProgressService.updateProgress(progressId, mobileNum);
                if (i > 0) {
                    logger.debug("更新t_sms_submit_progress 成功, id => {}, actual_submit += {}", progressId, mobileNum);
                } else {
                    logger.debug("更新t_sms_submit_progress 失败, id => {}, actual_submit += {}", progressId, mobileNum);
                }
                if (blockingQueue.isEmpty()) {
                    finishBatchData();
                }
                return mobileNum;
            } else {
                // 判断重试次数
                int retryTimes = jsmsAccessSmsProgress.getFailRetryTimes().decrementAndGet();
                if (retryTimes < 0) {
                    // 失败后, 重试5次依然不能发送, 写在异常文件中。 发告警
                    if (retryTimes == -1) { // 可能多个线程同时小于0,只需要一个线程
                        String path = new StringBuilder(errDataPath).append(File.separatorChar)
                                .append("send-fail-")
                                .append(DateTime.now().toString("yyyy-MM-dd")).append(".dat").toString();
                        FileIO.writeLine(path, JSON.toJSONString(jsmsAccessSmsProgress), true, true);
                    }
                } else {
                    try {
                        putIntoSendQueue(jsmsAccessSmsProgress, 1);
                    } catch (JsmsAsyncSendException e) {
                        e.printStackTrace();
                    }
                }
                return 0;
            }
        }

        private void sleep(long timeStamp) {
            long now = System.currentTimeMillis();
            long execMills = now - timeStamp;   // 执行时间
            logger.debug("本批次提交的结束 耗时 ---- > {}", execMills);
            if (execMills < jsmsSendRate.DEFAULT_MILLIS) {
                try {
                    long sleepMills = jsmsSendRate.DEFAULT_MILLIS - execMills;
                    logger.debug("本批次提交的结束 执行时间未满 ==> {} , 开始睡眠 ==> {}", jsmsSendRate.DEFAULT_MILLIS, sleepMills);
                    Thread.sleep(sleepMills);
                } catch (InterruptedException e) {
                    logger.error("本批次提交的结束, 睡眠异常 ---- > {}", e);
                }
            }
            // 如果多个线程同时睡眠, 在睡眠完毕后, 只需要一个线程修改每批次的开始时间戳
            long newTimeStamp = System.currentTimeMillis();
            boolean set = jsmsSendRate.getTimeStamp().compareAndSet(timeStamp, System.currentTimeMillis());
            if (set) {
                jsmsSendRate.getCount().set(jsmsSendRate.getRate().intValue());
                logger.debug("本批次提交的结束,开始时间戳 ==> {}, 重置下批次开始时间戳 ==> {}", timeStamp, newTimeStamp);
            }
        }

        private void finishBatchData() {

            // 更新数据库 实际提交数 = 总提交数 的记录 状态为 : 提交完成
            int i = jsmsSubmitProgressService.finishSubmitState();
            logger.debug("更新t_sms_submit_progress中actual_submit=submit_total的记录为【提交完成】 ==>{}条", i);
        }
    }

}
