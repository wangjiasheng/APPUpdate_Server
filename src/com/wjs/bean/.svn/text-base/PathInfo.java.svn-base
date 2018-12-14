package com.wjs.bean;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import com.wjs.utils.JDBC;

public class PathInfo 
{
	private String apppath;
	private String iconpath;
	private String patchpath;
	private String toolspath;
	public String getApppath() {
		return apppath;
	}
	public void setApppath(String apppath) {
		File file=new File(apppath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		this.apppath = apppath;
	}
	public String getIconpath() {
		return iconpath;
	}
	public void setIconpath(String iconpath) {
		File file=new File(iconpath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		this.iconpath = iconpath;
	}
	public String getPatchpath() {
		return patchpath;
	}
	public void setPatchpath(String patchpath) {
		File file=new File(patchpath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		this.patchpath = patchpath;
	}
	public String getToolspath() {
		return toolspath;
	}
	public void setToolspath(String toolspath) {
		File file=new File(toolspath);
		if(!file.exists())
		{
			file.mkdirs();
		}
		this.toolspath = toolspath;
	}
	public static PathInfo parsePathInfo()
	{
		try
		{
			InputStream in = JDBC.class.getClassLoader().getResourceAsStream("Path.properties");
			Properties properties=new Properties();
			properties.load(in);
			String apppath = properties.getProperty("apppath");
			String iconpath = properties.getProperty("iconpath");
			String patchpath = properties.getProperty("patchpath");
			String toolspath = properties.getProperty("toolspath");
			PathInfo dbinfo=new PathInfo();
			dbinfo.setApppath(apppath);
			dbinfo.setIconpath(iconpath);
			dbinfo.setPatchpath(patchpath);
			dbinfo.setToolspath(toolspath);
			return dbinfo;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
