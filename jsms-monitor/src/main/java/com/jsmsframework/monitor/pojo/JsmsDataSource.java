package com.jsmsframework.monitor.pojo;

import javax.sql.DataSource;

/**
 * 数据库基本描述
 */
public class JsmsDataSource {
    /**
     * 应用使用 数据库连接
     */
    private DataSource dataSource;
    /**
     * 测试结果
     */
    private DBConnection dbConnection;

    public JsmsDataSource(DataSource dataSource, String desc, boolean isNeedCheck) {
        this.dataSource = dataSource;
        this.dbConnection = new DBConnection();
        this.dbConnection.setDesc(desc);
        this.dbConnection.setNeedCheck(isNeedCheck);
    }

    public JsmsDataSource(DataSource dataSource, String desc) {
        this.dataSource = dataSource;
        this.dbConnection = new DBConnection();
        this.dbConnection.setDesc(desc);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DBConnection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public static class DBConnection {
        /**
         * 是否需要检查
         */
        private boolean isNeedCheck = true;
        /**
         * 数据是否异常, 不需要检测或同时满足以下三个条件为: 无异常 false
         * (1)需要检测
         * (2)执行测试
         * (3)能连接
         */
        private boolean dbException;
        /**
         * 是否进行过连接测试
         */
        private boolean isTestEd = false;
        /**
         * 是否能连接数据库
         */
        private boolean isConnectDB = false;
        /**
         * 数据库类型 mysql oracle ...
         */
        private String dbType;
        /**
         * 数据服务器IP
         */
//        private String jdbcUrl; // todo 可能会暴露关键信息, 先注释
        /**
         * 数据库名称, 实际的库名,如: smsp_message
         */
//        private String dbName; // todo 可能会暴露关键信息, 先注释
        /**
         * 数据库描述, 如: 业务库, Access流水库,主库...
         */
        private String desc;
        /**
         * 记录信息, 比如错误信息
         */
        private String msg;

        public boolean isNeedCheck() {
            return isNeedCheck;
        }


        public void setNeedCheck(boolean needCheck) {
            isNeedCheck = needCheck;
        }

        public boolean isDbException() {
            return dbException;
        }

        public void setDbException(boolean dbException) {
            this.dbException = dbException;
        }

        public String getDbType() {
            return dbType;
        }

        public void setDbType(String dbType) {
            this.dbType = dbType;
        }

        public boolean isTestEd() {
            return isTestEd;
        }

        public void setTestEd(boolean testEd) {
            isTestEd = testEd;
        }

        public boolean isConnectDB() {
            return isConnectDB;
        }

        public void setConnectDB(boolean connectDB) {
            isConnectDB = connectDB;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
