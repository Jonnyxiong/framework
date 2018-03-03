package com.jsmsframework.jsms;

import com.jsmsframework.jsms.dto.ModuleDTO;
import com.jsmsframework.jsms.dto.PackageDTO;
import com.jsmsframework.jsms.dto.TableDTO;
import com.jsmsframework.jsms.exception.FileExistsException;
import com.jsmsframework.util.FreemarkerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 */
public class SpringMVCGenerate implements Generate {

    private static Logger logger = LoggerFactory.getLogger(SpringMVCGenerate.class);

    /**
     * @param
     * @return
     * @title 根据模板来生成文件
     */
    public void generate(PackageDTO packageDTO, String template) {

        Map<String, ModuleDTO> moduleMap = packageDTO.moduleMap;

        Set<String> moduleSet = moduleMap.keySet();

        int moudleCount = 0;

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("packageName", packageDTO.getPackageName());
        dataMap.put("author", packageDTO.getAuthor());

        List<String> columnList = new ArrayList<String>();
        columnList.add("fcu");
        columnList.add("fcd");
        columnList.add("lmu");
        columnList.add("lmd");

        dataMap.put("filterColumns", columnList);
        dataMap.put("now", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        for (String moduleKey : moduleSet) {
            dataMap.put("moduleKey", moduleKey);
            ModuleDTO moduleDTO = moduleMap.get(moduleKey);
            dataMap.put("moduleName", moduleDTO.getModuleName());

            moudleCount++;

            System.out.println("Generation of No." + moudleCount + " module");

            // 生成目录地址
            // String modulePath = packageDTO.getDestDir() + "/" +
            // moduleDTO.getModuleName().replace(".", "/");
            String destDir = packageDTO.getDestDir();
            String packagePath = packageDTO.getPackageName().replace(".", "/");
            String mavenPackagePath = "/jsms-" + moduleDTO.getModuleName().replace(".", "/");
            destDir = destDir + mavenPackagePath + "/src/main/java";  //F:/IdeaProjects/jsmsframework /jsms-user /src/main/java

            String modulePath = moduleDTO.getModuleName().replace(".", "/");

            Iterator<Map.Entry<String, TableDTO>> tableIterator = moduleDTO.tableMap.entrySet().iterator();
            while (tableIterator.hasNext()) {
                Map.Entry<String, TableDTO> tableEntry = tableIterator.next();
                TableDTO table = tableEntry.getValue();
                boolean overwrite = table.getOverwrite() == null ? false : table.getOverwrite().equals("true");

                dataMap.put("table", table);

                // 生成文件
                try {
                    // Mapper.xml文件
                    String mapperxml = destDir + "/" + packagePath
                            + "/" + modulePath + "/mapper/Jsms" + table.getClassName() + "Mapper.xml";
                    if (!new File(mapperxml).exists() || overwrite) {
                        FreemarkerUtil.generateFile("/template/", "mapper.xml", mapperxml, dataMap);
                    }else {
                        logger.error(mapperxml + "文件已存在，如果想覆盖，请设置overwrite='true'");
                    }

                    // entity类
                    String beanjava = destDir + "/" + packagePath + "/"
                            + modulePath + "/entity/Jsms" + table.getClassName() + ".java";
                    if (!new File(beanjava).exists() || overwrite) {
                        FreemarkerUtil.generateFile("/template/", "bean.ftl", beanjava, dataMap);
                    }else{
                        logger.error(beanjava + "文件已存在，如果想覆盖，请设置overwrite='true'");
                    }

                    // Mapper类
                    String mapperjava = destDir + "/" + packagePath
                            + "/" + modulePath + "/mapper/Jsms" + table.getClassName() + "Mapper.java";
                    if (!new File(mapperjava).exists() || overwrite) {
                        FreemarkerUtil.generateFile("/template/", "mapper.ftl", mapperjava, dataMap);
                    }else {
                        logger.error(mapperjava + "文件已存在，如果想覆盖，请设置overwrite='true'");
                    }


                    // Service接口
                    String servicejava = destDir + "/" + packagePath
                            + "/" + modulePath + "/service/Jsms" + table.getClassName() + "Service.java";
                    if (!new File(servicejava).exists() || overwrite) {
                        FreemarkerUtil.generateFile("/template/", "Service.ftl", servicejava, dataMap);
                    }else{
                        logger.error(servicejava + "文件已存在，如果想覆盖，请设置overwrite='true'");
                    }

                    // Service实现类
                    String serviceimpljava = destDir + "/" + packagePath
                            + "/" + modulePath + "/service/Jsms" + table.getClassName() + "ServiceImpl.java";
                    if (!new File(serviceimpljava).exists() || overwrite) {
                        FreemarkerUtil.generateFile("/template/", "ServiceImpl.ftl", serviceimpljava, dataMap);
                    }else{
                        logger.error(serviceimpljava + "文件已存在，如果想覆盖，请设置overwrite='true'");
                    }

                    // Exception实现类
                    String exceptionjava = destDir + "/" + packagePath
                            + "/" + modulePath + "/exception/Jsms" + table.getClassName() + "Exception.java";
                    if (!new File(exceptionjava).exists() || overwrite) {
                        FreemarkerUtil.generateFile("/template/", "exception.ftl", exceptionjava, dataMap);
                    }else{
                        logger.error(exceptionjava + "文件已存在，如果想覆盖，请设置overwrite='true'");
                    }
                }catch (FileExistsException e){
                    logger.error(e.getMessage());
                    continue;
                }
            }

        }
    }
}
