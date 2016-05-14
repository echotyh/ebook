package com.xiaotangbao.ebook.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaotangbao.ebook.dao.UserDao;

public class ValidateUsername extends HttpServlet {

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        
		UserDao userdao = new UserDao();
		List<String> names = new ArrayList<String>();
		try {
			names = userdao.getAllNames();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		String userName = request.getParameter("user_name");
		String result = null;
        if(names.contains(userName)){
        	result = "<font color='red'>该用户名已经被注册</font>";
        }else{
        	result = "<font color='green'>该用户名可用</font>";
        }
			response.setContentType("text/html, pageEncoding='UTF-8'");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(result);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
