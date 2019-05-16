package com.xyz.create.vo;

public class Item {
    private String column;
    private String property;
    private String javaType;
    private String jdbcType;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Item(String column,String property,String javaType,String jdbcType){
        this.column = column;
        this.property = property;
        this.javaType = javaType;
        this.jdbcType = jdbcType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}
