package com.xiaotangbao.ebook.servlet;

import com.xiaotangbao.ebook.biz.UserBiz;
import com.xiaotangbao.ebook.entity.User;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * 图书阅读
 *
 * @author  SunJianwei<327021593@qq.com>
 * @date    16-5-29 22:33
 */
@WebServlet(name = "ReadBookSvl", urlPatterns="/user/ReadBookSvl")
public class ReadBookSvl extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        try {
            User user = (User) request.getSession().getAttribute("user");
            if (null == user) {
                response.sendRedirect(request.getContextPath() + "/main/login.jsp");
            }
            String bookParam = request.getParameter("book");
            if (null == bookParam || bookParam.isEmpty()) {
                throw new Exception("参数错误，少book");
            }
            int bookId = Integer.parseInt(bookParam);
            String pageStr = request.getParameter("page");
            int page = 0;
            if (null != pageStr && !pageStr.isEmpty()) {
                page = Integer.parseInt(pageStr);
            }
            page = new UserBiz().canReadBook(user, bookId, page);
            // 没有权限读
            if (page < 0) {
                throw new Exception("继续读需要购买");
            }
            request.setAttribute("page", page);
            request.setAttribute("bookId", bookId);
            request.getRequestDispatcher("/main/readbook.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errmsg", e.getMessage());
            request.setAttribute("errClass", e.getClass().getName());
            request.getRequestDispatcher("/main/error.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
