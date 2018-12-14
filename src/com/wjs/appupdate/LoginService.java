package com.wjs.appupdate;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wjs.utils.StringUtils;

public class LoginService extends HttpServlet 
{
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException 
    {
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String usernametext=request.getParameter("usernametext");
		String passwordtext=request.getParameter("passwordtext");
		if(!StringUtils.isNull(usernametext)&&!StringUtils.isNull(passwordtext)&&usernametext.equals("root")&&passwordtext.equals("root"))
		{
			HttpSession session = request.getSession();
			session.setAttribute("usernametext", usernametext);
			session.setAttribute("passwordtext", passwordtext);
			request.getRequestDispatcher("/index.jsp").forward(request, response);	
			return;
		}
		else
		{
			HttpSession session = request.getSession();
			boolean us=StringUtils.isNull((String)session.getAttribute("usernametext"));
			boolean p=StringUtils.isNull((String)session.getAttribute("passwordtext"));
			if(!us&&!p)
			{
				session.setAttribute("usernametext", usernametext);
				session.setAttribute("passwordtext", passwordtext);
				request.getRequestDispatcher("/index.jsp").forward(request, response);
				return;
			}
			else
			{
				response.sendRedirect("login.html");
				return;
			}
		}
	}

}
