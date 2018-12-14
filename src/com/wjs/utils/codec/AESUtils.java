package com.wjs.utils.codec;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AESUtils
{
	public static final String encodec(String string)
	{
		if(string==null)
		{
			return string;
		}
		AES mAes = new AES();
		byte[] mBytes = null;
		try 
		{
			mBytes = string.getBytes("utf-8");
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		String enString = mAes.encrypt(mBytes);
		return enString;
	}
	public static String decodec(String string)
	{
		if(string==null)
		{
			return string;
		}
		AES mAes = new AES();
		byte[] data=mAes.decrypt(string);
		if(data==null)
		{
			return null;
		}
		try 
		{
			return new String(data,"utf-8");
		} 
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
