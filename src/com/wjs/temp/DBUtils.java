package com.wjs.temp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wjs.bean.ApkInfo;
import com.wjs.utils.JDBC;
import com.wjs.utils.MD5Utils;

public class DBUtils 
{
	public static void main(String[] args) throws Exception
	{
		System.out.println(JDBC.createTable());
		//System.out.println(JDBC.dropTable());
		/*for(int i=0;i<100;i++)
		{
			ApkInfo info=new ApkInfo();
			info.setVerCode(i+"");
			JDBC.insert(info);
		}*/
		//System.out.println("---"+MD5Utils.getFileMD5(new File(JDBC.getApkInfo("wanyan.com.updatelib_incre","21").getAppPath())));
		//System.out.println(JDBC.getApkInfo("wanyan.com.updatelib_incre","21").getApkmd5());
		
		/*ApkInfo apkInfo = JDBC.checkUpdate("wanyan.com.updatelib_incre");
		apkInfo.setVerName("1.8");
		System.out.print(JDBC.update(apkInfo));*/
		
		//System.out.print(MD5Utils.getFileMD5(new File("E:\\Soft\\MyUtils\\MyUtils1.0\\UpdateLib_Incre\\app\\build\\outputs\\apk\\app-debug.apk")));
		
		//System.out.println(JDBC.getApkInfo("com.wenyan.liquefiedgas.liquefiedgas_v2","21").getUpdatemessage());
		//String[] split = "29_21.apk".split("[_.]");
		//for(int i=0;i<split.length;i++)
		//{
		//	System.out.println(split[i]);
		//}
		//System.out.println(JDBC.query(1, 2));
		/*String url="http://localhost:8080/AppUpdate/CheckUpdate?packageName=wanyan.com.updatelib_incre&verCode=21&apkmd5=a58013b7f27e62fccb3d23627a7b8ac0&incremental_update=true";
		String regex="((http|https)://(\\w+|\\.)*|(http|https)://(\\w+|\\.)*:([1-9][0-9]{0,3}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]{1}|6553[0-5]).*)";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(url);
         if(!matcher.matches())
         {
             throw new NullPointerException("URL格式错误");
         }
		System.out.println(regex);*/
		/*StringBuffer sql=new StringBuffer();
		sql.append("create table `apkinfo` (");
		sql.append("id  int(11) primary key not null AUTO_INCREMENT,");
		sql.append("`packageName` varchar(255) default null,");
		sql.append("`appName` varchar(255) default null,");
		sql.append("`apkmd5` varchar(255) default null,");
		sql.append("`verName` varchar(255) default null,");
		sql.append("`verCode` varchar(255) default null,");
		sql.append("`iconName` varchar(255) default null,");
		sql.append("`updatemessage` longtext,");
		sql.append("`iconPath` longtext,");
		sql.append("`appPath` longtext");
		sql.append(") engine=InnoDB default charset=utf8;");
		System.out.println(sql.toString());*/
		//System.out.println(size(1572864));
	}
	public static String size(long size)
	{
		 double kiloByte = size/1024;  
	        if(kiloByte < 1) {  
	            return size + "Byte(s)";  
	        }  
	          
	        double megaByte = kiloByte/1024;  
	        if(megaByte < 1) {  
	            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
	            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
	        }  
	          
	        double gigaByte = megaByte/1024;  
	        if(gigaByte < 1) {  
	            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
	            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
	        }  
	          
	        double teraBytes = gigaByte/1024;  
	        if(teraBytes < 1) {  
	            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
	            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
	        }  
	        BigDecimal result4 = new BigDecimal(teraBytes);  
	        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
	}
}
