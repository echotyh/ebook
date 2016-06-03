package com.xiaotangbao.ebook.servlet;

import com.xiaotangbao.ebook.biz.BookBiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-6-3 12:43
 */
@WebServlet(name = "BookDetial", urlPatterns = "/servlet/BookDetail")
public class BookDetial extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        try {
            // 处理参数
            String idStr = request.getParameter("book");
            int id = Integer.parseInt(idStr);
            // 查图书数据
            BookBiz bookBiz = new BookBiz();
            Map<String, Object> bookInfo = bookBiz.getBookInfoById(id);
            // 处理结果 跳转
            if (null == bookInfo) {
                request.setAttribute("errmsg", "图书已经不存在了");
                request.getRequestDispatcher("/main/error.jsp").forward(request, response);
            } else {
                request.setAttribute("book", bookInfo);
                request.getRequestDispatcher("/main/book_detail.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errmsg", "参数类型错误，book参数应该为数字形式的id");
            request.getRequestDispatcher("/main/error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errmsg", e.getMessage());
            request.getRequestDispatcher("/main/error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
