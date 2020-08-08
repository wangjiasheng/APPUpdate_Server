package com.wjs.appupdate;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wjs.bean.ApkInfo;
import com.wjs.bean.PathInfo;
import com.wjs.utils.DecoderEncoderUtils;
import com.wjs.utils.JDBC;
import com.wjs.utils.MD5Utils;
import com.wjs.utils.StringUtils;
import com.wjs.utils.createPath;
/*
 *已经实现完毕不需要修改了
 */
public class CheckUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PathInfo info=PathInfo.parsePathInfo();
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=utf-8");
		String packageName=request.getParameter("packageName");
		String verCode=request.getParameter("verCode");
		String incremental_update=request.getParameter("incremental_update");
		String apkmd5=request.getParameter("apkmd5");
		PrintWriter writer = response.getWriter();
		if(StringUtils.isNull(packageName))
		{
			writer.print("{\"status\": false,\"errCode\": 100,\"message\": \"packageName为空\"}");
			writer.flush();
			writer.close();
			return;
		}
		if(StringUtils.isNull(verCode))
		{
			writer.print("{\"status\": false,\"errCode\": 101,\"message\": \"verCode为空\"}");
			writer.flush();
			writer.close();
			return;
		}
		if(StringUtils.isNull(incremental_update))
		{
			writer.print("{\"status\": false,\"errCode\": 102,\"message\": \"incremental_update为空\"}");
			writer.flush();
			writer.close();
			return;
		}
		if(!verCode.matches("\\d+"))
		{
			writer.print("{\"status\": false,\"errCode\": 103,\"message\": \"verCode必须是数字\"}");
			writer.flush();
			writer.close();
			return;
		}
		if(!(incremental_update.matches("true|false")))
		{
			writer.print("{\"status\": false,\"errCode\": 104,\"message\": \"incremental_update必须是布尔\"}");
			writer.flush();
			writer.close();
			return;
		}
		ApkInfo dbMaxVersionApkInfo = JDBC.checkUpdate(packageName); //根据包名查询VersionCode最大的apk信息
		ApkInfo userCurrentVersionApkInfo = JDBC.getApkInfo(packageName, verCode);//根据包名和versionCode查询VersionCode的Api信息

		if(dbMaxVersionApkInfo==null){
			writer.print("{\"status\": false,\"errCode\": 110,\"message\": \"您的apk服务器不存在\"}");
			writer.flush();
			writer.close();
			return;
		}
		if(Integer.parseInt(dbMaxVersionApkInfo.getVerCode())<Integer.parseInt(verCode))
		{
			writer.print("{\"status\": false,\"errCode\": 109,\"message\": \"开发者，您的apk高于最新的版本，请先发布最新版本\"}");
			writer.flush();
			writer.close();
			return;
		}
		if(Integer.parseInt(dbMaxVersionApkInfo.getVerCode())==Integer.parseInt(verCode))
		{
			writer.print("{\"status\": false,\"errCode\": 108,\"message\": \"您的apk是最新的版本\"}");
			writer.flush();
			writer.close();
			return;
		}
		if(userCurrentVersionApkInfo==null)
		{
			writer.print("{\"status\": false,\"errCode\": 105,\"message\": \"apk版本太低或者重大bug已被删除，服务器不存在此版本apk\"}");
			writer.flush();
			writer.close();
			return;
		}
		if(incremental_update.equals("false"))
		{
			String basePath=request.getScheme()+"://"+getIp(request)+":"+request.getServerPort()+request.getContextPath()+"/"+"Down?path="+DecoderEncoderUtils.encodeFilepath(dbMaxVersionApkInfo.getAppPath());
			writer.print("{\"status\": true,\"incremental_update\": false,\"verCode\":"+dbMaxVersionApkInfo.getVerCode()+",\"verName\":\""+dbMaxVersionApkInfo.getVerName()+"\",\"size\":"+new File(dbMaxVersionApkInfo.getAppPath()).length()+",\"updatemessage\":"+dbMaxVersionApkInfo.getUpdatemessageJSON()+",\"url\": \""+basePath+"\"}");
			writer.flush();
			writer.close();
			return;
		}
		if(apkmd5==null)
		{
			String basePath=request.getScheme()+"://"+getIp(request)+":"+request.getServerPort()+request.getContextPath()+"/"+"Down?path="+DecoderEncoderUtils.encodeFilepath(dbMaxVersionApkInfo.getAppPath());
			writer.print("{\"status\": true,\"incremental_update\": false,\"message\":\"apkmd5为空\",\"verCode\":"+dbMaxVersionApkInfo.getVerCode()+",\"verName\":\""+dbMaxVersionApkInfo.getVerName()+"\",\"size\":"+new File(dbMaxVersionApkInfo.getAppPath()).length()+",\"updatemessage\":"+dbMaxVersionApkInfo.getUpdatemessageJSON()+",\"url\": \""+basePath+"\"}");
			writer.flush();
			writer.close();
			return;
		}
		if(userCurrentVersionApkInfo!=null&&!(apkmd5.equals(userCurrentVersionApkInfo.getApkmd5())))
		{
			String basePath=request.getScheme()+"://"+getIp(request)+":"+request.getServerPort()+request.getContextPath()+"/"+"Down?path="+DecoderEncoderUtils.encodeFilepath(dbMaxVersionApkInfo.getAppPath());
			writer.print("{\"status\": true,\"incremental_update\": false,\"message\":\"该版本服务器端apkmd5和客户端apkmd5不同\",\"verCode\":"+dbMaxVersionApkInfo.getVerCode()+",\"verName\":\""+dbMaxVersionApkInfo.getVerName()+"\",\"size\":"+new File(dbMaxVersionApkInfo.getAppPath()).length()+",\"updatemessage\":"+dbMaxVersionApkInfo.getUpdatemessageJSON()+",\"url\": \""+basePath+"\"}");
			writer.flush();
			writer.close();
			return;
		}
		//TODO 此情况暂时没看懂
		if(userCurrentVersionApkInfo.getAppPath().equals(dbMaxVersionApkInfo.getAppPath()))
		{
			writer.print("{\"status\": false,\"errCode\": 106,\"message\": \"您的apk是最新的版本\"}");
			writer.flush();
			writer.close();
			return;
		}
		//读取配置文件中差分包的根目录，判断包名是否存在，不存在则创建
		File file=new File(info.getPatchpath()+File.separator+userCurrentVersionApkInfo.getPackageName()+File.separator);
		if(!file.exists())
		{
			file.mkdirs();
		}
		//拼接差分包的路径，判断差分包是否存在，如果存在则返回差分包的下载路径，不存在则创建差分包，并返回差分包路径
		String patch=file.getAbsolutePath()+File.separator+dbMaxVersionApkInfo.getVerCode()+"_"+userCurrentVersionApkInfo.getVerCode()+".patch";
		File filepatch=new File(patch);
		if(filepatch.exists())
		{
			String basePath=request.getScheme()+"://"+getIp(request)+":"+request.getServerPort()+request.getContextPath()+"/"+"Down?path="+DecoderEncoderUtils.encodeFilepath((info.getPatchpath()+File.separator+userCurrentVersionApkInfo.getPackageName()+File.separator+dbMaxVersionApkInfo.getVerCode()+"_"+userCurrentVersionApkInfo.getVerCode()+".patch"));
			writer.print("{\"status\": true,\"incremental_update\": true,\"old_apk_md5\":\""+MD5Utils.getFileMD5(new File(userCurrentVersionApkInfo.getAppPath()))+"\",\"new_apk_md5\":\""+MD5Utils.getFileMD5(new File(dbMaxVersionApkInfo.getAppPath()))+"\",\"verCode\":"+dbMaxVersionApkInfo.getVerCode()+",\"verName\":\""+dbMaxVersionApkInfo.getVerName()+"\",\"size\":"+new File(patch).length()+",\"updatemessage\":"+dbMaxVersionApkInfo.getUpdatemessageJSON()+",\"url\": \""+basePath+"\"}");
			writer.flush();
			writer.close();
		}
		else
		{
			boolean bool=createPath.createPatch(dbMaxVersionApkInfo.getAppPath(), userCurrentVersionApkInfo.getAppPath(),patch);
			if(bool)
			{
				String basePath=request.getScheme()+"://"+getIp(request)+":"+request.getServerPort()+request.getContextPath()+"/"+"Down?path="+DecoderEncoderUtils.encodeFilepath((info.getPatchpath()+File.separator+userCurrentVersionApkInfo.getPackageName()+File.separator+dbMaxVersionApkInfo.getVerCode()+"_"+userCurrentVersionApkInfo.getVerCode()+".patch"));
				writer.print("{\"status\": true,\"incremental_update\": true,\"old_apk_md5\":\""+MD5Utils.getFileMD5(new File(userCurrentVersionApkInfo.getAppPath()))+"\",\"new_apk_md5\":\""+MD5Utils.getFileMD5(new File(dbMaxVersionApkInfo.getAppPath()))+"\",\"verCode\":"+dbMaxVersionApkInfo.getVerCode()+",\"verName\":\""+dbMaxVersionApkInfo.getVerName()+"\",\"size\":"+new File(patch).length()+",\"updatemessage\":"+dbMaxVersionApkInfo.getUpdatemessageJSON()+",\"url\": \""+basePath+"\"}");
				writer.flush();
				writer.close();
			}
			else
			{
				writer.print("{\"status\": false,\"errCode\": 107,\"message\": \"服务器出现异常\"}");
				writer.flush();
				writer.close();
			}
		}

	}
	private String getIp(HttpServletRequest request){
		  return request.getServerName();
		}
}
