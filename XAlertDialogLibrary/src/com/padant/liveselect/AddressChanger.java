package com.padant.liveselect;


public class AddressChanger {
//	 private AlertDialog dialog;
//	 private WheelView mViewProvince;
//	 private WheelView mViewCity;
//	 private WheelView mViewDistrict;
	//
	// public static void getAddressChanger() {
//	 AlertDialog.Builder builder = new Builder(MyActivity.this);
//	 LayoutInflater inflater = getLayoutInflater();
//	 View view = inflater.inflate(R.layout.address_changer_item, null);
//	 mViewProvince = (WheelView) view.findViewById(R.id.item_province);
//	 mViewCity = (WheelView) view.findViewById(R.id.item_city);
//	 mViewDistrict = (WheelView) view.findViewById(R.id.item_district);
//	
//	 setUpCityListener();
//	 setUpData();
//	 dialog = builder.create();
//	 dialog.setView(view);
//	 dialog.show();
	// }
	//
	// private void setUpCityListener() {
	// mViewProvince.addChangingListener(this);
	// // 添加change事件
	// mViewCity.addChangingListener(this);
	// // 添加change事件
	// mViewDistrict.addChangingListener(this);
	// }
	//
	// private void setUpData() {
	// initProvinceDatas();
	// mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(
	// MyActivity.this, mProvinceDatas));
	// // 设置可见条目数量
	// mViewProvince.setVisibleItems(7);
	// mViewCity.setVisibleItems(7);
	// mViewDistrict.setVisibleItems(7);
	// updateCities();
	// updateAreas();
	// }
	//
	// private void updateAreas() {
	// int pCurrent = mViewCity.getCurrentItem();
	// mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
	// String[] areas = mDistrictDatasMap.get(mCurrentCityName);
	//
	// if (areas == null) {
	// areas = new String[] { "" };
	// }
	// mViewDistrict
	// .setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
	// mViewDistrict.setCurrentItem(0);
	// }
	//
	// private void updateCities() {
	// int pCurrent = mViewProvince.getCurrentItem();
	// mCurrentProviceName = mProvinceDatas[pCurrent];
	// String[] cities = mCitisDatasMap.get(mCurrentProviceName);
	// if (cities == null) {
	// cities = new String[] { "" };
	// }
	// mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
	// mViewCity.setCurrentItem(0);
	// updateAreas();
	// }
	//
	// @Override
	// public void onChanged(WheelView wheel, int oldValue, int newValue) {
	// if (wheel == mViewProvince) {
	// updateCities();
	// } else if (wheel == mViewCity) {
	// updateAreas();
	// } else if (wheel == mViewDistrict) {
	// mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
	// mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
	// }
	// }

}
