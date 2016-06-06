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
import java.util.HashMap;
import java.util.Map;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-6-6 17:57
 */
@WebServlet(name = "Add2ShoppingCart", urlPatterns = "/user/add2shoppingcart")
public class Add2ShoppingCart extends HttpServlet {
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

            String numStr = request.getParameter("num");
            int num = 1;
            if (null != numStr && !numStr.isEmpty()) {
                num = Integer.parseInt(numStr);
            }
            ShoppingCartBiz shoppingCartBiz = new ShoppingCartBiz();
            shoppingCartBiz.addItem(loginUser.getUserid(), bookId, num);
            // 成功时的返回
            result.put("errno", 0);
        } catch (NumberFormatException e) {
            result.put("errno", 1);
            result.put("errmsg", "参数转换成数字出错了");
        } catch (Exception e) {
            result.put("errno", 2);
            result.put("errmsg", e.getMessage());
        } finally {
            out.print(JSONObject.toJSONString(result));
            out.flush();
            out.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
