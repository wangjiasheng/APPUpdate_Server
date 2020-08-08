<%@page import="com.wjs.utils.DecoderEncoderUtils"%>
<%@page import="com.wjs.bean.ApkInfo"%>
<%@page import="com.wjs.utils.JDBC"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="com.wjs.utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="test/jquery-1.4.4.min.js"></script>
<style type="text/css">
*
{
	padding:0;
	margin:0;
	box-sizing:border-box;
}
html,body
{
    height:100%
}
.code
{
	width:100%;height:95%;line-height:height:normal;overflow:auto;overflow-x:hidden;
}
.page
{
   height:5%;line-height:40px;overflow:hidden;vertical-align:middle;padding-left: 10px;
}
a
{
	padding-left: 2xp;
	padding-right: 2px;
}
table {
     border-collapse: collapse;
     font-family: Futura, Arial, sans-serif;
}

caption {
     font-size: larger;
     margin: 1em auto;
}
th,td {
     padding: .65em;
}
th 
{
   background: #555 nonerepeat scroll 0 0;
   color: #58565b;
   height: 50px;
}
td
{
}
tbody tr:nth-child(odd) {
     background: #e9eaec;
}

th:first-child {
     border-radius: 4px 0 0 0;
}

th:last-child {
     border-radius: 0 4px 0 0;
}

tr:last-child td:first-child {
     border-radius: 0 0 0 4px;
}

tr:last-child td:last-child {
     border-radius: 0 0 4px 0;
}
</style>
<script>
    function delete_order_dialog(order_id)
    {    
        confirm_ = confirm('This action will delete current order! Are you sure?');
        if(confirm_)
        {
            delete_order(order_id);
        }
    };
    function delete_order(order_id)
    {
    	$.ajax(
            {
                type:"POST",
                url:'Delete?code_id='+order_id,
                success:function(msg)
                {
	               if(msg)
	               {
		                if($(".item").size()==1)
		                {
		                	var no=$('#pageNo').val();
		                	if(no>1)
		                	{
		                   	  location.replace($('#url').val()+"?pageNo="+(no-1));
		                    }
		                    else
		                    {
		                       location.reload();
		                    }
		                }
		                else
		                {
		                    $("#tr_"+order_id).remove();
		                }
	               }
	               else
	               {
	               		alert('删除失败');
	               }
                }
            });
    }
    function paushMessage(path){
        $.ajax(
			{
				type:"POST",
				url:'PushMessage?path='+path,
				success:function (msg) {
					if(msg)
					{
                        alert("推送成功")
					}
					else{
					    alert('推送失败');
					}
                }
			});
	}
</script>
</head>
<body>
	<%
	String usernametext=(String)session.getAttribute("usernametext");
	String passwordtext=(String)session.getAttribute("passwordtext");
	if(!StringUtils.isNull(usernametext)&&!StringUtils.isNull(passwordtext))
	{
 	%>
		<%
			int pageNo=1;
			int pageSize=25;
			int botton=10;//底部页码个数
			int size=JDBC.count();
			String temp=request.getParameter("pageNo");
			if(temp!=null&&!temp.equals("")&&temp.matches("\\d+"))
			{
				pageNo=Integer.parseInt(temp);
			}
			if(pageNo!=0)
			{
				List<ApkInfo> codelist=JDBC.query(pageSize, pageNo);
				out.println("\r\n<input type=\"hidden\" id=\"pageNo\" name=\"pageNo\" value=\""+pageNo+"\">\r\n");
				out.println("<input type=\"hidden\" id=\"url\" name=\"url\" value=\""+request.getRequestURI()+"\">\r\n");
				out.print("<div id=\"code\" class=\"code\">");
				out.println("<table></br>\r\n");
				out.println("<tr>");
				out.println("<th oncopy=\"event.returnValue=false;\" ondragstart=\"window.event.returnValue=false\" oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"event.returnValue=false\">启动图标</th>");
				out.println("<th oncopy=\"event.returnValue=false;\" ondragstart=\"window.event.returnValue=false\" oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"event.returnValue=false\">包名</th>");
				out.println("<th oncopy=\"event.returnValue=false;\" ondragstart=\"window.event.returnValue=false\" oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"event.returnValue=false\">App名称</th>");
				out.println("<th oncopy=\"event.returnValue=false;\" ondragstart=\"window.event.returnValue=false\" oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"event.returnValue=false\">版本名称</th>");
				out.println("<th oncopy=\"event.returnValue=false;\" ondragstart=\"window.event.returnValue=false\" oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"event.returnValue=false\">版本号码</th>");
				out.println("<th oncopy=\"event.returnValue=false;\" ondragstart=\"window.event.returnValue=false\" oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"event.returnValue=false\">MD5</th>");
				out.println("<th oncopy=\"event.returnValue=false;\" ondragstart=\"window.event.returnValue=false\" oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"event.returnValue=false\">操作</th>");
				out.println("<th oncopy=\"event.returnValue=false;\" ondragstart=\"window.event.returnValue=false\" oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"event.returnValue=false\">操作</th>");
				out.println("<th oncopy=\"event.returnValue=false;\" ondragstart=\"window.event.returnValue=false\" oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"event.returnValue=false\">推送下载</th>");
				out.println("<th oncopy=\"event.returnValue=false;\" ondragstart=\"window.event.returnValue=false\" oncontextmenu=\"window.event.returnValue=false\" onselectstart=\"event.returnValue=false\">测试推送</th>");
				out.println("</tr>");
				for(int i=0;i<codelist.size();i++)
				{
					ApkInfo bean=codelist.get(i);
					out.println("<tr class=\"item\" id=\"tr_"+bean.getPackageName().replaceAll("\\.", "wjs_wjs")+"wjs_wjs"+bean.getVerCode()+"\">");
					out.println("<td>"+bean.getIconName()+"</td>");
					out.println("<td>"+bean.getPackageName()+"</td>");
					out.println("<td>"+bean.getAppName()+"</td>");
					out.println("<td>"+bean.getVerName()+"</td>");
					out.println("<td>"+bean.getVerCode()+"</td>");
					out.println("<td>"+bean.getApkmd5()+"</td>");
					out.println("<td><a href=\""+(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+"Down?path="+DecoderEncoderUtils.encodeFilepath(bean.getAppPath()))+"\">下载</a></td>");
					out.println("<td><a href=\"javascript:;\"  onclick=\"delete_order('"+bean.getPackageName().replaceAll("\\.", "wjs_wjs")+"wjs_wjs"+bean.getVerCode()+"')\">删除</a></td>");
					out.println("<td><a href=\"javascript:;\"  onclick=\"paushMessage('"+(DecoderEncoderUtils.encodeFilepath(bean.getAppPath())+"&tag=all")+"')\">推送</a></td>");
					out.println("<td><a href=\"javascript:;\"  onclick=\"paushMessage('"+(DecoderEncoderUtils.encodeFilepath(bean.getAppPath())+"&tag=wangjiashheng")+"')\">测试推送</a></td>");
					out.println("</tr>");
				}
				codelist.clear();
				codelist=null;
				out.print("</table>");
				out.println("</div>");
				boolean mode=(pageNo%botton==1)&&pageNo!=1;//判断是点击第一页还是最后一页，判断底部分页
				if(mode)
				{
					int code=pageNo/botton;    //判断是否是一个整超过一个   底部分页 例如:0-10 11-20
					long from=(code-1)*botton;     //从哪一页开始
					System.out.print(pageNo+"-"+from);
					long to=code*botton;  //到哪一页
					out.print("<div id=\"page\" class=\"page\">");
					for(long i=from;i<to;i++)
					{
						out.print("<a href=\"apk_list.jsp?pageNo="+(i+1)+"\"> "+(i+1)+" </a>");
					}	
					out.print("</div>");
				}
				else
				{
					int code=pageNo/botton;    //判断是否是一个整超过一个   底部分页 例如:0-10 11-20
					long from=code*botton;     //从哪一页开始
					long tmp=(code+1)*botton;  //到哪一页
					/*计算页数*/
					long t=size%pageSize;
					long yeshu=0;
					if(t>0)
					{
						yeshu=size/pageSize+1;
					}
					else
					{
					    yeshu=size/pageSize;
					}
					
					long to=tmp>yeshu?yeshu:tmp;//结束页
					out.print("<div id=\"page\" class=\"page\">");
					if(from==to&&(from-botton>0))//后来出现最后一页为空  from==to
					{
						from=from-botton;
					}
					if(from==0&&to==0)//如果只有一页 from==to==0
					{
						to=1;
					}
					for(long i=from;i<to;i++)
					{
						out.print("<a href=\"apk_list.jsp?pageNo="+(i+1)+"\"> "+(i+1)+" </a>");
					}	
					out.print("</div>");
				}
			}
			else
			{
				response.sendRedirect("login.html");
				return;
			}
	    %>
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