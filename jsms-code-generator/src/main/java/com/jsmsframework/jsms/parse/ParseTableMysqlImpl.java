package com.jsmsframework.jsms.parse;

import com.jsmsframework.jsms.db.SQLExecuteTools;
import com.jsmsframework.jsms.dto.ColumnDTO;
import com.jsmsframework.jsms.dto.ModuleDTO;
import com.jsmsframework.jsms.dto.PackageDTO;
import com.jsmsframework.jsms.dto.TableDTO;
import com.jsmsframework.util.NameUtil;
import com.jsmsframework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Iterator;

/**
  * 解析数据库中的表
 * 
 * @author huangwenjie
 * @date 2017-01-24
 */
public class ParseTableMysqlImpl implements ParseTable {
    
	private Logger logger = LoggerFactory.getLogger(ParseTableMysqlImpl.class);
	
    public PackageDTO generatePojoByTable(Connection conn, PackageDTO packageDTO) throws Exception {

        try {
            // 获取数据库的元信息
            DatabaseMetaData dbMetaData = conn.getMetaData();

            Iterator<String> packageIterator = packageDTO.moduleMap.keySet().iterator();

            // 遍历模块
            while (packageIterator.hasNext()) {

                ModuleDTO moduleDTO = packageDTO.moduleMap.get(packageIterator.next());

                if (moduleDTO == null) {
                    continue;
                }

                Iterator<String> tableIterator = moduleDTO.tableMap.keySet().iterator();

                // 遍历表
                while (tableIterator.hasNext()) {
                    TableDTO tableDTO = moduleDTO.tableMap.get(tableIterator.next());
                    
                    if (tableDTO == null || StringUtil.isEmpty(tableDTO.getTableName())) {
                        continue;
                    }

                    boolean isFoundKey = false;
                    // 在Oracle中表名大小写敏感，先转成大写
//                    String tableName = StringUtil.toUpper(tableDTO.getTableName());
                    String tableName = tableDTO.getTableName();
                    
                    //设置类名，并且会设置 实例名
                    String className = NameUtil.convertToJavaNameByTableName(tableName);
                    if(StringUtil.isNotEmpty(tableDTO.getTablePrefix())){ //去掉表名前缀
                    	className = NameUtil.convertToJavaNameByTableName(tableName.replaceFirst(tableDTO.getTablePrefix(), ""));
                    }
                    tableDTO.setClassName(className);
                    
                    ResultSet rs = dbMetaData.getTables(null, null, tableName,new String[] { "TABLE" });
                    boolean isTable = false;
                    if (rs.next()) {
                        isTable = true;
                    }
                    
                    // 判断是表还是视图
                    if (!isTable) {
                        rs = dbMetaData.getTables(null, null, tableName,new String[] { "VIEW" });
                    }
                      
                    rs = conn.prepareStatement(
                            "SELECT * FROM " + tableName + " WHERE 1=2")
                            .executeQuery();

                    ResultSetMetaData rsMetaData = rs.getMetaData();

                    for (int i = 0; i < rsMetaData.getColumnCount(); i++) {
                        ColumnDTO columnDTO = new ColumnDTO();
                        columnDTO.setPropertyName(NameUtil.convertToPropertiesNameByColumnName(rsMetaData.getColumnName(i + 1)));
                        columnDTO.setColumnName(rsMetaData.getColumnName(i + 1));
                        
                        
                        
                        int columnType = rsMetaData.getColumnType(i+1);
                        String columnClassName = JavaTypeResolver.getColumnClassName(columnType);
                        String columnTypeName = JavaTypeResolver.getColumnTypeName(columnType);
                        columnDTO.setColumnType(columnClassName);
                        
                        String javaType = columnDTO.getColumnType().substring(columnDTO.getColumnType().lastIndexOf(".") + 1);

                        if(columnDTO.getColumnName().equals(tableDTO.getDbKey())){ //设置主键类型
                        	tableDTO.setObjectKeyType(javaType);
                        	isFoundKey = true;
                        }
                        
                        columnDTO.setJavaType(javaType);
                        columnDTO.setFullJavaType(columnClassName);
                        columnDTO.setJdbcType(columnTypeName);
                        
                        columnDTO.setColumnSize(rsMetaData.getColumnDisplaySize(i + 1));
                        columnDTO.setIsNull(rsMetaData.isNullable(i + 1) == 0 ? false : true);
                        
                        String comment = SQLExecuteTools
                                .returnString(
                                        conn,
                                        "SELECT COLUMN_COMMENT FROM information_schema.COLUMNS WHERE "
                                        + "TABLE_NAME='"
                                        + tableName
                                        + "' AND COLUMN_NAME='"
                                        + StringUtil.toUpper(columnDTO.getColumnName()) + "'");
                        if(StringUtil.isNotEmpty(comment)) {
                            comment = comment.replace("\n", "").replace("\r", "").replace("\n\r", "");
                        }
                        columnDTO.setComment(comment);
                        
                        tableDTO.columnMap.put("column" + i, columnDTO);
                        
                        if (columnClassName.indexOf("java.lang") == -1) {
                            tableDTO.addTypeMap(javaType, columnClassName);
                        }
                    }
                    
                    if(!isFoundKey){
                    	logger.error(tableDTO.getTableName()+"表未发现主键"+tableDTO.getDbKey());
                    	throw new Exception(tableDTO.getTableName()+"表未发现主键"+tableDTO.getDbKey());
                    }
                    if(rsMetaData.getColumnCount()==0){
                    	logger.error(tableDTO.getTableName()+"表不能存在，请检查数据库是否配对");
                    	throw new Exception(tableDTO.getTableName()+"表不能存在，请检查数据库是否配对");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return packageDTO;
    }
}
