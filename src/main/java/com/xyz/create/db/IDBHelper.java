package com.xyz.create.db;

import java.sql.ResultSet;

/**
 * Created by yuerfeng on 2018/8/21.
 */
public interface IDBHelper {
    ResultSet executeQry(String sql) throws Exception;

    String getTablePropertySQL(String tableName,String dbName);

    String getAllTableSQL();


}
