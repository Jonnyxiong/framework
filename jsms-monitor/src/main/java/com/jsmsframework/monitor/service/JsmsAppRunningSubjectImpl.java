package com.jsmsframework.monitor.service;

import com.jsmsframework.monitor.pojo.JsmsAppServerInfo;
import com.jsmsframework.monitor.pojo.JsmsDataSource;
import com.jsmsframework.monitor.util.JsmsMonitorToken;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsmsAppRunningSubjectImpl implements JsmsAppRunningSubject {
    private static final int TEST_CONNECTION_RETURN_NUM = 1;
    private static final String TEST_CONNECTION_SQL = "SELECT " + TEST_CONNECTION_RETURN_NUM;
    private static final String DB_TYPE_REGEX = "(?<=\\:)[^:]+";
    private static final String JDBC_URL_REGEX = "(?<=\\://)[^/]+";
    private static final String DB_NAME_REGEX = "(?<=/)[^/]+(?=\\?)";
    /**
     * 被监控应用的数据源信息, 在被监控应用端配置, 如: smsa-operation 的数据源, smsp-schedule 的数据源
     */
    private List<JsmsDataSource> dataSourceList;

    public void setDataSourceList(List<JsmsDataSource> dataSourceList) {
        this.dataSourceList = dataSourceList;
    }

    private static final Logger logger = LoggerFactory.getLogger(JsmsAppRunningSubjectImpl.class);

    @Override
    public JsmsAppServerInfo isRunning(String token) {
        if(this.dataSourceList == null){
            throw new IllegalArgumentException("dataSourceList 没有初始化或赋值");
        }
        return isRunning(token, this.dataSourceList);
    }

    @Override
    public JsmsAppServerInfo isRunning(String token, List<JsmsDataSource> dataSourceList) {

        DateTime now = DateTime.now();
        if (StringUtils.isBlank(token)) {
            return new JsmsAppServerInfo(true,true, "无效token");
        }
        boolean compareResult = compareToken(token, now, 3);
        if (compareResult) {
            return checkDataSourceList(dataSourceList);
        } else {
            return new JsmsAppServerInfo(true,true, "无效token");
        }
    }

    /**
     * token比较工具, 根据时间比较, 下次比较向前推一秒
     * @param token
     * @param now
     * @param times 比较次数
     * @return
     */
    private boolean compareToken(String token, DateTime now, int times) {
        --times;
        String generateToken = JsmsMonitorToken.generateToken(now);
        if (generateToken.equals(token)) {
            return true;
        } else {
            if (times > 0) {
                compareToken(token, now.minusSeconds(1), times);
            }
        }
        return false;
    }

    private JsmsAppServerInfo checkDataSourceList(List<JsmsDataSource> dataSourceList) {
        if (dataSourceList == null || dataSourceList.isEmpty()) {
            return new JsmsAppServerInfo(true, false,"没有配置需要测试数据源");
        }
        List<JsmsDataSource.DBConnection> dbConnectionList = new ArrayList<>();
        JsmsAppServerInfo jsmsAppServerInfo = new JsmsAppServerInfo(true, "应用运行正常", dbConnectionList);
        for (JsmsDataSource jsmsDataSource : dataSourceList) {
            JsmsDataSource.DBConnection db = checkDataSource(jsmsDataSource);
            if( !jsmsAppServerInfo.isAppException() && // 已经有异常则不需要重复判断
                    db.isDbException()){
//                    db.isNeedCheck() && (!db.isTestEd() || !db.isConnectDB())){
                // (1)需要检测db (2) db未测试 或 db不能连接
                jsmsAppServerInfo.setAppException(true);
            }
            dbConnectionList.add(db);
        }
        return jsmsAppServerInfo;
    }

    private JsmsDataSource.DBConnection checkDataSource(JsmsDataSource jsmsDataSource) {
        JsmsDataSource.DBConnection dbConnection = jsmsDataSource.getDbConnection();
        /**
         * 判断是否需要检查数据库
         */
        if (!dbConnection.isNeedCheck()) { // 不需要连接数据库测试
            return dbConnection;
        }
        /**
         * 进行连接测试
         */
        dbConnection.setTestEd(true);
        DataSource dataSource = jsmsDataSource.getDataSource();
        if (dataSource == null) {
            dbConnection.setConnectDB(false);
            dbConnection.setDbException(true);
            return dbConnection;
        }
        int returnNum = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            if (connection != null) {
                preparedStatement = connection.prepareStatement(TEST_CONNECTION_SQL);
                resultSet = preparedStatement.executeQuery();
                returnNum = -TEST_CONNECTION_RETURN_NUM;
                while (returnNum == TEST_CONNECTION_RETURN_NUM || returnNum == 0) {
                    --returnNum;
                }
                if (resultSet.next()) {
                    returnNum = resultSet.getInt(1);
                }
                resultSet.close();
                preparedStatement.close();
                connection.close();
            }
        } catch (SQLException e) {
            dbConnection.setMsg(e.getMessage());
            logger.error("关闭连接异常 ---> {}", e);
        } catch (Exception e1) {
            dbConnection.setMsg(e1.getMessage());
            logger.error("连接异常 ---> {}", e1);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                logger.error("连接异常 ---> {}", e.getMessage());
                logger.error("连接异常 ---> {}", e);
            }
        }
        if (returnNum == TEST_CONNECTION_RETURN_NUM) {
            dbConnection.setConnectDB(true);
            dbConnection.setDbException(false);
        }else{
            dbConnection.setConnectDB(false);
            dbConnection.setDbException(true);
        }
        getJDBCInfo(jsmsDataSource);
        return dbConnection;
    }

    private JsmsDataSource getJDBCInfo(JsmsDataSource jsmsDataSource) {
        DataSource dataSource = jsmsDataSource.getDataSource();
        JsmsDataSource.DBConnection dbConnection = jsmsDataSource.getDbConnection();
        String jdbcUrl = null;
        Method[] methods = dataSource.getClass().getMethods();
        try {
            for (Method method : methods) {
                String name = method.getName();
                if (StringUtils.startsWith(name, "get") && StringUtils.containsIgnoreCase(name, "Url")) {
                    jdbcUrl = (String) method.invoke(dataSource);
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("反射获取jdbcUrl时错误 ---> {}", e);
        }
        if (StringUtils.isBlank(jdbcUrl)) {
            return jsmsDataSource;
        }
        Pattern pattern = Pattern.compile(DB_TYPE_REGEX);
        Matcher matcher = pattern.matcher(jdbcUrl);
        if (matcher.find()) {
            dbConnection.setDbType(matcher.group());
        }
        /*
        // todo 可能会暴露关键信息, 先注释
        pattern =  Pattern.compile(JDBC_URL_REGEX);
        matcher = pattern.matcher(jdbcUrl);
        if(matcher.find()){
            checkConnection.setJdbcUrl(matcher.group());
        }
        pattern =  Pattern.compile(DB_NAME_REGEX);
        matcher = pattern.matcher(jdbcUrl);
        if(matcher.find()){
            checkConnection.setDbName(matcher.group());
        }
        */
        return jsmsDataSource;
    }

}
