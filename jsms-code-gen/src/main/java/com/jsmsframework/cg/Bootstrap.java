package com.jsmsframework.cg;

import com.jsmsframework.factory.context.CodegenContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 启动程序，新版的生成代码工具
 * 支持xml校验
 * 动态检测字段属性，避免mybatis的一些错误
 *
 * @author huangwenjie
 * @since 2018-01-22
 *
 */
public class Bootstrap {

    private static Logger logger = LoggerFactory.getLogger(com.jsmsframework.Bootstrap.class);

    public static void main(String[] args) {

        logger.info("启动代码生成工具");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");

        CodegenContext codegenContext = new CodegenContext();
        codegenContext.refresh();
        codegenContext.gen();

        logger.info("结束");
    }
}
