<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>404页面没有找到</title>
    <style type="text/css">
        
        * { margin: 0; padding: 0; }
        
                
        .words { width: 417px; height: 277px; background: url(public503245/images/error/error.png) no-repeat 0 0; position: absolute; left: 50%; top: 40%; margin-left: -209px; margin-top: -150px; }
        .btn { width: 240px; height: 55px; background: url(public503245/images/error/error.png) no-repeat -88px -445px; display: inline-block; position: absolute; left: 50%; top: 40%; margin-left: -156px;margin-top: 200px;}
        
            </style>
</head>
<body>
<div class="words"></div>
<a href="index.jsp" target="_self" title="去问财首页瞧一瞧" class="btn"></a>
</body>
</html>