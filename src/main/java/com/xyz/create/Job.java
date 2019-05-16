package com.xyz.create;

import com.alibaba.fastjson.JSON;
import com.xyz.create.config.Setting;
import com.xyz.create.config.Table2PojoName;
import com.xyz.create.create.CreateDaoUtils;
import com.xyz.create.create.CreateMappingUtils;
import com.xyz.create.create.CreatePojoUtils;
import com.xyz.create.db.IDBHelper;
import com.xyz.create.db.IDBHelper;
import com.xyz.create.db.MysqlDBHelper;
import com.xyz.create.db.OracleDBHelper;
import com.xyz.create.vo.FileUtils;
import com.xyz.create.vo.Item;
import com.xyz.create.vo.KeyValue;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

/*
 {
 "dbUrl":"jdbc:mysql://192.168.32.149/lppz_cross_mall",
 "user":"root",
 "pass":"webchatdev",
 "pojoPath":"",
 "mappingPath":""
 }
 setting.json 
 * */
public class Job {
    public static void main(String[] args) {
        String content = "";
        String settingPath = "";
        boolean debug = false;
        if(debug){
            try{
                settingPath = Job.class.getClassLoader().getResource("").getPath();
                content = IOUtils.toString(Job.class.getResourceAsStream("/setting.json"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            settingPath = System.getProperties().getProperty("user.dir");
            File file = new File(settingPath + File.separator + "setting.json");
            if (!file.exists()) {
                System.out.println("配置文件不存在:" + settingPath + File.separator + "setting.json");
                return;
            } else {
                System.out.println("配置文件路径:" + settingPath + File.separator + "setting.json");
            }
            content = readSetting(file);
            if (content == null || content.isEmpty()) {
                System.out
                        .println("配置文件读取失败或者为空:" + settingPath + File.separator + "setting.json");
            }
        }
        //把模板文件释放到当前目录
        {
            releaseFtlFile("model-dao.ftl");
            releaseFtlFile("model-mapper.ftl");
            releaseFtlFile("model-pojo.ftl");
        }


        System.out.println("content:" + content);

        Setting setting = JSON.parseObject(content, Setting.class);
        // 从路径中中提取数据库名
        init(setting);

        if (false == checkData(setting)) {
            //System.out.println("配置文件中参数都是必填项:" + settingPath + File.separator + "setting.json");
            System.out.println("配置文件格式有问题!");
            return;
        }
        IDBHelper helper = null;
        if (setting.getDbType().toUpperCase().contains("ORACLE")) {
            helper = OracleDBHelper.getHelper(setting.getDbUrl(),
                    setting.getUser(), setting.getPass());
        } else {
            helper = MysqlDBHelper.getHelper(setting.getDbUrl(),
                    setting.getUser(), setting.getPass());
        }

        if (helper == null) {
            System.out.println("连接数据库失败");
            return;
        }

        List<String> tables = new LinkedList<>();
        if(setting.getTable2PojoName() != null && setting.getTable2PojoName().size() > 0){
            for( Table2PojoName tp : setting.getTable2PojoName()){
                if(setting.getDbType().toUpperCase().contains("ORACLE")){
                    tables.add(tp.getTb().toUpperCase());
                }else{
                    tables.add(tp.getTb().toLowerCase());
                }

            }
        }
        if(tables.isEmpty()){
            System.out.println("没有需要处理的数据");
            return;
        }
        CreatePojoUtils createPojoUtils = new CreatePojoUtils();
        CreateDaoUtils createDaoUtils = new CreateDaoUtils();
        CreateMappingUtils createMappingUtils = new CreateMappingUtils();

        FileUtils.createNewFolder(settingPath + File.separator + "pojo");
        FileUtils.createNewFolder(settingPath + File.separator + "dao");
        FileUtils.createNewFolder(settingPath + File.separator + "mapping");

        String path = "";
        URL url = Job.class.getClassLoader().getResource("setting.json");
        if(url == null){
            System.out.println("url获取失败");
            return;
        }else{
            path = url.getPath();
            System.out.println(path);
        }
        System.out.println("path:" + path);
        for (String table : tables) {
            List<Item> filed2ClassType = getColumn(helper, table,
                    setting.getDbName());
            String pojoName = transTableName(table, setting);
            if (StringUtils.isBlank(pojoName)) {
                continue;
            }

            // 生成pojo
            createPojoUtils.create(filed2ClassType, pojoName, settingPath,
                    setting.getPojoPath(),path);
            // 生成dao
            createDaoUtils.create(filed2ClassType, pojoName, settingPath,
                    setting.getPojoPath(), setting.getDaoPath(),path);
            // 生成xml
            createMappingUtils.create(filed2ClassType, pojoName, table, settingPath,
                    setting.getPojoPath(), setting.getDaoPath(),path);
        }
        System.out.println("操作完成!");
    }

    private static void releaseFtlFile(String fileName){
        try{
            String base = System.getProperties().getProperty("user.dir");
            FileOutputStream fileOutputStream = new FileOutputStream(base + File.separator + fileName);
            IOUtils.copy(Job.class.getClassLoader().getResourceAsStream(fileName),fileOutputStream);
            OutputStreamWriter outputStream = new OutputStreamWriter(fileOutputStream, "utf-8");
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private static String getJavaClassType(String t){
        t = t.toLowerCase();
        if(t.contains("int")){
            return "Integer";
        }
        if(t.contains("char")){
            return "String";
        }
        if(t.contains("decimal")){
            return "BigDecimal";
        }
        if(t.contains("float")){
            return "Float";
        }
        if(t.contains("num")|| t.contains("double")){
            return "Double";
        }
        if(t.contains("text")){
            return "String";
        }
        if(t.contains("date")){
            return "Date";
        }
        if(t.contains("timestamp")){
            return "Timestamp";
        }

        return null;
    }

    private static String getJdbcClassType(String t){
        t = t.toLowerCase();
        if(t.contains("int")){
            return "INTEGER";
        }
        if(t.contains("char")){
            return "VARCHAR";
        }
        if(t.contains("decimal")){
            return "BIGDECIMAL";
        }
        if(t.contains("float")){
            return "FLOAT";
        }
        if(t.contains("num")|| t.contains("double")){
            return "DOUBLE";
        }
        if(t.contains("text")){
            return "VARCHAR";
        }
        if(t.contains("date")){
            return "DATE";
        }
        if(t.contains("timestamp")){
            return "TIMESTAMP";
        }

        return null;
    }

    private static String transTableName(String name, Setting setting) {
        if (setting.getTable2PojoName() == null
                || setting.getTable2PojoName().isEmpty()) {
            return null;
        }
        for (Table2PojoName t : setting.getTable2PojoName()) {
            if (name.equalsIgnoreCase(t.getTb())) {
                return t.getCls();
            }
        }
        return null;

    }

    private static void init(Setting setting) {
        setting.setDbName(setting.getDbUrl().substring(
                setting.getDbUrl().lastIndexOf("/") + 1));
    }

    private static boolean checkData(Setting setting) {
        if (StringUtils.isBlank(setting.getDbUrl())) {
            return false;
        }

        if (StringUtils.isBlank(setting.getDbName())) {
            return false;
        }

        if (StringUtils.isBlank(setting.getUser())) {
            return false;
        }

        if (StringUtils.isBlank(setting.getPass())) {
            return false;
        }

        return true;
    }

    //wechat_name => wechatName
    private static String getStyleFiledName(String f){
        if(f.contains("_") == false){
            return f;
        }
        String[] sz = f.split("_");
        String rtn = sz[0];
        for (int i = 1; i < sz.length; i++) {
            String s = sz[i].toUpperCase();
            rtn += s.charAt(0) + sz[i].substring(1);
        }
        return rtn;
    }

    private static List<Item> getColumn(IDBHelper helper, String table,
                                            String dbName) {
        List<Item> map = new LinkedList<>();
        try {
            ResultSet rs = helper
                    .executeQry(helper.getTablePropertySQL(table, dbName));
            if (rs != null) {
                while (rs.next()) {
                    String column = rs.getString(1).toLowerCase();
                    System.out.println(" column=> " + column);
                    String type = rs.getString(2);
                    System.out.println(" calssName=> " + type);
                    map.add(new Item(column,getStyleFiledName(column),getJavaClassType(type),getJdbcClassType(type)));
                }
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String readSetting(File file) {
        try {
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    file), "utf-8");// 考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            StringBuilder sbBuilder = new StringBuilder();
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                sbBuilder.append(lineTxt);
            }
            bufferedReader.close();
            read.close();

            return sbBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
