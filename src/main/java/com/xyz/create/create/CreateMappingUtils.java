package com.xyz.create.create;

import com.xyz.create.vo.Item;
import com.xyz.create.vo.KeyValue;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateMappingUtils {
	public void create(List<Item> items, String pojoName, String realTableName,
					   String basePath,String pojoPath, String daoPath,String ftlPath) {

		String DAO_PACKAGE = daoPath;
		String POJO_PACKAGE = pojoPath;
		String CLASS_NAME = pojoName;

		Configuration configuration = new Configuration(Configuration.getVersion());
		try {
			String base = System.getProperties().getProperty("user.dir");
			configuration.setDirectoryForTemplateLoading(new File(base));
			//获得模板，传进来的ftl模板的名字
			Template template = configuration.getTemplate("model-mapper.ftl");
			Map map = new HashMap();
			//这里的name的对应模板内的${name}
			map.put("DAO_PACKAGE", daoPath);
			map.put("POJO_PACKAGE", pojoPath);
			map.put("CLASS_NAME", pojoName);
			map.put("FIELDS", items);
			map.put("TABLE_NAME", realTableName);
			FileOutputStream fileOutputStream = new FileOutputStream(basePath + File.separator + "mapping" + File.separator + pojoName + "Dao.xml");
			OutputStreamWriter outputStream = new OutputStreamWriter(fileOutputStream, "utf-8");
			template.process(map, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
