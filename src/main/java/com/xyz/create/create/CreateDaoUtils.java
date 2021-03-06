package com.xyz.create.create;

import com.xyz.create.Job;
import com.xyz.create.vo.Item;
import com.xyz.create.vo.KeyValue;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateDaoUtils {
    public void create(List<Item> items, String pojoName,
                       String basePath, String pojoPath,
                       String daoPath,String ftlPath) {

        String DAO_PACKAGE = daoPath;
        String POJO_PACKAGE = pojoPath;
        String CLASS_NAME = pojoName;

        Configuration configuration = new Configuration(Configuration.getVersion());
        try {
            String base = System.getProperties().getProperty("user.dir");
            configuration.setDirectoryForTemplateLoading(new File(base));
            configuration.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
            //获得模板，传进来的ftl模板的名字
            Template template = configuration.getTemplate("model-dao.ftl");
            Map map = new HashMap();
            //这里的name的对应模板内的${name}
            map.put("DAO_PACKAGE", daoPath);
            map.put("POJO_PACKAGE", pojoPath);
            map.put("CLASS_NAME", pojoName);
            FileOutputStream fileOutputStream = new FileOutputStream(basePath + File.separator + "dao" + File.separator + pojoName + "Dao.java");
            OutputStreamWriter outputStream = new OutputStreamWriter(fileOutputStream,"utf-8");
            template.process(map, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
