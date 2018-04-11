package cn.pedant.birthselect;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class BirthXmlParserHandler extends DefaultHandler {

	/**
	 * 存储所有的解析对象
	 */
	private List<BirthProvinceModel> provinceList = new ArrayList<BirthProvinceModel>();

	public BirthXmlParserHandler() {

	}

	public List<BirthProvinceModel> getDataList() {
		return provinceList;
	}

	@Override
	public void startDocument() throws SAXException {
		// 当读到第一个开始标签的时候，会触发这个方法
	}

	BirthProvinceModel provinceModel = new BirthProvinceModel();
	MonthModel cityModel = new MonthModel();
	DayModel districtModel = new DayModel();

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// 当遇到开始标记的时候，调用这个方法
		if (qName.equals("year")) {
			provinceModel = new BirthProvinceModel();
			provinceModel.setName(attributes.getValue(0));
			provinceModel.setCityList(new ArrayList<MonthModel>());
		} else if (qName.equals("month")) {
			cityModel = new MonthModel();
			cityModel.setName(attributes.getValue(0));
			cityModel.setDistrictList(new ArrayList<DayModel>());
		} else if (qName.equals("day")) {
			districtModel = new DayModel();
			districtModel.setName(attributes.getValue(0));
			districtModel.setZipcode(attributes.getValue(1));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// 遇到结束标记的时候，会调用这个方法
		if (qName.equals("day")) {
			cityModel.getDayList().add(districtModel);
		} else if (qName.equals("month")) {
			provinceModel.getMonthList().add(cityModel);
		} else if (qName.equals("year")) {
			provinceList.add(provinceModel);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}

}
