package com.jsmsframework.util;

/**
 * 字符串工具
 * 
 * @author huangwenjie
 * @date 2017-01-24
 */
public final class StringUtil {

	/**
	 * 去除前后空格 ，null时返回""字符串
	 * 
	 * @param
	 * @return
	 */
	public static String trim(Object object) {
		if (object == null) {
			return "";
		} else {
			return object.toString().trim();
		}
	}

	/**
	 * 转化成大写
	 * 
	 * @param
	 * @return
	 */
	public static String toUpper(Object object) {
		if (object == null) {
			return null;
		} else {
			return trim(object).toUpperCase();
		}
	}

	/**
	 * 判断指定对象是否为空，如果为空则返回false 否则返回true
	 * 
	 * @param
	 * @return
	 */
	public static boolean isEmpty(Object object) {
		if (object == null || "".equals(object.toString().trim())) {
			return true;
		}

		return false;
	}

	/**
	 * 判断两个字符串对象值是否相等
	 * 
	 * @param
	 * @return
	 */
	public static boolean isEquals(Object object1, Object object2) {
		String str1 = toUpper(object1);
		String str2 = toUpper(object2);

		if (str1.equals(str2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断指定对象是否不为空，isEmpty的值取反
	 * 
	 * @param
	 * @return
	 */
	public static boolean isNotEmpty(Object object) {
		return !isEmpty(object);
	}

}
