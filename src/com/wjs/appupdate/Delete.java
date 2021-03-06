package com.wjs.appupdate;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.wjs.bean.ApkInfo;
import com.wjs.bean.PathInfo;
import com.wjs.utils.JDBC;

public class Delete extends HttpServlet 
{
	PathInfo info=PathInfo.parsePathInfo();
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String str=request.getParameter("code_id");
		String string = str.substring(0,str.lastIndexOf("wjs_wjs"));
		String vercode=str.substring(str.lastIndexOf("wjs_wjs")+7);
		string=string.replaceAll("wjs_wjs", ".");
		ApkInfo apkInfo = JDBC.getApkInfo(string, vercode);
		File iconpath=new File(apkInfo.getIconPath());
		iconpath.delete();
		removeEmptyDir(iconpath.getParentFile());
		
		File apkPath=new File(apkInfo.getAppPath());
		apkPath.delete();
		removeEmptyDir(apkPath.getParentFile());
		
		File file=new File(info.getPatchpath()+File.separator+string+File.separator);
		File[] listFiles = file.listFiles();
		if(listFiles!=null)
		{
			for(int i=0;i<listFiles.length;i++)
			{
				File lfile=listFiles[i];
				String name = lfile.getName();
				String[] split = name.split("[_.]");
				for(int j=0;j<split.length;j++)
				{
					if(split[j]!=null&&split[j].equals(vercode))
					{
						lfile.delete();
					}
				}
			}
			removeEmptyDir(file);
		}
		int delete = JDBC.delete(string, vercode);
		if(delete==1)
		{
			out.print(true);
		}
		else
		{
			out.print(false);
		}
		out.flush();
		out.close();
	}
	public void removeEmptyDir(File file)
	{
		if(file.isDirectory()&&file.list().length==0)
		{
			file.delete();
			removeEmptyDir(file.getParentFile());
		}
	}
}
