package com.gcs.suban.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class DateTool {
	/**
	 * ���˷���������Ҫת����ʱ���������磨"2014��06��14��16ʱ09��00��"������ʱ���
	 * 
	 * @param time
	 * @return
	 */
	public String data(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��ss��",
				Locale.CHINA);
		Date date;
		String times = null;
		try {
			date = sdr.parse(time);
			long l = date.getTime();
			String stf = String.valueOf(l);
			times = stf.substring(0, 10);
			Log.d("--444444---", times);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}

	/**
	 * ���˷���������Ҫת����ʱ���������磨"2014-06-14-16-09-00"������ʱ���
	 * 
	 * @param time
	 * @return
	 */
	public String dataOne(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss",
				Locale.CHINA);
		Date date;
		String times = null;
		try {
			date = sdr.parse(time);
			long l = date.getTime();
			String stf = String.valueOf(l);
			times = stf.substring(0, 10);
			Log.d("--444444---", times);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}

	public static String getTimestamp(String time, String type) {
		SimpleDateFormat sdr = new SimpleDateFormat(type, Locale.CHINA);
		Date date;
		String times = null;
		try {
			date = sdr.parse(time);
			long l = date.getTime();
			String stf = String.valueOf(l);
			times = stf.substring(0, 10);
			Log.d("--444444---", times);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return times;
	}

	/**
	 * ���ô˷���������Ҫת����ʱ����������磨1402733340�������"2014��06��14��16ʱ09��00��"��
	 * 
	 * @param time
	 * @return
	 */
	public String times(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��ss��");
		@SuppressWarnings("unused")
		long lcc = Long.valueOf(time);
		int i = Integer.parseInt(time);
		String times = sdr.format(new Date(i * 1000L));
		return times;

	}

	/**
	 * ���ô˷���������Ҫת����ʱ����������磨1402733340�������"2014��06��14��16ʱ09��"��
	 * 
	 * @param time
	 * @return
	 */
	public String timet(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��");
		@SuppressWarnings("unused")
		long lcc = Long.valueOf(time);
		int i = Integer.parseInt(time);
		String times = sdr.format(new Date(i * 1000L));
		return times;

	}

	// ���ô˷���������Ҫת����ʱ������磨1402733340�������"2014��06��14��16ʱ09��00��"��
	public static String times(long timeStamp) {
		SimpleDateFormat sdr = new SimpleDateFormat("MM��dd��  #  HH:mm");
		return sdr.format(new Date(timeStamp)).replaceAll("#",
				getWeek(timeStamp));

	}

	private static String getWeek(long timeStamp) {
		int mydate = 0;
		String week = null;
		Calendar cd = Calendar.getInstance();
		cd.setTime(new Date(timeStamp));
		mydate = cd.get(Calendar.DAY_OF_WEEK);
		// ��ȡָ������ת�������ڼ�
		if (mydate == 1) {
			week = "����";
		} else if (mydate == 2) {
			week = "��һ";
		} else if (mydate == 3) {
			week = "�ܶ�";
		} else if (mydate == 4) {
			week = "����";
		} else if (mydate == 5) {
			week = "����";
		} else if (mydate == 6) {
			week = "����";
		} else if (mydate == 7) {
			week = "����";
		}
		return week;
	}

	// ���÷ָ����ʱ��ֳ�ʱ������
	/**
	 * ���ô˷���������Ҫת����ʱ����������磨1402733340�������"2014-06-14-16-09-00"��
	 * 
	 * @param time
	 * @return
	 */
	public String timesOne(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		@SuppressWarnings("unused")
		long lcc = Long.valueOf(time);
		int i = Integer.parseInt(time);
		String times = sdr.format(new Date(i * 1000L));
		return times;

	}

	/**
	 * ���÷ָ����ʱ��ֳ�ʱ������
	 * 
	 * @param time
	 * @return
	 */
	public static String[] timestamp(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��ss��");
		@SuppressWarnings("unused")
		long lcc = Long.valueOf(time);
		int i = Integer.parseInt(time);
		String times = sdr.format(new Date(i * 1000L));
		String[] fenge = times.split("[������ʱ����]");
		return fenge;
	}

	/**
	 * ���ݴ��ݵ����͸�ʽ��ʱ��
	 * 
	 * @param str
	 * @param type
	 *            ���磺yy-MM-dd
	 * @return
	 */
	public static String getDateTimeByMillisecond(String str, String type) {
		try {
			Date date = new Date(Long.valueOf(str));

			SimpleDateFormat format = new SimpleDateFormat(type);

			String time = format.format(date);

			return time;
		} catch (Exception e) {
			// TODO: handle exception
			return "";
		}

	}
	
	public static String TimeStamp2Date(String timestampString, String formats){    
		  Long timestamp = Long.parseLong(timestampString)*1000;    
		  String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));    
		  return date;    
		}  

	/**
	 * �ָ����ʱ��ֳ�ʱ������
	 * 
	 * @param time
	 * @return
	 */
	public String[] division(String time) {

		String[] fenge = time.split("[������ʱ����]");

		return fenge;

	}

	/**
	 * ����ʱ���������
	 * 
	 * @param time
	 * @return
	 */
	public static String changeweek(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��ss��");
		// long lcc = Long.valueOf(time);
		int i = Integer.parseInt(time);
		String times = sdr.format(new Date(i * 1000L));
		Date date = null;
		int mydate = 0;
		String week = null;
		try {
			date = sdr.parse(times);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			mydate = cd.get(Calendar.DAY_OF_WEEK);
			// ��ȡָ������ת�������ڼ�
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mydate == 1) {
			week = "������";
		} else if (mydate == 2) {
			week = "����һ";
		} else if (mydate == 3) {
			week = "���ڶ�";
		} else if (mydate == 4) {
			week = "������";
		} else if (mydate == 5) {
			week = "������";
		} else if (mydate == 6) {
			week = "������";
		} else if (mydate == 7) {
			week = "������";
		}
		return week;

	}

	/**
	 * ��ȡ���ں����ڡ����磺��������������������������:����������һ
	 * 
	 * @param time
	 * @param type
	 * @return
	 */
	public static String getDateAndWeek(String time, String type) {
		return getDateTimeByMillisecond(time + "000", type) + "  "
				+ changeweekOne(time);
	}

	/**
	 * ����ʱ���������
	 * 
	 * @param time
	 * @return
	 */
	public static String changeweekOne(String time) {
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		// long lcc = Long.valueOf(time);
		int i = Integer.parseInt(time);
		String times = sdr.format(new Date(i * 1000L));
		Date date = null;
		int mydate = 0;
		String week = null;
		try {
			date = sdr.parse(times);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			mydate = cd.get(Calendar.DAY_OF_WEEK);
			// ��ȡָ������ת�������ڼ�
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mydate == 1) {
			week = "������";
		} else if (mydate == 2) {
			week = "����һ";
		} else if (mydate == 3) {
			week = "���ڶ�";
		} else if (mydate == 4) {
			week = "������";
		} else if (mydate == 5) {
			week = "������";
		} else if (mydate == 6) {
			week = "������";
		} else if (mydate == 7) {
			week = "������";
		}
		return week;

	}

	/**
	 * ��ȡ��ǰʱ��
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��  HHʱmm��");
		return sdf.format(new java.util.Date());
	}

	/**
	 * ���������磨2014��06��14��16ʱ09��00�룩���أ���������
	 * 
	 * @param time
	 * @return
	 */
	public String week(String time) {
		Date date = null;
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy��MM��dd��HHʱmm��ss��");
		int mydate = 0;
		String week = null;
		try {
			date = sdr.parse(time);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			mydate = cd.get(Calendar.DAY_OF_WEEK);
			// ��ȡָ������ת�������ڼ�
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mydate == 1) {
			week = "������";
		} else if (mydate == 2) {
			week = "����һ";
		} else if (mydate == 3) {
			week = "���ڶ�";
		} else if (mydate == 4) {
			week = "������";
		} else if (mydate == 5) {
			week = "������";
		} else if (mydate == 6) {
			week = "������";
		} else if (mydate == 7) {
			week = "������";
		}
		return week;
	}

	/**
	 * ���������磨2014-06-14-16-09-00�����أ���������
	 * 
	 * @param time
	 * @return
	 */
	public String weekOne(String time) {
		Date date = null;
		SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		int mydate = 0;
		String week = null;
		try {
			date = sdr.parse(time);
			Calendar cd = Calendar.getInstance();
			cd.setTime(date);
			mydate = cd.get(Calendar.DAY_OF_WEEK);
			// ��ȡָ������ת�������ڼ�
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mydate == 1) {
			week = "������";
		} else if (mydate == 2) {
			week = "����һ";
		} else if (mydate == 3) {
			week = "���ڶ�";
		} else if (mydate == 4) {
			week = "������";
		} else if (mydate == 5) {
			week = "������";
		} else if (mydate == 6) {
			week = "������";
		} else if (mydate == 7) {
			week = "������";
		}
		return week;
	}
}
