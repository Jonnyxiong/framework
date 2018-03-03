package com.jsmsframework.jsms.parse;

import org.apache.commons.digester3.Digester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jsmsframework.jsms.dto.ModuleDTO;
import com.jsmsframework.jsms.dto.PackageDTO;
import com.jsmsframework.jsms.dto.TableDTO;

/**
 * xml解析
 * 
 * @author huangwenjie
 * @date 2017-01-24
 */
public class ParseConfigXml {

	private static Logger logger = LoggerFactory.getLogger(ParseConfigXml.class);

	private static String CONFIG_XML = "config.xml";

	public static PackageDTO parseConfigXml() {
		PackageDTO packageDTO = null;

		try {
			Digester digester = new Digester();
			digester.addObjectCreate("package", PackageDTO.class);

			digester.addSetProperties("package", "author", "author");
			digester.addSetProperties("package", "date", "date");
			digester.addSetProperties("package", "company", "company");
			digester.addSetProperties("package", "packageName", "packageName");
			digester.addSetProperties("package", "destDir", "destDir");

			digester.addObjectCreate("package/module", ModuleDTO.class);
			digester.addSetProperties("package/module", "moduleName", "moduleName");
			digester.addSetProperties("package/module", "moduleConstants", "moduleConstants");

			digester.addObjectCreate("package/module/table", TableDTO.class);
			digester.addSetProperties("package/module/table", "tableName", "tableName");
			digester.addSetProperties("package/module/table", "pojoName", "pojoName");
			digester.addSetProperties("package/module/table", "menuClass", "menuClass");
			digester.addSetProperties("package/module/table", "className", "className");
			digester.addSetProperties("package/module/table", "menuCode", "menuCode");
			digester.addSetProperties("package/module/table", "classPrefix", "classPrefix");
			digester.addSetProperties("package/module/table", "description", "description");
			digester.addSetProperties("package/module/table", "menuNameZh", "menuNameZh");
			digester.addSetProperties("package/module/table", "dbKey", "dbKey");
			digester.addSetProperties("package/module/table", "tablePrefix", "tablePrefix");
			digester.addSetProperties("package/module/table", "overwrite", "overwrite");

			digester.addBeanPropertySetter("package/page/table");

			digester.addSetNext("package/module", "addModuleDTO");

			digester.addSetNext("package/module/table", "addTableDTO");

			packageDTO = (PackageDTO) digester
					.parse(ParseConfigXml.class.getClassLoader().getResourceAsStream(CONFIG_XML));

		} catch (Exception e) {
			logger.error("请检查config.xml配置文件", e);
		}

		return packageDTO;
	}

}
