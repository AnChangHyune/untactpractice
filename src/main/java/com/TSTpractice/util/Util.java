package com.TSTpractice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

	public static String getNowDateStr() {
		SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
				
		Date time = new Date();
				
		return format1.format(time);
	}
	
}
