package com.jsmsframework.jsms.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * SQL执行工具，执行简单的SQL类
 * @author huangwenjie
 * @date 2017-01-28
 */
public class SQLExecuteTools {

    /**
     * 返回一个字符串
     * @param 
     * @return
     */
    public static String returnString(Connection conn, String sql) {
        String str = "";
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                str = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StreamClose.closeRsStmt(rs, pstmt);
        }
        
        return str;
    }
}
