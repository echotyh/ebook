package com.xiaotangbao.ebook.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.log.Log;
import com.xiaotangbao.ebook.biz.UserBiz;
import com.xiaotangbao.ebook.dao.UserDao;
import com.xiaotangbao.ebook.entity.User;

public class RegisterSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public RegisterSvl() {
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
		request.setCharacterEncoding("utf-8");
		//如果这样还不行的话，
		
		
		          String user_name=request.getParameter("user_name");
		// 把乱码的变量重新转码,cong iso-8859-1  =>  utf-8
		//user_name = new String(user_name.getBytes("iso-8859-1"), "utf-8");
		          String user_pwd=request.getParameter("user_pwd");
		          String user_phone=request.getParameter("user_phone");
		          String user_gender = request.getParameter("user_gender");
		          String user_status = request.getParameter("user_status");
		          String user_email = request.getParameter("user_email");
		          String user_birth = request.getParameter("user_birth");
		          
		          User user = new User(user_name, user_pwd, user_status, user_gender,
		      			user_birth, user_phone, user_email, 0);
		          
		          UserBiz userbiz = new UserBiz();
		          try{
					if(userbiz.register(user)>0){
						  request.setAttribute("msg", "恭喜您注册成功");
						  request.getRequestDispatcher("/main/message.jsp").forward(request, response);
					  }else{
						   request.setAttribute("msg", "哦啊，服务器出去卖（鸡汤）了~最多刷新7次即可召唤它回来!");
						   request.getRequestDispatcher("/main/register.jsp").forward(request, response);
					  }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					request.setAttribute("msg"," 哦啊，服务器出去卖（鸡汤）了~最多刷新7次即可召唤它回来~" );
					request.getRequestDispatcher("/main/register.jsp").forward(request, response);
				}
		          
	}
	public void init() throws ServletException {
		// Put your code here
	}

}
