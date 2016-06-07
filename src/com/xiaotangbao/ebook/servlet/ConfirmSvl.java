package com.xiaotangbao.ebook.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaotangbao.ebook.biz.OrderBiz;
import com.xiaotangbao.ebook.biz.ShoppingCartBiz;
import com.xiaotangbao.ebook.biz.UserBiz;
import com.xiaotangbao.ebook.entity.User;

public class ConfirmSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ConfirmSvl() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
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

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			User loginUser = (User) request.getSession(true).getAttribute(
					"user");
			int userid = loginUser.getUserid();
			if (null == loginUser) {
				request.getRequestDispatcher("/main/login.jsp").forward(
						request, response);
			}
			ShoppingCartBiz cartbiz = new ShoppingCartBiz();
			List<Map<String, Object>> books = cartbiz.getItems(loginUser
					.getUserid());
			int sum = 0;
			for (Map<String, Object> item : books) {
				int price = (int) item.get("price");
				BigDecimal discount = (BigDecimal) item.get("discount");
				int num = (int) item.get("booknum");
				sum += (int) (price * discount.doubleValue() * num);
			}
			if (sum != Integer.parseInt(request.getParameter("money"))) {
				throw new Exception("价格出现变动，请重新在购物车中查看哦。");
			}
			int expenditure = loginUser.getExpenditure();
			if (sum <= expenditure) {
				OrderBiz orderbiz = new OrderBiz();
				Date date = new Date();
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(date);
				for (Map<String, Object> item : books) {
					int bookId = (int) item.get("bookid");
					BigDecimal discount = (BigDecimal) item.get("discount");
					BigDecimal price = new BigDecimal(
							((int) item.get("price") * discount.floatValue()) / 10);
					orderbiz.addOrder(userid, bookId, time, "y", price);
					UserBiz userbiz = new UserBiz();
					userbiz.updateExpend(userid, loginUser.getExpenditure()
							- sum);

				}

				throw new Exception("你已购买成功，现在可以阅读啦！！！");
			} else {
				OrderBiz orderbiz = new OrderBiz();
				Date date = new Date();
				String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(date);
				for (Map<String, Object> item : books) {
					int bookId = (int) item.get("bookid");
					BigDecimal discount = (BigDecimal) item.get("discount");
					BigDecimal price = new BigDecimal(
							((int) item.get("price") * discount.floatValue()) / 10);
					orderbiz.addOrder(userid, bookId, time, "n", price);

					throw new Exception("你的余额已不足，请及时充值，并不要忘记再来购买这本书哦！！！");
				}

			}
		} catch (Exception e) {
			request.setAttribute("errmsg", e.getMessage());
			request.getRequestDispatcher("/main/error.jsp").forward(request,
					response);
		}

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
