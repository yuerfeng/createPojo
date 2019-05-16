package com.xyz.create.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MysqlDBHelper implements IDBHelper{
	public static final String _url = "";  
    public static final String _user = "";  
    public static final String _password = "";  
  
    private static Connection conn = null;  
    private static MysqlDBHelper dbHelper = new MysqlDBHelper();
    
    private MysqlDBHelper(){
    	
    }
   
  
    public void close() {  
        try {  
            conn.close();
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
    
    public static MysqlDBHelper getHelper(String url, String user, String pass){
    	try {
            //指定连接类型
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            conn = DriverManager.getConnection(url, user, pass);
            return dbHelper;
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    	return null;
    }
    
    @Override
    public ResultSet executeQry(String sql) throws Exception{
    	return conn.createStatement().executeQuery(sql);
    }

    @Override
    public String getTablePropertySQL(String tableName,String dbName) {
        return "select column_name,data_type from information_schema.columns where table_name = '"
                + tableName
                + "' and table_schema = '"
                + dbName
                + "' order by ordinal_position asc;";
    }

    @Override
    public String getAllTableSQL(){
        return "show tables;";
    }
}
