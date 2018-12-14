package com.wjs.utils;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.wjs.bean.ApkInfo;
import com.wjs.bean.DBInfo;

public class JDBC
{
	private static DBInfo dbinfo;
	public static DBInfo getProperties()
	{
		if(dbinfo==null)
		{
			try
			{
				InputStream in = JDBC.class.getClassLoader().getResourceAsStream("JDBC.properties");
				Properties properties=new Properties();
				properties.load(in);
				String driver = properties.getProperty("driver");
				String url = properties.getProperty("url");
				String username = properties.getProperty("username");
				String password = properties.getProperty("password");
				dbinfo=new DBInfo();
				dbinfo.setDriver(driver);
				dbinfo.setUrl(url);
				dbinfo.setUsername(username);
				dbinfo.setPassword(password);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return dbinfo;
	}
	public static Connection getConn() 
	{
		DBInfo properties2 = getProperties();
	    Connection conn = null;
	    try 
	    {
	    	if(properties2!=null)
	    	{
	    		Class.forName(properties2.getDriver()); //classLoader,加载对应驱动
	    		conn = (Connection) DriverManager.getConnection(properties2.getUrl(), properties2.getUsername(),properties2.getPassword());
	    	}
	    	else
	    	{
	    		throw new NullPointerException("数据库配置文件不存在\r\n请复制JDBC.properties到如下路径:\r\nWebapp/WEB-INF/lib\r\nWebapp/WEB-INF/classes\r\nAppserver/lib\r\nJRE/lib\r\n");
	    	}
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	public static boolean createTable()
	{
		StringBuffer sql=new StringBuffer();
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
		Connection conn = getConn();
		PreparedStatement pstmt = null;
		int i=-1;
		try 
		{
			pstmt = (PreparedStatement)conn.prepareStatement(sql.toString());
			i = pstmt.executeUpdate();
		}
		catch (SQLException e) 
		{
		}
		finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(i==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static boolean dropTable()
	{
		StringBuffer sql=new StringBuffer();
		sql.append("DROP TABLE IF EXISTS apkinfo;");
		Connection conn = getConn();
		PreparedStatement pstmt = null;
		int i=-1;
		try 
		{
			pstmt = (PreparedStatement)conn.prepareStatement(sql.toString());
			i = pstmt.executeUpdate();
		}
		catch (SQLException e) 
		{
		}
		finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(i==0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public static int insert(ApkInfo student) 
	{
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "insert into apkinfo (packageName,appName,verName,verCode,iconName,appPath,iconPath,apkmd5,updatemessage) values(?,?,?,?,?,?,?,?,?)";
	    PreparedStatement pstmt = null;
	    try 
	    {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setString(1, student.getPackageName());
	        pstmt.setString(2, student.getAppName());
	        pstmt.setString(3, student.getVerName());
	        pstmt.setString(4, student.getVerCode());
	        pstmt.setString(5, student.getIconName());
	        pstmt.setString(6, student.getAppPath());
	        pstmt.setString(7, student.getIconPath());
	        pstmt.setString(8, student.getApkmd5());
	        pstmt.setString(9, student.getUpdatemessage());
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } 
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    return i;
	}
	public static boolean has(ApkInfo info)
	{
		Connection conn = getConn();
	    String sql = "select * from apkinfo where packageName='"+info.getPackageName()+"' and verCode='"+info.getVerCode()+"'";
	    PreparedStatement pstmt = null;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) 
	        {
	           return true;
	        }
	        else
	        {
	        	return false;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    return false;
	}
	public static ApkInfo checkUpdate(String packName)
	{
		Connection conn = getConn();
		String sql = "select id,packageName,appName,verName,verCode,iconName,appPath,iconPath,apkmd5,updatemessage from apkinfo where packageName = '"+packName+"' and verCode=(select max(verCode) as verCode from apkinfo where packageName = '"+packName+"');";
		PreparedStatement pstmt = null;
	    try 
	    {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        if(rs.next()) 
	        {
	           ApkInfo apkInfo=new ApkInfo();
	           apkInfo.setId(rs.getInt("id"));
	           apkInfo.setPackageName(rs.getString("packageName"));
	           apkInfo.setAppName(rs.getString("appName"));
	           apkInfo.setVerName(rs.getString("verName"));
	           apkInfo.setVerCode(rs.getString("verCode"));
	           apkInfo.setIconName(rs.getString("iconName"));
	           apkInfo.setAppPath(rs.getString("appPath"));
	           apkInfo.setIconPath(rs.getString("iconPath"));
	           apkInfo.setApkmd5(rs.getString("apkmd5"));
	           apkInfo.setUpdatemessage(rs.getString("updatemessage"));
	           return apkInfo;
	        }
	    } 
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static ApkInfo getApkInfo(String packName,String verCode)
	{
		Connection conn = getConn();
		String sql = "select id,packageName,appName,verName,verCode as verCode,iconName,appPath,iconPath,apkmd5,updatemessage from apkinfo where packageName = '"+packName+"' and verCode='"+verCode+"';";
		PreparedStatement pstmt = null;
	    try 
	    {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        if(rs.next()) 
	        {
	           ApkInfo apkInfo=new ApkInfo();
	           apkInfo.setId(rs.getInt("id"));
	           apkInfo.setPackageName(rs.getString("packageName"));
	           apkInfo.setAppName(rs.getString("appName"));
	           apkInfo.setVerName(rs.getString("verName"));
	           apkInfo.setVerCode(rs.getString("verCode"));
	           apkInfo.setIconName(rs.getString("iconName"));
	           apkInfo.setAppPath(rs.getString("appPath"));
	           apkInfo.setIconPath(rs.getString("iconPath"));
	           apkInfo.setApkmd5(rs.getString("apkmd5"));
	           apkInfo.setUpdatemessage(rs.getString("updatemessage"));
	           return apkInfo;
	        }
	    } 
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static List<ApkInfo> query(int pageSize,int pageNumber)
	{
		Connection conn = getConn();
		String sql = "select id,packageName,appName,verName,verCode as verCode,iconName,appPath,iconPath,apkmd5,updatemessage from apkinfo where id limit "+((pageNumber-1)*pageSize)+","+pageSize+";";
		List<ApkInfo> list=new ArrayList<ApkInfo>();
		PreparedStatement pstmt = null;
	    try 
	    {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while(rs.next()) 
	        {
	           ApkInfo apkInfo=new ApkInfo();
	           apkInfo.setId(rs.getInt("id"));
	           apkInfo.setPackageName(rs.getString("packageName"));
	           apkInfo.setAppName(rs.getString("appName"));
	           apkInfo.setVerName(rs.getString("verName"));
	           apkInfo.setVerCode(rs.getString("verCode"));
	           apkInfo.setIconName(rs.getString("iconName"));
	           apkInfo.setAppPath(rs.getString("appPath"));
	           apkInfo.setIconPath(rs.getString("iconPath"));
	           apkInfo.setApkmd5(rs.getString("apkmd5"));
	           apkInfo.setUpdatemessage(rs.getString("updatemessage"));
	           list.add(apkInfo);
	        }
	        return list;
	    } 
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static ApkInfo query(String apkinfoid)
	{
		Connection conn = getConn();
		String sql = "select id,packageName,appName,verName,verCode,iconName,appPath,iconPath,apkmd5,updatemessage from apkinfo where id = '"+apkinfoid+"';";
		PreparedStatement pstmt = null;
	    try 
	    {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        if(rs.next()) 
	        {
	           ApkInfo apkInfo=new ApkInfo();
	           apkInfo.setId(rs.getInt("id"));
	           apkInfo.setPackageName(rs.getString("packageName"));
	           apkInfo.setAppName(rs.getString("appName"));
	           apkInfo.setVerName(rs.getString("verName"));
	           apkInfo.setVerCode(rs.getString("verCode"));
	           apkInfo.setIconName(rs.getString("iconName"));
	           apkInfo.setAppPath(rs.getString("appPath"));
	           apkInfo.setIconPath(rs.getString("iconPath"));
	           apkInfo.setApkmd5(rs.getString("apkmd5"));
	           apkInfo.setUpdatemessage(rs.getString("updatemessage"));
	           return apkInfo;
	        }
	    } 
	    catch (SQLException e)
	    {
	        e.printStackTrace();
	    }
	    finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static int count() 
	{
	    Connection conn = getConn();
	    String sql = "select count(*) as size from apkinfo;";
	    PreparedStatement pstmt = null;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        if(rs.next()) 
	        {
	           return rs.getInt("size");
	        }
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    return 0;
	}
	public static int delete(String packageName,String verCode) 
	{
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "delete from apkinfo where packageName='" + packageName + "'"+"and verCode='"+verCode+"';";
	    PreparedStatement pstmt = null;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    return i;
	}
	public static int update(ApkInfo student) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "update apkinfo set apkmd5='"+student.getApkmd5() +"' where packageName='" + student.getPackageName() + "'"+" and verCode='"+student.getVerCode()+"'";
	    PreparedStatement pstmt = null;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        System.out.println("resutl: " + i);
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    return i;
	}
	public static List<ApkInfo> getAll() {
	    Connection conn = getConn();
	    String sql = "select * from apkinfo";
	    List<ApkInfo> list=new ArrayList<ApkInfo>();
	    PreparedStatement pstmt = null;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) 
	        {
	        	ApkInfo apkInfo=new ApkInfo();
	        	try
	        	{
	             apkInfo.setPackageName(rs.getString("packageName"));
	             apkInfo.setId(rs.getInt("id"));
	             apkInfo.setAppName(rs.getString("appName"));
	             apkInfo.setVerName(rs.getString("verName"));
	             apkInfo.setVerCode(rs.getString("verCode"));
	             apkInfo.setIconName(rs.getString("iconName"));
	             apkInfo.setAppPath(rs.getString("appPath"));
	             apkInfo.setIconPath(rs.getString("iconPath"));
	             apkInfo.setApkmd5(rs.getString("apkmd5"));
	        	}
	        	catch(Exception ex)
	        	{
	        		
	        	}
	           list.add(apkInfo);
	        }
	        return list;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    finally
		{
			try {
				sql=null;
				if(pstmt!=null)
				{
					pstmt.close();
					pstmt=null;
				}
				if(conn!=null)
				{
					conn.close();
					conn=null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    return null;
	}
}
