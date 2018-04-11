package cn.pedant.birthselect;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import android.content.Context;
import android.content.res.AssetManager;

public class BirthSelect {
	
	/**
	 * 所有省
	 */
	public static String[] mYearDatas;
	/**
	 * key - 年 value - 月
	 */
	public static Map<String, String[]> mMonthDatasMap = new HashMap<String, String[]>();
	/**
	 * key -月 values -日
	 */
	public static Map<String, String[]> mDayDatasMap = new HashMap<String, String[]>();
	
	/**
	 * key - 日 values - 冇用
	 */
	public static Map<String, String> mZipcodeDatasMap = new HashMap<String, String>(); 

	/**
	 * 当前省的名称
	 */
	public static String mCurrentYearName;
	/**
	 * 当前市的名称
	 */
	public static String mCurrentMonthName;
	/**
	 * 当前区的名称
	 */
	public static String mCurrentDayName ="";
	
	/**
	 * 当前区的邮政编码
	 */
	public static String mCurrentZipCode ="";
	
	/**
	 * 解析省市区的XML数据
	 */
	
	public static void initProvinceDatas(Context context)
	{
		List<BirthProvinceModel> yearList = null;
    	AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("nyr_data.xml");
            // 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			BirthXmlParserHandler handler = new BirthXmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			yearList = handler.getDataList();
			//*/ 初始化默认选中的省、市、区
			if (yearList!= null && !yearList.isEmpty()) {
				mCurrentYearName = yearList.get(0).getName();
				List<MonthModel> monthList = yearList.get(0).getMonthList();
				if (monthList!= null && !monthList.isEmpty()) {
					mCurrentMonthName = monthList.get(0).getName();
					List<DayModel> dayList = monthList.get(0).getDayList();
					mCurrentDayName = dayList.get(0).getName();
					mCurrentZipCode = dayList.get(0).getZipcode();
				}
			}
			//*/
			mYearDatas = new String[yearList.size()];
        	for (int i=0; i< yearList.size(); i++) {
        		// 遍历所有省的数据
        		mYearDatas[i] = yearList.get(i).getName();
        		List<MonthModel> cityList = yearList.get(i).getMonthList();
        		String[] cityNames = new String[cityList.size()];
        		for (int j=0; j< cityList.size(); j++) {
        			// 遍历省下面的所有市的数据
        			cityNames[j] = cityList.get(j).getName();
        			List<DayModel> districtList = cityList.get(j).getDayList();
        			String[] distrinctNameArray = new String[districtList.size()];
        			DayModel[] distrinctArray = new DayModel[districtList.size()];
        			for (int k=0; k<districtList.size(); k++) {
        				// 遍历市下面所有区/县的数据
        				DayModel districtModel = new DayModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				// 区/县对于的邮编，保存到mZipcodeDatasMap
        				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				distrinctArray[k] = districtModel;
        				distrinctNameArray[k] = districtModel.getName();
        			}
        			// 市-区/县的数据，保存到mDistrictDatasMap
        			mDayDatasMap.put(yearList.get(i).getName()+cityNames[j], distrinctNameArray);
        		}
        		// 省-市的数据，保存到mCitisDatasMap
        		mMonthDatasMap.put(yearList.get(i).getName(), cityNames);
        	}
        } catch (Throwable e) {  
            e.printStackTrace();  
        } finally {
        	
        } 
	}

}
