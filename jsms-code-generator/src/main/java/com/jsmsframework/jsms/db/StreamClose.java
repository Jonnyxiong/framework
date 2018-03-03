package com.jsmsframework.jsms.db;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 关闭流
 * @author huangwenjie
 * @date 2017-01-28
 */
public final class StreamClose {

    public static void closeRsStmt(ResultSet rs, Statement stmt) {
        try {
            if (rs != null) {
                rs.close();
            }
            
            if (stmt != null) {
                stmt.close();
            }
            
        } catch (Exception e) {

        }
    }
}
