package cn.pedant.birthselect;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class BirthXmlParserHandler extends DefaultHandler {

	/**
	 * �洢���еĽ�������
	 */
	private List<BirthProvinceModel> provinceList = new ArrayList<BirthProvinceModel>();

	public BirthXmlParserHandler() {

	}

	public List<BirthProvinceModel> getDataList() {
		return provinceList;
	}

	@Override
	public void startDocument() throws SAXException {
		// ��������һ����ʼ��ǩ��ʱ�򣬻ᴥ���������
	}

	BirthProvinceModel provinceModel = new BirthProvinceModel();
	MonthModel cityModel = new MonthModel();
	DayModel districtModel = new DayModel();

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// ��������ʼ��ǵ�ʱ�򣬵����������
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
		// ����������ǵ�ʱ�򣬻�����������
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
