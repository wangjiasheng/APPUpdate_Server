package com.wjs.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wjs.bean.ApkInfo;
public class ReadApkInfo 
{
    /**
     * 查看meta信息 aapt d --include-meta-data badging *.apk
     * @param apkPath
     * @param aaptPath
     * @return
     */
	    public static ApkInfo getAppInfo(String apkPath, String aaptPath) 
	    {  
	    	ApkInfo info=new ApkInfo();
	        try 
	        {  
	            Runtime rt = Runtime.getRuntime();  
	            String order = aaptPath + "aapt.exe" + " d badging \"" + apkPath + "\"";  
	            Process proc = rt.exec(order);  
	            InputStream inputStream = proc.getInputStream();  
	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));  
	            String line = null;  
	            while((line = bufferedReader.readLine()) != null)
	            {  
	                if(line.contains("application:"))
	                {
	                    String iconName = line.substring(line.lastIndexOf("/") + 1, line.lastIndexOf("'")).trim().toLowerCase();  
	                    info.setIconName(iconName);
	                    break;  
	                }
	                else if(line.startsWith("package"))
	                {
	                	Pattern compile = Pattern.compile("package: name='(.*?)' versionCode='(.*?)' versionName='(.*?)'");
	                	Matcher matcher = compile.matcher(line);
	                	if(matcher.find())
	                	{
	                		String packageName=matcher.group(1);
	                		String verCode=matcher.group(2);
	                		String verName=matcher.group(3);
	                		info.setAppPath(apkPath);
	                		info.setPackageName(packageName);
	                		info.setVerName(verName);
	                		info.setVerCode(verCode);
	                	}
	                }
	                else if(line.startsWith("application-label:"))
	                {
	                	Pattern compile = Pattern.compile("application-label:\\s*'(.*?)'");
	                	Matcher matcher = compile.matcher(line);
	                	if(matcher.find())
	                	{
	                		String appName=matcher.group(1);
	                		info.setAppName(appName);
	                	}
	                }
	            }  
	        } catch (Throwable e) {  
	            e.printStackTrace();  
	        }  
	        return info;  
	    }  
}
