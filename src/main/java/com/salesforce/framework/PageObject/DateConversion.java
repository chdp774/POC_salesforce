package com.salesforce.framework.PageObject;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

public class DateConversion {

	public static void main(String[] args) {
		//Time format conversion
//		String str = "111623";
//		char[] ch = str.toCharArray();
//		System.out.println(ch[0] +""+ ch[1] + ":" + ch[2] + ch[3] + ":" + ch[4] + ch[5]);
		
		//Date format conversion
//		Calendar calendar = Calendar.getInstance();
////		calendar.add(Calendar.DAY_OF_YEAR, 1);
//		calendar.add(Calendar.DATE, 1);
//		Date futureDateTime = calendar.getTime();
//		System.out.println("----" +futureDateTime);
//		
//		LocalDate date = LocalDate.now(); //2020-01-12
//		LocalDate firstDay = date.with(firstDayOfYear());
//		System.out.println("firstDay of calendar: " +firstDay);
//		
//		Calendar cal = Calendar.getInstance();
////		cal.add(firstDay, 2);
		
//		Date date = new Date();
//	    System.out.println("date: "+date);
//	    Calendar cal = Calendar.getInstance();
//	    cal.setTime(date);
//
//	    System.out.println("cal:"+cal.getTime());
//
//	    cal.set(Calendar.MONTH, 0);
//	    cal.set(Calendar.DAY_OF_MONTH, 1);
//
//	    System.out.println("cal new: "+cal.getTime());
//	    
//	    Calendar calendar = Calendar.getInstance();
//	calendar.add(Calendar.DAY_OF_YEAR, 1);
////	calendar.add(Calendar.DATE, 1);
//	Date futureDateTime = calendar.getTime();
//	System.out.println("----" +futureDateTime);
//		Date dt=new Date();
//		dt.getYear();
//		
//		LocalDate date = LocalDate.of(2022, Month.JANUARY, 01);
//		System.out.println("date : " +date.plusDays(59));
//		
////		Calendar cal = new GregorianCalendar();
//		Calendar cal = Calendar.getInstance();
//		cal.set(2022, 0, 0);
//		cal.add(Calendar.DATE, 32);
//		Date futureDateTime = cal.getTime();
//		System.out.println("---------"+futureDateTime);
		String date = dateFormat();
		System.out.println("date value is: "+date);
		String str = timeFormat("123456");
		System.out.println("time value : " + str);
	}
	
	public static String dateFormat() {
		String str = "122111";
		char[] ch = str.toCharArray();
		String year = str.substring(0, 3);
		String daysCount = str.substring(3, 6);
		int daysCount_int = Integer.parseInt(daysCount);
//		System.out.println("days Count value is: "+daysCount);
//		System.out.println("year value is: "+ year);
		String Uyear = year.replaceAll(year, "2022");
//		System.out.println(Uyear + ":" + daysCount);
		
		LocalDate date = LocalDate.of(2022, Month.JANUARY, 1);
		LocalDate value = date.plusDays(daysCount_int - 1);
		String val = value.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//		System.out.println("Formated date : " +val);
		return val;
	}
	
	public static String timeFormat(String str) {
		
		char[] ch = str.toCharArray();
			if(str.length() == 6) {
				System.out.println("Formated Time: " +ch[0] +""+ ch[1] + ":" + ch[2] + ch[3] + ":" + ch[4] + ch[5]);
				String str1 = ch[0] +""+ ch[1] + ":" + ch[2] + ch[3] + ":" + ch[4] + ch[5];
				System.out.println(str1);
				return str1;
			}else {
				System.out.println("Formated Time: " + "0" +ch[0]  + ":" + ch[1] + ch[2] + ":" + ch[3]+ ch[4]);
				String str2 =  "0" +ch[0]  + ":" + ch[1] + ch[2] + ":" + ch[3]+ ch[4];
				System.out.println(str2);
				return str2;
			}
	}
//		if(str.length() == 6) {
//			System.out.println("Formated Time: " +ch[0] +""+ ch[1] + ":" + ch[2] + ch[3] + ":" + ch[4] + ch[5]);
//			String str1 = ch[0] +""+ ch[1] + ":" + ch[2] + ch[3] + ":" + ch[4] + ch[5];
//		}else {
//			System.out.println("Formated Time: " +ch[0] +""+ ch[1] + ":" + ch[2] + ch[3] + ":" + ch[4]);
//			String str2 = ch[0] +""+ ch[1] + ":" + ch[2] + ch[3] + ":" + ch[4];
//		}
}
