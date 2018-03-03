package com.jsmsframework.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Properties;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * Freemarker工具类
 * @author huangwenjie
 * @date 2017-01-28
 */
public final class FreemarkerUtil {

    public static void generateFile(String templatePath, String templateFile,String destFile, Map dataMap) {
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(new ClassTemplateLoader(FreemarkerUtil.class,templatePath));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        
        // 加载并设置freemarker.properties
        Properties properties = new Properties();
        
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("freemarker.properties"));
            cfg.setSettings(properties);
            
            /* 在整个应用的生命周期中，这个工作你可以执行多次 */
            /* 获取或创建模板 */
            Template temp = cfg.getTemplate(templateFile);
            
            /* 创建数据模型 */
            /* 将模板和数据模型合并 */
            File file = new File(destFile);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            
            temp.process(dataMap, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
