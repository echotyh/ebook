package com.xiaotangbao.ebook.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaotangbao.ebook.biz.UserBiz;
import com.xiaotangbao.ebook.entity.User;


public class LoginServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//System.out.println("这是个servlet");
		//request.getRequestDispatcher("/main/test.jsp").forward(request, response);
		    // TODO Auto-generated method stub
				 String user_name=request.getParameter("user_name");
				 String user_pwd=request.getParameter("user_pwd");
				 UserBiz userbiz=new UserBiz();
				 
				 User user=null;
				try {
					user = userbiz.login(user_name, user_pwd);
					//System.out.println("user"+user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					request.setAttribute("msg", "小服有点累，请稍等");
					
					e.printStackTrace();
					//request.getRequestDispatcher("/main/test.jsp").forward(request, response);
				}
				
					if(user!=null){
						 request.getSession().setAttribute("user",user );
						 request.getRequestDispatcher("AllBookSvl").forward(request, response);
						 
					 }else{
						 request.setAttribute("msg", "用户名或密码错误，请重新登录或注册");
						 request.getRequestDispatcher("/main/login.jsp").forward(request, response);
						 
					 }
	
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
