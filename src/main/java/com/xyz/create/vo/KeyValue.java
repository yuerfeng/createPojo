package com.xyz.create.vo;

public class KeyValue {
	private String k;
	private Object v;
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	public Object getV() {
		return v;
	}
	public void setV(Object v) {
		this.v = v;
	}
	
	public KeyValue(String key,Object value){
		k = key;
	    v = value;
	}
}
