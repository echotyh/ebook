package com.xiaotangbao.ebook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaotangbao.ebook.biz.UserBiz;
import com.xiaotangbao.ebook.entity.User;

public class GetBoughtBooks extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public GetBoughtBooks() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
		
	}

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User loginUser = (User) request.getSession(true).getAttribute("user");
		if (null == loginUser) {
			request.getRequestDispatcher("/main/login.jsp").forward(
					request, response);
		}
		int userid = loginUser.getUserid();
		UserBiz userbiz = new UserBiz();
		try {
			List<Map<String,Object>> books = userbiz.getBoughtBooks(userid);
			request.setAttribute("books", books);
			request.getRequestDispatcher("/user/mybooks.jsp").forward(
					request, response);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			
			e.printStackTrace();
			request.setAttribute("errmsg", "小服有点累，请稍后再试。");
			request.getRequestDispatcher("/main/error.jsp").forward(request,
					response);
		}
		
		

	}


	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


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
