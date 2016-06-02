package com.xiaotangbao.ebook.servlet;

import com.xiaotangbao.ebook.biz.BookBiz;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 首页
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-6-2 13:19
 */
@WebServlet(name = "Home", urlPatterns = "/home")
public class Home extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int limit = 6;
        BookBiz bookBiz = new BookBiz();
        List<Map<String, Object>> grades = bookBiz.getByGradeDesc(0, limit);
        if (null != grades && !grades.isEmpty()) {
            request.setAttribute("grades", grades);
        }
        List<Map<String, Object>> time = bookBiz.getByTimeDesc(0, limit);
        if (null != time && !time.isEmpty()) {
            request.setAttribute("time", time);
        }
        List<Map<String, Object>> price = bookBiz.getByPriceDesc(0, limit);
        if (null != price && !price.isEmpty()) {
            request.setAttribute("price", price);
        }
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        request.getRequestDispatcher("/main/home.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
