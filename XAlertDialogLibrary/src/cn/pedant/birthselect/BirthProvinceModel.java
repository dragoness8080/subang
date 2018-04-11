package cn.pedant.birthselect;

import java.util.List;

public class BirthProvinceModel {
	private String name;
	private List<MonthModel> monthList;
	
	public BirthProvinceModel() {
		super();
	}

	public BirthProvinceModel(String name, List<MonthModel> cityList) {
		super();
		this.name = name;
		this.monthList = cityList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MonthModel> getMonthList() {
		return monthList;
	}

	public void setCityList(List<MonthModel> monthList) {
		this.monthList = monthList;
	}

	@Override
	public String toString() {
		return "ProvinceModel [name=" + name + ", monthList=" + monthList + "]";
	}
	
}
