package com.heng.tree.entity;

import java.util.ArrayList;

/**
 * 
 * @author Apathy����
 * 
 *         ��������ʵ��
 * 
 * */

public class ParentEntity {

	private int groupColor;

	private String groupName;
	
	private String logo;
	
	private String id;

	private ArrayList<ChildEntity> childs;

	
	/* ==========================================================
	 * ======================= get method =======================
	 * ========================================================== */
	
	public int getGroupColor() {
		return groupColor;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public String getLogo() {
		return logo;
	}
	
	public String getId() {
		return id;
	}

	public ArrayList<ChildEntity> getChilds() {
		return childs;
	}
	
	/* ==========================================================
	 * ======================= set method =======================
	 * ========================================================== */

	public void setGroupColor(int groupColor) {
		this.groupColor = groupColor;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setChilds(ArrayList<ChildEntity> childs) {
		this.childs = childs;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	

}
