package com.jsmsframework.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author huangwenjie
 * @date 2017-01-24
 */
public final class Constants {

	public final static String TABLE_COLUMN_SPLIT = "_"; // 表字段分割符

	/**
	 * 增删改查的后缀
	 */
	public final static String ADD_CONTROLLER = "AddController";
	public final static String DEL_CONTROLLER = "DelController";
	public final static String EDIT_CONTROLLER = "EditController";
	public final static String LIST_CONTROLLER = "ListController";
	public final static String VIEW_CONTROLLER = "ViewController";

	public final static String SERVICE = "Service";
	public final static String SERVICE_IMPL = "ServiceImpl";
	public final static String DAO = "Mapper";

	public final static int MAXCOLUMNSIZE = 11;// 当字段数超过这个值时，每行显示两个

	/**
	 * 无需生成表单的属性字段名
	 */
	public static Set<String> propertySet = new HashSet<String>();
	public static Set<String> beanPropertySet = new HashSet<String>();

	static {
		propertySet.add("FCD");
		propertySet.add("FCU");
		propertySet.add("LMU");
		propertySet.add("LMD");

		beanPropertySet.add("FCD");
		beanPropertySet.add("FCU");
		beanPropertySet.add("LMU");
		beanPropertySet.add("LMD");
	}

}
