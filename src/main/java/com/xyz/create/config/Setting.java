package com.xyz.create.config;

import com.alibaba.fastjson.JSON;

import java.util.LinkedList;
import java.util.List;

public class Setting {
    private String dbUrl;
    private String dbName;
    private String user;
    private String pass;
    private String pojoPath;
    private String daoPath;
    private String dbType;
    private List<Table2PojoName> table2PojoName;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPojoPath() {
        return pojoPath;
    }

    public void setPojoPath(String pojoPath) {
        this.pojoPath = pojoPath;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public List<Table2PojoName> getTable2PojoName() {
        return table2PojoName;
    }

    public void setTable2PojoName(List<Table2PojoName> table2PojoName) {
        this.table2PojoName = table2PojoName;
    }

    public String getDaoPath() {
        return daoPath;
    }

    public void setDaoPath(String daoPath) {
        this.daoPath = daoPath;
    }

    public static void main(String[] arg){
        Setting setting = new Setting();
        setting.setDbUrl("jdbc:oracle:thin:@192.168.32.250:1521:smzqint");
        setting.setUser("shop");
        setting.setPass("shop");
        setting.setPojoPath("com.lppz.shop.pojo");
        setting.setDaoPath("com.lppz.shop.dao");
        setting.setDbType("oracle");
        setting.setTable2PojoName(new LinkedList<Table2PojoName>());

        Table2PojoName table2PojoName = new Table2PojoName();
        table2PojoName.setTb("T_SHOP");
        table2PojoName.setCls("Shop");
        setting.getTable2PojoName().add(table2PojoName);

        System.out.println(JSON.toJSONString(setting));

    }
}
