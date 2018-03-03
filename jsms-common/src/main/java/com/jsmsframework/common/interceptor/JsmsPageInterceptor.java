package com.jsmsframework.common.interceptor;


import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.util.Reflections;
import com.jsmsframework.common.util.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

@Component
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
		RowBounds.class, ResultHandler.class }) })
public class JsmsPageInterceptor implements Interceptor {

	private static Logger logger = LoggerFactory.getLogger(JsmsPageInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		logger.debug("进入分页器");

		// 当前环境 MappedStatement，BoundSql，及sql取得
		MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
		Object parameter = invocation.getArgs()[1];
		BoundSql boundSql = mappedStatement.getBoundSql(parameter);
		Object parameterObject = boundSql.getParameterObject();

		// 获取分页参数对象
		JsmsPage<Object> page = null;
		if (parameterObject != null) {
			page = convertParameter(parameterObject);
		}

		// Page对象获取，“信使”到达拦截器！
		// Page page = searchPageWithXpath(boundSql.getParameterObject(), ".",
		// "page", "*/page");

		if (page != null && page.getRows() != -1) {
			if (StringUtils.isBlank(boundSql.getSql())) {
				return null;
			}
			String originalSql = boundSql.getSql().trim();

			// Page对象存在的场合，开始分页处理
//			String countSql = getCountSql(originalSql);
			String countSql;

			if(isSimpleCountSQL(mappedStatement)){
				countSql = getCountSQL(originalSql);
			}else{
				countSql = getCountSql(originalSql);
			}
//			logger.debug("执行countSql:" + countSql);
			int totalRecord = 0;
			try {
				Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
				PreparedStatement countStmt = connection.prepareStatement(countSql);
				BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
				DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject,
                        countBS);
				parameterHandler.setParameters(countStmt);
				ResultSet rs = countStmt.executeQuery();
				totalRecord = 0;
				if (rs.next()) {
                    totalRecord = rs.getInt(1);
                }
				logger.debug("分页查询获取的数据条数--------------->{}", totalRecord);
				rs.close();
				countStmt.close();
				connection.close();
			} catch (Exception e) {
				logger.error("分页Count语句 异常 --->{}  ",e);
				throw e;
			}

			// 分页计算
			page.setTotalRecord(totalRecord);

			// 判断总记录数是否大于限制的查询数
			if(page.getMaxQueryLimit() != -1 && page.getMaxQueryLimit() < totalRecord){
				logger.debug("分页拦截器查询到的记录数 -------> {} , 设置的最大允许的查询记录数 ------> {}, 超出限制不查询结果",totalRecord,page.getMaxQueryLimit());
				// 对原始Sql追加limit
				StringBuffer sb = new StringBuffer();
				sb.append(originalSql);
				if (!StringUtils.isEmpty(page.getOrderByClause())) {
					sb.append(" ORDER BY ").append(page.getOrderByClause());
				}
				sb.append(" limit ").append(0);
				BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, sb.toString());
				MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
				invocation.getArgs()[0] = newMs;
				return invocation.proceed();
			}

			// 对原始Sql追加limit
			int offset = (page.getPage() - 1) * page.getRows();
			StringBuffer sb = new StringBuffer();
			sb.append(originalSql);
			if (!StringUtils.isEmpty(page.getOrderByClause())) {
				sb.append(" ORDER BY ").append(page.getOrderByClause());
			}
			sb.append(" limit ").append(offset).append(",").append(page.getRows());
			BoundSql newBoundSql = copyFromBoundSql(mappedStatement, boundSql, sb.toString());
			MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
			invocation.getArgs()[0] = newMs;
		}
		return invocation.proceed();
	}


	private boolean isSimpleCountSQL(MappedStatement mappedStatement){
		String id = mappedStatement.getId();
		String className = StringUtils.substring(id, 0, id.lastIndexOf("."));
		String methodName = StringUtils.substring(id, id.lastIndexOf(".") + 1);
		Class clazz = null;
		try {
			clazz = Class.forName(className);
			Method[] methods = clazz.getMethods();
			for (Method method : methods) {
				if (methodName.equals(method.getName())){
					SimpleCountSQL simpleCountSQL = method.getAnnotation(SimpleCountSQL.class);
					if (simpleCountSQL != null){
						return true;
					}
				}
			}
		} catch (ClassNotFoundException e) {
			logger.debug("分页Count语句 异常 --->{}",e);
			//e.printStackTrace();
		}
		return false;
	}
	private void checkCountId(MappedStatement mappedStatement,Invocation invocation,Object parameter){
		String id = mappedStatement.getId();
//		com.jsmsframework.audit.mapper.JsmsAutoTemplateMapper.queryPageList
		String className = StringUtils.substring(id, 0, id.lastIndexOf("."));
		String methodName = StringUtils.substring(id, id.lastIndexOf(".") + 1);
		Class clazz = null;
		try {
			clazz = Class.forName(className);
			Method method= clazz.getMethod(methodName);
			CountSQLId countSQLId = method.getAnnotation(CountSQLId.class);
			if (countSQLId != null){
				Executor executor = (Executor) invocation.getTarget();
//				executor.queryCursor("", "", new RowBounds());
//				BoundSql boundSql = mappedStatement.getBoundSql(parameter);
//				executor.query()
//
//				SqlSessionManager
//				mappedStatement
			}
		} catch (ClassNotFoundException e) {
			logger.debug("分页语句 异常 --->{}",e);
		} catch (NoSuchMethodException e) {
			logger.debug("分页语句 异常 --->{}",e);
		}
	}



	protected static JsmsPage<Object> convertParameter(Object parameterObject) {
		try {
			if (parameterObject instanceof JsmsPage) {
				return (JsmsPage<Object>) parameterObject;
			} else {
				return (JsmsPage<Object>) Reflections.getFieldValue(parameterObject, "jsmsPage");
			}
		} catch (Exception e) {
			return null;
		}
	}


	/**
	 * 复制MappedStatement对象
	 */
	private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
		MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource,
				ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.keyProperty(fromStrArry(ms.getKeyProperties()));
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(ms.getResultMaps());
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	private String fromStrArry(String[] arr) {
		if (arr == null || arr.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
			if (i < arr.length - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 复制BoundSql对象
	 */
	private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
				boundSql.getParameterObject());
		for (ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if (boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}
		return newBoundSql;
	}

	/**
	 * 根据原Sql语句获取对应的查询总记录数的Sql语句
	 */
	@Deprecated
	private String getCountSql(String sql) {
		logger.debug("sql -------> {}",sql);
		String newSQL = new StringBuilder("SELECT COUNT(*) FROM (").append(sql).append(") aliasForPage").toString();

		logger.debug("执行countSql ------->:" + newSQL);
//		return "SELECT COUNT(*) FROM (" + sql + ") aliasForPage";
		return newSQL;
	}

	private String getCountSQL(String sql) {
		String temp = sql.toUpperCase();
		int indexUp = sql.indexOf("FROM");
		int index = 0;
		if (indexUp != -1) {
			index = indexUp;
		}
		String newSQL = new StringBuilder("SELECT COUNT(0) ").append(sql.substring(index)).toString();
		logger.debug("执行countSql ------->:" + newSQL);
		return newSQL;
	}


	public class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	public Object plugin(Object arg0) {
		return Plugin.wrap(arg0, this);
	}

	public void setProperties(Properties arg0) {
	}
}
