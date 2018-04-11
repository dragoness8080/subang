package com.gcs.suban.bean;

import java.io.Serializable;

public class UserBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private String uri;
	private String id;
	private String iscustomservice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}
	
	public String getIscustomservice() {
		return iscustomservice;
	}

	public void setIscustomservice(String iscustomservice) {
		this.iscustomservice = iscustomservice;
	}

	@Override
	public String toString() {
		return "UserBean [name=" + name + ", uri=" + uri + ", id=" + id + ", iscustomservice=" + iscustomservice + "]";
	}

}

