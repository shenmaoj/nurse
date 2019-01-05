package com.cmnt.nurse.common.utils;

import java.math.BigDecimal;

public class DoubleUtil {

	public static Double fmtDouble(Double value,Integer num){
		BigDecimal fmtValue = new BigDecimal(value);
		return fmtValue.setScale(num, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
}
