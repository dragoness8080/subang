package cn.pedant.birthselect;

import java.util.List;

public class MonthModel {
	private String name;
	private List<DayModel> dayList;
	
	public MonthModel() {
		super();
	}

	public MonthModel(String name, List<DayModel> districtList) {
		super();
		this.name = name;
		this.dayList = districtList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DayModel> getDayList() {
		return dayList;
	}

	public void setDistrictList(List<DayModel> dayList) {
		this.dayList = dayList;
	}

	@Override
	public String toString() {
		return "CityModel [name=" + name + ", dayList=" + dayList
				+ "]";
	}
	
}
