package com.xyz.create.db;

import java.sql.*;

/**
 * Created by yuerfeng on 2018/8/21.
 */
public class OracleDBHelper implements IDBHelper{
    //数据库连接对象
    private static Connection conn = null;
    private static OracleDBHelper dbHelper = new OracleDBHelper();
    //驱动
    private static String driver = "oracle.jdbc.driver.OracleDriver";

    // 获得连接对象
    public static synchronized OracleDBHelper getHelper(String url,String user,String pass){
        if(conn == null){
            try {
                Class.forName(driver);
                conn = DriverManager.getConnection(url, user, pass);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dbHelper;
    }

    //关闭连接
    public void close(){
        try {
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeQry(String sql) throws Exception {
        //建立一个结果集，用来保存查询出来的结果
        return conn.prepareStatement(sql).executeQuery();
    }

    @Override
    public String getTablePropertySQL(String tableName,String dbName) {
        return "select t.COLUMN_NAME,t.DATA_TYPE from user_tab_columns t where t.table_name = '" + tableName + "' order by t.COLUMN_ID ASC";
    }

    @Override
    public String getAllTableSQL(){

        return "select TABLE_NAME from user_tab_comments";
    }
}
