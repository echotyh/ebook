package com.xiaotangbao.ebook.servlet;

import com.alibaba.fastjson.JSONObject;
import com.xiaotangbao.ebook.biz.ShoppingCartBiz;
import com.xiaotangbao.ebook.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-6-7 11:42
 */
@WebServlet(name = "DeleteCartItem", urlPatterns = "/user/deletecartitem")
public class DeleteCartItem extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Map<String, Object> result = new HashMap<>();
        try {
            User loginUser = (User) request.getSession(true).getAttribute("user");
            if (null == loginUser) {
                throw new Exception("未登录");
            }

            String bookIdStr = request.getParameter("book");
            if (null == bookIdStr || bookIdStr.isEmpty()) {
                throw new Exception("参数错误：需要有book");
            }
            int bookId = Integer.parseInt(bookIdStr);

            ShoppingCartBiz shoppingCartBiz = new ShoppingCartBiz();
            shoppingCartBiz.deleteItem(loginUser.getUserid(), bookId);
            // 成功时的返回
            request.getRequestDispatcher("/user/showshoppingcart").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("errmsg", "参数错误，需要是数字");
            request.getRequestDispatcher("/main/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errmsg", "参数错误，需要是数字");
            request.getRequestDispatcher("/main/error.jsp").forward(request, response);
        } finally {

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
