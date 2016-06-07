package com.xiaotangbao.ebook.servlet;

import com.xiaotangbao.ebook.biz.ShoppingCartBiz;
import com.xiaotangbao.ebook.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-6-6 18:52
 */
@WebServlet(name = "ShowShoppingCart", urlPatterns = "/user/showshoppingcart")
public class ShowShoppingCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User loginUser = (User) request.getSession(true).getAttribute("user");
            if (null == loginUser) {
                throw new Exception("未登录");
            }

            ShoppingCartBiz shoppingCartBiz = new ShoppingCartBiz();
            List<Map<String, Object>> shoppingCartItems = shoppingCartBiz.getItems(loginUser.getUserid());
            // 计算总计价
            int sum = 0;
            int count = 0;
            for (Map<String, Object> item : shoppingCartItems) {
                int price = (int) item.get("price");
                BigDecimal discount = (BigDecimal) item.get("discount");
                sum += (int) (price * discount.doubleValue());
                count ++ ;
            }
            request.setAttribute("books", shoppingCartItems);
            request.setAttribute("booknum", count);
            request.setAttribute("totalPrice", sum);
            request.getRequestDispatcher("/user/shoppingCar.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errmsg", e.getMessage());
            request.getRequestDispatcher("/main/error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
