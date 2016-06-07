package com.xiaotangbao.ebook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaotangbao.ebook.biz.CommentBiz;
import com.xiaotangbao.ebook.biz.OrderBiz;
import com.xiaotangbao.ebook.biz.UserBiz;
import com.xiaotangbao.ebook.entity.User;

public class CommentSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public CommentSvl() {
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

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the GET method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        
        User loginUser = (User) request.getSession(true).getAttribute(
				"user");
		if (null == loginUser) {
			request.getRequestDispatcher("/main/login.jsp").forward(
					request, response);
			return;
		}
        int bookid=Integer.parseInt(request.getParameter("bookid"));
        int userid = loginUser.getUserid();
        int grade = Integer.parseInt(request.getParameter("grade"));
        String comment = request.getParameter("comment");
        OrderBiz orderbiz = new OrderBiz();
        try {
        	boolean check = orderbiz.checkIfBoughtBook(userid, bookid);
			if(check == true){//添加评论
				
				Date date = new Date();
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
				CommentBiz commentbiz = new CommentBiz();
				commentbiz.addComment(userid, bookid, comment, grade, time);
				request.getRequestDispatcher("/servlet/BookDetail?book="+bookid).forward(
						request, response);	
			}else{
				request.setAttribute("errmsg", "你还未购买此书哦，想评论要先购买呀！");
				request.getRequestDispatcher("/main/error.jsp.jsp").forward(
						request, response);
				
			}
				
			}catch (Exception e) {
				request.setAttribute("errmsg", "小服有点累，请重试！");
				request.getRequestDispatcher("/main/error.jsp.jsp").forward(
						request, response);
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
