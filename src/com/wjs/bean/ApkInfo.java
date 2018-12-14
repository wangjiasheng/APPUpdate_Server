package com.wjs.bean;
public class ApkInfo
{
	private int id=0;
	private String appPath="";
	private String packageName="";
	private String verName="";
	private String verCode="";
	private String iconName = "";  
	private String iconPath="";
	private String appName="";
	private String apkmd5="";
	private String updatemessage;
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	public String getVerName() {
		return verName;
	}
	public void setVerName(String verName) {
		this.verName = verName;
	}
	public String getVerCode() {
		return verCode;
	}
	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getAppPath() {
		return appPath;
	}
	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}
	public String getIconPath() {
		return iconPath;
	}
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getApkmd5() {
		return apkmd5;
	}
	public void setApkmd5(String apkmd5) {
		this.apkmd5 = apkmd5;
	}
	public void setUpdatemessage(String updatemessage) {
		this.updatemessage = updatemessage;
	}
	public String getUpdatemessage() {
		return updatemessage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUpdatemessageJSON() {
		String[] split = updatemessage.split("\r\n");
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		if(split.length>0)
		{
			sb.append("\""+split[0]+"\"");
			for(int i=1;i<split.length;i++)
			{
				sb.append(",\""+split[i]+"\"");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	@Override
	public String toString() {
		return "ApkInfo [packageName=" + packageName + ",appName="+appName+",verName=" + verName
				+ ", verCode=" + verCode + ", iconName=" + iconName + "]";
	}
	public String toJSON(boolean bool)
	{
		StringBuffer buffer=new StringBuffer();
		buffer.append("{");
		buffer.append("\"status\": true,");
		buffer.append("\"incremental_update\": "+bool+",");
		buffer.append("\"packageName\": \""+getPackageName()+"\",");
		buffer.append("\"verName\": \""+getVerName()+"\",");
		buffer.append("\"verCode\": \""+getVerCode()+"\"");
		buffer.append("}");
		return  buffer.toString();
	}
}