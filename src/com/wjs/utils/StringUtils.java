package com.wjs.utils;

public class StringUtils
{
	public static boolean isNull(String str)
	{
		if(str==null||str.trim().equals(""))
			return true;
		return false;
	}
	public static boolean isNotNull(String str){
		return !isNull(str);
	}
}
