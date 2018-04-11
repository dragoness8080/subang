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
	 * ����ʡ
	 */
	public static String[] mYearDatas;
	/**
	 * key - �� value - ��
	 */
	public static Map<String, String[]> mMonthDatasMap = new HashMap<String, String[]>();
	/**
	 * key -�� values -��
	 */
	public static Map<String, String[]> mDayDatasMap = new HashMap<String, String[]>();
	
	/**
	 * key - �� values - ����
	 */
	public static Map<String, String> mZipcodeDatasMap = new HashMap<String, String>(); 

	/**
	 * ��ǰʡ������
	 */
	public static String mCurrentYearName;
	/**
	 * ��ǰ�е�����
	 */
	public static String mCurrentMonthName;
	/**
	 * ��ǰ��������
	 */
	public static String mCurrentDayName ="";
	
	/**
	 * ��ǰ������������
	 */
	public static String mCurrentZipCode ="";
	
	/**
	 * ����ʡ������XML����
	 */
	
	public static void initProvinceDatas(Context context)
	{
		List<BirthProvinceModel> yearList = null;
    	AssetManager asset = context.getAssets();
        try {
            InputStream input = asset.open("nyr_data.xml");
            // ����һ������xml�Ĺ�������
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// ����xml
			SAXParser parser = spf.newSAXParser();
			BirthXmlParserHandler handler = new BirthXmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// ��ȡ��������������
			yearList = handler.getDataList();
			//*/ ��ʼ��Ĭ��ѡ�е�ʡ���С���
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
        		// ��������ʡ������
        		mYearDatas[i] = yearList.get(i).getName();
        		List<MonthModel> cityList = yearList.get(i).getMonthList();
        		String[] cityNames = new String[cityList.size()];
        		for (int j=0; j< cityList.size(); j++) {
        			// ����ʡ����������е�����
        			cityNames[j] = cityList.get(j).getName();
        			List<DayModel> districtList = cityList.get(j).getDayList();
        			String[] distrinctNameArray = new String[districtList.size()];
        			DayModel[] distrinctArray = new DayModel[districtList.size()];
        			for (int k=0; k<districtList.size(); k++) {
        				// ����������������/�ص�����
        				DayModel districtModel = new DayModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				// ��/�ض��ڵ��ʱ࣬���浽mZipcodeDatasMap
        				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
        				distrinctArray[k] = districtModel;
        				distrinctNameArray[k] = districtModel.getName();
        			}
        			// ��-��/�ص����ݣ����浽mDistrictDatasMap
        			mDayDatasMap.put(yearList.get(i).getName()+cityNames[j], distrinctNameArray);
        		}
        		// ʡ-�е����ݣ����浽mCitisDatasMap
        		mMonthDatasMap.put(yearList.get(i).getName(), cityNames);
        	}
        } catch (Throwable e) {  
            e.printStackTrace();  
        } finally {
        	
        } 
	}

}
