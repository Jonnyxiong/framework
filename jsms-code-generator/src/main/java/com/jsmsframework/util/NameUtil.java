package com.jsmsframework.util;

/**
 * 名称处理工具
 * 
 * @author huangwenjie
 * @date 2017-01-24
 */
public final class NameUtil {

	/**
	 * 处理表名，把表名变成类名，去掉 _ 把下划线后字母变成大写 如: menu --> Menu Menu --> Menu MeNu -->
	 * MeNu sys_user --> SysUser SystEmUser --> SystEmUser
	 * 
	 * 注意: tb_user 这种情况暂未考虑，依然会生成TbUser
	 * 
	 * @param
	 * @return
	 */
	public static String convertToJavaNameByTableName(String tableName) {
		if (tableName == null) {
			return null;
		}
		if ("".equals(tableName.trim())) {
			return "";
		}

		StringBuilder sb = new StringBuilder(tableName.trim());

		sb.replace(0, 1, tableName.substring(0, 1).toUpperCase());

		while (sb.indexOf(Constants.TABLE_COLUMN_SPLIT) != -1) {
			int index = sb.indexOf(Constants.TABLE_COLUMN_SPLIT);
			sb.replace(index + 1, index + 2, sb.substring(index + 1, index + 2).toUpperCase());
			sb.replace(index, index + 1, "");
		}

		return sb.toString();
	}

	/**
	 * 处理字段名，把字段名变成成员变量，去掉 _ 把下划线后，首字母变成小写
	 * 
	 * @param
	 * @return
	 */
	public static String convertToPropertiesNameByColumnName(String columnName) {
		String tableName = convertToJavaNameByTableName(columnName);

		StringBuilder sb = new StringBuilder(tableName);
		sb.replace(0, 1, columnName.substring(0, 1).toLowerCase());
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(convertToJavaNameByTableName("menu"));
		System.out.println(convertToJavaNameByTableName("Menu"));
		System.out.println(convertToJavaNameByTableName("MeNu"));
		System.out.println(convertToJavaNameByTableName("system_user"));
		System.out.println(convertToJavaNameByTableName("SystEmUser"));
		System.out.println(convertToJavaNameByTableName("tb_user")); // 这种情况暂未考虑

		System.out.println(convertToPropertiesNameByColumnName("name"));
		System.out.println(convertToPropertiesNameByColumnName("creatTime"));
		System.out.println(convertToPropertiesNameByColumnName("creat_time"));

	}

	/**
	 * 根据全限定名获取类名
	 * 
	 * @param
	 * @return
	 */
	public static String getShortClassNameByFullName(String qualifiedName) {
		String str = StringUtil.trim(qualifiedName);

		return str.substring(str.lastIndexOf("\\.") + 1);
	}

	/**
	 * 根据全限定名获取类名
	 * 
	 * @param
	 * @return
	 */
	public static String getPackageByFullName(Object qualifiedName) {
		String str = StringUtil.trim(qualifiedName);

		return str.substring(0, str.lastIndexOf("\\."));
	}

	/**
	 * 首字母变大写
	 * 
	 * @param
	 * @return
	 */
	public static String capital(String name) {
		String str = StringUtil.trim(name);

		if (str.length() > 0) {
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}

}
