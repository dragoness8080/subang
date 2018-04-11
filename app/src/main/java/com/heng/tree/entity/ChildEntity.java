package com.heng.tree.entity;

import java.util.ArrayList;

/**
 * 
 * @author Apathy、恒
 * 
 *         父类分组的实体
 * 
 * */

public class ChildEntity {

	private int groupColor;

	private String groupName;
	
private String groupLogo;
	
	private String groupId;

	private ArrayList<String> childNames;
	
	private ArrayList<String> childLogos;
	
	private ArrayList<String> childIds;
	
	public ArrayList<String> getTeamcount() {
		return teamcount;
	}

	public void setTeamcount(ArrayList<String> teamcount) {
		this.teamcount = teamcount;
	}

	private ArrayList<String> teamcount;


	/* ==========================================================
	 * ======================= get method =======================
	 * ========================================================== */
	
	public int getGroupColor() {
		return groupColor;
	}

	public String getGroupName() {
		return groupName;
	}
	
	public String getGroupLogo() {
		return groupLogo;
	}
	
	public String getGroupId() {
		return groupId;
	}

	public ArrayList<String> getChildNames() {
		return childNames;
	}
	
	public ArrayList<String> getChildIds() {
		return childIds;
	}
	
	public ArrayList<String> getChildLogos() {
		return childLogos;
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
	
	public void setGroupLogo(String groupLogo) {
		this.groupLogo = groupLogo;
	}
	
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public void setChildLogos(ArrayList<String> childLogos) {
		this.childLogos = childLogos;
	}
	
	public void setChildIds(ArrayList<String> childIds) {
		this.childIds = childIds;
	}
	
	public void setChildNames(ArrayList<String> childNames) {
		this.childNames = childNames;
	}

}
