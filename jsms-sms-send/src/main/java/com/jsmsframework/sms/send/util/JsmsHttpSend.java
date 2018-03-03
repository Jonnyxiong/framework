package com.jsmsframework.sms.send.util;

import com.jsmsframework.common.dto.ResultVO;
import com.jsmsframework.common.util.HttpUtil;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.common.util.StringUtils;
import com.jsmsframework.common.util.file.FileIO;
import com.ucpaas.sms.common.util.security.Des3Utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public enum JsmsHttpSend {
    INSTANCE;
    private Logger logger = LoggerFactory.getLogger(JsmsHttpSend.class);

    public ResultVO invokeHttpToSend(String url, String accessSmsJson,Class responseClass) {
        return invokeHttpToSend(url, accessSmsJson, ContentType.APPLICATION_JSON, responseClass);
    }

    public ResultVO invokeHttpToSend(String url, String accessSmsJson,ContentType contentType ,Class responseClass) {
        String resultJson;
        logger.info("-------------即将请求的url---------->{}\r\n-------------即将发送的短信,请求包体---------->{}", url, accessSmsJson);
        Response response = httpPost(url, accessSmsJson,contentType);
        logger.debug("短信响应内容 --> resultJson = {}", response.toString());
        if (!response.connected) {
            logger.error("【短信发送失败】发送失败, 请求SMSP无响应&& 链接异常, JsmsAccessSms ---> {}", accessSmsJson);
        } else {
            try {
                Object o = JsonUtil.toObject(response.getResponse(), responseClass);
                return ResultVO.successDefault(o);
            } catch (IOException e) {
                logger.error("【短信发送失败】发送失败, JsmsAccessSms ---> {}, 请求SMSP响应异常 --> {}", accessSmsJson, response.getResponse());
                return ResultVO.successDefault(response.getResponse());
            }
        }
        return ResultVO.failure();
    }

    private Response httpPost(String url, String content,ContentType contentType) {
        boolean needSSL;
        if (url.startsWith("https")) {
            logger.debug("使用https协议请求短信接口");
            // 线上
            needSSL = true;
        } else {
            logger.debug("使用http协议请求短信接口");
            needSSL = false;
        }
        // 创建HttpPost
        HttpClient httpClient = HttpUtil.getHttpClient(needSSL, com.jsmsframework.common.util.StringUtils.getHostFromURL(url));

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", contentType+ ";charset=utf-8");
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(2000)
                    .setConnectTimeout(2000)
                    .setConnectionRequestTimeout(2000)
                    .build();
            httpPost.setConfig(requestConfig);
            BasicHttpEntity requestBody = new BasicHttpEntity();
            requestBody.setContent(new ByteArrayInputStream(content.getBytes("utf-8")));
            requestBody.setContentLength(content.getBytes("utf-8").length);
            httpPost.setEntity(requestBody);
            // 执行客户端请求

            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            logger.debug("响应状态码 ----> {}", statusCode);
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null) {
                result = EntityUtils.toString(entity, "utf-8");
                EntityUtils.consume(entity);
            }
            /**
             * 不管状态码, 除了网络IO异常都算成功
             */
            return this.new Response(true, result);
        } catch (IOException e) {
            logger.error("【HTTP请求失败】链接异常: url={}, content={}, \n error:{}", url, content, e);
            return this.new Response(false, e.getMessage());
        } catch (Exception e) {
            logger.error("【HTTP请求失败】: url={}, content={}, \n error:{}", url, content, e);
            return this.new Response(false, e.getMessage());
        }
    }


    private class Response {
        private boolean connected;
        private String response;

        public Response(boolean connected, String response) {
            this.connected = connected;
            this.response = response;
        }

        public boolean isConnected() {
            return connected;
        }

        public void setConnected(boolean connected) {
            this.connected = connected;
        }

        public String getResponse() {
            return response;
        }

        public void setResponse(String response) {
            this.response = response;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Response{");
            sb.append("connected=").append(connected);
            sb.append(", response='").append(response).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    /**
     * @param url
     * @param pathParam
     * @param savePath
     * @return 下载后文件路径
     */
    public String downloadFile(String url, String pathParam, String savePath) {
        String pathParamStr = new StringBuilder("path=").append(pathParam).toString();
        boolean needSSL;
        if (url.startsWith("https")) {
            needSSL = true;
        } else {
            needSSL = false;
        }
        String reuslt = null;

        HttpClient httpClient = HttpUtil.getHttpClient(needSSL, StringUtils.getHostFromURL(url));
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-Type", ContentType.APPLICATION_FORM_URLENCODED + ";charset=utf-8");
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
            httpPost.setConfig(requestConfig);
            BasicHttpEntity requestBody = new BasicHttpEntity();
            requestBody.setContent(new ByteArrayInputStream(pathParamStr.getBytes("utf-8")));
            requestBody.setContentLength(pathParamStr.getBytes("utf-8").length);
            httpPost.setEntity(requestBody);
            // 执行客户端请求
            HttpEntity entity = httpClient.execute(httpPost).getEntity();

            if (entity != null) {
                String fileName = FileIO.getFileName(Des3Utils.decodeDes3(pathParam));
                String filePathName = new StringBuilder(savePath).append(File.separatorChar)
                        .append(fileName).toString();
                logger.debug("从图片服务器下来的文件保存路径 ---> {}",filePathName);
                File storeFile = new File(filePathName);
                if (!storeFile.getParentFile().exists() || !storeFile.getParentFile().isDirectory()){
                    storeFile.getParentFile().mkdirs();
                }
                //得到网络资源并写入文件
                try(FileOutputStream output = new FileOutputStream(storeFile)){
                    InputStream input = entity.getContent();
                    BufferedInputStream bufin = new BufferedInputStream(input,8192 * 1024 * 2);
                    byte b[] = new byte[1024 * 20];
                    int j = 0;
                    while ((j = bufin.read(b)) != -1) {
                        output.write(b, 0, j);
                    }
                    output.flush();
                    bufin.close();
                    reuslt = filePathName;
                    logger.debug("从服务器下载的文件大小 ==> {}, 文件名 ==> {}",storeFile.length(),storeFile.getName());
                } catch (IOException e) {
                    logger.debug("从服务器下载文件失败  --> {}",e);
                }
                EntityUtils.consume(entity);
            }

        } catch (Throwable e) {
            logger.error("【HTTP请求失败】: url={}, content={}, \n error:{}", url, pathParam, e);
        }
        return reuslt;
    }

}


