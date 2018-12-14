<%@page import="java.util.Enumeration"%>
<%@page import="com.wjs.utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String usernametext=(String)session.getAttribute("usernametext");
	String passwordtext=(String)session.getAttribute("passwordtext");
	if(!StringUtils.isNull(usernametext)&&!StringUtils.isNull(passwordtext))
	{
 %>
 		<p><a href="apk_list.jsp" target="_self">管理页面</a></p>
	    <form action="${pageContext.request.contextPath}/UploadApk" enctype="multipart/form-data" method="post">
	    	<textarea rows="25" cols="150" name="message"></textarea><br>
	        apk上传：<input type="file" name="fileweb1"><br/>
	        <input type="hidden" name="usernametext" value="<%=usernametext%>">
	        <input type="hidden" name="passwordtext" value="<%=passwordtext%>">
	        <input type="submit" value="提交">
	    </form>
  <% 
	  }
 	else
 	{
 		response.sendRedirect("login.html");
 		return;
  	}
  	%>
</body>
</html>