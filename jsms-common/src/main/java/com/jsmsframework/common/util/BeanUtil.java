package com.jsmsframework.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * @description Bean工具类
 * @author huangwenjie
 * @date 2017-08-03
 */
public class BeanUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanUtil.class);

	/**
	 * 修改Spring的BeanUtils增加日志功能，且只从source复制到（覆盖到）target上,一般用于编辑功能
	 * @param source
	 * @param target
	 * @throws BeansException
	 */
	public static void copyProperties(Object source, Object target) throws BeansException {
		copyProperties(source, target, (String[]) null);
	}

	/**
	 * 修改Spring的BeanUtils增加日志功能，且只从source复制到（覆盖到）target上,一般用于编辑功能
	 * 
	 * @param source
	 * @param target
	 * @param ignoreProperties
	 * @throws BeansException
	 */
	private static void copyProperties(Object source, Object target, String... ignoreProperties) throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for (PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0],
							readMethod.getReturnType())) {
						try {
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object newValue = readMethod.invoke(source);
							if(newValue==null)
								continue;
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							Object oldValue = targetPd.getReadMethod().invoke(target);
							//可以加日志在此
							writeMethod.invoke(target, newValue);
						} catch (Throwable ex) {
							throw new FatalBeanException(
									"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}

	/**
	 * Retrieve the JavaBeans {@code PropertyDescriptor}s of a given class.
	 * 
	 * @param clazz
	 *            the Class to retrieve the PropertyDescriptors for
	 * @return an array of {@code PropertyDescriptors} for the given class
	 * @throws BeansException
	 *             if PropertyDescriptor look fails
	 */
	public static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws BeansException {
		CachedIntrospectionResults cr = CachedIntrospectionResults.forClass(clazz);
		return cr.getPropertyDescriptors();
	}

	/**
	 * Retrieve the JavaBeans {@code PropertyDescriptors} for the given
	 * property.
	 * 
	 * @param clazz
	 *            the Class to retrieve the PropertyDescriptor for
	 * @param propertyName
	 *            the name of the property
	 * @return the corresponding PropertyDescriptor, or {@code null} if none
	 * @throws BeansException
	 *             if PropertyDescriptor lookup fails
	 */
	public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String propertyName) throws BeansException {

		CachedIntrospectionResults cr = CachedIntrospectionResults.forClass(clazz);
		return cr.getPropertyDescriptor(propertyName);
	}


	/**
	 * javabean对象转换为map
	 * @param bean
	 * @param <T>
	 * @return
	 */
	public static <T> Map<String, Object> beanToMap(T bean, boolean ignoreNullProperty) {

		Map<String, Object> map = new HashMap<>();
		if (bean != null) {
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				if (ignoreNullProperty) {
					if (beanMap.get(key) != null){
						map.put(key + "", beanMap.get(key));
					}
				}else{
					map.put(key + "", beanMap.get(key));
				}

			}
		}
		return map;
	}

	/**
	 * listjavabean对象转换为maplist
	 * @param </>beanlist
	 * @param <T>
	 * @return
	 */
	public static <T> List<Map<String, Object>> ListbeanToMap(List<T> beanlist, boolean ignoreNullProperty) {

		List<Map<String, Object>> maplist = new ArrayList<>();
		for (T t : beanlist) {
			Map<String, Object> map=beanToMap(t,ignoreNullProperty);
			maplist.add(map);
		}


		return maplist;
	}
	/**
	 * javabean对象转换为map
	 * @param bean
	 * @return
	 */
	public static <T> Map<String, Object> beanToMap2(T bean, boolean ignoreNullProperty) {

		if(bean == null){
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (!key.equals("class")) {
					Method getter = property.getReadMethod();
					Object value = getter.invoke(bean);
					if(value == null && ignoreNullProperty)
						continue;
					else
						map.put(key, value);
				}

			}
		} catch (Exception e) {
			LOGGER.error("beanToMap2 Error " + e);
		}

		return map;

	}

	/**
	 * 将map转换为javabean对象
	 * @param map
	 * @param bean
	 */
	public static <T> void mapToBean(Map<String, Object> map, T bean) {

		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (!key.equals("class") && map.containsKey(key)) {
					Class propRowType = property.getPropertyType();
					Object value = map.get(key);
					if(!propRowType.isInstance(value)) {
						value = ConvertUtils.convert(value, propRowType);
					}
					Method setter = property.getWriteMethod();
					setter.invoke(bean, value);
				}

			}

		} catch (Exception e) {
			LOGGER.error("mapToBean Error " + e);
		}

	}

}
